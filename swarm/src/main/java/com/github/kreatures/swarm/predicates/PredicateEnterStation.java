package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * EnterStation(AgentName,AgentTypeName,StationName,StationTypeName,motiv).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateEnterStation extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private int motiv;
	
	public PredicateEnterStation(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateEnterStation(String agentName,String agentTypeName,String stationName, String stationTypeName,int motiv) throws Exception {
		super(TransformPredicates.getLiteral("EnterStation",agentName,agentTypeName,stationName,stationTypeName,""+motiv));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.motiv=motiv;
	}

	public PredicateEnterStation(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateEnterStation(PredicateEnterStation other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
		this.motiv=other.motiv;
	}	
	
	@Override
	public PredicateEnterStation clone() {
		return new PredicateEnterStation(this);
	}
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
	}

	/**
	 * EnterStation(AgentName,AgetnTypeName,StationName,StationTypeName,Motiv).
	 */
	@Override
	public String toString() {
		return String.format("EnterStation(%s,%s,%s,%s,%d)", agentName, agentTypeName,stationName,stationTypeName,motiv);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("EnterStation[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.motiv=Integer.parseInt(matcher.group(5));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other==null || !(other instanceof PredicateEnterStation ))return false;
		PredicateEnterStation obj=(PredicateEnterStation)other;
		String otherName=obj.agentName+obj.stationName;
		String thisName=this.agentName+this.stationName;
		
		return otherName.equals(thisName);
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

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @return the stationTypeName
	 */
	public String getStationTypeName() {
		return stationTypeName;
	}

	/**
	 * @return the motiv
	 */
	public int getMotiv() {
		return motiv;
	}
	
//	@Override
//	public String getPredicatType() {
//		return "EnterStation";
//	}
}
