package com.github.kreatures.core.asp.solver;

import com.github.kreatures.core.logic.asp.ISolverWrapper;
import com.github.kreatures.core.logic.asp.SolverWrapper;

import net.sf.tweety.lp.asp.solver.Solver;
/**
 * 
 * @author Cedric Perez Donfack
 *
 */
public enum ESolverWrapper implements ISolverWrapper {

	DLV;
	
	private ESolverWrapper() {
		
	}

	/**
	 * TODO for more solver, one has to check.
	 */
	@Override
	public Solver getSolver() {
		return new DLV(SolverWrapper.DLV.getSolverPath());
	}

	@Override
	public InstantiationException getError() {
		return SolverWrapper.DLV.getError();
	}

}
