package com.example.demo.controller.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.controller.rest.DishIngredientRest;
import com.example.demo.controller.rest.DishRest;
import com.example.demo.controller.rest.IngredientBasicProperty;
import com.example.demo.entity.Dish;
import com.example.demo.entity.DishIngredient;
import com.example.demo.entity.Ingredient;

public class DishRestMapper implements Function<Dish, DishRest> {
    
    private final Function<DishIngredient, DishIngredientRest> dishIngredientMapper;
    
   @Override
    public DishRest apply(Dish dish) {
        Integer availableQuantity = (int) Math.round(dish.getAvailableQuantity());
        
        List<DishIngredientRest> ingredientsRest = dish.getDishIngredients().stream()
            .map(dishIngredientMapper)
            .collect(Collectors.toList());
            
        return new DishRest(
            dish.getId(),
            dish.getName(),
            dish.getPrice(),
            availableQuantity,
            ingredientsRest
        );
    }
}
    
    
    

