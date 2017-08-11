package com.github.kreatures.swarm.basic;

import java.util.Set;

import com.github.kreatures.core.logic.Desires;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

/**
 * SwarmDesires are a set of desires that a agent has and can choice one 
 * which it want to become true.  
 * @author Cedric Perez Donfack
 * @see Desires
 */
public class SwarmDesires extends Desires {
	
	/**
	 * define the current main desire that a agent actually following.
	 * the first current main desire is the choice of a station.
	 */
	private MainDesire currentMainDesire=MainDesire.STATION_CHOICE;
	
	/**
	 * define the current concrete desires as predicate
	 *  that a agent actually following.
	 */
	private SwarmDesire currentDesire;

//	/**
//	 * list of all the current options.
//	 */
//	private Set<SwarmPredicate> curentDesires;
	
	/** Default Ctor: Used for dynamic instantiation */
	public SwarmDesires() {
//		curentDesires=new HashSet<>();
	}
	
	/**
	 *  Ctor: with given set of desires.
	 * @param desires set of desires
	 */
	public SwarmDesires(SwarmDesires otherDesires) {
		super(otherDesires);
	}
	
	public boolean removeDesire(SwarmPredicate desire) {
		return this.remove(desire);
	}
	
	public void addDesires(Set<SwarmPredicate> desires1) {
		desires1.stream().forEach(desire->{
			this.add(desire);
		});
	}
	
	public int sizeDesires() {
		return this.getDesires().size();
	}
	
	public boolean isEmptyDesires() {
		return this.getDesires().isEmpty();
	}
	public void addDesire(SwarmPredicate desire) {
		this.add(desire);
	}
	
	public void removeDesires(Set<SwarmPredicate> desires) {
		desires.stream().forEach(desire->{
			this.remove(desire);
		});
	}
	
	
	@Override
	public int hashCode() {
		
		return Utility.computeHashCode(super.hashCode(),getDesires());
	}
	
	
	@Override
	public String toString() {
		return getDesires().toString();
	}

	/**
	 * @return the actual desire
	 */
	public MainDesire getCurrentMainDesire() {
		return currentMainDesire;
	}

	/**
	 * @param currentDesire the actual desire to set
	 */
	public void setCurrentMainDesire(MainDesire currentDesire) {
		this.currentMainDesire = currentDesire;
	}

	/**
	 * @return the currentDesire
	 */
	public SwarmDesire getCurrentDesire() {
		return currentDesire;
	}

	/**
	 * @param currentDesire the currentDesire to set
	 */
	public void setCurrentDesire(SwarmDesire currentDesire) {
		this.currentDesire = currentDesire;
	}
}