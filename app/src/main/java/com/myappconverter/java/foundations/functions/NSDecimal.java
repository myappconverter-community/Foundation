package com.myappconverter.java.foundations.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.util.Log;

import com.myappconverter.java.foundations.NSLocale;
import com.myappconverter.java.foundations.NSNumber;
import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.utilities.AndroidNumberUtils;


public class NSDecimal extends NSNumber {

	// Enumeration

	public static enum NSRoundingMode {
		NSRoundPlain, //
		NSRoundDown, //
		NSRoundUp, //
		NSRoundBankers
	};


	public enum NSCalculationError {

		NSCalculationNoError, //
		NSCalculationLossOfPrecision, //
		NSCalculationUnderflow, //
		NSCalculationOverflow, //
		NSCalculationDivideByZero

	}

	private Number wrappedNumber;

	public Number getWrappedNumber() {
		return wrappedNumber;
	}

	public void setWrappedNumber(Number number) {
		this.wrappedNumber = number;
	}

	/**
	 * @Description : Copies the value of a decimal number.
	 **/

	static public void NSDecimalCopy(NSDecimal destination, NSDecimal source) {
		destination = source;
	}

	/**
	 * @Description : Returns a string representation of the decimal value.
	 **/


	static public NSString NSDecimalString(NSDecimal dcm, NSLocale locale) {
		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(locale.getWrappedLocale());
		unusualSymbols.setDecimalSeparator('.');
		unusualSymbols.setGroupingSeparator(',');
		String strange = "#,##0.###";
		DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
		return new NSString(weirdFormatter.format(dcm));
	}

	/**
	 * @Description : Compares two decimal values.
	 * @return : Return Value NSOrderedDescending if leftOperand is bigger than rightOperand; NSOrderedAscending if rightOperand is bigger
	 *         than leftOperand; or NSOrderedSame if the two operands are equal.
	 **/
	public static NSComparisonResult NSDecimalCompare(NSDecimal leftOperand, NSDecimal rightOperand) {
		if (leftOperand.wrappedNumber.byteValue() < rightOperand.wrappedNumber.byteValue())
			return NSComparisonResult.NSOrderedAscending;
		if (leftOperand.wrappedNumber.byteValue() > rightOperand.wrappedNumber.byteValue())
			return NSComparisonResult.NSOrderedDescending;
		return NSComparisonResult.NSOrderedSame;
	}

	/**
	 * @Description : Adds two decimal values.
	 **/
	public static NSCalculationError NSDecimalAdd(NSDecimal result, final NSDecimal leftOperand, final NSDecimal rightOperand,
			NSRoundingMode roundingMode) {
		result.wrappedNumber = leftOperand.wrappedNumber.doubleValue() + rightOperand.wrappedNumber.doubleValue();

		switch (roundingMode) {
		case NSRoundUp:

			break;
		case NSRoundDown:

			break;
		case NSRoundBankers:

			break;
		case NSRoundPlain:

			break;

		default:
			Math.round(result.wrappedNumber.doubleValue());
			break;
		}

		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Subtracts one decimal value from another.
	 **/
	public static NSCalculationError NSDecimalSubtract(NSDecimal result, final NSDecimal leftOperand, final NSDecimal rightOperand,
			NSRoundingMode roundingMode) {

		result.wrappedNumber = leftOperand.wrappedNumber.doubleValue() - rightOperand.wrappedNumber.doubleValue();
		Math.round(result.wrappedNumber.doubleValue());
		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Multiplies two decimal numbers together.
	 **/
	public static NSCalculationError NSDecimalMultiply(NSDecimal result, NSDecimal leftOperand, NSDecimal rightOperand,
			NSRoundingMode roundingMode) {

		result.wrappedNumber = leftOperand.wrappedNumber.intValue() * rightOperand.wrappedNumber.intValue();
		Math.round(result.wrappedNumber.doubleValue());
		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Divides one decimal value by another.
	 **/
	public static NSCalculationError NSDecimalDivide(NSDecimal result, NSDecimal leftOperand, NSDecimal rightOperand,
			NSRoundingMode roundingMode) {
		try {
			result.wrappedNumber = leftOperand.wrappedNumber.intValue() / rightOperand.wrappedNumber.intValue();
			Math.round(result.wrappedNumber.doubleValue());
			return NSCalculationError.NSCalculationNoError;
		} catch (Exception e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			return NSCalculationError.NSCalculationDivideByZero;
		}
	}

	/**
	 * @Description : Raises the decimal value to the specified power.
	 **/


	public static NSCalculationError NSDecimalPower(NSDecimal result, NSDecimal number, int power, NSRoundingMode roundingMode) {

		result.wrappedNumber = Math.pow(number.wrappedNumber.doubleValue(), power);
		Math.round(result.wrappedNumber.doubleValue());
		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Multiplies a decimal by the specified power of 10.
	 **/
	public static NSCalculationError NSDecimalMultiplyByPowerOf10(NSDecimal result, final NSDecimal nbr, short power,
			NSRoundingMode roundingMode) {
		result.wrappedNumber = nbr.wrappedNumber.byteValue() * Math.pow(10, power);
		Math.round(result.wrappedNumber.doubleValue());
		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Compacts the decimal structure for efficiency.
	 **/
	public static void NSDecimalCompact(NSDecimal number) {
		RoundingMode.valueOf(number.wrappedNumber.intValue());
	}

	/**
	 * @Description : Rounds off the decimal value.
	 **/


	public static void NSDecimalRound(NSDecimal result, final NSDecimal number, int scale, NSRoundingMode mode) {
		// TODO check that
		RoundingMode roundMode = RoundingMode.valueOf(mode.ordinal());
		int round = roundMode.ordinal();
		// set precision
		MathContext mc = new MathContext(round);
		BigDecimal bd = new BigDecimal(number.wrappedNumber.doubleValue());
		// set scale
		bd.setScale(scale);
		result.wrappedNumber = bd.round(mc).doubleValue();
	}

	/**
	 * @Description : Normalizes the internal format of two decimal numbers to simplify later operations.
	 **/
	public static NSCalculationError NSDecimalNormalize(NSDecimal number1, NSDecimal number2, NSRoundingMode roundingMode) {
		// FIXME
		return NSCalculationError.NSCalculationNoError;
	}

	/**
	 * @Description : Returns a Boolean that indicates whether a given decimal contains a valid number.
	 * @return : Return Value YES if the value in decimal represents a valid number, otherwise NO. For more information, see Number and
	 *         Value Programming Topics.
	 **/
	public static boolean NSDecimalIsNotANumber(final NSDecimal dcm) {
		return AndroidNumberUtils.isNumber(dcm.getNumberValue());
	}

	// Fucntions Declarations

	/**
	 * @Description : Returns a string representation of the decimal value.
	 **/

	public static NSString NSDecimalString(final NSDecimal dcm, Object locale) {
		return null;
	}

}
