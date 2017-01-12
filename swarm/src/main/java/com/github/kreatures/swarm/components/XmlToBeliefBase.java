/**
 * 
 */
package com.github.kreatures.swarm.components;

import java.util.Set;
/**
 * @author donfack
 *
 */
public interface XmlToBeliefBase {

	int getTimeUnit();
	String getName();
	String getDescription();
	
	Set<SwarmAgentType> getAllAgentType();
	Set<SwarmAgent> getAllAgents();
	
	Set<SwarmStationType> getAllStationType();
	Set<SwarmStation> getAllStations ();
	
	Set<SwarmPlaceEdgeType> getAllPlaceEdgeType();
	Set<SwarmPlaceEdge> getAllPlaceEdge();
	
	Set<SwarmVisitEdgeType> getAllVisitEdgeType();
	Set<SwarmVisitEdge> getAllVisitEdge();
	
	Set<SwarmTimeEdgeType> getAllTimeEdgeType();
	Set<SwarmTimeEdge> getAllTimeEdge();
	
	Set<NecAgentStation> getAllNecAgentStation();
	
	Set<ItemSetLoadingAgent> getAllItemSetLoadingAgent();
	Set<ItemSetLoadingStation> getAllItemSetLoadingStation();
	
	Set<TimeEdgeState> getAllTimeEdgeState();
}
