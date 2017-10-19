package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.SwarmPlanComponent;
import com.github.kreatures.core.operators.parameters.FilterParameter;
import com.github.kreatures.core.util.Pair;
import com.github.kreatures.core.operators.BaseIntentionUpdateOperator;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;

/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class SwarmIntentionUpdateOperator extends BaseIntentionUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);
//	/** get all station with max free space	 */
//	private static final String _MaxFreeSpace="maxFreeSpace";
//	/** get all station with max space	 */
//	private static final String _MaxSpace="maxSpace";
	
	@Override
	protected PlanElement processImpl(FilterParameter params) {
			
//		NewAgent agent=(NewAgent)params.getAgent();
//		SwarmDesires swarmDesires=(SwarmDesires) agent.getComponent(SwarmDesires.class);
		SwarmPlanComponent currentPlan=(SwarmPlanComponent)params.getActualPlan();
		/* List of desires and related informations */
		if(currentPlan==null)
			return null;
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		Pair<Boolean,PlanElement> checkEnter=desires.getCheckEnterStation();
		LOG.info("Run Default-Intention-Update");
		if(checkEnter.first) {
			PlanElement pe=checkEnter.second;
			params.report(String.format("%s is the next atomic step candidate", ((
					SwarmSpeechAct)pe.getIntention()).getActionTyp()));
			return checkEnter.second;
		}else
		for(Subgoal plan : currentPlan.getPlans()) {
			for(int i=0; i<plan.getNumberOfStacks(); ++i) {
				PlanElement pe = plan.peekStack(i);
				if(pe.isAtomic()) {
					params.report(String.format("%s is the next atomic step candidate", ((
							SwarmSpeechAct)pe.getIntention()).getActionTyp()));
					checkEnter.second=pe;
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
//	private void choiceDesire(FilterParameter params) {
//		
//	}
	
	@Override
	protected void prepare(FilterParameter params) {
		
	}
}
