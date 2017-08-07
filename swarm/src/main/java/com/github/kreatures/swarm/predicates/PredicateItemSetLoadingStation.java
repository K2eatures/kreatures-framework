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
	
	@Override
	public int hashCode() {
		return (super.hashCode() +
				(this.toString() == null ? 0 : this.toString().hashCode())) * 11;
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

	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * %ItemSetLoadingStation(StationTypeNameOut,StationNameIn,ItemNumber)
	 */
	@Override
	public String toString() {
		return String.format("ItemSetLoadingStation(%s,%s,%d).", stationOutTypeName, stationInName, itemNumber);
	}
	
	@Override
	public void createInstance(FolFormula atom) {
//		PredicateItemSetLoadingStation predicate=null;
		Pattern pattern=Pattern.compile("ItemSetLoadingStation[(](\\w+),(\\w+),(\\d+)[)].");
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
		if (other == null || !(other instanceof PredicateItemSetLoadingStation))
			return false;
		PredicateItemSetLoadingStation obj = (PredicateItemSetLoadingStation) other;

		if (obj.getStationInName() == null || this.getStationInName() == null) {
			return false;
		}
		if (obj.getStationOutTypeName() == null || this.getStationOutTypeName() == null) {
			return false;
		}

		if (obj.getStationInName().equals(this.getStationInName())
				&& obj.getStationOutTypeName().equals(this.getStationOutTypeName())) {
			return true;
		}

		return false;
	}

}
