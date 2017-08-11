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
	/* Define all desires properties */
	/**
	 * a answer set only contains ChoiceStation predicate
	 */
	
	ChoiceStation,
	/* Define all atomic intentions properties */
	/**
	 * a answer set only contains Station predicate
	 */
	Station,
	/**
	 * a answer set only contains StayStation predicate
	 */
	VisitStation,
	/**
	 * a answer set only contains LeaveStation predicate
	 */
	LeaveStation,

	EnterStation,
	
	ProductItem,
	ConsumItem,
	ProductConsumItem,

	/* Define all predicates from scenario model properties */
	AgentType,
	
	StationType,
	
	PlacedEdge,
	
	TimeEdge,
	
	/* Define all predicates from environment features properties */
	/**
	 * a answer set only contains TimeEdgeReady predicate 
	 */
	TimeEdgeReady,
	/**
	 * a answer set only contains TimeEdgeWaiting predicate
	 */
	TimeEdgeWaiting,
	
	/* Define all predicates from agent beliefs properties */
	
	Agent,
	VisitEdge,
	ItemSetLoadingAgent,
	ItemSetLoadingStation,
	NecAgentStation,
	TimeEdgeState	;
	
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
			case ConsumItem:return (T) new PredicateConsumItem(desire);
			case EnterStation:return (T) new PredicateEnterStation(desire);
			case LeaveStation:return (T) new PredicateLeaveStation(desire);
			case ProductConsumItem:return (T) new PredicateProductConsumItem(desire);
			case ProductItem:return (T) new PredicateProductItem(desire);
			case VisitStation:return (T) new PredicateVisitStation(desire);
			case AgentType:return (T) new PredicateAgentType(desire);
			case ChoiceStation:return (T) new PredicateChoiceStation(desire);
			case StationType:return (T) new PredicateStationType(desire);
//			case TimeEdgeReady:
//			case timeEdgeWaiting:
			default: throw new NullPointerException("there are no desire swarm representation of this tweety represenation");
		}
	}
	
	@Override
	public String toString() {
		String str=null;
		switch(this) {
		case TimeEdgeReady: str="TimeEdgeReady";
			break;
		case TimeEdgeWaiting: str="TimeEdgeWaiting";
			break;
		case ChoiceStation: str="ChoiceStation";
			break;
		case Station: str="Station";
			break;			
		case VisitStation: str="VisitStation";
			break;
		case LeaveStation: str="LeaveStation";
			break;
		case Agent:str="Agent";
			break;
		case AgentType:str="AgentType";
			break;
		case ItemSetLoadingAgent:str="ItemSetLoadingAgent";
			break;
		case ItemSetLoadingStation:str="ItemSetLoadingStation";
		break;
		case NecAgentStation: str="NecAgentStation";
			break;
		case PlacedEdge:str="PlacedEdge";
			break;
		case StationType:str="StationType";
			break;
		case TimeEdge:str="TimeEdge";
			break;
		case TimeEdgeState:str="TimeEdgeState";
			break;
		case VisitEdge:str="VisitEdge";
			break;
		case ConsumItem:str="ConsumItem";
			break;
		case EnterStation:str="EnterStation";
			break;
		case ProductConsumItem:str="ProductConsumItem";
			break;
		case ProductItem:str="ProductItem";
			break;
//		default:
//			break;
		
		}
		return str;
	}
}
