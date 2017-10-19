package com.github.kreatures.swarm.basic;

import java.util.Collection;

import com.github.kreatures.core.Perception;
/**
 * That is the class for encapsulation of the agents informations.
 * With it, the agents can change informations: perceptions and actions.
 * @author Cedric Perez Donfack
 *
 */
public class SwarmInformation{
	
	private Collection<Perception> perceptions=new java.util.HashSet<>();
	
	public Collection<Perception> getPerceptions(){
		return perceptions;
	}
	
	public void setPerceptions(Collection<Perception> perceptions) {
		
		this.perceptions.clear();
		
		this.perceptions.addAll(perceptions);
	}
	
	public void setPerception(Perception perceptions) {
		
		this.perceptions.clear();
		
		this.perceptions.add(perceptions);
	}
	
	@Override
	public String toString() {
		return perceptions==null||perceptions.isEmpty()?"":perceptions.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if(!(other instanceof SwarmInformation)) return false;
		
		SwarmInformation obj=(SwarmInformation)other;
		
		return perceptions.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return perceptions.hashCode();
	}
}