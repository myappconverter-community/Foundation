package com.myappconverter.mapping.utils;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;

public class AndroidVersionUtils {

	public static String getVersionName() {
		StringBuilder builder = new StringBuilder();
		builder.append("android: ").append(Build.VERSION.RELEASE);

		Field[] fields = Build.VERSION_CODES.class.getFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			int fieldValue = -1;

			try {
				fieldValue = field.getInt(new Object());
			} catch (IllegalArgumentException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (IllegalAccessException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (NullPointerException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}

			if (fieldValue == Build.VERSION.SDK_INT) {
				builder.append(" : ").append(fieldName);
				// builder.append("sdk=").append(fieldValue);
			}
		}
		return builder.toString();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
