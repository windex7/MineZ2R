package util;

public class RandomUtil {
	private double minrand;
	private double maxrand;

	public RandomUtil(double min, double max) {
		this.minrand = min;
		this.maxrand = max;
	}

	public double getRand() {
		double rand = Math.random();
		return rand * (maxrand - minrand) + minrand;
	}

	public static double getMathRand() {
		return Math.random();
	}

	public static boolean onPossibility(double p) {
		if (p > getMathRand()) return true;
		else return false;
	}
}
