package com.github.kreatures.swarm.components;
/**
 * There are the kind of connection which can be used into the time-edge components. 
 * AGENT_AGENT means connection between agents.
 * AGENT_STATION means connections between agent and station, where agent is the first component.
 * STATION_AGENT means connections between agent and station, where station is the first component.
 * STATION_STATION means connections between stations.
 * @author donfack
 *
 */
public enum TimeEdge {
	AGENT_AGENT,
	AGENT_STATION,
	STATION_AGENT,
	STATION_STATION;
	
	@Override
	public String toString(){
		if(name()=="AGENT_AGENT"){
			return "1";
		}
		if(name()=="STATION_STATION"){
			return "0";
		}
		
		if(name()=="STATION_AGENT"||name()=="AGENT_STATION")
			return "2";
		
		return null;
	}
	
	
}
	
