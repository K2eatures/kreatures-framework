/**
 * 
 */
package com.github.kreatures.swarm.components;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;
import com.github.kreatures.swarm.serialize.SwarmAgentTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmConfig;
import com.github.kreatures.swarm.serialize.SwarmConfigDefault;
import com.github.kreatures.swarm.serialize.SwarmPlaceEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmStationTypeConfig;
import com.github.kreatures.swarm.serialize.SwarmTimeEdgeConfig;
import com.github.kreatures.swarm.serialize.SwarmVisitEdgeConfig;

/**
 * @author donfack
 *
 */
public class BeliefParseOfSwarm implements XmlToBeliefBase {
	
	
	String  timeUnit;
	String name;
	String description;
	
	Set<SwarmAgentType> agentsTypeSet;
	Set<SwarmAgent> agentSet;
	
	Set<SwarmStationType> stationTypeSet;
	Set<SwarmStation> stationSet;
	
	Set<SwarmPlaceEdgeType> placeEdgeTypeSet;
	Set<SwarmPlaceEdge> placeEdgeSet;
	
	Set<SwarmVisitEdgeType> visitEdgeTypeSet;
	Set<SwarmVisitEdge> visitEdgeSet;
	
	Set<SwarmTimeEdgeType> timeEdgeTypeSet;
	Set<SwarmTimeEdge> timeEdgeSet;
	
	Set<NecAgentStation> necAgentStationSet;
	
	Set<ItemSetLoadingAgent> itemSetLoadingAgentSet;
	Set<ItemSetLoadingStation> itemSetLoadingStationSet;
	
	Set<TimeEdgeState> timeEdgeStateSet;
	
	
	public BeliefParseOfSwarm(Path path) throws SwarmException {
		SwarmConfig scenario=new SwarmConfigDefault().createSwarmConfig(path);
		
		
		
		if (scenario == null)
			throw new SwarmException("Null pointer exception");
		
		if (scenario.getListPerspective().isEmpty())
			throw new SwarmException("There aren't elements into the list");
		
		List<SwarmException> catchException=new ArrayList<>(1);
		
		scenario.getListPerspective().stream().forEach(percept->{
			try {
				setAgentType(percept.getListAgentType());
				setStationType(percept.getListStationType());
			} catch (SwarmException e) {
				catchException.add(e);
				e.printStackTrace();
			}
		});
		
		if(!catchException.isEmpty())
			throw catchException.get(0);
		
		setPlaceEdgeType(scenario.getListPlaceEdge(), getAllStationType());
		setVisitEdgeType(scenario.getListVisitEdge(), getAllAgentType(), getAllStationType());
		setTimeEdgeType(scenario.getListTimeEdge(), getAllAgentType(),getAllStationType());
		
		setAgents(getAllAgentType());
		setStations(getAllStationType());
		setPlaceEdge(getAllPlaceEdgeType());
		setVisitEdge(getAllVisitEdgeType());
		setTimeEdge(getAllTimeEdgeType());
		
		setNecAgentStation(getAllVisitEdge());
		setItemSetLoadingAgent(getAllVisitEdgeType());
		setItemSetLoadingStation(getAllPlaceEdgeType());
		setTimeEdgeState(getAllTimeEdge());
	}
	
