package org.unicen.multiagents.main;

import org.unicen.multiagents.fridge.Fridge;

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

		AgentController agentController3 = containerController.createNewAgent("FridgeAgent", "org.unicen.multiagents.fridge.controller.FridgeControllerAgent", arguments);
		agentController3.start();

		AgentController agentController4 = containerController.createNewAgent("Supermarket", "org.unicen.multiagents.supermarket.SupermarketAgent", null);
		agentController4.start();
		
		// TODO: attach sniffer agent(shows graphics) and dummy Agent to send
		// messages
	}
}
