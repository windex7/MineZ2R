package util;

public class RandomUtil {

	public static double getRand() {
		return Math.random();
	}

	public static double getRand(double min, double max) {
		double rand = Math.random();
		return rand * (max - min) + min;
	}

	public static boolean onPossibility(double p) {
		if (p > getRand()) return true;
		else return false;
	}
}
