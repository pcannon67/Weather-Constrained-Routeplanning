package models;

public class FreeTimeWindowNode extends Node {

	private TimeWindow timeWindow;
	private ResourceNode resourceNode;
	
	public FreeTimeWindowNode(String id, TimeWindow timeWindow, ResourceNode resourceNode) {
		super(id);
		this.timeWindow = timeWindow;
		this.resourceNode = resourceNode;
	}
	
	public FreeTimeWindowNode(String id, int startTime, int endTime) {
		super(id);
		timeWindow = new TimeWindow(startTime,endTime);
	}

	public ResourceNode getResourceNode() {
		return resourceNode;
	}
	
	public TimeWindow getEntryWindow() {
		return new TimeWindow(timeWindow.getStartTime(), timeWindow.getEndTime() - (int)Math.ceil(resourceNode.getDuration())); 
	}
	
	public TimeWindow getExitWindow() {
		return new TimeWindow(timeWindow.getStartTime() + (int)Math.ceil(resourceNode.getDuration()), timeWindow.getEndTime());
	}
	
	public TimeWindow getTimeWindow() {
		return timeWindow;
	}

	public boolean containsTime(TimeWindow exitTimeWindow) {
		return exitTimeWindow.isOverLappingWithTimeWindow(getEntryWindow());
	}
}
