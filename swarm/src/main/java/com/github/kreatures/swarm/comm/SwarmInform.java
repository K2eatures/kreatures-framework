package com.github.kreatures.swarm.comm;

import java.util.Set;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.github.kreatures.core.Agent;
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
	 * @see com.github.kreatures.core.comm.Inform#Inform(Agent, String, FolFormula)
	 */
	public SwarmInform(Agent sender, String receiver, FolFormula sentence) {
		
		super(sender, receiver, sentence);
	}
	
	/**
	 * @see com.github.kreatures.core.comm.Inform#Inform(Agent, String, Set)
	 */
	public SwarmInform(Agent sender, String receiver, Set<FolFormula> sentences) {
		super(sender, receiver,sentences);
	}

	
}
