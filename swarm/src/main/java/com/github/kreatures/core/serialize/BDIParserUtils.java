package com.github.kreatures.core.serialize;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.kreatures.core.KReaturesConst._KREaturesAgentCycleConfigGFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesRandomAgentConfigFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesHeuristicAgentConfigFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesBeliefsConfigFile;
import static com.github.kreatures.core.KReaturesConst._KReaturesSwarmCategorie;
import static com.github.kreatures.core.KReaturesConst._KREaturesSimulationConfigSuffixName;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_AGENTS_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_BELIEFS_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_EXAMPLES_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_DEFAULT_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_XML_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SCENARIO_MODELS;

import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.core.KReaturesConst;
import com.github.kreatures.core.KReaturesConst.AgStrategie;
import com.github.kreatures.core.serialize.AgentConfigImport;
import com.github.kreatures.core.serialize.AgentInstance;
import com.github.kreatures.core.serialize.BeliefbaseConfigImport;
import com.github.kreatures.swarm.components.BeliefParseOfSwarm;
import com.github.kreatures.swarm.components.ItemSetLoadingAgent;
import com.github.kreatures.swarm.components.ItemSetLoadingStation;
import com.github.kreatures.swarm.components.NecAgentStation;
import com.github.kreatures.swarm.components.SwarmAgent;
import com.github.kreatures.swarm.components.SwarmAgentType;
import com.github.kreatures.swarm.components.SwarmPlaceEdge;
import com.github.kreatures.swarm.components.SwarmStation;
import com.github.kreatures.swarm.components.SwarmStationType;
import com.github.kreatures.swarm.components.SwarmTimeEdge;
import com.github.kreatures.swarm.components.SwarmVisitEdge;
import com.github.kreatures.swarm.components.TimeEdgeState;
import com.github.kreatures.swarm.components.XmlToBeliefBase;
import com.github.kreatures.swarm.exceptions.SwarmException;
import com.github.kreatures.swarm.optimisation.OptShortPaths;
import com.github.kreatures.swarm.optimisation.StationNode;
import com.github.kreatures.swarm.predicates.PredicateCurrentAgent;
import com.github.kreatures.swarm.predicates.PredicateCurrentStation;

/**
 * This Class is used to parse the information from the {@link BeliefParseOfSwarm}'s object to a file. This file contents a scenario-model and initial knowledge of all agents.
 * Each agent has to load its initial knowledge into its belief-base.
 *  
 * @author Cedric Perez Donfack
 * 
 */
public final class BDIParserUtils implements BDIParser {
	/** reference to the logging facility */
	private static Logger LOG = LoggerFactory.getLogger(BDIParserUtils.class);

	private XmlToBeliefBase obj;
	/**
	 * The path where the scenario file is.
	 */
	private Path xmlPath;
	// private Path kreaturesAgentConfig;

	/**
	 * This method creates all needed files which are in relationship with a scenario. For example a scenario-model, all agent's belief,  
	 * @param path
	 * @param strategie
	 * @throws Exception
	 */
	public BDIParserUtils(Path path,AgStrategie strategie) throws Exception {
		obj = new BeliefParseOfSwarm(path);
		xmlPath = path;
		Path tmpAspPathWorldBelief=createAgentWorldBeliefs();
		Path tmpAspPathControllerBelief=createAgentControllerBeliefs();
		createScenarioModel();
		createKReaturesConfigFile(strategie);
		createExamplesDir(strategie);
		createAgentWorldAsp(tmpAspPathWorldBelief);
		Files.deleteIfExists(tmpAspPathWorldBelief);
		createAgentControllerAsp(tmpAspPathControllerBelief);
		Files.deleteIfExists(tmpAspPathControllerBelief);
		//Simulation Name
		String simName=obj.getName();
		//Log Data folder for all agents
		Path logDataPath=Paths.get(KReaturesPaths.KREATURES_EXAMPLES_DIR.toString()).resolve(simName).resolve(KReaturesConst._KREaturesLogDataFolderName
				);
		if(!Files.exists(logDataPath)){
			Files.createDirectory(logDataPath);
		}
	}

