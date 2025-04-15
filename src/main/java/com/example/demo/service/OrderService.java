package com.example.demo.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.controller.rest.OrderRest;
import com.example.demo.entity.DishOrder;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatistics;
import com.example.demo.repository.DishOrderRepository;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
       private final OrderRepository orderRepository;
    private final DishOrderRepository dishOrderRepository;

    public OrderService(OrderRepository orderRepository, DishOrderRepository dishOrderRepository) {
        this.orderRepository = orderRepository;
        this.dishOrderRepository = dishOrderRepository;
    }

    public Order getOrderByRef(String ref){
        return orderRepository.findOrderByReference(ref);
    }

    public List<OrderRest> findBestSellingDishes(int limit, LocalDate startDate, LocalDate endDate) {
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);

        List<Order> finishedOrders = orderRepository.findFinishedOrdersBetweenDates(sqlStartDate, sqlEndDate);

        Map<String, OrderStatistics> dishStats = new HashMap<>();

        for (Order order : finishedOrders) {
            List<DishOrder> dishOrders = dishOrderRepository.getAllDishInsideAnOrder(order.getId());
            
            for (DishOrder dishOrder : dishOrders) {
                String dishName = dishOrder.getDish().getName();
                double quantity = dishOrder.getQuantity();
                double price = dishOrder.getDish().getPrice();
                
                OrderStatistics stats = dishStats.getOrDefault(dishName, new OrderStatistics(dishName));
                stats.addQuantity(quantity);
                stats.addAmount(quantity * price);
                dishStats.put(dishName, stats);
            }
        }

        return dishStats.values().stream()
                .sorted((a, b) -> Double.compare(b.getQuantity(), a.getQuantity()))
                .limit(limit)
                .map(stats -> new OrderRest(stats.getDishName(), (int) stats.getQuantity(), stats.getAmount()))
                .collect(Collectors.toList());
    }
}
