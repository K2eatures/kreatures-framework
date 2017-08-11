package com.github.kreatures.swarm.basic;

public enum MainDesire {
	/**
	 * This desire is used, when a agent want to 
	 * know all the possible next stations.
	 */
	STATION_CHOICE,
	/**
	 * This desire is used, when a agent has selected a station
	 * and the station has a item.
	 */
	VISIT_WITH_ITEM,
	/**
	 * This desire is used, when a agent has selected a station
	 * and the station has no a item.
	 */
	VISIT_WITHOUT_ITEM,
	/**
	 * This desire is used, when a agent want to move to a next
	 * station.
	 */
	MOVE_TO_STATION

}
