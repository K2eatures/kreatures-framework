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
import com.github.kreatures.swarm.basic.SwarmBehavior;

public class SwarmSimulationListener implements  SimulationListener{

	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmSimulationListener.class);
	
	public SwarmSimulationListener() {}
	@Override
	public boolean simulationInit(KReaturesEnvironment env) {
		if(env.getBehavior() instanceof SwarmBehavior) {
			env.setAgentFactory(new CreateAgent());
			EnvironmentComponent environmentComponent= new EnvironmentComponentDefault(env.getName());
			environmentComponent.setScenariomodell(env.getName());
			AbstractSwarms.getInstance().listEnvironmentComponent.add(environmentComponent);
			LOG.info(environmentComponent.toString());
			return true;
		}
		return false;
	}
	@Override
	public void agentAdded(KReaturesEnvironment simulationEnvironment, AgentAbstract added){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : agentAdded");
	}

	@Override
	public void agentRemoved(KReaturesEnvironment simulationEnvironment,AgentAbstract removed){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : agentRemoved");
	}
	
	@Override
	public void actionPerformed(AgentAbstract agent, Action act){
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : actionPerformed");
	}

	@Override
	public void simulationStarted(KReaturesEnvironment simulationEnvironment) {
		LOG.info("SwarmSimulationListener : simulationStarted");
	}

	@Override
	public void simulationDestroyed(KReaturesEnvironment env) {
		// TODO Auto-generated method stub
		LOG.info("SwarmSimulationListener : simulationDestroyed");
		if(env.getAgentFactory() instanceof CreateAgent) {
			env.setAgentFactory(new CreateAgentAdapter());
			AbstractSwarms.getInstance().listEnvironmentComponent.removeIf(environmentComponent->{
				boolean check=((EnvironmentComponentDefault) environmentComponent).getProjectName().equals(env.getName());
				LOG.info(environmentComponent.toString()+"\n "+check);
				return check;
			});
		}
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
