package com.github.kreatures.core.logic;

import java.util.Set;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.operators.OperatorCallWrapper;
import com.github.kreatures.core.operators.parameter.BeliefbasePluginParameter;
import com.github.kreatures.core.operators.parameter.ChangeBeliefbaseParameter;
import com.github.kreatures.core.operators.parameters.BaseReasonerParameter;
import com.github.kreatures.core.util.Pair;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * This class is only use the show which information belong to world beliefbase
 * @author Cedric Perez Donfack
 *
 */
public class WorldBeliefbaseForGUI extends AspBeliefbase {

	public WorldBeliefbaseForGUI() {
	}

	public WorldBeliefbaseForGUI(WorldBeliefbaseForGUI other) {
		super(other);
	}
	@Override
	public WorldBeliefbaseForGUI clone() {
		
		return new WorldBeliefbaseForGUI(this);
	}

	@Override
	public Set<FolFormula> infere() {
		@SuppressWarnings("unchecked")
		Pair<Set<FolFormula>, KReaturesAnswer> reval = (Pair<Set<FolFormula>, KReaturesAnswer>) getReasoningOperator()
				.process(new BaseReasonerParameter(this));
		return reval.first;
	}
	
	public Set<FolFormula> infere(BeliefbasePluginParameter beliefbaseParams) {
		@SuppressWarnings("unchecked")
		Pair<Set<FolFormula>, KReaturesAnswer> reval = (Pair<Set<FolFormula>, KReaturesAnswer>) getReasoningOperator()
				.process(beliefbaseParams);
		return reval.first;
	}

	
	@Override
	public void addKnowledge(BaseBeliefbase newKnowledge, OperatorCallWrapper changeOperator) {
		if(changeOperator == null)
			changeOperator = getChangeOperator();
		if(newKnowledge!=null) {
			ChangeBeliefbaseParameter cbp = new ChangeBeliefbaseParameter(this, newKnowledge);
			changeOperator.process(cbp);
//			FolBeliefbase actuelBelief=(FolBeliefbase)changeOperator.process(cbp);
//			this.setProgram(actuelBelief.getProgram());		
			firePropertyChangeListener(BELIEFBASE_CHANGE_PROPERTY_NAME, null, null);
		}
	}

}