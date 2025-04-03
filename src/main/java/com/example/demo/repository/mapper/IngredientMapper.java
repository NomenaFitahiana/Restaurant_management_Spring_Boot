package com.example.demo.repository.mapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Price;
import com.example.demo.entity.StockMovement;
import com.example.demo.repository.PriceRepository;
import com.example.demo.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class IngredientMapper implements Function<ResultSet, Ingredient>{
    private final PriceRepository priceRepository;
    private final StockMovementRepository stockMovementRepository;

    @SneakyThrows
    @Override
    public Ingredient apply(ResultSet resultSet){
        Ingredient ingredient = new Ingredient();
        Long id = resultSet.getLong("id");
        List<Price> prices = priceRepository.findByIdIngredient(id);
        List<StockMovement> stocks = stockMovementRepository.findByIdIngredient(id);

        ingredient.setId(id);
        ingredient.setName(resultSet.getString("name"));
        ingredient.setPrices(prices);
        ingredient.setStockMovements(stocks);

        return ingredient;
    }
}
