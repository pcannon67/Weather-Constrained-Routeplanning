package tools;

import java.util.Map;
import java.util.Random;

import models.Edge;
import models.Graph;
import models.Node;

public class GraphTools {
	
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
    
	public static Edge getRandomEdge( Random rnGen, Graph g, String [] keys, int minEdgeValue, int maxEdgeValue) {
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
            double edgeValue = Edge.generateRandomEdgeValue(minEdgeValue,maxEdgeValue);
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
