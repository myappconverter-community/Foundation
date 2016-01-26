
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.logging.Logger;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;


public class NSException extends NSObject implements NSCoding, NSCopying {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private NSString name;
	private NSString reason;
	private NSDictionary<NSObject, NSObject> userInfo;
	private Exception exception;

	// General Exception Names

	
	public static final NSString NSGenericException = new NSString("NSGenericException");
	
	public static final NSString NSRangeException = new NSString("NSRangeException");
	
	public static final NSString NSInvalidArgumentException = new NSString(
			"NSInvalidArgumentException");
	
	public static final NSString NSInternalInconsistencyException = new NSString(
			"NSInternalInconsistencyException");
	
	public static final NSString NSMallocException = new NSString("NSMallocException");
	
	public static final NSString NSObjectInaccessibleException = new NSString(
			"NSObjectInaccessibleException");
	
	public static final NSString NSObjectNotAvailableException = new NSString(
			"NSObjectNotAvailableException");
	
	public static final NSString NSDestinationInvalidException = new NSString(
			"NSDestinationInvalidException");
	
	public static final NSString NSPortTimeoutException = new NSString("NSPortTimeoutException");
	
	public static final NSString NSInvalidSendPortException = new NSString(
			"NSInvalidSendPortException");
	
	public static final NSString NSInvalidReceivePortException = new NSString(
			"NSInvalidReceivePortException");
	
	public static final NSString NSPortSendException = new NSString("NSPortSendException");
	
	public static final NSString NSPortReceiveException = new NSString("NSPortReceiveException");
	
	public static final NSString NSOldStyleException = new NSString("NSOldStyleException");

	// Creating and Raising an NSException Object
	public NSException() {
		super();
		this.exception = new Exception();
	}

	/**
	 * @Signature: exceptionWithName:reason:userInfo:
	 * @Declaration : + (NSException *)exceptionWithName:(NSString *)name reason:(NSString *)reason userInfo:(NSDictionary *)userInfo
	 * @Description : Creates and returns an exception object .
	 * @param name The name of the exception.
	 * @param reason A human-readable message string summarizing the reason for the exception.
	 * @param userInfo A dictionary containing user-defined information relating to the exception
	 * @return Return Value The created NSException object or nil if the object couldn't be created.
	 **/
	
	public static NSException exceptionWithNameReasonUserInfo(NSString name, NSString reason,
															  NSDictionary userInfo) {
		NSException excp = new NSException();
		excp.exception = new Exception(reason.getWrappedString());
		excp.name = name;
		excp.reason = reason;
		excp.userInfo = userInfo;
		return excp;
	}

	/**
	 * @Signature: raise:format:
	 * @Declaration : + (void)raise:(NSString *)name format:(NSString *)format, ...
	 * @Description : A convenience method that creates and raises an exception.
	 * @param name The name of the exception.
	 * @param format, A human-readable message string (that is, the exception reason) with conversion specifications for the variable
	 *            arguments that follow.
	 * @param ... Variable information to be inserted into the formatted exception reason (in the manner of printf).
	 * @Discussion The user-defined information is nil for the generated exception object.
	 **/
	
