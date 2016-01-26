
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;



public class NSHTTPURLResponse extends NSURLResponse {

	HttpURLConnection httpUrlConnection;

	// Initializing a Reponse Object

	/**
	 * @Signature: initWithURL:statusCode:HTTPVersion:headerFields:
	 * @Declaration : - (id)initWithURL:(NSURL *)url statusCode:(NSInteger)statusCode HTTPVersion:(NSString *)HTTPVersion
	 *              headerFields:(NSDictionary *)headerFields
	 * @Description : Initializes an HTTP URL response object with a status code, protocol version, and response headers.
	 * @param url The URL from which the response was generated.
	 * @param statusCode The HTTP status code to return (404, for example). See RFC 2616 for details.
	 * @param HTTPVersion The version of the HTTP response as returned by the server. This is typically represented as "HTTP/1.1".
	 * @param headerFields A dictionary representing the keys and values from the server’s response header.
	 * @return Return Value An initialized NSHTTPURLResponse object or nil if an error occurred during initialization.
	 **/
	
	public Object initWithURLStatusCodeHTTPVersionHeaderFields(NSURL url, int statusCode, NSString HTTPVersion, NSDictionary headerFields) {

		try {
			httpUrlConnection = (HttpsURLConnection) url.getWrappedURL().openConnection();
		} catch (IOException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		httpUrlConnection.setRequestProperty("HTTP-Version", HTTPVersion.getWrappedString());
		// TODO
		return httpUrlConnection;

	}

	// Getting HTTP Response Headers

	/**
	 * @Signature: allHeaderFields
	 * @Declaration : - (NSDictionary *)allHeaderFields
	 * @Description : Returns all the HTTP header fields of the receiver.
	 * @return Return Value A dictionary containing all the HTTP header fields received as part of the server’s response. By examining this
	 *         dictionary clients can see the “raw header information returned by the HTTP server. The keys in this dictionary are the
	 *         header field names, as received from the server. See RFC 2616 for a list of commonly used HTTP header fields.
	 * @Discussion HTTP headers are case insensitive. To simplify your code, certain header field names are canonicalized into their
	 *             standard form. For example, if the server sends a content-length header, it is automatically adjusted to be
	 *             Content-Length. The returned dictionary of headers is configured to be case-preserving during the set operation (unless
	 *             the key already exists with a different case), and case-insensitive when looking up keys. For example, if you set the
	 *             header X-foo, and then later set the header X-Foo, the dictionary’s key will be X-foo, but the value will taken from the
	 *             X-Foo header.
	 **/
	
	public NSDictionary<NSString, NSObject> allHeaderFields() {
		NSDictionary<NSString, NSObject> nsDictionary = new NSDictionary<NSString, NSObject>();
		return nsDictionary;
	}

	// Getting Response Status Code

	/**
	 * @Signature: localizedStringForStatusCode:
	 * @Declaration : + (NSString *)localizedStringForStatusCode:(NSInteger)statusCode
	 * @Description : Returns a localized string corresponding to a specified HTTP status code.
	 * @param statusCode The HTTP status code. See RFC 2616 for details.
	 * @return Return Value A localized string suitable for displaying to users that describes the specified status code.
	 **/
	
	public static NSString localizedStringForStatusCode(int statusCode) {

		switch (statusCode) {
		case HttpURLConnection.HTTP_ACCEPTED:
			return new NSString("HTTP_ACCEPTED");
		case HttpURLConnection.HTTP_BAD_GATEWAY:
			return new NSString("HTTP_BAD_GATEWAY");
		case HttpURLConnection.HTTP_BAD_METHOD:
			return new NSString("HTTP_BAD_METHOD");
		case HttpURLConnection.HTTP_BAD_REQUEST:
			return new NSString("HTTP_BAD_REQUEST");
		case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
			return new NSString("HTTP_CLIENT_TIMEOUT");
		case HttpURLConnection.HTTP_CONFLICT:
			return new NSString("HTTP_CONFLICT");
		case HttpURLConnection.HTTP_CREATED:
			return new NSString("HTTP_CREATED");
		case HttpURLConnection.HTTP_ENTITY_TOO_LARGE:
			return new NSString("HTTP_ENTITY_TOO_LARGE");
		case HttpURLConnection.HTTP_FORBIDDEN:
			return new NSString("HTTP_FORBIDDEN");
		case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
			return new NSString("HTTP_GATEWAY_TIMEOUT");
		case HttpURLConnection.HTTP_GONE:
			return new NSString("HTTP_GONE");
		case HttpURLConnection.HTTP_INTERNAL_ERROR:
			return new NSString("HTTP_INTERNAL_ERROR");
		case HttpURLConnection.HTTP_LENGTH_REQUIRED:
			return new NSString("HTTP_LENGTH_REQUIRED");
		case HttpURLConnection.HTTP_MOVED_PERM:
			return new NSString("HTTP_MOVED_PERM");
		case HttpURLConnection.HTTP_MOVED_TEMP:
			return new NSString("HTTP_MOVED_TEMP");
		case HttpURLConnection.HTTP_MULT_CHOICE:
			return new NSString("HTTP_MULT_CHOICE");
		case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
			return new NSString("HTTP_NOT_ACCEPTABLE");
		case HttpURLConnection.HTTP_NOT_AUTHORITATIVE:
			return new NSString("HTTP_NOT_AUTHORITATIVE");
		case HttpURLConnection.HTTP_NOT_FOUND:
			return new NSString("HTTP_NOT_FOUND");
		case HttpURLConnection.HTTP_NOT_IMPLEMENTED:
			return new NSString("HTTP_NOT_IMPLEMENTED");
		case HttpURLConnection.HTTP_NOT_MODIFIED:
			return new NSString("HTTP_NOT_MODIFIED");
		case HttpURLConnection.HTTP_NO_CONTENT:
			return new NSString("HTTP_NO_CONTENT");
		case HttpURLConnection.HTTP_OK:
			return new NSString("HTTP_OK_Numeric");
		case HttpURLConnection.HTTP_PARTIAL:
			return new NSString("HTTP_PARTIAL");
		case HttpURLConnection.HTTP_PAYMENT_REQUIRED:
			return new NSString("HTTP_PAYMENT_REQUIRED");
		case HttpURLConnection.HTTP_PRECON_FAILED:
			return new NSString("HTTP_PRECON_FAILED");
		case HttpURLConnection.HTTP_PROXY_AUTH:
			return new NSString("HTTP_PROXY_AUTH");
		case HttpURLConnection.HTTP_REQ_TOO_LONG:
			return new NSString("HTTP_REQ_TOO_LONG");
		case HttpURLConnection.HTTP_RESET:
			return new NSString("HTTP_RESET");
		case HttpURLConnection.HTTP_SEE_OTHER:
			return new NSString("HTTP_SEE_OTHER");
		case HttpURLConnection.HTTP_UNAUTHORIZED:
			return new NSString("HTTP_UNAUTHORIZED");
		case HttpURLConnection.HTTP_UNAVAILABLE:
			return new NSString("HTTP_UNAVAILABLE");
		case HttpURLConnection.HTTP_UNSUPPORTED_TYPE:
			return new NSString("HTTP_UNSUPPORTED_TYPE");
		case HttpURLConnection.HTTP_USE_PROXY:
			return new NSString("HTTP_USE_PROXY");
		case HttpURLConnection.HTTP_VERSION:
			return new NSString("HTTP_VERSION");
		default:
			break;
		}
		return new NSString("HTTP_OK_Numeric");

	}

	/**
	 * @Signature: statusCode
	 * @Declaration : - (NSInteger)statusCode
	 * @Description : Returns the receiver’s HTTP status code.
	 * @return Return Value The receiver’s HTTP status code. See RFC 2616 for details.
	 **/
	
	public int statusCode() {
		int statusCode = 0;

		try {
			statusCode = httpUrlConnection.getResponseCode();
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return statusCode;

	}

}