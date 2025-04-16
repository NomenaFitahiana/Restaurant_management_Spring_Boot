package com.example.demo.controller.mapper;
import com.example.demo.entity.Order;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;

import com.example.demo.controller.rest.DishOrderRest;
import com.example.demo.controller.rest.OrderRest;


public class OrderMapper  implements Function<Order, OrderRest>{
    private  DishOrderMapper dishOrderMapper;

    public OrderMapper (){
        this.dishOrderMapper = new DishOrderMapper();
    }
    @Override
    public OrderRest apply(Order arg0) {
       OrderRest orderRest = new OrderRest();
       orderRest.setReferences(arg0.getReferences());
       orderRest.setOrderStatus(arg0.getOrderStatus());
       List<DishOrderRest> newDishOrderRest = arg0.getDishesOrder().stream().map(d -> dishOrderMapper.apply(d)).toList();
       orderRest.setDishOrders(newDishOrderRest);

       return orderRest;
    }
    
}
