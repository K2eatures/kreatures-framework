package com.github.kreatures.core.logic;

import java.util.Set;

import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.operators.parameter.BeliefbasePluginParameter;
import com.github.kreatures.core.util.Pair;

import net.sf.tweety.logics.fol.syntax.FolFormula;


public class EnvFeaturesBeliefbase extends AspBeliefbase{

	public EnvFeaturesBeliefbase() {}
	
	public EnvFeaturesBeliefbase(AspBeliefbase other) {
		super(other);
	}

	
	public Set<FolFormula> infere(BeliefbasePluginParameter beliefbaseParams) {
		@SuppressWarnings("unchecked")
		Pair<Set<FolFormula>, KReaturesAnswer> reval = (Pair<Set<FolFormula>, KReaturesAnswer>) getReasoningOperator()
				.process(beliefbaseParams);
		return reval.first;
	}
	
}
