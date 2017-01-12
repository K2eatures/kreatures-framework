package com.github.kreatures.swarm.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * @author donfack
 *
 */
public class SwarmException extends Exception{
	
	private static final Logger LOG = LoggerFactory.getLogger(SwarmException.class);
	private SwarmExceptionType exceptionType;
	
	
	public SwarmException(String message){
		super(message);		
		exceptionType=SwarmExceptionType.ERROR;
		LOG.error(message);
	}
	
	public SwarmException(String message,SwarmExceptionType type){
		super(message);	
		LOG.debug(message);
		exceptionType=type;
	}
	
	public SwarmExceptionType getExceptionType(){
		return exceptionType;
	}
}
