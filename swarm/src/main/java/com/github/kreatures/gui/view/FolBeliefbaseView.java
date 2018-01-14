package com.github.kreatures.gui.view;

import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;

import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.lp.asp.syntax.DLPLiteral;
import net.sf.tweety.lp.asp.util.AnswerSet;

import com.github.kreatures.gui.asp.AspBeliefbaseView;
import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.gui.view.BeliefbaseView;
import com.github.kreatures.swarm.beliefbase.SwarmAspReasoner;

import bibliothek.gui.dock.DefaultDockable;

import com.github.kreatures.core.logic.Beliefs;
import com.github.kreatures.core.logic.FolBeliefbase;


/**
 * This class is used to view the beliefset of a agent.
 * @author Cedric Perez Donfack
 * @see {@link AspBeliefbaseView}
 */
public class FolBeliefbaseView extends BeliefbaseView {

	/** kill warning */
	private static final long serialVersionUID = -9197437532805094834L;

	@Override
	public Class<? extends BaseBeliefbase> getObservedType() {
		return FolBeliefbase.class;
	}
	
	@Override
	protected void update(DefaultListModel<ListElement> model) {
		if(ref == null)	return;
		
		// First output the belief base content (using super class behavior)
		updateBeliefbaseOutput(model);
		
//		// Second: Process Answer sets 
//		FolBeliefbase bAct = (FolBeliefbase)actual;
//		if(! (bAct.getReasoningOperator().getImplementation() instanceof SwarmAspReasoner)) {
//			return;
//		}
//		SwarmAspReasoner reasoner = (SwarmAspReasoner) bAct.getReasoningOperator().getImplementation();
//
//		model.addElement(new ListElement(" ", ListElement.ST_NOTCHANGED));
//		model.addElement(new ListElement("--- Answerset Result using: " + 
//		reasoner.toString(), ListElement.ST_RESERVED));
//		
//		List<AnswerSet> answerSets = reasoner.processAnswerSets(bAct);
//		
//		// Output the answer sets to the JList.
//		int counter = 1;
//		if(answerSets != null) {
//			for(AnswerSet as : answerSets) {
//				model.addElement(new ListElement("Answer Set " + counter + "/" + answerSets.size(), 
//					ListElement.ST_NOTCHANGED));
//				
//				String output = "";
//				for(DLPLiteral l : as) {
//					output += l + ",";
//					
//					if(output.length() > 100) {
//						//output = output.substring(2);
//						model.addElement(new ListElement(output, ListElement.ST_NOTCHANGED));
//						output = "";
//					}
//				}
//				
//				if(output.length() != 0) {
//					output = output.substring(0, output.length()-1);
//					model.addElement(new ListElement(output, ListElement.ST_NOTCHANGED));
//				}
//				
//				counter += 1;
//				if(counter <= answerSets.size()) {
//					model.addElement(new ListElement(" ", ListElement.ST_NOTCHANGED));
//				}
//			}
//		}
//		
//		// Third: Output the inferred knowledge (using the super class method).
//		updateInferenceOutput(model);
	}
	
	@Override
	protected void updateInferenceOutput(DefaultListModel<ListElement> model) {
		// add a placeholder and then show the inference result:
		FolBeliefbase bAct = (FolBeliefbase)actual;
		FolBeliefbase bPrev = (FolBeliefbase)previous;
		model.addElement(new ListElement(" ", ListElement.ST_NOTCHANGED));
		model.addElement(new ListElement("--- Inference Result using: " + 
		bAct.getReasoningOperator().toString(), ListElement.ST_RESERVED));
		
		// Calculate the inference of the reasoning.
		Set<FolFormula> inferenceAct = bAct.infere();
		Set<FolFormula> inferenceOld = bPrev == null ? null : bPrev.infere();
		
		for(FolFormula f : inferenceAct) {
			if(inferenceOld != null && !inferenceOld.contains(f)) {
				model.addElement(new ListElement(f.toString(), ListElement.ST_NEW));
			} else {
				model.addElement(new ListElement(f.toString(), ListElement.ST_RESERVED));
			}
		}
		
		if(inferenceOld != null) {
			for(FolFormula f : inferenceOld) {
				if(!inferenceAct.contains(f)) {
					model.addElement(new ListElement(f.toString(), ListElement.ST_DELETED));
				}
			}
		}
	}
	
	@Override
	public void decorate(DefaultDockable dockable) {
		super.decorate(dockable);
		BaseBeliefbase bb = (BaseBeliefbase)ref;
		String title = bb.getAgent().getName();
		Beliefs bel = bb.getAgent().getBeliefs();
		if(ref == bel.getWorldKnowledge()) {
			title += " - BeliefSet";
		} 
		dockable.setTitleText(title);
	}
}
