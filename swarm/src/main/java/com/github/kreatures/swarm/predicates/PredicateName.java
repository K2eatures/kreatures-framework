package com.github.kreatures.swarm.predicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * Contains the name of all the AbstractSwarm representation of desire.
 * It is used to convert a tweety represntation of desire to a AbstractSwarm 
 * representation of desire.
 * 
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
	 * a answer set only contains VisitStation predicate
	 */
	VisitStation,
	/**
	 * a answer set only contains LeaveStation predicate
	 */
	LeaveStation,
	/**
	 * a answer set only contains EnterStation predicate
	 */
	EnterStation,
	/**
	 * a answer set only contains ProductItem predicate
	 */
	ProductItem,
	/**
	 * a answer set only contains ConsumItem predicate
	 */
	ConsumItem,
	/**
	 * a answer set only contains ProductConsumItem predicate
	 */
	ProductConsumItem,

	/* Define all predicates from scenario model properties */
	/**
	 * a answer set only contains AgentComponent predicate
	 */
	AgentType,
	/**
	 * a answer set only contains StationComponent predicate
	 */
	StationType,
	/**
	 * a answer set only contains PlaceComponent predicate
	 */
	PlacedEdge,
	/**
	 * a answer set only contains TimeComponent predicate
	 */
	TimeEdge,
	/**
	 * a answer set only contains information about the shortest path
	 */
	ShortPath,
	
	/* Define all predicates for know-how properties */
	/**
	 * a answer set only contains KnowHow predicate
	 */
	KnowHow,
	
	/* Define all predicates from environment features properties */
	/**
	 * a answer set only contains TimeEdgeReady predicate 
	 */
	TimeEdgeReady,
	/**
	 * a answer set only contains TimeEdgeWaiting predicate
	 */
	TimeEdgeWaiting,
	/**
	 * Predicate gives information about the item (Which action agent 
	 * can do: take and/or place or nothings) and time (how long a 
	 * agent can visit a station) of agent
	 */
	StationInfo,
	
	StationTypItem,
	/* Define all predicates from agent beliefs properties */
	
	Agent,
	VisitEdge,
	ItemSetLoadingAgent,
	ItemSetLoadingStation,
	NecAgentStation,
	CurrentAgent,
	CurrentStation,
	TimeEdgeState,
	TimeEdgeLockState,
	TimeEdgeLockGet;
	
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
			case StationTypItem:return (T) new PredicateStationTypItem(desire);
			case KnowHow:return (T) new PredicateKnowHow(desire);
			case CurrentAgent:return (T) new PredicateCurrentAgent(desire);
			case CurrentStation:return (T) new PredicateCurrentStation(desire);
			case TimeEdgeLockGet: return (T) new PredicateTimeEdgeLockGet(desire);
			case TimeEdgeLockState: return (T) new PredicateTimeEdgeLockState(desire);
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
		case ShortPath:str="ShortPath";
		break;
		case StationInfo:str="StationInfo";
		break;
		case KnowHow: str="KnowHow";
			break;
		case StationTypItem: str="StationTypItem";
			break;
		case CurrentAgent: str="CurrentAgent";
			break;
		case CurrentStation: str="CurrentStation";
			break;
		case TimeEdgeLockGet: str="TimeEdgeLockGet";
			break;
		case TimeEdgeLockState: str="TimeEdgeLockState";
			break;
		default:
			break;
		
//		default:
//			break;
		
		}
		return str;
	}
}
