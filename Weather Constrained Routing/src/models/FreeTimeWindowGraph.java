package models;

import java.util.ArrayList;
import java.util.List;

public class FreeTimeWindowGraph extends Graph {

	
	public FreeTimeWindowNode getFreeTimeWindowNode(TimeWindow startTimeWindow,
			Node node) {
		
		for (Node n : getNodeMap()) {
			FreeTimeWindowNode fn = (FreeTimeWindowNode) n;
			
			if(startTimeWindow.isOverLappingWithTimeWindow(fn.getEntryWindow()) && 
					fn.getResourceNode().getId().equals(node.getId()))
				return fn;
				
		}
		return null;
	}

	public List<FreeTimeWindowNode> getFreeTimeWindowNodes(ResourceNode node) {
		List<FreeTimeWindowNode> ftwNodes = new ArrayList<FreeTimeWindowNode>();
		for (Node n : getNodeMap()) {
			FreeTimeWindowNode fn = (FreeTimeWindowNode) n;
			if(fn.getResourceNode() == node)
				ftwNodes.add(fn);
		}
		return ftwNodes;
		
	}
}
