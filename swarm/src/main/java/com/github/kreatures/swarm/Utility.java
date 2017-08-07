package com.github.kreatures.swarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * This is a list of utility methods for KReatures Project.
 * @author donfack
 *
 */

public final class Utility {
	/** This class must no have instances.*/
	private Utility() {}
	
	/**
	 *This 
	 * @param hashCodeSuperClass is the hashCode of the super class. zero when Object is the super class.
	 * @param attributes all attributes of the class.
	 * @return the computed hashCode.
	 */
	public static int computeHashCode(int hashCodeSuperClass,Object... attributes) {
		if(attributes!=null) {
			int sum=hashCodeSuperClass;
			for(Object attribute:attributes) {
				sum+=(attribute!=null?attribute.hashCode():0);
			}
			return sum*7;
		}
		
		return hashCodeSuperClass*7;
	}
	
	/**
	 * @param desire a formula as option for a agent.
	 * @return the name of the given formula
	 */
	public static String getFormulName(FolFormula desire){
		Pattern pattern=Pattern.compile("(\\w*)[(]");
		Matcher matcher=pattern.matcher(desire.toString());
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
