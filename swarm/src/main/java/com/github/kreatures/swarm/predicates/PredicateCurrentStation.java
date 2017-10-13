package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * 
 * CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,IsInStation,HasChoose).
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class PredicateCurrentStation extends SwarmPredicate {

	
	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private boolean isInStation;
	private boolean hasChoose;
	
	public PredicateCurrentStation(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateCurrentStation(String agentName,String agentTypeName,String stationName, String stationTypeName, boolean isInStation,boolean hasChoose) throws Exception {
		super(TransformPredicates.getLiteral("CurrentStation",agentName,agentTypeName,stationName,stationTypeName,""+isInStation,""+hasChoose));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.isInStation=isInStation;
		this.hasChoose=hasChoose;
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
		this.isInStation=other.isInStation;
		this.hasChoose=other.hasChoose;
	}	
	
	@Override
	public PredicateCurrentStation clone() {
		return new PredicateCurrentStation(this);
	}

	/**
	 * CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,IsInStation,HasChoose).
	 */
	@Override
	public String toString() {
		return String.format("CurrentStation(%s,%s,%s,%s,%b,%b)", agentName, agentTypeName,stationName,stationTypeName,isInStation,hasChoose);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("CurrentStation[(](\\w+),(\\w+),(\\w+),(\\w+),(\\w+),(\\w+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.isInStation=Boolean.parseBoolean(matcher.group(5));
			this.hasChoose=Boolean.parseBoolean(matcher.group(6));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateCurrentStation))
			return false;
		
		PredicateCurrentStation obj=(PredicateCurrentStation)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.agentTypeName==null?this.agentTypeName==null:obj.agentTypeName.equals(this.agentTypeName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentTypeName.hashCode()* 11;
	}
	
	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @param stationName the stationName to set
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	/**
	 * @return the stationTypeName
	 */
	public String getStationTypeName() {
		return stationTypeName;
	}

	/**
	 * @param stationTypeName the stationTypeName to set
	 */
	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}
	/**
	 * @return the isInStation
	 */
	public boolean isIsInStation() {
		return isInStation;
	}

	/**
	 * @param isInStation the isInStation to set
	 */
	public void setIsInStation(boolean isInStation) {
		this.isInStation = isInStation;
	}

	/**
	 * @return the hasChoose
	 */
	public boolean isHasChoose() {
		return hasChoose;
	}

	/**
	 * @param hasChoose the hasChoose to set
	 */
	public void setHasChoose(boolean hasChoose) {
		this.hasChoose = hasChoose;
	}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @return the agentTypeName
	 */
	public String getAgentTypeName() {
		return agentTypeName;
	}	
	
//	@Override
//	public String getPredicatType() {
//		return "CurrentStation";
//	}
}
