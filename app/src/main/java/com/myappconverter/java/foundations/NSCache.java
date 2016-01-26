
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSCacheDelegate;
import com.myappconverter.java.foundations.utilities.MyAppException;

import android.util.Log;
import android.util.LruCache;


public class NSCache extends NSObject {

	LruCache<NSObject, NSObject> wrappedLruCache;

	public LruCache<NSObject, NSObject> getWrappedLruCache() {
		return wrappedLruCache;
	}

	public void setWrappedLruCache(LruCache<NSObject, NSObject> mCache) {
		this.wrappedLruCache = mCache;
	}

	boolean evictsObjectsWithDiscardedContent;
	private NSCacheDelegate nsCacheDelegate;
	private NSString nameCache;
	int countLimit;
	int _totalCostLimit;

	public int getCountLimit() {
		return countLimit;
	}

	private int nbreEntry;

	public int getNbreEntry() {
		return nbreEntry;
	}

	public void setNbreEntry(int nbreEntry) {
		this.nbreEntry = nbreEntry;
	}

	public NSCache(int costLimit) {
		this.setTotalCostLimit(costLimit);
		wrappedLruCache = new LruCache<NSObject, NSObject>(costLimit);
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name of the cache.
	 * @return Return Value The name of the cache.
	 * @Discussion Returns the empty string if no name is specified.
	 **/
	
	public NSString name() {
		if (nameCache == null) {
			return new NSString("");
		} else {
			return this.nameCache;
		}
	}

	public NSString getName() {
		if (nameCache == null) {
			return new NSString("");
		} else {
			return this.nameCache;
		}
	}

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)cacheName
	 * @Description : Sets the cache’s name attribute to a specific string.
	 * @param cacheName The new name for the cache.
	 **/
	
	public void setName(NSString name) {
		this.nameCache = name;

	}

	/**
	 * @Signature: objectForKey:
	 * @Declaration : - (id)objectForKey:(id)key
	 * @Description : Returns the value associated with a given key.
	 * @param key An object identifying the value.
	 * @return Return Value The value associated with key, or nil if no value is associated with key.
	 **/
	
	public NSObject objectForKey(NSObject key) {
		return wrappedLruCache.get(key);

	}

	/**
	 * @Signature: setObject:forKey:
	 * @Declaration : - (void)setObject:(id)obj forKey:(id)key
	 * @Description : Sets the value of the specified key in the cache.
	 * @param obj The object to be stored in the cache.
	 * @param key The key with which to associate the value.
	 * @Discussion Unlike an NSMutableDictionary object, a cache does not copy the key objects that are put into it.
	 **/
	
