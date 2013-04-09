package models;

import math.MathTools;

public class Edge implements Comparable<Edge> {
	
	private Node nodeFrom;
    private Node nodeTo;
    private double weight;
    private String id;
    
    public Edge( Node nodeFrom, Node nodeTo, String id, double weight ) {
        if ( nodeFrom == null || nodeTo== null ) throw new IllegalArgumentException( "Nodes must not be null!" );
        if ( nodeFrom == nodeTo ) throw new IllegalArgumentException( "Argument nodes must not be the same node!" );
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
        this.id = ( id == null ) ? computeDefaultEdgeId( nodeFrom, nodeTo ) : id;
        this.weight=weight;
    }
    
    public Edge(Node nodeFrom, Node nodeTo) {
    	this(nodeFrom,nodeTo,null,1);
	}

	public String getId() {
        return id;
    }
    
    public static String computeDefaultEdgeId( Node n1, Node n2 ) {
        if ( n1 == null || n2 == null )
            throw new IllegalArgumentException( "Arguments must not be null!" );
        if ( n1 == n2 )
            throw new IllegalArgumentException( "Argument nodes must be for different nodes!" );
        else
            return n1.getId() + ":" + n2.getId();
    }
    
    public static String computeBidirectionalEdgeId( Node n1, Node n2 ) {
        if ( n1 == null || n2 == null )
            throw new IllegalArgumentException( "Arguments must not be null!" );
        if ( n1 == n2 )
            throw new IllegalArgumentException( "Argument nodes must be for different nodes!" );
        if(n1.getId().compareTo(n2.getId()) < 0)
            return n1.getId() + ":" + n2.getId();
        else
            return n2.getId() + ":" + n1.getId();
    }
    
    public static double generateRandomEdgeValue(int minWeight,int maxWeight) {
    	return MathTools.randomInRange(minWeight, maxWeight);
    }
    
    public Node getNodeFrom() {
        return nodeFrom;
    }
    
    public Node getNodeTo() {
        return nodeTo;
    }
    
    public double getWeight() {
		return weight;
	}
    
    @Override
    public String toString() {
        return "Edge id: " + id + " n1: " + nodeFrom.getId() + " n2: " + nodeTo.getId() + " value: " + weight;
    }

	@Override
	public int compareTo(Edge o) {
		return id.compareTo(o.getId());
	}
}