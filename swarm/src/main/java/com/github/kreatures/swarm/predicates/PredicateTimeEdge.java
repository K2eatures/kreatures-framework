package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * %TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight) 
 * @author donfack
 *
 */
public class PredicateTimeEdge implements SwarmPredicate{

	private String firstName;
	private String firstTypeName;
	private String secondName;
	private String secondTypeName;
	private int timeType;
	private boolean directed;
	private int conType;
	private int weight;
	private static PredicateTimeEdge instance=new PredicateTimeEdge();
	
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	private PredicateTimeEdge(){
				
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

	public int getTimeType() {
		return timeType;
	}

	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}

	public boolean isDirected() {
		return directed;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}

	public int getConType() {
		return conType;
	}

	public void setConType(int conType) {
		this.conType = conType;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * %TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight).
	 */
	@Override
	public String toString() {
		return String.format("TimeEdge(%s,%s,%s,%s,%s,%s,%s,%d).",firstName, firstTypeName,secondName,secondTypeName,
				timeType, directed, conType, weight);
	}

	@Override
	public String getPredicatType() {
		
		return "TimeEdge";
	}

	public static PredicateTimeEdge getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@Override
	public PredicateTimeEdge createInstance(String fact) {
		PredicateTimeEdge predicate=null;
		Pattern pattern=Pattern.compile("TimeEdge[(](\\w+),(\\w+),(\\w+),(\\w+),([01]),({true|false}),([0-3]),(\\d+)[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateTimeEdge();
			predicate.firstName=matcher.group(1);
			predicate.firstTypeName=matcher.group(2);
			predicate.secondName=matcher.group(3);
			predicate.secondTypeName=matcher.group(4);
			predicate.timeType=Integer.parseInt(matcher.group(5));
			predicate.directed=Boolean.parseBoolean(matcher.group(6));
			predicate.conType=Integer.parseInt(matcher.group(7));
			predicate.weight=Integer.parseInt(matcher.group(8));
		}
		
		return predicate;
	}
	
}
