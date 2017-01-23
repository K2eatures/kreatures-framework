package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author donfack
 * SwarmStation is the basis class and allows to create new generic station.
 *
 */


public class PredicateStation extends SwarmPredicate{
	// Station(StationName,StationType,freq,nec,space).
	private String name;
	private String typeName;
	private int frequency;
	private int necessity;
	private int space;
	private static PredicateStation instance=new PredicateStation();
	private PredicateStation(){
		
	}
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getTypeName() {
		return typeName;
	}



	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



	public int getFrequency() {
		return frequency;
	}



	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}



	public int getNecessity() {
		return necessity;
	}



	public void setNecessity(int necessity) {
		this.necessity = necessity;
	}



	public int getSpace() {
		return space;
	}



	public void setSpace(int space) {
		this.space = space;
	}



	/**
	 * Station(StationName,StattionType,freq,nec,space).
	 */
	@Override
	public String toString() {
		return String.format("Station(%s,%s,%d,%d,%d).",name, typeName,frequency,necessity,space);
	}



	@Override
	public String getPredicatType() {
		
		return "Station";
	}
	public static PredicateStation getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PredicateStation createInstance(String fact) {
		PredicateStation predicate=null;
		Pattern pattern=Pattern.compile("Station[(](\\w+),(\\w+),(\\d+),(\\d+),(\\d+)[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateStation();
			predicate.name=matcher.group(1);
			predicate.typeName=matcher.group(2);
			predicate.frequency=Integer.parseInt(matcher.group(3));
			predicate.necessity=Integer.parseInt(matcher.group(4));
			predicate.space=Integer.parseInt(matcher.group(5));
		}
		
		return predicate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PredicateStation))
			return false;
		PredicateStation obj = (PredicateStation) other;

		if (obj.getName() != null && this.getName() != null) {
			return obj.getName().equals(this.getName());
		}
		return false;
	}
}
