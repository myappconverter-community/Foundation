
package com.myappconverter.java.foundations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

import android.util.Log;

import com.myappconverter.java.foundations.NSArray.NSBinarySearchingOptions;
import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.NSKeyValueObservingOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.GenericComparator;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.PerformBlock;


public class NSOrderedSet extends NSObject implements NSCopying, NSMutableCopying,
		NSFastEnumeration, NSSecureCoding, Iterator<NSObject>, Iterable<NSObject> {

	public Set<NSObject> getWrappedLinkedHashSet() {
		return wrappedLinkedHashSet;
	}

	public void setWrappedLinkedHashSet(LinkedHashSet<NSObject> wrappedLinkedHashSet) {
		this.wrappedLinkedHashSet = wrappedLinkedHashSet;
	}

	public Set<NSObject> wrappedLinkedHashSet;
	Map<String, Set<NSObject>> observers = new HashMap<String, Set<NSObject>>();

	public NSOrderedSet() {
		super();
		wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
	}

	// Creating an Ordered Set

	/**
	 * @Signature: orderedSet
	 * @Declaration : + (instancetype)orderedSet
	 * @Description : Creates and returns an empty ordered set
	 * @return Return Value A new empty ordered set.
	 * @Discussion This method is declared primarily for the use of mutable subclasses of NSOrderedSet.
	 **/

	public static NSOrderedSet orderedSet() {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
		return ordSet;
	}

	public static NSOrderedSet orderedSet(Class clazz) {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithArray:
	 * @Declaration : + (instancetype)orderedSetWithArray:(NSArray *)array
	 * @Description : Creates and returns a set containing a uniqued collection of the objects contained in a given array.
	 * @param array An array containing the objects to add to the new ordered set. If the same object appears more than once in array, it is
	 *            added only once to the returned set.
	 * @return Return Value A new ordered set containing a uniqued collection of the objects contained in array.
	 **/

	public static NSOrderedSet orderedSetWithArray(NSArray<NSObject> array) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(array.getWrappedList());
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithArray(Class clazz, NSArray<NSObject> array) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(array.getWrappedList());
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithArray:range:copyItems:
	 * @Declaration : + (instancetype)orderedSetWithArray:(NSArray *)array range:(NSRange)range copyItems:(BOOL)flag
	 * @Description : Creates and returns a new ordered set for a specified range of objects in an array.
	 * @param array The array
	 * @param range The range of the objects to add to the ordered set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value A new ordered set containing a uniqued collection of the objects contained in the specified range of the array.
	 **/

	public static <T extends NSObject> NSOrderedSet orderedSetWithArrayRangeCopyItems(
			NSArray<T> array, NSRange range, Boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
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

	public static <T extends NSObject> NSOrderedSet orderedSetWithArrayRangeCopyItems(Class clazz,
																					  NSArray<T> array, NSRange range, Boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
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
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithObject:
	 * @Declaration : + (instancetype)orderedSetWithObject:(id)object
	 * @Description : Creates and returns a ordered set that contains a single given object.
	 * @param object The object to add to the new set.
	 * @return Return Value A new ordered set containing object.
	 **/

	public static NSOrderedSet orderedSetWithObject(NSObject object) {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet.add(object);
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithObject(Class clazz, NSObject object) {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet.add(object);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithObjects:
	 * @Declaration : + (instancetype)orderedSetWithObjects:(id)firstObj,, ...
	 * @Description : Creates and returns a ordered set containing the objects in a given argument list.
	 * @param firstObj, The first object to add to the new set.
	 * @param ... A comma-separated list of objects, ending with nil, to add to the new set. If the same object appears more than once in
	 *            the list of objects, it is added only once to the returned set. The objects are added to the ordered set in the order that
	 *            they are listed.
	 * @return Return Value A new ordered set containing the objects in the argument list.
	 **/

	public static NSOrderedSet orderedSetWithObjects(NSObject... firstObj) {
		NSOrderedSet ordSet = new NSOrderedSet();
		for (int i = 0; i < firstObj.length; i++) {
			if (firstObj[i] != null)
				ordSet.wrappedLinkedHashSet.add(firstObj[i]);
		}
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithObjects(Class clazz, NSObject... firstObj) {
		NSOrderedSet ordSet = new NSOrderedSet();
		for (int i = 0; i < firstObj.length; i++) {
			if (firstObj[i] != null)
				ordSet.wrappedLinkedHashSet.add(firstObj[i]);
		}
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithObjects:count:
	 * @Declaration : + (instancetype)orderedSetWithObjects:(const id [])objects count:(NSUInteger)cnt
	 * @Description : Creates and returns a set containing a specified number of objects from a given C array of objects.
	 * @param objects A C array of objects to add to the new ordered set. If the same object appears more than once in objects, it is added
	 *            only once to the returned ordered set. Each object receives a retain message as it is added to the set.
	 * @param cnt The number of objects from objects to add to the new set.
	 * @return Return Value A new ordered set containing cnt objects from the list of objects specified by objects.
	 **/

	public static NSOrderedSet orderedSetWithObjectsCount(NSObject[] objects, int cnt) {
		NSObject[] objs = new NSObject[cnt];
		for (int i = 0; i < cnt; i++) {
			objs[i] = objects[i];
		}
		List<NSObject> list = Arrays.asList(objs);
		NSOrderedSet ordSet = new NSOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(list);
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithObjectsCount(Class clazz, NSObject[] objects,
														  int cnt) {
		NSObject[] objs = new NSObject[cnt];
		for (int i = 0; i < cnt; i++) {
			objs[i] = objects[i];
		}
		List<NSObject> list = Arrays.asList(objs);
		NSOrderedSet ordSet = new NSOrderedSet();
		Collection<NSObject> collection = new ArrayList<NSObject>(list);
		ordSet.wrappedLinkedHashSet.addAll(collection);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithOrderedSet:
	 * @Declaration : + (instancetype)orderedSetWithOrderedSet:(NSOrderedSet *)set
	 * @Description : Creates and returns an ordered set containing the objects from another ordered set.
	 * @param set A set containing the objects to add to the new ordered set. The objects are not copied, simply referenced.
	 * @return Return Value A new ordered set containing the objects from set.
	 **/

	public static NSOrderedSet orderedSetWithOrderedSet(NSOrderedSet set) {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet = set.wrappedLinkedHashSet;
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithOrderedSet(Class clazz, NSOrderedSet set) {
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet = set.wrappedLinkedHashSet;
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithOrderedSet:range:copyItems:
	 * @Declaration : + (instancetype)orderedSetWithOrderedSet:(NSOrderedSet *)set range:(NSRange)range copyItems:(BOOL)flag
	 * @Description : Creates and returns a new ordered set for a specified range of objects in an ordered set.
	 * @param set An ordered set.
	 * @param range The range of objects in set to add to the ordered set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value A new ordered set containing a uniqued collection of the objects contained in the specified range of the the
	 *         ordered set.
	 **/

	public static NSOrderedSet orderedSetWithOrderedSetRangeCopyItems(NSOrderedSet set,
																	  NSRange range, boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
		List<NSObject> list = new ArrayList<NSObject>(set.wrappedLinkedHashSet);
		for (int i = range.location; i < range.length + range.location; i++) {
			ordSet.wrappedLinkedHashSet.add(list.get(i));
		}
		return ordSet;
		// TODO flag
	}

	public static NSOrderedSet orderedSetWithOrderedSetRangeCopyItems(Class clazz, NSOrderedSet set,
																	  NSRange range, boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
		List<NSObject> list = new ArrayList<NSObject>(set.wrappedLinkedHashSet);
		for (int i = range.location; i < range.length + range.location; i++) {
			ordSet.wrappedLinkedHashSet.add(list.get(i));
		}
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
		// TODO flag
	}

	/**
	 * @Signature: orderedSetWithSet:
	 * @Declaration : + (instancetype)orderedSetWithSet:(NSSet *)set
	 * @Description : Creates and returns an ordered set with the contents of a set.
	 * @param set A set.
	 * @return Return Value A new ordered set containing a uniqued collection of the objects contained in the set.
	 **/

	public static NSOrderedSet orderedSetWithSet(NSSet<NSObject> set) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			ordSet.wrappedLinkedHashSet.add(obj);
		}
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithSet(Class clazz, NSSet<NSObject> set) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			ordSet.wrappedLinkedHashSet.add(obj);
		}
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: orderedSetWithSet:copyItems:
	 * @Declaration : + (instancetype)orderedSetWithSet:(NSSet *)set copyItems:(BOOL)flag
	 * @Description : Creates and returns an ordered set with the contents of a set, optionally copying the items.
	 * @param set A set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value A new ordered set containing a uniqued collection of the objects contained in the specified range of the set.
	 **/

	public static NSOrderedSet orderedSetWithSetCopyItems(NSSet<NSObject> set, boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			if (flag) {
				ordSet.wrappedLinkedHashSet.add(obj.copy());
			} else {
				ordSet.wrappedLinkedHashSet.add(obj);
			}
			// TODO flag
		}
		return ordSet;
	}

	public static NSOrderedSet orderedSetWithSetCopyItems(Class clazz, NSSet<NSObject> set,
														  boolean flag) {
		NSOrderedSet ordSet = new NSOrderedSet();
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			if (flag) {
				ordSet.wrappedLinkedHashSet.add(obj.copy());
			} else {
				ordSet.wrappedLinkedHashSet.add(obj);
			}
			// TODO flag
		}
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	// Initializing an Ordered Set

	/**
	 * @Signature: initWithArray:
	 * @Declaration : - (instancetype)initWithArray:(NSArray *)array
	 * @Description : Initializes a newly allocated set with the objects that are contained in a given array.
	 * @param array An array of objects to add to the new set. If the same object appears more than once in array, it is represented only
	 *            once in the returned ordered set.
	 * @return Return Value An initialized ordered set with the contents of array. The returned ordered set might be different than the
	 *         original receiver.
	 **/

	public NSOrderedSet initWithArray(NSArray<NSObject> array) {
		Collection<NSObject> collection = new ArrayList<NSObject>(array.getWrappedList());
		wrappedLinkedHashSet.addAll(collection);
		return this;
	}

	public NSOrderedSet initWithArray(Class clazz, NSArray<NSObject> array) {
		NSOrderedSet ordSet = initWithArray(array);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithArray:copyItems:
	 * @Declaration : - (instancetype)initWithArray:(NSArray *)array copyItems:(BOOL)flag
	 * @Description : Initializes a newly allocated set with the objects that are contained in a given array, optionally copying the items.
	 * @param array An array of objects to add to the new set. If the same object appears more than once in array, it is represented only
	 *            once in the returned ordered set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value An initialized ordered set containing a uniqued collection of the objects contained in the array.
	 **/

	public NSOrderedSet initWithArrayCopyItems(NSArray<NSObject> array, Boolean flag) {
		List<NSObject> list = array.getWrappedList();
		for (int i = 0; i < list.size(); i++) {
			wrappedLinkedHashSet.add(list.get(i));
		}
		// TODO flag
		return this;
	}

	public NSOrderedSet initWithArrayCopyItems(Class clazz, NSArray<NSObject> array, Boolean flag) {
		NSOrderedSet ordSet = initWithArrayCopyItems(array, flag);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithArray:range:copyItems:
	 * @Declaration : - (instancetype)initWithArray:(NSArray *)array range:(NSRange)range copyItems:(BOOL)flag
	 * @Description : Initializes a newly allocated set with the objects that are contained in the specified range of an array, optionally
	 *              copying the items.
	 * @param array An array of objects to add to the new set. If the same object appears more than once in array, it is represented only
	 *            once in the returned ordered set.
	 * @param range The range of objects in array to add to the ordered set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value An initialized ordered set containing a uniqued collection of the objects contained in specified range of the
	 *         the array.
	 **/

	public NSOrderedSet initWithArrayRangeCopyItems(NSArray<NSObject> array, NSRange range,
													boolean flag) {
		List<NSObject> list = array.getWrappedList();
		for (int i = range.location; i < range.length + range.location; i++) {
			wrappedLinkedHashSet.add(list.get(i));
		}
		// TODO flag
		return this;
	}

	public NSOrderedSet initWithArrayRangeCopyItems(Class clazz, NSArray<NSObject> array,
													NSRange range, boolean flag) {
		NSOrderedSet ordSet = initWithArrayRangeCopyItems(array, range, flag);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithObject:
	 * @Declaration : - (instancetype)initWithObject:(id)object
	 * @Description : Initializes a new ordered set with the object.
	 * @param object The object to add to the new ordered set
	 * @return Return Value A new ordered set that contains a single member, object.
	 **/

	public NSOrderedSet initWithObject(NSObject object) {
		wrappedLinkedHashSet.add(object);
		return this;
	}

	public NSOrderedSet initWithObject(Class clazz, NSObject object) {
		NSOrderedSet ordSet = initWithObject(object);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithObjects:
	 * @Declaration : - (instancetype)initWithObjects:(id)firstObj,, ...
	 * @Description : Initializes a newly allocated set with members taken from the specified list of objects.
	 * @param firstObj, The first object to add to the new set.
	 * @param ... A comma-separated list of objects, ending with nil, to add to the new ordered set. If the same object appears more than
	 *            once in the list, it is represented only once in the returned ordered set.
	 * @return Return Value An initialized ordered set containing the objects specified in the parameter list. The returned set might be
	 *         different than the original receiver.
	 **/

	public NSOrderedSet initWithObjects(NSObject... firstObj) {
		List<NSObject> list = Arrays.asList(firstObj);
		Collection<NSObject> collection = new ArrayList<NSObject>(list);
		wrappedLinkedHashSet.addAll(collection);
		return this;
	}

	public NSOrderedSet initWithObjects(Class clazz, NSObject... firstObj) {
		NSOrderedSet ordSet = initWithObjects(firstObj);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithObjects:count:
	 * @Declaration : - (instancetype)initWithObjects:(const id [])objects count:(NSUInteger)cnt
	 * @Description : Initializes a newly allocated set with a specified number of objects from a given C array of objects.
	 * @param objects A C array of objects to add to the new set. If the same object appears more than once in objects, it is added only
	 *            once to the returned ordered set.
	 * @param cnt The number of objects from objects to add to the new ordered set.
	 * @return Return Value An initialized ordered set containing cnt objects from the list of objects specified by objects. The returned
	 *         set might be different than the original receiver. This method is a designated initializer of NSOrderedSet.
	 **/

	public NSOrderedSet initWithObjectsCount(NSObject[] objects, int cnt) {
		NSObject[] objs = new NSObject[cnt];
		for (int i = 0; i < cnt; i++) {
			objs[i] = objects[i];
		}
		List<NSObject> list = Arrays.asList(objs);
		Collection<NSObject> collection = new ArrayList<NSObject>(list);
		wrappedLinkedHashSet.addAll(collection);
		return this;
	}

	public NSOrderedSet initWithObjectsCount(Class clazz, NSObject[] objects, int cnt) {
		NSOrderedSet ordSet = initWithObjectsCount(objects, cnt);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithOrderedSet:
	 * @Declaration : - (instancetype)initWithOrderedSet:(NSOrderedSet *)set
	 * @Description : Initializes a new ordered set with the contents of a set.
	 * @param set A set.
	 * @return Return Value An initialized ordered set containing references to the objects in the set.
	 **/

	public NSOrderedSet initWithOrderedSet(NSOrderedSet set) {
		wrappedLinkedHashSet = set.wrappedLinkedHashSet;
		return this;
	}

	public NSOrderedSet initWithOrderedSet(Class clazz, NSOrderedSet set) {
		NSOrderedSet ordSet = initWithOrderedSet(set);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithOrderedSet:copyItems:
	 * @Declaration : - (instancetype)initWithOrderedSet:(NSOrderedSet *)set copyItems:(BOOL)flag
	 * @Description : Initializes a new ordered set with the contents of a set, optionally copying the items.
	 * @param set A set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value An initialized ordered set containing the objects in the set.
	 **/

	public NSOrderedSet initWithOrderedSetCopyItems(NSOrderedSet set, boolean flag) {
		List<NSObject> list = new ArrayList<NSObject>(set.wrappedLinkedHashSet);
		for (int i = 0; i < list.size(); i++) {
			wrappedLinkedHashSet.add(list.get(i));
		}
		// TODO flag
		return this;
	}

	public NSOrderedSet initWithOrderedSetCopyItems(Class clazz, NSOrderedSet set, boolean flag) {
		NSOrderedSet ordSet = initWithOrderedSetCopyItems(set, flag);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithOrderedSet:range:copyItems:
	 * @Declaration : - (instancetype)initWithOrderedSet:(NSOrderedSet *)orderedSet range:(NSRange)range copyItems:(BOOL)flag
	 * @Description : Initializes a new ordered set with the contents of an ordered set, optionally copying the items.
	 * @param orderedSet An ordered set.
	 * @param range The range of objects in orderedSet to add to the ordered set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value An initialized ordered set containing the objects in the ordered set.
	 **/

	public NSOrderedSet initWithOrderedSetRangeCopyItems(NSOrderedSet orderedSet, NSRange range,
														 boolean flag) {
		List<NSObject> list = new ArrayList<NSObject>(orderedSet.wrappedLinkedHashSet);
		for (int i = range.location; i < range.length + range.location; i++) {
			wrappedLinkedHashSet.add(list.get(i));
		}
		// TODO flag
		return this;
	}

	public NSOrderedSet initWithOrderedSetRangeCopyItems(Class clazz, NSOrderedSet orderedSet,
														 NSRange range, boolean flag) {
		NSOrderedSet ordSet = initWithOrderedSetRangeCopyItems(orderedSet, range, flag);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithSet:
	 * @Declaration : - (instancetype)initWithSet:(NSSet *)set
	 * @Description : Initializes a new ordered set with the contents of a set.
	 * @param set The set.
	 * @return Return Value An initialized ordered set containing the objects in the set.
	 **/

	public NSOrderedSet initWithSet(NSSet<NSObject> set) {
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			wrappedLinkedHashSet.add(obj);
		}
		return this;
	}

	public NSOrderedSet initWithSet(Class clazz, NSSet<NSObject> set) {
		NSOrderedSet ordSet = initWithSet(set);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: initWithSet:copyItems:
	 * @Declaration : - (instancetype)initWithSet:(NSSet *)set copyItems:(BOOL)flag
	 * @Description : Initializes a new ordered set with the contents of a set, optionally copying the objects in the set.
	 * @param set The set.
	 * @param flag If YES the objects are copied to the ordered set; otherwise NO.
	 * @return Return Value An initialized ordered set containing the objects in the set.
	 **/

	public NSOrderedSet initWithSetCopyItems(NSSet<NSObject> set, boolean flag) {
		Iterator<NSObject> iterator = set.getWrappedSet().iterator();
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			if (flag) {
				wrappedLinkedHashSet.add(obj.copy());
			} else {
				wrappedLinkedHashSet.add(obj);
			}
			// TODO flag
		}
		return this;
	}

	public NSOrderedSet initWithSetCopyItems(Class clazz, NSSet<NSObject> set, boolean flag) {
		NSOrderedSet ordSet = initWithSetCopyItems(set, flag);
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated ordered set.
	 * @return Return Value An ordered set.
	 * @Discussion This method is a designated initializer of NSOrderedSet.
	 **/
	@Override

	public NSOrderedSet init() {
		wrappedLinkedHashSet = new LinkedHashSet<NSObject>();
		return this;
	}

	public NSOrderedSet init(Class clazz) {
		NSOrderedSet ordSet = init();
		return (NSOrderedSet) InstanceTypeFactory.getInstance(ordSet, NSOrderedSet.class, clazz,
				new NSString("setWrappedLinkedHashSet"), ordSet.getWrappedLinkedHashSet());
	}

	// Counting Entries
	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of members in the set.
	 * @return Return Value The number of members in the set.
	 **/

	public int count() {
		return wrappedLinkedHashSet.size();
	}

	// Accessing Set Members
	/**
	 * @Signature: containsObject:
	 * @Declaration : - (BOOL)containsObject:(id)object
	 * @Description : Returns a Boolean value that indicates whether a given object is present in the ordered set.
	 * @param object The object for which to test membership of the ordered set.
	 * @return Return Value YES if object is present in the set, otherwise NO.
	 **/

	public Boolean containsObject(NSObject object) {
		return wrappedLinkedHashSet.contains(object);
	}

	/**
	 * @Signature: enumerateObjectsAtIndexes:options:usingBlock:
	 * @Declaration : - (void)enumerateObjectsAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts usingBlock:(void (^)(id
	 *              obj, NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given block using the objects in the ordered set at the specified indexes.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param block The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered set.
	 *            idx The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to YES
	 *            to stop further processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean
	 *            to YES within the block.
	 * @param obj The element in the ordered set.
	 * @param idx The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the block.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the ordered set to the last
	 *             element specified by indexSet. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options
	 *             to modify this behavior. Important: If the block parameter or the indexSet is nil, this method raises an exception.
	 **/

	public void enumerateObjectsAtIndexesOptionsUsingBlock(NSIndexSet indexSet, int opts,
														   PerformBlock.VoidBlockIdNSUIntegerBOOL block) {
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			int i = indexSet.getWrappedTreeSet().first();
			for (NSObject anObject : wrappedLinkedHashSet) {
				if (i <= indexSet.getWrappedTreeSet().last()) {
					block.perform(anObject, i, stop);
					if (stop[0])
						break;
					i++;
				}
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			Object[] array = wrappedLinkedHashSet.toArray();

			for (int i = indexSet.getWrappedTreeSet().last(); i >= indexSet.getWrappedTreeSet()
					.first(); i--) {
				block.perform(array[i], i, stop);
				if (stop[0])
					break;
			}
		}
	}

	/**
	 * @Signature: enumerateObjectsUsingBlock:
	 * @Declaration : - (void)enumerateObjectsUsingBlock:(void (^)(id obj, NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given block using each object in the ordered set.
	 * @param block The block to apply to elements in the ordered set. The block takes three arguments: idx The element in the set. idx The
	 *            index of the item in the set. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the set. The stop argument is an out-only argument. You should only ever set this value to YES within the
	 *            block. The block returns a Boolean value that indicates whether obj passed the test.
	 * @param idx The element in the set.
	 * @param idx The index of the item in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 **/

	public void enumerateObjectsUsingBlock(PerformBlock.VoidBlockIdNSUIntegerBOOL block) {
		boolean[] stop = new boolean[1];
		stop[0] = false;
		int i = 0;
		for (NSObject anObject : wrappedLinkedHashSet) {
			block.perform(anObject, i, stop);
			if (stop[0])
				break;
			i++;
		}
	}

	/**
	 * @Signature: enumerateObjectsWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateObjectsWithOptions:(NSEnumerationOptions)opts usingBlock:(void (^)(id obj, NSUInteger idx, BOOL
	 *              *stop))block
	 * @Description : Executes a given block using each object in the set, using the specified enumeration options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param block The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the set. idx The
	 *            index of the item in the set. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the set. The stop argument is an out-only argument. You should only ever set this value to YES within the
	 *            block. The block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the set.
	 * @param idx The index of the item in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 **/

	public void enumerateObjectsWithOptionsUsingBlock(int opts,
													  PerformBlock.VoidBlockIdNSUIntegerBOOL block) {
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			int i = 0;
			for (NSObject anObject : wrappedLinkedHashSet) {
				block.perform(anObject, i, stop);
				if (stop[0])
					break;
				i++;
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			Object[] array = wrappedLinkedHashSet.toArray();

			for (int i = array.length - 1; i >= 0; i--) {
				block.perform(array[i], i, stop);
				if (stop[0])
					break;
			}
		}
	}

	/**
	 * @Signature: firstObject
	 * @Declaration : - (id)firstObject
	 * @Description : Returns the first object in the ordered set.
	 * @return Return Value The first object in the ordered set.
	 **/

	public NSObject firstObject() {
		return objectAtIndex(0);
	}

	/**
	 * @Signature: lastObject
	 * @Declaration : - (id)lastObject
	 * @Description : Returns the last object in the ordered set.
	 * @return Return Value The last object in the ordered set.
	 **/

	public NSObject lastObject() {
		return objectAtIndex(wrappedLinkedHashSet.size() - 1);
	}

	/**
	 * @Signature: objectAtIndex:
	 * @Declaration : - (id)objectAtIndex:(NSUInteger)index
	 * @Description : Returns the object at the specified index of the set.
	 * @param index The object located at index.
	 * @return Return Value If index is beyond the end of the ordered set (that is, if index is greater than or equal to the value returned
	 *         by count), an NSRangeException is raised.
	 **/

	public NSObject objectAtIndex(int index) {
		Iterator<NSObject> iterator = wrappedLinkedHashSet.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			if (i == index) {
				return obj;
			}
			i++;
		}
		return null;
	}

	/**
	 * @Signature: objectAtIndexedSubscript:
	 * @Declaration : - (id)objectAtIndexedSubscript:(NSUInteger)index
	 * @Description : Returns the object at the specified index of the set.
	 * @param index The object located at index.
	 * @return Return Value If index is beyond the end of the ordered set (that is, if index is greater than or equal to the value returned
	 *         by count), an NSRangeException is raised.
	 * @Discussion This method is the same as objectAtIndex:.
	 **/

	public NSObject objectAtIndexedSubscript(int index) {
		return objectAtIndex(index);
	}

	/**
	 * @Signature: objectsAtIndexes:
	 * @Declaration : - (NSArray *)objectsAtIndexes:(NSIndexSet *)indexes
	 * @Description : Returns the objects in the ordered set at the specified indexes.
	 * @param indexes The indexes.
	 * @return Return Value The returned objects are in the ascending order of their indexes in indexes, so that object in returned ordered
	 *         set with higher index in indexes will follow the object with smaller index in indexes.
	 * @Discussion Raises an NSRangeException if any location in indexes exceeds the bounds of the array, of if indexes is nil.
	 **/

	public NSArray<NSObject> objectsAtIndexes(NSIndexSet indexes) {
		NSMutableArray array = new NSMutableArray();
		Iterator<Integer> iterator = indexes.getWrappedTreeSet().iterator();
		while (iterator.hasNext()) {
			Integer index = iterator.next();
			if (index > wrappedLinkedHashSet.size()) {
				throw new IndexOutOfBoundsException("Index Out Of Bounds ");
			}
			array.addObject(objectAtIndex(index));
		}
		return array;
	}

	/**
	 * @Signature: indexOfObject:
	 * @Declaration : - (NSUInteger)indexOfObject:(id)object
	 * @Description : Returns the index of the specified object.
	 * @param object The object.
	 * @return Return Value The index whose corresponding ordered set value is equal to object. If none of the objects in the ordered set is
	 *         equal to object, returns NSNotFound.
	 **/

	public int indexOfObject(NSObject object) {
		Iterator<NSObject> iterator = wrappedLinkedHashSet.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			NSObject obj = iterator.next();
			if (object.equals(obj)) {
				return i;
			}
			i++;
		}
		return NSObjCRuntime.NSNotFound;
	}

	/**
	 * @Signature: indexOfObject:inSortedRange:options:usingComparator:
	 * @Declaration : - (NSUInteger)indexOfObject:(id)object inSortedRange:(NSRange)range options:(NSBinarySearchingOptions)opts
	 *              usingComparator:(NSComparator)cmp
	 * @Description : Returns the index, within a specified range, of an object compared with elements in the ordered set using a given
	 *              NSComparator block.
	 * @param object An object for which to search in the ordered set. If this value is nil, throws an NSInvalidArgumentException.
	 * @param range The range within the array to search for object. If r exceeds the bounds of the ordered set (if the location plus length
	 *            of the range is greater than the count of the ordered set), throws an NSRangeException.
	 * @param opts Options for the search. For possible values, see NSBinarySearchingOptions.
	 * @param cmp A comparator block used to compare the object obj with elements in the ordered set. If this value is NULL, throws an
	 *            NSInvalidArgumentException.
	 * @return Return Value If the NSBinarySearchingInsertionIndex option is not specified: If the object is found and neither
	 *         NSBinarySearchingFirstEqual nor NSBinarySearchingLastEqual is specified, returns the matching object's index. If the
	 *         NSBinarySearchingFirstEqual or NSBinarySearchingLastEqual option is also specified, returns the index of equal objects. If
	 *         the object is not found, returns NSNotFound. If the NSBinarySearchingInsertionIndex option is specified, returns the index at
	 *         which you should insert obj in order to maintain a sorted array: If the object is found and neither
	 *         NSBinarySearchingFirstEqual nor NSBinarySearchingLastEqual is specified, returns the matching object’s index. If the
	 *         NSBinarySearchingFirstEqual or NSBinarySearchingLastEqual option is also specified, returns the index of the equal objects.
	 *         If the object is not found, returns the index of the least greater object, or the index at the end of the array if the object
	 *         is larger than all other elements.
	 * @Discussion The elements in the ordered set must have already been sorted using the comparator cmp. If the ordered set is not sorted,
	 *             the result is undefined.
	 **/

	public int indexOfObjectInSortedRangeOptionsUsingComparator(NSObject object, NSRange range,
																NSBinarySearchingOptions opts, NSComparator cmp) {
		if ((range.location + range.length) > this.wrappedLinkedHashSet.size())
			throw new InvalidParameterException("NSRangeException range out of bound");
		if (object == null || cmp == null)
			throw new IllegalArgumentException("null parameter not allowed");

		int first = NSObjCRuntime.NSNotFound;
		int last = NSObjCRuntime.NSNotFound;
		for (int i = range.location; i < range.location + range.length; i++) {
			if (cmp.compare(objectAtIndex(i), object) == 0) {
				if (first == NSObjCRuntime.NSNotFound) {
					first = i;
				}
				last = i;
			}
		}
		if (opts == NSBinarySearchingOptions.NSBinarySearchingFirstEqual) {
			return first;
		} else if (opts == NSBinarySearchingOptions.NSBinarySearchingLastEqual) {
			return last;
		}
		// FIXME use opts Like NSarray
		return NSObjCRuntime.NSNotFound;
	}

	/**
	 * @Signature: indexOfObjectAtIndexes:options:passingTest:
	 * @Declaration : - (NSUInteger)indexOfObjectAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts passingTest:(BOOL
	 *              (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index, from a given set of indexes, of the object in the ordered set that passes a test in a given block
	 *              for a given set of enumeration options.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered
	 *            set. idx The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to
	 *            YES to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value
	 *            to YES within the block. The block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the ordered set.
	 * @param idx The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index of the corresponding value in the ordered set passes the test specified by predicate. If no objects in
	 *         the ordered set pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the ordered set to the last
	 *             element specified by indexSet. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options
	 *             to modify this behavior. Important: If the block parameter or indexSet is nil, this method raises an exception.
	 **/

	public int indexOfObjectAtIndexesOptionsPassingTest(NSIndexSet indexSet, int opts,
														PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		// extract objects with indexSet
		List<NSObject> newList = new ArrayList<NSObject>();
		List<Integer> listIndexes = new ArrayList<Integer>(indexSet.getWrappedTreeSet());
		for (int i = 0; i < indexSet.getWrappedTreeSet().size(); i++) {
			int index = listIndexes.get(i);
			if (index < (wrappedLinkedHashSet.size() - 1)) {
				NSObject object = objectAtIndex(listIndexes.get(i));
				newList.add(object);
			}
		}
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationConcurrent) {
			Collections.reverse(newList);
		}
		boolean[] stop = new boolean[] { Boolean.TRUE };
		for (int i = 0; i < newList.size() && stop[0]; i++) {
			if (predicate.perform(newList.get(i), i, stop))
				return i;
		}
		return -1;// NSNotFound
	}

	/**
	 * @Signature: indexesOfObjectsAtIndexes:options:passingTest:
	 * @Declaration : - (NSIndexSet *)indexesOfObjectsAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts passingTest:(BOOL
	 *              (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index, from a given set of indexes, of the object in the ordered set that passes a test in a given block
	 *              for a given set of enumeration options.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered
	 *            set. idx The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to
	 *            YES to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value
	 *            to YES within the block. The block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the ordered set.
	 * @param idx The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index of the corresponding value in the ordered set that passes the test specified by predicate. If no
	 *         objects in the ordered set pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the ordered set to the last
	 *             object. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this
	 *             behavior. Important:  If the block parameter or indexSet is nil this method will raise an exception.
	 **/

	public NSIndexSet indexesOfObjectsAtIndexesOptionsPassingTest(NSIndexSet indexSet, int opts,
																  PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		NSIndexSet resIndexSet = new NSIndexSet();
		resIndexSet.setWrappedTreeSet(new TreeSet<Integer>());
		// extract objects with indexSet
		List<NSObject> newList = new ArrayList<NSObject>();
		List<Integer> listIndexes = new ArrayList<Integer>(indexSet.getWrappedTreeSet());
		for (int i = 0; i < indexSet.getWrappedTreeSet().size(); i++) {
			int index = listIndexes.get(i);
			if (index < (wrappedLinkedHashSet.size() - 1)) {
				NSObject object = objectAtIndex(listIndexes.get(i));
				newList.add(object);
			}
		}
		//
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationConcurrent) {
			Collections.reverse(newList);
		}
		boolean[] stop = new boolean[] { Boolean.TRUE };
		for (int i = 0; i < newList.size(); i++) {

			if (predicate.perform(newList.get(i), i, stop))
				resIndexSet.getWrappedTreeSet().add(i);
		}
		return resIndexSet;
		// TODO Check that
	}

	/**
	 * @Signature: indexesOfObjectsPassingTest:
	 * @Declaration : - (NSIndexSet *)indexesOfObjectsPassingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index of the object in the ordered set that passes a test in a given block.
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered
	 *            set. Term The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to
	 *            YES to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value
	 *            to YES within the block.
	 * @param obj The element in the ordered set.
	 * @param Term The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index of the corresponding value in the ordered set that passes the test specified by predicate. If no
	 *         objects in the ordered set pass the test, returns NSNotFound..
	 * @Discussion If the block parameter is nil, this method raises an exception.
	 **/

	public NSIndexSet indexesOfObjectsPassingTest(
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		return indexesOfObjectsWithOptionsPassingTest(NSObjCRuntime.NSEnumerationConcurrent,
				predicate);
		// TODO Check that
	}

	/**
	 * @Signature: indexesOfObjectsWithOptions:passingTest:
	 * @Declaration : - (NSIndexSet *)indexesOfObjectsWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(id obj, NSUInteger idx,
	 *              BOOL *stop))predicate
	 * @Description : Returns the index of an object in the ordered set that passes a test in a given block for a given set of enumeration
	 *              options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered
	 *            set. Term The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to
	 *            YES to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value
	 *            to YES within the block.
	 * @param obj The element in the ordered set.
	 * @param Term The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index whose corresponding value in the ordered set passes the test specified by predicate and opts. If the
	 *         opts bitmask specifies reverse order, then the last item that matches is returned. Otherwise, the index of the first matching
	 *         object is returned. If no objects in the ordered set pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the ordered set to the last
	 *             object. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this
	 *             behavior. Important:  If the block parameter or s is nil, this method raises an exception.
	 **/

	public NSIndexSet indexesOfObjectsWithOptionsPassingTest(int opts,
															 PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		NSIndexSet resIndexSet = new NSIndexSet();
		resIndexSet.setWrappedTreeSet(new TreeSet<Integer>());
		List<NSObject> tmpList = new ArrayList<NSObject>(array().getWrappedList());
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationConcurrent) {
			Collections.reverse(tmpList);
		}
		boolean[] stop = new boolean[] { Boolean.TRUE };
		for (int i = 0; i < tmpList.size(); i++) {
			if (predicate.perform(tmpList.get(i), i, stop))
				resIndexSet.getWrappedTreeSet().add(i);
		}
		return resIndexSet;
		// TODO Check that
	}

	/**
	 * @Signature: indexOfObjectPassingTest:
	 * @Declaration : - (NSUInteger)indexOfObjectPassingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index of the object in the ordered set that passes a test in a given block.
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the ordered
	 *            set. Term The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to
	 *            YES to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value
	 *            to YES within the block.
	 * @param obj The element in the ordered set.
	 * @param Term The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index of the corresponding value in the ordered set that passes the test specified by predicate. If no
	 *         objects in the ordered set pass the test, returns NSNotFound.
	 **/

	public int indexOfObjectPassingTest(PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		return indexOfObjectWithOptionsPassingTest(NSObjCRuntime.NSEnumerationConcurrent,
				predicate);
		// TODO check that like NSArray
	}

	/**
	 * @Signature: indexOfObjectWithOptions:passingTest:
	 * @Declaration : - (NSUInteger)indexOfObjectWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL
	 *              *stop))predicate
	 * @Description : Returns the index of an object in the ordered set that passes a test in a given block for a given set of enumeration
	 *              options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the ordered set. The block takes three arguments: obj The element in the array.
	 *            idx The index of the element in the ordered set. stop A reference to a Boolean value. The block can set the value to YES
	 *            to stop further processing of the set. The stop argument is an out-only argument. You should only ever set this value to
	 *            YES within the block. The block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the ordered set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this value to YES within the block.
	 * @return Return Value The index whose corresponding value in the ordered set passes the test specified by predicate and opts. If no
	 *         objects in the ordered set pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the ordered set to the last
	 *             object. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this
	 *             behavior.
	 **/

	public int indexOfObjectWithOptionsPassingTest(int opts,
												   PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		List<NSObject> tmpList = new ArrayList<NSObject>(array().getWrappedList());

		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationConcurrent) {
			Collections.reverse(tmpList);
		}
		boolean[] stop = new boolean[] { Boolean.TRUE };
		for (int i = 0; i < tmpList.size(); i++) {

			if (predicate.perform(array().getWrappedList().get(i), i, stop))
				return i;
		}
		return NSObjCRuntime.NSNotFound;
		// TODO check that like NSArray
	}

	/**
	 * @Signature: objectEnumerator
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the ordered set.
	 * @return Return Value An enumerator object that lets you access each object in the ordered set, in order, from the element at the
	 *         lowest index upwards.
	 **/

	public NSEnumerator<NSObject> objectEnumerator() {
		return new NSEnumerator<NSObject>(this.wrappedLinkedHashSet.iterator());
	}

	/**
	 * @Signature: reverseObjectEnumerator
	 * @Declaration : - (NSEnumerator *)reverseObjectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the ordered set.
	 * @return Return Value An enumerator object that lets you access each object in the ordered set, in order, from the element at the
	 *         highest index downwards.
	 **/

	public NSEnumerator reverseObjectEnumerator() {
		List<NSObject> list = new ArrayList<NSObject>();
		for (int i = wrappedLinkedHashSet.size() - 1; i >= 0; i--) {
			list.add(objectAtIndex(i));
		}
		return new NSEnumerator<NSObject>(list.iterator());
	}

	/**
	 * @Signature: reversedOrderedSet
	 * @Declaration : - (NSOrderedSet *)reversedOrderedSet
	 * @Description : Returns an ordered set in the reverse order.
	 * @return Return Value Returns an ordered set in the reversed order of the receiver.
	 **/

	public NSOrderedSet reversedOrderedSet() {
		List<NSObject> list = new ArrayList<NSObject>(wrappedLinkedHashSet);
		Collections.reverse(list);
		Set<NSObject> revSet = new LinkedHashSet<NSObject>(list);
		NSOrderedSet ordSet = new NSOrderedSet();
		ordSet.wrappedLinkedHashSet = revSet;
		return ordSet;
	}

	/**
	 * @Signature: getObjects:range:
	 * @Declaration : - (void)getObjects:(id [])objects range:(NSRange)range
	 * @Description : Copies the objects contained in the ordered set that fall within the specified range to objects.
	 * @param objects A C array of objects of size at least the length of the range specified by aRange.
	 * @param range A range within the bounds of the array. If the location plus the length of the range is greater than the count of the
	 *            array, this method raises an NSRangeException.
	 **/

	public void getObjectsRange(NSObject[] objects, NSRange range) {
		if (objects.length < range.length) {
			throw new IndexOutOfBoundsException();
		}
		if ((range.location + range.length) > wrappedLinkedHashSet.size()) {
			throw new IndexOutOfBoundsException();
		}
		int j = 0;
		for (int i = range.location; i < range.length + range.location; i++) {
			objects[j++] = objectAtIndex(i);
		}
		// TODO verify objet copy
	}

	// Key-Value Coding Support
	/**
	 * @Signature: setValue:forKey:
	 * @Declaration : - (void)setValue:(id)value forKey:(NSString *)key
	 * @Description : Invokes setValue:forKey: on each of the receiver's members using the specified value and key
	 * @param value The object value.
	 * @param key The key to store the value.
	 **/

	public void setValueForKey(NSObject value, NSString key) {

		for (NSObject obj : wrappedLinkedHashSet) {
			Method method;
			try {
				method = obj.getClass().getMethod("setValue", Object.class, NSString.class);
				if (method != null) {
					method.invoke(obj, value, key);
				}
			} catch (NoSuchMethodException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IllegalArgumentException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IllegalAccessException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (InvocationTargetException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}

		}
	}

	/**
	 * @Signature: valueForKey:
	 * @Declaration : - (id)valueForKey:(NSString *)key
	 * @Description : Returns an ordered set containing the results of invoking valueForKey: using key on each of the ordered set’s objects.
	 * @param key The key to retrieve.
	 * @return Return Value The ordered set of the values for the retrieved key. The returned ordered set might not have the same number of
	 *         members as the receiver.
	 * @Discussion The returned ordered set will not contain any elements corresponding to instances of valueForKey: returning nil, nor will
	 *             it contain duplicates.
	 **/

	public NSOrderedSet valueForKey(NSString key) {
		NSOrderedSet resOrdSet = new NSOrderedSet();
		for (NSObject obj : wrappedLinkedHashSet) {
			Method method;
			try {
				method = obj.getClass().getMethod("valueForKey", String.class);
				if (method != null) {
					List<Object> resList = (ArrayList<Object>) method.invoke(obj,
							key.getWrappedString());
					NSObject resObj = (NSObject) resList.get(0);
					resOrdSet.wrappedLinkedHashSet.add(resObj);
				}
			} catch (NoSuchMethodException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IllegalArgumentException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IllegalAccessException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (InvocationTargetException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return resOrdSet;
	}

	// Key-Value Observing Support

	/**
	 * @Signature: addObserver:forKeyPath:options:context:
	 * @Declaration : - (void)addObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath options:(NSKeyValueObservingOptions)options
	 *              context:(void *)context
	 * @Description : Raises an exception.
	 * @param observer The object to register for KVO notifications.
	 * @param keyPath The key path, relative to the array, of the property to observe. This value must not be nil.
	 * @param options A combination of NSKeyValueObservingOptions values that specifies what is included in observation notifications.
	 * @param context Arbitrary data that is passed to observer in observeValueForKeyPath:ofObject:change:context:.
	 * @Discussion NSOrderedSet objects are not observable, so this method raises an exception when invoked on an NSOrderedSet object.
	 *             Instead of observing an ordered set, observe the to-many relationship for which the ordered set is the collection of
	 *             related objects.
	 **/

	public void addObserverForKeyPathOptionsContext(Observer observer, NSString keyPath,
													NSKeyValueObservingOptions options, NSObject context) {
		// TODO check that
		Set obs = observers.get(keyPath);
		obs.add(observer);
	}

	/**
	 * @Signature: removeObserver:forKeyPath:
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath
	 * @Description : Raises an exception.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the set, for which observer is registered to receive KVO change notifications. This value must
	 *            not be nil.
	 * @Discussion NSOrderedSet objects are not observable, so this method raises an exception when invoked on an NSOrderedSet object.
	 *             Instead of observing an ordered set, observe the to-many relationship for which the ordered set is the collection of
	 *             related objects.
	 **/

	public void removeObserverForKeyPath(NSObject observer, NSString keyPath) {
		Set obs = observers.get(keyPath);
		if (obs.contains(observer))
			obs.remove(observer);
	}

	/**
	 * @Signature: removeObserver:forKeyPath:context:
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath context:(void *)context
	 * @Description : Raises an exception.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the set, for which observer is registered to receive KVO change notifications. This value must
	 *            not be nil.
	 * @param context The context passed to the notifications.
	 * @Discussion NSOrderedSet objects are not observable, so this method raises an exception when invoked on an NSOrderedSet object.
	 *             Instead of observing an ordered set, observe the to-many relationship for which the ordered set is the collection of
	 *             related objects.
	 **/

	public void removeObserverForKeyPathContext(NSObject observer, NSString keyPath,
												NSObject context) {
		Set obs = observers.get(keyPath);
		if (obs.contains(observer))
			obs.remove(observer);
	}

	// Comparing Sets

	/**
	 * @Signature: isEqualToOrderedSet:
	 * @Declaration : - (BOOL)isEqualToOrderedSet:(NSOrderedSet *)other
	 * @Description : Compares the receiving ordered set to another ordered set.
	 * @param other The ordered set with which to compare the receiving ordered set.
	 * @return Return Value YES if the contents of other are equal to the contents of the receiving ordered set, otherwise NO.
	 * @Discussion Two ordered sets have equal contents if they each have the same number of members, if each member of one ordered set is
	 *             present in the other, and the members are in the same order.
	 **/

	public Boolean isEqualToOrderedSet(NSOrderedSet other) {
		if (wrappedLinkedHashSet.size() != other.wrappedLinkedHashSet.size()) {
			return false;
		}
		for (int i = 0; i < wrappedLinkedHashSet.size(); i++) {
			NSObject obj1 = this.objectAtIndex(i);
			NSObject obj2 = other.objectAtIndex(i);
			if (!obj1.equals(obj2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Signature: intersectsOrderedSet:
	 * @Declaration : - (BOOL)intersectsOrderedSet:(NSOrderedSet *)other
	 * @Description : Returns a Boolean value that indicates whether at least one object in the receiving ordered set is also present in
	 *              another given ordered set.
	 * @param other The other ordered set.
	 * @return Return Value YES if at least one object in the receiving ordered set is also present in other, otherwise NO.
	 **/

	public Boolean intersectsOrderedSet(NSOrderedSet other) {
		for (int i = 0; i < wrappedLinkedHashSet.size(); i++) {
			NSObject obj1 = this.objectAtIndex(i);
			if (other.wrappedLinkedHashSet.contains(obj1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Signature: intersectsSet:
	 * @Declaration : - (BOOL)intersectsSet:(NSSet *)set
	 * @Description : Returns a Boolean value that indicates whether at least one object in the receiving ordered set is also present in
	 *              another given set.
	 * @param set The set.
	 * @return Return Value YES if at least one object in the receiving ordered set is also present in other, otherwise NO.
	 **/

	public boolean intersectsSet(NSSet<NSObject> set) {
		for (int i = 0; i < wrappedLinkedHashSet.size(); i++) {
			NSObject obj1 = this.objectAtIndex(i);
			if (set.containsObject(obj1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Signature: isSubsetOfOrderedSet:
	 * @Declaration : - (BOOL)isSubsetOfOrderedSet:(NSOrderedSet *)other
	 * @Description : Returns a Boolean value that indicates whether every object in the receiving ordered set is also present in another
	 *              given ordered set.
	 * @param other The ordered set with which to compare the receiving ordered set.
	 * @return Return Value YES if every object in the receiving set is also present in other, otherwise NO.
	 **/

	public boolean isSubsetOfOrderedSet(NSOrderedSet other) {
		for (int i = 0; i < wrappedLinkedHashSet.size(); i++) {
			NSObject obj1 = this.objectAtIndex(i);
			if (!other.wrappedLinkedHashSet.contains(obj1)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Signature: isSubsetOfSet:
	 * @Declaration : - (BOOL)isSubsetOfSet:(NSSet *)set
	 * @Description : Returns a Boolean value that indicates whether every object in the receiving ordered set is also present in another
	 *              given set.
	 * @param set The set with which to compare the receiving ordered set.
	 * @return Return Value YES if every object in the receiving ordered set is also present in set, otherwise NO.
	 **/

	public Boolean isSubsetOfSet(NSSet<NSObject> set) {
		for (int i = 0; i < wrappedLinkedHashSet.size(); i++) {
			NSObject obj1 = this.objectAtIndex(i);
			if (!set.containsObject(obj1)) {
				return false;
			}
		}
		return true;
	}

	// Creating a Sorted Array

	/**
	 * @Signature: sortedArrayUsingDescriptors:
	 * @Declaration : - (NSArray *)sortedArrayUsingDescriptors:(NSArray *)sortDescriptors
	 * @Description : Returns an array of the ordered set’s elements sorted as specified by a given array of sort descriptors.
	 * @param sortDescriptors An array of NSSortDescriptor objects.
	 * @return Return Value An NSArray containing the ordered set’s elements sorted as specified by sortDescriptors.
	 * @Discussion The first descriptor specifies the primary key path to be used in sorting the ordered set’s elements. Any subsequent
	 *             descriptors are used to further refine sorting of objects with duplicate values. See NSSortDescriptor for additional
	 *             information.
	 **/

	public NSArray<NSObject> sortedArrayUsingDescriptors(NSArray<NSObject> sortDescriptors) {
		List<NSObject> desc = sortDescriptors.getWrappedList();
		NSArray<NSObject> array = array();
		for (Object sortD : desc) {
			// FIXME
			Collections.sort(array.getWrappedList(),
					new GenericComparator(((NSSortDescriptor) sortD).key().getWrappedString(),
							((NSSortDescriptor) sortD).isAscending()));
		}
		return array;
	}

	/**
	 * @Signature: sortedArrayUsingComparator:
	 * @Declaration : - (NSArray *)sortedArrayUsingComparator:(NSComparator)cmptr
	 * @Description : Returns an array that lists the receiving ordered set’s elements in ascending order, as determined by the comparison
	 *              method specified by a given NSComparator block
	 * @param cmptr A comparator block.
	 * @return Return Value An array that lists the receiving ordered set’s elements in ascending order, as determined by the comparison
	 *         method specified cmptr.
	 **/

	public NSArray<NSObject> sortedArrayUsingComparator(NSComparator cmptr) {
		NSArray<NSObject> array = array();
		Collections.sort(array.getWrappedList(), cmptr);
		return array;
	}

	/**
	 * @Signature: sortedArrayWithOptions:usingComparator:
	 * @Declaration : - (NSArray *)sortedArrayWithOptions:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
	 * @Description : Returns an array that lists the receiving ordered set’s elements in ascending order, as determined by the comparison
	 *              method specified by a given NSComparator block.
	 * @param opts A bitmask that specifies the options for the sort (whether it should be performed concurrently and whether it should be
	 *            performed stably).
	 * @param cmptr A comparator block.
	 * @return Return Value An array that lists the receiving ordered set’s elements in ascending order, as determined by the comparison
	 *         method specified cmptr.
	 **/

	public NSArray<NSObject> sortedArrayWithOptionsUsingComparator(int opts, NSComparator cmptr) {
		NSArray<NSObject> array = array();
		if (opts == NSObjCRuntime.NSSortConcurrent)
			synchronized (array.getWrappedList()) {
				Collections.sort(array.getWrappedList(), cmptr);
			}
		else
			sortedArrayUsingComparator(cmptr);
		return null;

	}

	// Filtering Ordered Sets

	/**
	 * @Signature: filteredOrderedSetUsingPredicate:
	 * @Declaration : - (NSOrderedSet *)filteredOrderedSetUsingPredicate:(NSPredicate *)predicate
	 * @Description : Evaluates a given predicate against each object in the receiving ordered set and returns a new ordered set containing
	 *              the objects for which the predicate returns true.
	 * @param predicate The predicate against which to evaluate the receiving ordered set’s elements.
	 * @return Return Value A new ordered set containing the objects in the receiving ordered set for which predicate returns true.
	 * @Discussion For more details, see Predicate Programming Guide.
	 **/

	public NSOrderedSet filteredOrderedSetUsingPredicate(NSPredicate predicate) {
		NSOrderedSet newOrd = new NSOrderedSet();
		for (NSObject anObject : wrappedLinkedHashSet) {
			if (predicate.evaluateWithObject(anObject))
				newOrd.wrappedLinkedHashSet.add(anObject);
		}
		return newOrd;
		// TODO Check after (NSPredicate not implemented)
	}

	// Describing a Set

	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns a string that represents the contents of the ordered set, formatted as a property list.
	 * @return Return Value A string that represents the contents of the ordered set, formatted as a property list.
	 **/
	@Override

	public NSString description() {
		StringBuilder buf = new StringBuilder();
		buf.append(
				NSPropertyListSerialization.TOKEN_BEGIN[NSPropertyListSerialization.PLIST_ARRAY]);
		Iterator<NSObject> it = wrappedLinkedHashSet.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (buf.length() == 1)
				buf.append(' ');
			NSObject k = it.next();
			buf.append(k.description());
			if (i != wrappedLinkedHashSet.size() - 1) {
				buf.append(", ");
			}
			i++;
		}
		buf.append(NSPropertyListSerialization.TOKEN_END[NSPropertyListSerialization.PLIST_ARRAY]);
		return new NSString(buf.toString());

	}

	/**
	 * @Signature: descriptionWithLocale:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale
	 * @Description : Returns a string that represents the contents of the ordered set, formatted as a property list.
	 * @param locale An NSLocale object or an NSDictionary object that specifies options used for formatting each of the ordered set’s
	 *            elements (where recognized). Specify nil if you don’t want the elements formatted.
	 * @return Return Value A string that represents the contents of the ordered set, formatted as a property list.
	 * @Discussion For a description of how locale is applied to each element in the receiving ordered set, see
	 *             descriptionWithLocale:indent:.
	 **/

	public NSString descriptionWithLocale(NSLocale locale) {
		if (locale == null) {
			return NSString.stringWithString(description());
		} else {
			String string = String.format(locale.getLocale(), description().getWrappedString());
			return NSString.stringWithString(new NSString(string));
		}
	}

	/**
	 * @Signature: descriptionWithLocale:indent:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale indent:(NSUInteger)level
	 * @Description : Returns a string that represents the contents of the ordered set, formatted as a property list.
	 * @param locale An NSLocale object or an NSDictionary object that specifies options used for formatting each of the array’s elements
	 *            (where recognized). Specify nil if you don’t want the elements formatted.
	 * @param level Specifies a level of indentation, to make the output more readable: the indentation is (4 spaces) * level.
	 * @return Return Value A string that represents the contents of the ordered set, formatted as a property list.
	 * @Discussion The returned NSString object contains the string representations of each of the ordered set’s elements, in order, from
	 *             first to last. To obtain the string representation of a given element, descriptionWithLocale:indent: proceeds as follows:
	 *             If the element is an NSString object, it is used as is. If the element responds to descriptionWithLocale:indent:, that
	 *             method is invoked to obtain the element’s string representation. If the element responds to descriptionWithLocale:, that
	 *             method is invoked to obtain the element’s string representation. If none of the above conditions is met, the element’s
	 *             string representation is obtained by invoking its description method
	 **/

	public NSString descriptionWithLocaleIndent(NSLocale locale, int level) {
		return descriptionWithLocale(locale);
		// TODO check level
	}

	// Converting Other Collections

	/**
	 * @Signature: array
	 * @Declaration : - (NSArray *)array
	 * @Description : Returns a representation of the ordered set as an array.
	 * @return Return Value An array containing the ordered set’s elements.
	 * @Discussion This return a proxy object for the receiving ordered set, which acts like an immutable array. While you cannot mutate the
	 *             ordered set through this proxy, mutations to the original ordered set will be reflected in the proxy and it will appear
	 *             to change spontaneously, since a copy of the ordered set is not being made.
	 **/

	public NSArray<NSObject> array() {
		NSMutableArray array = new NSMutableArray();
		Iterator<NSObject> it = wrappedLinkedHashSet.iterator();
		while (it.hasNext()) {
			NSObject object = it.next();
			array.addObject(object);
		}
		return array;
	}

	/**
	 * @Signature: set
	 * @Declaration : - (NSSet *)set
	 * @Description : Returns a representation of the set containing the contents of the ordered set.
	 * @return Return Value A set containing the ordered set’s elements.
	 * @Discussion This return a proxy object for the receiving ordered set, which acts like an immutable set. While you cannot mutate the
	 *             ordered set through this proxy, mutations to the original ordered set will be reflected in the proxy and it will appear
	 *             to change spontaneously, since a copy of the ordered set is not being made.
	 **/

	public NSSet<NSObject> set() {
		NSArray<NSObject> array = array();
		NSSet<NSObject> set = set();
		set.initWithObjects(array);
		return set;
	}

	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((wrappedLinkedHashSet == null) ? 0 : wrappedLinkedHashSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NSOrderedSet other = (NSOrderedSet) obj;
		if (wrappedLinkedHashSet == null) {
			if (other.wrappedLinkedHashSet != null)
				return false;
		} else if (!wrappedLinkedHashSet.equals(other.wrappedLinkedHashSet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NSOrderedSet [hSet=" + wrappedLinkedHashSet + "]";
	}

	@Override
	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state,
													   Object[] stackbuf, int len) {
		return 0;
	}

	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		return null;
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public Iterator<NSObject> iterator() {
		return wrappedLinkedHashSet.iterator();
	}

	@Override
	public boolean hasNext() {
		return wrappedLinkedHashSet.iterator().hasNext();
	}

	@Override
	public NSObject next() {
		return wrappedLinkedHashSet.iterator().next();
	}

	@Override
	public void remove() {
		wrappedLinkedHashSet.iterator().remove();
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}