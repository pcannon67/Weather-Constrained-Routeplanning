package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.GraphConsts;
import config.MathConsts;

import tools.GraphTools;

import math.MathTools;
import models.Edge;
import models.Graph;
import models.Node;

public class AstarAlgorithm extends AbstractWCRSolver {
	
	private Map<Node,Double> fScore;
	private Map<Node,Double> distance;
	private Map<Node,Double> entryTime;
	private List<Node> path;
	
	public AstarAlgorithm(Graph graph) {
		super(graph);
	}
	
	@Override
	public int pathLengthTo(Node target) {
		if(path != null)
			return path.indexOf(target) + 1;
    	else
    		return 0;
	}
	
	@Override
	public double distanceTo(Node target) {
    	if(path != null)
    		return distance.get(target);
    	else
    		return 0;
	}

	@Override
	public double timeTo(Node target) {
		if(path != null)
    		return entryTime.get(target);
    	else
    		return 0;
	}
	
	@Override
	public List<Node> pathTo(Node source, Node target, int startTime, int maxTimeSteps) {
		List<Node> closedSet = new ArrayList<Node>();
		List<Node> openSet = new ArrayList<Node>();
		Map<Node,Node> cameFrom = new HashMap<Node,Node>();
		fScore = new HashMap<Node,Double>();
		distance = new HashMap<Node,Double>();
		entryTime = new HashMap<Node,Double>();
		
		openSet.add(source);
		
		distance.put(source,0.0);
		entryTime.put(source,0.0);
		fScore.put(source,distance.get(source)+heuristicCostEstimate(source,target));
		
		while(!openSet.isEmpty()) {
			Node current = getMinimum(openSet);
			
			if(current == target) {
				path = reconstructPath(cameFrom,target);
				return path;
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			for(Edge e : current.getNeighbors())
			{
				Node neighbor = GraphTools.getNeighborFromEdge(e, current);
				
				double tentativeGScore = distance.get(current) + e.getWeight();
				if(closedSet.contains(neighbor)) {
					if(tentativeGScore >= distance.get(neighbor))
						continue;	
				}
				
				if(!openSet.contains(neighbor) || tentativeGScore < distance.get(neighbor)) {
					cameFrom.put(neighbor, current);
					distance.put(neighbor,tentativeGScore);
					entryTime.put(neighbor,tentativeGScore/GraphConsts.VEHICLE_SPEED);
					fScore.put(neighbor,tentativeGScore+heuristicCostEstimate(neighbor,target));
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
		return Node.distanceTo(source, target);
	}
}