package com.github.kreatures.swarm.operators;

import java.util.Set;

/**
 * List of Default Desires
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.asp.solver.SolverOptions;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseGenerateOptionsOperator;
import com.github.kreatures.core.operators.parameters.BaseReasonerParameter;
import com.github.kreatures.core.operators.parameters.OptionsParameter;

import net.sf.tweety.logics.fol.syntax.FolFormula;


/**
 * 
 * @author donfack
 *
 */

public class SwarmGenerateOptionsOperator extends BaseGenerateOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(SwarmGenerateOptionsOperator.class);

	@Override
	protected Integer processImpl(OptionsParameter params) {
		
		FolBeliefbase folBB=(FolBeliefbase)params.getBaseBeliefbase();
		
		 String query=String.format("%s%s", SolverOptions.FILTER,SolverOptions.TIME_EDGE_WAITING);
		 BaseReasonerParameter brParams=new BaseReasonerParameter(folBB,SolverOptions.NOFACTS,query);
		 Set<FolFormula> result=folBB.infere(brParams);
		
		 LOG.info("SwarmBeliefsUpdateOperator ->"+result.toString());
		
		return 0;
	}
	@Override
	protected void prepare(OptionsParameter params) {
		
	}
}