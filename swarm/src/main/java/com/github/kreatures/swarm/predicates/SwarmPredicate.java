package com.github.kreatures.
swarm.predicates;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.github.kreatures.core.KReaturesAtom;
import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.basic.SwarmDesire;

import net.sf.tweety.logics.fol.syntax.FolFormula;



/**
 *   
 * 
 * 
 * @author Cedric Perez Donfack
 *
 */

public abstract class SwarmPredicate extends SwarmDesire implements KReaturesAtom{
//	private static final Logger LOG = LoggerFactory.getLogger(SwarmPredicate.class);
	/** Default Ctor: Initialize plan and atom with null */
	protected SwarmPredicate() {}
	
	public SwarmPredicate(FolFormula desire) {
		super(desire);
	}

	public SwarmPredicate(FolFormula desire, Perception reason) {
		super(desire, reason);
	}

	public SwarmPredicate(SwarmPredicate other) {
		super(other);
	}	
	/**
	 * convert a {@link FolFormula} object to a {@link SwarmPredicate} object
	 * @param folFormula tweety representation of desire.
	 * @return AbstractSwarm representation of desire.
	 */
	protected abstract void createInstance(FolFormula folFormula);
	/**
	 * create a copy of this PredicateStation object.
	 */
	@Override
	protected abstract Object clone(); 
	/**
	 * 
	 * @return the predicate name of the desire
	 */
	public String getPredicatType() {
		return getFormulName();
	}
}