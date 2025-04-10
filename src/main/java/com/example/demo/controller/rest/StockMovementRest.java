package com.example.demo.controller.rest;

import java.time.Instant;

import com.example.demo.entity.StockMovementType;
import com.example.demo.entity.Unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StockMovementRest {
    private Long id;
    private Double quantity;
    private Unit unit;
    private StockMovementType type;
    private Instant creationDatetime;
}
