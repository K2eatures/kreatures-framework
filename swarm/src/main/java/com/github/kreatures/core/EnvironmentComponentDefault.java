package com.github.kreatures.core;

import static com.github.kreatures.core.KReaturesPaths.KREATURES_SCENARIO_MODELS;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.sf.tweety.lp.asp.parser.ASPParser;
import net.sf.tweety.lp.asp.parser.InstantiateVisitor;
import net.sf.tweety.lp.asp.parser.ParseException;
import net.sf.tweety.lp.asp.syntax.Program;

public final class EnvironmentComponentDefault implements EnvironmentComponent {
	
	/**
	 * @return name of the simulation to which this resource belong.
	 */
	private final String projectName;
	
	/**
	 * Content all the rules about a environment features.
	 * This is stored in a object of type {@link net.sf.tweety.lp.asp.syntax.Program} 
	 */
	private static Program programEnvFeatures=null;
	/**
	 * Content all the rules about a scenario model.
	 * This is stored in a object of type {@link net.sf.tweety.lp.asp.syntax.Program}
	 */
	private Program programScenarioModel=null;
	
	/**
	 * Create a environment component object for each simulation instance.
	 */
	public EnvironmentComponentDefault(String projectName) {
		this.projectName=projectName;
		if(programEnvFeatures==null)
			programEnvFeatures =EnvironmentComponent.getEnvironmentFeatures();
	}
	/**
	 * @return name of the simulation to which this resource belong.
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Load the environment features and converts it as a set of rules.
	 * A rule's object comes from tweety library.
	 * @return a object of type {@link net.sf.tweety.lp.asp.syntax.Program}
	 */
	protected static Program getEnvironmentFeatures() {
		return programEnvFeatures;
	}

	@Override
	public void setScenariomodell(String simulationName) {
		
		try(BufferedReader buffer=Files.newBufferedReader(Paths.get(KREATURES_SCENARIO_MODELS.toString()).resolve(simulationName).resolve(simulationName+".asp"));) {
			
			ASPParser parser = new ASPParser(buffer);
			InstantiateVisitor visitor = new InstantiateVisitor();
			programScenarioModel=visitor.visit(parser.Program(), null);
		
		}catch(ParseException parseEx) {
			LOG.error(parseEx.getMessage());
			parseEx.printStackTrace();			
		}catch(IOException ioEx) {
			LOG.error(ioEx.getMessage());
			ioEx.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return String.format("Environment features rules= %s \n\n Scenario modell rules= %s\n", programEnvFeatures.toString(),(programScenarioModel==null?"":programScenarioModel.toString()));
	}

}
