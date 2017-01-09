package com.github.kreatures.swarm.serialize.bdiparser;



import com.github.kreatures.core.util.Pair;
import com.github.kreatures.swarm.components.SwarmConfig;

/**
 * The BDIParser provide methods for generating the agents knowledge given a AbstractSwarm scenario.
 *  
 * @author donfack
 *
 */

public interface BDIParser {
	/**
	 * 
	 * @param swarmAgentType This is a class which parse an agent-type component in the abstractswarm scenario. 
	 * @return The Integer represents the value of count's attribute. That means the number of agents belong to this agent types.
	 * and the String is predicate whose arguments' values identifies the corresponding agent-type. 
	 */
	Pair<Integer,String> getAgentTypePredicat(SwarmConfig swarmAgentType);
	/**
	 * 
	 * @param SswarmStationType This is a class which parse a station-type component in the abstractswarm scenario. 
	 * @return The Integer represents the value of count's attribute. That means the number of stations belong to this station types.
	 * and the String is predicate whose arguments' values identifies the corresponding station-type. 
	 */
	Pair<Integer,String> getStationTypePredicat(SwarmConfig SswarmStationType);
	/**
	 * 
	 * @param swarmPlaceEdge This is a class which parse a place-edge component in the abstractswarm scenario. 
	 * @return a predicate whose arguments' values identifies the corresponding place-edge. 
	 */
	String getPlaceEdgeTypePredicat(SwarmConfig swarmPlaceEdge);
	/**
	 * 
	 * @param swarmVisitEdge This is a class which parse a visit type component in the abstractswarm scenario. 
	 * @return a predicate whose arguments' values identifies the corresponding visit-edge. 
	 */
	String getVisitEdgeTypePredicat(SwarmConfig swarmVisitEdge);
	/**
	 * 
	 * @param swarmTimeEdge This is a class which parse the time type component in the abstractswarm scenario. 
	 * @return a predicate whose arguments' values identifies the corresponding time-edge. 
	 */
	String getTimeEdgeTypePredicat(SwarmConfig swarmTimeEdge);
	
}
