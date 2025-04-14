package com.example.demo.controller.rest;


import com.example.demo.entity.Price;
import com.example.demo.entity.StockMovement;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class DishIngredientRest {
    private IngredientBasicProperty ingredient;
    private Double requiredQuantity;
    private StockMovement actualStock;
    private Double actualPrice;
}


