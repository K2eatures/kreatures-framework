package com.github.kreatures.swarm.serialize.transform;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.swarm.components.BeliefParseOfSwarm;
import com.github.kreatures.swarm.components.SwarmAgentType;
import com.github.kreatures.swarm.components.SwarmStationType;
import com.github.kreatures.swarm.serialize.SwarmConfig;
import com.github.kreatures.swarm.serialize.SwarmConfigBridge;
import com.github.kreatures.swarm.serialize.SwarmConfigDefault;


/**
 * 
 * @author donfack
 *
 */
public final class BDIParserUtils implements BDIParser {
	
	private static BDIParserUtils instance;

	private BDIParserUtils() {
		// TODO Auto-generated constructor stub
	}

	public static BDIParser getInstance(){
		if(instance==null)
			return new BDIParserUtils();
		
		return instance;
	}
	
	public void createAgentBeliefs(Path path)throws Exception{
		BeliefParseOfSwarm obj=new BeliefParseOfSwarm(path);
		BufferedWriter buffer=Files.newBufferedWriter(Paths.get("test.asp"));
		for(SwarmAgentType agT:obj.getAllAgentType()){
			buffer.write(agT.toString());
		}
		
		for(SwarmStationType stT:obj.getAllStationType()){
			buffer.write(stT.toString());
		}
	}
	/**
	 * TODO  main has to be deleted
	 * @param args
	 */
	public static void main(String... args){
		BDIParserUtils test=(BDIParserUtils) getInstance();
		
		SwarmConfigBridge swarm=new SwarmConfigDefault();
		System.out.format("%s%n",Paths.get(KReaturesPaths.SWARM_XML_DIR.toString()).resolve("PerspektivenLg.xml").toAbsolutePath());
		
		try {
			test.createAgentBeliefs(Paths.get(KReaturesPaths.SWARM_XML_DIR.toString()).resolve("PerspektivenLg.xml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
