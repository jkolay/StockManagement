package com.stock.rest.webservices.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyStockRequest {
    private long userId;
    private int stockId;
    private Integer numberOfStocks;


}
