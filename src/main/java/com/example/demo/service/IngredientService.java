package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;

@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService (IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

   public List<Ingredient> getAll(){
    return ingredientRepository.getAll(1, 5);
   }

   public Ingredient findById(Long id){
    Ingredient ingredient = ingredientRepository.findById(id);

    if (ingredient == null) {
        return null;
    }

    System.out.println(ingredient);
    return ingredient;
   }
}
