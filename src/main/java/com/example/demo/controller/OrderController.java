package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.rest.OrderRest;
import com.example.demo.service.OrderService;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Object> getBestSellingDishes(
            @RequestParam int limit,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<OrderRest> bestSellingList = orderService.findBestSellingDishes(limit, startDate, endDate);

        return new ResponseEntity<>(bestSellingList, HttpStatus.OK) ;
    }
}

