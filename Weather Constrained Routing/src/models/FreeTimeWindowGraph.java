package models;

public class FreeTimeWindowGraph extends Graph {

	public FreeTimeWindowGraph() {
		super(true); //is a directed graph
	}
	
	public FreeTimeWindowNode getFreeTimeWindowNode(TimeWindow startTimeWindow,
			Node node) {
		
		for (Node n : getNodeMap().values()) {
			FreeTimeWindowNode fn = (FreeTimeWindowNode) n;
			
			if(startTimeWindow.isOverLappingWithTimeWindow(fn.getEntryWindow()) && 
					fn.getResourceNode().getId().equals(node.getId()))
				return fn;
				
		}
		return null;
	}

}
