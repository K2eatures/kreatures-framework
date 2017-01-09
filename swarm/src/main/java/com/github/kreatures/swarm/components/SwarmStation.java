package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	protected SwarmStation(SwarmStation other) {
		super(other);
		
		if(_UNIQUE<count){
			SwarmStation._UNIQUE++;
			stationName=name+SwarmStation._UNIQUE;
		}else{
			LOG.error("This station-type %s can't have more than %d station(s).",name,count);
		}
	}
	
	public String getStationName(){
		return stationName;
	}
	public SwarmStation clone(){
		return new SwarmStation(this);
	}

}
