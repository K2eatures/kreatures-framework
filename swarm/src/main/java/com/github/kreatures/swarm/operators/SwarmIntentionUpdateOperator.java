package com.github.kreatures.swarm.operators;



import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* List of Default Desires
*/
import static com.github.kreatures.swarm.basic.SwarmDesires_old.WANT_TO_MOVE;
import static com.github.kreatures.swarm.basic.SwarmDesires_old.IS_VISIT;
import static com.github.kreatures.swarm.basic.SwarmDesires_old.WANT_TO_LEAVE;
import static com.github.kreatures.swarm.basic.SwarmDesires_old.WANT_TO_VISIT;
import static com.github.kreatures.swarm.basic.SwarmDesires_old.isEqual;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.Desire;
import com.github.kreatures.core.Intention;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.operators.OperatorCallWrapper;
import com.github.kreatures.core.operators.parameter.EvaluateParameter;
import com.github.kreatures.secrecy.operators.BaseIntentionUpdateOperator;
import com.github.kreatures.secrecy.operators.BaseViolatesOperator;
import com.github.kreatures.secrecy.operators.ViolatesResult;
import com.github.kreatures.secrecy.operators.parameter.PlanParameter;
import com.github.kreatures.simple.operators.FilterOperator;
import com.github.kreatures.swarm.beliefbase.SwarmUpdateBeliefsOperator;
//import com.github.kreatures.swarm.components.DefaultStation;
import com.github.kreatures.swarm.components.StatusAgentComponents;
//import com.github.kreatures.swarm.components.SwarmMappingGeneric;

public class SwarmIntentionUpdateOperator extends BaseIntentionUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmUpdateBeliefsOperator.class);
	
	@Override
	protected PlanElement processImpl(PlanParameter pParameters) {
		LOG.info("Run Default-Intention-Update");
		
		List<Subgoal> infer= pParameters.getActualPlan().getPlans();
		
		for(Subgoal plan : infer) {
			for(int i=0; i<plan.getNumberOfStacks(); ++i) {
				PlanElement pe = plan.peekStack(i);
				if(check(pParameters, pe)) {
					return pe;
				}
			}
		}
		pParameters.report("No atomic step candidate found.");
		return null;
	}
	
	
	/**
	 * 
	 * @param param
	 * @param pe
	 * @return
	 */
	protected boolean check(PlanParameter param, PlanElement pe) {
		Intention intention = pe.getIntention();
		Agent ag = param.getAgent();
		if(pe.getIntention().isAtomic()) {
			
			if(intention.isAtomic()) {
				boolean select = Boolean.parseBoolean(param.getSetting("allowUnsafe", String.valueOf(false)));
				
				if(!select) {
					OperatorCallWrapper op = ag.getOperators().getPreferedByType(BaseViolatesOperator.OPERATION_NAME);
					EvaluateParameter eparam = new EvaluateParameter(ag, ag.getBeliefs(), pe);
					select = ((ViolatesResult)op.process(eparam)).isAlright();
					if(select) {
						param.report("Mental action successful, using '" + intention.toString() + "' as next atomic action.");
					}
				}
				
				return select;
			}
		}
		return false;
	}
}
