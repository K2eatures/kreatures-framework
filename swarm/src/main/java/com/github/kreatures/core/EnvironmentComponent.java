package com.github.kreatures.core;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.logic.EnvFeaturesBeliefbase;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.ScenarioModelBeliefbase;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

import net.sf.tweety.lp.asp.syntax.Program;

public interface EnvironmentComponent{
	/** logging facility */
	static Logger LOG = LoggerFactory.getLogger(EnvironmentComponent.class);
	
	/**
	 * @return the environment features logic
	 */
	EnvFeaturesBeliefbase getEnvironmentFeatures() ;
	
	/**
	 * @return the scenario model logic
	 */
	ScenarioModelBeliefbase getScenariomodell();
	
	/**
	 * @return the name of the simulation
	 */
	String getSimulationName();
	
	/**
	 * @return the logic program of the scenario model and the environment features 
	 */
	Program getScenarioModelAndEnFeaturesBB();
	
	/**
	 * This method is use to communicate with the environment.
	 * @param bb the set of information about what the current agent beliefs
	 * @param query the question of the current agent to the environment.
	 * @return the answer about the question of the current agent.
	 */
	Set<SwarmPredicate> askEnvironment(FolBeliefbase bb, String...  query); 
	
}
