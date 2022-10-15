package com.stock.rest.webservices.services;

import java.util.List;

import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.model.BuyStockRequest;
import com.stock.rest.webservices.model.StockRequest;
import com.stock.rest.webservices.model.response.BuyStockResponse;
import com.stock.rest.webservices.model.response.StockResponse;
import com.stock.rest.webservices.model.entity.Stock;
import org.springframework.http.ResponseEntity;

/**
 *  Interface for service call
 * @author jayati
 *
 */
public interface StockOperationsService {
	
	public StockResponse findStock(int stockId);

	public List<StockResponse> findAllStocks();

	public List<Stock> addStock(List<StockRequest> stock) throws StockOperationsExceptions;

	public void updateStockPrice(int stockId, double price);

}
