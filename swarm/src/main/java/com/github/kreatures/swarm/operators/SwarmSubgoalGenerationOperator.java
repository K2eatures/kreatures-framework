package com.github.kreatures.swarm.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.github.kreatures.core.AbstractSwarms;
import com.github.kreatures.core.Action;
import com.github.kreatures.core.Desire;
import com.github.kreatures.core.EnvironmentComponent;
import com.github.kreatures.core.EnvironmentComponentDefault;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.Subgoal;
import com.github.kreatures.core.SwarmPlanComponent;
import com.github.kreatures.core.operators.BaseSubgoalGenerationOperator;
import com.github.kreatures.core.operators.parameters.PlanParameter;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.MainAction;
import com.github.kreatures.swarm.basic.MainDesire;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;
import com.github.kreatures.swarm.basic.SwarmDesire;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.optimisation.StationNode;
import com.github.kreatures.swarm.predicates.PredicateChoiceStation;
import com.github.kreatures.swarm.predicates.PredicateCurrentStation;
import com.github.kreatures.swarm.predicates.PredicateKnowHow;
import com.github.kreatures.swarm.predicates.PredicateStation;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */

public class SwarmSubgoalGenerationOperator extends
		BaseSubgoalGenerationOperator {
	/** reference to the logback logger instance */
	private Logger LOG = LoggerFactory
			.getLogger(SwarmSubgoalGenerationOperator.class);
	
	private Set<StationNode> allShortestPaths;
	{
		//TODO
	}
	
	/**
	 * reference to environment component of this simulation.  
	 */
//	private EnvironmentComponent envComponent; 
//	{
//		envComponent=AbstractSwarms.getInstance().getEnvComponent(KReatures.getInstance().getActualSimulation().getName());
//	}
	
	/**
	 * the desire which will be chosen at the last time, 
	 * for the currently choice.
	 */
	private SwarmDesire lastDesire=null;
	
//	/**
//	 * Count the number of subgoal which will be created.
//	 */
//	private int numberOfPlan=0;
	
	@Override
	protected Boolean processImpl(PlanParameter params) {
		
		PlanComponent plan=params.getAgent().getComponent(SwarmPlanComponent.class);
		if(plan==null)
			return false;
		
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		
		if(desires==null||desires.isEmptyDesires())
			return false;
		
		LOG.info("New plans will be generated.");
		PredicateStation oldStation=(PredicateStation)lastDesire;
		//PredicateChoiceStation bestDesire=doBestDesire("maxFreeSpace",desires);
		PredicateChoiceStation bestDesire=doBestDesire("minVisited",desires);
		if(bestDesire==null) return false;
		PredicateStation newStation=null;
		for(Desire desire:desires.getNeedDesires()) {
			if((desire instanceof PredicateStation)&&((PredicateStation)desire).getName().equals(bestDesire.getStationName())) {
				newStation=(PredicateStation)desire;
				break;
			}
		}
		/*
		 *There must give always a newStation when there are a choiceStation object.
		 *Therefore the plan isn't created when there are no stations. 
		 */
		if(newStation==null)return false;
		Subgoal subGoal=null;
		int stackIndex=0;
		/* Create a plan to move to the next station. */
		if(oldStation!=null&&!oldStation.getTypeName().equals(bestDesire.getStationTypeName())) {
			subGoal=doMovePlan(oldStation,newStation,params,stackIndex);
			if(subGoal!=null)
				plan.addPlan(subGoal);
		}
		
		subGoal=doVisitPlan(bestDesire, newStation, params,stackIndex);
		
		plan.addPlan(subGoal);
		
		desires.setCurrentDesire(newStation);
		desires.setCurrentMainDesire(MainDesire.CHOSE_STATION);
		desires.setCurrentChoice(bestDesire);
		PredicateCurrentStation actuellStation=desires.getCurrentStation();
		if(actuellStation==null) {
			try {
				actuellStation=new PredicateCurrentStation(bestDesire.getAgentName(), bestDesire.getAgentTypeName(), bestDesire.getStationName(),bestDesire.getStationTypeName(), false,true);
				desires.setCurrentStation(actuellStation);
			} catch (Exception e) {
				LOG.error("The currentStation object cannot be created: Parser not correct.");
				e.printStackTrace();
				return false;
			}
		}else {
			actuellStation.setHasChoose(true);
			actuellStation.setIsInStation(false);
			actuellStation.setStationName(newStation.getName());
			actuellStation.setStationTypeName(newStation.getTypeName());
		}
		
		lastDesire=newStation;
		params.getAgent().getContext().set(SwarmContextConst._PLAN, plan);
		
		return true;
	}
	
	/**
	 * This method chooses one of the best desires as next goal.
	 * @param criterion the criterion that will use to define the best desires. 
	 * @param desires list of all best desires given the criterion.
	 * @return one of the best desires which will chosen or null when there are no desires.
	 */
	private PredicateChoiceStation doBestDesire(String criterion,SwarmDesires desires) {
		if(criterion==null||desires==null)return null;
		
		Optional<PredicateKnowHow> optKnowHow;
//		for(Desire predicate:desires.getNeedDesires()) {
//			if((predicate instanceof PredicateKnowHow)&&criterion.equals(((PredicateKnowHow)predicate).getCrName())) {
//				knowHow= ((PredicateKnowHow)predicate);
//				break;
//			}			
//		}
		List<SwarmPredicate> allKnowHow=desires.getNeedDesires().stream()
				.filter(predicate->(predicate instanceof PredicateKnowHow) && 
						criterion.equals(((PredicateKnowHow)predicate).getCrName()))
				.collect(ArrayList::new, ArrayList::add,ArrayList::addAll);
		allKnowHow.stream().forEach(action->{
				desires.getAgent().report("New Strategie :"+action.toString());
			});
		Random random=new Random();
		int numbreOfKnowHow=allKnowHow.size();
		optKnowHow=Optional.ofNullable((PredicateKnowHow)allKnowHow.get(random.nextInt(numbreOfKnowHow)));
		
		if(!optKnowHow.isPresent()) return null;
		
		return desires.getNeedDesires().stream().filter(predicate->
		(predicate instanceof PredicateChoiceStation))
		.map(mapper->(PredicateChoiceStation)mapper)
		.filter(choiceStation->choiceStation.getStationName().equals(optKnowHow.get().getStationName()))
		.peek(action->{
			desires.getAgent().report("Selected Desire :"+action.toString());
		}).findFirst().get();

	}
	
	/**
	 * This method generates a sub plan for agent. It define step by step how a agent has to
	 * walk in order to move from a srcstation to tagstation. 
	 * @param srcStation the last visited station
	 * @param tagStation the new chosen station
	 * @param params the plan parameter of the agent.
	 * @return the generated sub plan or null when there is no plans to create.
	 */
	private Subgoal doMovePlan(PredicateStation srcStation,PredicateStation tagStation,PlanParameter params,int stackIndex) {
		Action aIntention=null;
		Subgoal subGoal=new Subgoal(params.getAgent(),tagStation);
		subGoal.newStack();
		StationNode stNode=null;
		for(StationNode node:allShortestPaths) {
			if(node.checkObject(srcStation.getTypeName(), tagStation.getTypeName())) {
				stNode=node;
				break;
			}
		}
		if(stNode==null || stNode.getWeight()==0)
			return null;
		
		for(int count=1;count<=stNode.getWeight();count++) {
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.MOVE);
			subGoal.addToStack(aIntention,MainAction.MOVE, stackIndex);
		}
		
		return subGoal;	
	}
	/**
	 * This method generates a sub plan for agent. It define step by 
	 * step how a agent has to do visit a station with a  item or not. First
	 * of all, it has to enter the station. Secondly, it has to do its
	 * job: ie take and / or placed a item or nothings. thirdly it has 
	 * to stay into the station util its time is finished. finally, 
	 * it has to leave the station.
	 * @param bestDesire the new chosen desire.
	 * @param tagStation the new chosen station
	 * @param params the plan parameter of the agent
	 * @return the generated sub plan.
	 */
	
	private Subgoal doVisitPlan(PredicateChoiceStation bestDesire,PredicateStation tagStation,PlanParameter params,int stackIndex) {
		Action aIntention=null;
		Subgoal subGoal=new Subgoal(params.getAgent(),tagStation);
		subGoal.newStack();
		aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.LEAVE_STATION);
		subGoal.addToStack(aIntention,MainAction.LEAVE_STATION, stackIndex);
		
		for(int count=2;count<=bestDesire.getTime();count++) {
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.VISIT_STATION);
			subGoal.addToStack(aIntention,MainAction.VISIT_STATION, stackIndex);
		}
		
		switch(bestDesire.getItemMotiv()) {
		case 0:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.PRODUCT_ITEM);
			subGoal.addToStack(aIntention,MainAction.PRODUCT_ITEM, stackIndex);
			break;
		case 1:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.CONSUM_ITEM);
			subGoal.addToStack(aIntention,MainAction.PRODUCT_ITEM, stackIndex);
			break;
		case 2:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.CONSUM_ITEM);
			subGoal.addToStack(aIntention,MainAction.PRODUCT_ITEM, stackIndex);
			break;
		case 3:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.PRODUCT_ITEM);
			subGoal.addToStack(aIntention,MainAction.PRODUCT_ITEM, stackIndex);
			break;
		case 4:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.PRODUCT_CONSUM_ITEM);
			subGoal.addToStack(aIntention,MainAction.PRODUCT_CONSUM_ITEM, stackIndex);
			break;
		case 5:
			aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.VISIT_STATION);
			subGoal.addToStack(aIntention,MainAction.VISIT_STATION, stackIndex);
			break;
		default:
			LOG.error("The value of the item motiv must be between 0 and 5.");
			break;
		}
		
		aIntention=new SwarmSpeechAct(params.getAgent(),MainAction.ENTER_STATION);
		subGoal.addToStack(aIntention,MainAction.ENTER_STATION, stackIndex);
		
		return subGoal;
	}

	private boolean isShorestPathNoLoad=true;
	@Override
	protected void prepare(PlanParameter params) {
		if(isShorestPathNoLoad) {
			allShortestPaths=((EnvironmentComponentDefault)(AbstractSwarms.getInstance()
				.getEnvComponent(KReatures.getInstance()
						.getActualSimulation().getName()))).getAllShortestPaths();
			isShorestPathNoLoad=false;
		}
	}
}