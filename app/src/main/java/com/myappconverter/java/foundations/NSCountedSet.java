
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class NSCountedSet<T> extends NSMutableSet<T> {

	int capacity;

	public int getCapacity() {
		return capacity;
	}

	// Initializing a Counted Set

	/**
	 * @Signature: initWithArray:
	 * @Declaration : - (id)initWithArray:(NSArray *)anArray
	 * @Description : Returns a counted set object initialized with the contents of a given array.
	 * @param anArray An array of objects to add to the new set.
	 * @return Return Value An initialized counted set object with the contents of anArray. The returned object might be different than the
	 *         original receiver.
	 **/
	@Override
	
	public NSCountedSet<T> initWithArray(NSArray<T> anArray) {
		this.initWithArray(anArray);
		capacity = anArray.count();
		return this;
	}

	/**
	 * @Signature: initWithSet:
	 * @Declaration : - (id)initWithSet:(NSSet *)aSet
	 * @Description : Returns a counted set object initialized with the contents of a given set.
	 * @param aSet An set of objects to add to the new set.
	 * @return Return Value An initialized counted set object with the contents of aSet. The returned object might be different than the
	 *         original receiver.
	 **/
	@Override
	
	public NSCountedSet<T> initWithSet(NSSet<T> aSet) {
		NSCountedSet<T> newSet = new NSCountedSet<T>();
		newSet.initWithSet(aSet);
		return newSet;
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (id)initWithCapacity:(NSUInteger)numItems
	 * @Description : Returns a counted set object initialized with enough memory to hold a given number of objects.
	 * @param numItems The initial capacity of the new counted set.
	 * @return Return Value A counted set object initialized with enough memory to hold numItems objects
	 * @Discussion The method is the designated initializer for NSCountedSet. Note that the capacity is simply a hint to help initial memory
	 *             allocation—the initial count of the object is 0, and the set still grows and shrinks as you add and remove objects. The
	 *             hint is typically useful if the set will become large.
	 **/
	@Override
	
	public NSCountedSet<T> initWithCapacity(int numItems) {
		capacity = numItems;
		super.initWithCapacity(numItems);
		return this;
	}

	// Adding and Removing Entries

	/**
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)anObject
	 * @Description : Adds a given object to the set.
	 * @param anObject The object to add to the set.
	 * @Discussion If anObject is already a member, addObject: increments the count associated with the object. If anObject is not already a
	 *             member, it is sent a retain message.
	 **/
	@Override
	
	public void addObject(T anObject) {
		capacity = capacity + 1;
		super.addObject(anObject);
	}

	/**
	 * @Signature: removeObject:
	 * @Declaration : - (void)removeObject:(id)anObject
	 * @Description : Removes a given object from the set.
	 * @param anObject The object to remove from the set.
	 * @Discussion If anObject is present in the set, decrements the count associated with it. If the count is decremented to 0, anObject is
	 *             removed from the set and sent a release message. removeObject: does nothing if anObject is not present in the set.
	 **/
	@Override
	
	public void removeObject(NSObject anObject) {
		capacity--;
	}

	// Examining a Counted Set

	/**
	 * @Signature: countForObject:
	 * @Declaration : - (NSUInteger)countForObject:(id)anObject
	 * @Description : Returns the count associated with a given object in the set.
	 * @param anObject The object for which to return the count.
	 * @return Return Value The count associated with anObject in the set, which can be thought of as the number of occurrences of anObject
	 *         present in the set.
	 **/
	
	public int countForObject(NSObject anObject) {
		return 0;
	}

	/**
	 * @Signature: objectEnumerator
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the set once, independent of its count.
	 * @return Return Value An enumerator object that lets you access each object in the set once, independent of its count.
	 * @Discussion If you add a given object to the counted set multiple times, an enumeration of the set will produce that object only
	 *             once. You shouldn’t modify the set during enumeration. If you intend to modify the set, use the allObjects method to
	 *             create a “snapshot, then enumerate the snapshot and modify the original set.
	 **/
	@Override
	
	public NSEnumerator<T> objectEnumerator() {
		NSEnumerator<T> mEnum = new NSEnumerator<T>(wrappedSet.iterator());
		return mEnum;
	}

	/**
	 * Surcharged for instancetype
	 */
	public static <E> NSCountedSet<E> set() {
		return new NSCountedSet<E>();
	}

	public static <E> NSCountedSet<E> setWithArray(NSArray<E> array) {
		NSCountedSet<E> nsSet = new NSCountedSet<E>();
		nsSet.wrappedSet.addAll(array.getWrappedList());
		return nsSet;
	}

	public static <E> NSCountedSet<E> setWithObject(E object) {
		NSCountedSet<E> nsSet = new NSCountedSet<E>();
		nsSet.wrappedSet.add(object);
		return nsSet;
	}

	public static <E> NSCountedSet<E> setWithArray(E... firstObj) {
		NSCountedSet<E> nsSet = new NSCountedSet<E>();
		nsSet.wrappedSet.addAll(Arrays.asList(firstObj));
		return nsSet;
	}

	public static <E> NSCountedSet<E> setWithObjectsCount(E[] objects, int cnt) {
		NSCountedSet<E> nsSet = new NSCountedSet<E>();
		List<E> mList = Arrays.asList(Arrays.copyOfRange(objects, 0, cnt));
		if (mList != null) {
			Set<E> mSet = new HashSet<E>(mList);
			nsSet.setWrappedSet(mSet);
			return nsSet;
		} else {
			throw new IllegalStateException("can't add object to this collection");
		}

	}

	public static <E extends Object> NSMutableSet<E> setWithSet(NSSet<E> set) {
		NSMutableSet<E> nsSet = set();
		nsSet.wrappedSet = new HashSet<E>(set.wrappedSet);
		return nsSet;
	}

}