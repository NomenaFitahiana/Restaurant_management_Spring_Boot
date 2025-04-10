package com.example.demo.entity;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class DishOrderStatus {
    private Long id;
    private Dish dish;
    private List<Status> dishStatus;
}
