package com.github.kreatures.core.serialize;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.core.Persister;

import static com.github.kreatures.core.KReaturesConst._KREaturesAgentCycleConfigGFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesRandomAgentConfigFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesHeuristicAgentConfigFile;
import static com.github.kreatures.core.KReaturesConst._KREaturesBeliefsConfigFile;
import static com.github.kreatures.core.KReaturesConst._KReaturesSwarmCategorie;
import static com.github.kreatures.core.KReaturesConst._KREaturesSimulationConfigSuffixName;
import static com.github.kreatures.core.KReaturesConst._KREaturesSwarmStrategieFile;

import static com.github.kreatures.core.KReaturesPaths.KREATURES_AGENTS_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_BELIEFS_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_EXAMPLES_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_DEFAULT_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_XML_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SCENARIO_MODELS;

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

/**
 * This Class is used to parse the information from the {@link BeliefParseOfSwarm}'s object to a file. This file contents a scenario-model and initial knowledge of all agents.
 * Each agent has to load its initial knowledge into its belief-base.
 *  
 * @author Cedric Perez Donfack
 * 
 */
public final class BDIParserUtils implements BDIParser {

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
		Path tmpAspPathWorldBelief=createAgentBeliefs();
		createScenarioModel();
		createKReaturesConfigFile(strategie);
		createExamplesDir(strategie);
		createAgentAsp(tmpAspPathWorldBelief);
		Files.deleteIfExists(tmpAspPathWorldBelief);
		
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
	protected Path createAgentBeliefs() throws Exception {
		obj = getBeliefParseOfSwarmInstance();
		
		//Path strategiePath=Paths.get(KREATURES_SWARM_CONFIG_DIR.toString()).resolve(_KREaturesSwarmStrategieFile);
		Path tmpPathWorldBelief=Files.createTempFile("swarm",".asp");
		System.out.println(tmpPathWorldBelief.toAbsolutePath());
		try (BufferedWriter buffer = Files.newBufferedWriter(tmpPathWorldBelief)) {

			buffer.write(String.format("%s%n", obj.getTimeUnit()));

			buffer.write(String.format("%s%n", SwarmAgent.getDescriptions()));
			for (SwarmAgent elt : obj.getAllAgents()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmStation.getDescriptions()));
			for (SwarmStation elt : obj.getAllStations()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmVisitEdge.getDescriptions()));
			for (SwarmVisitEdge elt : obj.getAllVisitEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			
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

//			try (BufferedReader strategieBuffer = Files.newBufferedReader(strategiePath)) {
//				String line = strategieBuffer.readLine();
//				;
//				while (line != null) {
//					buffer.write(String.format("%s%n", line));
//					line = strategieBuffer.readLine();
//				}
//			} catch (IOException ioe) {
//				throw ioe;
//			}

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
			directoryPathScenarioModell=null;
			pathScenarioModell=null;
		}
	}
	
	/**
	 * For each agent, this method creates a file with its initial belief.   
	 * @param aspPath the path to the created file.
	 * @return true when all files will be successfully created, and false otherwise.
	 * @throws Exception when unless one file doesn't exist.
	 */	
	protected boolean createAgentAsp(Path aspPath) throws Exception{
		if(aspPath==null)
			return false;
		/**
		 * Create agents beliefs.
		 */
		for(SwarmAgent agent:obj.getAllAgents()){
			Files.copy(aspPath, Paths.get(KREATURES_EXAMPLES_DIR.toString()).resolve(obj.getName()).resolve(String.format("%s.asp",agent.getName())));
		}
		
		return true;
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
		
		AgentConfigReal agentConfigReal=persister.read(AgentConfigReal.class, source.toFile());
		agentConfigReal.name=obj.getName();
		agentConfigReal.description=obj.getDescription();
		agentConfigReal.cylceScript=cycleScript;
		persister.write(agentConfigReal, target.toFile());
		agentConfigReal.cylceScript=null;
		cycleScript=null;
//		Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES,REPLACE_EXISTING);

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
