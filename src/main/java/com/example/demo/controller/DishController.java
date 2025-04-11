package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Dish;

@RestController

public class DishController{
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false, defaultValue = "SECONDS") TimeUnit unit,
            @RequestParam(required = false, defaultValue = "AVERAGE") CalculationType calculationType) {
        
                ProcessingTimeResponse response = dishService.calculateProcessingTime(id, startDate, endDate, unit, calculationType)
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
