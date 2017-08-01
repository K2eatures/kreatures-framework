package com.github.kreatures.swarm.predicates.transform;

import com.github.kreatures.core.serialize.transform.FolFormulaTransform;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

import net.sf.tweety.logics.fol.syntax.FolFormula;

public class TransformPredicates {

	public TransformPredicates() {
	}
	@SuppressWarnings("unchecked")
	public static <E extends FolFormula,T extends SwarmPredicate> T getPredicate(E predicate) {
		SwarmPredicate obj=new SwarmPredicate();
		T objT=(T)obj;
		
		return objT.createInstance(predicate.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends FolFormula,T extends SwarmPredicate> E getPredicate(T swarmPredicate) throws Exception {
		
		FolFormulaTransform folTransform=new FolFormulaTransform();
		 
		E obj=(E)folTransform.read(swarmPredicate.toString());
		folTransform=null;
		 return obj;
	}
}
