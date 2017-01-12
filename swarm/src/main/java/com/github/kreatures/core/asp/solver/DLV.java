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
	 * //TODO comments
	 */
	
	public AnswerSetList runDLV(Program p, int nModels, String... otherOptions) throws SolverException {
		String params="";
		
		for (String option:otherOptions){
			params+=" "+option;
		}
		
		checkSolver(path2dlv);
		
		String cmdLine = path2dlv + " -- " + "-N=" + nModels+" "+params; 
		
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
