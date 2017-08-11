package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.basic.SwarmDesires;

public class FilterParameter extends OperatorPluginParameter {
	/** the actual working goals */
	private PlanComponent currentPlan;

	/** Default Ctor: Used for dynamic instantiation */
	public FilterParameter() {}

	public FilterParameter(PlanComponent component) {
		super(component.getAgent());
		this.currentPlan=component;
	}
	
	/** 
	 * 
	 * @return the PlanComponent of the calling Agent 
	 */
	public PlanComponent getActualPlan() {
		return currentPlan;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter(SwarmContextConst._PLAN);
		if(obj != null) {
			if(!(obj instanceof PlanComponent)) {
				throw conversionException(SwarmContextConst._PLAN, SwarmDesires.class);
			}
			this.currentPlan= (PlanComponent)obj;
		}		
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof FilterParameter))	
			return false;
		
		FilterParameter other=(FilterParameter)otherObject;
		
		if(other.currentPlan!=null) {
			if(!other.currentPlan.equals(this.currentPlan))
				return false;
		}else if(this.currentPlan!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode(),currentPlan);
	}
	
	@Override
	public String toString() {
		return (currentPlan==null?"no plan":currentPlan.toString());
	}
}
