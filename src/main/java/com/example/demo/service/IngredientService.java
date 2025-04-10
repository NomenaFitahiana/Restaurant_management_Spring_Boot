package com.example.demo.service;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Price;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.Exceptions.NotFoundException;

@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;

    
    public IngredientService (IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }   

    public List<Ingredient> getAllIngredientsWithMaxPrice(double priceMaxFilter, List<Ingredient> list){

        List<Ingredient> ingredients =  list.stream().filter(i -> i.getActualPrice() <= priceMaxFilter).toList();
 
        return ingredients;
     }
 
     public List<Ingredient> getAllIngredientsWithMinPrice(double priceMinFilter, List <Ingredient> list){
         List<Ingredient> ingredients = list.stream().filter(i -> i.getActualPrice() >= priceMinFilter).toList();
         return ingredients;
     }
 
     public List<Ingredient> getAllIngredientsWithMinAndMaxPrice(double priceMinFilter,double priceMaxFilter, List <Ingredient> list){
         List<Ingredient> ingredients =  list.stream().filter(i -> i.getActualPrice() >= priceMinFilter && i.getUnitPrice() <= priceMaxFilter).toList();
         return ingredients;
     }

   public List<Ingredient> getAll(Double priceMinFilter, Double priceMaxFilter){

        if (priceMaxFilter != null && priceMaxFilter < 0 || priceMinFilter != null && priceMinFilter < 0 || priceMaxFilter != null && priceMinFilter != null && priceMaxFilter < priceMinFilter) {
            return null;
        } 
                
        List<Ingredient> response = new ArrayList<>();
        List<Ingredient> ingredients = ingredientRepository.getAll(1, 5);


        if (priceMaxFilter == null && priceMinFilter != null) {
            
            response = getAllIngredientsWithMinPrice(priceMinFilter, ingredients);
        }
        else if (priceMaxFilter != null && priceMinFilter == null) {
            response = getAllIngredientsWithMaxPrice(priceMaxFilter, ingredients);
        }
        else if  (priceMaxFilter != null && priceMinFilter != null) {
            response = getAllIngredientsWithMinAndMaxPrice(priceMinFilter,priceMaxFilter, ingredients);
        }
        else if (priceMaxFilter == null && priceMinFilter == null)  response = ingredients;

    return response;
   }

   public Ingredient findById(Long id){
    Ingredient ingredient = ingredientRepository.findById(id);

    if (ingredient == null) {
        return null;
    }

    System.out.println(ingredient);
    return ingredient;
   }

   public List<Ingredient> saveAll(List<Ingredient> entity){
    return ingredientRepository.saveAll(entity);
   }

   public Ingredient addPrices(Long ingredientId, List<Price> pricesToAdd) {
        System.out.println("ServiceIngId: " + ingredientId);
        System.out.println("PriceToAdd: " + pricesToAdd);

        Ingredient ingredient = ingredientRepository.findById(ingredientId);

        System.out.println("ingredient : " + ingredient);


        if (ingredient == null) {
            throw new NotFoundException("Ingredient not found with ID: " + ingredientId);
        }

        ingredient.addPrices(pricesToAdd);
        System.out.println("ingredient : " + ingredient);

        List<Ingredient> ingredientsSaved = ingredientRepository.saveAll(List.of(ingredient));
        return ingredientsSaved.getFirst();
    }
}
