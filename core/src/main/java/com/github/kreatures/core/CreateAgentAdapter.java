package com.github.kreatures.core;

/**
 * This is used to create a concrete instance object of {@link AgentAbstract}.
 * This is hereby a instance of {@link Agent}
 *@author Cedric Perez Donfack
 *@see AgentFactory
 */
public class CreateAgentAdapter implements AgentFactory {
	/** Default Ctor */
	public CreateAgentAdapter() {}

	
	@Override
	public AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName) {
		
		return new Agent(addedAgentName,simulationEnvironment);

	}

}
