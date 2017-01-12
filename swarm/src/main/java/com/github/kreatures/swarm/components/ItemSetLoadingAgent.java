/**
 * 
 */
package com.github.kreatures.swarm.components;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber).
 * @author donfack
 *
 */
public class ItemSetLoadingAgent implements SwarmComponents {
	
	private static int _UNIQUE=0;
	private String agentName;
	private String stationTypeName;
	private int numberOfItem;
	
	public ItemSetLoadingAgent(ItemSetLoadingAgent other) {
		this.agentName=other.agentName;
		this.stationTypeName=other.stationTypeName;
		this.numberOfItem=other.numberOfItem;
		
	}
	
	public ItemSetLoadingAgent(SwarmVisitEdgeType visitEdgeType) throws SwarmException {
		if(visitEdgeType==null){
			throw new SwarmException("Null pointer exception");
		}
		
		if(!visitEdgeType.IsItemLoading()){
			throw new SwarmException(String.format("The object connot be created, because station type %s no take items.", visitEdgeType.stationTypeName),SwarmExceptionType.INFORM);
		}
	
		_UNIQUE++;
		if(_UNIQUE<visitEdgeType.numberAgent){
			this.agentName=String.format("%s%d", visitEdgeType.getAgentTypeName(),_UNIQUE);
			this.stationTypeName=visitEdgeType.getStationTypeName();
			this.numberOfItem=0;
		}else{
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d Agent(s).",getName(),visitEdgeType.numberAgent),SwarmExceptionType.BREAKS);
		}
			
	}
	
	/**
	 * @see com.github.kreatures.core.serialize.Resource#getName()
	 */
	@Override
	public String getName() {
		return String.format("ItemLoading:%s:%s", agentName,stationTypeName);
	}

	/**
	 * @see com.github.kreatures.core.serialize.Resource#getDescription()
	 */
	@Override
	public String getDescription() {
		return String.format("count the number of item of station type %s, which will be carried by agent %s.",stationTypeName,agentName);
	}

	/** 
	 * @see com.github.kreatures.core.serialize.Resource#getResourceType()
	 */
	@Override
	public String getResourceType() {
		return RESOURCE_TYPE;
	}

	/**
	 * @see com.github.kreatures.core.serialize.Resource#getCategory()
	 */
	@Override
	public String getCategory() {

		return "AbstractSwarm: number of item.";
	}
	@Override
	public ItemSetLoadingAgent clone(){
		return new ItemSetLoadingAgent(this);
	}
	/**
	 * ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber).
	 */
	public String toString(){
		return String.format("ItemSetLoadingAgent(%s,%s,%d).", agentName,stationTypeName,numberOfItem);
	}
}
