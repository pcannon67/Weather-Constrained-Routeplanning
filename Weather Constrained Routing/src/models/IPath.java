package models;

public interface IPath {
	
	public boolean hasPathTo(Node s, Node t);
	
	public Iterable<Node> pathTo(Node s, Node t);
}
