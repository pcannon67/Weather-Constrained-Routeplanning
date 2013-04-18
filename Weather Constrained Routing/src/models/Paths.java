package models;

public abstract class Paths implements IPath{
	
	private Graph g;

	public Paths(Graph g) {
		this.g = g;
	}

	public Graph getG() {
		return g;
	}
}
