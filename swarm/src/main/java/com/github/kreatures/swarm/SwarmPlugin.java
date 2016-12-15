package com.github.kreatures.swarm;

import java.util.ArrayList;
import java.util.List;

import com.github.kreatures.core.KReaturesPluginAdapter;
import com.github.kreatures.core.AgentComponent;
import com.github.kreatures.core.EnvironmentBehavior;
import com.github.kreatures.core.operators.BaseOperator;
import com.github.kreatures.core.logic.BaseTranslator;
import com.github.kreatures.swarm.basic.SwarmBehavior;
import com.github.kreatures.swarm.beliefbase.SwarmTranslator;
import com.github.kreatures.swarm.beliefbase.SwarmUpdateBeliefsOperator;
import com.github.kreatures.swarm.components.StatusAgentComponents;
import com.github.kreatures.swarm.components.SwarmMappingGeneric;
import com.github.kreatures.swarm.operators.SwarmExecuteOperator;
import com.github.kreatures.swarm.operators.SwarmIntentionUpdateOperator;
import com.github.kreatures.swarm.operators.SwarmGenerateOptionsOperator;
import com.github.kreatures.swarm.operators.SwarmSubgoalGenerationOperator;

import net.xeoh.plugins.base.annotations.PluginImplementation;
/**
 * 
 * @author Donfack
 * 
 *This class registes important Classes, which we have to use to implement 
 *the AbstractSwarm's concepts.
 */

@PluginImplementation
public class SwarmPlugin extends KReaturesPluginAdapter {
	
	@Override
	public List<Class<? extends EnvironmentBehavior>> getEnvironmentBehaviors() {
		List<Class<? extends EnvironmentBehavior>> swarmBehaviors = new ArrayList<Class<? extends EnvironmentBehavior>>();
		swarmBehaviors.add(SwarmBehavior.class);
		return swarmBehaviors;
	}
	
	@Override
	public List<Class<? extends AgentComponent>> getAgentComponentImpl() {
		List<Class<? extends AgentComponent>> components = new ArrayList<Class<? extends AgentComponent>>();
		components.add(SwarmMappingGeneric.class);
		components.add(StatusAgentComponents.class);
		return components;
	}

	@Override
	public List<Class<? extends BaseTranslator>> getTranslatorImpl() {
		List<Class<? extends BaseTranslator>> translators = new ArrayList<Class<? extends BaseTranslator>>();
		translators.add(SwarmTranslator.class);
		return translators;
	}
	
	@Override
	public List<Class<? extends BaseOperator>> getOperators() {
		List<Class<? extends BaseOperator>> operators = new ArrayList<Class<? extends BaseOperator>>();
		operators.add(SwarmGenerateOptionsOperator.class);
		operators.add(SwarmIntentionUpdateOperator.class);
		operators.add(SwarmSubgoalGenerationOperator.class);
		operators.add(SwarmExecuteOperator.class);
		operators.add(SwarmUpdateBeliefsOperator.class);
		return operators;
	}
}
