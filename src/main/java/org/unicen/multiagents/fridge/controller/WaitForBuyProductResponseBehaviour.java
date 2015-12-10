package org.unicen.multiagents.fridge.controller;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForBuyProductResponseBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1567616332013641928L;

	private int exitValue = 1;
	
	public void action() {

		ACLMessage msgInform = myAgent.blockingReceive(MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE), MessageTemplate.MatchPerformative(ACLMessage.REFUSE)));

		exitValue = (msgInform.getPerformative() == ACLMessage.AGREE) ? 1 : 2; 
	}
	
	public int onEnd() {
		return exitValue;
	}
}
