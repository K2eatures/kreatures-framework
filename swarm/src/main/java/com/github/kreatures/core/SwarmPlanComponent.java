package com.github.kreatures.core;

import com.github.kreatures.core.Intention;
import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.swarm.basic.SwarmDesires;
/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public class SwarmPlanComponent extends PlanComponent {

	/**Default Ctor. for dynamic creation.*/
	public SwarmPlanComponent() {}

	public SwarmPlanComponent(PlanComponent plan) {
		super(plan);
	}
	
	@Override
	public void onSubgoalFinished(Intention subgoal) {
		if(subgoal.getParent() == null) {
			Subgoal sg = (Subgoal) subgoal;
			removePlan(sg);
			if(sg.getFulfillsDesire() != null) {
				getAgent().getComponent(SwarmDesires.class).remove(sg.getFulfillsDesire());
			}
		}
	}
	
	@Override
	public String toString() {
		return "Plan Data-Structure";
	}

}
