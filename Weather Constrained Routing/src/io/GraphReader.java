package io;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import models.Edge;
import models.Graph;
import models.Node;

public class GraphReader {
	
	public static Graph parseGraphFromData(File file)
	{
		//parse graph from file
		Graph graph =  new Graph();
		
	    try 
	    {
	    	Scanner inFile = new Scanner(new FileReader(file));
	    	
	        int nodeCount = inFile.nextInt();
	        //Create and add nodeList
            for ( int i = 0; i < nodeCount; ++i ) {
                String nodeId = Integer.toString(i);
                Node n = new Node(nodeId);
                graph.addNode(n);
            }
            
            inFile.nextLine();
            
            //Create and add edgeList
	        while (inFile.hasNextLine()) {
	            String[] line = inFile.nextLine().split(" ");
	            
	            Node nodeFrom = graph.getNode(line[0]);
	            Node nodeTo = graph.getNode(line[1]);
	            double edgeValue = Double.parseDouble(line[2]);
	            String id = Edge.computeDefaultEdgeId(nodeFrom, nodeTo);
	            
	            Edge e = new Edge(nodeFrom, nodeTo, id, edgeValue);
                graph.addEdge(e);
	        }
	     
	        inFile.close();
	    }
	    catch(Exception e)
	    {
	    	System.err.println("Error: " + e.getMessage());
	    }
	    
	    return graph;
	}
}
