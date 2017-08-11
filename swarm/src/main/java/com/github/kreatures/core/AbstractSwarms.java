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
	private List<EnvironmentComponent> listEnvComponent=new ArrayList<>();

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
		return listEnvComponent.toString();
	}

	/**
	 * @param simName the simulation name	
	 * @return a environment component whose simulation name is simName. 
	 * It returns also null when there are no mappieng name.
	 */
	public EnvironmentComponent getEnvComponent(String simName) {
		for(EnvironmentComponent obj:listEnvComponent) {
			if(obj.getSimulationName().equals(simName)) {
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * @param simName the simulation name
	 * @return true when the environment component, whose simulation name is simName,
	 * is removing; and false otherwise.
	 */
	public boolean removeEnvComponent(String simName) {
		return listEnvComponent.removeIf(env->env.getSimulationName().equals(simName));
	}

	/**
	 * @param envComponent add a new component to the list of EnvironmentComponent.
	 * @return true when the environment component is adding; and false otherwise.
	 */
	public boolean addEnvComponent(EnvironmentComponent envComponent) {
		return this.listEnvComponent.add(envComponent);
	}


}
