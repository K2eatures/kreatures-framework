/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * %TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,Type,
 * CountTick,IsWaiting,IsReady,IsFinish). 
 * @author donfack
 *
 */
public class PredicateTimeEdgeState extends SwarmPredicate {
	private String name;
	private String typeName;
	private String visitorName;
	private String visitorTypeName;
	private int compType;
	private int tick;
	private boolean isWaiting;
	private boolean isReady;
	private boolean isFinish;

	public PredicateTimeEdgeState(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateTimeEdgeState(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateTimeEdgeState(PredicateTimeEdgeState other) {
		super(other);
		this.name=other.name;
		this.typeName=other.typeName;
		this.visitorName=other.visitorName;
		this.visitorTypeName=other.visitorTypeName;
		this.compType=other.compType;
		this.tick=other.tick;
		this.isWaiting=other.isWaiting;
		this.isReady=other.isReady;
		this.isFinish=other.isFinish;

	}	
	
	/**
	 * set the initial value of the object.
	 * @return
	 */
	public PredicateTimeEdgeState init(){
//		this.visitorName="nothing";
//		this.visitorTypeName="nothing";
//		this.compType=0;
		this.tick=0;
		this.isWaiting=false;
		this.isReady=false;
//		this.isFinish=false;
		return this;
	}

	
	@Override
	public PredicateTimeEdgeState clone() {
		return new PredicateTimeEdgeState(this);
	}

	public String getName() {
		return name;
	}

//	public void setName(String name) {
//		this.name = name;
//	}

	public String getTypeName() {
		return typeName;
	}

//	public void setTypeName(String typeName) {
//		this.typeName = typeName;
//	}

	public String getVisitorName() {
		return visitorName;
	}

//	public void setVisitorName(String visitorName) {
//		this.visitorName = visitorName;
//	}

	public String getVisitorTypeName() {
		return visitorTypeName;
	}

//	public void setVisitorTypeName(String visitorTypeName) {
//		this.visitorTypeName = visitorTypeName;
//	}

	public int getCompType() {
		return compType;
	}

//	public void setCompType(int compType) {
//		this.compType = compType;
//	}

	public int getTick() {
		return tick;
	}

	public void incrTick() {
		this.tick++;
	}

	public boolean isWaiting() {
		return isWaiting;
	}

	public void setWaiting(boolean isWaiting) {
		this.isWaiting = isWaiting;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	@Override
	public void createInstance(FolFormula atom) {
		//PredicateTimeEdgeState predicate=null;
		Pattern pattern=Pattern.compile("TimeEdgeState[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\d+),(\\w+),(\\w+),(\\w+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
			//predicate=new PredicateTimeEdgeState(atom);
			this.name=matcher.group(1);
			this.typeName=matcher.group(2);
			this.visitorName=matcher.group(3);
			this.visitorTypeName=matcher.group(4);
			this.compType=Integer.parseInt(matcher.group(5));
			this.tick=Integer.parseInt(matcher.group(6));
			this.isWaiting=Boolean.parseBoolean(matcher.group(7));
			this.isReady=Boolean.parseBoolean(matcher.group(8));
			this.isFinish=Boolean.parseBoolean(matcher.group(9));

		}
	}

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateTimeEdgeState))
			return false;

		PredicateTimeEdgeState obj=(PredicateTimeEdgeState)other;
		boolean isName=obj.name==null?this.name==null:obj.name.equals(this.name);
		boolean isTypeName=obj.typeName==null?this.typeName==null:obj.typeName.equals(this.typeName);

		return isName & isTypeName;
	}

	@Override
	public int hashCode() {
		return this.typeName.hashCode()* 11;
	}

	/**
	 * TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,
	 * Type,CountTick,IsWaiting,IsReady,IsFinish). 
	 */
	@Override
	public String toString(){
		return String.format("TimeEdgeState(%s,%s,%s,%s,%s,%d,%s,%s,%s)", name,typeName,visitorName,visitorTypeName,compType,tick,isWaiting,isReady,isFinish);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */

	@Override
	public String getPredicatType() {
		return getFormulName();
	}
}
