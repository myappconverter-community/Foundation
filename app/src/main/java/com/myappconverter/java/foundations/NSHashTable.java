
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSPointerFunctions.NSPointerFunctionsOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class NSHashTable extends NSObject
		implements NSCoding, NSCopying, NSFastEnumeration, Iterable, Iterator {

	Map mHashTable;
	int capacity;
	NSPointerFunctionsOptions mPointerFunctions;
	NSPointerFunctions mPointerFunction = new NSPointerFunctions(null);

	WeakReference<Hashtable<Object, NSString>> mWeakref;

	// Initialization
	/**
	 * @Signature: initWithOptions:capacity:
	 * @Declaration : - (id)initWithOptions:(NSPointerFunctionsOptions)options capacity:(NSUInteger)capacity
	 * @Description : Returns a hash table initialized with the given attributes.
	 * @param options A bit field that specifies the options for the elements in the hash table. For possible values, see “Hash Table
	 *            Options.
	 * @param capacity The initial number of elements the hash table can hold.
	 * @return Return Value A hash table initialized with options specified by options and initial capacity of capacity.
	 **/
	
	public Object initWithOptionsCapacity(NSPointerFunctionsOptions options, int capacity) {
		// TODO check that
		NSHashTable hashTable = new NSHashTable();
		hashTable.mPointerFunctions = options;
		hashTable.mHashTable = new Hashtable(capacity);
		return hashTable;
	}

	/**
	 * @Signature: initWithPointerFunctions:capacity:
	 * @Declaration : - (id)initWithPointerFunctions:(NSPointerFunctions *)functions capacity:(NSUInteger)initialCapacity
	 * @Description : Returns a hash table initialized with the given functions and capacity.
	 * @param functions The pointer functions for the new hash table.
	 * @param initialCapacity The initial capacity of the hash table.
	 * @return Return Value A hash table initialized with the given functions and capacity.
	 * @Discussion Hash tables allocate additional memory as needed, so initialCapacity simply establishes the object’s initial capacity.
	 **/
	
	public Object initWithPointerFunctionsCapacity(NSPointerFunctions functions,
			int initialCapacity) {
		NSHashTable hashTable = new NSHashTable();
		hashTable.mPointerFunction = functions;
		hashTable.mHashTable = new Hashtable(capacity);
		return hashTable;
	}

	// Convenience Constructors
	/**
	 * @Signature: weakObjectsHashTable
	 * @Declaration : + (id)weakObjectsHashTable
	 * @Description : Returns a new hash table for storing weak references to its contents.
	 * @return Return Value A new has table that uses the options NSHashTableZeroingWeakMemory and NSPointerFunctionsObjectPersonality and
	 *         has an initial capacity of 0.
	 **/
	
	public static Object weakObjectsHashTable() {
		NSHashTable mHashTable = new NSHashTable();
		mHashTable.mHashTable = new Hashtable(0);
		mHashTable.mPointerFunctions = NSPointerFunctionsOptions.NSPointerFunctionsWeakMemory;
		return mHashTable;
	}

	/**
	 * @Signature: hashTableWithOptions:
	 * @Declaration : + (id)hashTableWithOptions:(NSPointerFunctionsOptions)options
	 * @Description : Returns a hash table with given pointer functions options.
	 * @param options A bit field that specifies the options for the elements in the hash table. For possible values, see “Hash Table
	 *            Options.
	 * @return Return Value A hash table with given pointer functions options.
	 **/
	
	public static NSHashTable hashTableWithOptions(NSPointerFunctionsOptions options) {
		NSHashTable mHashTable = new NSHashTable();
		mHashTable.mPointerFunctions = options;
		mHashTable.mHashTable = new Hashtable();
		return mHashTable;
	}

	// Accessing Content
	/**
	 * @Signature: allObjects
	 * @Declaration : - (NSArray *)allObjects
	 * @Description : Returns an array that contains the hash table’s members.
	 * @return Return Value An array that contains the hash table’s members.
	 **/
	
	public NSArray<Object> getAllObjects() {
		NSArray<Object> mArray = new NSArray(mHashTable.size());
		Iterator<Object> it = mHashTable.values().iterator();
		while (it.hasNext()) {
			mArray.getWrappedList().add(it.next());
		}

		return mArray;
	}

	/**
	 * @Signature: anyObject
	 * @Declaration : - (id)anyObject
	 * @Description : Returns one of the objects in the hash table.
	 * @return Return Value One of the objects in the hash table, or nil if the hash table contains no objects.
	 * @Discussion The object returned is chosen at the hash table’s convenience—the selection is not guaranteed to be random.
	 **/
	
	public Object anyObject() {
		if (mHashTable.isEmpty())
			return null;
		Iterator<Object> myIterator = mHashTable.values().iterator();
		List result = new ArrayList();
		while (myIterator.hasNext()) {
			result.add(myIterator.next());
		}
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(result.size());
		return result.get(nombreAleatoire);
	}

	/**
	 * @Signature: containsObject:
	 * @Declaration : - (BOOL)containsObject:(id)anObject
	 * @Description : Returns a Boolean value that indicates whether the hash table contains a given object.
	 * @param anObject The object to test for membership in the hash table.
	 * @return Return Value YES if the hash table contains anObject, otherwise NO.
	 * @Discussion The equality test used depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public boolean containsObject(Object anObject) {
		return ((Hashtable) mHashTable).contains(anObject);
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of elements in the hash table.
	 * @return Return Value The number of elements in the hash table.
	 **/
	
	public int count() {
		return mHashTable.size();
	}

	/**
	 * @Signature: member:
	 * @Declaration : - (id)member:(id)object
	 * @Description : Determines whether the hash table contains a given object, and returns that object if it is present
	 * @param object The object to test for membership in the hash table.
	 * @return Return Value If object is a member of the hash table, returns object, otherwise returns nil.
	 * @Discussion The equality test used depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public Object member(Object object) {
		if (((Hashtable) mHashTable).contains(object)) {
			return mHashTable.get(object);
		}
		return null;
	}

	/**
	 * @Signature: objectEnumerator
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the hash table.
	 * @return Return Value An enumerator object that lets you access each object in the hash table.
	 **/
	
	public NSEnumerator<Object> objectEnumerator() {
		Iterator myIterator = mHashTable.values().iterator();
		NSEnumerator<Object> nSEnumerator = new NSEnumerator(myIterator);
		return nSEnumerator;

	}

	/**
	 * @Signature: setRepresentation
	 * @Declaration : - (NSSet *)setRepresentation
	 * @Description : Returns a set that contains the hash table’s members.
	 * @return Return Value A set that contains the hash table’s members.
	 **/
	
	public NSSet<Object> getSetRepresentation() {
		// FIXME
		Iterator myIterator = mHashTable.values().iterator();
		Set result = new HashSet();
		while (myIterator.hasNext()) {
			result.add(myIterator.next());
		}
		NSSet<Object> nSSet = new NSSet();
		nSSet.setWrappedSet(result);
		return nSSet;
	}

	// Manipulating Membership
	/**
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)object
	 * @Description : Adds a given object to the hash table.
	 * @param object The object to add to the hash table.
	 **/
	
	public void addObject(Object object) {
		mHashTable.put(object, object);
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Removes all objects from the hash table.
	 **/
	
	public void removeAllObjects() {
		mHashTable.clear();
	}

	/**
	 * @Signature: removeObject:
	 * @Declaration : - (void)removeObject:(id)object
	 * @Description : Removes a given object from the hash table.
	 * @param object The object to remove from the hash table.
	 **/
	
	public void removeObject(Object object) {
		mHashTable.remove(object);
	}

	// Comparing Hash Tables
	/**
	 * @Signature: intersectsHashTable:
	 * @Declaration : - (BOOL)intersectsHashTable:(NSHashTable *)other
	 * @Description : Returns a Boolean value that indicates whether a given hash table intersects with the receiving hash table.
	 * @param other The hash table with which to compare the receiving hash table.
	 * @return Return Value YES if other intersects with the receiving hash table, otherwise NO.
	 * @Discussion The equality test used for members depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public boolean intersectsHashTable(NSHashTable other) {
		Iterator<Object> it = mHashTable.values().iterator();
		while (it.hasNext()) {
			if (other.containsObject(it.next()))
				return true;
		}
		return false;
	}

	/**
	 * @Signature: intersectHashTable:
	 * @Declaration : - (void)intersectHashTable:(NSHashTable *)other
	 * @Description : Removes from the receiving hash table each element that isn’t a member of another given hash table.
	 * @param other The hash table with which to perform the intersection.
	 * @Discussion The equality test used for members depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public void intersectHashTable(NSHashTable other) {
		Iterator<Object> myIterator = mHashTable.values().iterator();
		List result = new ArrayList();
		while (myIterator.hasNext()) {
			result.add(myIterator.next());
		}
		for (Object object : result) {
			if (!other.containsObject(object))
				mHashTable.remove(object);
		}

	}

	/**
	 * @Signature: isEqualToHashTable:
	 * @Declaration : - (BOOL)isEqualToHashTable:(NSHashTable *)other
	 * @Description : Returns a Boolean value that indicates whether a given hash table is equal to the receiving hash table.
	 * @param other The hash table with which to compare the receiving hash table.
	 * @return Return Value YES if the contents of other are equal to the contents of the receiving hash table, otherwise NO.
	 * @Discussion Two hash tables have equal contents if they each have the same number of members and if each member of one hash table is
	 *             present in the other. The equality test used for members depends on the personality option selected. For instance,
	 *             choosing the NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See
	 *             NSPointerFunctionsOptions for more information on personality options and their corresponding equality tests.
	 **/
	
	public boolean isEqualToHashTable(NSHashTable other) {

		Iterator<Object> it = mHashTable.values().iterator();
		while (it.hasNext()) {
			if (!other.containsObject(it.next()) || other.count() != mHashTable.size())
				return false;
		}
		return true;
	}

	/**
	 * @Signature: isSubsetOfHashTable:
	 * @Declaration : - (BOOL)isSubsetOfHashTable:(NSHashTable *)other
	 * @Description : Returns a Boolean value that indicates whether every element in the receiving hash table is also present in another
	 *              given hash table.
	 * @param other The hash table with which to compare the receiving hash table.
	 * @return Return Value YES if every element in the receiving hash table is also present in other, otherwise NO.
	 * @Discussion The equality test used for members depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public boolean isSubsetOfHashTable(NSHashTable other) {
		Iterator<Object> it = mHashTable.values().iterator();
		while (it.hasNext()) {
			if (!other.containsObject(it.next()))
				return false;
		}
		return true;
	}

	// Set Functions
	/**
	 * @Signature: minusHashTable:
	 * @Declaration : - (void)minusHashTable:(NSHashTable *)other
	 * @Description : Removes each element in another given hash table from the receiving hash table, if present.
	 * @param other The hash table of elements to remove from the receiving hash table.
	 * @Discussion The equality test used for members depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public void minusHashTable(NSHashTable other) {
		Iterator<Object> myIterator = mHashTable.values().iterator();
		List result = new ArrayList();
		while (myIterator.hasNext()) {
			result.add(myIterator.next());
		}
		for (Object object : result) {
			if (other.containsObject(object))
				mHashTable.remove(object);
		}
	}

	/**
	 * @Signature: unionHashTable:
	 * @Declaration : - (void)unionHashTable:(NSHashTable *)other
	 * @Description : Adds each element in another given hash table to the receiving hash table, if not present.
	 * @param other The hash table of elements to add to the receiving hash table.
	 * @Discussion The equality test used for members depends on the personality option selected. For instance, choosing the
	 *             NSPointerFunctionsObjectPersonality option will use isEqual: to determine equality. See NSPointerFunctionsOptions for
	 *             more information on personality options and their corresponding equality tests.
	 **/
	
	public void unionHashTable(NSHashTable other) {
		Iterator<Object> myIterator = other.mHashTable.values().iterator();
		List result = new ArrayList();
		while (myIterator.hasNext()) {
			result.add(myIterator.next());
		}
		for (Object object : result) {
			if (!((Hashtable) mHashTable).contains(object))
				mHashTable.put(object, object);
		}
	}

	// Accessing Pointer Functions

	/**
	 * @Signature: pointerFunctions
	 * @Declaration : - (NSPointerFunctions *)pointerFunctions
	 * @Description : Returns the pointer functions for the hash table.
	 * @return Return Value The pointer functions for the hash table.
	 **/
	
	public NSPointerFunctions pointerFunctions() {
		return mPointerFunction;
	}

	
	@Override
	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state,
			Object[] stackbuf, int len) {
		return 0;
	}

	
	@Override
	public void encodeWithCoder(NSCoder encoder) {
		throw new UnsupportedOperationException();
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
		return mHashTable.keySet().iterator().hasNext();
	}

	
	@Override
	public Object next() {
		return mHashTable.keySet().iterator().next();
	}

	
	@Override
	public void remove() {
		mHashTable.keySet().iterator().remove();

	}

	
	@Override
	public Iterator iterator() {
		return mHashTable.keySet().iterator();
	}
}