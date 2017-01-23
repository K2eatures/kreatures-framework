package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.operators.parameters.ExecuteParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseExecuteOperator extends ExecuteOperator<NewAgent, ExecuteParameter , Boolean> {

	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseExecuteOperator.class);
	}

	@Override
	protected ExecuteParameter getEmptyParameter() {
		
		return new ExecuteParameter();
	}

	@Override
	protected Boolean defaultReturnValue() {
		return false;
	}

}
