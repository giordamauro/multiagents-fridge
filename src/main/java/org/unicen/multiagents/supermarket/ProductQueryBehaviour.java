package org.unicen.multiagents.supermarket;

import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProductQueryBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = -20707675473742576L;

	public void action() {
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF));
		if (msg != null) {
	
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
	}
}
