package models;

public class ResourceGraph extends Graph {

	public void addRandomWeatherOverlay(int maxTimeSteps) {
		for (Node n : getNodeMap().values()) {
			ResourceNode fn = (ResourceNode) n;
			fn.setTimeWindowReserved(TimeWindow.generateRandomTimeWindow(maxTimeSteps));
		}
	}
	
}
