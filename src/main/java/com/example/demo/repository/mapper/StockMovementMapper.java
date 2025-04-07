package com.example.demo.repository.mapper;

import java.sql.ResultSet;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.entity.StockMovement;
import com.example.demo.entity.StockMovementType;
import com.example.demo.entity.Unit;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class StockMovementMapper implements Function<ResultSet, StockMovement> {
    
    @SneakyThrows
    @Override
    public StockMovement apply(ResultSet resultSet){
        StockMovement stock = new StockMovement();
        stock.setId(resultSet.getLong("id"));
        stock.setQuantity(resultSet.getDouble("quantity"));
        stock.setMovementType(StockMovementType.valueOf(resultSet.getString("movement_type")));
        stock.setUnit(Unit.valueOf(resultSet.getString("unit")));
        stock.setCreationDatetime(resultSet.getTimestamp("creation_datetime").toInstant());

        return stock;
    }
}


