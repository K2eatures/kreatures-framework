package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.simple.operators.ExecuteOperator;
import com.github.kreatures.swarm.beliefbase.SwarmUpdateBeliefsOperator;

public class SwarmExecuteOperator extends ExecuteOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmExecuteOperator.class);
}