package com.eficens.main.repository;


import com.eficens.main.entity.OrderDetailEntity;
import com.eficens.main.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailInterface extends JpaRepository<OrderDetailEntity, Long> {

}
