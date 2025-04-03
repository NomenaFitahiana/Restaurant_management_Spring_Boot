package com.example.demo.repository.mapper;

import java.sql.ResultSet;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.demo.entity.Price;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class PriceMapper implements Function<ResultSet, Price> {

    @SneakyThrows
    @Override
    public Price apply(ResultSet resultSet){
        Price price = new Price();
        price.setId(resultSet.getLong("id"));
        price.setAmount(resultSet.getDouble("amount"));
        price.setDateValue(resultSet.getDate("date_value").toLocalDate());

        return price;
    }

}
