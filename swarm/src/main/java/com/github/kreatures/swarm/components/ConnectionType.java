package com.github.kreatures.swarm.components;
/**
 * The List of all the possible logical kind of connection into a time-edge component.  
 * @author donfack
 *
 */
public enum ConnectionType {
	NO,
	YES,
	BOTH;
	
	@Override
	public String toString(){
		if(name()=="NO"){
			return "false";
		}
		if(name()=="YES"){
			return "true";
		}
		
		if(name()=="BOTH")
			return "both";
		
		return null;
	}
}
