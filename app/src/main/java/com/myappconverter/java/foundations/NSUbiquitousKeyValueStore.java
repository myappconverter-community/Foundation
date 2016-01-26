
package com.myappconverter.java.foundations;



public class NSUbiquitousKeyValueStore extends NSObject {

	public static final String PREFERENCES = "Preferences";
	public static final String HELPER_KEY = "prefs";

	/**
	 * @Signature: defaultStore
	 * @Declaration : + (NSUbiquitousKeyValueStore *)defaultStore
	 * @Description : Returns the shared iCloud key-value store object.
	 * @return Return Value The shared iCloud key-value store object.
	 * @Discussion An app must always use the default iCloud key-value store object to get and set values. This store is tied to the unique
	 *             identifier string your app provides in its entitlement requests.
	 **/

	public static NSUbiquitousKeyValueStore defaultStore() {
		return null;
	}

	// 2
	/**
	 * @Signature: arrayForKey:
	 * @Declaration : - (NSArray *)arrayForKey:(NSString *)aKey
	 * @Description : Returns the array associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The array associated with the specified key, or nil if the key was not found or its value is not an NSArray
	 *         object.
	 **/

	public NSArray arrayForKey(NSString aKey) {
		return null;
	}

	// 3
	/**
	 * @Signature: boolForKey:
	 * @Declaration : - (BOOL)boolForKey:(NSString *)aKey
	 * @Description : Returns the Boolean value associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value If a Boolean value is associated with the specified key, that value is returned. If the key was not found, this
	 *         method returns NO.
	 **/

	public boolean boolForKey(NSString aKey) {
		return false;
	}

	// 4
	/**
	 * @Signature: dataForKey:
	 * @Declaration : - (NSData *)dataForKey:(NSString *)aKey
	 * @Description : Returns the data object associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The data object associated with the specified key or nil if the key was not found or its value is not an NSData
	 *         object.
	 **/

	public NSData dataForKey() {
		return null;
	}

	// 5
	/**
	 * @Signature: dictionaryForKey:
	 * @Declaration : - (NSDictionary *)dictionaryForKey:(NSString *)aKey
	 * @Description : Returns the dictionary object associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The dictionary object associated with the specified key or nil if the key was not found or its value is not an
	 *         NSDictionary object.
	 **/

	public NSDictionary dictionaryForKey(NSString aKey) {
		return null;
	}

	// 6
	/**
	 * @Signature: dictionaryRepresentation
	 * @Declaration : - (NSDictionary *)dictionaryRepresentation
	 * @Description : Returns a dictionary containing all of the key-value pairs in the key-value store.
	 * @return Return Value A dictionary containing the key and value data in the key-value store.
	 * @Discussion This method returns the in-memory version of the keys and values. If you want to ensure that this dictionary contains the
	 *             most recent set of changes, call synchronize shortly before calling this method.
	 **/

	public NSDictionary dictionaryRepresentation() {
		return null;
	}

	// 7
	/**
	 * @Signature: doubleForKey:
	 * @Declaration : - (double)doubleForKey:(NSString *)aKey
	 * @Description : Returns the double value associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The double value associated with the specified key or 0 if the key was not found. If the key exists but does not
	 *         contain a numerical value, this method returns 0.
	 **/

	public double doubleForKey(NSString aKey) {
		return 0;
	}

	// 8
	/**
	 * @Signature: longLongForKey:
	 * @Declaration : - (long long)longLongForKey:(NSString *)aKey
	 * @Description : Returns the long long value associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The long long value associated with the specified key or 0 if the key was not found. If the key exists but does
	 *         not contain a numerical value, this method returns 0.
	 **/

	public long longLongForKey(NSString aKey) {
		return 0;
	}

	// 9
	/**
	 * @Signature: objectForKey:
	 * @Declaration : - (id)objectForKey:(NSString *)aKey
	 * @Description : Returns the object associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The object associated with the specified key or nil if the key was not found.
	 * @Discussion You can use this method to retrieve objects whose type you do not necessarily know from the key-value store. The object
	 *             returned by this method is always one of the property list types: NSNumber, NSString, NSDate, NSData, NSArray, or
	 *             NSDictionary.
	 **/

	public NSObject objectForKey(NSString aKey) {
		return null;
	}

	// 10
	/**
	 * @Signature: removeObjectForKey:
	 * @Declaration : - (void)removeObjectForKey:(NSString *)aKey
	 * @Description : Removes the value associated with the specified key from the key-value store.
	 * @param aKey The key corresponding to the value you want to remove.
	 * @Discussion If the specified key is not found in the key-value store, this method does nothing. This method removes the key from the
	 *             in-memory version of the store only. Call the synchronize method at appropriate times to update the information on disk.
	 **/

	public void removeObjectForKey(NSString aKey) {
	}

	// 11
	/**
	 * @Signature: setArray:forKey:
	 * @Declaration : - (void)setArray:(NSArray *)anArray forKey:(NSString *)aKey
	 * @Description : Sets an array object for the specified key in the key-value store.
	 * @param anArray An array whose contents can be stored in a property list format. In other words, the objects in the array must be of
	 *            the types NSNumber, NSString, NSDate, NSData, NSArray, or NSDictionary. The total size (in bytes) of the array and its
	 *            contents must not exceed the per-key size limits.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 **/

	public void setArrayForKey(NSArray anArray, NSString aKey) {
	}

