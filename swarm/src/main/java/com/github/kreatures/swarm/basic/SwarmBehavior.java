package com.github.kreatures.swarm.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.KReatures;
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

		env.getAgents().stream().filter(agent->(agent instanceof NewAgent)&&!agent.equals(((Action) percept).getAgent())).forEach(agent->{

			NewAgent newAgent=(NewAgent)agent;
			((SwarmSpeechAct)percept).getActions().stream().peek(System.out::println).forEach(newPercept->{
				newAgent.getPerceptions().stream().peek(System.out::println).forEach(oldPercept->{
					System.out.println("Hier #  "+((SwarmSpeechAct)oldPercept).getActions().removeIf(obj->obj.equals(newPercept)));						
				});
			});
			newAgent.getPerceptions().add(percept);
			LOG.debug(String.format("All Perceptions of %s%n %s%n",newAgent.getName(),newAgent.getPerceptions().toString()));
		});
	}
	
	
//	@Override
//	public boolean runOneTick(KReaturesEnvironment env) {
//		doingTick = true;
//		
//		while(!isSimulationReady()) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		if(!somethingHappens && tick != 0)
//			return false;
//		
//		somethingHappens = false;
//		kreaturesReady = false;
//		++tick;
//		KReatures.getInstance().onTickStarting(env);
//		
//		List<AgentAbstract> orderedUniformProb= new ArrayList<>(env.getAgents());
//		int agentNumber=orderedUniformProb.size();
//		final int[] indexs= new Random().ints(0, agentNumber).distinct().limit(agentNumber).toArray();
//
//		for(int index :indexs) {
//			// cycle internally sends the selected action
//			// to the environment using sendAction() method.
//			orderedUniformProb.get(index).cycle();
//		}
//		kreaturesReady = true;
//		
//		doingTick = false;
//		KReatures.getInstance().onTickDone(env);
//		return true;
//	}
}

