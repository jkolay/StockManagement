package com.stock.rest.webservices.model.response;

import com.stock.rest.webservices.model.entity.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllStockResponse {

    private int id;
    private String name;
    private Price prices;
    private Integer numberOfStocks;
    private Integer selfOwnedStock;
}
