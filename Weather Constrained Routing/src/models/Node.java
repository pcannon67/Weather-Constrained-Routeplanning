package models;

import java.util.ArrayList;
import java.util.List;

import math.MathTools;

public class Node implements Comparable<Node> {
	
	private final String id;
    private int degree = 0;
    private double x;
    private double y;
    private List<Edge> neighbors;
    
    public Node(String id) {
        this(id,0,0);
    }
    
    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        neighbors = new ArrayList<Edge>();
    }
    
    public String getId() {
        return id;
    }
    
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
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
    
    public static double distanceTo(Node n, Node u) {
        return MathTools.euclideanDistance(n.getX(), n.getY(), u.getX(), u.getY());
    }
    
    @Override
    public synchronized String toString() {
        return "Node: id: " + id + " degree: " + degree;
    }
}
