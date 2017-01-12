package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;
import com.github.kreatures.swarm.serialize.SwarmVisitEdgeConfig;
/**
 * VisitEdge(agentName,AgentTypeName,StationName,StationTypeName,bold).
 * @author donfack
 *
 */
public class SwarmVisitEdgeType implements SwarmComponents {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmVisitEdgeType.class);
	protected int id;
	protected String agentTypeName;
	protected int numberAgent;
	protected String stationTypeName;
	protected int numberStation;
	protected boolean bold;
	/**
	 * check if a agent can carry items of visited stationType
	 */
	protected boolean isItemLoading;
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmVisitEdgeType(SwarmVisitEdgeType other) {
		this.id=other.id;
		this.agentTypeName=other.agentTypeName;
		this.stationTypeName=other.stationTypeName;
		this.numberAgent=other.numberAgent;
		this.numberStation=other.numberStation;
	}
	
	
	public String getAgentTypeName(){
		return this.agentTypeName;
	}

	
	public String getStationTypeName(){
		return this.stationTypeName;
	}
	
	@Override
	public String getName() {
		
		return String.format("VisitEdge:%s:%s", agentTypeName,stationTypeName);
	}
	@Override
	public String getDescription() {
		
		return String.format("Visit edge of agent =%s and station =%s", agentTypeName,stationTypeName);
		
	}
	
	@Override
	public String getResourceType() {
		return RESOURCE_TYPE;
	}
	
	
	@Override
	public String getCategory() {
		return "AbstractSwarm: visit edge";
	}
	
	public SwarmVisitEdgeType(SwarmVisitEdgeConfig swarmConfig,SwarmAgentType agentType,SwarmStationType stationType)throws SwarmException{
		if(swarmConfig==null || agentType==null || stationType==null ){
			throw new SwarmException("Null pointer exception");
		}
		
		if(agentType.id==swarmConfig.getFirstConnectedIdRefSwarmVisitEdge()){
			agentTypeName=agentType.getName();
			numberAgent=agentType.getCount();
		}else{
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}
		
		if(stationType.id==swarmConfig.getSecondConnectedIdRefSwarmVisitEdge()){
			stationTypeName=stationType.getName();
			numberStation=stationType.getCount();
			if(stationType.getItem()>0){
				isItemLoading=true;
			}else{
				isItemLoading=false;
			}
		}else{
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}
		
		this.id=swarmConfig.getIdSwarmVisitEdge();
		
		if(swarmConfig.getBoldSwarmVisitEdge().equals("yes")){
			this.bold=true;
		}else{
			this.bold=false;
		}
	}
	
	public boolean IsItemLoading(){
		return isItemLoading;
	}
	/**
	 * VisitEdgeType(AgentTypeName,StationTypeName,bold).
	 */
	public String toString() {
		return String.format("VisitEdgeType(%s,%s,%s).",getAgentTypeName(), getStationTypeName(),bold);
	}
}
