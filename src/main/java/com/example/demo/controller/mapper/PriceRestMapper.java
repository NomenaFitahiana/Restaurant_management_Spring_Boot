package com.example.demo.controller.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.controller.rest.PriceRest;
import com.example.demo.entity.Price;

@Component
public class PriceRestMapper implements Function<Price, PriceRest> {

    @Override
    public PriceRest apply(Price price) {
        return new PriceRest(price.getId(), price.getAmount(), price.getDateValue());
    }
}
