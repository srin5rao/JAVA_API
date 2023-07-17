package com.eficens.main.repository;

import com.eficens.main.entity.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerInterface extends JpaRepository<CustomerModel, Long> {

    @Query(value = "select * from ecomm_order.customer_details c where c.customer_name = :customer_name and c.full_address =:full_address", nativeQuery = true)
    public List<CustomerModel> getCustomerDetailsWithNameAndAddress(String customer_name, String full_address);

}
