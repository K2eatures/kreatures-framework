package com.github.kreatures.extraction.learning.utils;

/**
 * 
 * @author Manuel barbi
 *
 * @param <A> action
 */
@FunctionalInterface
public interface ActionDecoder<A> {

	A decode(int code);

}
