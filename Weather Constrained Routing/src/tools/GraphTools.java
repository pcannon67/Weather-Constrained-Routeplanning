package tools;

import java.util.List;
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
	
	public static FreeTimeWindowGraph convertGraphToFreeTimeWindowGraph( Graph graph ) {
		return convertResourceGraphToFreeTimeWindowGraph(convertGraphToResourceGraph(graph));
	}
	
	public static FreeTimeWindowGraph convertResourceGraphToFreeTimeWindowGraph( ResourceGraph resourceGraph ) {
		FreeTimeWindowGraph ftwg = new FreeTimeWindowGraph();
		
		//convert resourcesnodes to freetimewindows
		for(Node n : resourceGraph.getNodeMap()) {
			ResourceNode r = (ResourceNode) n;
			
			List<TimeWindow> freeTimeWindows =  r.getFreeTimeWindows();
			for (int i = 0; i < r.getFreeTimeWindows().size(); i++)
				ftwg.addNode(new FreeTimeWindowNode(r.getId()+"_"+i,freeTimeWindows.get(i),r));
		}
		
		for(Edge e : resourceGraph.getEdgeMap()) {
			List<FreeTimeWindowNode> freeTimeWindowsSource = ftwg.getFreeTimeWindowNodes((ResourceNode)e.getNodeFrom());
			List<FreeTimeWindowNode> freeTimeWindowsTarget = ftwg.getFreeTimeWindowNodes((ResourceNode)e.getNodeTo());
			
			for(FreeTimeWindowNode u : freeTimeWindowsSource) {
				for(FreeTimeWindowNode  v : freeTimeWindowsTarget) {
					if(u.getExitWindow().isOverLappingWithTimeWindow(v.getEntryWindow())) {
						ftwg.addEdge(new Edge(u, v));
					}
				}
			}
		}
		
		return ftwg;	
    }
	
	public static ResourceGraph convertGraphToResourceGraph( Graph graph ) {
		ResourceGraph rg = new ResourceGraph();
		
		//convert nodes to resourcesnodes
		for(Node n :graph.getNodeMap())
			rg.addNode(new ResourceNode(n.getId(), n.getX(), n.getY(), GraphConsts.DEFAULT_RESOURCE_NODE_CAPACITY, GraphConsts.DEFAULT_RESOURCE_NODE_DURATION,GraphConsts.MAX_TIME_STEPS));
		
		//convert edges to resourcesnodes and resourceedges
		for(Edge e :graph.getEdgeMap())
		{
			ResourceNode resourceNode = new ResourceNode(e.getId(),e.getNodeTo().getX(),e.getNodeTo().getY(), GraphConsts.DEFAULT_RESOURCE_NODE_CAPACITY, e.getWeight(), GraphConsts.MAX_TIME_STEPS);
			
			Edge resourceEdgeFrom = new Edge(resourceNode,rg.getNode(e.getNodeTo().getId()),null,1);
			Edge resourceEdgeTo = new Edge(rg.getNode(e.getNodeFrom().getId()),resourceNode,null,1);
			
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
    	Object[] values = graph.getNodeMap().toArray();
    	return (Node)values[generator.nextInt(values.length)];
    }
    
	public static Edge getRandomEdge( Random rnGen, Graph g, String [] keys, int minWeight, int maxWeight) {
        if ( g.getNodeCount() < 2 ) throw new IllegalStateException( "Attempt to add edge when < 2 nodes are in graph!" );
        if ( keys == null || keys.length != g.getNodeCount() ) throw new IllegalArgumentException( "keys argument null or wrong size!" );
        Node n1 = null;
        Node n2 = null;
        Edge retEdge = null;
        while ( true ) {
            n1 = g.getNode( keys[ rnGen.nextInt( g.getNodeCount() ) ] );
            n2 = g.getNode( keys[ rnGen.nextInt( g.getNodeCount() ) ] );
            if ( n1 == n2 ) // Skip if already have edge between these two nodes
                continue;
            String id = Edge.computeDefaultEdgeId( n1, n2 );
            if ( g.containsEdge(new Edge(n1,n2)))
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
	
	public static void printTimeWindows(ResourceGraph resourceGraph) {
			for (Node n : resourceGraph.getNodeMap()) {
				ResourceNode rn = (ResourceNode)n;
				
				System.out.print(rn.getId() + " ");
				for (int i = 0; i < rn.getTimeWindow().length; i++) {
					System.out.print(rn.getTimeWindow()[i] + " ");
				}
				System.out.println();
			}
		}
	
	public static void printPath(List<Node> path) {
		if(path!= null && path.size()>0) {
			for (int i = 0; i < path.size()-1; i++)
				System.out.print(path.get(i).getId() + "->");
			System.out.print(path.get(path.size()-1).getId());
		}
		else {
			System.out.print("NO PATH FOUND");
		}
	}
}
