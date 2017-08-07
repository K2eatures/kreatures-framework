package com.github.kreatures.swarm.operators;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseEvaluationOptionsOperator;
import com.github.kreatures.core.operators.parameters.BaseReasonerParameter;
import com.github.kreatures.core.operators.parameters.EvaluationParameter;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.lp.asp.syntax.Program;

public class SwarmEvaluationOptionsOperator extends BaseEvaluationOptionsOperator {
	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(SwarmEvaluationOptionsOperator.class);

	
	@Override
	protected Boolean processImpl(EvaluationParameter params) {
		boolean check=false;
		// get a copy of the agent.
		NewAgent agent=(NewAgent)params.getAgent();
		// get the given filter in the agent context
		String query=(String)agent.getContext().get("filter");
		// get a object of FolBeliefbase
		FolBeliefbase folBB=(FolBeliefbase)params.getBaseBeliefbase();
		// keep a object of FolBeliefbase program
		Program oldBBProgram =folBB.getProgram();
		// get a object of scenario model and environment features program
		Program bbProgram=params.getScenarioModelAndEnFeaturesBB();
		bbProgram.add(new Program(oldBBProgram));
		folBB.setProgram(bbProgram);
		//TODO Hier weiter
		BaseReasonerParameter brParams=new BaseReasonerParameter(folBB,query);
		Set<FolFormula> result=folBB.infere(brParams);
		if(result!=null) {
			agent.getContext().set("desires", new SwarmDesires(TransformPredicates.getSetPredicat(result)));
			LOG.info("available Desires are:"+result);
			check=true;
		}else {
			LOG.info("no available Desires");
		}
		
		folBB.setProgram(oldBBProgram);
		return check;
	}
	
	@Override
	protected void prepare(EvaluationParameter params) {
		
	}

}
