package com.github.kreatures.swarm.beliefbase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.logic.BaseChangeBeliefs;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.operators.parameter.ChangeBeliefbaseParameter;
import com.github.kreatures.swarm.predicates.SwarmPredicate;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.translators.aspnlp.AspNlpTranslator;
import net.sf.tweety.lp.nlp.syntax.NLPProgram;
import net.sf.tweety.lp.nlp.syntax.NLPRule;

public class SwarmAspChangeBeliefs extends BaseChangeBeliefs {

	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmAspChangeBeliefs.class);
	
	private static AspNlpTranslator translator = new AspNlpTranslator();
	
	public SwarmAspChangeBeliefs() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBeliefbase processImpl(ChangeBeliefbaseParameter in) {
		BaseBeliefbase newBelief=in.getNewKnowledge();
		BaseBeliefbase callerBelief=in.getSourceBeliefBase();
		try {
			List<String> listBeliefCaller=callerBelief.getAtomsAsStringList();
			Set<SwarmPredicate> setPredicate=TransformPredicates.getSetPredicate(listBeliefCaller);
			Set<SwarmPredicate> newSetPredicate=new HashSet<>();
			boolean test=false;
			for(String atom:newBelief.getAtomsAsStringList()) {
				SwarmPredicate swarmPredicate=TransformPredicates.getPredicate(atom) ;
				
				test=setPredicate.remove(getEquivalentPredicate(setPredicate,swarmPredicate));
				newSetPredicate.add(swarmPredicate);
			}
			if(!newSetPredicate.isEmpty()) {
				setPredicate.addAll(newSetPredicate);
			}
			return translateFOL(callerBelief, TransformPredicates.getSetFolFormula(setPredicate));
		}catch(Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Translates the given set of FOL formula in a BaseBeliefbase that only contains the knowledge
	 * encoded in the set.
	 * @param caller		The BaseBeliefbase which acts as caller.
	 * @param formulas		A set of FOL formulas representing the knowledge
	 * @return				A belief base containing the knowledge encoded in the set.
	 */
	protected BaseBeliefbase translateFOL(BaseBeliefbase caller, Set<FolFormula> formulas) {
			NLPProgram program = new NLPProgram();
			for(FolFormula f : formulas) {
				program.add(new NLPRule(f));
			}
			((AspBeliefbase)caller).setProgram(translator.toASP(program));
			return caller;
	}
		
	@Override
	public Class<? extends BaseBeliefbase> getSupportedBeliefbase() {
		// TODO Auto-generated method stub
		return FolBeliefbase.class;
	}
	
	@SuppressWarnings("unchecked")
	protected <E extends SwarmPredicate> E getEquivalentPredicate(Set<SwarmPredicate> setPredicate, SwarmPredicate predicate) {
		
		for(SwarmPredicate result: setPredicate ) {
			String strResult=result.getPredicatType();
			String strPredicate=predicate.getPredicatType();
			if(strPredicate.equals(strResult) && result.equals(predicate))
				return (E)result;
		}
		
		return null;
	}

}
