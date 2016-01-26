
package com.myappconverter.java.foundations;


import java.math.BigDecimal;


public class NSByteOrder {

	private NSByteOrder() {
	}

	
	public static enum _NSByteOrder {
		NS_UnknownByteOrder, //
		NS_LittleEndian, //
		NS_BigEndian;//

		public int getValue() {
			return ordinal();
		}

	};

	
	public static class NSSwappedFloat {
		public NSSwappedFloat(float x) {
			this.v = x;
		}

		float v;

		public float floatValue() {
			return v;
		}
	}

	
	public static class NSSwappedDouble {
		public NSSwappedDouble(double x) {
			v = BigDecimal.valueOf(x);
		}

		BigDecimal v;

		public double doubleValue() {
			return v.doubleValue();
		}
	}

	// Functions Declarations

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static double NSSwapBigDoubleToHost(NSSwappedDouble x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static float NSSwapBigFloatToHost(NSSwappedFloat x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static int NSSwapBigIntToHost(int x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapBigLongLongToHost(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapBigLongToHost(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static short NSSwapBigShortToHost(short x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedDouble NSSwapDouble(NSSwappedDouble x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedFloat NSSwapFloat(NSSwappedFloat x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedDouble NSSwapHostDoubleToBig(double x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedDouble NSSwapHostDoubleToLittle(double x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedFloat NSSwapHostFloatToBig(float x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static NSSwappedFloat NSSwapHostFloatToLittle(float x) {
		return null;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static int NSSwapHostIntToBig(int x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static int NSSwapHostIntToLittle(int x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapHostLongLongToBig(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapHostLongLongToLittle(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapHostLongToBig(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapHostLongToLittle(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static short NSSwapHostShortToBig(short x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static short NSSwapHostShortToLittle(short x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static int NSSwapInt(int inv) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static double NSSwapLittleDoubleToHost(NSSwappedDouble x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static float NSSwapLittleFloatToHost(NSSwappedFloat x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static int NSSwapLittleIntToHost(int x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapLittleLongLongToHost(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapLittleLongToHost(long x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static short NSSwapLittleShortToHost(short x) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapLong(long inv) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 **/

	
	public static long NSSwapLongLong(long inv) {
		return 0;
	}

	/**
	 * @Description : A utility for swapping the bytes of a number.
	 * @Discussion : Swaps the low-order and high-order bytes of inv and returns the resulting value.
	 **/

	
	public static short NSSwapShort(short inv) {
		return 0;
	}

	/**
	 * @Description : Returns the endian format.
	 * @return : Return Value The endian format, either NS_LittleEndian or NS_BigEndian.
	 **/

	
	public static long NSHostByteOrder() {
		return 0;
	}

	/**
	 * @Description : Performs a type conversion.
	 * @Discussion: Converts the double value in x to a value whose bytes can be swapped. This function does not actually swap the bytes of
	 *              x. You should not need to call this function directly
	 **/

	
	public static NSSwappedDouble NSConvertHostDoubleToSwapped(double x) {
		return new NSSwappedDouble(x);
	}

	/**
	 * @Description : Performs a type conversion.
	 **/

	
	public static NSSwappedFloat NSConvertHostFloatToSwapped(float x) {
		return new NSSwappedFloat(x);
	}

	/**
	 * @Description : Performs a type conversion.
	 **/

	
	public static double NSConvertSwappedDoubleToHost(NSSwappedDouble x) {
		return x.doubleValue();
	}

	/**
	 * @Description : Performs a type conversion.
	 **/

	
	public static float NSConvertSwappedFloatToHost(NSSwappedFloat x) {
		return x.floatValue();
	}

}