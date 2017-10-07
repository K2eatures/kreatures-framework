package com.github.kreatures.extraction;

import java.util.ArrayList;
import java.util.List;

import com.github.kreatures.core.AgentComponent;
import com.github.kreatures.core.EnvironmentBehavior;
import com.github.kreatures.core.KReaturesPluginAdapter;
import com.github.kreatures.core.operators.BaseOperator;
import com.github.kreatures.extraction.gridworld.GridworldLakeBehavior;
import com.github.kreatures.extraction.gridworld.GridworldRiverBehavior;
import com.github.kreatures.extraction.gridworld.GridworldSimpleBehavior;
import com.github.kreatures.extraction.island.IslandLabBehavior;
import com.github.kreatures.extraction.operators.ExtractionOperator;
import com.github.kreatures.extraction.operators.UpdateOperator;

import net.xeoh.plugins.base.annotations.PluginImplementation;

/**
 * 
 * @author Manuel Barbi
 *
 */
@PluginImplementation
public class ExtractionPlugin extends KReaturesPluginAdapter {

	@Override
	public List<Class<? extends AgentComponent>> getAgentComponentImpl() {
		List<Class<? extends AgentComponent>> comps = new ArrayList<>();

		return comps;
	}

	@Override
	public List<Class<? extends BaseOperator>> getOperators() {
		List<Class<? extends BaseOperator>> ops = new ArrayList<>();
		ops.add(UpdateOperator.class);
		ops.add(ExtractionOperator.class);
		ops.add(ExtractionOperator.class);
		return ops;
	}

	@Override
	public List<Class<? extends EnvironmentBehavior>> getEnvironmentBehaviors() {
		List<Class<? extends EnvironmentBehavior>> envs = new ArrayList<>();
		envs.add(IslandLabBehavior.class);
		envs.add(GridworldSimpleBehavior.class);
		envs.add(GridworldRiverBehavior.class);
		envs.add(GridworldLakeBehavior.class);
		return envs;
	}

}
