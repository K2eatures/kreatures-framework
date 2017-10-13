package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;



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
	
	
	public PredicatePlaceEdge(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicatePlaceEdge(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicatePlaceEdge(PredicatePlaceEdge other) {
		super(other);
		this.firstName=other.firstName;
		this.firstTypeName=other.firstTypeName;
		this.secondName=other.secondName;
		this.secondTypeName=other.secondTypeName;
		this.weight=other.weight;
		this.directed=other.directed;
		
	}	
	
	@Override
	public PredicatePlaceEdge clone() {
		return new PredicatePlaceEdge(this);
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
		return String.format("PlacedEdge(%s,%s,%s,%s,%d,%s)",firstName,firstTypeName,secondName,secondTypeName,weight,directed);
	}

//	@Override
//	public String getPredicatType() {
//		
//		return "PlaceEdge";
//	}
	
	@Override
	public void createInstance(FolFormula atom) {
		//PredicatePlaceEdge predicate=null;
		Pattern pattern=Pattern.compile("PlaceEdge[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),({true|false})[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
			//predicate=new PredicatePlaceEdge();
			this.firstName=matcher.group(1);
			this.firstTypeName=matcher.group(2);
			this.secondName=matcher.group(3);
			this.secondTypeName=matcher.group(4);
			this.weight=Integer.parseInt(matcher.group(5));
			this.directed=Boolean.parseBoolean(matcher.group(6));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicatePlaceEdge))
			return false;
		
		PredicatePlaceEdge obj=(PredicatePlaceEdge)other;
		boolean isName=obj.firstName==null?this.firstName==null:obj.firstName.equals(this.firstName);
		boolean isTypeName=obj.secondName==null?this.secondName==null:obj.secondName.equals(this.secondName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.firstName.hashCode()* 11;
	}
}
