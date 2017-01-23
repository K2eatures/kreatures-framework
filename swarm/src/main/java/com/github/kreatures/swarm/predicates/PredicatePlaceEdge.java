package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * PlacedEdge(StationNameIn,StationTypeNameIn,StationNameOut,StationTypeNameOut,Weight,directed).
 * @author donfack
 *
 */
public class PredicatePlaceEdge extends SwarmPredicate {
	

	private String firstName;
	private String firstTypeName;
	private String secondName;
	private String secondTypeName;
	private int weight;
	private boolean directed;
	private static PredicatePlaceEdge instance=new PredicatePlaceEdge();

	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	public PredicatePlaceEdge(){
	
	}
	
	
	
	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getFirstTypeName() {
		return firstTypeName;
	}



	public void setFirstTypeName(String firstTypeName) {
		this.firstTypeName = firstTypeName;
	}



	public String getSecondName() {
		return secondName;
	}



	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}



	public String getSecondTypeName() {
		return secondTypeName;
	}



	public void setSecondTypeName(String secondTypeName) {
		this.secondTypeName = secondTypeName;
	}



	public int getWeight() {
		return weight;
	}



	public void setWeight(int weight) {
		this.weight = weight;
	}



	public boolean isDirected() {
		return directed;
	}



	public void setDirected(boolean directed) {
		this.directed = directed;
	}



	/**
	 * PlacedEdge(StationNameIn,StationTypeNameIn,StationNameOut,StationTypeNameOut,Weight,directed).
	 */
	@Override
	public String toString() {
		return String.format("PlacedEdge(%s,%s,%s,%s,%d,%s).",firstName,firstTypeName,secondName,secondTypeName,weight,directed);
	}



	@Override
	public String getPredicatType() {
		
		return "PlaceEdge";
	}
	public static PredicatePlaceEdge getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PredicatePlaceEdge createInstance(String fact) {
		PredicatePlaceEdge predicate=null;
		Pattern pattern=Pattern.compile("PlaceEdge[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),({true|false})[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicatePlaceEdge();
			predicate.firstName=matcher.group(1);
			predicate.firstTypeName=matcher.group(2);
			predicate.secondName=matcher.group(3);
			predicate.secondTypeName=matcher.group(4);
			predicate.weight=Integer.parseInt(matcher.group(5));
			predicate.directed=Boolean.parseBoolean(matcher.group(6));
		}
		
		return predicate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PredicatePlaceEdge))
			return false;
		PredicatePlaceEdge obj = (PredicatePlaceEdge) other;

		if (obj.getFirstName() == null || this.getFirstName() == null) {
			return false;
		}
		if (obj.getSecondName() == null || this.getSecondName() == null) {
			return false;
		}

		if (obj.getFirstName().equals(this.getFirstName())
				&& obj.getSecondName().equals(this.getSecondName())) {
			return true;
		}

		return false;
	}
}
