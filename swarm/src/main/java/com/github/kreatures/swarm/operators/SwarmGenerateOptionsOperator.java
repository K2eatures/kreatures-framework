package com.github.kreatures.swarm.operators;



import com.github.kreatures.core.Agent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.Desire;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.internal.IdGenerator;
import com.github.kreatures.core.logic.Desires;

/**
 * List of Default Desires
 */
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.secrecy.operators.BaseGenerateOptionsOperator;
import com.github.kreatures.secrecy.operators.parameter.GenerateOptionsParameter;
import com.github.kreatures.swarm.basic.SwarmDesires;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * 
 * @author donfack
 *
 */

public class SwarmGenerateOptionsOperator extends BaseGenerateOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory.getLogger(SwarmGenerateOptionsOperator.class);
	
	
	public static final Predicate prepareQueryProcessing = new Predicate("queryProcessing", 1);
	
	public static final Predicate prepareRevisionRequestProcessing = new Predicate("revisionRequestProcessing", 1);
	
	public static final Predicate prepareReasonCalculation = new Predicate("reasonProcessing", 1);
	
	public static final Predicate prepareScriptingProcessing = new Predicate("scriptingProcessing", 0);
	
	public static final Predicate prepareJustificationReaction = new Predicate("justificationProcessing", 1);
	
	public static final IdGenerator desireIds = new IdGenerator();
	
	
	@Override
	protected Integer processImpl(GenerateOptionsParameter parameters) {
		LOG.info("Run Swarm-Generate-Options-operator");
		
		//FOLAtom ad = null;
		Set<Desire> reval = new HashSet<Desire>();
		
		Desires desires=parameters.getAgent().getComponent(Desires.class);
		Desire desire=getDesire(desires);
		
		reval.add(desire);
		
		
		parameters.report("Agent want to " + desire.toString());
		parameters.getAgent().getComponent(Desires.class).clear();
		
		Agent ag=parameters.getAgent();
		BaseBeliefbase bb=ag.getBeliefs().getWorldKnowledge();
		Set<FolFormula> infer=bb.infere();
		
		for(FolFormula folFormula:infer){
			Desire des=new Desire(folFormula);
			for(Predicate predicate: des.getFormula().getPredicates()){
				if(predicate.getName().contains("station")){
					ag.getPlanComponent().addPlan(new Subgoal(ag, des));
					break;
				};
				
			}
			
		}
		
		return reval.size();
	}


private Desire getDesire(Desires desires){
	Desire desire=null;
	
	for(Desire desire1 :desires.getDesires()){
		if(SwarmDesires.isEqual(SwarmDesires.CHOICE_STATION, desire1)){
			desire=SwarmDesires.CHOICE_STATION;
			break;
		}
		
		if(SwarmDesires.isEqual(SwarmDesires.CHANGE_STATION, desire1)){
			desire=SwarmDesires.CHANGE_STATION;
			break;
		}
		
		if(SwarmDesires.isEqual(SwarmDesires.STAY_STATION, desire1)){
			desire=SwarmDesires.STAY_STATION;
			break;
		}
		if(SwarmDesires.isEqual(SwarmDesires.QUIT_STATION, desire1)){
			desire=SwarmDesires.QUIT_STATION;
			break;
		}
	}
	
	return desire;
}

}




