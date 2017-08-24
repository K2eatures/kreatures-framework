package com.github.kreatures.swarm.beliefbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.logic.asp.AspTranslator;
import com.github.kreatures.core.operators.parameter.TranslatorParameter;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;

import net.sf.tweety.logics.translators.aspnlp.AspNlpTranslator;
import net.sf.tweety.lp.nlp.syntax.NLPProgram;

public class SwarmTranslator extends AspTranslator{
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmTranslator.class);
	
	private static AspNlpTranslator translator = new AspNlpTranslator();
	
	@Override
	protected BaseBeliefbase processImpl(TranslatorParameter params) {
		if(params.getPerception() != null) {
			return translatePerceptionImpl(params.getBeliefBase(), 
					params.getPerception());
		} else if(params.getInformation() != null) {
			return translateNLPImpl(params.getBeliefBase(), 
					params.getInformation());
		}
		return null;
	}
	
	
	
	@Override
	protected AspBeliefbase translatePerceptionImpl(BaseBeliefbase caller, Perception perception) {
		if (perception instanceof SwarmSpeechAct) {
			SwarmSpeechAct speechAct=(SwarmSpeechAct)perception;
			LOG.info("SwarmTranslator  ->"+perception.toString());
			return (AspBeliefbase) translateFOL(caller, speechAct.getContent());
		}
		
		return  null;
	}
	
	@Override
	protected FolBeliefbase translateNLPImpl(BaseBeliefbase caller, NLPProgram program) {
		FolBeliefbase reval = new FolBeliefbase();
		reval.setProgram(translator.toASP(program));
		return reval;
	}
	
//	@Override
//	protected AspBeliefbase translateNLPImpl(BaseBeliefbase caller,NLPProgram information) {
//		if (information instanceof SwarmSpeechAct) {
//			SwarmSpeechAct speechAct=(SwarmSpeechAct)information;
//			LOG.info("SwarmTranslator  ->"+perception.toString());
//			return (AspBeliefbase) translateFOL(caller, speechAct.getContent());
//		}
//		
//		return  null;
//	}
}