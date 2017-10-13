package com.github.kreatures.swarm.operators;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.EnvironmentComponent;
//import com.github.kreatures.core.PlanComponent;
import com.github.kreatures.core.PlanElement;
import com.github.kreatures.core.SwarmPlanComponent;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.operators.BaseExecuteOperator;
import com.github.kreatures.core.operators.parameters.ExecuteParameter;
import com.github.kreatures.swarm.SwarmConst;
import com.github.kreatures.swarm.SwarmContextConst;
import com.github.kreatures.swarm.basic.SwarmSpeechAct;
import com.github.kreatures.swarm.basic.SwarmDesires;
import com.github.kreatures.swarm.predicates.PredicateAgent;
import com.github.kreatures.swarm.predicates.PredicateAgentType;
import com.github.kreatures.swarm.predicates.PredicateCurrentStation;
import com.github.kreatures.swarm.predicates.PredicateEnterStation;
import com.github.kreatures.swarm.predicates.PredicateItemSetLoadingAgent;
import com.github.kreatures.swarm.predicates.PredicateItemSetLoadingStation;
import com.github.kreatures.swarm.predicates.PredicateName;
import com.github.kreatures.swarm.predicates.PredicateNecAgentStation;
import com.github.kreatures.swarm.predicates.PredicateProductConsumItem;
import com.github.kreatures.swarm.predicates.PredicateStation;
import com.github.kreatures.swarm.predicates.PredicateStationTypItem;
import com.github.kreatures.swarm.predicates.PredicateStationType;
import com.github.kreatures.swarm.predicates.PredicateTimeEdgeState;
import com.github.kreatures.swarm.predicates.SwarmPredicate;
import com.github.kreatures.swarm.basic.MainDesire;
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


	//	/**
	//	 * This attribute helps a agent to know which atoms it has to 
	//	 * update when it leaves a station. 
	//	 * visitTyp 
	//	 * =0, if the agent has taken a item.
	//	 * =1, if the agent has placed a item.
	//	 * =2, if the agent has placed a item and a other agent can take it.
	//	 * =3, if the agent has taken and placed a item.
	//	 * =4, if the agent has only visit a station. This is the default value.  
	//	 */
	//	private int visitTyp=4;
	@Override
	protected Boolean processImpl(ExecuteParameter params) {
		boolean check=false;
		PlanElement pe=(PlanElement)params.getAgent().getContext().get(SwarmContextConst._ACTION);
		if(pe==null)return check;
		envComponent=params.getEnvComponent();
		SwarmSpeechAct action=(SwarmSpeechAct)pe.getIntention();

		switch(action.getActionTyp()) {
		case MOVE:
			check=doMove(action,params);
			break;
		case ENTER_STATION:
			check=doEnter(action,params);
			break;

		case CONSUM_ITEM:
			doProductConsumItem(action,params);
			break;


		case PRODUCT_CONSUM_ITEM:
			/* Product and Consume must execute at the same time. Hereby we have to use ItemSetLoading 
			 * of all the agent instead of the ItemSetLoadingStation.  */
			doProductConsumItem(action,params);
			break;

		case PRODUCT_ITEM:
			doProductConsumItem(action,params);
			break;

		case VISIT_STATION:
			check=doVisit(action,params);
			break;
		case LEAVE_STATION:
			check=doLeave(action,params);
			break;
		default: return false;

		}
		return check;
	}

	private boolean doEnter(SwarmSpeechAct action,ExecuteParameter params) {
		String[] Options= {PredicateName.EnterStation.toString(),PredicateName.Agent.toString(),PredicateName.AgentType.toString(),
				PredicateName.Station.toString(),PredicateName.NecAgentStation.toString(),PredicateName.ChoiceStation.toString(),PredicateName.TimeEdgeState.toString()};
		// get a object of FolBeliefbase
		FolBeliefbase folBB=(FolBeliefbase)params.getBaseBeliefbase();
		/* List of desires and related informations */
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		// keep a object of FolBeliefbase program
		Set<SwarmPredicate> result=envComponent.askEnvironment(folBB, Options);
		
		
		Set<SwarmPredicate> enterStationSet=result.stream().filter(predicate->(predicate instanceof PredicateEnterStation)&& 
				((PredicateEnterStation)predicate).getStationName().equals(desires.getCurrentStation().getStationName()))
			.collect(HashSet::new,HashSet::add,HashSet::addAll);
		Optional<PredicateEnterStation> optEnterStation=enterStationSet.stream()
				.map(predicate->(PredicateEnterStation)predicate)
				.filter(predicate->predicate.getMotiv()<4).findFirst();
		
		/* add currentStation to the action */
		PredicateCurrentStation currentStation=desires.getCurrentStation();
		if(desires.getCurrentMainDesire()==MainDesire.CHOSE_STATION) {
			desires.setCurrentMainDesire(MainDesire.WAIT);
			action.getActions().add(currentStation);			
		}
		/* check whether agent can enter the station. */
		if(enterStationSet.isEmpty()) {
			if(desires.getWaitTime()==0) {
				desires.clear();

				params.getAgent().getComponent(SwarmPlanComponent.class).clear();
				desires.initWaitTime();
				return false;
			}
			desires.decrWaitTime();
			return false;
		}


		/* update the Timeedgestate predicate which will be performed.  */
		
		Optional<PredicateTimeEdgeState> optTimeEdgeState=Optional.empty();
		PredicateEnterStation enterStation=optEnterStation.orElseGet(()->enterStationSet.stream()
				.map(predicate->(PredicateEnterStation)predicate)
				.findFirst().get());
		
		if(enterStation.getMotiv()!=0){
			optTimeEdgeState=result.stream().filter(predicate->(predicate instanceof PredicateTimeEdgeState))
					.map(predicate->(PredicateTimeEdgeState)predicate)
					.filter(predicate->predicate.getName().equals(enterStation.getStationName()))
					.findFirst();
			optTimeEdgeState.get().incrTick();
			if(optEnterStation.isPresent()){
				optTimeEdgeState.get().setReady(true);
				
			}else{
				
				if(desires.getWaitTime()==SwarmConst.WAIT_TIME.getValue()) {
					optTimeEdgeState.get().setVisitorName(enterStation.getAgentName());
					optTimeEdgeState.get().setVisitorTypeName(enterStation.getAgentTypeName());
				}else if(desires.getWaitTime()==0){
					optTimeEdgeState.get().init();
					desires.clear();

					params.getAgent().getComponent(SwarmPlanComponent.class).clear();
					desires.initWaitTime();
				}
				
				desires.decrWaitTime();
						
				optTimeEdgeState.get().setWaiting(true);
				action.getActions().add(optTimeEdgeState.get());
				desires.setTimeEdgeState(optTimeEdgeState.get());
				return false;
			}
			
			action.getActions().add(optTimeEdgeState.get());
			desires.setTimeEdgeState(optTimeEdgeState.get());
		}
		
		
		
		
		desires.initWaitTime();
		/* set that agent has enter the station */
		currentStation.setIsInStation(true);
		desires.setCurrentMainDesire(MainDesire.VISIT);
		if(desires.getCurrentMainDesire()!=MainDesire.CHOSE_STATION) {
			action.getActions().add(currentStation);			
		}
		/* search agent, agentType and necAgentStation */
		PredicateAgent agent=null;
		PredicateAgentType agentType=null;
		PredicateNecAgentStation necAgentStation=null;
		if(desires.getCurrentAgent()==null) {
			int breakForEach=0;
			for(SwarmPredicate predicate:result) {
				if(breakForEach==3)
					break;
				if(predicate instanceof PredicateAgent && ((PredicateAgent)predicate).getName().equals(params.getAgent().getName())) {
					agent=(PredicateAgent)predicate;
					desires.setCurrentAgent(agent);
					breakForEach++;
					continue;
				}
				if(predicate instanceof PredicateAgentType && ((PredicateAgentType)predicate).getTypeName().equals(currentStation.getAgentTypeName())) {
					agentType=(PredicateAgentType)predicate;
					desires.setCurrentAgentType(agentType);
					breakForEach++;
					continue;
				}
				if(predicate instanceof PredicateNecAgentStation && ((PredicateNecAgentStation)predicate).getAgentName().equals(currentStation.getAgentName())&&((PredicateNecAgentStation)predicate).getStationName().equals(currentStation.getStationName())) {
					necAgentStation=(PredicateNecAgentStation)predicate;
					breakForEach++;
					continue;
				}
			}
		}else {
			agent=desires.getCurrentAgent();
			agentType=desires.getCurrentAgentType();
			for(SwarmPredicate predicate:result) {
				if(predicate instanceof PredicateNecAgentStation && ((PredicateNecAgentStation)predicate).getAgentName().equals(currentStation.getAgentName())&&((PredicateNecAgentStation)predicate).getStationName().equals(currentStation.getStationName())) {
					necAgentStation=(PredicateNecAgentStation)predicate;
					continue;
				}
			}
		}
		/* add necAgentStation to the action */
		necAgentStation.incrementNec();
		action.getActions().add(necAgentStation);
		/* add agent to the action */
		agent.incrFrequency();
		action.getActions().add(agent);
		/* given the next actions which will be performed. */
		PredicateStation station=(PredicateStation)desires.getCurrentDesire();
		station.incrFrequency();
		station.incrSpace(agentType.getSize());
		
		
		action.getActions().add(station);
		return true;
	}

	
	
	
	
	private boolean doMove(SwarmSpeechAct action,ExecuteParameter params) {

		/* List of desires and related informations */
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);

		/* add currentStation to the action */
		if(desires.getCurrentMainDesire()==MainDesire.CHOSE_STATION) {
			desires.setCurrentMainDesire(MainDesire.MOVE);
			PredicateCurrentStation currentStation=desires.getCurrentStation();
			action.getActions().add(currentStation);			
		}
		return true;
	}

	private boolean doVisit(SwarmSpeechAct action,ExecuteParameter params) {
		/* List of desires and related informations */
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		desires.getTimeEdgeState().map(timeState->{
			timeState.incrTick();
			return timeState;
			}).ifPresent(action.getActions()::add);
		return true;
	}

	private boolean doLeave(SwarmSpeechAct action,ExecuteParameter params) {

		/* List of desires and related informations */
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		/* add currentStation to the action */
		PredicateCurrentStation currentStation=desires.getCurrentStation();
		desires.setCurrentMainDesire(MainDesire.CHOSE_STATION);
		currentStation.setHasChoose(false);
		currentStation.setIsInStation(false);
		action.getActions().add(currentStation);			
		/* search agent and agentType */
		PredicateAgentType agentType=desires.getCurrentAgentType();
		/* add station to the action */
		PredicateStation station=(PredicateStation)desires.getCurrentDesire();
		station.decrSpace(agentType.getSize());
		action.getActions().add(station);
		
		desires.getTimeEdgeState().map(PredicateTimeEdgeState::init).ifPresent(action.getActions()::add);
		
		desires.clear();
		return true;

	}

