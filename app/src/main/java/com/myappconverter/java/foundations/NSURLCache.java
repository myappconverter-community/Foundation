
package com.myappconverter.java.foundations;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.util.Log;


/**
 * @Overview The NSURLCache class implements the caching of responses to URL load requests by mapping NSURLRequest objects to
 *           NSCachedURLResponse objects. <br>
 *           It provides a composite in-memory and on-disk cache, and lets you manipulate the sizes of both the in-memory and on-disk
 *           portions.<br>
 *           You can also control the path where cache data is stored persistently.
 */

public class NSURLCache extends NSObject {
    private Context context;
    // cache properties
    private long diskCapacity;
    private long memoryCapacity;
    private long diskUsage;
    private long memoryUsage;
    private NSString path;
    private NSMutableDictionary memory;

    // constant

    // public static enum NSURLCacheStoragePolicy {
    //
    // }

    /**
     * Specifies that storage in NSURLCache is allowed without restriction. Important: iOS prior to version 5 ignores this cache policy, and
     * instead treats it as NSURLCacheStorageAllowedInMemoryOnly.
     */
    public static final int NSURLCacheStorageAllowed = 1; //
    /**
     * Specifies that storage in NSURLCache is allowed; however storage should be restricted to memory only.
     */
    public static final int NSURLCacheStorageAllowedInMemoryOnly = 2;
    /**
     * Specifies that storage in NSURLCache is not allowed in any fashion, either in memory or on disk.
     */
    public static final int NSURLCacheStorageNotAllowed = 0;

    // Not all protocol implementations support response caching. Currently only http and https requests are cached in
    // IOS
    private HttpResponseCache httpResponseCache;

    private static NSURLCache sharedInstance;

    public NSURLCache() {
        httpResponseCache = HttpResponseCache.getInstalled();
    }

    // Getting and Setting Shared Cache
    /**
     * @Signature: sharedURLCache
     * @Declaration : + (NSURLCache *)sharedURLCache
     * @Description : Returns the shared NSURLCache instance.
     * @return Return Value The shared NSURLCache instance.
     * @Discussion Applications that do not have special caching requirements or constraints should find the default shared cache instance
     *             acceptable. An application with more specific needs can create a custom NSURLCache object and set it as the shared cache
     *             instance using setSharedURLCache:. The application should do so before any calls to this method.
     **/

    public static NSURLCache sharedURLCache() {
        if (sharedInstance == null)
            sharedInstance = new NSURLCache();
        return sharedInstance;
    }

    /**
     * @Signature: setSharedURLCache:
     * @Declaration : + (void)setSharedURLCache:(NSURLCache *)cache
     * @Description : Sets the shared NSURLCache instance to a specified cache object.
     * @param cache The cache object to use as the shared cache object.
     * @Discussion An application that has special caching requirements or constraints should use this method to specify an NSURLCache
     *             instance with customized cache settings. The application should do so before any calls to the sharedURLCache method.
     **/

    public static void setSharedURLCache(NSURLCache cache) {
        sharedInstance = cache;
    }

    // Creating a New Cache Object
    /**
     * @Signature: initWithMemoryCapacity:diskCapacity:diskPath:
     * @Declaration : - (id)initWithMemoryCapacity:(NSUInteger)memoryCapacity diskCapacity:(NSUInteger)diskCapacity diskPath:(NSString
     *              *)path
     * @Description : Initializes an NSURLCache object with the specified values.
     * @param memoryCapacity The memory capacity of the cache, in bytes.
     * @param diskCapacity The disk capacity of the cache, in bytes.
     * @param path In OS X, path is the location at which to store the on-disk cache. In iOS, path is the name of a subdirectory of the
     *            application’s default cache directory in which to store the on-disk cache (the subdirectory is created if it does not
     *            exist).
     * @return Return Value The initialized NSURLCache object.
     * @Discussion The returned NSURLCache is backed by disk, so developers can be more liberal with space when choosing the capacity for
     *             this kind of cache. A disk cache measured in the tens of megabytes should be acceptable in most cases.
     **/

    public Object initWithMemoryCapacity(long memoryCapacity, long diskCapacity, NSString path) {
        NSURLCache nsurlCache = new NSURLCache();
        nsurlCache.memoryCapacity = memoryCapacity;
        nsurlCache.diskCapacity = diskCapacity;

        File httpCacheDir = new File(path.getWrappedString(), "http");
        if (!httpCacheDir.exists()) {
            httpCacheDir.mkdirs();
        } else {
            httpCacheDir = context.getCacheDir();
        }
        long httpCacheSize = diskCapacity;
        try {
            nsurlCache.httpResponseCache = HttpResponseCache.install(httpCacheDir, httpCacheSize);
            return nsurlCache;
        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }
        return null;
    }

    // Getting and Storing Cached Objects
    /**
     * @Signature: cachedResponseForRequest:
     * @Declaration : - (NSCachedURLResponse *)cachedResponseForRequest:(NSURLRequest *)request
     * @Description : Returns the cached URL response in the cache for the specified URL request.
     * @param request The URL request whose cached response is desired.
     * @return Return Value The cached URL response for request, or nil if no response has been cached.
     **/

