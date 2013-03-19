package models;

public class FreeTimeWindowGraph extends Graph {

	public FreeTimeWindowNode getFreeTimeWindowNode(int startTime,
			ResourceNode resourceNode) {
		
		TimeWindow startTimeWindow = new TimeWindow(startTime, startTime);
		
		for (Node n : getNodeMap().values()) {
			FreeTimeWindowNode fn = (FreeTimeWindowNode) n;
			if(startTimeWindow.isOverLappingWithTimeWindow(fn.getEntryWindow()) && 
					fn.getResourceNode() == resourceNode)
				return fn;
				
		}
		return null;
	}

}
