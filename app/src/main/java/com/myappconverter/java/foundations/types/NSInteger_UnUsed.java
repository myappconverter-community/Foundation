package com.myappconverter.java.foundations.types;

import com.myappconverter.java.foundations.NSObject;

public class NSInteger_UnUsed extends NSObject {
	Long value;

	public int getValue() {
		return value.intValue();
	}

	public long longValue() {
		return value.longValue();
	}

	public int intValue() {
		return value.intValue();
	}

	public void setValue(int value) {
		this.value = Long.valueOf(value);
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NSInteger [value=" + value + "]";
	}

}
