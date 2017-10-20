package com.github.kreatures.swarm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.swarm.basic.MainAction;

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
	/**
	 * This format the log information of a agent. At the end of the simulation, a agent has to stored its information
	 * into a files data whose name is the agent name.
	 * @param tick the current tick number
	 * @param actionTyp Which action a agent has current do. The MainAction are defined in {@link MainAction}.  
	 * @param stationId the of a station.
	 * @param waitTime give how many time a agent has wait before enter to a station. 
	 * @return data which will be stored by the agent in the format tick,actionTyp,stationId
	 */
	public static String logData(long tick,MainAction mainAction, String stationId, int waitTime ){
		return String.format("%d,%s,%s,%d%n", tick,mainAction.name(),stationId,waitTime);
	}

}
