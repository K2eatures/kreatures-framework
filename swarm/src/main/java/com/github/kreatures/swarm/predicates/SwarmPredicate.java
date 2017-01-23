package com.github.kreatures.
swarm.predicates;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.github.kreatures.core.KReaturesAtom;



/**
 *   
 * 
 * 
 * @author donfack
 *
 */

public class SwarmPredicate implements KReaturesAtom{
//	private static final Logger LOG = LoggerFactory.getLogger(SwarmPredicate.class);

	public SwarmPredicate() {}
	
	public String getPredicatType() {return null;}
	
	public <T extends SwarmPredicate>  T createInstance(String fact) {return null;} 
}