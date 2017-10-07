package com.github.kreatures.extraction.island;

import com.github.kreatures.extraction.Premise;
import com.github.kreatures.extraction.Translator;

/**
 * 
 * @author Manuel Barbi
 *
 */
public class IslandTranslator implements Translator<IslandPerception> {

	@Override
	public Premise stateToPremise(com.github.kreatures.extraction.island.IslandPerception state) {
		Premise premise = new Premise();

		premise.put("site", state.getSite());
		premise.put("secured", state.isSecured());
		premise.put("battery", state.getBattery());
		premise.put("location", state.getLocation());
		premise.put("weather", state.getWeather());
		premise.put("prediction", state.getPrediction());

		return premise;
	}

}
