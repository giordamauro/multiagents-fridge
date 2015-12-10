package org.unicen.multiagents.supermarket;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProductSellerBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	public void action() {
		ACLMessage msg = myAgent
				.blockingReceive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF),
						MessageTemplate.MatchPerformative(ACLMessage.REQUEST)));

		if (msg.getPerformative() == ACLMessage.QUERY_IF) {
			handleQueryIf(msg);
		} else {
			handleRequest(msg);
		}
	}

	private void handleQueryIf(ACLMessage msg) {

		try {
			DetalleProducto content = (DetalleProducto) myAgent.getContentManager().extractContent(msg);
			
			System.out.println(String.format("Received QUERY_IF message with content '%s'", content));

			DetalleProducto productSellDetail = ((SupermarketAgent)myAgent).getProductSellDetail(content);

			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setConversationId(msg.getConversationId());

			reply.setLanguage(ProductoOntology.getCodecInstance().getName());
			reply.setOntology(ProductoOntology.ONTOLOGY_NAME);
	
			myAgent.getContentManager().fillContent(reply, productSellDetail);

			myAgent.send(reply);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	private void handleRequest(ACLMessage msg) {

		try {
			Action requestAction = (Action) myAgent.getContentManager().extractContent(msg);
			ComprarProducto content = (ComprarProducto) requestAction.getAction();
			
			System.out.println(String.format("Received REQUEST message with content '%s'", content));
			
			boolean agreeOrRefuse = ((SupermarketAgent)myAgent).agreeOrRefuseProductSell(content);
			
			ACLMessage reply = msg.createReply();
			if(agreeOrRefuse){
				reply.setPerformative(ACLMessage.AGREE);
			}
			else{
				reply.setPerformative(ACLMessage.REFUSE);
			}
			reply.setConversationId(msg.getConversationId());
		
			myAgent.send(reply);
			
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
