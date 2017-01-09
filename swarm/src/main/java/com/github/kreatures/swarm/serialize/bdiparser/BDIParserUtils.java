package com.github.kreatures.swarm.serialize.bdiparser;

import com.github.kreatures.core.util.Pair;
import com.github.kreatures.swarm.components.SwarmAgentType;
import com.github.kreatures.swarm.components.SwarmConfig;
import com.github.kreatures.swarm.components.SwarmStationType;

public final class BDIParserUtils implements BDIParser {

	public BDIParserUtils() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pair<Integer, String> getAgentTypePredicat(SwarmConfig swarmAgentType) {
		Pair<Integer, String> agentTypeCount = null;
		String agentTypeFakt = "AgentType(";
		if (swarmAgentType instanceof SwarmAgentType) {
			SwarmAgentType agentType = (SwarmAgentType) swarmAgentType;
			agentTypeFakt += agentType.getName();
			agentTypeFakt += "," + agentType.getFrequency();
			agentTypeFakt += "," + agentType.getNecessity();
			agentTypeFakt += "," + agentType.getTime();
			agentTypeFakt += "," + agentType.getPriority();
			agentTypeFakt += "," + agentType.getCycle();
			agentTypeFakt += "," + agentType.getCapacity();
			agentTypeFakt += "," + agentType.getSize();
			agentTypeFakt += "," + agentType.getSpeed() + ").";
			agentTypeCount = new Pair<>(agentType.getCount(), agentTypeFakt);
		} else {
			throw new IllegalArgumentException(
					"The argument must be a agent type.");
		}
		return agentTypeCount;
	}

	@Override
	public Pair<Integer, String> getStationTypePredicat(
			SwarmConfig swarmStationType) {
		Pair<Integer, String> stationTypeCount = null;
		String stationTypeFakt = "StationType(";
		if (swarmStationType instanceof SwarmStationType) {
			SwarmStationType stationType = (SwarmStationType) swarmStationType;
			stationTypeFakt += stationType.getName();
			stationTypeFakt += "," + stationType.getFrequency();
			stationTypeFakt += "," + stationType.getNecessity();
			stationTypeFakt += "," + stationType.getTime();
			stationTypeFakt += "," + stationType.getPriority();
			stationTypeFakt += "," + stationType.getCycle();
			stationTypeFakt += "," + stationType.getSpace();
			stationTypeFakt += "," + stationType.getItem() + ").";
			stationTypeCount = new Pair<>(stationType.getCount(),
					stationTypeFakt);
		} else {
			throw new IllegalArgumentException(
					"The argument must be a station type.");
		}
		return stationTypeCount;
	}

	@Override
	public String getPlaceEdgeTypePredicat(SwarmConfig swarmPlaceEdge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVisitEdgeTypePredicat(SwarmConfig swarmVisitEdge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTimeEdgeTypePredicat(SwarmConfig swarmTimeEdge) {
		// TODO Auto-generated method stub
		return null;
	}

}
