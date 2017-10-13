/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.kreatures.core.Perception;

import net.sf.tweety.logics.fol.syntax.FolFormula;

/**
 * %ItemSetLoadingStation(StationTypeNameOut,StationNameIn,ItemNumber)
 * @author donfack
 *
 */
public class PredicateItemSetLoadingStation extends SwarmPredicate {

	private String stationOutTypeName;
	private String stationInName;
	private int itemNumber;

	public PredicateItemSetLoadingStation(FolFormula desire) {
		super(desire);
		createInstance(desire);
	}

	public PredicateItemSetLoadingStation(FolFormula desire, Perception reason) {
		super(desire, reason);
		createInstance(desire);
	}

	public PredicateItemSetLoadingStation(PredicateItemSetLoadingStation other) {
		super(other);
		this.stationOutTypeName=other.stationOutTypeName;
		this.stationInName=other.stationInName;
		this.itemNumber=other.itemNumber;
	}	
	
	@Override
	public PredicateItemSetLoadingStation clone() {
		return new PredicateItemSetLoadingStation(this);
	}

	public String getStationOutTypeName() {
		return stationOutTypeName;
	}

	public void setStationOutTypeName(String stationOutTypeName) {
		this.stationOutTypeName = stationOutTypeName;
	}

	public String getStationInName() {
		return stationInName;
	}

	public void setStationInName(String stationInName) {
		this.stationInName = stationInName;
	}

	public int getItemNumber() {
		return itemNumber;
	}

	public void incrItemNumber(int itemNber) {
		this.itemNumber+= itemNber;
	}
	public void decrItemNumber(int itemNber) {
		itemNber=this.itemNumber-itemNber;
		if(itemNber<=0) {
			this.itemNumber=0;
			return;
		}
		this.itemNumber= itemNber;
	}
	/**
	 * %ItemSetLoadingStation(StationTypeNameOut,StationNameIn,ItemNumber)
	 */
	@Override
	public String toString() {
		return String.format("ItemSetLoadingStation(%s,%s,%d)", stationOutTypeName, stationInName, itemNumber);
	}
	
	@Override
	public void createInstance(FolFormula atom) {
//		PredicateItemSetLoadingStation predicate=null;
		Pattern pattern=Pattern.compile("ItemSetLoadingStation[(](\\w+),(\\w+),(\\d+)[)]");
		Matcher matcher=pattern.matcher(atom.toString());
		if(matcher.find()) {
//			predicate=new PredicateItemSetLoadingStation();
			this.stationInName=matcher.group(1);
			this.stationOutTypeName=matcher.group(2);
			this.itemNumber=Integer.parseInt(matcher.group(3));
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PredicateItemSetLoadingStation))
			return false;
		
		PredicateItemSetLoadingStation obj=(PredicateItemSetLoadingStation)other;
		boolean isName=obj.stationInName==null?this.stationInName==null:obj.stationInName.equals(this.stationInName);
		boolean isTypeName=obj.stationOutTypeName==null?this.stationOutTypeName==null:obj.stationOutTypeName.equals(this.stationOutTypeName);
		
		return isName & isTypeName;
	}
	
	@Override
	public int hashCode() {
		return this.stationInName.hashCode()* 11;
	}
}
