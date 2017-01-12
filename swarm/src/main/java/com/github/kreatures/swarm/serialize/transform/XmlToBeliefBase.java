/**
 * 
 */
package com.github.kreatures.swarm.serialize.transform;

import com.github.kreatures.swarm.serialize.SwarmAgentTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmPlaceEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmStationTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmTimeEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmVisitEdgeConfig;
import com.github.kreatures.swarm.components.ItemSetLoadingAgent;
import com.github.kreatures.swarm.components.ItemSetLoadingStation;
import com.github.kreatures.swarm.components.NecAgentStation;
import com.github.kreatures.swarm.components.SwarmAgent;
import com.github.kreatures.swarm.components.SwarmAgentType;
import com.github.kreatures.swarm.components.SwarmStationType;
import com.github.kreatures.swarm.components.SwarmStation;
import com.github.kreatures.swarm.components.SwarmPlaceEdgeType;
import com.github.kreatures.swarm.components.SwarmPlaceEdge;
import com.github.kreatures.swarm.components.SwarmVisitEdgeType;
import com.github.kreatures.swarm.components.TimeEdgeState;
import com.github.kreatures.swarm.components.SwarmVisitEdge;
import com.github.kreatures.swarm.components.SwarmTimeEdgeType;
import com.github.kreatures.swarm.components.SwarmTimeEdge;


import java.util.Set;
import java.util.List;
/**
 * @author donfack
 *
 */
public interface XmlToBeliefBase {

	String getTimeUnit();
	String getName();
	String getDescription();
	
	Set<SwarmAgentType> getAgentType(List<SwarmAgentTypeConfig> list);
	Set<SwarmAgent> getAgens(Set<SwarmAgentType> set);
	
	Set<SwarmStationType> getStationType(List<SwarmStationTypeConfig> list);
	Set<SwarmStation> getStations (Set<SwarmStationType> set);
	
	Set<SwarmPlaceEdgeType> getPlaceEdgeType(List<SwarmPlaceEdgeConfig> list);
	Set<SwarmPlaceEdge> getPlaceEdge(Set<SwarmPlaceEdgeType> set);
	
	Set<SwarmVisitEdgeType> getVisitEdgeType(List<SwarmVisitEdgeConfig> list);
	Set<SwarmVisitEdge> getVisitEdge(Set<SwarmVisitEdge> set);
	
	Set<SwarmTimeEdgeType> getTimeEdgeType(List<SwarmTimeEdgeConfig> list);
	Set<SwarmTimeEdge> getTimeEdge(Set<SwarmTimeEdgeType> set);
	
	Set<NecAgentStation> getNecAgentStation(Set<SwarmVisitEdge> set);
	
	Set<ItemSetLoadingAgent> getItemSetLoadingAgent(Set<SwarmVisitEdgeType> set);
	Set<ItemSetLoadingStation> getItemSetLoadingStation(Set<SwarmPlaceEdge> set);
	
	Set<TimeEdgeState> getTimeEdgeState(Set<SwarmTimeEdge> set);
}
