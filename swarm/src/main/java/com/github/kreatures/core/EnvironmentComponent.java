package com.github.kreatures.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.kreatures.core.KReaturesPaths.KREATURES_ENV_FEATURES;

import net.sf.tweety.lp.asp.parser.ASPParser;
import net.sf.tweety.lp.asp.parser.InstantiateVisitor;
import net.sf.tweety.lp.asp.parser.ParseException;
import net.sf.tweety.lp.asp.syntax.Program;

public interface EnvironmentComponent {
	/** logging facility */
	static Logger LOG = LoggerFactory.getLogger(EnvironmentComponent.class);
	
	/**
	 * Load the environment features and converts it as a set of rules.
	 * A rule's object comes from tweety library.
	 * @return a object of type {@link net.sf.tweety.lp.asp.syntax.Program}
	 */
	static Program getEnvironmentFeatures() {
		Program program=null;
		try(BufferedReader buffer=Files.newBufferedReader(Paths.get(KREATURES_ENV_FEATURES.toString()).resolve("envfeatures.asp"));) {
			
			ASPParser parser = new ASPParser(buffer);
			InstantiateVisitor visitor = new InstantiateVisitor();
			program=visitor.visit(parser.Program(), null);
		
		}catch(ParseException parseEx) {
			LOG.error(parseEx.getMessage());
			parseEx.printStackTrace();			
		}catch(IOException ioEx) {
			LOG.error(ioEx.getMessage());
			ioEx.printStackTrace();
		}
		return program;
	}
	/**
	 * Load the scenario model and converts it as a set of rules.
	 * A rule's object comes from tweety library.
	 * @param simulationName name of the current simulation in order to load the correct scenario model.
	 */
	void setScenariomodell(String simulationName);
}
