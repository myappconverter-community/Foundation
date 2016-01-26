
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;

/**
 * @Overview An NSCachedURLResponse object represents a cached response to a URL request. It provides the server’s response metadata in the
 *           form of an NSURLResponse object, along with an NSData object containing the actual cached content data. Its storage policy
 *           determines whether the response should be cached on disk, in memory, or not at all. <br>
 *           Cached responses also contain a user info dictionary where you can store app-specific information about the cached item. <br>
 *           The NSURLCache class stores and retrieves instances of NSCachedURLResponse.
 */


public class NSCachedURLResponse implements NSCoding, NSCopying {

	private NSData wrappedCachedData;

	/**
	 * The receiver’s user info dictionary. (read-only)
	 * 
	 * @Declaration OBJECTIVE-C
	 *  NSDictionary *userInfo
	 * @Discussion nil if there is no user info dictionary.
	 */
	private NSDictionary userInfo;

	public NSData getWrappedCachedData() {
		return wrappedCachedData;
	}

	public void setWrappedCachedData(NSData wrappedCachedData) {
		this.wrappedCachedData = wrappedCachedData;
	}

	// Creating a Cached URL Response

	/**
	 * @Signature: initWithResponse:data:
	 * @Declaration : - (id)initWithResponse:(NSURLResponse *)response data:(NSData *)data
	 * @Description : Initializes an NSCachedURLResponse object.
	 * @param response The response to cache.
	 * @param data The data to cache.
	 * @return Return Value The NSCachedURLResponse object, initialized using the given data.
	 * @Discussion The cache storage policy is set to the default, NSURLCacheStorageAllowed, and the user info dictionary is set to nil.
	 **/
	
	public NSObject initWithResponsedata(NSURLResponse response, NSData data) {
		return null;
	}

	/**
	 * @Signature: initWithResponse:data:userInfo:storagePolicy:
	 * @Declaration : - (id)initWithResponse:(NSURLResponse *)response data:(NSData *)data userInfo:(NSDictionary *)userInfo
	 *              storagePolicy:(NSURLCacheStoragePolicy)storagePolicy
	 * @Description : Initializes an NSCachedURLResponse object.
	 * @param response The response to cache.
	 * @param data The data to cache.
	 * @param userInfo An optional dictionary of user information. May be nil.
	 * @param storagePolicy The storage policy for the cached response.
	 * @return Return Value The NSCachedURLResponse object, initialized using the given data.
	 **/
	
	public Object initWithResponsedatauserInfostoragePolicy(NSURLResponse response, NSData data,
			NSDictionary userInfo, int storagePolicy) {
		return null;
	}

	// Getting Cached URL Response Properties
	/**
	 * @Signature: data
	 * @Declaration : - (NSData *)data
	 * @Description : Returns the receiver’s cached data.
	 * @return Return Value The receiver’s cached data.
	 **/
	
	public NSData data() {
		return null;
	}

	/**
	 * @Signature: response
	 * @Declaration : - (NSURLResponse *)response
	 * @Description : Returns the NSURLResponse object associated with the receiver.
	 * @return Return Value The NSURLResponse object associated with the receiver.
	 **/
	
	public NSURLResponse response() {
		return null;
	}

	/**
	 * @Signature: storagePolicy
	 * @Declaration : - (NSURLCacheStoragePolicy)storagePolicy
	 * @Description : Returns the receiver’s cache storage policy.
	 * @return Return Value The receiver’s cache storage policy.
	 **/
	
	public int storagePolicy() {
		return NSURLCache.NSURLCacheStorageAllowed;
	}

	/**
	 * @Signature: userInfo
	 * @Declaration : - (NSDictionary *)userInfo
	 * @Description : Returns the receiver’s user info dictionary.
	 * @return Return Value An NSDictionary object containing the receiver’s user info, or nil if there is no such object.
	 **/
	
	public NSDictionary userInfo() {
		return getUserInfo();
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	
	public NSDictionary getUserInfo() {
		return userInfo;
	}

	
	public void setUserInfo(NSDictionary userInfo) {
		this.userInfo = userInfo;
	}

}