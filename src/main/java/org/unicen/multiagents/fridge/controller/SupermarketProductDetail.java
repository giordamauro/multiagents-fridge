package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.AID;

class SupermarketProductDetail implements Comparable<SupermarketProductDetail>{

	private final AID supermarketAID;
	private final DetalleProducto detalleProducto;
	private float montoTotal;
	
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
		
		Float precio = detalleProducto.getPrecio();
		Float oPrice = o.getDetalleProducto().getPrecio();
		
		return precio.compareTo(oPrice);
	}
	
	public float getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}

	@Override
	public String toString() {
		return "SupermarketProductDetail [supermarketAID=" + supermarketAID.getLocalName() + ", detalleProducto=" + detalleProducto
				+ ", montoTotal=" + montoTotal + "]";
	}
}
