
package com.myappconverter.java.foundations;


import java.io.Serializable;


public class NSError extends NSObject implements Serializable{

	private static final long serialVersionUID = 180123L;

	private int code;
    transient private NSString domain;
    transient private NSDictionary userInfo;

	
	public static final NSString NSRecoveryAttempterErrorKey = new NSString("NSRecoveryAttempterErrorKey");
	
	public static final NSString NSLocalizedDescriptionKey = new NSString("NSLocalizedDescriptionKey");
	
	public static final NSString NSLocalizedFailureReasonErrorKey = new NSString(" NSLocalizedFailureReasonErrorKey");
	
	public static final NSString NSLocalizedRecoverySuggestionErrorKey = new NSString(" NSLocalizedRecoverySuggestionErrorKey");
	
	public static final NSString NSLocalizedRecoveryOptionsErrorKey = new NSString(" NSLocalizedRecoveryOptionsErrorKey");
	
	public static final NSString NSHelpAnchorErrorKey = new NSString(" NSHelpAnchorErrorKey");
	
	public static final NSString NSUnderlyingErrorKey = new NSString("NSUnderlyingErrorKey");
	
	public static final NSString NSErrorFailingURLStringKey = new NSString("NSErrorFailingURLStringKey");
	
	public static final NSString NSFilePathErrorKey = new NSString("NSFilePathErrorKey");
	
	public static final NSString NSStringEncodingErrorKey = new NSString("NSStringEncodingErrorKey");
	
	public static final NSString NSURLErrorKey = new NSString("NSURLErrorKey");
	
	public static final NSString NSURLErrorFailingURLErrorKey = new NSString("NSURLErrorFailingURLErrorKey");
	
	public static final NSString NSURLErrorFailingURLStringErrorKey = new NSString("NSURLErrorFailingURLStringErrorKey");
	
	public static final NSString NSURLErrorFailingURLPeerTrustErrorKey = new NSString("NSURLErrorFailingURLPeerTrustErrorKey");

	// Creating Error Objects

	/**
	 * @Signature: errorWithDomain:code:userInfo:
	 * @Declaration : + (id)errorWithDomain:(NSString *)domain code:(NSInteger)code userInfo:(NSDictionary *)dict
	 * @Description : Creates and initializes an NSError object for a given domain and code with a given userInfo dictionary.
	 * @param domain The error domain—this can be one of the predefined NSError domains, or an arbitrary string describing a custom domain.
	 *            domain must not be nil. See “Error Domains for a list of predefined domains.
	 * @param code The error code for the error.
	 * @param dict The userInfo dictionary for the error. userInfo may be nil.
	 * @return Return Value An NSError object for domain with the specified error code and the dictionary of arbitrary data userInfo.
	 **/
	
	public static NSError errorWithDomainCodeUserInfo(NSString Domain, int CODE, NSDictionary dict) {
		NSError error = new NSError();
		error.code = CODE;
		error.domain = Domain;
		error.userInfo = dict;
		return error;
	}

	/**
	 * @Signature: initWithDomain:code:userInfo:
	 * @Declaration : - (id)initWithDomain:(NSString *)domain code:(NSInteger)code userInfo:(NSDictionary *)dict
	 * @Description : Returns an NSError object initialized for a given domain and code with a given userInfo dictionary.
	 * @param domain The error domain—this can be one of the predefined NSError domains, or an arbitrary string describing a custom domain.
	 *            domain must not be nil. See “Error Domains for a list of predefined domains.
	 * @param code The error code for the error.
	 * @param dict The userInfo dictionary for the error. userInfo may be nil.
	 * @return Return Value An NSError object initialized for domain with the specified error code and the dictionary of arbitrary data
	 *         userInfo.
	 * @Discussion This is the designated initializer for NSError.
	 **/
	
