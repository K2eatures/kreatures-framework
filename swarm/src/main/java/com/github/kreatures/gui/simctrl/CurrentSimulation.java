package com.github.kreatures.gui.simctrl;

/**
 * 
 * @author donfack
 *
 */

public class CurrentSimulation {
	/**
	 * name of current Simulation 
	 */
	private static String name;
	
	/**
	 * Count the total waiting time of all agents in the current simulation 
	 * 
	 */
	private static int totalWaitTime=0;
	
	public static String getName() {
		return name;
	}

	protected static void setName(String name) {
		CurrentSimulation.name = name;
	}

	/**
	 * @return the totalWaitTime
	 */
	public static int getTotalWaitTime() {
		return totalWaitTime;
	}

	/**
	 * increasing the total waiting time of one
	 * 
	 */
	public static void incrTotalWaitTime() {
		CurrentSimulation.totalWaitTime++;
	}
	
	/**
	 *  increasing the total waiting time of the given time
	 * @param totalWaitTime the totalWaitTime to set
	 */
	public static void incrTotalWaitTime(int totalWaitTime) {
		CurrentSimulation.totalWaitTime+=totalWaitTime;
	}
	

}
