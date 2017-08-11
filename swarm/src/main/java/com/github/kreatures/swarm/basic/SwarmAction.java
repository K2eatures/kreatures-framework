package com.github.kreatures.swarm.basic;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.Intention;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

/**
 * In contract to Angerona, a action by AbstractSwarm cannot only be a single 
 * Atom, because for example the enter into a station need more than one change
 * in the environment. Therefore, either one take each changes as a atomic intention
 * or one considers all of them as a atomic intention. In order to simplify this work
 * we have retain the second approach. 
 * So atomic intention is a set of specific action. 
 * @author donfack
 *
 */
/*must extends PlanElement*/
public class SwarmAction extends Action {
	
	protected MainAction actionTyp;
	
	/**
	 * Set of actions as atomic intention. 
	 */
	private Set<SwarmPredicate> actions=new HashSet<>();
	
	/** Ctor used for deserialization */
	public SwarmAction(AgentAbstract sender) {
		this(sender,null,null);
	}
	
	/**
	 * @param sender sender of the actions. 
	 * @param actions all the actions whic have to perform at the same9
	 * 				time. 
	 */
	public SwarmAction(AgentAbstract sender,Set<SwarmPredicate> actions) {
		this(sender,actions,null);
	}
	
	/**
	 * @param sender sender of the actions. 
	 * @param actions all the actions whic have to perform at the same9
	 * 				time.
	 * @param sender
	 * @param actionTyp the typ of the atomic intention. 
	 * 
	 * @see MainAction for all atomic intentions.
	 *  
	 */
	public SwarmAction(AgentAbstract sender,MainAction actionTyp) {
		this(sender,null,actionTyp);
	}
	
	/** Ctor used for deserialization */
	public SwarmAction(AgentAbstract sender,Set<SwarmPredicate> actions, MainAction actionTyp) {
		super(sender.getName());
		super.agent=sender;
		this.actions.addAll(actions);
		this.actionTyp=actionTyp;
	}
	
	/**
	 * @return the set of actions
	 */
	public Set<SwarmPredicate> getActions() {
		return actions;
	}

	/**
	 * 
	 * @param predicateName a predicate name
	 * @return a stream of predicate corresponding to this atomic intention 
	 * 			and whose predicate name is predicateName.
	 */
	public Stream<SwarmPredicate> getActionByPredicatName(String predicateName) {
		
		return actions.stream().filter(predicate->{
			 if(predicate.getTypeOfDesire().equals(predicateName)) {
				 return true;
			 }
			 return false;
		 });
	}
	
	/**
	 * @param actions the actions to set
	 */
	public void setActions(Set<SwarmPredicate> actions) {
		this.actions = actions;
	}

	/**
	 * @return the actionTyp
	 */
	public MainAction getActionTyp() {
		return actionTyp;
	}

	/**
	 * @param actionTyp the actionTyp to set
	 */
	public void setActionTyp(MainAction actionTyp) {
		this.actionTyp = actionTyp;
	}

	@Override
	public void onSubgoalFinished(Intention subgoal) {
		if(super.parent != null) {
			super.parent.onSubgoalFinished(this);
		}
	}
}


