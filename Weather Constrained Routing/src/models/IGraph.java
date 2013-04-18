package models;

public interface IGraph {
	
	public int V();
	
	public int E();
	
	public void addEdge(Node n, Node u);
	
	public Iterable<Node> adj(Node n);
}
