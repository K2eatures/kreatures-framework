package com.github.kreatures.swarm.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Desire;
import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

public class SwarmDesire extends Desire {
	private String formulName="";

	public SwarmDesire() {
	}

	public SwarmDesire(FolFormula desire) {
		super(desire);
		formulName=getFormulName(desire);
	}

	public SwarmDesire(FolFormula desire, Perception reason) {
		super(desire, reason);
		formulName=getFormulName(desire);
	}

	public SwarmDesire(Desire other) {
		super(other);
	}
	
	/**
	 * @param desire a formula as option for a agent.
	 * @return the name of the given formula
	 */
	public String getFormulName(FolFormula desire){
		Pattern pattern=Pattern.compile("(\\w*)[(]");
		Matcher matcher=pattern.matcher(desire.toString());
		if(matcher.find()) {
			return matcher.group(1);
		}
		return "";
	}
	
	public boolean compareFormulaName(FolFormula formula){
		return formulName.equals(getFormulName(formula));
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
		
}
