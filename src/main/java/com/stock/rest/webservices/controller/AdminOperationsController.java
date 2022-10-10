package com.stock.rest.webservices.controller;

import com.stock.rest.webservices.exception.UserValidationException;
import com.stock.rest.webservices.model.UserRequest;
import com.stock.rest.webservices.model.entity.User;
import com.stock.rest.webservices.model.response.UserResponse;
import com.stock.rest.webservices.services.AdminOperationsService;
import com.stock.rest.webservices.services.StockOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOperationsController {
    @Autowired
    private AdminOperationsService adminOperationsService;


    @PostMapping("/add/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) throws UserValidationException {
        return ResponseEntity.ok(adminOperationsService.addUser(userRequest));
    }

    @GetMapping("/retrieveUsers")
    public ResponseEntity<List<UserResponse>> addUser(){
        return ResponseEntity.ok(adminOperationsService.retrieveUsers());
    }
}
