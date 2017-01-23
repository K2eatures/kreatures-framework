package com.github.kreatures.core.operators.parameters;

import java.util.Collection;

import javax.management.AttributeNotFoundException;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.KReaturesAtom;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.swarm.Utility;

public class PerceptionParameter extends OperatorPluginParameter {
	
	private KReaturesAtom information;
	
	private Collection<Perception> perceptions;
	
	private BaseBeliefbase baseBeliefbase;
	
	/** Default Ctor: Used for dynamic instantiation */
	public PerceptionParameter() {}
	
	public PerceptionParameter(Agent caller) {
		super(caller);
	}
	public PerceptionParameter(Agent caller,KReaturesAtom intention) {
		super(caller);
		this.information=intention;
		this.baseBeliefbase=caller.getBeliefs().getWorldKnowledge();
		this.perceptions=((NewAgent)caller).getPerceptions();
	}
	public PerceptionParameter(Agent caller,BaseBeliefbase beliefs,KReaturesAtom intention) {
		super(caller);
		this.baseBeliefbase=beliefs;
		this.information=intention;
		this.perceptions=((NewAgent)caller).getPerceptions();
	}
	
	public PerceptionParameter(Agent caller,BaseBeliefbase beliefs,KReaturesAtom intention, Collection<Perception> perceptions) {
		super(caller);
		this.baseBeliefbase=beliefs;
		this.information=intention;
		this.perceptions=perceptions;
	}
	
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("information");
		if(obj != null ) {
			if(! (obj instanceof BaseBeliefbase)) {
				throw conversionException("information", KReaturesAtom.class);
			}
			this.information=(KReaturesAtom)obj;
		}
		
		
		obj=param.getParameter("beliefs");
		if(obj != null ) {
			if(! (obj instanceof BaseBeliefbase)) {
				throw conversionException("beliefs", BaseBeliefbase.class);
			}
			this.baseBeliefbase=(BaseBeliefbase)obj;
		}
		
		
		
	}
//	@SuppressWarnings({ "null", "unused" })
	@Override
	public boolean equals(Object otherObject) {
		
		if(otherObject==null || !(otherObject instanceof PerceptionParameter))	
			return false;
		
		PerceptionParameter other=(PerceptionParameter)otherObject;
		
		if(other.information!=null) {
			if(!other.information.equals(this.information))
				return false;
		}else if(this.information!=null) {
			return false;
		}
		
		if(other.baseBeliefbase!=null) {
			if(!other.baseBeliefbase.equals(this.baseBeliefbase))
				return false;
		}else if(this.baseBeliefbase!=null) {
			return false;
		}
		
		if(other.perceptions!=null) {
			if(!other.perceptions.equals(this.perceptions))
				return false;
		}else if(this.perceptions!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode(),information,baseBeliefbase,perceptions);
	}

	public KReaturesAtom getInformation() {
		return information;
	}

	public Collection<Perception> getPerceptions() {
		return perceptions;
	}

	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}
	
	@Override
	public String toString() {
		return (perceptions==null?"no perceptions":perceptions.toString());
	}
}
