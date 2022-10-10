package com.stock.rest.webservices.model;

import lombok.Data;

@Data
public class WalletRequest {
    public long userId;
    public double amount;
}
