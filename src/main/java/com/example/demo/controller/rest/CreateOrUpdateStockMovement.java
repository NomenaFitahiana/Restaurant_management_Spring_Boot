package com.example.demo.controller.rest;

import java.time.Instant;

import com.example.demo.entity.StockMovementType;
import com.example.demo.entity.Unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CreateOrUpdateStockMovement {
    private Long id;
    private Double quantity;
    private Unit unit;
    private StockMovementType movementType;
    private Instant creationDatetime;
}