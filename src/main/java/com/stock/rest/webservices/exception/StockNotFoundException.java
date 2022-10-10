package com.stock.rest.webservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Stock_not_found exception
 * @author jayati
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public StockNotFoundException(String message) {
		super(message);
	}
}
