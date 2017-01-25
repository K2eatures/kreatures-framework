/**
 * 
 */
package com.github.kreatures.swarm.beliefbase;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kreatures.core.BaseBeliefbase;
import com.github.kreatures.core.asp.solver.DLV;
import com.github.kreatures.core.asp.solver.ESolverWrapper;
import com.github.kreatures.core.logic.BaseReasoner;
import com.github.kreatures.core.logic.KReaturesAnswer;
import com.github.kreatures.core.logic.FolBeliefbase;
import com.github.kreatures.core.logic.asp.AspBeliefbase;
import com.github.kreatures.core.logic.asp.AspReasoner;
import com.github.kreatures.core.logic.asp.ISolverWrapper;
import com.github.kreatures.core.operators.parameter.ReasonerParameter;
import com.github.kreatures.core.operators.parameters.BaseReasonerParameter;
import com.github.kreatures.core.util.Pair;
import com.github.kreatures.swarm.SwarmConst;

import net.sf.tweety.logics.fol.syntax.FolFormula;
import net.sf.tweety.logics.translators.aspfol.AspFolTranslator;
import net.sf.tweety.lp.asp.solver.Solver;
import net.sf.tweety.lp.asp.solver.SolverException;
import net.sf.tweety.lp.asp.syntax.DLPLiteral;
import net.sf.tweety.lp.asp.util.AnswerSet;

/**
 * @author donfack
 *
 */
public class SwarmAspReasoner extends AspReasoner {
	/** The logger used for output in the kreatures Framework */
	static private Logger LOG = LoggerFactory.getLogger(SwarmAspReasoner.class);
	/** 
	 * the Dlv solver used by the reasoning class instance 
	 */
	private ISolverWrapper solver;
	
	/**
	 * @throws InstantiationException
	 */
	public SwarmAspReasoner() throws InstantiationException {
		super();
		super.setSolverWrapper(ESolverWrapper.DLV);
		solver=ESolverWrapper.DLV;
	}

	/**
	 * Helper method: Decides which solver to use when running an inference.
	 * @param bb	the solver will be applied on this beliefbase.
	 * @return		A list of answersets 
	 * @throws SolverException
	 */
	private List<AnswerSet> runSolver(AspBeliefbase bb,String... options) throws SolverException {
		if(solver == null) {
			LOG.warn("No asp solver linked to SwarmAspReasoner operator");
			return new LinkedList<>();
		}
		
		DLV dlv = (DLV) solver.getSolver();
		return dlv.computeModels(bb.getProgram(), SwarmConst.MAX_INT.getValue(),options);
	}
	
	
	@Override
	protected Set<FolFormula> inferImpl(ReasonerParameter params) {
		BaseReasonerParameter reasonerParam=(BaseReasonerParameter)params;
		List<AnswerSet> answerSets = processAnswerSets((FolBeliefbase)reasonerParam.getBeliefBase(),reasonerParam.getOptions());
		
		if(answerSets == null) {
			LOG.warn("Something went wrong during ASP-Solver invocation.");
			return new HashSet<>();
		}
		
		Set<DLPLiteral> literals = selectAnswerSet(params, answerSets);
		Set<FolFormula> reval = new HashSet<>();
		AspFolTranslator translator = new AspFolTranslator();
		for(DLPLiteral l : literals) {
			reval.add(translator.toFOL(l));

		}
		return reval;
	}
	
	public List<AnswerSet> processAnswerSets(AspBeliefbase bb,String... options) {
		List<AnswerSet> reval = null;
		
		try {
			reval = runSolver(bb,options);
			LOG.info(reval.toString());
		} catch(SolverException ex) {
			LOG.error("Error occured: " + ex.getMessage());
		}
		
		return reval;
	}
}