package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStatistics {
    private final String dishName;
        private double quantity;
        private double amount;

        public OrderStatistics(String dishName) {
            this.dishName = dishName;
        }

        public void addQuantity(double quantity) {
            this.quantity += quantity;
        }

        public void addAmount(double amount) {
            this.amount += amount;
        }
   
}
