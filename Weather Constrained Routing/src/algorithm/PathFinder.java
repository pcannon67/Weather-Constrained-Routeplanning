package algorithm;

import java.util.List;
import models.Node;

public interface PathFinder {
	
	/**
	 * @param target
	 * @return the shortest-time distance between source and target
	 */
	public double distanceTo(Node target);
	
	/**
	 * @param target
	 * @return the shortest-time going from source to target
	 */
	public double timeTo(Node target);
	
	/**
	 * @param target
	 * @return the number of nodes visited from source to target
	 */
	public int pathLengthTo(Node target);
	
	/**
	 * @param target
	 * @return the shortest-time path starting from source and ending in target 
	 */
	public List<Node> pathTo(Node source, Node target, int startTime, int maxTimeSteps);
}
