/*package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Dish;
import com.example.demo.service.RestaurantService;

@RestController

public class DishController{
    @Autowired
    private RestaurantService r ;

    @GetMapping("/dishes")
    public List<Dish> connect(){
     return  r.getAll();
    }


}*/
