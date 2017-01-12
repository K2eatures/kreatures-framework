/**
 * 
 */
package com.github.kreatures.swarm.components;

import com.github.kreatures.swarm.exceptions.SwarmException;

/**
 * @author donfack
 *
 */
public class NecAgentStation implements SwarmComponents {
	
	private String agentName;
	private String stationName;
	private int countNec;
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	public NecAgentStation(NecAgentStation other) {
		this.agentName=other.agentName;
		this.stationName=other.stationName;
		this.countNec=other.countNec;
	}
	
	

	public int getCountNec() {
		return countNec;
	}



	public void setCountNec(int countNec) {
		this.countNec = countNec;
	}



	public String getAgentName() {
		return agentName;
	}



	public String getStationName() {
		return stationName;
	}



	/**
	 * @see com.github.kreatures.core.serialize.Resource#getName()
	 */
	@Override
	public String getName() {
		return String.format("Nec:%s:%S", agentName,stationName);
	}

	/**
	 * @see com.github.kreatures.core.serialize.Resource#getDescription()
	 */
	@Override
	public String getDescription() {
		return String.format("count the number of visit between %s and %s",agentName,stationName);
	}

	/** 
	 * @see com.github.kreatures.core.serialize.Resource#getResourceType()
	 */
	@Override
	public String getResourceType() {
		return RESOURCE_TYPE;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.core.serialize.Resource#getCategory()
	 */
	@Override
	public String getCategory() {

		return "AbstractSwarm: count of necessity.";
	}
	
	public NecAgentStation(SwarmVisitEdge visitEdge)throws SwarmException{
		if (visitEdge == null)
			throw new SwarmException("the given argument has to be no null.");
		
		this.agentName=visitEdge.getAgentName();
		this.stationName=visitEdge.getStationName();
		this.countNec=0;
	}
	@Override	
	public NecAgentStation clone(){
		return new NecAgentStation(this);
	}
	/**
	 * NecAgentStation(AgentName,StationName,Nec). 
	 */
	public String toString(){
		return String.format("NecAgentStation(%s,%s,%d). ", agentName,stationName,countNec);
	}
}
