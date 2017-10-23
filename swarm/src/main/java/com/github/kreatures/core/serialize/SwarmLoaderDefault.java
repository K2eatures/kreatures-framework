/**
 * 
 */
package com.github.kreatures.core.serialize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.KReatures;
import com.github.kreatures.gui.controller.ShowResult;

import static com.github.kreatures.core.KReaturesPaths.KREATURES_SWARM_XML_DIR;
import static com.github.kreatures.core.KReaturesPaths.KREATURES_EXAMPLES_DIR;
import static com.github.kreatures.core.KReaturesConst._KReaturesSwarmXmlPatternFile;
import static com.github.kreatures.core.KReaturesConst.AgStrategie.AGENT_RANDOM_STRATEGIE_FILE;
//import static com.github.kreatures.core.KReaturesConst.AgStrategie.AGENT_HEURISTIC_STRATEGIE_FILE;

/**
 * @author Cedric Perez Donfack
 *
 */
public class SwarmLoaderDefault implements SwarmLoader {
	/** reference to the logging facility */
	private static Logger LOG = LoggerFactory.getLogger(SwarmLoaderDefault.class);

	private static SwarmLoader instance = new SwarmLoaderDefault();

	public static SwarmLoader getInstance() {
		if(instance==null)
			instance=new SwarmLoaderDefault();
		return instance;
	}
	
	public static void freeInstance() {
		instance=null;
	}

	/**
	 * 
	 */
	private SwarmLoaderDefault() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.core.serialize.SwarmLoader#init()
	 */
	@Override
	public boolean init() {
		try {
			Stream<Path> allXmlPath = Files.list(Paths.get(KREATURES_SWARM_XML_DIR.toString()));
			allXmlPath.filter(path -> Pattern.matches(_KReaturesSwarmXmlPatternFile, path.getFileName().toString()))
					.filter(path -> {
						Path swarmExamplePath = Paths.get(KREATURES_EXAMPLES_DIR.toString())
								.resolve(String.format("%sLG", path.getFileName().toString().split("[.]")[0]));
						if (!Files.isDirectory(swarmExamplePath))
							return true;
						return false;
					}).forEach(path -> {
						try {
							new BDIParserUtils(path, AGENT_RANDOM_STRATEGIE_FILE);
						} catch (Exception e) {
							LOG.error(e.getMessage());
							e.printStackTrace();
						}
					});

		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.core.serialize.SwarmLoader#loading()
	 */
	@Override
	public boolean loading() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.core.serialize.SwarmLoader#unloading()
	 */
	@Override
	public boolean unloading() {
		
		new ShowResult(KReatures.getInstance().getActualSimulation().getName()).show();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.kreatures.core.serialize.SwarmLoader#reloading()
	 */
	@Override
	public boolean reloading() {
		// TODO Auto-generated method stub
		return false;
	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//		//new SwarmLoaderDefault().init();
//		Collection<String> t=new ArrayList<>(); 
//		t.add("oui");
//		t.add("non");
//		t.add("oui");
//		t.add("qui");
//		t.add("oui");
//		System.out.println(t);
//		while(t.remove("oui")) {
//			
//		}
//		
//		System.out.println(t);
//
//	}
}