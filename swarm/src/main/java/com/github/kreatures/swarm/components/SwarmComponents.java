package com.github.kreatures.swarm.components;

import com.github.kreatures.core.serialize.Resource;


/**
 * This is an Interface of serialized XML-file which will be created by a swarm-scenario.   
 * 
 * 
 * @author donfack
 *
 */

public interface SwarmComponents extends Resource, Comparable<SwarmComponents> {

	public static final String RESOURCE_TYPE = "AbstractSwarm-Configuration";
	int MAX_INT=100;
	int UNIT=1;
	int ZERO_VALUE=0;
	
	default public int compareTo(SwarmComponents other){
		return this.getName().compareTo(other.getName());
	}
	
	int getIdentity();
	
 
}