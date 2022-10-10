package com.stock.rest.webservices.controller;

import com.stock.rest.webservices.exception.WalletOperationsException;
import com.stock.rest.webservices.model.WalletRequest;
import com.stock.rest.webservices.model.response.WalletResponse;
import com.stock.rest.webservices.services.WalletOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/user/wallet")
public class UserWalletController {
    @Autowired
    WalletOperationService walletOperationService;
    @PostMapping("/add")
    public ResponseEntity<WalletResponse> addBalance(@RequestBody WalletRequest walletRequest) throws WalletOperationsException {
        return ResponseEntity.ok(walletOperationService.addAmount(walletRequest));
    }
    @GetMapping("/check/balance/{userId}")
    public ResponseEntity<WalletResponse> checkBalance(@PathVariable int userId) throws WalletOperationsException {
        return ResponseEntity.ok(walletOperationService.checkBalance(userId));
    }
}
