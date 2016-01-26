
package com.myappconverter.java.foundations;



public abstract class NSCoder extends NSObject {

	boolean requiresSecureCoding;
	NSSet allowedClasses;
	boolean allowsKeyedCoding;
	int systemVersion;

	public abstract boolean getRequiresSecureCoding();

	public abstract NSSet getAllowedClasses();

	public abstract boolean getAllowsKeyedCoding();

	public abstract int getSystemVersion();

//	// Testing Coder
//	/**
//	 * @Declaration : - (BOOL)allowsKeyedCoding
//	 * @Description : Returns a Boolean value that indicates whether the receiver supports keyed coding of objects.
//	 * @Discussion The default implementation returns NO. Concrete subclasses that support keyed coding, such as NSKeyedArchiver, need to
//	 *             override this method to return YES.
//	 */

	/**
	 * @Declaration : - (BOOL)containsValueForKey:(NSString *)key
	 * @Description : Returns a Boolean value that indicates whether an encoded value is available for a string.
	 * @Discussion Subclasses must override this method if they perform keyed coding. The string is passed as key.
	 */
	
	public abstract boolean containsValueForKey(NSString key);

	// Encoding Data
	/**
	 * @Signature: encodeArrayOfObjCType:count:at:
	 * @Declaration : - (void)encodeArrayOfObjCType:(const char *)itemType count:(NSUInteger)count at:(const void *)address
	 * @Description : Encodes an array of count items, whose Objective-C type is given by itemType.
	 * @Discussion The values are encoded from the buffer beginning at address. itemType must contain exactly one type code. NSCoder’s
	 *             implementation invokes encodeValueOfObjCType:at: to encode the entire array of items. Subclasses that implement the
	 *             encodeValueOfObjCType:at: method do not need to override this method. This method must be matched by a subsequent
	 *             decodeArrayOfObjCType:count:at: message. For information on creating an Objective-C type code suitable for itemType, see
	 *             “Type Encodings
	 **/
	
