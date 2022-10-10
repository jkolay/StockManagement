package com.stock.rest.webservices.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserStockDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "stock_id")
    private Integer stockId;

    @Column(name = "number_of_stocks")
    private Integer numberOfStocks;
}
