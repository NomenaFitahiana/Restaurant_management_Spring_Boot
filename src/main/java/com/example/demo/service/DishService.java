package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.mapper.DishRestMapper;
import com.example.demo.controller.rest.DishRest;
import com.example.demo.entity.Dish;
import com.example.demo.repository.DishRepository;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final DishRestMapper dishRestMapper ;

    public DishService(DishRepository dishRepository){
        this.dishRepository = dishRepository;
        this.dishRestMapper = new DishRestMapper();
    }

    public List<DishRest> getAll(int page, int size){
        List<Dish> dishes = dishRepository.getAll(page, size);
        
        System.out.println("service: " + dishes);
        List<DishRest> dishRests = dishes.stream().map(dish -> {
          DishRest dishRest =  dishRestMapper.apply(dish);
            return dishRest;
        }).toList();

        System.out.println("service2: " + this.dishRestMapper);

        return dishRests;
    }
}
