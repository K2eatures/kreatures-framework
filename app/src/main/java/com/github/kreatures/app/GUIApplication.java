package com.github.kreatures.app;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.core.serialize.CreateKReaturesXMLFileDefault;
import com.github.kreatures.gui.KReaturesGUIDataStorage;
import com.github.kreatures.gui.KReaturesWindow;
import com.github.kreatures.core.serialize.SimulationConfiguration;
import com.github.kreatures.core.serialize.SwarmLoaderDefault;
import com.github.kreatures.core.util.Utility;
public class GUIApplication {
	/** logging facility */
	private static Logger LOG = LoggerFactory.getLogger(KReaturesWindow.class);
	
	public static void main(String[] args) throws Exception {	
		
		/**
		 * @author donfack
		 * Here we have to create all abstract swarm config file 
		 * in order that there can be laoded after into KReatures.
		 */
		//All needed paths will be created.
		KReaturesPaths.iniFolder();
		//All Swarm Scenarien will be creatured.
		SwarmLoaderDefault.getInstance().init();
		
		SwarmLoaderDefault.freeInstance();

		
		KReaturesWindow.get().init();
		
		// load simulation per commandline.
		for(int k=0; k<args.length; ++k) {
			if(args[k].equalsIgnoreCase("-presentation")) {
				Utility.presentationMode = true;
			}
			
			String [] ary = args[k].split("=");
			if(ary.length == 2 && ary[0].equalsIgnoreCase("simulation")) {
				SimulationConfiguration config = SimulationConfiguration.loadXml(new File(ary[1]));
				KReaturesGUIDataStorage.get().getSimulationControl().setSimulation(config);
			}
		}
	}

}
