package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.asp.solver.SolverOptions;
import com.github.kreatures.core.operators.BaseExecuteOperator;
import com.github.kreatures.core.operators.parameters.ExecuteParameter;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.SwarmAction;
import com.github.kreatures.swarm.predicates.SwarmPredicate;
import com.github.kreatures.swarm.basic.MainAction;
/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class SwarmExecuteOperator extends BaseExecuteOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory.getLogger(SwarmExecuteOperator.class);
	
	/**
	 * reference to environment component of this simulation.  
	 */
	private EnvironmentComponent envComponent; 
	{
		envComponent=AbstractSwarms.getInstance().getEnvComponent(KReatures.getInstance().getActualSimulation().getName());
	}
	
	@Override
	protected Boolean processImpl(ExecuteParameter params) {
		PlanElement pe=(PlanElement)params.getAgent().getContext().get(SwarmContextConst._ACTION);
		SwarmAction action=(SwarmAction)pe.getIntention();
		
		switch(action.getActionTyp()) {
			case CONSUM_ITEM:break;
		case LEAVE_STATION:break;
		case MOVE:break;
		case PRODUCT_CONSUM_ITEM:break;
		case VISIT_STATION:break;
		case ENTER_STATION:break;
		case PRODUCT_ITEM:break;
		default: return false;
		
		}
		return false;
	}
	
	private boolean doEnter() {
		String[] Option= {SolverOptions.};
		return false;
	}
	
	private boolean doVisit() {
		return false;
	}
	
	private boolean doLeave() {
		return false;
	}
	private boolean doMove() {
		return false;
	}
	private boolean doProductItem() {
		return false;
	}
	private boolean doConsumItem() {
		return false;
	}
	private boolean doProductConsumItem() {
		return false;
	}
	
	@Override
	protected void prepare(ExecuteParameter params) {
		
	}
}