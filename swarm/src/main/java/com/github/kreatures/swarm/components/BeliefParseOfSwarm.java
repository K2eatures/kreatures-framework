/**
 * 
 */
package com.github.kreatures.swarm.components;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
	
	
	private String  timeUnit;
	private String name;
	private String description;
	
	private Collection<SwarmAgentType> agentsTypeSet;
	private Collection<SwarmAgent> agentSet;
	
	private Collection<SwarmStationType> stationTypeSet;
	private Collection<SwarmStation> stationSet;
	
	private Collection<SwarmPlaceEdgeType> placeEdgeTypeSet;
	private Collection<SwarmPlaceEdge> placeEdgeSet;
	
	private Collection<SwarmVisitEdgeType> visitEdgeTypeSet;
	private Collection<SwarmVisitEdge> visitEdgeSet;
	
	private Collection<SwarmTimeEdgeType> timeEdgeTypeSet;
	private Collection<SwarmTimeEdge> timeEdgeSet;
	
	private Collection<NecAgentStation> necAgentStationSet;
	
	private Collection<ItemSetLoadingAgent> itemSetLoadingAgentSet;
	private Collection<ItemSetLoadingStation> itemSetLoadingStationSet;
	
	private Collection<TimeEdgeState> timeEdgeStateSet;
	
	{
		timeUnit="";
		name="";
		description="";
		
		agentsTypeSet=new HashSet<>();
		agentSet=new HashSet<>();
		
		stationTypeSet=new HashSet<>();
		stationSet=new HashSet<>();
		
		placeEdgeTypeSet=new HashSet<>();
		placeEdgeSet=new HashSet<>();
		
		visitEdgeTypeSet=new HashSet<>();
		visitEdgeSet=new HashSet<>();
		
		timeEdgeTypeSet=new HashSet<>();
		timeEdgeSet=new HashSet<>();
		
		necAgentStationSet=new HashSet<>();
		
		itemSetLoadingAgentSet=new HashSet<>();
		itemSetLoadingStationSet=new HashSet<>();
		
		timeEdgeStateSet=new HashSet<>();
	}
	
	
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
		
		setName(path);
		setDescription(path);
		setTimeUnit(SwarmComponents.UNIT);
		
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
		/*
		 * All these were help's variable. 
		 */
		placeEdgeTypeSet=null;
		timeEdgeTypeSet=null;
		visitEdgeTypeSet=null;
	}
	
	/*
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeUnit
	 * ()
	 */
	
	public void setTimeUnit(int timeUnit) {
		this.timeUnit=String.format("ZeitEinheit(%d).", timeUnit);
	}
	
	@Override
	public String getTimeUnit() {
		return timeUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getName()
	 */
	@Override
	public String getName() {
		
		return name;
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

	protected String setName(Path path){
		name=String.format("%sLG",path.getFileName().toString().split("[.]")[0]);
		return  name;
	}
	
	protected String setDescription(Path path){
		description=path.getFileName().toString();
		return  description;
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
	
	protected void setAgents(Collection<SwarmAgentType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
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
	
	protected void setStations(Collection<SwarmStationType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
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
			List<SwarmPlaceEdgeConfig> list, Collection<SwarmStationType> set) throws SwarmException{
		
		if (list == null)
			return;
		
		List<SwarmStationType> args=new ArrayList<>(2);
		for (SwarmPlaceEdgeConfig obj : list) {
			
			args.add(new SwarmComponentDefaultFilter().filter(set, obj.getFirstConnectedIdRefSwarmPlaceEdge()));
			args.add(new SwarmComponentDefaultFilter().filter(set, obj.getSecondConnectedIdRefSwarmPlaceEdge()));
			
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
	
	protected void setPlaceEdge(Collection<SwarmPlaceEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			return;
		
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
			List<SwarmVisitEdgeConfig> list,Collection<SwarmAgentType> agSet,Collection<SwarmStationType> stSet)throws SwarmException {
		
		if (list == null)
			throw new SwarmException("Null pointer exception");
		if (list.isEmpty())
			throw new SwarmException("There aren't elements into the list");
		
		List<SwarmComponents> args=new ArrayList<>(2);
		for (SwarmVisitEdgeConfig obj : list) {
			
			args.add(new SwarmComponentDefaultFilter().filter(agSet, obj.getFirstConnectedIdRefSwarmVisitEdge()));
			args.add(new SwarmComponentDefaultFilter().filter(stSet, obj.getSecondConnectedIdRefSwarmVisitEdge()));
			if(args.get(0)==null||args.get(1)==null)
				continue;
			
			visitEdgeTypeSet.add(new SwarmVisitEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmStationType)args.get(1)));
			
			args.clear();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getVisitEdge
	 * (java.util.Set)
	 */
	
	protected void setVisitEdge(Collection<SwarmVisitEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		
		
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
	
	protected void setTimeEdgeType(List<SwarmTimeEdgeConfig> list,Collection<SwarmAgentType> agSet,Collection<SwarmStationType> stSet ) throws SwarmException{
		if (list == null)
			return;
		
		
		List<SwarmComponents> args=new ArrayList<>(2);
		SwarmAgentType agT=null;
		SwarmStationType stT=null;
		for (SwarmTimeEdgeConfig obj : list) {
			
			agT=new SwarmComponentDefaultFilter().filter(agSet, obj.getFirstConnectedIdRefSwarmTimeEdge());
			if(agT==null){
				stT=new SwarmComponentDefaultFilter().filter(stSet, obj.getFirstConnectedIdRefSwarmTimeEdge());
				args.add(stT);
			}else{
				args.add(agT);
			}
			
			agT=new SwarmComponentDefaultFilter().filter(agSet, obj.getSecondConnectedIdRefSwarmTimeEdge());
			
			if(agT==null){
				stT=new SwarmComponentDefaultFilter().filter(stSet, obj.getSecondConnectedIdRefSwarmTimeEdge());
				args.add(stT);
			}else{
				args.add(agT);
			}
			
			if(args.get(0) instanceof SwarmAgentType && args.get(1) instanceof SwarmStationType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmStationType)args.get(1)));	
			}else if(args.get(0) instanceof SwarmAgentType && args.get(1) instanceof SwarmAgentType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmAgentType)args.get(0),(SwarmAgentType)args.get(1)));	
			}else if(args.get(0) instanceof SwarmStationType && args.get(1) instanceof SwarmStationType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmStationType)args.get(0),(SwarmStationType)args.get(1)));	
			}else if(args.get(0) instanceof SwarmStationType && args.get(1) instanceof SwarmAgentType){
				timeEdgeTypeSet.add(new SwarmTimeEdgeType(obj,(SwarmStationType)args.get(0),(SwarmAgentType)args.get(1)));	
			}
			
			args.clear();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.kreatures.swarm.serialize.transform.XmlToBeliefBase#getTimeEdge
	 * (java.util.Set)
	 */
	
	protected void setTimeEdge(Collection<SwarmTimeEdgeType> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
//		if (set.isEmpty())
//			throw new SwarmException("There aren't elements into the set");
		
		
		
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
	
	protected void setNecAgentStation(Collection<SwarmVisitEdge> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
		
		
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
			Collection<SwarmVisitEdgeType> set)throws SwarmException {
		
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			throw new SwarmException("There aren't elements into the set");
		
			
		for(SwarmVisitEdgeType obj:set){
			try{
				for(;;){
					itemSetLoadingAgentSet.add(new ItemSetLoadingAgent(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS&&ex.getExceptionType()!=SwarmExceptionType.INFORM)
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
			Collection<SwarmPlaceEdgeType> set)throws SwarmException {
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			return;
		
		List<SwarmException> catchException=new ArrayList<>(1);
		
		set.stream().filter(p->p.isDirected()).forEach(obj->{
			try{
				for(;;){
					itemSetLoadingStationSet.add(new ItemSetLoadingStation(obj));
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS&&ex.getExceptionType()!=SwarmExceptionType.INFORM){
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
	
	protected void setTimeEdgeState(Collection<SwarmTimeEdge> set) throws SwarmException{
		if (set == null)
			throw new SwarmException("Null pointer exception");
		
		if (set.isEmpty())
			return;
		TimeEdgeState tmp=null;
		for(SwarmTimeEdge obj:set){
			try{
				checkDuplication: 
				for(;;){
					tmp=new TimeEdgeState(obj);
						for(TimeEdgeState elt:timeEdgeStateSet){
							if(tmp.equals(elt)){
								continue checkDuplication;
							}
						}
						timeEdgeStateSet.add(tmp);
				}
			}catch(SwarmException ex){
				if(ex.getExceptionType()!=SwarmExceptionType.BREAKS&&ex.getExceptionType()!=SwarmExceptionType.INFORM)
					throw ex;
					
			}
		}
		
		
	}



	@Override
	public Collection<SwarmAgentType> getAllAgentType() {
		
		return agentsTypeSet;
	}



	@Override
	public Collection<SwarmAgent> getAllAgents() {
		
		return agentSet;
	}



	@Override
	public Collection<SwarmStationType> getAllStationType() {
		
		return stationTypeSet;
	}



	@Override
	public Collection<SwarmStation> getAllStations() {
		
		return stationSet;
	}



	
	protected Collection<SwarmPlaceEdgeType> getAllPlaceEdgeType() {
		
		return placeEdgeTypeSet;
	}



	@Override
	public Collection<SwarmPlaceEdge> getAllPlaceEdge() {
		
		return placeEdgeSet;
	}



	
	protected Collection<SwarmVisitEdgeType> getAllVisitEdgeType() {
		
		return visitEdgeTypeSet;
	}



	@Override
	public Collection<SwarmVisitEdge> getAllVisitEdge() {
		
		return visitEdgeSet;
	}



	
	protected Collection<SwarmTimeEdgeType> getAllTimeEdgeType() {

		return timeEdgeTypeSet;
	}



	@Override
	public Collection<SwarmTimeEdge> getAllTimeEdge() {

		return timeEdgeSet;
	}



	@Override
	public Collection<NecAgentStation> getAllNecAgentStation() {
		
		return necAgentStationSet;
	}



	@Override
	public Collection<ItemSetLoadingAgent> getAllItemSetLoadingAgent() {

		return itemSetLoadingAgentSet;
	}



	@Override
	public Collection<ItemSetLoadingStation> getAllItemSetLoadingStation() {
		
		return itemSetLoadingStationSet;
	}



	@Override
	public Collection<TimeEdgeState> getAllTimeEdgeState() {
		
		return timeEdgeStateSet;
	}

}
