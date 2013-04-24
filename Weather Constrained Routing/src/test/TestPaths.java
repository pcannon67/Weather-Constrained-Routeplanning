package test;

import static org.junit.Assert.*;

import io.GraphReader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import math.MathTools;
import models.Graph;
import models.Node;

import org.junit.Test;

import tools.GraphTools;
import algorithm.AbstractWCRSolver;
import algorithm.AstarAlgorithm;
import algorithm.AstarWCRSolver;
import algorithm.DijkstraAlgorithm;

import config.GraphConsts;
import config.TestConsts;

public class TestPaths {

	private static List<Class<? extends AbstractWCRSolver>> ALG_CLASSES = Arrays.asList(DijkstraAlgorithm.class, AstarAlgorithm.class);
	private static List<Class<AstarWCRSolver>> wcrClasses = Arrays.asList();
	
	@Test
	public void testAstar() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		List<Class<? extends AbstractWCRSolver>> astar = new ArrayList<Class<? extends AbstractWCRSolver>>();
		astar.add(AstarAlgorithm.class);
		runSP(new File(TestConsts.SMALL_GRAPHS_DIR+"/random_euclidean_12_40"),astar);
	}
	
	@Test
	public void testAllAlgorithms() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		File folder = new File(TestConsts.SMALL_GRAPHS_DIR);
		for (File file : folder.listFiles()) {
			runSP(file,ALG_CLASSES);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private static void runSP(File file,List<Class<? extends AbstractWCRSolver>> algClasses) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		
		Graph graph = GraphReader.parseGraphFromData(file);
		
		/*ResourceGraph resourceGraph = GraphTools.convertGraphToResourceGraph(graph);
		//resourceGraph.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(resourceGraph);*/
		System.out.print(file.getName()+ "\t\t" + graph.getNodeCount() + "\t" + graph.getEdgeCount());
		
		
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
				AbstractWCRSolver alg  = !wcrClasses.contains(algClass) ? (AbstractWCRSolver) co.newInstance(new Object[]{graph}) : (AbstractWCRSolver) co.newInstance(new Object[]{graph});
				
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
