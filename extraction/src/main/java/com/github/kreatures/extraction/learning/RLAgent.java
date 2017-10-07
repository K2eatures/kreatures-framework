package com.github.kreatures.extraction.learning;

/**
 * The most basic form of an {@link RLAgent}. She perceives the {@link Environment} by
 * perceptions (e.g. sensor data) and interacts with through actions.
 *
 * @author Manuel Barbi
 * 
 */

@FunctionalInterface
public interface RLAgent<P, A> {

	/**
	 * Null is a valid return statement for 'do nothing'.
	 *
	 * @param perception the perception the {@link RLAgent} perceives
	 * @return the action the {@link RLAgent} will execute
	 */
	A generateAction(P perception);

	/**
	 * Optional way of learning by rewards
	 * 
	 * @param reward a numeric value rating the last action
	 * @param terminal whether the following state is terminal
	 */
	default void reward(double reward, boolean terminal) {};

}
