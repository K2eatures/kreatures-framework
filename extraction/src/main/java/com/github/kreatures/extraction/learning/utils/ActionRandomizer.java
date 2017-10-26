package com.github.kreatures.extraction.learning.utils;

/**
 * 
 * @author Manuel Barbi
 *
 * @param <A> action
 */
@FunctionalInterface
public interface ActionRandomizer<A> {

	A randomAction();

}
