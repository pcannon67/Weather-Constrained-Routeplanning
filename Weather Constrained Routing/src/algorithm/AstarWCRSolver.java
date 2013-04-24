package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.GraphConsts;
import config.MathConsts;

import math.MathTools;
import models.Edge;
import models.FreeTimeWindowGraph;
import models.FreeTimeWindowNode;
import models.Graph;
import models.Node;
import models.ResourceNode;
import models.TimeWindow;
import tools.GraphTools;

public class AstarWCRSolver extends AbstractWCRSolver {
	
	private Map<FreeTimeWindowNode,Integer> entryTime;
	private Map<FreeTimeWindowNode,Double> distance;
	private List<Node> path;
	
	public AstarWCRSolver(Graph graph)
	{ 
		super(graph);
	}

	@Override
	public int pathLengthTo(Node target) {
		if(path != null) {
			int count=0;
    		for (Node node : path) {
    			count++;
				if(node.getId().equals(target.getId()))
					return count;
			}
    	}
    	
    	return 0;
	}
	
	@Override
	public double distanceTo(Node target) {
		if(path != null) {
    		for (FreeTimeWindowNode node : distance.keySet()) {
				if(node.getResourceNode().getId().equals(target.getId()))
					return distance.get(node);
			}
    	}
    	
    	return 0;
	}
	
	@Override
	public double timeTo(Node target) {
		if(path != null) {
    		for (FreeTimeWindowNode node : entryTime.keySet()) {
				if(node.getResourceNode().getId().equals(target.getId()))
					return entryTime.get(node);
			}
    	}
    	
    	return 0;
	}

	@Override
	public List<Node> pathTo(Node source, Node target, int startTime, int maxTimeSteps) {
		List<FreeTimeWindowNode> openSet = new ArrayList<FreeTimeWindowNode>();
		List<FreeTimeWindowNode> closedSet = new ArrayList<FreeTimeWindowNode>();
		Map<FreeTimeWindowNode,FreeTimeWindowNode> cameFrom = new HashMap<FreeTimeWindowNode,FreeTimeWindowNode>();
		Map<FreeTimeWindowNode,Double> fScore = new HashMap<FreeTimeWindowNode,Double>();
		entryTime = new HashMap<FreeTimeWindowNode,Integer>();
		distance = new HashMap<FreeTimeWindowNode,Double>();
		
		FreeTimeWindowNode w = ((FreeTimeWindowGraph)graph).getFreeTimeWindowNode(new TimeWindow(startTime, maxTimeSteps),source);
		if(w!= null) {
			startTime = Math.max(startTime, w.getTimeWindow().getStartTime());
			
			openSet.add(w);
			fScore.put(w,startTime+heuristicCostEstimate(w.getResourceNode(),target));
			entryTime.put(w, startTime);
			distance.put(w,0.0);
		}
		
		while(!openSet.isEmpty())
		{
			FreeTimeWindowNode current = getMinimum(openSet,fScore);
			ResourceNode r = current.getResourceNode();
			//System.out.print("\nMIN:"+r.getId());
			if (r.getId().equals(target.getId())) {
				path = reconstructPath(cameFrom, current);
				return path;
			}
			
			closedSet.add(current);
			openSet.remove(current);
			
			int exitTime = entryTime.get(current) + (int)Math.ceil(current.getResourceNode().getDuration());
			
			for (Edge e : current.getNeighbors()) {
				FreeTimeWindowNode neighbor = (FreeTimeWindowNode)GraphTools.getNeighborFromEdge(e, current);
				
				if(neighbor.containsTime(new TimeWindow(exitTime, current.getTimeWindow().getEndTime()))) {
					int enterTime = Math.max(exitTime, neighbor.getTimeWindow().getStartTime());
					int neighborEntryTime = entryTime.get(neighbor) != null ? entryTime.get(neighbor) : (int)MathConsts.INFINITY;
					
					if(closedSet.contains(neighbor)) {
						if(enterTime >= neighborEntryTime){
							continue;	
						}
					}
					
					if(!openSet.contains(neighbor) || enterTime < neighborEntryTime) {
						cameFrom.put(neighbor, current);
						entryTime.put(neighbor, enterTime);
						distance.put(neighbor, distance.get(current)+neighbor.getResourceNode().getDuration());
						fScore.put(neighbor, enterTime+heuristicCostEstimate(current.getResourceNode(),neighbor.getResourceNode()));
						openSet.add(neighbor);
						//System.out.print("\n"+current.getId() + "->" + neighbor.getId() + " " + exitTime);
					}
				}
			}
		}
		
		return null;
	}
	
	private List<Node> reconstructPath(Map<FreeTimeWindowNode, FreeTimeWindowNode> cameFrom, FreeTimeWindowNode currentNode) {
		if(cameFrom.get(currentNode) != null) {
			List<Node> p = reconstructPath(cameFrom, cameFrom.get(currentNode));
			if(currentNode.getResourceNode().isANode())
				p.add(currentNode.getResourceNode());
			return p;
		}
		else {
			List<Node> p = new ArrayList<Node>();
			if(currentNode.getResourceNode().isANode())
				p.add(currentNode.getResourceNode());
			return p;
		}
	}

	private FreeTimeWindowNode getMinimum(List<FreeTimeWindowNode> nodes, Map<FreeTimeWindowNode, Double> fScore) {
		FreeTimeWindowNode min = null;
		for (FreeTimeWindowNode node : nodes) {
			if(min == null || fScore.get(node) < fScore.get(min))
				min =  node;
		}
	    
	    return min;
	  }

	private double heuristicCostEstimate(Node source, Node target) {
		return MathTools.euclideanDistance(source.getX(),source.getY(), target.getX(), target.getY());
	}
}
