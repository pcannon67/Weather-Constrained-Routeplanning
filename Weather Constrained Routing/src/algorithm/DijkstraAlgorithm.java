package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import config.MathConsts;

import tools.GraphTools;

import models.Edge;
import models.Graph;
import models.Node;

public class DijkstraAlgorithm {
	
	private Map<Node,Node> previous;
	private Map<Node,Double> distance;
	private Graph graph;
	
	public DijkstraAlgorithm(Graph graph)
	{ 
		distance = new HashMap<Node, Double>();
		previous = new HashMap<Node, Node>();
		this.graph = graph;
	}
	
    public double computeShortestDistance(Node source, Node target) {
    	computePaths(source);
        return distance.get(target);
    }
    
    public Stack<Node> computeShortestPath(Node source, Node target) {
    	Stack<Node> path = new Stack<Node>();
    	
    	computePaths(source);
    	
    	Node u = target;
    	while(previous.get(u) != null)
    	{
    		path.push(u);
    		u = previous.get(u);
    	}
    	
    	path.push(source);
    	
    	return path;
    }
	
	private void computePaths(Node source)
    {
		for ( Node n : graph.getNodeMap().values())
		{
			distance.put(n, MathConsts.INFINITY);
			previous.put(n, null);
		}
		
		distance.put(source, 0.0);
		
		Map<String, Node> nodes = graph.getNodeMap();
		
		while(!nodes.isEmpty())
		{
			Node u = getMinimum(nodes);
			nodes.remove(u.getId());
			
			if(distance.get(u) == MathConsts.INFINITY) break;
			
			for(Edge e : u.getNeighbors())
			{
				Node neighbor = GraphTools.getNeighborFromEdge(e, u);
				double alt = distance.get(u) + e.getWeight();
				if(alt < distance.get(neighbor) - MathConsts.EPSILON)
				{
					distance.put(neighbor, alt);
					previous.put(neighbor, u);
				}
			}
		}
    }
	
	private Node getMinimum(Map<String,Node> nodes) {
		Node min = null;
		for (Node node : nodes.values()) {
			if(min == null || distance.get(node) < distance.get(min)) {
				min = node;
			}
		}
	    
	    return min;
	  }
}
