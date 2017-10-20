package com.github.kreatures.swarm;

public enum SwarmConst {
	MAX_INT,
	UNIT,
	ZERO_VALUE,
	WAIT_TIME;
	public int getValue(){
		switch(this){
			case MAX_INT: return 1000;
			case UNIT: return 1;
			case ZERO_VALUE: return 0;
			case WAIT_TIME: return 6;
			default:return 0;
		}
	}
}
