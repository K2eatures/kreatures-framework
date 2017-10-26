package com.github.kreatures.extraction.island;

public class IslandPerception {

	protected final int site;
	protected final boolean secured;
	protected final int battery;
	protected final IslandLocation location;
	protected final IslandWeather weather;
	protected final IslandWeather prediction;

	public IslandPerception(int site, boolean secured, int battery, IslandLocation location, IslandWeather weather, IslandWeather prediction) {
		this.site = site;
		this.secured = secured;
		this.battery = battery;
		this.location = location;
		this.weather = weather;
		this.prediction = prediction;
	}

	public int getSite() {
		return site;
	}

	public boolean isSecured() {
		return secured;
	}

	public int getBattery() {
		return battery;
	}

	public IslandLocation getLocation() {
		return location;
	}

	public IslandWeather getWeather() {
		return weather;
	}

	public IslandWeather getPrediction() {
		return prediction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + battery;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((prediction == null) ? 0 : prediction.hashCode());
		result = prime * result + (secured ? 1231 : 1237);
		result = prime * result + site;
		result = prime * result + ((weather == null) ? 0 : weather.hashCode());
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
		IslandPerception other = (IslandPerception) obj;
		if (battery != other.battery)
			return false;
		if (location != other.location)
			return false;
		if (prediction != other.prediction)
			return false;
		if (secured != other.secured)
			return false;
		if (site != other.site)
			return false;
		if (weather != other.weather)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + site + ", " + secured + ", " + battery + ", " + location + ", " + weather + ", " + prediction + ")";
	}

}
