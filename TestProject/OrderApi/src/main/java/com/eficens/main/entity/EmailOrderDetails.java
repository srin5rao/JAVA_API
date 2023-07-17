package com.eficens.main.entity;

public class EmailOrderDetails {

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private Long transaction_id;

    private String customer_name;

    public EmailOrderDetails(Long transaction_id, String customer_name, String product_name, String payment_type, String order_status, int price) {
        this.transaction_id = transaction_id;
        this.customer_name = customer_name;
        this.product_name = product_name;
        this.payment_type = payment_type;
        this.order_status = order_status;
        this.price = price;
    }

    private String product_name;

    private String payment_type;

    private String order_status;

    private  int price;

}
