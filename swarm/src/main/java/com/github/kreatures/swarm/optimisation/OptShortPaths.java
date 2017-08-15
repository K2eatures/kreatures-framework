package com.github.kreatures.swarm.optimisation;
/**
 * This class computes all shortest paths within the scenario model.
 * Its takes all places components as inputs. 
 * 
 * @author Cedric Perez Donfack
 *
 */
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.List;

import com.github.kreatures.swarm.SwarmConst;
import com.github.kreatures.swarm.components.SwarmPlaceEdgeType;


public class OptShortPaths {
	/**
	 * The set of all the shortest paths.
	 */


	/** Default Ctor:  */
	public OptShortPaths() {

	}
	/***
	 * compute the set of all possible shortest pairs of paths 
	 * @param list of place components
	 * @return set of shortest pairs paths.
	 */
	public Set<StationNode> allShortestPaths(List<SwarmPlaceEdgeType> list){
		Set<String> allNodes=getAllStCompName(list);
		Set<String> nodes=new HashSet<>(allNodes);
		Set<StationNode> stNodes=new HashSet<>();
		Set<String> allbetweenNodes=new HashSet<>();
		for(String stCompName:nodes) {
			nodes.remove(stCompName);
			Set<StationNode> S=new HashSet<>();
			Set<StationNode> Q=new TreeSet<>();
			nodes.stream().forEach(strCompName->{
				StationNode stNode= new StationNode(stCompName, strCompName);
				stNode.setWeight(SwarmConst.MAX_INT.getValue());
				Q.add(stNode);
			});
			int currentMin=0;
			String currentNodeName=stCompName;

			while(!Q.isEmpty()) {
				allbetweenNodes.add(stCompName);
				Set<String> childs=getChildNode(currentNodeName, list);
				childs.removeAll(allbetweenNodes);

				for(String strNode:childs) {
					StationNode currenNode=null;
					for(StationNode weightNode:Q) {
						if(weightNode.checkObject(currentNodeName, strNode)!=null) {
							currenNode=weightNode;
							break;
						}
					}

					if(Q.remove(currenNode)) {
						for(SwarmPlaceEdgeType place:list) {
							if(place.checkObject(currenNode.getStCompNameIn(), currenNode.getStCompNameOut())!=null){
								int tmpWeight=currentMin+place.getWeight();
								if(tmpWeight<currenNode.getWeight()) {
									currenNode.setWeight(tmpWeight);
								}
							}
						}
						Q.add(currenNode);
					}
				}
				TreeSet<StationNode> treeQ=(TreeSet<StationNode>)Q;
				StationNode minStNode=treeQ.last();
				currentNodeName=minStNode.getStCompNameOut();
				currentMin=minStNode.getWeight();
				Q.remove(minStNode);
				S.add(minStNode);
			}
			S.remove(stNodes);
			stNodes.addAll(S);
		}
		return stNodes;
	}

	/**
	 * compute of the children's nodes of a given node.
	 * @param node parent whose children will be found
	 * @param list all the nodes which exist
	 * @return the children node of the parent
	 */
	private Set<String> getChildNode(String node,List<SwarmPlaceEdgeType> list){
		Set<String> children=new HashSet<>();
		if(node==null||list==null||list.isEmpty()) return children;

		for(SwarmPlaceEdgeType edge:list) {
			if(node.equals(edge.getFirstStationTypeName())) {
				children.add(edge.getSecondStationTypeName());
				continue;
			}

			if(node.equals(edge.getSecondStationTypeName()))
				children.add(edge.getFirstStationTypeName());
		}
		return children;
	}
	/**
	 * search all the station component which exist in the scenario model.
	 * @param list
	 * @return
	 */
	private Set<String> getAllStCompName(List<SwarmPlaceEdgeType> list){
		Set<String> stCompNames=new HashSet<>();
		if(list==null||list.isEmpty()) return stCompNames;

		list.forEach(edge->{
			stCompNames.add(edge.getFirstStationTypeName());
			stCompNames.add(edge.getSecondStationTypeName());
		});

		return stCompNames;
	}




}
