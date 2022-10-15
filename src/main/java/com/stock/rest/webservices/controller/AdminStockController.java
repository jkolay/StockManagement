package com.stock.rest.webservices.controller;

import javax.validation.Valid;

import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.model.StockRequest;
import com.stock.rest.webservices.model.entity.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.rest.webservices.exception.StockNotFoundException;
import com.stock.rest.webservices.model.PriceRequest;
import com.stock.rest.webservices.model.StockLists;
import com.stock.rest.webservices.model.response.StockResponse;
import com.stock.rest.webservices.services.StockOperationsService;

import java.util.List;

/**
 * Controller method is used to get the rest resource for stock object for admin users
 *
 */
@RestController
@RequestMapping("/api/admin/stocks")
public class AdminStockController {

	@Autowired
	private StockOperationsService stockOperationsService;

	/**
	 * @return all Stock latest result
	 */
	@GetMapping("/retrieveAll")
	public ResponseEntity<StockLists> getAllStocks() {
		return ResponseEntity.ok(new StockLists(stockOperationsService.findAllStocks()));
	}

	/**
	 * @param id Stock Id Based search
	 * @return Result fetched with specific ID
	 * @throws StockNotFoundException Stock ID not present in the database
	 */
	@GetMapping("/{id}")
	public ResponseEntity<StockResponse> getStockById(@PathVariable int id) throws StockNotFoundException {
		return ResponseEntity.ok(stockOperationsService.findStock(id));
	}

	/**
	 * @param stock Take stock object in body
	 * @return response body with HTTP status
	 */

	@PostMapping("/add")
	public ResponseEntity<List<Stock>> addStock(@Valid @RequestBody List<StockRequest> stock) throws StockOperationsExceptions {
		return ResponseEntity.ok(stockOperationsService.addStock(stock));
	}

	/**
	 * @param stockId take stock ID
	 * @param price   price body
	 * @return response message with HTTP Status
	 * @throws StockNotFoundException
	 * @throws JsonProcessingException
	 */
	@PutMapping("/update/{stockid}")
	public ResponseEntity<String> updateStockPrice(@Valid @PathVariable(name = "stockid") int stockId,
			@RequestBody PriceRequest price) throws StockNotFoundException, JsonProcessingException {
		stockOperationsService.updateStockPrice(stockId, price.getPrice());
		return new ResponseEntity<String>(new ObjectMapper().writerWithDefaultPrettyPrinter()
				.writeValueAsString(String.format("Stock %s is updated successfully", stockId)), HttpStatus.OK);
	}
}