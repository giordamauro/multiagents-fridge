package org.unicen.multiagents.fridge.controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BuyLowestPriceBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	private boolean supermarketAgree;
	private SupermarketProductDetail productDetail;
	
	public void action() {

		DataStore dataStore = getDataStore();
		
		@SuppressWarnings("unchecked")
		List<SupermarketProductDetail> supermarkets = (List<SupermarketProductDetail>) dataStore.get(QuerySupermarketsState.SUPERMARKETS_LIST_READY.name());
		Collections.sort(supermarkets);
		
		supermarketAgree = false;
		int i = 0;
		while(!supermarketAgree && i < supermarkets.size()){
		
			productDetail = supermarkets.get(i);
			DetalleProducto detalleProducto = productDetail.getDetalleProducto();
			
			float montoTotal = detalleProducto.getPrecio() * detalleProducto.getCantidad();
			productDetail.setMontoTotal(montoTotal);
			
			ComprarProducto comprarProducto = new ComprarProducto();
			comprarProducto.setProducto(detalleProducto.getProducto());
			comprarProducto.setCantidad(detalleProducto.getCantidad());
			comprarProducto.setMontoTotal(montoTotal);

			UUID conversationId = sendProductBuyRequest(productDetail.getSupermarketAID(), comprarProducto);
			supermarketAgree = getSupermarketAgree(conversationId);
			
			i++;
		}
	}	
	
	public int onEnd() {
		
		BuyLowestPriceState state = BuyLowestPriceState.NOTHING_TO_BUY;
		
		if(supermarketAgree){
			
			state = BuyLowestPriceState.PRODUCT_BUY_AGREE;

			DataStore dataStore = getDataStore();
			dataStore.put(state.name(), productDetail);
		}
		
		return state.value();
	}
	
	private UUID sendProductBuyRequest(AID supermarket, ComprarProducto productDetail){
	
		UUID conversationId = UUID.randomUUID();
		
		try {
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setConversationId(conversationId.toString());
			msg.addReceiver(supermarket);
			
			msg.setLanguage(ProductoOntology.getCodecInstance().getName());
			msg.setOntology(ProductoOntology.ONTOLOGY_NAME);

			Action requestAction = new Action();
			requestAction.setActor(supermarket);
			requestAction.setAction(productDetail);
			
			myAgent.getContentManager().fillContent(msg, requestAction);
			myAgent.send(msg);
		
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return conversationId;
	}
	
	private boolean getSupermarketAgree(UUID conversationId) {

		ACLMessage msgInform = myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId.toString()), MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE), MessageTemplate.MatchPerformative(ACLMessage.REFUSE))));

		return msgInform.getPerformative() == ACLMessage.AGREE; 
	}
}
