package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * ConsumItem(AgentName,AgentTypeName,StationName,StationTypeName).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateConsumItem extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	
	public PredicateConsumItem(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateConsumItem(String agentName,String agentTypeName,String stationName, String stationTypeName) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
	}

	public PredicateConsumItem(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateConsumItem(PredicateConsumItem other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
	}	
	
	@Override
	public PredicateConsumItem clone() {
		return new PredicateConsumItem(this);
	}

	/**
	 * ConsumItem(AgentName,AgetnTypeName,StationName,StationTypeName).
	 */
	@Override
	public String toString() {
		return String.format("ConsumItem(%s,%s,%s,%s)", agentName, agentTypeName,stationName,stationTypeName);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("ConsumItem[(](\\w+),(\\w+),(\\w+),(\\w+)[)].");
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
		if(!(other instanceof PredicateConsumItem))
			return false;
		
		PredicateConsumItem obj=(PredicateConsumItem)other;
		boolean isAgName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isAgTypeName=obj.agentTypeName==null?this.agentTypeName==null:obj.agentTypeName.equals(this.agentTypeName);
		boolean isStName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		boolean isStTypeName=obj.stationTypeName==null?this.stationTypeName==null:obj.stationTypeName.equals(this.stationTypeName);
		
		return isAgName & isAgTypeName & isStName & isStTypeName;
	}
	
	@Override
	public int hashCode() {
		return (this.agentTypeName.hashCode()+this.stationTypeName.hashCode())* 11;
	}
}
