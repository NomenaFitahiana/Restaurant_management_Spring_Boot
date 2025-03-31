package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.IngredientDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class IngredientController {
    private IngredientDto i1;
    private IngredientDto i2;

        {   i1 = new IngredientDto();
            i1.setId(1L);
            i1.setName("Oeuf");
            i1.setUnitPrice(1000.0);
            i1.setUpdatedAt(LocalDateTime.parse("2025-03-01T00:00:00"));
        
            i2 = new IngredientDto();
            i2.setId(2L);
            i2.setName("Huile");
            i2.setUnitPrice(10000.0);
            i2.setUpdatedAt(LocalDateTime.parse("2025-03-20T00:00:00"));
        }
        
    
    

    public List<IngredientDto> getAllIngredientsWithMaxPrice(double priceMaxFilter, List<IngredientDto> list){

       List<IngredientDto> ingredients =  list.stream().filter(i -> i.getUnitPrice() <= priceMaxFilter).toList();

       return ingredients;
    }

    public List<IngredientDto> getAllIngredientsWithMinPrice(double priceMinFilter, List <IngredientDto> list){
        List<IngredientDto> ingredients = list.stream().filter(i -> i.getUnitPrice() >= priceMinFilter).toList();
        return ingredients;
    }

    public List<IngredientDto> getAllIngredientsWithMinAndMaxPrice(double priceMinFilter,double priceMaxFilter, List <IngredientDto> list){
        List<IngredientDto> ingredients =  list.stream().filter(i -> i.getUnitPrice() >= priceMinFilter && i.getUnitPrice() <= priceMaxFilter).toList();
        return ingredients;
    }

    List<IngredientDto> iList = List.of(i1, i2);

    @GetMapping("/ingredients")
    public ResponseEntity<Object> getAll(@RequestParam(name = "priceMinFilter", required = false) Double priceMinFilter, @RequestParam(name = "priceMaxFilter", required = false) Double priceMaxFilter) {      


        if (priceMaxFilter != null && priceMaxFilter < 0 || priceMinFilter != null && priceMinFilter < 0 || priceMaxFilter != null && priceMinFilter != null && priceMaxFilter < priceMinFilter) {
            String responseBody = "Filters can't be a negative value  and priceMaxFilter: " + priceMaxFilter + " can't be smaller than priceMinFilter: " + priceMinFilter;
            return new ResponseEntity<>( responseBody, HttpStatus.BAD_REQUEST);
        } 


        List<IngredientDto> response = new ArrayList<>();

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
      
        

        System.out.println("priceMinFilter: " + priceMinFilter);
        System.out.println("priceMaxFilter: " + priceMaxFilter);
        return ResponseEntity.ok(response);
        

    }

    @PostMapping("/ingredients")
    public ResponseEntity<Object> createIngredient(@RequestBody List<IngredientDto> entity) {
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
    
    @PutMapping("ingredients/{id}")
    public ResponseEntity<Object> putIngredient(@PathVariable Long id, @RequestBody IngredientDto entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }
    
}

