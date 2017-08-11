package com.github.kreatures.core.asp.solver;

/**
 * SolverOptions is used to define the filter parameter
 * which the solver needs to generate a answer set. 
 * @author Cedric Perez Donfack
 *
 */
public enum SolverOptions {
	/**
	 * a answer set contains no facts 
	 */
	NOFACTS,
	
	/**
	 * a answer set can contain a filter
	 */
	FILTER,
	;
	
	@Override
	public String toString() {
		String str=null;
		switch(this) {
		case NOFACTS: str="-nofacts";
			break;
		case FILTER: str="-filter=";
			break; 
		}
		return str;
	}
}
