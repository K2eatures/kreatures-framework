package com.github.kreatures.core.operators;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;


public abstract class IntentionUpdateOperator<TCaller extends Agent, IN extends OperatorPluginParameter, OUT> extends Operator<TCaller, IN, OUT> {

	public static final String OPERATION_TYPE = "IntentionsUpdate";

}
