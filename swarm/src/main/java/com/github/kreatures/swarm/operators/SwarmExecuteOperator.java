package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.operators.BaseExecuteOperator;
import com.github.kreatures.core.operators.parameters.ExecuteParameter;

public class SwarmExecuteOperator extends BaseExecuteOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmExecuteOperator.class);

	@Override
	protected Boolean processImpl(ExecuteParameter preprocessedParameters) {
		// TODO Auto-generated method stub
		if(preprocessedParameters!=null){
			LOG.info("SwarmExecuteOperator   ->"+preprocessedParameters.toString());
		}else{
			LOG.info("SwarmExecuteOperator");
		}
		return null;
	}
	@Override
	protected void prepare(ExecuteParameter params) {
		
	}
}