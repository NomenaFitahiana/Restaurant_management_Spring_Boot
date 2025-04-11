package com.example.demo.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.controller.mapper.DishIngredientMapper;
import com.example.demo.entity.Dish;

import lombok.*;

@Getter
@Setter
public class DishRest {
    private Long id;
    private String name;
    private Double price;
    private Integer availableQuantity; 
    private List<DishIngredientRest> ingredients;

    public DishRest(Long id, String name, Double price, Integer availableQuantity, List<DishIngredientRest> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
        this.ingredients = ingredients;
    }

    
}



    
   

