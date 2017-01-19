package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.operators.BaseSubgoalGenerationOperator;
import com.github.kreatures.core.operators.parameters.PlanParameter;


public class SwarmSubgoalGenerationOperator extends
		BaseSubgoalGenerationOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory
			.getLogger(SwarmSubgoalGenerationOperator.class);

	@Override
	protected Boolean processImpl(PlanParameter preprocessedParameters) {
		// TODO Auto-generated method stub
		if(preprocessedParameters!=null){
			LOG.info("SwarmSubgoalGenerationOperator  ->"+preprocessedParameters.toString());
		}else{
			LOG.info("SwarmSubgoalGenerationOperator");
		}
		return null;
	}

}