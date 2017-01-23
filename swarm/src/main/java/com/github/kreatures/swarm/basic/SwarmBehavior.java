package com.github.kreatures.swarm.basic;



import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.KReaturesEnvironment;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.comm.SpeechAct;
import com.github.kreatures.core.def.DefaultBehavior;

/**
 * 
 * @author donfack
 *
 */
public class SwarmBehavior extends DefaultBehavior{
	private static final Logger LOG = LoggerFactory.getLogger(SwarmBehavior.class);
	
	@Override
	protected void localDelegate(KReaturesEnvironment env, Perception percept, String agentName) {
		if(SpeechAct.ALL.equals(agentName)) {
			
		}else {
			NewAgent ag=(NewAgent)env.getAgentByName(agentName);
			Collection<Perception> perceptions=ag.getPerceptions();
			/* This remove all the elements which are equal the given object */
			while(perceptions.remove(percept)) {}
			
			perceptions.add(percept);
			
		}
	}
	
	protected void addPerception(Agent agent, Perception percept) {
		
	}
}

