package models;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    
    public Node(int i) {
		this(Integer.toString(i));
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
    	neighbors.add(e);
    	++degree;
	}
    
    public int compareTo( Node n ) {
        return getId().compareTo( n.getId() );
    }
    
    public static double distanceTo(Node n, Node u) {
        return MathTools.euclideanDistance(n.getX(), n.getY(), u.getX(), u.getY());
    }
    
    @Override
	public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            // if deriving: appendSuper(super.hashCode()).
            append(id).
            toHashCode();
    }

	@Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Node))
            return false;

        Node rhs = (Node) obj;
        return new EqualsBuilder().
            // if deriving: appendSuper(super.equals(obj)).
            append(id, rhs.id).
            isEquals();
    }
    
    @Override
    public synchronized String toString() {
        return "Node: id: " + id + " degree: " + degree;
    }
}
