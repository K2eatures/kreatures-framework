package com.github.kreatures.core.operators;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.operators.parameters.OptionsParameter;
import com.github.kreatures.core.util.Pair;

public abstract class BaseGenerateOptionsOperator extends GenerateOptionsOperator<NewAgent, OptionsParameter, Integer> {

	
	@Override
	public Pair<String, Class<?>> getOperationType() {
		
		return new Pair<>(OPERATION_TYPE,BaseGenerateOptionsOperator.class);
	}

	@Override
	protected OptionsParameter getEmptyParameter() {
		
		return new OptionsParameter();
	}

	@Override
	protected Integer defaultReturnValue() {
		
		return 0;
	}

}
