package com.myappconverter.mapping.utils;

import android.text.Html;

public class AndroidStringEscapeUtils {

	public static String escapeHtml(String currentTokenString) {
		return Html.escapeHtml(currentTokenString);
	}
}
