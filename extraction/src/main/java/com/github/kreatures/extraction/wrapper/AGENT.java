package com.github.kreatures.extraction.wrapper;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.Agent;
import com.github.kreatures.extraction.components.ValuesComponent;
import com.github.kreatures.extraction.learning.RLAgent;

/**
 * 
 * @author Manuel Barbi
 *
 * @param <P> state
 * @param <A> action
 */
public class AGENT<P, A> implements RLAgent<P, A> {

	private static final Logger LOG = LoggerFactory.getLogger(AGENT.class);

	protected final Agent krAgent;
	protected final BlockingQueue<A> queue;

	public AGENT(Agent krAgent, BlockingQueue<A> queue) {
		this.krAgent = Objects.requireNonNull(krAgent, "kreatures agent must not be null");
		this.queue = Objects.requireNonNull(queue, "queue must not be null");
	}
	
	@Override
	public A generateAction(P perception, long tick) {
		this.krAgent.perceive(new PERCEPTION<P>(krAgent, perception));
		this.krAgent.cycle();

		try {
			return this.queue.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(P perceptiom, A action, double reward, P next, boolean terminal) {
		@SuppressWarnings("unchecked")
		ValuesComponent<P, A> values = krAgent.getComponent(ValuesComponent.class);

		if (values != null) {
			values.reward(reward, terminal);
		} else {
			LOG.warn("agent does not have a value-component");
		}
	}

}
