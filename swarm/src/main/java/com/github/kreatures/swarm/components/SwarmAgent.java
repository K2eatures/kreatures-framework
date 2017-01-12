package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * 
 * @author donfack
 *
 */

public class SwarmAgent extends SwarmAgentType{
	private static final Logger LOG = LoggerFactory.getLogger(SwarmAgent.class);
	/**
	 * this attribute is used in order to always have a unique name for a given agent type.  
	 */
	private static int _UNIQUE=0;
	/**
	 * This is the Agent name and has to be unique. 
	 */
	private String agentName; 
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmAgent(SwarmAgent other){
		super(other);
		this.agentName=other.agentName;
	}
	
	public SwarmAgent(SwarmAgentType other) throws SwarmException{
		super(other);
		if(_UNIQUE<count){
			SwarmAgent._UNIQUE++;
			agentName=name+SwarmAgent._UNIQUE;
		}else{
			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d Agent(s).",super.getName(),
					count),SwarmExceptionType.BREAKS);
		}
	}
	
	@Override
	public String getName(){
		return agentName;
	}
	
	@Override
	public SwarmAgent clone(){
		return new SwarmAgent(this);
	}
	/**
	 * Agent(AgentName,AgentType,freq,nec,cap).
	 */
	public String toString() {
		return String.format("Agent(%s,%s,%d,%d,%d).",agentName,getAgentTypeName(),frequency,necessity,capacity);
	}
}
