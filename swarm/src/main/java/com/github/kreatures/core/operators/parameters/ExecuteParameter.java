package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;

public class ExecuteParameter extends OperatorPluginParameter {

	
	private PlanElement action;
	private BaseBeliefbase baseBeliefbase;

	/**
	 * reference to environment component of this simulation.  
	 */
	private EnvironmentComponent envComponent;
	{
		envComponent=AbstractSwarms.getInstance().getEnvComponent(KReatures.getInstance().getActualSimulation().getName());
	}
	
	public EnvironmentComponent getEnvComponent() {
		return envComponent;
	}
	/** Default Ctor: Used for dynamic instantiation */
	public ExecuteParameter() {}
	
	public ExecuteParameter(AgentAbstract caller) {
		super(caller);
		action=null;
		this.baseBeliefbase=null;
	}
	
	public ExecuteParameter(AgentAbstract caller, PlanElement action,BaseBeliefbase baseBeliefbase) {
		super(caller);
		this.action=action;
		this.baseBeliefbase=baseBeliefbase;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("action");
		if(obj!=null) {
			if(!(obj instanceof PlanElement)) {
				throw conversionException("action",PlanElement.class);
			}
			this.action=(PlanElement)obj;
		}
		
		Object objBB=param.getParameter("basebeliefbase");
		if(obj!=null) {
			if(!(objBB instanceof BaseBeliefbase)) {
				throw conversionException("basebeliefbase",BaseBeliefbase.class);
			}
			this.baseBeliefbase=(BaseBeliefbase)objBB;
		}
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
		
		return Utility.computeHashCode(super.hashCode(),action);
	}

	public PlanElement getAction() {
		return action;
	}
	
	/**
	 * @return the baseBeliefbase
	 */
	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}

	@Override
	public String toString() {
		return (action==null?"no actions":action.toString());
	}
}
