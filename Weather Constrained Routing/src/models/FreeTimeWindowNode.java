package models;

public class FreeTimeWindowNode extends Node {

	private TimeWindow timeWindow;
	
	public FreeTimeWindowNode(String id, TimeWindow timeWindow) {
		super(id);
		this.timeWindow = timeWindow;
	}
	
	public FreeTimeWindowNode(String id, int startTime, int endTime) {
		super(id);
		timeWindow = new TimeWindow(startTime,endTime);
	}

	public TimeWindow getTimeWindow() {
		return timeWindow;
	}
}
