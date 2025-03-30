package com.example.demo.entity;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class DishIngredient {
    private Long id;
    private Ingredient ingredient;
    private Double requiredQuantity;
    private Unit unit;
}