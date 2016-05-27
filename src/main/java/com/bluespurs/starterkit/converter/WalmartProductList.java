package com.bluespurs.starterkit.converter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WalmartProductList {

	private int start;
	private int totalResults;
	private List<WalmartCanadaProduct> items;
	

	public List<WalmartCanadaProduct> getItems() {
		if (this.items == null){
			this.items = new ArrayList<WalmartCanadaProduct>();
		}
		return this.items;
	}

	public void setItems(List<WalmartCanadaProduct> items) {
		this.items = new ArrayList<WalmartCanadaProduct>(items);
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	
}
