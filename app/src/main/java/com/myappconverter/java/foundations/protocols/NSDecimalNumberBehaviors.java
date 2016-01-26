package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSDecimalNumber;
import com.myappconverter.java.foundations.SEL;

public interface NSDecimalNumberBehaviors {

	// 1
	/**
	 * @Signature: exceptionDuringOperation:error:leftOperand:rightOperand:
	 * @Declaration : - (NSDecimalNumber *)exceptionDuringOperation:(SEL)method error:(NSCalculationError)error leftOperand:(NSDecimalNumber
	 *              *)leftOperand rightOperand:(NSDecimalNumber *)rightOperand
	 * @Description : Specifies what an NSDecimalNumber object will do when it encounters an error. (required)
	 * @Discussion There are four possible values for error, described in NSCalculationError. The first three have to do with limits on the
	 *             ability of NSDecimalNumber to represent decimal numbers. An NSDecimalNumber object can represent any number that can be
	 *             expressed as mantissa x 10^exponent, where mantissa is a decimal integer up to 38 digits long, and exponent is between
	 *             –256 and 256. The fourth results from the caller trying to divide by 0. In implementing
	 *             exceptionDuringOperation:error:leftOperand:rightOperand:, you can handle each of these errors in several ways: Raise an
	 *             exception. For an explanation of exceptions, see Exception Programming Topics. Return nil. The calling method will return
	 *             its value as though no error had occurred. If error is NSCalculationLossOfPrecision, method will return an imprecise
	 *             value—that is, one constrained to 38 significant digits. If error is NSCalculationUnderflow or NSCalculationOverflow,
	 *             method will return NSDecimalNumber's notANumber. You shouldn’t return nil if error is NSDivideByZero. Correct the error
	 *             and return a valid NSDecimalNumber object. The calling method will use this as its own return value.
	 **/

	public NSDecimalNumber exceptionDuringOperationErrorLeftOperandRightOperand(SEL method, NSCalculationError error,
																				NSDecimalNumber leftOperand, NSDecimalNumber rightOperand);

	// 2
	/**
	 * @Signature: roundingMode
	 * @Declaration : - (NSRoundingMode)roundingMode
	 * @Description : Returns the way that NSDecimalNumber's decimalNumberBy... methods round their return values. (required)
	 * @return Return Value Returns the current rounding mode. See “NSRoundingMode” for possible values.
	 **/

	public NSRoundingMode roundingMode();

	// 3
	/**
	 * @Signature: scale
	 * @Declaration : - (short)scale
	 * @Description : Returns the number of digits allowed after the decimal separator. (required)
	 * @return Return Value The number of digits allowed after the decimal separator.
	 * @Discussion This method limits the precision of the values returned by NSDecimalNumber’s decimalNumberBy... methods. If scale returns
	 *             a negative value, it affects the digits before the decimal separator as well. If scale returns NSDecimalNoScale, the
	 *             number of digits is unlimited. Assuming that roundingMode returns NSRoundPlain, different values of scale have the
	 *             following effects on the number 123.456: Scale Return Value NSDecimalNoScale 123.456 2 123.45 0 123 –2 100
	 **/

	public short scale();

	public static enum NSRoundingMode {
		NSRoundPlain, NSRoundDown, NSRoundUp, NSRoundBankers
	}

	public static enum NSCalculationError {
		NSCalculationNoError, NSCalculationLossOfPrecision, NSCalculationUnderflow, NSCalculationOverflow, NSCalculationDivideByZero
	}

}
