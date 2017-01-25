package com.github.kreatures.core.listener;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.KReaturesEnvironment;

/**
 * Listener interface which gets informed about changes of the simulation. For example if a new agent
 * is added to simulation. Or an agent is removed, when the simulation starts or a tick is done.
 * 
 * @author Tim Janus
 */
public interface SimulationListener {	
	/** is called before creating of agent. */
	default boolean simulationInit(KReaturesEnvironment simulationEnvironment) {return false;}
	/** is called when a new simulation starts, before any initialization */
	void simulationStarted(KReaturesEnvironment simulationEnvironment);
	
	/** is called when a simulations cleanup method is called */
	void simulationDestroyed(KReaturesEnvironment simulationEnvironment);
	
	/** is called when the given agent is added to the simulation-environment */
	void agentAdded(KReaturesEnvironment simulationEnvironment, AgentAbstract added);
	
	/** is called when the given agent is removed from the simulation-environment. */
	void agentRemoved(KReaturesEnvironment simulationEnvironment, AgentAbstract removed);
	
	/** is called when a new tick is starting, before the first agent does anything */
	void tickStarting(KReaturesEnvironment simulationEnvironment);
	
	/** is called after a complete cycle of the simulation (an update of ever agent) is done. */
	void tickDone(KReaturesEnvironment simulationEnvironment);

	/** is called after an agent has performed an action. */
	void actionPerformed(AgentAbstract agent, Action act);
	
}
