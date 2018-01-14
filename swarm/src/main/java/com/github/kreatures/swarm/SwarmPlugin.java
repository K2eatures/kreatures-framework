package com.github.kreatures.swarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.simpleframework.xml.transform.Transform;

import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.core.KReaturesPluginAdapter;
import com.github.kreatures.core.SwarmPlanComponent;
import com.github.kreatures.core.AgentComponent;
//import com.github.kreatures.core.AgentComponent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.EnvironmentBehavior;
import com.github.kreatures.core.operators.BaseOperator;
import com.github.kreatures.core.serialize.SerializeHelper;
import com.github.kreatures.core.serialize.SwarmLoaderDefault;
import com.github.kreatures.gui.UIPlugin;
import com.github.kreatures.gui.base.ViewComponent;
import com.github.kreatures.gui.view.ControllerBeliefbaseView;
import com.github.kreatures.gui.view.EnvFeaturesBeliefbaseView;
import com.github.kreatures.gui.view.FolBeliefbaseView;
import com.github.kreatures.gui.view.ScenarioModelBeliefbaseView;
import com.github.kreatures.gui.view.WorldBeliefbaseView;
import com.github.kreatures.serialize.asp.RuleTransform;
import com.github.kreatures.core.listener.SwarmSimulationListener;
import com.github.kreatures.core.logic.BaseChangeBeliefs;
import com.github.kreatures.core.logic.BaseReasoner;
import com.github.kreatures.core.logic.BaseTranslator;
import com.github.kreatures.core.logic.ControllerBeliefbaseForGUI;
import com.github.kreatures.core.logic.EnvFeaturesBeliefbase;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.ScenarioModelBeliefbase;
import com.github.kreatures.core.logic.WorldBeliefbaseForGUI;
import com.github.kreatures.swarm.basic.ActionState;
import com.github.kreatures.swarm.basic.SwarmBehavior;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.beliefbase.SwarmTranslator;
import com.github.kreatures.swarm.beliefbase.SwarmAspChangeBeliefs;
import com.github.kreatures.swarm.beliefbase.SwarmAspReasoner;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;
import com.github.kreatures.swarm.operators.SwarmEvaluationOptionsOperator;
import com.github.kreatures.swarm.operators.SwarmExecuteOperator;
import com.github.kreatures.swarm.operators.SwarmIntentionUpdateOperator;
import com.github.kreatures.swarm.operators.SwarmGenerateOptionsOperator;
import com.github.kreatures.swarm.operators.SwarmSubgoalGenerationOperator;

import net.sf.tweety.lp.asp.syntax.Rule;
import net.xeoh.plugins.base.annotations.PluginImplementation;
/**
 * 
 * @author Donfack
 * 
 *This class registes important Classes, which we have to use to implement 
 *the AbstractSwarm's concepts.
 */

@PluginImplementation
public class SwarmPlugin extends KReaturesPluginAdapter implements UIPlugin {
	private Map<Class<?>, Class<? extends Transform<?>>> matcherMap = new HashMap<>();
	/*
	 * Here we have to create all abstract swarm config file 
	 * in order that there can be laoded after into KReatures.
	 */
	
	
	@Override
	public void onLoading() {
		
		//All needed paths will be created.
		KReaturesPaths.iniFolder();
		//All Swarm Scenarien will be creatured.
		SwarmLoaderDefault.getInstance().init();
		
		SwarmLoaderDefault.freeInstance();
		
		KReatures.getInstance().addSimulationListener(new SwarmSimulationListener());
		addTransformMapping(Rule.class, RuleTransform.class);
	}
	
	@Override
	public void unUnloaded() {
		SerializeHelper sh = SerializeHelper.get();
		for(Entry<Class<?>, Class<? extends Transform<?>>> entry : matcherMap.entrySet()) {
			sh.removeTransformMapping(entry.getKey());
		}
		matcherMap.clear();
		
	}
	
	@Override
	public List<Class<? extends EnvironmentBehavior>> getEnvironmentBehaviors() {
		List<Class<? extends EnvironmentBehavior>> swarmBehaviors = new ArrayList<>();
		swarmBehaviors.add(SwarmBehavior.class);
		return swarmBehaviors;
	}

	@Override
	public List<Class<? extends BaseTranslator>> getTranslatorImpl() {
		List<Class<? extends BaseTranslator>> translators = new ArrayList<>();
		translators.add(SwarmTranslator.class);
		return translators;
	}
	
	@Override
	public List<Class<? extends BaseOperator>> getOperators() {
		List<Class<? extends BaseOperator>> operators = new ArrayList<>();
		operators.add(SwarmGenerateOptionsOperator.class);
		operators.add(SwarmEvaluationOptionsOperator.class);
		operators.add(SwarmIntentionUpdateOperator.class);
		operators.add(SwarmSubgoalGenerationOperator.class);
		operators.add(SwarmExecuteOperator.class);
		operators.add(SwarmBeliefsUpdateOperator.class);
		return operators;
	}
	@Override
	public List<Class<? extends BaseBeliefbase>> getBeliefbaseImpl() {
		List<Class<? extends BaseBeliefbase>> reval = new LinkedList<>();
		reval.add(FolBeliefbase.class);
		reval.add(EnvFeaturesBeliefbase.class);
		reval.add(ScenarioModelBeliefbase.class);
		reval.add(WorldBeliefbaseForGUI.class);
		reval.add(ControllerBeliefbaseForGUI.class);
		return reval;
	}
	@Override
	public List<Class<? extends BaseChangeBeliefs>> getChangeImpl() {
		List<Class<? extends BaseChangeBeliefs>> reval = new LinkedList<>();
		reval.add(SwarmAspChangeBeliefs.class);
		return reval;
	}
	
	@Override
	public List<Class<? extends BaseReasoner>> getReasonerImpl() {
		List<Class<? extends BaseReasoner>> reval = new LinkedList<>();
		reval.add(SwarmAspReasoner.class);
		return reval;
	}
	
	@Override
	public Map<String, Class<? extends ViewComponent>> getUIComponents() {
		Map<String, Class<? extends ViewComponent>> reval = new HashMap<>();
		reval.put("Beliefbase", FolBeliefbaseView.class);
		reval.put("WorldBeliefbase", WorldBeliefbaseView.class);
		reval.put("ControllerBeliefbase", ControllerBeliefbaseView.class);
		reval.put("BeliefEnvFeatures", EnvFeaturesBeliefbaseView.class);
		reval.put("ScenarioModel", ScenarioModelBeliefbaseView.class);
		return reval;
	}
	
	@Override
	public List<Class<? extends AgentComponent>> getAgentComponentImpl() {
		List<Class<? extends AgentComponent>> components = new ArrayList<>();
		components.add(SwarmDesires.class);
		components.add(ActionState.class);
		components.add(SwarmPlanComponent.class);
		return components;
	}
//	@Override
//	public List<Class<? extends AgentComponent>> getAgentComponentImpl() {
//		List<Class<? extends AgentComponent>> reval = new ArrayList<>();
//		reval.add(SwarmMappingGeneric.class);
//		reval.add(StatusAgentComponents.class);
//		return reval;
//	}
}
