
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.mapping.utils.GenericComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



public class NSMutableOrderedSet extends NSOrderedSet {

	public NSMutableOrderedSet() {
		super();
	}

	// Creating a Mutable Ordered Set
	/**
	 * @Signature: orderedSetWithCapacity:
	 * @Declaration : + (instancetype)orderedSetWithCapacity:(NSUInteger)numItems
	 * @Description : Creates and returns an mutable ordered set with a given initial capacity.
	 * @param numItems The initial capacity of the new ordered set.
	 * @return Return Value A mutable ordered set with initial capacity to hold numItems members.
	 * @Discussion Mutable ordered sets allocate additional memory as needed, so numItems simply establishes the set’s initial capacity.
	 **/
	
	public static NSMutableOrderedSet orderedSetWithCapacity(int numItems) {
		NSMutableOrderedSet ord = new NSMutableOrderedSet();
		ord.wrappedLinkedHashSet = new LinkedHashSet<NSObject>(numItems);
		return ord;
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (instancetype)initWithCapacity:(NSUInteger)numItems
	 * @Description : Returns an initialized mutable ordered set with a given initial capacity.
	 * @param numItems The initial capacity of the new ordered set.
	 * @return Return Value An initialized mutable ordered set with initial capacity to hold numItems members.
	 * @Discussion Mutable ordered sets allocate additional memory as needed, so numItems simply establishes the set’s initial capacity.
	 *             This method is a designated initializer of NSMutableOrderedSet.
	 **/
	
	public NSMutableOrderedSet initWithCapacity(int numItems) {
		this.wrappedLinkedHashSet = new LinkedHashSet<NSObject>(numItems);
		return this;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated mutable ordered set.
	 * @return Return Value A mutable ordered set.
	 * @Discussion This method is a designated initializer of NSMutableOrderedSet.
	 **/
	
	public NSMutableOrderedSet init() {
		this.wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
		return this;

	}

	// Adding, Removing, and Reordering Entries

	/**
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)object
	 * @Description : Appends a given object to the end of the mutable ordered set, if it is not already a member.
	 * @param object The object to add to the set.
	 **/
	
	public void addObject(NSObject object) {
		wrappedLinkedHashSet.add(object);
	}

	/**
	 * @Signature: addObjects:count:
	 * @Declaration : - (void)addObjects:(const id [])objects count:(NSUInteger)count
	 * @Description : Appends the given number of objects from a given C array to the end of the mutable ordered set.
	 * @param objects A C array of objects.
	 * @param count The number of values from the objects C array to append to the mutable ordered set. This number will be the count of the
	 *            new array—it must not be negative or greater than the number of elements in objects.
	 **/
	
	public void addObjectsCount(NSObject[] objects, int count) {
		if (objects.length >= count) {
			for (int i = 0; i < count; i++) {
				wrappedLinkedHashSet.add(objects[i]);
			}
		}
	}

	/**
	 * @Signature: addObjectsFromArray:
	 * @Declaration : - (void)addObjectsFromArray:(NSArray *)array
	 * @Description : Appends to the end of the mutable ordered set each object contained in a given array that is not already a member.
	 * @param array An array of objects to add to the set.
	 **/
	
	public void addObjectsFromArray(NSArray<NSObject> array) {
		for (int i = 0; i < array.getWrappedList().size(); i++) {
			wrappedLinkedHashSet.add(array.getWrappedList().get(i));
		}
	}

	/**
	 * @Signature: insertObject:atIndex:
	 * @Declaration : - (void)insertObject:(id)object atIndex:(NSUInteger)idx
	 * @Description : Inserts the given object at the specified index of the mutable ordered set.
	 * @param object The object to insert into to the set’s content. This value must not be nil. Important: Important: Raises an
	 *            NSInvalidArgumentException if object is nil.
	 * @param idx The index in the mutable ordered set at which to insert object. This value must not be greater than the count of elements
	 *            in the array. Important: Important: Raises an NSRangeException if idx is greater than the number of elements in the
	 *            mutable ordered set.
	 * @Discussion If the index is already occupied, the objects at index and beyond are shifted by adding 1 to their indices to make room.
	 **/
	
	public void insertObjectAtIndex(NSObject object, int idx) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		if (idx > wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		Set<NSObject> newHset = new LinkedHashSet<NSObject>();
		Iterator<NSObject> it = wrappedLinkedHashSet.iterator();
		for (int i = 0; i < wrappedLinkedHashSet.size() + 1; i++) {
			if (i == idx) {
				newHset.add(object);
			} else {
				newHset.add(it.next());
			}
		}
		wrappedLinkedHashSet = newHset;
	}

	/**
	 * @Signature: setObject:atIndex:
	 * @Declaration : - (void)setObject:(id)obj atIndex:(NSUInteger)idx
	 * @Description : Appends or replaces the object at the specified index.
	 * @param obj The object to insert or append.
	 * @param idx The index. If the index is equal to the length of the collection, then it inserts the object at that index, otherwise it
	 *            replaces the object at that index with the given object.
	 **/
	
	public void setObjectAtIndex(NSObject obj, int idx) {
		if (obj == null) {
			throw new IllegalArgumentException("object is null");
		}
		if (idx > wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		Set<NSObject> newHset = new LinkedHashSet<NSObject>();
		Iterator<NSObject> it = wrappedLinkedHashSet.iterator();
		for (int i = 0; i < wrappedLinkedHashSet.size() + 1; i++) {
			if (i == idx) {
				wrappedLinkedHashSet.remove(it.next());
				newHset.add(obj);
			} else {
				newHset.add(it.next());
			}
		}
		wrappedLinkedHashSet = newHset;
	}

	/**
	 * @Signature: setObject:atIndexedSubscript:
	 * @Declaration : - (void)setObject:(id)obj atIndexedSubscript:(NSUInteger)idx
	 * @Description : Inserts the given object at the specified index of the mutable ordered set.
	 * @param object The object to insert into to the set’s content. This value must not be nil. Important: Important: Raises an
	 *            NSInvalidArgumentException if object is nil.
	 * @param idx The index in the mutable ordered set at which to insert object. This value must not be greater than the count of elements
	 *            in the array. Important: Important: Raises an NSRangeException if idx is greater than the number of elements in the
	 *            mutable ordered set.
	 * @Discussion If the index is already occupied, the objects at index and beyond are shifted by adding 1 to their indices to make room.
	 *             This method is identical to insertObject:atIndex:.
	 **/
	
	public void setObjectAtIndexedSubscript(NSObject obj, int idx) {
		setObjectAtIndex(obj, idx);
	}

	/**
	 * @Signature: insertObjects:atIndexes:
	 * @Declaration : - (void)insertObjects:(NSArray *)objects atIndexes:(NSIndexSet *)indexes
	 * @Description : Inserts the objects in the array at the specified indexes.
	 * @param objects An array of objects to insert into the mutable ordered set.
	 * @param indexes The indexes at which the objects in objects should be inserted. The count of locations in indexes must equal the count
	 *            of objects.
	 * @Discussion Each object in objects is inserted into the receiving mutable ordered set in turn at the corresponding location specified
	 *             in indexes after earlier insertions have been made.
	 **/
	
	public void insertObjectsAtIndexes(NSArray<NSObject> objects, NSIndexSet indexes) {
		if (objects.getWrappedList().size() == indexes.getWrappedTreeSet().size()) {
			List<Integer> listIndex = new ArrayList<Integer>(indexes.getWrappedTreeSet());
			for (int i = 0; i < objects.getWrappedList().size(); i++) {
				insertObjectAtIndex(objects.getWrappedList().get(i), listIndex.get(i));
			}
		}
	}

	/**
	 * @Signature: removeObject:
	 * @Declaration : - (void)removeObject:(id)object
	 * @Description : Removes a given object from the mutable ordered set.
	 * @param object The object to remove from the mutable ordered set.
	 **/
	
	public void removeObject(NSObject object) {
		wrappedLinkedHashSet.remove(object);
	}

	/**
	 * @Signature: removeObjectAtIndex:
	 * @Declaration : - (void)removeObjectAtIndex:(NSUInteger)idx
	 * @Description : Removes a the object at the specified index from the mutable ordered set.
	 * @param idx The index of the object to remove from the mutable ordered set. The value must not exceed the bounds of the set.
	 *            Important: Raises an NSRangeException if index is beyond the end of the mutable ordered set.
	 * @Discussion To fill the gap, all elements beyond index are moved by subtracting 1 from their index.
	 **/
	
	public void removeObjectAtIndex(int idx) {
		if (idx > wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		NSObject obj = (NSObject) wrappedLinkedHashSet.toArray()[idx];
		wrappedLinkedHashSet.remove(obj);
	}

	/**
	 * @Signature: removeObjectsAtIndexes:
	 * @Declaration : - (void)removeObjectsAtIndexes:(NSIndexSet *)indexes
	 * @Description : Removes the objects at the specified indexes from the mutable ordered set.
	 * @param indexes The indexes of the objects to remove from the mutable ordered set. The locations specified by indexes must lie within
	 *            the bounds of the mutable ordered .
	 * @Discussion This method is similar to removeObjectAtIndex:, but allows you to efficiently remove multiple objects with a single
	 *             operation.
	 **/
	
	public void removeObjectsAtIndexes(NSIndexSet indexes) {
		List<NSObject> removedList = new ArrayList<NSObject>();
		for (int i = 0; i < indexes.getWrappedTreeSet().size(); i++) {
			if (i > wrappedLinkedHashSet.size()) {
				throw new IndexOutOfBoundsException();
			}
			NSObject obj = (NSObject) wrappedLinkedHashSet.toArray()[i];
			removedList.add(obj);
		}
		for (NSObject nsObject : removedList) {
			wrappedLinkedHashSet.remove(nsObject);
		}
	}

	/**
	 * @Signature: removeObjectsInArray:
	 * @Declaration : - (void)removeObjectsInArray:(NSArray *)array
	 * @Description : Removes the objects in the array from the mutable ordered set.
	 * @param array An array containing the objects to be removed from the receiving mutable ordered set.
	 * @Discussion This method is similar to removeObject:, but allows you to efficiently remove large sets of objects with a single
	 *             operation. If the receiving mutable ordered set does not contain objects in array, the method has no effect (although it
	 *             does incur the overhead of searching the contents). This method assumes that all elements in array respond to hash and
	 *             isEqual:.
	 **/
	
	public void removeObjectsInArray(NSArray<NSObject> array) {
		for (int i = 0; i < array.getWrappedList().size(); i++) {
			removeObject(array.getWrappedList().get(i));
		}
	}

	/**
	 * @Signature: removeObjectsInRange:
	 * @Declaration : - (void)removeObjectsInRange:(NSRange)range
	 * @Description : Removes from the mutable ordered set each of the objects within a given range.
	 * @param range The range of the objects to remove from the mutable ordered set.
	 * @Discussion The objects are removed using removeObjectAtIndex:.
	 **/
	
	public void removeObjectsInRange(NSRange range) {
		for (int i = range.location; i < range.location + range.length; i++) {
			removeObjectAtIndex(i);
		}
	}

	/**
	 * @Signature: removeAllObjects
	 * @Declaration : - (void)removeAllObjects
	 * @Description : Removes all the objects from the mutable ordered set.
	 **/
	
	public void removeAllObjects() {
		wrappedLinkedHashSet.clear();
	}

	/**
	 * @Signature: replaceObjectAtIndex:withObject:
	 * @Declaration : - (void)replaceObjectAtIndex:(NSUInteger)idx withObject:(id)object
	 * @Description : Replaces the object at the specified index with the new object.
	 * @param idx The index of the object to be replaced. This value must not exceed the bounds of the mutable ordered set.
	 *            Important: Raises an NSRangeException if index is beyond the end of the mutable ordered set.
	 * @param object The object with which to replace the object at the index in the ordered set specified by idx. This value must not be
	 *            nil. Important: Raises an NSInvalidArgumentException if object is nil.
	 **/
	
	public void replaceObjectAtIndexWithObject(int idx, NSObject object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		if (idx >= wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		removeObjectAtIndex(idx);
		insertObjectAtIndex(object, idx);
	}

	/**
	 * @Signature: replaceObjectsAtIndexes:withObjects:
	 * @Declaration : - (void)replaceObjectsAtIndexes:(NSIndexSet *)indexes withObjects:(NSArray *)objects
	 * @Description : Replaces the objects at the specified indexes with the new objects.
	 * @param indexes The indexes of the objects to be replaced.
	 * @param objects The objects with which to replace the objects in the receiving mutable ordered set at the indexes specified by
	 *            indexes. The count of locations in indexes must equal the count of objects.
	 * @Discussion The indexes in indexes are used in the same order as the objects in objects. If objects or indexes is nil, this method
	 *             raises an exception.
	 **/
	
	public void replaceObjectsAtIndexesWithObjects(NSIndexSet indexes, NSArray<NSObject> objects) {
		if (objects.getWrappedList().size() == indexes.getWrappedTreeSet().size()) {
			List<Integer> listIndex = new ArrayList<Integer>(indexes.getWrappedTreeSet());
			for (int i = 0; i < objects.getWrappedList().size(); i++) {
				replaceObjectAtIndexWithObject(listIndex.get(i), objects.getWrappedList().get(i));
			}
		}
	}

	/**
	 * @Signature: replaceObjectsInRange:withObjects:count:
	 * @Declaration : - (void)replaceObjectsInRange:(NSRange)range withObjects:(const id [])objects count:(NSUInteger)count
	 * @Description : Replaces the objects in the receiving mutable ordered set at the range with the specified number of objects from a
	 *              given C array.
	 * @param range The range of the objects to replace.
	 * @param objects A C array of objects.
	 * @param count The number of values from the objects C array to insert in place of the objects in range. This number will be the count
	 *            of the new array—it must not be negative or greater than the number of elements in objects.
	 * @Discussion Elements are added to the new array in the same order they appear in objects, up to but not including index count.
	 **/
	
	public void replaceObjectsInRangeWithObjectsCount(NSRange range, NSObject[] objects, int count) {
		if (count >= 0 && objects.length >= count) {
			int j = 0;
			for (int i = range.location; i < count; i++) {
				NSObject obj = objects[j++];
				replaceObjectAtIndexWithObject(i, obj);
			}
		}
	}

	/**
	 * @Signature: moveObjectsAtIndexes:toIndex:
	 * @Declaration : - (void)moveObjectsAtIndexes:(NSIndexSet *)indexes toIndex:(NSUInteger)idx
	 * @Description : Moves the objects at the specified indexes to the new location.
	 * @param indexes The indexes of the objects to move.
	 * @param idx The index in the mutable ordered set at which to insert the objects. The objects being moved are first removed from the
	 *            set, then this index is used to find the location at which to insert the moved objects.
	 * @Discussion An example: NSMutableIndexSet *movedObjectIndexes = [NSMutableIndexSet indexSet]; [movedObjectIndexes addIndex: 1];
	 *             [movedObjectIndexes addIndex: 3]; NSMutableOrderedSet *mySet = [NSMutableOrderedSet orderedSetWithCapacity:5]; [mySet
	 *             addObject:@"a"]; [mySet addObject:@"b"]; [mySet addObject:@"c"]; [mySet addObject:@"d"]; [mySet addObject:@"e"]; [mySet
	 *             moveObjectsAtIndexes:movedObjectIndexes toIndex:2]; This code results in the contents of mySet being [@"a", @"c", @"b",
	 * @"d", @"e"].
	 **/
	
	public void moveObjectsAtIndexesToIndex(NSIndexSet indexes, int idx) {
		List<NSObject> objectsToMove = new ArrayList<NSObject>();
		for (Integer index : indexes.getWrappedTreeSet()) {
			objectsToMove.add(objectAtIndex(index));
		}
		// remove object
		Iterator<NSObject> it = this.wrappedLinkedHashSet.iterator();
		while (it.hasNext()) {
			NSObject nsObject = it.next();
			if (objectsToMove.contains(nsObject))
				it.remove();
		}
		// add object at index
		Collections.reverse(objectsToMove);
		for (NSObject objMoved : objectsToMove) {
			insertObjectAtIndex(objMoved, idx);
		}
	}

	/**
	 * @Signature: exchangeObjectAtIndex:withObjectAtIndex:
	 * @Declaration : - (void)exchangeObjectAtIndex:(NSUInteger)idx1 withObjectAtIndex:(NSUInteger)idx2
	 * @Description : Exchanges the object at the specified index with the object at the other index.
	 * @param idx1 The index of the first object. Important: Raises an NSRangeException if index is beyond the end of the mutable ordered
	 *            set.
	 * @param idx2 The index of the second object. Important: Raises an NSRangeException if index is beyond the end of the mutable ordered
	 *            set.
	 **/
	
	public void exchangeObjectAtIndexWithObjectAtIndex(int idx1, int idx2) {
		if (idx1 >= wrappedLinkedHashSet.size() || idx2 >= wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		NSObject obj1 = objectAtIndex(idx1);
		NSObject obj2 = objectAtIndex(idx2);
		removeObjectAtIndex(idx1);
		insertObjectAtIndex(obj2, idx1);
		insertObjectAtIndex(obj1, idx2);
	}

	/**
	 * @Signature: filterUsingPredicate:
	 * @Declaration : - (void)filterUsingPredicate:(NSPredicate *)predicate
	 * @Description : Evaluates a given predicate against the mutable ordered set’s content and leaves only objects that match.
	 * @param predicate The predicate to evaluate against the set’s elements.
	 **/
	
	public void filterUsingPredicate(NSPredicate predicate) {
		NSMutableOrderedSet newOrd = new NSMutableOrderedSet();
		for (NSObject anObject : wrappedLinkedHashSet) {
			if (!predicate.evaluateWithObject(anObject))
				newOrd.wrappedLinkedHashSet.add(anObject);
		}
		wrappedLinkedHashSet = newOrd.wrappedLinkedHashSet;
		// TODO Check after (NSPredicate not implemented)
	}

	// Sorting Entries

	/**
	 * @Signature: sortUsingDescriptors:
	 * @Declaration : - (void)sortUsingDescriptors:(NSArray *)sortDescriptors
	 * @Description : Sorts the receiving ordered set using a given array of sort descriptors.
	 * @param sortDescriptors An array containing the NSSortDescriptor objects to use to sort the receiving ordered set’s contents.
	 * @Discussion See NSSortDescriptor for additional information.
	 **/
	
	public void sortUsingDescriptors(NSArray<NSObject> sortDescriptors) {
		List<NSObject> newList = new ArrayList<NSObject>(wrappedLinkedHashSet);
		List<NSObject> desc = sortDescriptors.wrappedList;
		for (Object sortD : desc) {
			Collections.sort(newList,
					new GenericComparator(((NSSortDescriptor) sortD).key().getWrappedString(), ((NSSortDescriptor) sortD).isAscending()));
		}
		wrappedLinkedHashSet = new LinkedHashSet<NSObject>(newList);
		// TODO Check that
	}

	/**
	 * @Signature: sortUsingComparator:
	 * @Declaration : - (void)sortUsingComparator:(NSComparator)cmptr
	 * @Description : Sorts the mutable ordered set using the comparison method specified by the comparator block.
	 * @param cmptr A comparator block.
	 **/
	
	public void sortUsingComparator(NSComparator cmptr) {
		List<NSObject> newList = new ArrayList<NSObject>(wrappedLinkedHashSet);
		Collections.sort(newList, cmptr);
		wrappedLinkedHashSet = new LinkedHashSet<NSObject>(newList);
		// TODO Check that
	}

	/**
	 * @Signature: sortWithOptions:usingComparator:
	 * @Declaration : - (void)sortWithOptions:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
	 * @Description : Sorts the mutable ordered set using the specified options and the comparison method specified by a given comparator
	 *              block.
	 * @param opts A bitmask that specifies the options for the sort (whether it should be performed concurrently and whether it should be
	 *            performed stably).
	 * @param cmptr A comparator block.
	 **/
	
	public void sortWithOptionsUsingComparator(int opts, NSComparator cmptr) {
		if (opts == NSObjCRuntime.NSSortConcurrent)
			synchronized (this.wrappedLinkedHashSet) {
				sortUsingComparator(cmptr);
			}
		else
			sortUsingComparator(cmptr);
		// TODO Check that
	}

	/**
	 * @Signature: sortRange:options:usingComparator:
	 * @Declaration : - (void)sortRange:(NSRange)range options:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
	 * @Description : Sorts the specified range of the mutable ordered set using the specified options and the comparison method specified
	 *              by a given comparator block.
	 * @param range The range to sort.
	 * @param opts A bitmask that specifies the options for the sort (whether it should be performed concurrently and whether it should be
	 *            performed stably).
	 * @param cmptr A comparator block.
	 **/
	
	public void sortRangeOptionsUsingComparator(NSRange range, int opts, NSComparator cmptr) {

		List<NSObject> newList = new ArrayList<NSObject>(wrappedLinkedHashSet);
		Collections.sort(newList.subList(1, newList.size()), cmptr);
		this.wrappedLinkedHashSet = new LinkedHashSet<NSObject>(newList);
		// TODO check That

		// others method
		/*
		 * NSMutableOrderedSet newOrderSet = new NSMutableOrderedSet(); LinkedHashSet<NSObject> newHSet = new LinkedHashSet<NSObject>(); int
		 * i = 0; for (NSObject nsObject : this.hSet) { if (i >= range.location && i < range.location + range.length) {
		 * newHSet.add(nsObject); } i++; } newOrderSet.hSet = newHSet; removeObjectsInRange(range); // sort object in newHSet
		 * newOrderSet.sortedArrayWithOptions(opts, cmptr); // add sorted newOrderSet to this.hSet NSArray<NSObject> array = new
		 * NSArray<NSObject>(); NSObject[] listObj = new NSObject[range.length]; int j = 0; for (NSObject nsObject : newOrderSet.hSet) {
		 * listObj[j++] = nsObject; } array.initWithObjects(listObj); this.insertObjects(array,
		 * NSIndexSet.indexSetWithIndexesInRange(range));
		 */
	}

	// Combining and Recombining Entries

	/**
	 * @Signature: intersectOrderedSet:
	 * @Declaration : - (void)intersectOrderedSet:(NSOrderedSet *)other
	 * @Description : Removes from the receiving ordered set each object that isn’t a member of another ordered set.
	 * @param other The ordered set with which to perform the intersection.
	 **/
	
	public void intersectOrderedSet(NSOrderedSet other) {
		for (NSObject obj : this.wrappedLinkedHashSet) {
			if (!other.wrappedLinkedHashSet.contains(obj)) {
				wrappedLinkedHashSet.remove(obj);
			}
		}
	}

	/**
	 * @Signature: intersectSet:
	 * @Declaration : - (void)intersectSet:(NSSet *)other
	 * @Description : Removes from the receiving ordered set each object that isn’t a member of another set.
	 * @param other The set with which to perform the intersection.
	 **/
	
	public void intersectSet(NSSet<NSObject> other) {
		for (NSObject obj : this.wrappedLinkedHashSet) {
			if (!other.getWrappedSet().contains(obj)) {
				wrappedLinkedHashSet.remove(obj);
			}
		}
	}

	/**
	 * @Signature: minusOrderedSet:
	 * @Declaration : - (void)minusOrderedSet:(NSOrderedSet *)other
	 * @Description : Removes each object in another given ordered set from the receiving mutable ordered set, if present.
	 * @param other The ordered set of objects to remove from the receiving set.
	 **/
	
	public void minusOrderedSet(NSOrderedSet other) {
		for (NSObject obj : this.wrappedLinkedHashSet) {
			if (other.wrappedLinkedHashSet.contains(obj)) {
				wrappedLinkedHashSet.remove(obj);
			}
		}
	}

	/**
	 * @Signature: minusSet:
	 * @Declaration : - (void)minusSet:(NSSet *)other
	 * @Description : Removes each object in another given set from the receiving mutable ordered set, if present.
	 * @param other The set of objects to remove from the receiving set.
	 **/
	
	public void minusSet(NSSet<NSObject> other) {
		for (NSObject obj : this.wrappedLinkedHashSet) {
			if (other.getWrappedSet().contains(obj)) {
				wrappedLinkedHashSet.remove(obj);
			}
		}
	}

	/**
	 * @Signature: unionOrderedSet:
	 * @Declaration : - (void)unionOrderedSet:(NSOrderedSet *)other
	 * @Description : Adds each object in another given ordered set to the receiving mutable ordered set, if not present.
	 * @param other The set of objects to add to the receiving mutable ordered set.
	 **/
	
	public void unionOrderedSet(NSOrderedSet other) {
		for (NSObject obj : other.wrappedLinkedHashSet) {
			this.wrappedLinkedHashSet.add(obj);
		}
	}

	/**
	 * @Signature: unionSet:
	 * @Declaration : - (void)unionSet:(NSSet *)other
	 * @Description : Adds each object in another given set to the receiving mutable ordered set, if not present.
	 * @param other The set of objects to add to the receiving mutable ordered set.
	 **/
	
	public void unionSet(NSSet<NSObject> other) {
		for (NSObject obj : other.getWrappedSet()) {
			this.wrappedLinkedHashSet.add(obj);
		}
	}

	/**
	 * Surcharged for instancetype
	 */

	public static NSMutableOrderedSet orderedSet() {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		ordSet.wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithArray(NSArray<NSObject> array) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(array.getWrappedList());
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return ordSet;
	}

	public static <T extends NSObject> NSMutableOrderedSet orderedSetWithArrayRangeCopyItems(NSArray<T> array, NSRange range, Boolean flag) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		List<T> list = array.getWrappedList();
		// clone of list
		List<T> mList = new ArrayList<T>(list);
		for (int i = range.location; i < range.length + range.location; i++) {
			if (flag) {
				ordSet.wrappedLinkedHashSet.add(mList.get(i));
			} else {
				ordSet.wrappedLinkedHashSet.add(list.get(i));
			}
		}
		return ordSet;
	}

	public static NSMutableOrderedSet setObjectToMutableOrderedSet(NSObject object) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		ordSet.wrappedLinkedHashSet.add(object);
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithObjects(NSObject... firstObj) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		for (int i = 0; i < firstObj.length; i++) {
			if (firstObj[i] != null)
				ordSet.wrappedLinkedHashSet.add(firstObj[i]);
		}
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithObjectsCount(NSObject[] objects, int cnt) {
		NSObject[] objs = new NSObject[cnt];
		for (int i = 0; i < cnt; i++) {
			objs[i] = objects[i];
		}
		List<NSObject> list = Arrays.asList(objs);
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(list);
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithOrderedSet(NSOrderedSet set) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		ordSet.wrappedLinkedHashSet = set.wrappedLinkedHashSet;
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithOrderedSetRangeCopyItems(NSOrderedSet set, NSRange range, boolean flag) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		List<NSObject> list = new ArrayList<NSObject>(set.wrappedLinkedHashSet);
		for (int i = range.location; i < range.length + range.location; i++) {
			ordSet.wrappedLinkedHashSet.add(list.get(i));
		}
		return ordSet;
		// TODO flag
	}

	public static NSMutableOrderedSet orderedSetWithSet(NSSet<NSObject> set) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = (NSObject) iterator.next();
			ordSet.wrappedLinkedHashSet.add(obj);
		}
		return ordSet;
	}

	public static NSMutableOrderedSet orderedSetWithSetCopyItems(NSSet<NSObject> set, boolean flag) {
		NSMutableOrderedSet ordSet = new NSMutableOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = (NSObject) iterator.next();
			if (flag) {
				ordSet.wrappedLinkedHashSet.add((NSObject) obj.copy());
			} else {
				ordSet.wrappedLinkedHashSet.add(obj);
			}
			// TODO flag
		}
		return ordSet;
	}
}