package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ingredient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class IngredientController {
    private Ingredient i1;
    private Ingredient i2;

{   i1 = new Ingredient();
    i1.setId(1L);
    i1.setName("Oeuf");
    i1.setUnitPrice(1000.0);
    i1.setUpdatedAt(LocalDateTime.parse("2025-03-01T00:00:00"));

    i2 = new Ingredient();
    i2.setId(2L);
    i2.setName("Huile");
    i2.setUnitPrice(10000.0);
    i2.setUpdatedAt(LocalDateTime.parse("2025-03-20T00:00:00"));
}

    public List<Ingredient> getAllIngredientsWithMaxPrice(double priceMaxFilter, List<Ingredient> list){

       List<Ingredient> ingredients =  list.stream().filter(i -> i.getUnitPrice() <= priceMaxFilter).toList();

       return ingredients;
    }

    public List<Ingredient> getAllIngredientsWithMinPrice(double priceMinFilter, List <Ingredient> list){
        List<Ingredient> ingredients = list.stream().filter(i -> i.getUnitPrice() >= priceMinFilter).toList();
        return ingredients;
    }

    public List<Ingredient> getAllIngredientsWithMinAndMaxPrice(double priceMinFilter,double priceMaxFilter, List <Ingredient> list){
        List<Ingredient> ingredients =  list.stream().filter(i -> i.getUnitPrice() >= priceMinFilter && i.getUnitPrice() <= priceMaxFilter).toList();
        return ingredients;
    }

    @GetMapping("/ingredients")
    public ResponseEntity<Object> getAll(@RequestParam(name = "priceMinFilter", required = false) Double priceMinFilter, @RequestParam(name = "priceMaxFilter", required = false) Double priceMaxFilter) {
        List<Ingredient> iList = List.of(i1, i2);

        if (priceMaxFilter != null && priceMaxFilter < 0 || priceMinFilter != null && priceMinFilter < 0 || priceMaxFilter != null && priceMinFilter != null && priceMaxFilter < priceMinFilter) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Ingredient> response = new ArrayList<>();

        if (priceMaxFilter == null && priceMinFilter != null) {
            response = getAllIngredientsWithMinPrice(priceMinFilter, iList);
        }
        else if (priceMaxFilter != null && priceMinFilter == null) {
            response = getAllIngredientsWithMaxPrice(priceMaxFilter, iList);
        }
        else if  (priceMaxFilter != null && priceMinFilter != null) {
            response = getAllIngredientsWithMinAndMaxPrice(priceMinFilter,priceMaxFilter, iList);
        }
        else if (priceMaxFilter == null && priceMinFilter == null)  response = iList;
      
        

        return ResponseEntity.ok(response);

    }
    
    
}

// todo: mettre les données statiques dans le package static
// todo: afficher des messages avec les réponses donnés