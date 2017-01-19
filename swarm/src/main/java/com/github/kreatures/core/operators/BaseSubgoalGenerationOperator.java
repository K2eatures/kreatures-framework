package com.github.kreatures.core.operators;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.operators.parameters.PlanParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseSubgoalGenerationOperator extends
		SubgoalGenerationOperator<Agent, PlanParameter, Boolean> {

	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseSubgoalGenerationOperator.class);
	}

	@Override
	protected PlanParameter getEmptyParameter() {
		
		return new PlanParameter();
	}

	@Override
	protected Boolean defaultReturnValue() {
		
		return false;
	}

}
