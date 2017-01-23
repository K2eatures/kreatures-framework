package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.basic.SwarmAction;

public class ExecuteParameter extends OperatorPluginParameter {

	private SwarmAction action;
	
	/** Default Ctor: Used for dynamic instantiation */
	public ExecuteParameter() {}
	
	public ExecuteParameter(Agent caller) {
		super(caller);
		action=null;
	}
	
	public ExecuteParameter(Agent caller, SwarmAction action) {
		super(caller);
		this.action=action;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("action");
		if(obj==null || !(obj instanceof SwarmAction)) {
			throw conversionException("action",SwarmAction.class);
		}
		this.action=(SwarmAction)obj;
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof ExecuteParameter))	
			return false;
		
		ExecuteParameter other=(ExecuteParameter)otherObject;
		
		if(other.action!=null) {
			if(!other.action.equals(this.action))
				return false;
		}else if(this.action!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return Utility.computeHashCode(super.hashCode(),action);
	}

	public SwarmAction getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return (action==null?"no actions":action.toString());
	}
}
