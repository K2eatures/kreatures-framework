package com.github.kreatures.core.operators.parameters;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import javax.management.AttributeNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.KReatures;
import com.github.kreatures.core.KReaturesPaths;
import com.github.kreatures.core.error.ConversionException;
import com.github.kreatures.core.logic.EnvFeaturesBeliefbase;
import com.github.kreatures.core.logic.ScenarioModelBeliefbase;
import com.github.kreatures.core.operators.parameter.GenericOperatorParameter;
import com.github.kreatures.core.operators.parameter.OperatorPluginParameter;
import com.github.kreatures.core.parser.ParseException;
import com.github.kreatures.swarm.Utility;
import com.github.kreatures.swarm.operators.SwarmEvaluationOptionsOperator;

import net.sf.tweety.lp.asp.syntax.Program;
/**
 * This class contains all logic programs. That means: 
 * <li>
 * 		<ul>actuel world beliefbase,</ul>
 * 		<ul>the scenario model beliefbase</ul>
 * 		<ul>and the environment features beliefbase.</ul>
 * </li> 
 * It is use as {@link OperatorPluginParameter} for the operator 
 * {@link SwarmEvaluationOptionsOperator}
 * 
 * @author Cedric Perez Donfack
 * @see OperatorPluginParameter
 */
public class EvaluationParameter extends OperatorPluginParameter {
	/** reference to the logging facility */
	private static Logger LOG = LoggerFactory.getLogger(EvaluationParameter.class);
	private BaseBeliefbase baseBeliefbase;
	
	/**
	 * the environment features logic program
	 */
	private static final EnvFeaturesBeliefbase envFeaturesBB;
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
	 * the scenario model logic program
	 */
	private final ScenarioModelBeliefbase scenarioModelBB;
	{
		String simName=KReatures.getInstance().getActualSimulation().getName();
		scenarioModelBB=ScenarioModelBeliefbase.getInstance(simName);
		
		try {
			scenarioModelBB.parse(Paths.get(KReaturesPaths.KREATURES_SCENARIO_MODELS.toString()).resolve(simName).resolve(String.format("%s.asp", simName)).toString());
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
	 * @return the logic program of the scenario model and the environment features 
	 */
	public Program getScenarioModelAndEnFeaturesBB() {
		return new Program(scenarioModelAndEnFeaturesBB);
	}
	
	/** Default Ctor: Used for dynamic instantiation */
	public EvaluationParameter() {
		
	}
	
	public EvaluationParameter(AgentAbstract caller) {
		super(caller);
		baseBeliefbase=null;
	}
	
	public EvaluationParameter(AgentAbstract caller, BaseBeliefbase baseBeliefbase) {
		super(caller);
		this.baseBeliefbase=baseBeliefbase;
	}
	/**
	 * @return the epistemic state of scenario model 
	 */
	public ScenarioModelBeliefbase getScenarioModelBB() {
		return this.scenarioModelBB;
	}
	/**
	 * @return the epistemic state of environment features 
	 */
	public EnvFeaturesBeliefbase getEnvFeaturesBB() {
		return envFeaturesBB;
	}
	@Override
	public void fromGenericParameter(GenericOperatorParameter param) 
			throws ConversionException, AttributeNotFoundException {
		super.fromGenericParameter(param);
		
		Object obj=param.getParameter("basebeliefbase");
		if(obj!=null) {
			if(!(obj instanceof BaseBeliefbase)) {
				throw conversionException("basebeliefbase",BaseBeliefbase.class);
			}
			this.baseBeliefbase=(BaseBeliefbase)obj;
		}
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if(otherObject==null || !(otherObject instanceof EvaluationParameter))	
			return false;
		
		EvaluationParameter other=(EvaluationParameter)otherObject;
		
		if(other.baseBeliefbase!=null) {
			if(!other.baseBeliefbase.equals(this.baseBeliefbase))
				return false;
		}else if(this.baseBeliefbase!=null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return Utility.computeHashCode(super.hashCode(),baseBeliefbase);
	}

	public BaseBeliefbase getBaseBeliefbase() {
		return baseBeliefbase;
	}
	
	@Override
	public String toString() {
		return (baseBeliefbase==null?"no nothings to evaluate ":baseBeliefbase.toString());
	}
}
