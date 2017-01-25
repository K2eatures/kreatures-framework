package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;

public class OptionsParameter extends OperatorPluginParameter {
	
	private BaseBeliefbase baseBeliefbase;

	/** Default Ctor: Used for dynamic instantiation */
	public OptionsParameter() {	}

	public OptionsParameter(AgentAbstract caller) {
		super(caller);
		this.baseBeliefbase=caller.getBeliefs().getWorldKnowledge();
	}
	
	public OptionsParameter(AgentAbstract caller, BaseBeliefbase baseBeliefbase) {
		super(caller);
		this.baseBeliefbase=baseBeliefbase;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj = param.getParameter("basebeliefbase");
		if(obj != null) {
			if(! (obj instanceof BaseBeliefbase)) {
				throw conversionException("basebeliefbase", BaseBeliefbase.class);
			}
			this.baseBeliefbase= (BaseBeliefbase)obj;
		}
	}
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof OptionsParameter))	
			return false;
		
		OptionsParameter other=(OptionsParameter)otherObject;
		
		if(other.baseBeliefbase!=null) {
			if(!other.baseBeliefbase.equals(this.baseBeliefbase))
				return false;
		}else if(this.baseBeliefbase!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return Utility.computeHashCode(super.hashCode(),this.baseBeliefbase);
	}

	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}
	@Override
	public String toString() {
		return (baseBeliefbase==null?"no updated Beliefbase.":"updated Beliefbase");
	}
}