	public void setObjectForKey(NSObject obj, NSObject keyforKey) {
		if (wrappedLruCache.size() >= getCountLimit()) {
			try {
				throw new MyAppException("can't exceed count limit");
			} catch (Exception e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		} else {
			wrappedLruCache.put(keyforKey, obj);
		}

	}

	/**
	 * @Signature: setObject:forKey:cost:
	 * @Declaration : - (void)setObject:(id)obj forKey:(id)key cost:(NSUInteger)num
	 * @Description : Sets the value of the specified key in the cache, and associates the key-value pair with the specified cost.
	 * @param obj The object to store in the cache.
	 * @param key The key with which to associate the value.
	 * @param num The cost with which to associate the key-value pair.
	 * @Discussion The cost value is used to compute a sum encompassing the costs of all the objects in the cache. When memory is limited or
	 *             when the total cost of the cache eclipses the maximum allowed total cost, the cache could begin an eviction process to
	 *             remove some of its elements. However, this eviction process is not in a guaranteed order. As a consequence, if you try to
	 *             manipulate the cost values to achieve some specific behavior, the consequences could be detrimental to your program.
	 *             Typically, the obvious cost is the size of the value in bytes. If that information is not readily available, you should
	 *             not go through the trouble of trying to compute it, as doing so will drive up the cost of using the cache. Pass in 0 for
	 *             the cost value if you otherwise have nothing useful to pass, or simply use the setObject:forKey: method, which does not
	 *             require a cost value to be passed in. Unlike an NSMutableDictionary object, a cache does not copy the key objects that
	 *             are put into it.
	 **/
	
	public void setObjectForKeyCost(NSObject obj, NSObject key, int num) {
		// TODO modify cost entry is not available in LruCache(set value and its key with value constant 1)
		if (wrappedLruCache.size() >= getCountLimit()) {
			try {
				throw new MyAppException("can't exceed count limit");
			} catch (Exception e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		} else {
			wrappedLruCache.put(key, obj);
		}

	}

	/**
	 * @Signature: removeObjectForKey:
	 * @Declaration : - (void)removeObjectForKey:(id)key
	 * @Description : Removes the value of the specified key in the cache.
	 * @param key The key identifying the value to be removed.
	 **/
	
	public void removeObjectForKey(NSObject key) {
		wrappedLruCache.remove(key);
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Empties the cache.
	 **/
	
	public void removeAllObjects() {
		wrappedLruCache.evictAll();
	}

	// Managing Cache Size

	/**
	 * @Signature: countLimit
	 * @Declaration : - (NSUInteger)countLimit
	 * @Description : Returns the maximum number of objects that the cache can currently hold.
	 * @return Return Value The maximum number of objects that the cache can currently hold.
	 * @Discussion By default, countLimit will be set to 0. Any countLimit less than or equal to 0 has no effect on the number of allowed
	 *             entries in the cache. This limit is not a strict limit, and if the cache goes over the limit, an object in the cache
	 *             could be evicted instantly, later, or possibly never, all depending on the implementation details of the cache.
	 **/
	
	public int countLimit() {
		return this.countLimit;
	}

	/**
	 * @Signature: setCountLimit:
	 * @Declaration : - (void)setCountLimit:(NSUInteger)lim
	 * @Description : Sets the maximum number of objects that the cache can hold.
	 * @param lim The maximum number of objects that the cache will be allowed to hold.
	 * @Discussion Setting the count limit to a number less than or equal to 0 will have no effect on the maximum size of the cache.
	 **/
	
	public void setCountLimit(int countLimit) {
		this.countLimit = countLimit;
	}

	/**
	 * @Signature: totalCostLimit
	 * @Declaration : - (NSUInteger)totalCostLimit
	 * @Description : Returns the maximum total cost that the cache can have before it starts evicting objects.
	 * @return Return Value The current maximum cost that the cache can have before it starts evicting objects.
	 * @Discussion The default value is 0, which means there is no limit on the size of the cache. If you add an object to the cache, you
	 *             may pass in a specified cost for the object, such as the size in bytes of the object. If adding this object to the cache
	 *             causes the cache’s total cost to rise above totalCostLimit, the cache could automatically evict some of its objects until
	 *             its total cost falls below totalCostLimit. The order in which the cache evicts objects is not guaranteed. This limit is
	 *             not a strict limit, and if the cache goes over the limit, an object in the cache could be evicted instantly, at a later
	 *             point in time, or possibly never, all depending on the implementation details of the cache.
	 **/
	
	public int totalCostLimit() {
		return this._totalCostLimit;

	}

	public int getTotalCostLimit() {
		return this._totalCostLimit;
	}

	/**
	 * @Signature: setTotalCostLimit:
	 * @Declaration : - (void)setTotalCostLimit:(NSUInteger)lim
	 * @Description : Sets the maximum total cost that the cache can have before it starts evicting objects.
	 * @param lim The maximum total cost that the cache can have before it starts evicting objects.
	 **/
	
	public void setTotalCostLimit(int costLimit) {
		this._totalCostLimit = costLimit;
	}

	/**
	 * @Signature: evictsObjectsWithDiscardedContent
	 * @Declaration : - (BOOL)evictsObjectsWithDiscardedContent
	 * @Description : Returns whether or not the cache will automatically evict discardable-content objects whose content has been
	 *              discarded.
	 * @return Return Value YES if the cache will evict the object after it is discarded; otherwise, NO.
	 * @Discussion By default, evictsObjectsWithDiscardedContent is set to YES.
	 **/
	
	public boolean evictsObjectsWithDiscardedContent() {
		return this.evictsObjectsWithDiscardedContent;
	}

	public boolean getEvictsObjectsWithDiscardedContent() {
		return this.evictsObjectsWithDiscardedContent;
	}

	/**
	 * @Signature: evictsObjectsWithDiscardedContent:
	 * @Declaration : - (void)evictsObjectsWithDiscardedContent:(BOOL)b
	 * @Description : Sets whether the cache will automatically evict NSDiscardableContent objects after the object’s content has been
	 *              discarded.
	 * @param b If YES, the cache evicts NSDiscardableContent objects after the object’s contents has been discarded; if NO the cache does
	 *            not evict these objects.
	 **/
	
	public void setEvictsObjectsWithDiscardedContent(boolean b) {
		this.evictsObjectsWithDiscardedContent = b;
	}

	/**
	 * @Signature: delegate
	 * @Declaration : - (<NSCacheDelegate>)delegate
	 * @Description : Returns the cache’s delegate.
	 * @return Return Value The application delegate object.
	 * @Discussion The delegate object is expected to conform to the NSCacheDelegate protocol.
	 **/
	
	public NSCacheDelegate delegate() {
		return this.nsCacheDelegate;
	}

	public NSCacheDelegate getDdelegate() {
		return this.nsCacheDelegate;
	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(<NSCacheDelegate>)del
	 * @Description : Makes the given object the cache’s delegate.
	 * @param del The object to be registered as the delegate.
	 * @Discussion The delegate object is expected to conform to the NSCacheDelegate protocol.
	 **/
	
	public void setDelegate(NSCacheDelegate nsCacheDelegate) {
		this.nsCacheDelegate = nsCacheDelegate;
	}

}