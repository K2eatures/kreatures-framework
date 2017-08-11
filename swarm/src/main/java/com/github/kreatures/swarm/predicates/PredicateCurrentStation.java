package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,time,isMove).
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class PredicateCurrentStation extends SwarmPredicate {

	
	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private int time;
	private boolean isMove;
	
	
	public PredicateCurrentStation(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateCurrentStation(String agentName,String agentTypeName,String stationName, String stationTypeName, int time, boolean isMove) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName,""+time,""+isMove));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.time=time;
		this.isMove=isMove;
	}

	public PredicateCurrentStation(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateCurrentStation(PredicateCurrentStation other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
		this.time=other.time;
		this.isMove=other.isMove;	
	}	
	
	@Override
	public PredicateCurrentStation clone() {
		return new PredicateCurrentStation(this);
	}
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
	}

	/**
	 * CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,time,isMove).
	 */
	@Override
	public String toString() {
		return String.format("CurrentStation(%s,%s,%s,%s,%d,%d).", agentName, agentTypeName,stationName,stationTypeName,time,isMove);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("CurrentStation[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\w+)[)].");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.time=Integer.parseInt(matcher.group(5));
			this.isMove=Boolean.parseBoolean(matcher.group(6));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other==null || !(other instanceof PredicateCurrentStation ))return false;
		PredicateCurrentStation obj=(PredicateCurrentStation)other;
		String otherName=obj.agentName+obj.stationName;
		String thisName=this.agentName+this.stationName;
		
		return otherName.equals(thisName);
	}

}
