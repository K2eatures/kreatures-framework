package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;
import com.github.kreatures.swarm.predicates.transform.TransformPredicates;

import net.sf.tweety.logics.fol.syntax.FolFormula;
/**
 * TODO
 * StationTypItem(AgentName,StationNameIn,StationTypNameIn,StationTypeNameOut,itemDim)
 * @author Cedric Perez Donfack
 *
 */
public class PredicateStationTypItem extends SwarmPredicate {

	private String agentName;
	private String stNameIn;
	private String stTypNameIn;
	private String stTypNameOut;
	
	private int itemDim;

	public PredicateStationTypItem(FolFormula desire){
		super(desire);
		createInstance(desire);
	}
	
//	public PredicateStationTypItem(String agentName,String agentTypeName,String stationName, String stationTypeName,int timeMotiv, int time,int itemMotiv) throws Exception {
//		super(TransformPredicates.getLiteral("CurrentAgent",agentName,agentTypeName,stationName,stationTypeName));
//		this.agentName=agentName;
//		this.agentTypeName=agentTypeName;
//		this.stationName=stationName;
//		this.stationTypeName=stationTypeName;
//		this.timeMotiv=timeMotiv;
//		this.time=time;
//		this.itemMotiv=itemMotiv;
//	}

	public PredicateStationTypItem(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateStationTypItem(PredicateStationTypItem other) {
		super(other);
		this.agentName=other.agentName;
		this.stNameIn=other.stNameIn;
		this.stTypNameIn=other.stTypNameIn;
		this.stTypNameOut=other.stTypNameOut;
		this.itemDim=other.itemDim;
	}	
	
	@Override
	public PredicateStationTypItem clone() {
		return new PredicateStationTypItem(this);
	}
	
	/**
	 * StationTypItem(AgentName,StationNameIn,StationTypNameIn,StationTypeNameOut,item)
	 */
	@Override
	public String toString() {
		return String.format("StationTypItem(%s,%s,%s,%s,%d)", agentName,stNameIn, stTypNameIn,stTypNameOut,itemDim);
	}

	@Override
	public void createInstance(FolFormula atom) {
//		PredicateAgent agent=null;
		Pattern pattern=Pattern.compile("StationTypItem[(](\\w+),(\\w+),(\\w+),(\\w+),(\\d+),(\\d+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			agent=new PredicateAgent();
			this.agentName=matcher.group(1);
			this.stNameIn=matcher.group(2);
			this.stTypNameIn=matcher.group(3);
			this.stTypNameOut=matcher.group(4);
			this.itemDim=Integer.parseInt(matcher.group(5));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateStationTypItem))
			return false;
		
		PredicateStationTypItem obj=(PredicateStationTypItem)other;
		boolean isName=obj.agentName==null?this.agentName==null:obj.agentName.equals(this.agentName);
		boolean isTypeNameIn=obj.stNameIn==null?this.stNameIn==null:obj.stNameIn.equals(this.stNameIn);
		boolean isTypeNameOut=obj.stTypNameOut==null?this.stTypNameOut==null:obj.stTypNameOut.equals(this.stTypNameOut);
		return isName & isTypeNameIn & isTypeNameOut;
	}
	
	@Override
	public int hashCode() {
		return (this.agentName.hashCode()+this.stNameIn.hashCode())* 11;
	}
	
	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @return the stNameIn
	 */
	public String getStNameIn() {
		return stNameIn;
	}

	/**
	 * @return the stTypNameIn
	 */
	public String getStTypNameIn() {
		return stTypNameIn;
	}

	/**
	 * @return the stTypNameOut
	 */
	public String getStTypNameOut() {
		return stTypNameOut;
	}

	/**
	 * @return the itemDim
	 */
	public int getItemDim() {
		return itemDim;
	}



}
