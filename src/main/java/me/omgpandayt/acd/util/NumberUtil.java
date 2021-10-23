package me.omgpandayt.acd.util;

public class NumberUtil {

	public static double decimals(double v, int d) {
		double a = Math.pow(10, d);
		return (Math.floor(v * a)) / a;
	}
	
}
