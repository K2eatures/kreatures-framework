package com.github.kreatures.swarm.predicates;

import com.github.kreatures.core.KReaturesAtom;



/**
 * This is an Interface of serialized XML-file which will be created by a swarm-scenario.   
 * 
 * 
 * @author donfack
 *
 */

public interface SwarmPredicate extends KReaturesAtom{

	String getPredicatType();
	
	<T extends SwarmPredicate>  T createInstance(String fact) ;
		 
	
	@Override
	String toString();
 
}