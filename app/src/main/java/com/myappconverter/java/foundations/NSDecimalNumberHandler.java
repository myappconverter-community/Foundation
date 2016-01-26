
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSDecimalNumberBehaviors;


public class NSDecimalNumberHandler extends NSObject implements NSCoding, NSDecimalNumberBehaviors {

	private NSRoundingMode nsroundingmode;
	private short scale;
	private Boolean raiseOnExactness;
	private Boolean raiseOnOverflow;
	private Boolean raiseOnUnderflow;
	private Boolean raiseOnDivideByZero;

	private static NSDecimalNumberHandler nsDecimalNumberHandler;

	public NSDecimalNumberHandler() {
	}

	public NSDecimalNumberHandler(NSRoundingMode nsroundingmode, short scale, Boolean raiseOnExactness, Boolean raiseOnOverflow,
			Boolean raiseOnUnderflow, Boolean raiseOnDivideByZero) {
		super();
		this.nsroundingmode = nsroundingmode;
		this.scale = scale;
		this.raiseOnExactness = raiseOnExactness;
		this.raiseOnOverflow = raiseOnOverflow;
		this.raiseOnUnderflow = raiseOnUnderflow;
		this.raiseOnDivideByZero = raiseOnDivideByZero;
	}

	// Creating a Decimal Number Handler

	/**
	 * @Signature: defaultDecimalNumberHandler
	 * @Declaration : + (id)defaultDecimalNumberHandler
	 * @Description : Returns the default instance of NSDecimalNumberHandler.
	 * @return Return Value The default instance of NSDecimalNumberHandler.
	 * @Discussion This default decimal number handler rounds to the closest possible return value. It assumes your need for precision does
	 *             not exceed 38 significant digits, and it raises an exception when its NSDecimalNumber object tries to divide by 0 or when
	 *             its NSDecimalNumber object produces a number too big or too small to be represented.
	 **/
	
	public static void defaultDecimalNumberHandler() {
		// not yet covered
	}

	/**
	 * @Signature: decimalNumberHandlerWithRoundingMode:scale:raiseOnExactness: raiseOnOverflow:raiseOnUnderflow: raiseOnDivideByZero :
	 * @Declaration : + (id)decimalNumberHandlerWithRoundingMode:(NSRoundingMode) roundingMode scale:(short)scale
	 *              raiseOnExactness:(BOOL)raiseOnExactness raiseOnOverflow:(BOOL)raiseOnOverflow raiseOnUnderflow:(BOOL)raiseOnUnderflow
	 *              raiseOnDivideByZero:(BOOL)raiseOnDivideByZero
	 * @Description : Returns an NSDecimalNumberHandler object with customized behavior.
	 * @param roundingMode The rounding mode to use. There are four possible values: NSRoundUp, NSRoundDown, NSRoundPlain, and
	 *            NSRoundBankers.
	 * @param scale The number of digits a rounded value should have after its decimal point.
	 * @param raiseOnExactness If YES, in the event of an exactness error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method.
	 * @param raiseOnOverflow If YES, in the event of an overflow error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method
	 * @param raiseOnUnderflow If YES, in the event of an underflow error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method
	 * @param raiseOnDivideByZero If YES, in the event of a divide by zero error the handler will raise an exception, otherwise it will
	 *            ignore the error and return control to the calling method
	 * @return Return Value An NSDecimalNumberHandler object with customized behavior.
	 * @Discussion See the NSDecimalNumberBehaviors protocol specification for a complete explanation of the possible behaviors.
	 **/
	
	public static NSDecimalNumberHandler decimalNumberHandlerWithRoundingModeScaleRaiseOnExactnessRaiseOnOverflowRaiseOnUnderflowRaiseOnDivideByZero(
			NSRoundingMode nsroundingmode, short scale, boolean raiseOnExactness, boolean raiseOnOverflow, boolean raiseOnUnderflow,
			boolean raiseOnDivideByZero) {

		nsDecimalNumberHandler = new NSDecimalNumberHandler(nsroundingmode, scale, raiseOnExactness, raiseOnOverflow, raiseOnUnderflow,
				raiseOnDivideByZero);
		return nsDecimalNumberHandler;

	}

	// Initializing a Decimal Number Handler
	/**
	 * @Signature: initWithRoundingMode:scale:raiseOnExactness:raiseOnOverflow: raiseOnUnderflow:raiseOnDivideByZero:
	 * @Declaration : - (id)initWithRoundingMode:(NSRoundingMode)roundingMode scale:(short)scale raiseOnExactness:(BOOL)raiseOnExactness
	 *              raiseOnOverflow:(BOOL)raiseOnOverflow raiseOnUnderflow:(BOOL)raiseOnUnderflow
	 *              raiseOnDivideByZero:(BOOL)raiseOnDivideByZero
	 * @Description : Returns an NSDecimalNumberHandler object initialized so it behaves as specified by the method’s arguments.
	 * @param roundingMode The rounding mode to use. There are four possible values: NSRoundUp, NSRoundDown, NSRoundPlain, and
	 *            NSRoundBankers.
	 * @param scale The number of digits a rounded value should have after its decimal point.
	 * @param raiseOnExactness If YES, in the event of an exactness error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method.
	 * @param raiseOnOverflow If YES, in the event of an overflow error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method
	 * @param raiseOnUnderflow If YES, in the event of an underflow error the handler will raise an exception, otherwise it will ignore the
	 *            error and return control to the calling method
	 * @param raiseOnDivideByZero If YES, in the event of a divide by zero error the handler will raise an exception, otherwise it will
	 *            ignore the error and return control to the calling method
	 * @return Return Value An initialized NSDecimalNumberHandler object initialized with customized behavior. The returned object might be
	 *         different than the original receiver.
	 * @Discussion See the NSDecimalNumberBehaviors protocol specification for a complete explanation of the possible behaviors.
	 **/
	
	public void initWithRoundingModeScaleRaiseOnExactnessRaiseOnOverflowRaiseOnUnderflowRaiseOnDivideByZero() {
        // not yet covered
	}

	// Adopted Protocols
	
	@Override
	public NSRoundingMode roundingMode() {
		return null;
	}

	
	@Override
	public short scale() {
		return 0;
	}

	
	@Override
	public NSDecimalNumber exceptionDuringOperationErrorLeftOperandRightOperand(SEL method, NSCalculationError error,
			NSDecimalNumber leftOperand, NSDecimalNumber rightOperand) {
		return null;
	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
        //
	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}