package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;

public class SubGoalParameter extends OperatorPluginParameter {

	private PlanComponent plan;
	
	/** Default Ctor used for empty generation */
	public SubGoalParameter() {}

	
	public SubGoalParameter(AgentAbstract caller) {
		super(caller);
		this.plan=null;
	}

	public SubGoalParameter(AgentAbstract caller,PlanComponent plan ) {
		super(caller);
		this.plan=plan;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("plan");
		if(obj != null) {
			if(!(obj instanceof PlanComponent)) {
				throw conversionException("plan", PlanComponent.class);
			}
			this.plan= (PlanComponent)obj;	
		}
		
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof PlanComponent))	
			return false;
		
		SubGoalParameter other=(SubGoalParameter)otherObject;
		
		if(other.plan!=null) {
			if(!other.plan.equals(this.plan))
				return false;
		}else if(this.plan!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode(),plan);
	}


	public PlanComponent getCurrentPlan() {
		return plan;
	}
	
	@Override
	public String toString() {
		return (plan==null?"no plans":plan.toString());
	}

}
