package com.github.kreatures.core.logic;

import java.util.Hashtable;
import java.util.Map;

import com.github.kreatures.core.logic.asp.AspBeliefbase;

/**
 * The {@link AspBeliefbase} extension for the logic program
 * of a scenario model. There are one instance object per 
 * loaded simulation.
 *   
 * @author Cedric Perez Donfack
 *
 */
public class ScenarioModelBeliefbase extends AspBeliefbase{

//	/**
//	 * Field is a {@link java.util.Hashtable} and is a mapping between 
//	 * a simulation name as key and one instance of this class as Object.
//	 */
//	private static	Map<String,ScenarioModelBeliefbase> listAllInstances=new Hashtable<>(2,0.5f);
	/**
	 * Default ctor. It's private, because there must give only one instance per 
	 * simulation. 
	 */
	public ScenarioModelBeliefbase() {}
//	/**
//	 * Same meaning as {@link java.util.HashMap#put(Object, Object)}.
//	 * @param simName simulation name as key
//	 * @return the added object.
//	 */
//	public static ScenarioModelBeliefbase addInstance(String simName) {
//		return listAllInstances.put(simName, new ScenarioModelBeliefbase());
//	}
//	/**
//	 * Same meaning as {@link java.util.HashMap#get(Object)}.
//	 * @param simName simulation name as key
//	 * @return the object, whose key is simName.
//	 */
//	public static ScenarioModelBeliefbase getInstance(String simName) {
//		return listAllInstances.get(simName);
//	}
//	/**
//	 * Same meaning as {@link java.util.HashMap#remove(Object)}.
//	 * @param simName simulation name as key
//	 * @return the removed object.
//	 */
//	public static ScenarioModelBeliefbase removeInstance(String simName) {
//		return listAllInstances.remove(simName);
//	}
	
}
