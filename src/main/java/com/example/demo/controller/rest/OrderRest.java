package com.example.demo.controller.rest;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRest {
    private String dishName;
    private int quantitySelled;
    private double amount;
}
