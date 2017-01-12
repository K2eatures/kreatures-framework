package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;
import com.github.kreatures.swarm.serialize.SwarmPlaceEdgeConfig;
/**
 * 
 * @author donfack
 *
 */
public class SwarmPlaceEdgeType implements SwarmComponents {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmPlaceEdgeType.class);
	
	protected int id;
	protected int numberFirstStation;
	
	protected int numberSecondStation;
	
	protected String firstStationTypeName;
	
	protected String secondStationTypeName;
	
	protected int weight;
	
	protected boolean directed;
	
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
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
		
		return String.format("PlaceEdge:%s:%s", firstStationTypeName,secondStationTypeName);
				
	}
	@Override
	public String getDescription() {
		
		return String.format("Place edge of station =%s and station =%s",firstStationTypeName,secondStationTypeName);
	}
	@Override
	public String getResourceType() {
		
		return RESOURCE_TYPE;
	}
	@Override
	public String getCategory() {
		return "AbstractSwarm: place edge";
	}
	
	public SwarmPlaceEdgeType(SwarmPlaceEdgeConfig swarmConfig,SwarmStationType firstStation,SwarmStationType secondStation)throws SwarmException{
		
		if(swarmConfig==null || firstStation==null || secondStation==null ){
			throw new SwarmException("Null pointer exception");
		}
		
		if(firstStation.id==swarmConfig.getFirstConnectedIdRefSwarmPlaceEdge()){
			firstStationTypeName=firstStation.getName();
			numberFirstStation=firstStation.getCount();
		}else{
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}
		
		if(secondStation.id==swarmConfig.getSecondConnectedIdRefSwarmPlaceEdge()){
			secondStationTypeName=secondStation.getName();
			numberSecondStation=secondStation.getCount();
		}else{
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}
		
		this.id=swarmConfig.getIdSwarmPlaceEdge();
		
		this.weight=swarmConfig.getValueSwarmPlaceEdge();
		
		if(swarmConfig.getDirectedSwarmPlaceEdge().equals("yes")){
			this.directed=true;
		}else{
			this.directed=false;
		}
	}
	
	/**
	 * PlacedEdgeType(StationTypeNameIn,StationTypeNameOut,Weight,directed).
	 */
	public String toString() {
		return String.format("PlacedEdgeType(%s,%s,%d,%s).",firstStationTypeName, secondStationTypeName,weight,directed);
	}
}
