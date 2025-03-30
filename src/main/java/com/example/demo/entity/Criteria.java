package com.example.demo.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Criteria {
    private String key;
    private Object value;
    private String operation;
    private String conjunction;
    
}