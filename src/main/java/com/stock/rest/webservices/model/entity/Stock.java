package com.stock.rest.webservices.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JoinFormula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "Stock Details", description = "Contains all details of a Stock")
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
	
	private static final String VALIDATION="Name should have atleast 3 characters";
	private static final String FORMULA="(" + "SELECT s.id " + "FROM PRICE s " + "WHERE s.STOCK_ID   = ID " + "ORDER BY s.timestamp DESC " + "LIMIT 1" + ")";

	@Id
	@Column(name="stock_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="stock_id_seq")
	@SequenceGenerator(name="stock_id_seq", sequenceName="stock_id_seq", allocationSize=1,initialValue = 1015)
	private int id;

	@Size(min = 3, message = VALIDATION)
	@ApiModelProperty(notes = VALIDATION)
	@NotBlank(message="Stock name")
	@Column(unique = true,name="stock_name")
	private String name;


	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id", referencedColumnName = "id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull
	private Price prices;

	private Integer numberOfStocks;

	public Stock(int id, String name,Integer numberOfStocks) {
		super();
		this.id = id;
		this.name = name;
		this.numberOfStocks= numberOfStocks;
	}



}