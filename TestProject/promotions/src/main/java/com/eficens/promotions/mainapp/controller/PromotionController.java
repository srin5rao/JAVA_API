package com.eficens.promotions.mainapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eficens.promotions.mainapp.modal.Product;
import com.eficens.promotions.mainapp.modal.PromoResponse;
import com.eficens.promotions.mainapp.service.PromoService;

@RestController
@RequestMapping("/promo")

public class PromotionController {
	@Autowired PromoService promoService;

	@GetMapping("/createProductsFile")
	public PromoResponse createProductsFile() {
		Product[] productArray = promoService.getAllProductsFromProductAPI();
		PromoResponse promoResponse= new PromoResponse();
		try {
			String fileName =promoService.createProductsFile(productArray);
			promoResponse.setFilename(fileName);
			promoResponse.setStatusCode(200);
			promoResponse.setStatusMessage("Product api call is successful");
		}catch (Exception ex) {
		
			promoResponse.setStatusCode(500);
			promoResponse.setStatusMessage("API call failed");
		}
	
		return promoResponse;
	}
	
	@PostMapping("SendProductDataInEmail")
	public PromoResponse SendProductDataInEmail() {
		PromoResponse promoResponse= new PromoResponse();
		Product[] productArray = promoService.getAllProductsFromProductAPI();
		
		String fileName = promoService.createProductCSVFile(productArray);
		String emailAPIResponse = promoService.sendEmailWithProductDataInCSV(fileName);
		promoResponse.setFilename(fileName);
		promoResponse.setStatusCode(200);
		promoResponse.setStatusMessage("Product api call is successful");
		return promoResponse;
	}

}
