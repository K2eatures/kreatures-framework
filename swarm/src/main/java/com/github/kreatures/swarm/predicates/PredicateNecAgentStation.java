/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NecAgentStation(AgentName,StationName,Nec).
 * @author donfack
 *
 */
public class PredicateNecAgentStation implements SwarmPredicate {
	
	private String agentName;
	private String stationName;
	private int countNec;
	private static PredicateNecAgentStation instance=new PredicateNecAgentStation();
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	private PredicateNecAgentStation() {
		
	}
	
	
	public String getAgentName() {
		return agentName;
	}


	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}


	public String getStationName() {
		return stationName;
	}


	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


	public int getCountNec() {
		return countNec;
	}


	public void setCountNec(int countNec) {
		this.countNec = countNec;
	}


	/**
	 * NecAgentStation(AgentName,StationName,Nec). 
	 */
	@Override
	public String toString(){
		return String.format("NecAgentStation(%s,%s,%d). ", agentName,stationName,countNec);
	}



	@Override
	public String getPredicatType() {
		
		return "NecAgentStation";
	}

	public static PredicateNecAgentStation getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@Override
	public PredicateNecAgentStation createInstance(String fact) {
		PredicateNecAgentStation predicate=null;
		Pattern pattern=Pattern.compile("NecAgentStation[(](\\w+),(\\w+),(\\d+)[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateNecAgentStation();
			predicate.agentName=matcher.group(1);
			predicate.stationName=matcher.group(2);
			predicate.countNec=Integer.parseInt(matcher.group(3));
			System.out.println(predicate.toString());
		}
		
		return predicate;
	}
	
}
