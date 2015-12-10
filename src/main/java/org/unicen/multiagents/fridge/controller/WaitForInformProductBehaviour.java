package org.unicen.multiagents.fridge.controller;

import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForInformProductBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1567616332013641928L;

	private final DetalleProducto productBuyDetail;
	private int exitValue = 1;
	
	public WaitForInformProductBehaviour(DetalleProducto productBuyDetail) {
		this.productBuyDetail = productBuyDetail;
	}

	public void action() {

		try{
			ACLMessage msgInform = myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			
			DetalleProducto informContent = (DetalleProducto) myAgent.getContentManager().extractContent(msgInform);
			
			System.out.println(String.format("Received INFORM: %s", informContent));

			exitValue = (productBuyDetail.getCantidad() <= informContent.getCantidad()) ? 1 : 2;
		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
	public int onEnd() {
		return exitValue;
	}
}
