package models;

import math.MathTools;

public class Edge {
	
	private Node nodeFrom;
    private Node nodeTo;
    private double edgeValue;
    private String id;
    
    public Edge( Node nodeFrom, Node nodeTo, String id, double edgeValue ) {
        if ( nodeFrom == null || nodeTo== null ) throw new IllegalArgumentException( "Nodes must not be null!" );
        if ( nodeFrom == nodeTo ) throw new IllegalArgumentException( "Argument nodes must not be the same node!" );
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.id = ( id == null ) ? computeDefaultEdgeId( nodeFrom, nodeTo ) : id;
        this.edgeValue=edgeValue;
    }
    
    public String getId() {
        return id;
    }
    
    public static String computeDefaultEdgeId( Node n1, Node n2 ) {
        if ( n1 == null || n2 == null )
            throw new IllegalArgumentException( "Arguments must not be null!" );
        if ( n1 == n2 )
            throw new IllegalArgumentException( "Argument nodes must be for different nodes!" );
        if ( n1.compareTo( n2 ) < 0 )
            return n1.getId() + ":" + n2.getId();
        else
            return n2.getId() + ":" + n1.getId();
    }
    
    public static double generateRandomEdgeValue(int minEdgeValue,int maxEdgeValue) {
    	return MathTools.randomInRange(minEdgeValue, maxEdgeValue);
    }
    
    public Node getNodeFrom() {
        return nodeFrom;
    }
    
    public Node getNodeTo() {
        return nodeTo;
    }
    
    public double getEdgeValue() {
		return edgeValue;
	}
    
    @Override
    public String toString() {
        return "Edge id: " + id + " n1: " + nodeFrom.getId() + " n2: " + nodeTo.getId() + " value: " + edgeValue;
    }
}
