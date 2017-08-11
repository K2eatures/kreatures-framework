package com.github.kreatures.swarm.basic;

/**
 * This list define all the actions which the execution operator
 * can use to determine what it can do.
 * @author Cedric Perez Donfack
 *
 */
public enum MainAction {

	/**
	 * A agent is located at a station and is entering it.
	 */
	ENTER_STATION,
	/**
	 * A agent has visited a station and it is leaving.
	 */
	LEAVE_STATION,
	/**
	 * A agent is visiting a station and can do its jobs on that station.
	 */
	VISIT_STATION,
	/**
	 * A agent is on the way to a station.
	 */
	MOVE,
	/**
	 * A agent is visited a station and takes one or more item(s) 
	 * which will be produced by the station.
	 */
	PRODUCT_ITEM,
	/**
	 * A agent is visited a station and places one or more item(s) 
	 * which will be consumed by the station.
	 */
	CONSUM_ITEM,
	/**
	 * A agent is visited a station and places and takes one or more item(s) 
	 * which will be consumed and produced by the station.
	 */
	PRODUCT_CONSUM_ITEM 
}
