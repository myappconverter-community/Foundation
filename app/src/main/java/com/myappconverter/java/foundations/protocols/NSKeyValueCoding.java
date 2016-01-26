package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSMutableArray;
import com.myappconverter.java.foundations.NSMutableOrderedSet;
import com.myappconverter.java.foundations.NSMutableSet;
import com.myappconverter.java.foundations.NSString;


public interface NSKeyValueCoding {

	// // 1
	/**
	 * @Signature: accessInstanceVariablesDirectly
	 * @Declaration : + (BOOL)accessInstanceVariablesDirectly
	 * @Description : Returns a Boolean value that indicates whether the key-value coding methods should access the corresponding instance
	 *              variable directly on finding no accessor method for a property.
	 * @return Return Value YES if the key-value coding methods should access the corresponding instance variable directly on finding no
	 *         accessor method for a property, otherwise NO.
	 * @Discussion The default returns YES. Subclasses can override it to return NO, in which case the key-value coding methods won’t access
	 *             instance variables.
	 **/

	public boolean accessInstanceVariablesDirectly();

	// 2
	/**
	 * @Signature: dictionaryWithValuesForKeys:
	 * @Declaration : - (NSDictionary *)dictionaryWithValuesForKeys:(NSArray *)keys
	 * @Description : Returns a dictionary containing the property values identified by each of the keys in a given array.
	 * @return Return Value A dictionary containing as keys the property names in keys, with corresponding values being the corresponding
	 *         property values.
	 * @Discussion The default implementation invokes valueForKey: for each key in keys and substitutes NSNull values in the dictionary for
	 *             returned nil values.
	 **/

	public NSDictionary dictionaryWithValuesForKeys(NSArray keys);

	// 3
	/**
	 * @Signature: mutableArrayValueForKey:
	 * @Declaration : - (NSMutableArray *)mutableArrayValueForKey:(NSString *)key
	 * @Description : Returns a mutable array proxy that provides read-write access to an ordered to-many relationship specified by a given
	 *              key.
	 * @return Return Value A mutable array proxy that provides read-write access to the ordered to-many relationship specified by key.
	 * @Discussion Objects added to the mutable array become related to the receiver, and objects removed from the mutable array become
	 *             unrelated. The default implementation recognizes the same simple accessor methods and array accessor methods as
	 *             valueForKey:, and follows the same direct instance variable access policies, but always returns a mutable collection
	 *             proxy object instead of the immutable collection that valueForKey: would return. The search pattern that
	 *             mutableArrayValueForKey: uses is described in Accessor Search Implementation Details in Key-Value Coding Programming
	 *             Guide.
	 **/

	public NSMutableArray mutableArrayValueForKey(NSString key);

	// 4
	/**
	 * @Signature: mutableArrayValueForKeyPath:
	 * @Declaration : - (NSMutableArray *)mutableArrayValueForKeyPath:(NSString *)keyPath
	 * @Description : Returns a mutable array that provides read-write access to the ordered to-many relationship specified by a given key
	 *              path.
	 * @return Return Value A mutable array that provides read-write access to the ordered to-many relationship specified by keyPath.
	 * @Discussion See mutableArrayValueForKey: for additional details.
	 **/

	public NSMutableArray mutableArrayValueForKeyPath(NSString keyPath);

	// 5
	/**
	 * @Signature: mutableOrderedSetValueForKey:
	 * @Declaration : - (NSMutableOrderedSet *)mutableOrderedSetValueForKey:(NSString *)key
	 * @Description : Returns a mutable ordered set that provides read-write access to the uniquing ordered to-many relationship specified
	 *              by a given key.
	 * @return Return Value A mutable ordered set that provides read-write access to the uniquing to-many relationship specified by key.
	 * @Discussion Objects added to the mutable set proxy become related to the receiver, and objects removed from the mutable set become
	 *             unrelated. The default implementation recognizes the same simple accessor methods and set accessor methods as
	 *             valueForKey:, and follows the same direct instance variable access policies, but always returns a mutable collection
	 *             proxy object instead of the immutable collection that valueForKey: would return. The search pattern that
	 *             mutableOrderedSetValueForKey: uses is described in Accessor Search Implementation Details in Key-Value Coding Programming
	 *             Guide.
	 **/

	public NSMutableOrderedSet mutableOrderedSetValueForKey(NSString key);

