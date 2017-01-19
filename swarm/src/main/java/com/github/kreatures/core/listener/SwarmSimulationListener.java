package com.github.kreatures.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.Agent;
import com.github.kreatures.core.KReaturesEnvironment;

public class SwarmSimulationListener implements  SimulationListener{

	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmSimulationListener.class);
	@Override
	public void agentAdded(KReaturesEnvironment simulationEnvironment, Agent added){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : agentAdded");
	}

	@Override
	public void agentRemoved(KReaturesEnvironment simulationEnvironment,Agent removed){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : agentRemoved");
	}
	
	@Override
	public void actionPerformed(Agent agent, Action act){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : actionPerformed");
	}

	@Override
	public void simulationStarted(KReaturesEnvironment simulationEnvironment) {
		// TODO Auto-generated method stub
		//simulationEnvironment.
		LOG.info("SwarmSimulationListener : simulationStarted");
	}

	@Override
	public void simulationDestroyed(KReaturesEnvironment simulationEnvironment) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : simulationDestroyed");
	}

	@Override
	public void tickStarting(KReaturesEnvironment simulationEnvironment) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : tickStarting");
	}

	@Override
	public void tickDone(KReaturesEnvironment simulationEnvironment) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : tickDone");
	}

}
