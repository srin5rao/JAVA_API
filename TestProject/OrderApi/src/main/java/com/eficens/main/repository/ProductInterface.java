package com.eficens.main.repository;

import com.eficens.main.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInterface extends JpaRepository<ProductModel, Long> {

    @Query(value = "SELECT * FROM product p WHERE p.product_name = :product_name", nativeQuery = true)
    public List<ProductModel> getProductWithName(String product_name);
}
