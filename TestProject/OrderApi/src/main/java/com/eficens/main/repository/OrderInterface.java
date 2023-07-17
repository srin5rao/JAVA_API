package com.eficens.main.repository;

import com.eficens.main.entity.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInterface extends JpaRepository<OrderModel, Long> {
}
