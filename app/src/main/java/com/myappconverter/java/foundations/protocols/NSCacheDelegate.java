package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSCache;

public interface NSCacheDelegate extends NSObject {
	// Responding to Object Eviction

	/**
	 * @Signature: cache:willEvictObject:
	 * @Declaration : - (void)cache:(NSCache *)cache willEvictObject:(id)obj
	 * @Description : Called when an object is about to be evicted or removed from the cache.
	 * @param cache The cache with which the object of interest is associated.
	 * @param obj The object of interest in the cache.
	 * @Discussion It is not possible to modify cache from within the implementation of this delegate method.
	 **/
	public void cacheWillEvictObject(NSCache cache, NSObject obj);

}
