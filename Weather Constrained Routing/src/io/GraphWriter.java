package io;

import java.io.BufferedWriter;
import java.io.FileWriter;

import models.Edge;
import models.Graph;
import models.Node;

public class GraphWriter {
	
	public static void writeGraphToFile(Graph g,String fileName)
	{
		try
		{
			// Create file 
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
		  
			out.write(g.getNodeCount() +  "\n");
			out.write(g.getEdgeCount() +  "\n");
			
			for ( Node n : g.getNodeMap().values())
				out.write(n.getId() + " " + n.getX() + " " + n.getY() + "\n");
			
			for ( Edge e : g.getEdgeMap().values())
				out.write(e.getNodeFrom().getId() + " " + e.getNodeTo().getId() + " " + e.getWeight() + "\n");
			
			//Close the output stream
			out.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}
