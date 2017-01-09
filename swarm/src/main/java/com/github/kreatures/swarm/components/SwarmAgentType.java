package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.serialize.SwarmAgentTypeConfig;


public class SwarmAgentType implements SwarmConfig{
	
	private static final Logger LOG = LoggerFactory.getLogger(SwarmAgentType.class);
	
	/**
	 * The following attributes belong to AbstractSwarm
	 */
	protected int id;
	
	/**
	 * This is the Agent-type name and has to be unique. 
	 */
	protected String name;
	/**
	 * default value is infinite. 
	 */
	protected int frequency;
	/**
	 * default value is infinite. 
	 */
	protected int necessity;
	/**
	 * default value is one unit. 
	 */
	protected int time;
	/**
	 * default value is one unit. 
	 */
	protected int priority;
	
	/**
	 * default value is zero. 
	 */
	protected int cycle;
	/**
	 * default value is infinite. 
	 */
	protected int capacity;
	/**
	 * default value is zero. 
	 */
	protected int size;
	/**
	 * default value is unit. 
	 */
	protected int speed;
	/**
	 * default value is unit. 
	 */
	protected int count;

	protected SwarmAgentType(SwarmAgentType other) {
		this.frequency=other.getFrequency();
		this.necessity=other.getNecessity();
		this.time=other.getTime();
		this.priority=other.getPriority();
		this.cycle=other.getCycle();
		this.capacity=other.getCapacity();
		this.size=other.getSize();
		this.speed=other.getSpeed();
		this.id=other.getIdentity();
	}
	
	public int getIdentity() {
		return this.id;
	}
	
	public int getFrequency() {
		
		return frequency;
	}
	
	public int getNecessity() {
		return necessity;
	}
	public int getTime() {
				return time;
	}

	public int getPriority() {
		return priority;
	}

	public int getCycle() {
		return cycle;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getSize() {	
		return size;
	}

	public int getSpeed() {
		return speed;
	}
	
	public int getCount() {
		return count;
	}
	
	@Override
	public String getName() {
		
		return name;
	}
	@Override
	public String getDescription() {
		
		return "Agent type "+name;
	}
	@Override
	public String getResourceType() {
		
		return RESOURCE_TYPE;
	}
	@Override
	public String getCategory() {
		return "AbstractSwarm: Agent Type";
	}
	/**
	 * create a agentType object with all importance informations
	 * @param swarmAgentTypeConfig 
	 */
	public SwarmAgentType(SwarmAgentTypeConfig swarmAgentTypeConfig){
			if(swarmAgentTypeConfig==null){
				LOG.error("the given argument has to be no null.");
				throw new NullPointerException("the given argument has to be no null.");
			}
			
			if(swarmAgentTypeConfig.getFrequencySwarm()==null){
					frequency= MAX_INT;
			}else{
				frequency=swarmAgentTypeConfig.getFrequencySwarm().getValue();
			}
			
			if(swarmAgentTypeConfig.getNecessitySwarm()==null){
				necessity= MAX_INT;
		}else{
			necessity=swarmAgentTypeConfig.getNecessitySwarm().getValue();
		}
			if(swarmAgentTypeConfig.getTimeSwarm()==null){
				time= UNIT;
		}else{
			time=swarmAgentTypeConfig.getTimeSwarm().getValue();
		}
			
			if(swarmAgentTypeConfig.getPrioritySwarm()==null){
				priority= UNIT;
		}else{
			priority=swarmAgentTypeConfig.getPrioritySwarm().getValue();
		}
			
			if(swarmAgentTypeConfig.getCycleSwarm()==null){
				cycle= ZERO_VALUE;
		}else{
			cycle=swarmAgentTypeConfig.getCycleSwarm().getValue();
		}

			if(swarmAgentTypeConfig.getCapacitySwarmAgentType()==null){
				capacity= MAX_INT;
		}else{
			capacity=swarmAgentTypeConfig.getCapacitySwarmAgentType().getValue();
		}

			if(swarmAgentTypeConfig.getSizeSwarmAgentType()==null){
				size= ZERO_VALUE;
		}else{
			size=swarmAgentTypeConfig.getSizeSwarmAgentType().getValue();
		}
			if(swarmAgentTypeConfig.getSpeedSwarmAgentType()==null){
				speed= UNIT;
		}else{
			speed=swarmAgentTypeConfig.getSpeedSwarmAgentType().getValue();
		}
			count=swarmAgentTypeConfig.getCountSwarmAgentType();
			name=swarmAgentTypeConfig.getNameSwarmAgentType();
	}
}
