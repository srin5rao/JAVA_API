package com.eficens.main.service;

import com.eficens.main.entity.CustomerModel;
import com.eficens.main.entity.EmailTemplate;
import com.eficens.main.entity.PaymentModel;
import com.eficens.main.entity.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

    @Value("${customer.url}")
    public String customerUrl;

    @Value("${product.url}")
    public String ProductUrl;

    @Value("${payment.url}")
    public String paymentUrl;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    public String fromMail;

    public CustomerModel[] getCustomerDetailFromApi(String customer_name, String full_address){


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CustomerModel[]> customerresponse = restTemplate.getForEntity(customerUrl+"/"+customer_name+"/"+full_address, CustomerModel[].class);
        return customerresponse.getBody();

    }

    public ProductModel[] getProductWithName(String ProductName){


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductModel[]> productresponse = restTemplate.getForEntity(ProductUrl+"/"+ProductName, ProductModel[].class);
        return productresponse.getBody();

    }

    public PaymentModel[] getPaymentWithType(String paymentType){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PaymentModel[]> paymentResponse = restTemplate.getForEntity(paymentUrl+"/"+paymentType, PaymentModel[].class);
        return paymentResponse.getBody();

    }

    public String sendEmail(EmailTemplate emailTemplate) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(fromMail);
            mailMessage.setTo(emailTemplate.getEmailTo());
            mailMessage.setText(emailTemplate.getEmailBody());
            mailMessage.setSubject(emailTemplate.getEmailSubject());
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception ex) {

        }
        return "Error while Sending Mail";

    }

}
