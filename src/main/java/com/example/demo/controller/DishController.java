package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DishService;
import com.example.demo.controller.rest.DishRest;

@RestController
public class DishController{
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public ResponseEntity<Object> getDishes(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size) {
                List<DishRest> dishes = dishService.getAll(page, size);

                System.out.println("controller" + dishes);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    /*@GetMapping("/{id}/processingTime")
    public ResponseEntity<Object> getProcessingTime(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(required = false, defaultValue = "SECONDS") TimeUnit unit,
            @RequestParam(required = false, defaultValue = "AVERAGE") CalculationType calculationType) {
        
                ProcessingTimeResponse response = dishService.calculateProcessingTime(id, startDate, endDate, unit, calculationType)
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/

}
