
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;




public class NSMutableURLRequest extends NSURLRequest {

	/**
	 * Sets the cache policy of the receiver.
	 */
	public NSMutableURLRequest() {
		super();
	}

	@Override
	public NSURL URL() {
		return URL;
	}

	/**
	 * @Signature: setCachePolicy:
	 * @Declaration : - (void)setCachePolicy:(NSURLRequestCachePolicy)policy
	 * @Description : Sets the cache policy of the receiver.
	 * @param policy The new cache policy.
	 **/
	
	public void setCachePolicy(int policy) {
		this.cachePolicy = policy;
	}

	/**
	 * Sets the main document URL for the receiver.
	 */

	/**
	 * - (void)setMainDocumentURL:(NSURL *)theURL
	 * 
	 * @param theURL The new main document URL. Can be nil.
	 */
	
	public void setMainDocumentURL(NSURL theURL) {
		this.maindocumentURL = theURL;
	}

	/**
	 * Sets the network service type of the connection.
	 */

	/**
	 * - (void)setNetworkServiceType:(NSURLRequestNetworkServiceType) networkServiceType
	 * 
	 * @param networkServiceType The network service type.
	 */
	
	public void setNetworkServiceType(NSURLRequestNetworkServiceType networkServiceType) {
		this.networkServiceType = networkServiceType;
	}

	/**
	 * Sets the receiver s timeout interval, in seconds.
	 */

	/**
	 * - (void)setTimeoutInterval:(NSTimeInterval)timeoutInterval
	 * 
	 * @param timeoutInterval The timeout interval, in seconds. If during a connection attempt the request remains idle for longer than the
	 *            timeout interval, the request is considered to have timed out. The default timeout interval is 60 seconds.
	 */
	
	public void setTimeoutInterval(double timeoutInterval) {
		this.timeoutInterval = timeoutInterval;
	}

	/**
	 * Sets the URL of the receiver
	 */

	/**
	 * - (void)setURL:(NSURL *)theURL
	 * 
	 * @param theURL The new URL.
	 */
	
	public void setURL(NSURL theURL) {
		this.URL = theURL;
	}

	/**
	 * Sets whether the connection can use the device s cellular radio (if present).
	 */

	/**
	 * @Signature: setAllowsCellularAccess:
	 * @Declaration : - (void)setAllowsCellularAccess:(BOOL)allow
	 * @Description : Sets whether the connection can use the device’s cellular radio (if present).
	 * @param allow YES if the device’s cellular radio can be used; NO otherwise;
	 * @Discussion The default is YES.
	 **/
	
	public void setAllowsCellularAccess(boolean allow) {
		this.allowsCellularAccess = allow;
	}

	/**
	 * Adds an HTTP header to the receiver s HTTP header dictionary.
	 */

	/**
	 * @Signature: addValue:forHTTPHeaderField:
	 * @Declaration : - (void)addValue:(NSString *)value forHTTPHeaderField:(NSString *)field
	 * @Description : Adds an HTTP header to the receiver’s HTTP header dictionary.
	 * @param value The value for the header field.
	 * @param field The name of the header field. In keeping with the HTTP RFC, HTTP header field names are case-insensitive.
	 * @Discussion This method provides the ability to add values to header fields incrementally. If a value was previously set for the
	 *             specified field, the supplied value is appended to the existing value using the appropriate field delimiter. In the case
	 *             of HTTP, the delimiter is a comma. Important: The NSURLConnection class and NSURLSession classes are designed to handle
	 *             various aspects of the HTTP protocol for you. As a result, you should not modify the following headers: Authorization
	 *             Connection Host WWW-AuthenticateAdditionally, if the length of your upload body data can be determined automatically (for
	 *             example, if you provide the body content with an NSData object), then the value of Content-Length is set for you.
	 **/
	
	public void addValueForHTTPHeaderField(NSString value, NSString field) {
		String fieldContent = "";
		if (headersInfo.containsKey(value.getWrappedString())) {
			fieldContent = (String) headersInfo.get(value.getWrappedString()) + ",";
		}
		headersInfo.put(value.getWrappedString(), fieldContent + field.getWrappedString());
	}

	/**
	 * Replaces the receiver's header fields with the passed values.
	 */
	// Sets the specified HTTP header field.

