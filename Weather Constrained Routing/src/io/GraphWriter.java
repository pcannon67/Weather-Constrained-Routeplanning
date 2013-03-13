package io;

import java.io.BufferedWriter;
import java.io.FileWriter;

import models.Edge;
import models.Graph;

public class GraphWriter {
	
	public static void writeGraphToFile(Graph g,String fileName)
	{
		try
		{
			// Create file 
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
		  
			out.write(g.getNodeCount() +  "\n");
			for ( Edge e : g.getEdgeMap().values())
				out.write(e.getNodeFrom().getId() + " " + e.getNodeTo().getId() + " " + e.getEdgeValue() + "\n");
			
			//Close the output stream
			out.close();
		}
		catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}
