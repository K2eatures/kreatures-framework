package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * KnowHow(AgentName,AgentTypeName,StationName,StationTypeName,CriterieName,CriterieValue).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateKnowHow extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private String crName;
	private int crValue;
	
	public PredicateKnowHow(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateKnowHow(String agentName,String agentTypeName,String stationName, String stationTypeName,String crName, int crValue) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.crName=crName;
		this.crValue=crValue;
	}

	public PredicateKnowHow(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateKnowHow(PredicateKnowHow other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
		this.crName=other.crName;
		this.crValue=other.crValue;
	}	
	
	@Override
	public PredicateKnowHow clone() {
		return new PredicateKnowHow(this);
	}

	/**
	 * KnowHow(AgentName,AgentTypeName,StationName,StationTypeName,CriterieName,CriterieValue).
	 */
	@Override
	public String toString() {
		return String.format("KnowHow(%s,%s,%s,%s,%s,%d)", agentName, agentTypeName,stationName,stationTypeName,crName,crValue);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("KnowHow[(](\\w+),(\\w+),(\\w+),(\\w+),(\\w+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.crName=matcher.group(5);
			this.crValue=Integer.parseInt(matcher.group(6));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateKnowHow))
			return false;
		
		PredicateKnowHow obj=(PredicateKnowHow)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		boolean isCrName=obj.crName==null?this.crName==null:obj.crName.equals(this.crName);
		
		return isName & isTypeName & isCrName;
	}
	
	@Override
	public int hashCode() {
		return (this.crName.hashCode() + this.agentName.hashCode())* 11;
	}
	
	public String getAgentName() {
		return agentName;
	}

	public String getAgentTypeName() {
		return agentTypeName;
	}

	public String getStationName() {
		return stationName;
	}

	public String getStationTypeName() {
		return stationTypeName;
	}

	public String getCrName() {
		return crName;
	}

	public int getCrValue() {
		return crValue;
	}
//	@Override
//	public String getPredicatType() {
//		return "KnowHow";
//	}

}
