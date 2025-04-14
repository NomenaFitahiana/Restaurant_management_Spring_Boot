package com.example.demo.controller.mapper;

import java.util.function.Function;

import com.example.demo.controller.rest.DishIngredientRest;
import com.example.demo.controller.rest.IngredientBasicProperty;
import com.example.demo.entity.DishIngredient;

public class DishIngredientMapper implements Function<DishIngredient, DishIngredientRest>{

    @Override
    public DishIngredientRest apply(DishIngredient arg0) {
      return  new DishIngredientRest(new IngredientBasicProperty(arg0.getIngredient().getId(), arg0.getIngredient().getName()), arg0.getRequiredQuantity(), arg0.getIngredient().getActualStock(), arg0.getIngredient().getActualPrice());
    }
    
}

