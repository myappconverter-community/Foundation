
package com.myappconverter.java.foundations;

import com.myappconverter.java.corefoundations.CFArrayRef;
import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.java.foundations.functions.NSLog;
import com.myappconverter.java.foundations.kvc.NSKeyValueCoding;
import com.myappconverter.java.foundations.kvc.NSKeyValueCodingAdditions;
import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.NSKeyValueObservingOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.ASCIIPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.java.foundations.utilities.IntrospectionUtil;
import com.myappconverter.java.foundations.utilities.MACFuntionPointer;
import com.myappconverter.java.foundations.utilities.NSForwardException;
import com.myappconverter.java.foundations.utilities.NSTimestamp;
import com.myappconverter.java.foundations.utilities.PropertyListFormatException;
import com.myappconverter.java.foundations.utilities.PropertyListParser;
import com.myappconverter.java.foundations.utilities._NSCollectionPrimitives;
import com.myappconverter.java.foundations.utilities._NSFoundationCollection;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.InvokableHelper;
import com.myappconverter.mapping.utils.PerformBlock;
import com.myappconverter.mapping.utils.SerializationUtil;

import org.xml.sax.SAXException;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

// NSKeyValueCoding.h,NThrowableSKeyValueObserving.h,
// NSPathUtilities.h,NSPredicate.h,NSSortDescriptor.h

