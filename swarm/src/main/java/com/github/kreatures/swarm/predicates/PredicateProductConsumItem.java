package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,ItemNumber,Motiv).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateProductConsumItem extends SwarmPredicate {

	private String agentName;
	private String agentTypeName;
	private String stationName;
	private String stationTypeName;
	private int itemNumber;
	private int motiv;
	
	
	public PredicateProductConsumItem(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	public PredicateProductConsumItem(String agentName,String agentTypeName,String stationName, String stationTypeName,int itemNumber,int motiv) throws Exception {
		super(TransformPredicates.getLiteral("ProductConsumItem",agentName,agentTypeName,stationName,stationTypeName,itemNumber+"",motiv+""));
		this.agentName=agentName;
		this.agentTypeName=agentTypeName;
		this.stationName=stationName;
		this.stationTypeName=stationTypeName;
		this.itemNumber=itemNumber;
		this.motiv=motiv;
	}

	public PredicateProductConsumItem(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateProductConsumItem(PredicateProductConsumItem other) {
		super(other);
		this.agentName=other.agentName;
		this.agentTypeName=other.agentTypeName;
		this.stationName=other.stationName;
		this.stationTypeName=other.stationTypeName;
		this.itemNumber=other.itemNumber;
		this.motiv=other.motiv;
	}	
	
	@Override
	public PredicateProductConsumItem clone() {
		return new PredicateProductConsumItem(this);
	}
	
	/**
	 * ProductConsumItem(AgentName,AgentTypeName,StationName,StationTypeName,ItemNumber,Motiv).
	 */
	@Override
	public String toString() {
		return String.format("ProductConsumItem(%s,%s,%s,%s,%d,%d)", agentName, agentTypeName,stationName,stationTypeName,itemNumber,motiv);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("ProductConsumItem[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\d)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.agentTypeName=matcher.group(2);
			this.stationName=matcher.group(3);
			this.stationTypeName=matcher.group(4);
			this.itemNumber=Integer.parseInt(matcher.group(5));
			this.motiv=Integer.parseInt(matcher.group(6));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateProductConsumItem))
			return false;
		
		PredicateProductConsumItem obj=(PredicateProductConsumItem)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationName==null?this.stationName==null:obj.stationName.equals(this.stationName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentName.hashCode()* 11;
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
	 * @return the itemNumber
	 */
	public int getItemNumber() {
		return itemNumber;
	}

	/**
	 * @return the motiv
	 */
	public int getMotiv() {
		return motiv;
	}
	
//	@Override
//	public String getPredicatType() {
//		return "ProductConsumItem";
//	}
}
