package com.github.kreatures.swarm.beliefbase;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.Agent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.Desire;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.operators.BaseUpdateBeliefsOperator;
import com.github.kreatures.core.operators.parameter.EvaluateParameter;
import com.github.kreatures.swarm.comm.SwarmInform;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * 
 * @author donfack
 *
 */
public class SwarmUpdateBeliefsOperator extends BaseUpdateBeliefsOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmUpdateBeliefsOperator.class);
	
	@Override
	protected Beliefs processImpl(EvaluateParameter objParameter) {
		LOG.info("Run Swarm-Update-Beliefs-operator");
		
		Beliefs beliefs = objParameter.getBeliefs();
		//Beliefs oldBeliefs = (Beliefs) objParameter.getBeliefs().clone();
		BaseBeliefbase bb=null;
		String out="";
		
		boolean receiver=false;
		if(objParameter.getAtom() instanceof Action){
			Action action=(Action)objParameter.getAtom();
			receiver=!action.getSenderId().equals(objParameter.getAgent().getName());
		}else{
			receiver=true;
		}
						
		if (objParameter.getAtom() instanceof SwarmInform) {
			SwarmInform objInform = (SwarmInform) objParameter.getAtom();
			bb.addKnowledge(objInform);
			out = "Inform as ";
			if(receiver) {
				bb = beliefs.getWorldKnowledge();
				bb.addKnowledge(objInform);
				objParameter.report(out + "receiver adapt world knowledge", bb);
				
				bb = beliefs.getViewKnowledge().get(objInform.getSenderId());
				bb.addKnowledge(objInform);
				objParameter.report(out + "receiver adapt view on '" + objInform.getSenderId() + "'", bb);
			} else {
				bb = beliefs.getViewKnowledge().get(objInform.getReceiverId());
				bb.addKnowledge(objInform);
				objParameter.report(out + "sender adapt view on '" + objInform.getReceiverId() + "'", bb);
			}			
		}
		
		
		return beliefs;
	}

}
