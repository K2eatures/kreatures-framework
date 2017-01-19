package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Agent(AgentName,AgentType,freq,nec,cap).
 * 
 * @author donfack
 *
 */

public class PredicateAgent implements SwarmPredicate {
	/**
	 * This is the Agent name and has to be unique.
	 */
	private String name;
	private String typeName;
	private int frequency;
	private int necessity;
	private int capacity;
	private static PredicateAgent instance =new PredicateAgent();
	/**
	 * This constructor is use to make a copy of the object.
	 * 
	 * @param other
	 *            object to copy
	 */
	private PredicateAgent() {

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getNecessity() {
		return necessity;
	}

	public void setNecessity(int necessity) {
		this.necessity = necessity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public static PredicateAgent getInstance(String fact){
		return instance.createInstance(fact);
	}

	/**
	 * Agent(AgentName,AgentType,freq,nec,cap).
	 */
	@Override
	public String toString() {
		return String.format("Agent(%s,%s,%d,%d,%d).", name, typeName, frequency, necessity, capacity);
	}

	@Override
	public String getPredicatType() {

		return "Agent";
	}

	//@SuppressWarnings("unchecked")
	public PredicateAgent createInstance(String fact) {
		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("Agent[(](\\w+),(\\w+),(\\d+),(\\d+),(\\d+)[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			agent=new PredicateAgent();
			agent.name=matcher.group(1);
			agent.typeName=matcher.group(2);
			agent.frequency=Integer.parseInt(matcher.group(3));
			agent.necessity=Integer.parseInt(matcher.group(4));
			agent.capacity=Integer.parseInt(matcher.group(5));
			
		}
		
		return agent;
	}

}
