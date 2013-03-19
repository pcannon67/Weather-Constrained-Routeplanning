package models;

import java.util.Random;

import math.MathTools;

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
	
	public boolean isOverLappingWithTimeWindow(TimeWindow other) {
		for(int i=startTime ; i <= endTime;i++) {
			if(i>= other.getStartTime() && i<= other.getEndTime())
				return true;
		}
		return false;
	}
	
	public static TimeWindow generateRandomTimeWindow(int maxTime) {
		int start = (new Random()).nextInt(maxTime);
		int end = MathTools.randomInRange(start, maxTime);
		return new TimeWindow(start, end);
	}
	
	@Override
    public String toString() {
        return "Timewindow: start: " + startTime + " end: " + endTime;
    }
}
