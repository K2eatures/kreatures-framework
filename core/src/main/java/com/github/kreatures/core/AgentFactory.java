package com.github.kreatures.core;

/**
 * This factory is used to create a concrete AgentAbstract instance.
 * 
 * @author Cedric Perez Donfack
 * @see CreateAgentAdapter a concrete implementation class.
 * @see CreateAgent a concrete implementation class.  
 */
public interface AgentFactory {
	/**
	 * is called when the given agent's name have to be created and added to the simulation-environment
	 * @param simulationEnvironment environment where the simulation running 
	 * @param addedAgentName the name of the current added agent 
	 * @return instance object of the created agent.
	 */
	AgentAbstract createAgent(KReaturesEnvironment simulationEnvironment, String addedAgentName);
}
