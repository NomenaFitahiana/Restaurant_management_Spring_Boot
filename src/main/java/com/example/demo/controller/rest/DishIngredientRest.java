package com.example.demo.controller.rest;

import com.example.demo.entity.Unit;

import lombok.*;

@AllArgsConstructor
@Getter
public class DishIngredientRest {
    private Long id;
    private IngredientBasicProperty ingredient;
    private Double requiredQuantity;
    private Unit unit;
}


