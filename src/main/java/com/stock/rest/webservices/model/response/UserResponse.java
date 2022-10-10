package com.stock.rest.webservices.model.response;

import com.stock.rest.webservices.model.entity.Wallet;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private long id;
    private String username;
    private String password;
    private String role;
    private Wallet wallet;
}
