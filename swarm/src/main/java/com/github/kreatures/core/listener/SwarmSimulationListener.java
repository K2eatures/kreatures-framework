package com.github.kreatures.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.CreateAgent;
import com.github.kreatures.core.CreateAgentAdapter;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.EnvironmentComponentDefault;
import com.github.kreatures.core.KReaturesEnvironment;
import com.github.kreatures.core.NewAgent;
import com.github.kreatures.core.serialize.SwarmLoaderDefault;
import com.github.kreatures.swarm.basic.SwarmBehavior;

public class SwarmSimulationListener implements  SimulationListener{

	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmSimulationListener.class);
	
	public SwarmSimulationListener() {}
	@Override
	public boolean simulationInit(KReaturesEnvironment env) {
		if(env.getBehavior() instanceof SwarmBehavior) {
			env.setAgentFactory(new CreateAgent());
			EnvironmentComponent environmentComponent= new EnvironmentComponentDefault();
			AbstractSwarms.getInstance().addEnvComponent(environmentComponent);
//			LOG.info(environmentComponent.toString());
//			ScenarioModelBeliefbase.addInstance(env.getName());

			return true;
		}
		return false;
	}
	@Override
	public void agentAdded(KReaturesEnvironment simulationEnvironment, AgentAbstract added){
		/*
		 * add the initial context of each needed context varaibles. 
		 
		if(!(added instanceof NewAgent))
			return;
		
		Context context=added.getContext();
		context.set(SwarmContextConst._DESIRES, new SwarmDesires());
		*/
	}

	@Override
	public void agentRemoved(KReaturesEnvironment simulationEnvironment,AgentAbstract removed){
		/*
		 * add the initial context of each needed context varaibles. 
		
		if(!(removed instanceof NewAgent))
			return;
		
		Context context=removed.getContext();
		context.set(SwarmContextConst._DESIRES, null);
		 */
	}
	
	@Override
	public void actionPerformed(AgentAbstract agent, Action act){
		if(agent instanceof NewAgent) {
			// TODO Auto-generated method stub
			LOG.info("SwarmSimulationListener : actionPerformed");
		}
	}

	@Override
	public void simulationStarted(KReaturesEnvironment simulationEnvironment) {
		//TODO Must be deleted later
//		SwarmLoaderDefault.getInstance().unloading();
//		SwarmLoaderDefault.freeInstance();
		LOG.info("SwarmSimulationListener : simulationStarted");
	}

	@Override
	public void simulationDestroyed(KReaturesEnvironment env) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : simulationDestroyed");
		if(env.getAgentFactory() instanceof CreateAgent) {
			env.setAgentFactory(new CreateAgentAdapter());
			AbstractSwarms.getInstance().removeEnvComponent(env.getName());
		}
		
		SwarmLoaderDefault.getInstance().unloading();
		SwarmLoaderDefault.freeInstance();
	}

	@Override
	public void tickStarting(KReaturesEnvironment simulationEnvironment) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : tickStarting");
	}

	@Override
	public void tickDone(KReaturesEnvironment simulationEnvironment) {
		/*
		 * Unused objects have to be clear each ~21 iterations.
		 * But only the java runtime knows if this instruction will be run.
		 */
		int actuelTick=simulationEnvironment.getSimulationTick();		
		if(actuelTick %21==20) {
			long mg=1024*1024;
			LOG.info("Use Memory before the gc run is MB: " + (double) (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory() )/ mg);
			LOG.info("Actual Free Memory before the gc run is MB: " + (double) Runtime.getRuntime().freeMemory() / mg);
			System.gc();
			LOG.info("Actual Free Memory after the gc run is MB: " + (double) Runtime.getRuntime().freeMemory() / mg);
			LOG.info("Use Memory after the gc run is MB: " + (double) (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory() )/ mg);
		}
		LOG.info("SwarmSimulationListener : tickDone");
	}
	

}