package com.github.kreatures.swarm.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.KReaturesEnvironment;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.Perception;
import com.github.kreatures.core.def.DefaultBehavior;

/**
 * 
 * @author donfack
 *
 */
public class SwarmBehavior extends DefaultBehavior{
	private static final Logger LOG = LoggerFactory.getLogger(SwarmBehavior.class);
	
	@Override
	public void sendAction(KReaturesEnvironment env, Action act) {
		// The action send by one agent is the perception of the other one.
		somethingHappens = true;
		
		// forward the action if it can be perceived by other agents
		if(act instanceof Perception) {

			Perception per = (Perception) act;
			String agentName = per.getReceiverId();
			localDelegate(env, per, agentName);
		}
	}
	
	@Override
	protected void localDelegate(KReaturesEnvironment env, Perception percept, String agentName) {
		for(AgentAbstract agent:env.getAgents()) {
			
			if(agent instanceof NewAgent && !((Action)percept).getAgent().equals(agent)) {
				NewAgent newAgent=(NewAgent)agent;
				newAgent.getPerceptions().add(percept);
			}
		}
	}
}

