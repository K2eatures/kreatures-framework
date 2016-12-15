/**
 * 
 */
package com.github.kreatures.swarm.beliefbase;

import java.util.Set;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.logic.BaseReasoner;
import com.github.kreatures.core.logic.KReaturesAnswer;
import com.github.kreatures.core.logic.asp.AspReasoner;
import com.github.kreatures.core.operators.parameter.ReasonerParameter;
import com.github.kreatures.core.util.Pair;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * @author donfack
 *
 */
public class SwarmAspReasoner extends BaseReasoner {

	/**
	 * @throws InstantiationException
	 */
	public SwarmAspReasoner() throws InstantiationException {
		super();
	}

	@Override
	protected Set<FolFormula> inferImpl(ReasonerParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Pair<Set<FolFormula>, KReaturesAnswer> queryImpl(ReasonerParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends BaseBeliefbase> getSupportedBeliefbase() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
