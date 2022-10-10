package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.exception.UserNotFoundException;
import com.stock.rest.webservices.model.BuyStockRequest;
import com.stock.rest.webservices.model.SellStockRequest;
import com.stock.rest.webservices.model.response.AllStockResponse;
import com.stock.rest.webservices.model.response.BuyStockResponse;
import com.stock.rest.webservices.model.response.SellStockResponse;

import java.util.List;

public interface UserStockOperationsService {
    public BuyStockResponse buyNewStock(BuyStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions;
    public SellStockResponse sellStock(SellStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions;

    public List<AllStockResponse> retrieveStocks(int userId);
}
