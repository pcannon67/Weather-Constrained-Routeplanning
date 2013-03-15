package models;

public abstract class Resource {
	private int capacity;
	private double duration;
	
	private boolean[] availability;

	public Resource(int capacity, double duration) {
		this.capacity = capacity;
		this.duration = duration;
	}

	public boolean[] getAvailability() {
		return availability;
	}

	public void setAvailability(boolean[] availability) {
		this.availability = availability;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}  
}
