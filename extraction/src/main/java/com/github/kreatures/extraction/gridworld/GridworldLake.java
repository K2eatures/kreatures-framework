package com.github.kreatures.extraction.gridworld;

import java.util.List;

import com.github.kreatures.extraction.learning.RLAgent;

public class GridworldLake extends GridworldSimple {

	public GridworldLake(List<RLAgent<Location2D, Direction2D>> agents) {
		this(agents, 8, 6);
	}

	public GridworldLake(List<RLAgent<Location2D, Direction2D>> agents, int width, int height) {
		super(agents, width, height);

		for (int y = 0; y < height - 1; y++) {
			for (int x = 1; x < width - 1; x++) {
				reward(x, y, -100.0);
			}
		}
	}

}
