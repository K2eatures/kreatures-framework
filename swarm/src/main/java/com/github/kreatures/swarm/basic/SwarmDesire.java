package com.github.kreatures.swarm.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Desire;
import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.SwarmPredicate;

import net.sf.tweety.logics.fol.syntax.FolFormula;

public abstract class SwarmDesire extends Desire {
	/**
	 * predicate name of the tweety representation desire.
	 */
	private String formulName=null;
	/** Default Ctor: Initialize plan and atom with null */
	public SwarmDesire() {
	}

	public SwarmDesire(FolFormula desire) {
		super(desire);
		formulName=createFormulName(desire);
	}

	public SwarmDesire(FolFormula desire, Perception reason) {
		super(desire, reason);
		formulName=createFormulName(desire);
	}

	public SwarmDesire(SwarmDesire other) {
		super(other);
	}
	/**
	 * 
	 * @return predicate name of the tweety representation desire.
	 */
	
	public String getFormulName(){
		return formulName;
	}
	/**
	 * @param desire a formula as option for a agent.
	 * @return the name of the given formula
	 */
	private String createFormulName(FolFormula desire){
		Pattern pattern=Pattern.compile("(\\w*)[(]");
		Matcher matcher=pattern.matcher(desire.toString());
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
	
	public boolean compareFormulaName(FolFormula formula){
		String otherFormulaName=createFormulName(formula);
		if(formula==null || otherFormulaName==null)
			return false;
		return formulName.equals(otherFormulaName);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.getFormulName() == null ? 0 : this.getFormulName().hashCode())) * 11;
	}
}
