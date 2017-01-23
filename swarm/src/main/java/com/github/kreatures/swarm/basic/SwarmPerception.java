package com.github.kreatures.swarm.basic;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

public class SwarmPerception implements Perception {

	private SwarmPredicate perception;
	
	public SwarmPerception(SwarmPredicate perception ) {
		this.perception=perception;
	}

	@SuppressWarnings("unchecked")
	public <T extends SwarmPredicate> T getFact() {
		return (T)perception;
	}
	
	@Override
	public String getReceiverId() {
		return "__ALL__";
	}
	
	@Override
	public String toString() {
		return perception.toString();
	}
}
