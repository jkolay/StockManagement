package com.stock.rest.webservices.repository;

import com.stock.rest.webservices.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PriceRepository extends JpaRepository<Price, Integer>{

	
}
