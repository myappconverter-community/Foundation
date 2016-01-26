package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSCachedURLResponse;
import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSInputStream;
import com.myappconverter.java.foundations.NSURLConnection;
import com.myappconverter.java.foundations.NSURLRequest;
import com.myappconverter.java.foundations.NSURLResponse;


public interface NSURLConnectionDataDelegate {

	/**
	 * Sent when the connection has received sufficient data to construct the URL response for its request. -
	 * (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
	 * 
	 * @Param connection The connection sending the message. response The URL response for the connection's request. This object is
	 *        immutable and will not be modified by the URL loading system once it is presented to the delegate.
	 * @Discussion In rare cases, for example in the case of an HTTP load where the content type of the load data is
	 *             multipart/x-mixed-replace, the delegate will receive more than one connection:didReceiveResponse: message. When this
	 *             happens, discard (or process) all data previously delivered by connection:didReceiveData:, and prepare to handle the next
	 *             part (which could potentially have a different MIME type). The only case where this message is not sent to the delegate
	 *             is when the protocol implementation encounters an error before a response could be created.
	 */
	/**
	 * @Signature: connection:didReceiveResponse:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
	 * @Description : Sent when the connection has received sufficient data to construct the URL response for its request.
	 * @param connection The connection sending the message.
	 * @param response The URL response for the connection's request. This object is immutable and will not be modified by the URL loading
	 *            system once it is presented to the delegate.
	 * @Discussion In rare cases, for example in the case of an HTTP load where the content type of the load data is
	 *             multipart/x-mixed-replace, the delegate will receive more than one connection:didReceiveResponse: message. When this
	 *             happens, discard (or process) all data previously delivered by connection:didReceiveData:, and prepare to handle the next
	 *             part (which could potentially have a different MIME type). The only case where this message is not sent to the delegate
	 *             is when the protocol implementation encounters an error before a response could be created.
	 **/

	public void connectionDidReceiveResponse(NSURLConnection connection, NSURLResponse response);;

	/**
	 * Sent as the body (message data) of a request is transmitted (such as in an http POST request). - (void)connection:(connection
	 * *)connection didSendBodyData:(NSInteger)bytesWritten totalBytesWritten:(NSInteger)totalBytesWritten
	 * totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite
	 * 
	 * @Param connection The connection sending the message. bytesWritten The number of bytes written in the latest write. totalBytesWritten
	 *        The total number of bytes written for this connection. totalBytesExpectedToWrite The number of bytes the connection expects to
	 *        write.
	 * @Discussion This method provides an estimate of the progress of a URL upload. The value of totalBytesExpectedToWrite may change
	 *             during the upload if the request needs to be retransmitted due to a lost connection or an authentication challenge from
	 *             the server.
	 */

	/**
	 * @Signature: connection:didSendBodyData:totalBytesWritten:totalBytesExpectedToWrite:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didSendBodyData:(NSInteger)bytesWritten
	 *              totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite
	 * @Description : Sent as the body (message data) of a request is transmitted (such as in an http POST request).
	 * @param connection The connection sending the message.
	 * @param bytesWritten The number of bytes written in the latest write.
	 * @param totalBytesWritten The total number of bytes written for this connection.
	 * @param totalBytesExpectedToWrite The number of bytes the connection expects to write.
	 * @Discussion This method provides an estimate of the progress of a URL upload. The value of totalBytesExpectedToWrite may change
	 *             during the upload if the request needs to be retransmitted due to a lost connection or an authentication challenge from
	 *             the server.
	 **/

	public void connectionDidSendBodyDataTotalBytesWrittenTotalBytesExpectedToWrite(NSURLConnection connection, int bytesWritten,
																					int totalBytesWritten, int totalBytesExpectedToWrite);

	/**
	 * Sent as a connection loads data incrementally. - (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
	 * 
	 * @throws JSONException
	 * 
	 * @Param connection The connection sending the message. data The newly available data. The delegate should concatenate the contents of
	 *        each data object delivered to build up the complete data for a URL load.
	 * @Discussion This method provides the only way for an asynchronous delegate to retrieve the loaded data. It is the responsibility of
	 *             the delegate to retain or copy this data as it is delivered.
	 */

	/**
	 * @Signature: connection:didReceiveData:
	 * @Declaration : - (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
	 * @Description : Sent as a connection loads data incrementally.
	 * @param connection The connection sending the message.
	 * @param data The newly available data. The delegate should concatenate the contents of each data object delivered to build up the
	 *            complete data for a URL load.
	 * @Discussion This method provides the only way for an asynchronous delegate to retrieve the loaded data. It is the responsibility of
	 *             the delegate to retain or copy this data as it is delivered.
	 **/

	public void connectionDidReceiveData(NSURLConnection connection, final NSData data);;

