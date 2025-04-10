package com.example.demo.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Price;
import com.example.demo.entity.StockMovement;
import com.example.demo.entity.StockMovementType;
import com.example.demo.entity.Unit;
import com.example.demo.repository.mapper.PriceMapper;
import com.example.demo.repository.mapper.StockMovementMapper;
import com.jayway.jsonpath.Criteria;

@Repository
public class StockMovementRepository implements RepositoryInterface<StockMovement> {
    private final DataSource dataSource;
     private final StockMovementMapper stockMovementMapper;

     public StockMovementRepository(){
        this.dataSource = new DataSource();
        this.stockMovementMapper = new StockMovementMapper();
     }


    @Override
    public List<StockMovement> getAll(int page, int size) {
        List<StockMovement> stocks = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select p.id, p.quantity, p.unit, p.movement_type, p.creation_datetime, p.id_ingredient from " +
                    "stock_movement limit ? offset ?")) {
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, size*(page-1));

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                stocks.add(stockMovementMapper.apply(resultSet));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  stocks;
    }



    @Override
    public StockMovement findById(Long id) {
        StockMovement stock = new StockMovement();

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select p.id, p.quantity, p.unit, p.movement_type, p.creation_datetime, p.id_ingredient from stock_movement where id = ?")) {
          
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                stock = stockMovementMapper.apply(resultSet);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  stock;
    }

   /* @Override
    public List<StockMovement> filterByCriteria(List<Criteria> criterias, int page, int size, Map<String, String> sort) {
        return List.of();
    }

    public List<StockMovement> filterByIngredientIdByCriteria(Long ingredientId, List<Criteria> criterias, Map<String, String> sort) {
        List<StockMovement> movements = new ArrayList<>();
        StringBuilder query = new StringBuilder("select distinct sm.id, sm.movement_type, sm.quantity, sm.creation_datetime, sm.unit from stock_movement sm ");
        List<String> criteriaKey = criterias.stream()
                .map(Criteria::getKey)
                .toList();
        List<String> targetKeys = List.of("movement_type", "quantity", "date", "unit");
        if (criteriaKey.stream().anyMatch(targetKeys::contains)) {
            query.append(" where ");
            for (Criteria criteria : criterias) {
                StringBuilder conditions = new StringBuilder();
                if (criteria.getValue() instanceof Date || criteria.getValue() instanceof LocalDate) {
                    conditions.append("sm.creation_datetime").append(" ").append(criteria.getOperation()).append(" '").append(criteria.getValue()).append("'");
                    query.append(conditions).append(" ").append(criteria.getConjunction().toLowerCase()).append(" ");
                } else if(criteria.getValue() instanceof String) {
                    if("movement_type".equals(criteria.getKey()) || "unit".equals(criteria.getKey())){
                        conditions.append("sm.").append(criteria.getKey()).append("::TEXT ").append(criteria.getOperation()).append(" '%").append(criteria.getValue()).append("%' ").append(" ");
                        query.append(conditions).append(" ").append(criteria.getConjunction().toLowerCase()).append(" ");
                    }
                } else if ("quantity".equals(criteria.getKey())) {
                    conditions.append("sm.").append(criteria.getKey()).append(" ").append(criteria.getOperation()).append(" ").append(criteria.getValue());
                    query.append(conditions).append(" ").append(criteria.getConjunction().toLowerCase()).append(" ");
                }

            }
            query.append(" sm.id_ingredient=").append(ingredientId);
        }else{
            query.append(" where sm.id_ingredient=").append(ingredientId);
        }

        if (!sort.isEmpty()) {
            query.append(" order by ");
            List<String> orderClauses = sort.entrySet().stream()
                    .map(entry -> "sm." + entry.getKey() + " " + entry.getValue())
                    .toList();
            query.append(String.join(", ", orderClauses));
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                movements.add(mapFromResultSet(resultSet));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return movements;
    }

    @Override
    public List<StockMovement> saveAll(List<StockMovement> entities) {
        List<StockMovement> stockMovements = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            for (StockMovement entityToSave : entities) {
                String sql;
                if (entityToSave.getId() == null) {
                    sql = "INSERT INTO stock_movement (quantity, unit, movement_type, creation_datetime, id_ingredient) " +
                            "VALUES (?, ?::unit, ?::stock_movement_type, ?, ?) " +
                            "RETURNING id, quantity, unit, movement_type, creation_datetime, id_ingredient;";
                } else {
                    sql = "INSERT INTO stock_movement (id, quantity, unit, movement_type, creation_datetime, id_ingredient) " +
                            "VALUES (?, ?, ?::unit, ?::stock_movement_type, ?, ?) " +
                            "ON CONFLICT (id) DO NOTHING " +
                            "RETURNING id, quantity, unit, movement_type, creation_datetime, id_ingredient;";
                    }
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    int parameterIndex = 1;
                    if (entityToSave.getId() != null) {
                        statement.setLong(parameterIndex++, entityToSave.getId());
                    }
                    statement.setDouble(parameterIndex++, entityToSave.getQuantity());
                    statement.setString(parameterIndex++, entityToSave.getUnit().name());
                    statement.setString(parameterIndex++, entityToSave.getMovementType().name());
                    statement.setTimestamp(parameterIndex++, Timestamp.from(entityToSave.getCreationDatetime()));
                    statement.setLong(parameterIndex, entityToSave.getIngredient().getId());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            StockMovement savedStockMovement = mapFromResultSet(resultSet);
                            stockMovements.add(savedStockMovement);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stockMovements;
    }
*/
    public List<StockMovement> findByIdIngredient(Long idIngredient) {
        List<StockMovement> stockMovements = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "select s.id, s.quantity, s.unit, s.movement_type, s.creation_datetime from stock_movement s"
                             + " join ingredient i on s.id_ingredient = i.id"
                             + " where s.id_ingredient = ?")) {
            statement.setLong(1, idIngredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    stockMovements.add(mapFromResultSet(resultSet));
                }
                return stockMovements;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private StockMovement mapFromResultSet(ResultSet resultSet) throws SQLException {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(resultSet.getLong("id"));
        stockMovement.setQuantity(resultSet.getDouble("quantity"));
        stockMovement.setMovementType(StockMovementType.valueOf(resultSet.getString("movement_type")));
        stockMovement.setUnit(Unit.valueOf(resultSet.getString("unit")));
        stockMovement.setCreationDatetime(resultSet.getTimestamp("creation_datetime").toInstant());
        return stockMovement;
    }

    @Override
    public List<StockMovement> filterByCriteria(List<Criteria> criterias, int page, int size,
            Map<String, String> sort) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterByCriteria'");
    }

    @Override
    public List<StockMovement> saveAll(List<StockMovement> entities) {
        List<StockMovement> stock = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into stock_movement (id, quantity, unit, movement_type, creation_datetime, id_ingredient) " + "values (?, ?, ?, ?, ?, ?)" + "on conflict (id) do nothing " + "returning id, quantity, unit, movement_type, creation_datetime, id_ingredient ");){
            
                entities.forEach(s -> {
                  try {
                    preparedStatement.setLong(1, s.getId());
                    preparedStatement.setDouble(2, s.getQuantity());
                    preparedStatement.setObject(3, s.getUnit(), Types.OTHER);
                    preparedStatement.setObject(4, s.getMovementType(), Types.OTHER);
                    preparedStatement.setTimestamp(5, Timestamp.from(s.getCreationDatetime()));
                    preparedStatement.setLong(6, s.getIngredient().getId());
                    preparedStatement.addBatch();
                  } catch (SQLException e) {
                    throw new RuntimeException(e);
                  }
                    
                });

                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()) {
                        stock.add(stockMovementMapper.apply(resultSet));
                    }
                }

                return stock;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      
    }
}
