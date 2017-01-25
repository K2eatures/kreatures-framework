/**
 * 
 */
package com.github.kreatures.core;

import java.util.List;
import java.util.Map;

import net.sf.tweety.logics.fol.syntax.FolFormula;

import com.github.kreatures.core.error.AgentInstantiationException;
import com.github.kreatures.core.listener.AgentListener;
import com.github.kreatures.core.listener.SubgoalListener;
import com.github.kreatures.core.logic.KReaturesAnswer;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.operators.OperatorProvider;
import com.github.kreatures.core.reflection.Context;
import com.github.kreatures.core.serialize.AgentInstance;
import com.github.kreatures.core.serialize.SimulationConfiguration;


/**
 * Interface as Template for the implementation of new agent type.
 * @author Cedric Perez Donfack
 *
 */
public interface IAgent {
	
		OperatorProvider getOperators() ;

		List<String> getCapabilities() ;

		boolean hasCapability(String capabilityName) ;
		/**
		 * add the new perception to the agent perception.
		 * @param percept a new perception of the agent.
		 */
		void perceive(Perception percept);
		
		/**
		 * Sets the belief bases of the agent.
		 * 
		 * @param world
		 *            The world knowledge of this agents
		 * @param views
		 *            A map of agent names to belief bases representing the views
		 *            onto the knowledge of other agents
		 */
		void setBeliefs(BaseBeliefbase world,Map<String, BaseBeliefbase> views) ;

				
		KReaturesEnvironment getEnvironment() ;

		/**
		 * Creates the belief bases, operators and components of the agent.
		 * 
		 * @param ai
		 *            The configuration of the agent instance.
		 * @param config
		 *            The configuration of the simulation.
		 * @throws AgentInstantiationException
		 */
		void create(AgentInstance ai, SimulationConfiguration config)
				throws AgentInstantiationException ;
				
		boolean addListener(AgentListener listener) ;

		boolean removeListener(AgentListener listener) ;

		boolean addSubgoalListener(SubgoalListener listener) ;

		boolean removeSubgoalListener(SubgoalListener listener) ;

		List<SubgoalListener> getSubgoalListeners() ;

		boolean cycle() ;
		
		boolean hasPerceptions() ;

		/**
		 * Informs all the agent listeners of the agent about the update beliefs
		 * process using the given perception and the given old beliefs.
		 * 
		 * @param perception
		 *            The perception responsible for the update beliefs process
		 * @param oldBeliefs
		 *            A reference to the beliefs of the agent before the update
		 *            beliefs process was invoked.
		 */
		void onUpdateBeliefs(Perception perception, Beliefs oldBeliefs) ;

		Beliefs updateBeliefs(KReaturesAtom perception) ;
		/**
		 * Updates the beliefs of the agent. This method searches for the correct
		 * Update operator and calls its process method.
		 * 
		 * @param perception
		 *            The perception causing the update.
		 * @param beliefs
		 *            The Beliefs used as basis for the update process.
		 * @return The updated version of the beliefs.
		 */
		Beliefs updateBeliefs(KReaturesAtom perception, Beliefs beliefs) ;

		/**
		 * Reasons the given query on the world knowledge using the default
		 * reasoning operator.
		 * 
		 * @param query
		 *            Formula representing the question.
		 * @return An KReatures Answer containing the result of the reasoning.
		 */
		KReaturesAnswer reason(FolFormula query) ;

		/** @return the actual belief base of the agent. */
		Beliefs getBeliefs() ;

		PlanComponent getPlanComponent() ;
		
		
		Context getContext() ;

		
		void performAction(Action act) ;
		
		String getName() ;
}
