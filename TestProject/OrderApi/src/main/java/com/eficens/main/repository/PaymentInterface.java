package com.eficens.main.repository;

import com.eficens.main.entity.PaymentModel;
import com.eficens.main.entity.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentInterface extends JpaRepository<PaymentModel, Long> {

    @Query(value = "SELECT * FROM payment pay WHERE pay.payment_type = :payment_type", nativeQuery = true)
    public List<PaymentModel> getPaymentTypeWithName(String payment_type);

}
