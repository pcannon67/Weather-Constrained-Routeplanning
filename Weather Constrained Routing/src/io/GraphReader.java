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
	        int edgeCount = inFile.nextInt();
	        inFile.nextLine();
	        
	        //Create and add nodeList
            for ( int i = 0; i < nodeCount; ++i ) {
            	String[] line = inFile.nextLine().split(" ");
                graph.addNode(line[0],Double.parseDouble(line[1]),Double.parseDouble(line[2]));
            }
            
            //Create and add edgeList
            for ( int i = 0; i < edgeCount; ++i ) {
	            String[] line = inFile.nextLine().split(" ");
                graph.addEdge(line[0],line[1],Double.parseDouble(line[2]));
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
