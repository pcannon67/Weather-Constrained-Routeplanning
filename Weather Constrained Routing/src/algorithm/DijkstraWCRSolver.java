package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class DijkstraWCRSolver extends AbstractWCRSolver {
	
	private Map<FreeTimeWindowNode,FreeTimeWindowNode> previous;
	private Map<FreeTimeWindowNode,Double> distance;
	private Map<FreeTimeWindowNode,Integer> entryTime;
	private List<Node> path;
	
	public DijkstraWCRSolver(Graph graph)
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
		distance = new HashMap<FreeTimeWindowNode, Double>();
		entryTime = new HashMap<FreeTimeWindowNode, Integer>();
		previous = new HashMap<FreeTimeWindowNode, FreeTimeWindowNode>();
		List<FreeTimeWindowNode> nodes = new ArrayList<FreeTimeWindowNode>();
		
		FreeTimeWindowNode w = ((FreeTimeWindowGraph)graph).getFreeTimeWindowNode(new TimeWindow(startTime, maxTimeSteps),source);
		if(w!= null) {
			startTime = Math.max(startTime, w.getTimeWindow().getStartTime());
			
			nodes.add(w);
			entryTime.put(w, startTime);
			distance.put(w,0.0);
			previous.put(w, null);
		}
		
		while(!nodes.isEmpty())
		{
			FreeTimeWindowNode current = getMinimum(nodes,entryTime);
			ResourceNode r = current.getResourceNode();
			//System.out.print("\nMIN:"+r.getId());
			if (r.getId().equals(target.getId())) {
				path = reconstructPath(previous, current);
				return path;
			}
			
			nodes.remove(current);
			
			int exitTime = entryTime.get(current) + (int)Math.ceil(current.getResourceNode().getDuration());
			
			for (Edge e : current.getNeighbors()) {
				FreeTimeWindowNode neighbor = (FreeTimeWindowNode)GraphTools.getNeighborFromEdge(e, current);
				
				if(neighbor.containsTime(new TimeWindow(exitTime, current.getTimeWindow().getEndTime()))) {
					int enterTime = Math.max(exitTime, neighbor.getTimeWindow().getStartTime());
					int neighborEntryTime = entryTime.get(neighbor) != null ? entryTime.get(neighbor) : (int)MathConsts.INFINITY;
					
					if(enterTime < neighborEntryTime) {
						previous.put(neighbor, current);
						entryTime.put(neighbor, enterTime);
						distance.put(neighbor, distance.get(current)+neighbor.getResourceNode().getDuration());
						nodes.add(neighbor);
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

	private FreeTimeWindowNode getMinimum(List<FreeTimeWindowNode> nodes, Map<FreeTimeWindowNode, Integer> fScore) {
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
