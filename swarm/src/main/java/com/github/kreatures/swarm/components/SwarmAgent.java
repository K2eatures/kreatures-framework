package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	protected SwarmAgent(SwarmAgent other) {
		super(other);
		if(_UNIQUE<count){
			SwarmAgent._UNIQUE++;
			agentName=name+SwarmAgent._UNIQUE;
		}else{
			LOG.error("This agent-type %s can't have more than %d agent(s).",name,count);
		}
	}
	
	public String getSwarmAgentName(){
		return agentName;
	}
	public SwarmAgent clone(){
		return new SwarmAgent(this);
	}

}
