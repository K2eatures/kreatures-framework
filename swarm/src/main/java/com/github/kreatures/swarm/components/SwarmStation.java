package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * 
 * @author donfack
 * SwarmStation is the basis class and allows to create new generic station.
 *
 */


public class SwarmStation extends SwarmStationType{
	private static final Logger LOG = LoggerFactory.getLogger(SwarmStation.class);
	/**
	 * this attribute is used in order to always have a unique name for a given station type.  
	 */
	private static int _UNIQUE=0;
	
	private String stationName;
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmStation(SwarmStation other) {
		super(other);
		this.stationName=other.stationName;
		
	}
	
	protected SwarmStation(SwarmStationType other) throws SwarmException {
		super(other);
		
		if(_UNIQUE<count){
			SwarmStation._UNIQUE++;
			stationName=name+SwarmStation._UNIQUE;
		}else{
			_UNIQUE=0;
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d Stations(s).",super.getName(),
					count),SwarmExceptionType.BREAKS);
		}
	}
	@Override
	public String getName(){
		return stationName;
	}
	
	@Override
	public SwarmStation clone(){
		return new SwarmStation(this);
	}
	/**
	 * Station(StationName,StattionType,freq,nec,space).
	 */
	public String toString() {
		return String.format("Station(%s,%s,%d,%d,%d).",getName(), getStationTypeName(),frequency,necessity,space);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */
	public static String getDescriptions() {

		return "%Station(StationName,StattionType,freq,nec,space).";
	}
}
