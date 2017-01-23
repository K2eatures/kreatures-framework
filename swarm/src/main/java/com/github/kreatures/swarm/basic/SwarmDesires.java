package com.github.kreatures.swarm.basic;

import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.fol.syntax.FOLAtom;

import java.util.ArrayList;
import java.util.Collection;

import com.github.kreatures.core.Desire;
import com.github.kreatures.core.logic.Desires;
import com.github.kreatures.swarm.Utility;

/**
 * 
 * @author donfack
 *
 */
public class SwarmDesires extends Desires {
	
	
	/**
	 * A agent want to select a station as next destination.
	 */
	public static final Desire CHOICE_STATION= new Desire(new FOLAtom(new Predicate("ChoicesSation()")));
//	/**
//	 * A agent want to change the current's station.
//	 */
//	public static final Desire CHANGE_STATION = new Desire(new FOLAtom(new Predicate("ChangeStation()")));
	/**
	 * A agent want to leave the station.
	 */
	public static final Desire LEAVE_STATION= new Desire(new FOLAtom(new Predicate("LeaveStation()")));
	/**
	 * A agent is visiting a current station and want to stay again.
	 */
	public static final Desire STAY_STATION = new Desire(new FOLAtom(new Predicate("StayStation()")));
	/**
	 * A agent want to enter a station.
	 */
	public static final Desire ENTER_STATION = new Desire(new FOLAtom(new Predicate("EnterStation")));
	
	/**
	 * list of all the current options.
	 */
	private Collection<Desire> desires;
	
	/** Default Ctor: Used for dynamic instantiation */
	public SwarmDesires() {
		desires=new ArrayList<>();
	}
	
	public void clearDesires() {
		desires.clear();
	}
	
	public void addDesire(Desire desire) {
		desires.add(desire);
	}
	
	public void removeDesire(Desire desire) {
		desires.remove(desire);
	}
	
	public void addDesire(int index) {
		desires.remove(index);
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(super.hashCode(),desires);
	}
	
	
	@Override
	public String toString() {
		return (desires==null?"":desires.toString());
	}
}