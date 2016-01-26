
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;




public class NSMutableSet<E> extends NSSet<E> {

	public NSMutableSet() {
		super();
	}

	public NSMutableSet(Set<E> wrappedSet) {
		super(wrappedSet);
	}

	// Creating a Mutable Set

	/**
	 * @Signature: setWithCapacity:
	 * @Declaration : + (instancetype)setWithCapacity:(NSUInteger)numItems
	 * @Description : Creates and returns a mutable set with a given initial capacity.
	 * @param numItems The initial capacity of the new set.
	 * @return Return Value A mutable set with initial capacity to hold numItems members.
	 * @Discussion Mutable sets allocate additional memory as needed, so numItems simply establishes the object’s initial capacity.
	 **/
	
	public static <E> NSMutableSet setWithCapacity(int numItems) {
		NSMutableSet<E> newSet = new NSMutableSet<E>();
		newSet.wrappedSet = new HashSet<E>(numItems);
		return newSet;
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (instancetype)initWithCapacity:(NSUInteger)numItems
	 * @Description : Returns an initialized mutable set with a given initial capacity.
	 * @param numItems The initial capacity of the set.
	 * @return Return Value An initialized mutable set with initial capacity to hold numItems members. The returned set might be different
	 *         than the original receiver.
	 * @Discussion Mutable sets allocate additional memory as needed, so numItems simply establishes the object’s initial capacity. This
	 *             method is a designated initializer for NSMutableSet.
	 **/
	
	public NSSet<E> initWithCapacity(int numItems) {
		this.wrappedSet = new HashSet<E>(numItems);
		return this;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated set.
	 * @return Return Value A set.
	 * @Discussion This method is a designated initializer of NSMutableSet.
	 **/
	
	public NSMutableSet<E> init() {
		this.wrappedSet = new HashSet<E>();
		return this;
	}

	// Adding and Removing Entries

	/**
	 * Adds an object to the set.
	 *
	 * @param obj    The object to add.
	 * @param object The object to add to the set.
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)object
	 * @Description : Adds a given object to the set, if it is not already a member.
	 **/
	
	public synchronized void addObject(E obj) {
		wrappedSet.add((E) obj);
	}

	/**
	 * @Signature: filterUsingPredicate:
	 * @Declaration : - (void)filterUsingPredicate:(NSPredicate *)predicate
	 * @Description : Evaluates a given predicate against the set’s content and removes from the set those objects for which the predicate
	 *              returns false.
	 * @param predicate A predicate.
	 * @Discussion The following example illustrates the use of this method. NSMutableSet *mutableSet = [NSMutableSet setWithObjects:@"One",
	 * @"Two", @"Three", @"Four", nil]; NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF beginswith 'T'"]; [mutableSet
	 *         filterUsingPredicate:predicate]; // mutableSet contains (Two, Three)
	 **/
	
	public void filterUsingPredicate(NSPredicate predicate) {
		Set<E> newSet = new HashSet<E>();
		for (E anObject : wrappedSet) {
			if (predicate.evaluateWithObject(anObject))
				newSet.add(anObject);
		}
		this.wrappedSet = newSet;
	}

	/**
	 * @Signature: removeObject:
	 * @Declaration : - (void)removeObject:(id)object
	 * @Description : Removes a given object from the set.
	 * @param object The object to remove from the set.
	 **/
	
	public void removeObject(E object) {
		wrappedSet.remove(object);
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Empties the set of all of its members.
	 **/
	
	public void removeAllObjects() {
		wrappedSet.clear();
	}

	/**
	 * @Signature: addObjectsFromArray:
	 * @Declaration : - (void)addObjectsFromArray:(NSArray *)array
	 * @Description : Adds to the set each object contained in a given array that is not already a member.
	 * @param array An array of objects to add to the set.
	 **/
	
	public void addObjectsFromArray(NSArray<? extends E> array) {
		for (E obj : array.getWrappedList()) {
			this.wrappedSet.add(obj);
		}
	}

	// Combining and Recombining Sets

	/**
	 * @Signature: unionSet:
	 * @Declaration : - (void)unionSet:(NSSet *)otherSet
	 * @Description : Adds each object in another given set to the receiving set, if not present.
	 * @param otherSet The set of objects to add to the receiving set.
	 **/
	
	public void unionSet(NSSet<? extends E> otherSet) {
		this.wrappedSet.addAll(otherSet.getWrappedSet());
	}

	/**
	 * @Signature: minusSet:
	 * @Declaration : - (void)minusSet:(NSSet *)otherSet
	 * @Description : Removes each object in another given set from the receiving set, if present.
	 * @param otherSet The set of objects to remove from the receiving set.
	 **/
	
	public void minusSet(NSSet<E> otherSet) {
		Iterator<E> iter = wrappedSet.iterator();
		while (iter.hasNext()) {
			if (otherSet.getWrappedSet().contains(iter.next()))
				iter.remove();
		}
	}

	/**
	 * @Signature: intersectSet:
	 * @Declaration : - (void)intersectSet:(NSSet *)otherSet
	 * @Description : Removes from the receiving set each object that isn’t a member of another given set.
	 * @param otherSet The set with which to perform the intersection.
	 **/
	
	public void intersectSet(NSSet<E> otherSet) {
		Iterator<E> iter = wrappedSet.iterator();
		while (iter.hasNext()) {
			if (!otherSet.getWrappedSet().contains(iter.next()))
				iter.remove();
		}
	}

	/**
	 * @Signature: setSet:
	 * @Declaration : - (void)setSet:(NSSet *)otherSet
	 * @Description : Empties the receiving set, then adds each object contained in another given set.
	 * @param otherSet The set whose members replace the receiving set's content.
	 **/
	
	public void setSet(NSSet<? extends E> otherSet) {
		wrappedSet.clear();
		for (E se : otherSet.getWrappedSet()) {
			wrappedSet.add(se);
		}
	}

	/*** Added ****/

	public NSMutableSet(Collection<? extends E> collection) {
		super(collection);
	}

	public NSMutableSet(E object) {
		super(object);
	}

	public NSMutableSet(E... objects) {
		super(objects);
	}

	public NSMutableSet(int capacity) {
		super(capacity);
	}

	public NSMutableSet(NSArray<? extends E> objects) {
		super(objects);
	}

	public NSMutableSet(NSSet<? extends E> otherSet) {
		super(otherSet);
	}

	public NSMutableSet(Set<? extends E> set, boolean ignoreNull) {
		super(set, ignoreNull);
	}

	@Override
	protected Set<E> _initializeWithCapacity(int capacity) {
		return _setSet(new HashSet<E>(capacity));
	}

	public boolean add(E o) {
		return setNoCopy().add(o);
	};

	public boolean addAll(Collection<? extends E> c) {
		return setNoCopy().addAll(c);
	}

	@Override
	public NSMutableSet<E> clone() {
		return mutableClone();
	}

	@Override
	public NSMutableSet<E> mutableClone() {
		return new NSMutableSet<E>(this);
	}

	@Override
	public NSSet<E> immutableClone() {
		return new NSSet<E>(this);
	}

	@Override
	public int _shallowHashCode() {
		return NSMutableSet.class.hashCode();
	}

	public boolean remove(Object o) {
		return setNoCopy().remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return setNoCopy().removeAll(c);
	}

	public void subtractSet(NSSet<? extends E> otherSet) {
		wrappedSet.removeAll(otherSet.getWrappedSet());
	}

	/**
	 * Surcharged for instancetype
	 */
	public static <E> NSMutableSet<E> set() {
		return new NSMutableSet<E>();
	}

	public static <E> NSMutableSet<E> setWithArray(NSArray<E> array) {
		NSMutableSet<E> nsSet = new NSMutableSet<E>(array.getWrappedList());
		return nsSet;
	}

	public static <E> NSMutableSet<E> setWithObject(E object) {
		NSMutableSet<E> nsSet = new NSMutableSet<E>();
		nsSet.wrappedSet.add(object);
		return nsSet;
	}

	public static <E> NSMutableSet<E> setWithObjects(E... firstObj) {
		NSMutableSet<E> nsSet = new NSMutableSet<E>();
		nsSet.wrappedSet.addAll(Arrays.asList(firstObj));
		return nsSet;
	}

	public static <E> NSMutableSet<E> setWithObjectsCount(E[] objects, int cnt) {
		NSMutableSet<E> nsSet = new NSMutableSet<E>();
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