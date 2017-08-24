package com.github.kreatures.swarm.basic;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

public interface SwarmPerception extends Perception {
	
	@Override
	public default String getReceiverId() {
		return "__ALL__";
	}
	
	@Override
	public String toString() ;
}
