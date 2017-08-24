package com.github.kreatures.swarm.optimisation;

import com.github.kreatures.swarm.Utility;

public class StationNode implements Comparable<StationNode> {

	private String stCompNameIn;
	private String stCompNameOut;
	private int weight;

	public StationNode(String stCompNameIn,String stCompNameOut) {
		this.stCompNameIn=stCompNameIn;
		this.stCompNameOut=stCompNameOut;
	}

	public String getStCompNameIn() {
		return stCompNameIn;
	}

	public String getStCompNameOut() {
		return stCompNameOut;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public boolean equals(Object other) {
		if(other==null) return false;
		StationNode obj=(StationNode)other;
		if(obj.stCompNameIn==null || obj.stCompNameOut==null) return false;

		if(obj.stCompNameIn.equals(this.stCompNameIn)&& obj.stCompNameOut.equals(this.stCompNameOut))
			return true;
		if(obj.stCompNameIn.equals(this.stCompNameOut)&& obj.stCompNameOut.equals(this.stCompNameIn))
			return true;	

		return false;	
	}

	/**
	 * check whether the given first and second name equals a {@link StationNode}. 
	 * @param str1 the first or second component name
	 * @param str2 the first or second component name
	 * @return the corresponding {@link StationNode}'s object or null otherwise.
	 */
	public boolean checkObject(String str1,String str2) {
		if(str1==null||str2==null) return false;
		if((str1.equals(stCompNameIn)&& str2.equals(stCompNameOut))||(str2.equals(stCompNameIn)&& str1.equals(stCompNameOut)))
			return true;
		return false;
	}
	
	/* ShortPath(StationComponentName1,StationComponentName2,minWeght).*/
	@Override
	public String toString() {
		return String.format("ShortPah(%s,%s,%d).", stCompNameIn,stCompNameOut,weight);
	}

	@Override
	public int hashCode() {
		return Utility.computeHashCode(1, stCompNameIn,stCompNameIn,weight);
	}

	@Override
	public int compareTo(StationNode other) {
		if(this.equals(other)) return 0;
		
		int compare=this.weight==other.weight?0:this.weight>other.weight?1:-1;
		return compare;
	}
	
}