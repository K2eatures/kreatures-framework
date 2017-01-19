package com.github.kreatures.swarm.beliefbase;



import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.logic.asp.AspTranslator;
import com.github.kreatures.swarm.comm.SwarmPerception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

public class SwarmTranslator extends AspTranslator{
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmTranslator.class);
	@Override
	protected AspBeliefbase translatePerceptionImpl(BaseBeliefbase caller, Perception perception) {
		Set<FolFormula> formulas = new HashSet<FolFormula>();
		if (perception instanceof SwarmPerception) {
			//SwarmPerception swarmPerception = (SwarmPerception) perception;
		}
		// TODO Translator	
		
		if(perception!=null){
			LOG.info("SwarmTranslator  ->"+perception.toString());
		}else{
			LOG.info("SwarmTranslator");
		}
		return (AspBeliefbase) translateFOL(caller, formulas);
	}
}