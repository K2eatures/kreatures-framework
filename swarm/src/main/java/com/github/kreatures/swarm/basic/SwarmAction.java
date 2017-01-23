package com.github.kreatures.swarm.basic;

import com.github.kreatures.core.Intention;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.swarm.Utility;

import net.sf.tweety.logics.commons.syntax.interfaces.SimpleLogicalFormula;


/**
 * 
 * @author donfack
 *
 */
/*must extends PlanElement*/
public class SwarmAction extends PlanElement {
		
	public SwarmAction(Intention intention) {
		super(intention, null, null, 0);
	}
	
	
	public SwarmAction(Intention intention, Object executionData) {
		super(intention, executionData, null, 0);
	}
	
	
	public SwarmAction(Intention intention, Object executionData, Object userData) {
		super(intention, executionData, userData, 0);
	}
	
	
	
	public SwarmAction(Intention intention, Object executionData, Object userData, double cost) {
		super(intention,executionData,userData,cost);
	}
	
	
	public SwarmAction(PlanElement other) {
		super(other);
	}
	
	@Override
	public String toString() {
		return getIntention().toString();
	}  
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode());
	}
}


