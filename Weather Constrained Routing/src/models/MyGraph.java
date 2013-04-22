package models;

import org.jgrapht.graph.DefaultDirectedWeightedGraph;

public class MyGraph {
	
	private DefaultDirectedWeightedGraph<Node, Edge> g;

	public MyGraph() {
		g = new DefaultDirectedWeightedGraph<Node, Edge>(Edge.class);
	}
	
    public void addVertex(String id)  {
        g.addVertex(new Node(id));
    }

    public void addEdge(String n1, String n2, Edge e) {
        g.addEdge(getNode(n1),getNode(n2),e);
    }
    
    public Node getNode(String id) {
    	for (Node n : g.vertexSet()) {
			if(n.getId().equals(id))
				return n;
		}
    	return null;
    }
}