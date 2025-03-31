package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.IngredientRepository;

@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService (IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    public String test(){
        return ingredientRepository.Test();
    }
}
