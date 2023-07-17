package com.eficens.main.controller;


import com.eficens.main.entity.*;
import com.eficens.main.repository.*;
import com.eficens.main.sender.SendeMsg;
import com.eficens.main.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderDetailInterface orderDetailInterface;

    @Autowired
    ProductInterface productInterface;

    @Autowired
    CustomerInterface customerInterface;

    @Autowired
    CustomerService customerService;

    @Autowired
    PaymentInterface paymentInterface;

    @Autowired
    OrderInterface orderInterface;

    @Autowired
    SendeMsg messageQueue;


    @GetMapping("/getallorder")
    public List<OrderDetailEntity> getallorder(){

         List<OrderDetailEntity> orderDetailEntities = orderDetailInterface.findAll();
         return orderDetailEntities;
    }

    @GetMapping("/getProductWithName/{product_name}")
    public List<ProductModel> getProductWithName(@PathVariable("product_name") String product_name){
        try {
            List<ProductModel> productList = productInterface.getProductWithName(product_name);
            return productList;
        }
        catch (Exception ex){

            return (List<ProductModel>) new ProductModel();
        }

    }

    @GetMapping("/getCustomerDetailsByName/{customer_name}/{full_address}")
    public List<CustomerModel> getCustomerDetails(@PathVariable("customer_name") String customer_name, @PathVariable("full_address")String full_address) {

        List<CustomerModel> customerModel = customerInterface.getCustomerDetailsWithNameAndAddress(customer_name,full_address);
        return customerModel;
    }

    @GetMapping("/getPaymentWithType/{payment_type}")
    public List<PaymentModel> getPaymentDetails(@PathVariable("payment_type") String payment_type) {

        List<PaymentModel>  paymentModel= paymentInterface.getPaymentTypeWithName(payment_type);
        return paymentModel;
    }

    @PostMapping("/createOrder")
    public ResponseModel createProduct(@RequestBody OrderDetailEntity orderdetail) throws JsonProcessingException {

        OrderModel orderModel = new OrderModel();


        var flag = 0;
        ResponseModel responseModel = new ResponseModel();
        StringBuffer stringBuffer = new StringBuffer();
        CustomerModel[] customerArray = customerService.getCustomerDetailFromApi(orderdetail.getCustomer_name(),orderdetail.getFull_address());
        if(customerArray.length <= 0) {
            flag = 1;
            stringBuffer.append("Invalid Customer Name or Full address");
        }


        ProductModel[] productArry= customerService.getProductWithName(orderdetail.getProduct_name());
        if(productArry.length <= 0) {
            flag =1;
            stringBuffer.append("Invalid Product Name");
        }

        PaymentModel[] paymentarr= customerService.getPaymentWithType(orderdetail.getPayment_type());
        if(paymentarr.length <= 0) {
            flag = 1;
            stringBuffer.append("Invalid Payment Type");
        }

        if (flag == 1){
            responseModel.setStatusCode(400);
        }
        else {
            CustomerModel validCustomer = customerArray[0];
            PaymentModel validPayment = paymentarr[0];
            ProductModel validProduct = productArry[0];
            responseModel.setStatusCode(200);


            orderModel.setProductId((long) validProduct.getProduct_id());
            orderModel.setAddressId((long) validCustomer.getAddress_id());
            orderModel.setCustomerId((long) validCustomer.getCustomer_id());
            orderModel.setPaymentId((long) validPayment.getPayment_id());
            orderModel.setStatusId(2L);
            int trans = 1082;
            trans = trans + 1;
            orderModel.setTransactionId((long) trans);

//            EmailTemplate emailTemplate = new EmailTemplate();
//            emailTemplate.setEmailTo(validCustomer.getCustomer_email());
//            emailTemplate.setEmailSubject("Order Placed");
//            emailTemplate.setEmailBody("Order has been placed with transacation ID"+orderModel.getTransactionId()+"\n Thank You for Order");

            OrderModel newOrder = orderInterface.save(orderModel);
//            String stringPrice =String.valueOf(validProduct.getPrice());
            EmailOrderDetails emailOrderDetails = new EmailOrderDetails(orderModel.getTransactionId(),orderdetail.getCustomer_name(),orderdetail.getProduct_name(),orderdetail.getPayment_type(),orderdetail.getOrder_status(),validProduct.getPrice());
            messageQueue.send(emailOrderDetails);
//           customerService.sendEmail(emailTemplate);

           stringBuffer.append("New order created");
           stringBuffer.append("Email has been Send");
        }
        responseModel.setMessage(stringBuffer.toString());
        return responseModel;
    }
}
