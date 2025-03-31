package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.IngredientController;
import com.example.demo.repository.*;
import com.example.demo.service.IngredientService;

@Configuration
public class ConnectionConfig {

    @Bean
    DataSource dataSource(){
        return new DataSource();
    }

    @Bean
    IngredientRepository IngredientRepository(){
        return new IngredientRepository();
    }

    @Bean
    IngredientService ingredientService (IngredientRepository ingredientRepository){
        return new IngredientService(ingredientRepository);
    }

   @Bean
   IngredientController ingredientController(IngredientService ingredientService){
    return new IngredientController(ingredientService);
   }
}
