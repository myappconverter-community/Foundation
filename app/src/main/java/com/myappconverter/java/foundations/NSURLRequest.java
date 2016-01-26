
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;

import android.util.Log;



public class NSURLRequest extends NSObject implements NSCopying, NSMutableCopying {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());

	// Enumeration


	public static enum NSURLRequestNetworkServiceType {
		NSURLNetworkServiceTypeDefault, //
		NSURLNetworkServiceTypeVoIP, //
		NSURLNetworkServiceTypeVideo, //
		NSURLNetworkServiceTypeBackground, //
		NSURLNetworkServiceTypeVoice;
	};

	public static enum NSURLRequestCachePolicy {
		NSURLRequestUseProtocolCachePolicy(0), //
		NSURLRequestReloadIgnoringLocalCacheData(1), //
		NSURLRequestReloadIgnoringLocalAndRemoteCacheData(4), // Unimplemented
		NSURLRequestReloadIgnoringCacheData(NSURLRequestReloadIgnoringLocalCacheData.getValue()), //
		NSURLRequestReturnCacheDataElseLoad(2), //
		NSURLRequestReturnCacheDataDontLoad(3), //

		NSURLRequestReloadRevalidatingCacheData(5); // Unimplemented

		int value;

		NSURLRequestCachePolicy(int v) {
			value = v;
		}

		int getValue() {
			return this.value;
		}

	};


	public static final int NSURLRequestUseProtocolCachePolicy = 0;

	public static final int NSURLRequestReloadIgnoringLocalCacheData = 1;

	public static final int NSURLRequestReturnCacheDataElseLoad = 2;

	public static final int NSURLRequestReturnCacheDataDontLoad = 3;

	protected Map headersInfo = new HashMap<String, String>();


	public NSURL maindocumentURL;


	public NSURL URL;


	public int cachePolicy;


	public NSURLRequestNetworkServiceType networkServiceType;


	public double timeoutInterval;


	public NSDictionary allHTTPHeaderFields;


	public NSData HTTPBody;


	NSInputStream HTTPBodyStream;


	public NSString HTTPMethod;

	boolean HttpShouldHandleCookies;
	boolean allowsCellularAccess;

	public boolean httpShouldUsePipelining;

	// HttpURLConnection urlConnection;

	public NSURLRequest() {
		super();
	}

	public NSURLRequest(NSURL theURL) {
		this.URL = theURL;
		cachePolicy = NSURLRequestUseProtocolCachePolicy;
		timeoutInterval = 60;
	}

	/**
	 * @Declaration : + (id)requestWithURL:(NSURL *)theURL
	 * @Description : Creates and returns a URL request for a specified URL with default cache policy and timeout value.
	 * @param theURL The URL for the new request.
	 * @return Return Value The newly created URL request.
	 * @Discussion The default cache policy is NSURLRequestUseProtocolCachePolicy and the default timeout interval is 60 seconds.
	 **/

	public static Object requestWithURL(NSURL theURL) {
		return new NSURLRequest(theURL);
	}

	/**
	 * @Declaration : - (id)initWithURL:(NSURL *)theURL
	 * @Description : Returns a URL request for a specified URL with default cache policy and timeout value.
	 * @param theURL The URL for the request.
	 * @return Return Value The initialized URL request.
	 * @Discussion The default cache policy is NSURLRequestUseProtocolCachePolicy and the default timeout interval is 60 seconds.
	 **/

	public Object initWithURL(NSURL theURL) throws IOException {
		NSURL url = (NSURL) theURL.initWithString(new NSString(theURL.getWrappedURL().toString()));
		URLConnection urlConnection = url.getUrl().openConnection();
		urlConnection.setReadTimeout(60000);
		urlConnection.setConnectTimeout(6000);
		urlConnection.setDefaultUseCaches(true);
		return urlConnection.getRequestProperties();
	}

	/**
	 * @Declaration : + (id)requestWithURL:(NSURL *)theURL cachePolicy:(NSURLRequestCachePolicy)cachePolicy
	 *              timeoutInterval:(NSTimeInterval)timeoutInterval
	 * @Description : Creates and returns an initialized URL request with specified values.
	 * @param theURL The URL for the new request.
	 * @param cachePolicy The cache policy for the new request.
	 * @param timeoutInterval The timeout interval for the new request, in seconds.
	 * @return Return Value The newly created URL request.
	 **/

	public static Object requestWithURL(NSURL theURL, int cachePolicy, double timeoutInterval) {
		try {
			NSURLRequest request = new NSURLRequest(theURL);
			request.URL.getWrappedURL().openConnection().setConnectTimeout((int) timeoutInterval);
			boolean cache = false;
			if (cachePolicy != NSURLRequestReloadIgnoringLocalCacheData)
				cache = true;
			request.URL.getWrappedURL().openConnection().setDefaultUseCaches(cache);
			return request;
		} catch (IOException e) {
			LOGGER.info(String.valueOf(e));
		}
		return new NSURLRequest(theURL);
	}

	public static Object requestWithURL(NSURL theURL, NSURLRequestCachePolicy cachePolicy, double timeoutInterval) {
		try {
			NSURLRequest request = new NSURLRequest(theURL);
			request.URL.getWrappedURL().openConnection().setConnectTimeout((int) timeoutInterval);
			boolean cache = false;
			if (cachePolicy != NSURLRequestCachePolicy.NSURLRequestReloadIgnoringLocalCacheData)
				cache = true;
			request.URL.getWrappedURL().openConnection().setDefaultUseCaches(cache);
			return request;
		} catch (IOException e) {
			LOGGER.info(String.valueOf(e));
		}
		return new NSURLRequest(theURL);
	}

	/**
	 * @Declaration : - (id)initWithURL:(NSURL *)theURL cachePolicy:(NSURLRequestCachePolicy)cachePolicy
	 *              timeoutInterval:(NSTimeInterval)timeoutInterval
	 * @Description : Returns an initialized URL request with specified values.
	 * @param theURL The URL for the request.
	 * @param cachePolicy The cache policy for the request.
	 * @param timeoutInterval The timeout interval for the request, in seconds.
	 * @return Return Value The initialized URL request.
	 * @Discussion This is the designated initializer for NSURLRequest.
	 **/

	public Object initWithURL(NSURL theURL, int cachePolicy, double timeoutInterval) throws IOException {

		URLConnection urlConnection = theURL.getWrappedURL().openConnection();
		urlConnection.setConnectTimeout((int) timeoutInterval);
		urlConnection.setConnectTimeout((int) timeoutInterval);
		boolean cache = false;
		if (cachePolicy != NSURLRequestReloadIgnoringLocalCacheData) {
			cache = true;
		}
		urlConnection.setDefaultUseCaches(cache);
		return urlConnection.getRequestProperties();
	}

	/**
	 * @Declaration : - (NSURLRequestCachePolicy)cachePolicy
	 * @Description : Returns the receiverâ€™s cache policy.
	 * @return Return Value The receiverâ€™s cache policy.
	 **/

	public int cachePolicy() {
		return this.cachePolicy;
	}

	/**
	 * @Declaration : - (BOOL)HTTPShouldUsePipelining
	 * @Description : Returns whether the request should continue transmitting data before receiving a response from an earlier
	 *              transmission.
	 * @return Return Value YES if the request should continue transmitting data; otherwise, NO.
	 **/

	public boolean HTTPShouldUsePipelining() {
		return httpShouldUsePipelining;
	}

	/**
	 * @Declaration : - (NSURL *)mainDocumentURL
	 * @Description : Returns the main document URL associated with the request.
	 * @return Return Value The main document URL associated with the request.
	 * @Discussion This URL is used for the cookie â€œsame domain as main documentâ€? policy.
	 **/

	public NSURL mainDocumentURL() {
		return maindocumentURL;
	}

	/**
	 * @Declaration : - (NSTimeInterval)timeoutInterval
	 * @Description : Returns the receiverâ€™s timeout interval, in seconds.
	 * @return Return Value The receiver's timeout interval, in seconds.
	 * @Discussion If during a connection attempt the request remains idle for longer than the timeout interval, the request is considered
	 *             to have timed out.
	 **/

	public double timeoutInterval() {
		return this.timeoutInterval;
	}

	/**
	 * @Declaration : - (NSURLRequestNetworkServiceType)networkServiceType
	 * @Description : Returns the network service type of the request.
	 * @return Return Value The network service type of the request.
	 * @Discussion The network service type provides a hint to the operating system about what the underlying traffic is used for. This hint
	 *             enhances the system's ability to prioritize traffic, determine how quickly it needs to wake up the cellular or Wi-Fi
	 *             radio, and so on. By providing accurate information, you improve the ability of the system to optimally balance battery
	 *             life, performance, and other considerations. For example, you should specify the NSURLNetworkServiceTypeBackground type
	 *             if your app is performing a download that was not requested by the userâ€”for example, prefetching content so that it will
	 *             be available when the user chooses to view it.
	 **/

	public NSURLRequestNetworkServiceType networkServiceType() {
		return this.networkServiceType;
	}

	/**
	 * @Declaration : - (NSURL *)URL
	 * @Description : Returns the request's URL.
	 * @return Return Value The request's URL.
	 **/

	public NSURL URL() {
		return URL;
	}

	/**
	 * @Declaration : - (NSDictionary *)allHTTPHeaderFields
	 * @Description : Returns a dictionary containing all the receiverâ€™s HTTP header fields.
	 * @return Return Value A dictionary containing all the receiverâ€™s HTTP header fields.
	 **/

	public NSDictionary allHTTPHeaderFields() throws IOException {
		URLConnection urlConnection = URL.getWrappedURL().openConnection();
		// FIXME
		return new NSDictionary(urlConnection.getHeaderFields());
	}

	/**
	 * @Declaration : - (NSData *)HTTPBody
	 * @Description : Returns the receiverâ€™s HTTP body data.
	 * @return Return Value The receiver's HTTP body data.
	 * @Discussion This data is sent as the message body of a request, as in an HTTP POST request.
	 **/

	public NSData HTTPBody() {
		return this.HTTPBody;

	}

	/**
	 * @Declaration : - (NSInputStream *)HTTPBodyStream
	 * @Description : Returns the receiverâ€™s HTTP body stream.
	 * @return Return Value The receiverâ€™s HTTP body stream, or nil if it has not been set. The returned stream is for examination only, it
	 *         is not safe to manipulate the stream in any way.
	 * @Discussion The receiver will have either an HTTP body or an HTTP body stream, only one may be set for a request. A HTTP body stream
	 *             is preserved when copying an NSURLRequest object, but is lost when a request is archived using the NSCoding protocol.
	 **/

	public NSInputStream HTTPBodyStream() {
		return this.HTTPBodyStream;

	}

	/**
	 * @Declaration : - (NSString *)HTTPMethod
	 * @Description : Returns the receiverâ€™s HTTP request method.
	 * @return Return Value The receiverâ€™s HTTP request method.
	 * @Discussion The default HTTP method is â€œGETâ€?.
	 **/

	public NSString HTTPMethod() {
		return this.HTTPMethod;

	}

	/**
	 * @Declaration : - (BOOL)HTTPShouldHandleCookies
	 * @Description : Returns whether the default cookie handling will be used for this request.
	 * @return Return Value YES if the default cookie handling will be used for this request, NO otherwise.
	 * @Discussion The default is YES.
	 **/

	public boolean HTTPShouldHandleCookies() {
		return this.HttpShouldHandleCookies;

	}

	/**
	 * @Declaration : - (NSString *)valueForHTTPHeaderField:(NSString *)field
	 * @Description : Returns the value of the specified HTTP header field.
	 * @param field The name of the header field whose value is to be returned. In keeping with the HTTP RFC, HTTP header field names are
	 *            case-insensitive.
	 * @return Return Value The value associated with the header field field, or nil if there is no corresponding header field.
	 **/

	public NSString valueForHTTPHeaderField(NSString field) {
		NSMutableDictionary headers = new NSMutableDictionary();
		return (NSString) headers.objectForKey(field);

	}

	/**
	 * @Declaration : - (BOOL)allowsCellularAccess
	 * @Description : Returns whether the request is allowed to use the cellular radio (if present).
	 * @return Return Value YES if the cellular radio can be used; NO otherwise.
	 **/

	public boolean allowsCellularAccess() {
		return this.allowsCellularAccess;
	}

	/**
	 * @Declaration : - (BOOL)supportsSecureCoding
	 * @Description : Indicates that NSURLRequest implements the NSSecureCoding protocol.
	 * @return Return Value YES to indicate that NSURLRequest implements the NSSecureCoding protocol.
	 **/

	public boolean supportsSecureCoding() {
		if (NSSecureCoding.class.isAssignableFrom(getClass())) {
			return true;
		}
		return false;
	}

	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

}