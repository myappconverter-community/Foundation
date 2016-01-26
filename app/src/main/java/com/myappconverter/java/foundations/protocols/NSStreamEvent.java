package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSURLCredential;

public interface NSStreamEvent {

	/**
	 * Cancels a given authentication challenge. (required) - (void)cancelAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Param challenge The authentication challenge to cancel.
	 */

	public void cancelAuthenticationChallenge(NSURLAuthenticationChallengeSender challenge);

	/**
	 * Attempt to continue downloading a request without providing a credential for a given challenge. (required) -
	 * (void)continueWithoutCredentialForAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Param challenge A challenge without authentication credentials.
	 * @Discussion This method has no effect if it is called with an authentication challenge that has already been handled.
	 */

	public void continueWithoutCredentialForAuthenticationChallenge(NSURLAuthenticationChallengeSender challenge);

	/**
	 * Rejects the currently supplied protection space. - (void)rejectProtectionSpaceAndContinueWithChallenge:(NSURLAuthenticationChallenge
	 * *)challenge;
	 * 
	 * @Param challenge The challenge that should be rejected.
	 */
	public void rejectProtectionSpaceAndContinueWithChallenge(NSURLAuthenticationChallengeSender challenge);

	/**
	 * Attempt to use a given credential for a given authentication challenge. (required) - (void)useCredential:(NSURLCredential
	 * *)credential forAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * 
	 * @Param credential The credential to use for authentication. challenge The challenge for which to use credential.
	 * @Discussion This method has no effect if it is called with an authentication challenge that has already been handled.
	 */

	public void useCredentialforAuthenticationChallenge(NSURLCredential credential, NSURLAuthenticationChallengeSender challenge);
}
