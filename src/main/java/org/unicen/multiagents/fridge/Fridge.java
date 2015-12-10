package org.unicen.multiagents.fridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La heladera contiene la cantidad de los productos disponibles.
 */
public class Fridge {

	private final Map<String, Float> productsStock = new HashMap<>();
	
	public synchronized void addProduct(String product, float amount){
		
		if(amount < 0){
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		
		float productStock = getProductStock(product);	
		productsStock.put(product, productStock + amount);
	}

	public synchronized void consumeProduct(String product, float amount){

		if(amount < 0){
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		
		float productStock = getProductStock(product);	

		if(productStock < amount){
			throw new IllegalStateException("Product amount is greater than the stock available");
		}
		
		productsStock.put(product, productStock - amount);
	}
	
	public List<String> getProducts(){
		
		List<String> products = new ArrayList<>();
		products.addAll(productsStock.keySet());
		
		return products;
	}
	
	public float getProductStock(String product){

		if(product == null){
			throw new IllegalArgumentException("Product cannot be null");
		}
		
		Float stockProduct = productsStock.get(product);
		if(stockProduct == null){
			stockProduct = 0f;
		}
		
		return stockProduct;
	}
}
