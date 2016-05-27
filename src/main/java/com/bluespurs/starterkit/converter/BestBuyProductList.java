package com.bluespurs.starterkit.converter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BestBuyProductList {

	private int currentPage;
	private int totalPages;
	private List<BestBuyCanadaProduct> products;
	
	public int getCurrentPage() {
		return currentPage; 
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<BestBuyCanadaProduct> getProducts() {
		if (this.products == null){
			this.products = new ArrayList<BestBuyCanadaProduct>();
		}
		return this.products;
	}

	public void setProducts(List<BestBuyCanadaProduct> products) {
		this.products = new ArrayList<BestBuyCanadaProduct>(products);
	}
	
}
