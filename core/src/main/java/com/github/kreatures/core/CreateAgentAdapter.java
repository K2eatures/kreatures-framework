package com.github.kreatures.core;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public class CreateAgentAdapter implements AgentFactory {

	public CreateAgentAdapter() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName) {
		
		return new Agent(addedAgentName,simulationEnvironment);

	}

}
