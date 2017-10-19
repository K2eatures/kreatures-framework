package com.github.kreatures.core.operators.parameters;

import java.util.Collection;

import javax.management.AttributeNotFoundException;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.KReaturesAtom;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.basic.SwarmInformation;

public class PerceptionParameter extends OperatorPluginParameter {
	
//	private KReaturesAtom information;
	
	private Collection<Perception> informations;
	
	private BaseBeliefbase baseBeliefbase;
	
	/** Default Ctor: Used for dynamic instantiation */
	public PerceptionParameter() {}
	
	public PerceptionParameter(AgentAbstract caller) {
		this(caller,caller.getBeliefs().getWorldKnowledge(),((NewAgent)caller).getPerceptions());
	}

	public PerceptionParameter(AgentAbstract caller,BaseBeliefbase beliefs) {
		this(caller,beliefs,((NewAgent)caller).getPerceptions());
	}
	
	public PerceptionParameter(AgentAbstract caller,BaseBeliefbase beliefs, Collection<Perception> informations) {
		super(caller);
		this.baseBeliefbase=beliefs;
		this.informations=informations;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("information");
		if(obj != null ) {
			if(! (obj instanceof SwarmInformation)) {
				throw conversionException("information", SwarmInformation.class);
			}
			this.informations=((SwarmInformation)obj).getPerceptions();
		}
		
		obj=param.getParameter("basebeliefbase");
		if(obj != null ) {
			if(! (obj instanceof BaseBeliefbase)) {
				throw conversionException("basebeliefbase", BaseBeliefbase.class);
			}
			this.baseBeliefbase=(BaseBeliefbase)obj;
		}else {
			if(getAgent().getBeliefs()!=null) {
				this.baseBeliefbase=getAgent().getBeliefs().getWorldKnowledge();
			}else {
				this.baseBeliefbase=null;
			}
					
		}
	}
//	@SuppressWarnings({ "null", "unused" })
	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof PerceptionParameter)) return false;
		
		PerceptionParameter obj=(PerceptionParameter)other;
		
		boolean isInfo=this.informations==null?this.informations==null:this.informations.equals(obj.informations);
		boolean isBb=this.baseBeliefbase==null?this.baseBeliefbase==null:this.baseBeliefbase.equals(obj.baseBeliefbase);
		return isInfo & isBb;
	}
	
	@Override
	public int hashCode() {
		return 11;
	}

	public Collection<Perception> getPerceptions() {
		return informations;
	}

	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}
	
	@Override
	public String toString() {
		return (informations==null?"no perceptions":informations.toString());
	}
}
