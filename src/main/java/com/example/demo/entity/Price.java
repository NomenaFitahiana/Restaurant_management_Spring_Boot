package com.example.demo.entity;


import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static java.time.LocalDate.now;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Price {
    private Long id;
    @JsonIgnore
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

    @Override
    public String toString() {
        return "Price(id=" + id + ", amount=" + amount + ", dateValue=" + dateValue + ")";
    }
}
