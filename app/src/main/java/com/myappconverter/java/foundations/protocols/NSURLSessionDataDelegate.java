package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSCachedURLResponse;
import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSURLResponse;
import com.myappconverter.java.foundations.NSURLSession;
import com.myappconverter.java.foundations.NSURLSessionDataTask;
import com.myappconverter.java.foundations.NSURLSessionDownloadTask;
import com.myappconverter.mapping.utils.PerformBlock;


public interface NSURLSessionDataDelegate {

	// Enum
	public static enum NSURLSessionResponseDisposition {
		NSURLSessionResponseCancel, //
		NSURLSessionResponseAllow1, //
		NSURLSessionResponseBecomeDownload//
	};

	// 1
	/**
	 * @Signature: URLSession:dataTask:didBecomeDownloadTask:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask
	 *              didBecomeDownloadTask:(NSURLSessionDownloadTask *)downloadTask
	 * @Description : Tells the delegate that the data task was changed to a download task.
	 * @Discussion When the delegate’s URLSession:dataTask:didReceiveResponse:completionHandler: method decides to change the disposition
	 *             from a data request to a download, the session calls this delegate method to provide you with the new download task.
	 *             After this call, the session delegate receives no further delegate method calls related to the original data task.
	 **/

	public abstract void URLSessionDataTaskDidBecomeDownloadTask(NSURLSession session,
																 NSURLSessionDataTask dataTask, NSURLSessionDownloadTask downloadTask);

	// 2
	/**
	 * @Signature: URLSession:dataTask:didReceiveData:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveData:(NSData *)data
	 * @Description : Tells the delegate that the data task has received some of the expected data.
	 * @Discussion Because the NSData object is often pieced together from a number of different data objects, whenever possible, use
	 *             NSData’s enumerateByteRangesUsingBlock: method to iterate through the data rather than using the bytes method (which
	 *             flattens the NSData object into a single memory block). This delegate method may be called more than once, and each call
	 *             provides only data received since the previous call. The app is responsible for accumulating this data if needed.
	 **/

	public abstract void URLSessionDataTaskDidReceiveData(NSURLSession session,
														  NSURLSessionDataTask dataTask, NSData data);

	// 3
	/**
	 * @Signature: URLSession:dataTask:didReceiveResponse:completionHandler:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask didReceiveResponse:(NSURLResponse
	 *              *)response completionHandler:(void (^)(NSURLSessionResponseDisposition disposition))completionHandler
	 * @Description : Tells the delegate that the data task received the initial reply (headers) from the server.
	 * @Discussion This method is optional unless you need to support the (relatively obscure) multipart/x-mixed-replace content type. With
	 *             that content type, the server sends a series of parts, each of which is intended to replace the previous part. The
	 *             session calls this method at the beginning of each part, and you should then display, discard, or otherwise process the
	 *             previous part, as appropriate. If you do not provide this delegate method, the session always allows the task to
	 *             continue.
	 **/

	public abstract void URLSessionDataTaskDidReceiveResponseCompletionHandler(NSURLSession session,
																			   NSURLSessionDataTask dataTask, NSURLResponse response,
																			   PerformBlock.VoidBlockNSURLSessionResponseDisposition completionHandler);

	// 4
	/**
	 * @Signature: URLSession:dataTask:willCacheResponse:completionHandler:
	 * @Declaration : - (void)URLSession:(NSURLSession *)session dataTask:(NSURLSessionDataTask *)dataTask
	 *              willCacheResponse:(NSCachedURLResponse *)proposedResponse completionHandler:(void (^)(NSCachedURLResponse
	 *              *cachedResponse))completionHandler
	 * @Description : Asks the delegate whether the data (or upload) task should store the response in the cache.
	 * @Discussion The session calls this delegate method after the task finishes receiving all of the expected data. If you do not
	 *             implement this method, the default behavior is to use the caching policy specified in the session’s configuration object.
	 *             The primary purpose of this method is to prevent caching of specific URLs or to modify the userInfo dictionary associated
	 *             with the URL response. This method is called only if the NSURLProtocol handling the request decides to cache the
	 *             response. As a rule, responses are cached only when all of the following are true: The request is for an HTTP or HTTPS
	 *             URL (or your own custom networking protocol that supports caching). The request was successful (with a status code in the
	 *             200–299 range). The provided response came from the server, rather than out of the cache. The session configuration’s
	 *             cache policy allows caching. The provided NSURLRequest object's cache policy (if applicable) allows caching. The
	 *             cache-related headers in the server’s response (if present) allow caching. The response size is small enough to
	 *             reasonably fit within the cache. (For example, if you provide a disk cache, the response must be no larger than about 5%
	 *             of the disk cache size.)
	 **/

	public abstract void URLSessionDataTaskWillCacheResponseCompletionHandler(NSURLSession session,
																			  NSURLSessionDataTask dataTask, NSCachedURLResponse proposedResponse,
																			  PerformBlock.VoidBlockNSCachedURLResponse completionHandler);

}
