/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;


/**
 * ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber).
 * 
 * @author donfack
 *
 */
public class PredicateItemSetLoadingAgent extends SwarmPredicate {

	private String agentName;
	private String stationTypeName;
	private int itemNumber;

	public PredicateItemSetLoadingAgent(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateItemSetLoadingAgent(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateItemSetLoadingAgent(PredicateItemSetLoadingAgent other) {
		super(other);
		this.agentName=other.agentName;
		this.stationTypeName=other.stationTypeName;
		this.itemNumber=other.itemNumber;
		
	}	
	
	@Override
	public PredicateItemSetLoadingAgent clone() {
		return new PredicateItemSetLoadingAgent(this);
	}
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getStationTypeName() {
		return stationTypeName;
	}

	public void setStationTypeName(String stationTypeName) {
		this.stationTypeName = stationTypeName;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void incrItemNumber(int itemNber) {
		this.itemNumber+= itemNber;
	}
	public void decrItemNumber(int itemNber) {
		itemNber=this.itemNumber-itemNber;
		if(itemNber<=0) {
			this.itemNumber=0;
			return;
		}
		this.itemNumber= itemNber;
	}
	
	@Override
	public void createInstance(FolFormula atom) {
//		PredicateItemSetLoadingAgent predicate = null;
		Pattern pattern = Pattern.compile("ItemSetLoadingAgent[(](\\w+),(\\w+),(\\d+)[)]");
		Matcher matcher = pattern.matcher(atom.toString());
		if (matcher.find()) {
//			predicate = new PredicateItemSetLoadingAgent();
			this.agentName = matcher.group(1);
			this.stationTypeName = matcher.group(2);
			this.itemNumber = Integer.parseInt(matcher.group(3));
		}
	}

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateItemSetLoadingAgent))
			return false;
		
		PredicateItemSetLoadingAgent obj=(PredicateItemSetLoadingAgent)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeName=obj.stationTypeName==null?this.stationTypeName==null:obj.stationTypeName.equals(this.stationTypeName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.agentName.hashCode()* 11;
	}

	/**
	 * ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber).
	 */
	@Override
	public String toString() {
		return String.format("ItemSetLoadingAgent(%s,%s,%d)", agentName, stationTypeName, itemNumber);
	}

}
