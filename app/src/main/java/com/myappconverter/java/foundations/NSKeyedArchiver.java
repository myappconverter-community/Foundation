
package com.myappconverter.java.foundations;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;
import android.util.Xml.Encoding;

import com.myappconverter.java.foundations.constants.NSPropertyList;
import com.myappconverter.java.foundations.protocols.NSKeyedArchiverDelegate;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.PList;


public class NSKeyedArchiver extends NSCoder {

	public static NSString codedName;
	public static Class cls;
	//
	Hashtable<String, Object> mapValues;
	int initSize = 0;
	NSMutableData mData;
	boolean secureCoding = false;
	int outFormat = NSPropertyList.NSPropertyListBinaryFormat_v1_0;
	NSKeyedArchiverDelegate delegate;

	public NSMutableData getmData() {
		return mData;
	}

	public void setmData(NSMutableData mData) {
		this.mData = mData;
	}

	public Hashtable<String, Object> getMapValues() {
		return mapValues;
	}

	public void setMapValues(Hashtable<String, Object> mapValues) {
		this.mapValues = mapValues;
	}

	public NSKeyedArchiver() {
		this.mData = new NSMutableData();
		mapValues = new Hashtable<String, Object>();
	}

	public NSKeyedArchiver(NSMutableData mData) {
		super();
		initSize = mData.getWrappedData().length;
		this.mData = mData;
		mapValues = new Hashtable<String, Object>();
	}

	// Constants

	public static final NSString NSKeyedArchiveRootObjectKey = new NSString(
			"NSKeyedArchiveRootObjectKey");

	// Initializing an NSKeyedArchiver Object

	/**
	 * @Signature: initForWritingWithMutableData:
	 * @Declaration : - (id)initForWritingWithMutableData:(NSMutableData *)data
	 * @Description : Returns the receiver, initialized for encoding an archive into a given a mutable-data object.
	 * @param data The mutable-data object into which the archive is written.
	 * @return Return Value The receiver, initialized for encoding an archive into data.
	 * @Discussion When you finish encoding data, you must invoke finishEncoding at which point data is filled. The format of the receiver
	 *             is NSPropertyListBinaryFormat_v1_0.
	 **/
	
	public Object initForWritingWithMutableData(NSMutableData data) {
		return new NSKeyedArchiver(data);
	}

	// Archiving Data

	/**
	 * @Signature: archivedDataWithRootObject:
	 * @Declaration : + (NSData *)archivedDataWithRootObject:(id)rootObject
	 * @Description : Returns an NSData object containing the encoded form of the object graph whose root object is given.
	 * @param rootObject The root of the object graph to archive.
	 * @return Return Value An NSData object containing the encoded form of the object graph whose root object is rootObject. The format of
	 *         the archive is NSPropertyListBinaryFormat_v1_0.
	 **/
	
