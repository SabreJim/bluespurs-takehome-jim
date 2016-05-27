package com.bluespurs.starterkit.converter;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductResult {
	protected String productName;
	protected double bestPrice;
	protected String currency;
	protected String location;
	protected String sku;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getBestPrice() {
		return bestPrice;
	}
	public void setBestPrice(double bestPrice) {
		this.bestPrice = bestPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@JsonIgnore
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}

}
