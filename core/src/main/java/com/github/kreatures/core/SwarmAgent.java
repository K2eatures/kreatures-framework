package com.github.kreatures.core;

/**
 * This interface will be implemented by a agent of the swarm Project.
 * It gives the possibility that a method which will be written by a agent in the swarm project, can also be 
 * visible in all other project 
 * @author Cedric Perez Donfack
 *
 */

public interface SwarmAgent {

	/**
	 * reference to information which belong to the controller {@link BaseBeliefbase}
	 * @return the controller {@link BaseBeliefbase} of this agent.
	 */
	BaseBeliefbase getControllerBB();
	/**
	 * reference to information which belong to the world {@link BaseBeliefbase}
	 * @return the world {@link BaseBeliefbase} of this agent.
	 */
	BaseBeliefbase getWorldBB();
	
	/**
	 * reference to information which belong to the controller {@link BaseBeliefbase}
	 * @param controllerBB is the actual controller {@link BaseBeliefbase} of this agent.
	 */
	void setControllerBB(BaseBeliefbase controllerBB);
	/**
	 * reference to information which belong to the world {@link BaseBeliefbase}
	 * @param worldBB is the actual world {@link BaseBeliefbase} of this agent.
	 */
	void setWorldBB(BaseBeliefbase worldBB);
}