	public NSError initWithDomainCodeUserInfo(NSString Domain, int CODE, NSDictionary dict) {
		NSError error = new NSError();
		error.code = CODE;
		error.domain = Domain;
		error.userInfo = dict;
		return error;
	}

	// Getting Error Properties

	/**
	 * @Signature: domain
	 * @Declaration : - (NSString *)domain
	 * @Description : Returns the receiver’s error domain.
	 * @return Return Value A string containing the receiver’s error domain.
	 **/
	
	public NSString domain() {
		return this.domain;
	}

	/**
	 * @Signature: code
	 * @Declaration : - (NSInteger)code
	 * @Description : Returns the receiver’s error code.
	 * @return Return Value The receiver’s error code.
	 * @Discussion Note that errors are domain specific.
	 **/
	
	public int code() {
		return code;
	}

	/**
	 * @Signature: userInfo
	 * @Declaration : - (NSDictionary *)userInfo
	 * @Description : Returns the receiver's user info dictionary.
	 * @return Return Value The receiver's user info dictionary, or nil if the user info dictionary has not been set.
	 **/
	
	public NSDictionary userInfo() {
		return userInfo;
	}

	// Getting a Localized Error Description

	/**
	 * @Signature: localizedDescription
	 * @Declaration : - (NSString *)localizedDescription
	 * @Description : Returns a string containing the localized description of the error.
	 * @return Return Value A string containing the localized description of the error. By default this method returns the object in the
	 *         user info dictionary for the key NSLocalizedDescriptionKey. If the user info dictionary doesn’t contain a value for
	 *         NSLocalizedDescriptionKey, a default string is constructed from the domain and code.
	 * @Discussion This method can be overridden by subclasses to present customized error strings.
	 **/
	
	public NSString localizedDescription() {
		NSString nsString = new NSString();
		nsString.setWrappedString("Error");
		return nsString;
	}

	public NSString getLocalizedDescription() {
		return localizedDescription();
	}



	/**
	 * @Signature: localizedFailureReason
	 * @Declaration : - (NSString *)localizedFailureReason
	 * @Description : Returns a string containing the localized explanation of the reason for the error.
	 * @return Return Value A string containing the localized explanation of the reason for the error. By default this method returns the
	 *         object in the user info dictionary for the key NSLocalizedFailureReasonErrorKey.
	 * @Discussion This method can be overridden by subclasses to present customized error strings.
	 **/
	
