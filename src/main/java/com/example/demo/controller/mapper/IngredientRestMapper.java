package com.example.demo.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.rest.CreateOrUpdateIngredient;
import com.example.demo.controller.rest.IngredientRest;
import com.example.demo.controller.rest.PriceRest;
import com.example.demo.controller.rest.StockMovementRest;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.Exceptions.NotFoundException;

@Component
public class IngredientRestMapper {
    @Autowired private PriceRestMapper priceRestMapper;
    @Autowired private StockMovementRestMapper stockMovementRestMapper;
    @Autowired private IngredientRepository ingredientRepository;

    public IngredientRest toRest(Ingredient ingredient) {
        List<PriceRest> prices = ingredient.getPrices().stream()
                .map(price -> priceRestMapper.apply(price)).toList();
        List<StockMovementRest> stockMovementRests = ingredient.getStockMovements().stream()
                .map(stockMovement -> stockMovementRestMapper.apply(stockMovement))
                .toList();
        return new IngredientRest(ingredient.getId(), ingredient.getName(), prices, stockMovementRests);
    }

    public Ingredient toModel(CreateOrUpdateIngredient newIngredient) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(newIngredient.getId());
        ingredient.setName(newIngredient.getName());
        try {
            Ingredient existingIngredient = ingredientRepository.findById(newIngredient.getId());
            ingredient.addPrices(existingIngredient.getPrices());
            ingredient.addStockMovements(existingIngredient.getStockMovements());
        } catch (NotFoundException e) {
            ingredient.addPrices(new ArrayList<>());
            ingredient.addStockMovements(new ArrayList<>());
        }
        return ingredient;
    }
}
