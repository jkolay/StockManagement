package com.stock.rest.webservices.repository;

import com.stock.rest.webservices.model.entity.UserStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockRepository extends JpaRepository<UserStockDetails, Long> {
    UserStockDetails findByUserIdAndStockId(Long userId,Integer stockId);


}
