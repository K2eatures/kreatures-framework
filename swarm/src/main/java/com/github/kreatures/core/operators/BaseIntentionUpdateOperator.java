package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.operators.parameters.FilterParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseIntentionUpdateOperator extends
		IntentionUpdateOperator<NewAgent, FilterParameter, PlanElement> {

	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseIntentionUpdateOperator.class);
	}

	@Override
	protected FilterParameter getEmptyParameter() {
		
		return new FilterParameter();
	}

	@Override
	protected PlanElement defaultReturnValue() {
		
		return null;
	}

	
}