//	private boolean doProductItem() {
//		return false;
//	}
//	private boolean doConsumItem() {
//		return false;
//	}


	private boolean doProductConsumItem(SwarmSpeechAct action,ExecuteParameter params) {
		String[] Options= {PredicateName.ProductConsumItem.toString(),PredicateName.StationType.toString(),PredicateName.StationTypItem.toString(),PredicateName.ItemSetLoadingAgent.toString(),PredicateName.ItemSetLoadingStation.toString()};
		// get a object of FolBeliefbase
		FolBeliefbase folBB=(FolBeliefbase)params.getBaseBeliefbase();
		/* List of desires and related informations */
		SwarmDesires desires=params.getAgent().getComponent(SwarmDesires.class);
		// keep a object of FolBeliefbase program
		Set<SwarmPredicate> result=envComponent.askEnvironment(folBB, Options);
		PredicateProductConsumItem productConsumItem=null;
		for(SwarmPredicate predicate:result) {
			if(predicate instanceof PredicateProductConsumItem) {
				productConsumItem=(PredicateProductConsumItem)predicate;
				break;
			}
		}
		/* check whether agent can product or consume a item. */
		if(productConsumItem==null) 
			return doVisit(action, params);

		/* search agent, agentType and necAgentStation */
		PredicateAgent agent=desires.getCurrentAgent();
		PredicateAgentType agentType=desires.getCurrentAgentType();
		Set<PredicateItemSetLoadingAgent> itemSetLoadingAgents=new TreeSet<>();
		Set<PredicateItemSetLoadingStation> itemSetLoadingStations=new TreeSet<>();
		PredicateStationType stationType=null;
		Set<PredicateStationTypItem> stationTypItems=new TreeSet<>();

		for(SwarmPredicate predicate:result) {
			if(predicate instanceof PredicateItemSetLoadingAgent && ((PredicateItemSetLoadingAgent)predicate).getAgentName().equals(productConsumItem.getAgentName())) {
				itemSetLoadingAgents.add((PredicateItemSetLoadingAgent)predicate);
				continue;
			}
			if(predicate instanceof PredicateItemSetLoadingStation && ((PredicateItemSetLoadingStation)predicate).getStationInName().equals(productConsumItem.getStationName())) {
				itemSetLoadingStations.add((PredicateItemSetLoadingStation)predicate);
				continue;
			}
			if(predicate instanceof PredicateStationType && ((PredicateStationType)predicate).getTypeName().equals(productConsumItem.getStationTypeName())) {
				stationType=(PredicateStationType)predicate;
				continue;
			}
			if(predicate instanceof PredicateStationTypItem) {
				stationTypItems.add((PredicateStationTypItem)predicate);
				continue;
			}
		}

		switch(productConsumItem.getMotiv()) {
		case 0://=0, : Agent can only take item, because there are no ingoing stations.
			/* update and add itemSetLoadingAgent to the action */
			for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
				if(predicate.getStationTypeName().equals(productConsumItem.getStationTypeName())) {
					predicate.incrItemNumber(1);
					agent.incrCapacity(stationType.getItem());
					action.getActions().add(predicate);
					break;
				}
			}
			break;	
		case 1://=1, : Agent can only place item, because there are no outgoing stations.
			int sumItem1=0;
			/* update and add itemSetLoadingAgent to the action */
			for(PredicateStationTypItem predicateStTypItem:stationTypItems) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateStTypItem.getStTypNameOut())) {
						predicate.decrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);
						sumItem1+=productConsumItem.getItemNumber()*predicateStTypItem.getItemDim();
						break;
					}
				}				
			}
			
			/* update and add itemSetLoadingStation to the action */
			for(PredicateItemSetLoadingStation predicateItemStation:itemSetLoadingStations) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateItemStation.getStationOutTypeName())) {
						predicate.incrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);						
						break;
					}
				}				
			}
			agent.decrCapacity(sumItem1);
			break;
		case 2://=2, : Agent can only place item, because there are outgoing stations, but agent cannot visit it.
			int sumItem2=0;
			/* update and add itemSetLoadingAgent to the action */
			for(PredicateStationTypItem predicateStTypItem:stationTypItems) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateStTypItem.getStTypNameOut())) {
						predicate.decrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);
						sumItem2+=productConsumItem.getItemNumber()*predicateStTypItem.getItemDim();
						break;
					}
				}				
			}
			
			/* update and add itemSetLoadingStation to the action */
			for(PredicateItemSetLoadingStation predicateItemStation:itemSetLoadingStations) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateItemStation.getStationOutTypeName())) {
						predicate.incrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);						
						break;
					}
				}				
			}
			agent.decrCapacity(sumItem2);
			break;
		case 3://=3, : Agent can only take item, because there are ingoing stations, but agent cannot visit it.
			/* update and add itemSetLoadingStation to the action */
			for(PredicateItemSetLoadingStation predicate:itemSetLoadingStations) {
				predicate.decrItemNumber(productConsumItem.getItemNumber());
				action.getActions().add(predicate);
			}
			agent.incrCapacity(productConsumItem.getItemNumber()*stationType.getItem());
			break;
		case 4://=4, : Agent can take and place item, because there are ingoing and outgoing stations, and the agent can visit it.
			int sumItem4=0;
			/* update and add itemSetLoadingAgent to the action */
			for(PredicateStationTypItem predicateStTypItem:stationTypItems) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateStTypItem.getStTypNameOut())) {
						predicate.decrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);
						sumItem4+=productConsumItem.getItemNumber()*predicateStTypItem.getItemDim();
						break;
					}
				}				
			}
			agent.decrCapacity(sumItem4);
			
			/* update and add itemSetLoadingStation to the action */
			for(PredicateItemSetLoadingStation predicate:itemSetLoadingStations) {
				predicate.decrItemNumber(productConsumItem.getItemNumber());
				action.getActions().add(predicate);
			}
			agent.incrCapacity(productConsumItem.getItemNumber()*stationType.getItem());
			
		case 5:/*=5, : Agent can place and cannot take item, because there are ingoing and outgoing stations, and the condition to
			 take is not fullfilly.*/
			int sumItem5=0;
			/* update and add itemSetLoadingAgent to the action */
			for(PredicateStationTypItem predicateStTypItem:stationTypItems) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateStTypItem.getStTypNameOut())) {
						predicate.decrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);
						sumItem5+=productConsumItem.getItemNumber()*predicateStTypItem.getItemDim();
						break;
					}
				}				
			}
			
			/* update and add itemSetLoadingStation to the action */
			for(PredicateItemSetLoadingStation predicateItemStation:itemSetLoadingStations) {
				for(PredicateItemSetLoadingAgent predicate:itemSetLoadingAgents) {
					if(predicate.getItemNumber()>0&& predicate.getStationTypeName().equals(predicateItemStation.getStationOutTypeName())) {
						predicate.incrItemNumber(productConsumItem.getItemNumber());
						action.getActions().add(predicate);						
						break;
					}
				}				
			}
			agent.decrCapacity(sumItem5);
			break;
		}



		/* add station to the action */
		PredicateStation station=(PredicateStation)desires.getCurrentDesire();
		//station.incrFrequency();
		//station.incrSpace(agentType.getSize());
		action.getActions().add(station);
		desires.getTimeEdgeState().map(timeState->{
			timeState.incrTick();
			return timeState;
			}).ifPresent(action.getActions()::add);
		
		return true;
	}

	@Override
	protected void prepare(ExecuteParameter params) {
		
	}
}