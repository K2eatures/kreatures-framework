package com.github.kreatures.core;

/**
 * This class contents information about files and folder
 * that a agent or the environment needs to treats data and files. 
 * @author Cedric Perez Donfack
 *
 */
public final class KReaturesConst {
	/**
	 * Time unit 
	 * TODO This is no use and has to be deleted later
	 */
	private static int timeUnit;
	/**
	 * Folder where agents stored informations about their behaviors.
	 */
	public static final String _KREaturesLogDataFileExt=".swarm";
	
	/**
	 * Folder where agents stored informations about their behaviors.
	 */
	public static final String _KREaturesLogDataFolderName="historic";
	
	/**
	 * random Agent suffix of agent file in KReatures.
	 */
	public static final String _KREaturesRandomAgentConfigFile="_random_agent.xml";
	
	/**
	 * heuristic Agent suffix of agent file in KReatures.
	 */
	public static final String _KREaturesHeuristicAgentConfigFile="_heuristic_agent.xml";
	
	/**
	 * Cycle suffix of cycle file in KReatures.
	 */
	public static final String _KREaturesAgentCycleConfigGFile="_cycle.xml";
	/**
	 * Beliefbase suffix of Beliefbase file in KReatures.
	 */
	public static final String _KREaturesBeliefsConfigFile="_beliefbase.xml";
	
	public static final String _KREaturesSwarmStrategieFile="strategie.asp";
	
	/**
	 * simulation suffix of simulation file in KReatures.
	 */
	public static final String _KREaturesSimulationConfigSuffixName="_simulation.xml";
	/**
	 * Angerona config file.
	 */
	public static final String _KReaturesConfigFile="configuration.xml";
	/**
	 * Folder where the configuration of the scenarios model are locate 
	 */
	public static final String _KReaturesSwarmCategorie="swarm";
	
	/**
	 * extension of file which will be create by AbstractSwarm
	 */
	public static final String _KReaturesSwarmXmlPatternFile=".*.lg.xml";
	/**
	 * name of the abstractSwarm application
	 */
	public static final String _KReaturesSwarmGuiLoaderFile="AbstractSwarm.exe";
	
	public static enum AgStrategie{
		AGENT_RANDOM_STRATEGIE_FILE,
		AGENT_HEURISTIC_STRATEGIE_FILE;
	}

}
