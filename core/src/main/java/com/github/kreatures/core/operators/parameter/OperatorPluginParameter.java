package com.github.kreatures.core.operators.parameter;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.OperatorStack;
import com.github.kreatures.core.report.Reporter;

/**
 * Base class for all input parameters for operators defined in the
 * operator plugin.
 * 
 * @author Tim Janus
 */
public class OperatorPluginParameter extends OperatorParameterAdapter {

	/** the agent who calls the operator */
	private AgentAbstract caller;
	
	/** Default Ctor: Used for dynamic instantiation */
	public OperatorPluginParameter() {}
	
	public OperatorPluginParameter(AgentAbstract caller) {
		if(caller == null) {
			throw new IllegalArgumentException("Caller most not be null.");
		}
		this.caller = caller;
	}
	
	@Override
	public Reporter getReporter() {
		return caller;
	}	
	
	/** @return the agent who class the operator */
	public AgentAbstract getAgent() {
		return caller;
	}

	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		if(! (param.getCaller() instanceof AgentAbstract) ) {
			throw conversionException("caller", AgentAbstract.class);
		}
		this.caller = (AgentAbstract)param.getCaller();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof OperatorPluginParameter))
			return false;
		
		OperatorPluginParameter co = (OperatorPluginParameter)other;
		return super.equals(this) && co.caller == this.caller;
	}
	
	@Override
	public int hashCode() {
		return caller.hashCode() + 5;
	}

	@Override
	public OperatorStack getStack() {
		return caller;
	}
}
