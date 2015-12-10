package org.unicen.multiagents.fridge.controller;

import java.util.List;

import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class QueryIfProductBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	private final DetalleProducto productDetail;
	private final FridgeControllerAgent myFridgeAgent;

	public QueryIfProductBehaviour(DetalleProducto productDetail) {
		super();
		this.myFridgeAgent = (FridgeControllerAgent) myAgent;

		this.productDetail = productDetail;
	}

	public void action() {
		
		List<AID> publishedServices = myFridgeAgent.getPublishedSupermarkets();
		
		if(!publishedServices.isEmpty()){
		
			AID supermarket = publishedServices.get(0);
			
			try {
				ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
				msg.addReceiver(supermarket);
		
				msg.setLanguage(ProductoOntology.getCodecInstance().getName());
				msg.setOntology(ProductoOntology.ONTOLOGY_NAME);
				
				myAgent.getContentManager().fillContent(msg, productDetail);
				
				myAgent.send(msg);
			
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}
	

}
