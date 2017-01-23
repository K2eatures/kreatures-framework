package com.github.kreatures.swarm.predicates.transform;

import com.github.kreatures.serialize.asp.DLPAtomTransform;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

import net.sf.tweety.lp.asp.syntax.DLPAtom;

public class TransformPredicates {

	public TransformPredicates() {
	}

	public static <E extends DLPAtom,T extends SwarmPredicate> T getPredicate(E predicate) {
		SwarmPredicate obj=new SwarmPredicate();
		//@SuppressWarnings("unchecked")
		T objT=(T)obj;
		
		return objT.createInstance(predicate.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends DLPAtom,T extends SwarmPredicate> E getPredicate(T swarmPredicate) throws Exception {
		
		DLPAtomTransform atomTransform=new DLPAtomTransform();
		 
		E obj=(E)atomTransform.read(swarmPredicate.toString());
		 atomTransform=null;
		 return obj;
	}
}
