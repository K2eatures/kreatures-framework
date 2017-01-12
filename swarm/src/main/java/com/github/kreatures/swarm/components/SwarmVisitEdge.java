package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 *TODO
 * @author donfack
 *
 */
public class SwarmVisitEdge extends SwarmVisitEdgeType {
	private static final Logger LOG = LoggerFactory
			.getLogger(SwarmPlaceEdge.class);
	/**
	 * this attribute is used in order to always have a unique map key < agent name, station name> for a given visit-edge type.
	 */
	private static int _UNIQUE_AGENT = 0;
	private static int _UNIQUE_STATION = 1;

	private String agentName;
	private String stationName;
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	public SwarmVisitEdge(SwarmVisitEdge other) {
		super(other);
		this.agentName=other.agentName;
		this.stationName=other.stationName;
	}
	
	public SwarmVisitEdge(SwarmVisitEdgeType other) throws SwarmException{
		super(other);
		
		if (_UNIQUE_AGENT < numberAgent) {
			SwarmVisitEdge._UNIQUE_AGENT++;
			agentName = agentTypeName + SwarmVisitEdge._UNIQUE_AGENT;
			stationName = stationTypeName + SwarmVisitEdge._UNIQUE_STATION;
		} else if (_UNIQUE_STATION <= numberStation) {
			SwarmVisitEdge._UNIQUE_STATION++;
		} else {
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d first component(s) and %d second component(s).",super.getName(),
					numberAgent,numberStation),SwarmExceptionType.BREAKS);
		}
	}
	
	
	@Override
	public String getName(){
		return String.format("%s:%s", agentName,stationName);
	}
	
	public String getAgentName() {
		return agentName;
	}

	public String getStationName() {
		return stationName;
	}
	
	@Override
	public SwarmVisitEdge clone(){
		return new SwarmVisitEdge(this);
	}
	
	/**
	 * VisitEdge(agentName,AgentTypeName,StationName,StationTypeName,bold).
	 */
	public String toString() {
		return String.format("VisitEdge(%s,%s,%s,%s,%s).",agentName, getAgentTypeName(), stationName,getStationTypeName(),bold);
	}
}
