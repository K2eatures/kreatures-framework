package com.github.kreatures.extraction;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Generic Translator implementation using reflections.
 * 
 * @author Manuel Barbi
 *
 * @param <S> state
 */
public class TranslatorRefl<S> implements Translator<S> {

	protected Field[] fields;

	@Override
	public Premise stateToPremise(S state) {
		if (this.fields == null) {
			this.fields = state.getClass().getDeclaredFields();

			for (Field f : this.fields) {
				if (!Modifier.isStatic(f.getModifiers()))
					f.setAccessible(true);
			}
		}

		final Premise premise = new Premise();

		for (Field f : this.fields) {
			try {
				premise.put(f.getName(), f.get(state));
			} catch (IllegalAccessException e) {
				throw new IllegalStateException(e);
			}
		}

		return premise;
	}

}
