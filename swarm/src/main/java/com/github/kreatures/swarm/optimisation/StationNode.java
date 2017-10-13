package com.github.kreatures.swarm.optimisation;

public class StationNode implements Comparable<StationNode> {

	private String stCompNameIn;
	private String stCompNameOut;
	private int weight;

	public StationNode(String stCompNameIn,String stCompNameOut) {
		this(stCompNameIn,stCompNameOut,0);
	}

	public StationNode(String stCompNameIn,String stCompNameOut, int weight) {
		this.stCompNameIn=stCompNameIn;
		this.stCompNameOut=stCompNameOut;
		this.weight=weight;
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
		if(!(other instanceof StationNode)) return false;
		StationNode obj=(StationNode)other;
	
		boolean isName=obj.stCompNameIn==null?this.stCompNameIn==null:obj.stCompNameIn.equals(this.stCompNameIn);
		boolean isTypeName=obj.stCompNameOut==null?this.stCompNameOut==null:obj.stCompNameOut.equals(this.stCompNameOut);
		
		boolean isName1=obj.stCompNameIn==null?this.stCompNameOut==null:obj.stCompNameIn.equals(this.stCompNameOut);
		boolean isTypeName1=obj.stCompNameOut==null?this.stCompNameIn==null:obj.stCompNameOut.equals(this.stCompNameIn);
		
		return (isName & isTypeName)|(isName1 & isTypeName1);	
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
		//return String.format("ShortPah(%s,%s,%d).", stCompNameIn,stCompNameOut,weight);
		return String.format("%s,%s,%d", stCompNameIn,stCompNameOut,weight);
	}

	@Override
	public int hashCode() {
		return (stCompNameIn.hashCode()+stCompNameOut.hashCode())*11;
	}

	@Override
	public int compareTo(StationNode other) {
				
		return Integer.compare(this.weight, other.weight);
	}
	
	public static StationNode parseToStationNode(String edge) {
		String[] splitEdge=edge.split(",");
		StationNode node=new StationNode(splitEdge[0], splitEdge[1],Integer.parseInt(splitEdge[2]));
		return node;
	}
	
}