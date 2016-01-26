
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.protocols.NSURLAuthenticationChallengeSender;


public class NSURLAuthenticationChallenge extends NSObject implements NSSecureCoding {

	NSURLAuthenticationChallenge challenge = null;
	NSURLProtectionSpace space = null;
	NSURLCredential credential = null;
	int count;
	NSURLResponse response = null;
	NSError error = null;
	NSURLAuthenticationChallengeSender sender = null;

	// Creating an Authentication Challenge Instance

	public NSURLAuthenticationChallenge() {
		super();
	}

	/**
	 * @Signature: initWithAuthenticationChallenge:sender:
	 * @Declaration : - (id)initWithAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge sender:(id <
	 *              NSURLAuthenticationChallengeSender >)sender
	 * @Description : Returns an initialized NSURLAuthenticationChallenge object copying the properties from challenge, and setting the
	 *              authentication sender to sender.
	 * @param challenge The challenge that you want to copy.
	 * @param sender The sender that you want to use for the new object. Typically, the sender is the instance of your custom NSURLProtocol
	 *            subclass that called this method.
	 * @Discussion Most apps do not create authentication challenges themselves. However, you might need to create authentication challenge
	 *             objects when adding support for custom networking protocols, as part of your custom NSURLProtocol subclasses. When
	 *             subclassing an existing NSURLProtocol subclass, this method lets you modify challenges issued by the existing class so
	 *             that your subclass receives any responses to those challenges.
	 **/

	public Object initWithAuthenticationChallengeSender(NSURLAuthenticationChallenge challenge, NSURLAuthenticationChallengeSender sender) {
		this.challenge = challenge;
		this.sender = sender;
		return this;
	}

	/**
	 * @Signature: initWithProtectionSpace:proposedCredential:previousFailureCount:failureResponse:error:sender:
	 * @Declaration : - (id)initWithProtectionSpace:(NSURLProtectionSpace *)space proposedCredential:(NSURLCredential *)credential
	 *              previousFailureCount:(NSInteger)count failureResponse:(NSURLResponse *)response error:(NSError *)error sender:(id <
	 *              NSURLAuthenticationChallengeSender >)sender
	 * @Description : Returns an initialized NSURLAuthenticationChallenge object for the specified protection space, credential, failure
	 *              count, server response, error, and sender.
	 * @param space The protection space for the URL challenge. This provides additional information about the authentication request, such
	 *            as the host, port, authentication realm, and so on.
	 * @param credential The proposed credential, or nil.
	 * @param count The total number of previous failures for this request, including failures for other protection spaces.
	 * @param response An NSURLResponse object containing the server response that caused you to generate an authentication challenge, or
	 *            nil if no response object is applicable to the challenge.
	 * @param error An NSError object describing the authentication failure, or nil if it is not applicable to the challenge.
	 * @param sender The object that initiated the authentication challenge (typically, the object that called this method).
	 * @Discussion Most apps do not create authentication challenges themselves. However, you might need to create authentication challenge
	 *             objects when adding support for custom networking protocols, as part of your custom NSURLProtocol subclasses.
	 **/

	public Object initWithProtectionSpaceProposedCredentialPreviousFailureCountFailureResponseErrorSender(NSURLProtectionSpace space,
																										  NSURLCredential credential, int count, NSURLResponse response, NSError error, NSURLAuthenticationChallengeSender sender) {
		this.space = space;
		this.credential = credential;
		this.count = count;
		this.response = response;
		this.error = error;
		this.sender = sender;
		return this;
	}

	// Getting Authentication Challenge Properties

	/**
	 * - (NSError *)error
	 *
	 * @Description : Returns the NSError object representing the last authentication failure.
	 * @Discussion This method returns nil if the protocol doesn’t use errors to indicate an authentication failure.
	 */

	public NSError error() {
		return this.error;

	}

	/**
	 * - (NSURLResponse *)failureResponse
	 *
	 * @Description : Returns the NSURLResponse object representing the last authentication failure.
	 * @Discussion This method returns nil if the protocol doesn’t use responses to indicate an authentication failure.
	 */

	public NSURLResponse failureResponse() {
		return this.response;

	}

	/**
	 * - (NSInteger)previousFailureCount
	 *
	 * @Description : Returns the receiver’s count of failed authentication attempts.
	 * @Discussion The previous failure count includes failures from all protection spaces, not just the current one.
	 */

	public int previousFailureCount() {
		return this.count;
	}

	/**
	 * - (NSURLCredential *)proposedCredential
	 *
	 * @Description : Returns the proposed credential for this challenge.
	 * @Discussion This method returns nil if there is no default credential for this challenge. If you have previously attempted to
	 *             authenticate and failed, this method returns the most recent failed credential. If the proposed credential is not nil and
	 *             returns YES when you call its hasPassword method, then the credential is ready to use as-is. If the proposed credential’s
	 *             hasPassword method returns NO, then the credential provides a default user name, and the client must prompt the user for
	 *             a corresponding password.
	 */

	public NSURLCredential proposedCredential() {
		return this.credential;

	}

	/**
	 * - (NSURLProtectionSpace *)protectionSpace
	 *
	 * @Description : Returns the receiver’s protection space.
	 * @Discussion A protection space object provides additional information about the authentication request, such as the host, port,
	 *             authentication realm, and so on. The protection space also tells you whether the authentication challenge is asking you
	 *             to provide the user’s credentials or to verify the TLS credentials provided by the server.
	 */

	public NSURLProtectionSpace protectionSpace() {
		return this.space;

	}

	/**
	 * @Signature: sender
	 * @Declaration : - (id < NSURLAuthenticationChallengeSender >)sender
	 * @Description : Returns the receiver’s sender.
	 * @Discussion The sender is typically an instance of an NSURLProtocol subclass that initially received the authentication challenge. If
	 *             you are using the NSURLSession API, this value is purely informational, because you must respond to authentication
	 *             challenges by passing constants to the provided completion handler blocks. However, if you are using the NSURLConnection
	 *             or NSURLDownload API, in your authentication handler delegate method, you respond to authentication challenges by calling
	 *             methods defined in the NSURLAuthenticationChallengeSender protocol on this sender object after you finish processing the
	 *             authentication challenge.
	 **/

	public Object sender() {
		return this.sender;

	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}