package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;

/**
 * %TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight) 
 * @author donfack
 *
 */
public class SwarmTimeEdge extends SwarmTimeEdgeType{

	private static final Logger LOG = LoggerFactory
			.getLogger(SwarmTimeEdge.class);
	/**
	 * this attribute is used in order to always have a unique map key <first station name, second station name> for a given
	 * place-edge type.
	 */
	private static int _UNIQUE_FIRST = 0;
	private static int _UNIQUE_SECOND = 1;

	private String firstName;
	private String secondName;
	
	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmTimeEdge(SwarmTimeEdge other){
		super(other);
		this.firstName=other.firstName;
		this.secondName=other.secondName;		
	}
	
	protected SwarmTimeEdge(SwarmTimeEdgeType other) throws SwarmException{
		super(other);
		
		if (_UNIQUE_FIRST < numberFirstComponent) {
			SwarmTimeEdge._UNIQUE_FIRST++;
			firstName = firstComponentTypeName + SwarmTimeEdge._UNIQUE_FIRST;
			secondName = secondComponentTypeName + SwarmTimeEdge._UNIQUE_SECOND;
		} else if (_UNIQUE_SECOND <= numberSecondComponent) {
			SwarmTimeEdge._UNIQUE_SECOND++;
		} else {

			throw new SwarmException(String.format("All elements of components type '%s' have be created: %d first component(s) and %d second component(s).",super.getName(),
					numberFirstComponent,numberSecondComponent),SwarmExceptionType.BREAKS);
		}
		
	}

	@Override
	public String getName(){
		return String.format("%s:%s", firstName,secondName);
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}
	
	@Override
	public SwarmTimeEdge clone(){
		return new SwarmTimeEdge(this);
	}
	
	public String toString() {
		return String.format("TimeEdge(%s,%s,%s,%s,%s,%s,%s,%d).",firstName, firstComponentTypeName, secondName,secondComponentTypeName,
				kindOfConnection.toString(), directed, logicalConnection.toString(), weight);
	}
}
