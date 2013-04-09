package test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import config.GraphConsts;

import tools.GraphTools;
import algorithm.AbstractWCRSolver;
import algorithm.AstarAlgorithm;
import algorithm.AstarWCRSolver;
import algorithm.DijkstraAlgorithm;
import models.FreeTimeWindowGraph;
import models.Graph;
import models.Node;
import models.ResourceGraph;
import io.GraphReader;

public class TestShortestPathAlgorithms {
	
	private static final String DIR = "random_graphs";
	private static List<Class<? extends AbstractWCRSolver>> algClasses = Arrays.asList(DijkstraAlgorithm.class, AstarAlgorithm.class, AstarWCRSolver.class);
	private static List<Class<AstarWCRSolver>> wcrClasses = Arrays.asList(AstarWCRSolver.class);
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		runSP(new File(DIR+"/random_5_10_1_100_3"));
		/*File folder = new File(DIR);
		for (File file : folder.listFiles()) {
			runSP(file);
		}*/
	}

	@SuppressWarnings("rawtypes")
	private static void runSP(File file) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Graph graph = GraphReader.parseGraphFromData(file);
		
		ResourceGraph resourceGraph = GraphTools.convertGraphToResourceGraph(graph);
		//resourceGraph.addRandomWeatherOverlay(GraphConsts.MAX_TIME_STEPS);
		FreeTimeWindowGraph ftwGraph = GraphTools.convertResourceGraphToFreeTimeWindowGraph(resourceGraph);
		
		Node source = GraphTools.getRandomNode(graph);
		Node target = GraphTools.getRandomNode(graph);
		
		System.out.print(file.getName()+"("+source.getId()+"->"+target.getId()+")");
		for (Class<? extends AbstractWCRSolver> algClass : algClasses) {
			java.lang.reflect.Constructor co = algClass.getConstructor(new Class[] {Graph.class});
			AbstractWCRSolver alg  = !wcrClasses.contains(algClass) ? (AbstractWCRSolver) co.newInstance(new Object[]{graph}) : (AbstractWCRSolver) co.newInstance(new Object[]{ftwGraph});
			
			List<Node> path = alg.pathTo(source, target, 0, GraphConsts.MAX_TIME_STEPS);
			double time = alg.timeTo(target);
			double distance = alg.distanceTo(target);
			int pathLength = alg.pathLengthTo(target);
			
			System.out.print("\t"+distance);
			System.out.print("\t"+time);
			System.out.print("\t"+pathLength);
			System.out.print("\t");
			GraphTools.printPath(path);
		}
		
		System.out.println();
	}
}