	/**
	 * @Signature: setAllHTTPHeaderFields:
	 * @Declaration : - (void)setAllHTTPHeaderFields:(NSDictionary *)headerFields
	 * @Description : Replaces the receiver's header fields with the passed values.
	 * @param headerFields A dictionary with the new header fields. HTTP header fields must be string values; therefore, each object and key
	 *            in the headerFields dictionary must be a subclass of NSString. If either the key or value for a key-value pair is not a
	 *            subclass of NSString, the key-value pair is skipped.
	 * @Discussion The NSURLConnection class and NSURLSession classes are designed to handle various aspects of the HTTP protocol for you.
	 *             As a result, you should not modify the following headers: Authorization Connection Host WWW-Authenticate Additionally, if
	 *             the length of your upload body data can be determined automatically (for example, if you provide the body content with an
	 *             NSData object), then the value of Content-Length is set for you.
	 **/
	
	public void setAllHTTPHeaderFields(NSDictionary headerFields) throws IOException {
		NSDictionary map = headerFields;
		Set<NSString> cles = map.keySet();
		Iterator<NSString> it = cles.iterator();
		while (it.hasNext()) {
			NSString cle = it.next();
			NSString valeur = (NSString) map.get(cle);
			this.setValueForHTTPHeaderField(valeur, cle);
		}
	}

	/**
	 * Sets the request body of the receiver to the specified data.
	 */

	/**
	 * - (void)setHTTPBody:(NSData *)data
	 * 
	 * @param data The new request body for the receiver. This is sent as the message body of the request, as in an HTTP POST request.
	 */
	
	public void setHTTPBody(NSData data) {
		this.HTTPBody = data;
	}

	/**
	 * Sets the request body of the receiver to the contents of a specified input stream.
	 */

	/**
	 * - (void)setHTTPBodyStream:(NSInputStream *)inputStream
	 * 
	 * @param inputStream The input stream that will be the request body of the receiver. The entire contents of the stream will be sent as
	 *            the body, as in an HTTP POST request. The inputStream should be unopened and the receiver will take over as the stream s
	 *            delegate.
	 */
	
	public void setHTTPBodyStream(NSInputStream inputStream) {
		this.HTTPBodyStream = inputStream;
	}

	/**
	 * Sets the receiver s HTTP request method.
	 */

	/**
	 * - (void)setHTTPMethod:(NSString *)method
	 * 
	 * @param method The new HTTP request method. The default HTTP method is GET
	 * @throws IOException
	 */
	
	public void setHTTPMethod(NSString method) {
		HTTPMethod = method;
	}

	/**
	 * Sets whether the receiver should use the default cookie handling for the request.
	 */

	/**
	 * @Signature: setHTTPShouldHandleCookies:
	 * @Declaration : - (void)setHTTPShouldHandleCookies:(BOOL)handleCookies
	 * @Description : Sets whether the receiver should use the default cookie handling for the request.
	 * @param handleCookies YES if the receiver should use the default cookie handling for the request, NO otherwise. The default is YES.
	 * @Discussion If your app sets the Cookie header on an NSMutableURLRequest object, then this method has no effect, and the cookie data
	 *             you set in the header overrides all cookies from the cookie store.
	 **/
	
	public void setHTTPShouldHandleCookies(boolean handleCookies) {
		this.HttpShouldHandleCookies = handleCookies;
	}

	/**
	 * Sets whether the request can continue transmitting data before receiving a response from an earlier transmission.
	 */

	/**
	 * @Signature: setHTTPShouldUsePipelining:
	 * @Declaration : - (void)setHTTPShouldUsePipelining:(BOOL)shouldUsePipelining
	 * @Description : Sets whether the request can continue transmitting data before receiving a response from an earlier transmission.
	 * @param shouldUsePipelining If YES, the request should continue transmitting data; if NO, the request should wait for a response.
	 * @Discussion The default value is NO.
	 **/
	
	public void setHTTPShouldUsePipelining(boolean shouldUsePipelining) {
		this.httpShouldUsePipelining = shouldUsePipelining;
	}

	/**
	 * setValue:forHTTPHeaderField: Sets the specified HTTP header field.
	 */

	/**
	 * @Signature: setValue:forHTTPHeaderField:
	 * @Declaration : - (void)setValue:(NSString *)value forHTTPHeaderField:(NSString *)field
	 * @Description : Sets the specified HTTP header field.
	 * @param value The new value for the header field. Any existing value for the field is replaced by the new value.
	 * @param field The name of the header field to set. In keeping with the HTTP RFC, HTTP header field names are case-insensitive.
	 * @Discussion The NSURLConnection class and NSURLSession classes are designed to handle various aspects of the HTTP protocol for you.
	 *             As a result, you should not modify the following headers: Authorization Connection Host WWW-Authenticate Additionally, if
	 *             the length of your upload body data can be determined automatically (for example, if you provide the body content with an
	 *             NSData object), then the value of Content-Length is set for you.
	 **/
	
	public void setValueForHTTPHeaderField(NSString value, NSString field) {
		headersInfo.put(field.getWrappedString(), value.getWrappedString());
	}
}