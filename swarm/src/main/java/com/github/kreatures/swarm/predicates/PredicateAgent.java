package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * Agent(AgentName,AgentType,freq,nec,cap).
 * 
 * @author Cedric Perez Donfack
 *
 */

public class PredicateAgent extends SwarmPredicate {
	/**
	 * This is the Agent name and has to be unique.
	 */
	private String name;
	private String typeName;
	private int frequency;
	private int necessity;
	private int capacity;
	
	public PredicateAgent(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateAgent(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateAgent(PredicateAgent other) {
		super(other);
		this.name=other.name;
		this.typeName=other.typeName;
		this.frequency=other.frequency;
		this.necessity=other.necessity;
		this.capacity=other.capacity;	
	}	
	
	@Override
	public PredicateAgent clone() {
		return new PredicateAgent(this);
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

	public void incrFrequency() {
		this.frequency++;
	}

	public int getNecessity() {
		return necessity;
	}

	public void incrNecessity() {
		this.necessity++;
	}

	public int getCapacity() {
		return capacity;
	}

	public void incrCapacity(int itemCapacity) {
		this.capacity+= itemCapacity;
	}
	public void decrCapacity(int itemCapacity) {
		itemCapacity=this.capacity-itemCapacity;
		if(itemCapacity<=0) {
			this.capacity=0;
			return;
		}
		
		this.capacity= itemCapacity;
	}

	/**
	 * Agent(AgentName,AgentType,freq,nec,cap).
	 */
	@Override
	public String toString() {
		return String.format("Agent(%s,%s,%d,%d,%d)", name, typeName, frequency, necessity, capacity);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("Agent[(](\\w+),(\\w+),(\\d+),(\\d+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.name=matcher.group(1);
			this.typeName=matcher.group(2);
			this.frequency=Integer.parseInt(matcher.group(3));
			this.necessity=Integer.parseInt(matcher.group(4));
			this.capacity=Integer.parseInt(matcher.group(5));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateAgent))
			return false;
		
		PredicateAgent obj=(PredicateAgent)other;
		boolean isName=obj.name==null?this.name==null:obj.name.equals(this.name);
		boolean isTypeName=obj.typeName==null?this.typeName==null:obj.typeName.equals(this.typeName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.typeName.hashCode()* 11;
	}
}
