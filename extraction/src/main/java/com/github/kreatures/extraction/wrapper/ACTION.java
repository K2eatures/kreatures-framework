package com.github.kreatures.extraction.wrapper;

import java.util.Objects;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.Agent;

/**
 * 
 * @author Manuel Barbi
 *
 * @param <A> action
 */
public class ACTION<A> extends Action {

	protected final A action;

	public ACTION(Agent sender, A action) {
		super(sender);
		this.action = Objects.requireNonNull(action, "action must not be null");
	}

	public A getAction() {
		return action;
	}

}
