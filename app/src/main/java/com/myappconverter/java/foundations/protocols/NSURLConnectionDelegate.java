package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSURLConnection;
import com.myappconverter.java.foundations.NSURLProtectionSpace;


public interface NSURLConnectionDelegate extends NSURLConnectionDataDelegate {

	/**
	 * @Signature: connection:willSendRequestForAuthenticationChallenge:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection
	 *              willSendRequestForAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge
	 * @Description : Tells the delegate that the connection will send a request for an authentication challenge.
	 * @param connection The connection sending the message.
	 * @param challenge The authentication challenge for which a request is being sent.
	 * @Discussion This method allows the delegate to make an informed decision about connection authentication at once. If the delegate
	 *             implements this method, it has no need to implement connection:canAuthenticateAgainstProtectionSpace: or
	 *             connection:didReceiveAuthenticationChallenge:. In fact, those other methods are not invoked (except on older operating
	 *             systems, where applicable). In this method,you must invoke one of the challenge-responder methods
	 *             (NSURLAuthenticationChallengeSender protocol): useCredential:forAuthenticationChallenge:
	 *             continueWithoutCredentialForAuthenticationChallenge: cancelAuthenticationChallenge:
	 *             performDefaultHandlingForAuthenticationChallenge: rejectProtectionSpaceAndContinueWithChallenge: Important: Your delegate
	 *             method is called on the thread where the connection is scheduled. Always call the methods above on that same thread. You
	 *             might also want to analyze challenge for the authentication scheme and the proposed credential before calling a
	 *             NSURLAuthenticationChallengeSender method. You should never assume that a proposed credential is present. You can either
	 *             create your own credential and respond with that, or you can send the proposed credential back. (Because this object is
	 *             immutable, if you want to change it you must copy it and then modify the copy.)
	 **/

	public void connectionWillSendRequestForAuthenticationChallenge(NSURLConnection connection, NSURLAuthenticationChallengeSender challenge);

	/**
	 * @Signature: connection:canAuthenticateAgainstProtectionSpace:
	 * @Declaration : - (BOOL)connection:(NSURLConnection *)connection canAuthenticateAgainstProtectionSpace:(NSURLProtectionSpace
	 *              *)protectionSpace
	 * @Description : Sent to determine whether the delegate is able to respond to a protection space’s form of authentication.
	 * @param connection The connection sending the message.
	 * @param protectionSpace The protection space that generates an authentication challenge.
	 * @return Return Value YES if the delegate if able to respond to a protection space’s form of authentication, otherwise NO.
	 * @Discussion This method is called before connection:didReceiveAuthenticationChallenge:, allowing the delegate to inspect a protection
	 *             space before attempting to authenticate against it. By returning YES, the delegate indicates that it can handle the form
	 *             of authentication, which it does in the subsequent call to connection:didReceiveAuthenticationChallenge:. If the delegate
	 *             returns NO, the system attempts to use the user’s keychain to authenticate. If your delegate does not implement this
	 *             method and the protection space uses client certificate authentication or server trust authentication, the system behaves
	 *             as if you returned NO. The system behaves as if you returned YES for all other authentication methods. Note: This method
	 *             is not called if the delegate implements the connection:willSendRequestForAuthenticationChallenge: method.
	 **/

	public boolean connectionCanAuthenticateAgainstProtectionSpace(NSURLConnection connection, NSURLProtectionSpace protectionSpace);;

	/**
	 * @Signature: connection:didCancelAuthenticationChallenge:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didCancelAuthenticationChallenge:(NSURLAuthenticationChallenge
	 *              *)challenge
	 * @Description : Sent when a connection cancels an authentication challenge.
	 * @param connection The connection sending the message.
	 * @param challenge The challenge that was canceled.
	 **/

	public void connectionDidCancelAuthenticationChallenge(NSURLConnection connection, NSURLAuthenticationChallengeSender challenge);;

	/**
	 * @Signature: connection:didReceiveAuthenticationChallenge:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge
	 *              *)challenge
	 * @Description : Sent when a connection must authenticate a challenge in order to download its request.
	 * @param connection The connection sending the message.
	 * @param challenge The challenge that connection must authenticate in order to download its request.
	 * @Discussion This method gives the delegate the opportunity to determine the course of action taken for the challenge: provide
	 *             credentials, continue without providing credentials, or cancel the authentication challenge and the download. Note: This
	 *             method is not called if the delegate implements the connection:willSendRequestForAuthenticationChallenge: method. The
	 *             delegate can determine the number of previous authentication challenges by sending the message previousFailureCount to
	 *             challenge. If the previous failure count is 0 and the value returned by proposedCredential is nil, the delegate can
	 *             create a new NSURLCredential object, providing information specific to the type of credential, and send a
	 *             useCredential:forAuthenticationChallenge: message to [challenge sender], passing the credential and challenge as
	 *             parameters. If proposedCredential is not nil, the value is a credential from the URL or the shared credential storage
	 *             that can be provided to the user as feedback. The delegate may decide to abandon further attempts at authentication at
	 *             any time by sending [challenge sender] a continueWithoutCredentialForAuthenticationChallenge: or a
	 *             cancelAuthenticationChallenge: message. The specific action is implementation dependent. If the delegate implements this
	 *             method, the download will suspend until [challenge sender] is sent one of the following messages:
	 *             useCredential:forAuthenticationChallenge:, continueWithoutCredentialForAuthenticationChallenge: or
	 *             cancelAuthenticationChallenge:. If the delegate does not implement this method the default implementation is used. If a
	 *             valid credential for the request is provided as part of the URL, or is available from the NSURLCredentialStorage the
	 *             [challenge sender] is sent a useCredential:forAuthenticationChallenge: with the credential. If the challenge has no
	 *             credential or the credentials fail to authorize access, then continueWithoutCredentialForAuthenticationChallenge: is sent
	 *             to [challenge sender] instead.
	 **/

	public void connectionDidReceiveAuthenticationChallenge(NSURLConnection connection, NSURLAuthenticationChallengeSender challenge);;

	/**
	 * @Signature: connectionShouldUseCredentialStorage:
	 * @Declaration : - (BOOL)connectionShouldUseCredentialStorage:(NSURLConnection *)connection
	 * @Description : Sent to determine whether the URL loader should use the credential storage for authenticating the connection.
	 * @param connection The connection sending the message.
	 * @Discussion This method is called before any attempt to authenticate is made. If you return NO, the connection does not consult the
	 *             credential storage automatically, and does not store credentials. However, in your
	 *             connection:didReceiveAuthenticationChallenge: method, you can consult the credential storage yourself and store
	 *             credentials yourself, as needed. Not implementing this method is the same as returning YES. Important: Prior to iOS 7 and
	 *             OS X v10.9, the connectionShouldUseCredentialStorage: method is never called on delegates that implement the
	 *             connection:willSendRequestForAuthenticationChallenge: method. In later operating systems, if the delegate implements the
	 *             connection:willSendRequestForAuthenticationChallenge: method, the connectionShouldUseCredentialStorage: method is called
	 *             only if the app’s deployment target is at least iOS 7 or OS X v10.9.
	 **/

	public boolean connectionShouldUseCredentialStorage(NSURLConnection connection);;

	/**
	 * @Signature: connection:didFailWithError:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
	 * @Description : Sent when a connection fails to load its request successfully.
	 * @param connection The connection sending the message.
	 * @param error An error object containing details of why the connection failed to load the request successfully.
	 * @Discussion Once the delegate receives this message, it will receive no further messages for connection.
	 **/

	public void connectionDidFailWithError(NSURLConnection connection, NSError error);

}
