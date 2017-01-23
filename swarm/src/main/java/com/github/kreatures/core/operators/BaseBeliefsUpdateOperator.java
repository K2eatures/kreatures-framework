package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseBeliefsUpdateOperator extends
		BeliefsUpdateOperator<NewAgent, PerceptionParameter, Beliefs> {

	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseBeliefsUpdateOperator.class);
	}

	@Override
	protected PerceptionParameter getEmptyParameter() {
		
		return new PerceptionParameter();
	}

	@Override
	protected Beliefs defaultReturnValue() {
		
		return null;
	}

}
