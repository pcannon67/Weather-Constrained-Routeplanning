package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Edge;
import models.FreeTimeWindowGraph;
import models.FreeTimeWindowNode;
import models.Node;
import models.ResourceNode;
import tools.GraphTools;

public class AstarWCRSolver extends AbstractWCRSolver {
	
	private Map<Node,Double> fScore;
	private Map<Node,Double> entryTime;
	
	public AstarWCRSolver()
	{ 
		fScore = new HashMap<Node,Double>();
		entryTime = new HashMap<Node,Double>();
	}
	
	public double computeShortestDistance(FreeTimeWindowGraph graph,ResourceNode source, ResourceNode target, int startTime) {
    	List<Node> path = computePaths(graph, source,target, startTime);
    	if(path != null)
    		return entryTime.get(target);
    	else
    		return -1;
    }
	
	public List<Node> computePaths(FreeTimeWindowGraph graph ,ResourceNode source, ResourceNode target, int startTime) {
		
		List<FreeTimeWindowNode> openSet = new ArrayList<FreeTimeWindowNode>();
		List<FreeTimeWindowNode> closedSet = new ArrayList<FreeTimeWindowNode>();
		Map<FreeTimeWindowNode,FreeTimeWindowNode> cameFrom = new HashMap<FreeTimeWindowNode,FreeTimeWindowNode>();
		Map<FreeTimeWindowNode, Integer> entryTime = new HashMap<FreeTimeWindowNode, Integer>();
		
		FreeTimeWindowNode w = graph.getFreeTimeWindowNode(startTime,source);
		if(w!= null)
		{
			openSet.add(w);
			fScore.put(w,0.0);
			entryTime.put(w, startTime);
		}
		
		while(!openSet.isEmpty())
		{
			FreeTimeWindowNode current = getMinimum(openSet);
			closedSet.add(current);
			ResourceNode r = current.getResourceNode();
			if (r == target)
				return reconstructPath(cameFrom, current);
			
			int exitTime = entryTime.get(current) + (int)Math.ceil(current.getResourceNode().getDuration());
			
			for (Edge e : current.getNeighbors()) {
				FreeTimeWindowNode neighbor = (FreeTimeWindowNode)GraphTools.getNeighborFromEdge(e, current);
				if(!closedSet.contains(neighbor) && neighbor.containsTime(exitTime))
				{
					int enterTime = Math.max(exitTime, neighbor.getTimeWindow().getStartTime());
					if(enterTime < entryTime.get(neighbor))
					{
						cameFrom.put(neighbor, current);
						entryTime.put(w, enterTime);
						fScore.put(w, (double) enterTime);
						openSet.add(neighbor);
					}
				}
			}
		}
		
		return null;
    }
	
	private List<Node> reconstructPath(Map<FreeTimeWindowNode, FreeTimeWindowNode> cameFrom, Node currentNode) {
		if(cameFrom.get(currentNode) != null) {
			List<Node> p = reconstructPath(cameFrom, cameFrom.get(currentNode));
			p.add(currentNode);
			return p;
		}
		else {
			List<Node> p = new ArrayList<Node>();
			p.add(currentNode);
			return p;
		}
	}

	private FreeTimeWindowNode getMinimum(List<FreeTimeWindowNode> nodes) {
		FreeTimeWindowNode min = null;
		for (FreeTimeWindowNode node : nodes) {
			if(min == null || fScore.get(node) < fScore.get(min))
				min = node;
		}
	    
	    return min;
	  }

	private double heuristicCostEstimate(Node source, Node target) {
		// TODO Auto-generated method stub
		return 0.0;
	}
}
