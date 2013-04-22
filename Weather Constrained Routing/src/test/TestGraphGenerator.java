package test;

import java.util.HashMap;
import java.util.Map;

import gnu.trove.map.hash.THashMap;
import io.GraphWriter;
import models.Edge;
import models.Graph;
import models.Node;
import tools.GraphBuilder;

public class TestGraphGenerator {

	private static final int NUMBER_OF_GRAPHS_TO_GENERATE = 1;
	
	private static final int NUMBER_OF_NODES = 50;
	
	private static final int NUMBER_OF_EDGES = 2*NUMBER_OF_NODES;
	private static final int MIN_EDGE_VALUE = 1;
	private static final int MAX_EDGE_VALUE = 100;

	private static final int BOX_HEIGHT = 100;
	private static final int BOX_WIDTH = 100;
	private static final double DENSITY = BOX_WIDTH*0.05;
	
	
	private static final String DATASET_DIR = "graphs";
	private static final String LARGE_GRAPHS_DIR = "large_graphs";
	private static final String SMALL_GRAPHS_DIR = "small_graphs";
	private static final String RANDOM_EUCLIDEAN_GRAPHS_DIR = "random_euclidean_graphs";

	public static void main(String[] args) {
		generateRandomEuclideanGraphs(DATASET_DIR+"/");
		//generateRandomGraphs();
	}

	private static void generateRandomEuclideanGraphs(String dir) {
		for (int j = 1; j <= 10; j++) {
			int nof_nodes = j*NUMBER_OF_NODES*NUMBER_OF_NODES;
			
			System.out.println("Generating "+NUMBER_OF_GRAPHS_TO_GENERATE+" with |V| = "+nof_nodes+" random euclidean graph(s)...");
			
			for(int i = 0; i < NUMBER_OF_GRAPHS_TO_GENERATE; i++)
			{
				Graph g = GraphBuilder.createRandomEuclideanGraph(nof_nodes, DENSITY, BOX_WIDTH, BOX_HEIGHT);
				GraphWriter.writeGraphToFile(g, dir+"random_euclidean_"+g.getNodeCount()+"_"+DENSITY+"_"+BOX_WIDTH+"_"+BOX_HEIGHT+"_"+(i+1));
			}
			
			System.out.println("DONE");
		}
	}
	
	private static void generateRandomGraphs(String dir) {
		for (int j = 1; j <= 10; j++) {
			
			int nof_nodes = j*NUMBER_OF_NODES;
			int nof_edges = 2 * nof_nodes;
			
			System.out.println("Generating "+NUMBER_OF_GRAPHS_TO_GENERATE+" with |V| = "+nof_nodes+", |E| = "+nof_edges+" random graph(s)...");
			
			for(int i = 0; i < NUMBER_OF_GRAPHS_TO_GENERATE; i++)
			{
				Graph g = GraphBuilder.createRandomGraph(nof_nodes, nof_edges, MIN_EDGE_VALUE, MAX_EDGE_VALUE);
				GraphWriter.writeGraphToFile(g, dir+"/random_"+g.getNodeCount()+"_"+g.getEdgeCount()+"_"+MIN_EDGE_VALUE+"_"+MAX_EDGE_VALUE+"_"+(i+1));
			}
			
			
			System.out.println("DONE");
		}
	}
}
