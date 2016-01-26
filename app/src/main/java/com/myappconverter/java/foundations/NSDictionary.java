
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.java.foundations.utilities.ASCIIPropertyListParser;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.java.foundations.utilities.PropertyListParser;
import com.myappconverter.java.foundations.utilities._NSFoundationCollection.NullHandling;
import com.myappconverter.mapping.utils.AndroidXMLPlistParser;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.PerformBlock;

import org.xml.sax.SAXException;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



public class NSDictionary<K extends Object, V extends Object> extends NSObject
		implements NSCopying, NSMutableCopying, NSFastEnumeration, NSSecureCoding, Iterable<K>,
		Iterator<K>, Serializable {

	// inner data
	public static final NSDictionary EmptyDictionary = new NSDictionary();

	public static final boolean CheckForNull = true;
	public static final boolean IgnoreNull = true;
	protected static final String NULL_NOT_ALLOWED = "Attempt to insert null into an NSDictionary.";
	transient protected Set<Entry<K, V>> _entrySetCache;

	 Map<K, V> wrappedDictionary;
	transient private NSData innerTXTNSData;
	private File plistFile;

	public Map<K, V> getWrappedDictionary() {
		return wrappedDictionary;
	}

	public void setWrappedDictionary(Map<K, V> wrappedDictionary) {
		this.wrappedDictionary = wrappedDictionary;
	}

	public NSData getTxtNSData() {
		return innerTXTNSData;
	}

	public void setTxtNSData(NSData innerNSData) {
		this.innerTXTNSData = innerNSData;
	}

	// Creating a Dictionary
	/**
	 * @Signature: dictionary
	 * @Declaration : + (instancetype)dictionary
	 * @Description : Creates and returns an empty dictionary.
	 * @return Return Value A new empty dictionary.
	 * @Discussion This method is declared primarily for use with mutable subclasses of NSDictionary. If you don’t want a temporary object,
	 *             you can also create an empty dictionary using alloc... and init.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionary(Class clazz) {
		NSDictionary<K, V> anOtherDic = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		anOtherDic.wrappedDictionary = new HashMap<K, V>();
		return anOtherDic;
	}

	
	public static <K, V> NSDictionary<K, V> dictionaryWithCapacity(Class clazz, int capacity) {
		NSDictionary<K, V> anOtherDic = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		anOtherDic.wrappedDictionary = new HashMap<K, V>(capacity);
		return anOtherDic;
	}

	/**
	 * @Signature: dictionaryWithContentsOfFile:
	 * @Declaration : + (id)dictionaryWithContentsOfFile:(NSString *)path
	 * @Description : Creates and returns a dictionary using the keys and values found in a file specified by a given path.
	 * @param path A full or relative pathname. The file identified by path must contain a string representation of a property list whose
	 *            root object is a dictionary.
	 * @return Return Value A new dictionary that contains the dictionary at path, or nil if there is a file error or if the contents of the
	 *         file are an invalid representation of a dictionary.
	 * @Discussion The dictionary representation in the file identified by path must contain only property list objects (NSString, NSData,
	 *             NSDate, NSNumber, NSArray, or NSDictionary objects). For more details, see Property List Programming Guide. The objects
	 *             contained by this dictionary are immutable, even if the dictionary is mutable.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithContentsOfFile(NSString path) {
		NSDictionary<K, V> anOtherDic = new NSDictionary<K, V>();
		try {
			NSString compentExt = path.lastPathComponent();
			InputStream in = GenericMainContext.sharedContext.getAssets()
					.open(compentExt.getWrappedString());
			anOtherDic = (NSDictionary<K, V>) PropertyListParser.parse(in);
			return anOtherDic;
		} catch (Exception e) {
			Logger.getLogger("context", String.valueOf(e));
		}
		return null;

	}

	/**
	 * @Signature: dictionaryWithContentsOfURL:
	 * @Declaration : + (id)dictionaryWithContentsOfURL:(NSURL *)aURL
	 * @Description : Creates and returns a dictionary using the keys and values found in a resource specified by a given URL.
	 * @param aURL An URL that identifies a resource containing a string representation of a property list whose root object is a
	 *            dictionary.
	 * @return Return Value A new dictionary that contains the dictionary at aURL, or nil if there is an error or if the contents of the
	 *         resource are an invalid representation of a dictionary.
	 * @Discussion The dictionary representation in the file identified by path must contain only property list objects (NSString, NSData,
	 *             NSDate, NSNumber, NSArray, or NSDictionary objects). For more details, see Property List Programming Guide. The objects
	 *             contained by this dictionary are immutable, even if the dictionary is mutable.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithContentsOfURL(NSURL aURL) {
		NSDictionary<K, V> anOtherDic = new NSDictionary<K, V>();
		AndroidXMLPlistParser plistParserHandler = new AndroidXMLPlistParser();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		SAXParser sp;
		try {
			sp = factory.newSAXParser();
			sp.parse(aURL.path().getWrappedString(), plistParserHandler);
			Map<NSString, NSObject> result = plistParserHandler.getResult();
			if (result != null) {
				anOtherDic.wrappedDictionary.putAll((Map<? extends K, ? extends V>) result);
				return anOtherDic;
			}
		} catch (ParserConfigurationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (SAXException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: dictionaryWithDictionary:
	 * @Declaration : + (instancetype)dictionaryWithDictionary:(NSDictionary *)otherDictionary
	 * @Description : Creates and returns a dictionary containing the keys and values from another given dictionary.
	 * @param otherDictionary A dictionary containing the keys and values with which to initialize the new dictionary.
	 * @return Return Value A new dictionary containing the keys and values found in otherDictionary.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithDictionary(Class clazz,
			NSDictionary<K, V> otherDictionary) {
		NSDictionary<K, V> nsd = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		nsd.setWrappedDictionary(new HashMap<K, V>(otherDictionary.wrappedDictionary));
		return nsd;
	}

	/**
	 * @Signature: dictionaryWithObject:forKey:
	 * @Declaration : + (instancetype)dictionaryWithObject:(id)anObject forKey:(id<NSCopying>)aKey
	 * @Description : Creates and returns a dictionary containing a given key and value.
	 * @param anObject The value corresponding to aKey. If this value is nil, an NSInvalidArgumentException is raised.
	 * @param aKey The key for anObject. If this value is nil, an NSInvalidArgumentException is raised.
	 * @return Return Value A new dictionary containing a single object, anObject, for a single key, aKey.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithObjectForKey(Class clazz, V anObject,
			K aKey) {
		if (((NSObject) aKey).conformsToProtocol(NSCopying.class)) {
			NSDictionary<K, V> nsd = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
			nsd.wrappedDictionary.put(aKey, anObject);
			return nsd;
		}
		return null;
	}

	/**
	 * @Signature: dictionaryWithObjects:forKeys:
	 * @Declaration : + (instancetype)dictionaryWithObjects:(NSArray *)objects forKeys:(NSArray *)keys
	 * @Description : Creates and returns a dictionary containing entries constructed from the contents of an array of keys and an array of
	 *              values.
	 * @param objects An array containing the values for the new dictionary.
	 * @param keys An array containing the keys for the new dictionary. Each key is copied (using copyWithZone:; keys must conform to the
	 *            NSCopying protocol), and the copy is added to the dictionary.
	 * @return Return Value A new dictionary containing entries constructed from the contents of objects and keys.
	 * @Discussion This method steps through the objects and keys arrays, creating entries in the new dictionary as it goes. An
	 *             NSInvalidArgumentException is raised if objects and keys don’t have the same number of elements.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithObjectsForKeys(Class clazz,
			NSArray<V> objects, NSArray<K> keys) {
		NSDictionary<K, V> nsd = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		if (keys.count() != objects.count()) {
			throw new IllegalArgumentException(
					"NSInvalidArgumentException :: Array lengths do not match.");
		}
		for (int i = 0; i < keys.count(); i++) {
			nsd.wrappedDictionary.put(keys.objectAtIndex(i), objects.objectAtIndex(i));
		}
		return nsd;
	}

	/**
	 * @Signature: dictionaryWithObjects:forKeys:count:
	 * @Declaration : + (instancetype)dictionaryWithObjects:(const id [])objects forKeys:(const id<NSCopying> [])keys
	 *              count:(NSUInteger)count
	 * @Description : Creates and returns a dictionary containing count objects from the objects array.
	 * @param objects A C array of values for the new dictionary.
	 * @param keys A C array of keys for the new dictionary. Each key is copied (using copyWithZone:; keys must conform to the NSCopying
	 *            protocol), and the copy is added to the new dictionary.
	 * @param count The number of elements to use from the keys and objects arrays. count must not exceed the number of elements in objects
	 *            or keys.
	 * @Discussion This method steps through the objects and keys arrays, creating entries in the new dictionary as it goes. An
	 *             NSInvalidArgumentException is raised if a key or value object is nil.
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithObjectsForKeysCount(Class clazz,
			V[] objects, K[] keys, int count) {
		NSDictionary<K, V> nsd = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(clazz);
		if (keys.length != objects.length) {
			throw new IllegalArgumentException(
					"NSInvalidArgumentException :: Array lengths do not match.");
		}
		for (int i = 0; i < count; i++) {
			if (((NSObject) keys[i]).conformsToProtocol(NSCopying.class))
				nsd.wrappedDictionary.put(keys[i], objects[i]);
		}
		return nsd;
	}

	/**
	 * @Signature: initWithDictionary:
	 * @Declaration : + (instancetype)initWithDictionary:(id)firstObject, , ...
	 * @Description : Creates and returns a dictionary containing entries constructed from the specified set of values and keys.
	 * @param firstObject The first value to add to the new dictionary.
	 * @param ... First the key for firstObject, then a null-terminated list of alternating values and keys. If any key is nil, an
	 *            NSInvalidArgumentException is raised.
	 * @Discussion This method is similar to dictionaryWithObjects:forKeys:, differing only in the way key-value pairs are specified. For
	 *             example: NSDictionary *dict = [NSDictionary initWithDictionary: @"value1", @"key1", @"value2", @"key2", nil];
	 **/
	
	public static <K, V> NSDictionary<K, V> dictionaryWithObjectsAndKeys(Class clazz,
			K... firstObject) {
		NSDictionary<K, V> nsd = null;
		try {
			nsd = (NSDictionary<K, V>) clazz.newInstance();
			if (nsd.wrappedDictionary == null)
				nsd.wrappedDictionary = new HashMap();
			if (firstObject != null && firstObject.length > 1) {
				for (int i = 0; i < firstObject.length - 1; i = i + 2) {
					if (firstObject[i + 1] == null) {
						throw new IllegalArgumentException(
								"NSInvalidArgumentException :: Array lengths do not match.");
					}
					nsd.wrappedDictionary.put(firstObject[i + 1], (V) firstObject[i]);
				}
			}
		} catch (InstantiationException e) {
            Logger.getLogger("context", String.valueOf(e));
		} catch (IllegalAccessException e) {
            Logger.getLogger("context", String.valueOf(e));
		}
		return nsd;
	}

	// Initializing an NSDictionary Instance

	/**
	 * @Signature: initWithContentsOfFile:
	 * @Declaration : - (id)initWithContentsOfFile:(NSString *)path
	 * @Description : Initializes a newly allocated dictionary using the keys and values found in a file at a given path.
	 * @param path A full or relative pathname. The file identified by path must contain a string representation of a property list whose
	 *            root object is a dictionary.
	 * @return Return Value An initialized dictionary—which might be different than the original receiver—that contains the dictionary at
	 *         path, or nil if there is a file error or if the contents of the file are an invalid representation of a dictionary.
	 * @Discussion The dictionary representation in the file identified by path must contain only property list objects (NSString, NSData,
	 *             NSDate, NSNumber, NSArray, or NSDictionary objects). For more details, see Property List Programming Guide. The objects
	 *             contained by this dictionary are immutable, even if the dictionary is mutable.
	 **/
	
	public Object initWithContentsOfFile(NSString path) {
		return dictionaryWithContentsOfFile(path);
	}

	/**
	 * @Signature: initWithContentsOfURL:
	 * @Declaration : - (id)initWithContentsOfURL:(NSURL *)aURL
	 * @Description : Initializes a newly allocated dictionary using the keys and values found at a given URL.
	 * @param aURL An URL that identifies a resource containing a string representation of a property list whose root object is a
	 *            dictionary.
	 * @return Return Value An initialized dictionary—which might be different than the original receiver—that contains the dictionary at
	 *         aURL, or nil if there is an error or if the contents of the resource are an invalid representation of a dictionary.
	 * @Discussion The dictionary representation in the file identified by path must contain only property list objects (NSString, NSData,
	 *             NSDate, NSNumber, NSArray, or NSDictionary objects). For more details, see Property List Programming Guide. The objects
	 *             contained by this dictionary are immutable, even if the dictionary is mutable.
	 **/
	
	public NSDictionary initWithContentsOfURL(NSURL aURL) {
		return dictionaryWithContentsOfURL(aURL);
	}

	/**
	 * @Signature: initWithDictionary:
	 * @Declaration : - (instancetype)initWithDictionary:(NSDictionary *)otherDictionary
	 * @Description : Initializes a newly allocated dictionary by placing in it the keys and values contained in another given dictionary.
	 * @param otherDictionary A dictionary containing the keys and values with which to initialize the new dictionary.
	 * @return Return Value An initialized dictionary—which might be different than the original receiver—containing the keys and values
	 *         found in otherDictionary.
	 **/
	
	public NSDictionary<K, V> initWithDictionary(NSDictionary<K, V> otherDictionary) {
		this.wrappedDictionary = new HashMap();
		this.wrappedDictionary.putAll(otherDictionary.wrappedDictionary);
		return this;
	}

	/**
	 * @Signature: initWithDictionary:copyItems:
	 * @Declaration : - (instancetype)initWithDictionary:(NSDictionary *)otherDictionary copyItems:(BOOL)flag
	 * @Description : Initializes a newly allocated dictionary using the objects contained in another given dictionary.
	 * @param otherDictionary A dictionary containing the keys and values with which to initialize the new dictionary.
	 * @param flag If YES, each object in otherDictionary receives a copyWithZone: message to create a copy of the object—objects must
	 *            conform to the NSCopying protocol. In a managed memory environment, this is instead of the retain message the object would
	 *            otherwise receive. The object copy is then added to the returned dictionary. If NO, then in a managed memory environment
	 *            each object in otherDictionary simply receives a retain message when it is added to the returned dictionary.
	 * @return Return Value An initialized object—which might be different than the original receiver—containing the keys and values found
	 *         in otherDictionary.
	 * @Discussion After an immutable dictionary has been initialized in this way, it cannot be modified. The copyWithZone: method performs
	 *             a shallow copy. If you have a collection of arbitrary depth, passing YES for the flag parameter will perform an immutable
	 *             copy of the first level below the surface. If you pass NO the mutability of the first level is unaffected. In either
	 *             case, the mutability of all deeper levels is unaffected.
	 **/
	
	public NSDictionary<K, V> initWithDictionaryCopyItems(NSDictionary<K, V> otherDictionary,
			boolean flag) {
		if (flag) {
			this.wrappedDictionary = new HashMap<K, V>(otherDictionary.wrappedDictionary);
			return this;

		}
		NSDictionary<K, V> dict = (NSDictionary<K, V>) InstanceTypeFactory
				.getInstance(this.getClass());
		dict.setWrappedDictionary(otherDictionary.getWrappedDictionary());
		return dict;
	}

	/**
	 * @Signature: initWithObjects:forKeys:
	 * @Declaration : - (instancetype)initWithObjects:(NSArray *)objects forKeys:(NSArray *)keys
	 * @Description : Initializes a newly allocated dictionary with entries constructed from the contents of the objects and keys arrays.
	 * @param objects An array containing the values for the new dictionary.
	 * @param keys An array containing the keys for the new dictionary. Each key is copied (using copyWithZone:; keys must conform to the
	 *            NSCopying protocol), and the copy is added to the new dictionary.
	 * @Discussion This method steps through the objects and keys arrays, creating entries in the new dictionary as it goes. An
	 *             NSInvalidArgumentException is raised if the objects and keys arrays do not have the same number of elements.
	 **/
	
	public NSDictionary<K, V> initWithObjectsForKeys(NSArray<V> objects, NSArray<K> keys) {
		if (keys.count() != objects.count()) {
			throw new IllegalArgumentException(
					"NSInvalidArgumentException :: Array lengths do not match.");
		}

		NSDictionary<K, V> nsd;

		nsd = (NSDictionary<K, V>) InstanceTypeFactory.getInstance(this.getClass());
		for (int i = 0; i < keys.count(); i++) {
			nsd.wrappedDictionary.put(keys.objectAtIndex(i), objects.objectAtIndex(i));
		}
		return nsd;
	}

	/**
	 * @Signature: initWithObjects:forKeys:count:
	 * @Declaration : - (instancetype)initWithObjects:(const id [])objects forKeys:(const id<NSCopying> [])keys count:(NSUInteger)count
	 * @Description : Initializes a newly allocated dictionary with count entries.
	 * @param objects A C array of values for the new dictionary.
	 * @param keys A C array of keys for the new dictionary. Each key is copied (using copyWithZone:; keys must conform to the NSCopying
	 *            protocol), and the copy is added to the new dictionary.
	 * @param count The number of elements to use from the keys and objects arrays. count must not exceed the number of elements in objects
	 *            or keys.
	 * @Discussion This method steps through the objects and keys arrays, creating entries in the new dictionary as it goes. An
	 *             NSInvalidArgumentException is raised if a key or value object is nil. This method is a designated initializer of
	 *             NSDictionary.
	 **/
	
	public NSDictionary<K, V> initWithObjectsForKeysCount(V[] objects, K[] keys, int count) {
		NSDictionary<K, V> nsd = (NSDictionary<K, V>) InstanceTypeFactory
				.getInstance(this.getClass());
		if (keys.length != objects.length) {
			throw new IllegalArgumentException(
					"NSInvalidArgumentException :: Array lengths do not match.");
		}
		for (int i = 0; i < count; i++) {
			nsd.wrappedDictionary.put(keys[i], objects[i]);
		}
		return nsd;
	}

	/**
	 * @Signature: initWithObjectsAndKeys:
	 * @Declaration : - (instancetype)initWithObjectsAndKeys:(id)firstObject, , ...
	 * @Description : Initializes a newly allocated dictionary with entries constructed from the specified set of values and keys.
	 * @param firstObject The first value to add to the new dictionary.
	 * @param ... First the key for firstObject, then a null-terminated list of alternating values and keys. If any key is nil, an
	 *            NSInvalidArgumentException is raised.
	 * @Discussion This method is similar to initWithObjects:forKeys:, differing only in the way in which the key-value pairs are specified.
	 *             For example: NSDictionary *dict = [[NSDictionary alloc] initWithObjectsAndKeys: @"value1", @"key1", @"value2", @"key2",
	 *             nil];
	 **/
	
	public NSDictionary<K, V> initWithObjectsAndKeys(Object... firstObject) {
		this.wrappedDictionary = new HashMap();
		for (int i = 0; i < firstObject.length - 1; i = i + 2) {
			this.wrappedDictionary.put((K) firstObject[i + 1], (V) firstObject[i]);
		}
		return this;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes a newly allocated dictionary.
	 * @return Return Value A dictionary.
	 * @Discussion This method is a designated initializer of NSDictionary.
	 **/
	@Override
	
	public NSDictionary<K, V> init() {
		_initializeWithCapacity(0);
		return this;
	}

	// Comparing Dictionaries

	/**
	 * @Signature: isEqualToDictionary:
	 * @Declaration : - (BOOL)isEqualToDictionary:(NSDictionary *)otherDictionary
	 * @Description : Returns a Boolean value that indicates whether the contents of the receiving dictionary are equal to the contents of
	 *              another given dictionary.
	 * @param otherDictionary The dictionary with which to compare the receiving dictionary.
	 * @return Return Value YES if the contents of otherDictionary are equal to the contents of the receiving dictionary, otherwise NO.
	 * @Discussion Two dictionaries have equal contents if they each hold the same number of entries and, for a given key, the corresponding
	 *             value objects in each dictionary satisfy the isEqual: test.
	 **/
	
	public boolean isEqualToDictionary(NSDictionary<K, V> otherDictionary) {
		return (wrappedDictionary.equals(otherDictionary.wrappedDictionary));
	}

	// Accessing Keys and Values

	/**
	 * @Signature: allKeys
	 * @Declaration : - (NSArray *)allKeys
	 * @Description : Returns a new array containing the dictionary’s keys.
	 * @return Return Value A new array containing the dictionary’s keys, or an empty array if the dictionary has no entries.
	 * @Discussion The order of the elements in the array is not defined.
	 **/
	
	public NSArray<K> allKeys() {
		NSArray<K> nsArray = new NSArray<K>();
		Set<K> keys = wrappedDictionary.keySet();
		for (K akey : keys) {
			nsArray.getWrappedList().add(akey);
		}
		return nsArray;
	}

	public NSArray<K> getAllKeys() {
		return allKeys();
	}

	/**
	 * @Signature: allKeysForObject:
	 * @Declaration : - (NSArray *)allKeysForObject:(id)anObject
	 * @Description : Returns a new array containing the keys corresponding to all occurrences of a given object in the dictionary.
	 * @param anObject The value to look for in the dictionary.
	 * @return Return Value A new array containing the keys corresponding to all occurrences of anObject in the dictionary. If no object
	 *         matching anObject is found, returns an empty array.
	 * @Discussion Each object in the dictionary is sent an isEqual: message to determine if it’s equal to anObject.
	 **/
	
	public NSArray<K> allKeysForObject(V aObject) {
		NSArray<K> nsArray = new NSArray<K>();
		if (aObject != null) {
			Iterator<Entry<K, V>> it = wrappedDictionary.entrySet().iterator();
			while (it.hasNext()) {
				Entry<K, V> entry = it.next();
				if ((entry.getValue() != null) && (aObject.equals(entry.getValue()))) {
					nsArray.getWrappedList().add(entry.getKey());
				}
			}
		}
		return nsArray;
	}

	/**
	 * @Signature: allValues
	 * @Declaration : - (NSArray *)allValues
	 * @Description : Returns a new array containing the dictionary’s values.
	 * @return Return Value A new array containing the dictionary’s values, or an empty array if the dictionary has no entries.
	 * @Discussion The order of the values in the array isn’t defined.
	 **/
	
	public NSArray<V> allValues() {
		Collection<V> values = wrappedDictionary.values();
		List<V> list = new ArrayList<V>(values);
		return new NSArray<V>(list);
	}

	public NSArray<V> getAllValues() {
		return allValues();
	}

	/**
	 * @Signature: getObjects:andKeys:
	 * @Declaration : - (void)getObjects:(id [])objects andKeys:(id [])keys
	 * @Description : Returns by reference C arrays of the keys and values in the dictionary.
	 * @param objects Upon return, contains a C array of the values in the dictionary.
	 * @param keys Upon return, contains a C array of the keys in the dictionary.
	 * @Discussion The elements in the returned arrays are ordered such that the first element in objects is the value for the first key in
	 *             keys and so on.
	 **/
	
	public void getObjectsAndKeys(Object[][] objects, Object[][] keys) {
		objects[0] = wrappedDictionary.values().toArray();
		keys[0] = wrappedDictionary.keySet().toArray();
	}

	/**
	 * @Signature: objectForKey:
	 * @Declaration : - (id)objectForKey:(id)aKey
	 * @Description : Returns the value associated with a given key.
	 * @param aKey The key for which to return the corresponding value.
	 * @return Return Value The value associated with aKey, or nil if no value is associated with aKey.
	 **/
	
	public Object objectForKey(Object key) {
		Object result = null;
		if (key instanceof NSString) {
			result = wrappedDictionary.get(key);
			return result;
		}

		result = wrappedDictionary.get(key);
		return result;
	}

	/**
	 * @Signature: objectForKeyedSubscript:
	 * @Declaration : - (id)objectForKeyedSubscript:(id)key
	 * @Description : Returns the value associated with a given key.
	 * @param key The key for which to return the corresponding value.
	 * @return Return Value The value associated with aKey, or nil if no value is associated with aKey.
	 * @Discussion This method behaves the same as objectForKey:.
	 **/
	
	public Object objectForKeyedSubscript(K key) {
		return objectForKey(key);
	}

	/**
	 * @Signature: objectsForKeys:notFoundMarker:
	 * @Declaration : - (NSArray *)objectsForKeys:(NSArray *)keys notFoundMarker:(id)anObject
	 * @Description : Returns the set of objects from the dictionary that corresponds to the specified keys as an NSArray.
	 * @param keys An NSArray containing the keys for which to return corresponding values.
	 * @param anObject The marker object to place in the corresponding element of the returned array if an object isn’t found in the
	 *            dictionary to correspond to a given key.
	 * @Discussion The objects in the returned array and the keys array have a one-for-one correspondence, so that the nthe object in the
	 *             returned array corresponds to the nthe key in keys.
	 **/
	
	public NSArray objectsForKeysNotFoundMarker(NSArray keys, V notFoundMarker) {
		if (keys != null) {
			int size = keys.getWrappedList().size();
			NSMutableArray array = new NSMutableArray(size);
			for (Object key : keys.getWrappedList()) {
				Object object = objectForKey(key);
				if (object != null) {
					array.addObject(object);
					continue;
				}
				if (notFoundMarker != null) {
					array.addObject(notFoundMarker);
				}
			}
			return array;
		}
		return NSArray.emptyArray();

	}

	/**
	 * @Signature: valueForKey:
	 * @Declaration : - (id)valueForKey:(NSString *)key
	 * @Description : Returns the value associated with a given key.
	 * @param key The key for which to return the corresponding value. Note that when using key-value coding, the key must be a string (see
	 *            “Key-Value Coding Fundamentals�?).
	 * @return Return Value The value associated with key.
	 * @Discussion If key does not start with “@�?, invokes objectForKey:. If key does start with “@�?, strips the “@�? and invokes [super
	 *             valueForKey:] with the rest of the key.
	 **/
	
	public Object valueForKey(K key) {
		Object value = objectForKey(key);
		if ((value == null) && (key != null)) {
			if ("allValues".equals(key))
				return allValues();
			if ("allKeys".equals(key))
				return allKeys();
			if ("count".equals(key))
				return count();
		}

		return value;
	}

	// Storing Dictionaries

	/**
	 * @Signature: writeToFile:atomically:
	 * @Declaration : - (BOOL)writeToFile:(NSString *)path atomically:(BOOL)flag
	 * @Description : Writes a property list representation of the contents of the dictionary to a given path.
	 * @param path The path at which to write the file. If path contains a tilde (~) character, you must expand it with
	 *            stringByExpandingTildeInPath before invoking this method.
	 * @param flag A flag that specifies whether the file should be written atomically. If flag is YES, the dictionary is written to an
	 *            auxiliary file, and then the auxiliary file is renamed to path. If flag is NO, the dictionary is written directly to path.
	 *            The YES option guarantees that path, if it exists at all, won’t be corrupted even if the system should crash during
	 *            writing.
	 * @return Return Value YES if the file is written successfully, otherwise NO.
	 * @Discussion This method recursively validates that all the contained objects are property list objects (instances of NSData, NSDate,
	 *             NSNumber, NSString, NSArray, or NSDictionary) before writing out the file, and returns NO if all the objects are not
	 *             property list objects, since the resultant file would not be a valid property list. If the dictionary’s contents are all
	 *             property list objects, the file written by this method can be used to initialize a new dictionary with the class method
	 *             dictionaryWithContentsOfFile: or the instance method initWithContentsOfFile:. For more information about property lists,
	 *             see Property List Programming Guide.
	 **/
	
	public boolean writeToFileAtomically(NSString path, boolean flag) {
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path.getWrappedString()), Charset.defaultCharset()));
			writer.write(wrappedDictionary.toString());
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return false;
	}

	/**
	 * @Signature: writeToURL:atomically:
	 * @Declaration : - (BOOL)writeToURL:(NSURL *)aURL atomically:(BOOL)flag
	 * @Description : Writes a property list representation of the contents of the dictionary to a given URL.
	 * @param aURL The URL to which to write the dictionary.
	 * @param flag A flag that specifies whether the output should be written atomically. If flag is YES, the dictionary is written to an
	 *            auxiliary location, and then the auxiliary location is renamed to aURL. If flag is NO, the dictionary is written directly
	 *            to aURL. The YES option guarantees that aURL, if it exists at all, won’t be corrupted even if the system should crash
	 *            during writing. flag is ignored if aURL is of a type that cannot be written atomically.
	 * @return Return Value YES if the location is written successfully, otherwise NO.
	 * @Discussion This method recursively validates that all the contained objects are property list objects (instances of NSData, NSDate,
	 *             NSNumber, NSString, NSArray, or NSDictionary) before writing out the file, and returns NO if all the objects are not
	 *             property list objects, since the resultant output would not be a valid property list. If the dictionary’s contents are
	 *             all property list objects, the location written by this method can be used to initialize a new dictionary with the class
	 *             method dictionaryWithContentsOfURL: or the instance method initWithContentsOfURL:. For more information about property
	 *             lists, see Property List Programming Guide.
	 **/
	
	public boolean writeToURLAtomically(NSURL aURL, boolean flag) {
		Writer writer = null;
		File file;
		boolean check = false;
		try {

			file = new File(aURL.getWrappedURL().toString());
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			writer = new BufferedWriter(
					new OutputStreamWriter(fileOutputStream, Charset.defaultCharset()));
			writer.write(wrappedDictionary.toString());
			check = true;
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} finally {
			try {
				writer.close();
				// FIXME Return statements should not appear in finally blocks
				// because the semantics is too difficult to
				// understand.
				check = true;
			} catch (Exception e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
		return check;
	}

	/**
	 * @Signature: keysOfEntriesPassingTest:
	 * @Declaration : - (NSSet *)keysOfEntriesPassingTest:(BOOL (^)(id key, id obj, BOOL *stop))predicate
	 * @Description : Returns the set of keys whose corresponding value satisfies a constraint described by a block object.
	 * @param predicate A block object that specifies constraints for values in the dictionary.
	 * @return Return Value The set of keys whose corresponding value satisfies predicate.
	 **/
	
	public NSSet<K> keysOfEntriesPassingTest(PerformBlock.BOOLBlockIdIdBOOL predicateBlock) {
		boolean[] stop = new boolean[1];
		NSSet<K> filteredList = new NSSet<K>();
		Set<Entry<K, V>> entries = wrappedDictionary.entrySet();
		while (entries.iterator().hasNext()) {
			if (!stop[0])
				break;
			Entry<K, V> elmnt = entries.iterator().next();
			if (predicateBlock.perform(elmnt.getKey(), elmnt.getValue(), stop)) {
				filteredList.getWrappedSet().add(elmnt.getKey());
			}
		}
		return filteredList;
	}

	/**
	 * @Signature: keysOfEntriesWithOptions:passingTest:
	 * @Declaration : - (NSSet *)keysOfEntriesWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(id key, id obj, BOOL
	 *              *stop))predicate
	 * @Description : Returns the set of keys whose corresponding value satisfies a constraint described by a block object.
	 * @param opts A bit mask of enumeration options.
	 * @param predicate A block object that specifies constraints for values in the dictionary.
	 * @return Return Value The set of keys whose corresponding value satisfies predicate.
	 **/
	
	public NSSet<K> keysOfEntriesWithOptionsPassingTest(int opts,
			PerformBlock.BOOLBlockIdIdBOOL predicateBlock) {
		boolean[] stop = new boolean[1];
		NSSet<K> filteredList = new NSSet<K>();
		Set<Entry<K, V>> entries = wrappedDictionary.entrySet();

		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			while (entries.iterator().hasNext()) {
				if (!stop[0])
					break;
				Entry<K, V> elmnt = entries.iterator().next();
				if (predicateBlock.perform(elmnt.getKey(), elmnt.getValue(), stop)) {
					filteredList.getWrappedSet().add(elmnt.getKey());
				}
			}
			return filteredList;
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			for (int i = entries.size(); i > 0; i--) {
				if (!stop[0])
					break;
				Entry<K, V> elmnt = entries.iterator().next();
				if (predicateBlock.perform(elmnt.getKey(), elmnt.getValue(), stop)) {
					filteredList.getWrappedSet().add(elmnt.getKey());
				}
			}
			return filteredList;
		}
		return filteredList;

	}

	/**
	 * @Signature: sharedKeySetForKeys:
	 * @Declaration : + (id)sharedKeySetForKeys:(NSArray *)keys
	 * @Description : Creates a shared key set object for the specified keys.
	 * @param keys The array of keys. If the parameter is nil, an exception is thrown. If the array of keys is empty, an empty key set is
	 *            returned.
	 * @return Return Value A shared key set object.
	 * @Discussion The array of keys may contain duplicates which are quietly ignored. Duplicate hash values of the keys are quietly
	 *             allowed, but may cause lower performance and increase memory usage. Typically you would create a shared key set for a
	 *             given set of keys once, before creating shared key dictionaries, and retain and save the result of this method for use
	 *             with the NSMutableDictionary class method dictionaryWithSharedKeySet:.
	 **/
	
	public static NSSharedKeySet sharedKeySetForKeys(NSArray<Object> keys) {
		NSSharedKeySet sharedKeySet = new NSSharedKeySet();
		List sharedSet = new ArrayList();
		for (int i = 0; i < keys.count(); i++) {
			sharedSet.add(keys.objectAtIndex(i));

		}
		sharedKeySet.setSharedKeySet(sharedSet);
		return sharedKeySet;
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of entries in the dictionary.
	 * @return Return Value The number of entries in the dictionary.
	 **/
	
	public int count() {
		return wrappedDictionary.size();
	}

	public int getCount() {
		return count();
	}

	/**
	 * @Signature: keyEnumerator
	 * @Declaration : - (NSEnumerator *)keyEnumerator
	 * @Description : Returns an enumerator object that lets you access each key in the dictionary.
	 * @return Return Value An enumerator object that lets you access each key in the dictionary.
	 * @Discussion The following code fragment illustrates how you might use this method. NSEnumerator *enumerator = [myDictionary
	 *             keyEnumerator]; id key; while ((key = [enumerator nextObject])) {
	 **/
	
	public NSEnumerator<K> keyEnumerator() {
		NSEnumerator<K> nsEnumerator = new NSEnumerator<K>(wrappedDictionary.keySet().iterator());
		return nsEnumerator;
	}

	/**
	 * @Declaration : - (NSEnumerator *)objectEnumerator
	 * @Description : Returns an enumerator object that lets you access each value in the dictionary.
	 * @return Return Value An enumerator object that lets you access each value in the dictionary.
	 **/
	
	public NSEnumerator<V> objectEnumerator() {
		NSEnumerator<V> nsEnumerator = new NSEnumerator<V>(wrappedDictionary.values().iterator());
		return nsEnumerator;
	}

	/**
	 * @Signature: enumerateKeysAndObjectsUsingBlock:
	 * @Declaration : - (void)enumerateKeysAndObjectsUsingBlock:(void (^)(id key, id obj, BOOL *stop))block
	 * @Description : Applies a given block object to the entries of the dictionary.
	 * @param block A block object to operate on entries in the dictionary.
	 * @Discussion If the block sets *stop to YES, the enumeration stops.
	 **/
	
	public void enumerateKeysAndObjectsUsingBlock(PerformBlock.VoidBlockIdIdBOOL block) {
		boolean[] stop = new boolean[1];
		Set<Entry<K, V>> entries = wrappedDictionary.entrySet();
		while (entries.iterator().hasNext()) {
			Entry<K, V> elmnt = entries.iterator().next();
			block.perform(elmnt.getKey(), elmnt.getValue(), stop);
			if (!stop[0])
				break;
		}
	}

	/**
	 * @Signature: enumerateKeysAndObjectsWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateKeysAndObjectsWithOptions:(NSEnumerationOptions )opts usingBlock:(void (^)(id key, id obj, BOOL
	 *              *stop))block
	 * @Description : Applies a given block object to the entries of the dictionary.
	 * @param opts Enumeration options.
	 * @param block A block object to operate on entries in the dictionary.
	 * @Discussion If the block sets *stop to YES, the enumeration stops.
	 **/
	
	public void enumerateKeysAndObjectsWithOptionsUsingBlock(int opts, PerformBlock.VoidBlockIdIdBOOL block) {
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			
			Iterator it = wrappedDictionary.entrySet().iterator();

			while (it.hasNext()) {
				block.perform(it.next(), "");
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			List<Integer> myTempList = new ArrayList(wrappedDictionary.keySet());
			Collections.reverse(myTempList);

			for (Object anObject : myTempList) {
				block.perform(anObject, "");
			}

		}

	}

	/**
	 * @Signature: keysSortedByValueUsingSelector:
	 * @Declaration : - (NSArray *)keysSortedByValueUsingSelector:(SEL)comparator
	 * @Description : Returns an array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values.
	 * @param comparator A selector that specifies the method to use to compare the values in the dictionary. The comparator method should
	 *            return NSOrderedAscending if the dictionary value is smaller than the argument, NSOrderedDescending if the dictionary
	 *            value is larger than the argument, and NSOrderedSame if they are equal.
	 * @return Return Value An array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values.
	 * @Discussion Pairs of dictionary values are compared using the comparison method specified by comparator; the comparator message is
	 *             sent to one of the values and has as its single argument the other value from the dictionary.
	 **/
	
	public NSArray<Object> keysSortedByValueUsingSelector(final SEL comparator) {
		NSArray<Object> nsArray = new NSArray<Object>();
		List<String> sortedValues = new ArrayList(wrappedDictionary.values());
		Comparator<Object> cmptr = new Comparator<Object>() {
			@Override
			public int compare(Object lhs, Object rhs) {
				Object result = null;
				try {
					result = comparator.invoke(this, lhs, rhs);
					if (result == NSComparisonResult.NSOrderedAscending) {
						return -1;
					} else if (result == NSComparisonResult.NSOrderedDescending) {
						return 1;
					} else if (result == NSComparisonResult.NSOrderedSame) {
						return 0;
					}
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (NoSuchMethodException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}

				return (Integer) result;
			}
		};
		// We sort the value using the selector (Comparator)
		Collections.sort(sortedValues, cmptr);
		// Now that the value are sorted, we have to create a list of keys
		// matching that order.
		Iterator<Entry<K, V>> it = wrappedDictionary.entrySet().iterator();
		List<Object> sortedKeys = new ArrayList<Object>();
		while (it.hasNext()) {
			Entry<K, V> pairs = it.next();
			for (int i = 0; i < sortedValues.size(); i++) {
				if (pairs.getValue() == sortedValues.get(i))
					sortedKeys.add(i, pairs.getKey());
			}
		}
		nsArray.setWrappedList(sortedKeys);
		return nsArray;
	}

	/**
	 * @Signature: keysSortedByValueUsingComparator:
	 * @Declaration : - (NSArray *)keysSortedByValueUsingComparator:(NSComparator)cmptr
	 * @Description : Returns an array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values
	 *              using a given comparator block.
	 * @param cmptr A comparator block.
	 * @return Return Value An array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values
	 *         using cmptr.
	 **/
	
	public NSArray<Object> keysSortedByValueUsingComparator(NSComparator cmptr) {
		NSArray<Object> nsArray = new NSArray<Object>();
		List<Object> sortedValues = new ArrayList(wrappedDictionary.keySet());
		Collections.sort(sortedValues, cmptr);
		// Now that the values are sorted, we have to create a sort the keys to
		// match their values.
		Iterator<Entry<K, V>> it = wrappedDictionary.entrySet().iterator();
		List<Object> sortedKeys = new ArrayList<Object>();
		while (it.hasNext()) {
			Entry<K, V> pairs = it.next();
			for (int i = 0; i < sortedValues.size(); i++) {
				if (pairs.getValue() == sortedValues.get(i))
					sortedKeys.add(i, pairs.getKey());
			}
		}
		nsArray.setWrappedList(sortedKeys);
		return nsArray;

	}

	/**
	 * @Signature: keysSortedByValueWithOptions:usingComparator:
	 * @Declaration : - (NSArray *)keysSortedByValueWithOptions:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
	 * @Description : Returns an array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values
	 *              using a given comparator block and a specified set of options.
	 * @param opts A bitmask of sort options.
	 * @param cmptr A comparator block.
	 * @return Return Value An array of the dictionary’s keys, in the order they would be in if the dictionary were sorted by its values
	 *         using cmptr with the options given in opts.
	 **/
	
	public NSArray<Object> keysSortedByValueWithOptionsUsingComparator(int opts,
			NSComparator cmptr) {
		NSArray<Object> nsArray = new NSArray<Object>();
		List<Object> sortedValues = new ArrayList(wrappedDictionary.keySet());

		Collections.sort(sortedValues, cmptr);

		// Now that the values are sorted, we have to create a sort the keys to
		// match their values.
		Iterator<Entry<K, V>> it = wrappedDictionary.entrySet().iterator();

		List<Object> sortedKeys = new ArrayList<Object>();
		while (it.hasNext()) {
			Entry<K, V> pairs = it.next();
			for (int i = 0; i < sortedValues.size(); i++) {
				if (pairs.getValue() == sortedValues.get(i))
					sortedKeys.add(i, pairs.getKey());
			}
		}
		nsArray.setWrappedList(sortedKeys);
		return nsArray;
	}

	/**
	 * @Signature: fileCreationDate
	 * @Declaration : - (NSDate *)fileCreationDate
	 * @Description : Returns the value for the NSFileCreationDate key.
	 * @return Return Value The value for the NSFileCreationDate key, or nil if the dictionary doesn’t have an entry for the key.
	 **/
	
	public NSDate fileCreationDate() {
		return null;

	}

	/**
	 * @Signature: fileExtensionHidden
	 * @Declaration : - (BOOL)fileExtensionHidden
	 * @Description : Returns the value for the NSFileExtensionHidden key.
	 * @return Return Value The value for the NSFileExtensionHidden key, or NO if the dictionary doesn’t have an entry for the key.
	 **/
	
	public boolean fileExtensionHidden() {
		return plistFile.isHidden();
	}

	/**
	 * @Signature: fileGroupOwnerAccountID
	 * @Declaration : - (NSNumber *)fileGroupOwnerAccountID
	 * @Description : Returns the value for the NSFileGroupOwnerAccountID key.
	 * @return Return Value The value for the NSFileGroupOwnerAccountID key, or nil if the dictionary doesn’t have an entry for the key.
	 **/
	
	public NSNumber fileGroupOwnerAccountID() {
		return null;
	}

	/**
	 * @Signature: fileGroupOwnerAccountName
	 * @Declaration : - (NSString *)fileGroupOwnerAccountName
	 * @Description : Returns the value for the NSFileGroupOwnerAccountName key.
	 * @return Return Value The value for the key NSFileGroupOwnerAccountName, or nil if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the name of the
	 *             corresponding file’s group.
	 **/
	
	public NSString fileGroupOwnerAccountName() {
		return null;
	}

	/**
	 * @Signature: fileHFSCreatorCode
	 * @Declaration : - (OSType)fileHFSCreatorCode
	 * @Description : Returns the value for the NSFileHFSCreatorCode key.
	 * @return Return Value The value for the NSFileHFSCreatorCode key, or 0 if the dictionary doesn’t have an entry for the key.
	 * @Discussion See “HFS File Types�? for details on the OSType data type.
	 **/
	
	public void fileHFSCreatorCode() {
	}

	/**
	 * @Signature: fileHFSTypeCode
	 * @Declaration : - (OSType)fileHFSTypeCode
	 * @Description : Returns the value for the NSFileHFSTypeCode key.
	 * @return Return Value The value for the NSFileHFSTypeCode key, or 0 if the dictionary doesn’t have an entry for the key.
	 * @Discussion See “HFS File Types�? for details on the OSType data type.
	 **/
	
	public void fileHFSTypeCode() {
	}

	/**
	 * @Signature: fileIsAppendOnly
	 * @Declaration : - (BOOL)fileIsAppendOnly
	 * @Description : Returns the value for the NSFileAppendOnly key.
	 * @return Return Value The value for the NSFileAppendOnly key, or NO if the dictionary doesn’t have an entry for the key.
	 **/
	
	public boolean fileIsAppendOnly() {
		return plistFile.canWrite();
	}

	/**
	 * @Signature: fileIsImmutable
	 * @Declaration : - (BOOL)fileIsImmutable
	 * @Description : Returns the value for the NSFileImmutable key.
	 * @return Return Value The value for the NSFileImmutable key, or NO if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory.
	 **/
	
	public boolean fileIsImmutable() {
		return !plistFile.canWrite();
	}

	/**
	 * @Signature: fileModificationDate
	 * @Declaration : - (NSDate *)fileModificationDate
	 * @Description : Returns the value for the key NSFileModificationDate.
	 * @return Return Value The value for the key NSFileModificationDate, or nil if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the date that
	 *             the file’s data was last modified.
	 **/
	
	public NSDate fileModificationDate() {
		NSDate nsDate = new NSDate();
		nsDate.setWrappedDate(new Date(plistFile.lastModified()));
		return nsDate;
	}

	/**
	 * @Signature: fileOwnerAccountID
	 * @Declaration : - (NSNumber *)fileOwnerAccountID
	 * @Description : Returns the value for the NSFileOwnerAccountID key.
	 * @return Return Value The value for the NSFileOwnerAccountID key, or nil if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the account
	 *             name of the file’s owner.
	 **/
	
	public NSNumber fileOwnerAccountID() {
		return null;
	}

	/**
	 * @Signature: fileOwnerAccountName
	 * @Declaration : - (NSString *)fileOwnerAccountName
	 * @Description : Returns the value for the key NSFileOwnerAccountName.
	 * @return Return Value The value for the key NSFileOwnerAccountName, or nil if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the account
	 *             name of the file’s owner.
	 **/
	
	public NSString fileOwnerAccountName() {
		return null;
	}

	/**
	 * @Signature: filePosixPermissions
	 * @Declaration : - (NSUInteger)filePosixPermissions
	 * @Description : Returns the value for the key NSFilePosixPermissions.
	 * @return Return Value The value, as an unsigned long, for the key NSFilePosixPermissions, or 0 if the dictionary doesn’t have an entry
	 *         for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the file’s
	 *             permissions.
	 **/
	
	public int filePosixPermissions() {
		return 0;
	}

	/**
	 * @Signature: fileSize
	 * @Declaration : - (unsigned long long)fileSize
	 * @Description : Returns the value for the key NSFileSize.
	 * @return Return Value The value, as an unsigned long long, for the key NSFileSize, or 0 if the dictionary doesn’t have an entry for
	 *         the key.
	 * @Discussion This and the other file... methods are for use with a dictionary such, as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the file’s
	 *             size.
	 **/
	
	public long fileSize() {
		return plistFile.length();
	}

	/**
	 * @Signature: fileSystemFileNumber
	 * @Declaration : - (NSUInteger)fileSystemFileNumber
	 * @Description : Returns the value for the key NSFileSystemFileNumber.
	 * @return Return Value The value, as an unsigned long, for the key NSFileSystemFileNumber, or 0 if the dictionary doesn’t have an entry
	 *         for the key
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the file’s
	 *             inode.
	 **/
	
	public int fileSystemFileNumber() {
		return 0;
	}

	/**
	 * @Signature: fileSystemNumber
	 * @Declaration : - (NSInteger)fileSystemNumber
	 * @Description : Returns the value for the key NSFileSystemNumber.
	 * @return Return Value The value, as an unsigned long, for the key NSFileSystemNumber, or 0 if the dictionary doesn’t have an entry for
	 *         the key
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the ID of the
	 *             device containing the file.
	 **/
	
	public int fileSystemNumber() {
		return 0;
	}

	/**
	 * @Signature: fileType
	 * @Declaration : - (NSString *)fileType
	 * @Description : Returns the value for the key NSFileType.
	 * @return Return Value The value for the key NSFileType, or nil if the dictionary doesn’t have an entry for the key.
	 * @Discussion This and the other file... methods are for use with a dictionary, such as those returned from the methods
	 *             fileAttributesAtPath:traverseLink: (NSFileManager), directoryAttributes (NSDirectoryEnumerator), and fileAttributes
	 *             (NSDirectoryEnumerator), that represents the POSIX attributes of a file or directory. This method returns the file’s
	 *             type. Possible return values are described in the “Constants�? section of NSFileManager.
	 **/
	
	public NSString fileType() {
		return new NSString("");
	}

	// Creating a Description

	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns a string that represents the contents of the dictionary, formatted as a property list.
	 * @return Return Value A string that represents the contents of the dictionary, formatted as a property list.
	 * @Discussion If each key in the dictionary is an NSString object, the entries are listed in ascending order by key, otherwise the
	 *             order in which the entries are listed is undefined. This method is intended to produce readable output for debugging
	 *             purposes, not for serializing data. If you want to store dictionary data for later retrieval, see Property List
	 *             Programming Guide and Archives and Serializations Programming Guide.
	 **/
	@Override
	
	public NSString description() {
		return null;
	}

	public NSString getDescription() {
		return description();
	}

	/**
	 * @Signature: descriptionInStringsFileFormat
	 * @Declaration : - (NSString *)descriptionInStringsFileFormat
	 * @Description : Returns a string that represents the contents of the dictionary, formatted in .strings file format.
	 * @return Return Value A string that represents the contents of the dictionary, formatted in .strings file format.
	 * @Discussion The order in which the entries are listed is undefined.
	 **/
	
	public NSString descriptionInStringsFileFormat() {
		return description();
	}

	public NSString getDescriptionInStringsFileFormat() {
		return descriptionInStringsFileFormat();
	}

	/**
	 * @Signature: descriptionWithLocale:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale
	 * @Description : Returns a string object that represents the contents of the dictionary, formatted as a property list.
	 * @param locale An object that specifies options used for formatting each of the dictionary’s keys and values; pass nil if you don’t
	 *            want them formatted. On iOS and OS X v10.5 and later, either an instance of NSDictionary or an NSLocale object may be used
	 *            for locale. On OS X v10.4 and earlier it must be an instance of NSDictionary.
	 * @Discussion For a description of how locale is applied to each element in the dictionary, see descriptionWithLocale:indent:. If each
	 *             key in the dictionary responds to compare:, the entries are listed in ascending order by key, otherwise the order in
	 *             which the entries are listed is undefined.
	 **/
	
	public NSString descriptionWithLocale(Object locale) {
		return description();
	}

	/**
	 * @Signature: descriptionWithLocale:indent:
	 * @Declaration : - (NSString *)descriptionWithLocale:(id)locale indent:(NSUInteger)level
	 * @Description : Returns a string object that represents the contents of the dictionary, formatted as a property list.
	 * @param locale An object that specifies options used for formatting each of the dictionary’s keys and values; pass nil if you don’t
	 *            want them formatted. On iOS and OS X v10.5 and later, either an instance of NSDictionary or an NSLocale object may be used
	 *            for locale. On OS X v10.4 and earlier it must be an instance of NSDictionary.
	 * @param level Specifies a level of indentation, to make the output more readable: the indentation is (4 spaces) * level.
	 * @return Return Value A string object that represents the contents of the dictionary, formatted as a property list.
	 * @Discussion The returned NSString object contains the string representations of each of the dictionary’s entries.
	 *             descriptionWithLocale:indent: obtains the string representation of a given key or value as follows: If the object is an
	 *             NSString object, it is used as is. If the object responds to descriptionWithLocale:indent:, that method is invoked to
	 *             obtain the object’s string representation. If the object responds to descriptionWithLocale:, that method is invoked to
	 *             obtain the object’s string representation. If none of the above conditions is met, the object’s string representation is
	 *             obtained by invoking its description method. If each key in the dictionary responds to compare:, the entries are listed
	 *             in ascending order, by key. Otherwise, the order in which the entries are listed is undefined.
	 **/
	
	public NSString descriptionWithLocaleIndent(Object locale, int level) {
		return description();
	}

	@Override
	public String toString() {
		return super.toString();
		//return getWrappedDictionary().toString();
		// StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		// xml.append(NSObject."\n");
		// xml.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		// xml.append(NSObject."\n");
		// xml.append("<plist version=\"1.0\">");
		// xml.append(NSObject."\n");
		// toXML(xml, 0);
		// xml.append(NSObject."\n");
		// xml.append("</plist>");
		// return xml.toString();
	}

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

	public NSDictionary() {
		_initializeWithCapacity(0);
	}

	protected NSDictionary(int capacity) {
		_initializeWithCapacity(capacity);
	}

	public NSDictionary(Map<K, V> map) {
		_initializeWithMap(map, NullHandling.CheckAndFail);
	}

	public NSDictionary(Map<K, V> map, boolean ignoreNull) {
		_initializeWithMap(map, ignoreNull ? NullHandling.NoCheck : NullHandling.CheckAndFail);
	}

	
	public NSDictionary(NSArray<V> objects, NSArray<K> keys) {
		this((V[]) (objects != null ? objects.getWrappedList().toArray() : null),
				(K[]) (keys != null ? keys.getWrappedList().toArray() : null));
	}

	public NSDictionary(NSDictionary<K, V> otherDictionary) {
		this(otherDictionary.getWrappedDictionary());
	}

	public NSDictionary(V[] objects, K[] keys) {
		_initFromKeyValues(objects, keys, NullHandling.CheckAndFail);
	}

	public NSDictionary(V object, K key) {
		if (key == null || object == null)
			throw new IllegalArgumentException("Object or key may not be null");

		_initializeWithCapacity(1).put(key, object);
	}

	protected Map<K, V> _initializeWithCapacity(int capacity) {
		Map<K, V> map = new HashMap();
		_setMap(map);
		return map;
	}

	protected void _initializeWithMap(Map<K, V> map, NullHandling nullHandling) {
		Map<K, V> store = _initializeWithCapacity(map.size());
		if (map instanceof NSDictionary<?, ?> || nullHandling == NullHandling.NoCheck)
			store.putAll(map);
		else {
			for (Entry<K, V> entry : map.entrySet()) {
				if (entry.getKey() == null || entry.getValue() == null) {
					if (nullHandling == NullHandling.CheckAndFail)
						throw new IllegalArgumentException("Key or value may not be null");
					continue;
				}
				store.put(entry.getKey(), entry.getValue());
			}
		}
	}

	protected void _initFromKeyValues(V[] objects, K[] keys, NullHandling nullHandling) {
		if (objects == null && keys == null) {
			_initializeWithCapacity(0);
			return;
		}

		if (keys == null || objects == null)
			throw new IllegalArgumentException("Both objects and keys cannot be null");

		if (objects.length != keys.length) {
			throw new IllegalArgumentException("Attempt to create an " + getClass().getName()
					+ " with a different number of objects and keys.");
		}
		Map<K, V> store = _initializeWithCapacity(objects.length);
		if (nullHandling != NullHandling.NoCheck) {
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] == null || keys[i] == null) {
					if (nullHandling == NullHandling.CheckAndFail)
						throw new IllegalArgumentException(
								"Attempt to insert a null into an  " + getClass().getName() + ".");
					continue;
				}
				store.put(keys[i], objects[i]);
			}
		} else {
			for (int i = 0; i < objects.length; i++) {
				store.put(keys[i], objects[i]);
			}
		}
	}

	protected static <K, V, T extends NSDictionary<K, V>> T _initializeDictionaryWithMap(T dict,
			Map<K, V> map, NullHandling nullHandling) {
		if (map == null)
			throw new IllegalArgumentException("map may not be null");

		if (!(map instanceof NSDictionary<?, ?>) && nullHandling != NullHandling.NoCheck
				&& map.size() > 0) {
			try {
				if (map.containsValue(null) || map.containsKey(null)) {
					if (nullHandling == NullHandling.CheckAndFail)
						throw new IllegalArgumentException(NULL_NOT_ALLOWED);
					dict._initializeWithMap(map, nullHandling);
					return dict;
				}
			} catch (NullPointerException e) {
				// Must not support nulls either
                Logger.getLogger("context",String.valueOf(e));
			}
		}

		dict._setMap(map);
		return dict;
	}

	protected Map<K, V> mapNoCopy() {
		return wrappedDictionary;
	}

	protected Object[] keysNoCopy() {
		return mapNoCopy().keySet().toArray();
	}

	protected Object[] objectsNoCopy() {
		return mapNoCopy().values().toArray();
	}

	protected Map<K, V> _setMap(Map<K, V> map) {
        wrappedDictionary = map;
		return wrappedDictionary;
	}

	public static <K, V> NSDictionary<K, V> asDictionary(NSDictionary<K, V> map) {
		return asDictionary(map, NullHandling.CheckAndFail);
	}

	public static <K, V> NSDictionary<K, V> asDictionary(NSDictionary<K, V> map,
			NullHandling nullHandling) {
		if (map == null || map.size() == 0)
			return emptyDictionary();
		if (map.getClass() == NSDictionary.class)
			return map;
		return _initializeDictionaryWithMap(new NSDictionary<K, V>(),
				Collections.unmodifiableMap(map.getWrappedDictionary()), nullHandling);
	}

	public static <K, V> NSMutableDictionary<K, V> asMutableDictionary(Map<K, V> map) {
		return asMutableDictionary(map, NullHandling.CheckAndFail);
	}

	public static <K, V> NSMutableDictionary<K, V> asMutableDictionary(Map<K, V> map,
			NullHandling nullHandling) {
		if (map == null || map.size() == 0)
			return new NSMutableDictionary<K, V>();
		if (map instanceof NSMutableDictionary<?, ?>)
			return (NSMutableDictionary<K, V>) map;
		return _initializeDictionaryWithMap(new NSMutableDictionary<K, V>(), map, nullHandling);
	}

	
	public static <K, V> NSDictionary<K, V> emptyDictionary() {
		return EmptyDictionary;
	}

	public Map<K, V> hashMap() {
		return new HashMap<K, V>(mapNoCopy());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this || obj == mapNoCopy())
			return true;
		if (obj instanceof NSDictionary<?, ?>
				&& mapNoCopy() == ((NSDictionary<?, ?>) obj).mapNoCopy())
			return true;
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return _shallowHashCode() ^ count();
	}

	public int _shallowHashCode() {
		return NSDictionary.class.hashCode();
	}

	public NSDictionary<K, V> immutableClone() {
		return this;
	}

	public NSMutableDictionary<K, V> mutableClone() {
		return new NSMutableDictionary<K, V>(mapNoCopy());
	}

	public void takeValueForKey(Object value, String key) {
		throw new IllegalStateException(super.getClass().getName() + " is immutable.");
	}

	/**
	 * Gets the hashmap which stores the keys and values of this dictionary. Changes to the hashmap's contents are directly reflected in
	 * this dictionary.
	 * 
	 * @return The hashmap which is used by this dictionary to store its contents.
	 */
	public Map<String, NSObject> getHashMap() {
		return (HashMap<String, NSObject>) wrappedDictionary;
	}

	/**
	 * Gets the NSObject stored for the given key.
	 * 
	 * @param key The key.
	 * @return The object.
	 */
	public NSObject objectForKey(String key) {
		return (NSObject) wrappedDictionary.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	public int size() {
		return wrappedDictionary.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return wrappedDictionary.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return wrappedDictionary.containsKey(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
		NSObject wrap = NSObject.wrap(value);
		return wrappedDictionary.containsValue(wrap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public NSObject get(Object key) {
		return (NSObject) wrappedDictionary.get(key);
	}

	/**
	 * Removes a key-value pair from this dictionary.
	 * 
	 * @param key The key
	 * @return the value previously associated to the given key.
	 */
	public NSObject remove(String key) {
		return (NSObject) wrappedDictionary.remove(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public NSObject remove(Object key) {
		return (NSObject) wrappedDictionary.remove(key);
	}

	/**
	 * Removes all key-value pairs from this dictionary.
	 * 
	 * @see Map#clear()
	 */
	public void clear() {
		wrappedDictionary.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	public Set<String> keySet() {
		return (Set<String>) wrappedDictionary.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	public Collection<NSObject> values() {
		return (Collection<NSObject>) wrappedDictionary.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	public Set<Entry<K, V>> entrySet() {
		return wrappedDictionary.entrySet();
	}

	/**
	 * Checks whether a given key is contained in this dictionary.
	 * 
	 * @param key The key that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsKey(String key) {
		return wrappedDictionary.containsKey(key);
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(NSObject val) {
		return wrappedDictionary.containsValue(val);
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(String val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSString.class)) {
				NSString str = (NSString) o;
				if (str.getContent().equals(val))
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(long val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSNumber.class)) {
				NSNumber num = (NSNumber) o;
				if (num.isInteger() && num.intValue() == val)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(double val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSNumber.class)) {
				NSNumber num = (NSNumber) o;
                if (num.isReal() && Double.doubleToRawLongBits(num.doubleValue)==val)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(boolean val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSNumber.class)) {
				NSNumber num = (NSNumber) o;
				if (num.isBoolean() && num.boolValue() == val)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(Date val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSDate.class)) {
				NSDate dat = (NSDate) o;
				if (dat.getDate().equals(val))
					return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether a given value is contained in this dictionary.
	 * 
	 * @param val The value that will be searched for.
	 * @return Whether the key is contained in this dictionary.
	 */
	public boolean containsValue(byte[] val) {
		for (V o : wrappedDictionary.values()) {
			if (o.getClass().equals(NSData.class)) {
				NSData dat = (NSData) o;
				if (Arrays.equals(dat.bytes(), val))
					return true;
			}
		}
		return false;
	}

	@Override
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<dict>");
		xml.append("\n");
		for (K key : wrappedDictionary.keySet()) {
			NSObject val = (NSObject) objectForKey(key);
			indent(xml, level + 1);
			xml.append("<key>");
			// According to http://www.w3.org/TR/REC-xml/#syntax node values
			// must not
			// contain the characters < or &. Also the > character should be
			// escaped.
			if (((String) key).contains("&") || ((String) key).contains("<")
					|| ((String) key).contains(">")) {
				xml.append("<![CDATA[");
				xml.append(((String) key).replaceAll("]]>", "]]]]><![CDATA[>"));
				xml.append("]]>");
			} else {
				xml.append(key);
			}
			xml.append("</key>");
			xml.append("\n");
			val.toXML(xml, level + 1);
			xml.append("\n");
		}
		indent(xml, level);
		xml.append("</dict>");
	}

	@Override
	public void assignIDs(BinaryPropertyListWriter out) {
		super.assignIDs(out);
		for (Entry<K, V> entry : wrappedDictionary.entrySet()) {
			new NSString((String) entry.getKey()).assignIDs(out);
			((NSObject) entry.getValue()).assignIDs(out);
		}
	}

	@Override
	public void toBinary(BinaryPropertyListWriter out) throws IOException {
		out.writeIntHeader(0xD, wrappedDictionary.size());
		Set<Entry<K, V>> entries = wrappedDictionary.entrySet();
		for (Entry<K, V> entry : entries) {
			out.writeID(out.getID(new NSString((String) entry.getKey())));
		}
		for (Entry<K, V> entry : entries) {
			out.writeID(out.getID((NSObject) entry.getValue()));
		}
	}

	/**
	 * Generates a valid ASCII property list which has this NSDictionary as its root object. The generated property list complies with the
	 * format as described in
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
	 * Generates a valid ASCII property list in GnuStep format which has this NSDictionary as its root object. The generated property list
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
		ascii.append(ASCIIPropertyListParser.DICTIONARY_BEGIN_TOKEN);
		ascii.append("\n");
		String[] keys = wrappedDictionary.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			NSObject val = objectForKey(key);
			indent(ascii, level + 1);
			ascii.append("\"");
			ascii.append(NSString.escapeStringForASCII(key));
			ascii.append("\" =");
			Class<?> objClass = val.getClass();
			if (objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) {
				ascii.append("\n");
				val.toASCII(ascii, level + 2);
			} else {
				ascii.append(" ");
				val.toASCII(ascii, 0);
			}
			ascii.append(ASCIIPropertyListParser.DICTIONARY_ITEM_DELIMITER_TOKEN);
			ascii.append("\n");
		}
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.DICTIONARY_END_TOKEN);
	}

	@Override
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.DICTIONARY_BEGIN_TOKEN);
		ascii.append("\n");
		String[] keys = wrappedDictionary.keySet().toArray(new String[0]);
		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];
			NSObject val = objectForKey(key);
			indent(ascii, level + 1);
			ascii.append("\"");
			ascii.append(NSString.escapeStringForASCII(key));
			ascii.append("\" =");
			Class<?> objClass = val.getClass();
			if (objClass.equals(NSDictionary.class) || objClass.equals(NSArray.class)
					|| objClass.equals(NSData.class)) {
				ascii.append("\n");
				val.toASCIIGnuStep(ascii, level + 2);
			} else {
				ascii.append(" ");
				val.toASCIIGnuStep(ascii, 0);
			}
			ascii.append(ASCIIPropertyListParser.DICTIONARY_ITEM_DELIMITER_TOKEN);
			ascii.append("\n");
		}
		indent(ascii, level);
		ascii.append(ASCIIPropertyListParser.DICTIONARY_END_TOKEN);
	}

	public V put(K keyString, V wrap) {
		return wrappedDictionary.put(keyString, wrap);

	}

	@Override
	public boolean hasNext() {
		return getWrappedDictionary().keySet().iterator().hasNext();
	}

	@Override
	public K next() {
		return getWrappedDictionary().keySet().iterator().next();
	}

	@Override
	public void remove() {
		getWrappedDictionary().keySet().iterator().remove();
	}

	@Override
	public Iterator<K> iterator() {
		return getWrappedDictionary().keySet().iterator();
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public boolean isKindOfClass(Class<?> aClass) {
		if (aClass.equals(this.getClass()) || aClass.isAssignableFrom(this.getClass()))
			return true;
		return false;
	}
}