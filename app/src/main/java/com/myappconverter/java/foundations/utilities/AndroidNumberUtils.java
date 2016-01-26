package com.myappconverter.java.foundations.utilities;

public class AndroidNumberUtils {

	public static boolean isNumber(String numberValue) {
		boolean isnumeric = false;

		if (numberValue != null && !numberValue.equals("")) {
			isnumeric = true;
			char chars[] = numberValue.toCharArray();

			for (int d = 0; d < chars.length; d++) {
				isnumeric &= Character.isDigit(chars[d]);

				if (!isnumeric)
					break;
			}
		}
		return isnumeric;
	}

}