	public abstract void encodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address);

	/**
	 * @Signature: encodeBool:forKey:
	 * @Declaration : - (void)encodeBool:(BOOL)boolv forKey:(NSString *)key
	 * @Description : Encodes boolv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeBoolForKey(boolean boolv, NSString key);

	/**
	 * @Signature: encodeBycopyObject:
	 * @Declaration : - (void)encodeBycopyObject:(id)object
	 * @Description : Can be overridden by subclasses to encode object so that a copy, rather than a proxy, is created upon decoding.
	 * @Discussion NSCoder’s implementation simply invokes encodeObject:. This method must be matched by a corresponding decodeObject
	 *             message.
	 **/
	
	public abstract void encodeBycopyObject(NSObject object);

	/**
	 * @Declaration : - (void)encodeByrefObject:(id)object
	 * @Description : Can be overridden by subclasses to encode object so that a proxy, rather than a copy, is created upon decoding.
	 * @Discussion NSCoder’s implementation simply invokes encodeObject:. This method must be matched by a corresponding decodeObject
	 *             message.
	 */
	
	public abstract void encodeByrefObject(NSObject object);

	/**
	 * @Signature: encodeBytes:length:
	 * @Declaration : - (void)encodeBytes:(const void *)address length:(NSUInteger)numBytes
	 * @Description : Encodes a buffer of data whose types are unspecified.
	 * @Discussion The buffer to be encoded begins at address, and its length in bytes is given by numBytes. This method must be matched by
	 *             a corresponding decodeBytesWithReturnedLength: message.
	 **/
	
	public abstract void encodeBytesLength(NSObject address, int numBytes);

	/**
	 * @Signature: encodeBytes:length:forKey:
	 * @Declaration : - (void)encodeBytes:(const uint8_t *)bytesp length:(NSUInteger)lenv forKey:(NSString *)key
	 * @Description : Encodes a buffer of data, bytesp, whose length is specified by lenv, and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeBytesLengthForKey(int[] bytesp, int lenv, NSString key);

	/**
	 * @Signature: encodeConditionalObject:
	 * @Declaration : - (void)encodeConditionalObject:(id)object
	 * @Description : Can be overridden by subclasses to conditionally encode object, preserving common references to that object.
	 * @Discussion In the overriding method, object should be encoded only if it’s unconditionally encoded elsewhere (with any other
	 *             encode...Object: method). This method must be matched by a subsequent decodeObject message. Upon decoding, if object was
	 *             never encoded unconditionally, decodeObject returns nil in place of object. However, if object was encoded
	 *             unconditionally, all references to object must be resolved. NSCoder’s implementation simply invokes encodeObject:.
	 **/
	
	public abstract void encodeConditionalObject(NSObject object);

	/**
	 * @Signature: encodeConditionalObject:forKey:
	 * @Declaration : - (void)encodeConditionalObject:(id)objv forKey:(NSString *)key
	 * @Description : Conditionally encodes a reference to objv and associates it with the string key only if objv has been unconditionally
	 *              encoded with encodeObject:forKey:.
	 * @Discussion Subclasses must override this method if they support keyed coding. The encoded object is decoded with the
	 *             decodeObjectForKey: method. If objv was never encoded unconditionally, decodeObjectForKey: returns nil in place of objv.
	 **/
	
	public abstract void encodeConditionalObjectForKey(NSObject objv, NSString key);

	/**
	 * @Signature: encodeDataObject:
	 * @Declaration : - (void)encodeDataObject:(NSData *)data
	 * @Description : Encodes a given NSData object.
	 * @Discussion Subclasses must override this method. This method must be matched by a subsequent decodeDataObject message.
	 **/
	
	public abstract void encodeDataObject(NSData data);

	/**
	 * @Signature: encodeDouble:forKey:
	 * @Declaration : - (void)encodeDouble:(double)realv forKey:(NSString *)key
	 * @Description : Encodes realv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeDoubleForKey(double realv, NSString key);

	/**
	 * @Signature: encodeFloat:forKey:
	 * @Declaration : - (void)encodeFloat:(float)realv forKey:(NSString *)key
	 * @Description : Encodes realv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeFloatForKey(float realv, NSString key);

	/**
	 * @Signature: encodeInt:forKey:
	 * @Declaration : - (void)encodeInt:(int)intv forKey:(NSString *)key
	 * @Description : Encodes intv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeIntForKey(int intv, NSString key);

	/**
	 * @Signature: encodeInteger:forKey:
	 * @Declaration : - (void)encodeInteger:(NSInteger)intv forKey:(NSString *)key
	 * @Description : Encodes a given NSInteger and associates it with a given key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeIntegerForKey(int intv, NSString key);

	/**
	 * @Signature: encodeInt32:forKey:
	 * @Declaration : - (void)encodeInt32:(int32_t)intv forKey:(NSString *)key
	 * @Description : Encodes the 32-bit integer intv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeInt32ForKey(int intv, NSString key);

	/**
	 * @Signature: encodeInt64:forKey:
	 * @Declaration : - (void)encodeInt64:(int64_t)intv forKey:(NSString *)key
	 * @Description : Encodes the 64-bit integer intv and associates it with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract void encodeInt64ForKey(int intv, NSString key);

	/**
	 * @Signature: encodeObject:
	 * @Declaration : - (void)encodeObject:(id)object
	 * @Description : Encodes object.
	 * @Discussion NSCoder’s implementation simply invokes encodeValueOfObjCType:at: to encode object. Subclasses can override this method
	 *             to encode a reference to object instead of object itself. For example, NSArchiver detects duplicate objects and encodes a
	 *             reference to the original object rather than encode the same object twice. This method must be matched by a subsequent
	 *             decodeObject message.
	 **/
	
	public abstract void encodeObject(NSObject object);

	/**
	 * @Signature: encodeObject:forKey:
	 * @Declaration : - (void)encodeObject:(id)objv forKey:(NSString *)key
	 * @Description : Encodes the object objv and associates it with the string key.
	 * @Discussion Subclasses must override this method to identify multiple encodings of objv and encode a reference to objv instead. For
	 *             example, NSKeyedArchiver detects duplicate objects and encodes a reference to the original object rather than encode the
	 *             same object twice.
	 **/
	
	public abstract void encodeObjectForKey(NSObject objv, NSString key);

	/**
	 * @Signature: encodeRootObject:
	 * @Declaration : - (void)encodeRootObject:(id)rootObject
	 * @Description : Can be overridden by subclasses to encode an interconnected group of Objective-C objects, starting with rootObject.
	 * @Discussion NSCoder’s implementation simply invokes encodeObject:. This method must be matched by a subsequent decodeObject message.
	 **/
	
	public abstract void encodeRootObject(NSObject rootObject);

	/**
	 * @Signature: encodeValueOfObjCType:at:
	 * @Declaration : - (void)encodeValueOfObjCType:(const char *)valueType at:(const void *)address
	 * @Description : Must be overridden by subclasses to encode a single value residing at address, whose Objective-C type is given by
	 *              valueType.
	 * @Discussion valueType must contain exactly one type code. This method must be matched by a subsequent decodeValueOfObjCType:at:
	 *             message. For information on creating an Objective-C type code suitable for valueType, see “Type Encodings
	 **/
	
	public abstract void encodeValueOfObjCTypeAt(char[] valueType, NSObject address);

	/**
	 * @Signature: encodeValuesOfObjCTypes:
	 * @Declaration : - (void)encodeValuesOfObjCTypes:(const char *)valueTypes, ...
	 * @Description : Encodes a series of values of potentially differing Objective-C types.
	 * @Discussion valueTypes is a C string containing any number of type codes. The variable arguments to this method consist of one or
	 *             more pointer arguments, each of which specifies a buffer containing the value to be encoded. For each type code in
	 *             valueTypes, you must specify a corresponding pointer argument. This method must be matched by a subsequent
	 *             decodeValuesOfObjCTypes: message. NSCoder’s implementation invokes encodeValueOfObjCType:at: to encode individual types.
	 *             Subclasses that implement the encodeValueOfObjCType:at: method do not need to override this method. However, subclasses
	 *             that provide a more efficient approach for encoding a series of values may override this method to implement that
	 *             approach. For information on creating Objective-C type codes suitable for valueTypes, see “Type Encodings
	 **/
	
	public abstract void encodeValuesOfObjCTypes(char[]... valueTypes);

	// Decoding Data
	/**
	 * @Signature: decodeArrayOfObjCType:count:at:
	 * @Declaration : - (void)decodeArrayOfObjCType:(const char *)itemType count:(NSUInteger)count at:(void *)address
	 * @Description : Decodes an array of count items, whose Objective-C type is given by itemType.
	 * @Discussion The items are decoded into the buffer beginning at address, which must be large enough to contain them all. itemType must
	 *             contain exactly one type code. NSCoder’s implementation invokes decodeValueOfObjCType:at: to decode the entire array of
	 *             items. This method matches an encodeArrayOfObjCType:count:at: message used during encoding. For information on creating
	 *             an Objective-C type code suitable for itemType, see “Type Encodings
	 **/
	
	public abstract void decodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address);

	/**
	 * @Signature: decodeBoolForKey:
	 * @Declaration : - (BOOL)decodeBoolForKey:(NSString *)key
	 * @Description : Decodes and returns a boolean value that was previously encoded with encodeBool:forKey: and associated with the string
	 *              key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract boolean decodeBoolForKey(NSString key);

	/**
	 * @Signature: decodeBytesForKey:returnedLength:
	 * @Declaration : - (const uint8_t *)decodeBytesForKey:(NSString *)key returnedLength:(NSUInteger *)lengthp
	 * @Description : Decodes a buffer of data that was previously encoded with encodeBytes:length:forKey: and associated with the string
	 *              key.
	 * @Discussion The buffer’s length is returned by reference in lengthp. The returned bytes are immutable. Subclasses must override this
	 *             method if they perform keyed coding.
	 **/
	
	public abstract byte[] decodeBytesForKeyReturnedLength(NSString key, int[] lengthp);

	/**
	 * @Signature: decodeBytesWithReturnedLength:
	 * @Declaration : - (void *)decodeBytesWithReturnedLength:(NSUInteger *)numBytes
	 * @Description : Decodes a buffer of data whose types are unspecified.
	 * @Discussion NSCoder’s implementation invokes decodeValueOfObjCType:at: to decode the data as a series of bytes, which this method
	 *             then places into a buffer and returns. The buffer’s length is returned by reference in numBytes. If you need the bytes
	 *             beyond the scope of the current @autoreleasepool block, you must copy them. This method matches an encodeBytes:length:
	 *             message used during encoding.
	 **/
	
	public abstract void decodeBytesWithReturnedLength(int numBytes);

	/**
	 * @Signature: decodeDataObject
	 * @Declaration : - (NSData *)decodeDataObject
	 * @Description : Decodes and returns an NSData object that was previously encoded with encodeDataObject:. Subclasses must override this
	 *              method.
	 * @Discussion The implementation of your overriding method must match the implementation of your encodeDataObject: method. For example,
	 *             a typical encodeDataObject: method encodes the number of bytes of data followed by the bytes themselves. Your override of
	 *             this method must read the number of bytes, create an NSData object of the appropriate size, and decode the bytes into the
	 *             new NSData object.
	 **/
	
	public abstract NSData decodeDataObject();

	/**
	 * @Signature: decodeDoubleForKey:
	 * @Declaration : - (double)decodeDoubleForKey:(NSString *)key
	 * @Description : Decodes and returns a double value that was previously encoded with either encodeFloat:forKey: or encodeDouble:forKey:
	 *              and associated with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract double decodeDoubleForKey(NSString key);

	/**
	 * @Signature: decodeFloatForKey:
	 * @Declaration : - (float)decodeFloatForKey:(NSString *)key
	 * @Description : Decodes and returns a float value that was previously encoded with encodeFloat:forKey: or encodeDouble:forKey: and
	 *              associated with the string key.
	 * @Discussion If the value was encoded as a double, the extra precision is lost. If the encoded real value does not fit into a float,
	 *             the method raises an NSRangeException. Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract float decodeFloatForKey(NSString key);

	/**
	 * @Signature: decodeIntForKey:
	 * @Declaration : - (int)decodeIntForKey:(NSString *)key
	 * @Description : Decodes and returns an int value that was previously encoded with encodeInt:forKey:, encodeInteger:forKey:,
	 *              encodeInt32:forKey:, or encodeInt64:forKey: and associated with the string key.
	 * @Discussion If the encoded integer does not fit into the default integer size, the method raises an NSRangeException. Subclasses must
	 *             override this method if they perform keyed coding.
	 **/
	
	public abstract int decodeIntForKey(NSString key);

	/**
	 * @Signature: decodeIntegerForKey:
	 * @Declaration : - (NSInteger)decodeIntegerForKey:(NSString *)key
	 * @Description : Decodes and returns an NSInteger value that was previously encoded with encodeInt:forKey:, encodeInteger:forKey:,
	 *              encodeInt32:forKey:, or encodeInt64:forKey: and associated with the string key.
	 * @Discussion If the encoded integer does not fit into the NSInteger size, the method raises an NSRangeException. Subclasses must
	 *             override this method if they perform keyed coding.
	 **/
	
	public abstract int decodeIntegerForKey(NSString key);

	/**
	 * @Signature: decodeInt32ForKey:
	 * @Declaration : - (int32_t)decodeInt32ForKey:(NSString *)key
	 * @Description : Decodes and returns a 32-bit integer value that was previously encoded with encodeInt:forKey:, encodeInteger:forKey:,
	 *              encodeInt32:forKey:, or encodeInt64:forKey: and associated with the string key.
	 * @Discussion If the encoded integer does not fit into a 32-bit integer, the method raises an NSRangeException. Subclasses must
	 *             override this method if they perform keyed coding.
	 **/
	
	public abstract int decodeInt32ForKey(NSString key);

	/**
	 * @Signature: decodeInt64ForKey:
	 * @Declaration : - (int64_t)decodeInt64ForKey:(NSString *)key
	 * @Description : Decodes and returns a 64-bit integer value that was previously encoded with encodeInt:forKey:, encodeInteger:forKey:,
	 *              encodeInt32:forKey:, or encodeInt64:forKey: and associated with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract int decodeInt64ForKey(NSString key);

	/**
	 * @Signature: decodeObject
	 * @Declaration : - (id)decodeObject
	 * @Description : Decodes an Objective-C object that was previously encoded with any of the encode...Object: methods.
	 * @Discussion NSCoder’s implementation invokes decodeValueOfObjCType:at: to decode the object data. Subclasses may need to override
	 *             this method if they override any of the corresponding encode...Object: methods. For example, if an object was encoded
	 *             conditionally using the encodeConditionalObject: method, this method needs to check whether the object had actually been
	 *             encoded.
	 **/
	
	public abstract NSObject decodeObject();

	/**
	 * @Signature: decodeObjectForKey:
	 * @Declaration : - (id)decodeObjectForKey:(NSString *)key
	 * @Description : Decodes and returns an Objective-C object that was previously encoded with encodeObject:forKey: or
	 *              encodeConditionalObject:forKey: and associated with the string key.
	 * @Discussion Subclasses must override this method if they perform keyed coding.
	 **/
	
	public abstract NSObject decodeObjectForKey(NSString key);

	/**
	 * @Signature: decodeValueOfObjCType:at:
	 * @Declaration : - (void)decodeValueOfObjCType:(const char *)valueType at:(void *)data
	 * @Description : Decodes a single value, whose Objective-C type is given by valueType.
	 * @Discussion valueType must contain exactly one type code, and the buffer specified by data must be large enough to hold the value
	 *             corresponding to that type code. For information on creating an Objective-C type code suitable for valueType, see “Type
	 *             Encodings. Subclasses must override this method and provide an implementation to decode the value. In your overriding
	 *             implementation, decode the value into the buffer beginning at data. This method matches an encodeValueOfObjCType:at:
	 *             message used during encoding.
	 **/
	
	public abstract void decodeValueOfObjCTypeAt(char[] valueType, NSObject data);

	/**
	 * @Signature: decodeValuesOfObjCTypes:
	 * @Declaration : - (void)decodeValuesOfObjCTypes:(const char *)valueTypes, ...
	 * @Description : Decodes a series of potentially different Objective-C types.
	 * @Discussion valueTypes is a single C string containing any number of type codes. The variable arguments to this method consist of one
	 *             or more pointer arguments, each of which specifies the buffer in which to place a single decoded value. For each type
	 *             code in valueTypes, you must specify a corresponding pointer argument whose buffer is large enough to hold the decoded
	 *             value. This method matches an encodeValuesOfObjCTypes: message used during encoding. NSCoder’s implementation invokes
	 *             decodeValueOfObjCType:at: to decode individual types. Subclasses that implement the decodeValueOfObjCType:at: method do
	 *             not need to override this method. For information on creating Objective-C type codes suitable for valueTypes, see “Type
	 *             Encodings
	 **/
	
	public abstract void decodeValuesOfObjCTypes(char[] valueTypes);

	/**
	 * @Signature: decodeObjectOfClass:forKey:
	 * @Declaration : - (id)decodeObjectOfClass:(Class)aClass forKey:(NSString *)key
	 * @Description : Decodes an object for the key, restricted to the specified class.
	 * @param aClass The expect class type.
	 * @param key The coder key.
	 * @return Return Value The decoded object.
	 * @Discussion If the coder responds YES to requiresSecureCoding, then an exception will be thrown if the class to be decoded does not
	 *             implement NSSecureCoding or is not isKindOfClass: of aClass. If the coder responds NO to requiresSecureCoding, then the
	 *             class argument is ignored and no check of the class of the decoded object is performed, exactly as if decodeObjectForKey:
	 *             had been called.
	 **/
	
	public abstract NSObject decodeObjectOfClassForKey(Class<?> aClass, NSString key);

	/**
	 * @Signature: decodeObjectOfClasses:forKey:
	 * @Declaration : - (id)decodeObjectOfClasses:(NSSet *)classes forKey:(NSString *)key
	 * @Description : Decodes an object for the key, restricted to the specified classes.
	 * @param classes A set of the expected classes.
	 * @param key The coder key.
	 * @return Return Value The decoded object.
	 * @Discussion The class of the object may be any class in the classes set, or a subclass of any class in the set. Otherwise, the
	 *             behavior is the same as decodeObjectOfClass:forKey:.
	 **/
	
	public abstract void decodeObjectOfClassesForKey(NSSet<?> classes, NSString key);

	/**
	 * @Signature: decodePropertyListForKey:
	 * @Declaration : - (id)decodePropertyListForKey:(NSString *)key
	 * @Description : Returns a decoded property list for the specified key.
	 * @param key The coder key.
	 * @return Return Value A decoded object containing a property list.
	 * @Discussion This method calls decodeObjectOfClasses:forKey: with a set allowing only property list types.
	 **/
	
	public abstract NSObject decodePropertyListForKey(NSString key);

	// Secure Coding
	/**
	 * @Signature: requiresSecureCoding
	 * @Declaration : - (BOOL)requiresSecureCoding
	 * @Description : Returns whether the coder requires secure coding.
	 * @return Return Value YES if this coder requires secure coding; NO otherwise.
	 * @Discussion Secure coders check a set of allowed classes before decoding objects, and all objects must implement the NSSecureCoding
	 *             protocol.
	 **/
