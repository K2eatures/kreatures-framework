package com.github.kreatures.swarm.operators;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseEvaluationOptionsOperator;
import com.github.kreatures.core.operators.parameters.EvaluationParameter;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class SwarmEvaluationOptionsOperator extends BaseEvaluationOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(SwarmEvaluationOptionsOperator.class);
	
	/**
	 * reference to environment component of this simulation.  
	 */
	private EnvironmentComponent envComponent; 
		
	
	@Override
	protected Boolean processImpl(EvaluationParameter params) {
		boolean check=false;
		// get a copy of the agent.
		NewAgent agent=(NewAgent)params.getAgent();
		/**
		 * Get the agentcomponent where the desires will be stored.
		 */
		SwarmDesires swarmDesires=(SwarmDesires) agent.getComponent(SwarmDesires.class);
		swarmDesires.clear();
		// get the given filter in the agent context
		String query=(String)agent.getContext().get(SwarmContextConst._FILTER);
		// get a object of FolBeliefbase
		FolBeliefbase folBB=(FolBeliefbase)params.getBaseBeliefbase();
		// keep a object of FolBeliefbase program
		envComponent=params.getEnvComponent();
		Set<SwarmPredicate> result=envComponent.askEnvironment(folBB, query);
		if(result!=null) {
			swarmDesires.addDesires(result);
			//TODO it is actually do nothings.
			evaluationFunction(swarmDesires);  
			LOG.info("available Desires are:"+result);
			check=true;
		}else {
			params.report("no Desires to evaluate found");
			LOG.info("no available Desires");
		}
		return check;
	}
	
	@Override
	protected void prepare(EvaluationParameter params) {
		
	}
	/**
	 * Use for the reward.
	 * Evaluation each desire and sort it from best to bad desire. 
	 * 
	 * @param desires
	 */
	private void evaluationFunction(SwarmDesires desires) {
		
	}

}
