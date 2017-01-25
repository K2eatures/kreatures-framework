package com.github.kreatures.swarm.comm;

import java.util.Set;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.github.kreatures.core.AgentAbstract;
import com.github.kreatures.core.comm.Inform;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * @see com.github.kreatures.core.comm.Inform
 * @author donfack
 *
 */

public class SwarmInform extends Inform {
	
	
	/**
	 * @see com.github.kreatures.core.comm.Inform#Inform(String, String, Set)
	 */
	public SwarmInform(
			@Element(name="sender") String sender, 
			@Element(name="receiver") String receiver, 
			@ElementList(name="sentences") Set<FolFormula> sentences) {
		super(sender, receiver,sentences);
		
	}

	/**
	 * @see com.github.kreatures.core.comm.Inform#Inform(AgentAbstract, String, FolFormula)
	 */
	public SwarmInform(AgentAbstract sender, String receiver, FolFormula sentence) {
		
		super(sender, receiver, sentence);
	}
	
	/**
	 * @see com.github.kreatures.core.comm.Inform#Inform(AgentAbstract, String, Set)
	 */
	public SwarmInform(AgentAbstract sender, String receiver, Set<FolFormula> sentences) {
		super(sender, receiver,sentences);
	}

	
}
