package com.stock.rest.webservices.model.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WalletResponse {
    public Double balance;
}
