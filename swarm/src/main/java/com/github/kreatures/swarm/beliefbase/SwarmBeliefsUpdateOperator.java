package com.github.kreatures.swarm.beliefbase;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.comm.SpeechAct;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;
import com.github.kreatures.swarm.predicates.SwarmPredicate;
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
	protected BaseBeliefbase processImpl(PerceptionParameter params) {
		NewAgent nAgent = (NewAgent) params.getAgent();
		FolBeliefbase bb=(FolBeliefbase)params.getBaseBeliefbase();
		if (params.getPerceptions() == null || params.getPerceptions().isEmpty()) {
			nAgent.report("no Perceptions receive.");
		}else {
//			Set<SwarmPredicate> oldBelief=TransformPredicates.getSetPredicat();
			for(Perception  percept:params.getPerceptions()) {
				SwarmSpeechAct speechAct=(SwarmSpeechAct)percept;
				if(speechAct.getContent().isEmpty())
					continue;
				String msg=String.format("received Perceptions : %s",speechAct.getContent());
				LOG.info(msg);
				nAgent.report(msg);
				bb.addKnowledge(speechAct.getContent());
			}			
		}

		if(params.getInformation()!=null && !((SwarmSpeechAct) params.getInformation()).getContent().isEmpty() ) {
			SwarmSpeechAct speechAct=(SwarmSpeechAct)(params.getInformation());
			String msg=String.format("received Perceptions : %s",speechAct.getContent());
			LOG.info(msg);
			nAgent.report(msg);
			bb.addKnowledge(speechAct.getContent());
		}
		
		return bb;
	}

	@Override
	protected void prepare(PerceptionParameter params) {
//		if (params == null)
//			return;
//
//		if (params.getPerceptions() == null)
//			return;
//		params.getPerceptions().stream().filter(swarmPercept -> {
//			SwarmPerception obj = (SwarmPerception) swarmPercept;
//
//			if (obj.getFact() == null)
//				return false;
//			return true;
//		}).forEach(swarmPercept -> {
//			SwarmPerception obj = (SwarmPerception) swarmPercept;
//			try {
//				FolFormula fol = TransformPredicates.getLiteral(obj.getFact());
//				params.getBaseBeliefbase().addKnowledge(fol);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
	}
}
