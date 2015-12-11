package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.behaviours.OneShotBehaviour;

public class NothingToBuyBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 6000913548150246315L;

	private DetalleProducto productDetail;

	public NothingToBuyBehaviour(DetalleProducto productDetail) {

		this.productDetail = productDetail;
	}

	public void action() {
		
		System.out.println("Nothing to buy - " + productDetail);
	}
}
