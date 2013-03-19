package test;

import io.GraphReader;

import java.util.List;

import config.GraphConsts;

import models.FreeTimeWindowGraph;
import models.Graph;
import models.Node;
import models.ResourceGraph;
import models.ResourceNode;
import tools.GraphTools;
import algorithm.AstarAlgorithm;
import algorithm.AstarWCRSolver;
import algorithm.DijkstraAlgorithm;

public class TestWCRSolvers {
	
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData("random_10_20_1_10");
		ResourceGraph resourceGraph = GraphTools.convertGraphToResourceGraph(graph);
		resourceGraph.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		
		printTimeWindows(resourceGraph);
		
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(resourceGraph);
		
		System.out.println(ftwGraph.toStringVerbose());
		
		/*AstarWCRSolver astar = new AstarWCRSolver();
	
		ResourceNode source = (ResourceNode)GraphTools.getRandomNode(resourceGraph);
		ResourceNode target = (ResourceNode)GraphTools.getRandomNode(resourceGraph);
		
		//double distance = astar.computeShortestDistance(graph,source, target);
		List<Node> path = astar.computePaths(ftwGraph, source, target, 0);
		
		System.out.println("Calculating from "+source.getId()+" to "+target.getId() + " using Astar algorithm");
		//System.out.println("Distance from "+source.getId()+" to "+target.getId() + ": "+distance);
		System.out.print("Path from "+source.getId()+" to "+target.getId() + ": ");
		
		if(path!= null && path.size()>0)
		{
			for (int i = 0; i < path.size()-1; i++)
				System.out.print(path.get(i).getId() + "->");
			System.out.println(path.get(path.size()-1).getId());
		}
		else
		{
			System.out.println("NO PATH FOUND");
		}*/
	}

	private static void printTimeWindows(ResourceGraph resourceGraph) {
		for (Node n : resourceGraph.getNodeMap().values()) {
			ResourceNode rn = (ResourceNode)n;
			
			System.out.print(rn.getId() + " ");
			for (int i = 0; i < rn.getTimeWindow().length; i++) {
				System.out.print(rn.getTimeWindow()[i] + " ");
			}
			System.out.println();
		}
	}
}
