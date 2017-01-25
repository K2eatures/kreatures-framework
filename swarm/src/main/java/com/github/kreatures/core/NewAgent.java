/**
 * 
 */
package com.github.kreatures.core;

import java.util.Collection;

/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public class NewAgent extends AgentAbstract {

	/**
	 * @param name
	 * @param env
	 */
	public NewAgent(String name, KReaturesEnvironment env) {
		super(name, env);
	}
	
	public Collection<Perception> getPerceptions(){
		return perceptions;
	}
	

	public Perception getPerception(int index){
		return perceptions.get(index);
	}
}
