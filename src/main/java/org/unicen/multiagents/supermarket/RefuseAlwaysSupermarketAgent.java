package org.unicen.multiagents.supermarket;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;

public class RefuseAlwaysSupermarketAgent extends SupermarketAgent{

	private static final long serialVersionUID = -2330002390018284381L;

	protected DetalleProducto getProductSellDetail(DetalleProducto content) {

		DetalleProducto sellDetail = new DetalleProducto();
		sellDetail.setProducto(content.getProducto());
		sellDetail.setCantidad(10000);
		sellDetail.setPrecio(1.5f);

		return sellDetail;
	}

	protected boolean agreeOrRefuseProductSell(ComprarProducto content) {
		return false;
	}
}
