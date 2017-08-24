/**
 * 
 */
package com.github.kreatures.core.asp.solver;

import net.sf.tweety.lp.asp.solver.SolverException;
import net.sf.tweety.lp.asp.syntax.Program;
import net.sf.tweety.lp.asp.util.AnswerSetList;

/**
 * @author donfack
 *
 */
public class DLV extends  net.sf.tweety.lp.asp.solver.DLV {
	
	private String path2dlv;
	/**
	 * @param path2dlv
	 */
	public DLV(String path2dlv) {
		super(path2dlv);
		this.path2dlv=path2dlv;

	}
	/**
	 * This methode calls the run method appropriated to the given solver.
	 * @param p the asp program to be infer
	 * @param nModels the maximal number of models 
	 * @param otherOptions the other options which will be used to specifier the inference.
	 * @return the AnswerSetList of the inference
	 * @throws SolverException exception is thrown when there are any problem with the solver
	 */
	public AnswerSetList computeModels(Program p, int nModels,String... otherOptions) throws SolverException{
		
		return runDLV(p,nModels,otherOptions);
		
	}
	
	/**
	 * This method is the run method of solver DLV.
	 * @param p the asp program to be infer
	 * @param nModels the maximal number of models 
	 * @param otherOptions the other options which will be used to specifier the inference.
	 * @return the AnswerSetList of the inference
	 * @throws SolverException exception is thrown when there are any problem with the solver
	 */

	public AnswerSetList runDLV(Program p, int nModels, String... otherOptions) throws SolverException {
		StringBuilder params=new StringBuilder("");
		String cmdLine=path2dlv + " -- " + "-N=" + nModels+" ";		if(otherOptions.length>0) {
			params.append(otherOptions[0]);
			for (int index=1;index<otherOptions.length;index++){
				params.append(","+otherOptions[index]);
			}
			cmdLine +=SolverOptions.FILTER+params.toString();
		}
		
		checkSolver(path2dlv);
			
		// try running dlv
		try {
			ai.executeProgram(cmdLine,p.toStringFlat());
		} catch (Exception e) {
			System.out.println("dlv error!");
			e.printStackTrace();
		}
		
		checkErrors();	
		String parseable = "";
		for(String str : ai.getOutput()) {
			if(str.trim().startsWith("{")) {
				parseable += str;
			}
		}
		
		return parseAnswerSets(parseable);
	}
}
