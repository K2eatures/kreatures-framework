package com.github.kreatures.swarm.basic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.logic.Desires;
import com.github.kreatures.core.util.Pair;
import com.github.kreatures.swarm.SwarmConst;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.predicates.PredicateAgent;
import com.github.kreatures.swarm.predicates.PredicateAgentType;
import com.github.kreatures.swarm.predicates.PredicateChoiceStation;
import com.github.kreatures.swarm.predicates.PredicateCurrentStation;
import com.github.kreatures.swarm.predicates.PredicateStation;
import com.github.kreatures.swarm.predicates.PredicateTimeEdgeState;
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
	/**
	 * The needDesires contents not only the generate desires (ie: the really generated desires), 
	 * but it can also have informations which will be used choice one desire and informations to create the plan 
	 * corresponding to the choice desire.
	 */	
	private Set<SwarmPredicate> needDesires=new HashSet<>();
	/**
	 * the desire which will be chosen at the last time, 
	 * for the currently choice.
	 */
	private SwarmDesire lastDesire=null;
	
	/**
	 * store timeEdgestates components to controller the time-components
	 */
	private Set<PredicateTimeEdgeState> timeEdgeStateSet=new HashSet<>();
	
	/**
	 * How long a agent can wait.
	 * Actually, it can only wait 4 units.
	 */
	private int waitTime=SwarmConst.WAIT_TIME.getValue();
	/**
	 * The first parameter is use to checked whether the agent can enter a station or not. 
	 * True means that the agent can enter a station and false otherwise.
	 */
	private Pair<Boolean, PlanElement> checkEnterStation=new Pair<>();
	{
		checkEnterStation.first=false;
	}
	/**
	 * The first parameter is use to checked whether the agent can enter a station or not. 
	 * 
	 * @param first True means that the agent can enter a station and false otherwise.
	 * @param second the plan whose action a agent has to repeat.
	 */
	public void setCheckEnterStation(Boolean first,PlanElement second) {
		this.checkEnterStation.first=first;
		this.checkEnterStation.second=second;
	}
	/**
	 * The first parameter is use to checked whether the agent can enter a station or not.
	 * True means that the agent can enter a station and false otherwise.
	 * @return a pair boolean and plan. first=true the agent do the next plan.
	 * first=false then agent repeat plan in second.
	 */
	public Pair<Boolean, PlanElement> getCheckEnterStation(){
		return checkEnterStation;
	}
	
	
	
	/**
	 * @return the timeEdgeState
	 */
	public Set<PredicateTimeEdgeState> getTimeEdgeState() {
		return timeEdgeStateSet;
	}

	/**
	 * @param timeEdgeState the timeEdgeState to set
	 */
	public void setTimeEdgeState(Set<PredicateTimeEdgeState> timeEdgeStateSet) {
		this.timeEdgeStateSet.clear();
		this.timeEdgeStateSet.addAll( timeEdgeStateSet);
	}

	/**
	 * @return the waitTime
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void decrWaitTime() {
		this.waitTime++;
	}

	/**
	 * @param waitTime the waitTime to set
	 */
	public void initWaitTime() {
		this.waitTime=SwarmConst.WAIT_TIME.getValue();
	}
	
	/**
	 * the desire which will be chosen at the last time, 
	 * for the currently choice.
	 * @return the lastDesire is a PredicateStation
	 */
	public SwarmDesire getLastDesire() {
		return lastDesire;
	}

	/**
	 * the desire which will be chosen at the last time, 
	 * for the currently choice.
	 * @param lastDesire the last Desire to set
	 */
	public void setLastDesire(SwarmDesire lastDesire) {
		this.lastDesire = lastDesire;
	}

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
	/**
	 * 
	 * @param desire can be a choice desire or a information which will be use to evaluate the choice desire. 
	 * @return true if the object is removed or false otherwise.
	 */
	public boolean removeDesire(SwarmPredicate desire) {
		return super.remove(desire);
	}
	
	
	
	/**
	 * Adds a set of generated desires and shows it on the screen.
	 * @param desire can be a choice desire or a information which will be use to evaluate the choice desire. 
	 * @return a set with the new objects.
	 */
	public void addDesires(Set<SwarmPredicate> desires) {
		desires.stream().forEach(desire->{
			super.add(desire);
		});
	}
	
	/**
	 * The needDesires contents not only the generate desires (ie: the really generated desires), 
	 * but it can also have informations which will be used choice one desire and informations to create the plan 
	 * corresponding to the choice desire.
	 * add a set of needed desires
	 * @param choiceDesire this is a new desire, which can be choose by the agent. 
	 */
	public void addNeedDesires(Set<SwarmPredicate> needesDesires) {
		needesDesires.stream().forEach(desire->{
			this.needDesires.add(desire);
		});
	}
	
	/**
	 * The needDesires contents not only the generate desires (ie: the really generated desires), 
	 * but it can also have informations which will be used choice one desire and informations to create the plan 
	 * corresponding to the choice desire.
	 * @return all the needed desires. 
	 */
	public Set<SwarmPredicate> getNeedDesires() {
			return (Set<SwarmPredicate>) Collections.unmodifiableSet(needDesires);
	}
	
	/**
	 * The needDesires contents not only the generate desires (ie: the really generated desires), 
	 * but it can also have informations which will be used choice one desire and informations to create the plan 
	 * corresponding to the choice desire.
	 * add needed desires
	 * @param choiceDesire this is a new desire, which can be choose by the agent. 
	 */
	public void addNeedDesire(SwarmPredicate needDesire) {
		this.needDesires.add(needDesire);
	}
	/**
	 * 
	 * @return number of generate desires.
	 */
	public int sizeDesires() {
		return this.getDesires().size();
	}
	/**
	 * @return true iff there are no generated desires. 
	 */
	public boolean isEmptyDesires() {
		return this.getDesires().isEmpty();
	}
	
	/**
	 * add a generated desires.
	 * @param desire generated desires.
	 */
	public void addDesire(SwarmPredicate desire) {
		this.add(desire);
	}
	
	/**
	 * remove a set of generated desires.
	 * @param desire generated desires.
	 */
	public void removeDesires(Set<SwarmPredicate> desires) {
		desires.stream().forEach(desire->{
			this.remove(desire);
		});
	}
	
	
	@Override
	public int hashCode() {
		
		return Utility.computeHashCode(super.hashCode());
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
	
	/**
	 * Removes all current desires.
	 */
	@Override
	public void clear() {
		super.clear();
		this.needDesires= new HashSet<>();
	}
	
	@Override
	public String toString() {
		return needDesires.toString();
	}
}