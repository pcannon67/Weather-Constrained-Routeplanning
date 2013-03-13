package test;

import io.GraphWriter;
import models.Graph;
import tools.RandomGraphBuilder;

public class TestGraphGenerator {

	private static final int NUMBER_OF_GRAPHS_TO_GENERATE = 1;
	
	private static final int NUMBER_OF_NODES = 10;
	private static final int NUMBER_OF_EDGES = 2*NUMBER_OF_NODES;
	private static final int MIN_EDGE_VALUE = 1;
	private static final int MAX_EDGE_VALUE = 10;

	public static void main(String[] args) {
		
		generateRandomGraphs();
	}

	private static void generateRandomGraphs() {
		System.out.println("Generating "+NUMBER_OF_GRAPHS_TO_GENERATE+" random graph(s)...");
		
		for(int i = 0; i < NUMBER_OF_GRAPHS_TO_GENERATE; i++)
		{
			Graph g = RandomGraphBuilder.createRandomGraph(NUMBER_OF_NODES, NUMBER_OF_EDGES, MIN_EDGE_VALUE, MAX_EDGE_VALUE);
			GraphWriter.writeGraphToFile(g, "random_"+g.getNodeCount()+"_"+g.getEdgeCount()+"_"+MIN_EDGE_VALUE+"_"+MAX_EDGE_VALUE);
		}
		
		System.out.println("DONE");
	}

}
