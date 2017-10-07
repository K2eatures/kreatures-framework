package com.github.kreatures.extraction.components;

import java.util.List;

import com.github.kreatures.core.BaseAgentComponent;
import com.github.kreatures.core.comp.Presentable;

/**
 * 
 * @author barbi
 *
 * @param <S> state
 * @param <A> action
 */
public abstract class ValuesComponent<S, A> extends BaseAgentComponent implements Presentable {

	protected S keepState;
	protected A keepAction;
	protected double keepReward;

	public void reward(double reward, boolean terminal) {
		this.keepReward = reward;
	}

	public abstract A getBestAction(S state);

	@Override
	public void getRepresentation(List<String> representation) {}

	@Override
	public BaseAgentComponent clone() {
		return this;
	}

}
