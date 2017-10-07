package com.github.kreatures.extraction.learning;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The environment one or more agents are located in.
 * The environment's execution is measured in ticks and is executed as long as not all
 * agents has finished respectively abandon their task.
 *
 * @author Manuel Barbi
 * 
 */
public abstract class RLEnvironment<P, A> implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(RLEnvironment.class);

	protected List<RLAgent<P, A>> agents;
	protected boolean[] agentDisabled;
	protected double[] cumulative;
	protected long tick = 0;

	public RLEnvironment(List<RLAgent<P, A>> agents) {
		this.agents = Objects.requireNonNull(agents, "agents must not be null");
		this.agentDisabled = new boolean[agents.size()];
		this.cumulative = new double[agents.size()];
	}

	@Override
	public void run() {
		reboot();
		while (runSingleStep());
	}

	/**
	 * @return whether something happened during this step
	 */
	public boolean runSingleStep() {
		tick();
		runEnvironment();
		return runAgents();
	}

	/**
	 * Creates a perception suitable for the delivered agent.
	 *
	 * @param agentId the Id of the agent which has to be treated
	 * @return a perception for the delivered agent
	 */
	public abstract P createPerception(int agentId);

	/**
	 * Changes the environment's state according to the delivered action.
	 *
	 * @param agentId the Id of the agent which performs the action
	 * @param action the action which is performed
	 */
	public abstract void executeAction(int agentId, A action);

	/**
	 * @param agentId the id of the agent which has to be treated
	 * @return whether the delivered agent has successfully finished her task
	 */
	public abstract boolean terminationCriterion(int agentId);

	/**
	 * Performs any self-dependent changes of the environment's state.
	 */
	public void runEnvironment() {}

	/**
	 * @return whether any agent continues her task
	 */
	public boolean runAgents() {
		boolean doContinue = false;
		int agentId = 0;

		for (RLAgent<P, A> agent : agents) {
			try {
				if (!agentDisabled[agentId]) {
					runSingleAgent(agent, agentId);

					if (terminationCriterion(agentId)) {
						onComplete(agentId, agent);
						continue;
					}

					doContinue = true;
				}
			} catch (Exception e) {
				agentDisabled[agentId] = true;
				LOG.warn(e.getMessage(), e);
			} finally {
				agentId++;
			}
		}

		return doContinue;
	}

	protected void onComplete(int agentId, RLAgent<P, A> agent) {
		LOG.info("<tick {}> agent {} finished", tick, agentId);
		agentDisabled[agentId] = true;
	}

	protected void runSingleAgent(RLAgent<P, A> agent, int agentId) {
		P perception = createPerception(agentId);
		LOG.info("<tick {}> agent {} perceives {}", tick, agentId, perception);
		A action = agent.generateAction(perception);
		LOG.info("<tick {}> agent {} executes {}", tick, agentId, action);
		executeAction(agentId, action);

		double reward = getReward(agentId);
		cumulate(agentId, reward);
		agent.reward(reward, terminationCriterion(agentId));
	}

	public abstract double getReward(int agendId);

	public void tick() {
		tick++;
	}

	public long getCurrentTick() {
		return tick;
	}

	public void cumulate(int agentId, double reward) {
		cumulative[agentId] += reward;
	}

	public double getCumulative(int agentId) {
		return cumulative[agentId];
	}

	public void reboot() {
		this.tick = 0;
		Arrays.fill(this.agentDisabled, false);
		Arrays.fill(this.cumulative, 0);
	}

}
