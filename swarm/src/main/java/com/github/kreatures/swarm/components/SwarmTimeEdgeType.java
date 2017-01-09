package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.serialize.SwarmTimeEdgeConfig;

public class SwarmTimeEdgeType implements SwarmConfig {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmTimeEdgeType.class);
	/**
	 * This informs about the kind of components which are used in this time-Edge the
	 */
	protected TimeEdge kindOfConnection;
	protected int id;
	protected int numberFirstComponent;
	protected int numberSecondComponent;
	protected String firstComponentTypeName;
	protected String secondComponentTypeName;
	//à continuer Ici,  un attribute pour les deux attributes suivants: firstLogicalConnection, secondLogicalConnection ou bien? 
	protected ConnectionType firstLogicalConnection;
	protected ConnectionType secondLogicalConnection;
	
	
	protected boolean directed;
	protected int weight;
	

	protected SwarmTimeEdgeType(SwarmTimeEdgeType other){
		this.kindOfConnection=other.kindOfConnection;
		this.firstComponentTypeName=other.firstComponentTypeName;
		this.secondComponentTypeName=other.secondComponentTypeName;
		this.firstLogicalConnection=other.firstLogicalConnection;
		this.secondLogicalConnection=other.secondLogicalConnection;
		this.directed=other.directed;
		this.weight=other.weight;
	}

	@Override
	public String getName() {
		
		return "TimeEdge:"+firstComponentTypeName+" and "+secondComponentTypeName;
	}
	@Override
	public String getDescription() {
		
		return "Time edge of component ="+firstComponentTypeName+"and component ="+secondComponentTypeName;
	}
	@Override
	public String getResourceType() {
		
		return RESOURCE_TYPE;
	}
	@Override
	public String getCategory() {
		return "AbstractSwarm: time edge";
	}

	public TimeEdge getKindOfConnection() {
		return kindOfConnection;
	}

	public String getFirstComponentTypeName() {
		return firstComponentTypeName;
	}

	public String getSecondComponentTypeName() {
		return secondComponentTypeName;
	}

	public ConnectionType getLogicalConnection() {
		return logicalConnection;
	}

	public boolean isDirected() {
		return directed;
	}

	public int getWeight() {
		return weight;
	}
	
	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig,SwarmAgentType agentType1,SwarmAgentType agentType2){
		
	}
	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig,SwarmAgentType agentType,SwarmStationType stationType){
		
	}
	
	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig,SwarmStationType stationType,SwarmAgentType agentType){
		
	}
	
	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig,SwarmStationType StationType1,SwarmStationType stationType2){
		if(swarmConfig==null || StationType1==null || stationType2==null ){
			LOG.error("the given argument has to be no null.");
			throw new NullPointerException("the given argument has to be no null.");
		}
		
		if(StationType1.id==swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()){
			firstComponentTypeName=StationType1.getName();
			numberFirstComponent=StationType1.getCount();
		}else{
			LOG.error("the given first component isn't correct.");
			throw new IllegalArgumentException("the given first compoment isn't correct.");
		}
		
		if(stationType2.id==swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()){
			secondComponentTypeName=stationType2.getName();
			numberSecondComponent=stationType2.getCount();
		}else{
			LOG.error("the given second component isn't correct.");
			throw new IllegalArgumentException("the given second component isn't correct.");
		}
		
		this.id=swarmConfig.getIdSwarmTimeEdge();
		
		this.weight=swarmConfig.getValueSwarmTimeEdge();
		
		
		
		if(swarmConfig.getDirectedSwarmTimeEdge().equals("yes")){
			this.directed=true;
		}else{
			this.directed=false;
		}
	}
}
