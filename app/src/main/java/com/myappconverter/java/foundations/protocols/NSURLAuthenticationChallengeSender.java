package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSURLAuthenticationChallenge;
import com.myappconverter.java.foundations.NSURLCredential;


public abstract class NSURLAuthenticationChallengeSender {

	/**
	 * - (void)cancelAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Description : Cancels a given authentication challenge. (required)
	 * @param challenge The authentication challenge to cancel.
	 */

	public abstract void cancelAuthenticationChallenge(NSURLAuthenticationChallenge challenge);

	/**
	 * Attempt to continue downloading a request without providing a credential for a given challenge. (required) -
	 * (void)continueWithoutCredentialForAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Param challenge A challenge without authentication credentials.
	 * @Discussion This method has no effect if it is called with an authentication challenge that has already been handled.
	 */

	public abstract void continueWithoutCredentialForAuthenticationChallenge(NSURLAuthenticationChallenge challenge);

	/**
	 * Rejects the currently supplied protection space. - (void)rejectProtectionSpaceAndContinueWithChallenge:(NSURLAuthenticationChallenge
	 * *)challenge;
	 * 
	 * @Param challenge The challenge that should be rejected.
	 */

	public abstract void rejectProtectionSpaceAndContinueWithChallenge(NSURLAuthenticationChallenge challenge);

	/**
	 * Attempt to use a given credential for a given authentication challenge. (required) - (void)useCredential:(NSURLCredential
	 * *)credential forAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Param credential The credential to use for authentication. challenge The challenge for which to use credential.
	 * @Discussion This method has no effect if it is called with an authentication challenge that has already been handled.
	 */

	public abstract void useCredentialForAuthenticationChallenge(NSURLCredential credential, NSURLAuthenticationChallenge challenge);

	/**
	 * @Signature: performDefaultHandlingForAuthenticationChallenge:
	 * @Declaration : - (void)performDefaultHandlingForAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge;
	 * @Description : Causes the system-provided default behavior to be used.
	 * @param challenge The challenge for which the default behavior should be used.
	 **/

	public abstract void performDefaultHandlingForAuthenticationChallenge(NSURLAuthenticationChallenge challenge);

}
