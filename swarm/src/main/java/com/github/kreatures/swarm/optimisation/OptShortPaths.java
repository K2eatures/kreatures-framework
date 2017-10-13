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
import java.util.function.Predicate;
import java.util.Collection;
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
	public static Set<StationNode> allShortestPaths(Collection<SwarmPlaceEdgeType> list){		
		Set<String> allNodes=getAllStCompName(list);
		Set<String> nodes=new HashSet<>(allNodes);
		Set<StationNode> stNodes=new HashSet<>();
		Set<String> allbetweenNodes=new HashSet<>();
		allNodes.stream().forEach(stCompName->{

			//nodes.remove(stCompName);
			Set<StationNode> S=new HashSet<>();
			Set<StationNode> Q=new HashSet<>();
			nodes.stream().filter(predicate->!predicate.equals(stCompName)).forEach(strCompName->{
				StationNode stNode=stNodes.stream().filter(predicate->predicate.checkObject(stCompName, strCompName))
				.findFirst().orElseGet(()->{
					StationNode tmp= new StationNode(stCompName, strCompName);
						tmp.setWeight(SwarmConst.MAX_INT.getValue());
						return tmp;
					}); 
				Q.add(stNode);
			});
			int currentMin=0;
			String currentNodeName=stCompName;
//			allbetweenNodes.add(stCompName);
//			Set<StationNode>alreadyInQ=new HashSet<>();
			while(!Q.isEmpty()) {
				
				Set<String> childs=getChildNode(currentNodeName, list);
//				allbetweenNodes.stream().filter(predicate->childs.contains(predicate))
//				.forEach(action->{
//					Set<StationNode> containsNodes=stNodes.stream().filter(predicate->
//								predicate.checkObject(action, stCompName))
//							.collect(HashSet::new,HashSet::add,HashSet::addAll);
//					containsNodes.removeAll(alreadyInQ);
//					alreadyInQ.addAll(containsNodes);
//					Q.addAll(containsNodes);
//				});
				childs.remove(stCompName);
				for(String strNode:childs) {
					StationNode currentNode=null;
					for(StationNode weightNode:Q) {
						if(weightNode.checkObject(stCompName, strNode)) {
							currentNode=weightNode;
							break;
						}
					}

					if(Q.remove(currentNode)) {
						for(SwarmPlaceEdgeType place:list) {
							if(place.checkObject(currentNodeName, strNode)!=null){
								int tmpWeight=currentMin+place.getWeight();
								if(tmpWeight<currentNode.getWeight()) {
									currentNode.setWeight(tmpWeight);
								}
							}
						}
						Q.add(currentNode);
					}
				}
				StationNode minStNode=Q.stream().min(StationNode::compareTo).get();
				currentNodeName=minStNode.getStCompNameOut().equals(stCompName)?minStNode.getStCompNameIn():minStNode.getStCompNameOut();
				currentMin=minStNode.getWeight();
				Q.remove(minStNode);
				S.add(minStNode);
			}
			//S.remove(stNodes);
			stNodes.addAll(S);
		});
		return stNodes;
	}

	/**
	 * compute of the children's nodes of a given node.
	 * @param node parent whose children will be found
	 * @param list all the nodes which exist
	 * @return the children node of the parent
	 */
	private static Set<String> getChildNode(String node,Collection<SwarmPlaceEdgeType> list){
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
	private static Set<String> getAllStCompName(Collection<SwarmPlaceEdgeType> list){
		Set<String> stCompNames=new HashSet<>();
		if(list==null||list.isEmpty()) return stCompNames;

		list.forEach(edge->{
			stCompNames.add(edge.getFirstStationTypeName());
			stCompNames.add(edge.getSecondStationTypeName());
		});

		return stCompNames;
	}




}
