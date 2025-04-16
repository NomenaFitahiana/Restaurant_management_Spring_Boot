package com.example.demo.controller.rest;

import java.util.List;
import com.example.demo.entity.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DishOrderRest  {
    private String name;
    private double actualPrice;
    private double quantity;
    private List<OrderStatus> dishOrderStatus;
}
