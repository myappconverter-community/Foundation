package com.myappconverter.java.foundations.utilities;

public abstract interface _NSFoundationCollection {
	public abstract int _shallowHashCode();

	public enum NullHandling {
		CheckAndFail, CheckAndSkip, NoCheck
	}
}