    public NSCachedURLResponse cachedResponseForRequest(NSURLRequest request) {
        // TODO how to send this
        // FIXME correct NSURLRequest
        // try {
        // CacheResponse cachedURL = httpResponseCache.get(new URI(request.nsUrl.path().getWrappedString()),
        // request.HttpMethod.getWrappedString(), request.allHttpHeader.getDictionary());
        // NSCachedURLResponse nsCachedURLResponse = new NSCachedURLResponse();
        // // nsCachedURLResponse.cachedContents.data = cachedURL;
        // return nsCachedURLResponse;
        // } catch (IOException e) {
        // Log.d("Exception ","Message :"+e.getMessage()+"\n StackTrace: "+Log.getStackTraceString(e));
        // } catch (URISyntaxException e) {
        // Log.d("Exception ","Message :"+e.getMessage()+"\n StackTrace: "+Log.getStackTraceString(e));
        // }
        return null;
    }

    /**
     * @Signature: storeCachedResponse:forRequest:
     * @Declaration : - (void)storeCachedResponse:(NSCachedURLResponse *)cachedResponse forRequest:(NSURLRequest *)request
     * @Description : Stores a cached URL response for a specified request
     * @param cachedResponse The cached URL response to store.
     * @param request The request for which the cached URL response is being stored.
     **/

    public void storeCachedResponse(NSCachedURLResponse cachedResponse, NSURLRequest request) {
        URI uri;
        try {
            uri = new URI(request.URL().getUri().toString());
            URLConnection urlConnection = null;
            httpResponseCache.put(uri, urlConnection);

        } catch (URISyntaxException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));

        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }

    }

    // Removing Cached Objects
    /**
     * @Signature: removeAllCachedResponses
     * @Declaration : - (void)removeAllCachedResponses
     * @Description : Clears the receiver’s cache, removing all stored cached URL responses.
     **/

    public void removeAllCachedResponses() {

        if (httpResponseCache == null) {
            httpResponseCache = HttpResponseCache.getInstalled();
        }
        // Uninstalls the cache and deletes all of its stored contents.
        try {
            httpResponseCache.delete();
        } catch (IOException e) {
            Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
        }

    }

    /**
     * @Signature: removeCachedResponseForRequest:
     * @Declaration : - (void)removeCachedResponseForRequest:(NSURLRequest *)request
     * @Description : Removes the cached URL response for a specified URL request.
     * @param request The URL request whose cached URL response should be removed. If there is no corresponding cached URL response, no
     *            action is taken.
     **/

    public void removeCachedResponseForRequest(NSURLRequest request) {
    }

    // Getting and Setting On-disk Cache Properties
    /**
     * @Signature: currentDiskUsage
     * @Declaration : - (NSUInteger)currentDiskUsage
     * @Description : Returns the current size of the receiver’s on-disk cache, in bytes.
     * @return Return Value The current size of the receiver’s on-disk cache, in bytes.
     **/

    public long currentDiskUsage() {
        return httpResponseCache.size();
    }

    /**
     * @Signature: diskCapacity
     * @Declaration : - (NSUInteger)diskCapacity
     * @Description : Returns the capacity of the receiver’s on-disk cache, in bytes.
     * @return Return Value The capacity of the receiver’s on-disk cache, in bytes.
     **/

    public long diskCapacity() {
        return httpResponseCache.maxSize();
    }

    /**
     * @Signature: setDiskCapacity:
     * @Declaration : - (void)setDiskCapacity:(NSUInteger)diskCapacity
     * @Description : Sets the receiver’s on-disk cache capacity
     * @param diskCapacity The new on-disk cache capacity, in bytes. The on-disk cache will truncate its contents to diskCapacity, if
     *            necessary.
     **/

    public void setDiskCapacity(long diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    // Getting and Setting In-memory Cache Properties
    /**
     * @Signature: currentMemoryUsage
     * @Declaration : - (NSUInteger)currentMemoryUsage
     * @Description : Returns the current size of the receiver’s in-memory cache, in bytes.
     * @return Return Value The current size of the receiver’s in-memory cache, in bytes.
     **/

    public long currentMemoryUsage() {
        // Not implemented
        return httpResponseCache.size();
    }

    /**
     * @Signature: memoryCapacity
     * @Declaration : - (NSUInteger)memoryCapacity
     * @Description : Returns the capacity of the receiver’s in-memory cache, in bytes.
     * @return Return Value The capacity of the receiver’s in-memory cache, in bytes.
     **/

    public long memoryCapacity() {
        return memoryCapacity;
    }

    /**
     * @Signature: setMemoryCapacity:
     * @Declaration : - (void)setMemoryCapacity:(NSUInteger)memoryCapacity
     * @Description : Sets the receiver’s in-memory cache capacity.
     * @param memoryCapacity The new in-memory cache capacity, in bytes. The in-memory cache will truncate its contents to memoryCapacity,
     *            if necessary.
     **/

    public void setMemoryCapacity(long memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

}