package com.github.kreatures.extraction.operators;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.operators.Operator;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.core.util.Pair;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class UpdateOperator extends Operator<Agent, OperatorPluginParameter, Void> {

	public static final String OPERATION_TYPE = "Update";

	@Override
	public Pair<String, Class<?>> getOperationType() {
		return new Pair<String, Class<?>>(OPERATION_TYPE, UpdateOperator.class);
	}

	@Override
	protected OperatorPluginParameter getEmptyParameter() {
		return new OperatorPluginParameter();
	}

	@Override
	protected Void processImpl(OperatorPluginParameter preprocessedParameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Void defaultReturnValue() {
		return null;
	}

}