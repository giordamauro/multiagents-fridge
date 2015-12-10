package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.ComprarProducto;

import jade.core.behaviours.OneShotBehaviour;

public class AddProductToFridgeBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1567616332013641928L;
	
	private final Fridge fridge;
	private final ComprarProducto buyDetail;
	
	public AddProductToFridgeBehaviour(Fridge fridge, ComprarProducto buyDetail) {
		this.fridge = fridge;
		this.buyDetail = buyDetail;
	}

	public void action() {
		
		fridge.addProduct(buyDetail.getProducto(), buyDetail.getCantidad());
	}
}
