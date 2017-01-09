package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.serialize.SwarmPlaceEdgeConfig;
/**
 * 
 * @author donfack
 *
 */
public class SwarmPlaceEdgeType implements SwarmConfig {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmPlaceEdgeType.class);
	
	protected int id;
	protected int numberFirstStation;
	
	protected int numberSecondStation;
	
	protected String firstStationTypeName;
	
	protected String secondStationTypeName;
	
	protected int weight;
	
	protected boolean directed;
	
	
	protected SwarmPlaceEdgeType(SwarmPlaceEdgeType other) {
		this.numberFirstStation=other.getNumberFirstStation();
		this.numberSecondStation=other.getNumberSecondStation();
		this.firstStationTypeName=other.getFirstStationTypeName();
		this.secondStationTypeName=other.getSecondStationTypeName();
		this.weight=other.getWeight();
		this.directed=other.isDirected();
		this.id=other.getIdentity();
	}

	public int getNumberFirstStation() {
		return numberFirstStation;
	}
	public int getNumberSecondStation() {
		return numberSecondStation;
	}
	public String getFirstStationTypeName() {
		return firstStationTypeName;
	}
	public String getSecondStationTypeName() {
		return secondStationTypeName;
	}
	public int getWeight() {
		return weight;
	}
	public boolean isDirected() {
		return directed;
	}
	public int getIdentity(){
		return id;
	}



	@Override
	public String getName() {
		
		return "PlaceEdge:"+firstStationTypeName+" and "+secondStationTypeName;
	}
	@Override
	public String getDescription() {
		
		return "Place edge of station ="+firstStationTypeName+"and station id="+secondStationTypeName;
	}
	@Override
	public String getResourceType() {
		
		return RESOURCE_TYPE;
	}
	@Override
	public String getCategory() {
		return "AbstractSwarm: place edge";
	}
	
	public SwarmPlaceEdgeType(SwarmPlaceEdgeConfig swarmConfig,SwarmStationType firstStation,SwarmStationType secondStation){
		
		if(swarmConfig==null || firstStation==null || secondStation==null ){
			LOG.error("the given argument has to be no null.");
			throw new NullPointerException("the given argument has to be no null.");
		}
		
		if(firstStation.id==swarmConfig.getFirstConnectedIdRefSwarmPlaceEdge()){
			firstStationTypeName=firstStation.getName();
			numberFirstStation=firstStation.getCount();
		}else{
			LOG.error("the given first station isn't correct.");
			throw new IllegalArgumentException("the given first station isn't correct.");
		}
		
		if(secondStation.id==swarmConfig.getSecondConnectedIdRefSwarmPlaceEdge()){
			secondStationTypeName=secondStation.getName();
			numberSecondStation=secondStation.getCount();
		}else{
			LOG.error("the given second station isn't correct.");
			throw new IllegalArgumentException("the given second station isn't correct.");
		}
		
		this.id=swarmConfig.getIdSwarmPlaceEdge();
		
		this.weight=swarmConfig.getValueSwarmPlaceEdge();
		
		if(swarmConfig.getDirectedSwarmPlaceEdge().equals("yes")){
			this.directed=true;
		}else{
			this.directed=false;
		}
	}
}
