package com.github.kreatures.swarm.beliefbase;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.logic.BaseChangeBeliefs;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.parameter.ChangeBeliefbaseParameter;

public class SwarmAspChangeBeliefs extends BaseChangeBeliefs {

	public SwarmAspChangeBeliefs() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected BaseBeliefbase processImpl(ChangeBeliefbaseParameter in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends BaseBeliefbase> getSupportedBeliefbase() {
		// TODO Auto-generated method stub
		return FolBeliefbase.class;
	}

}