	/**
	 * 
	 * @return
	 * @throws SwarmException
	 *             if the instance is null.
	 */
	public XmlToBeliefBase getBeliefParseOfSwarmInstance() {
		return obj;
	}

	/**
	 * create and Write a initial belief of all agents into a temporal file.   
	 * @return the temporal file path
	 * @throws Exception when the temporal file will not successfully created.
	 */
	protected Path createAgentWorldBeliefs() throws Exception {
		obj = getBeliefParseOfSwarmInstance();
		
		//Path strategiePath=Paths.get(KREATURES_SWARM_CONFIG_DIR.toString()).resolve(_KREaturesSwarmStrategieFile);
		Path tmpPathWorldBelief=Files.createTempFile("swarm",".asp");
		System.out.println(tmpPathWorldBelief.toAbsolutePath());
		try (BufferedWriter buffer = Files.newBufferedWriter(tmpPathWorldBelief)) {

//			buffer.write(String.format("%s%n", obj.getTimeUnit()));
			
			buffer.write(String.format("%s%n", SwarmAgent.getDescriptions()));
			for (SwarmAgent elt : obj.getAllAgents()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmStation.getDescriptions()));
			for (SwarmStation elt : obj.getAllStations()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.flush();

		} catch (IOException ex) {
			throw ex;
		}		
		return tmpPathWorldBelief;
	}

	
	/**
	 * create and Write a initial belief of all agents into a temporal file.   
	 * @return the temporal file path
	 * @throws Exception when the temporal file will not successfully created.
	 */
	protected Path createAgentControllerBeliefs() throws Exception {
		obj = getBeliefParseOfSwarmInstance();
		
		//Path strategiePath=Paths.get(KREATURES_SWARM_CONFIG_DIR.toString()).resolve(_KREaturesSwarmStrategieFile);
		Path tmpPathWorldBelief=Files.createTempFile("swarm",".asp");
		System.out.println(tmpPathWorldBelief.toAbsolutePath());
		try (BufferedWriter buffer = Files.newBufferedWriter(tmpPathWorldBelief)) {

//			buffer.write(String.format("%s%n", obj.getTimeUnit()));
			
			buffer.write(String.format("%s%n", NecAgentStation.getDescriptions()));
			for (NecAgentStation elt : obj.getAllNecAgentStation()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", ItemSetLoadingAgent.getDescriptions()));
			for (ItemSetLoadingAgent elt : obj.getAllItemSetLoadingAgent()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", ItemSetLoadingStation.getDescriptions()));
			for (ItemSetLoadingStation elt : obj.getAllItemSetLoadingStation()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", TimeEdgeState.getDescriptions()));
			for (TimeEdgeState elt : obj.getAllTimeEdgeState()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.flush();

		} catch (IOException ex) {
			throw ex;
		}		
		return tmpPathWorldBelief;
	}
	
	/**
	 * create and Write a scenario-model into a scenario-model file.   
	 * @return the temporal file path
	 * @throws Exception when the temporal file will not successfully created.
	 */
	protected void createScenarioModel() throws Exception {
		obj = getBeliefParseOfSwarmInstance();
		
		// Check, if the scenario-model directory already exists.
		Path directoryPathScenarioModell=Paths.get(KREATURES_SCENARIO_MODELS.toString()).resolve(obj.getName());
		if(!Files.exists(directoryPathScenarioModell)) {
			Files.createDirectory(directoryPathScenarioModell);
		}
		
		// Check, if the scenario-model file already exists. 
		Path pathScenarioModell=directoryPathScenarioModell.resolve(obj.getName()+".asp");
		if(!Files.exists(pathScenarioModell)) {
			Files.createFile(pathScenarioModell);
		}
		
		System.out.println(pathScenarioModell.toAbsolutePath());
		try (BufferedWriter buffer = Files.newBufferedWriter(pathScenarioModell)) {

			buffer.write(String.format("%s%n", obj.getTimeUnit()));

			buffer.write(String.format("%s%n", SwarmAgentType.getDescriptions()));
			for (SwarmAgentType elt : obj.getAllAgentType()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", SwarmStationType.getDescriptions()));
			for (SwarmStationType elt : obj.getAllStationType()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmVisitEdge.getDescriptions()));
			for (SwarmVisitEdge elt : obj.getAllVisitEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", SwarmPlaceEdge.getDescriptions()));
			for (SwarmPlaceEdge elt : obj.getAllPlaceEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
			buffer.write(String.format("%s%n", SwarmTimeEdge.getDescriptions()));
			for (SwarmTimeEdge elt : obj.getAllTimeEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			buffer.flush();

			
		} catch (IOException ex) {
			throw ex;
		}finally {
			pathScenarioModell=null;
		}
		
		Path shortestPathInScenarioModell=directoryPathScenarioModell.resolve(obj.getName()+".opt");
		if(!Files.exists(shortestPathInScenarioModell)) {
			Files.createFile(shortestPathInScenarioModell);
		}
		
		System.out.println(shortestPathInScenarioModell.toAbsolutePath());
		
		Set<StationNode> allShortestPath=OptShortPaths.allShortestPaths(((BeliefParseOfSwarm)obj).getAllPlaceEdgeType());
		
		try (BufferedWriter buffer = Files.newBufferedWriter(shortestPathInScenarioModell)) {

			buffer.write(String.format("%% %s %n","Set of all shortest paths."));
			for (StationNode elt : allShortestPath) {
				buffer.write(String.format("%s%n", elt.toString()));				
			}
			buffer.flush();
			
		} catch (IOException ex) {
			throw ex;
		}finally {
			directoryPathScenarioModell=null;
			shortestPathInScenarioModell=null;
		}
	}
	
	/**
	 * For each agent, this method creates a file with its initial World belief.   
	 * @param aspPath the path to the created file.
	 * @return true when all files will be successfully created, and false otherwise.
	 * @throws Exception when unless one file doesn't exist.
	 */	
	protected boolean createAgentWorldAsp(Path aspPath) throws Exception{
		if(aspPath==null)
			return false;
		/**
		 * Create agents beliefs.
		 */
		for(SwarmAgent agent:obj.getAllAgents()){
			Path target=Files.copy(aspPath, Paths.get(KREATURES_EXAMPLES_DIR.toString()).resolve(obj.getName()).resolve(String.format("%s_world.asp",agent.getName())));
			createPredicateForOneAgent(agent,target);
		}
		
		return true;
	}
	
	/**
	 * For each agent, this method creates a file with its initial Controller belief.   
	 * @param aspPath the path to the created file.
	 * @return true when all files will be successfully created, and false otherwise.
	 * @throws Exception when unless one file doesn't exist.
	 */	
	protected boolean createAgentControllerAsp(Path aspPath) throws Exception{
		if(aspPath==null)
			return false;
		/**
		 * Create agents beliefs.
		 */
		for(SwarmAgent agent:obj.getAllAgents()){
			Path target=Files.copy(aspPath, Paths.get(KREATURES_EXAMPLES_DIR.toString()).resolve(obj.getName()).resolve(String.format("%s_controller.asp",agent.getName())));
//			createPredicateForOneAgent(agent,target);
		}
		
		return true;
	}
	/**
	 * Add belief own to agent such as {@link PredicateCurrentAgent}.   
	 * @param agent the agent which belief will be added
	 * @param aspPath the path to the created file.
	 * @return true when all files will be successfully created, and false otherwise.
	 * @throws Exception when unless one file doesn't exist.
	 */
	private boolean createPredicateForOneAgent(SwarmAgent agent,Path aspPath) throws Exception{
		try(BufferedWriter bufferW=Files.newBufferedWriter(aspPath, StandardOpenOption.APPEND)){
			String strPredicate=null;
			
			bufferW.write(String.format("%% CurrentAgent(AgentName,AgentTypeName). %n"));
			strPredicate=new PredicateCurrentAgent(agent.getName(), agent.getAgentTypeName()).toString();
			bufferW.write(String.format("%s.%n", strPredicate));
			
			bufferW.write(String.format("%% CurrentStation(AgentName,AgetnTypeName,StationName,StationTypeName,IsInStation,HasChoose). %n"));
			strPredicate=new PredicateCurrentStation(agent.getName(), agent.getAgentTypeName(), "nothings", "nothings", false, false).toString();
			bufferW.write(String.format("%s.%n", strPredicate));
			
			strPredicate=null;
			bufferW.flush();
			bufferW.close();
		}catch(Exception e) {
			LOG.error(e.getMessage());
			throw e;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param strategieType
	 * @throws Exception
	 */
	protected void createKReaturesConfigFile(AgStrategie strategieType) throws Exception {
		if (obj == null)
			throw new SwarmException("Null pointer exception");

		Path source = null, target = null;
		Persister persister=new Persister();
		CommandSequenceSerializeImport cycleScript=new CommandSequenceSerializeImport();

		/*
		 * copy _cycle.xml
		 */
		source = Paths.get(KREATURES_SWARM_DEFAULT_CONFIG_DIR.toString())
				.resolve(Paths.get(_KREaturesAgentCycleConfigGFile));

		target = Paths.get(KREATURES_CONFIG_DIR.toString())
				.resolve(String.format("%s%s", obj.getName(), _KREaturesAgentCycleConfigGFile));

		String cyclePath=target.toString(); //TODO must be delete is reflection is resolve
		Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES,REPLACE_EXISTING);
		
		cycleScript.source=target.toFile();

		/*
		 * copy _beliefbase.xml
		 */
		source = Paths.get(KREATURES_SWARM_DEFAULT_CONFIG_DIR.toString())
				.resolve(Paths.get(_KREaturesBeliefsConfigFile));

		target = Paths.get(KREATURES_BELIEFS_CONFIG_DIR.toString())
				.resolve(String.format("%s%s", obj.getName(), _KREaturesBeliefsConfigFile));

		Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES,REPLACE_EXISTING);

		/*
		 * copy _agent.xml
		 */
		if (strategieType == AgStrategie.AGENT_RANDOM_STRATEGIE_FILE) {
			source = Paths.get(KREATURES_SWARM_DEFAULT_CONFIG_DIR.toString())
					.resolve(Paths.get(_KREaturesRandomAgentConfigFile));
			target = Paths.get(KREATURES_AGENTS_CONFIG_DIR.toString())
					.resolve(String.format("%s%s", obj.getName(), _KREaturesRandomAgentConfigFile));
		} else {
			source = Paths.get(KREATURES_SWARM_DEFAULT_CONFIG_DIR.toString())
					.resolve(Paths.get(_KREaturesHeuristicAgentConfigFile));
			target = Paths.get(KREATURES_AGENTS_CONFIG_DIR.toString())
					.resolve(String.format("%s%s", obj.getName(), _KREaturesHeuristicAgentConfigFile));
		}
		createAgentConfigFile(source,target,cyclePath,obj.getName(),obj.getDescription());
		//TODO All the following comments have to be use when the reflection problem will be resolve.
//		AgentConfigReal agentConfigReal=persister.read(AgentConfigReal.class, source.toFile());
//		agentConfigReal.name=obj.getName();
//		agentConfigReal.description=obj.getDescription();
//		agentConfigReal.cylceScript=cycleScript;
//		persister.write(agentConfigReal, target.toFile());
//		agentConfigReal.cylceScript=null;
//		cycleScript=null;
//		Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES,REPLACE_EXISTING);

	}
	/**
	 * Create the agent config file of each KReatures project. 
	 * @param sourceAgentConfigPath the path where the defeault kreatures agent config is located.
	 * @param targetAgentConfigPath the path where the agent config of the load project will be located.
	 * @param cyclePath the path the cycle config file
	 * @param projectName the name of the agents which use this agent config file.
	 * @param description the description of the agents which use this agent config file.
	 * @throws Exception throws when there are some error by opening, writing or reading a file. 
	 */
	
	protected void createAgentConfigFile(Path sourceAgentConfigPath,Path targetAgentConfigPath, String cyclePath,String projectName,String description) throws Exception {
		Files.deleteIfExists(targetAgentConfigPath);
		try(BufferedWriter bufferW=Files.newBufferedWriter(targetAgentConfigPath,StandardOpenOption.CREATE);
				BufferedReader bufferR=Files.newBufferedReader(sourceAgentConfigPath);){
			String line=null;
			while((line=bufferR.readLine())!=null) {
				if(line.contains("<cycle-script")) {
					bufferW.write(String.format("<cycle-script source=\"%s\"/>%n", cyclePath));
				}
//				else if(line.contains("<name>")) {
//					bufferW.write(String.format("<name>%s</name>%n", projectName));
//				}
				else if(line.contains("<description>")) {
					bufferW.write(String.format("<description>%s</description>%n", description));
				}
//				else if(line.contains("<category>")) {
//					bufferW.write(String.format("<category>%s</category>", category));
//				}
				else{
					bufferW.write(String.format("%s%n", line));
				}
			}
			bufferW.flush();
		}catch(Exception ex) {
			throw ex;
		}
		
	}

	protected Collection<AgentInstance> createAgentInstanceConfig(AgStrategie strategie) {
		Collection<AgentInstance> AgentInstanceSet = new LinkedList<AgentInstance>();
		String choice = null;
		if (strategie == AgStrategie.AGENT_HEURISTIC_STRATEGIE_FILE) {
			choice = _KREaturesHeuristicAgentConfigFile;
		} else {
			choice = _KREaturesRandomAgentConfigFile;
		}
		for (SwarmAgent ag : obj.getAllAgents()) {
			AgentInstance agentInstance = new AgentInstance();
			agentInstance.name = ag.getName();
			AgentConfigImport agentConfigImport = new AgentConfigImport();
			agentConfigImport.source = Paths.get(KREATURES_AGENTS_CONFIG_DIR.toString())
					.resolve(String.format("%s%s", obj.getName(), choice)).toFile();
			agentInstance.config = agentConfigImport;
			BeliefbaseConfigImport beliefConfigImport = new BeliefbaseConfigImport();
			
			beliefConfigImport.source = Paths.get(KREATURES_BELIEFS_CONFIG_DIR.toString())
					.resolve(String.format("%s%s", obj.getName(), _KREaturesBeliefsConfigFile)).toFile();
			agentInstance.beliefbaseConfig = beliefConfigImport;
			AgentInstanceSet.add(agentInstance);

		}
		return AgentInstanceSet;
	}

	protected SimulationConfiguration createSimulationConfig(AgStrategie strategie) {

		SimulationConfiguration simulaConfig=new SimulationConfiguration() ;
		// the file's name is the simulation configuration's name.
		simulaConfig.name=obj.getName(); 
		simulaConfig.category=_KReaturesSwarmCategorie;
		simulaConfig.description=obj.getDescription();
		//TODO must be generate after
		simulaConfig.behaviorCls="com.github.kreatures.swarm.basic.SwarmBehavior";

		//agentInstance.
		simulaConfig.agents=(List<AgentInstance>) createAgentInstanceConfig(strategie);

		//simulaConfig.setName(swarmConfig.getName());
		return simulaConfig;
	}
	
	protected void createExamplesDir(AgStrategie strategie) throws Exception{
			Path simScenarioDir=Paths.get(KREATURES_EXAMPLES_DIR.toString()).resolve(obj.getName());			
			if(!simScenarioDir.toFile().exists()){
				simScenarioDir.toFile().mkdir();
			}	
			Persister persister=new Persister();
			File simScenarioConfig=simScenarioDir.resolve(String.format("%s%s",obj.getName(),_KREaturesSimulationConfigSuffixName)).toFile();
			persister.write(createSimulationConfig(strategie), simScenarioConfig);
	}

	
	
	/**
	 * TODO main has to be deleted
	 * 
	 * @param args
	 */
	public static void main(String... args) {
			
		try {
			 

			//SwarmConfigBridge swarm = new SwarmConfigDefault();
			System.out.format("%s%n",
					Paths.get(KREATURES_SWARM_XML_DIR.toString()).resolve("Perspectives.lg.xml").toAbsolutePath());
			new BDIParserUtils(
					Paths.get(KREATURES_SWARM_XML_DIR.toString()).resolve("Perspectives.lg.xml"),AgStrategie.AGENT_RANDOM_STRATEGIE_FILE);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception | Error ex) {
			ex.printStackTrace();
		}
	}
}
