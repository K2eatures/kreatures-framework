package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.operators.parameters.FilterParameter;
import com.github.kreatures.core.operators.BaseIntentionUpdateOperator;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;

/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class SwarmIntentionUpdateOperator extends BaseIntentionUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);
	
	@Override
	protected PlanElement processImpl(FilterParameter params) {
			
//		NewAgent agent=(NewAgent)params.getAgent();
//		SwarmDesires swarmDesires=(SwarmDesires) agent.getComponent(SwarmDesires.class);
		PlanComponent currentPlan=(PlanComponent)params.getActualPlan();
		if(currentPlan==null)
			return null;
		
		LOG.info("Run Default-Intention-Update");
		for(Subgoal plan : currentPlan.getPlans()) {
			for(int i=0; i<plan.getNumberOfStacks(); ++i) {
				PlanElement pe = plan.peekStack(i);
				if(pe.isAtomic()) {
					params.report(String.format("%s is the next atomic step candidate", pe.toString()));
					return pe;
				}
			}
		}
		params.report("No atomic step candidate found.");
		return null;
	}
	/**
	 * 
	 * @param params parameter about the current plan and 
	 * 				the running agent. 
	 */
	private void choiceDesire(FilterParameter params) {
		
	}
	
	@Override
	protected void prepare(FilterParameter params) {
		
	}
}
