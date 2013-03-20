package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.GraphConsts;
import config.MathConsts;

import models.Edge;
import models.FreeTimeWindowGraph;
import models.FreeTimeWindowNode;
import models.Node;
import models.ResourceNode;
import models.TimeWindow;
import tools.GraphTools;

public class AstarWCRSolver extends AbstractWCRSolver {
	
	private Map<Node,Double> fScore;
	private Map<Node,Double> entryTime;
	
	public AstarWCRSolver()
	{ 
		fScore = new HashMap<Node,Double>();
		entryTime = new HashMap<Node,Double>();
	}
	
	public double computeShortestDistance(FreeTimeWindowGraph graph,ResourceNode source, ResourceNode target, int startTime, int maxTimeSteps) {
    	List<Node> path = computePaths(graph, source,target, startTime, maxTimeSteps);
    	if(path != null)
    		return entryTime.get(target);
    	else
    		return -1;
    }
	
	public List<Node> computePaths(FreeTimeWindowGraph graph ,Node source, Node target, int startTime, int maxTimeSteps) {
		
		List<FreeTimeWindowNode> openSet = new ArrayList<FreeTimeWindowNode>();
		List<FreeTimeWindowNode> closedSet = new ArrayList<FreeTimeWindowNode>();
		Map<FreeTimeWindowNode,FreeTimeWindowNode> cameFrom = new HashMap<FreeTimeWindowNode,FreeTimeWindowNode>();
		Map<FreeTimeWindowNode, Integer> entryTime = new HashMap<FreeTimeWindowNode, Integer>();
		
		FreeTimeWindowNode w = graph.getFreeTimeWindowNode(new TimeWindow(startTime, maxTimeSteps),source);
		if(w!= null)
		{
			startTime = Math.max(startTime, w.getTimeWindow().getStartTime());
			
			openSet.add(w);
			fScore.put(w,(double)startTime);
			entryTime.put(w, startTime);
			
			System.out.println(w.getId() + " " + startTime );
		}
		else
			System.out.println("NO FREE TIME WINDOW FOUND AT SOURCE");
		
		while(!openSet.isEmpty())
		{
			FreeTimeWindowNode current = getMinimum(openSet);
			ResourceNode r = current.getResourceNode();
			if (r.getId().equals(target.getId()))
				return reconstructPath(cameFrom, current);
			
			closedSet.add(current);
			openSet.remove(current);
			
			int exitTime = entryTime.get(current) + (int)Math.ceil(current.getResourceNode().getDuration());
			
			for (Edge e : current.getNeighbors()) {
				FreeTimeWindowNode neighbor = (FreeTimeWindowNode)GraphTools.getNeighborFromEdge(e, current);
				if(!openSet.contains(neighbor) && neighbor.containsTime(new TimeWindow(exitTime, current.getTimeWindow().getEndTime())))
				{
					int enterTime = Math.max(exitTime, neighbor.getTimeWindow().getStartTime());
					int neighborEntryTime = entryTime.get(neighbor) != null ? entryTime.get(neighbor) : (int)MathConsts.INFINITY;
					if(enterTime < neighborEntryTime)
					{
						cameFrom.put(neighbor, current);
						entryTime.put(neighbor, enterTime);
						fScore.put(neighbor, (double) enterTime);
						openSet.add(neighbor);
						System.out.println(current.getId()+ "->"+neighbor.getId() + " " + enterTime );
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
		// TODO Euclidian distance
		return 0.0;
	}
}
