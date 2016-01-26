
package com.myappconverter.java.foundations;

import java.net.HttpCookie;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class NSHTTPCookieStorage extends NSObject {

	// Enumeration

	
	public enum NSHTTPCookieAcceptPolicy {
		NSHTTPCookieAcceptPolicyAlways, //
		NSHTTPCookieAcceptPolicyNever, //
		NSHTTPCookieAcceptPolicyOnlyFromMainDocumentDomain

	}

	Map<NSURL, List<HttpCookie>> mapCookies = new HashMap<NSURL, List<HttpCookie>>();
	List<NSHTTPCookie> Cookies;
	NSHTTPCookieAcceptPolicy cookieAcceptPolicy;
	NSURL storageFile;
	NSURL mainDocument;
	private static NSHTTPCookieStorage mSigleton;

	// Creating and Initializing a Cookie Storage Object
	/**
	 * @Signature: initWithStorageLocation:
	 * @Declaration : - (id)initWithStorageLocation:(NSURL *)storageFileURL
	 * @Description : Returns an initialized NSHTTPCookieStorage object with a given file system location to store cookie information on
	 *              disk. (Available in iOS 4.0 through iOS 4.3.)
	 * @param storageFileURL A file:// URL that indicates the file to use for cookie storage.
	 * @return Return Value An initialized NSHTTPCookieStorage object that stores its cookie information at storageFileURL.
	 **/
	
	
	public Object initWithStorageLocation(NSURL storageFileURL) {
		this.storageFile = storageFileURL;
		this.cookieAcceptPolicy = NSHTTPCookieAcceptPolicy.NSHTTPCookieAcceptPolicyAlways;
		this.mainDocument = null;
		return this;
	}

	// Getting the Shared Cookie Storage Object
	/**
	 * @Signature: sharedHTTPCookieStorage
	 * @Declaration : + (NSHTTPCookieStorage *)sharedHTTPCookieStorage
	 * @Description : Returns the shared cookie storage instance.
	 * @return Return Value The shared cookie storage instance.
	 **/
	
	public static NSHTTPCookieStorage sharedHTTPCookieStorage() {
		if (mSigleton == null) {
			mSigleton = new NSHTTPCookieStorage();
		}
		return mSigleton;
	}

	// Getting and Setting the Cookie Accept Policy
	/**
	 * @Signature: cookieAcceptPolicy
	 * @Declaration : - (NSHTTPCookieAcceptPolicy)cookieAcceptPolicy
	 * @Description : Returns the cookie storage’s cookie accept policy.
	 * @return Return Value The cookie storage’s cookie accept policy. The default cookie accept policy is NSHTTPCookieAcceptPolicyAlways.
	 **/
	
	public NSHTTPCookieAcceptPolicy cookieAcceptPolicy() {
		return cookieAcceptPolicy;
	}

	/**
	 * @Signature: setCookieAcceptPolicy:
	 * @Declaration : - (void)setCookieAcceptPolicy:(NSHTTPCookieAcceptPolicy)aPolicy
	 * @Description : Sets the cookie accept policy of the cookie storage.
	 * @param aPolicy The new cookie accept policy.
	 * @Discussion The default cookie accept policy is NSHTTPCookieAcceptPolicyAlways. Changing the cookie policy affects all currently
	 *             running applications using the cookie storage.
	 **/
	
	public void setCookieAcceptPolicy(NSHTTPCookieAcceptPolicy aPolicy) {
		this.cookieAcceptPolicy = aPolicy;
	}

	// Adding and Removing Cookies
	/**
	 * @Signature: deleteCookie:
	 * @Declaration : - (void)deleteCookie:(NSHTTPCookie *)aCookie
	 * @Description : Deletes the specified cookie from the cookie storage.
	 * @param aCookie The cookie to delete.
	 **/
	
	public void deleteCookie(NSHTTPCookie aCookie) {
		if (mapCookies.containsValue(aCookie))
			mapCookies.remove(aCookie);
		else
			return;
	}

	/**
	 * @Signature: setCookie:
	 * @Declaration : - (void)setCookie:(NSHTTPCookie *)aCookie
	 * @Description : Stores a specified cookie in the cookie storage if the cookie accept policy permits.
	 * @param aCookie The cookie to store.
	 * @Discussion The cookie replaces an existing cookie with the same name, domain, and path, if one exists in the cookie storage. This
	 *             method accepts the cookie only if the receiver’s cookie accept policy is NSHTTPCookieAcceptPolicyAlways or
	 *             NSHTTPCookieAcceptPolicyOnlyFromMainDocumentDomain. The cookie is ignored if the receiver’s cookie accept policy is
	 *             NSHTTPCookieAcceptPolicyNever.
	 **/
	
	public void setCookie(NSHTTPCookie aCookie) {
		// TODO check that
		if (this.cookieAcceptPolicy() == NSHTTPCookieAcceptPolicy.NSHTTPCookieAcceptPolicyAlways) {
			if (mapCookies.containsKey(aCookie.getWrappedCookie().getName())) {
				mapCookies.remove(aCookie.getWrappedCookie().getName());
				mapCookies.put(new NSURL(), (List<HttpCookie>) aCookie);
			} else {
				mapCookies.put(new NSURL(), (List<HttpCookie>) aCookie);
			}

		} else {
			return;
		}
	}

	/**
	 * @Signature: setCookies:forURL:mainDocumentURL:
	 * @Declaration : - (void)setCookies:(NSArray *)cookies forURL:(NSURL *)theURL mainDocumentURL:(NSURL *)mainDocumentURL
	 * @Description : Adds an array of cookies to the receiver if the receiver’s cookie acceptance policy permits.
	 * @param cookies The cookies to add.
	 * @param theURL The URL associated with the added cookies.
	 * @param mainDocumentURL The URL of the main HTML document for the top-level frame, if known. Can be nil. This URL is used to determine
	 *            if the cookie should be accepted if the cookie accept policy is NSHTTPCookieAcceptPolicyOnlyFromMainDocumentDomain.
	 * @Discussion The cookies will replace existing cookies with the same name, domain, and path, if one exists in the cookie storage. The
	 *             cookie will be ignored if the receiver's cookie accept policy is NSHTTPCookieAcceptPolicyNever. To store cookies from a
	 *             set of response headers, an application can use cookiesWithResponseHeaderFields:forURL: passing a header field dictionary
	 *             and then use this method to store the resulting cookies in accordance with the receiver’s cookie acceptance policy.
	 **/
	
	public void setCookiesForURLMainDocumentURL(NSArray<NSHTTPCookie> cookies, NSURL theURL, NSURL mainDocumentURL) {
		this.Cookies.addAll(cookies.wrappedList);
		this.storageFile = theURL;
		this.cookieAcceptPolicy = NSHTTPCookieAcceptPolicy.NSHTTPCookieAcceptPolicyAlways;
		this.mainDocument = mainDocumentURL;
	}

	// Retrieving Cookies
	/**
	 * @Signature: cookies
	 * @Declaration : - (NSArray *)cookies
	 * @Description : Returns the cookie storage’s cookies.
	 * @return Return Value An array containing all of the cookie storage’s cookies.
	 * @Discussion If you want to sort the cookie storage’s cookies, you should use the sortedCookiesUsingDescriptors: method instead of
	 *             sorting the result of this method.
	 **/
	
	public NSArray<NSHTTPCookie> cookies() {
		NSArray<NSHTTPCookie> mArray = new NSArray<NSHTTPCookie>();
		mArray.wrappedList.addAll(Cookies);
		return mArray;
	}

	/**
	 * @Signature: cookiesForURL:
	 * @Declaration : - (NSArray *)cookiesForURL:(NSURL *)theURL
	 * @Description : Returns all the cookie storage’s cookies that are sent to a specified URL.
	 * @param theURL The URL to filter on.
	 * @return Return Value An array of cookies whose URL matches the provided URL.
	 * @Discussion An application can use NSHTTPCookie method requestHeaderFieldsWithCookies: to turn this array into a set of header fields
	 *             to add to an NSMutableURLRequest object.
	 **/
	
	public NSArray<NSHTTPCookie> cookiesForURL(NSURL theURL) {
		NSArray<NSHTTPCookie> mArray = new NSArray<NSHTTPCookie>();
		mArray.wrappedList.add((NSHTTPCookie) mapCookies.get(theURL));
		return mArray;
	}

	/**
	 * @Signature: sortedCookiesUsingDescriptors:
	 * @Declaration : - (NSArray *)sortedCookiesUsingDescriptors:(NSArray *)sortOrder
	 * @Description : Returns all of the cookie storage’s cookies, sorted according to a given set of sort descriptors.
	 * @param sortOrder The sort descriptors to use for sorting, as an array of NSSortDescriptor objects.
	 * @return Return Value The cookie storage’s cookies, sorted according to sortOrder, as an array of NSHTTPCookie objects.
	 **/
	
	public NSArray<NSHTTPCookie> sortedCookiesUsingDescriptors(final NSArray<NSSortDescriptor> sortOrder) {
		NSArray<NSHTTPCookie> arrayCopie = new NSArray<NSHTTPCookie>();
		arrayCopie.setWrappedList(Cookies);
		Collections.sort(arrayCopie.getWrappedList(), new Comparator() {
			@Override
			public int compare(Object lhs, Object rhs) {
				int ordre = 0;
				for (NSSortDescriptor nsSort : sortOrder) {
					if (nsSort != null) {
						ordre = nsSort.getComparatorObject().compare(lhs, rhs);
						if (ordre != 0)
							return ordre;
					}
				}
				return ordre;
			}
		});
		return arrayCopie;
	}

	// Notifications

	NSHTTPCookieStorage NSHTTPCookieManagerCookiesChangedNotification;
	NSNotification NSHTTPCookieManagerAcceptPolicyChangedNotification;

}