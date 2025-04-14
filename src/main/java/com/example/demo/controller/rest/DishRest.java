package com.example.demo.controller.rest;

import java.util.List;


import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class DishRest {
    private Long id;
    private String name;
    private Double availableQuantity; 
    private List<DishIngredientRest> ingredients;

    public DishRest(Long id, String name, Double availableQuantity, List<DishIngredientRest> ingredients) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
        this.ingredients = ingredients;
    }

    
}




   

