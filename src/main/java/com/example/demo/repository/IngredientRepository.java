package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.DishIngredient;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Price;
import com.example.demo.entity.StockMovement;
import com.example.demo.entity.Unit;
import com.example.demo.repository.mapper.IngredientMapper;
import com.jayway.jsonpath.Criteria;

import lombok.SneakyThrows;

@Repository
public class IngredientRepository implements RepositoryInterface<Ingredient> {
    private final DataSource dataSource;
    private final PriceRepository priceCrudOperations = new PriceRepository();
    private final StockMovementRepository stockMovementCrudOperations = new StockMovementRepository();
    private final IngredientMapper ingredientMapper;

    public IngredientRepository(){
        this.dataSource = new DataSource();
        this.ingredientMapper = new IngredientMapper(priceCrudOperations, stockMovementCrudOperations);
    }


    @Override
    public List<Ingredient> getAll(int page, int size) {
        List<Ingredient> ingredients = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select i.id, i.name, di.id as dish_ingredient_id, di.required_quantity, di.unit from " +
                    "ingredient i join dish_ingredient di on i.id = di.id_ingredient limit ? offset ?")) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size*(page-1));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ingredients.add(ingredientMapper.apply(resultSet));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  ingredients;
    }

    @Override
    public Ingredient findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select i.id, i.name, di.id as dish_ingredient_id, di.required_quantity, di.unit from ingredient i"
                     + " join dish_ingredient di on i.id = di.id_ingredient"
                     + " where i.id = ?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Ingredient ingredient = ingredientMapper.apply(resultSet);
                    return ingredient ;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

  /* @Override
    public List<Ingredient> filterByCriteria(List<Criteria> criterias, int page, int size, Map<String, String> sort) {
        List<Ingredient> ingredients = new ArrayList<>();
        List<String> criteriaKey = criterias.stream()
                .map(Criteria::getKey)
                .toList();
        List<String> targetKeys = List.of("id", "name");
        try(Connection connection = dataSource.getConnection()){
            if (!criterias.isEmpty()) {
                List<Long> ingredientsId = new ArrayList<>();
                Statement statement = connection.createStatement();
                for (Criteria criteria : criterias) {
                    if (criteriaKey.stream().noneMatch(targetKeys::contains)){
                        ResultSet resultSet = statement.executeQuery("select id from ingredient limit "+size+" offset "+size*(page-1));
                        while(resultSet.next()){
                            ingredientsId.add(resultSet.getLong("id"));
                        }
                        break;
                    }
                    if("name".equals(criteria.getKey())){
                        ResultSet resultSet = statement.executeQuery("select id from ingredient where name ilike '%" + criteria.getValue() +"%' limit "+size+" offset "+size*(page-1));
                        while(resultSet.next()){
                            ingredientsId.add(resultSet.getLong("id"));
                        }
                    } else if("id".equals(criteria.getKey())){
                        ingredientsId.add((Long) criteria.getValue());
                    }
                }
                criterias = criterias.stream().filter(criteria -> !"id".equals(criteria.getKey()) && !"name".equals(criteria.getKey())).toList();
                for(Long id : ingredientsId){
                    Ingredient ingredient = new Ingredient();
                    ingredient.setId(id);
                    ingredient.setName(findById(id).getName());
                    List<Price> prices = priceCrudOperations.filterByIngredientIdAndCriteria(id, criterias, sort);
                    ingredient.setPrices(prices);
                    List<StockMovement> stockMovements = stockMovementCrudOperations.filterByIngredientIdByCriteria(id, criterias, sort);
                    ingredient.setStockMovements(stockMovements);
                    if(prices.isEmpty() || stockMovements.isEmpty()){
                        continue;
                    }else{
                        ingredients.add(ingredient);
                    }
                }
            }
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        return ingredients;
    }
*/
   @SneakyThrows
    @Override
    public List<Ingredient> saveAll(List<Ingredient> entities) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement("insert into ingredient (id, name) values (?, ?)"
                                 + " on conflict (id) do update set name=excluded.name"
                                 + " returning id, name")) {
                entities.forEach(entityToSave -> {
                    try {

                        statement.setLong(1, entityToSave.getId());
                        statement.setString(2, entityToSave.getName());
                        statement.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                try (ResultSet resultSet = statement.executeQuery()) {
                    entities.forEach(entityToSave -> {
                        try {
                            priceCrudOperations.saveAll(entityToSave.getPrices());
                            stockMovementCrudOperations.saveAll(entityToSave.getStockMovements());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                    while (resultSet.next()) {
                        ingredients.add(ingredientMapper.apply(resultSet));
                    }
                }
                return ingredients;
            }
        }
    }

    public List<DishIngredient> findByDishId(Long dishId) {
        List<DishIngredient> dishIngredients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select i.id, i.name, di.id as dish_ingredient_id, di.required_quantity, di.unit from ingredient i"
                     + " join dish_ingredient di on i.id = di.id_ingredient"
                     + " where di.id_dish = ?")) {
            statement.setLong(1, dishId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Ingredient ingredient = ingredientMapper.apply(resultSet);
                    DishIngredient dishIngredient = mapDishIngredient(resultSet, ingredient);
                    dishIngredients.add(dishIngredient);
                }
                return dishIngredients;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   

    private DishIngredient mapDishIngredient(ResultSet resultSet, Ingredient ingredient) throws SQLException {
        double requiredQuantity = resultSet.getDouble("required_quantity");
        Unit unit = Unit.valueOf(resultSet.getString("unit"));
        Long dishIngredientId = resultSet.getLong("dish_ingredient_id");
        return new DishIngredient(dishIngredientId, ingredient, requiredQuantity, unit);
    }


    @Override
    public List<Ingredient> filterByCriteria(List<Criteria> criterias, int page, int size, Map<String, String> sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterByCriteria'");
    }

   

   
}
