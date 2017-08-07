package com.github.kreatures.swarm.operators;


import java.util.Set;

/**
 * List of Default Desires
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.asp.solver.SolverOptions;
import com.github.kreatures.core.operators.BaseGenerateOptionsOperator;
import com.github.kreatures.core.operators.parameters.OptionsParameter;
import com.github.kreatures.swarm.basic.SwarmDesires;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */

public class SwarmGenerateOptionsOperator extends BaseGenerateOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(SwarmGenerateOptionsOperator.class);

	@Override
	protected Integer processImpl(OptionsParameter params) {
		
		NewAgent agent=(NewAgent)params.getAgent();
		boolean obj=(Boolean)agent.getContext().get("evaluated");
		int numberDesires=0;
		if(!obj) {
			/* The Desires are stations */
			String filter=String.format("%s%s", SolverOptions.FILTER,SolverOptions.STATION);
			agent.getContext().set("filter",filter);
			return numberDesires;
		}
		
		Object objSwarmPredicate=agent.getContext().get("desires");
		if(objSwarmPredicate!=null) {
			numberDesires=((SwarmDesires)objSwarmPredicate).size();
		}
		
		
		 
		
		return numberDesires;
	}
	@Override
	protected void prepare(OptionsParameter params) {
		
	}
}