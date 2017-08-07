package com.github.kreatures.core.operators;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;

public abstract class BeliefsUpdateOperator<TCaller extends AgentAbstract, IN extends OperatorPluginParameter, OUT>
		extends Operator<TCaller, IN, OUT> {
	public static final String OPERATION_TYPE = "BeliefsUpdateKR";

}
