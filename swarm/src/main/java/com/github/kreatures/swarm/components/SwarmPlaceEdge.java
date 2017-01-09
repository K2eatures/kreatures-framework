package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwarmPlaceEdge extends SwarmPlaceEdgeType {
	private static final Logger LOG = LoggerFactory
			.getLogger(SwarmPlaceEdge.class);
	/**
	 * this attribute is used in order to always have a unique map key <first station name, second station name> for a given
	 * place-edge type.
	 */
	private static int _UNIQUE_FIRST = 0;
	private static int _UNIQUE_SECOND = 1;

	private String firstName;
	private String secondName;

	public SwarmPlaceEdge(SwarmPlaceEdgeType other) {
		super(other);

		if (_UNIQUE_FIRST < numberFirstStation) {
			SwarmPlaceEdge._UNIQUE_FIRST++;
			firstName = firstStationTypeName + SwarmPlaceEdge._UNIQUE_FIRST;
			secondName = secondStationTypeName + SwarmPlaceEdge._UNIQUE_SECOND;
		} else if (_UNIQUE_SECOND <= numberSecondStation) {
			SwarmPlaceEdge._UNIQUE_SECOND++;
		} else {

			LOG.error("This place-edge with than %d incomming station(s) and %d outgoing station(s).",
					numberFirstStation,numberSecondStation);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}
	
	public SwarmPlaceEdge clone(){
		return new SwarmPlaceEdge(this);
	}

}
