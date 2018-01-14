package com.github.kreatures.gui.view;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.lp.asp.syntax.DLPLiteral;
import net.sf.tweety.lp.asp.util.AnswerSet;

import com.github.kreatures.gui.asp.AspBeliefbaseView;
import com.github.kreatures.gui.component.OperatorConfig;
import com.github.kreatures.gui.component.OperatorConfigController;
import com.github.kreatures.gui.component.OperatorConfigPanel;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.internal.Entity;
import com.github.kreatures.gui.view.BeliefbaseView;
import com.github.kreatures.gui.view.ListViewColored.ListElement;
import com.github.kreatures.swarm.beliefbase.SwarmAspReasoner;

import bibliothek.gui.dock.DefaultDockable;

import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.ScenarioModelBeliefbase;
import com.github.kreatures.core.logic.WorldBeliefbaseForGUI;


/**
 * This class is used to view the beliefset of a agent.
 * @author Cedric Perez Donfack
 * @see {@link AspBeliefbaseView}
 */
public class WorldBeliefbaseView extends ListViewColored {

	/** kill warning */
	private static final long serialVersionUID = -9197437532805094834L;

	private OperatorConfig opConfig;
	
	@Override
	public Class<? extends BaseBeliefbase> getObservedType() {
		return WorldBeliefbaseForGUI.class;
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	protected void update(DefaultListModel<ListElement> model) {
		if(ref == null)	return;
		
		// First output the belief base content (using super class behavior)
		updateBeliefbaseOutput(model);
	}
		
	@Override
	protected void onElementClicked(int index, int status) {
		if(status == ListElement.ST_RESERVED) {
			OperatorConfigController controller = new OperatorConfigController(opConfig);
			OperatorConfigPanel opPanel = new OperatorConfigPanel(controller);
//			opPanel.init(opConfig);
			
			JFrame frame = new JFrame();
			frame.setLayout(new BorderLayout());
			frame.getContentPane().add(opPanel, BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);
		}
	}

	@Override
	protected List<String> getStringRepresentation(Entity obj) {
		if(obj instanceof BaseBeliefbase) {
			BaseBeliefbase bb = (BaseBeliefbase)obj;
			return bb.getAtomsAsStringList();
		}
		
		return null;
	}

	@Override
	public void setObservedEntity(Entity bb) {
		this.ref = bb;
		this.actual = bb;
		this.previous = null;
	}

	@Override
	public void decorate(DefaultDockable dockable) {
		super.decorate(dockable);
		BaseBeliefbase bb = (BaseBeliefbase)ref;
		String title = bb.getAgent().getName();
		Beliefs bel = bb.getAgent().getBeliefs();
		title += " - World Beliefs";
		dockable.setTitleText(title);
	}
}
