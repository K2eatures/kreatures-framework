/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * 
 * TimeEdgeLockState(AgentName2,StationName2,AgentName1,StationName1,EdgeType,IsActiv,Lock2,Lock1,Finish2,Finish1).
 * @author Cedric Perez Donfack
 *
 */
public class PredicateTimeEdgeLockState extends SwarmPredicate {
	
	private long id;
	private String agName1;
	private String stName1;
	private String agName2;
	private String stName2;
	private int edgeType;
	private boolean isActiv;
	private boolean isLock1;
	private boolean isLock2;
	private boolean isFinish1;
	private boolean isFinish2;

	public PredicateTimeEdgeLockState(FolFormula desire) {
		this(desire,null);
		
	}

	public PredicateTimeEdgeLockState(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
		id++;
	}

	public PredicateTimeEdgeLockState(PredicateTimeEdgeLockState other) {
		super(other);
		this.agName1=other.agName1;
		this.stName1=other.stName1;
		this.agName2=other.agName2;
		this.stName2=other.stName2;
		this.edgeType=other.edgeType;
		this.isActiv=other.isActiv;
		this.isLock1=other.isLock1;
		this.isLock2=other.isLock2;
		this.isFinish1=other.isFinish1;
		this.isFinish2=other.isFinish2;
		id++;

	}	
	
	
	public PredicateTimeEdgeLockState(PredicateTimeEdgeLockGet other) {
		super(other);
		this.agName1=other.getAgName1();
		this.stName1=other.getStName1();
		this.agName2=other.getAgName2();
		this.stName2=other.getStName2();
		this.edgeType=other.getEdgeType();
		this.isActiv=false;
		this.isLock1=other.isLock1();
		this.isLock2=other.isLock2();
		this.isFinish1=other.isFinish1();
		this.isFinish2=other.isFinish2();
		id++;

	}	
	
	/**
	 * set the initial value of the object.
	 * @return
	 */
	public PredicateTimeEdgeLockState init(){
		this.isActiv=false;
		this.isLock1=false;
		this.isLock2=false;
		this.isFinish1=false;
		this.isFinish2=false;

		return this;
	}

	
	@Override
	public PredicateTimeEdgeLockState clone() {
		return new PredicateTimeEdgeLockState(this);
	}

	
	/**
	 * {@link PredicateTimeEdgeLockState}
	 * TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,IsActiv,Lock1,Lock2,Finish1,Finish2).
	 */
	@Override
	public void createInstance(FolFormula atom) {
		//PredicateTimeEdgeState predicate=null;
		Pattern pattern=Pattern.compile("TimeEdgeLockState[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\w+),(\\w+),(\\w+),(\\w+),(\\w+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
			//predicate=new PredicateTimeEdgeState(atom);
			this.agName1=matcher.group(1);
			this.stName1=matcher.group(2);
			this.agName2=matcher.group(3);
			this.stName2=matcher.group(4);
			this.edgeType=Integer.parseInt(matcher.group(5));
			this.isActiv=Boolean.parseBoolean(matcher.group(6));
			this.isLock1=Boolean.parseBoolean(matcher.group(7));
			this.isLock2=Boolean.parseBoolean(matcher.group(8));
			this.isFinish1=Boolean.parseBoolean(matcher.group(9));
			this.isFinish2=Boolean.parseBoolean(matcher.group(10));

		}
	}
	/**
	 *This compare the information about the current agent and the information of the corresponding agent in this object.
	 * @param currentStation the information about the actual goal.
	 * @return
	 */
	public boolean compareToCurrentStation(PredicateCurrentStation currentStation){
		boolean isAgName=currentStation.getAgentName()==null?this.agName2==null:currentStation.getAgentName().equals(this.agName2);
		boolean isStName=currentStation.getStationName()==null?this.stName2==null:currentStation.getStationName().equals(this.stName2);
		
		return isAgName & isStName;
	}
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateTimeEdgeLockState))
			return false;

		PredicateTimeEdgeLockState obj=(PredicateTimeEdgeLockState)other;
		boolean isAgName1=obj.agName1==null?this.agName1==null:obj.agName1.equals(this.agName1);
		boolean isAgName2=obj.agName2==null?this.agName2==null:obj.agName2.equals(this.agName2);
		boolean isStName1=obj.stName1==null?this.stName1==null:obj.stName1.equals(this.stName1);
		boolean isStName2=obj.stName2==null?this.stName2==null:obj.stName2.equals(this.stName2);
		

		return isAgName1 & isAgName2 & isStName2 & isStName1;
	}

	@Override
	public int hashCode() {
		return ((agName1==null?0:agName1.hashCode())+(agName2==null?0:agName2.hashCode()))* 11;
	}

	/**
	 * TimeEdgeLockState(AgentName1,StationName1,AgentName2,StationName2,EdgeType,IsActiv,Lock1,Lock2,Finish1,Finish2).
	 */
	@Override
	public String toString(){
		return String.format("TimeEdgeLockState(%s,%s,%s,%s,%d,%s,%s,%s,%s,%s)", agName1,stName1,agName2,stName2,edgeType,isActiv,isLock1,isLock2,isFinish1,isFinish2);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */

	@Override
	public String getPredicatType() {
		return getFormulName();
	}

	/**
	 * @return the agName1
	 */
	public String getAgName1() {
		return agName1;
	}

	/**
	 * @return the stName1
	 */
	public String getStName1() {
		return stName1;
	}

	/**
	 * @return the agName2
	 */
	public String getAgName2() {
		return agName2;
	}

	/**
	 * @return the stName2
	 */
	public String getStName2() {
		return stName2;
	}

	/**
	 * @return the edgeType
	 */
	public int getEdgeType() {
		return edgeType;
	}

	/**
	 * @return the isActiv
	 */
	public boolean isActiv() {
		return isActiv;
	}

	/**
	 * @return the isLock1
	 */
	public boolean isLock1() {
		return isLock1;
	}

	/**
	 * @return the isLock2
	 */
	public boolean isLock2() {
		return isLock2;
	}

	/**
	 * @return the isFinish1
	 */
	public boolean isFinish1() {
		return isFinish1;
	}

	/**
	 * @return the isFinish2
	 */
	public boolean isFinish2() {
		return isFinish2;
	}

	/**
	 * @param isActiv the isActiv to set
	 */
	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;
	}

	/**
	 * @param isLock1 the isLock1 to set
	 */
	public void setLock1(boolean isLock1) {
		this.isLock1 = isLock1;
	}

	/**
	 * @param isLock2 the isLock2 to set
	 */
	public void setLock2(boolean isLock2) {
		this.isLock2 = isLock2;
	}

	/**
	 * @param isFinish1 the isFinish1 to set
	 */
	public void setFinish1(boolean isFinish1) {
		this.isFinish1 = isFinish1;
	}

	/**
	 * @param isFinish2 the isFinish2 to set
	 */
	public void setFinish2(boolean isFinish2) {
		this.isFinish2 = isFinish2;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	
}
