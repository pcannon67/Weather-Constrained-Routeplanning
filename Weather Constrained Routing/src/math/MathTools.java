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
	
	public static int randomInRange(int min, int max) {
		Random r = new Random();
		return r.nextInt(max-min) + min;
	}
	
	public static double euclideanDistance(double x1, double y1, double x2, double y2) {
		double dx = Math.abs(x1-x2);
		double dy = Math.abs(y1-y2);
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public static int avg(int[] arr) {
		int result=0;
		int count=0;
		for(int i=0; i < arr.length; i++) {
			result=result + arr[i];
			if(arr[i]>0)
				count++;
		}
		if(count>0)
			return result/count;
		else
			return 0;
	}
	
	public static long avg(long[] arr) {
		long result=0;
		int count=0;
		for(int i=0; i < arr.length; i++) {
			result=result + arr[i];
			if(arr[i]>0)
				count++;
		}
		if(count>0)
			return result/count;
		else
			return 0;
	}
	
	public static double avg(double[] arr) {
		double result=0;
		int count=0;
		for(int i=0; i < arr.length; i++) {
			result=result + arr[i];
			if(arr[i]>0)
				count++;
		}
		
		if(count>0)
			return result/count;
		else
			return 0;
	}
}
