package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public SwarmVisitEdge(SwarmVisitEdgeType other) {
		super(other);
		
		if (_UNIQUE_AGENT < numberAgent) {
			SwarmVisitEdge._UNIQUE_AGENT++;
			agentName = agentTypeName + SwarmVisitEdge._UNIQUE_AGENT;
			stationName = stationTypeName + SwarmVisitEdge._UNIQUE_STATION;
		} else if (_UNIQUE_STATION <= numberStation) {
			SwarmVisitEdge._UNIQUE_STATION++;
		} else {

			LOG.error("This place-edge with than %d incomming station(s) and %d outgoing station(s).",
					numberAgent,numberStation);
		}
	}

	public String getAgentName() {
		return agentName;
	}

	public String getStationName() {
		return stationName;
	}
	
	public SwarmVisitEdge clone(){
		return new SwarmVisitEdge(this);
	}

}
