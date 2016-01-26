
package com.myappconverter.java.foundations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import android.util.Log;

import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.NSKeyValueObservingOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.ASCIIPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.java.foundations.utilities._NSFoundationCollection;
import com.myappconverter.java.foundations.utilities._NSFoundationCollection.NullHandling;
import com.myappconverter.mapping.utils.GenericComparator;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.PerformBlock;



public class NSSet<E extends Object> extends NSObject implements NSCopying, NSMutableCopying,
		NSFastEnumeration, NSSecureCoding, Iterable<E>, Iterator<E> {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	protected Set<E> wrappedSet;

	public Set<E> getWrappedSet() {
		return this.wrappedSet;
	}

	public void setWrappedSet(Set<E> mSet) {
		this.wrappedSet = mSet;
	}

	public static final boolean CheckForNull = true;
	public static final NSSet EmptySet = new NSSet();
	public static final boolean IgnoreNull = true;

	protected static final String NULL_NOT_ALLOWED = "Attempt to insert null into an NSSet.";

	// Creating a Set

	/**
	 * @return Return Value A new empty set.
	 * @Signature: set
	 * @Declaration : + (instancetype)set
	 * @Description : Creates and returns an empty set.
	 * @Discussion This method is declared primarily for the use of mutable subclasses of NSSet.
	 **/

	public static <E> NSSet<E> set() {
		return new NSSet<E>();
	}

	public static Object set(Class clazz) {
		NSSet pSet = new NSSet();
		return InstanceTypeFactory.getInstance(pSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), pSet.getWrappedSet());
	}

	/**
	 * @param array An array containing the objects to add to the new set. If the same object appears more than once in array, it is added
	 *            only once to the returned set. Each object receives a retain message as it is added to the set.
	 * @return Return Value A new set containing a uniqued collection of the objects contained in array.
	 * @Signature: setWithArray:
	 * @Declaration : + (instancetype)setWithArray:(NSArray *)array
	 * @Description : Creates and returns a set containing a uniqued collection of the objects contained in a given array.
	 **/

	public static <E> NSSet<E> setWithArray(NSArray<E> array) {
		NSSet<E> nsSet = new NSSet<E>(array.getWrappedList());
		return nsSet;
	}

	public static <E> NSSet<E> setWithArray(Class clazz, NSArray<E> array) {
		NSSet<E> nsSet = new NSSet<E>(array.getWrappedList());
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param object The object to add to the new set. object receives a retain message after being added to the set.
	 * @return Return Value A new set that contains a single member, object.
	 * @Signature: setWithObject:
	 * @Declaration : + (instancetype)setWithObject:(id)object
	 * @Description : Creates and returns a set that contains a single given object.
	 **/

	public static <E> NSSet<E> setWithObject(E object) {
		NSSet<E> nsSet = new NSSet<E>();
		nsSet.wrappedSet.add(object);
		return nsSet;
	}

	public static <E> NSSet<E> setWithObject(Class clazz, E object) {
		NSSet<E> nsSet = new NSSet<E>();
		nsSet.wrappedSet.add(object);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param objects A C array of objects to add to the new set. If the same object appears more than once in objects, it is added only
	 *            once to the returned set. Each object receives a retain message as it is added to the set.
	 * @param cnt The number of objects from objects to add to the new set.
	 * @return Return Value A new set containing cnt objects from the list of objects specified by objects.
	 * @Signature: setWithObjects:count:
	 * @Declaration : + (instancetype)setWithObjects:(const id [])objects count:(NSUInteger)cnt
	 * @Description : Creates and returns a set containing a specified number of objects from a given C array of objects.
	 **/

	public static <E> NSSet<E> setWithObjectsCount(E[] objects, int cnt) {
		NSSet<E> nsSet = new NSSet<E>();
		List<E> mList = Arrays.asList(Arrays.copyOfRange(objects, 0, cnt));
		if (mList != null) {
			Set<E> mSet = new HashSet<E>(mList);
			nsSet.setWrappedSet(mSet);
			return nsSet;
		} else {
			throw new IllegalStateException("can't add object to this collection");
		}
	}

	public static <E> Object setWithObjectsCount(Class clazz, E[] objects, int cnt) {
		NSSet pSet = setWithObjectsCount(objects, cnt);
		return InstanceTypeFactory.getInstance(pSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), pSet.getWrappedSet());
	}

	/**
	 * @param firstObj The first object to add to the new set.
	 * @param firstObj, ... A comma-separated list of objects, ending with nil, to add to the new set. If the same object appears more than
	 *            once in the list of objects, it is added only once to the returned set. Each object receives a retain message as it is
	 *            added to the set.
	 * @return Return Value A new set containing the objects in the argument list.
	 * @Signature: setWithObjects:
	 * @Declaration : + (instancetype)setWithObjects:(id)firstObj, ...
	 * @Description : Creates and returns a set containing the objects in a given argument list.
	 * @Discussion As an example, the following code excerpt creates a set containing three different types of elements (assuming aPath
	 *             exits): NSSet *mySet; NSData *someData = [NSData dataWithContentsOfFile:aPath]; NSValue *aValue = [NSNumber
	 *             numberWithInteger:5]; NSString *aString = @"a string"; mySet = [NSSet setWithObjects:someData, aValue, aString, nil];
	 **/

	public static <E> NSSet<E> setWithObjects(E... firstObj) {
		NSSet<E> nsSet = new NSSet<E>();
		nsSet.wrappedSet.addAll(Arrays.asList(firstObj));
		return nsSet;
	}

	public static <E> NSSet<E> setWithObjects(Class clazz, E... firstObj) {
		NSSet<E> nsSet = new NSSet<E>();
		nsSet.wrappedSet.addAll(Arrays.asList(firstObj));
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param set A set containing the objects to add to the new set. Each object receives a retain message as it is added to the new set.
	 * @return Return Value A new set containing the objects from set.
	 * @Signature: setWithSet:
	 * @Declaration : + (instancetype)setWithSet:(NSSet *)set
	 * @Description : Creates and returns a set containing the objects from another set.
	 **/

	public static <E extends Object> NSSet<E> setWithSet(NSSet<E> set) {
		NSSet<E> nsSet = set();
		nsSet.wrappedSet = new HashSet<E>(set.wrappedSet);
		return nsSet;
	}

	public static <E extends Object> NSSet<E> setWithSet(Class clazz, NSSet<E> set) {
		NSSet<E> nsSet = set();
		nsSet.wrappedSet = new HashSet<E>(set.wrappedSet);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param anObject The object to add to the set.
	 * @return Return Value A new set formed by adding anObject to the receiving set.
	 * @Signature: setByAddingObject:
	 * @Declaration : - (NSSet *)setByAddingObject:(id)anObject
	 * @Description : Returns a new set formed by adding a given object to the receiving set.
	 **/

	public NSSet<E> setByAddingObject(E anObject) {
		NSSet<E> newSet = set();
		newSet.wrappedSet.addAll(this.wrappedSet);
		newSet.wrappedSet.add(anObject);
		return newSet;

	}

	/**
	 * @param other The set of objects to add to the receiving set.
	 * @return Return Value A new set formed by adding the objects in other to the receiving set.
	 * @Signature: setByAddingObjectsFromSet:
	 * @Declaration : - (NSSet *)setByAddingObjectsFromSet:(NSSet *)other
	 * @Description : Returns a new set formed by adding the objects in a given set to the receiving set.
	 **/

	public NSSet<E> setByAddingObjectsFromSet(NSSet<E> other) {
		NSSet<E> newSet = set();
		newSet.wrappedSet.addAll(this.wrappedSet);
		newSet.wrappedSet.addAll(other.wrappedSet);
		return newSet;
	}

	/**
	 * @param other The array of objects to add to the set.
	 * @return Return Value A new set formed by adding the objects in other to the receiving set.
	 * @Declaration : - (NSSet *)setByAddingObjectsFromArray:(NSArray *)other
	 * @Description : Returns a new set formed by adding the objects in a given array to the receiving set.
	 */

	public NSSet<E> setByAddingObjectsFromArray(NSArray<E> other) {
		NSSet<E> newSet = set();
		newSet.wrappedSet.addAll(this.wrappedSet);
		newSet.wrappedSet.addAll(other.wrappedList);
		return newSet;
	}

	// Initializing a Set

	/**
	 * @param array An array of objects to add to the new set. If the same object appears more than once in array, it is represented only
	 *            once in the returned set. Each object receives a retain message as it is added to the set.
	 * @return Return Value An initialized set with the contents of array. The returned set might be different than the original receiver.
	 * @Signature: initWithArray:
	 * @Declaration : - (instancetype)initWithArray:(NSArray *)array
	 * @Description : Initializes a newly allocated set with the objects that are contained in a given array.
	 **/

	public NSSet<E> initWithArray(NSArray<E> array) {
		return setWithArray(array);
	}

	public NSSet<E> initWithArray(Class clazz, NSArray<E> array) {
		NSSet<E> nsSet = setWithArray(array);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param objects A C array of objects to add to the new set. If the same object appears more than once in objects, it is added only
	 *            once to the returned set. Each object receives a retain message as it is added to the set.
	 * @param cnt The number of objects from objects to add to the new set.
	 * @return Return Value An initialized set containing cnt objects from the list of objects specified by objects. The returned set might
	 *         be different than the original receiver.
	 * @Signature: initWithObjects:count:
	 * @Declaration : - (instancetype)initWithObjects:(const id [])objects count:(NSUInteger)cnt
	 * @Description : Initializes a newly allocated set with a specified number of objects from a given C array of objects.
	 * @Discussion This method is a designated initializer for NSSet.
	 **/

	public NSSet<E> initWithObjectsCount(E[] objects, int cnt) {
		return setWithObjectsCount(objects, cnt);
	}

	public NSSet<E> initWithObjectsCount(Class clazz, E[] objects, int cnt) {
		NSSet<E> nsSet = setWithObjectsCount(objects, cnt);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param set A set containing objects to add to the receiving set. Each object is retained as it is added.
	 * @return Return Value An initialized objects set containing the objects from set. The returned set might be different than the
	 *         original receiver.
	 * @Signature: initWithSet:
	 * @Declaration : - (instancetype)initWithSet:(NSSet *)set
	 * @Description : Initializes a newly allocated set and adds to it objects from another given set.
	 **/

	public NSSet<E> initWithSet(NSSet<E> set) {
		return setWithSet(set);
	}

	public NSSet<E> initWithSet(Class clazz, NSSet<E> set) {
		NSSet<E> nsSet = setWithSet(set);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param firstObj The first object to add to the new set.
	 * @param firstObj, ... A comma-separated list of objects, ending with nil, to add to the new set. If the same object appears more than
	 *            once in the list, it is represented only once in the returned set. Each object receives a retain message as it is added to
	 *            the set
	 * @return Return Value An initialized set containing the objects specified in the parameter list. The returned set might be different
	 *         than the original receiver.
	 * @Signature: initWithObjects:
	 * @Declaration : - (instancetype)initWithObjects:(id)firstObj, ...
	 * @Description : Initializes a newly allocated set with members taken from the specified list of objects.
	 **/

	public NSSet<E> initWithObjects(E... firstObj) {
		return setWithObjects(firstObj);
	}

	public NSSet<E> initWithObjects(Class clazz, E... firstObj) {
		NSSet<E> nsSet = setWithObjects(firstObj);
		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @param set A set containing objects to add to the new set.
	 * @param flag If YES, each object in set receives a copyWithZone: message to create a copy of the object—objects must conform to the
	 *            NSCopying protocol. In a managed memory environment, this is instead of the retain message the object would otherwise
	 *            receive. The object copy is then added to the returned set. If NO, then in a managed memory environment each object in set
	 *            simply receives a retain message when it is added to the returned set.
	 * @return Return Value An initialized set that contains the members of set. The returned set might be different than the original
	 *         receiver.
	 * @Signature: initWithSet:copyItems:
	 * @Declaration : - (instancetype)initWithSet:(NSSet *)set copyItems:(BOOL)flag
	 * @Description : Initializes a newly allocated set and adds to it members of another given set.
	 * @Discussion After an immutable s has been initialized in this way, it cannot be modified. The copyWithZone: method performs a shallow
	 *             copy. If you have a collection of arbitrary depth, passing YES for the flag parameter will perform an immutable copy of
	 *             the first level below the surface. If you pass NO the mutability of the first level is unaffected. In either case, the
	 *             mutability of all deeper levels is unaffected.
	 **/

	public NSSet<E> initWithSetCopyItems(NSSet<E> set, boolean flag) {
		if (!flag)
			return setWithSet(set);
		else {
			NSSet<E> nsSet = new NSSet<E>();
			for (E elm : set.wrappedSet) {
				nsSet.wrappedSet.add(elm);
			}
			return nsSet;
		}

	}

	public NSSet<E> initWithSetCopyItems(Class clazz, NSSet<E> set, boolean flag) {
		NSSet<E> nsSet = initWithSetCopyItems(set, flag);

		return (NSSet<E>) InstanceTypeFactory.getInstance(nsSet, NSSet.class, clazz,
				new NSString("setWrappedSet"), nsSet.getWrappedSet());
	}

	/**
	 * @return Return Value A set.
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated set.
	 * @Discussion This method is a designated initializer of NSSet.
	 **/
	@Override

	public NSSet<E> init() {
		wrappedSet = new HashSet<E>();
		return this;
	}

	public NSSet<E> init(Class clazz) {
		wrappedSet = new HashSet<E>();

		return (NSSet<E>) InstanceTypeFactory.getInstance(this, NSSet.class, clazz,
				new NSString("setWrappedSet"), this.getWrappedSet());
	}

	// Counting Entries

	/**
	 * @return Return Value The number of members in the set.
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of members in the set.
	 **/

	public int count() {
		return wrappedSet.size();
	}

	// Accessing Set Members

	/**
	 * @return Return Value An array containing the set’s members, or an empty array if the set has no members. The order of the objects in
	 *         the array isn’t defined.
	 * @Declaration : - (NSArray *)allObjects
	 * @Description : Returns an array containing the set’s members, or an empty array if the set has no members.
	 */

	public NSArray<E> allObjects() {
		NSArray<E> array = new NSArray<E>();
		array.wrappedList.addAll(wrappedSet);
		return array;
	}

	/**
	 * @return Return Value One of the objects in the set, or nil if the set contains no objects. The object returned is chosen at the set’s
	 *         convenience—the selection is not guaranteed to be random.
	 * @Signature: anyObject
	 * @Declaration : - (id)anyObject
	 * @Description : Returns one of the objects in the set, or nil if the set contains no objects.
	 **/

	public Object anyObject() {
		if (!wrappedSet.isEmpty()) {
			return wrappedSet.toArray()[(int) (Math.random() * wrappedSet.size())];
		}
		return null;
	}

	/**
	 * @param anObject The object for which to test membership of the set.
	 * @return Return Value YES if anObject is present in the set, otherwise NO.
	 * @Signature: containsObject:
	 * @Declaration : - (BOOL)containsObject:(id)anObject
	 * @Description : Returns a Boolean value that indicates whether a given object is present in the set.
	 **/

	public boolean containsObject(Object anObject) {
		return wrappedSet.contains(anObject);
	}

	/**
	 * @param predicate A predicate.
	 * @return Return Value A new set containing the objects in the receiving set for which predicate returns true.
	 * @Declaration : - (NSSet *)filteredSetUsingPredicate:(NSPredicate *)predicate
	 * @Description : Evaluates a given predicate against each object in the receiving set and returns a new set containing the objects for
	 *              which the predicate returns true.
	 */

	public NSSet<E> filteredSetUsingPredicate(NSPredicate predicate) {
		NSSet<E> tmpSet = set();
		for (E anObject : getWrappedSet()) {
			if (predicate.evaluateWithObject(anObject))
				tmpSet.wrappedSet.add(anObject);
		}
		return tmpSet;
	}

	/**
	 * @param aSelector A selector that specifies the message to send to the members of the set. The method must not take any arguments. It
	 *            should not have the side effect of modifying the set. This value must not be NULL.
	 * @Declaration : - (void)makeObjectsPerformSelector:(SEL)aSelector
	 * @Description : Sends a message specified by a given selector to each object in the set.
	 * @Discussion The message specified by aSelector is sent once to each member of the set. This method raises an
	 *             NSInvalidArgumentException if aSelector is NULL.
	 */

	public void makeObjectsPerformSelector(SEL aSelector) {
		for (Object anObject : wrappedSet) {
			Method[] methods = anObject.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				final Method selfMethod = methods[i];
				if (selfMethod.getName().equals(aSelector)) {
					try {
						selfMethod.invoke(anObject);
					} catch (IllegalAccessException e) {
						Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
								+ Log.getStackTraceString(e));
					} catch (IllegalArgumentException e) {
						Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
								+ Log.getStackTraceString(e));
					} catch (InvocationTargetException e) {
						Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
								+ Log.getStackTraceString(e));
					}
				}
			}
		}
	}

	/**
	 * @param aSelector A selector that specifies the message to send to the set's members. The method must take a single argument of type
	 *            id. The method should not, as a side effect, modify the set. The value must not be NULL.
	 * @param argument The object to pass as an argument to the method specified by aSelector.
	 * @Signature: makeObjectsPerformSelector:withObject:
	 * @Declaration : - (void)makeObjectsPerformSelector:(SEL)aSelector withObject:(id)argument
	 * @Description : Sends a message specified by a given selector to each object in the set.
	 * @Discussion The message specified by aSelector is sent, with argument as the argument, once to each member of the set. This method
	 *             raises an NSInvalidArgumentException if aSelector is NULL.
	 **/

	public void makeObjectsPerformSelectorWithObject(SEL aSelector, E argument) {
		for (E anObject : wrappedSet) {
			Method[] methods = anObject.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				final Method selfMethod = methods[i];
				if (selfMethod.getName().equals(aSelector)) {
					Class[] parameterTypes = selfMethod.getParameterTypes();
					if (argument != null) {
						if (!parameterTypes[0].isAssignableFrom(argument.getClass())) {
							throw new RuntimeException("unkonw type");
						}
						try {
							selfMethod.invoke(anObject);
						} catch (IllegalAccessException e) {
							Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
									+ Log.getStackTraceString(e));
						} catch (IllegalArgumentException e) {
							Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
									+ Log.getStackTraceString(e));
						} catch (InvocationTargetException e) {
							Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
									+ Log.getStackTraceString(e));
						}

					}
				}

			}

		}
	}

	/**
	 * @param object The object for which to test for membership of the set.
	 * @return Return Value If the set contains an object equal to object (as determined by isEqual:) then that object (typically this will
	 *         be object), otherwise nil.
	 * @Signature: member:
	 * @Declaration : - (id)member:(id)object
	 * @Description : Determines whether the set contains an object equal to a given object, and returns that object if it is present.
	 * @Discussion If you override isEqual:, you must also override the hash method for the member: method to work on a set of objects of
	 *             your class.
	 **/

	public E member(NSObject anObject) {
		for (E element : getWrappedSet()) {
			if (element.equals(anObject))
				return element;
		}
		return null;
	}

	/**
	 * @return Return Value An enumerator object that lets you access each object in the set.
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the set.
	 */

	public NSEnumerator<E> objectEnumerator() {
		return new NSEnumerator<E>(wrappedSet.iterator());
	}

	/**
	 * @param block The Block to apply to elements in the set. The Block takes two arguments: obj The element in the set. stop A reference
	 *            to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an
	 *            out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value that
	 *            indicates whether obj passed the test.
	 * @param obj The element in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Signature: enumerateObjectsUsingBlock:
	 * @Declaration : - (void)enumerateObjectsUsingBlock:(void (^)(id obj, BOOL *stop))block
	 * @Description : Executes a given Block using each object in the set.
	 **/

	public void enumerateObjectsUsingBlock(PerformBlock.VoidBlockIdBOOL block) {
		for (Object anObject : wrappedSet) {
			if (!block.perform(anObject, ""))
				break;
		}
	}

	/**
	 * @param opts A bitmask that specifies the options for the enumeration.
	 * @param block The Block to apply to elements in the set. The Block takes two arguments: obj The element in the set. stop A reference
	 *            to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an
	 *            out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value that
	 *            indicates whether obj passed the test.
	 * @param obj The element in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Signature: enumerateObjectsWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateObjectsWithOptions:(NSEnumerationOptions)opts usingBlock:(void (^)(id obj, BOOL *stop))block
	 * @Description : Executes a given Block using each object in the set, using the specified enumeration options.
	 **/

	public void enumerateObjectsWithOptionsUsingBlock(int option,
													  PerformBlock.VoidBlockIdBOOL block) {

		if (option == NSObjCRuntime.NSEnumerationConcurrent) {
			for (Object anObject : wrappedSet) {
				if (!block.perform(anObject, ""))
					break;
			}
		} else if (option == NSObjCRuntime.NSEnumerationReverse) {
			List<E> myTmpList = new ArrayList<E>(); // mainList.addAll(set);
			myTmpList.addAll(wrappedSet);

			Collections.reverse(myTmpList);

			for (Object anObject : myTmpList) {
				if (!block.perform(anObject, ""))
					break;
			}

		}
	}

	/**
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the set. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param obj The element in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value An NSSet containing objects that pass the test.
	 * @Signature: objectsPassingTest:
	 * @Declaration : - (NSSet *)objectsPassingTest:(BOOL (^)(id obj, BOOL *stop))predicate
	 * @Description : Returns a set of object that pass a test in a given Block.
	 **/

	public NSSet<E> objectsPassingTest(PerformBlock.VoidBlockIdBOOL block) {
		NSSet<E> tmpSet = new NSSet<E>();
		for (E anObject : wrappedSet) {
			if (block.perform(anObject, ""))
				tmpSet.wrappedSet.add(anObject);
		}
		return tmpSet;
	}

	/**
	 * @param opts A bitmask that specifies the options for the enumeration.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: obj The element in the set. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param obj The element in the set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value An NSSet containing objects that pass the test.
	 * @Signature: objectsWithOptions:passingTest:
	 * @Declaration : - (NSSet *)objectsWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(id obj, BOOL *stop))predicate
	 * @Description : Returns a set of object that pass a test in a given Block, using the specified enumeration options.
	 **/

	public NSSet<E> objectsWithOptionsPassingTest(int option, Object obj, boolean stop,
												  PerformBlock.VoidBlockIdBOOL predicate) {
		NSSet<E> tmpSet = new NSSet<E>();
		if (option == NSObjCRuntime.NSEnumerationConcurrent) {
			for (E anObject : wrappedSet) {
				if (predicate.perform(anObject, ""))
					tmpSet.wrappedSet.add(anObject);
			}
		} else if (option == NSObjCRuntime.NSEnumerationReverse) {
			List<E> myTmpList = new ArrayList<E>();
			myTmpList.addAll(wrappedSet);
			Collections.reverse(myTmpList);
			for (E anObject : myTmpList) {
				if (predicate.perform(anObject, ""))
					tmpSet.getWrappedSet().add(anObject);
			}
		}
		return tmpSet;
	}

	// Comparing Sets

	/**
	 * @param otherSet The set with which to compare the receiving set.
	 * @return Return Value YES if every object in the receiving set is also present in otherSet, otherwise NO.
	 * @Declaration : - (BOOL)isSubsetOfSet:(NSSet *)otherSet
	 * @Description : Returns a Boolean value that indicates whether every object in the receiving set is also present in another given set.
	 * @Discussion Object equality is tested using isEqual:.
	 */

	public boolean isSubsetOfSet(NSSet<?> otherSet) {
		for (Object o : wrappedSet) {
			if (!otherSet.getWrappedSet().contains(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param otherSet The set with which to compare the receiving set.
	 * @return Return Value YES if at least one object in the receiving set is also present in otherSet, otherwise NO.
	 * @Declaration : - (BOOL)intersectsSet:(NSSet *)otherSet
	 * @Description : Returns a Boolean value that indicates whether at least one object in the receiving set is also present in another
	 *              given set.
	 * @Discussion Object equality is tested using isEqual:.
	 */

	public boolean intersectsSet(NSSet<?> otherSet) {
		for (Object o : otherSet.getWrappedSet()) {
			if (containsObject(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param otherSet The set with which to compare the receiving set.
	 * @return Return Value YES if the contents of otherSet are equal to the contents of the receiving set, otherwise NO.
	 * @Declaration : - (BOOL)isEqualToSet:(NSSet *)otherSet
	 * @Description : Compares the receiving set to another set.
	 * @Discussion Two sets have equal contents if they each have the same number of members and if each member of one set is present in the
	 *             other. Object equality is tested using isEqual:.
	 */

	public boolean isEqualToSet(NSSet<?> otherSet) {
		if (otherSet.wrappedSet.size() != wrappedSet.size()) {
			return false;
		} else {
			for (Object obj : wrappedSet) {
				if (!otherSet.wrappedSet.contains(obj))
					return false;
			}
			return true;
		}
	}

	/**
	 * @param key The name of one of the properties of the receiving set's members.
	 * @return Return Value A set containing the results of invoking valueForKey: (with the argument key) on each of the receiving set's
	 *         members.
	 * @Signature: valueForKey:
	 * @Declaration : - (id)valueForKey:(NSString *)key
	 * @Description : Return a set containing the results of invoking valueForKey: on each of the receiving set's members.
	 * @Discussion The returned set might not have the same number of members as the receiving set. The returned set will not contain any
	 *             elements corresponding to instances of valueForKey: returning nil (note that this is in contrast with NSArray’s
	 *             implementation, which may put NSNull values in the arrays it returns).
	 **/

	public E valueForKey(NSString key) {
		for (E obj : getWrappedSet()) {
			try {
				// FIXME
				obj.getClass().getDeclaredField(key.getWrappedString());
				return obj;
			} catch (NoSuchFieldException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return null;
	}

	/**
	 * @param value The value for the property identified by key.
	 * @param key The name of one of the properties of the set's members.
	 * @Signature: setValue:forKey:
	 * @Declaration : - (void)setValue:(id)value forKey:(NSString *)key
	 * @Description : Invokes setValue:forKey: on each of the set’s members.
	 **/

	public void setValueForKey(NSObject value, NSString key) {
		for (E obj : wrappedSet) {
			Field myField;
			try {
				myField = obj.getClass().getDeclaredField(key.getWrappedString());
				myField.set(obj, value);
			} catch (IllegalArgumentException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IllegalAccessException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (NoSuchFieldException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}

		}
	}

	// Creating a Sorted Array

	/**
	 * @param <T>
	 * @param sortDescriptors An array of NSSortDescriptor objects.
	 * @return Return Value An NSArray containing the set’s content sorted as specified by sortDescriptors.
	 * @Declaration : - (NSArray *)sortedArrayUsingDescriptors:(NSArray *)sortDescriptors
	 * @Description : Returns an array of the set’s content sorted as specified by a given array of sort descriptors.
	 * @Discussion The first descriptor specifies the primary key path to be used in sorting the set’s contents. Any subsequent descriptors
	 *             are used to further refine sorting of objects with duplicate values. See NSSortDescriptor for additional information.
	 */

	public NSArray<E> sortedArrayUsingDescriptors(NSArray<NSSortDescriptor> sortDescriptors) {

		List<E> elements = new ArrayList<E>();
		for (NSSortDescriptor sortD : sortDescriptors.wrappedList) {
			elements = (List<E>) Arrays.asList(wrappedSet.toArray());
			Collections.sort(elements, new GenericComparator(sortD.key().getWrappedString(), true));
		}
		NSArray<E> array = new NSArray<E>();
		array.setWrappedList(elements);

		return array;
	}

	// Key-Value Observing

	/**
	 * @param observer The object to register for KVO notifications. The observer must implement the key-value observing method
	 *            observeValueForKeyPath:ofObject:change:context:.
	 * @param keyPath The key path, relative to the set, of the property to observe. This value must not be nil.
	 * @param options A combination of the NSKeyValueObservingOptions values that specifies what is included in observation notifications.
	 *            For possible values, see NSKeyValueObservingOptions.
	 * @param context Arbitrary data that is passed to observer in observeValueForKeyPath:ofObject:change:context:.
	 * @Signature: addObserver:forKeyPath:options:context:
	 * @Declaration : - (void)addObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath options:(NSKeyValueObservingOptions)options
	 *              context:(void *)context
	 * @Description : Raises an exception.
	 **/

	public void addObserverForKeyPathOptionsContext(E observer, NSString keyPath,
													NSKeyValueObservingOptions options, NSObject context) {

	}

	/**
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the set, for which observer is registered to receive KVO change notifications. This value must
	 *            not be nil.
	 * @Signature: removeObserver:forKeyPath:
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath
	 * @Description : Raises an exception.
	 **/

	public void removeObserverForKeyPath(E observer, NSString keyPath) {
	}

	/**
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the set, for which observer is registered to receive KVO change notifications. This value must
	 *            not be nil.
	 * @param context Arbitrary data that is passed to observer in observeValueForKeyPath:ofObject:change:context:.
	 * @Signature: removeObserver:forKeyPath:context:
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath context:(void *)context
	 * @Description : Raises an exception.
	 **/

	public void removeObserverForKeyPathContext(E observer, NSString keyPath, NSObject context) {

	}

	// Describing a Set

	/**
	 * @return Return Value A string that represents the contents of the set, formatted as a property list.
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns a string that represents the contents of the set, formatted as a property list.
	 **/
	@Override

	public NSString description() {
		return new NSString(wrappedSet.toString());
	}

	/**
	 * @param locale On iOS and OS X v10.5 and later, either an instance of NSDictionary or an NSLocale object may be used for locale.On OS
	 *            X v10.4 and earlier it must be an instance of NSDictionary.
	 * @return Return Value A string that represents the contents of the set, formatted as a property list.
	 * @Signature: descriptionWithLocale:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale
	 * @Description : Returns a string that represents the contents of the set, formatted as a property list.
	 * @Discussion This method sends each of the set’s members descriptionWithLocale: with locale passed as the sole parameter. If the set’s
	 *             members do not respond to descriptionWithLocale:, this method sends description instead.
	 **/


	public NSString descriptionWithLocale(NSLocale locale) {
		return new NSString(wrappedSet.toString());
	}

	// implemented Protocols

	@Override
	public boolean supportsSecureCoding() {
		return false;
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
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	// Added

	public NSSet() {
		_initializeWithCapacity(0);
	}

	protected NSSet(int capacity) {
		_initializeWithCapacity(capacity);
	}

	public NSSet(Collection<? extends E> collection) {
		if (collection == null)
			throw new IllegalArgumentException("objects may not be null");

		_initializeWithCollection(collection, NullHandling.CheckAndFail);
	}

	public NSSet(E object) {
		if (object == null)
			throw new IllegalArgumentException("object may not be null");
		_initializeWithCapacity(1).add(object);
	}

	public NSSet(E... objects) {
		if (objects == null)
			throw new IllegalArgumentException("objects may not be null");

		_initializeWithObjects(objects, NullHandling.CheckAndFail);
	}

	public NSSet(NSArray<? extends E> objects) {
		if (objects == null)
			throw new IllegalArgumentException("objects may not be null");
		_initializeWithCollection(objects.getWrappedList(), NullHandling.CheckAndFail);

	}

	public NSSet(NSSet<? extends E> otherSet) {
		this(otherSet.getWrappedSet(), false);
	}

	public NSSet(Set<? extends E> set, boolean ignoreNull) {
		if (set == null)
			throw new IllegalArgumentException("set may not be null");

		_initializeWithCollection(set,
				ignoreNull ? NullHandling.CheckAndSkip : NullHandling.CheckAndFail);
	}

	protected Set<E> setNoCopy() {
		return wrappedSet;
	}

	protected Set<E> _setSet(Set<E> set) {
		return wrappedSet = set;
	}

	protected Set<E> _initializeWithCapacity(int capacity) {
		Set<E> set = new HashSet<E>(capacity);
		_setSet(Collections.unmodifiableSet(set));
		return set;
	}

	protected void _initializeWithObjects(E[] objects, NullHandling nullHandling) {
		Set<E> store = _initializeWithCapacity(objects.length);
		if (nullHandling == NullHandling.NoCheck) {
			store.addAll(Arrays.asList(objects));
			return;
		}
		for (E e : objects) {
			if (e == null) {
				if (nullHandling == NullHandling.CheckAndFail)
					throw new IllegalArgumentException(NULL_NOT_ALLOWED);
			} else {
				store.add(e);
			}
		}
	}

	protected void _initializeWithCollection(Collection<? extends E> collection,
											 NullHandling nullHandling) {
		if (collection == null) {
			_initializeWithCapacity(0);
			return;
		}
		Set<E> store = _initializeWithCapacity(collection.size());
		if (nullHandling == NullHandling.NoCheck || collection instanceof _NSFoundationCollection) {
			store.addAll(collection);
			return;
		}
		for (E e : collection) {
			if (e == null) {
				if (nullHandling == NullHandling.CheckAndFail)
					throw new IllegalArgumentException(NULL_NOT_ALLOWED);
				continue;
			}
			store.add(e);
		}
	}

	public static <E> NSSet<E> asSet(NSSet<E> set) {
		return asSet(set, NullHandling.CheckAndFail);
	}

	public static <E> NSSet<E> asSet(NSSet<E> set, NullHandling nullHandling) {
		if (set == null || set.size() == 0)
			return emptySet();
		if (set.getClass() == NSSet.class)
			return new NSSet<E>(set);
		return _initializeNSSetWithSet(new NSSet<E>(),
				new NSSet<E>(Collections.unmodifiableSet(set.getWrappedSet())), nullHandling);
	}

	public static <E> NSMutableSet<E> asMutableSet(NSSet<E> set) {
		return asMutableSet(set, NullHandling.CheckAndFail);
	}

	public static <E> NSMutableSet<E> asMutableSet(NSSet<E> set, NullHandling nullHandling) {
		if (set == null)
			return new NSMutableSet<E>();
		if (set.getClass() == NSMutableSet.class)
			return new NSMutableSet<E>(set);
		return _initializeNSSetWithSet(new NSMutableSet<E>(), set, nullHandling);
	}

	protected static <E, T extends NSSet<E>> T _initializeNSSetWithSet(T nsset, NSSet<E> set,
																	   NullHandling nullHandling) {
		if (set == null)
			throw new IllegalArgumentException("set may not be null");

		if (nullHandling != NullHandling.NoCheck && set.size() > 0) {
			try {
				if (set.getWrappedSet().contains(null)) {
					if (nullHandling == NullHandling.CheckAndFail)
						throw new IllegalArgumentException(NULL_NOT_ALLOWED);
					nsset._initializeWithCollection(set.getWrappedSet(), nullHandling);
					return nsset;
				}
			} catch (NullPointerException e) {
				// Must not support nulls either
				LOGGER.info(String.valueOf(e));
			}
		}
		nsset._setSet((Set<E>) set);

		return nsset;
	}

	@Override
	public NSSet<E> clone() {
		return this;
	}


	public static <T> NSSet<T> emptySet() {
		return EmptySet;
	}

	public int _shallowHashCode() {
		return NSSet.class.hashCode();
	}

	@Override
	public int hashCode() {
		return _shallowHashCode() ^ count();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this || obj == setNoCopy())
			return true;
		if (obj instanceof NSSet<?> && setNoCopy() == ((NSSet<?>) obj).setNoCopy())
			return true;
		return super.equals(obj);
	}

	public HashSet<E> hashSet() {
		return new HashSet<E>(wrappedSet);
	}

	public NSSet<E> immutableClone() {
		return this;
	}

	public Object member(Object object) {
		if (!wrappedSet.contains(object))
			return null;

		for (Object element : wrappedSet) {
			if (element.equals(object))
				return element;
		}
		return null;
	}

	public NSMutableSet<E> mutableClone() {
		return new NSMutableSet<E>(wrappedSet);
	}

	public int size() {
		return setNoCopy().size();
	}

	public NSSet<E> setByIntersectingSet(NSSet<?> otherSet) {
		NSMutableSet<E> result = new NSMutableSet<E>();

		for (E e : wrappedSet) {
			if (otherSet.getWrappedSet().contains(e)) {
				result.getWrappedSet().add(e);
			}
		}

		return result;
	}

	public NSSet<E> setBySubtractingSet(NSSet<?> otherSet) {
		NSSet<E> result = mutableClone();
		result.getWrappedSet().removeAll(otherSet.getWrappedSet());
		return result;
	}

	public NSSet<E> setByUnioningSet(NSSet<? extends E> otherSet) {
		NSSet<E> result = mutableClone();
		result.getWrappedSet().addAll(otherSet.getWrappedSet());
		return result;
	}

	public boolean isEmpty() {
		return wrappedSet.isEmpty();
	}

	private boolean ordered = false;

	/**
	 * Creates an empty set.
	 *
	 * @param ordered Indicates whether the created set should be ordered or unordered.
	 * @see java.util.LinkedHashSet
	 * @see java.util.TreeSet
	 */
	public NSSet(boolean ordered) {
		this.ordered = ordered;
		if (!ordered)
			wrappedSet = (Set<E>) new LinkedHashSet<NSObject>();
		else
			wrappedSet = (Set<E>) new TreeSet<NSObject>();
	}

	/**
	 * Create a set and fill it with the given objects.
	 *
	 * @param objects The objects to populate the set.
	 * @see java.util.LinkedHashSet
	 */
	public NSSet(NSObject... objects) {
		wrappedSet = (Set<E>) new LinkedHashSet<NSObject>();
		wrappedSet.addAll((Collection<? extends E>) Arrays.asList(objects));
	}

	/**
	 * Create a set and fill it with the given objects.
	 *
	 * @param objects The objects to populate the set.
	 * @see java.util.LinkedHashSet
	 * @see java.util.TreeSet
	 */
	public NSSet(boolean ordered, NSObject... objects) {
		this.ordered = ordered;
		if (!ordered)
			wrappedSet = (Set<E>) new LinkedHashSet<NSObject>();
		else
			wrappedSet = (Set<E>) new TreeSet<NSObject>();
		wrappedSet.addAll((Collection<? extends E>) Arrays.asList(objects));
	}

	/**
	 * Adds an object to the set.
	 *
	 * @param obj The object to add.
	 * @param object The object to add to the set.
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)object
	 * @Description : Adds a given object to the set, if it is not already a member.
	 **/

	public synchronized void addObject(E obj) {
		wrappedSet.add((E) obj);
	}

	/**
	 * Removes an object from the set.
	 *
	 * @param obj The object to remove.
	 */
	public synchronized void removeObject(NSObject obj) {
		wrappedSet.remove(obj);
	}

	/**
	 * Finds out whether a given object is contained in the set.
	 *
	 * @param obj The object to look for.
	 * @return <code>true</code>, when the object was found, <code>false</code> otherwise.
	 */
	public boolean containsObject(NSObject obj) {
		return wrappedSet.contains(obj);
	}

	/**
	 * Returns an iterator object that lets you iterate over all elements of the set. This is the equivalent to
	 * <code>objectEnumerator</code> in the Cocoa implementation of NSSet.
	 *
	 * @return The iterator for the set.
	 */
	public synchronized Iterator<NSObject> objectIterator() {
		return (Iterator<NSObject>) wrappedSet.iterator();
	}

	/**
	 * Gets the underlying data structure in which this NSSets stores its content.
	 *
	 * @return A Set object.
	 */
	public Set<NSObject> getSet() {
		return (Set<NSObject>) wrappedSet;
	}

	/**
	 * Returns the XML representantion for this set. There is no official XML representation specified for sets. In this implementation it
	 * is represented by an array.
	 *
	 * @param xml The XML StringBuilder
	 * @param level The indentation level
	 */
	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<array>");
		xml.append("\n");
		for (E o : wrappedSet) {
			((NSObject) o).toXML(xml, level + 1);
			xml.append("\n");
		}
		indent(xml, level);
		xml.append("</array>");
	}

	@Override
	public void assignIDs(BinaryPropertyListWriter out) {
		super.assignIDs(out);
		for (E obj : wrappedSet) {
			((NSObject) obj).assignIDs(out);
		}
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		if (ordered) {
			out.writeIntHeader(0xB, wrappedSet.size());
		} else {
			out.writeIntHeader(0xC, wrappedSet.size());
		}
		for (E obj : wrappedSet) {
			out.writeID(out.getID((NSObject) obj));
		}
	}

	/**
	 * Returns the ASCII representation of this set. There is no official ASCII representation for sets. In this implementation sets are
	 * represented as arrays.
	 *
	 * @param ascii
	 * @param level
	 */
	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		NSArray<E> array = allObjects();
		ascii.append(ASCIIPropertyListParser.ARRAY_BEGIN_TOKEN);
		int indexOfLastNewLine = ascii.lastIndexOf("\n");
		for (int i = 0; i < array.count(); i++) {
			Class<?> objClass = array.getWrappedList().get(i).getClass();
			if ((objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) && indexOfLastNewLine != ascii.length()) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
				((NSObject) array.getWrappedList().get(i)).toASCII(ascii, level + 1);
			} else {
				if (i != 0)
					ascii.append(" ");
				((NSObject) array.getWrappedList().get(i)).toASCII(ascii, 0);
			}

			if (i != array.count() - 1)
				ascii.append(ASCIIPropertyListParser.ARRAY_ITEM_DELIMITER_TOKEN);

			if (ascii.length() - indexOfLastNewLine > 80) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
			}
		}
		ascii.append(ASCIIPropertyListParser.ARRAY_END_TOKEN);
	}

	/**
	 * Returns the ASCII representation of this set according to the GnuStep format. There is no official ASCII representation for sets. In
	 * this implementation sets are represented as arrays.
	 *
	 * @param ascii
	 * @param level
	 */
	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		indent(ascii, level);
		NSArray<E> array = allObjects();
		ascii.append(ASCIIPropertyListParser.ARRAY_BEGIN_TOKEN);
		int indexOfLastNewLine = ascii.lastIndexOf("\n");
		for (int i = 0; i < array.count(); i++) {
			Class<?> objClass = array.getWrappedList().get(i).getClass();
			if ((objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) && indexOfLastNewLine != ascii.length()) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
				((NSObject) array.getWrappedList().get(i)).toASCIIGnuStep(ascii, level + 1);
			} else {
				if (i != 0)
					ascii.append(" ");
				((NSObject) array.getWrappedList().get(i)).toASCIIGnuStep(ascii, 0);
			}

			if (i != array.count() - 1)
				ascii.append(ASCIIPropertyListParser.ARRAY_ITEM_DELIMITER_TOKEN);

			if (ascii.length() - indexOfLastNewLine > 80) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
			}
		}
		ascii.append(ASCIIPropertyListParser.ARRAY_END_TOKEN);
	}

	@Override
	public boolean hasNext() {
		return getWrappedSet().iterator().hasNext();
	}

	@Override
	public E next() {
		return getWrappedSet().iterator().next();
	}

	@Override
	public void remove() {
		getWrappedSet().iterator().remove();
	}

	@Override
	public Iterator<E> iterator() {
		return getWrappedSet().iterator();
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}