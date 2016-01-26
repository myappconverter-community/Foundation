package com.myappconverter.mapping.utils;

public class AndroidStringUtils {

	public static boolean containsIgnoreCase(String str, String token) {
		return str.toLowerCase().contains(token.toLowerCase());
	}

	public static boolean contains(String str, String token) {
		return str.contains(token);
	}

	public static int indexOf(String str, String token) {
		return str.indexOf(token);
	}

}
