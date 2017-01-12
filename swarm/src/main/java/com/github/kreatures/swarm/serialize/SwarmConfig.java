package com.github.kreatures.swarm.serialize;

import java.util.List;

/**
 * This is a Factory that allows to create a instance of SwarmConfigRead given a Path object. 
 * This is an Interface of serialized XML-file which will be created by a swarm-scenario.   
 * 
 * 
 * @author donfack
 *
 */

public interface SwarmConfig{
	public List<SwarmPerspectiveConfig> getListPerspective();

	public List<SwarmVisitEdgeConfig> getListVisitEdge();

	public List<SwarmTimeEdgeConfig> getListTimeEdge();

	public List<SwarmPlaceEdgeConfig> getListPlaceEdge();
}