	/*
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeUnit
	 * ()
	 */
	@Override
	public int getTimeUnit() {
		
		return SwarmComponents.UNIT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getName()
	 */
	@Override
	public String getName() {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getDescription
	 * ()
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getAgentType
	 * (java.util.List)
	 */
	protected void setAgentType(List<SwarmAgentTypeConfig> list)
			throws SwarmException {
		if (list == null)
			throw new SwarmException("Null pointer exception");
		if (list.isEmpty())
			throw new SwarmException("There aren't elements into the list");
		agentsTypeSet = new HashSet<>();
		for (SwarmAgentTypeConfig obj : list) {
			agentsTypeSet.add(new SwarmAgentType(obj));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getAgens
	 * (java.util.Set)
	 */
	
	protected void setAgents(Set<SwarmAgentType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		agentSet=new HashSet<>();
		
		for(SwarmAgentType obj:set){
			try{
				for(;;){
					agentSet.add(new SwarmAgent(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getStationType
	 * (java.util.List)
	 */
	
	protected void setStationType(
			List<SwarmStationTypeConfig> list)throws SwarmException {
		
		if (list == null)
			throw new SwarmException("Null pointer exception");
		
		if (list.isEmpty())
			throw new SwarmException("There aren't elements into the list");
		
		stationTypeSet = new HashSet<>();
		
		for (SwarmStationTypeConfig obj : list) {
			stationTypeSet.add(new SwarmStationType(obj));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getStations
	 * (java.util.Set)
	 */
	
	protected void setStations(Set<SwarmStationType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		stationSet=new HashSet<>();
		
		for(SwarmStationType obj:set){
			try{
				for(;;){
					stationSet.add(new SwarmStation(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getPlaceEdgeType(java.util.List)
	 */
	
	protected void setPlaceEdgeType(
			List<SwarmPlaceEdgeConfig> list, Set<SwarmStationType> set) throws SwarmException{
		
		if (list == null)
			throw new SwarmException("Null pointer exception");
//		if (list.isEmpty())
//			throw new SwarmException("There aren't elements into the list");
		
		placeEdgeTypeSet = new HashSet<>();
		List<SwarmStationType> args=new ArrayList<>(2);
		for (SwarmPlaceEdgeConfig obj : list) {
			
			set.stream().filter(p->p.getIdentity()==obj.getFirstConnectedIdRefSwarmPlaceEdge()).map(q->args.add(q));
			set.stream().filter(p->p.getIdentity()==obj.getSecondConnectedIdRefSwarmPlaceEdge()).map(q->args.add(q));
			
			placeEdgeTypeSet.add(new SwarmPlaceEdgeType(obj,args.get(0),args.get(1)));
			args.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getPlaceEdge
	 * (java.util.Set)
	 */
	
	protected void setPlaceEdge(Set<SwarmPlaceEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		placeEdgeSet=new HashSet<>();
		
		for(SwarmPlaceEdgeType obj:set){
			try{
				for(;;){
					placeEdgeSet.add(new SwarmPlaceEdge(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getVisitEdgeType(java.util.List)
	 */
	
	protected void setVisitEdgeType(
			List<SwarmVisitEdgeConfig> list,Set<SwarmAgentType> agSet,Set<SwarmStationType> stSet)throws SwarmException {
		
		if (list == null)
			throw new SwarmException("Null pointer exception");
		if (list.isEmpty())
			throw new SwarmException("There aren't elements into the list");
		
		placeEdgeTypeSet = new HashSet<>();
		List<SwarmComponents> args=new ArrayList<>(2);
		for (SwarmVisitEdgeConfig obj : list) {
			args.clear();
			agSet.stream().filter(p->p.getIdentity()==obj.getFirstConnectedIdRefSwarmVisitEdge()).map(q->args.add(q));
			stSet.stream().filter(p->p.getIdentity()==obj.getSecondConnectedIdRefSwarmVisitEdge()).map(q->args.add(q));
			
			visitEdgeTypeSet.add(new SwarmVisitEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmStationType)args.get(1)));
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getVisitEdge
	 * (java.util.Set)
	 */
	
	protected void setVisitEdge(Set<SwarmVisitEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		visitEdgeSet=new HashSet<>();
		
		for(SwarmVisitEdgeType obj:set){
			try{
				for(;;){
					visitEdgeSet.add(new SwarmVisitEdge(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getTimeEdgeType(java.util.List)
	 */
	
	protected void setTimeEdgeType(List<SwarmTimeEdgeConfig> list,Set<SwarmAgentType> agSet,Set<SwarmStationType> stSet ) throws SwarmException{
		if (list == null)
			throw new SwarmException("Null pointer exception");
//		if (list.isEmpty())
//			throw new SwarmException("There aren't elements into the list");
		
		timeEdgeTypeSet = new HashSet<>();
		List<SwarmComponents> args=new ArrayList<>(2);
		for (SwarmTimeEdgeConfig obj : list) {
			args.clear();
			agSet.stream().filter(p->p.getIdentity()==obj.getFirstConnectedIdRefSwarmTimeEdge()).map(q->args.add(q));
			stSet.stream().filter(p->p.getIdentity()==obj.getSecondConnectedIdRefSwarmTimeEdge()).map(q->args.add(q));
			
			if(args.get(0) instanceof SwarmAgentType && args.get(1) instanceof SwarmStationType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmStationType)args.get(1)));	
			}else if(args.get(0) instanceof SwarmAgentType && args.get(1) instanceof SwarmAgentType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmAgentType)args.get(1)));	
			}else if(args.get(0) instanceof SwarmStationType && args.get(1) instanceof SwarmStationType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmStationType)args.get(0),(SwarmStationType)args.get(1)));	
			}else{
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmStationType)args.get(0),(SwarmAgentType)args.get(1)));	
			}
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeEdge
	 * (java.util.Set)
	 */
	
	protected void setTimeEdge(Set<SwarmTimeEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		timeEdgeSet=new HashSet<>();
		
		for(SwarmTimeEdgeType obj:set){
			try{
				for(;;){
					timeEdgeSet.add(new SwarmTimeEdge(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getNecAgentStation(java.util.Set)
	 */
	
	protected void setNecAgentStation(Set<SwarmVisitEdge> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		necAgentStationSet=new HashSet<>();
		
		for(SwarmVisitEdge obj:set){
			necAgentStationSet.add(new NecAgentStation(obj));
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getItemSetLoadingAgent(java.util.Set)
	 */
	
	protected void setItemSetLoadingAgent(
			Set<SwarmVisitEdgeType> set)throws SwarmException {
		
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		itemSetLoadingAgentSet=new HashSet<>();
		
		for(SwarmVisitEdgeType obj:set){
			try{
				for(;;){
					itemSetLoadingAgentSet.add(new ItemSetLoadingAgent(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getItemSetLoadingStation(java.util.Set)
	 */
	
	protected void setItemSetLoadingStation(
			Set<SwarmPlaceEdgeType> set)throws SwarmException {
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		List<SwarmException> catchException=new ArrayList<>(1);
		
		itemSetLoadingStationSet=new HashSet<>();
		
		
		set.stream().filter(p->p.isDirected()).forEach(obj->{
			try{
				for(;;){
					itemSetLoadingStationSet.add(new ItemSetLoadingStation(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS){
					catchException.add(ex);
					return;
				}
					
			}
			});
		
		if(!catchException.isEmpty())
			throw catchException.get(0);
		
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#
	 * getTimeEdgeState(java.util.Set)
	 */
	
	protected void setTimeEdgeState(Set<SwarmTimeEdge> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		timeEdgeStateSet=new HashSet<>();
		
		for(SwarmTimeEdge obj:set){
			try{
				for(;;){
					timeEdgeStateSet.add(new TimeEdgeState(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS)
					throw ex;
					
			}
		}
		
		
	}



	@Override
	public Set<SwarmAgentType> getAllAgentType() {
		
		return agentsTypeSet;
	}



	@Override
	public Set<SwarmAgent> getAllAgents() {
		
		return agentSet;
	}



	@Override
	public Set<SwarmStationType> getAllStationType() {
		
		return stationTypeSet;
	}



	@Override
	public Set<SwarmStation> getAllStations() {
		
		return stationSet;
	}



	@Override
	public Set<SwarmPlaceEdgeType> getAllPlaceEdgeType() {
		
		return placeEdgeTypeSet;
	}



	@Override
	public Set<SwarmPlaceEdge> getAllPlaceEdge() {
		
		return placeEdgeSet;
	}



	@Override
	public Set<SwarmVisitEdgeType> getAllVisitEdgeType() {
		
		return visitEdgeTypeSet;
	}



	@Override
	public Set<SwarmVisitEdge> getAllVisitEdge() {
		
		return visitEdgeSet;
	}



	@Override
	public Set<SwarmTimeEdgeType> getAllTimeEdgeType() {

		return timeEdgeTypeSet;
	}



	@Override
	public Set<SwarmTimeEdge> getAllTimeEdge() {

		return timeEdgeSet;
	}



	@Override
	public Set<NecAgentStation> getAllNecAgentStation() {
		
		return necAgentStationSet;
	}



	@Override
	public Set<ItemSetLoadingAgent> getAllItemSetLoadingAgent() {

		return itemSetLoadingAgentSet;
	}



	@Override
	public Set<ItemSetLoadingStation> getAllItemSetLoadingStation() {
		
		return itemSetLoadingStationSet;
	}



	@Override
	public Set<TimeEdgeState> getAllTimeEdgeState() {
		
		return timeEdgeStateSet;
	}

}
