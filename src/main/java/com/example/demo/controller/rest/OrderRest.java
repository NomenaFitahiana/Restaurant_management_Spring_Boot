package com.example.demo.controller.rest;

import java.util.List;
import com.example.demo.entity.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRest {
    private String references;
    private List<DishOrderRest> dishOrders;
    private List<OrderStatus> orderStatus;
}
