package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private static PredicateVisitEdge instance=new PredicateVisitEdge();
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	private PredicateVisitEdge() {
	
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
		return String.format("VisitEdge(%s,%s,%s,%s,%s).",agentName, agentTypeName, stationName,stationTypeName,bold);
	}

	@Override
	public String getPredicatType() {
		return "VisitEdge";
	}

	public static PredicateVisitEdge getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PredicateVisitEdge createInstance(String fact) {
		PredicateVisitEdge predicate=null;
		Pattern pattern=Pattern.compile("VisitEdge[(](\\w+),(\\w+),(\\w+),(\\w+),({true|false})[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateVisitEdge();
			predicate.agentName=matcher.group(1);
			predicate.agentTypeName=matcher.group(2);
			predicate.stationName=matcher.group(3);
			predicate.stationTypeName=matcher.group(4);
			predicate.bold=Boolean.parseBoolean(matcher.group(5));
			
			
		}
		
		return predicate;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PredicateVisitEdge))
			return false;
		PredicateVisitEdge obj = (PredicateVisitEdge) other;

		if (obj.getAgentName() == null || this.getAgentName() == null) {
			return false;
		}
		if (obj.getStationName() == null || this.getStationName() == null) {
			return false;
		}

		if (obj.getAgentName().equals(this.getAgentName())
				&& obj.getStationName().equals(this.getStationName())) {
			return true;
		}

		return false;
	}
}
