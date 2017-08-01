package com.github.kreatures.core;


import java.util.ArrayList;
import java.util.List;

/**
 * SubMain class of KReatures manages all resources 
 * which come from AbstractSwarm such as Environment 
 * features, scenario models.
 *  
 * @author Cedric Perez Donfack
 * @todo the current project has to be refence.
 */
public class AbstractSwarms {
	
	private static AbstractSwarms abstractSwarm=new AbstractSwarms();
	
	/**
	 * Collections of all the resources about the environment features and scenario model.
	 * It contents one element per simulation instance.
	 */
	public List<EnvironmentComponent> listEnvironmentComponent=new ArrayList<>();
	
	
	/**
	 * Default constructor, cannot be call outside this class, because 
	 * a object of this class is a singleton.
	 */
	private AbstractSwarms() {}
	
	/**
	 * 	Implements the singleton pattern.
	 * 	@return the application wide unique instance of the KReatures class.
	 */
	public static AbstractSwarms getInstance() {
		
		return abstractSwarm;
	}
	@Override
	public String toString() {
		return listEnvironmentComponent.toString();
	}
	

}
