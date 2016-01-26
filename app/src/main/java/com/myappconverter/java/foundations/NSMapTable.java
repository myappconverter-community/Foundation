
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSPointerFunctions.NSPointerFunctionsOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;



public class NSMapTable<K, V> extends NSObject implements NSCoding, NSCopying, NSFastEnumeration, Iterable<K>, Iterator<K> {

	private Map<K, V> wrappedMap;
	private NSPointerFunctions keyPointerFunctions;
	private NSPointerFunctions valuePointerFunctions;

	// Creating and Initializing a Map Table

	public NSMapTable() {
		wrappedMap = new Hashtable<K, V>();
	}

	/**
	 * @Signature: initWithKeyOptions:valueOptions:capacity:
	 * @Declaration : - (id)initWithKeyOptions:(NSPointerFunctionsOptions)keys valueOptions:(NSPointerFunctionsOptions)values
	 *              capacity:(NSUInteger)capacity
	 * @Description : Returns a map table, initialized with the given options.
	 * @param keys A bit field that specifies the options for the keys in the map table. For possible values, see “NSMapTableOptions.
	 * @param values A bit field that specifies the options for the values in the map table. For possible values, see “NSMapTableOptions.
	 * @param capacity The initial capacity of the map table. This is just a hint; the map table may subsequently grow and shrink as
	 *            required.
	 * @return Return Value A map table initialized using the given options.
	 * @Discussion values must contain entries at all the indexes specified in keys.
	 **/
	
	public NSMapTable initWithKeyOptionsValueOptionsCapacity(NSPointerFunctionsOptions keys, NSPointerFunctionsOptions values, long capacity) {
		NSMapTable nsMapTable = new NSMapTable();
		nsMapTable.wrappedMap = new HashMap<Object, Object>((int) capacity);
		return nsMapTable;
	}

	/**
	 * @Signature: mapTableWithKeyOptions:valueOptions:
	 * @Declaration : + (id)mapTableWithKeyOptions:(NSPointerFunctionsOptions)keyOptions
	 *              valueOptions:(NSPointerFunctionsOptions)valueOptions
	 * @Description : Returns a new map table, initialized with the given options
	 * @param keyOptions A bit field that specifies the options for the keys in the map table. For possible values, see “NSMapTableOptions.
	 * @param valueOptions A bit field that specifies the options for the values in the map table. For possible values, see
	 *            “NSMapTableOptions.
	 * @return Return Value A new map table, initialized with the given options.
	 **/
	
	public static Object mapTableWithKeyOptionsValueOptions(NSPointerFunctionsOptions keyOptions, NSPointerFunctionsOptions valueOptions) {
		return new NSMapTable();
	}

	/**
	 * @Signature: initWithKeyPointerFunctions:valuePointerFunctions:capacity:
	 * @Declaration : - (id)initWithKeyPointerFunctions:(NSPointerFunctions *)keyFunctions valuePointerFunctions:(NSPointerFunctions
	 *              *)valueFunctions capacity:(NSUInteger)initialCapacity
	 * @Description : Returns a map table, initialized with the given functions.
	 * @param keyFunctions The functions the map table uses to manage keys.
	 * @param valueFunctions The functions the map table uses to manage values.
	 * @param initialCapacity The initial capacity of the map table. This is just a hint; the map table may subsequently grow and shrink as
	 *            required.
	 * @return Return Value A map table, initialized with the given functions.
	 **/
	
	public static Object initWithKeyPointerFunctions(NSPointerFunctionsOptions keyFunctions, NSPointerFunctionsOptions valueFunctions,
			long initialCapacity) {
		Map aMap = new Hashtable<Object, Object>((int) initialCapacity);
		NSMapTable mapTable = new NSMapTable();
		mapTable.wrappedMap = aMap;
		return mapTable;
	}

	/**
	 * @Signature: strongToStrongObjectsMapTable
	 * @Declaration : + (id)strongToStrongObjectsMapTable
	 * @Description : Returns a new map table object which has strong references to the keys and values.
	 * @return Return Value A new map table object which has strong references to the keys and values.
	 **/
	
	public static Object strongToStrongObjectsMapTable() {
		return new NSMapTable();
	}

	/**
	 * @Signature: weakToStrongObjectsMapTable
	 * @Declaration : + (id)weakToStrongObjectsMapTable
	 * @Description : Returns a new map table object which has weak references to the keys and strong references to the values.
	 * @return Return Value A new map table object which has weak references to the keys and strong references to the values.
	 **/
	
	public static Object weakToStrongObjectsMapTable() {
		return new NSMapTable();
	}

	/**
	 * @Signature: strongToWeakObjectsMapTable
	 * @Declaration : + (id)strongToWeakObjectsMapTable
	 * @Description : Returns a new map table object which has strong references to the keys and weak references to the values.
	 * @return Return Value A new map table object which has strong references to the keys and weak references to the values.
	 **/
	
	public static Object strongToWeakObjectsMapTable() {
		return new NSMapTable();
	}

