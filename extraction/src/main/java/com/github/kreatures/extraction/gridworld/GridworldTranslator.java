package com.github.kreatures.extraction.gridworld;

import com.github.kreatures.extraction.Premise;
import com.github.kreatures.extraction.Translator;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class GridworldTranslator implements Translator<Location2D> {

	@Override
	public Premise stateToPremise(Location2D state) {
		Premise premise = new Premise();

		premise.put("x", state.x);
		premise.put("y", state.y);

		return premise;
	}

}
