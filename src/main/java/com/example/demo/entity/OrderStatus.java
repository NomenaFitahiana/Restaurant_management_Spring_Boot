package com.example.demo.entity;

import java.time.Instant;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class OrderStatus {
    Long id;
    Instant statusDate;
    Status status;
    Order order;

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

}
