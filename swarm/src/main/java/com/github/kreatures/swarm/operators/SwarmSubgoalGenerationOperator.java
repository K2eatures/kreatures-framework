package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.Action;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.operators.BaseSubgoalGenerationOperator;
import com.github.kreatures.core.operators.parameters.PlanParameter;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.MainAction;
import com.github.kreatures.swarm.basic.SwarmAction;
import com.github.kreatures.swarm.basic.SwarmDesire;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.predicates.PredicateStation;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */

public class SwarmSubgoalGenerationOperator extends
		BaseSubgoalGenerationOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory
			.getLogger(SwarmSubgoalGenerationOperator.class);
	
	/**
	 * reference to environment component of this simulation.  
	 */
	private EnvironmentComponent envComponent; 
	{
		envComponent=AbstractSwarms.getInstance().getEnvComponent(KReatures.getInstance().getActualSimulation().getName());
	}
	
	/**
	 * the last fulfilling or following desire. 
	 */
	private SwarmDesire lastDesire=null;

	@Override
	protected Boolean processImpl(PlanParameter params) {
		
		PlanComponent plan=params.getAgent().getComponent(PlanComponent.class);
		if(plan==null)
			return false;
		
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		
		if(desires==null||desires.isEmptyDesires())
			return false;
		LOG.info("New plans will be generated.");
		SwarmDesire predicate=desires.getCurrentDesire();
		boolean hasItem=((PredicateStation)predicate).hasItem();
		if(lastDesire==null||predicate.equals(lastDesire)) {
			lastDesire=predicate;			
			
			if(hasItem){
				plan.addPlan(doVisitPlanWithItem(params));
			}else {
				plan.addPlan(doVisitPlanWithoutItem(params));
			}
			params.getAgent().getContext().set(SwarmContextConst._PLAN, plan);
			return true;
		}
		
		lastDesire=predicate;
		plan.addPlan(doMovePlan(params));			
		if(hasItem){
			plan.addPlan(doVisitPlanWithItem(params));
		}else {
			plan.addPlan(doVisitPlanWithoutItem(params));
		}
		params.getAgent().getContext().set(SwarmContextConst._PLAN, plan);
		
		return true;
	}
	
	/**
	 * This method generates a sub plan for agent. It define step by step how a agent has to
	 * walk in order to move from a station A to a station B. 
	 * @param params 
	 * @return the generated sub plan.
	 */
	private Subgoal doMovePlan(PlanParameter params) {
//		PredicatePlaceEdge place=new PredicateP
//		Subgoal subGoal=new Subgoal(params.getAgent());
		return null;
	}
	/**
	 * This method generates a sub plan for agent. It define step by 
	 * step how a agent has to do visit a station without a item.
	 * First of all, it has to enter the station. Secondly, it has 
	 * to do its job: ie do nothings. finally, it has
	 * to leave the station.
	 * @param params
	 * @return the generated sub plan.
	 */
	
	private Subgoal doVisitPlanWithoutItem(PlanParameter... params) {
		return null;
	}
	/**
	 * This method generates a sub plan for agent. It define step by 
	 * step how a agent has to do visit a station with a  item. First
	 * of all, it has to enter the station. Secondly, it has to do its
	 * job: ie take and/or placed a item. finally, it has to leave the
	 * station.
	 * @param params
	 * @return the generated sub plan.
	 */
	
	private Subgoal doVisitPlanWithItem(PlanParameter params) {
		Action aIntention=null;
		Subgoal subGoal=new Subgoal(params.getAgent(),lastDesire);
		Stack<PlanElement> stack=new Stack<>();
		aIntention=new SwarmAction(params.getAgent(),MainAction.LEAVE_STATION);
		stack.push(new PlanElement(aIntention, MainAction.LEAVE_STATION));
		aIntention=new SwarmAction(params.getAgent(),MainAction.VISIT_STATION);
		stack.push(new PlanElement(aIntention, MainAction.VISIT_STATION));
		aIntention=new SwarmAction(params.getAgent(),MainAction.ENTER_STATION);
		stack.push(new PlanElement(aIntention, MainAction.ENTER_STATION));
		
		subGoal.setStack(0, stack);
		
		return subGoal;
	}
	
	
	@Override
	protected void prepare(PlanParameter params) {
		
	}
}