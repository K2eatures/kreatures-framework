package com.github.kreatures.extraction.island;

import java.util.List;

import com.github.kreatures.extraction.learning.RLAgent;
import com.github.kreatures.extraction.learning.RLEnvironment;
import com.github.kreatures.extraction.wrapper.ENVIRONMENT;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class IslandLabBehavior extends ENVIRONMENT<IslandPerception, IslandAction> {

	@Override
	protected RLEnvironment<IslandPerception, IslandAction> createEnv(List<RLAgent<IslandPerception, IslandAction>> agents) {
		return new IslandLabEnvironment(agents);
	}

}
