package org.unicen.multiagents.supermarket;

import java.util.HashMap;
import java.util.Map;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;

public class StockSupermarketAgent extends SupermarketAgent{

	private static final long serialVersionUID = -7430906402625851528L;

	private final Map<String, DetalleProducto> productsStock = new HashMap<>();

	public void setProductStock(String productName, float amount, float price){
		
		DetalleProducto detalleProducto = new DetalleProducto();
		detalleProducto.setProducto(productName);
		detalleProducto.setCantidad(amount);
		detalleProducto.setPrecio(price);
		
		productsStock.put(productName, detalleProducto);
	}
	
	protected void setup(){
		
		Object[] arguments = getArguments();

		for(Object argument : arguments){
			
			DetalleProducto detalleProducto = (DetalleProducto) argument;
			productsStock.put(detalleProducto.getProducto(), detalleProducto);
		}
		
		super.setup();
	}
	
	protected DetalleProducto getProductSellDetail(DetalleProducto detalleProducto) {

		String productName = detalleProducto.getProducto();
		
		DetalleProducto productInfo = productsStock.get(productName);
		if(productInfo == null){
		
			productInfo = new DetalleProducto();
			productInfo.setProducto(productName);
			productInfo.setCantidad(-1F);
			productInfo.setPrecio(-1F);
		}
		
		return productInfo;
	}

	protected boolean agreeOrRefuseProductSell(ComprarProducto comprarProducto) {

		String productName = comprarProducto.getProducto();
		DetalleProducto productInfo = productsStock.get(productName);
		
		boolean agree = false;
		if (productInfo != null){
			
			float cantidad = comprarProducto.getCantidad();
			float montoTotal = comprarProducto.getMontoTotal();
			
			if(productInfo.getCantidad() >= cantidad){
				
				float precio = productInfo.getPrecio();
				if(precio * cantidad >= montoTotal){
					agree = true;
				}
			}
		}
		
		return agree;
	}
}
