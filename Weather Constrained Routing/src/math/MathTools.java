package math;

import java.util.Random;

public class MathTools {

	protected static Random random = new Random();
	
	public static double randomInRange(double min, double max) {
	  double range = max - min;
	  double scaled = random.nextDouble() * range;
	  double shifted = scaled + min;
	  return shifted;
	}
}
