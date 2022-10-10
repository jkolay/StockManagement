package com.stock.rest.webservices.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SellStockResponse {
    private Integer stockId;
    private Integer numberOFStocks;
    private String status;
}
