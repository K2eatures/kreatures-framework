package com.github.kreatures.swarm.components;

import com.github.kreatures.core.serialize.Resource;


/**
 * This is an Interface of serialized XML-file which will be created by a swarm-scenario.   
 * 
 * 
 * @author donfack
 *
 */

public interface SwarmConfig extends Resource {
	public static final String RESOURCE_TYPE = "AbstractSwarm-Configuration";
	int MAX_INT=Integer.MAX_VALUE;
	int UNIT=1;
	int ZERO_VALUE=0;
 
}