package org.unicen.multiagents.fridge;

import java.util.List;
import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class FridgeConsumerAgent extends Agent {

	private static final long serialVersionUID = 5942763705871230730L;
	private static final Random RANDOM = new Random();
	
	private Fridge fridge;
		
	@SuppressWarnings("serial")
	protected void setup() {

		Fridge fridge = (Fridge) getArguments()[0];
		this.fridge = fridge;
		
		addBehaviour(new TickerBehaviour(this, 5000) {

			protected void onTick() {
				consumeRandomly();
			}
		});
	}
	
	private void consumeRandomly() {

		String product = pickProductRandomly();
		float productStock = fridge.getProductStock(product);

		float randomAmount = RANDOM.nextFloat() * productStock;
		
		if(productStock - randomAmount < 1){
			randomAmount = productStock;
		}
		
		System.out.println(String.format("Consuming randomly %s - Amount: %s", product, randomAmount));
		fridge.consumeProduct(product, randomAmount);
	}

	private String pickProductRandomly() {

		List<String> products = fridge.getProducts();
		
		if(products.isEmpty()){
			throw new IllegalStateException("Cannot pick a product - Products are empty");
		}
			
		int randomIndex = RANDOM.nextInt(products.size());
		String randomProduct = products.get(randomIndex);
		
		return randomProduct;
	}
}
