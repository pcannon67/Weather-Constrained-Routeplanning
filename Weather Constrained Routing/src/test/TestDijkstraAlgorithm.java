package test;

import java.util.List;

import tools.GraphTools;
import algorithm.DijkstraAlgorithm;
import models.Graph;
import models.Node;
import io.GraphReader;

public class TestDijkstraAlgorithm {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData("random_10_20_1_10");
		
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		
		Node source = GraphTools.getRandomNode(graph);
		Node target = GraphTools.getRandomNode(graph);
		
		double distance = dijkstra.computeShortestDistance(source, target);
		List<Node> path = dijkstra.computeShortestPath(source, target);
		
		System.out.println("Calculating from "+source.getId()+" to "+target.getId() + " using Dijkstra's algorithm");
		System.out.println("Distance from "+source.getId()+" to "+target.getId() + ": "+distance);
		System.out.print("Path from "+source.getId()+" to "+target.getId() + ": ");
		
		if(path.size()>0)
		{
			for(int i = 0; i < path.size()-1;i++)
				System.out.print(path.get(i).getId() + "->");
			System.out.println(path.get(path.size()-1).getId());
		}
		else
		{
			System.out.println("NO PATH FOUND");
		}
		
		
	}

}
