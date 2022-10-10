package com.stock.rest.webservices.repository;

import com.stock.rest.webservices.model.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "SELECT s.stock_id,s.price_id,s.stock_name,s.number_of_stocks,usd.number_of_stocks as stockForUser from stock s,user_stock_details usd where usd.stock_id=s.stock_Id and  user_id= :userId",nativeQuery = true)
    public List<Stock> getStockForUser(@Param("userId") int userId);

}
