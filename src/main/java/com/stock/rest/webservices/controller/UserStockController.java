package com.stock.rest.webservices.controller;

import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.exception.UserNotFoundException;
import com.stock.rest.webservices.model.*;
import com.stock.rest.webservices.model.entity.Stock;
import com.stock.rest.webservices.model.response.AllStockResponse;
import com.stock.rest.webservices.model.response.BuyStockResponse;
import com.stock.rest.webservices.model.response.SellStockResponse;
import com.stock.rest.webservices.services.StockOperationsService;
import com.stock.rest.webservices.services.UserStockOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user/stocks")
public class UserStockController {

    @Autowired
    private StockOperationsService stockOperationsService;
    @Autowired
    private UserStockOperationsService userStockOperationsService;

    @GetMapping("/retrieveAll")
    public ResponseEntity<StockLists> getAllStocks() {
        return ResponseEntity.ok(new StockLists(stockOperationsService.findAllStocks()));
    }

    @GetMapping("/retrieve/ownStock/{userId}")
    public ResponseEntity<List<AllStockResponse>> getAllStock(@PathVariable int userId){

        return ResponseEntity.ok( userStockOperationsService.retrieveStocks(userId));
    }

    @PostMapping("/buy")
    public ResponseEntity<BuyStockResponse> buyNewStock( @Valid @RequestBody BuyStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions {
        return ResponseEntity.ok(userStockOperationsService.buyNewStock(stockRequest));
    }

    @PostMapping("/sell")
    public ResponseEntity<SellStockResponse> sellStock(@Valid @RequestBody SellStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions {
        return ResponseEntity.ok(userStockOperationsService.sellStock(stockRequest));
    }
}
