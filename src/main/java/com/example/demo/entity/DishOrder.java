package com.example.demo.entity;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class DishOrder {
    private Long id;
    private Order order;
    private Dish dish;
    private double quantity;

    @Builder.Default
    private List<OrderStatus> orderStatus = new ArrayList<>();

    public DishOrder(Dish dish, double quantity, List<OrderStatus> orderStatus, Long id) {
        this.dish = dish;
        this.quantity = quantity;
        this.orderStatus = orderStatus;
        this.id = id;
    }

    @Override
    public String toString() {
        return "DishOrder{" +
                "id=" + id + 
                ", dish=" + (dish) +
                ", quantity=" + quantity +
                '}';
    }

}