//	

//	/**
//	 * @Signature: allowedClasses
//	 * @Declaration : - (NSSet *)allowedClasses
//	 * @Description : Get the current set of coded classes that allow secure coding.
//	 * @return Return Value The allowed classes.
//	 * @Discussion Secure coders check this set of allowed classes before decoding objects, and all objects must implement the
//	 *             NSSecureCoding protocol.
//	 **/
//	

	// Getting Version Information

//	/**
//	 * @Signature: systemVersion
//	 * @Declaration : - (unsigned)systemVersion
//	 * @Description : During encoding, this method should return the system version currently in effect.
//	 * @Discussion During decoding, this method should return the version that was in effect when the data was encoded. By default, this
//	 *             method returns the current system version, which is appropriate for encoding but not for decoding. Subclasses that
//	 *             implement decoding must override this method to return the system version of the data being decoded.
//	 **/
//	

	/**
	 * @Signature: versionForClassName:
	 * @Declaration : - (NSInteger)versionForClassName:(NSString *)className
	 * @Description : This method is present for historical reasons and is not used with keyed archivers.
	 * @return Return Value The version in effect for the class named className or NSNotFound if no class named className exists.
	 * @Discussion The version number does apply not to NSKeyedArchiver/NSKeyedUnarchiver. A keyed archiver does not encode class version
	 *             numbers.
	 **/
	
	public abstract int versionForClassName(NSString className);

	// Managing Zones

	/**
	 * @Signature: objectZone
	 * @Declaration : - (NSZone *)objectZone
	 * @Description : This method is present for historical reasons and has no effect.
	 * @Discussion NSCoder’s implementation returns the default memory zone, as given by NSDefaultMallocZone().
	 **/
	
	public abstract NSZone objectZone();

	/**
	 * @Signature: setObjectZone:
	 * @Declaration : - (void)setObjectZone:(NSZone *)zone
	 * @Description : This method is present for historical reasons and has no effect.
	 **/
	
	public abstract void setObjectZone(NSZone zone);

	/** KV **/

	// encodeBoolForKey
	public abstract void encodeBoolean(boolean paramBoolean);

	// encodeBytesLength
	public abstract void encodeByte(byte paramByte);

	// encodeBytesLengthForKey
	public abstract void encodeBytes(byte[] paramArrayOfByte);

	// encodeArrayOfObjCTypeCountAt
	public abstract void encodeChar(char paramChar);

	public abstract void encodeShort(short paramShort);

	public abstract void encodeInt(int paramInt);

	public abstract void encodeLong(long paramLong);

	public abstract void encodeFloat(float paramFloat);

	public abstract void encodeDouble(double paramDouble);

	public abstract void encodeObject(Object paramObject);

	public abstract void encodeClass(Class<?> paramClass);

	public abstract void encodeObjects(Object... paramArrayOfObject);

	public abstract boolean decodeBoolean();

	public abstract byte decodeByte();

	public abstract byte[] decodeBytes();

	public abstract char decodeChar();

	public abstract short decodeShort();

	public abstract int decodeInt();

	public abstract long decodeLong();

	public abstract float decodeFloat();

	public abstract double decodeDouble();


	public abstract Class<?> decodeClass();

	public abstract Object[] decodeObjects();

	public void finishCoding() {
	}
}