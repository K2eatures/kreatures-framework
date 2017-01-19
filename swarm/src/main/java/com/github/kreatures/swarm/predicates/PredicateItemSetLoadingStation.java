/**
 * 
 */
package com.github.kreatures.swarm.predicates;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author donfack
 *
 */
public class PredicateItemSetLoadingStation implements SwarmPredicate {

	private String stationOutTypeName;
	private String stationInName;
	private int itemNumber;

	private static PredicateItemSetLoadingStation instance=new PredicateItemSetLoadingStation();
	private PredicateItemSetLoadingStation() {
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
	public String getPredicatType() {
		return "ItemSetLoadingStation";
	}

	public static PredicateItemSetLoadingStation getInstance(String fact){
		return instance.createInstance(fact);
	}
	
	@Override
	public PredicateItemSetLoadingStation createInstance(String fact) {
		PredicateItemSetLoadingStation predicate=null;
		Pattern pattern=Pattern.compile("ItemSetLoadingStation[(](\\w+),(\\w+),(\\d+)[)].");
		Matcher matcher=pattern.matcher(fact);
		if(matcher.find()) {
			predicate=new PredicateItemSetLoadingStation();
			predicate.stationInName=matcher.group(1);
			predicate.stationOutTypeName=matcher.group(2);
			predicate.itemNumber=Integer.parseInt(matcher.group(3));
		
		}
		
		return predicate;
	}

}
