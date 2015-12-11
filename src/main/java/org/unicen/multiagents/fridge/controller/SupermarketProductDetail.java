package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.AID;

class SupermarketProductDetail implements Comparable<SupermarketProductDetail>{

	private final AID supermarketAID;
	private final DetalleProducto detalleProducto;
	
	public SupermarketProductDetail(AID supermarketAID, DetalleProducto detalleProducto) {
	
		this.supermarketAID = supermarketAID;
		this.detalleProducto = detalleProducto;
	}

	public AID getSupermarketAID() {
		return supermarketAID;
	}

	public DetalleProducto getDetalleProducto() {
		return detalleProducto;
	}
	
	@Override
	public int compareTo(SupermarketProductDetail o) {
	
		float oPrice = o.getDetalleProducto().getPrecio();
		
		if(oPrice > detalleProducto.getPrecio()){
			return -1;
		}
		else if(oPrice < detalleProducto.getPrecio()){
			return 1;
		}
		
		return 0;
	}

	@Override
	public String toString() {
		return "SupermarketProductDetail [supermarketAID=" + supermarketAID + ", detalleProducto=" + detalleProducto
				+ "]";
	}

}
