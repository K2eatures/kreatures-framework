package com.github.kreatures.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * A rule consisting of a premise of partial states, an action as conclusion, and the rule's
 * confidence between 0 and 1.
 * 
 * @author Manuel Barbi
 *
 * @param <A> action
 */
public class Rule<A> implements Comparable<Rule<A>> {

	protected final Premise premise;
	protected final A conclusion;
	protected final double confidence;

	public Rule(Premise premise, A conclusion, double confidence) {
		this.premise = Objects.requireNonNull(premise, "premise must not be null");
		this.conclusion = Objects.requireNonNull(conclusion, "conclusion must not be null");
		this.confidence = checkConfidence(confidence);
	}

	protected static double checkConfidence(double confidence) {
		if (confidence <= 0 || confidence > 1)
			throw new IllegalArgumentException("confidence is out of range: " + confidence);

		return confidence;
	}

	public Premise getPremise() {
		return premise;
	}

	public A getConclusion() {
		return conclusion;
	}

	public double getConfidence() {
		return confidence;
	}

	/**
	 * @return a list of rules containing only this rule
	 */
	public List<Rule<A>> wrap() {
		List<Rule<A>> rules = new ArrayList<>(1);
		rules.add(this);
		return rules;
	}

	@Override
	public int compareTo(Rule<A> other) {
		int result = this.premise.compareTo(other.premise);
		if (result != 0)
			return result;

		return Utils.lexicalOrder(this.conclusion, other.conclusion);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conclusion == null) ? 0 : conclusion.hashCode());
		long temp;
		temp = Double.doubleToLongBits(confidence);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((premise == null) ? 0 : premise.hashCode());
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
		Rule other = (Rule) obj;
		if (conclusion == null) {
			if (other.conclusion != null)
				return false;
		} else if (!conclusion.equals(other.conclusion))
			return false;
		if (Double.doubleToLongBits(confidence) != Double.doubleToLongBits(other.confidence))
			return false;
		if (premise == null) {
			if (other.premise != null)
				return false;
		} else if (!premise.equals(other.premise))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return premise + " => " + conclusion + " [" + String.format(Locale.ENGLISH, "%.2f", confidence) + "]";
	}

}
