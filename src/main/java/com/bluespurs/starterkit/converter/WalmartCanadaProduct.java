package com.bluespurs.starterkit.converter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalmartCanadaProduct {
	private static final String Currency = "CAD";
	private static final String Location = "Walmart Canada";
	private String name;
	private double salePrice;
	private String itemId;
	
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
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public ProductResult getProductResult(){
		ProductResult result = new ProductResult();
		result.setBestPrice(this.salePrice);
		result.setCurrency(WalmartCanadaProduct.Currency);
		result.setLocation(WalmartCanadaProduct.Location);
		result.setProductName(this.name);
		result.setSku(this.itemId);
		return result;
	}

}
