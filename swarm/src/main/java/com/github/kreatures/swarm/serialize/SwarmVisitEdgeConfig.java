package com.github.kreatures.swarm.serialize;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import com.github.kreatures.swarm.components.SwarmConfig;

@Root(name="visitEdge")
public class SwarmVisitEdgeConfig{
	@Attribute(name="id")
	protected int idSwarmVisitEdge;
	@Attribute(name="connectedIdRef1")
	protected int firstConnectedIdRefSwarmVisitEdge;
	@Attribute(name="connectedIdRef2")
	protected int secondConnectedIdRefSwarmVisitEdge;
	@Attribute (name="bold")
	protected String boldSwarmVisitEdge;
	
	public int getFirstConnectedIdRefSwarmVisitEdge() {
		return firstConnectedIdRefSwarmVisitEdge;
	}
	
	public int getSecondConnectedIdRefSwarmVisitEdge() {
		return secondConnectedIdRefSwarmVisitEdge;
	}
	
	public boolean isBold(){
		if(boldSwarmVisitEdge.equals("yes"))
			return true;
		return false;
	}
	
	//TODO all following lines have to be declare private
	public int getIdSwarmVisitEdge() {
		return idSwarmVisitEdge;
	}
	public String getBoldSwarmVisitEdge() {
		return boldSwarmVisitEdge;
	}
	
}
