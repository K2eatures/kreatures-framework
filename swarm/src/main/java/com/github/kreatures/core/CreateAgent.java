package com.github.kreatures.core;

public class CreateAgent implements AgentFactory {

	public CreateAgent() {}

	@Override
	public AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName) {
		
		return new NewAgent(addedAgentName,simulationEnvironment);
	}

}
