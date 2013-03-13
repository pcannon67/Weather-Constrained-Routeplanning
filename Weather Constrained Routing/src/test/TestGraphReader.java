package test;

import io.GraphReader;
import models.Graph;

public class TestGraphReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph graph = GraphReader.parseGraphFromData("random_10_20_1_10");
		System.out.println(graph.toStringVerbose());
	}

}
