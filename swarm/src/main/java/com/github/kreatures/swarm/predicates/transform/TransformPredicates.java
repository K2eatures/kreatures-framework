package com.github.kreatures.swarm.predicates.transform;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.serialize.transform.FolFormulaTransform;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.predicates.PredicateName;
import com.github.kreatures.swarm.predicates.SwarmPredicate;


import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * Convert a {@link SwarmPredicat} object to a {@link FolFormula}
 * and vis versa. And convert also a collection of SwarmPredicat
 * to a collection of FolFormula.
 * @author Cedric Perez Donfack
 *
 */
public class TransformPredicates {

	/** reference to the logback instance used for logging */
	private static Logger LOG = LoggerFactory
			.getLogger(TransformPredicates.class);
	public TransformPredicates() {}
	/**
	 * transform a {@link FolFormula} to a {@link SwarmPredicat}
	 * @param predicate a FolFormula object
	 * @return a SwarmPredicat object.
	 * @throws NullPointerException when there are no desire AbstractSwarm representation of this tweety represenation
	 */
	@SuppressWarnings("unchecked")
	public static <E extends FolFormula,T extends SwarmPredicate> T getSwarmPredicate(E predicate) throws Exception {
		PredicateName enumPredicateName=getPredicateName(Utility.getFormulName(predicate));
		return (T) enumPredicateName.getSwarmPredicate(predicate);
		
	}
	/**
	 * transform a {@link SwarmPredicat} to a {@link FolFormula} 
	 * @param a SwarmPredicat object.
	 * @return predicate as FolFormula object
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <E extends FolFormula,T extends SwarmPredicate> E getLiteral(T swarmPredicate) throws Exception {

		FolFormulaTransform folTransform=new FolFormulaTransform();

		E obj=(E)folTransform.read(swarmPredicate.toString());
		folTransform=null;
		return obj;
	}

	/**
	 * This method helps to create a formula through a String. 
	 * The first parameter must be the predicate name and the others are the terms of the predicate
	 * in the given order.   
	 * @param params list of parameters, where the first is the predicate name and the reste
	 * are the terms in the given order.
	 * @return predicate as FolFormula object
	 * @throws IllegalArgumentException when the parameters are less than 2.
	 */
	@SuppressWarnings("unchecked")
	public static <E extends FolFormula,T extends SwarmPredicate> E getLiteral(String ...params) throws Exception {

		if (params.length<2)
			throw new IllegalArgumentException("The parameters are wrongs");
		
		StringBuilder str=new StringBuilder(params[0]).append("(");
		int counter;
		for(counter=1;counter<params.length-1;counter++) {
			str.append(params[counter]);
		}
		str.append(params[counter]).append(").");
		FolFormulaTransform folTransform=new FolFormulaTransform();

		E obj=(E)folTransform.read(str.toString());
		folTransform=null;
		str=null;
		return obj;
	}
	
//	private static <E extends FolFormula>  String getPredicatName(E predicat){
//		String predicatName=predicat.toString().substring(0, predicat.toString().indexOf("("));
//		return String.format("com.github.kreatures.swarm.predicates.Predicate%s",predicatName);
//	}
	/**
	 * transform a set of {@link FolFormula} to a set of {@link SwarmPredicat}
	 * @param setFormula a set of predicate of FolFormula object
	 * @return a set of SwarmPredicat object.
	 */
	public static <E extends FolFormula, T extends SwarmPredicate>  Set<SwarmPredicate> getSetPredicat(Set<E> setFormula){
		Set<SwarmPredicate> setSwarmPredicate=new HashSet<>();
		setFormula.stream().forEach(folFormula->{
			try {
				T t=getSwarmPredicate(folFormula);
				System.out.println(setSwarmPredicate);
				setSwarmPredicate.add(t);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		});
		return setSwarmPredicate;
	}
	
	/**
	 * transform a set of {@link SwarmPredicat} to a set of {@link FolFormula} 
	 * @param a set of SwarmPredicat object.
	 * @return setFormula a set of predicate of FolFormula object
	 */
	public static <E extends FolFormula, T extends SwarmPredicate>  Set<E> getSetFolFormula(Set<T> setSwarmPredicate){
		Set<E> setLiteral=new HashSet<>();
		setSwarmPredicate.stream().forEach(swarmPredicate->{
			try {
				setLiteral.add(getLiteral(swarmPredicate));
			} catch (Exception e) {
				LOG.error(e.getMessage());
				e.printStackTrace();
			}
		});
		return setLiteral;
	}
	
	/**
	 * @param desire tweety representation of desire.
	 * @return AbstractSwarm reprsentation of desire.
	 * @throws NullPointerException when there are no desire AbstractSwarm representation of this tweety represenation
	 */
	private static PredicateName getPredicateName(String desireName) {
		switch(desireName) {
			case "Agent": return PredicateName.Agent;
			case "ItemSetLoadingAgent": return PredicateName.ItemSetLoadingAgent;
			case "ItemSetLoadingStation" : return PredicateName.ItemSetLoadingStation;
			case "NecAgentStation":return PredicateName.NecAgentStation;
			case "PlacedEdge":return PredicateName.PlacedEdge;
			case "Station" :return PredicateName.Station;
			case "TimeEdge":return PredicateName.TimeEdge;
			case "TimeEdgeState":return PredicateName.TimeEdgeState;
			case "VisitEdge" :return PredicateName.VisitEdge;
			default: throw new NullPointerException("there are no desire swarm representation of this tweety represenation");
		}
	}
}
