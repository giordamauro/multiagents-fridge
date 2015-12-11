package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;

public class BoughtProductBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1567616332013641928L;
	
	private final Fridge fridge;
	
	public BoughtProductBehaviour(Fridge fridge) {
		this.fridge = fridge;
	}

	public void action() {
		
		DataStore dataStore = getDataStore();
		SupermarketProductDetail productDetail = (SupermarketProductDetail) dataStore.get(BuyLowestPriceState.PRODUCT_BUY_AGREE.name());
		DetalleProducto detalleProducto = productDetail.getDetalleProducto();
		
		fridge.addProduct(detalleProducto.getProducto(), detalleProducto.getCantidad());
		
		//descontar de la billetera el monto total
		
		System.out.println("Finished buying " + productDetail);
	}
}
