package org.unicen.multiagents.fridge.controller;

import java.util.ArrayList;
import java.util.List;

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
	private final FridgeControllerAgent myFridgeAgent;
	
	private final List<SupermarketProductDetail> possibleSupermarkets = new ArrayList<>();

	public QuerySupermarketsBehaviour(DetalleProducto productDetail) {
		super();
		this.myFridgeAgent = (FridgeControllerAgent) myAgent;

		this.productDetail = productDetail;
	}

	public void action() {
		
		List<AID> publishedServices = myFridgeAgent.getPublishedSupermarkets();
		
		for(AID supermarket : publishedServices){
		
			try {
				sendQueryIfMessage(supermarket);
				DetalleProducto supermarketInform = getSupermarketInform();
			
				if(supermarketInform.getCantidad() >= productDetail.getCantidad()){
				
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
	
	private void sendQueryIfMessage(AID supermarket){
	
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

	private DetalleProducto getSupermarketInform(){
		
		try{
			ACLMessage msgInform = myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
			
			DetalleProducto informContent = (DetalleProducto) myAgent.getContentManager().extractContent(msgInform);
			
			System.out.println(String.format("Received INFORM: %s", informContent));
			
			return informContent;
		}
		catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
}
