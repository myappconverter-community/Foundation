package com.myappconverter.java.foundations.utilities;

import java.util.HashSet;
import java.util.Set;

public class SetsUtils {

	public static <T> Set<T> union(Set<T> s1, Set<T> s2) {
		Set<T> result = new HashSet<T>();
		result.addAll(s1);
		result.addAll(s2);
		return result;
	}

	public static <T> Set<T> intersection(Set<T> s1, Set<T> s2) {
		Set<T> result = new HashSet<T>();
		for (T elm : s1) {
			if (s2.contains(elm)) {
				result.add(elm);
			}
		}
		return result;
	}
}