	/**
	 * @Signature: weakToWeakObjectsMapTable
	 * @Declaration : + (id)weakToWeakObjectsMapTable
	 * @Description : Returns a new map table object which has weak references to the keys and values.
	 * @return Return Value A new map table object which has weak references to the keys and values.
	 **/
	
	public Object weakToWeakObjectsMapTable() {
		return new NSMapTable();
	}

	// Accessing Content
	/**
	 * @Signature: objectForKey:
	 * @Declaration : - (id)objectForKey:(id)aKey
	 * @Description : Returns a the value associated with a given key.
	 * @param aKey The key for which to return the corresponding value.
	 * @return Return Value The value associated with aKey, or nil if no value is associated with aKey.
	 **/
	
	public NSObject objectForKey(Object aKey) {
		return (NSObject) wrappedMap.get(aKey);

	}

	/**
	 * @Signature: keyEnumerator
	 * @Declaration : - (NSEnumerator *)keyEnumerator
	 * @Description : Returns an enumerator object that lets you access each key in the map table.
	 * @return Return Value An enumerator object that lets you access each key in the map table.
	 * @Discussion The following code fragment illustrates how you might use the method. NSEnumerator *enumerator = [myMapTable
	 *             keyEnumerator]; id value; while ((value = [enumerator nextObject])) code that acts on the map table's keys
	 **/
	
	public NSEnumerator <K> keyEnumerator() {
		return (NSEnumerator<K>) wrappedMap.keySet().iterator();
	}

	/**
	 * @Signature: objectEnumerator
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each value in the map table.
	 * @return Return Value An enumerator object that lets you access each value in the map table.
	 * @Discussion The following code fragment illustrates how you might use the method. NSEnumerator *enumerator = [myMapTable
	 *             objectEnumerator]; id value; while ((value = [enumerator nextObject])) { code that acts on the map table's values }
	 **/
	
	public NSEnumerator<V> objectEnumerator() {
		return (NSEnumerator<V>) wrappedMap.values().iterator();
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of key-value pairs in the map table.
	 * @return Return Value The number of key-value pairs in the map table.
	 **/
	
	public long count() {
		return wrappedMap.size();
	}

	// Manipulating Content
	/**
	 * @Signature: setObject:forKey:
	 * @Declaration : - (void)setObject:(id)anObject forKey:(id)aKey
	 * @Description : Adds a given key-value pair to the map table.
	 * @param anObject The value for aKey. This value must not be nil.
	 * @param aKey The key for anObject. This value must not be nil.
	 **/
	
	public void setObjectForKey(V anObject, K aKey) {
		wrappedMap.put(aKey, anObject);
	}

	/**
	 * @Signature: removeObjectForKey:
	 * @Declaration : - (void)removeObjectForKey:(id)aKey
	 * @Description : Removes a given key and its associated value from the map table.
	 * @param aKey The key to remove.
	 * @Discussion Does nothing if aKey does not exist.
	 **/
	
	public void removeObjectForKey(K aKey) {
		wrappedMap.remove(aKey);
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Empties the map table of its entries.
	 **/
	
	public void removeAllObjects() {
		wrappedMap.clear();
	}

	// Creating a Dictionary Representation
	/**
	 * @Signature: dictionaryRepresentation
	 * @Declaration : - (NSDictionary *)dictionaryRepresentation
	 * @Description : Returns a dictionary representation of the map table.
	 * @return Return Value A dictionary representation of the map table.
	 * @Discussion The map table’s values and keys must conform to all the requirements specified in setObject:forKey: in
	 *             NSMutableDictionary.
	 **/
	
	public Map<K,V> dictionaryRepresentation() {
		return (Map<K, V>) wrappedMap.entrySet();

	}

	// Accessing Pointer Functions
	/**
	 * @Signature: keyPointerFunctions
	 * @Declaration : - (NSPointerFunctions *)keyPointerFunctions
	 * @Description : Returns the pointer functions the map table uses to manage keys.
	 * @return Return Value The pointer functions the map table uses to manage keys.
	 **/
	
	public NSPointerFunctions keyPointerFunctions() {
		return keyPointerFunctions;
	}

	/**
	 * @Signature: valuePointerFunctions
	 * @Declaration : - (NSPointerFunctions *)valuePointerFunctions
	 * @Description : Returns the pointer functions the map table uses to manage values.
	 * @return Return Value The pointer functions the map table uses to manage values.
	 **/
	
	public NSPointerFunctions valuePointerFunctions() {
		return valuePointerFunctions;
	}

	@Override
	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state, Object[] stackbuf, int len) {
		return 0;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public boolean hasNext() {
		return wrappedMap.keySet().iterator().hasNext();
	}

	@Override
	public K next() {
		return wrappedMap.keySet().iterator().next();
	}

	@Override
	public void remove() {
		wrappedMap.keySet().iterator().remove();

	}

	@Override
	public Iterator<K> iterator() {
		return wrappedMap.keySet().iterator();
	}
}