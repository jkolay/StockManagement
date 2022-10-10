package com.stock.rest.webservices.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellStockRequest {
    private long userId;
    private int stockId;
    private Integer numberOfStocks;
}
