package com.myappconverter.java.foundations.utilities;

public class ExpressNullable {
	public static boolean assertCondition(Object obj) {
		return (obj != null);
	}

	public static boolean assertCondition(boolean obj) {
		return obj;
	}

	public static boolean assertCondition(int obj) {
		return (obj != 0);
	}

	public static boolean assertCondition(float obj) {
		return (obj != 0);
	}

	public static boolean assertCondition(char obj) {
		return (obj != 0);
	}
}
