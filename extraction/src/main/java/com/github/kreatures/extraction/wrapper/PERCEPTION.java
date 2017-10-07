package com.github.kreatures.extraction.wrapper;

import java.util.Objects;

import com.github.kreatures.core.Agent;
import com.github.kreatures.core.Perception;

/**
 * 
 * @author Manuel Barbi
 *
 * @param <P> perception
 */
public class PERCEPTION<P> implements Perception {

	protected final Agent receiver;
	protected final P perception;

	public PERCEPTION(Agent receiver, P perception) {
		this.receiver = Objects.requireNonNull(receiver, "receiver must not be null");
		this.perception = Objects.requireNonNull(perception, "perception must not be null");
	}

	public P getPerception() {
		return perception;
	}

	@Override
	public String getReceiverId() {
		return this.receiver.getName();
	}

}
