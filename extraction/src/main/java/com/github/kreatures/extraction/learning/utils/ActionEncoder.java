package com.github.kreatures.extraction.learning.utils;

/**
 * 
 * @author Manuel barbi
 *
 * @param <A> action
 */
@FunctionalInterface
public interface ActionEncoder<A> {

	int encode(A action);

}
