package com.github.kreatures.swarm.components;

public enum ComponentType {
	
	STATION,
	AGENT;
	
	@Override
	public String toString(){
		if(name().equals("STATION")){
			return "0";
		}
		
		if(name().equals("AGENT")){
			return "1";
		}
		
		return null;
			
	}

}
