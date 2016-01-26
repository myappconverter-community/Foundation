
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSTextCheckingResult.NSTextCheckingTypes;



public class NSDataDetector extends NSRegularExpression {

	public NSDataDetector(NSString pattern) {
		super(pattern);
	}

	public NSDataDetector() {
	}

	// Creating Data Detector Instances
	/**
	 * @Signature: dataDetectorWithTypes:error:
	 * @Declaration : + (NSDataDetector *)dataDetectorWithTypes:(NSTextCheckingTypes)checkingTypes error:(NSError **)error
	 * @Description : Creates and returns a new data detector instance.
	 * @param checkingTypes The checking types. The supported checking types are a subset of the types specified in NSTextCheckingType.
	 *            Those constants can be combined using the C-bitwise OR operator.
	 * @param error An out parameter that if an error occurs during initialization contains the encountered error.
	 * @return Return Value Returns the newly initialized data detector. If an error was encountered returns nil, and error contains the
	 *         error.
	 * @Discussion Currently, the supported data detectors checkingTypes are: NSTextCheckingTypeDate, NSTextCheckingTypeAddress,
	 *             NSTextCheckingTypeLink, NSTextCheckingTypePhoneNumber, and NSTextCheckingTypeTransitInformation.
	 **/
	
	public static NSDataDetector dataDetectorWithTypesError(NSTextCheckingTypes checkingTypes, NSError[] error) {
		NSDataDetector mDetector = new NSDataDetector().initWithTypesError(checkingTypes, error);
		if (mDetector == null) {
			error[0] = NSError.errorWithDomainCodeUserInfo(new NSString(" "), 1, null);
			return null;
		}
		return mDetector;
	}

	/**
	 * @Signature: initWithTypes:error:
	 * @Declaration : - (id)initWithTypes:(NSTextCheckingTypes)checkingTypes error:(NSError **)error
	 * @Description : Initializes and returns a data detector instance.
	 * @param checkingTypes The checking types. The supported checking types are a subset of the types NSTextCheckingType. Those constants
	 *            can be combined using the C-bitwise OR operator.
	 * @param error An out parameter that if an error occurs during initialization contains the encountered error.
	 * @return Return Value Returns the newly initialized data detector. If an error was encountered returns nil, and error contains the
	 *         error.
	 * @Discussion Currently, the supported data detectors checkingTypes are: NSTextCheckingTypeDate, NSTextCheckingTypeAddress,
	 *             NSTextCheckingTypeLink, NSTextCheckingTypePhoneNumber, and NSTextCheckingTypeTransitInformation.
	 **/
	
	public NSDataDetector initWithTypesError(NSTextCheckingTypes checkingTypes, NSError[] error) {
		this.mCheckingTypes = checkingTypes;
		return this;
	}

	// Getting the Checking Types
	private NSTextCheckingTypes mCheckingTypes;

	/**
	 * @Declaration :  NSTextCheckingTypes checkingTypes
	 * @Description : Returns the checking types for this data detector. (read-only)
	 * @Discussion The supported subset of checking types are specified in NSTextCheckingType. Those constants can be combined using the
	 *             C-bitwise OR operator. Currently, the supported data detectors checkingTypes are: NSTextCheckingTypeDate,
	 *             NSTextCheckingTypeAddress, NSTextCheckingTypeLink, NSTextCheckingTypePhoneNumber, and
	 *             NSTextCheckingTypeTransitInformation.
	 **/
	
	public NSTextCheckingTypes checkingTypes() {
		return this.mCheckingTypes;
	}
}