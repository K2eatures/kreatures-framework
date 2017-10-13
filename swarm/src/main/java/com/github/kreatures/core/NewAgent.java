/**
 * 
 */
package com.github.kreatures.core;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.BaseUpdateBeliefsOperator;
import com.github.kreatures.core.operators.OperatorCallWrapper;
import com.github.kreatures.core.operators.parameter.EvaluateParameter;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.core.reflection.Context;
import com.github.kreatures.core.reflection.ContextFactory;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;

/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class NewAgent extends AgentAbstract {
	/** reference to the logback logger instance */
	private static Logger LOG = LoggerFactory.getLogger(NewAgent.class);
	
	/**
	 * @param name
	 * @param env
	 */
	public NewAgent(String name, KReaturesEnvironment env) {
		super(name, env);
	}
	
	public Collection<Perception> getPerceptions(){
		return perceptions;
	}
	

	public Perception getPerception(int index){
		return perceptions.get(index);
	}
	/**
	 * @return the type name of the current agent.
	 * @throws a {@link RuntimeException} when the type name of a agent isn't found. 
	 */
	public String getAgentTypeName() {
		Pattern  pattern=Pattern.compile("([^0-9]*)");
		Matcher matcher=pattern.matcher(getName());
		if(matcher.find()) {
			return matcher.group();
		}
		throw new RuntimeException("The AgentTypeName isn't matching the pattern for the given agent name.");
	}
	
	/**
	 * Updates the beliefs of the agent. This method searches for the correct
	 * Update operator and calls its process method.
	 * 
	 * @param perception
	 *            The perception causing the update.
	 * @param beliefs
	 *            The Beliefs used as basis for the update process.
	 * @return The updated version of the beliefs.
	 */
	@Override
	@SuppressWarnings("hiding")
	public Beliefs updateBeliefs(KReaturesAtom perception,  Beliefs beliefs) {
		if (perception != null) {
			// save the perception for later use in messaging system.
			if(perception instanceof Perception)
				lastUpdateBeliefsPercept = (Perception)perception;
			PerceptionParameter param = new PerceptionParameter(this, beliefs.getWorldKnowledge(), perception);
			OperatorCallWrapper bubo = operators.getPreferedByType(BaseBeliefsUpdateOperator.OPERATION_TYPE);
			bubo.process(param);
//			FolBeliefbase actuelBelief=(FolBeliefbase)
//			FolBeliefbase oldBelief=(FolBeliefbase)beliefs.getWorldKnowledge();
//			oldBelief.setProgram(actuelBelief.getProgram());
			return beliefs; 
		}
		
		return beliefs;
	}
	
	@Override
	public boolean cycle() {
		Perception percept = perceptions.isEmpty() ? null : perceptions.get(0);
		perceptions.clear();
		LOG.info("[" + this.getName() + "] Cylce starts: " + percept);

		regenContext();
		Context c = getContext();
		c.set("perception", percept);
		
		return asmlCylce.execute(c);
	}
	
	@Override
	public PlanComponent getPlanComponent() {
		PlanComponent plan = getComponent(SwarmPlanComponent.class);
		if (plan == null) {
			LOG.warn(
					"Tried to access the plan-component of agent '{}' which has no plan-component.",
					getName());
			return null;
		}
		return plan;
	}
	@Override
	public Context getContext() {
		return context;
	}

	@Override
	protected void regenContext() {
		context = ContextFactory.createContext(this);
		context.set("operators", this.operators);
		context.set("plan", this.getComponent(SwarmPlanComponent.class));
		if (beliefs != null) {
			context.set("world", beliefs.getWorldKnowledge());
			Map<String, BaseBeliefbase> views = beliefs.getViewKnowledge();
			Context vc = new Context();
			context.attachContext("views", vc);
			for (Entry<String, BaseBeliefbase> ent : views.entrySet()) {
				vc.set(ent.getKey(), ent.getValue());
			}
		}
	}

	
	@Override
	public boolean equals(Object other) {
		if(other==null) return false;
		if(!(other instanceof NewAgent))return false;
		NewAgent agent=(NewAgent)other;
		return this.name.equals(agent.name);
	}
}
