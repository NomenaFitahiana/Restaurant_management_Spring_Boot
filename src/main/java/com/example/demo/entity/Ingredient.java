package com.example.demo.entity;


import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Ingredient {
    private Long id;
    private String name;
    private Double unitPrice;
    private LocalDateTime updatedAt;
    private List<Price> prices;
    private List<StockMovement> stockMovements;

   public List<StockMovement> addStockMovements(List<StockMovement> stockMovements) {

    if (this.stockMovements == null) {
        this.stockMovements = new ArrayList<>();
    }

        stockMovements.forEach(stockMovement -> stockMovement.setIngredient(this));
        if (getStockMovements() == null || getStockMovements().isEmpty()){
            return stockMovements;
        }
        getStockMovements().addAll(stockMovements);
        return getStockMovements();
    }

    public List<Price> getPrices() {
        if (prices == null) {
            prices = new ArrayList<>();
        }
        return prices;
    }

    public List<Price> addPrices(List<Price> prices) {

        if (getPrices() == null || getPrices().isEmpty()){
            return prices;
        }
        prices.forEach(price -> price.setIngredient(this));
        getPrices().addAll(prices);

        return getPrices();
    }


    public Double getActualPrice() {
        return findActualPrice().orElse(new Price(0.0)).getAmount();
    }

    public StockMovement getActualStock(){
        return findActualStock().orElse(new StockMovement());
    }

    public Double getAvailableQuantity() {
        return getAvailableQuantityAt(Instant.now());
    }

    public Double getPriceAt(LocalDate dateValue) {
        return findPriceAt(dateValue).orElse(new Price(0.0)).getAmount();
    }

    public Double getAvailableQuantityAt(Instant datetime) {
        List<StockMovement> stockMovementsBeforeToday = stockMovements.stream()
                .filter(stockMovement ->
                        stockMovement.getCreationDatetime().isBefore(datetime)
                                || stockMovement.getCreationDatetime().equals(datetime))
                .toList();
        double quantity = 0;
        for (StockMovement stockMovement : stockMovementsBeforeToday) {
            if (StockMovementType.IN.equals(stockMovement.getMovementType())) {
                quantity += stockMovement.getQuantity();
            } else if (StockMovementType.OUT.equals(stockMovement.getMovementType())) {
                quantity -= stockMovement.getQuantity();
            }
        }
        return quantity;
    }

    private Optional<Price> findPriceAt(LocalDate dateValue) {
        return prices.stream()
                .filter(price -> price.getDateValue().equals(dateValue))
                .findFirst();
    }

    private Optional<Price> findActualPrice() {
        return prices.stream().max(Comparator.comparing(Price::getDateValue));
    }

    private Optional<StockMovement> findActualStock(){
        return stockMovements.stream().max(Comparator.comparing(StockMovement::getCreationDatetime));
    }
    @Override
    public String toString() {
        return "Ingredient(id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + ", updatedAt=" + updatedAt + ")";
    }
}