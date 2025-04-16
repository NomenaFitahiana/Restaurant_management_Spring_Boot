package com.example.demo.controller.mapper;

import java.util.function.Function;

import com.example.demo.controller.rest.DishOrderRest;
import com.example.demo.entity.DishOrder;

public class DishOrderMapper implements Function<DishOrder, DishOrderRest>{

    @Override
    public DishOrderRest apply(DishOrder arg0) {
       DishOrderRest dishOrderRest = new DishOrderRest();
       dishOrderRest.setName(arg0.getDish().getName());
       dishOrderRest.setQuantity(arg0.getQuantity());
       dishOrderRest.setActualPrice(arg0.getDish().getPrice());
       dishOrderRest.setDishOrderStatus(arg0.getOrderStatus());

       return dishOrderRest;
    }
    
}
