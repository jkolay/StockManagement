package com.stock.rest.webservices.model;

import com.stock.rest.webservices.model.response.StockResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StockLists {

	private List<StockResponse> listOfStock;

	public StockLists(List<StockResponse> listOfStock) {
		this.listOfStock = listOfStock;
	}

	public List<StockResponse> getListOfStock() {
		if (null == listOfStock) {
			this.listOfStock  = new ArrayList<>();
		}
		return listOfStock;
	}

}