	public static NSData archivedDataWithRootObject(Object rootObject) {
		Hashtable<NSString, Object> mv = new Hashtable<NSString, Object>();
		mv.put(new NSString("root"), rootObject);
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o;
		try {
			o = new ObjectOutputStream(b);
			o.writeObject(b);
			o.flush();
			o.close();
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		NSMutableData md = new NSMutableData(b.toByteArray());
		return md;
	}

	/**
	 * @Signature: archiveRootObject:toFile:
	 * @Declaration : + (BOOL)archiveRootObject:(id)rootObject toFile:(NSString *)path
	 * @Description : Archives an object graph rooted at a given object by encoding it into a data object then atomically writes the
	 *              resulting data object to a file at a given path, and returns a Boolean value that indicates whether the operation was
	 *              successful.
	 * @param rootObject The root of the object graph to archive.
	 * @param path The path of the file in which to write the archive.
	 * @return Return Value YES if the operation was successful, otherwise NO.
	 * @Discussion The format of the archive is NSPropertyListBinaryFormat_v1_0.
	 **/
	
	
	public static boolean archiveRootObjectToFile(Object rootObject, NSString path)
			throws FileNotFoundException, IOException {
		Hashtable<NSString, Object> mv = new Hashtable<NSString, Object>();
		mv.put(new NSString("root"), rootObject);
		ObjectOutputStream outputStream = null;
		outputStream = new ObjectOutputStream(new FileOutputStream(path.getWrappedString()));
		outputStream.writeObject(mv);
		outputStream.flush();
		outputStream.close();
		return true;
		// TODO check that
	}

	/**
	 * @Signature: finishEncoding
	 * @Declaration : - (void)finishEncoding
	 * @Description : Instructs the receiver to construct the final data stream.
	 * @Discussion No more values can be encoded after this method is called. You must call this method when finished.
	 **/
	
	public void finishEncoding() {
		encodeObjects(mapValues);
	}

	/**
	 * - (NSPropertyListFormat)outputFormat
	 *
	 * @Description : Returns the format in which the receiver encodes its data.
	 * @return Return Value The format in which the receiver encodes its data. The available formats are NSPropertyListXMLFormat_v1_0 and
	 *         NSPropertyListBinaryFormat_v1_0.
	 */
	
	public int outputFormat() {
		return outFormat;

	}

	/**
	 * - (void)setOutputFormat:(NSPropertyListFormat)format
	 *
	 * @Description : Sets the format in which the receiver encodes its data.
	 * @param format The format in which the receiver encodes its data. format can be NSPropertyListXMLFormat_v1_0 or
	 *            NSPropertyListBinaryFormat_v1_0.
	 */
	
	public void setOutputFormat(int format) {
		this.outFormat = format;
	}

	/**
	 * @Signature: setRequiresSecureCoding:
	 * @Declaration : - (void)setRequiresSecureCoding:(BOOL)flag
	 * @Description : Indicates whether the receiver requires all archived classes to conform to NSSecureCoding.
	 * @param flag YES if the receiver requires NSSecureCoding; NO if not.
	 * @Discussion If you set the receiver to require secure coding, it will throw an exception if you attempt to archive a class which does
	 *             not conform to NSSecureCoding. Note that the getter is on the superclass, NSCoder. See NSCoder for more information about
	 *             secure coding.
	 **/
	
	public void setRequiresSecureCoding(boolean flag) {
		secureCoding = flag;
	}

	// Encoding Data and Objects

	@Override
	/**
	 * @Signature: encodeBool:forKey:
	 * @Declaration : - (void)encodeBool:(BOOL)boolv forKey:(NSString *)key
	 * @Description : Encodes a given Boolean value and associates it with a given key.
	 * @param boolv The value to encode.
	 * @param key The key with which to associate boolv. This value must not be nil.
	 **/
	
	public void encodeBoolForKey(boolean boolv, NSString key) {
		addObjects(boolv, key);
	}

	@Override
	/**
	 * @Signature: encodeBytes:length:forKey:
	 * @Declaration : - (void)encodeBytes:(const uint8_t *)bytesp length:(NSUInteger)lenv forKey:(NSString *)key
	 * @Description : Encodes a given number of bytes from a given C array of bytes and associates them with the a given key.
	 * @param bytesp A C array of bytes to encode.
	 * @param lenv The number of bytes from bytesp to encode.
	 * @param key The key with which to associate the encoded value. This value must not be nil.
	 **/
	
	public void encodeBytesLengthForKey(int[] bytesp, int lenv, NSString key) {
		addObjects(bytesp, key);
	}

	/**
	 * @Signature: encodeConditionalObject:forKey:
	 * @Declaration : - (void)encodeConditionalObject:(id)objv forKey:(NSString *)key
	 * @Description : Encodes a reference to a given object and associates it with a given key only if it has been unconditionally encoded
	 *              elsewhere in the archive with encodeObject:forKey:.
	 * @param objv The object to encode.
	 * @param key The key with which to associate the encoded value. This value must not be nil.
	 **/
	@Override
	
	public void encodeConditionalObjectForKey(NSObject objv, NSString key) {
		addObjects(objv, key);
	}

	/**
	 * @Signature: encodeDouble:forKey:
	 * @Declaration : - (void)encodeDouble:(double)realv forKey:(NSString *)key
	 * @Description : Encodes a given double value and associates it with a given key.
	 * @param realv The value to encode.
	 * @param key The key with which to associate realv. This value must not be nil.
	 **/
	@Override
	
	public void encodeDoubleForKey(double realv, NSString key) {
		addObjects(realv, key);
	}

	/**
	 * @Signature: encodeFloat:forKey:
	 * @Declaration : - (void)encodeFloat:(float)realv forKey:(NSString *)key
	 * @Description : Encodes a given float value and associates it with a given key.
	 * @param realv The value to encode.
	 * @param key The key with which to associate realv. This value must not be nil.
	 **/
	@Override
	
	public void encodeFloatForKey(float realv, NSString key) {
		addObjects(realv, key);
	}

	/**
	 * @Signature: encodeInt:forKey:
	 * @Declaration : - (void)encodeInt:(int)intv forKey:(NSString *)key
	 * @Description : Encodes a given int value and associates it with a given key.
	 * @param intv The value to encode.
	 * @param key The key with which to associate intv. This value must not be nil.
	 **/
	
	@Override
	public void encodeIntForKey(int intv, NSString key) {
		addObjects(intv, key);
	}

	/**
	 * @Signature: encodeInt32:forKey:
	 * @Declaration : - (void)encodeInt32:(int32_t)intv forKey:(NSString *)key
	 * @Description : Encodes a given 32-bit integer value and associates it with a given key.
	 * @param intv The value to encode.
	 * @param key The key with which to associate intv. This value must not be nil.
	 **/
	@Override
	
	public void encodeInt32ForKey(int intv, NSString key) {
		addObjects(intv, key);
	}

	/**
	 * @Signature: encodeInt64:forKey:
	 * @Declaration : - (void)encodeInt64:(int64_t)intv forKey:(NSString *)key
	 * @Description : Encodes a given 64-bit integer value and associates it with a given key.
	 * @param intv The value to encode.
	 * @param key The key with which to associate intv. This value must not be nil.
	 **/
	@Override
	
	public void encodeInt64ForKey(int intv, NSString key) {
		addObjects(intv, key);
	}

	/**
	 * @Signature: encodeObject:forKey:
	 * @Declaration : - (void)encodeObject:(id)objv forKey:(NSString *)key
	 * @Description : Encodes a given object and associates it with a given key.
	 * @param objv The value to encode. This value may be nil.
	 * @param key The key with which to associate objv. This value must not be nil.
	 **/
	
	@Override
	public void encodeObjectForKey(NSObject objv, NSString key) {
		addObjects(objv, key);

	}

	// Managing Delegates

	/**
	 * @Signature: delegate
	 * @Declaration : - (id<NSKeyedArchiverDelegate>)delegate
	 * @Description : Returns the receiver’s delegate.
	 * @return Return Value The receiver's delegate.
	 **/
	
	public Object delegate() {
		return delegate;
	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id<NSKeyedArchiverDelegate>)delegate
	 * @Description : Sets the delegate for the receiver.
	 * @param delegate The delegate for the receiver.
	 **/
	
	public void setDelegate(Object delegate) {
		this.delegate = (NSKeyedArchiverDelegate) delegate;
	}

	// Managing Classes and Class Names

	/**
	 * @Signature: setClassName:forClass:
	 * @Declaration : + (void)setClassName:(NSString *)codedName forClass:(Class)cls
	 * @Description : Adds a class translation mapping to NSKeyedArchiver whereby instances of of a given class are encoded with a given
	 *              class name instead of their real class names.
	 * @param codedName The name of the class that NSKeyedArchiver uses in place of cls.
	 * @param cls The class for which to set up a translation mapping.
	 * @Discussion When encoding, the class’s translation mapping is used only if no translation is found first in an instance’s separate
	 *             translation map.
	 **/
	
	public static void setClassNameForClass(NSString codedName, Class cls) {
		NSKeyedArchiver.cls = cls;
		NSKeyedArchiver.codedName = codedName;
	}

	/**
	 * - (NSString *)classNameForClass:(Class)cls
	 *
	 * @Description : Returns the class name with which the receiver encodes instances of a given class.
	 * @param cls The class for which to determine the translation mapping.
	 * @return Return Value The class name with which the receiver encodes instances of cls. Returns nil if the receiver does not have a
	 *         translation mapping for cls. The class’s separate translation map is not searched.
	 */
	
	public static NSString classNameForClass(Class cls) {
		return NSString.stringWithString(new NSString(NSKeyedArchiver.cls.getName()));
	}

	/**
	 * @Signature: setClassName:forClass:
	 * @Declaration : + (void)setClassName:(NSString *)codedName forClass:(Class)cls
	 * @Description : Adds a class translation mapping to NSKeyedArchiver whereby instances of of a given class are encoded with a given
	 *              class name instead of their real class names.
	 * @param codedName The name of the class that NSKeyedArchiver uses in place of cls.
	 * @param cls The class for which to set up a translation mapping.
	 * @Discussion When encoding, the class’s translation mapping is used only if no translation is found first in an instance’s separate
	 *             translation map.
	 **/
	
	public void _setClassNameForClass(NSString codedName, Class cls) {
		NSKeyedArchiver.cls = cls;
		NSKeyedArchiver.codedName = codedName;
	}

	/**
	 * @Signature: classNameForClass:
	 * @Declaration : + (NSString *)classNameForClass:(Class)cls
	 * @Description : Returns the class name with which NSKeyedArchiver encodes instances of a given class.
	 * @param cls The class for which to determine the translation mapping.
	 * @return Return Value The class name with which NSKeyedArchiver encodes instances of cls. Returns nil if NSKeyedArchiver does not have
	 *         a translation mapping for cls.
	 **/
	
	public NSString _classNameForClass(Class cls) {
		return NSString.stringWithString(new NSString(NSKeyedArchiver.cls.getName()));
	}

	@Override
	public void setObjectZone(NSZone zone) {

		OutputStream outFile;
		try {
			outFile = new FileOutputStream("mFile");
			XmlSerializer xs = Xml.newSerializer();
			OutputStream sw = new ObjectOutputStream(outFile);

			xs.setOutput(sw, Encoding.UTF_8.name());
		} catch (FileNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	// Utils
	public void encodeObjects(Object obj) {

		if (outFormat == NSPropertyList.NSPropertyListBinaryFormat_v1_0) {
			// binary
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ObjectOutputStream o;
			ObjectInputStream ob;
			try {
				o = new ObjectOutputStream(b);
				o.writeObject(obj);
				o.flush();
				o.close();
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
			mData.appendBytesLength(b.toByteArray(), b.toByteArray().length);
		} else {

			PList plist = new PList();
			try {
				String xml = plist.encode(mapValues);
				byte[] b = xml.getBytes();
				mData.appendBytesLength(b, b.length);
			} catch (Exception e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
		}
	}

	public void addObjects(Object obj, NSString key) {
		if (secureCoding) {
			if (obj instanceof NSSecureCoding) {
				mapValues.put(key.getWrappedString(), obj);
			} else {
				throw new IllegalArgumentException("");
			}
		} else {
			mapValues.put(key.getWrappedString(), obj);
		}
	}

	@Override
	public boolean getAllowsKeyedCoding() {

		return false;
	}

	@Override
	public boolean containsValueForKey(NSString key) {

		return false;
	}

	@Override
	public void encodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address) {

	}

	@Override
	public void encodeBycopyObject(NSObject object) {

	}

	@Override
	public void encodeByrefObject(NSObject object) {

	}

	@Override
	public void encodeBytesLength(NSObject address, int numBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeConditionalObject(NSObject object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeDataObject(NSData data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeIntegerForKey(int intv, NSString key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeObject(NSObject object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeRootObject(NSObject rootObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeValueOfObjCTypeAt(char[] valueType, NSObject address) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeValuesOfObjCTypes(char[]... valueTypes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean decodeBoolForKey(NSString key) {

		return false;
	}

	@Override
	public byte[] decodeBytesForKeyReturnedLength(NSString key, int[] lengthp) {
		byte[] emptyArray = new byte[0];
		return emptyArray;
	}

	@Override
	public void decodeBytesWithReturnedLength(int numBytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NSData decodeDataObject() {

		return null;
	}

	@Override
	public double decodeDoubleForKey(NSString key) {

		return 0;
	}

	@Override
	public float decodeFloatForKey(NSString key) {

		return 0;
	}

	@Override
	public int decodeIntForKey(NSString key) {

		return 0;
	}

	@Override
	public int decodeIntegerForKey(NSString key) {

		return 0;
	}

	@Override
	public int decodeInt32ForKey(NSString key) {

		return 0;
	}

	@Override
	public int decodeInt64ForKey(NSString key) {

		return 0;
	}

	@Override
	public NSObject decodeObject() {

		return null;
	}

	@Override
	public NSObject decodeObjectForKey(NSString key) {

		return null;
	}

	@Override
	public void decodeValueOfObjCTypeAt(char[] valueType, NSObject data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void decodeValuesOfObjCTypes(char[] valueTypes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NSObject decodeObjectOfClassForKey(Class<?> aClass, NSString key) {

		return null;
	}

	@Override
	public void decodeObjectOfClassesForKey(NSSet<?> classes, NSString key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public NSObject decodePropertyListForKey(NSString key) {

		return null;
	}

	@Override
	public boolean getRequiresSecureCoding() {

		return false;
	}

	@Override
	public NSSet<?> getAllowedClasses() {

		return null;
	}

	@Override
	public int getSystemVersion() {

		return 0;
	}

	@Override
	public int versionForClassName(NSString className) {

		return 0;
	}

	@Override
	public NSZone objectZone() {

		return null;
	}

	@Override
	public void encodeBoolean(boolean paramBoolean) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeByte(byte paramByte) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeBytes(byte[] paramArrayOfByte) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeChar(char paramChar) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeShort(short paramShort) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeInt(int paramInt) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeLong(long paramLong) {

	}

	@Override
	public void encodeFloat(float paramFloat) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeDouble(double paramDouble) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeObject(Object paramObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeClass(Class<?> paramClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void encodeObjects(Object... paramArrayOfObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean decodeBoolean() {

		return false;
	}

	@Override
	public byte decodeByte() {

		return 0;
	}

	@Override
	public byte[] decodeBytes() {
		byte[] emptyArray = new byte[0];
		return emptyArray;
	}

	@Override
	public char decodeChar() {

		return 0;
	}

	@Override
	public short decodeShort() {

		return 0;
	}

	@Override
	public int decodeInt() {

		return 0;
	}

	@Override
	public long decodeLong() {

		return 0;
	}

	@Override
	public float decodeFloat() {

		return 0;
	}

	@Override
	public double decodeDouble() {

		return 0;
	}

	@Override
	public Class<?> decodeClass() {

		return null;
	}

	@Override
	public Object[] decodeObjects() {
		Object[] emptyArray = new Object[0];
		return emptyArray;
	}

}