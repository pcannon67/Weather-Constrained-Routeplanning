package models;

import java.util.ArrayList;
import java.util.List;

public class ResourceNode extends Node{
	
	private int capacity;
	private double duration;
	private boolean[] timeWindow;

	public ResourceNode(String id,int capacity, double duration, int maxTimeSteps) {
		super(id);
		this.capacity = capacity;
		this.duration = duration;
		timeWindow = new boolean[maxTimeSteps];
	}

	public int getCapacity() {
		return capacity;
	}

	public double getDuration() {
		return duration;
	}
	
	public List<TimeWindow> getFreeTimeWindows() {
		return getTimeWindows(false);
	}
	
	public List<TimeWindow> getReservedTimeWindows() {
		return getTimeWindows(true);
	}
	
	public void setTimeWindowReserved(int time) {
		timeWindow[time] = true;
	}
	
	public void setTimeWindowFree(int time) {
		timeWindow[time] = false;
	}
	
	private List<TimeWindow> getTimeWindows(boolean reserved) {
		List<TimeWindow> timeWindows = new ArrayList<TimeWindow>();
		
		boolean foundStart=false;
		int startTime=0;
		for (int i = 0; i < timeWindow.length; i++) {
			if(timeWindow[i]==reserved) {
				if(!foundStart) { 
					//we found a new start for a new freetimewindow
					foundStart = true;
					startTime = i;
				}
			}
			else {
				if(foundStart)
				{
					//we found a start and now we have an end
					timeWindows.add(new TimeWindow(startTime, i-1));
					foundStart = false;
					startTime = 0;
				}
			}
		}
		
		return timeWindows;
	}
}
