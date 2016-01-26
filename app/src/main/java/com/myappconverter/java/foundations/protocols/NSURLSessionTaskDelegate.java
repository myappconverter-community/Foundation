package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSHTTPURLResponse;
import com.myappconverter.java.foundations.NSURLAuthenticationChallenge;
import com.myappconverter.java.foundations.NSURLRequest;
import com.myappconverter.java.foundations.NSURLSession;
import com.myappconverter.java.foundations.NSURLSessionTask;
import com.myappconverter.mapping.utils.PerformBlock;


public abstract class NSURLSessionTaskDelegate {

	// 1
	/**
	 * @Signature: URLSession:task:didCompleteWithError:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task didCompleteWithError:(NSError *)error
	 * @Description : Tells the delegate that the task finished transferring data.
	 * @Discussion Server errors are not reported through the error parameter. The only errors your delegate receives through the error
	 *             parameter are client-side errors, such as being unable to resolve the hostname or connect to the host.
	 **/

	public abstract void URLSessionTaskDidCompleteWithError(NSURLSession session,
			NSURLSessionTask task, NSError error);

	// 2
	/**
	 * @Signature: URLSession:task:didReceiveChallenge:completionHandler:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task
	 *              didReceiveChallenge:(NSURLAuthenticationChallenge *)challenge completionHandler:(void
	 *              (^)(NSURLSessionAuthChallengeDisposition disposition, NSURLCredential *credential))completionHandler
	 * @Description : Requests credentials from the delegate in response to an authentication request from the remote server.
	 * @Discussion This method handles task-level authentication challenges. The NSURLSessionDelegate protocol also provides a session-level
	 *             authentication delegate method. The method called depends on the type of authentication challenge: For session-level
	 *             challenges—NSURLAuthenticationMethodNTLM, NSURLAuthenticationMethodNegotiate, NSURLAuthenticationMethodClientCertificate,
	 *             or NSURLAuthenticationMethodServerTrust—the NSURLSession object calls the session delegate’s
	 *             URLSession:didReceiveChallenge:completionHandler: method. If your app does not provide a session delegate method, the
	 *             NSURLSession object calls the task delegate’s URLSession:task:didReceiveChallenge:completionHandler: method to handle the
	 *             challenge. For non-session-level challenges (all others), the NSURLSession object calls the session delegate’s
	 *             URLSession:task:didReceiveChallenge:completionHandler: method to handle the challenge. If your app provides a session
	 *             delegate and you need to handle authentication, then you must either handle the authentication at the task level or
	 *             provide a task-level handler that calls the per-session handler explicitly. The session delegate’s
	 *             URLSession:didReceiveChallenge:completionHandler: method is not called for non-session-level challenges.
	 **/

	public abstract void URLSessionTaskDidReceiveChallengeCompletionHandler(NSURLSession session,
			NSURLSessionTask task, NSURLAuthenticationChallenge challenge,
			PerformBlock.VoidBlockVoidNSURLSessionAuthChallengeDispositionNSURLCredential completionHandler);

	// 3
	/**
	 * @Signature: URLSession:task:didSendBodyData:totalBytesSent:totalBytesExpectedToSend:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task didSendBodyData:(int64_t)bytesSent
	 *              totalBytesSent:(int64_t)totalBytesSent totalBytesExpectedToSend:(int64_t)totalBytesExpectedToSend
	 * @Description : Periodically informs the delegate of the progress of sending body content to the server.
	 **/

	public abstract void URLSessionTaskDidSendBodyDataTotalBytesSentTotalBytesExpectedToSend(
			NSURLSession session, NSURLSessionTask task, long bytesSent, long totalBytesSent,
			long totalBytesExpectedToSend);

	// 4
	/**
	 * @Signature: URLSession:task:needNewBodyStream:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task needNewBodyStream:(void (^)(NSInputStream
	 *              *bodyStream))completionHandler
	 * @Description : Tells the delegate when a task requires a new request body stream to send to the remote server.
	 * @Discussion This delegate method is called under two circumstances: To provide the initial request body stream if the task was
	 *             created with uploadTaskWithStreamedRequest: To provide a replacement request body stream if the task needs to resend a
	 *             request that has a body stream because of an authentication challenge or other recoverable server error. Note: You do not
	 *             need to implement this if your code provides the request body using a file URL or an NSData object.
	 **/

	public abstract void URLSessionTaskNeedNewBodyStream(NSURLSession session,
			NSURLSessionTask task, PerformBlock.VoidBlockNSInputStream completionHandler);

	// 5
	/**
	 * @Signature: URLSession:task:willPerformHTTPRedirection:newRequest:completionHandler:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session task:(NSURLSessionTask *)task willPerformHTTPRedirection:(NSHTTPURLResponse
	 *              *)response newRequest:(NSURLRequest *)request completionHandler:(void (^)(NSURLRequest *))completionHandler
	 * @Description : Tells the delegate that the remote server requested an HTTP redirect.
	 * @Discussion This method is called only for tasks in default and ephemeral sessions. Tasks in background sessions automatically follow
	 *             redirects.
	 **/

	public abstract void URLSessionTaskWillPerformHTTPRedirectionNewRequestCompletionHandler(
			NSURLSession session, NSURLSessionTask task, NSHTTPURLResponse response,
			NSURLRequest request, PerformBlock.VoidBlockNSURLRequest completionHandler);

}
