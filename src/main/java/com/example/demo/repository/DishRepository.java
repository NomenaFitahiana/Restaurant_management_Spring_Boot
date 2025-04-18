package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Dish;
import com.example.demo.entity.DishIngredient;
import com.jayway.jsonpath.Criteria;

import lombok.SneakyThrows;

@Repository
public class DishRepository implements RepositoryInterface<Dish> {
    private final DataSource dataSource = new DataSource();

    public void connect(){
        try(Connection connection = dataSource.getConnection();){
            if(connection == null){
                System.out.println("Failed !");
            }
            System.out.println("Connected !");
        }catch(SQLException e ){
            throw new RuntimeException(e);
        }
    }
    private final IngredientRepository ingredientCrudOperations = new IngredientRepository();

    @Override
    public List<Dish> getAll(int page, int size) {
        List<Dish> dishes = new ArrayList<>();

        int offset = (page - 1) * size;

        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("select d.id, d.name, d.price from dish d limit ? offset ?")) {
            statement.setInt(1, size);
            statement.setInt(2, offset);
       try (ResultSet resultSet = statement.executeQuery()) {

           while (resultSet.next()) {
               dishes.add(mapFromResultSet(resultSet));
           }
           return dishes;
       }
   } catch (SQLException e) {
       throw new RuntimeException(e);
   }
    }

    @Override
    public Dish findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select d.id, d.name, d.price from dish d where id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapFromResultSet(resultSet);
                }
            }
            throw new RuntimeException("Dish.id=" + id + " not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Dish> filterByCriteria(List<Criteria> criterias, int page, int size, Map<String, String> sort) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private Dish mapFromResultSet(ResultSet resultSet) throws SQLException {
        Long idDish = resultSet.getLong("id");

        Dish dish = new Dish();
        dish.setId(idDish);
        dish.setName(resultSet.getString("name"));
        dish.setPrice(resultSet.getDouble("price"));
        List<DishIngredient> dishIngredients = ingredientCrudOperations.findByDishId(idDish);
        dish.setDishIngredients(dishIngredients);

        return dish;
    }

    @SneakyThrows
    @Override
    public List<Dish> saveAll(List<Dish> entities) {
        List<Dish> dishes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement("insert into dish (id, name, price) values (?, ?, ?)"
                                 + " on conflict (id) do update set name=excluded.name, price=excluded.price"
                                 + " returning id, name, price")) {
                entities.forEach(entityToSave -> {
                    try {
                        statement.setLong(1, entityToSave.getId());
                        statement.setString(2, entityToSave.getName());
                        statement.setDouble(3, entityToSave.getPrice());
                        statement.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        dishes.add(mapFromResultSet(resultSet));
                    }
                }
                return dishes;
            }
        }
    }
}
