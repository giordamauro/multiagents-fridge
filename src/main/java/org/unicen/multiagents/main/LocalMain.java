package org.unicen.multiagents.main;

import org.unicen.multiagents.fridge.Fridge;
import org.unicen.multiagents.ontology.DetalleProducto;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class LocalMain {

	public static void main(String[] args) throws StaleProxyException {

		Fridge fridge = new Fridge();
		fridge.addProduct("EGG", 0);//24);
		fridge.addProduct("MILK", 0);//5);

		// Start agent platform:

		Runtime runtime = jade.core.Runtime.instance();
		Profile profile = new ProfileImpl();
		ContainerController containerController = runtime.createMainContainer(profile);

		AgentController agentController = containerController.createNewAgent("RMA", "jade.tools.rma.rma", null);
		agentController.start();
		
		Object[] arguments = new Object[] {fridge};
		AgentController agentController2 = containerController.createNewAgent("ConsumerAgent", "org.unicen.multiagents.fridge.FridgeConsumerAgent", arguments);
		agentController2.start();

		//-------------------- Supermercados
		
		DetalleProducto egg1 = new DetalleProducto();
		egg1.setProducto("EGG");
		egg1.setCantidad(10000);
		egg1.setPrecio(1);

		DetalleProducto milk1 = new DetalleProducto();
		milk1.setProducto("MILK");
		milk1.setCantidad(10000);
		milk1.setPrecio(2);

		AgentController agentController4 = containerController.createNewAgent("Supermarket1", "org.unicen.multiagents.supermarket.StockSupermarketAgent", new Object[] {egg1, milk1});
		agentController4.start();

		DetalleProducto egg2 = new DetalleProducto();
		egg2.setProducto("EGG");
		egg2.setCantidad(10000);
		egg2.setPrecio(2);

		DetalleProducto milk2 = new DetalleProducto();
		milk2.setProducto("MILK");
		milk2.setCantidad(10000);
		milk2.setPrecio(1);

		AgentController agentController5 = containerController.createNewAgent("Supermarket2", "org.unicen.multiagents.supermarket.StockSupermarketAgent", new Object[] {egg2, milk2});
		agentController5.start();

		AgentController agentController6 = containerController.createNewAgent("Supermarket3", "org.unicen.multiagents.supermarket.RefuseAlwaysSupermarketAgent", null);
		agentController6.start();
		
		//-- Fridge controller
		
		AgentController agentController3 = containerController.createNewAgent("FridgeAgent", "org.unicen.multiagents.fridge.controller.FridgeControllerAgent", arguments);
		agentController3.start();

	}
}
