package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.behaviours.FSMBehaviour;

public class CheckAndBuyProductFSMBehaviour extends FSMBehaviour {

	private static final long serialVersionUID = -2881775527407350732L;

	private static final String STATE_QUERY_PRODUCT = "QUERY_PRODUCT";
	private static final String STATE_WAIT_FOR_PRODUCT_INFORM = "WAIT_FOR_PRODUCT_INFORM";
	private static final String STATE_REQUEST_BUY_PRODUCT = "REQUEST_BUY_PRODUCT";
	private static final String STATE_WAIT_FOR_BUY_PRODUCT_RESPONSE = "WAIT_FOR_BUY_PRODUCT_RESPONSE";
	private static final String STATE_BOUGHT_PRODUCT = "BOUGHT_PRODUCT";

	private final String product;
	private final float amount;

	CheckAndBuyProductFSMBehaviour(Fridge fridge, String product, float amount) {
		this.product = product;
		this.amount = amount;

		DetalleProducto productDetail = new DetalleProducto();
		productDetail.setProducto(product);
		productDetail.setCantidad(amount);
		
		ComprarProducto productBuy = new ComprarProducto();
		productBuy.setProducto(product);
		productBuy.setCantidad(amount);
		productBuy.setMontoTotal(1.5F);

		// Registering states
		registerFirstState(new QueryIfProductBehaviour(productDetail), STATE_QUERY_PRODUCT);
		registerState(new WaitForInformProductBehaviour(productDetail), STATE_WAIT_FOR_PRODUCT_INFORM);
		registerState(new RequestBuyProductBehaviour(productBuy), STATE_REQUEST_BUY_PRODUCT);
		registerState(new WaitForBuyProductResponseBehaviour(), STATE_WAIT_FOR_BUY_PRODUCT_RESPONSE);
		registerLastState(new AddProductToFridgeBehaviour(fridge, productBuy), STATE_BOUGHT_PRODUCT);

		// Registering the transitions
		registerDefaultTransition(STATE_QUERY_PRODUCT, STATE_WAIT_FOR_PRODUCT_INFORM);
		registerTransition(STATE_WAIT_FOR_PRODUCT_INFORM, STATE_REQUEST_BUY_PRODUCT, 1);
		registerTransition(STATE_WAIT_FOR_PRODUCT_INFORM, STATE_QUERY_PRODUCT, 2);
		registerDefaultTransition(STATE_REQUEST_BUY_PRODUCT, STATE_WAIT_FOR_BUY_PRODUCT_RESPONSE);
		registerTransition(STATE_WAIT_FOR_BUY_PRODUCT_RESPONSE, STATE_BOUGHT_PRODUCT, 1);
		registerTransition(STATE_WAIT_FOR_BUY_PRODUCT_RESPONSE, STATE_QUERY_PRODUCT, 2);
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
