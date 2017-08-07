package com.github.kreatures.swarm.beliefbase;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.swarm.basic.SwarmPerception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public class SwarmBeliefsUpdateOperator extends BaseBeliefsUpdateOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmBeliefsUpdateOperator.class);

	@Override
	protected BaseBeliefbase processImpl(PerceptionParameter objParameter) {
		NewAgent nAgent = (NewAgent) objParameter.getAgent();
		FolBeliefbase bb=(FolBeliefbase)objParameter.getBaseBeliefbase();
		if (objParameter.getPerceptions() == null || objParameter.getPerceptions().isEmpty()) {
			nAgent.report("no Perceptions receive.");
			return bb;
		}

		String msg=String.format("received Perceptions : %s",objParameter.getPerceptions());
		LOG.info(msg);
		nAgent.report(msg);
		return bb;
	}

	@Override
	protected void prepare(PerceptionParameter params) {
		if (params == null)
			return;

		if (params.getPerceptions() == null)
			return;
		params.getPerceptions().stream().filter(swarmPercept -> {
			SwarmPerception obj = (SwarmPerception) swarmPercept;

			if (obj.getFact() == null)
				return false;
			return true;
		}).forEach(swarmPercept -> {
			SwarmPerception obj = (SwarmPerception) swarmPercept;
			try {
				FolFormula fol = TransformPredicates.getLiteral(obj.getFact());
				params.getBaseBeliefbase().addKnowledge(fol);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
