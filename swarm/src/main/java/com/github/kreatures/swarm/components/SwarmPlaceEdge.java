package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

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

	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	public SwarmPlaceEdge(SwarmPlaceEdge other){
		super(other);

		this.firstName=other.firstName;
		this.secondName=other.secondName;
	}
	
	public SwarmPlaceEdge(SwarmPlaceEdgeType other) throws SwarmException {
		super(other);

		if (_UNIQUE_FIRST < numberFirstStation) {
			SwarmPlaceEdge._UNIQUE_FIRST++;
			firstName = firstStationTypeName + SwarmPlaceEdge._UNIQUE_FIRST;
			secondName = secondStationTypeName + SwarmPlaceEdge._UNIQUE_SECOND;
		} else if (_UNIQUE_SECOND < numberSecondStation) {
			SwarmPlaceEdge._UNIQUE_SECOND++;
			SwarmPlaceEdge._UNIQUE_FIRST=1;
			firstName = firstStationTypeName + SwarmPlaceEdge._UNIQUE_FIRST;
			secondName = secondStationTypeName + SwarmPlaceEdge._UNIQUE_SECOND;
		} else {
			_UNIQUE_FIRST=0;
			_UNIQUE_SECOND=1;
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d first component(s) and %d second component(s).",super.getName(),
					numberFirstStation,numberSecondStation),SwarmExceptionType.BREAKS);
		}
	}

	@Override
	public String getName(){
		return String.format("%s:%s", firstName,secondName);
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
	/**
	 * PlacedEdge(StationNameIn,StationTypeNameIn,StationNameOut,StationTypeNameOut,Weight,directed).
	 */
	public String toString() {
		return String.format("PlacedEdge(%s,%s,%s,%s,%d,%s).",firstName,firstStationTypeName,secondName,secondStationTypeName,weight,directed);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */
	public static String getDescriptions() {

		return "%PlacedEdge(StationNameIn,StationTypeNameIn,StationNameOut,StationTypeNameOut,Weight,directed).";
	}
}
