package test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import config.GraphConsts;

import tools.GraphTools;
import algorithm.AbstractWCRSolver;
import algorithm.AstarAlgorithm;
import algorithm.AstarWCRSolver;
import algorithm.DijkstraAlgorithm;
import math.MathTools;
import models.FreeTimeWindowGraph;
import models.Graph;
import models.Node;
import models.ResourceGraph;
import io.GraphReader;

public class TestShortestPathAlgorithms {
	
	private static final String DIR = "datasets";
	private static final String SMALL_GRAPH_DIR = "small_graphs";
	private static final String LARGE_GRAPH_DIR = "large_graphs";
	//private static final String DIR = "random_euclidean_graphs";
	private static List<Class<? extends AbstractWCRSolver>> algClasses = Arrays.asList(DijkstraAlgorithm.class, AstarAlgorithm.class, AstarWCRSolver.class);
	private static List<Class<AstarWCRSolver>> wcrClasses = Arrays.asList(AstarWCRSolver.class);
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		//runSP(new File(DIR+"/random_euclidean_35_25.0_100_100_4"));
		File folder = new File(DIR+"/"+LARGE_GRAPH_DIR);
		for (File file : folder.listFiles()) {
			runSP(file);
		}
	}

	@SuppressWarnings("rawtypes")
	private static void runSP(File file) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		
		Graph graph = GraphReader.parseGraphFromData(file);
		
		ResourceGraph resourceGraph = GraphTools.convertGraphToResourceGraph(graph);
		//resourceGraph.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(resourceGraph);
		System.out.print(file.getName()+ "\t" + graph.getNodeCount() + "\t" + graph.getEdgeCount());
		
		int runs = 10;
		double[][] distance = new double[algClasses.size()][runs];
		int[][] pathLength = new int[algClasses.size()][runs];
		long[][] estimatedTime = new long[algClasses.size()][runs];
		
		for (int i = 0; i < runs; i++) {
			
			Node source = GraphTools.getRandomNode(graph);
			Node target = GraphTools.getRandomNode(graph);
			
			int count = 0;
			for (Class<? extends AbstractWCRSolver> algClass : algClasses) {
				java.lang.reflect.Constructor co = algClass.getConstructor(new Class[] {Graph.class});
				AbstractWCRSolver alg  = !wcrClasses.contains(algClass) ? (AbstractWCRSolver) co.newInstance(new Object[]{graph}) : (AbstractWCRSolver) co.newInstance(new Object[]{ftwGraph});
				
				long startTime = System.nanoTime();
				List<Node> path = alg.pathTo(source, target, 0, GraphConsts.MAX_TIME_STEPS);
				estimatedTime[count][i] = System.nanoTime() - startTime;
				
				distance[count][i] = (int)alg.distanceTo(target);
				pathLength[count][i] = alg.pathLengthTo(target);
				//double time = (int)alg.timeTo(target);
				
				count++;
			}
		}
		
		for (int j = 0; j < algClasses.size(); j++) {
			System.out.print("\t"+(int)MathTools.avg(distance[j]));
			//System.out.print("\t"+time);
			System.out.print("\t"+(int)MathTools.avg(pathLength[j]));
			System.out.print("\t"+((int)MathTools.avg(pathLength[j])-1));
			System.out.print("\t"+MathTools.avg(estimatedTime[j]));
			System.out.print("\t");
			//GraphTools.printPath(path);
		}
		
		System.out.println();
	}
	
	
}