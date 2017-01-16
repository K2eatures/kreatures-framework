package com.github.kreatures.core;

/**
 * This behaves all fixed paths
 * 
 * @author donfack
 *
 */
public enum KReaturesPaths {

	KREATURES_ICONS_DIR, KREATURES_EXAMPLES_DIR, KREATURES_CONFIG_DIR, KREATURES_AGENTS_CONFIG_DIR, KREATURES_BELIEFS_CONFIG_DIR,
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
	 * TODO Only for test and must be deleted after. use
	 * KREATURES_SWARM_CONFIG_DIR instead.
	 */
	SWARM_STRATEGIE_DIR,
	/**
	 * TODO Only for test and must be deleted after.
	 */
	SWARM_XML_DIR;

	@Override
	public String toString() {

		if (name().equals("KREATURES_ICONS_DIR"))
			return "/com/github/kreatures/gui/icons";

		if (name().equals("KREATURES_EXAMPLES_DIR"))
			return "examples";

		if (name().equals("KREATURES_CONFIG_DIR"))
			return "config";

		if (name().equals("KREATURES_AGENTS_CONFIG_DIR"))
			return "config/agents";

		if (name().equals("KREATURES_BELIEFS_CONFIG_DIR"))
			return "config/beliefbases";

		if (name().equals("KREATURES_SWARM_CONFIG_DIR"))
			return "config/swarm";

		if (name().equals("SWARM_GUI_DIR"))
			return "abstractSwarm/";

		if (name().equals("KREATURES_SWARM_DEFAULT_CONFIG_DIR"))
			return "config/swarm/kreatures_default";

		if (name().equals("KREATURES_SWARM_XML_DIR"))
			return "abstractSwarm/Examples";
		/**
		 * 
		 * TODO Only for test and must be deleted after.
		 */
		if (name().equals("SWARM_XML_DIR"))
			return "config";
		
		if (name().equals("SWARM_STRATEGIE_DIR"))
			return "";

		return null;
	}

}
