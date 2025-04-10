package com.example.demo.controller.rest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IngredientRest {
    private Long id;
    private String name;
    private List<PriceRest> prices;
    private List<StockMovementRest> stockMovements;
}
