package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.IngredientDto;
import com.example.demo.service.IngredientService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

   private IngredientService ingredientService;

   public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
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


        if (priceMaxFilter != null && priceMaxFilter < 0 || priceMinFilter != null && priceMinFilter < 0 || priceMaxFilter != null && priceMinFilter != null && priceMaxFilter < priceMinFilter) {
            String responseBody = "Filters can't be a negative value  and priceMaxFilter: " + priceMaxFilter + " can't be smaller than priceMinFilter: " + priceMinFilter;
            return new ResponseEntity<>( responseBody, HttpStatus.BAD_REQUEST);
        } 


        List<Ingredient> response = new ArrayList<>();
        List<Ingredient> iList = ingredientService.getAll();


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

    @GetMapping("ingredients/{id}")
    public ResponseEntity<Object> getIngredientById(@PathVariable Long id) {
        Ingredient response =ingredientService.findById(id);

        if (response == null) {
            String responseBody = "Ingredient " + id + " is not found";
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @PostMapping("/ingredients")
    public ResponseEntity<Object> createIngredient(@RequestBody List<Ingredient> entity) {
        List<Ingredient> ingredients = ingredientService.saveAll(entity);

        return new ResponseEntity<>(ingredients, HttpStatus.CREATED);
    }
    
    @PutMapping("ingredients/{id}")
    public ResponseEntity<Object> putIngredient(@RequestBody List<Ingredient> entity) {
        List<Ingredient> ingredients = ingredientService.saveAll(entity);

        return new ResponseEntity<>(ingredients, HttpStatus.CREATED);
    }

   
    
    
}

