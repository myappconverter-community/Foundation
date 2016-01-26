
package com.myappconverter.java.foundations;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.mapping.utils.InstanceTypeFactory;


public class NSMutableDictionary<K, V> extends NSDictionary<K, V> implements Serializable{

	public NSMutableDictionary(int i) {
		super(i);
	}

	public NSMutableDictionary() {
		super();
	}

	// Creating and Initializing a Mutable Dictionary
	/**
	 * @Signature: dictionaryWithCapacity:
	 * @Declaration : + (instancetype)dictionaryWithCapacity:(NSUInteger)numItems
	 * @Description : Creates and returns a mutable dictionary, initially giving it enough allocated memory to hold a given number of
	 *              entries.
	 * @param numItems The initial capacity of the new dictionary.
	 * @return Return Value A new mutable dictionary with enough allocated memory to hold numItems entries.
	 * @Discussion Mutable dictionaries allocate additional memory as needed, so numItems simply establishes the object’s initial capacity.
	 **/
	
	public static <K, V> NSMutableDictionary<K, V> dictionaryWithCapacity(Class clazz, int numItems) {
		NSMutableDictionary<K, V> mDict = (NSMutableDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		mDict.wrappedDictionary = new HashMap<K, V>(numItems);
		return mDict;
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (instancetype)initWithCapacity:(NSUInteger)numItems
	 * @Description : Initializes a newly allocated mutable dictionary, allocating enough memory to hold numItems entries.
	 * @param numItems The initial capacity of the initialized dictionary.
	 * @return Return Value An initialized mutable dictionary, which might be different than the original receiver.
	 * @Discussion Mutable dictionaries allocate additional memory as needed, so numItems simply establishes the object’s initial capacity.
	 *             This method is a designated initializer of NSMutableDictionary.
	 **/
	
	public NSMutableDictionary<K, V> initWithCapacity(int numItems) {
		this.wrappedDictionary = new HashMap<K, V>(numItems);
		return this;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated mutable dictionary.
	 * @return Return Value A mutable dictionary.
	 * @Discussion This method is a designated initializer of NSMutableDictionary.
	 **/
	@Override
	
	public NSMutableDictionary<K, V> init() {
		wrappedDictionary = new HashMap<K, V>();
		return this;
	}

	/**
	 * @Signature: dictionaryWithSharedKeySet:
	 * @Declaration : + (id)dictionaryWithSharedKeySet:(id)keyset
	 * @Description : Creates a mutable dictionary which is optimized for dealing with a known set of keys.
	 * @param keyset The keyset, created by the NSDictionary class method sharedKeySetForKeys:. If keyset is nil, an exception is thrown. If
	 *            keyset is not an object returned by sharedKeySetForKeys:, an exception is thrown.
	 * @return Return Value A new mutable dictionary optimized for a known set of keys.
	 * @Discussion Keys that are not in the key set can still be set in the dictionary, but that usage is not optimal.
	 **/
	
	
	public static Object dictionaryWithSharedKeySet(NSSharedKeySet keyset) {
		NSMutableDictionary<Object, Object> nsd = dictionaryWithCapacity(NSMutableDictionary.class, keyset.getSharedKeySet().size());
		if (keyset == null) {
			throw new IllegalArgumentException("NullArgumentException :: keyset is null.");
		}
		if (keyset instanceof NSSharedKeySet) {
			Iterator<String> iterator = keyset.getSharedKeySet().iterator();
			while (iterator.hasNext()) {
				nsd.wrappedDictionary.put(new NSString(iterator.next()), new NSString());
			}
		} else {
			throw new IllegalArgumentException("TypeException :: keyset is not NSSharedKeySet");
		}

		return nsd;
	}

	// Adding Entries to a Mutable Dictionary

	/**
	 * @Signature: setObject:forKey:
	 * @Declaration : - (void)setObject:(id)anObject forKey:(id < NSCopying >)aKey
	 * @Description : Adds a given key-value pair to the dictionary.
	 * @param anObject The value for aKey. A strong reference to the object is maintained by the dictionary. Raises an
	 *            NSInvalidArgumentException if anObject is nil. If you need to represent a nil value in the dictionary, use NSNull.
	 * @param aKey The key for value. The key is copied (using copyWithZone:; keys must conform to the NSCopying protocol). Raises an
	 *            NSInvalidArgumentException if aKey is nil. If aKey already exists in the dictionary anObject takes its place.
	 **/
	
	public void setObjectForKey(V anObject, K aKey) {
		if (anObject == null)
			throw new IllegalArgumentException("Attempt to insert null object into an " + getClass().getName() + ".");
		if (aKey == null)
			throw new IllegalArgumentException("Attempt to insert null key into an " + getClass().getName() + ".");
		wrappedDictionary.put(aKey, anObject);
	}

	/**
	 * @Signature: setObject:forKeyedSubscript:
	 * @Declaration : - (void)setObject:(id)object forKeyedSubscript:(id < NSCopying >)aKey
	 * @Description : Adds a given key-value pair to the dictionary.
	 * @param object The value for aKey. A strong reference to the object is maintained by the dictionary. Raises an
	 *            NSInvalidArgumentException if anObject is nil. If you need to represent a nil value in the dictionary, use NSNull.
	 * @param aKey The key for value. The key is copied (using copyWithZone:; keys must conform to the NSCopying protocol). Raises an
	 *            NSInvalidArgumentException if aKey is nil. If aKey already exists in the dictionary anObject takes its place.
	 * @Discussion This method is identical to setObject:forKey:.
	 **/
	
	public void setObjectForKeyedSubscript(V object, K aKey) {
		setObjectForKey(object, aKey);
	}

	/**
	 * @Signature: setValue:forKey:
	 * @Declaration : - (void)setValue:(id)value forKey:(NSString *)key
	 * @Description : Adds a given key-value pair to the dictionary.
	 * @param value The value for key.
	 * @param key The key for value. Note that when using key-value coding, the key must be a string (see “Key-Value Coding Fundamentals).
	 * @Discussion This method adds value and key to the dictionary using setObject:forKey:, unless value is nil in which case the method
	 *             instead attempts to remove key using removeObjectForKey:.
	 **/
	
	public void setValueForKey(V value, K key) {
		setObjectForKey(value, key);
	}

	/**
	 * @Signature: addEntriesFromDictionary:
	 * @Declaration : - (void)addEntriesFromDictionary:(NSDictionary *)otherDictionary
	 * @Description : Adds to the receiving dictionary the entries from another dictionary.
	 * @param otherDictionary The dictionary from which to add entries
	 * @Discussion Each value object from otherDictionary is sent a retain message before being added to the receiving dictionary. In
	 *             contrast, each key object is copied (using copyWithZone:—keys must conform to the NSCopying protocol), and the copy is
	 *             added to the receiving dictionary. If both dictionaries contain the same key, the receiving dictionary’s previous value
	 *             object for that key is sent a release message, and the new value object takes its place.
	 **/
	
	public void addEntriesFromDictionary(NSDictionary<? extends K, ? extends V> otherDictionary) {
		if (otherDictionary.wrappedDictionary.containsKey(null) || otherDictionary.wrappedDictionary.containsValue(null))
			throw new IllegalArgumentException("Key or value may not be null");

		wrappedDictionary.putAll(otherDictionary.wrappedDictionary);
	}

	/**
	 * @Signature: setDictionary:
	 * @Declaration : - (void)setDictionary:(NSDictionary *)otherDictionary
	 * @Description : Sets the contents of the receiving dictionary to entries in a given dictionary.
	 * @param otherDictionary A dictionary containing the new entries.
	 * @Discussion All entries are removed from the receiving dictionary (with removeAllObjects), then each entry from otherDictionary added
	 *             into the receiving dictionary.
	 **/
	
	public void setDictionary(NSDictionary<? extends K, ? extends V> otherDictionary) {
		if (otherDictionary != this) {
			wrappedDictionary.clear();
			wrappedDictionary.putAll(otherDictionary.getWrappedDictionary());
		}

	}

	// Removing Entries From a Mutable Dictionary
	/**
	 * @Signature: removeObjectForKey:
	 * @Declaration : - (void)removeObjectForKey:(id)aKey
	 * @Description : Removes a given key and its associated value from the dictionary.
	 * @param aKey The key to remove.
	 * @Discussion Does nothing if aKey does not exist. For example, assume you have an archived dictionary that records the call letters
	 *             and associated frequencies of radio stations. To remove an entry for a defunct station, you could write code similar to
	 *             the following: NSMutableDictionary *stations = nil; stations = [[NSMutableDictionary alloc] initWithContentsOfFile:
	 *             pathToArchive]; [stations removeObjectForKey:@"KIKT"]; Important: Raises an NSInvalidArgumentException if aKey is nil.
	 **/
	
	public void removeObjectForKey(Object key) {
		wrappedDictionary.remove(key);
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Empties the dictionary of its entries.
	 * @Discussion Each key and corresponding value object is sent a release message.
	 **/
	
	public void removeAllObjects() {
		wrappedDictionary.clear();
	}

	/**
	 * @Signature: removeObjectsForKeys:
	 * @Declaration : - (void)removeObjectsForKeys:(NSArray *)keyArray
	 * @Description : Removes from the dictionary entries specified by elements in a given array.
	 * @param keyArray An array of objects specifying the keys to remove.
	 * @Discussion If a key in keyArray does not exist, the entry is ignored.
	 **/
	
	public void removeObjectsForKeys(NSArray<? extends NSObject> keyArray) {
		if (keyArray == null || keyArray.getWrappedList() == null)
			return;
		for (int i = 0; i < keyArray.getWrappedList().size(); i++) {
			removeObjectForKey(keyArray.getWrappedList().get(i));
		}
	}

	/*** KV ***/

	public NSMutableDictionary(V object, K key) {
		super(object, key);
	}

	public NSMutableDictionary(V[] objects, K[] keys) {
		super(objects, keys);
	}

	public NSMutableDictionary(Map<K, V> map) {
		super(map);
	}

	public NSMutableDictionary(NSArray<V> objects, NSArray<K> keys) {
		super(objects, keys);
	}

	public NSMutableDictionary(NSDictionary<K, V> otherDictionary) {
		super(otherDictionary);
	}

	@Override
	protected Map<K, V> _initializeWithCapacity(int capacity) {
		return _setMap(new HashMap<K, V>(capacity));
	}

	@Override
	public int _shallowHashCode() {
		return NSMutableDictionary.class.hashCode();
	}

	@Override
	public NSDictionary<K, V> immutableClone() {
		return new NSDictionary<K, V>(this);
	}

	@Override
	
	public void takeValueForKey(Object value, String key) {
		if (value != null)
			wrappedDictionary.put((K) key, (V) value);
		else
			wrappedDictionary.remove(key);
	}

	@Override
	public void clear() {
		removeAllObjects();
	}

	@Override
	public NSMutableDictionary<K, V> clone() {
		return mutableClone();
	}

	@Override
	public boolean isEmpty() {
		return wrappedDictionary.isEmpty();
	}

	// Overidded Methods

//	public static <K, V> NSMutableDictionary<K, V> dictionary() {
//		NSMutableDictionary<K, V> anOtherDic = new NSMutableDictionary<K, V>();
//		anOtherDic.wrappedDictionary = new HashMap<K, V>();
//		return anOtherDic;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithDictionary(NSDictionary<K, V> otherDictionary) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		nsd.setWrappedDictionary(new HashMap<K, V>(otherDictionary.wrappedDictionary));
//		return nsd;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithObjectForKey(V anObject, K aKey) {
//		if (((NSObject) aKey).conformsToProtocol(NSCopying.class)) {
//			NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//			nsd.wrappedDictionary.put(aKey, anObject);
//			return nsd;
//		}
//		return null;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithObjectsForKeys(NSArray<V> objects, NSArray<K> keys) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		// if (keys.count() != objects.count()) {
//		// // Should not raise exception
//		// // throw new
//		// IllegalArgumentException("NSInvalidArgumentException :: Array lengths do not match.");
//		// }
//		for (int i = 0; i < keys.count(); i++) {
//			nsd.wrappedDictionary.put(keys.objectAtIndex(i), objects.objectAtIndex(i));
//		}
//		return nsd;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithObjectsForKeysCount(V[] objects, K[] keys, int count) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		// if (keys.length != objects.length) {
//		// throw new
//		// IllegalArgumentException("NSInvalidArgumentException :: Array lengths do not match.");
//		// }
//		for (int i = 0; i < count; i++) {
//			if (((NSObject) keys[i]).conformsToProtocol(NSCopying.class))
//				nsd.wrappedDictionary.put(keys[i], objects[i]);
//		}
//		return nsd;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithObjectsAndKeys(K... firstObject) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		if (firstObject.length < 2 || firstObject.length % 2 - 1 != 0) {
//			// throw new
//			// IllegalArgumentException("NSInvalidArgumentException :: Array lengths do not match.");
//		} else {
//			for (int i = 0; i < firstObject.length - 1; i = i + 2) {
//				nsd.wrappedDictionary.put(firstObject[i + 1], (V) firstObject[i]);
//			}
//		}
//		return nsd;
//	}
//
//	public static <K, V> NSMutableDictionary<K, V> dictionaryWithObjectsAndKeys(Class<?> clazz, K... firstObject) {
//		return (NSMutableDictionary<K, V>) NSDictionary.dictionaryWithObjectsAndKeys(clazz, firstObject);
//	}
//
//
//	@Override
//	public NSMutableDictionary<K, V> initWithDictionary(NSDictionary<K, V> otherDictionary) {
//		return new NSMutableDictionary<K, V>(otherDictionary.getWrappedDictionary());
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithDictionary(Class clazz, NSDictionary<K, V> otherDictionary) {
//		// return new NSMutableDictionary<K, V>(
//		// otherDictionary.getWrappedDictionary());
//		return (NSMutableDictionary<K, V>) super.initWithDictionary(clazz, otherDictionary);
//
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithDictionaryCopyItems(NSDictionary<K, V> otherDictionary, boolean flag) {
//		if (flag) {
//			NSMutableDictionary<K, V> ds = new NSMutableDictionary<K, V>();
//			ds.wrappedDictionary = new HashMap<K, V>(otherDictionary.wrappedDictionary);
//			return ds;
//
//		}
//		return new NSMutableDictionary<K, V>(otherDictionary.getWrappedDictionary());
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithDictionaryCopyItems(NSDictionary<K, V> otherDictionary, boolean flag) {
//		return (NSMutableDictionary<K, V>) super.initWithDictionaryCopyItems(otherDictionary, flag);
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithObjectsForKeys(NSArray<V> objects, NSArray<K> keys) {
//		return (NSMutableDictionary<K, V>) super.initWithObjectsForKeys(objects, keys);
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithObjectsForKeysCount(V[] objects, K[] keys, int count) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		if (keys.length != objects.length) {
//			throw new IllegalArgumentException("NSInvalidArgumentException :: Array lengths do not match.");
//		}
//		for (int i = 0; i < count; i++) {
//			nsd = new NSMutableDictionary<K, V>();
//			nsd.wrappedDictionary.put(keys[i], objects[i]);
//		}
//		return nsd;
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithObjectsForKeysCount(V[] objects, K[] keys, int count) {
//		return (NSMutableDictionary<K, V>) super.initWithObjectsForKeysCount(objects, keys, count);
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithObjectsAndKeys(Object... firstObject) {
//		NSMutableDictionary<K, V> nsd = new NSMutableDictionary<K, V>();
//		// if (firstObject.length < 2 || firstObject.length % 2 - 1 != 0) {
//		// throw new IllegalArgumentException("NSInvalidArgumentException :: Array lengths do not match.");
//		// } else {
//		for (int i = 0; i < firstObject.length - 1; i = i + 2) {
//			nsd.wrappedDictionary.put((K) firstObject[i + 1], (V) firstObject[i]);
//		}
//		// }
//		return nsd;
//	}
//
//	@Override
//	public NSMutableDictionary<K, V> initWithObjectsAndKeys(Class clazz, Object... firstObject) {
//		return (NSMutableDictionary<K, V>) super.initWithObjectsAndKeys(clazz, firstObject);
//	}

	@Override
	public boolean isKindOfClass(Class<?> aClass) {
		boolean isKindOf = super.isKindOfClass(aClass);
		return isKindOf;
	}

}