/**
 * 
 */
package com.github.kreatures.swarm.serialize;

import java.nio.file.Path;


/**
 * This is a bridge that will be you to create a SwarmConfigRead  object
 * 
 * @author donfack
 *
 */
public abstract class SwarmConfigBridge {
	
    
	

	/**
	 * Here, a instance of the SwarmConfig's classes will be create.
	 * 
	 * @return all SwarmConfiguration of current simulation. This comes from the
	 *         Abstract_Swarm file and are using to initialize the environment.
	 */

	abstract public SwarmConfig createSwarmConfig(Path path);
	
}
