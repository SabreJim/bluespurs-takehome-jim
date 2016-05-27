package com.bluespurs.starterkit.converter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BestBuyCanadaProduct {
	private static final String Currency = "CAD";
	private static final String Location = "Best Buy Canada";
	private String name;
	private String activeUpdateDate;
	private double salePrice;
	private String sku;
	
	public double getSalePrice() {
		return salePrice;
	}
	
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getActiveUpdateDate() {
		return activeUpdateDate;
	}

	public void setActiveUpdateDate(String activeUpdateDate) {
		this.activeUpdateDate = activeUpdateDate;
	}
	
	public ProductResult getProductResult(){
		ProductResult result = new ProductResult();
		result.setBestPrice(this.salePrice);
		result.setCurrency(BestBuyCanadaProduct.Currency);
		result.setLocation(BestBuyCanadaProduct.Location);
		result.setProductName(this.name);
		result.setSku(this.sku);
		return result;
	}

}
