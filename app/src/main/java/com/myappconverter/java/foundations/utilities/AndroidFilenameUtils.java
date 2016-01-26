package com.myappconverter.java.foundations.utilities;

import java.text.Normalizer;

public class AndroidFilenameUtils {

	public static final Object EXTENSION_SEPARATOR = ".";

	public static String getExtension(String wrappedString) {
		int lastIndex = wrappedString.lastIndexOf(".");
		return wrappedString.substring(lastIndex, wrappedString.length());
	}

	public static String normalize(String wrappedString) {
		return Normalizer.normalize(wrappedString, Normalizer.Form.NFD);
	}
}
