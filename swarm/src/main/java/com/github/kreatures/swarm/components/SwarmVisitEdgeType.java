package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.serialize.SwarmVisitEdgeConfig;

public class SwarmVisitEdgeType implements SwarmConfig {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmVisitEdgeType.class);
	protected int id;
	protected String agentTypeName;
	protected int numberAgent;
	protected String stationTypeName;
	protected int numberStation;
	protected boolean bold;
	
	protected SwarmVisitEdgeType(SwarmVisitEdgeType other) {
		this.id=other.id;
		this.agentTypeName=other.agentTypeName;
		this.stationTypeName=other.stationTypeName;
		this.numberAgent=other.numberAgent;
		this.numberStation=other.numberStation;
	}
	
	@Override
	public String getName() {
		
		return "VisitEdge:"+agentTypeName+" and "+stationTypeName;
	}
	@Override
	public String getDescription() {
		
		return "Visit edge of agent ="+agentTypeName+"and station ="+stationTypeName;
	}
	@Override
	public String getResourceType() {
		
		return RESOURCE_TYPE;
	}
	@Override
	public String getCategory() {
		return "AbstractSwarm: visit edge";
	}
	
	public SwarmVisitEdgeType(SwarmVisitEdgeConfig swarmConfig,SwarmAgentType agentType,SwarmStationType stationType){
		if(swarmConfig==null || agentType==null || stationType==null ){
			LOG.error("the given argument has to be no null.");
			throw new NullPointerException("the given argument has to be no null.");
		}
		
		if(agentType.id==swarmConfig.getFirstConnectedIdRefSwarmVisitEdge()){
			agentTypeName=agentType.getName();
			numberAgent=agentType.getCount();
		}else{
			LOG.error("the given agent-type isn't correct.");
			throw new IllegalArgumentException("the given agent-type isn't correct.");
		}
		
		if(stationType.id==swarmConfig.getSecondConnectedIdRefSwarmVisitEdge()){
			stationTypeName=stationType.getName();
			numberStation=stationType.getCount();
		}else{
			LOG.error("the given station-type isn't correct.");
			throw new IllegalArgumentException("the given station-type isn't correct.");
		}
		
		this.id=swarmConfig.getIdSwarmVisitEdge();
		
		if(swarmConfig.getBoldSwarmVisitEdge().equals("yes")){
			this.bold=true;
		}else{
			this.bold=false;
		}
	}
}
