package com.github.kreatures.swarm.operators;

/**
 * List of Default Desires
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.operators.BaseGenerateOptionsOperator;
import com.github.kreatures.core.operators.parameters.OptionsParameter;


/**
 * 
 * @author donfack
 *
 */

public class SwarmGenerateOptionsOperator extends BaseGenerateOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(SwarmGenerateOptionsOperator.class);

	@Override
	protected Integer processImpl(OptionsParameter preprocessedParameters) {
		if(preprocessedParameters!=null){
			LOG.info("SwarmGenerateOptionsOperator  ->"+preprocessedParameters.toString());
		}else{
			LOG.info("SwarmGenerateOptionsOperator");
		}
		
		return 0;
	}
	@Override
	protected void prepare(OptionsParameter params) {
		
	}
}