	// 6
	/**
	 * @Signature: mutableOrderedSetValueForKeyPath:
	 * @Declaration : - (NSMutableOrderedSet *)mutableOrderedSetValueForKeyPath:(NSString *)keyPath
	 * @Description : Returns a mutable ordered set that provides read-write access to the uniquing ordered to-many relationship specified
	 *              by a given key path.
	 * @return Return Value A mutable ordered set that provides read-write access to the uniquing to-many relationship specified by keyPath.
	 * @Discussion See mutableOrderedSetValueForKey: for additional details.
	 **/

	public NSMutableOrderedSet mutableOrderedSetValueForKeyPath(NSString keyPath);

	// 7
	/**
	 * @Signature: mutableSetValueForKey:
	 * @Declaration : - (NSMutableSet *)mutableSetValueForKey:(NSString *)key
	 * @Description : Returns a mutable set proxy that provides read-write access to the unordered to-many relationship specified by a given
	 *              key.
	 * @return Return Value A mutable set that provides read-write access to the unordered to-many relationship specified by key.
	 * @Discussion Objects added to the mutable set proxy become related to the receiver, and objects removed from the mutable set become
	 *             unrelated. The default implementation recognizes the same simple accessor methods and set accessor methods as
	 *             valueForKey:, and follows the same direct instance variable access policies, but always returns a mutable collection
	 *             proxy object instead of the immutable collection that valueForKey: would return. The search pattern that
	 *             mutableSetValueForKey: uses is described in Accessor Search Implementation Details in Key-Value Coding Programming Guide.
	 **/

	public NSMutableSet mutableSetValueForKey(NSString key);

	// 8
	/**
	 * @Signature: mutableSetValueForKeyPath:
	 * @Declaration : - (NSMutableSet *)mutableSetValueForKeyPath:(NSString *)keyPath
	 * @Description : Returns a mutable set that provides read-write access to the unordered to-many relationship specified by a given key
	 *              path.
	 * @return Return Value A mutable set that provides read-write access to the unordered to-many relationship specified by keyPath.
	 * @Discussion See mutableSetValueForKey: for additional details.
	 **/

	public NSMutableSet mutableSetValueForKeyPath(NSString keyPath);

	// 9
	/**
	 * @Signature: setNilValueForKey:
	 * @Declaration : - (void)setNilValueForKey:(NSString *)key
	 * @Description : Invoked by setValue:forKey: when it’s given a nil value for a scalar value (such as an int or float).
	 * @Discussion Subclasses can override this method to handle the request in some other way, such as by substituting 0 or a sentinel
	 *             value for nil and invoking setValue:forKey: again or setting the variable directly. The default implementation raises an
	 *             NSInvalidArgumentException.
	 **/

	public void setNilValueForKey(NSString key);

	// 10
	/**
	 * @Signature: setValue:forKey:
	 * @Declaration : - (void)setValue:(id)value forKey:(NSString *)key
	 * @Description : Sets the property of the receiver specified by a given key to a given value.
	 * @Discussion If key identifies a to-one relationship, relate the object specified by value to the receiver, unrelating the previously
	 *             related object if there was one. Given a collection object and a key that identifies a to-many relationship, relate the
	 *             objects contained in the collection to the receiver, unrelating previously related objects if there were any. The search
	 *             pattern that setValue:forKey: uses is described in Accessor Search Implementation Details in Key-Value Coding Programming
	 *             Guide. In a reference-counted environment, if the instance variable is accessed directly, value is retained.
	 **/

	public void setValueForKey(Object value, NSString key);

	// 11
	/**
	 * @Signature: setValue:forKeyPath:
	 * @Declaration : - (void)setValue:(id)value forKeyPath:(NSString *)keyPath
	 * @Description : Sets the value for the property identified by a given key path to a given value.
	 * @Discussion The default implementation of this method gets the destination object for each relationship using valueForKey:, and sends
	 *             the final object a setValue:forKey: message.
	 **/

	public void setValueForKeyPath(Object value, NSString keyPath);

	// 12
	/**
	 * @Signature: setValue:forUndefinedKey:
	 * @Declaration : - (void)setValue:(id)value forUndefinedKey:(NSString *)key
	 * @Description : Invoked by setValue:forKey: when it finds no property for a given key.
	 * @Discussion Subclasses can override this method to handle the request in some other way. The default implementation raises an
	 *             NSUndefinedKeyException.
	 **/

	public void setValueForUndefinedKey(Object value, NSString key);

