package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 *TODO
 *VisitEdge(agentName,AgentTypeName,StationName,StationTypeName,bold).
 * @author donfack
 *
 */
public class PredicateVisitEdge extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private boolean bold;
	/** Default Ctor: Initialize plan and atom with null */
	protected PredicateVisitEdge() {
	
	}
		
	public PredicateVisitEdge(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateVisitEdge(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateVisitEdge(PredicateVisitEdge other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
	}	
	
	@Override
	public PredicateVisitEdge clone() {
		return new PredicateVisitEdge(this);
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentTypeName() {
		return agentTypeName;
	}

	public void setAgentTypeName(String agentTypeName) {
		this.agentTypeName = agentTypeName;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationTypeName() {
		return stationTypeName;
	}

	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	/**
	 * VisitEdge(agentName,AgentTypeName,StationName,StationTypeName,bold).
	 */
	@Override
	public String toString() {
		return String.format("VisitEdge(%s,%s,%s,%s,%s)",agentName, agentTypeName, stationName,stationTypeName,bold);
	}

	@Override
	public String getPredicatType() {
		return getFormulName();
	}

	@Override
	public void createInstance(FolFormula atom) {
		//PredicateVisitEdge predicate=null;
		Pattern pattern=Pattern.compile("VisitEdge[(](\\w+),(\\w+),(\\w+),(\\w+),(\\w+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
			//predicate=new PredicateVisitEdge(atom);
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.bold=Boolean.parseBoolean(matcher.group(5));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateVisitEdge))
			return false;
		
		PredicateVisitEdge obj=(PredicateVisitEdge)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentName.hashCode()* 11;
	}
	
}
