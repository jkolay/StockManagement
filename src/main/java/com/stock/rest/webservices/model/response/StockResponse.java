package com.stock.rest.webservices.model.response;

import com.stock.rest.webservices.model.entity.Price;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class StockResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Integer id;

	private final String name;

	private final Price price;

	private final Integer availableNumberOfStocks;

	public StockResponse(Integer id, String name, Price price,Integer availableNumberOfStocks) {
		super();

		this.id = id;
		this.name = name;
		this.price = price;
		this.availableNumberOfStocks=availableNumberOfStocks;
	}



	@Override
	public String toString() {
		return String.format("Stock [id=%s, name=%s]", id, name);
	}

}