	public static void raiseFormat(NSString name, NSString... format) {
		String myFormat = null;
		NSString myNSString = new NSString();
		if (format.length > 0) {
			myFormat = format[0].getWrappedString();
			if (format[0].getWrappedString() == null)
				throw new IllegalArgumentException();
		}
		myNSString.wrappedString = String.format(myFormat,
				(Object) (Arrays.copyOfRange(format, 0, format.length - 1)));
		NSException excp = NSException.exceptionWithNameReasonUserInfo(name, myNSString, null);
		try {
			excp.raise();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	/**
	 * @Signature: raise:format:arguments:
	 * @Declaration : + (void)raise:(NSString *)name format:(NSString *)format arguments:(va_list)argList
	 * @Description : Creates and raises an exception with the specified name, reason, and arguments.
	 * @param name The name of the exception.
	 * @param format A human-readable message string (that is, the exception reason) with conversion specifications for the variable
	 *            arguments in argList.
	 * @param argList Variable information to be inserted into the formatted exception reason (in the manner of vprintf).
	 * @Discussion The user-defined dictionary of the generated object is nil.
	 **/
	
	public static void raiseFormatArguments(NSString name, NSString format, NSString... argList) {
		String myArgreason = null;
		NSString myReason = format;
		NSString argReason = new NSString();
		if (argList.length > 0) {
			myArgreason = argList[0].getWrappedString();
			if (format == null)
				throw new IllegalArgumentException();
		}
		argReason.wrappedString = String.format(myReason.getWrappedString() + myArgreason,
				(Object) (Arrays.copyOfRange(argList, 0, argList.length - 1)));
		NSException excp = NSException.exceptionWithNameReasonUserInfo(name, argReason, null);
		try {
			excp.raise();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
	}

	/**
	 * @Signature: initWithName:reason:userInfo:
	 * @Declaration : - (id)initWithName:(NSString *)name reason:(NSString *)reason userInfo:(NSDictionary *)userInfo
	 * @Description : Initializes and returns a newly allocated exception object.
	 * @param name The name of the exception.
	 * @param reason A human-readable message string summarizing the reason for the exception.
	 * @param userInfo A dictionary containing user-defined information relating to the exception
	 * @return Return Value The created NSException object or nil if the object couldn't be created.
	 * @Discussion This is the designated initializer.
	 **/
	
	public void initWithNameReasonUserInfo(NSString name, NSString reason, NSDictionary userInfo) {
		this.name = name;
		this.reason = reason;
		this.userInfo = userInfo;
	}

	/**
	 * @Signature: raise
	 * @Declaration : - (void)raise
	 * @Description : Raises the receiver, causing program flow to jump to the local exception handler.
	 * @Discussion When there are no exception handlers in the exception handler stack, unless the exception is raised during the posting of
	 *             a notification, this method calls the uncaught exception handler, in which last-minute logging can be performed. The
	 *             program then terminates, regardless of the actions taken by the uncaught exception handler.
	 **/
	
	public void raise() throws Exception {
		Log.d("Exception ", "Message :" + this.exception.getMessage() + "\n StackTrace: "
				+ Log.getStackTraceString(this.exception));

		throw this.exception;

	}

	// Querying an NSException Object
	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns an NSString object used to uniquely identify the receiver.
	 **/
	
	public NSString name() {
		return this.name;

	}

	public NSString getName() {
		return name();

	}

	/**
	 * @Signature: reason
	 * @Declaration : - (NSString *)reason
	 * @Description : Returns an NSString object containing a “human-readable reason for the receiver.
	 **/
	
	public NSString reason() {
		return this.reason;
	}

	public NSString getReason() {
		return reason();
	}

	/**
	 * @Signature: userInfo
	 * @Declaration : - (NSDictionary *)userInfo
	 * @Description : Returns an NSDictionary object containing application-specific data pertaining to the receiver.
	 * @Discussion Returns nil if no application-specific data exists. As an example, if a method’s return value caused the exception to be
	 *             raised, the return value might be available to the exception handler through this method.
	 **/
	
	public NSDictionary<NSObject, NSObject> userInfo() {
		return this.userInfo;
	}

	public NSDictionary<NSObject, NSObject> getUserInfo() {
		return userInfo();
	}

	// Getting Exception Stack Frames
	/**
	 * @Signature: callStackReturnAddresses
	 * @Declaration : - (NSArray *)callStackReturnAddresses
	 * @Description : Returns the call return addresses related to a raised exception.
	 * @return Return Value An array of NSNumber objects encapsulating NSUInteger values. Each value is a call frame return address. The
	 *         array of stack frames starts at the point at which the exception was first raised, with the first items being the most recent
	 *         stack frames.
	 * @Discussion NSException subclasses posing as the NSException class or subclasses or other API elements that interfere with the
	 *             exception-raising mechanism may not get this information.
	 **/
	
	public NSArray<Object> callStackReturnAddresses() {
		NSArray<Object> nsArray = new NSArray<Object>();
		nsArray.getWrappedList().add(exception.getStackTrace());
		return nsArray;
	}

	public NSArray<Object> getCallStackReturnAddresses() {
		return callStackReturnAddresses();
	}

	/**
	 * @Signature: callStackSymbols
	 * @Declaration : - (NSArray *)callStackSymbols
	 * @Description : Returns an array containing the current call symbols.
	 * @return Return Value An array containing the current call stack symbols.
	 * @Discussion This method returns an array of strings describing the call stack backtrace at the moment the exception was first raised.
	 *             The format of each string is non-negotiable and is determined by the backtrace_symbols() API
	 **/
	
	public NSArray<NSObject> callStackSymbols() {
		NSMutableArray<NSObject> mArray = new NSMutableArray<NSObject>();
		for (StackTraceElement element : this.exception.getStackTrace()) {
			mArray.addObject(NSString.stringWithString(new NSString(element.getMethodName())));
		}
		return mArray;
	}

	public NSArray<NSObject> getCallStackSymbols() {
		return callStackSymbols();
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	public static void NSAssert(boolean condition, NSString description, NSObject... o) {

		if (!condition) {
			NSException excp = NSException.exceptionWithNameReasonUserInfo(new NSString(""),
					description, null);
			try {
				excp.raise();
			} catch (Exception e) {
				LOGGER.info(e.getMessage());
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}

	}

	// Data type
	interface NSUncaughtExceptionHandler {
		void NSUncaughtExceptionHandler(NSException exception);
	}

	// Functions Declarations

	/**
	 * @Description : Marks the start of the exception-handling domain.
	 **/

	
	public static void NS_DURING() {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Marks the end of the local event handler.
	 **/

	
	public static void NS_ENDHANDLER() {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Marks the end of the exception-handling domain and the start of the local exception handler.
	 **/

	
	public static void NS_HANDLER() {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Permits program control to exit from an exception-handling domain with a value of a specified type.
	 * @param val : A value to preserve beyond the exception-handling domain.
	 * @param type : The type of the value specified in val.
	 **/

	
	public static void NS_VALUERETURN(Object val, Object type) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Permits program control to exit from an exception-handling domain.
	 **/

	
	public static void NS_VOIDRETURN() {
		throw new UnsupportedOperationException();

	}

	
	public static double MAX(double a, double b) {
		return Math.max(a, b);
	}

	/**
	 * @Description : Changes the top-level error handler.
	 **/

	
	public static void NSSetUncaughtExceptionHandler(NSUncaughtExceptionHandler handler) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Validates the specified parameter.
	 **/

	
	public static void NSParameterAssert(Object condition) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Returns the top-level error handler.
	 * @return : Return Value A pointer to the top-level error-handling function where you can perform last-minute logging before the
	 *         program terminates.
	 **/

	
	public static NSUncaughtExceptionHandler NSGetUncaughtExceptionHandler() {
		return null;
	}

	/**
	 * @Description : Evaluates the specified parameter.
	 **/

	
	public static void NSCParameterAssert(Object condition) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            placeholders for the arguments.
	 * @param ... : The arguments displayed in the desc string.
	 **/

	
	public static void NSAssert(Object condition, NSString desc, Object... arg) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            a placeholder for a single argument.
	 * @param arg1 : An argument to be inserted, in place, into desc.
	 **/

	
	public static void NSAssert1(Object condition, NSString desc, Object arg1) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            placeholders for two arguments.
	 * @param arg1 : An argument to be inserted, in place, into desc.
	 * @param arg2 : An argument to be inserted, in place, into desc.
	 **/

	
	public static void NSAssert2(Object condition, NSString desc, Object arg1, Object arg2) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            placeholders for three arguments.
	 * @param arg1 : An argument to be inserted, in place, into desc.
	 * @param arg2 : An argument to be inserted, in place, into desc.
	 * @param arg3 : An argument to be inserted, in place, into desc.
	 **/

	
	public static void NSAssert3(Object condition, NSString desc, Object arg1, Object arg2,
								 Object arg3) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            placeholders for four arguments.
	 * @param arg1 : An argument to be inserted, in place, into desc.
	 * @param arg2 : An argument to be inserted, in place, into desc.
	 * @param arg3 : An argument to be inserted, in place, into desc.
	 * @param arg4 : An argument to be inserted, in place, into desc.
	 **/

	
	public static void NSAssert4(Object condition, NSString desc, Object arg1, Object arg2,
								 Object arg3, Object arg4) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if a given condition is false.
	 * @param condition : An expression that evaluates to YES or NO.
	 * @param desc : An NSString object that contains a printf-style string containing an error message describing the failure condition and
	 *            placeholders for five arguments.
	 * @param arg1 : An argument to be inserted, in place, into desc.
	 * @param arg2 : An argument to be inserted, in place, into desc.
	 * @param arg3 : An argument to be inserted, in place, into desc.
	 * @param arg4 : An argument to be inserted, in place, into desc.
	 * @param arg5 : An argument to be inserted, in place, into desc.
	 **/

	
	public static void NSAssert5(Object condition, NSString desc, Object arg1, Object arg2,
								 Object arg3, Object arg4, Object arg5) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : Generates an assertion if the given condition is false.
	 **/

	
	public static void NSCAssert(Object condition, NSString description) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : NSCAssert1 is one of a series of macros that generate assertions if the given condition is false.
	 **/

	
	public static void NSCAssert1(Object condition, NSString description, Object arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Description : NSCAssert2 is one of a series of macros that generate assertions if the given condition is false.
	 **/

	
	public static void NSCAssert2(Object condition, NSString description, Object arg1,
								  Object arg2) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : NSCAssert3 is one of a series of macros that generate assertions if the given condition is false.
	 **/

	
	public static void NSCAssert3(Object condition, NSString description, Object arg1, Object arg2,
								  Object arg3) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : NSCAssert4 is one of a series of macros that generate assertions if the given condition is false.
	 **/

	
	public static void NSCAssert4(Object condition, NSString description, Object arg1, Object arg2,
								  Object arg3, Object arg4) {
		throw new UnsupportedOperationException();

	}

	/**
	 * @Description : NSCAssert5 is one of a series of macros that generate assertions if the given condition is false.
	 **/

	
	public static void NSCAssert5(Object condition, NSString description, Object arg1, Object arg2,
								  Object arg3, Object arg4, Object arg5) {
		throw new UnsupportedOperationException();

	}

}