package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.rest.OrderRest;
import com.example.demo.service.OrderService;
import com.example.demo.service.Exceptions.NotFoundException;
import com.example.demo.entity.Order;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{reference}")
    public ResponseEntity<Object> getOrderByRef(@PathVariable ("reference") String ref){
      try {
        Order order = orderService.getOrderByRef(ref);
        System.out.println("order" + ref);

        return new ResponseEntity<>(order, HttpStatus.OK);
      } catch (Exception e) {
        throw new NotFoundException(e);
      }
    }

    @GetMapping("/bestSales")
    public ResponseEntity<Object> getBestSellingDishes(
            @RequestParam (name = "limit") int limit,
            @RequestParam (name = "startDate") LocalDate startDate,
            @RequestParam (name = "endDate") LocalDate endDate) {

        List<OrderRest> bestSellingList = orderService.findBestSellingDishes(limit, startDate, endDate);

        return new ResponseEntity<>(bestSellingList, HttpStatus.OK) ;
    }
}

