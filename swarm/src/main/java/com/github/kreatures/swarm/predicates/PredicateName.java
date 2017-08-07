package com.github.kreatures.swarm.predicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * Contains the name of all the AbstractSwarm representation of desire.
 * It is used to convert a tweety represntation of desire to a AbstractSwarm 
 * representation of desire.
 * @author Cedric Perez Donfack
 * @see SwarmPredicate for AbstractSwarm representation of desire
 * @see FolFormula for tweety representation of desire.
 */
public enum PredicateName {

	Agent,
	Station,
	VisitEdge,
	PlacedEdge,
	TimeEdge,
	ItemSetLoadingAgent,
	ItemSetLoadingStation,
	NecAgentStation,
	TimeEdgeState;
	/**
	 * @param desire tweety representation of desire.
	 * @return AbstractSwarm reprsentation of desire.
	 * @throws NullPointerException when there are no desire AbstractSwarm representation of this tweety represenation
	 */
	@SuppressWarnings("unchecked")
	public <T extends SwarmPredicate> T getSwarmPredicate(FolFormula desire) {
		switch(this) {
			case Agent: return (T) new PredicateAgent(desire);
			case ItemSetLoadingAgent: return (T) new PredicateItemSetLoadingAgent(desire);
			case ItemSetLoadingStation: return (T) new PredicateItemSetLoadingStation(desire);
			case NecAgentStation:return (T) new PredicateNecAgentStation(desire);
			case PlacedEdge:return (T) new PredicatePlaceEdge(desire);
			case Station:return (T) new PredicateStation(desire);
			case TimeEdge:return (T) new PredicateTimeEdge(desire);
			case TimeEdgeState:return (T) new PredicateTimeEdgeState(desire);
			case VisitEdge:return (T) new PredicateVisitEdge(desire);
			default: throw new NullPointerException("there are no desire swarm representation of this tweety represenation");
		}
	}
}
