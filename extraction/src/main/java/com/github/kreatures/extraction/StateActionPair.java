package com.github.kreatures.extraction;

import java.util.Objects;

/**
 * Both state and action should override the hashCode() and equals() methods in order to work properly.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 * @param <A> action
 */
public class StateActionPair<S, A> {

	private final S state;
	private final A action;

	public StateActionPair(S state, A action) {
		this.state = Objects.requireNonNull(state, "state must not be null");
		this.action = Objects.requireNonNull(action, "actions must not be null");
	}

	public S getState() {
		return state;
	}

	public A getAction() {
		return action;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		StateActionPair other = (StateActionPair) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + state + "," + action + ")";
	}

}
