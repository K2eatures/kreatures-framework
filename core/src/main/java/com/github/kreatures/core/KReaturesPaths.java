package com.github.kreatures.core;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.math3.analysis.function.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This behaves all fixed paths
 * 
 * @author donfack
 *
 */
public enum KReaturesPaths {
	
	KREATURES_ICONS_DIR, KREATURES_EXAMPLES_DIR, KREATURES_CONFIG_DIR, 
	KREATURES_AGENTS_CONFIG_DIR, KREATURES_BELIEFS_CONFIG_DIR,
	/**
	 * path where all configurations files of swarm are. For example
	 * strategie.asp
	 */
	KREATURES_SWARM_CONFIG_DIR, KREATURES_SWARM_DEFAULT_CONFIG_DIR,
	/**
	 * 
	 */
	KREATURES_SWARM_XML_DIR, SWARM_GUI_DIR,
	/**
	 * path where all the environment features are saved.
	 */
	KREATURES_ENV_FEATURES,
	/**
	 * path where all the scenario-models are saved.
	 */
	KREATURES_SCENARIO_MODELS,
	/**
	 * TODO Only for test and must be deleted after. use
	 * KREATURES_SWARM_CONFIG_DIR instead.
	 */
	SWARM_STRATEGIE_DIR,
	/**
	 * TODO Only for test and must be deleted after.
	 */
	SWARM_XML_DIR;

	/** reference to the logging facility */
	private static Logger LOG = LoggerFactory.getLogger(KReaturesPaths.class);
	
	public  static void iniFolder(){
		//Files.is
		try{
			Files.createDirectories(Paths.get(KREATURES_EXAMPLES_DIR.toString()));
			Files.createDirectories(Paths.get(KREATURES_AGENTS_CONFIG_DIR.toString()));
			Files.createDirectories(Paths.get(KREATURES_BELIEFS_CONFIG_DIR.toString()));
			Files.createDirectories(Paths.get(KREATURES_SWARM_DEFAULT_CONFIG_DIR.toString()));
			Files.createDirectories(Paths.get(KREATURES_SWARM_XML_DIR.toString()));
			Files.createDirectories(Paths.get(KREATURES_ENV_FEATURES.toString()));
			Files.createDirectories(Paths.get(KREATURES_SCENARIO_MODELS.toString()));
		}catch(Exception e){
			LOG.error(e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	@Override
	public String toString() {
		
		switch(this) {
			default: return null;//throw new NullPointerException("USER DEFINED: File directory no found exception");
			case KREATURES_ICONS_DIR: return "/com/github/kreatures/gui/icons";
			case KREATURES_EXAMPLES_DIR: return "examples";
			case KREATURES_CONFIG_DIR: return "config";
			case KREATURES_AGENTS_CONFIG_DIR: return "config/agents";
			case KREATURES_BELIEFS_CONFIG_DIR: return "config/beliefbases";
			case KREATURES_SWARM_CONFIG_DIR: return "config/swarm";
			case SWARM_GUI_DIR: return "abstractSwarm";
			case KREATURES_SWARM_DEFAULT_CONFIG_DIR: return "config/swarm/kreatures_default";
			case KREATURES_SWARM_XML_DIR: return "abstractSwarm/Examples";
			case KREATURES_ENV_FEATURES: return "envfeatures";
			case KREATURES_SCENARIO_MODELS: return "scenariomodels"; 
			/**
			 * 
			 * TODO Only for test and must be deleted after.
			 */
			case SWARM_XML_DIR: return "config";
			case SWARM_STRATEGIE_DIR: return "";
		}
	}

}
