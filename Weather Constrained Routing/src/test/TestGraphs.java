package test;

import tools.GraphTools;
import io.GraphReader;
import models.Graph;
import models.ResourceGraph;

public class TestGraphs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData("random_10_20_1_10");
		ResourceGraph rg = GraphTools.convertGraphToResourceGraph(graph);
		
		System.out.println(graph.toStringVerbose());
		System.out.println(rg.toStringVerbose());
	}

}
