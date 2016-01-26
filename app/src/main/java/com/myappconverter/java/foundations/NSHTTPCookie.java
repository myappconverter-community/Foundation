
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;



public class NSHTTPCookie extends NSObject {

	HttpCookie wrappedHttpCookie;

	public HttpCookie getWrappedCookie() {
		return wrappedHttpCookie;
	}

	public void setWrappedCookie(HttpCookie mCookie) {
		this.wrappedHttpCookie = mCookie;
	}

	// Create Cookie Instances
	/**
	 * @Signature: cookiesWithResponseHeaderFields:forURL:
	 * @Declaration : + (NSArray *)cookiesWithResponseHeaderFields:(NSDictionary *)headerFields forURL:(NSURL *)theURL
	 * @Description : Returns an array of NSHTTPCookie objects corresponding to the provided response header fields for the provided URL.
	 * @param headerFields The header fields used to create the NSHTTPCookie objects.
	 * @param theURL The URL associated with the created cookies.
	 * @return Return Value The array of created cookies.
	 * @Discussion This method ignores irrelevant header fields in headerFields, allowing dictionaries to contain additional data. If
	 *             headerFields does not specify a domain for a given cookie, the cookie is created with a default domain value of theURL.
	 *             If headerFields does not specify a path for a given cookie, the cookie is created with a default path value of "/".
	 **/
	
