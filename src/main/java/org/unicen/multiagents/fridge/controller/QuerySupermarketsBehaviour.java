package org.unicen.multiagents.fridge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.core.AID;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class QuerySupermarketsBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 4098778181086476506L;

	private final DetalleProducto productDetail;
	
	private final List<SupermarketProductDetail> possibleSupermarkets = new ArrayList<>();

	public QuerySupermarketsBehaviour(DetalleProducto productDetail) {
		super();
		
		this.productDetail = productDetail;
	}

	public void action() {
		
		FridgeControllerAgent myFridgeAgent = (FridgeControllerAgent) myAgent;
		List<AID> publishedServices = myFridgeAgent.getPublishedSupermarkets();
		
		for(AID supermarket : publishedServices){
		
			try {
				UUID conversationId = sendQueryIfMessage(supermarket);
				DetalleProducto supermarketInform = getSupermarketInform(conversationId);
			
				if(supermarketInform.getCantidad() >= productDetail.getCantidad()){
				
					supermarketInform.setCantidad(productDetail.getCantidad());
					SupermarketProductDetail supermarketProductDetail = new SupermarketProductDetail(supermarket, supermarketInform);
					possibleSupermarkets.add(supermarketProductDetail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int onEnd() {

		QuerySupermarketsState state = QuerySupermarketsState.NOTHING_TO_BUY;
		
		if(!possibleSupermarkets.isEmpty()){
			
			state = QuerySupermarketsState.SUPERMARKETS_LIST_READY;
			
			DataStore dataStore = super.getDataStore();	
			dataStore.put(state.name(), possibleSupermarkets);
		}
		
		return state.value();
	}
	
	private UUID sendQueryIfMessage(AID supermarket){
	
		UUID conversationId = UUID.randomUUID();

		try {
			ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
			msg.setConversationId(conversationId.toString());
			msg.addReceiver(supermarket);
	
			msg.setLanguage(ProductoOntology.getCodecInstance().getName());
			msg.setOntology(ProductoOntology.ONTOLOGY_NAME);
			
			myAgent.getContentManager().fillContent(msg, productDetail);
			
			myAgent.send(msg);
		
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		
		return conversationId;
	}

	private DetalleProducto getSupermarketInform(UUID conversationId){
		
		try{
			ACLMessage msgInform = myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchConversationId(conversationId.toString()), MessageTemplate.MatchPerformative(ACLMessage.INFORM)));
			
			DetalleProducto informContent = (DetalleProducto) myAgent.getContentManager().extractContent(msgInform);
			
			System.out.println(String.format("Received INFORM: %s", informContent));
			
			return informContent;
		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
}
