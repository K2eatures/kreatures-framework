package com.github.kreatures.gui.controller;

import java.util.Set;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bibliothek.gui.Dockable;
import ch.qos.logback.core.joran.action.NewRuleAction;

import com.github.kreatures.core.Action;
import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.AgentComponent;
import com.github.kreatures.core.KReaturesEnvironment;
import com.github.kreatures.core.SwarmAgent;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.KReaturesConst;
import com.github.kreatures.gui.KReaturesWindow;
import com.github.kreatures.gui.base.ViewComponent;
import com.github.kreatures.gui.view.BeliefbaseView;
import com.github.kreatures.core.internal.ViewComponentFactory;
import com.github.kreatures.core.listener.SimulationListener;

/**
 * @deprecated
 */
public class SimulationTreeControllerAS extends SimulationTreeController {
	/** logging facility */
	private static Logger LOG = LoggerFactory.getLogger(SimulationTreeControllerAS.class);

	public SimulationTreeControllerAS(JTree tree) {
		super(tree);
	}


	/**
	 * Handles the selection of a tree-node which encapsulates a belief base.
	 * @param bb	The base belief base saved in selected tree node.
	 */
	private void handlerBeliefbase(BaseBeliefbase bb) {
		LOG.trace("Handle beliefbase: '{}'", bb.getFileEnding());

		Dockable dd = null;
		if(bb.getFileEnding().toLowerCase().equals("asp")) {
			ViewComponent view = ViewComponentFactory.get().createViewForEntityComponent(bb);
			if(view != null) {
				dd = KReaturesWindow.get().openView(view);
			}
		} else {
			BeliefbaseView bc = ViewComponentFactory.get().createEntityView(
					BeliefbaseView.class, bb);
			dd = KReaturesWindow.get().openView(bc);
		}

		if(dd != null) {
			KReaturesWindow.get().registerDockableForCurrentSimulation(dd);
		}
	}
	
	/**
	 * Handles the selection of a tree-node which encapsulates an Agent-Component.
	 * @param component	The agent component saved in the clicked tree node.
	 * 
	 */
	private void handlerAgentComponent(AgentComponent component) {
		String agname = component.getAgent().getName();
		LOG.trace("Handle AgentComponent: '{}' of Agent '{}'.", agname);
		ViewComponent view = ViewComponentFactory.get().createViewForEntityComponent(component);
		if(view != null) {
			Dockable dd = KReaturesWindow.get().openView(view);
			KReaturesWindow.get().registerDockableForCurrentSimulation(dd);
		}
	}
	
	@Override
	public void simulationStarted(KReaturesEnvironment simulation) {
		// TODO: Important the controller is not registered to the simulation listener because
		// the controller is created during a simulationStarted event and the list of listeners
		// must not be changed during this event.

		for(String agName : simulation.getAgentNames()) {
			agentAddedInt(getRoot(), simulation.getAgentByName(agName));
		}

		expandAll(tree, true);
		tree.updateUI();
	}
	
	/**
	 * Handles the selection of a tree-node which encapsulates an Agent.
	 * @param agent	The agent saved in the clicked tree node.
	 */
	private void handlerAgent(AgentAbstract agent) {
		LOG.trace("Handle Agent '{}'", agent.getName());
		/*AgentView ac = new AgentView();
		ac.setObservationObject(agent);
		ac.init(); 
		KReaturesWindow.getInstance().addComponentToCenter(ac); */
	}
	
