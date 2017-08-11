package com.github.kreatures.swarm.basic;

import com.github.kreatures.core.BaseAgentComponent;
/**
 * This class gives information about the current and the next action of 
 * a agent.
 * @author Cedric Perez Donfack
 *
 */
public class ActionState extends BaseAgentComponent {
	/**
	 * The next action which a agent want to execute.
	 */
	private MainAction nextAction;
	/**
	 * the actual action which a agent has to execute.
	 */
	private MainAction currentAction;
	
	/**Default ctor: dynamic instantiation */
	public ActionState() {
		nextAction=null;
		currentAction=null;
	}
	/** make a copy of this instance object*/
	public ActionState(ActionState other) {
		super(other);
		nextAction=other.nextAction;
		currentAction=other.currentAction;
	}

	@Override
	public ActionState clone() {
		return new ActionState(this) ;
	}

	/**
	 * @return the current action of the agent
	 */
	public MainAction getCurrentAction() {
		return currentAction;
	}

	/**
	 * @param currentAction the new current action to set
	 */
	public void setCurrentAction(MainAction currentAction) {
		this.currentAction = currentAction;
	}

	/**
	 * @return the next action of the agent
	 */
	public MainAction getNextAction() {
		return nextAction;
	}

	/**
	 * change the actual current action to the actual next action
	 * and change the actual next action to the new one given as parameter. 
	 * @param nextAction the new next action to set
	 */
	public void setNextAction(MainAction nextAction) {
		if(this.nextAction!=null && this.currentAction!=this.nextAction)
			this.currentAction=this.nextAction;
		this.nextAction = nextAction;
	}

}