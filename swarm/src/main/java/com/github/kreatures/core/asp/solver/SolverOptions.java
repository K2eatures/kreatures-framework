package com.github.kreatures.core.asp.solver;

/**
 * SolverOptions is used to define the filter parameter
 * which the solver needs to generate a answer set. 
 * @author Cedric Perez Donfack
 *
 */
public enum SolverOptions {
	/**
	 * a answer set contains no facts 
	 */
	NOFACTS,
	/**
	 * a answer set only contains TimeEdgeReady predicate 
	 */
	TIME_EDGE_READY,
	/**
	 * a answer set only contains TimeEdgeWaiting predicate
	 */
	TIME_EDGE_WAITING,
	/**
	 * a answer set can contain a filter
	 */
	FILTER,
	/**
	 * a answer set only contains ChoiceStation predicate
	 */
	CHOICE_STATION,
	/**
	 * a answer set only contains Station predicate
	 */
	STATION,
	/**
	 * a answer set only contains StayStation predicate
	 */
	STAY_STATION,
	/**
	 * a answer set only contains LeaveStation predicate
	 */
	LEAVE_STATION; 
	
	@Override
	public String toString() {
		String str=null;
		switch(this) {
		case NOFACTS: str="-nofacts";
			break;
		case FILTER: str="-filter=";
			break;		
		case TIME_EDGE_READY: str="TimeEdgeReady";
			break;
		case TIME_EDGE_WAITING: str="TimeEdgeWaiting";
			break;
		case CHOICE_STATION: str="ChoiceStation";
			break;
		case STATION: str="Station";
			break;			
		case STAY_STATION: str="StayStation";
			break;
		case LEAVE_STATION: str="LeaveStation";
			break;
		}
		return str;
	}
}
