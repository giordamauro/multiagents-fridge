package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestBuyProductBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	private final ComprarProducto productDetail;

	public RequestBuyProductBehaviour(ComprarProducto productDetail) {
		this.productDetail = productDetail;
	}

	public void action() {

		try {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			AID receiver = new AID("Supermarket", AID.ISLOCALNAME);
			msg.addReceiver(receiver);
			
			msg.setLanguage(ProductoOntology.getCodecInstance().getName());
			msg.setOntology(ProductoOntology.ONTOLOGY_NAME);

			Action requestAction = new Action();
			requestAction.setActor(receiver);
			requestAction.setAction(productDetail);
			
			myAgent.getContentManager().fillContent(msg, requestAction);
			myAgent.send(msg);
		
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
