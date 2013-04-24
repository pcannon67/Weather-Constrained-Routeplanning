package test;

import java.io.File;

import config.GraphConsts;
import config.TestConsts;
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
		Graph graph = GraphReader.parseGraphFromData(new File(TestConsts.SMALL_GRAPHS_DIR+"/random_euclidean_28_110"));
		ResourceGraph rg = GraphTools.convertGraphToResourceGraph(graph);
		//rg.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(rg);
		
		System.out.println(graph.toStringVerbose());
		System.out.println(rg.toStringVerbose());
		//GraphTools.printTimeWindows(rg);
		System.out.println(ftwGraph.toStringVerbose());
	}

}
