package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * AgentType(AgentTypeName,freq,nec,time,prio,cycle,cap,size,speed).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateAgentType extends SwarmPredicate {

	/**
	 * This is the AgentType name and has to be unique.
	 */
	private String typeName;
	private int frequency;
	private int necessity;
	private int time;
	private int priority;
	private int cycle;
	private int capacity;
	private int size;
	private int speed;
	
	public PredicateAgentType(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateAgentType(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateAgentType(PredicateAgentType other) {
		super(other);
		this.typeName=other.typeName;
		this.frequency=other.frequency;
		this.necessity=other.necessity;
		this.time=other.time;
		this.priority=other.priority;
		this.cycle=other.cycle;
		this.capacity=other.capacity;
		this.size=other.size;
		this.speed=other.speed;
	}	
	
	
	@Override
	public PredicateAgentType clone() {
		return new PredicateAgentType(this);
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

	public int getNecessity() {
		return necessity;
	}

	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @return the cycle
	 */
	public int getCycle() {
		return cycle;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * AgentType(AgentTypeName,freq,nec,time,prio,cycle,cap,size,speed).
	 */
	@Override
	public String toString() {
		return String.format("AgentType(%s,%d,%d,%d,%d,%d,%d,%d,%d)", typeName, frequency, necessity,time,priority,cycle, capacity,size,speed);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("AgentType[(](\\w+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.typeName=matcher.group(1);
			this.frequency=Integer.parseInt(matcher.group(2));
			this.necessity=Integer.parseInt(matcher.group(3));
			this.time=Integer.parseInt(matcher.group(4));
			this.priority=Integer.parseInt(matcher.group(5));
			this.cycle=Integer.parseInt(matcher.group(6));
			this.capacity=Integer.parseInt(matcher.group(7));
			this.size=Integer.parseInt(matcher.group(8));
			this.speed=Integer.parseInt(matcher.group(9));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateAgentType))
			return false;
		
		PredicateAgentType obj=(PredicateAgentType)other;
		return obj.typeName==null?this.typeName==null:obj.typeName.equals(this.typeName);
	}
	
	@Override
	public int hashCode() {
		return 11;
	}
}
