
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.Protocol;


public class NSObjCRuntime {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());

	private static final String TAG = "NSObjCRuntime";

	
	public static final double NSFoundationVersionNumber = 993.00;

	
	public static final int NSNotFound = 999999999;

	
	public static final int NSSortConcurrent = (int) (1L << 0); //
	
	public static final int NSSortStable = (int) (1L << 4);

	
	public static final int NSEnumerationConcurrent = (int) (1L << 0); //
	
	public static final int NSEnumerationReverse = (int) (1L << 1);

	final double NSFoundationVersionNumber_iPhoneOS_2_0 = 678.24;
	final double NSFoundationVersionNumber_iPhoneOS_2_1 = 678.26;
	final double NSFoundationVersionNumber_iPhoneOS_2_2 = 678.29;
	final double NSFoundationVersionNumber_iPhoneOS_3_0 = 678.47;
	final double NSFoundationVersionNumber_iPhoneOS_3_1 = 678.51;
	final double NSFoundationVersionNumber_iPhoneOS_3_2 = 678.60;
	final double NSFoundationVersionNumber_iOS_4_0 = 751.32;
	final double NSFoundationVersionNumber_iOS_4_1 = 751.37;
	final double NSFoundationVersionNumber_iOS_4_2 = 751.49;
	final double NSFoundationVersionNumber_iOS_4_3 = 751.49;
	final double NSFoundationVersionNumber_iOS_5_0 = 881.00;
	final double NSFoundationVersionNumber_iOS_5_1 = 890.10;
	final double NSFoundationVersionNumber_iOS_6_0 = 993.00;
	final double NSFoundationVersionNumber_iOS_6_1 = 993.00;

	
	public static enum NSComparisonResult {

		NSOrderedAscending(-1), //
		NSOrderedSame(0), //
		NSOrderedDescending(1);

		int value;

		public int getValue() {
			return value;
		}

		NSComparisonResult(int v) {
			value = v;
		}
	}

	/**
	 * @Description : Creates and returns a new instance of a given class.
	 * @param aClass : The class of which to create an instance.
	 * @param extraBytes : The number of extra bytes required for indexed instance variables (this value is typically 0).
	 * @param zone : The zone in which to create the new instance (pass NULL to specify the default zone).
	 * @return : Return Value A new instance of aClass or nil if an instance could not be created.
	 **/

	
	Object NSAllocateObject(Class<?> aClass, long extraBytes, NSZone zone) {
		return null;
	}

	/**
	 * @Description : Logs an error message to the Apple System Log facility.
	 **/

	
	public static void NSLog(NSString format, Object... args) {
		StringBuilder concatArg = new StringBuilder();
		if (args != null) {
			List<Object> argsList = Arrays.asList(args);
			for (Object obj : argsList) {
				if (obj != null)
					concatArg.append(obj.toString());
			}
		}
		// TODO finish this later
		String mStr = format.getWrappedString();
		mStr = mStr.replaceAll("%@", "%s");
		mStr = mStr.replaceAll("ld", "d");
		mStr = mStr.replaceAll("lf", "f");
		mStr = mStr.replaceAll("lx", "d");
		mStr = mStr.replaceAll("lu", "d");
		mStr = mStr.replaceAll("(?i)u", "d");
		mStr = mStr.replaceAll("%%", "%c");
		mStr = mStr.replaceAll(".f", "f");
		mStr = mStr.replaceAll("%02i", "%02d");
		mStr = mStr.replaceAll("%i", "%d");

		Log.i(TAG, String.format(mStr, args));
	}

	/**
	 * @Description : Obtains a class by name.
	 * @param aClassName : The name of a class.
	 * @return : Return Value The class object named by aClassName, or nil if no class by that name is currently loaded. If aClassName is
	 *         nil, returns nil.
	 **/

	
	public static Class<?> NSClassFromString(NSString aClassName) {
		try {
			Class<?> classFromName = Class.forName(aClassName.getWrappedString());
			return classFromName;
		} catch (ClassNotFoundException e) {
            LOGGER.info(String.valueOf(e));
			return null;
		}
	}

	/**
	 * @Description : Returns the name of a class as a string.
	 * @param aClass : A class.
	 * @return : Return Value A string containing the name of aClass. If aClass is nil, returns nil.
	 **/

	
	public static NSString NSStringFromClass(Class aClassName) {
		return new NSString(aClassName.getSimpleName());
	}

	/**
	 * @Description : Obtains the actual size and the aligned size of an encoded type.
	 **/

	
	public static char[] NSGetSizeAndAlignment(char[] typePtr, long sizep, long alignp) {
		// not yet covered
		return null;
	}

	/**
	 * @Description : Logs an error message to the Apple System Log facility.
	 **/

	
	public void NSLogv(NSString format, Object[] args) {
        // not yet covered
	}

	/**
	 * @Description : Returns a the protocol with a given name.
	 * @param namestr : The name of a protocol.
	 * @return : Return Value The protocol object named by namestr, or nil if no protocol by that name is currently loaded. If namestr is
	 *         nil, returns nil.
	 **/

	
	Protocol NSProtocolFromString(NSString namestr) {
        // not yet covered
        return null;
	}

	/**
	 * @Description : Returns the selector with a given name.
	 * @param aSelectorName : A string of any length, with any characters, that represents the name of a selector.
	 * @return : Return Value The selector named by aSelectorName. If aSelectorName is nil, or cannot be converted to UTF-8 (this should be
	 *         only due to insufficient memory), returns (SEL)0.
	 **/

	
	SEL NSSelectorFromString(NSString aSelectorName) {
        // not yet covered
        return null;
	}

	/**
	 * @Description : Returns the name of a protocol as a string.
	 * @param proto : A protocol.
	 * @return : Return Value A string containing the name of proto.
	 **/

	
	NSString NSStringFromProtocol(Protocol proto) {
        // not yet covered
        return null;
	}

	/**
	 * @Description : Returns a string representation of a given selector.
	 * @param aSelector : A selector.
	 * @return : Return Value A string representation of aSelector.
	 **/

	
	NSString NSStringFromSelector(SEL aSelector) {
        // not yet covered
        return null;
	}

}