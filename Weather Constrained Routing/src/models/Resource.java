package models;

public abstract class Resource {
	private boolean[] availability;

	public boolean[] getAvailability() {
		return availability;
	}

	public void setAvailability(boolean[] availability) {
		this.availability = availability;
	}  
}
