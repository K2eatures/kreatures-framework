package com.github.kreatures.swarm.operators;

/**
 * List of Default Desires
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.asp.solver.SolverOptions;
import com.github.kreatures.core.operators.BaseGenerateOptionsOperator;
import com.github.kreatures.core.operators.parameters.OptionsParameter;
import com.github.kreatures.core.reflection.Context;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.MainDesire;
import com.github.kreatures.swarm.basic.SwarmDesire;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.predicates.PredicateName;

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
		
		
		Context context=params.getAgent().getContext();
//		boolean obj=(Boolean)context.get(SwarmContextConst._EVALUTED);
		SwarmDesires swarmDesires=params.getAgent().getComponent(SwarmDesires.class);
		int numberDesires=swarmDesires.getDesires().size();
		if(numberDesires==0) {
			/* The Desires are stations */
			LOG.info("Generate new desires");
			swarmDesires.setCurrentMainDesire(MainDesire.STATION_CHOICE);
			String filter=String.format("%s%s",PredicateName.Station);
			context.set(SwarmContextConst._FILTER,filter);
			return 0;
		}
		LOG.info("Use current desires");
		swarmDesires.getDesires().stream().forEach(desire->{
			swarmDesires.setCurrentDesire((SwarmDesire)desire);
			return;
		});
		SwarmDesire desire=swarmDesires.getCurrentDesire(); 
		if(desire!=null){
			params.report("current desire: "+desire);
		}
		return numberDesires;
	}
	
	@Override
	protected void prepare(OptionsParameter params) {
		
	}
}