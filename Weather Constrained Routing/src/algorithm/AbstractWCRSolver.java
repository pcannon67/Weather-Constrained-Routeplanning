package algorithm;

import models.Graph;

public abstract class AbstractWCRSolver implements PathFinder {
	
	protected Graph graph;

	public AbstractWCRSolver(Graph graph) {
		this.graph = graph;
	}
}
