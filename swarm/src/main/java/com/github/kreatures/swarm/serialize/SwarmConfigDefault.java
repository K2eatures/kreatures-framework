/**
 * 
 */
package com.github.kreatures.swarm.serialize;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.swarm.exceptions.SwarmException;

/**
 * @author donfack
 *
 */
public class SwarmConfigDefault extends SwarmConfigBridge {
	private static final Logger LOG = LoggerFactory.getLogger(SwarmException.class);
	
	/**
	 * 
	 */
	public SwarmConfigDefault() {

	}

	/**
	 * @see com.github.kreatures.swarm.serialize.SwarmConfigBridge#createDefaultSwarmConfig(java.nio.file.Path)
	 * @param path where the file's scenario is.
	 */
	@Override
	public SwarmConfig createSwarmConfig(Path path) {
		{
			File file =path.toFile(); 
			Persister persister = new Persister();
			SwarmConfig scenario=null;
			try {
				persister.write(file, System.out);
				scenario=persister.read(SwarmConfigRead.class, file);
				
			} catch (Exception e) {
				LOG.error("The Abstract_Swarm's file doesn't exist. Check the giving directory.");
				e.printStackTrace();
			}finally{
				persister=null;
			}
			return scenario;
		}
	}

	/**
	 * TODO  main has to be deleted
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		SwarmConfigBridge swarm=new SwarmConfigDefault();
		System.out.format("%s%n",Paths.get(KReaturesPaths.SWARM_XML_DIR.toString()).resolve("PerspektivenLg.xml").toAbsolutePath());
		SwarmConfig swarmDefault= swarm.createSwarmConfig(Paths.get(KReaturesPaths.SWARM_XML_DIR.toString()).resolve("PerspektivenLg.xml"));
		System.out.format("%nPerception Name: %s%n",swarmDefault.getListPerspective().get(0).nameSwarmPerspective);
	}
}
