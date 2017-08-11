package com.github.kreatures.core;

/**
 * This is used to create a concrete instance object of {@link AgentAbstract}.
 * This is hereby a instance of {@link NewAgent}
 * @author Cedric Perez Donfack
 *
 */
public class CreateAgent implements AgentFactory {
	/** Default Ctor: */
	public CreateAgent() {}

	@Override
	public AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName) {
		
		return new NewAgent(addedAgentName,simulationEnvironment);
	}

}
