package com.stock.rest.webservices.model;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class PriceRequest {
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	private Double price;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
