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
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_DEFAULT_CONFIG_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_XML_DIR;


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
import com.github.kreatures.swarm.serialize.SwarmConfigBridge;
import com.github.kreatures.swarm.serialize.SwarmConfigDefault;
import com.github.kreatures.swarm.serialize.SwarmConfigRead;

/**
 * 
 * @author donfack
 *
 */
public final class BDIParserUtils implements BDIParser {

	private XmlToBeliefBase obj;
	/**
	 * The path where the scenario file is.
	 */
	private Path xmlPath;
	// private Path kreaturesAgentConfig;

	public BDIParserUtils(Path path,AgStrategie strategie) throws Exception {
		obj = new BeliefParseOfSwarm(path);
		xmlPath = path;
		Path tmpAspPath=createAgentBeliefs();
		createKReaturesConfigFile(strategie);
		createExamplesDir(strategie);
		createAgentAsp(tmpAspPath);
		Files.deleteIfExists(tmpAspPath);
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

	protected Path createAgentBeliefs() throws Exception {
		obj = getBeliefParseOfSwarmInstance();
		
		Path strategiePath=Paths.get(KREATURES_SWARM_CONFIG_DIR.toString()).resolve(_KREaturesSwarmStrategieFile);
		Path tmpPath=Files.createTempFile("swarm",".asp");
		System.out.println(tmpPath.toAbsolutePath());
		try (BufferedWriter buffer = Files.newBufferedWriter(tmpPath)) {

			buffer.write(String.format("%s%n", obj.getTimeUnit()));

			buffer.write(String.format("%s%n", SwarmAgentType.getDescriptions()));
			for (SwarmAgentType elt : obj.getAllAgentType()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			buffer.write(String.format("%s%n", SwarmAgent.getDescriptions()));
			for (SwarmAgent elt : obj.getAllAgents()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmStationType.getDescriptions()));
			for (SwarmStationType elt : obj.getAllStationType()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}

			buffer.write(String.format("%s%n", SwarmStation.getDescriptions()));
			for (SwarmStation elt : obj.getAllStations()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			buffer.write(String.format("%s%n", SwarmPlaceEdge.getDescriptions()));
			for (SwarmPlaceEdge elt : obj.getAllPlaceEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			buffer.write(String.format("%s%n", SwarmVisitEdge.getDescriptions()));
			for (SwarmVisitEdge elt : obj.getAllVisitEdge()) {
				buffer.write(String.format("%s%n", elt.toString()));
			}
			buffer.write(String.format("%s%n", SwarmTimeEdge.getDescriptions()));
			for (SwarmTimeEdge elt : obj.getAllTimeEdge()) {
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

			try (BufferedReader strategieBuffer = Files.newBufferedReader(strategiePath)) {
				String line = strategieBuffer.readLine();
				;
				while (line != null) {
					buffer.write(String.format("%s%n", line));
					line = strategieBuffer.readLine();
				}
			} catch (IOException ioe) {
				throw ioe;
			}

			buffer.flush();

		} catch (IOException ex) {
			throw ex;
		}
		
		return tmpPath;

	}

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

//		BeliefbaseConfigReal beliefConfigReal=persister.read(BeliefbaseConfigReal.class, source.toFile());
//		beliefConfigReal.name=obj.getName();
//		beliefConfigReal.description=obj.getDescription();
//		beliefConfigReal.cylceScript=cycleScript;
//		persister.write(AgentConfigReal.class, target.toFile());
				
		
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
