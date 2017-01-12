package com.github.kreatures.swarm.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.exceptions.SwarmExceptionType;
import com.github.kreatures.swarm.serialize.SwarmTimeEdgeConfig;

/**
 * TimeEdge(Name1,TypeName1,Name2,TypeName2,TimeType,IsDirected,ConnectionType,weight)
 * 
 * @author donfack
 *
 */
public class SwarmTimeEdgeType implements SwarmComponents {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmTimeEdgeType.class);
	/**
	 * This informs about the kind of components which are used in this
	 * time-Edge the
	 */
	protected TimeEdge kindOfConnection;
	protected int id;
	protected int numberFirstComponent;
	protected int numberSecondComponent;
	protected String firstComponentTypeName;
	protected String secondComponentTypeName;
	// ConnectionType
	protected ConnectionType logicalConnection;

	protected boolean directed;
	protected int weight;

	/**
	 * This constructor is use to make a copy of the object.
	 * @param other object to copy
	 */
	protected SwarmTimeEdgeType(SwarmTimeEdgeType other) {
		this.kindOfConnection = other.kindOfConnection;
		this.firstComponentTypeName = other.firstComponentTypeName;
		this.secondComponentTypeName = other.secondComponentTypeName;
		this.logicalConnection = other.logicalConnection;
		this.directed = other.directed;
		this.weight = other.weight;
	}

	@Override
	public String getName() {

		return String.format("TimeEdge:%s%s",firstComponentTypeName, secondComponentTypeName);
	}

	@Override
	public String getDescription() {

		return String.format("Time edge of component =%s and component =%S",firstComponentTypeName , secondComponentTypeName);
	}

	@Override
	public String getResourceType() {

		return RESOURCE_TYPE;
	}

	@Override
	public String getCategory() {
		return "AbstractSwarm: time edge";
	}
	
	@Override
	public String toString() {
		return String.format("TimeEdge(%s,%s,%s,%s,%s,%d).", firstComponentTypeName, secondComponentTypeName,
				kindOfConnection.toString(), directed, logicalConnection, weight);
	}

	public TimeEdge getKindOfConnection() {
		return kindOfConnection;
	}

	public String getFirstComponentTypeName() {
		return firstComponentTypeName;
	}

	public String getSecondComponentTypeName() {
		return secondComponentTypeName;
	}

	public ConnectionType getLogicalConnection() {
		return logicalConnection;
	}

	public boolean isDirected() {
		return directed;
	}

	public int getWeight() {
		return weight;
	}

	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig, SwarmAgentType agentType1, SwarmAgentType agentType2) throws SwarmException{
		if (swarmConfig == null || agentType1 == null || agentType2 == null) {
			throw new SwarmException("Null pointer exception");
		}

		logicalConnection = ConnectionType.NO;

		if (swarmConfig.getDirectedSwarmTimeEdge().equals("yes")) {
			this.directed = true;
		} else {
			this.directed = false;
		}

		if (swarmConfig.getFirstAndConnectedSwarmTimeEdge().equals("yes")) {
			logicalConnection = ConnectionType.YES;
		}

		if (swarmConfig.getSecondAndConnectedSwarmTimeEdge().equals("yes")) {
			if (logicalConnection == ConnectionType.YES) {
				logicalConnection = ConnectionType.BOTH;
			} else {
				logicalConnection = ConnectionType.YES;
			}

		} else if (logicalConnection == ConnectionType.YES) {
			SwarmAgentType agentType3 = agentType2;
			agentType2 = agentType1;
			agentType1 = agentType3;
		}

		if (agentType1.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
			firstComponentTypeName = agentType1.getName();
			numberFirstComponent = agentType1.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		if (agentType2.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
			secondComponentTypeName = agentType2.getName();
			numberSecondComponent = agentType2.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		kindOfConnection = TimeEdge.AGENT_AGENT;

		this.id = swarmConfig.getIdSwarmTimeEdge();

		this.weight = swarmConfig.getValueSwarmTimeEdge();
	}

	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig, SwarmAgentType agentType, SwarmStationType stationType) throws SwarmException{
		if (swarmConfig == null || stationType == null || agentType == null) {
			throw new SwarmException("Null pointer exception");
		}
		boolean permut = false;
		logicalConnection = ConnectionType.NO;

		if (swarmConfig.getDirectedSwarmTimeEdge().equals("yes")) {
			this.directed = true;
		} else {
			this.directed = false;
		}

		if (swarmConfig.getFirstAndConnectedSwarmTimeEdge().equals("yes")) {
			logicalConnection = ConnectionType.YES;
		}

		if (swarmConfig.getSecondAndConnectedSwarmTimeEdge().equals("yes")) {
			if (logicalConnection == ConnectionType.YES) {
				logicalConnection = ConnectionType.BOTH;
			} else {
				logicalConnection = ConnectionType.YES;
			}

		} else if (logicalConnection == ConnectionType.YES) {
			permut = true;
			kindOfConnection = TimeEdge.STATION_AGENT;
			if (stationType.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
				firstComponentTypeName = stationType.getName();
				numberFirstComponent = stationType.getCount();
			} else {
				throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
			}

			if (agentType.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
				secondComponentTypeName = agentType.getName();
				numberSecondComponent = agentType.getCount();
			} else {
				throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
			}
		}

		this.id = swarmConfig.getIdSwarmTimeEdge();

		this.weight = swarmConfig.getValueSwarmTimeEdge();

		if (permut)
			return;
		kindOfConnection = TimeEdge.AGENT_STATION;
		if (stationType.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
			secondComponentTypeName = stationType.getName();
			numberSecondComponent = stationType.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		if (agentType.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
			firstComponentTypeName = agentType.getName();
			numberFirstComponent = agentType.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

	}

	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig, SwarmStationType stationType, SwarmAgentType agentType) throws SwarmException {
		if (swarmConfig == null || stationType == null || agentType == null) {
			throw new SwarmException("Null pointer exception",SwarmExceptionType.IllEGALARGUMENT);
		}
		boolean permut = false;
		logicalConnection = ConnectionType.NO;

		if (swarmConfig.getDirectedSwarmTimeEdge().equals("yes")) {
			this.directed = true;
		} else {
			this.directed = false;
		}

		if (swarmConfig.getFirstAndConnectedSwarmTimeEdge().equals("yes")) {
			logicalConnection = ConnectionType.YES;
		}

		if (swarmConfig.getSecondAndConnectedSwarmTimeEdge().equals("yes")) {
			if (logicalConnection == ConnectionType.YES) {
				logicalConnection = ConnectionType.BOTH;
			} else {
				logicalConnection = ConnectionType.YES;
			}

		} else if (logicalConnection == ConnectionType.YES) {
			permut = true;
			if (stationType.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
				secondComponentTypeName = stationType.getName();
				numberSecondComponent = stationType.getCount();
			} else {
				throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
			}

			if (agentType.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
				firstComponentTypeName = agentType.getName();
				numberFirstComponent = agentType.getCount();
			} else {
				throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
			}
			kindOfConnection = TimeEdge.AGENT_STATION;
		}

		this.id = swarmConfig.getIdSwarmTimeEdge();

		this.weight = swarmConfig.getValueSwarmTimeEdge();

		if (permut)
			return;

		kindOfConnection = TimeEdge.STATION_AGENT;
		if (stationType.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
			firstComponentTypeName = stationType.getName();
			numberFirstComponent = stationType.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		if (agentType.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
			secondComponentTypeName = agentType.getName();
			numberSecondComponent = agentType.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

	}

	public SwarmTimeEdgeType(SwarmTimeEdgeConfig swarmConfig, SwarmStationType stationType1,
			SwarmStationType stationType2) throws SwarmException {
		if (swarmConfig == null || stationType1 == null || stationType2 == null) 
			throw new SwarmException("Null pointer exception");

		logicalConnection = ConnectionType.NO;

		if (swarmConfig.getDirectedSwarmTimeEdge().equals("yes")) {
			this.directed = true;
		} else {
			this.directed = false;
		}

		if (swarmConfig.getFirstAndConnectedSwarmTimeEdge().equals("yes")) {
			logicalConnection = ConnectionType.YES;
		}

		if (swarmConfig.getSecondAndConnectedSwarmTimeEdge().equals("yes")) {
			if (logicalConnection == ConnectionType.YES) {
				logicalConnection = ConnectionType.BOTH;
			} else {
				logicalConnection = ConnectionType.YES;
			}

		} else if (logicalConnection == ConnectionType.YES) {
			SwarmStationType stationType3 = stationType2;
			stationType2 = stationType1;
			stationType1 = stationType3;
		}

		if (stationType1.id == swarmConfig.getFirstConnectedIdRefSwarmTimeEdge()) {
			firstComponentTypeName = stationType1.getName();
			numberFirstComponent = stationType1.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		if (stationType2.id == swarmConfig.getSecondConnectedIdRefSwarmTimeEdge()) {
			secondComponentTypeName = stationType2.getName();
			numberSecondComponent = stationType2.getCount();
		} else {
			throw new SwarmException("the given second component isn't correct.",SwarmExceptionType.IllEGALARGUMENT);
		}

		kindOfConnection = TimeEdge.STATION_STATION;

		this.id = swarmConfig.getIdSwarmTimeEdge();

		this.weight = swarmConfig.getValueSwarmTimeEdge();
	}
}
