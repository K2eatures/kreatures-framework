/**
 * 
 */
package com.github.kreatures.swarm.components;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * @author donfack
 *
 */
public class ItemSetLoadingStation implements SwarmComponents {

	private static int _UNIQUE=0;
	private String stationOutTypeName;
	private String stationInName;
	private int numberOfItem;
	
	public ItemSetLoadingStation(ItemSetLoadingStation other) {
		this.stationOutTypeName=other.stationOutTypeName;
		this.stationInName=other.stationInName;
		this.numberOfItem=other.numberOfItem;
		
	}
	
	public ItemSetLoadingStation(SwarmPlaceEdgeType placeEdgeType) throws SwarmException {
		if(placeEdgeType==null){
			throw new SwarmException("Null pointer exception");
		}
		
		if(!placeEdgeType.isDirected()){
			throw new SwarmException(String.format("The object connot be created, because place-edge type %s isn't directed.", placeEdgeType.getName()),SwarmExceptionType.INFORM);
		}
	
		_UNIQUE++;
		if(_UNIQUE<=placeEdgeType.numberSecondStation){
			this.stationOutTypeName=placeEdgeType.getFirstStationTypeName();
			this.stationInName=String.format("%s%d", placeEdgeType.getSecondStationTypeName(),_UNIQUE);
			this.numberOfItem=0;
		}else{
			_UNIQUE=0;
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d Station(s).",getName(),placeEdgeType.numberSecondStation),SwarmExceptionType.BREAKS);
		}
			
	}
	
	/**
	 * @see com.github.kreatures.core.serialize.Resource#getName()
	 */
	@Override
	public String getName() {
		return String.format("ItemLoading:%s:%s", stationOutTypeName,stationInName);
	}

	/**
	 * @see com.github.kreatures.core.serialize.Resource#getDescription()
	 */
	@Override
	public String getDescription() {
		return String.format("count the number of item of station type %s, which will be placed in the station %s.",stationOutTypeName,stationInName);
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
	public ItemSetLoadingStation clone(){
		return new ItemSetLoadingStation(this);
	}
	/**
	 * %ItemSetLoadingStation(StationTypeNameOut,StationNameIn,ItemNumber)
	 */
	public String toString(){
		return String.format("ItemSetLoadingStation(%s,%s,%d).", stationOutTypeName,stationInName,numberOfItem);
	}
	
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */
	public static String getDescriptions() {

		return "%ItemSetLoadingStation(StationTypeNameIn,StationNameOut,ItemNumber).";
	}
	
	public int getIdentity(){
		//TODO
		return hashCode();
	}
}
