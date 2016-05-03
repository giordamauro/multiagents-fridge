package org.unicen.multiagents.supermarket;

import org.unicen.multiagents.ontology.ComprarProducto;

import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProductRequestBehaviour extends CyclicBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	public void action() {
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msg != null) {
			
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
}
