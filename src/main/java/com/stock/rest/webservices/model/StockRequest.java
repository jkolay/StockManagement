package com.stock.rest.webservices.model;

import com.stock.rest.webservices.model.entity.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    private int id;
    private String name;
    private PriceRequest prices;
    private Integer numberOfStocks;
}
