package org.unicen.multiagents.main;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class RemoteConnectionMain {

	public static void main(String[] args) {

		Runtime runtime = jade.core.Runtime.instance();
		
		Profile p = new ProfileImpl();
		p.setParameter(Profile.MAIN_HOST, "192.168.5.123");

		//Default port:
//		p.setParameter(Profile.MAIN_PORT, "1099");

		ContainerController containerController = runtime.createAgentContainer(p);

		try {
			containerController.createNewAgent("MGIORDA", "org.unicen.multiagents.MiAgente", null);

		} catch (StaleProxyException e) {

			throw new IllegalStateException(e);
		}
	}

}
