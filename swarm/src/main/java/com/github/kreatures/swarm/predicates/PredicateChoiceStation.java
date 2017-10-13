package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Time,ItemMotiv).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateChoiceStation extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	/**
	 * %StationInfo(StationName,StationTypeName,Time,ItemMotiv).
	 *	%ItemMotiv	=0, if agent can neither take or place item .
	 * 	%		=1, if agent can take but cannot place item
	 *	%		=2, if agent can place but cannot take item
	 *	%		=3, if agent can take and place item
	 */
	private int timeMotiv;
	private int time;
	private int itemMotiv;

	public PredicateChoiceStation(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateChoiceStation(String agentName,String agentTypeName,String stationName, String stationTypeName,int timeMotiv, int time,int itemMotiv) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.timeMotiv=timeMotiv;
		this.time=time;
		this.itemMotiv=itemMotiv;
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
		this.timeMotiv=other.timeMotiv;
		this.time=other.time;
		this.itemMotiv=other.itemMotiv;
	}	
	
	@Override
	public PredicateChoiceStation clone() {
		return new PredicateChoiceStation(this);
	}

	/**
	 * ChoiceStation(AgentName,AgentTypeName,StationName,StationTypeName,Motiv,Time,ItemMotiv).
	 */
	@Override
	public String toString() {
		return String.format("ChoiceStation(%s,%s,%s,%s,%d,%d,%d)", agentName, agentTypeName,stationName,stationTypeName,timeMotiv,time,itemMotiv);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("ChoiceStation[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\d+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.timeMotiv=Integer.parseInt(matcher.group(5));
			this.time=Integer.parseInt(matcher.group(6));
			this.itemMotiv=Integer.parseInt(matcher.group(7));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateChoiceStation))
			return false;
		
		PredicateChoiceStation obj=(PredicateChoiceStation)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentName.hashCode()* 11;
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

	public int getTimeMotiv() {
		return timeMotiv;
	}

	public int getTime() {
		return time;
	}
	/**
	 * %StationInfo(StationName,StationTypeName,Time,ItemMotiv).
	 *	%ItemMotiv	=0, if agent can neither take or place item .
	 * 	%		=1, if agent can take but cannot place item
	 *	%		=2, if agent can place but cannot take item
	 *	%		=3, if agent can take and place item
	 */
	public int getItemMotiv() {
		return itemMotiv;
	}
	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * @param stationTypeName the stationTypeName to set
	 */
	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}

	/**
	 * @param timeMotiv the timeMotiv to set
	 */
	public void setTimeMotiv(int timeMotiv) {
		this.timeMotiv = timeMotiv;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * %StationInfo(StationName,StationTypeName,Time,ItemMotiv).
	 *	%ItemMotiv	=0, if agent can neither take or place item .
	 * 	%		=1, if agent can take but cannot place item
	 *	%		=2, if agent can place but cannot take item
	 *	%		=3, if agent can take and place item
	 * @param itemMotiv the itemMotiv to set
	 */
	public void setItemMotiv(int itemMotiv) {
		this.itemMotiv = itemMotiv;
	}

}
