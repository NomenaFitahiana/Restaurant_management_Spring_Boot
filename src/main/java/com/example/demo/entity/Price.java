package com.example.demo.entity;


import lombok.*;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
public class Price {
    private Long id;
    private Ingredient ingredient;
    private Double amount;
    private LocalDate dateValue;

    public Price(Double amount) {
        this.amount = amount;
        this.dateValue = now();
    }

    public Price(Double amount, LocalDate dateValue){
        this.amount = amount;
        this.dateValue = dateValue;
    }

    public Price(Long id, Double amount, LocalDate dateValue){
        this.amount = amount;
        this.dateValue = dateValue;
        this.id = id;
    }
}
