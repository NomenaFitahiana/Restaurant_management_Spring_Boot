package com.example.demo.controller.mapper;

import java.util.List;
import java.util.function.Function;

import com.example.demo.controller.rest.DishIngredientRest;
import com.example.demo.controller.rest.DishRest;
import com.example.demo.entity.Dish;


public class DishRestMapper implements Function<Dish, DishRest> {
    
    private final DishIngredientMapper dishIngredientMapper;

    public DishRestMapper(){
        this.dishIngredientMapper = new DishIngredientMapper();
    }
    
   @Override
    public DishRest apply(Dish dish) {

        List<DishIngredientRest> ingredients =  dish.getDishIngredients().stream().map(ingredient -> {
            DishIngredientRest ingredientRest = dishIngredientMapper.apply(ingredient);
             return ingredientRest;
         }).toList();

       
       DishRest dishRest = new DishRest();
       dishRest.setId(dish.getId());
       dishRest.setName(dish.getName());
       dishRest.setAvailableQuantity(dish.getAvailableQuantity());
       dishRest.setIngredients(ingredients);

       return dishRest;
    }
}
    
    
    