	public NSString localizedFailureReason() {
		NSNotification nsNotification = new NSNotification();
		if (nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedFailureReasonErrorKey) != null) {
			return (NSString) nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedFailureReasonErrorKey);
		} else {
			return null;
		}
	}

	public NSString getLocalizedFailureReason() {
		return localizedFailureReason();
	}

	/**
	 * @Signature: localizedRecoverySuggestion
	 * @Declaration : - (NSString *)localizedRecoverySuggestion
	 * @Description : Returns a string containing the localized recovery suggestion for the error.
	 * @return Return Value A string containing the localized recovery suggestion for the error. By default this method returns the object
	 *         in the user info dictionary for the key NSLocalizedRecoverySuggestionErrorKey. If the user info dictionary doesn’t contain a
	 *         value for NSLocalizedRecoverySuggestionErrorKey, nil is returned.
	 * @Discussion The returned string is suitable for displaying as the secondary message in an alert panel. This method can be overridden
	 *             by subclasses to present customized recovery suggestion strings.
	 **/
	
	public NSString localizedRecoverySuggestion() {
		NSNotification nsNotification = new NSNotification();
		if (nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedRecoverySuggestionErrorKey) != null) {
			return (NSString) nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedRecoverySuggestionErrorKey);
		} else {
			return null;
		}
	}

	public NSString getLocalizedRecoverySuggestion() {
		return localizedRecoverySuggestion();
	}

	/**
	 * @Signature: localizedRecoveryOptions
	 * @Declaration : - (NSArray *)localizedRecoveryOptions
	 * @Description : Returns an array containing the localized titles of buttons appropriate for displaying in an alert panel.
	 * @return Return Value An array containing the localized titles of buttons appropriate for displaying in an alert panel. By default
	 *         this method returns the object in the user info dictionary for the key NSLocalizedRecoveryOptionsErrorKey. If the user info
	 *         dictionary doesn’t contain a value for NSLocalizedRecoveryOptionsErrorKey, nil is returned.
	 * @Discussion The first string is the title of the right-most and default button, the second the one to the left of that, and so on.
	 *             The recovery options should be appropriate for the recovery suggestion returned by localizedRecoverySuggestion. If the
	 *             user info dictionary doesn’t contain a value for NSLocalizedRecoveryOptionsErrorKey, only an OK button is displayed. This
	 *             method can be overridden by subclasses to present customized recovery suggestion strings.
	 **/
	
	public NSArray localizedRecoveryOptions() {
		NSArray nsArray = new NSArray();
		NSNotification nsNotification = new NSNotification();
		if (nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedRecoveryOptionsErrorKey) != null) {
			nsArray.wrappedList.add(nsNotification.getUserInfo().getWrappedDictionary().get(NSLocalizedRecoveryOptionsErrorKey));
			return nsArray;
		} else {
			return null;
		}
	}

	public NSArray getLocalizedRecoveryOptions() {
			return localizedRecoveryOptions();
	}


	// Getting the Error Recovery Attempter

	/**
	 * @Signature: recoveryAttempter
	 * @Declaration : - (id)recoveryAttempter
	 * @Description : Returns an object that conforms to the NSErrorRecoveryAttempting informal protocol.
	 * @return Return Value An object that conforms to the NSErrorRecoveryAttempting informal protocol. By default this method returns the
	 *         object for the user info dictionary for the key NSRecoveryAttempterErrorKey. If the user info dictionary doesn’t contain a
	 *         value for NSRecoveryAttempterErrorKey, nil is returned.
	 * @Discussion The recovery attempter must be an object that can correctly interpret an index into the array returned by
	 *             localizedRecoveryOptions.
	 **/
	
	public Object recoveryAttempter() {
		NSNotification nsNotification = new NSNotification();
		if (nsNotification.getUserInfo().getWrappedDictionary().get(NSRecoveryAttempterErrorKey) != null) {
			return nsNotification.getUserInfo().getWrappedDictionary().get(NSRecoveryAttempterErrorKey);
		} else {
			return null;
		}
	}

	public Object getRecoveryAttempter() {
		return recoveryAttempter();
	}

	// Getting the Error Recovery Attempter

	/**
	 * @Signature: helpAnchor
	 * @Declaration : - (NSString *)helpAnchor
	 * @Description : A string to display in response to an alert panel help anchor button being pressed.
	 * @return Return Value An NSString that the alert panel will include a help anchor button with that value.
	 * @Discussion If this method returns a non-nil value for an error being presented by alertWithError:, the alert panel will include a
	 *             help anchor button that can display this string. The best way to get a value to return for this method is to specify it
	 *             as the value of NSHelpAnchorErrorKey in the NSError object’s userInfo dictionary; or the method can be overridden.
	 **/
	
	public String helpAnchor() {
		NSNotification nsNotification = new NSNotification();
		if (nsNotification.getUserInfo().getWrappedDictionary().get(NSHelpAnchorErrorKey) != null) {
			return ((NSString) nsNotification.getUserInfo().getWrappedDictionary().get(NSHelpAnchorErrorKey)).getWrappedString();
		} else {
			return null;
		}
	}

	public String getHelpAnchor() {
		return helpAnchor();
	}

	// Getters and Setters

	public void setDomain(NSString Dom) {
		domain = Dom;
	}

	public NSString getDomain() {
		return domain;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public NSDictionary getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(NSDictionary userInfo) {
		this.userInfo = userInfo;
	}

}