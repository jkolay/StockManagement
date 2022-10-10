package com.stock.rest.webservices.services;


import com.stock.rest.webservices.exception.UserValidationException;
import com.stock.rest.webservices.model.UserRequest;
import com.stock.rest.webservices.model.response.UserResponse;

import java.util.List;

public interface AdminOperationsService {

    public UserResponse addUser(UserRequest userRequest) throws UserValidationException;
    public List<UserResponse> retrieveUsers();
}
