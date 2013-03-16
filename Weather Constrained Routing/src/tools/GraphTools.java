package tools;

import java.util.Map;
import java.util.Random;

import config.GraphConsts;

import models.Edge;
import models.FreeTimeWindowGraph;
import models.FreeTimeWindowNode;
import models.Graph;
import models.Node;
import models.ResourceGraph;
import models.ResourceNode;
import models.TimeWindow;

public class GraphTools {
	
	public static FreeTimeWindowGraph convertResourceGraphToFreeTimeWindowGraph( ResourceGraph resourceGraph ) {
		FreeTimeWindowGraph ftwg = new FreeTimeWindowGraph();
		
		//convert resourcesnodes to freetimewindows
		/*for(ResourceNode r :resourceGraph.getNodeMap().values())
		{
			for(TimeWindow tw : r.getFreeTimeWindows()) {
				FreeTimeWindowNode ftwgNode = new FreeTimeWindowNode(r.getId(),tw);
				ftwg.addNode(ftwgNode);
			}
		}*/
		return ftwg;	
    }
	
	public static ResourceGraph convertGraphToResourceGraph( Graph graph ) {
		ResourceGraph rg = new ResourceGraph();
		
		//convert nodes to resourcesnodes
		for(Node n :graph.getNodeMap().values())
			rg.addNode(new ResourceNode(n.getId(), GraphConsts.DEFAULT_RESOURCE_NODE_CAPACITY, GraphConsts.DEFAULT_RESOURCE_NODE_DURATION,GraphConsts.MAX_TIME_STEPS));
		
		//convert edges to resourcesnodes and resourceedges
		for(Edge e :graph.getEdgeMap().values())
		{
			ResourceNode resourceNode = new ResourceNode(e.getId(), GraphConsts.DEFAULT_RESOURCE_NODE_CAPACITY, e.getWeight(),GraphConsts.MAX_TIME_STEPS);
			
			Edge resourceEdgeFrom = new Edge(rg.getNode(e.getNodeFrom().getId()),resourceNode,null,1);
			Edge resourceEdgeTo = new Edge(rg.getNode(e.getNodeTo().getId()),resourceNode,null,1);
			
			rg.addNode(resourceNode);
			rg.addEdge(resourceEdgeFrom);
			rg.addEdge(resourceEdgeTo);
		}
		
		
		return rg;	
    }
	
	public static int getMaxEdgesForGraph( int nodeCount ) {
        if  ( nodeCount < 0 ) throw new IllegalArgumentException( "nodeCount must be >= 0!" );
        if ( nodeCount == 0 ) return 0;
        int n = nodeCount - 1;
        // Use math formula sum of first n integers where n here is nodeCount - 1
        int maxEdges = ( n * n + n )/2;
        return maxEdges;
    }
    
    public static boolean hasSelfLoops(Map<String, Edge> edgeMap) {
        
		for ( Edge e : edgeMap.values() )
            if ( e.getNodeFrom() == e.getNodeTo() )
                return true;
        return false;
    }
    
    public static Node getRandomNode(Graph graph) {
    	Random generator = new Random();
    	Object[] values = graph.getNodeMap().values().toArray();
    	return (Node)values[generator.nextInt(values.length)];
    }
    
	public static Edge getRandomEdge( Random rnGen, Graph g, String [] keys, int minWeight, int maxWeight) {
        if ( g.getNodeCount() < 2 ) throw new IllegalStateException( "Attempt to add edge when < 2 nodes are in graph!" );
        if ( keys == null || keys.length != g.getNodeCount() ) throw new IllegalArgumentException( "keys argument null or wrong size!" );
        Node n1 = null;
        Node n2 = null;
        Edge retEdge = null;
        while ( true ) {
            n1 = g.getNodeMap().get( keys[ rnGen.nextInt( g.getNodeCount() ) ] );
            n2 = g.getNodeMap().get( keys[ rnGen.nextInt( g.getNodeCount() ) ] );
            if ( n1 == n2 ) // Skip if already have edge between these two nodes
                continue;
            String id = Edge.computeDefaultEdgeId( n1, n2 );
            if ( g.getEdgeMap().get( id ) != null )
                continue;
            double edgeValue = Edge.generateRandomEdgeValue(minWeight,maxWeight);
            retEdge = new Edge(n1,n2,id,edgeValue);
            break;
        }
        return retEdge;
    }
	
	public static Node getNeighborFromEdge(Edge e, Node n) {
        if(e.getNodeFrom() == n)
        {
        	return e.getNodeTo();
        }
        else if(e.getNodeTo() == n)
        {
        	return e.getNodeFrom();
        }
        else
        {
        	return null;
        }
    }
}
