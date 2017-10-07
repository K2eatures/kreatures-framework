package com.github.kreatures.extraction;

/**
 * Base class for explicit translation from states to premisses.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 */
@FunctionalInterface
public interface Translator<S> {

	/**
	 * Translates the given state as premise.
	 * 
	 * @param state
	 * @return the given state as premise
	 */
	Premise stateToPremise(S state);

}