	// 12
	/**
	 * @Signature: setBool:forKey:
	 * @Declaration : - (void)setBool:(BOOL)value forKey:(NSString *)aKey
	 * @Description : Sets a Boolean value for the specified key in the key-value store.
	 * @param value The Boolean value to store.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.\
	 **/

	public void setBoolForKey(boolean value, NSString aKey) {
	}

	// 13
	/**
	 * @Signature: setData:forKey:
	 * @Declaration : - (void)setData:(NSData *)aData forKey:(NSString *)aKey
	 * @Description : Sets a data object for the specified key in the key-value store.
	 * @param aData The data object to store. The total size of this data object (including any object overhead) must not exceed 1 MB.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 * @Discussion Using an NSData object as a value in key-value storage lets you store arbitrary data. For example, in a game app, you can
	 *             use it to store game state to iCloud. Be sure to exercise caution when storing a data object. Because it is available to
	 *             be read and modified by every instance of your app attached to a userâ€™s iCloud account, some of which may be older
	 *             versions or running on another platform, you must diligently protect a data objectâ€™s integrity. For more information, see
	 *             â€œExercise Caution When Using NSData Objects as Valuesâ€? in iCloud Design Guide.
	 **/

	public void setDataForKey(NSData aData, NSString aKey) {
	}

	/**
	 * @Signature: setDictionary:forKey:
	 * @Declaration : - (void)setDictionary:(NSDictionary *)aDictionary forKey:(NSString *)aKey
	 * @Description : Sets a dictionary object for the specified key in the key-value store.
	 * @param aDictionary A dictionary whose contents can be stored in a property list format. In other words, the objects in the dictionary
	 *            must be of the types NSNumber, NSString, NSDate, NSData, NSArray, or NSDictionary. The total size (in bytes) of the
	 *            dictionary and its contents must not exceed the per-key size limits.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 **/

	public void setDictionaryForKey(NSDictionary aDictionary, NSString aKey) {
	}

	/**
	 * @Signature: setDouble:forKey:
	 * @Declaration : - (void)setDouble:(double)value forKey:(NSString *)aKey
	 * @Description : Sets a double value for the specified key in the key-value store.
	 * @param value The double value to store.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 **/

	public void setDoubleForKey(double value, NSString aKey) {
	}

	/**
	 * @Signature: setLongLong:forKey:
	 * @Declaration : - (void)setLongLong:(long long)value forKey:(NSString *)aKey
	 * @Description : Sets a long long value for the specified key in the key-value store.
	 * @param value The long long value to store.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 **/

	public void setLongLongForKey(long value, NSString aKey) {
	}

	/**
	 * @Signature: setObject:forKey:
	 * @Declaration : - (void)setObject:(id)anObject forKey:(NSString *)aKey
	 * @Description : Sets an object for the specified key in the key-value store.
	 * @param anObject The object you want to store. The type of the object must be one of the property list types: NSNumber, NSString,
	 *            NSDate, NSData, NSArray, or NSDictionary. The total size (in bytes) of the object must not exceed the per-key size limits.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 * @Discussion If the type of anObject is not one of the property list types, this method does not set it in the key-value store.
	 *             Instead, it logs an error and silently ignores the object.
	 **/

	public void setObjectForKey(NSObject anObject, NSString aKey) {
	}

	/**
	 * @Signature: setString:forKey:
	 * @Declaration : - (void)setString:(NSString *)aString forKey:(NSString *)aKey
	 * @Description : Sets a string object for the specified key in the key-value store.
	 * @param aString The string you want to store. The total size (in bytes) of the string must not exceed the per-key size limits.
	 * @param aKey The key under which to store the value. The length of this key must not exceed 64 bytes using UTF8 encoding.
	 **/

	public void setStringForKey(NSString aString, NSString aKey) {
	}

	/**
	 * @Signature: stringForKey:
	 * @Declaration : - (NSString *)stringForKey:(NSString *)aKey
	 * @Description : Returns the string associated with the specified key.
	 * @param aKey A key in the key-value store.
	 * @return Return Value The string associated with the specified key or nil if the key was not found or its value is not an NSString
	 *         object.
	 **/

	public NSString stringForKey(NSString aKey) {
		return null;
	}

	/**
	 * @Signature: synchronize
	 * @Declaration : - (BOOL)synchronize
	 * @Description : Explicitly synchronizes in-memory keys and values with those stored on disk.
	 * @return Return Value YES if the in-memory and on-disk keys and values were synchronized, or NO if an error occurred. For example,
	 *         this method returns NO if an app was not built with the proper entitlement requests.
	 * @Discussion The only recommended time to call this method is upon app launch, or upon returning to the foreground, to ensure that the
	 *             in-memory key-value store representation is up-to-date. Changes you make to the key-value store are saved to memory. The
	 *             system then synchronizes the in-memory keys and values with the local on-disk cache, automatically and at appropriate
	 *             times. For example, it synchronizes the keys when your app is put into the background, when changes are received from
	 *             iCloud, and when your app makes changes to a key but does not call the synchronize method for several seconds. This
	 *             method does not force new keys and values to be written to iCloud. Rather, it lets iCloud know that new keys and values
	 *             are available to be uploaded. Do not rely on your keys and values being available on other devices immediately. The
	 *             system controls when those keys and values are uploaded. The frequency of upload requests for key-value storage is
	 *             limited to several per minute. During synchronization between memory and disk, this method updates your in-memory set of
	 *             keys and values with changes previously received from iCloud.
	 **/

	public boolean synchronize() {
		return false;
	}
}