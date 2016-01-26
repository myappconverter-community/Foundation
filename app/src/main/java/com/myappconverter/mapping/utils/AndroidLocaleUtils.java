package com.myappconverter.mapping.utils;

import java.util.Locale;

public class AndroidLocaleUtils {

	public static Locale toLocale(String wrappedString) {
		String[] parts = wrappedString.split("_");
		switch (parts.length) {
		case 3:
			return new Locale(parts[0], parts[1], parts[2]);
		case 2:
			return new Locale(parts[0], parts[1]);
		case 1:
			return new Locale(parts[0]);
		default:
			throw new IllegalArgumentException("Invalid locale: " + wrappedString);
		}
	}

}
