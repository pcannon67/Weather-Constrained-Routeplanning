package models;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Graph {
	
	private SortedMap< String, Node > nodeMap = null;
    private Map< String, Edge > edgeMap = null;
    
    public Graph() {
        nodeMap = new TreeMap< String, Node >( new Comparator< String >() {
            public int compare( String s1, String s2 ) {
                return s1.compareTo( s2 );
            }
        } );
        edgeMap = new HashMap< String, Edge >();
    }

    public void addNode( Node n ) {
        if ( n == null ) throw new IllegalArgumentException( "Argument must be non-null!" );
        if ( nodeMap.get( n.getId() ) != null ) throw new IllegalArgumentException( "Attempt to add node with duplicate id <" + n.getId() + ">" );
        nodeMap.put( n.getId(), n);
    }
    
    public void addEdge( Edge e ) {
        if ( edgeMap.get( e.getId() ) != null ) throw new IllegalStateException( "Attemp to add edge wiith duplicate id <" + e.getId() + ">" );
        edgeMap.put( e.getId(), e );
        e.getNodeFrom().addEdge(e);
        e.getNodeTo().addEdge(e);
    }
    
    public Node getNode(String id) {
        return nodeMap.get(id);
    }
    
    public Edge getEdge(String id) {
        return edgeMap.get(id);
    }
    
    public SortedMap<String,Node> getNodeMap() {
		return nodeMap;
	}

	public Map<String, Edge> getEdgeMap() {
		return edgeMap;
	}
    
    public int getNodeCount() {
        return nodeMap.size();
    }
    
    public int getEdgeCount() {
        return edgeMap.size();
    }
    
    public int countNodesWithDegree( int degree ) {
        int sum = 0;
        for ( Node n : nodeMap.values() )
            if ( n.getDegree() == degree )
                ++sum;
        return sum;
    }
    
    public int computeMaxDegree() {
        int maxDegree = 0;
        for ( Node n : nodeMap.values() ) {
            if ( maxDegree < n.getDegree() )
                maxDegree = n.getDegree();
        }
        return maxDegree;
    }
    
    public boolean hasEdge(Edge thisEdge) {
    	for (Edge e : edgeMap.values()) {
    		//System.out.println(thisEdge.getId() + " " + e.getId() + " " + (e == thisEdge));
			if(e.getId().equals(thisEdge.getId()))
				return true;
		}
    	
    	return false;
    }
    
    public String getGraphSummary() {
        StringBuffer sb = new StringBuffer();
        sb.append( "Graph Object Summary:\n" );
        sb.append( "\tNode Count: " + getNodeCount() + "\n" );
        sb.append( "\tEdge Count: "  + getEdgeCount() + "\n" );
        return sb.toString();
    }
    
    public String toStringVerbose() {
        StringBuffer sb = new StringBuffer();
        sb.append( "Graph Object Dump:\n" );
        sb.append( "\tNode Count: " + getNodeCount() + "\n" );
        sb.append( "\tEdge Count: "  + getEdgeCount() + "\n" );
        sb.append( "\tNodes: \n" );
        int nodeIndex = 0;
        for ( Node n : nodeMap.values() )
            sb.append( "\t\tNode[ " + nodeIndex++ + " ]: " + n.toString() + "\n" );
        sb.append( "\tEdges: \n" );
        int edgeIndex = 0;
        for ( Edge e : edgeMap.values() )
            sb.append( "\t\tEdge[ " + edgeIndex++ + " ]: " + e.toString() + "\n" );
        return sb.toString();
    }
}