package com.github.kreatures.swarm.components;

import com.github.kreatures.core.serialize.Resource;
import com.github.kreatures.swarm.SwarmConst;


/**
 * This is an Interface of serialized XML-file which will be created by a swarm-scenario.   
 * 
 * 
 * @author donfack
 *
 */

public interface SwarmComponents extends Resource, Comparable<SwarmComponents> {

	public static final String RESOURCE_TYPE = "AbstractSwarm-Configuration";
	public int MAX_INT=SwarmConst.MAX_INT.getValue();
	int UNIT=SwarmConst.UNIT.getValue();
	int ZERO_VALUE=SwarmConst.ZERO_VALUE.getValue();
	
	default public int compareTo(SwarmComponents other){
		return this.getName().compareTo(other.getName());
	}
	
	int getIdentity();
	
 
}