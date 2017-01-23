/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

	private static PredicateItemSetLoadingAgent instance = new PredicateItemSetLoadingAgent();

	private PredicateItemSetLoadingAgent() {

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

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	public static PredicateItemSetLoadingAgent getInstance(String fact) {
		return instance.createInstance(fact);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PredicateItemSetLoadingAgent createInstance(String fact) {
		PredicateItemSetLoadingAgent predicate = null;
		Pattern pattern = Pattern.compile("ItemSetLoadingAgent[(](\\w+),(\\w+),(\\d+)[)].");
		Matcher matcher = pattern.matcher(fact);
		if (matcher.find()) {
			predicate = new PredicateItemSetLoadingAgent();
			predicate.agentName = matcher.group(1);
			predicate.stationTypeName = matcher.group(2);
			predicate.itemNumber = Integer.parseInt(matcher.group(3));

		}

		return predicate;
	}

	/**
	 * ItemSetLoadingAgent(AgentName,StationTypeName,ItemNumber).
	 */
	@Override
	public String toString() {
		return String.format("ItemSetLoadingAgent(%s,%s,%d).", agentName, stationTypeName, itemNumber);
	}

	@Override
	public String getPredicatType() {

		return "ItemSetLoadingAgent";
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof PredicateItemSetLoadingAgent))
			return false;
		PredicateItemSetLoadingAgent obj = (PredicateItemSetLoadingAgent) other;

		if (obj.getAgentName() == null || this.getAgentName() == null) {
			return false;
		}
		if (obj.getStationTypeName() == null || this.getStationTypeName() == null) {
			return false;
		}

		if (obj.getAgentName().equals(this.getAgentName())
				&& obj.getStationTypeName().equals(this.getStationTypeName())) {
			return true;
		}

		return false;
	}
}
