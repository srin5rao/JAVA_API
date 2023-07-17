package com.eficens.promotions.mainapp.service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.eficens.promotions.mainapp.modal.EmailTemplate;
import com.eficens.promotions.mainapp.modal.Product;

@Service

public class PromoService {

	@Value("${application.getproducts.url}")
	private String getAllProductsURL;

	@Value("${application.file.server.path}")
	private String serverPath;
	
	@Value("${application.send.email.with.attachment.url}")
	private String sendEmailWithAttachmentEndpoint;
	
	@Value("${mail.to.address}")
	private String toAddress;

	public Product[] getAllProductsFromProductAPI() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(getAllProductsURL, Product[].class);
		System.err.println(responseEntity.getBody().length);
		return responseEntity.getBody();

	}

	public String createProductsCSVFile(Product[] productArray) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddhhmmss");
	String dateAsString = simpleDateFormat.format(null);
		
		String fileName = serverPath + "products_" + +".csv";
		try {
			FileWriter myWriter = new FileWriter(fileName);
			myWriter.write("Product ID" + "," + "Product Name"+ "Price"+ "Discount");
			for (Product product : productArray) {
				myWriter.write(product.getProductId() + "," + product.getProductName()+ product.getPrice()+ product.getDiscount());
			}

			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return fileName;
	}

	public String createProductCSVFile(Product[] productArray) {
		String fileName = serverPath + "products.csv";
		return fileName;
	}
	public String sendEmailWithProductDataInCSV(String fileName)
	RestTemplate restTemplate = new RestTemplate();
	EmailTemplate emailTemplate = new EmailTemplate();
	
	String emailBody = "hi \n "
			+"\n"
	+"\n Please find attached "
	+"\n";
	
	String subject ="Product Data :" + new Date(0);
	
	emailTemplate.setToAddress(toAddress);
	emailTemplate.setSubject(subject);
	emailTemplate.setEmailBody(emailBody);
	emailTemplate.setAttachmentRequired(true);
	emailTemplate.setFilePath(fileName);
	
	ResponseEntity<String> response = restTemplate.postForEntity(sendEmailWithAttachmentEndpoint, emailTemplate , String.class );
	
	return response.getBody() ; 
	
}
