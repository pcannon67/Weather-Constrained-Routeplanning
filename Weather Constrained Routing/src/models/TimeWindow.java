package models;

public class TimeWindow {

	private int startTime;
	private int endTime;

	public TimeWindow(int startTime, int endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public int getEndTime() {
		return endTime;
	}
}
