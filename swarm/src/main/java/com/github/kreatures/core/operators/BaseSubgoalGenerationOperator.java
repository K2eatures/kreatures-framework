package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.operators.parameters.PlanParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseSubgoalGenerationOperator extends
		SubgoalGenerationOperator<NewAgent, PlanParameter, PlanElement> {

	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseSubgoalGenerationOperator.class);
	}

	@Override
	protected PlanParameter getEmptyParameter() {
		
		return new PlanParameter();
	}

	@Override
	protected PlanElement defaultReturnValue() {
		
		return null;
	}

}
