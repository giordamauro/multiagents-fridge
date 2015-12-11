package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;

public class ProductBuyerFSMBehaviour extends FSMBehaviour {

	private static final long serialVersionUID = -2881775527407350732L;

	private final String product;
	private final float amount;

	ProductBuyerFSMBehaviour(Fridge fridge, String product, float amount) {
		this.product = product;
		this.amount = amount;

		DataStore dataStore = new DataStore();
		
		DetalleProducto productDetail = new DetalleProducto();
		productDetail.setProducto(product);
		productDetail.setCantidad(amount);
		
		// Creating state behaviors
		QuerySupermarketsBehaviour querySupermarketsBehaviour = new QuerySupermarketsBehaviour(productDetail);
		querySupermarketsBehaviour.setDataStore(dataStore);
		
		NothingToBuyBehaviour nothingToBuyBehaviour = new NothingToBuyBehaviour(productDetail);
		
		BuyLowestPriceBehaviour buyLowestPriceBehaviour = new BuyLowestPriceBehaviour();
		buyLowestPriceBehaviour.setDataStore(dataStore);
		
		BoughtProductBehaviour boughtProductBehaviour = new BoughtProductBehaviour(fridge);
		boughtProductBehaviour.setDataStore(dataStore);
		
		final String QUERY_SUPERMARKET_STATE = querySupermarketsBehaviour.getClass().getSimpleName();
		final String BUY_LOWEST_PRICE_STATE = buyLowestPriceBehaviour.getClass().getSimpleName();
		final String BOUGHT_PRODUCT_STATE = boughtProductBehaviour.getClass().getSimpleName();
		final String NOTHING_TO_BUY_STATE = nothingToBuyBehaviour.getClass().getSimpleName();
		
		// Registering states
		registerFirstState(querySupermarketsBehaviour, QUERY_SUPERMARKET_STATE);
		registerState(buyLowestPriceBehaviour, BUY_LOWEST_PRICE_STATE);
		registerLastState(boughtProductBehaviour, BOUGHT_PRODUCT_STATE);
		registerLastState(nothingToBuyBehaviour,NOTHING_TO_BUY_STATE);

		// Registering the transitions
		registerTransition(QUERY_SUPERMARKET_STATE, BUY_LOWEST_PRICE_STATE, QuerySupermarketsState.SUPERMARKETS_LIST_READY.value());
		registerTransition(QUERY_SUPERMARKET_STATE, NOTHING_TO_BUY_STATE, QuerySupermarketsState.NOTHING_TO_BUY.value());
		registerTransition(BUY_LOWEST_PRICE_STATE, BOUGHT_PRODUCT_STATE, BuyLowestPriceState.PRODUCT_BUY_AGREE.value());
		registerTransition(BUY_LOWEST_PRICE_STATE, NOTHING_TO_BUY_STATE, BuyLowestPriceState.NOTHING_TO_BUY.value());
	}

	public String getProduct(){
		return product;
	}
	
	@Override
	public int onEnd() {

		System.out.println("Finished buying " + product + " - " + amount);

		FridgeControllerAgent controllerAgent = (FridgeControllerAgent) myAgent;
		controllerAgent.onBuyProductEnd(this);
		
		return super.onEnd();
	}
}
