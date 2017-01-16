package com.github.kreatures.swarm.components;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.serialize.SwarmStationTypeConfig;

public class SwarmStationType implements SwarmComponents {

	/**
	 * The following attributes belong to AbstractSwarm
	 */
	protected int id;

	/**
	 * 
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
	protected int space;
	/**
	 * default value is zero.
	 */
	protected int item;
	/**
	 * default value is unit.
	 */
	protected int count;
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmStationType(SwarmStationType other) {
		this.frequency = other.getFrequency();
		this.necessity = other.getNecessity();
		this.time = other.getTime();
		this.priority = other.getPriority();
		this.cycle = other.getCycle();
		this.space = other.getSpace();
		this.item = other.getItem();
		this.id = other.getIdentity();
		this.count=other.getCount();
		this.name=other.getName();
	}

	public String getStationTypeName(){
		return name;
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

	public int getSpace() {
		return space;
	}

	public int getItem() {
		return item;
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

		return "Agent type " + name;
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
	 * 
	 * @param swarmAgentTypeConfig
	 */
	public SwarmStationType(SwarmStationTypeConfig swarmStationTypeConfig) throws SwarmException{
		if (swarmStationTypeConfig == null)
			throw new SwarmException("Null pointer exception");

		if (swarmStationTypeConfig.getFrequencySwarm() == null) {
			frequency = MAX_INT;
		} else {
			frequency = swarmStationTypeConfig.getFrequencySwarm().getValue();
		}

		if (swarmStationTypeConfig.getNecessitySwarm() == null) {
			necessity = MAX_INT;
		} else {
			necessity = swarmStationTypeConfig.getNecessitySwarm().getValue();
		}
		if (swarmStationTypeConfig.getTimeSwarm() == null) {
			time = UNIT;
		} else {
			time = swarmStationTypeConfig.getTimeSwarm().getValue();
		}

		if (swarmStationTypeConfig.getPrioritySwarm() == null) {
			priority = UNIT;
		} else {
			priority = swarmStationTypeConfig.getPrioritySwarm().getValue();
		}

		if (swarmStationTypeConfig.getCycleSwarm() == null) {
			cycle = ZERO_VALUE;
		} else {
			cycle = swarmStationTypeConfig.getCycleSwarm().getValue();
		}

		if (swarmStationTypeConfig.getSpaceSwarmStationType() == null) {
			space = MAX_INT;
		} else {
			space = swarmStationTypeConfig.getSpaceSwarmStationType()
					.getValue();
		}

		if (swarmStationTypeConfig.getItemSwarmStationType() == null) {
			item = ZERO_VALUE;
		} else {
			item = swarmStationTypeConfig.getItemSwarmStationType().getValue();
		}
		count = swarmStationTypeConfig.getCountSwarmStationType();
		name = swarmStationTypeConfig.getNameSwarmStationType().toLowerCase();
		id=swarmStationTypeConfig.getIdSwarmStationType();
	}
	/**
	 * StationType(StationTypeName,freq,nec,time,prio,cylce,item,space,count)
	 */
	public String toString() {
		return String.format("StationType(%s,%d,%d,%d,%d,%d,%d,%d,%d).",name,frequency,necessity,time,priority,cycle,item,space,count);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */
	public static String getDescriptions() {

		return "%StationType(StationTypeName,freq,nec,time,prio,cylce,item,space,count)";
	}
}
