package com.github.kreatures.swarm.beliefbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;


/**
 * 
 * @author donfack
 *
 */
public class SwarmBeliefsUpdateOperator extends BaseBeliefsUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);
	
	@Override
	protected Beliefs processImpl(PerceptionParameter objParameter) {
		
			// TODO Auto-generated method stub
			if(objParameter!=null){
				LOG.info("SwarmBeliefsUpdateOperator  ->"+objParameter.toString());
			}else{
				LOG.info("SwarmBeliefsUpdateOperator");
			}
		return null;
	}

}
