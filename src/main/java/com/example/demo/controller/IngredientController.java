package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Ingredient;
import com.example.demo.service.IngredientService;
import com.example.demo.service.Exceptions.*;
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

   private IngredientService ingredientService;

   public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
   }
  
    @GetMapping("/ingredients")
    public ResponseEntity<Object> getAll(@RequestParam(name = "priceMinFilter", required = false) Double priceMinFilter, @RequestParam(name = "priceMaxFilter", required = false) Double priceMaxFilter) {     

        try {
            List<Ingredient> iList = ingredientService.getAll(priceMinFilter, priceMaxFilter);
            return new ResponseEntity<>(iList, HttpStatus.OK);
        } catch (ClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (ServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }      

    }

    @GetMapping("ingredients/{id}")
    public ResponseEntity<Object> getIngredientById(@PathVariable Long id) {
        try {
            Ingredient response =ingredientService.findById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (ClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (ServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }           
    }
    

    @PostMapping("/ingredients")
    public ResponseEntity<Object> createIngredient(@RequestBody List<Ingredient> entity) {
        try {
            List<Ingredient> ingredients = ingredientService.saveAll(entity);
            return new ResponseEntity<>(ingredients, HttpStatus.CREATED);
        } catch (ServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PutMapping("ingredients/{id}")
    public ResponseEntity<Object> putIngredient(@RequestBody List<Ingredient> entity) {
        try {
            List<Ingredient> ingredients = ingredientService.saveAll(entity);

            return new ResponseEntity<>(ingredients, HttpStatus.ACCEPTED);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (ClientException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (ServerException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }      
       
    }

   
    
    
}

