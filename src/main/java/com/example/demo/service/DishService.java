package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Dish;
import com.example.demo.repository.DishRepository;

public class DishService {
    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAll(int page, int size){
        List<Dish> dishes = dishRepository.getAll(page, size);

        return dishes;
    }
}
