package com.example.demo.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore
    Order order;

    @Override
    public String toString() {
        return "OrderStatus{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

}
