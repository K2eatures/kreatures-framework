package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.basic.SwarmDesires;

public class FilterParameter extends OperatorPluginParameter {
	
	private SwarmDesires desires;

	/** Default Ctor: Used for dynamic instantiation */
	public FilterParameter() {}

	public FilterParameter(Agent caller) {
		super(caller);
		desires=null;
	}
	
	public FilterParameter(Agent caller,SwarmDesires desires) {
		super(caller);
		this.desires=desires;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("desires");
		if(obj==null || !(obj instanceof FilterParameter)) {
			throw conversionException("desires", SwarmDesires.class);
		}
		
	}
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof FilterParameter))	
			return false;
		
		FilterParameter other=(FilterParameter)otherObject;
		
		if(other.desires!=null) {
			if(!other.desires.equals(this.desires))
				return false;
		}else if(this.desires!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode(),desires);
	}

	public SwarmDesires getDesires() {
		return desires;
	}
	
	@Override
	public String toString() {
		return (desires==null?"no options":desires.toString());
	}
}