	public static List<NSHTTPCookie> cookiesWithResponseHeaderFieldsForURL(Map<String, List<String>> headerFields, NSURL theURL) {
		List<NSHTTPCookie> cookies = null;
		try {
			URLConnection conn = theURL.getUrl().openConnection();
		} catch (IOException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		Set<String> headerFieldsSet = headerFields.keySet();
		String cookieValue = "";
		String expires = null;
		String path = null;
		String domain = null;
		boolean secure = false;
		Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

		while (hearerFieldsIter.hasNext()) {

			String headerFieldKey = hearerFieldsIter.next();

			if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {

				List<String> headerFieldValue = headerFields.get(headerFieldKey);
				cookies = new ArrayList<NSHTTPCookie>();
				for (String headerValue : headerFieldValue) {

					String[] fields = headerValue.split(";s*");

					cookieValue = fields[0];

					// Parse each field

					for (int j = 1; j < fields.length; j++) {

						if ("secure".equalsIgnoreCase(fields[j])) {

							secure = true;

						}

						else if (fields[j].indexOf('=') > 0) {

							String[] f = fields[j].split("=");

							if ("expires".equalsIgnoreCase(f[0])) {

								expires = f[1];

							}

							else if ("domain".equalsIgnoreCase(f[0])) {

								domain = f[1];

							}

							else if ("path".equalsIgnoreCase(f[0])) {

								path = f[1];

							}

						}

					}
					NSHTTPCookie cookie = new NSHTTPCookie();
					cookie.wrappedHttpCookie.setValue(cookieValue);
					cookie.wrappedHttpCookie.setMaxAge(Long.valueOf(expires));
					cookie.wrappedHttpCookie.setPath(path);
					cookie.wrappedHttpCookie.setDomain(domain);
					cookie.wrappedHttpCookie.setSecure(secure);
					cookies.add(cookie);
					cookie = null;
				}

			}

		}

		return cookies;
	}

	/**
	 * @Signature: cookieWithProperties:
	 * @Declaration : + (id)cookieWithProperties:(NSDictionary *)properties
	 * @Description : Creates and initializes an NSHTTPCookie object using the provided properties.
	 * @param properties The properties for the new cookie object, expressed as key value pairs.
	 * @return Return Value The newly created cookie object. Returns nil if the provided properties are invalid.
	 * @Discussion To successfully create a cookie, you must provide values for (at least) the NSHTTPCookiePath, NSHTTPCookieName, and
	 *             NSHTTPCookieValue keys, and either the NSHTTPCookieOriginURL key or the NSHTTPCookieDomain key. See “Constants for more
	 *             information on the available cookie attribute constants and the constraints imposed on the values in the dictionary.
	 **/
	
	public static Object cookieWithProperties(NSDictionary<NSString, NSObject> properties) {
		NSHTTPCookie nsHttpCookie = new NSHTTPCookie();
		// FIXME
		// nsHttpCookie.mCookie.setComment((String) properties.valueForKey(new NSString("cookieComment")));
		// nsHttpCookie.mCookie.setMaxAge((Long) properties.valueForKey(new NSString("cookieMaxAgeSeconds")));
		// nsHttpCookie.mCookie.setPath((String) properties.valueForKey(new NSString("cookiePath")));
		// nsHttpCookie.mCookie.setDomain((String) properties.valueForKey(new NSString("cookieDomain")));
		return nsHttpCookie;
	}

	/**
	 * @Signature: initWithProperties:
	 * @Declaration : - (id)initWithProperties:(NSDictionary *)properties
	 * @Description : Returns an initialized NSHTTPCookie object using the provided properties.
	 * @param properties The properties for the new cookie object, expressed as key value pairs.
	 * @return Return Value The initialized cookie object. Returns nil if the provided properties are invalid.
	 * @Discussion To successfully create a cookie, you must provide values for (at least) the NSHTTPCookiePath, NSHTTPCookieName, and
	 *             NSHTTPCookieValue keys, and either the NSHTTPCookieOriginURL key or the NSHTTPCookieDomain key. See “Constants for more
	 *             information on the available cookie attribute constants and the constraints imposed on the values in the dictionary.
	 **/
	
	public Object initWithProperties(NSDictionary<NSString, NSObject> properties) {
		return this;
	}

	// Convert Cookies to Request Headers
	/**
	 * @Signature: requestHeaderFieldsWithCookies:
	 * @Declaration : + (NSDictionary *)requestHeaderFieldsWithCookies:(NSArray *)cookies
	 * @Description : Returns a dictionary of header fields corresponding to a provided array of cookies.
	 * @param cookies The cookies from which the header fields are created.
	 * @return Return Value The dictionary of header fields created from the provided cookies.
	 * @Discussion To send these headers as part of a URL request to a remote server, create an NSMutableURLRequest object, then call the
	 *             setAllHTTPHeaderFields: or setValue:forHTTPHeaderField: method to set the provided headers for the request. Finally,
	 *             initialize and start an NSURLSessionTask, NSURLConnection, or NSURLDownload object based on that request object.
	 **/
	
	public static NSDictionary<NSString, NSObject> requestHeaderFieldsWithCookies(NSArray<NSObject> cookies) {
		NSDictionary<NSString, NSObject> mDict = new NSDictionary<NSString, NSObject>();
		mDict.wrappedDictionary.put(new NSString("cookies"), (NSObject) cookies.getWrappedList());
		return mDict;
	}

	// Getting Cookie Properties
	/**
	 * @Signature: comment
	 * @Declaration : - (NSString *)comment
	 * @Description : Returns the receiver's comment string.
	 * @return Return Value The receiver’s comment string or nil if the cookie has no comment. This string is suitable for presentation to
	 *         the user, explaining the contents and purpose of this cookie.
	 **/
	
	public NSString comment() {
		return new NSString(wrappedHttpCookie.getComment());
	}

	/**
	 * @Signature: commentURL
	 * @Declaration : - (NSURL *)commentURL
	 * @Description : Returns the receiver’s comment URL.
	 * @return Return Value The receiver’s comment URL or nil if the cookie has none. This value specifies a URL which is suitable for
	 *         presentation to the user as a link for further information about this cookie.
	 **/
	
	public NSURL commentURL() {
		return new NSURL(wrappedHttpCookie.getCommentURL());
	}

	/**
	 * @Signature: domain
	 * @Declaration : - (NSString *)domain
	 * @Description : Returns the domain of the receiver’s cookie.
	 * @return Return Value The domain of the receiver’s cookie.
	 * @Discussion If the domain does not start with a dot, then the cookie is only sent to the exact host specified by the domain. If the
	 *             domain does start with a dot, then the cookie is sent to other hosts in that domain as well, subject to certain
	 *             restrictions. See RFC 2965 for more detail.
	 **/
	
	public NSString domain() {
		return new NSString(wrappedHttpCookie.getDomain());
	}

	/**
	 * @Signature: expiresDate
	 * @Declaration : - (NSDate *)expiresDate
	 * @Description : Returns the receiver’s expiration date.
	 * @return Return Value The receiver’s expiration date, or nil if there is no specific expiration date such as in the case of
	 *         “session-only cookies. The expiration date is the date when the cookie should be deleted.
	 **/
	
	public NSDate expiresDate() {
		NSDate nsDate = new NSDate();
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, (int) wrappedHttpCookie.getMaxAge());
		nsDate.setWrappedDate(c.getTime());
		return nsDate;
	}

