package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateChoiceStation extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	
	public PredicateChoiceStation(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateChoiceStation(String agentName,String agentTypeName,String stationName, String stationTypeName) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
	}

	public PredicateChoiceStation(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateChoiceStation(PredicateChoiceStation other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
	}	
	
	@Override
	public PredicateChoiceStation clone() {
		return new PredicateChoiceStation(this);
	}
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
	}

	/**
	 * ChoiceStation(AgentName,AgetnTypeName,StationName,StationTypeName).
	 */
	@Override
	public String toString() {
		return String.format("ChoiceStation(%s,%s,%s,%s).", agentName, agentTypeName,stationName,stationTypeName);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("ChoiceStation[(](\\w+),(\\w+),(\\w+),(\\w+)[)].");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other==null || !(other instanceof PredicateChoiceStation ))return false;
		PredicateChoiceStation obj=(PredicateChoiceStation)other;
		String otherName=obj.agentName+obj.stationName;
		String thisName=this.agentName+this.stationName;
		
		return otherName.equals(thisName);
	}

}
