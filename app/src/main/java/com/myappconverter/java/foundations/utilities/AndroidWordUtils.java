package com.myappconverter.java.foundations.utilities;

import java.util.Locale;

public class AndroidWordUtils {

	public static String capitalizeFully(String format) {
		return format.toUpperCase(Locale.getDefault());
	}

	public static String capitalize(String wrappedString) {
		return wrappedString.toUpperCase(Locale.getDefault());
	}

}
