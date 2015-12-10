package org.unicen.multiagents.ontology;

import jade.content.AgentAction;

public class ComprarProducto implements AgentAction {

	private static final long serialVersionUID = 7928883146739565343L;

	private String producto;
	private float cantidad;
	private float montoTotal;

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	public float getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		this.montoTotal = montoTotal;
	}

	@Override
	public String toString() {
		return "ComprarProducto [producto=" + producto + ", cantidad=" + cantidad + ", montoTotal=" + montoTotal + "]";
	}
}
