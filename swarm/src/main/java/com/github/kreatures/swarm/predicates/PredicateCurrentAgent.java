package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * CurrentAgent(AgentName,AgentTypeName).
 * TODO
 * @author Cedric Perez Donfack
 *
 */
public class PredicateCurrentAgent extends SwarmPredicate {
	/**
	 * This is the Agent name and has to be unique.
	 */
	private String name;
	private String typeName;
	
	public PredicateCurrentAgent(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
	
	public PredicateCurrentAgent(String agentName,String agentTypeName) throws Exception {
		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName));
		this.name=agentName;
		this.typeName=agentTypeName;
	}

	public PredicateCurrentAgent(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateCurrentAgent(PredicateCurrentAgent other) {
		super(other);
		this.name=other.name;
		this.typeName=other.typeName;	
	}	
	
	@Override
	public PredicateCurrentAgent clone() {
		return new PredicateCurrentAgent(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * CurrentAgent(AgentName,AgentTypeName).
	 */
	@Override
	public String toString() {
		return String.format("CurrentAgent(%s,%s)", name, typeName);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("CurrentAgent[(](\\w+),(\\w+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.name=matcher.group(1);
			this.typeName=matcher.group(2);
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateCurrentAgent))
			return false;
		
		PredicateCurrentAgent obj=(PredicateCurrentAgent)other;
		boolean isName=obj.name==null?this.name==null:obj.name.equals(this.name);
		boolean isTypeName=obj.typeName==null?this.typeName==null:obj.typeName.equals(this.typeName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.typeName.hashCode()* 11;
	}
}
