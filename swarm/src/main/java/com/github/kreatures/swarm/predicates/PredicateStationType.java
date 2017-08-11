package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * StationType(StationTypeName,freq,nec,time,prio,cylce,item,space,count).
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class PredicateStationType extends SwarmPredicate {


	/**
	 * This is the AgentType name and has to be unique.
	 */
	private String typeName;
	private int frequency;
	private int necessity;
	private int time;
	private int priority;
	private int cycle;
	private int item;
	private int space;
	private int count;
	
	public PredicateStationType(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateStationType(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateStationType(PredicateStationType other) {
		super(other);
		this.typeName=other.typeName;
		this.frequency=other.frequency;
		this.necessity=other.necessity;
		this.time=other.time;
		this.priority=other.priority;
		this.cycle=other.cycle;
		this.item=other.item;
		this.space=other.space;
		this.count=other.count;
	}	
	
	
	@Override
	public PredicateStationType clone() {
		return new PredicateStationType(this);
	}
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void incrementFrequency() {
		frequency++;
	}

	public int getNecessity() {
		return necessity;
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
	 * @return the item
	 */
	public int getItem() {
		return item;
	}

	/**
	 * @return the space
	 */
	public int getSpace() {
		return space;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * StationType(StationTypeName,freq,nec,time,prio,cylce,item,space,count).
	 */
	@Override
	public String toString() {
		return String.format("StationType(%s,%d,%d,%d,%d,%d,%d,%d,%d).", typeName, frequency, necessity,time,priority,cycle, item,space,count);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("StationType[(](\\w+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)[)].");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.typeName=matcher.group(1);
			this.frequency=Integer.parseInt(matcher.group(2));
			this.necessity=Integer.parseInt(matcher.group(3));
			this.time=Integer.parseInt(matcher.group(4));
			this.priority=Integer.parseInt(matcher.group(5));
			this.cycle=Integer.parseInt(matcher.group(6));
			this.item=Integer.parseInt(matcher.group(7));
			this.space=Integer.parseInt(matcher.group(8));
			this.count=Integer.parseInt(matcher.group(9));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(other==null || !(other instanceof PredicateStationType ))
			return false;
		
		PredicateStationType obj=(PredicateStationType)other;
		
		if(obj.typeName!=null && this.typeName!=null) {
			return obj.typeName.equals(this.typeName);
		}
		
		return false;
	}

}
