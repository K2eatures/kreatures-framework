package com.github.kreatures.core;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public interface AgentFactory {
	/** is called when the given agent's name have to be created and added to the simulation-environment */
	AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName);
}
