package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.GraphTools;

import models.Edge;
import models.Graph;
import models.Node;

public class AstarAlgorithm {
	
	private Map<Node,Double> fScore;
	
	public AstarAlgorithm()
	{ 
		fScore = new HashMap<Node,Double>();
	}
	
	public List<Node> computePaths(Node source,Node target) {
		
		List<Node> closedSet = new ArrayList<Node>();
		List<Node> openSet = new ArrayList<Node>();
		Map<Node,Node> cameFrom = new HashMap<Node,Node>();
		Map<Node,Double> gScore = new HashMap<Node,Double>();
		
		openSet.add(source);
		
		gScore.put(source,0.0);
		fScore.put(source,gScore.get(source)+heuristicCostEstimate(source,target));
		
		while(!openSet.isEmpty()) {
			Node current = getMinimum(openSet);
			if(current == target) return reconstructPath(cameFrom,target);
			
			openSet.remove(current);
			closedSet.add(current);
			
			for(Edge e : current.getNeighbors())
			{
				Node neighbor = GraphTools.getNeighborFromEdge(e, current);
				double tentativeGScore = gScore.get(current) + e.getEdgeValue();
				if(closedSet.contains(neighbor)) {
					if(tentativeGScore >= gScore.get(neighbor))
						continue;	
				}
				
				if(!openSet.contains(neighbor) || tentativeGScore < gScore.get(neighbor)) {
					cameFrom.put(neighbor, current);
					gScore.put(neighbor,tentativeGScore);
					fScore.put(neighbor,gScore.get(current)+heuristicCostEstimate(neighbor,target));
					if(!openSet.contains(neighbor))
						openSet.add(neighbor);
				}
			}
		}
		
		return null;
		
    }
	
	private List<Node> reconstructPath(Map<Node, Node> cameFrom, Node currentNode) {
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

	private Node getMinimum(List<Node> nodes) {
		Node min = null;
		for (Node node : nodes) {
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
