package com.example.demo.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateOrUpdateIngredient {
    private Long id;
    private String name;
}