	/**
	 * Sent before the connection stores a cached response in the cache, to give the delegate an opportunity to alter it. -
	 * (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse *)cachedResponse
	 * 
	 * @param connection The connection sending the message. cachedResponse The proposed cached response to store in the cache. Return Value
	 *            The actual cached response to store in the cache. The delegate may return cachedResponse unmodified, return a modified
	 *            cached response, or return nil if no cached response should be stored for the connection.
	 * @Discussion This method is called only if the NSURLProtocol handling the request decides to cache the response. As a rule, responses
	 *             are cached only when all of the following are true: The request is for an HTTP or HTTPS URL (or your own custom
	 *             networking protocol that supports caching). The request was successful (with a status code in the 200–299 range). The
	 *             provided response came from the server, rather than out of the cache. The NSURLRequest object's cache policy allows
	 *             caching. The cache-related headers in the server’s response (if present) allow caching. The response size is small enough
	 *             to reasonably fit within the cache. (For example, if you provide a disk cache, the response must be no larger than about
	 *             5% of the disk cache size.)
	 */

	/**
	 * @Signature: connection:willCacheResponse:
	 * @Declaration : - (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse
	 *              *)cachedResponse
	 * @Description : Sent before the connection stores a cached response in the cache, to give the delegate an opportunity to alter it.
	 * @param connection The connection sending the message.
	 * @param cachedResponse The proposed cached response to store in the cache.
	 * @return Return Value The actual cached response to store in the cache. The delegate may return cachedResponse unmodified, return a
	 *         modified cached response, or return nil if no cached response should be stored for the connection.
	 * @Discussion This method is called only if the NSURLProtocol handling the request decides to cache the response. As a rule, responses
	 *             are cached only when all of the following are true: The request is for an HTTP or HTTPS URL (or your own custom
	 *             networking protocol that supports caching). The request was successful (with a status code in the 200–299 range). The
	 *             provided response came from the server, rather than out of the cache. The NSURLRequest object's cache policy allows
	 *             caching. The cache-related headers in the server’s response (if present) allow caching. The response size is small enough
	 *             to reasonably fit within the cache. (For example, if you provide a disk cache, the response must be no larger than about
	 *             5% of the disk cache size.)
	 **/

	public NSCachedURLResponse connectionWillCacheResponse(NSURLConnection connection, NSCachedURLResponse cachedResponse);;

	/**
	 * @Signature: connection:needNewBodyStream:
	 * @Declaration : - (NSInputStream *)connection:(NSURLConnection *)connection needNewBodyStream:(NSURLRequest *)request;
	 * @Description : Called when an NSURLConnection needs to retransmit a request that has a body stream to provide a new, unopened stream.
	 * @param connection The NSURLConnection that is requesting a new body stream.
	 * @return Return Value This delegate method should return a new, unopened stream that provides the body contents for the request. If
	 *         this delegate method returns NULL, the connection fails.
	 * @Discussion On OS X, if this method is not implemented, body stream data is spooled to disk in case retransmission is required. This
	 *             spooling may not be desirable for large data sets. By implementing this delegate method, the client opts out of automatic
	 *             spooling, and must provide a new, unopened stream for each retransmission.
	 **/

	public NSInputStream connectionNeedNewBodyStream(NSURLConnection connection, NSURLRequest request);

	/**
	 * @Signature: connectionDidFinishLoading:
	 * @Declaration : - (void)connectionDidFinishLoading:(NSURLConnection *)connection
	 * @Description : Sent when a connection has finished loading successfully.
	 * @param connection The connection sending the message.
	 * @Discussion The delegate will receive no further messages for connection.
	 **/

	public void connectionDidFinishLoading(NSURLConnection connection);;

	/**
	 * @Signature: connection:willSendRequest:redirectResponse:
	 * @Declaration : - (NSURLRequest *)connection:(NSURLConnection *)connection willSendRequest:(NSURLRequest *)request
	 *              redirectResponse:(NSURLResponse *)redirectResponse
	 * @Description : Sent when the connection determines that it must change URLs in order to continue loading a request.
	 * @param connection The connection sending the message.
	 * @param request The proposed redirected request. The delegate should inspect the redirected request to verify that it meets its needs,
	 *            and create a copy with new attributes to return to the connection if necessary.
	 * @param redirectResponse The URL response that caused the redirect. May be nil in cases where this method is called because of URL
	 *            canonicalization.
	 * @return Return Value The actual URL request to use in light of the redirection response. The delegate may return request unmodified
	 *         to allow the redirect, return a new request, or return nil to reject the redirect and continue processing the connection.
	 * @Discussion If redirectResponse is nil, the URL was canonicalized (rewritten into its standard form) by the NSURLProtocol object
	 *             handling the request. Update your user interface to show the standardized form of the URL, then return the original
	 *             request unmodified. Otherwise, to cancel the redirect, call the connection object’s cancel method, then return the
	 *             provided request object. To receive the body of the redirect response itself, return nil to cancel the redirect. The
	 *             connection continues to process, eventually sending your delegate a connectionDidFinishLoading or
	 *             connection:didFailLoadingWithError: message, as appropriate. To redirect the request to a different URL, create a new
	 *             request object and return it. Important: Your delegate method is called on the thread where the connection is scheduled.
	 *             If you call the cancel method, do so on that same thread. The delegate should be prepared to receive this message
	 *             multiple times.
	 **/

	public NSURLRequest connectionWillSendRequestRedirectResponse(NSURLConnection connection, NSURLRequest request,
																  NSURLResponse redirectResponse);

}
