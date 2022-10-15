package com.stock.rest.webservices.model.entity;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Price model
 * @author jayati
 *
 */
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="price_id_seq")
	@SequenceGenerator(name="price_id_seq", sequenceName="price_id_seq", allocationSize=1,initialValue = 1015)

	private Integer id;
	@NotNull
	@NumberFormat(style = Style.NUMBER)
	private Double prices;
	private Timestamp timestamp;

	@ManyToOne
	@JsonIgnore
	private Stock stock;


	public Price(Integer id, Double prices, Timestamp timestamp) {
		super();
		this.id = id;
		this.prices = prices;
		this.timestamp = timestamp;

	}

	public Price( Double prices, Timestamp timestamp) {
		super();
		this.prices = prices;
		this.timestamp = timestamp;

	}
	@JsonIgnore
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Price [id=%s, price=%s , timestamp=%s]", id, prices, timestamp);
	}

}