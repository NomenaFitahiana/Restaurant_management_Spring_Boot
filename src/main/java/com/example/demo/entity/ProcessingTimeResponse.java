package com.example.demo.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class ProcessingTimeResponse {
    private double value;
    private TimeUnit unit;
    private CalculationType calculationType;
    
}
