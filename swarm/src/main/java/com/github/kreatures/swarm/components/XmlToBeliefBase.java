/**
 * 
 */
package com.github.kreatures.swarm.components;

import java.util.Collection;
/**
 * @author donfack
 *
 */
public interface XmlToBeliefBase {

	String getTimeUnit();
	String getName();
	String getDescription();
	
	Collection<SwarmAgentType> getAllAgentType();
	Collection<SwarmAgent> getAllAgents();
	
	Collection<SwarmStationType> getAllStationType();
	Collection<SwarmStation> getAllStations ();
	
	//Set<SwarmPlaceEdgeType> getAllPlaceEdgeType();
	Collection<SwarmPlaceEdge> getAllPlaceEdge();
	
	//Set<SwarmVisitEdgeType> getAllVisitEdgeType();
	Collection<SwarmVisitEdge> getAllVisitEdge();
	
	//Set<SwarmTimeEdgeType> getAllTimeEdgeType();
	Collection<SwarmTimeEdge> getAllTimeEdge();
	
	Collection<NecAgentStation> getAllNecAgentStation();
	
	Collection<ItemSetLoadingAgent> getAllItemSetLoadingAgent();
	Collection<ItemSetLoadingStation> getAllItemSetLoadingStation();
	
	Collection<TimeEdgeState> getAllTimeEdgeState();
}
