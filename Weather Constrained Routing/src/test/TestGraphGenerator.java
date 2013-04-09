package test;

import io.GraphWriter;
import models.Graph;
import tools.RandomGraphBuilder;

public class TestGraphGenerator {

	private static final int NUMBER_OF_GRAPHS_TO_GENERATE = 10;
	
	private static final int NUMBER_OF_NODES = 5;
	private static final int NUMBER_OF_EDGES = 2*NUMBER_OF_NODES;
	private static final int MIN_EDGE_VALUE = 1;
	private static final int MAX_EDGE_VALUE = 100;

	private static final String DIR = "random_graphs";

	public static void main(String[] args) {
		
		generateRandomGraphs();
	}

	private static void generateRandomGraphs() {
		for (int j = 1; j <= 10; j++) {
			
			int nof_nodes = j*NUMBER_OF_NODES;
			int nof_edges = 2 * nof_nodes;
			
			System.out.println("Generating "+NUMBER_OF_GRAPHS_TO_GENERATE+" with |V| = "+nof_nodes+", |E| = "+nof_edges+" random graph(s)...");
			
			for(int i = 0; i < NUMBER_OF_GRAPHS_TO_GENERATE; i++)
			{
				Graph g = RandomGraphBuilder.createRandomGraph(nof_nodes, nof_edges, MIN_EDGE_VALUE, MAX_EDGE_VALUE);
				GraphWriter.writeGraphToFile(g, DIR+"/random_"+g.getNodeCount()+"_"+g.getEdgeCount()+"_"+MIN_EDGE_VALUE+"_"+MAX_EDGE_VALUE+"_"+(i+1));
			}
			
			System.out.println("DONE");
		}
	}
}
