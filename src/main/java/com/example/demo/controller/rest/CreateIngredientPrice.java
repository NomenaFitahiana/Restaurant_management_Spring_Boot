package com.example.demo.controller.rest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CreateIngredientPrice {
    private Long id;
    private Double amount;
    private LocalDate dateValue;
}
