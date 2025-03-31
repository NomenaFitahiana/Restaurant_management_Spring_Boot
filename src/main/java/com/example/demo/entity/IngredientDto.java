package com.example.demo.entity;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class IngredientDto {
    private Long id;
    private String name;
    private double unitPrice;
    private LocalDateTime updatedAt;

}
