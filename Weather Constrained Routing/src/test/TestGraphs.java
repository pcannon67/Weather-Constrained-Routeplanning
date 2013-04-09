package test;

import java.io.File;

import config.GraphConsts;
import tools.GraphTools;
import io.GraphReader;
import models.FreeTimeWindowGraph;
import models.Graph;
import models.ResourceGraph;

public class TestGraphs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData(new File("random_10_20_1_10"));
		ResourceGraph rg = GraphTools.convertGraphToResourceGraph(graph);
		rg.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(rg);
		
		System.out.println(graph.toStringVerbose());
		System.out.println(rg.toStringVerbose());
		GraphTools.printTimeWindows(rg);
		System.out.println(ftwGraph.toStringVerbose());
	}

}
