package com.github.kreatures.swarm.basic;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

public class SwarmPerception implements Perception {

	private SwarmPredicate predicate;
	
	public SwarmPerception(SwarmPredicate perception ) {
		this.predicate=perception;
	}

	@SuppressWarnings("unchecked")
	public <T extends SwarmPredicate> T getFact() {
		return (T)predicate;
	}
	
	@Override
	public String getReceiverId() {
		return "__ALL__";
	}
	
	@Override
	public String toString() {
		return predicate.toString();
	}
}
