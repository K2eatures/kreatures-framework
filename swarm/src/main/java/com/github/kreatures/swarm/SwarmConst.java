package com.github.kreatures.swarm;

public enum SwarmConst {
	MAX_INT,
	UNIT,
	ZERO_VALUE;
	public int getValue(){
		
		if(name()=="MAX_INT"){
			return 1000;
		}
		
		if(name()=="UNIT"){
			return 1;
		}
		
		if(name()=="ZERO_VALUE"){
			return 0;
		}
		
		return 0;
	}
}
