package com.myappconverter.mapping.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndroidArrayUtils {

	public static Object toObject(char[] characterSetFromFile) {
		return null;
	}

	public static void reverse(Byte[] wrappedData) {
		List<Byte> byteList = Arrays.asList(wrappedData);
		Collections.reverse(byteList);
	}

	public static void reverse(byte[] array) {
		if (array == null) {
			return;
		}
		int i = 0;
		int j = array.length - 1;
		byte tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
	}

	public static Byte[] toObject(byte[] bytesPrim) {
		if (bytesPrim == null) {
			return null;
		} else if (bytesPrim.length == 0) {
			return null;
		}

		Byte[] bytes = new Byte[bytesPrim.length];
		int i = 0;
		for (byte b : bytesPrim)
			bytes[i++] = b; // Autoboxing
		return bytes;

	}

	public static byte[] toPrimitive(Byte[] oBytes) {
		byte[] bytes = new byte[oBytes.length];
		for (int i = 0; i < oBytes.length; i++) {
			bytes[i] = oBytes[i].byteValue();
		}
		return bytes;
	}

}
