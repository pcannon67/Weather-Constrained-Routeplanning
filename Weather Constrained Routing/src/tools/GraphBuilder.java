package tools;

import java.util.Random;

import models.Edge;
import models.Graph;
import models.Node;

public class GraphBuilder {
	
	//make a random euclidean graph
	public static Graph createRandomEuclideanGraph( int nodeCount, double density, int width, int height) {
		if ( nodeCount < 1 ) throw new IllegalArgumentException( "nodeCount must be >= 1" );
        
        //Create empty Graph object
        Graph g = new Graph();
        
        for ( int i = 0; i < nodeCount; ++i ) {
            String nodeId = Integer.toString( g.getNodeCount() );
            Node n = new Node(nodeId, (int)(Math.random()*width), (int)(Math.random()*height));
            g.addNode( n );
            //System.out.println(n.getId());
        }
        
        for (Node n : g.getNodeMap().values()) {
            for (Node u : g.getNodeMap().values()) {
            	//System.out.println(u.getId());
            	if(u==n) continue;
            	if (Node.distanceTo(n,u) < density) {
            		Edge e = new Edge(n,u,null,(int)(Node.distanceTo(n,u)));
					//if(!g.hasEdge(e)) 
                    	g.addEdge( e );
					//}
               }
            }
         }
        
        return g;
    }
		
	//make a random graph
	public static Graph createRandomGraph( int nodeCount, int edgeCount, int minWeight, int maxWeight) {
        if ( nodeCount < 1 || edgeCount < 0 ) throw new IllegalArgumentException( "nodeCount must be >= 1 and edgeCount must be >= 0!" );
        Random rnGen = new Random( System.currentTimeMillis() );
        int maxEdges = GraphTools.getMaxEdgesForGraph(nodeCount);
        if ( edgeCount > maxEdges )
            throw new IllegalArgumentException( "Input edgeCount (" + edgeCount + ") exceeds maximum possible edges for graph with " + nodeCount + " nodes!" );
        // Create empty Graph object
        Graph g = new Graph();
        // Create temp array to hold node keys - required for getRandomEdge()
        String [] nodeKeys = new String [ nodeCount ];
        // Create and add nodeList
        for ( int i = 0; i < nodeCount; ++i ) {
            String nodeId = Integer.toString( g.getNodeCount() );
            nodeKeys[ i ] = nodeId;
            Node n = new Node( nodeId );
            g.addNode( n ); // Let list index be node's id
        }
        // Create and add edgeList
        for ( int i = 0; i < edgeCount; ++i ) {
            Edge e = GraphTools.getRandomEdge( rnGen, g, nodeKeys,minWeight,maxWeight);
            if(!g.hasEdge(e))
            	g.addEdge( e );
        }
        return g;
    }
}


