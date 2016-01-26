
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;



public class NSNull extends NSObject implements NSCopying, NSSecureCoding {

	// public static final NSNull nullObject = new NSNull();

	private NSNull() {
	}

	private static class NSNullHolder {
		private final static NSNull instance = null;
	}

	/**
	 * @Signature: null
	 * @Declaration : + (NSNull *)null
	 * @Description : Returns the singleton instance of NSNull.
	 * @return Return Value The singleton instance of NSNull.
	 **/
	
	public static NSNull _null() {
		return NSNullHolder.instance;
	}

	public static boolean isNull() {
		return true;
	}

	public static boolean isNotNull() {
		return false;
	}

	public static boolean isEmpty() {
		return true;
	}

	public static boolean isNotEmpty() {
		return false;
	}

	public static interface NSNullDetection {

		public boolean isNull();

		public boolean isNotNull();

		public boolean isEmpty();

		public boolean isNotEmpty();
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}