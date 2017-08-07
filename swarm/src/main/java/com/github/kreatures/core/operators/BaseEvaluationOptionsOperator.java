package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.operators.parameters.EvaluationParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseEvaluationOptionsOperator extends EvaluationOptionsOperator<NewAgent, EvaluationParameter, Boolean> {

	
	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseEvaluationOptionsOperator.class);
	}

	@Override
	protected EvaluationParameter getEmptyParameter() {
		
		return new EvaluationParameter();
	}

	@Override
	protected Boolean defaultReturnValue() {
		
		return false;
	}

}
