package com.example.demo.controller.rest;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriceRest {
    private Long id;
    private Double price;
    private LocalDate dateValue;
}
