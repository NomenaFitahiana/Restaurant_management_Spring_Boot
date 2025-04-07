package com.example.demo.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Price;
import com.example.demo.repository.mapper.PriceMapper;
import com.jayway.jsonpath.Criteria;

import lombok.SneakyThrows;

@Repository
public class PriceRepository implements RepositoryInterface<Price> {
     private final DataSource dataSource;
     private final PriceMapper priceMapper;

     public PriceRepository(){
        this.dataSource = new DataSource();
        this.priceMapper = new PriceMapper();
     }

    @Override
    public List<Price> getAll(int page, int size) {
         List<Price> prices = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select p.id, p.amount, p.date_value, p.id_ingredient from " +
                    "price limit ? offset ?")) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size*(page-1));

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                prices.add(priceMapper.apply(resultSet));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  prices;
    }

    @Override
    public Price findById(Long id) {
        Price price = new Price();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select p.id, p.amount, p.date_value, p.id_ingredient from " +
                    "price where id = ?")) {
             preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
              price =   priceMapper.apply(resultSet);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  price;
    }

  /*   @Override
    public List<Price> filterByIngredientIdAndCriteria(Long ingredientId, List<Criteria> criterias, Map<String, String> sort){
        List<Price> prices = new ArrayList<>();
        StringBuilder query = new StringBuilder("select distinct p.id, p.id_ingredient, p.amount, p.date_value from price p ");
        List<String> criteriaKey = criterias.stream()
                .map(Criteria::getKey)
                .toList();
        List<String> targetKeys = List.of("amount", "date");
        if (criteriaKey.stream().anyMatch(targetKeys::contains)) {
            query.append(" where ");
            for (Criteria criteria : criterias) {
                StringBuilder conditions = new StringBuilder();
                if (criteria.getValue() instanceof Date || criteria.getValue() instanceof LocalDate) {
                    conditions.append("p.date_value").append(" ").append(criteria.getOperation()).append(" '").append(criteria.getValue()).append("' ");
                    query.append(conditions).append(" ").append(criteria.getConjunction().toLowerCase()).append(" ");
                } else if ("amount".equals(criteria.getKey())) {
                    conditions.append("p.amount").append(" ").append(criteria.getOperation()).append(" ").append(criteria.getValue());
                    query.append(conditions).append(" ").append(criteria.getConjunction().toLowerCase()).append(" ");
                }
            }
            query.append(" p.id_ingredient=").append(ingredientId);
        }else{
            query.append(" where p.id_ingredient=").append(ingredientId);
        }

        if (!sort.isEmpty()) {
            query.append(" order by ");
            List<String> orderClauses = sort.entrySet().stream()
                    .map(entry -> "p." + entry.getKey() + " " + entry.getValue())
                    .toList();
            query.append(String.join(", ", orderClauses));
        }

        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                prices.add(mapFromResultSet(resultSet));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return prices;
    }
*/

    @SneakyThrows
    @Override
    public List<Price> saveAll(List<Price> entities) {
        List<Price> prices = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("insert into price (id, amount, date_value, id_ingredient) values (?, ?, ?, ?)"
                             + " on conflict (id) do nothing"
                             + " returning id, amount, date_value, id_ingredient");) {
            entities.forEach(entityToSave -> {
                try {
                    statement.setLong(1, entityToSave.getId());
                    statement.setDouble(2, entityToSave.getAmount());
                    statement.setDate(3, Date.valueOf(entityToSave.getDateValue()));
                    statement.setLong(4, entityToSave.getIngredient().getId());
                    statement.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    prices.add(priceMapper.apply(resultSet));
                }
            }
            return prices;
        }
    }

    public List<Price> findByIdIngredient(Long idIngredient) {
        List<Price> prices = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select p.id, p.amount, p.date_value from price p"
                     + " join ingredient i on p.id_ingredient = i.id"
                     + " where p.id_ingredient = ?")) {
            statement.setLong(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Price price = priceMapper.apply(resultSet);
                    prices.add(price);
                }
                return prices;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Price> filterByCriteria(List<Criteria> criterias, int page, int size, Map<String, String> sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterByCriteria'");
    }

}

// todo: mregler saveAll an'ilay price - date
// todo: manao ny mapper sy ny saveAll an'ny stock 
// todo: rehefa manao addPrice || addStock => saveAll

