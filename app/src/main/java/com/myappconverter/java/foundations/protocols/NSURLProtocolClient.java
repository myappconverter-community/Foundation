package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSCachedURLResponse;
import com.myappconverter.java.foundations.NSData;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSURLProtocol;
import com.myappconverter.java.foundations.NSURLRequest;
import com.myappconverter.java.foundations.NSURLResponse;


public abstract class NSURLProtocolClient {

	/**
	 * @Signature: URLProtocol:cachedResponseIsValid:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol cachedResponseIsValid:(NSCachedURLResponse *)cachedResponse
	 * @Description : Sent to indicate to the URL loading system that a cached response is valid. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param cachedResponse The cached response whose validity is being communicated.
	 **/

	public abstract void URLProtocolCachedResponseIsValid(NSURLProtocol protocol, NSCachedURLResponse cachedResponse);

	/**
	 * @Signature: URLProtocol:didCancelAuthenticationChallenge:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol didCancelAuthenticationChallenge:(NSURLAuthenticationChallenge
	 *              *)challenge
	 * @Description : Sent to indicate to the URL loading system that an authentication challenge has been canceled. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param challenge The authentication challenge that was canceled.
	 **/

	public abstract void URLProtocolDidCancelAuthenticationChallenge(NSURLProtocol protocol, NSURLAuthenticationChallengeSender challenge);

	/**
	 * @Signature: URLProtocol:didFailWithError:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol didFailWithError:(NSError *)error
	 * @Description : Sent when the load request fails due to an error. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param error The error that caused the failure of the load request.
	 **/

	public abstract void URLProtocolDidFailWithError(NSURLProtocol protocol, NSError error);

	/**
	 * @Signature: URLProtocol:didLoadData:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol didLoadData:(NSData *)data
	 * @Description : An NSURLProtocol subclass instance, protocol, sends this message to [protocol client] as it loads data. (required)
	 * @Discussion The data object must contain only new data loaded since the previous invocation of this method.
	 **/

	public abstract void URLProtocolDidLoadData(NSURLProtocol protocol, NSData data);

	/**
	 * @Signature: URLProtocol:didReceiveAuthenticationChallenge:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge
	 *              *)challenge
	 * @Description : Sent to indicate to the URL loading system that an authentication challenge has been received. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param challenge The authentication challenge that has been received.
	 * @Discussion The protocol client guarantees that it will answer the request on the same thread that called this method. The client may
	 *             add a default credential to the challenge it issues to the connection delegate, if protocol did not provide one.
	 **/

	public abstract void URLProtocolDidReceiveAuthenticationChallenge(NSURLProtocol protocol, NSURLAuthenticationChallengeSender challenge);

	/**
	 * @Signature: URLProtocol:didReceiveResponse:cacheStoragePolicy:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol didReceiveResponse:(NSURLResponse *)response
	 *              cacheStoragePolicy:(NSURLCacheStoragePolicy)policy
	 * @Description : Sent to indicate to the URL loading system that the protocol implementation has created a response object for the
	 *              request. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param response The newly available response object.
	 * @param policy The cache storage policy for the response.
	 * @Discussion The implementation should use the provided cache storage policy to determine whether to store the response in a cache.
	 **/

	public abstract void URLProtocolDidReceiveResponseCacheStoragePolicy(NSURLProtocol protocol, NSURLResponse response,
			NSURLCacheStoragePolicy policy);

	/**
	 * @Signature: URLProtocol:wasRedirectedToRequest:redirectResponse:
	 * @Declaration : - (void)URLProtocol:(NSURLProtocol *)protocol wasRedirectedToRequest:(NSURLRequest *)request
	 *              redirectResponse:(NSURLResponse *)redirectResponse
	 * @Description : Sent to indicate to the URL loading system that the protocol implementation has been redirected. (required)
	 * @param protocol The URL protocol object sending the message.
	 * @param request The new request that the original request was redirected to.
	 * @param redirectResponse The response from the original request that caused the redirect.
	 **/

	public abstract void URLProtocolWasRedirectedToRequestRedirectResponse(NSURLProtocol protocol, NSURLRequest request,
			NSURLResponse redirectResponse);

	/**
	 * @Signature: URLProtocolDidFinishLoading:
	 * @Declaration : - (void)URLProtocolDidFinishLoading:(NSURLProtocol *)protocol
	 * @Description : Sent to indicate to the URL loading system that the protocol implementation has finished loading. (required)
	 * @param protocol The URL protocol object sending the message.
	 **/

	public abstract void URLProtocolDidFinishLoading(NSURLProtocol protocol);

}
