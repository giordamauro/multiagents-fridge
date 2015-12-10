package org.unicen.multiagents.supermarket;

import org.unicen.multiagents.ontology.ComprarProducto;
import org.unicen.multiagents.ontology.DetalleProducto;
import org.unicen.multiagents.ontology.ProductoOntology;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public abstract class SupermarketAgent extends Agent {

	private static final long serialVersionUID = 2018654645044400844L;

	protected abstract DetalleProducto getProductSellDetail(DetalleProducto content);
	
	protected abstract boolean agreeOrRefuseProductSell(ComprarProducto content);
	
	protected void setup() {		
		
		getContentManager().registerLanguage(ProductoOntology.getCodecInstance());
		getContentManager().registerOntology(ProductoOntology.getInstance());		
		
		registerInDF();
		
		addBehaviour(new ProductSellerBehaviour());
	}

	private void registerInDF(){
		
		// Registra el servicio en las paginas amarillas
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("ambient-intelligence");
		sd.setName("supermarket");
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			throw new IllegalStateException(fe);
		}
	}
	
	protected void takeDown() {
		// Quita el servicio del DF
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}
