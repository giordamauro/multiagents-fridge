package org.unicen.multiagents.ontology;

import jade.content.ContentElement;

public class DetalleProducto implements ContentElement {

	private static final long serialVersionUID = 465467994073412213L;

	private String producto;
	private float precio;
	private float cantidad;

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getCantidad() {
		return cantidad;
	}

	public void setCantidad(float cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "DetalleProducto [producto=" + producto + ", precio=" + precio + ", cantidad=" + cantidad + "]";
	}
}
