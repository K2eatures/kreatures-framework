/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * NecAgentStation(AgentName,StationName,Nec).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateNecAgentStation extends SwarmPredicate {
	
	private String agentName;
	private String stationName;
	private int countNec;
	
	public PredicateNecAgentStation(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateNecAgentStation(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateNecAgentStation(PredicateNecAgentStation other) {
		super(other);
		this.agentName=other.agentName;
		this.stationName=other.stationName;
		this.countNec=other.countNec;
	}	
	
	@Override
	public PredicateNecAgentStation clone() {
		return new PredicateNecAgentStation(this);
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


	public void incrementNec() {
		countNec++;
	}
	
	/**
	 * NecAgentStation(AgentName,StationName,Nec). 
	 */
	@Override
	public String toString(){
		return String.format("NecAgentStation(%s,%s,%d) ", agentName,stationName,countNec);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateNecAgentStation predicate=null;
		Pattern pattern=Pattern.compile("NecAgentStation[(](\\w+),(\\w+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			predicate=new PredicateNecAgentStation();
			this.agentName=matcher.group(1);
			this.stationName=matcher.group(2);
			this.countNec=Integer.parseInt(matcher.group(3));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateNecAgentStation))
			return false;
		
		PredicateNecAgentStation obj=(PredicateNecAgentStation)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentName.hashCode()* 11;
	}
	
}
