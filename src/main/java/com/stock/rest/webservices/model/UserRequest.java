package com.stock.rest.webservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private long id;
    private String username;
    private String password;
    private String role;
    private String email;

}