public class NSArray<E> extends NSObject implements NSCopying, NSMutableCopying, NSFastEnumeration,
		NSSecureCoding, Iterator<E>, Iterable<E>, Serializable {

	// Enum
	
	public enum NSBinarySearchingOptions {
		NSBinarySearchingFirstEqual(1 << 8), //
		NSBinarySearchingLastEqual(1 << 9), //
		NSBinarySearchingInsertionIndex(1 << 10);//
		private int value;

		NSBinarySearchingOptions(int v) {
			value = v;
		}

		public int getValue() {
			return value;
		}
	}

	protected List<E> wrappedList;

	protected static Class<?> isa = NSArray.class;

	public static Class<?> __class() {
		return isa;
	}

	public List<E> getWrappedList() {
		return wrappedList;
	}

	public void setWrappedList(List<E> list) {
		this.wrappedList = new ArrayList<E>(list);
	}

	public Object instanceType() {

		try {
			return this.getClass().newInstance();
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	public NSArray(int size) {
		this.wrappedList = new ArrayList<E>(size);
	}

	public NSArray() {
		this.wrappedList = new ArrayList<E>();
	}

	public NSArray(NSArray<E> array) {
		this(array.getWrappedList());
	}

	public NSArray(List<E> anArray) {
		this.wrappedList = new ArrayList<>(anArray);
	}

	// Creating an Array

	public NSArray(E[] array) {
		this.wrappedList = new ArrayList();
		for (E nsObject : array) {
			this.wrappedList.add((E) nsObject);
		}
	}

	/**
	 * @Declaration: + (instancetype)array Return Value An empty array.
	 * @return : An empty array.
	 * @Discussion : This method is used by mutable subclasses of NSArray.
	 */
	
	public static <E> NSArray<E> array(Class<?> clazz) {
		NSArray array = (NSArray) InstanceTypeFactory.getInstance(clazz);
		array.setWrappedList(new ArrayList());
		return array;
	}

	/**
	 * @Declaration : +  (instancetype)arrayWithArray:(NSArray *)anArray
	 * @Description : Creates and returns an array containing the objects in another given array.
	 * @param anArray An array.
	 * @return Return Value An array containing the objects in anArray.
	 **/

	
	public static <E> NSArray<E> arrayWithArray(Class clazz, NSArray<E> anArray) {
		NSArray<E> arrayResult = (NSArray<E>) InstanceTypeFactory.getInstance(clazz);
		arrayResult.wrappedList = new ArrayList<>(anArray.wrappedList);
		return arrayResult;
	}

	
	public static <E> NSArray<E> arrayWithCapacity(Class clazz, int capacity) {
		NSArray<E> array = (NSArray<E>) InstanceTypeFactory.getInstance(clazz);
		array.wrappedList = new ArrayList<E>(capacity);
		return array;
	}

	/**
	 * @Declaration : +  (id)arrayWithContentsOfFile:(NSString *)aPath
	 * @Description : Creates and returns an array containing the contents of the file specified by a given path.
	 * @param aPath The path to a file containing a string representation of an array produced by the writeToFile:atomically: method.
	 * @return Return Value An array containing the contents of the file specified by aPath. Returns nil if the file can t be opened or if
	 *         the contents of the file can t be parsed into an array.
	 * @Discussion The array representation in the file identified by aPath must contain only property list objects (NSString, NSData,
	 *             NSDate, NSNumber, NSArray, or NSDictionary objects). For more details, see Property List Programming Guide. The objects
	 *             contained by this array are immutable, even if the array is mutable.
	 **/

	
	public static NSArray arrayWithContentsOfFile(NSString aPath) {
		if (aPath.getWrappedString().contains("asset")) {
			NSString compentExt = aPath.lastPathComponent();
			try {
				Object obj = PropertyListParser.parse(compentExt.getWrappedString());
				if (obj != null && obj instanceof NSArray)
					return (NSArray) obj;
				return null;
			} catch(Exception e) {
                Logger.getLogger("context", String.valueOf(e));
			}
		} else {
			NSString compentExt = aPath.lastPathComponent();
			Object obj = SerializationUtil.retrieveObject(compentExt.getWrappedString());
			return obj != null && obj instanceof List ? new NSArray((List) obj) : null;
		}
		return null;
	}

	/**
	 * @Declaration : +  (id)arrayWithContentsOfURL:(NSURL *)aURL
	 * @Description : Creates and returns an array containing the contents specified by a given URL.
	 * @param aURL The location of a file containing a string representation of an array produced by the writeToURL:atomically: method.
	 * @return Return Value An array containing the contents specified by aURL. Returns nil if the location can t be opened or if the
	 *         contents of the location can t be parsed into an array.
	 * @Discussion The array representation at the location identified by aURL must contain only property list objects (NSString, NSData,
	 *             NSArray, or NSDictionary objects). The objects contained by this array are immutable, even if the array is mutable.
	 **/
	
	public static NSArray arrayWithContentsOfURL(NSURL aURL) {
		try {
			Object obj = PropertyListParser.parse(aURL.getFileName());
			if (obj != null && obj instanceof NSArray)
				return (NSArray) obj;
			return null;
		} catch (Exception e) {
			Logger.getLogger("Context", String.valueOf(e));
		}
		return null;
	}

	/**
	 * @Declaration : +  (instancetype)arrayWithObject:(id)anObject
	 * @Description : Creates and returns an array containing a given object.
	 * @param anObject An object.
	 * @return Return Value An array containing the single element anObject.
	 **/
	
	
	public static NSArray arrayWithObject(Class clazz, Object anObject) {
		List myList = new ArrayList();
		myList.add(anObject);
		NSArray array = (NSArray) InstanceTypeFactory.getInstance(clazz);
		array.setWrappedList(myList);
		return array;
	}

	/**
	 * @Declaration : +  (instancetype)arrayWithObjects:(const id [])objects count:(NSUInteger)count
	 * @Description : Creates and returns an array that includes a given number of objects from a given C array.
	 * @param objects A C array of objects.
	 * @param count The number of values from the objects C array to include in the new array. This number will be the count of the new
	 *            array it must not be negative or greater than the number of elements in objects.
	 * @return Return Value A new array including the first count objects from objects.
	 * @Discussion Elements are added to the new array in the same order they appear in objects, up to but not including index count. For
	 *             example: NSString *strings[3]; strings[0] = @"First"; strings[1] = @"Second"; strings[2] = @"Third"; NSArray
	 *             *stringsArray = [NSArray arrayWithObjects:strings count:2]; // strings array contains { @"First", @"Second" }
	 **/
	
	
	public static NSArray arrayWithObjectsCount(Class clazz, Object[] objects, int count) {
		NSArray array = (NSArray) InstanceTypeFactory.getInstance(clazz);
		Object[] tmpArray = null;
		if (objects != null && objects.length > 0 && count > 0) {
			if (objects.length > count)
				tmpArray = Arrays.copyOfRange(objects, 0, count - 1);
			else
				tmpArray = Arrays.copyOfRange(objects, 0, objects.length - 1);
			array.setWrappedList(Arrays.asList(tmpArray));
		}
		return array;
	}

	/**
	 * @Declaration : +  (instancetype)arrayWithObjects:(id)firstObj,,...
	 * @Description : Creates and returns an array containing the objects in the argument list.
	 * @param firstObj , ... A comma-separated list of objects ending with nil.
	 * @return Return Value An array containing the objects in the argument list.
	 **/
	@SafeVarargs
	
	
	public static NSArray arrayWithObjects(Class clazz, Object... firstObj) {
		NSArray array = (NSArray) InstanceTypeFactory.getInstance(clazz);
		if (firstObj != null && firstObj.length > 0)
			for (Object obj : firstObj)
				if (obj != null)
					array.wrappedList.add(obj);
		return array;
	}

	// Initializing an Array

	/**
	 * @Declaration : -  (instancetype)init
	 * @Description : Initializes a newly allocated array.
	 * @return Return Value An array.
	 * @Discussion After an immutable array has been initialized in this way, it cannot be modified. This method is a designated
	 *             initializer.
	 **/
	@Override
	// 
	
	
	public NSArray init() {
		this.wrappedList = new ArrayList<>();
		return this;
	}

	/**
	 * @Declaration : -  (instancetype)initWithArray:(NSArray *)anArray
	 * @Description : Initializes a newly allocated array by placing in it the objects contained in a given array.
	 * @param anArray An array.
	 * @return Return Value An array initialized to contain the objects in anArray. The returned object might be different than the original
	 *         receiver.
	 * @Discussion After an immutable array has been initialized in this way, it cannot be modified.
	 **/
	
	
	public NSArray initWithArray(NSArray anArray) {
		this.wrappedList = new ArrayList();
		this.wrappedList.addAll(anArray.getWrappedList());
		return this;
	}

	/**
	 * @Declaration : -  (instancetype)initWithArray:(NSArray *)array copyItems:(BOOL)flag
	 * @Description : Initializes a newly allocated array using anArray as the source of data objects for the array.
	 * @param array An array containing the objects with which to initialize the new array.
	 * @param flag If YES, each object in array receives a copyWithZone: message to create a copy of the object objects must conform to the
	 *            NSCopying protocol. In a managed memory environment, this is instead of the retain message the object would otherwise
	 *            receive. The object copy is then added to the returned array. If NO, then in a managed memory environment each object in
	 *            array simply receives a retain message when it is added to the returned array.
	 * @return Return Value An array initialized to contain the objects or if flag is YES, copies of the objects in array. The returned
	 *         object might be different than the original receiver.
	 * @Discussion After an immutable array has been initialized in this way, it cannot be modified. The copyWithZone: method performs a
	 *             shallow copy. If you have a collection of arbitrary depth, passing YES for the flag parameter will perform an immutable
	 *             copy of the first level below the surface. If you pass NO the mutability of the first level is unaffected. In either
	 *             case, the mutability of all deeper levels is unaffected.
	 **/
	
	
	public Object initWithArraycopyItems(NSArray<E> array, boolean flag) {

		if (flag) {
			this.wrappedList = new ArrayList();
			for (E element : array.wrappedList) {
				if (element instanceof NSCopying) {
					// clone
					this.wrappedList.add(element);
				}
			}
			return this;
		} else {
			return initWithArray(array);
		}
	}

	/**
	 * @Declaration : -  (id)initWithContentsOfFile:(NSString *)aPath
	 * @Description : Initializes a newly allocated array with the contents of the file specified by a given path.
	 * @param aPath The path to a file containing a representation of an array produced by the writeToFile:atomically: method.
	 * @return Return Value An array initialized to contain the contents of the file specified by aPath or nil if the file can t be opened
	 *         or the contents of the file can t be parsed into an array. The returned object might be different than the original receiver.
	 * @Discussion The array representation in the file identified by aPath must contain only property list objects (NSString, NSData,
	 *             NSArray, or NSDictionary objects). The objects contained by this array are immutable, even if the array is mutable.
	 **/
	
	
	public NSArray<E> initWithContentsOfFile(NSString aPath) {
		if (aPath.getWrappedString().contains("asset")) {
			NSString compentExt = aPath.lastPathComponent();
			InputStream in = null;
			try {
				in = GenericMainContext.sharedContext.getAssets()
						.open(compentExt.getWrappedString());
			} catch (IOException e) {
				Logger.getLogger("context",String.valueOf(e));
			}
			Object obj = null;
			if (in != null) {
				try {
					obj = PropertyListParser.parse(in);
				} catch (IOException e) {
                    Logger.getLogger("context", String.valueOf(e));
				} catch (PropertyListFormatException e) {
                    Logger.getLogger("context", String.valueOf(e));
				} catch (ParseException e) {
                    Logger.getLogger("context", String.valueOf(e));
				} catch (ParserConfigurationException e) {
                    Logger.getLogger("context", String.valueOf(e));
				} catch (SAXException e) {
                    Logger.getLogger("context", String.valueOf(e));
				}
				if (obj instanceof NSArray)
					return (NSArray) obj;
				else if (obj instanceof NSDictionary) {
					NSDictionary nsDictionary = (NSDictionary) obj;
					for (Object object : nsDictionary.getWrappedDictionary().values()) {
						if (object instanceof NSString || object instanceof NSNumber
								|| object instanceof NSData || object instanceof NSDate
								|| object instanceof NSArray || object instanceof NSDictionary)
							this.wrappedList.add((E) object);
					}
					return this;
				}
			}
		} else {
			NSString compentExt = aPath.lastPathComponent();
			Object obj = SerializationUtil.retrieveObject(compentExt.getWrappedString());
			if (obj != null && obj instanceof List) {
				this.wrappedList = (List) obj;
			}
		}
		return null;
	}

	/**
	 * @Declaration : -  (id)initWithContentsOfURL:(NSURL *)aURL
	 * @Description : Initializes a newly allocated array with the contents of the location specified by a given URL.
	 * @param aURL The location of a file containing a string representation of an array produced by the writeToURL:atomically: method.
	 * @return Return Value An array initialized to contain the contents specified by aURL. Returns nil if the location can t be opened or
	 *         if the contents of the location can t be parsed into an array. The returned object might be different than the original
	 *         receiver.
	 * @Discussion The array representation at the location identified by aURL must contain only property list objects (NSString, NSData,
	 *             NSArray, or NSDictionary objects). The objects contained by this array are immutable, even if the array is mutable.
	 **/
	
	
	public NSArray<E> initWithContentsOfURL(NSURL aURL) {
		return initWithContentsOfFile(aURL.path());
	}

	/**
	 * @Declaration : -  (instancetype)initWithObjects:(id)firstObj,,...
	 * @Description : Initializes a newly allocated array by placing in it the objects in the argument list.
	 * @param firstObj , ... A comma-separated list of objects ending with nil.
	 * @return Return Value An array initialized to include the objects in the argument list. The returned object might be different than
	 *         the original receiver.
	 * @Discussion After an immutable array has been initialized in this way, it can t be modified. This method is a designated initializer.
	 **/
	
	
	public NSArray<E> initWithObjects(E... firstObj) {
		if (firstObj == null)
			this.wrappedList = new ArrayList<>();
		if (firstObj.length == 0)
			this.wrappedList = new ArrayList<>();
		int nsi = firstObj.length;
		return initWithObjectsCount(firstObj, nsi);
	}

	/**
	 * @Declaration : -  (instancetype)initWithObjects:(const id [])objects count:(NSUInteger)count
	 * @Description : Initializes a newly allocated array to include a given number of objects from a given C array.
	 * @param objects A C array of objects.
	 * @param count The number of values from the objects C array to include in the new array. This number will be the count of the new
	 *            array it must not be negative or greater than the number of elements in objects.
	 * @return Return Value A newly allocated array including the first count objects from objects. The returned object might be different
	 *         than the original receiver.
	 * @Discussion Elements are added to the new array in the same order they appear in objects, up to but not including index count. After
	 *             an immutable array has been initialized in this way, it can t be modified. This method is a designated initializer.
	 **/
	
	
	public NSArray<E> initWithObjectsCount(E[] objects, int count) {
		if (count < 0 || count > objects.length)
			try {
				return this.getClass().newInstance();
			} catch (InstantiationException e) {
				Logger.getLogger("context",String.valueOf(e));
			} catch (IllegalAccessException e) {
                Logger.getLogger("context", String.valueOf(e));
			}
		/*
		 * throw new IllegalArgumentException( "count must not be negative or greater than the number of elements in objects" );
		 */
		wrappedList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (objects[i] != null)
				wrappedList.add(objects[i]);
		}
		return this;
	}

	// Querying an Array

	/**
	 * @Declaration : -  (BOOL)containsObject:(id)anObject
	 * @Description : Returns a Boolean value that indicates whether a given object is present in the array.
	 * @param anObject An object.
	 * @return Return Value YES if anObject is present in the array, otherwise NO.
	 * @Discussion This method determines whether anObject is present in the array by sending an isEqual: message to each of the array s
	 *             objects (and passing anObject as the parameter to each isEqual: message).
	 **/
	
	
	public boolean containsObject(Object anObject) {
		if (anObject == null)
			return false;
		return this.wrappedList.contains(anObject);

	}

	/**
	 * @Declaration : -  (NSUInteger)count
	 * @Description : Returns the number of objects currently in the array.
	 * @return Return Value The number of objects currently in the array.
	 **/
	
	public int count() {
		return wrappedList.size();
	}

	
	public int getCount() {
		return wrappedList.size();
	}

	/**
	 * @Declaration : -  (void)getObjects:(id __unsafe_unretained [])objects range:(NSRange)range
	 * @Description : Copies the objects contained in the array that fall within the specified range to aBuffer.
	 * @param aBuffer A C array of objects of size at least the length of the range specified by aRange.
	 * @param aRange A range within the bounds of the array. If the location plus the length of the range is greater than the count of the
	 *            array, this method raises an NSRangeException.
	 * @Discussion The method copies into aBuffer the objects in the array in the range specified by aRange; the size of the buffer must
	 *             therefore be at least the length of the range multiplied by the size of an object reference,
	 **/
	
	public void getObjects(Object[] objects, NSRange range) {
        for (int i = 0, j = range.location; i < range.length; i++, j++) {
            objects[i] = getWrappedList().get(j);
        }
	}

	public void getObjectsRange(E[] aBuffer, NSRange aRange) {
		if (aBuffer.length > getWrappedList().size() || aBuffer.length != aRange.length) {
			throw new IndexOutOfBoundsException("NSRangeException");
		}
		int j = 0;
		for (int i = aRange.location; i < aRange.length; i++) {
			aBuffer[j] = getWrappedList().get(i);
			j++;
		}
	}

	/**
	 * @Declaration : -  (id)firstObject
	 * @Description : Returns the first object in the array.
	 * @return Return Value The first object in the array. If the array is empty, returns nil.
	 **/
	
	
	public E firstObject() {
		if (wrappedList == null || wrappedList.isEmpty())
			return null;
		return wrappedList.get(0);
	}

	public E getFirstObject() {
		return firstObject();
	}

	/**
	 * @Declaration : -  (id)lastObject
	 * @Description : Returns the last object in the array.
	 * @return Return Value The last object in the array. If the array is empty, returns nil.
	 **/
	
	
	public E lastObject() {
		int count = count();
		return count != 0 ? objectAtIndex(count - 1) : null;
	}

	public E getLastObject() {
		return lastObject();
	}

	/**
	 * @Declaration : -  (id)objectAtIndex:(NSUInteger)index
	 * @Description : Returns the object located at the specified index.
	 * @param index An index within the bounds of the array.
	 * @return Return Value The object located at index.
	 * @Discussion If index is beyond the end of the array (that is, if index is greater than or equal to the value returned by count), an
	 *             NSRangeException is raised.
	 **/
	
	
	public E objectAtIndex(int index) {
		if (wrappedList == null || wrappedList.isEmpty())
			return null;
		if (index >= wrappedList.size()) {
			throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
		} else {
			return wrappedList.get(index);
		}
	}

	/**
	 * @Declaration : -  (id)objectAtIndexedSubscript:(NSUInteger)idx
	 * @Description : Returns the object at the specified index.
	 * @param idx An index within the bounds of the array.
	 * @return Return Value The object located at index.
	 * @Discussion If index is beyond the end of the array (that is, if index is greater than or equal to the value returned by count), an
	 *             NSRangeException is raised. This method is identical to objectAtIndex:.
	 **/
	
	
	public E objectAtIndexedSubscript(int idx) {

		if (wrappedList == null || wrappedList.isEmpty())
			return null;
		if (idx >= wrappedList.size()) {
			throw new IndexOutOfBoundsException("index is beyond the end of the array");
		} else {
			return wrappedList.get(idx);
		}
	}

	/**
	 * @Declaration : -  (NSArray *)objectsAtIndexes:(NSIndexSet *)indexes
	 * @Description : Returns an array containing the objects in the array at the indexes specified by a given index set.
	 * @return Return Value An array containing the objects in the array at the indexes specified by indexes.
	 * @Discussion The returned objects are in the ascending order of their indexes in indexes, so that object in returned array with higher
	 *             index in indexes will follow the object with smaller index in indexes. Raises an NSRangeException if any location in
	 *             indexes exceeds the bounds of the array, indexes is nil.
	 **/

	
	
	public NSArray<E> objectsAtIndexes(NSIndexSet indexes) {
		List<E> arrayList = new ArrayList<E>();
		for (int i = indexes.firstIndex(); i <= indexes.lastIndex(); i++) {
			arrayList.add(wrappedList.get(i));
		}
		return new NSArray<E>(arrayList);
	}

	/**
	 * @Declaration : -  (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the array.
	 * @return Return Value An enumerator object that lets you access each object in the array, in order, from the element at the lowest
	 *         index upwards.
	 * @Discussion Returns an enumerator object that lets you access each object in the array, in order, starting with the element at index
	 *             0
	 **/
	
	
	public NSEnumerator<E> objectEnumerator() {
		return new NSEnumerator<E>(wrappedList.iterator());
	}

	/**
	 * @Declaration : -  (NSEnumerator *)reverseObjectEnumerator
	 * @Description : Returns an enumerator object that lets you access each object in the array, in reverse order.
	 * @return Return Value An enumerator object that lets you access each object in the array, in order, from the element at the highest
	 *         index down to the element at index 0.
	 **/
	
	public NSEnumerator<E> reverseObjectEnumerator() {
		List tmpList = new ArrayList(this.wrappedList);
		Collections.reverse(tmpList);
		NSEnumerator<E> myEnum = new NSEnumerator<E>(tmpList.iterator());
		return myEnum;
	}

	/**
	 * @Declaration :- (void)getObjects:(id __unsafe_unretained [])objects Use getObjects:range: instead.
	 * @Description :Copies all the objects contained in the array to aBuffer.
	 * @return aBuffer A C array of objects of size at least the count of the array.
	 */
	
	
	public void getObjects(E[] objects) {
		for (int i = 0; i < getWrappedList().size(); i++) {
			objects[i] = getWrappedList().get(i);
		}
	}

	// Finding Objects in an Array
	/**
	 * @Declaration : -  (NSUInteger)indexOfObject:(id)anObject
	 * @Description : Returns the lowest index whose corresponding array value is equal to a given object.
	 * @param anObject An object.
	 * @return Return Value The lowest index whose corresponding array value is equal to anObject. If none of the objects in the array is
	 *         equal to anObject, returns NSNotFound.
	 * @Discussion Starting at index 0, each element of the array is sent an isEqual: message until a match is found or the end of the array
	 *             is reached. This method passes the anObject parameter to each isEqual: message. Objects are considered equal if isEqual:
	 *             (declared in the NSObject protocol) returns YES.
	 **/

	
	
	public int indexOfObject(E anObject) {
		if (anObject == null) {
			return NotFound;
		}
		return wrappedList.indexOf(anObject);
	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObject:(id)anObject inRange:(NSRange)range
	 * @Description : Returns the lowest index within a specified range whose corresponding array value is equal to a given object .
	 * @param anObject An object.
	 * @param range The range of indexes in the array within which to search for anObject.
	 * @return Return Value The lowest index within range whose corresponding array value is equal to anObject. If none of the objects
	 *         within range is equal to anObject, returns NSNotFound.
	 * @Discussion Starting at range.location, each element of the array is sent an isEqual: message until a match is found or the end of
	 *             the range is reached. This method passes the anObject parameter to each isEqual: message. Objects are considered equal if
	 *             isEqual: returns YES. This method raises an NSRangeException exception if the range parameter represents a range that
	 *             doesn t exist in the array.
	 **/

	
	
	public int indexOfObjectInRange(E anObject, NSRange inRange) {
		for (int i = inRange.location; i < inRange.length + inRange.location; i++) {
			if (wrappedList.get(i).equals(anObject))
				return i;
		}
		return -1;
	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObjectIdenticalTo:(id)anObject
	 * @Description : Returns the lowest index whose corresponding array value is identical to a given object.
	 * @param anObject An object.
	 * @return Return Value The lowest index whose corresponding array value is identical to anObject. If none of the objects in the array
	 *         is identical to anObject, returns NSNotFound.
	 * @Discussion Objects are considered identical if their object addresses are the same.
	 **/
	
	
	public int indexOfObjectIdenticalTo(E anObject) {// FIXME: 26/11/2015
		for (int i = 0; i < wrappedList.size(); i++) {
			if (anObject.equals(wrappedList.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObjectIdenticalTo:(id)anObject inRange:(NSRange)range
	 * @Description : Returns the lowest index within a specified range whose corresponding array value is equal to a given object .
	 * @param anObject An object.
	 * @param range The range of indexes in the array within which to search for anObject.
	 * @return Return Value The lowest index within range whose corresponding array value is identical to anObject. If none of the objects
	 *         within range is identical to anObject, returns NSNotFound.
	 * @Discussion Objects are considered identical if their object addresses are the same.
	 **/

	
	
	public int indexOfObjectIdenticalToInRange(E anObject, NSRange inRange) {// FIXME: 26/11/2015 equals is not hte operation to compare two
		// objects
		for (int i = inRange.location; i < inRange.length + inRange.location; i++) {
			if (anObject.equals(wrappedList.get(i))) {

				return i;
			}
		}
		return -1;
	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObjectPassingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index of the first object in the array that passes a test in a given Block.
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test. Returning YES will stop further
	 *            processing of the array.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The lowest index whose corresponding value in the array passes the test specified by predicate. If no objects in
	 *         the array pass the test, returns NSNotFound.
	 * @Discussion If the Block parameter is nil this method will raise an exception.
	 **/

	
	
	public int indexOfObjectPassingTest(PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {

		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");
		boolean[] stop = new boolean[1];
		stop[0] = false;
		int position = 0;

		for (int i = 0; i < wrappedList.size(); i++) {
			if (predicate.perform(wrappedList.get(i), i, stop)) {
				position = i;
			}
			if (stop[0]) {
				return position;
			}
		}
		return position;
	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObjectWithOptions:(NSEnumerationOptions )opts passingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL
	 *              *stop))predicate
	 * @Description : Returns the index of an object in the array that passes a test in a given Block for a given set of enumeration
	 *              options.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The index whose corresponding value in the array passes the test specified by predicate and opts. If the opts
	 *         bit mask specifies reverse order, then the last item that matches is returned. Otherwise, the index of the first matching
	 *         object is returned. If no objects in the array pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last object. You
	 *             can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this behavior.
	 *             Important:  If the Block parameter is nil this method will raise an exception.
	 **/

	
	
	public int indexOfObjectWithOptionsPassingTest(int opts,
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {

		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		int position = 0;
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {

			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = 0; i < wrappedList.size(); i++) {
				if (predicate.perform(wrappedList.get(i), i, stop)) {
					position = i;
				}
				if (stop[0]) {
					return position;
				}
			}

		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {

			List<E> tmpList = new ArrayList<E>(wrappedList);
			Collections.reverse(tmpList);
			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = 0; i < wrappedList.size(); i++) {
				if (predicate.perform(tmpList.get(i), wrappedList.size() - 1 - i, stop)) {
					position = wrappedList.size() - 1 - i;
				}
				if (stop[0]) {
					return position;
				}
			}

		}

		return position;

	}

	/**
	 * @Declaration : -  (NSUInteger)indexOfObjectAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts passingTest:(BOOL
	 *              (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index, from a given set of indexes, of the first object in the array that passes a test in a given Block
	 *              for a given set of enumeration options.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The lowest index whose corresponding value in the array passes the test specified by predicate. If no objects in
	 *         the array pass the test, returns NSNotFound.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last element
	 *             specified by indexSet. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to
	 *             modify this behavior. Important:  If the Block parameter or indexSet is nil this method will raise an exception.
	 **/

	
	public int indexOfObjectAtIndexesOptionsPassingTest(NSIndexSet indexSet, int opts,
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		boolean[] stop = new boolean[1];
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");
		int position = -1;
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			stop[0] = false;
			for (int i = indexSet.firstIndex(); i <= indexSet.lastIndex(); i++) {
				if (predicate.perform(wrappedList.get(i), i, stop)) {
					position = i;
				}
				if (stop[0]) {
					return position;
				}
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {

			stop[0] = false;

			for (int i = indexSet.lastIndex(); i >= indexSet.firstIndex(); i--) {

				if (predicate.perform(wrappedList.get(i), wrappedList.size() - 1 - i, stop)) {
					position = i;
				}
				if (stop[0]) {
					return position;
				}
			}
		}

		return position;

	}

	/**
	 * @Declaration : -  (NSIndexSet *)indexesOfObjectsPassingTest:(BOOL (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the indexes of objects in the array that pass a test in a given Block.
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The indexes whose corresponding values in the array pass the test specified by predicate. If no objects in the
	 *         array pass the test, returns an empty index set.
	 **/

	
	public NSIndexSet indexesOfObjectsPassingTest(
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		boolean[] stop = new boolean[1];
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");
		NSIndexSet indexSet = new NSIndexSet();
		stop[0] = false;
		for (int i = 0; i < wrappedList.size(); i++) {
			if (predicate.perform(wrappedList.get(i), i, stop)) {
				indexSet.wrappedTreeSet.add(i);
			}
			if (stop[0]) {
				return indexSet;
			}
		}

		return indexSet;
	}

	/**
	 * @Declaration : -  (NSIndexSet *)indexesOfObjectsWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(id obj, NSUInteger idx,
	 *              BOOL *stop))predicate
	 * @Description : Returns the indexes of objects in the array that pass a test in a given Block for a given set of enumeration options.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The indexes whose corresponding values in the array pass the test specified by predicate. If no objects in the
	 *         array pass the test, returns an empty index set.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last object. You
	 *             can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this behavior.
	 *             Important:  If the Block parameter is nil this method will raise an exception.
	 **/
	
	public NSIndexSet indexesOfObjectsWithOptionsPassingTest(int opts,
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		boolean[] stop = new boolean[1];
		NSIndexSet indexSet = new NSIndexSet();
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			stop = new boolean[1];
			stop[0] = false;
			for (int i = 0; i < wrappedList.size(); i++) {
				if (predicate.perform(wrappedList.get(i), i, stop)) {
					indexSet.wrappedTreeSet.add(i);
				}
				if (stop[0]) {
					return indexSet;
				}
			}
		}

		if (opts == NSObjCRuntime.NSEnumerationReverse) {
			stop = new boolean[1];
			stop[0] = false;
			for (int i = wrappedList.size() - 1; i >= 0; i--) {

				if (predicate.perform(wrappedList.get(i), i, stop)) {
					indexSet.wrappedTreeSet.add(i);
				}
				if (stop[0]) {
					return indexSet;
				}
			}
		}

		return indexSet;
	}

	/**
	 * @Signature: indexesOfObjectsAtIndexes:options:passingTest:
	 * @Declaration : - (NSIndexSet *)indexesOfObjectsAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts passingTest:(BOOL
	 *              (^)(id obj, NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the indexes, from a given set of indexes, of objects in the array that pass a test in a given Block for a
	 *              given set of enumeration options.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param predicate The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block. The Block returns a Boolean value that indicates whether obj passed the test.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The indexes whose corresponding values in the array pass the test specified by predicate. If no objects in the
	 *         array pass the test, returns an empty index set.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last element
	 *             specified by indexSet. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to
	 *             modify this behavior. Important: If the Block parameter or the indexSet is nil this method will raise an exception.
	 **/
	
	public NSIndexSet indexesOfObjectsAtIndexesOptionsPassingTest(NSIndexSet indexSet, int opts,
			PerformBlock.BOOLBlockIdNSUIntegerBOOL predicate) {
		NSIndexSet resultIndexs = new NSIndexSet();
		boolean[] stop = new boolean[1];
		stop[0] = Boolean.FALSE;
		List<E> tmpList = new ArrayList<E>(wrappedList);
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationConcurrent) {
			Collections.reverse(tmpList);
		}
		for (int i = 0; i < tmpList.size() && indexSet.getWrappedTreeSet().contains(i); i++) {
			if (predicate.perform(wrappedList.get(i), i, stop))
				resultIndexs.getWrappedTreeSet().add(i);
		}
		return resultIndexs;
	}

	/**
	 * @Signature: indexOfObject:inSortedRange:options:usingComparator:
	 * @Declaration : - (NSUInteger)indexOfObject:(id)obj inSortedRange:(NSRange)r options:(NSBinarySearchingOptions)opts
	 *              usingComparator:(NSComparator)cmp
	 * @Description : Returns the index, within a specified range, of an object compared with elements in the array using a given
	 *              NSComparator block.
	 * @param obj An object for which to search in the array. If this value is nil, throws an NSInvalidArgumentException.
	 * @param r The range within the array to search for obj. If r exceeds the bounds of the array (if the location plus length of the range
	 *            is greater than the count of the array), throws an NSRangeException.
	 * @param opts Options for the search. For possible values, see “NSBinarySearchingOptions.? If you specify both
	 *            NSBinarySearchingFirstEqual and NSBinarySearchingLastEqual, throws an NSInvalidArgumentException.
	 * @param cmp A comparator block used to compare the object obj with elements in the array. If this value is NULL, throws an
	 *            NSInvalidArgumentException.
	 * @return Return Value If the NSBinarySearchingInsertionIndex option is not specified: If the obj is found and neither
	 *         NSBinarySearchingFirstEqual nor NSBinarySearchingLastEqual is specified, returns an arbitrary matching object's index. If the
	 *         NSBinarySearchingFirstEqual option is also specified, returns the lowest index of equal objects. If the
	 *         NSBinarySearchingLastEqual option is also specified, returns the highest index of equal objects. If the object is not found,
	 *         returns NSNotFound. If the NSBinarySearchingInsertionIndex option is specified, returns the index at which you should insert
	 *         obj in order to maintain a sorted array: If the obj is found and neither NSBinarySearchingFirstEqual nor
	 *         NSBinarySearchingLastEqual is specified, returns any equal or one larger index than any matching object’s index. If the
	 *         NSBinarySearchingFirstEqual option is also specified, returns the lowest index of equal objects. If the
	 *         NSBinarySearchingLastEqual option is also specified, returns the highest index of equal objects. If the object is not found,
	 *         returns the index of the least greater object, or the index at the end of the array if the object is larger than all other
	 *         elements.
	 **/
	
	public int indexOfObjectInSortedRangeOptionsUsingComparator(E obj, NSRange r,
			NSBinarySearchingOptions opts, NSComparator cmp) {
		if (obj == null || cmp == null)
			throw new IllegalArgumentException("null parameter not allowed");
		if ((r.length + r.location) > wrappedList.size())
			throw new InvalidParameterException("NSRangeException range out of bound");
		if (opts.getValue() == (NSBinarySearchingOptions.NSBinarySearchingFirstEqual.getValue()
				| NSBinarySearchingOptions.NSBinarySearchingLastEqual.getValue()))
			throw new IllegalArgumentException(
					"can't use both NSBinarySearchingFirstEqual and NSBinarySearchingLastEqual as option in same time");

		int resultCode = -1;
		if (opts == NSBinarySearchingOptions.NSBinarySearchingFirstEqual)
			resultCode = Collections.binarySearch(wrappedList, obj, cmp);
		else if (opts == NSBinarySearchingOptions.NSBinarySearchingLastEqual) {
			{
				List<E> reversedList = new ArrayList<E>(wrappedList);
				Collections.reverse(reversedList);
				resultCode = Collections.binarySearch(reversedList, obj, cmp);
			}

		} else if (opts == NSBinarySearchingOptions.NSBinarySearchingInsertionIndex) {
			// TODO complete
		}
		return resultCode;
	}

	// Sending Messages to Elements

	/**
	 * @Declaration : -  (void)makeObjectsPerformSelector:(SEL)aSelector
	 * @Description : Sends to each object in the array the message identified by a given selector, starting with the first object and
	 *              continuing through the array to the last object.
	 * @param aSelector A selector that identifies the message to send to the objects in the array. The method must not take any arguments,
	 *            and must not have the side effect of modifying the receiving array.
	 * @Discussion This method raises an NSInvalidArgumentException if aSelector is NULL.
	 **/

	
	public void makeObjectsPerformSelector(SEL aSelector) {
		if (aSelector == null) {
			throw new IllegalArgumentException("Selector cannot be null");
		}
		for (E element : getWrappedList()) {
			SEL._safeInvokeSelector(aSelector, element);
		}

	}

	/**
	 * @Declaration : -  (void)makeObjectsPerformSelector:(SEL)aSelector withObject:(id)anObject
	 * @Description : Sends the aSelector message to each object in the array, starting with the first object and continuing through the
	 *              array to the last object.
	 * @param aSelector A selector that identifies the message to send to the objects in the array. The method must take a single argument
	 *            of type id, and must not have the side effect of modifying the receiving array.
	 * @param anObject The object to send as the argument to each invocation of the aSelector method.
	 * @Discussion This method raises an NSInvalidArgumentException if aSelector is NULL.
	 **/
	
	
	public void makeObjectsPerformSelectorWithObject(SEL aSelector, Object argument) {
		for (Object anObject : wrappedList) {
			if (anObject instanceof NSObject) {
				NSObject ouwnObject = (NSObject) anObject;
				ouwnObject.performSelectorWithObject(aSelector, argument);
			}
		}

	}

	/**
	 * @Declaration : -  (void)enumerateObjectsUsingBlock:(void (^)(id obj, NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given block using each object in the array, starting with the first object and continuing through the array
	 *              to the last object.
	 * @param block The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion If the Block parameter is nil this method will raise an exception. This method executes synchronously.
	 **/

	
	
	public void enumerateObjectsUsingBlock(PerformBlock.VoidBlockIdNSUIntegerBOOL block) {
		boolean[] stop = new boolean[1];
		//
		stop[0] = Boolean.FALSE;
		for (int i = 0; i < wrappedList.size(); i++) {
			block.perform(wrappedList.get(i), i, stop);
			if (stop[0])
				break;
		}
	}

	/**
	 * @Declaration : -  (void)enumerateObjectsWithOptions:(NSEnumerationOptions) opts usingBlock:(void (^)(id obj, NSUInteger idx, BOOL
	 *              *stop))block
	 * @Description : Executes a given block using each object in the array.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param block The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last object. You
	 *             can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify this behavior. This
	 *             method executes synchronously. Important:  If the Block parameter is nil this method will raise an exception.
	 **/

	
	public void enumerateObjectsWithOptionsUsingBlock(int option,
			PerformBlock.VoidBlockIdNSUIntegerBOOL block) {

		if (option == NSObjCRuntime.NSEnumerationConcurrent) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = 0; i < wrappedList.size(); i++) {
				block.perform(wrappedList.get(i), i, stop);
				if (stop[0])
					break;

			}
		} else if (option == NSObjCRuntime.NSEnumerationReverse) {
			List<Object> myTmpList = new ArrayList<Object>(); // mainList.addAll(set);
			myTmpList.addAll(wrappedList);

			Collections.reverse(myTmpList);
			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = 0; i < myTmpList.size(); i++) {
				block.perform(myTmpList.get(i), i, stop);
				if (stop[0])
					break;

			}

		}

	}

	/**
	 * @Declaration : -  (void)enumerateObjectsAtIndexes:(NSIndexSet *)indexSet options:(NSEnumerationOptions)opts usingBlock:(void (^)(id
	 *              obj, NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given block using the objects in the array at the specified indexes.
	 * @param indexSet The indexes of the objects over which to enumerate.
	 * @param opts A bit mask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order).
	 * @param block The block to apply to elements in the array. The block takes three arguments: obj The element in the array. idx The
	 *            index of the element in the array. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block.
	 * @param obj The element in the array.
	 * @param idx The index of the element in the array.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the array to the last element
	 *             specified by indexSet. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to
	 *             modify this behavior. This method executes synchronously. Important:  If the Block parameter or the indexSet is nil this
	 *             method will raise an exception.
	 **/
	
	public void enumerateObjectsAtIndexesOptionsUsingBlock(NSIndexSet indexSet, int opts,
			PerformBlock.VoidBlockIdNSUIntegerBOOL<E> block) {

		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = indexSet.firstIndex(); i <= indexSet.lastIndex(); i++) {
				block.perform(wrappedList.get(i), i, stop);
				if (stop[0])
					break;

			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			List<E> myTmpList = new ArrayList<E>(); // mainList.addAll(set);
			myTmpList.addAll(wrappedList);

			Collections.reverse(myTmpList);
			boolean[] stop = new boolean[1];
			stop[0] = false;
			for (int i = indexSet.firstIndex(); i <= indexSet.lastIndex(); i++) {
				block.perform(myTmpList.get(i), i, stop);
				if (stop[0])
					break;

			}

		}

	}

	// Comparing Arrays

	/**
	 * @Declaration : -  (id)firstObjectCommonWithArray:(NSArray *)otherArray
	 * @Description : Returns the first object contained in the receiving array that s equal to an object in another given array.
	 * @param otherArray An array.
	 * @return Return Value Returns the first object contained in the receiving array that s equal to an object in otherArray. If no such
	 *         object is found, returns nil.
	 * @Discussion This method uses isEqual: to check for object equality.
	 **/
	
	public E firstObjectCommonWithArray(NSArray<E> otherArray) {
		if (otherArray == null || otherArray.getWrappedList().isEmpty())
			return null;
		NSSet<E> set = new NSSet<E>(otherArray);
		for (E e : getWrappedList()) {
			if (set.containsObject(e))
				return e;
		}
		return null;
	}

	/**
	 * @Declaration : -  (BOOL)isEqualToArray:(NSArray *)otherArray
	 * @Description : Compares the receiving array to another array.
	 * @param otherArray An array.
	 * @return Return Value YES if the contents of otherArray are equal to the contents of the receiving array, otherwise NO.
	 * @Discussion Two arrays have equal contents if they each hold the same number of objects and objects at a given index in each array
	 *             satisfy the isEqual: test.
	 **/
	
	
	public boolean isEqualToArray(NSArray<E> otherArray) {

		if (otherArray.getWrappedList().size() != wrappedList.size()) {
			return false;
		} else {
			for (int i = 0; i < wrappedList.size(); i++) {
				if (!otherArray.getWrappedList().get(i).equals(wrappedList.get(i)))
					return false;
			}
			return true;
		}

	}

	// Deriving New Arrays
	/**
	 * @Declaration : -  (NSArray *)arrayByAddingObject:(id)anObject
	 * @Description : Returns a new array that is a copy of the receiving array with a given object added to the end.
	 * @param anObject An object.
	 * @return Return Value A new array that is a copy of the receiving array with anObject added to the end.
	 * @Discussion If anObject is nil, an NSInvalidArgumentException is raised.
	 **/
	
	public NSArray<E> arrayByAddingObject(E object) {
		if (object == null)
			throw new IllegalArgumentException("object may not be null");
		NSMutableArray<E> result = this.mutableClone();
		result.addObject(object);
		return result;
	}

	/**
	 * @Declaration : -  (NSArray *)arrayByAddingObjectsFromArray:(NSArray *)otherArray
	 * @Description : Returns a new array that is a copy of the receiving array with the objects contained in another array added to the
	 *              end.
	 * @param otherArray An array.
	 * @return Return Value A new array that is a copy of the receiving array with the objects contained in otherArray added to the end.
	 **/
	
	public NSArray<E> arrayByAddingObjectsFromArray(NSArray<E> otherArray) {
		if (otherArray == null || otherArray.count() == 0)
			return new NSArray<E>(this.wrappedList);
		NSMutableArray<E> result = this.mutableClone();
		result.addObjectsFromArray(otherArray);
		return result;
	}

	/**
	 * @Declaration : -  (NSArray *)filteredArrayUsingPredicate:(NSPredicate *)predicate
	 * @Description : Evaluates a given predicate against each object in the receiving array and returns a new array containing the objects
	 *              for which the predicate returns true.
	 * @param predicate The predicate against which to evaluate the receiving array s elements.
	 * @return Return Value A new array containing the objects in the receiving array for which predicate returns true.
	 * @Discussion For more details, see Predicate Programming Guide.
	 **/
	
	public NSArray filteredArrayUsingPredicate(NSPredicate predicate) {
		return null;
	}

	/**
	 * @Declaration : -  (NSArray *)subarrayWithRange:(NSRange)range
	 * @Description : Returns a new array containing the receiving array s elements that fall within the limits specified by a given range.
	 * @param range A range within the receiving array s range of elements.
	 * @return Return Value A new array containing the receiving array s elements that fall within the limits specified by range.
	 * @Discussion If range isn t within the receiving array s range of elements, an NSRangeException is raised. For example, the following
	 *             code example creates an array containing the elements found in the first half of wholeArray (assuming wholeArray exists).
	 *             NSArray *halfArray; NSRange theRange; theRange.location = 0; theRange.length = [wholeArray count] / 2; halfArray =
	 *             [wholeArray subarrayWithRange:theRange];
	 **/
	
	public NSArray<E> subarrayWithRange(NSRange range) {
		if (range == null || range.length == 0)
			return NSArray.emptyArray();
		int start = range.location;
		int end = start + range.length;
		return new NSArray<E>(wrappedList.subList(start, end));
	}

	// Sorting

	/**
	 * @Declaration : -  (NSData *)sortedArrayHint
	 * @Description : Analyzes the array and returns a hint that speeds the sorting of the array when the hint is supplied to
	 *              sortedArrayUsingFunction:context:hint:.
	 **/
	
	public NSData sortedArrayHint() {
		return null;
	}

	public transient NSData sortedArrayHint;

	public NSData getSortedArrayHint() {
		return sortedArrayHint();
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayUsingFunction:(NSInteger (*)(id, id, void *))comparator context:(void *)context
	 * @Description : Returns a new array that lists the receiving array s elements in ascending order as defined by the comparison function
	 *              comparator.
	 * @Discussion The new array contains references to the receiving array s elements, not copies of them. The comparison function is used
	 *             to compare two elements at a time and should return NSOrderedAscending if the first element is smaller than the second,
	 *             NSOrderedDescending if the first element is larger than the second, and NSOrderedSame if the elements are equal. Each
	 *             time the comparison function is called, it s passed context as its third argument.
	 **/

	public NSArray<E> sortedArrayUsingFunctionContext(MACFuntionPointer comparator,
			Object context) {
		return sortedArrayUsingFunctionContextHint(comparator, context, null);
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayUsingFunction:(NSInteger (*)(id, id, void *))comparator context:(void *)context hint:(NSData
	 *              *)hint
	 * @Description : Returns a new array that lists the receiving array s elements in ascending order as defined by the comparison function
	 *              comparator.
	 * @Discussion The new array contains references to the receiving array s elements, not copies of them. This method is similar to
	 *             sortedArrayUsingFunction:context:, except that it uses the supplied hint to speed the sorting process. When you know the
	 *             array is nearly sorted, this method is faster than sortedArrayUsingFunction:context:.
	 **/
	
	public NSArray<E> sortedArrayUsingFunctionContextHint(final MACFuntionPointer comparator,
			Object context, NSData hint) {
		List<E> tmpList = new ArrayList<E>(wrappedList);
		Comparator<Object> compare = new Comparator<Object>() {

			@Override
			public int compare(Object lhs, Object rhs) {
				try {
					Method method = IntrospectionUtil.findMethod(comparator);
					return (Integer) method.invoke(null, lhs, rhs, null);
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (NoSuchMethodException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
				return 0;

			}

		};
		Collections.sort(tmpList, compare);
		NSArray<E> nsArray = new NSArray<E>();
		nsArray.wrappedList = new ArrayList<E>(tmpList);
		return nsArray;
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayUsingDescriptors:(NSArray *)sortDescriptors
	 * @Description : Returns a copy of the receiving array sorted as specified by a given array of sort descriptors.
	 * @param sortDescriptors An array of NSSortDescriptor objects.
	 * @return Return Value A copy of the receiving array sorted as specified by sortDescriptors.
	 * @Discussion The first descriptor specifies the primary key path to be used in sorting the receiving array s contents. Any subsequent
	 *             descriptors are used to further refine sorting of objects with duplicate values. See NSSortDescriptor for additional
	 *             information.
	 **/
	
	public NSArray<E> sortedArrayUsingDescriptors(final NSArray<NSSortDescriptor> sortDescriptors) {
		NSArray arrayCopie = this.clone();
		Collections.sort(arrayCopie.getWrappedList(), new Comparator() {
			@Override
			public int compare(Object lhs, Object rhs) {
				int ordre = 0;
				for (NSSortDescriptor nsSort : sortDescriptors) {
					if (nsSort != null) {
						ordre = nsSort.getComparatorObject().compare(lhs, rhs);
						if (ordre != 0)
							return ordre;
					}
				}
				return ordre;
			}
		});
		return arrayCopie;
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayUsingSelector:(SEL)comparator
	 * @Description : Returns an array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *              specified by a given selector.
	 * @param comparator A selector that identifies the method to use to compare two elements at a time. The method should return
	 *            NSOrderedAscending if the receiving array is smaller than the argument, NSOrderedDescending if the receiving array is
	 *            larger than the argument, and NSOrderedSame if they are equal.
	 * @return Return Value An array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *         specified by the selector comparator.
	 * @Discussion The new array contains references to the receiving array s elements, not copies of them. The comparator message is sent
	 *             to each object in the array and has as its single argument another object in the array. For example, an array of NSString
	 *             objects can be sorted by using the caseInsensitiveCompare: method declared in the NSString class. Assuming anArray
	 *             exists, a sorted version of the array can be created in this way: NSArray *sortedArray = [anArray
	 *             sortedArrayUsingSelector:];
	 **/
	
	
	public NSArray<E> sortedArrayUsingSelector(SEL comparator) {
		NSArray arrayCopie = this.clone();
		if (comparator != null && !arrayCopie.getWrappedList().isEmpty()
				&& comparator.getMethodName() != null) {
			final Method m = InvokableHelper.getMethod(arrayCopie.objectAtIndex(0),
					comparator.getMethodName(), arrayCopie.objectAtIndex(0));
			if (m != null)
				Collections.sort(arrayCopie.wrappedList, new Comparator<E>() {
					@Override
					public int compare(E lhs, E rhs) {
						NSObjCRuntime.NSComparisonResult indice = NSObjCRuntime.NSComparisonResult.NSOrderedSame;
						try {
							indice = (NSObjCRuntime.NSComparisonResult) m.invoke(lhs, rhs);
						} catch (IllegalAccessException e) {
							Logger.getLogger("context",String.valueOf(e));
						} catch (InvocationTargetException e) {
                            Logger.getLogger("context", String.valueOf(e));
						}
						if (indice == NSObjCRuntime.NSComparisonResult.NSOrderedSame) {
							return 0;
						} else if (indice == NSObjCRuntime.NSComparisonResult.NSOrderedDescending) {
							return 1;
						} else {
							return -1;
						}
					}
				});
		}
		return arrayCopie;
	}

	private static List SortedArray(List initialeList, Method m) {
		boolean change = false;
		List newList = new ArrayList();
		Object obj = initialeList.get(0);
		for (int i = 1; i < initialeList.size(); i++) {
			Object obj2 = (Object) initialeList.get(i);
			NSObjCRuntime.NSComparisonResult indice = NSObjCRuntime.NSComparisonResult.NSOrderedSame;

			try {
				indice = (NSObjCRuntime.NSComparisonResult) m.invoke(obj, obj2);
			} catch (IllegalAccessException e) {
                Logger.getLogger("context", String.valueOf(e));
			} catch (InvocationTargetException e) {
                Logger.getLogger("context", String.valueOf(e));
			}
			if (indice == NSObjCRuntime.NSComparisonResult.NSOrderedDescending) {
				newList.add(obj2);
				change = true;
			} else {
				newList.add(obj);
				obj = obj2;
			}

		}
		newList.add(obj);
		if (change)
			newList = SortedArray(newList, m);
		return newList;
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayUsingComparator:(NSComparator)cmptr
	 * @Description : Returns an array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *              specified by a given NSComparator Block.
	 * @param cmptr A comparator block.
	 * @return Return Value An array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *         specified cmptr.
	 **/
	
	
	public NSArray<E> sortedArrayUsingComparator(final NSComparator<E> cmptr) {
		NSArray arrayCopie = this.clone();
		Collections.sort(arrayCopie.wrappedList, new Comparator<E>() {
			@Override
			public int compare(E lhs, E rhs) {
				return cmptr.compare(lhs, rhs);
			}
		});
		return this;
	}

	/**
	 * @Declaration : -  (NSArray *)sortedArrayWithOptions:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
	 * @Description : Returns an array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *              specified by a given NSComparator Block.
	 * @param opts A bit mask that specifies the options for the sort (whether it should be performed concurrently and whether it should be
	 *            performed stably).
	 * @param cmptr A comparator block.
	 * @return Return Value An array that lists the receiving array s elements in ascending order, as determined by the comparison method
	 *         specified cmptr.
	 **/
	
	
	public NSArray<E> sortedArrayWithOptionsUsingComparator(int opts, NSComparator<E> cmptr) {
		NSArray arrayCopie = this.clone();
		// FIXME
		// if (!opts.equals(NSSortOptions.NSSortConcurrent))
		// Collections.reverse(tmpList);
		Collections.sort(arrayCopie.wrappedList, cmptr);
		return arrayCopie;
	}

	// Working with String Elements

	/**
	 * @Declaration : -  (NSString *)componentsJoinedByString:(NSString *)separator
	 * @Description : Constructs and returns an NSString object that is the result of interposing a given separator between the elements of
	 *              the array.
	 * @param separator The string to interpose between the elements of the array.
	 * @return Return Value An NSString object that is the result of interposing separator between the elements of the array. If the array
	 *         has no elements, returns an NSString object representing an empty string.
	 * @Discussion For example, this code excerpt writes "here be dragons" to the console: NSArray *pathArray = [NSArray
	 *             arrayWithObjects:@"here", @"be", @"dragons", nil]; NSLog(@"%@",[pathArray componentsJoinedByString:@" "]);
	 **/
	
	
	public NSString componentsJoinedByString(NSString separator) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < wrappedList.size(); i++) {
			result.append(objectAtIndex(i).toString());
			if (i < wrappedList.size() - 1 && separator != null)
				result.append(separator);
		}
		return new NSString(result.toString());
	}

	// Creating a Description

	/**
	 * @Declaration : -  (NSString *)description
	 * @Description : Returns a string that represents the contents of the array, formatted as a property list.
	 * @return Return Value A string that represents the contents of the array, formatted as a property list.
	 **/
	@Override
	
	
	public NSString description() {
		StringBuilder buf = new StringBuilder();
		buf.append(
				NSPropertyListSerialization.TOKEN_BEGIN[NSPropertyListSerialization.PLIST_ARRAY]);
		for (int i = 0; i < getWrappedList().size(); i++) {
			Object x = objectAtIndex(i);
			buf.append(NSPropertyListSerialization.stringForPropertyList(x));
			if (i < getWrappedList().size() - 1)
				buf.append(", ");
		}
		buf.append(NSPropertyListSerialization.TOKEN_END[NSPropertyListSerialization.PLIST_ARRAY]);
		return new NSString(buf.toString());
	}

	public NSString getDescription() {
		return description();
	}

	/**
	 * @Declaration : -  (NSString *)descriptionWithLocale:(id)locale
	 * @Description : Returns a string that represents the contents of the array, formatted as a property list.
	 * @param locale An NSLocale object or an NSDictionary object that specifies options used for formatting each of the array s elements
	 *            (where recognized). Specify nil if you don t want the elements formatted.
	 * @return Return Value A string that represents the contents of the array, formatted as a property list.
	 * @Discussion For a description of how locale is applied to each element in the receiving array, see descriptionWithLocale:indent:.
	 **/
	
	
	public NSString descriptionWithLocale(Object locale) {
		return description();
	}

	/**
	 * @Declaration : -  (NSString *)descriptionWithLocale:(id)locale indent:(NSUInteger)level
	 * @Description : Returns a string that represents the contents of the array, formatted as a property list.
	 * @param locale An NSLocale object or an NSDictionary object that specifies options used for formatting each of the array s elements
	 *            (where recognized). Specify nil if you don t want the elements formatted.
	 * @param level A level of indent, to make the output more readable: set level to 0 to use four spaces to indent, or 1 to indent the
	 *            output with a tab character.
	 * @return Return Value A string that represents the contents of the array, formatted as a property list.
	 * @Discussion The returned NSString object contains the string representations of each of the array s elements, in order, from first to
	 *             last. To obtain the string representation of a given element, descriptionWithLocale:indent: proceeds as follows: If the
	 *             element is an NSString object, it is used as is. If the element responds to descriptionWithLocale:indent:, that method is
	 *             invoked to obtain the element s string representation. If the element responds to descriptionWithLocale:, that method is
	 *             invoked to obtain the element s string representation. If none of the above conditions is met, the element s string
	 *             representation is obtained by invoking its description method.
	 **/
	
	public NSString descriptionWithLocaleIndent(Object locale, int level) {
		return null;
	}

	/**
	 * @Declaration : -  (BOOL)writeToFile:(NSString *)path atomically:(BOOL)flag
	 * @Description : Writes the contents of the array to a file at a given path.
	 * @param path The path at which to write the contents of the array. If path contains a tilde (~) character, you must expand it with
	 *            stringByExpandingTildeInPath before invoking this method.
	 * @param flag If YES, the array is written to an auxiliary file, and then the auxiliary file is renamed to path. If NO, the array is
	 *            written directly to path. The YES option guarantees that path, if it exists at all, won t be corrupted even if the system
	 *            should crash during writing.
	 * @return Return Value YES if the file is written successfully, otherwise NO.
	 * @Discussion If the array s contents are all property list objects (NSString, NSData, NSArray, or NSDictionary objects), the file
	 *             written by this method can be used to initialize a new array with the class method arrayWithContentsOfFile: or the
	 *             instance method initWithContentsOfFile:. This method recursively validates that all the contained objects are property
	 *             list objects before writing out the file, and returns NO if all the objects are not property list objects, since the
	 *             resultant file would not be a valid property list.
	 **/
	
	public boolean writeToFileAtomically(NSString path, boolean flag) {
		NSString aPath = new NSString(path.getWrappedString());
		if (path.getWrappedString().contains("/") || path.getWrappedString().contains("\'")) {
			int index = path.getWrappedString().lastIndexOf("/");
			if (index > -1 && path.getWrappedString().length() > index + 1) {
				path = new NSString(path.getWrappedString().substring(index + 1));
			}
		}
		return SerializationUtil.storeObject(getWrappedList(), path.getWrappedString());
	}

	/**
	 * @Declaration : -  (BOOL)writeToURL:(NSURL *)aURL atomically:(BOOL)flag
	 * @Description : Writes the contents of the array to the location specified by a given URL.
	 * @param aURL The location at which to write the array.
	 * @param flag If YES, the array is written to an auxiliary location, and then the auxiliary location is renamed to aURL. If NO, the
	 *            array is written directly to aURL. The YES option guarantees that aURL, if it exists at all, won t be corrupted even if
	 *            the system should crash during writing.
	 * @return Return Value YES if the location is written successfully, otherwise NO.
	 * @Discussion If the array s contents are all property list objects (NSString, NSData, NSArray, or NSDictionary objects), the location
	 *             written by this method can be used to initialize a new array with the class method arrayWithContentsOfURL: or the
	 *             instance method initWithContentsOfURL:.
	 **/

	
	public boolean writeToURLAtomically(NSURL aURL, boolean atomically) {
		return writeToFileAtomically(aURL.path(), atomically);
	}

	// Collecting Paths

	/**
	 * @Declaration : -  (NSArray *)pathsMatchingExtensions:(NSArray *)filterTypes
	 * @Description : Returns an array containing all the pathname elements in the receiving array that have filename extensions from a
	 *              given array.
	 * @param filterTypes An array of NSString objects containing filename extensions. The extensions should not include the dot ( . )
	 *            character.
	 * @return Return Value An array containing all the pathname elements in the receiving array that have filename extensions from the
	 *         filterTypes array.
	 **/
	
	public NSArray pathsMatchingExtensions(NSArray filterTypes) {
		return null;
	}

	// Key-Value Observing

	/**
	 * @Declaration : -  (void)addObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath options:(NSKeyValueObservingOptions)options
	 *              context:(void *)context
	 * @Description : Raises an exception.
	 * @param observer The object to register for KVO notifications. The observer must implement the key-value observing method
	 *            observeValueForKeyPath:ofObject:change:context:.
	 * @param keyPath The key path, relative to the array, of the property to observe. This value must not be nil.
	 * @param options A combination of NSKeyValueObservingOptions values that specifies what is included in observation notifications.
	 * @param context Arbitrary data that is passed to observer in observeValueForKeyPath:ofObject:change:context:.
	 **/

	
	public void addObserverForKeyPathOptionsContext(NSObject observer, NSString keyPath,
			NSKeyValueObservingOptions options, Object context) {
		// not yet covered
	}

	/**
	 * @Declaration : -  (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath
	 * @Description : Raises an exception.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the array, for which observer is registered to receive KVO change notifications. This value
	 *            must not be nil.
	 **/
	
	public void removeObserverForKeyPath(NSObject observer, NSString forKeyPath) {
        // not yet covered
	}

	/**
	 * @Declaration : -  (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath context:(void *)context
	 * @Description : Raises an exception.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the set, for which observer is registered to receive KVO change notifications. This value must
	 *            not be nil.
	 * @param context The context passed to the notifications.
	 **/

	
	public void removeObserverForKeyPathContext(NSObject observer, NSString keyPath,
			Object context) {
        // not yet covered
	}

	/**
	 * @Declaration : -  (void)removeObserver:(NSObject *)observer fromObjectsAtIndexes:(NSIndexSet *)indexes forKeyPath:(NSString *)keyPath
	 *              context:(void *)context
	 * @Description : Raises an exception.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the array, for which observer is registered to receive KVO change notifications. This value
	 *            must not be nil.
	 * @param context The context passed to the notifications.
	 **/

	
	public void removeObserverFromObjectsAtIndexesForKeyPathContext(NSObject observer,
			NSIndexSet indexes, NSString keyPath, Object context) {
        // not yet covered
	}

	/**
	 * @Declaration : -  (void)addObserver:(NSObject *)anObserver toObjectsAtIndexes:(NSIndexSet *)indexes forKeyPath:(NSString *)keyPath
	 *              options:(NSKeyValueObservingOptions)options context:(void *)context
	 * @Description : Registers an observer to receive key value observer notifications for the specified key-path relative to the objects
	 *              at the indexes.
	 * @param anObserver The observer.
	 * @param indexes The index set.
	 * @param keyPath The key path, relative to the array, to be observed.
	 * @param options The options to be included in the notification.
	 * @param context The context passed to the notifications.
	 * @Discussion The options determine what is included in the notifications, and the context is passed in the notifications. This is not
	 *             merely a convenience method; invoking this method is potentially much faster than repeatedly invoking
	 *             addObserver:forKeyPath:options:context:.
	 **/

	
	public void addObserverToObjectsAtIndexesForKeyPathOptionsContext(NSObject anObserver,
			NSIndexSet indexes, NSString keyPath, NSKeyValueObservingOptions options,
			Object context) {
        // not yet covered
	}

	/**
	 * @Declaration : -  (void)removeObserver:(NSObject *)anObserver fromObjectsAtIndexes:(NSIndexSet *)indexes forKeyPath:(NSString
	 *              *)keyPath
	 * @Description : Removes anObserver from all key value observer notifications associated with the specified keyPath relative to the
	 *              array s objects at indexes.
	 * @param anObserver The observer.
	 * @param indexes The index set.
	 * @param keyPath The key path, relative to the array, to be observed.
	 * @Discussion This is not merely a convenience method; invoking this method is potentially much faster than repeatedly invoking
	 *             removeObserver:forKeyPath:.
	 **/

	
	public void removeObserverFromObjectsAtIndexesForKeyPath(NSObject anObserver,
			NSIndexSet indexes, NSString keyPath) {
		removeObserverFromObjectsAtIndexesForKeyPathContext(anObserver, indexes, keyPath, null);
	}

	// Key-Value Coding

	/**
	 * @Declaration : -  (void)setValue:(id)value forKey:(NSString *)key
	 * @Description : Invokes setValue:forKey: on each of the array's items using the specified value and key.
	 * @param value The object value.
	 * @param key The key to store the value.
	 **/

	
	public void setValueForKey(E value, NSString key) {
        // not yet covered
	}

	/**
	 * @Signature: valueForKey:
	 * @Declaration : - (id)valueForKey:(NSString *)key
	 * @Description : Returns an array containing the results of invoking valueForKey: using key on each of the array's objects.
	 * @param key The key to retrieve.
	 * @return Return Value The value of the retrieved key.
	 * @Discussion The returned array contains NSNull elements for each object that returns nil.
	 **/
	
	public Object valueForKey(NSString key) {
		if (key != null) {
			if (key.getWrappedString().charAt(0) == _OperatorIndicatorChar) {
				return _valueForKeyPathWithOperator(key);
			}
			if (key.equals(CountOperatorName)) {
				return count();
			}
		}
		NSMutableArray<Object> values = new NSMutableArray<Object>(getWrappedList().size());
		for (E element : getWrappedList()) {
			Object value = NSKeyValueCodingAdditions.Utility.valueForKeyPath(element,
					key.getWrappedString());
			values.addObject(value == null ? (Object) (NSKeyValueCoding.NullValue) : value);
		}

		return values;
	}

	
	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	/** added for KVO ****/

	public NSMutableArray<E> mutableClone() {
		return new NSMutableArray<E>(wrappedList);
	}

	
	@Override
	public String toString() {
		return "NSArray [" + wrappedList + "]";
	}

	// implemented Protocol methods

	
	@Override
	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state,
			Object[] stackbuf, int len) {
		return 0;
	}

	
	@Override
	public Object mutableCopyWithZone(NSZone zone) {
		return null;
	}

	public static class _AvgNumberOperator extends _Operator implements Operator {
		@Override
		public Object compute(NSArray<?> values, String keyPath) {
			int count = values.count();
			if (count != 0) {
				BigDecimal sum = _sum(values, keyPath);
				return sum.divide(new BigDecimal(count), sum.scale() + 4, 6);
			}
			return null;
		}
	}

	public static class _SumNumberOperator extends _Operator implements Operator {
		@Override
		public Object compute(NSArray<?> values, String keyPath) {
			return _sum(values, keyPath);
		}
	}

	public static class _MinOperator extends _Operator implements Operator {
		@Override
		public Object compute(NSArray<?> values, String keyPath) {
			Object min = null;
			for (Object obj : values.getWrappedList()) {
				min = _minOrMaxValue(min, _operationValue(obj, keyPath), false);
			}
			return min;
		}
	}

	public static class _MaxOperator extends _Operator implements Operator {
		@Override
		public Object compute(NSArray<?> values, String keyPath) {
			Object max = null;
			for (Object obj : values.getWrappedList()) {
				max = _minOrMaxValue(max, _operationValue(obj, keyPath), true);
			}
			return max;
		}
	}

	public static class _Operator {
		protected Object _operationValue(Object object, String keyPath) {
			return keyPath == null || keyPath.length() <= 0 ? object
					: NSKeyValueCodingAdditions.Utility.valueForKeyPath(object, keyPath);
		}

		private BigDecimal _bigDecimalForValue(Object object) {
			if (object != null) {
				if (object instanceof Number)
					return new BigDecimal(object.toString());
				if (object instanceof Boolean)
					return new BigDecimal((Boolean) object ? 1 : 0);
				if (object instanceof String)
					return new BigDecimal((String) object);
				throw new IllegalStateException("Can't convert " + object + " (class "
						+ object.getClass().getName() + ") into number");
			}
			return null;
		}

		BigDecimal _sum(NSArray<?> values, String keyPath) {
			BigDecimal sum = _BigDecimalZero;
			for (Object obj : values.getWrappedList()) {
				BigDecimal value = _bigDecimalForValue(_operationValue(obj, keyPath));
				if (value != null)
					sum = sum.add(value);
			}
			return sum;
		}

		
		Object _minOrMaxValue(Object referenceValue, Object compareValue,
				boolean trueForMaxAndFalseForMin) {
			if (referenceValue == null)
				return compareValue;
			if (compareValue == null)
				return referenceValue;

			int comparison;
			if (referenceValue instanceof Number || referenceValue instanceof Boolean) {
				Comparable<?> refValue = (Comparable<?>) referenceValue;
				if (referenceValue instanceof Boolean)
					refValue = ((Boolean) referenceValue) ? 1 : 0;
				Comparable<?> compValue = (Comparable<?>) compareValue;
				if (compareValue instanceof Boolean)
					compValue = ((Boolean) compareValue) ? 1 : 0;
				comparison = ((Comparable<Object>) refValue).compareTo(compValue);
			} else if (referenceValue instanceof NSTimestamp) {
				comparison = ((NSTimestamp) referenceValue).compare((NSTimestamp) compareValue);
			} else if (referenceValue instanceof Comparable) {
				comparison = ((Comparable<Object>) referenceValue).compareTo(compareValue);
			} else {
				throw new IllegalStateException("Cannot compare values " + referenceValue + " and "
						+ compareValue + " (they are not instance of Comparable");
			}
			if (trueForMaxAndFalseForMin) {
				if (comparison >= 0) {
					return referenceValue;
				}
			} else if (comparison <= 0) {
				return referenceValue;
			}
			return compareValue;
		}
	}

	public static class _CountOperator implements Operator {
		@Override
		public Object compute(NSArray<?> values, String keyPath) {
			return values.count();
		}
	}

	public interface Operator {
		Object compute(NSArray<?> values, String keyPath);
	}

	private static final char _OperatorIndicatorChar = '@';
	public static final String AverageOperatorName = "avg";
	public static final String CountOperatorName = "count";
	public static final String MaximumOperatorName = "max";
	public static final String MinimumOperatorName = "min";
	public static final String SumOperatorName = "sum";
	public static final int NotFound = -1;

	protected static final BigDecimal _BigDecimalZero = new BigDecimal(0);
	static final long serialVersionUID = -3789592578296478260L;

	
	public static final NSArray EmptyArray = new NSArray();
	public static final boolean CheckForNull = true;
	public static final boolean IgnoreNull = true;
	public static final boolean NoCopy = true;

	protected static final String NULL_NOT_ALLOWED = "Attempt to insert null into an NSArray.";
	protected static final String NULL_NOT_SUPPORTED = "NSArray does not support null values";
	private static NSMutableDictionary<String, Operator> _operators = new NSMutableDictionary<String, Operator>(
			8);

	static {
		try {
			setOperatorForKey(CountOperatorName, new _CountOperator());
			setOperatorForKey(MaximumOperatorName, new _MaxOperator());
			setOperatorForKey(MinimumOperatorName, new _MinOperator());
			setOperatorForKey(SumOperatorName, new _SumNumberOperator());
			setOperatorForKey(AverageOperatorName, new _AvgNumberOperator());
		} catch (Exception e) {
			NSLog.err.appendln("Exception occurred in initializer");
			if (NSLog.debugLoggingAllowedForLevel(1)) {
				NSLog.debug.appendln(e);
			}
			throw NSForwardException._runtimeExceptionForThrowable(e);
		}
	}

	
	@Override
	public NSArray<E> clone() {
		NSArray arrayCopie = (NSArray) InstanceTypeFactory.getInstance(this.getClass());
		arrayCopie.setWrappedList(new ArrayList(this.getWrappedList()));
		return arrayCopie;
	}

	public static <T> NSArray<T> emptyArray() {
		return EmptyArray;
	}

	public int _shallowHashCode() {
		return NSArray.class.hashCode();
	}

	
	@Override
	public int hashCode() {
		int hash = 1;
		int index = 0;
		Iterator<E> i = getWrappedList().iterator();
		while (i.hasNext() && index <= 16) {
			E element = i.next();
			index++;
			if (element instanceof _NSFoundationCollection) {
				hash ^= ((_NSFoundationCollection) element)._shallowHashCode();
			} else {
				hash ^= element.hashCode();
			}
		}

		return hash;
	}

	
	@Override
	public boolean equals(Object o) {
		return super.equals(o);

	}

	public NSArray<E> immutableClone() {
		return new NSArray<E>(getWrappedList());
	}

	protected Object[] objectsNoCopy() {
		Object[] objs = getWrappedList().toArray();
		return objs != null ? objs : _NSCollectionPrimitives.EmptyArray;
	}

	public static NSArray<String> operatorNames() {
		NSArray<String> operatorNames;
		synchronized (_operators) {
			operatorNames = _operators.allKeys();
		}
		return operatorNames;
	}

	public static void setOperatorForKey(String operatorName, Operator arrayOperator) {
		if (operatorName == null) {
			throw new IllegalArgumentException("Operator key cannot be null");
		}
		if (arrayOperator == null) {
			throw new IllegalArgumentException("Operator cannot be null for " + operatorName);
		}
		synchronized (_operators) {
			_operators.setObjectForKey(arrayOperator, operatorName);
		}
	}

	public static Operator operatorForKey(String operatorName) {
		Operator arrayOperator;
		synchronized (_operators) {
			arrayOperator = (Operator) _operators.objectForKey(operatorName);
		}
		return arrayOperator;
	}

	public static void removeOperatorForKey(String operatorName) {
		if (operatorName != null) {
			synchronized (_operators) {
				_operators.removeObjectForKey(operatorName);
			}
		}
	}

	private Object _valueForKeyPathWithOperator(NSString NSkeyPath) {
		String keyPath = NSkeyPath.getWrappedString();
		int index = keyPath.indexOf('.');
		String operatorName;
		String operatorPath;
		if (index < 0) {
			operatorName = keyPath.substring(1);
			operatorPath = "";
		} else {
			operatorName = keyPath.substring(1, index);
			operatorPath = index >= keyPath.length() - 1 ? "" : keyPath.substring(index + 1);
		}
		Operator arrayOperator = operatorForKey(operatorName);
		if (arrayOperator != null) {
			return arrayOperator.compute(this, operatorPath);
		}
		throw new IllegalArgumentException(
				"No key operator available to compute aggregate " + keyPath);
	}

	public int indexOfIdenticalObject(E object, NSRange range) {
		if (object == null || range == null)
			return NotFound;

		if (range.maxRange() > count()) {
			throw new IllegalArgumentException("Range [" + range.location() + "; " + range.length()
					+ "] out of bounds [0, " + (count() - 1) + "]");
		}

		NSArray<E> subArray = subarrayWithRange(range);
		return subArray.indexOfIdenticalObject(object) + range.location();
	}

	public int indexOfIdenticalObject(E object) {
		if (object == null)
			return NotFound;
		for (int i = 0; i < getWrappedList().size(); i++) {
			if (objectAtIndex(i) == object)
				return i;
		}
		return NotFound;
	}

	@Override
	public Iterator<E> iterator() {
		return getWrappedList().iterator();
	}

	@Override
	public boolean hasNext() {
		return getWrappedList().iterator().hasNext();
	}

	@Override
	public E next() {
		return getWrappedList().iterator().next();
	}

	@Override
	public void remove() {
		getWrappedList().iterator().remove();
	}

	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<array>");
		xml.append("\n");
		for (E o : getWrappedList()) {
			((NSObject) o).toXML(xml, level + 1);
			xml.append("\n");
		}
		indent(xml, level);
		xml.append("</array>");
	}

	@Override
	public void assignIDs(BinaryPropertyListWriter out) {
		super.assignIDs(out);
		for (E o : getWrappedList()) {
			((NSObject) o).assignIDs(out);
		}
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		out.writeIntHeader(0xA, getWrappedList().size());
		for (E obj : getWrappedList()) {
			out.writeID(out.getID((NSObject) obj));
		}
	}

	/**
	 * Generates a valid ASCII property list which has this NSArray as its root object. The generated property list complies with the format
	 * as described in
	 * <a href= "https://developer.apple.com/library/mac/#documentation/Cocoa/Conceptual/PropertyLists/OldStylePlists/OldStylePLists.html" >
	 * Property List Programming Guide - Old-Style ASCII Property Lists</a>.
	 *
	 * @return ASCII representation of this object.
	 */
	public String toASCIIPropertyList() {
		StringBuilder ascii = new StringBuilder();
		toASCII(ascii, 0);
		ascii.append("\n");
		return ascii.toString();
	}

	/**
	 * Generates a valid ASCII property list in GnuStep format which has this NSArray as its root object. The generated property list
	 * complies with the format as described in
	 * <a href= "http://www.gnustep.org/resources/documentation/Developer/Base/Reference/NSPropertyList.html" > GnuStep -
	 * NSPropertyListSerialization class documentation </a>
	 *
	 * @return GnuStep ASCII representation of this object.
	 */
	public String toGnuStepASCIIPropertyList() {
		StringBuilder ascii = new StringBuilder();
		toASCIIGnuStep(ascii, 0);
		ascii.append("\n");
		return ascii.toString();
	}

	@Override
	public void toASCII(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.ARRAY_BEGIN_TOKEN);
		int indexOfLastNewLine = ascii.lastIndexOf("\n");
		for (int i = 0; i < getWrappedList().size(); i++) {
			Class<?> objClass = getWrappedList().get(i).getClass();
			if ((objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) && indexOfLastNewLine != ascii.length()) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
				((NSObject) getWrappedList().get(i)).toASCII(ascii, level + 1);
			} else {
				if (i != 0)
					ascii.append(" ");
				((NSObject) getWrappedList().get(i)).toASCII(ascii, 0);
			}

			if (i != getWrappedList().size() - 1)
				ascii.append(ASCIIPropertyListParser.ARRAY_ITEM_DELIMITER_TOKEN);

			if (ascii.length() - indexOfLastNewLine > 80) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
			}
		}
		ascii.append(ASCIIPropertyListParser.ARRAY_END_TOKEN);
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.ARRAY_BEGIN_TOKEN);
		int indexOfLastNewLine = ascii.lastIndexOf("\n");
		for (int i = 0; i < getWrappedList().size(); i++) {
			Class<?> objClass = getWrappedList().get(i).getClass();
			if ((objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) && indexOfLastNewLine != ascii.length()) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
				((NSObject) getWrappedList().get(i)).toASCIIGnuStep(ascii, level + 1);
			} else {
				if (i != 0)
					ascii.append(" ");
				((NSObject) getWrappedList().get(i)).toASCIIGnuStep(ascii, 0);
			}

			if (i != getWrappedList().size() - 1)
				ascii.append(ASCIIPropertyListParser.ARRAY_ITEM_DELIMITER_TOKEN);

			if (ascii.length() - indexOfLastNewLine > 80) {
				ascii.append("\n");
				indexOfLastNewLine = ascii.length();
			}
		}
		ascii.append(ASCIIPropertyListParser.ARRAY_END_TOKEN);
	}

	public NSObject[] getArray() {
		return getWrappedList().toArray(null);
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
        //
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	/*
	 * Added Methods to match needs :
	 */
	
	public CFArrayRef toCFArray() {
		CFArrayRef result = new CFArrayRef();
		result.setWrappedList(this.getWrappedList());
		return result;

	}

	
	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

}