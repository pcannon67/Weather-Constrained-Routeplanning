package test;

import java.util.List;
import java.util.Stack;

import tools.GraphTools;
import algorithm.AstarAlgorithm;
import algorithm.DijkstraAlgorithm;
import models.Graph;
import models.Node;
import io.GraphReader;

public class TestDijkstraAlgorithm {
	
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData("random_10_20_1_10");
		
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		AstarAlgorithm astar = new AstarAlgorithm();
	
		Node source = GraphTools.getRandomNode(graph);
		Node target = GraphTools.getRandomNode(graph);
		
		//double distance = dijkstra.computeShortestDistance(source, target);
		//Stack<Node> path = dijkstra.computeShortestPath(source, target);
		
		List<Node> path = astar.computePaths(source, target);
		System.out.println("Calculating from "+source.getId()+" to "+target.getId() + " using Astar algorithm");
		//System.out.println("Distance from "+source.getId()+" to "+target.getId() + ": "+distance);
		System.out.print("Path from "+source.getId()+" to "+target.getId() + ": ");
		
		if(path.size()>0)
		{
			for (int i = 0; i < path.size()-1; i++)
				System.out.print(path.get(i).getId() + "->");
			System.out.println(path.get(path.size()-1).getId());
		}
		else
		{
			System.out.println("NO PATH FOUND");
		}
		
		/*System.out.println("Calculating from "+source.getId()+" to "+target.getId() + " using Dijkstra's algorithm");
		System.out.println("Distance from "+source.getId()+" to "+target.getId() + ": "+distance);
		System.out.print("Path from "+source.getId()+" to "+target.getId() + ": ");
		
		if(path.size()>0)
		{
			while(path.size() > 1)
				System.out.print(path.pop().getId() + "->");
			System.out.println(path.pop().getId());
		}
		else
		{
			System.out.println("NO PATH FOUND");
		}*/
	}

}
