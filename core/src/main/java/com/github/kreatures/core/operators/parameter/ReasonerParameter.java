package com.github.kreatures.core.operators.parameter;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.error.ConversionException;

public class ReasonerParameter extends BeliefbasePluginParameter {
	
	private FolFormula query;
	
	public ReasonerParameter() {}
	
	public ReasonerParameter(BaseBeliefbase beliefbase) {
		this(beliefbase, null);
	}
	
	public ReasonerParameter(BaseBeliefbase beliefbase, FolFormula query) {
		super(beliefbase);
		this.query = query;
	}

	public FolFormula getQuery() {
		return query;
	}
}