	private void agentAddedInt(DefaultMutableTreeNode parent, AgentAbstract added) {

		// create user object wrapper for agent node:
		UserObjectWrapper agent = new DefaultUserObjectWrapper(added) {
			@Override
			public void onActivated() {
				handlerAgent((AgentAbstract)this.getUserObject());
			}
		};
		DefaultMutableTreeNode agNode = new DefaultMutableTreeNode(agent);

		// create user object wrapper for all belief set information
		BaseBeliefbase beliefSet = added.getBeliefs().getWorldKnowledge();
		UserObjectWrapper beliefSetWrapper = new DefaultUserObjectWrapper(beliefSet, "BeliefSet") {
			@Override
			public void onActivated() {
				handlerBeliefbase((BaseBeliefbase)getUserObject());
			}
		};
		agNode.add(new DefaultMutableTreeNode(beliefSetWrapper));

		// defines each belief part into a container node.
		DefaultMutableTreeNode beliefbase= null;
		beliefbase = new DefaultMutableTreeNode("Belief_Parts");
		agNode.add(beliefbase);		
		
		// create user object wrapper for world belief base node
		BaseBeliefbase worldBB = ((SwarmAgent) added).getWorldBB();
		UserObjectWrapper worldWrapper = new DefaultUserObjectWrapper(worldBB, "WorldBB") {
			@Override
			public void onActivated() {
				handlerBeliefbase((BaseBeliefbase)getUserObject());
			}
		};
		beliefbase.add(new DefaultMutableTreeNode(worldWrapper));
		
		// create user object wrapper for controller belief base node
		BaseBeliefbase controllerBB = ((SwarmAgent) added).getControllerBB();
		UserObjectWrapper controllerWrapper = new DefaultUserObjectWrapper(controllerBB, "ControllerBB") {
			@Override
			public void onActivated() {
				handlerBeliefbase((BaseBeliefbase)getUserObject());
			}
		};
		beliefbase.add(new DefaultMutableTreeNode(controllerWrapper));
		
		
		// create Environment adapter components container node.
		DefaultMutableTreeNode envAdapter = null;
		envAdapter = new DefaultMutableTreeNode("EnvAdapter");
		agNode.add(envAdapter);
		
		// create nodes for the environment adapter components and their environment features and scenario model
		//Define Environment features which will be send to the agent by its environment
		BaseBeliefbase envFeaturesBB = KReaturesConst._EnvFeatures;
		UserObjectWrapper envFeaturesWrapper = new DefaultUserObjectWrapper(envFeaturesBB, "EnvFeatures") {
			@Override
			public void onActivated() {
				handlerBeliefbase((BaseBeliefbase)getUserObject());
			}
		};
		envAdapter.add(new DefaultMutableTreeNode(envFeaturesWrapper));
		//Define scenario model which will be send to the agent by its environment
		BaseBeliefbase scenarioModelBB = KReaturesConst._ScenarioModel;
		UserObjectWrapper scenarioModelWrapper = new DefaultUserObjectWrapper(scenarioModelBB, "ScenarioModel") {
			@Override
			public void onActivated() {
				handlerBeliefbase((BaseBeliefbase)getUserObject());
			}
		};
		envAdapter.add(new DefaultMutableTreeNode(scenarioModelWrapper));

		// Create tree nodes for the agent components and their user object wrappers.
		DefaultMutableTreeNode comps = new DefaultMutableTreeNode("Components");
		for(AgentComponent ac  : added.getComponents()) {
			UserObjectWrapper w = new DefaultUserObjectWrapper(ac, ac.getClass().getSimpleName()) {
				@Override
				public void onActivated() {
					handlerAgentComponent((AgentComponent)getUserObject());
				}
			};
			comps.add(new DefaultMutableTreeNode(w));
		}

		agNode.add(comps);
		parent.add(agNode);

		expandAll(tree, true);

	}

	@Override
	public void simulationDestroyed(
			KReaturesEnvironment simulationEnvironment) {
		DefaultTreeModel tm = (DefaultTreeModel)tree.getModel();
		for(int i=0; i<tm.getChildCount(getRoot()); ++i) {
			DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)tm.getChild(getRoot(), i);
			if(! (dmtn.getUserObject() instanceof UserObjectWrapper)) 
				continue;

			UserObjectWrapper tuo = (UserObjectWrapper)dmtn.getUserObject();
			if(! (tuo.getUserObject() instanceof KReaturesEnvironment) )
				continue;

			KReaturesEnvironment sim = (KReaturesEnvironment)tuo.getUserObject();
			if(sim == simulationEnvironment) {
				tm.removeNodeFromParent(dmtn);
				break;
			}
		}
	}
}
