package com.github.kreatures.swarm;
import com.github.kreatures.core.reflection.Context;
/**
 * Contains variables name which will be used in the 
 * agent context.
 * @author Cedric Perez Donfack
 *@see Context
 */
public final class SwarmContextConst {
	/**
	 * Default Ctor: This class hasn't instance object. 
	 */
	private SwarmContextConst() {}

	/**
	 *  _CHECK_ACTION search a next action which will be execute by the
	 *	execution operator. When action exists, it returns true and 
	 *  false otherwise. It consists to take a plan a search the 
	 *  next possible action.
	 */
	public static final String _CHECK_ACTION="checkAction";
	/**
	 *	_EVALUTED checked whether desires will be evaluated or not. 
	 *	it is setting to false in order to allows unless one evaluation process.
	 */
	public static final String _EVALUTED="evaluated";
	/**
	 * _FILTER contains the building filter which will be used
	 * by the evaluation operator to generate the possible desires.
	 */
	public static final String _FILTER="filter";
	/**
	 * this context is used to treat the SwarmDesires objects.
	 */
	public static final String _DESIRES="desires";
	/**
	 * this context is used to treat the PlanComponent objects.
	 */
	public static final String _PLAN="plan";
	/**
	 * this context is used to treat the PlanElement objects.
	 */
	public static final String _ACTION="action";
}
