package models;

import java.util.ArrayList;
import java.util.List;

public class ResourceNode extends Node{
	
	private int capacity;
	private double duration;
	private int[] timeWindow;

	public ResourceNode(String id, double x, double y, int capacity, double duration, int maxTimeSteps) {
		super(id,x,y);
		this.capacity = capacity;
		this.duration = duration;
		timeWindow = new int[maxTimeSteps];
	}

	public int[] getTimeWindow() {
		return timeWindow;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public double getDuration() {
		return duration;
	}
	
	public List<TimeWindow> getFreeTimeWindows() {
		List<TimeWindow> timeWindows = new ArrayList<TimeWindow>();
		
		boolean foundStart=false;
		int startTime=0;
		for (int i = 0; i < timeWindow.length; i++) {
			if(timeWindow[i] < capacity) {
				if(!foundStart) { 
					//we found a new start for a new freetimewindow
					foundStart = true;
					startTime = i;
				}
				
				if(i == timeWindow.length-1) {
					int endTime = i;
					if( (endTime-startTime) >= duration)
						timeWindows.add(new TimeWindow(startTime, endTime));
				}
			}
			else {
				if(foundStart)
				{
					//we found a start and now we have an end
					int endTime = i-1;
					
					if( (endTime-startTime) >= duration)
						timeWindows.add(new TimeWindow(startTime, endTime));
					
					foundStart = false;
					startTime = 0;
				}
			}
		}
		
		return timeWindows;
	}
	
	public void setTimeWindowReserved(TimeWindow tw) {
		for (int i = tw.getStartTime(); i <= tw.getEndTime(); i++) {
			timeWindow[i] += 1;
		}
	}

	public boolean isANode() {
		return !getId().contains(":");
	}
}
