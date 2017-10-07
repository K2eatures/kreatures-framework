package com.github.kreatures.extraction.gridworld;

import java.util.List;

import com.github.kreatures.extraction.learning.RLAgent;

public class GridworldRiver extends GridworldSimple {

	public GridworldRiver(List<RLAgent<Location2D, Direction2D>> agents) {
		this(agents, 8, 6);
	}

	public GridworldRiver(List<RLAgent<Location2D, Direction2D>> agents, int width, int height) {
		super(agents, width, height);

		for (int x = 1; x < width - 1; x++) {
			reward(x, 0, -100.0);
		}
	}

}
