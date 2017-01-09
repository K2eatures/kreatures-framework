package com.github.kreatures.swarm.serialize;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import com.github.kreatures.swarm.components.SwarmConfig;
@Root(name="timeEdge")
public class SwarmTimeEdgeConfig{
	@Attribute(name="id")
	protected int idSwarmTimeEdge;
	@Attribute(name="connectedIdRef1")
	protected int firstConnectedIdRefSwarmTimeEdge;
	@Attribute(name="connectedIdRef2")
	protected int secondConnectedIdRefSwarmTimeEdge;
	@Attribute (name="value")
	protected int valueSwarmTimeEdge;
	@Attribute(name="directed")
	protected String directedSwarmTimeEdge;
	@Attribute(name="andConnected1")
	protected String firstAndConnectedSwarmTimeEdge;
	@Attribute(name="andConnected2")
	protected String secondAndConnectedSwarmTimeEdge;
	
	public int getIdSwarmTimeEdge() {
		return idSwarmTimeEdge;
	}
	public int getFirstConnectedIdRefSwarmTimeEdge() {
		return firstConnectedIdRefSwarmTimeEdge;
	}
	public int getSecondConnectedIdRefSwarmTimeEdge() {
		return secondConnectedIdRefSwarmTimeEdge;
	}
	public int getValueSwarmTimeEdge() {
		return valueSwarmTimeEdge;
	}
	public String getDirectedSwarmTimeEdge() {
		return directedSwarmTimeEdge;
	}
	public String getFirstAndConnectedSwarmTimeEdge() {
		return firstAndConnectedSwarmTimeEdge;
	}
	public String getSecondAndConnectedSwarmTimeEdge() {
		return secondAndConnectedSwarmTimeEdge;
	}
	
	
}
