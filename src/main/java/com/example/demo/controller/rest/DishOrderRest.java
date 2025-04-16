package com.example.demo.controller.rest;

import java.util.List;
import com.example.demo.entity.DishOrderStatus;
import lombok.*;

@Getter
@Setter
public class DishOrderRest  {
    private String name;
    private double actualPrice;
    private double quantity;
    private List<DishOrderStatus> dishOrderStatus;
}
