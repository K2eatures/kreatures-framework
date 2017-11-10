/**
 * 
 */
package com.github.kreatures.swarm.components;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * %TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,Type,
 * CountTick,IsWaiting,IsReady,IsFinish). 
 * @author donfack
 *
 */
public class TimeEdgeState implements SwarmComponents {
	private static byte _UNIQUE=0;

	private String name;
	private String typeName;
	private String visitorName;
	private String visitorTypeName;
	/**
	 * Indicate with type of timeedge: ie NoDNoC
	 */
	private int edgeType;
	/**
	 * Have to run parallel without waiting time?  
	 */
	private boolean echTime=false;

	private ComponentType componentType;
	private int tick;
	private boolean isWaiting;
	private boolean isReady;
	private boolean isFinish;

	/**
	 * 
	 */
	public TimeEdgeState(TimeEdgeState other) {		
		this.name=other.name;
		this.typeName=other.typeName;
		this.visitorName=other.visitorName;
		this.visitorTypeName=other.visitorTypeName;
		this.edgeType=other.edgeType;
		this.echTime=other.echTime;
		this.componentType=other.componentType;
		this.tick=other.tick;
		this.isWaiting=other.isWaiting;
		this.isReady=other.isReady;
		this.isFinish=other.isFinish;

	}
	/**
	 * TODO
	 * this constructor must be call twice time in oder to create the both necessary objects.
	 * @param timeEdge 
	 */
	public TimeEdgeState(SwarmTimeEdge timeEdge) throws SwarmException{
		if(timeEdge==null){
			throw new SwarmException("Null pointer exception");
		}

		if(timeEdge.getKindOfConnection()==null )
			throw new SwarmException(String.format("The object connot be created, because there aren't time-type."),SwarmExceptionType.INFORM);

		_UNIQUE++;
		if(_UNIQUE==1){
			this.name=timeEdge.getFirstName();
			this.typeName=timeEdge.firstComponentTypeName;
			if(timeEdge.getKindOfConnection()==TimeEdge.AGENT_AGENT||timeEdge.getKindOfConnection()==TimeEdge.AGENT_STATION){
				this.componentType=ComponentType.AGENT;
			}else{
				this.componentType=ComponentType.STATION;
			}

		}else if(_UNIQUE==2){
			this.name=timeEdge.getSecondName();
			this.typeName=timeEdge.getSecondComponentTypeName();
			if(timeEdge.getKindOfConnection()==TimeEdge.AGENT_AGENT||timeEdge.getKindOfConnection()==TimeEdge.STATION_AGENT){
				this.componentType=ComponentType.AGENT;
			}else{
				this.componentType=ComponentType.STATION;
			}
		}else{
			_UNIQUE=0;
			throw new SwarmException(String.format("All elements of components type '%s' have be created.",getName()),SwarmExceptionType.BREAKS);
		}
		this.visitorName="nothing";
		this.visitorTypeName="nothing";
		this.tick=0;
		this.isWaiting=false;
		this.isReady=false;
		this.isFinish=false;
		if(timeEdge.getWeight()==0)
			this.echTime=true;
		if(timeEdge.directed){
			switch(timeEdge.getLogicalConnection()){
			case NO:edgeType=1;
			break;
			case YES: edgeType=3;
			break;
			case BOTH: /* this cannot happen */
			}
		}else{
			switch(timeEdge.getLogicalConnection()){
			case NO:edgeType=0;
			break;
			case YES: edgeType=2;
			break;
			case BOTH: edgeType=4;
			break;
			}
		}

	}


	/**
	 * @see com.github.kreatures.core.serialize.Resource#getName()
	 */
	@Override
	public String getName() {
		return name;//String.format("TimeEdgeState:%s", name);
	}

	/**
	 * @see com.github.kreatures.core.serialize.Resource#getDescription()
	 */
	@Override
	public String getDescription() {
		return String.format("Define the time state of component %s",name);
	}

	@Override
	public boolean equals(Object other){
		if(!(other instanceof TimeEdgeState))return false;
		TimeEdgeState obj=(TimeEdgeState)other;
		return obj.name==null?this.name==null:obj.name.equals(this.name);

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

		return "AbstractSwarm: time-edge state.";
	}
	@Override
	public TimeEdgeState clone(){
		return new TimeEdgeState(this);
	}
	/**
	 * TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,
	 * Type,CountTick,IsWaiting,IsReady,IsFinish). 
	 */
	@Override
	public String toString(){
		return String.format("TimeEdgeState(%s,%s,%s,%s,%d,%s,%s,%d,%s,%s,%s).", name,typeName,visitorName,visitorTypeName,edgeType,echTime,componentType,tick,isWaiting,isReady,isFinish);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */
	public static String getDescriptions() {

		return "%TimeEdgeStatus(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,EdgeType,EchTime,Type,CountTick,IsWaiting,IsReady,IsFinish).";
	}
	public int getIdentity(){
		//TODO
		return hashCode();
	}
	/**
	 * @param visitorName the visitorName to set
	 */
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	/**
	 * @param visitorTypeName the visitorTypeName to set
	 */
	public void setVisitorTypeName(String visitorTypeName) {
		this.visitorTypeName = visitorTypeName;
	}

}
