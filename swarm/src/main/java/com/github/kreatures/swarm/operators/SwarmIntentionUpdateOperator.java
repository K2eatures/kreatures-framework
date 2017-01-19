package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.operators.parameters.FilterParameter;
import com.github.kreatures.core.operators.BaseIntentionUpdateOperator;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;

public class SwarmIntentionUpdateOperator extends BaseIntentionUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);
	
	@Override
	protected PlanElement processImpl(
			FilterParameter preprocessedParameters) {
		// TODO Auto-generated method stub
		if(preprocessedParameters!=null){
			LOG.info("SwarmIntentionUpdateOperator  ->"+preprocessedParameters.toString());
		}else{
			LOG.info("SwarmIntentionUpdateOperator");
		}
		return null;
	}
}
