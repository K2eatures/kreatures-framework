package com.github.kreatures.extraction.gridworld;

import java.util.List;

import com.github.kreatures.extraction.learning.RLAgent;
import com.github.kreatures.extraction.learning.RLEnvironment;
import com.github.kreatures.extraction.wrapper.ENVIRONMENT;

public class GridworldRiverBehavior extends ENVIRONMENT<Location2D, Direction2D> {

	@Override
	protected RLEnvironment<Location2D, Direction2D> createEnv(List<RLAgent<Location2D, Direction2D>> agents) {
		return new GridworldRiver(agents);
	}

}
