/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * %TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,Type,
 * CountTick,IsWaiting,IsReady,IsFinish). 
 * @author donfack
 *
 */
public class PredicateTimeEdgeState implements SwarmPredicate {
	private String name;
	private String typeName;
	private String visitorName;
	private String visitorTypeName;
	private int compType;
	private int tick;
	private boolean isWaiting;
	private boolean isReady;
	private boolean isFinish;
	
	private static PredicateTimeEdgeState instance=new PredicateTimeEdgeState();
	
	/**
	 * 
	 */
	private PredicateTimeEdgeState() {	
			
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

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorTypeName() {
		return visitorTypeName;
	}

	public void setVisitorTypeName(String visitorTypeName) {
		this.visitorTypeName = visitorTypeName;
	}

	public int getCompType() {
		return compType;
	}

	public void setCompType(int compType) {
		this.compType = compType;
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
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

	public static PredicateTimeEdgeState getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@Override
	public PredicateTimeEdgeState createInstance(String fact) {
		PredicateTimeEdgeState predicate=null;
		Pattern pattern=Pattern.compile("TimeEdgeState[(](\\w+),(\\w+),(\\w+),(\\w+),([01]),(\\d+),({true|false}),({true|false}),({true|false})[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateTimeEdgeState();
			predicate.name=matcher.group(1);
			predicate.typeName=matcher.group(2);
			predicate.visitorName=matcher.group(3);
			predicate.visitorTypeName=matcher.group(4);
			predicate.compType=Integer.parseInt(matcher.group(5));
			predicate.tick=Integer.parseInt(matcher.group(6));
			predicate.isWaiting=Boolean.parseBoolean(matcher.group(7));
			predicate.isReady=Boolean.parseBoolean(matcher.group(8));
			predicate.isFinish=Boolean.parseBoolean(matcher.group(9));
			
		}
		
		return predicate;
	}
	
		/**
	 * TimeEdgeState(NameWithTimeEdge,TypeNameWithTimeEdge,NameVisited,TypeNameVisited,
	 * Type,CountTick,IsWaiting,IsReady,IsFinish). 
	 */
	@Override
	public String toString(){
		return String.format("TimeEdgeState(%s,%s,%s,%s,%s,%d,%s,%s,%s).", name,typeName,visitorName,visitorTypeName,compType,tick,isWaiting,isReady,isFinish);
	}
	/**
	 * This gives the understanding of toString result.
	 * @return Description of the toString output.
	 */

	@Override
	public String getPredicatType() {
		return "TimeEdgeStatus";
	}
}
