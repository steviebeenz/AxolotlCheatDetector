package me.omgpandayt.acd.util;

public class NumberUtil {

	public static double decimals(double v, int d) {
		return ((Math.floor((double)v * (10 ^ d)))/(10 ^ d));
	}
	
}