	/**
	 * @Signature: isHTTPOnly
	 * @Declaration : - (BOOL)isHTTPOnly
	 * @Description : Returns whether the receiver should only be sent to HTTP servers per RFC 2965.
	 * @return Return Value Returns YES if this cookie should only be sent via HTTP headers, NO otherwise.
	 * @Discussion Cookies may be marked as HTTP only by a server (or by a javascript). Cookies marked as such must only be sent via HTTP
	 *             Headers in HTTP requests for URL's that match both the path and domain of the respective cookies. Important: Cookies
	 *             specified as HTTP only should not be delivered to any javascript applications to prevent cross-site scripting
	 *             vulnerabilities.
	 **/
	
	public boolean isHTTPOnly() {
		/*
		 * This implementation silently discards unrecognized attributes. In particular, the HttpOnly attribute is widely served but isn't
		 * in any of the above specs. It was introduced by Internet Explorer to prevent server cookies from being exposed in the DOM to
		 * JavaScript, etc.
		 */
		return true;
	}

	/**
	 * @Signature: isSecure
	 * @Declaration : - (BOOL)isSecure
	 * @Description : Returns whether his cookie should only be sent over secure channels.
	 * @return Return Value YES if this cookie should only be sent over secure channels, otherwise NO.
	 **/
	
	public boolean isSecure() {
		return wrappedHttpCookie.getSecure();
	}

	/**
	 * @Signature: isSessionOnly
	 * @Declaration : - (BOOL)isSessionOnly
	 * @Description : Returns whether the receiver should be discarded at the end of the session (regardless of expiration date).
	 * @return Return Value YES if the receiver should be discarded at the end of the session (regardless of expiration date), otherwise NO.
	 **/
	
	public boolean isSessionOnly() {
		return wrappedHttpCookie.getDiscard();
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the receiver’s name.
	 * @return Return Value The receiver's name.
	 **/
	
	public NSString name() {
		return new NSString(wrappedHttpCookie.getName());
	}

	/**
	 * @Signature: path
	 * @Declaration : - (NSString *)path
	 * @Description : Returns the receiver’s path.
	 * @return Return Value The receiver's path.
	 * @Discussion The cookie will be sent with requests for this path in the cookie's domain, and all paths that have this prefix. A path
	 *             of "/" means the cookie will be sent for all URLs in the domain.
	 **/
	
	public NSString path() {
		return new NSString(wrappedHttpCookie.getPath());
	}

	/**
	 * @Signature: portList
	 * @Declaration : - (NSArray *)portList
	 * @Description : Returns the receiver's port list.
	 * @return Return Value The list of ports for the cookie, returned as an array of NSNumber objects containing integers. If the cookie
	 *         has no port list this method returns nil and the cookie will be sent to any port. Otherwise, the cookie is only sent to ports
	 *         specified in the port list.
	 **/
	
	public NSArray<NSNumber> portList() {
		NSArray<NSNumber> nsArray = new NSMutableArray<NSNumber>();
		String[] portList = wrappedHttpCookie.getPortlist().split(",");
		for (String portNumber : portList) {
			NSNumber nsNumber = new NSNumber();
			nsNumber.setNumberValue(portNumber);
			nsArray.getWrappedList().add(nsNumber);
		}
		return nsArray;
	}

	/**
	 * @Signature: properties
	 * @Declaration : - (NSDictionary *)properties
	 * @Description : Returns the receiver’s cookie properties.
	 * @return Return Value A dictionary representation of the receiver’s cookie properties.
	 * @Discussion This dictionary can be used with initWithProperties: or cookieWithProperties: to create an equivalent NSHTTPCookie
	 *             object. See initWithProperties: for more information on the constraints imposed on the properties dictionary.
	 **/
	
	public NSDictionary<NSString, NSObject> properties() {
		NSDictionary<NSString, NSObject> result = new NSDictionary<NSString, NSObject>();
		result.wrappedDictionary.put(new NSString("cookieDomain"), domain());
		result.wrappedDictionary.put(new NSString("cookiePath"), path());
		result.wrappedDictionary.put(new NSString("cookieMaxAgeSeconds"), new NSString(String.valueOf(wrappedHttpCookie.getMaxAge())));
		result.wrappedDictionary.put(new NSString("cookieComment"), comment());
		return result;
	}

	/**
	 * @Signature: value
	 * @Declaration : - (NSString *)value
	 * @Description : Returns the receiver’s value.
	 * @return Return Value The receiver's value.
	 **/
	
	public NSString value() {
		return new NSString(wrappedHttpCookie.getValue());
	}

	/**
	 * @Signature: version
	 * @Declaration : - (NSUInteger)version
	 * @Description : Returns the receiver’s version.
	 * @return Return Value The receiver's version. Version 0 maps to “old-style Netscape cookies. Version 1 maps to RFC 2965 cookies.
	 **/
	
	public int _version() {
		return 0;
	}

}