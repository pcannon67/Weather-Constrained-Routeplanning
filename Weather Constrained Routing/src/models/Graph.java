package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;

public class Graph {
	public List<Node> nodes;
	public List<Edge> edges;
	
	public Graph() {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();		
	}
	
	public void addNode(Node n)  {
        nodes.add(n);
    }
	
    public void addNode(String id, double x, double y)  {
    	addNode(new Node(id, x, y));
    }

    public void addEdge(Edge e) {
    	addEdge(e.getNodeFrom(), e.getNodeTo(), e.getWeight());
    }
    
    public void addEdge(String n1, String n2, double weight) {
    	Node source = getNode(n1);
    	Node target = getNode(n2);
    	addEdge(source, target, weight);
    }
    
    public void addEdge(Node source, Node target, double weight) {
    	Edge e = new Edge(source,target,weight);
    	source.addEdge(e);
        edges.add(e);
    }
    
    public Node getNode(String id) {
    	for (Node n : nodes) {
			if(n.getId().equals(id))
				return n;
		}
    	return null;
    }

	public int getNodeCount() {
		return nodes.size();
	}
	
	public int getEdgeCount() {
		return edges.size();
	}

	public List<Node> getNodeMap() {
		return nodes;
	}
	
	public List<Edge> getEdgeMap() {
		return edges;
	}
	
	public boolean containsEdge(Edge edge) {
		return edges.contains(edge);
	}
	
	public String toStringVerbose() {
        StringBuffer sb = new StringBuffer();
        sb.append( "Graph Object Dump:\n" );
        sb.append( "\tNode Count: " + getNodeCount() + "\n" );
        sb.append( "\tEdge Count: "  + getEdgeCount() + "\n" );
        sb.append( "\tNodes: \n" );
        int nodeIndex = 0;
        for ( Node n : nodes )
            sb.append( "\t\tNode[ " + nodeIndex++ + " ]: " + n.toString() + "\n" );
        sb.append( "\tEdges: \n" );
        int edgeIndex = 0;
        for ( Edge e : edges )
            sb.append( "\t\tEdge[ " + edgeIndex++ + " ]: " + e.toString() + "\n" );
        return sb.toString();
    }
}

/*public class Graph {
	
	public DefaultDirectedWeightedGraph<Node, Edge> g;

	public Graph() {
		g = new DefaultDirectedWeightedGraph<Node, Edge>(Edge.class);
	}
	
	public void addNode(Node n)  {
        g.addVertex(n);
    }
	
    public void addNode(String id, double x, double y)  {
        g.addVertex(new Node(id, x, y));
    }

    public void addEdge(Edge e) {
    	addEdge(e.getNodeFrom(), e.getNodeTo(), e.getWeight());
    }
    
    public void addEdge(String n1, String n2, double weight) {
    	Node source = getNode(n1);
    	Node target = getNode(n2);
    	addEdge(source, target, weight);
    }
    
    public void addEdge(Node source, Node target, double weight) {
    	Edge e = new Edge(source,target,weight);
    	source.addEdge(e);
        g.addEdge(source,target,e);
    }
    
    public Node getNode(String id) {
    	for (Node n : g.vertexSet()) {
			if(n.getId().equals(id))
				return n;
		}
    	return null;
    }

	public int getNodeCount() {
		return g.vertexSet().size();
	}
	
	public int getEdgeCount() {
		return g.edgeSet().size();
	}

	public Set<Node> getNodeMap() {
		return g.vertexSet();
	}
	
	public Set<Edge> getEdgeMap() {
		return g.edgeSet();
	}
	
	public boolean containsEdge(Edge edge) {
		return g.containsEdge(edge);
	}
	
	public String toStringVerbose() {
        StringBuffer sb = new StringBuffer();
        sb.append( "Graph Object Dump:\n" );
        sb.append( "\tNode Count: " + getNodeCount() + "\n" );
        sb.append( "\tEdge Count: "  + getEdgeCount() + "\n" );
        sb.append( "\tNodes: \n" );
        int nodeIndex = 0;
        for ( Node n : g.vertexSet() )
            sb.append( "\t\tNode[ " + nodeIndex++ + " ]: " + n.toString() + "\n" );
        sb.append( "\tEdges: \n" );
        int edgeIndex = 0;
        for ( Edge e : g.edgeSet() )
            sb.append( "\t\tEdge[ " + edgeIndex++ + " ]: " + e.toString() + "\n" );
        return sb.toString();
    }
}*/

/*package models;


import gnu.trove.map.hash.THashMap;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Graph {
	
	private SortedMap< String, Node > nodeMap = null;
    private THashMap< String, Edge > edgeMap = null;
    private boolean directed;
    
    public Graph() {
        this(false);
    }
    
    public Graph(boolean directed) {
        nodeMap = new TreeMap< String, Node >( new Comparator< String >() {
            public int compare( String s1, String s2 ) {
                return s1.compareTo( s2 );
            }
        } );
        edgeMap = new THashMap< String, Edge >();
        this.directed = directed;
    }

    public void addNode( Node n ) {
        if ( n == null ) throw new IllegalArgumentException( "Argument must be non-null!" );
        if ( nodeMap.get( n.getId() ) != null ) throw new IllegalArgumentException( "Attempt to add node with duplicate id <" + n.getId() + ">" );
        nodeMap.put( n.getId(), n);
    }
    
    public void addEdge( Edge e ) {
        if ( edgeMap.get( e.getId() ) != null ) throw new IllegalStateException( "Attemp to add edge wiith duplicate id <" + e.getId() + ">" );
        edgeMap.put( e.getId(), e);
        e.getNodeFrom().addEdge(e);
        if(!directed) {
        	e.getNodeTo().addEdge(e);
        }
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
    
    public boolean hasEdge(Edge thisEdge) {
    	String edgeId = Edge.computeBidirectionalEdgeId(thisEdge.getNodeFrom(),thisEdge.getNodeTo());
    	for (Edge e : edgeMap.values()) {
			if(directed) {
				if(thisEdge.getId().equals(e.getId()))
					return true;
			}
			else {
				if(edgeId.equals(Edge.computeBidirectionalEdgeId(e.getNodeFrom(), e.getNodeTo())))
					return true;
			}
		}
    	
    	return  edgeMap.get(thisEdge.getId())!=null;
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
}*/