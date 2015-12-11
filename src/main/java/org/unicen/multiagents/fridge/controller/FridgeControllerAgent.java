package org.unicen.multiagents.fridge.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class FridgeControllerAgent extends Agent {

	private static final long serialVersionUID = 2018654645044400844L;
	
	private final List<String> requestedProducts = new ArrayList<>();
	private final Set<AID> supermarketServices = new HashSet<>();

	private ACLMessage subscriptionMessage;
	private Fridge fridge;
	
	@SuppressWarnings("serial")
	protected void setup() {
		
		getContentManager().registerLanguage(ProductoOntology.getCodecInstance());
		getContentManager().registerOntology(ProductoOntology.getInstance());
			
		Fridge fridge = (Fridge) getArguments()[0];
		this.fridge = fridge;
		
		loadSupermarketServices();
		subscribeToSupermarketsDF();
		
		addBehaviour(new TickerBehaviour(this, 3000) {

			protected void onTick() {
				checkExhaustedProducts();
			}
		});
	}
	
	protected void takeDown() {
		
		DFService.createCancelMessage(this, getDefaultDF(), subscriptionMessage);
	}
	
	List<AID> getPublishedSupermarkets(){
		return new ArrayList<AID>(supermarketServices);
	}
	
	Fridge getFridge(){
		return fridge;
	}

	void onBuyProductEnd(ProductBuyerFSMBehaviour buyProductBehaviour){
		
		String product = buyProductBehaviour.getProduct();
		requestedProducts.remove(product);
	}

	private void loadSupermarketServices() {

		DFAgentDescription template = getSupermarketAgentDescription();

		try {
			DFAgentDescription[] result = DFService.search(this, template);
			addSupermarkets(result);
			
		} catch (FIPAException fe) {
			throw new IllegalStateException(fe);
		}
	}

	@SuppressWarnings("serial")
	private ACLMessage subscribeToSupermarketsDF() {
		
		DFAgentDescription template = getSupermarketAgentDescription();
		ACLMessage subscriptionMessage = DFService.createSubscriptionMessage(this, getDefaultDF(), template, null);
		
		addBehaviour(new SubscriptionInitiator(this,subscriptionMessage) {
			protected void handleInform(ACLMessage inform) {
				try {
					DFAgentDescription[] result = DFService.decodeNotification(inform.getContent());
					addSupermarkets(result);
					
				} catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		});
		
		return subscriptionMessage;
	}
	
	private void addSupermarkets(DFAgentDescription[] result){
	
		for (DFAgentDescription description : result) {
			
			AID agentId = description.getName();
			supermarketServices.add(agentId);
		}
	}
	
	private void checkExhaustedProducts() {
				
		List<String> products = fridge.getProducts();
		
		for(String product : products){

			if(!requestedProducts.contains(product)){
			
				float productStock = fridge.getProductStock(product);
				System.out.println(String.format("Checking stock for %s - Stock: %s", product, productStock));
				
				if(productStock == 0){
					requestedProducts.add(product);
					initProductBuyConversation(product, 10);
				}
			}
		}
	}
		
	private void initProductBuyConversation(String product, float amount) {
		
		System.out.println(String.format("Initiating buy conversation for %s - Amount: %s", product, amount));
		
		addBehaviour(new ProductBuyerFSMBehaviour(fridge, product, amount));
	}
	
	private static DFAgentDescription getSupermarketAgentDescription(){
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("ambient-intelligence");
		sd.setName("supermarket");
		template.addServices(sd);
		
		return template;
	}
}