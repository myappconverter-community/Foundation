package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSURLAuthenticationChallenge;
import com.myappconverter.java.foundations.NSURLCredential;
import com.myappconverter.java.foundations.NSURLSession;
import com.myappconverter.mapping.utils.PerformBlock;


public interface NSURLSessionDelegate extends NSObject {


	public static enum NSURLSessionAuthChallengeDisposition {
		NSURLSessionAuthChallengeUseCredential, //
		NSURLSessionAuthChallengePerformDefaultHandling, //
		NSURLSessionAuthChallengeCancelAuthenticationChallenge, //
		NSURLSessionAuthChallengeRejectProtectionSpace
	};

	// Delegate Methods

	/**
	 * @Signature: URLSession:didBecomeInvalidWithError:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session didBecomeInvalidWithError:(NSError *)error
	 * @Description : Tells the URL session that the session has been invalidated.
	 * @param session The session object that was invalidated.
	 * @param error The error that caused invalidation, or nil if the invalidation was explicit.
	 * @Discussion If you invalidate a session by calling its finishTasksAndInvalidate method, the session waits until after the final task
	 *             in the session finishes or fails before calling this delegate method. If you call the invalidateAndCancel method, the
	 *             session calls this delegate method immediately.
	 **/

	public void URLSessionDidBecomeInvalidWithError(NSURLSession session, NSError error);

	/**
	 * @Signature: URLSession:didReceiveChallenge:completionHandler:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session didReceiveChallenge:(NSURLAuthenticationChallenge *)challenge
	 *              completionHandler:(void (^)(NSURLSessionAuthChallengeDisposition disposition, NSURLCredential
	 *              *credential))completionHandler
	 * @Description : Requests credentials from the delegate in response to a session-level authentication request from the remote server.
	 * @param session The session containing the task that requested authentication.
	 * @param challenge An object that contains the request for authentication.
	 * @param completionHandler A handler that your delegate method must call. Its parameters are: disposition—One of several constants that
	 *            describes how the challenge should be handled. credential—The credential that should be used for authentication if
	 *            disposition is NSURLSessionAuthChallengeUseCredential, otherwise NULL.
	 * @Discussion This method is called in two situations: When a remote server asks for client certificates or Windows NT LAN Manager
	 *             (NTLM) authentication, to allow your app to provide appropriate credentials When a session first establishes a connection
	 *             to a remote server that uses SSL or TLS, to allow your app to verify the server’s certificate chain If you do not
	 *             implement this method, the session calls its delegate’s URLSession:task:didReceiveChallenge:completionHandler: method
	 *             instead. Note: This method handles only the NSURLAuthenticationMethodNTLM, NSURLAuthenticationMethodNegotiate,
	 *             NSURLAuthenticationMethodClientCertificate, and NSURLAuthenticationMethodServerTrust authentication types. For all other
	 *             authentication schemes, the session calls only the URLSession:task:didReceiveChallenge:completionHandler: method.
	 **/

	public void URLSessionDidReceiveChallengeCompletionHandler(NSURLSession session, NSURLAuthenticationChallenge challenge,
															   PerformBlock.VoidBlockNSURLSessionAuthChallengeDispositionNSURLCredential completionBlock);

	/**
	 * @Signature: URLSessionDidFinishEventsForBackgroundURLSession:
	 * @Declaration : - (void)URLSessionDidFinishEventsForBackgroundURLSession:(NSURLSession *)session;
	 * @Description : Tells the delegate that all messages enqueued for a session have been delivered.
	 * @param session The session that no longer has any outstanding requests.
	 * @Discussion In iOS, when a background transfer completes or requires credentials, if your app is no longer running, your app is
	 *             automatically relaunched in the background, and the app’s UIApplicationDelegate is sent an
	 *             application:handleEventsForBackgroundURLSession:completionHandler: message. This call contains the identifier of the
	 *             session that caused your app to be launched. Your app should then store that completion handler before creating a
	 *             background configuration object with the same identifier, and creating a session with that configuration. The newly
	 *             created session is automatically reassociated with ongoing background activity. When your app later receives a
	 *             URLSessionDidFinishEventsForBackgroundURLSession: message, this indicates that all messages previously enqueued for this
	 *             session have been delivered, and that it is now safe to invoke the previously stored completion handler or to begin any
	 *             internal updates that may result in invoking the completion handler. Important: Because the provided completion handler
	 *             is part of UIKit, you must call it on your main thread. For example: [[NSOperationQueue mainQueue]
	 *             addOperationWithBlock:^{ storedHandler(); }];
	 **/

	public void URLSessionDidFinishEventsForBackgroundURLSession(NSURLSession session);

}
