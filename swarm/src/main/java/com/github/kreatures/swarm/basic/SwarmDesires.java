package com.github.kreatures.swarm.basic;

import java.util.Set;

import com.github.kreatures.core.logic.Desires;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.predicates.PredicateAgent;
import com.github.kreatures.swarm.predicates.PredicateAgentType;
import com.github.kreatures.swarm.predicates.PredicateChoiceStation;
import com.github.kreatures.swarm.predicates.PredicateCurrentStation;
import com.github.kreatures.swarm.predicates.PredicateStation;
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
	private MainDesire currentMainDesire=MainDesire.CHOSE_STATION;
	
	/**
	 * define the current concrete desires as {@link PredicateStation}
	 *  that a agent actually following.
	 */
	private SwarmDesire currentDesire=null;
	/**
	 * store the stay of the next target of the agent.
	 */
	private PredicateCurrentStation currentStation=null;
	/**
	 * store the current {@link PredicateChoiceStation} object.
	 */
	private PredicateChoiceStation currentChoice=null;
	/**
	 * store the state of the current {@link PredicateAgent} object. 
	 */
	private PredicateAgent currentAgent=null;
	/**
	 * store the state of the current {@link PredicateAgentType} object.
	 */
	private PredicateAgentType currentAgentType=null;
	

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
	 * This is the current concrete desires as {@link PredicateStation}
	 *  that a agent actually following.
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

	/**
	 * @return the currentStation
	 */
	public PredicateCurrentStation getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation the currentStation to set
	 */
	public void setCurrentStation(PredicateCurrentStation currentStation) {
		this.currentStation = currentStation;
	}

	/**
	 * @return the currentChoice
	 */
	public PredicateChoiceStation getCurrentChoice() {
		return currentChoice;
	}

	/**
	 * @param currentChoice the currentChoice to set
	 */
	public void setCurrentChoice(PredicateChoiceStation currentChoice) {
		this.currentChoice = currentChoice;
	}

	/**
	 * @return the currentAgentType
	 */
	public PredicateAgentType getCurrentAgentType() {
		return currentAgentType;
	}

	/**
	 * @param currentAgentType the currentAgentType to set
	 */
	public void setCurrentAgentType(PredicateAgentType currentAgentType) {
		this.currentAgentType = currentAgentType;
	}

	/**
	 * @return the currentAgent
	 */
	public PredicateAgent getCurrentAgent() {
		return currentAgent;
	}

	/**
	 * @param currentAgent the currentAgent to set
	 */
	public void setCurrentAgent(PredicateAgent currentAgent) {
		this.currentAgent = currentAgent;
	}
}