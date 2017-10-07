package com.github.kreatures.extraction;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A premise-tuple containing a frequent premise for Apriori-algorithm, the set of state-action-pairs
 * supporting the frequent premise and the list of rules that are dominant,
 * if no further rule is inserted for the premise.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 * @param <A> action
 */
public class PremiseTuple<S, A> {

	protected final Premise premise;
	protected final Set<StateActionPair<S, A>> supporting;
	protected List<Rule<A>> dominant;

	public PremiseTuple(Premise premise, Set<StateActionPair<S, A>> supporting, List<Rule<A>> dominant) {
		this.premise = Objects.requireNonNull(premise, "premise must not be null");
		this.supporting = Objects.requireNonNull(supporting, "supporting pairs must not be null");
		this.setDominant(dominant);
	}

	public Premise getPremise() {
		return premise;
	}

	public Set<StateActionPair<S, A>> getSupporting() {
		return supporting;
	}

	public List<Rule<A>> getDominant() {
		return dominant;
	}

	public void setDominant(List<Rule<A>> dominant) {
		this.dominant = Objects.requireNonNull(dominant, "dominant rules must not be null");
	}

	@Override
	public String toString() {
		return "(" + premise + ", " + supporting + ", " + dominant + ")";
	}

}
