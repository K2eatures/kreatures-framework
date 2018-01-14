/**
 * 
 */
package com.github.kreatures.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.error.AgentInstantiationException;
import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.ControllerBeliefbaseForGUI;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.WorldBeliefbaseForGUI;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.operators.BaseBeliefsUpdateOperator;
import com.github.kreatures.core.operators.BaseUpdateBeliefsOperator;
import com.github.kreatures.core.operators.OperatorCallWrapper;
import com.github.kreatures.core.operators.parameter.EvaluateParameter;
import com.github.kreatures.core.operators.parameters.PerceptionParameter;
import com.github.kreatures.core.parser.ParseException;
import com.github.kreatures.core.reflection.Context;
import com.github.kreatures.core.reflection.ContextFactory;
import com.github.kreatures.core.serialize.AgentInstance;
import com.github.kreatures.swarm.basic.SwarmInformation;
import com.github.kreatures.swarm.beliefbase.SwarmBeliefsUpdateOperator;
import com.github.kreatures.swarm.comm.SwarmInform;

import net.sf.tweety.commons.Formula;
import net.sf.tweety.lp.asp.syntax.Program;

/**
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class NewAgent extends AgentAbstract implements SwarmAgent{
	/** reference to the logback logger instance */
	private static Logger LOG = LoggerFactory.getLogger(NewAgent.class);
	
	/**
	 * reference to information which belong to the world beliefbase
	 */
	private BaseBeliefbase worldBB=new  WorldBeliefbaseForGUI();
	/**
	 * reference to information which belong to the controller beliefbase
	 */
	private BaseBeliefbase controllerBB=new  ControllerBeliefbaseForGUI();
	
	
	
	private SwarmInformation informations= new SwarmInformation();
	
	/**
	 * @param name
	 * @param env
	 */
	public NewAgent(String name, KReaturesEnvironment env) {
		super(name, env);
	}
	@Override
	protected void parseBeliefbases(AgentInstance ai, File workingDirectory)
			throws AgentInstantiationException {
		String errorOutput = null;

		String workingPath = workingDirectory.getAbsolutePath() + "/";
		
		try {
			// parse the content of the world belief base:
			BaseBeliefbase world = getBeliefs().getWorldKnowledge();
			File worldBBFile = new File(workingPath + ai.getBeliefbaseName() + "_world." + world.getFileEnding());
			File controllerBBFile = new File(workingPath + ai.getBeliefbaseName() + "_controller." + world.getFileEnding());
			if (worldBBFile.exists() & controllerBBFile.exists() ) {
				BufferedReader bufferReader=new BufferedReader(new FileReader(worldBBFile));
				worldBB.parse(bufferReader);
				worldBB.setParent(this.id);
				bufferReader=new BufferedReader(new FileReader(controllerBBFile));
				controllerBB.parse(bufferReader);
				controllerBB.setParent(this.id);
				Program bbProgram=((AspBeliefbase)world).getProgram();
				bbProgram.add(((AspBeliefbase)worldBB).getProgram());
				bbProgram.add(((AspBeliefbase)controllerBB).getProgram());
				
			} else {
				LOG.warn("No world belief base file for '{}'.", ai.getName());
			}
			// parse the content of every view belief base:
			Map<String, BaseBeliefbase> views = getBeliefs().getViewKnowledge();
			for (Entry<String, BaseBeliefbase> ent : views.entrySet()) {
				BaseBeliefbase actView = ent.getValue();
				File viewFile = new File(workingPath
						+ ai.getBeliefbaseName() + "_" + ent.getKey() + "."
						+ actView.getFileEnding());
				if (viewFile.exists()) {
					actView.parse(new BufferedReader(new FileReader(viewFile)));
				} else {
					LOG.warn("No belief base file for view of '{}'->'{}'.",
							ai.getName(), ent.getKey());
				}
			}
		} catch (FileNotFoundException e) {
			errorOutput = "Cannot create agent '" + getName()
					+ "' file not found occured: " + e.getMessage();
			e.printStackTrace();
		} catch (IOException ex) {
			errorOutput = "Cannot create agent '" + getName() + "' IO-Error: "
					+ ex.getMessage();
			ex.printStackTrace();
		} catch (ParseException e) {
			errorOutput = "Cannot create agent '" + getName()
					+ "' parsing error occured: " + e.getMessage();
			e.printStackTrace();
		} finally {
			if (errorOutput != null) {
				throw new AgentInstantiationException(errorOutput);
			}
		}
	}
	
	/**
	 * The environment and scenario model must be created only one time.
	 * This do the check.
	 */
	private static boolean envFeanturesReport=false;
	
	/**
	 * Helper method: reports the created belief bases and components to the
	 * report-system.
	 */
	@Override
	protected void reportCreation() {
		report("Agent: '" + getName() + "' created.");

		Beliefs b = getBeliefs();

		if(!envFeanturesReport) {
			envFeanturesReport=true;
		
			report("Environment features of '" + this.getName() + "' created.",
				KReaturesConst._EnvFeatures);
		
			report("Scenario model of '" + this.getName() + "' created.",
				KReaturesConst._ScenarioModel);
		}else {
			report("Environment features of '" + this.getName() + "' created.");
			
			report("Scenario model of '" + this.getName() + "' created.");
		}
		
		report("World beliefbase of '" + this.getName() + "' created.",
				this.worldBB);
		
		report("Controller beliefbase of '" + this.getName() + "' created.",
				this.controllerBB);
		
		report("Beliefbase of '" + this.getName() + "' built.",
				b.getWorldKnowledge());
		
//		Map<String, BaseBeliefbase> views = b.getViewKnowledge();
//		for (Entry<String, BaseBeliefbase> ent : views.entrySet()) {
//			BaseBeliefbase actView = ent.getValue();
//			report("View->'" + name + "' Beliefbase of '" + ent.getKey()
//					+ "' created.", actView);
//		}

		for (AgentComponent ac : getComponents()) {
			report("Custom component '" + ac.getClass().getSimpleName()
					+ "' of '" + getName() + "' created.", ac);
		}
	}
	
	public Collection<Perception> getPerceptions(){
		return perceptions;
	}
	

	public Perception getPerception(int index){
		return perceptions.get(index);
	}
	/**
	 * @return the type name of the current agent.
	 * @throws a {@link RuntimeException} when the type name of a agent isn't found. 
	 */
	public String getAgentTypeName() {
		Pattern  pattern=Pattern.compile("([^0-9]*)");
		Matcher matcher=pattern.matcher(getName());
		if(matcher.find()) {
			return matcher.group();
		}
		throw new RuntimeException("The AgentTypeName isn't matching the pattern for the given agent name.");
	}
	
	/**
	 * Updates the beliefs of the agent. This method searches for the correct
	 * Update operator and calls its process method.
	 * 
	 * @param perception
	 *            The perception causing the update.
	 * @param beliefs
	 *            The Beliefs used as basis for the update process.
	 * @return The updated version of the beliefs.
	 */
	@Override
	@SuppressWarnings("hiding")
	public Beliefs updateBeliefs(KReaturesAtom perception,  Beliefs beliefs) {
		if (perception != null) {
			// save the perception for later use in messaging system.
			if(perception instanceof Perception) {
				lastUpdateBeliefsPercept = (Perception)perception;
			}
			informations.setPerception((Perception)perception);
			PerceptionParameter param = new PerceptionParameter(this, beliefs.getWorldKnowledge(), informations.getPerceptions());
			OperatorCallWrapper bubo = operators.getPreferedByType(BaseBeliefsUpdateOperator.OPERATION_TYPE);
			bubo.process(param);
//			FolBeliefbase actuelBelief=(FolBeliefbase)
//			FolBeliefbase oldBelief=(FolBeliefbase)beliefs.getWorldKnowledge();
//			oldBelief.setProgram(actuelBelief.getProgram());
			return beliefs; 
		}
		
		return beliefs;
	}
	
	@Override
	public boolean cycle() {
//		Perception percept = perceptions.isEmpty() ? null : perceptions.get(0);
		informations.setPerceptions(perceptions); 
		perceptions.clear();
		LOG.info("[" + this.getName() + "] Cylce starts: " + informations);

		regenContext();
		Context c = getContext();
		c.set("perception", informations);
//		boolean checkExe=asmlCylce.execute(c);
		return asmlCylce.execute(c);
	}
	
	@Override
	public PlanComponent getPlanComponent() {
		PlanComponent plan = getComponent(SwarmPlanComponent.class);
		if (plan == null) {
			LOG.warn(
					"Tried to access the plan-component of agent '{}' which has no plan-component.",
					getName());
			return null;
		}
		return plan;
	}
	@Override
	public Context getContext() {
		return context;
	}

	@Override
	protected void regenContext() {
		context = ContextFactory.createContext(this);
		context.set("operators", this.operators);
		context.set("plan", this.getComponent(SwarmPlanComponent.class));
		if (beliefs != null) {
			context.set("world", beliefs.getWorldKnowledge());
			Map<String, BaseBeliefbase> views = beliefs.getViewKnowledge();
			Context vc = new Context();
			context.attachContext("views", vc);
			for (Entry<String, BaseBeliefbase> ent : views.entrySet()) {
				vc.set(ent.getKey(), ent.getValue());
			}
		}
	}

	
	
	@Override
	public BaseBeliefbase getWorldBB() {
		return worldBB;
	}
	
	@Override
	public void setWorldBB(BaseBeliefbase worldBB) {
		this.worldBB = worldBB;
	}
	
	@Override
	public BaseBeliefbase getControllerBB() {
		return controllerBB;
	}
	
	
	@Override
	public void setControllerBB(BaseBeliefbase controllerBB) {
		this.controllerBB = controllerBB;
	}
	
	/**
	 * @return the informations
	 */
	public SwarmInformation getInformations() {
		return informations;
	}
	/**
	 * @param informations the informations to set
	 */
	public void setInformations(SwarmInformation informations) {
		this.informations = informations;
	}
	@Override
	public boolean equals(Object other) {
		if(other==null) return false;
		if(!(other instanceof NewAgent))return false;
		NewAgent agent=(NewAgent)other;
		return this.name.equals(agent.name);
	}
}
