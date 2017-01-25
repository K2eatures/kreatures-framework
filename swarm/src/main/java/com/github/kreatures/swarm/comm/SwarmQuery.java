package com.github.kreatures.swarm.comm;


import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.comm.Query;
import com.github.kreatures.core.reflection.FolFormulaVariable;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * @see com.github.kreatures.core.comm.Query
	 
 * @author donfack
 *
 */

public class SwarmQuery extends Query {

	/**
	 * @see com.github.kreatures.core.comm.Query#Query(String, String, FolFormulaVariable)
	 */
	public SwarmQuery(String sender, String receiver, FolFormulaVariable question) {
		super(sender, receiver, question);
	}
	
	/**
	 * @see com.github.kreatures.core.comm.Query#Query(AgentAbstract, String, FolFormula)
	 */
	public SwarmQuery(AgentAbstract sender, String receiverId, FolFormula question) {
		super(sender, receiverId,question);
		
	}

}
