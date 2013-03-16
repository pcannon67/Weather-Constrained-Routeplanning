package models;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
	
	private final String id;
    private int degree = 0;
    private List<Edge> neighbors;
    
    public Node( String id ) {
        this.id = id;
        neighbors = new ArrayList<Edge>();
    }
    
    public String getId() {
        return id;
    }
    
    public List<Edge> getNeighbors() {
		return neighbors;
	}
    
    public synchronized int getDegree() {
        return degree;
    }
    
    public void addEdge(Edge e) {
    	if(e != null && !neighbors.contains(e))
    	{
    		neighbors.add(e);
    		++degree;
    	}
	}
    
    public int compareTo( Node n ) {
        return getId().compareTo( n.getId() );
    }
    
    @Override
    public synchronized String toString() {
        return "Node: id: " + id + " degree: " + degree;
    }
}
