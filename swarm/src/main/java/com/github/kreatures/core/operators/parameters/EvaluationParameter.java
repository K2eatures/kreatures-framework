package com.github.kreatures.core.operators.parameters;

import javax.management.AttributeNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.operators.SwarmEvaluationOptionsOperator;

/**
 * This class contains all logic programs. That means: 
 * <li>
 * 		<ul>actuel world beliefbase,</ul>
 * 		<ul>the scenario model beliefbase</ul>
 * 		<ul>and the environment features beliefbase.</ul>
 * </li> 
 * It is use as {@link OperatorPluginParameter} for the operator 
 * {@link SwarmEvaluationOptionsOperator}
 * 
 * @author Cedric Perez Donfack
 * @see OperatorPluginParameter
 */
public class EvaluationParameter extends OperatorPluginParameter {
	/** reference to the logging facility */
	private static Logger LOG = LoggerFactory.getLogger(EvaluationParameter.class);
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
	public EvaluationParameter() {
		
	}
	
	public EvaluationParameter(AgentAbstract caller) {
		super(caller);
		baseBeliefbase=null;
	}
	
	public EvaluationParameter(AgentAbstract caller, BaseBeliefbase baseBeliefbase) {
		super(caller);
		this.baseBeliefbase=baseBeliefbase;
	}
//	/**
//	 * @return the epistemic state of scenario model 
//	 */
//	public ScenarioModelBeliefbase getScenarioModelBB() {
//		return this.scenarioModelBB;
//	}
//	/**
//	 * @return the epistemic state of environment features 
//	 */
//	public EnvFeaturesBeliefbase getEnvFeaturesBB() {
//		return envFeaturesBB;
//	}
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("basebeliefbase");
		if(obj!=null) {
			if(!(obj instanceof BaseBeliefbase)) {
				throw conversionException("basebeliefbase",BaseBeliefbase.class);
			}
			this.baseBeliefbase=(BaseBeliefbase)obj;
		}
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof EvaluationParameter))	
			return false;
		
		EvaluationParameter other=(EvaluationParameter)otherObject;
		
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
		
		return Utility.computeHashCode(super.hashCode(),baseBeliefbase);
	}

	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}
	
	@Override
	public String toString() {
		return (baseBeliefbase==null?"no nothings to evaluate ":baseBeliefbase.toString());
	}
}
