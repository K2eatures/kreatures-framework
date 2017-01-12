/**
 * 
 */
package com.github.kreatures.swarm.serialize.transform;

import java.util.List;
import java.util.Set;

import com.github.kreatures.swarm.components.ItemSetLoadingAgent;
import com.github.kreatures.swarm.components.ItemSetLoadingStation;
import com.github.kreatures.swarm.components.NecAgentStation;
import com.github.kreatures.swarm.components.SwarmAgent;
import com.github.kreatures.swarm.components.SwarmAgentType;
import com.github.kreatures.swarm.components.SwarmPlaceEdge;
import com.github.kreatures.swarm.components.SwarmPlaceEdgeType;
import com.github.kreatures.swarm.components.SwarmStation;
import com.github.kreatures.swarm.components.SwarmStationType;
import com.github.kreatures.swarm.components.SwarmTimeEdge;
import com.github.kreatures.swarm.components.SwarmTimeEdgeType;
import com.github.kreatures.swarm.components.SwarmVisitEdge;
import com.github.kreatures.swarm.components.SwarmVisitEdgeType;
import com.github.kreatures.swarm.components.TimeEdgeState;
import com.github.kreatures.swarm.serialize.SwarmAgentTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmConfigRead;
import com.github.kreatures.swarm.serialize.SwarmPlaceEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmStationTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmTimeEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmVisitEdgeConfig;

/**
 * @author donfack
 *
 */
public class BeliefParseOfSwarm implements XmlToBeliefBase {
	
	/**
	 * 
	 */
	public BeliefParseOfSwarm(SwarmConfigRead xmlLogisticsGraph) {
		//TODO
	}

	/*
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeUnit()
	 */
	@Override
	public String getTimeUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getDescription()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getAgentType(java.util.List)
	 */
	@Override
	public Set<SwarmAgentType> getAgentType(List<SwarmAgentTypeConfig> list) {//throws SwarmException {
		
		//Set<SwarmAgentType> set=new Set<>();
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getAgens(java.util.Set)
	 */
	@Override
	public Set<SwarmAgent> getAgens(Set<SwarmAgentType> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getStationType(java.util.List)
	 */
	@Override
	public Set<SwarmStationType> getStationType(List<SwarmStationTypeConfig> list) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getStations(java.util.Set)
	 */
	@Override
	public Set<SwarmStation> getStations(Set<SwarmStationType> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getPlaceEdgeType(java.util.List)
	 */
	@Override
	public Set<SwarmPlaceEdgeType> getPlaceEdgeType(List<SwarmPlaceEdgeConfig> list) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getPlaceEdge(java.util.Set)
	 */
	@Override
	public Set<SwarmPlaceEdge> getPlaceEdge(Set<SwarmPlaceEdgeType> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getVisitEdgeType(java.util.List)
	 */
	@Override
	public Set<SwarmVisitEdgeType> getVisitEdgeType(List<SwarmVisitEdgeConfig> list) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getVisitEdge(java.util.Set)
	 */
	@Override
	public Set<SwarmVisitEdge> getVisitEdge(Set<SwarmVisitEdge> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeEdgeType(java.util.List)
	 */
	@Override
	public Set<SwarmTimeEdgeType> getTimeEdgeType(List<SwarmTimeEdgeConfig> list) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeEdge(java.util.Set)
	 */
	@Override
	public Set<SwarmTimeEdge> getTimeEdge(Set<SwarmTimeEdgeType> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getNecAgentStation(java.util.Set)
	 */
	@Override
	public Set<NecAgentStation> getNecAgentStation(Set<SwarmVisitEdge> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getItemSetLoadingAgent(java.util.Set)
	 */
	@Override
	public Set<ItemSetLoadingAgent> getItemSetLoadingAgent(Set<SwarmVisitEdgeType> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getItemSetLoadingStation(java.util.Set)
	 */
	@Override
	public Set<ItemSetLoadingStation> getItemSetLoadingStation(Set<SwarmPlaceEdge> set) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeEdgeState(java.util.Set)
	 */
	@Override
	public Set<TimeEdgeState> getTimeEdgeState(Set<SwarmTimeEdge> set) {
		// TODO Auto-generated method stub
		return null;
	}

}
