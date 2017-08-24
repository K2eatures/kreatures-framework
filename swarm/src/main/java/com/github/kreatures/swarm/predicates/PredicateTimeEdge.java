package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * %TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight) 
 * @author donfack
 *
 */
public class PredicateTimeEdge extends SwarmPredicate{

	private String firstName;
	private String firstTypeName;
	private String secondName;
	private String secondTypeName;
	private int timeType;
	private boolean directed;
	private int conType;
	private int weight;
	
	public PredicateTimeEdge(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateTimeEdge(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateTimeEdge(PredicateTimeEdge other) {
		super(other);
		this.firstName=other.firstName;
		this.firstTypeName=other.firstTypeName;
		this.secondName=other.secondName;
		this.secondTypeName=other.secondTypeName;
		this.timeType=other.timeType;
		this.directed=other.directed;
		this.conType=other.conType;
		this.weight=other.weight;
		
	}	
	
	@Override
	public PredicateTimeEdge clone() {
		return new PredicateTimeEdge(this);
	}
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
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
		return String.format("TimeEdge(%s,%s,%s,%s,%s,%s,%s,%d)",firstName, firstTypeName,secondName,secondTypeName,
				timeType, directed, conType, weight);
	}

	@Override
	public String getPredicatType() {
		
		return getFormulName();
	}

	@Override
	public void createInstance(FolFormula atom) {
		//PredicateTimeEdge predicate=null;
		Pattern pattern=Pattern.compile("TimeEdge[(](\\w+),(\\w+),(\\w+),(\\w+),([01]),({true|false}),([0-3]),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
			//predicate=new PredicateTimeEdge();
			this.firstName=matcher.group(1);
			this.firstTypeName=matcher.group(2);
			this.secondName=matcher.group(3);
			this.secondTypeName=matcher.group(4);
			this.timeType=Integer.parseInt(matcher.group(5));
			this.directed=Boolean.parseBoolean(matcher.group(6));
			this.conType=Integer.parseInt(matcher.group(7));
			this.weight=Integer.parseInt(matcher.group(8));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PredicateTimeEdge))
			return false;
		PredicateTimeEdge obj = (PredicateTimeEdge) other;

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
