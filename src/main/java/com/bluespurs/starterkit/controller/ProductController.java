package com.bluespurs.starterkit.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.bluespurs.starterkit.converter.BestBuyCanadaProduct;
import com.bluespurs.starterkit.converter.BestBuyProductList;
import com.bluespurs.starterkit.converter.ProductResult;
import com.bluespurs.starterkit.converter.WalmartProductList;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value="/product")
public class ProductController {
    public static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public static final String NO_MATCH = "NO MATCH FOUND";
    public static final String BBRequest = "http://api.bestbuy.com/v1/products(name=XXXX)?show=sku,name,salePrice,activeUpdateDate&pageSize=100&apiKey=pfe9fpy68yg28hvvma49sc89&format=json&page=";
    public static final String WMRequest = "http://api.walmartlabs.com/v1/search?apiKey=rm25tyum3p9jm9x9x7zxshfa&query=XXXX&format=json&sort=price&order=asc&numItems=1&responseGroup=base";

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public ProductResult getProduct(@RequestParam String name) {
    	
    	// Spring MVC is already sanitizing the query parameter
        log.info("Search request for: " + name);
        return getBestPricedProduct(name) ;
    }
    
    /**
     * Get a single Product result across the two vendors. Compare the best from each, and take the best of those.
     * @param searchValue
     * @return
     */
    public ProductResult getBestPricedProduct(String searchValue){
    	ProductResult bbBest = getMinimumPricedBBProduct(searchValue);
    	ProductResult wmBest = getBestWalmartPrice(searchValue);
    	if (bbBest.getBestPrice() < 0 || bbBest.getBestPrice() > wmBest.getBestPrice()) {
    		return wmBest;
    	} else {
    		return bbBest;
    	}
    	
    }
    
    /**
     * Iterate over the full list of returned results from Best Buy. Take the product with the smallest price
     * @param searchString
     * @return
     */
    public ProductResult getMinimumPricedBBProduct(String searchString){
    	List<BestBuyCanadaProduct> matchedProducts = getBBPrices(searchString);
    	ProductResult bestPriced = new ProductResult();
    	bestPriced.setProductName(ProductController.NO_MATCH); // fail out with valid object
    	bestPriced.setBestPrice(-1.0);
    	for (BestBuyCanadaProduct prod : matchedProducts){
    		if ((bestPriced.getBestPrice() == -1.0) || //first value
    				(prod.getSalePrice() < bestPriced.getBestPrice())){
    			bestPriced = prod.getProductResult(); // update as new current best
    		}
    	}
    	
    	return bestPriced;
    }
    
    /**
     * Send the request to BestBuy API to retrieve all matching products
     * @param searchString
     * @return
     */
    public List<BestBuyCanadaProduct> getBBPrices(String searchString){
        RestTemplate bbRestTemplate = new RestTemplate();
        BestBuyProductList bestBuyResponse = null;
        try {
        	log.info("Checking BestBuy for matches");
        	String jsonResponse = (String) bbRestTemplate.getForObject(
        			ProductController.BBRequest.replace("XXXX", searchString + "*") + "1", String.class);
        	ObjectMapper mapper = new ObjectMapper();
        	bestBuyResponse = mapper.readValue(jsonResponse, BestBuyProductList.class);
        	int curPage = bestBuyResponse.getCurrentPage();
        	while (curPage <= bestBuyResponse.getTotalPages()){
        		curPage++;
        		jsonResponse = (String) bbRestTemplate.getForObject(
            			ProductController.BBRequest.replace("XXXX", searchString + "*") + curPage, String.class);
        		BestBuyProductList nextPage = mapper.readValue(jsonResponse, BestBuyProductList.class);
        		bestBuyResponse.getProducts().addAll(nextPage.getProducts());
        	}
        	
                } catch (HttpStatusCodeException ex) {
                 log.info(ex.getMessage());
                 log.info("Unable to process BestBuy request");
                } catch (IOException ex){
                	log.info(ex.getMessage());
                }
        if (bestBuyResponse == null){
        	return new ArrayList<BestBuyCanadaProduct>();
        }
        return bestBuyResponse.getProducts();
    }
    
    /**
     * The Walmart API provides a search by price, so just take the first result when sorting by price
     * @param searchString
     * @return
     */
    public ProductResult getBestWalmartPrice(String searchString){
        RestTemplate wmRestTemplate = new RestTemplate();
        WalmartProductList wmResponse = null;
        try {
        	log.info("checking Walmart for matches");
        	String jsonResponse = (String) wmRestTemplate.getForObject(
        			ProductController.WMRequest.replace("XXXX", searchString ) , String.class);
        	ObjectMapper mapper = new ObjectMapper();
        	wmResponse = mapper.readValue(jsonResponse, WalmartProductList.class);
        	
                } catch (HttpStatusCodeException ex) {
                 log.info(ex.getMessage());
                 log.info("Unable to process Walmart request");
                } catch (IOException ex){
                	log.info(ex.getMessage());
                }
        ProductResult result = null;
        if (wmResponse == null || wmResponse.getItems().isEmpty()){
        	result = new ProductResult();
        	result.setProductName(ProductController.NO_MATCH); // fail out with valid object
        	result.setBestPrice(-1.0);
        } else {
        	result = wmResponse.getItems().get(0).getProductResult();
        }
        return result;
    }
    
}
