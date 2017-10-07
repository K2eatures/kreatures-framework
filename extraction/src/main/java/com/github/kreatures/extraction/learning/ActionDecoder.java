package com.github.kreatures.extraction.learning;

@FunctionalInterface
public interface ActionDecoder<A> {

	A decode(int code);

}
