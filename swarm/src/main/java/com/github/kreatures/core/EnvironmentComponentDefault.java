package com.github.kreatures.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.github.kreatures.core.logic.EnvFeaturesBeliefbase;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.ScenarioModelBeliefbase;
import com.github.kreatures.core.operators.parameters.BaseReasonerParameter;
import com.github.kreatures.core.parser.ParseException;

import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.beliefbase.SwarmAspReasoner;
import com.github.kreatures.swarm.optimisation.StationNode;
import com.github.kreatures.swarm.predicates.SwarmPredicate;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;


import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.lp.asp.syntax.Program;
/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public final class EnvironmentComponentDefault implements EnvironmentComponent {
	
	/**
	 * the environment features logic program
	 * Content all the rules about a environment features.
	 * This is stored in a object of type {@link EnvFeaturesBeliefbase} 
	 */
	private static final EnvFeaturesBeliefbase envFeaturesBB;
	
	/**
	 * Load the environment features and converts it as a set of rules.
	 * A rule's object comes from tweety library.
	 * @return a object of type {@link net.sf.tweety.lp.asp.syntax.Program}
	 */
	static {
		envFeaturesBB=new EnvFeaturesBeliefbase();
		try {
			envFeaturesBB.parse(Paths.get(KReaturesPaths.KREATURES_ENV_FEATURES.toString()).resolve("envfeatures.asp").toString());
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @return name of the simulation to which this resource belong.
	 */
	private final String projectName;
	
	/**
	 * load the set of all the shortest paths.
	 */
	private final Set<StationNode> allShortestPaths;
	{
		projectName=KReatures.getInstance().getActualSimulation().getName();
		Path path=Paths.get(KReaturesPaths.KREATURES_SCENARIO_MODELS.toString()).resolve(projectName).resolve(String.format("%s.opt", projectName));
		Optional<Set<StationNode>> optAllShortestPaths=Optional.empty();
		try {
			optAllShortestPaths=Optional.ofNullable(Files.lines(path).skip(1).map(StationNode::parseToStationNode)
					.collect(HashSet::new,HashSet::add,HashSet::addAll));
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}finally {
			allShortestPaths=optAllShortestPaths.orElseGet(HashSet::new);
		}

	}	
	
	/**
	 * the scenario model logic program
	 * Content all the rules about a scenario model.
	 * This is stored in a object of type {@link EnvFeaturesBeliefbase}
	 */
	private final ScenarioModelBeliefbase scenarioModelBB;
	/**
	 * Load the scenario modell and converts it as a set of rules.
	 * A rule's object comes from tweety library.
	 * @return a object of type {@link ScenarioModelBeliefbase}
	 */
	{
		//projectName=KReatures.getInstance().getActualSimulation().getName();
		scenarioModelBB=new ScenarioModelBeliefbase();
		
		try {
			scenarioModelBB.parse(Paths.get(KReaturesPaths.KREATURES_SCENARIO_MODELS.toString()).resolve(projectName).resolve(String.format("%s.asp", projectName)).toString());
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}	
	
	/**
	 * This field contains scenario model logic program and environment features logic program. 
	 */
	private final Program scenarioModelAndEnFeaturesBB=new Program();
	{
		scenarioModelAndEnFeaturesBB.add(envFeaturesBB.getProgram());
		scenarioModelAndEnFeaturesBB.add(scenarioModelBB.getProgram());
	}
	
	/**
	 * Create a environment component object for each simulation instance.
	 */
	public EnvironmentComponentDefault() {}
	
	@Override
	public EnvFeaturesBeliefbase getEnvironmentFeatures() {
		return envFeaturesBB;
	}

	@Override
	public ScenarioModelBeliefbase getScenariomodell() {
		return scenarioModelBB;
	}
	
	/**
	 * @return the allShortestPaths
	 */
	public Set<StationNode> getAllShortestPaths() {
		return allShortestPaths;
	}

	@Override
	public String toString() {
		return String.format("Environment features rules= %s \n\n Scenario modell rules= %s\n", envFeaturesBB.toString(),(scenarioModelBB==null?"":scenarioModelBB.toString()));
	}
	
	/**
	 * @return name of the simulation to which this resource belong.
	 */
	@Override
	public String getSimulationName() {
		return this.projectName;
	}
	
	
	/**
	 * @return the logic program of the scenario model and the environment features 
	 */
	@Override
	public Program getScenarioModelAndEnFeaturesBB() {
		return new Program(scenarioModelAndEnFeaturesBB);
	}
	
	/**
	 * two {@link EnvironmentComponentDefault} are equal if and only if 
	 * there have the same simulation name.
	 */
	@Override
	public boolean equals(Object other) {
		if(other==null || !(other instanceof EnvironmentComponentDefault))
			return false;
		
		EnvironmentComponentDefault envComponent=(EnvironmentComponentDefault)other;
		
		return projectName.equals(envComponent.projectName);
	}
	
	@Override
	public int hashCode() {
		return Utility.computeHashCode(projectName.hashCode());
	}

	@Override
	public Set<SwarmPredicate> askEnvironment(FolBeliefbase bb, String... query) {
		
		FolBeliefbase folBB=new FolBeliefbase();
		folBB.setProgram(new Program());
		folBB.getProgram().add(scenarioModelAndEnFeaturesBB);
		folBB.getProgram().add(bb.getProgram());
//		LOG.info(folBB.toString());
		BaseReasonerParameter brParams=new BaseReasonerParameter(folBB,query);
		SwarmAspReasoner reasonner;
		try {
			reasonner = new SwarmAspReasoner();
			Set<FolFormula> result =reasonner.process(brParams).first;
			return TransformPredicates.getSetPredicat(result);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		=folBB.infere(brParams);
		
		return null;
	}

}