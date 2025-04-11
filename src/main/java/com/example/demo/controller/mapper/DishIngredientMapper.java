package com.example.demo.controller.mapper;

import java.util.function.Function;

import com.example.demo.controller.rest.DishIngredientRest;
import com.example.demo.entity.DishIngredient;

public class DishIngredientMapper implements Function<DishIngredient, DishIngredientRest>{

    @Override
    public DishIngredientRest apply(DishIngredient arg0) {
      return  new DishIngredientRest()
    }
    
}

private IngredientBasicProperty ingredient;
    private Double requiredQuantity;
    private Unit unit;