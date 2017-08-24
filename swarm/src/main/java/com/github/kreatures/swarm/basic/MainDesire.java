package com.github.kreatures.swarm.basic;

/**
 * This helps a agent to know its state in order to 
 * prepare its actions and send it to the other agents.
 *  
 * @author Cedric Perez Donfack
 *
 */
public enum MainDesire {
	/**
	 * This desire is used, when a agent want to 
	 * know all the possible next stations.
	 */
	CHOSE_STATION,
	/**
	 * This desire is used, when a agent has selected a station,
	 * it has reached and want to visit it.
	 */
	VISIT,
	/**
	 * This desire is used, when a agent move to a next
	 * station.
	 */
	MOVE,
	/**
	 * This desire is used, when a agent cannot enter the station and has to wait a minute.
	 */
	WAIT
}
