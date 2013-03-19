package config;

public class GraphConsts extends Consts {
	
	public static final int MAX_TIME_STEPS = 10; //the max time steps we may consider a solution
	public static int DEFAULT_RESOURCE_NODE_CAPACITY = 1; //indicated how many agents can occupy a resource node
	public static int DEFAULT_RESOURCE_NODE_DURATION = 1; //indicates how long it takes to traverse a node
	
	public static double VEHICLE_SPEED = 1.0; //1 step per time-unit
}
