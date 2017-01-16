package com.github.kreatures.swarm.components;

import java.util.Collection;

public interface SwarmComponentFilter {
	/**
	 * 
	 * @param components the swarmComponents which will be match with given id.
	 * @param id the of the searched component.
	 * @return return the component which id is given.
	 */
	<T extends SwarmComponents> T filter(Collection<T> components,int id);
}
