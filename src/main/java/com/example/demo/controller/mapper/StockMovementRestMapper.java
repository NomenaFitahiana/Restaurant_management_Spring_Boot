package com.example.demo.controller.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.controller.rest.StockMovementRest;
import com.example.demo.entity.StockMovement;

@Component
public class StockMovementRestMapper implements Function<StockMovement, StockMovementRest> {

    @Override
    public StockMovementRest apply(StockMovement stockMovement) {
        return new StockMovementRest(stockMovement.getId(),
                stockMovement.getQuantity(),
                stockMovement.getUnit(),
                stockMovement.getMovementType(),
                stockMovement.getCreationDatetime());
    }
}