	// 13
	/**
	 * @Signature: setValuesForKeysWithDictionary:
	 * @Declaration : - (void)setValuesForKeysWithDictionary:(NSDictionary *)keyedValues
	 * @Description : Sets properties of the receiver with values from a given dictionary, using its keys to identify the properties.
	 * @Discussion The default implementation invokes setValue:forKey: for each key-value pair, substituting nil for NSNull values in
	 *             keyedValues.
	 **/

	public void setValuesForKeysWithDictionary(NSDictionary keyedValues);

	// 14
	/**
	 * @Signature: validateValue:forKey:error:
	 * @Declaration : - (BOOL)validateValue:(id *)ioValue forKey:(NSString *)key error:(NSError **)outError
	 * @Description : Returns a Boolean value that indicates whether the value specified by a given pointer is valid for the property
	 *              identified by a given key.
	 * @return Return Value YES if *ioValue is a valid value for the property identified by key, or if the method is able to modify the
	 *         value to *ioValue to make it valid; otherwise NO.
	 * @Discussion The default implementation of this method searches the class of the receiver for a validation method whose name matches
	 *             the pattern validate<Key>:error:. If such a method is found it is invoked and the result is returned. If no such method
	 *             is found, YES is returned. The sender of the message is never given responsibility for releasing ioValue or outError. See
	 *             “Key-Value Validation” for more information.
	 **/

	public boolean validateValueForKeyError(Object ioValue, NSString key, NSError outError);

	// 15
	/**
	 * @Signature: validateValue:forKeyPath:error:
	 * @Declaration : - (BOOL)validateValue:(id *)ioValue forKeyPath:(NSString *)inKeyPath error:(NSError **)outError
	 * @Description : Returns a Boolean value that indicates whether the value specified by a given pointer is valid for a given key path
	 *              relative to the receiver.
	 * @Discussion The default implementation gets the destination object for each relationship using valueForKey: and returns the result of
	 *             a validateValue:forKey:error: message to the final object.
	 **/

	public boolean validateValueForKeyPathError(Object ioValue, NSString inKeyPath, NSError outError);

	// 16
	/**
	 * @Signature: valueForKey:
	 * @Declaration : - (id)valueForKey:(NSString *)key
	 * @Description : Returns the value for the property identified by a given key.
	 * @return Return Value The value for the property identified by key.
	 * @Discussion The search pattern that valueForKey: uses to find the correct value to return is described in Accessor Search
	 *             Implementation Details in Key-Value Coding Programming Guide.
	**/
	public Object valueForKey(NSString key);

	// 17
	/**
	 * @Signature: valueForKeyPath:
	 * @Declaration : - (id)valueForKeyPath:(NSString *)keyPath
	 * @Description : Returns the value for the derived property identified by a given key path.
	 * @return Return Value The value for the derived property identified by keyPath.
	 * @Discussion The default implementation gets the destination object for each relationship using valueForKey: and returns the result of
	 *             a valueForKey: message to the final object.
	 **/

	public Object valueForKeyPath(NSString keyPath);

	// 18
	/**
	 * @Signature: valueForUndefinedKey:
	 * @Declaration : - (id)valueForUndefinedKey:(NSString *)key
	 * @Description : Invoked by valueForKey: when it finds no property corresponding to a given key.
	 * @Discussion Subclasses can override this method to return an alternate value for undefined keys. The default implementation raises an
	 *             NSUndefinedKeyException.
	 **/

	public Object valueForUndefinedKey(NSString key);

	// Constants
	public static final NSString NSUndefinedKeyException = new NSString();

	public static final NSString NSTargetObjectUserInfoKey = new NSString();
	public static final NSString NSUnknownUserInfoKey = new NSString();

	public static final NSString NSAverageKeyValueOperator = new NSString();
	public static final NSString NSCountKeyValueOperator = new NSString();
	public static final NSString NSDistinctUnionOfArraysKeyValueOperator = new NSString();
	public static final NSString NSDistinctUnionOfObjectsKeyValueOperator = new NSString();
	public static final NSString NSDistinctUnionOfSetsKeyValueOperator = new NSString();
	public static final NSString NSMaximumKeyValueOperator = new NSString();
	public static final NSString NSMinimumKeyValueOperator = new NSString();
	public static final NSString NSSumKeyValueOperator = new NSString();
	public static final NSString NSUnionOfArraysKeyValueOperator = new NSString();
	public static final NSString NSUnionOfObjectsKeyValueOperator = new NSString();
	public static final NSString NSUnionOfSetsKeyValueOperator = new NSString();

}