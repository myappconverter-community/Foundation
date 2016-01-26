
package com.myappconverter.java.foundations;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import android.util.Log;

import com.myappconverter.java.coregraphics.CGPoint;
import com.myappconverter.java.coregraphics.CGRect;
import com.myappconverter.java.coregraphics.CGSize;
import com.myappconverter.java.coregraphics.CGVector;
import com.myappconverter.java.foundations.protocols.NSKeyedUnarchiverDelegate;


public class NSKeyedUnarchiver extends NSCoder {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private static NSString codedName;
	private static Class cls;
	private boolean secureCoding = false;
	private int initSize = 0;
	private NSData mData;
	private NSKeyedUnarchiverDelegate delegate;
	private Map<String, Object> mapValues = new Hashtable<String, Object>();

	// Initializing a Keyed Unarchiver

	public Map<String, Object> getMapValues() {
		return mapValues;
	}

	public void setMapValues(Map<String, Object> mapValues) {
		this.mapValues = mapValues;
	}

	public NSData getmData() {
		return mData;
	}

	public void setmData(NSData mData) {
		this.mData = mData;
	}

	public NSKeyedUnarchiver() {
		super();
		this.mData = new NSData();
	}

	public NSKeyedUnarchiver(NSData mData) {
		super();
		this.mData = mData;
	}

	/**
	 * @Signature: initForReadingWithData:
	 * @Declaration : - (id)initForReadingWithData:(NSData *)data
	 * @Description : Initializes the receiver for decoding an archive previously encoded by NSKeyedArchiver.
	 * @param data An archive previously encoded by NSKeyedArchiver.
	 * @return Return Value An NSKeyedUnarchiver object initialized for for decoding data.
	 * @Discussion When you finish decoding data, you should invoke finishDecoding. This method throws an exception if data is not a valid
	 *             archive.
	 **/
	
	public Object initForReadingWithData(NSData data) {
		return new NSKeyedUnarchiver(data);
	}

	// Unarchiving Data

	/**
	 * @Signature: unarchiveObjectWithData:
	 * @Declaration : + (id)unarchiveObjectWithData:(NSData *)data
	 * @Description : Decodes and returns the object graph previously encoded by NSKeyedArchiver and stored in a given NSData object.
	 * @param data An object graph previously encoded by NSKeyedArchiver.
	 * @return Return Value The object graph previously encoded by NSKeyedArchiver and stored in data.
	 * @Discussion This method raises an NSInvalidArchiveOperationException if data is not a valid archive.
	 **/
	
	public static Object unarchiveObjectWithData(NSData data) {
		Map<String, Object> map = decodeObjects(data);
		if (map != null) {
			map.get(new NSString("root"));
		}
		return null;
		// TODO check that
	}

	/**
	 *
	 * sks methods
	 */
	private static boolean isNumber(String str) {
		char[] tabChar = str.toCharArray();
		if (tabChar[0] == '{')
			return true;
		return false;
	}

	public static CGPoint getInfor(String stringValue, int ensemble, int index) {
		CGPoint point = CGPoint.make(0, 0);
		char[] tab = stringValue.toCharArray();
		String stringValueTmp = stringValue;
		int ensembleTmp = ensemble;

		if (ensembleTmp == 2) {
			int indexE1 = 0, indexE2 = 0, num = 0;
			boolean isIn = false;
			for (int i = 0; i < tab.length; i++) {
				char c = tab[i];
				if (c == '{' && isIn == false) {
					indexE1 = i;
					num++;
				}
				if (c == '}' && isIn == true) {
					indexE2 = i;
					break;
				}

				if (index == 0 && num == 2) {
					isIn = true;

				} else if (num == 3)
					isIn = true;

			}
			stringValueTmp = stringValueTmp.substring(indexE1, indexE2 + 1);
			tab = stringValueTmp.toCharArray();
			ensembleTmp = 1;
		}
		if (ensembleTmp == 1) {
			int indexX = 0, indexY = 0;
			for (int i = 1; i < tab.length; i++) {
				char c = tab[i];
				if (c == ',') {
					indexX = i;
				} else if (c == '}') {
					indexY = i;
				}
			}
			if (indexX != 0) {
				String value = stringValueTmp.substring(1, indexX);
				point.x = Float.valueOf(value);
			}
			if (indexY != 0) {
				String value = stringValueTmp.substring(indexX + 2, indexY);
				point.y = Float.valueOf(value);
			}
		}
		return point;
	}

	private static Object getObjectValueWithString(String valueS, String className) {
		if ("CGVector".equals(className)) {
			CGPoint point = getInfor(valueS, 1, -1);
			return new CGVector(point.x, point.y);
		}
		return null;
	}

	public Object getValueWithDictionary(String name, String className, NSDictionary dict) {

		for (Object objKey : dict.keySet()) {
			Object key = null;
			if (objKey instanceof String)
				key = objKey;
			else if (objKey instanceof NSString)
				key = ((NSString) objKey).getWrappedString();
			if (key != null && key.equals(name)) {

				Object objValue = dict.get(objKey);
				if (objValue instanceof NSUUID) {
					Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
					if (uuid != 0 && this.dictObjects.containsKey(uuid)) {
						Object objt = this.dictObjects.get(uuid);
						if (objt.getClass().getSimpleName().equals(className)) {
							return objt;
						} else {
							if (objt instanceof NSString
									&& isNumber(((NSString) objt).getWrappedString())) {

								return getObjectValueWithString(
										((NSString) objt).getWrappedString(), className);

							}
						}

					}
					return null;
				} else if (objValue instanceof NSNumber) {
					if ("Boolean".equals(className))
						return Boolean.valueOf(((NSNumber) objValue).getNumberValue());
					if ("Integer".equals(className))
						return ((NSNumber) objValue).longValue();
					if ("Float".equals(className))
						return Float.valueOf(((NSNumber) objValue).getNumberValue());
					return 0;
				}
			}

		}
		return null;
	}

	public static int getUuidWithDictionary(String keyName, NSDictionary dict) {
		Object key = null;
		if (dict.containsKey(keyName))
			key = keyName;
		else if (dict.containsKey(new NSString(keyName)))
			key = new NSString(keyName);
		if (key != null) {
			Object objValue = dict.get(key);
			if (objValue instanceof NSUUID) {
				Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
				return uuid;
			}
		}
		return 0;
	}

	public NSArray arrayValue;
	private Map dictObjects = new HashMap<Integer, Object>();

	private void chargeDictionary(NSDictionary<Object, Object> dic) {
		for (Object objKey : dic.keySet()) {
			Object objD = dic.objectForKey(objKey);
			if (objD instanceof NSArray) {
				arrayValue = (NSArray) objD;
				List<Object> list = arrayValue.getWrappedList();
				int i = -1;
				for (Object obj : list) {
					i++;
					if (obj instanceof NSDictionary) {
						constructObject((NSDictionary<Object, Object>) obj, i);
					} else {
						if (obj instanceof NSNumber) {
							Object objV = null;
							if (((NSNumber) obj).type() == NSNumber.BOOLEAN)
								objV = Boolean.valueOf(((NSNumber) obj).getNumberValue());
							if (((NSNumber) obj).type() == NSNumber.INTEGER)
								objV = ((NSNumber) obj).longValue();
							if (((NSNumber) obj).type() == NSNumber.REAL)
								objV = Float.valueOf(((NSNumber) obj).getNumberValue());
							dictObjects.put(i, objV);
						} else if (obj instanceof NSString) {
							if (!isNumber(((NSString) obj).getWrappedString()))
								dictObjects.put(i, obj);
							else {
								dictObjects.put(i, obj);
							}
						} else if (obj instanceof NSData) {
							dictObjects.put(i, obj);
						}
					}
				}
				NSDictionary<Object, Object> diction = new NSDictionary<>();
				diction.setWrappedDictionary(dictObjects);
			}
		}
	}

	private void constructObject(NSDictionary<Object, Object> dict, int id) {
		for (Object objKey : dict.keySet()) {
			if (dictObjects.containsKey(id))
				break;
			String key = null;
			if (objKey instanceof String)
				key = (String) objKey;
			else if (objKey instanceof NSString)
				key = ((NSString) objKey).getWrappedString();
			if (key != null) {
				if (Pattern.quote(key).equals(Pattern.quote("$class"))) {
					Object objValue = dict.get(objKey);
					if (objValue instanceof NSUUID) {
						Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
						if (uuid != 0) {
							if (dictObjects.containsKey(uuid)) {
								dictObjects.put(id, dictObjects.get(uuid));
							} else {
								Object uuidValue = arrayValue.getWrappedList().get(uuid);
								if (uuidValue instanceof NSDictionary) {
									constructObject((NSDictionary<Object, Object>) uuidValue, uuid);
									if (dictObjects.containsKey(uuid)) {
										dictObjects.put(id, dictObjects.get(uuid));
									}
								}
							}
						}
					}
				} else if (Pattern.quote(key).equals(Pattern.quote("$classname"))) {
					Object objV = null;
					NSString objValue = (NSString) dict.get(objKey);
					Class<?> clazz = null;
					if (!"NSValue".equals(objValue.getWrappedString())) {
						if ("PKPhysicsWorld".equals(objValue.getWrappedString()))
							objValue.setWrappedString("SKPhysicsWorld");
						try {
							clazz = Class.forName("com.myappconverter.java.spritekit."
									+ objValue.getWrappedString());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							LOGGER.info(e.getMessage());
						}
						if (clazz == null) {
							try {
								clazz = Class.forName("com.myappconverter.java.foundations."
										+ objValue.getWrappedString());
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								LOGGER.info(e.getMessage());
							}
						}
						if (clazz != null)
							try {
								objV = clazz.newInstance();
							} catch (InstantiationException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								LOGGER.info(e.getMessage());
							}
					}
					if (objV != null)
						dictObjects.put(id, objV);
				} else {// for all struct wrapped in NSValue
					if ("NS.pointval".equals(key)) {// NS.objects, NS.keys, NS.special, NS.sizeval (NS.special = 2), NS.rectval (NS.special
						// = 3), NS.pointval (NS.special = 1)
						if (dictObjects.containsKey(id))
							if (dictObjects.get(id) instanceof NSValue)
								;

						Object objValue = dict.get(objKey);
						if (objValue instanceof NSUUID) {
							Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
							CGPoint point;
							if (uuid == 0)
								point = null;
							else
								point = getInfor(((NSString) arrayValue.getWrappedList().get(uuid))
										.getWrappedString(), 1, -1);
							dictObjects.put(id, point);
						}

					} else if ("NS.sizeval".equals(key)) {

						Object objValue = dict.get(objKey);
						if (objValue instanceof NSUUID) {
							Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
							CGPoint point;
							if (uuid == 0)
								dictObjects.put(id, null);
							else {
								point = getInfor(((NSString) arrayValue.getWrappedList().get(uuid))
										.getWrappedString(), 1, -1);
								CGSize size = new CGSize();
								size.width = point.x;
								size.height = point.y;
								dictObjects.put(id, size);
							}
						}

					} else if ("NS.rectval".equals(key)) {

						Object objValue = dict.get(objKey);
						if (objValue instanceof NSUUID) {
							Integer uuid = Integer.valueOf(((NSUUID) objValue).getName());
							if (uuid == 0)
								dictObjects.put(id, null);
							else {
								CGRect rect = new CGRect();
								CGPoint point = getInfor(
										((NSString) arrayValue.getWrappedList().get(uuid))
												.getWrappedString(),
										2, 0);
								rect.origin = point;
								point = getInfor(((NSString) arrayValue.getWrappedList().get(uuid))
										.getWrappedString(), 2, 1);
								rect.size.width = point.x;
								rect.size.height = point.y;
								dictObjects.put(id, rect);
							}

						}

					}
				}
			}
		}

	}

	/**
	 * @Signature: unarchiveObjectWithFile:
	 * @Declaration : + (id)unarchiveObjectWithFile:(NSString *)path
	 * @Description : Decodes and returns the object graph previously encoded by NSKeyedArchiver written to the file at a given path.
	 * @param path A path to a file that contains an object graph previously encoded by NSKeyedArchiver.
	 * @return Return Value The object graph previously encoded by NSKeyedArchiver written to the file path. Returns nil if there is no file
	 *         at path.
	 * @Discussion This method raises an NSInvalidArgumentException if the file at path does not contain a valid archive.
	 **/
	
	public static Object unarchiveObjectWithFile(NSString path) {
		if (".sks".equals(path.getWrappedString().substring(path.getWrappedString().length() - 4))) {
			NSDictionary<Object, Object> dico = NSDictionary.dictionaryWithContentsOfFile(path);
			NSKeyedUnarchiver unarchiver = new NSKeyedUnarchiver();
			unarchiver.chargeDictionary(dico);
			int indexOfObject = unarchiver.arrayValue.getWrappedList().size() - 1;
			Object obj = null;
			obj = unarchiver.dictObjects.get(indexOfObject);
			// obj.createWithArchiveFile(unarchiver);
			Method m = null;
			;
			try {
				m = obj.getClass().getMethod("createWithArchiveFile", obj.getClass().getClass(),
						unarchiver.getClass());
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				LOGGER.info(e.getMessage());
			}

			// TODO Auto-generated catch block

			try {
				m.invoke(obj, obj.getClass(), unarchiver);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				LOGGER.info(e.getMessage());
			}
			return obj;

		} else {
			Hashtable<NSString, Object> mv = new Hashtable<NSString, Object>();
			ObjectInputStream inputStream = null;
			try {
				inputStream = new ObjectInputStream(new FileInputStream(path.getWrappedString()));
				mv = (Hashtable<NSString, Object>) inputStream.readObject();
				inputStream.close();
				return mv.get(new NSString("root"));
			} catch (StreamCorruptedException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (FileNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (IOException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			} catch (ClassNotFoundException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
						+ Log.getStackTraceString(e));
			}
			return null;
			// TODO check that
		}
	}

	/**
	 * @Signature: setRequiresSecureCoding:
	 * @Declaration : - (void)setRequiresSecureCoding:(BOOL)flag
	 * @Description : Indicates whether the receiver requires all unarchived classes to conform to NSSecureCoding.
	 * @param flag YES if the receiver requires NSSecureCoding; NO if not.
	 * @Discussion If you set the receiver to require secure coding, it will throw an exception if you attempt to unarchive a class which
	 *             does not conform to NSSecureCoding. The secure coding requirement for NSKeyedUnarchiver is designed to be set once at the
	 *             top level and remain on. Once enabled, attempting to call setRequiresSecureCoding: with a value of NO will throw an
	 *             exception. This is to prevent classes from selectively turning secure coding off. Note that the getter is on the
	 *             superclass, NSCoder. See NSCoder for more information about secure coding.
	 **/
	
	public void setRequiresSecureCoding(boolean flag) {
		secureCoding = flag;
	}

	// Managing Delegates

	/**
	 * @Signature: delegate
	 * @Declaration : - (id<NSKeyedUnarchiverDelegate>)delegate
	 * @Description : Returns the receiver’s delegate.
	 * @return Return Value The receiver’s delegate.
	 **/
	
	public Object delegate() {
		return delegate;
	}

	/**
	 * @Signature: setDelegate:
	 * @Declaration : - (void)setDelegate:(id<NSKeyedUnarchiverDelegate>)delegate
	 * @Description : Sets the receiver’s delegate.
	 * @param delegate The delegate for the receiver.
	 **/
	
	public void setDelegate(Object delegate) {
		this.delegate = (NSKeyedUnarchiverDelegate) delegate;
	}

	// Managing Classes and Class Names

	/**
	 * @Signature: setClass:forClassName:
	 * @Declaration : + (void)setClass:(Class)cls forClassName:(NSString *)codedName
	 * @Description : Adds a class translation mapping to NSKeyedUnarchiver whereby objects encoded with a given class name are decoded as
	 *              instances of a given class instead.
	 * @param cls The class with which to replace instances of the class named codedName.
	 * @param codedName The ostensible name of a class in an archive.
	 * @Discussion When decoding, the class’s translation mapping is used only if no translation is found first in an instance’s separate
	 *             translation map.
	 **/
	
	public static void setClassForClassName(Class cls, NSString codedName) {
		NSKeyedUnarchiver.cls = cls;
		NSKeyedUnarchiver.codedName = codedName;
	}

	/**
	 * - (Class)classForClassName:(NSString *)codedName
	 *
	 * @Description : Returns the class from which the receiver instantiates an encoded object with a given class name.
	 * @param codedName The name of a class.
	 * @return Return Value The class from which the receiver instantiates an encoded object with the class name codedName. Returns nil if
	 *         the receiver does not have a translation mapping for codedName.
	 * @Discussion The class’s separate translation map is not searched.
	 */
	
	public Class classForClassName(NSString codedName) {
		return NSKeyedUnarchiver.cls;
	}

	
	public void _setClassForClassName(Class cls, NSString codedName) {
		NSKeyedUnarchiver.cls = cls;
		NSKeyedUnarchiver.codedName = codedName;
	}

	/**
	 * + (Class)classForClassName:(NSString *)codedName
	 *
	 * @Description : Returns the class from which NSKeyedUnarchiver instantiates an encoded object with a given class name.
	 * @param codedName The ostensible name of a class in an archive.
	 * @return Return Value The class from which NSKeyedUnarchiver instantiates an object encoded with the class name codedName. Returns nil
	 *         if NSKeyedUnarchiver does not have a translation mapping for codedName.
	 */
	
	public static Class _classForClassName(NSString codedName) {
		return NSKeyedUnarchiver.cls;
	}

	// Decoding Data

	/**
	 * @Signature: containsValueForKey:
	 * @Declaration : - (BOOL)containsValueForKey:(NSString *)key
	 * @Description : Returns a Boolean value that indicates whether the archive contains a value for a given key within the current
	 *              decoding scope.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value YES if the archive contains a value for key within the current decoding scope, otherwise NO.
	 **/
	
	@Override
	public boolean containsValueForKey(NSString key) {
		return mapValues.containsKey(key.getWrappedString());
	}

	/**
	 * @Signature: decodeBoolForKey:
	 * @Declaration : - (BOOL)decodeBoolForKey:(NSString *)key
	 * @Description : Decodes a Boolean value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The Boolean value associated with the key key. Returns NO if key does not exist.
	 **/
	
	@Override
	public boolean decodeBoolForKey(NSString key) {
		return (Boolean) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * @Signature: decodeBytesForKey:returnedLength:
	 * @Declaration : - (const uint8_t *)decodeBytesForKey:(NSString *)key returnedLength:(NSUInteger *)lengthp
	 * @Description : Decodes a stream of bytes associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @param lengthp Upon return, contains the number of bytes returned.
	 * @return Return Value The stream of bytes associated with the key key. Returns NULL if key does not exist.
	 * @Discussion The returned value is a pointer to a temporary buffer owned by the receiver. The buffer goes away with the unarchiver,
	 *             not the containing autoreleasepool block. You must copy the bytes into your own buffer if you need the data to persist
	 *             beyond the life of the receiver.
	 **/
	
	@Override
	public byte[] decodeBytesForKeyReturnedLength(NSString key, int[] lengthp) {
		return (byte[]) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * @Signature: decodeDoubleForKey:
	 * @Declaration : - (double)decodeDoubleForKey:(NSString *)key
	 * @Description : Decodes a double-precision floating-point value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The double-precision floating-point value associated with the key key. Returns 0.0 if key does not exist.
	 * @Discussion If the archived value was encoded as single-precision, the type is coerced.
	 **/
	
	@Override
	public double decodeDoubleForKey(NSString key) {
		return (Double) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * - (float)decodeFloatForKey:(NSString *)key
	 *
	 * @Description : Decodes a single-precision floating-point value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The single-precision floating-point value associated with the key key. Returns 0.0 if key does not exist.
	 * @Discussion If the archived value was encoded as double precision, the type is coerced, loosing precision. If the archived value is
	 *             too large for single precision, the method raises an NSRangeException.
	 */
	
	@Override
	public float decodeFloatForKey(NSString key) {
		return (Float) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * - (int)decodeIntForKey:(NSString *)key
	 *
	 * @Description : Decodes an integer value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The integer value associated with the key key. Returns 0 if key does not exist.
	 * @Discussion If the archived value was encoded with a different size but is still an integer, the type is coerced. If the archived
	 *             value is too large to fit into the default size for an integer, the method raises an NSRangeException.
	 */
	
	@Override
	public int decodeIntForKey(NSString key) {
		return (Integer) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * @Signature: decodeInt32ForKey:
	 * @Declaration : - (int32_t)decodeInt32ForKey:(NSString *)key
	 * @Description : Decodes a 32-bit integer value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The 32-bit integer value associated with the key key. Returns 0 if key does not exist.
	 * @Discussion If the archived value was encoded with a different size but is still an integer, the type is coerced. If the archived
	 *             value is too large to fit into a 32-bit integer, the method raises an NSRangeException.
	 **/
	
	@Override
	public int decodeInt32ForKey(NSString key) {
		return (Integer) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * @Signature: decodeInt64ForKey:
	 * @Declaration : - (int64_t)decodeInt64ForKey:(NSString *)key
	 * @Description : Decodes a 64-bit integer value associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The 64-bit integer value associated with the key key. Returns 0 if key does not exist.
	 * @Discussion If the archived value was encoded with a different size but is still an integer, the type is coerced.
	 **/
	
	@Override
	public int decodeInt64ForKey(NSString key) {
		return (Integer) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * @Signature: decodeObjectForKey:
	 * @Declaration : - (id)decodeObjectForKey:(NSString *)key
	 * @Description : Decodes and returns an object associated with a given key.
	 * @param key A key in the archive within the current decoding scope. key must not be nil.
	 * @return Return Value The object associated with the key key. Returns nil if key does not exist, or if the value for key is nil.
	 **/
	
	@Override
	public NSObject decodeObjectForKey(NSString key) {
		return (NSObject) this.mapValues.get(key.getWrappedString());
	}

	/**
	 * - (void)finishDecoding
	 *
	 * @Description : Tells the receiver that you are finished decoding objects.
	 * @Discussion Invoking this method allows the receiver to notify its delegate and to perform any final operations on the archive. Once
	 *             this method is invoked, the receiver cannot decode any further values.
	 */
	
	public void finishDecoding() {
		this.mapValues = decodeObjects(this.mData);
	}

	public static Hashtable<String, Object> decodeObjects(NSData data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data.getWrappedData());
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(in);
			Object obj = is.readObject();
			Hashtable<String, Object> mapValues = (Hashtable<String, Object>) obj;
			return mapValues;
		} catch (StreamCorruptedException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IOException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	@Override
	public boolean getAllowsKeyedCoding() {

		return false;
	}

	@Override
	public void encodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address) {

	}

	@Override
	public void encodeBoolForKey(boolean boolv, NSString key) {

	}

	@Override
	public void encodeBycopyObject(NSObject object) {

	}

	@Override
	public void encodeByrefObject(NSObject object) {

	}

	@Override
	public void encodeBytesLength(NSObject address, int numBytes) {

	}

	@Override
	public void encodeBytesLengthForKey(int[] bytesp, int lenv, NSString key) {

	}

	@Override
	public void encodeConditionalObject(NSObject object) {

	}

	@Override
	public void encodeConditionalObjectForKey(NSObject objv, NSString key) {

	}

	@Override
	public void encodeDataObject(NSData data) {

	}

	@Override
	public void encodeDoubleForKey(double realv, NSString key) {

	}

	@Override
	public void encodeFloatForKey(float realv, NSString key) {

	}

	@Override
	public void encodeIntForKey(int intv, NSString key) {

	}

	@Override
	public void encodeIntegerForKey(int intv, NSString key) {

	}

	@Override
	public void encodeInt32ForKey(int intv, NSString key) {

	}

	@Override
	public void encodeInt64ForKey(int intv, NSString key) {

	}

	@Override
	public void encodeObject(NSObject object) {

	}

	@Override
	public void encodeObjectForKey(NSObject objv, NSString key) {

	}

	@Override
	public void encodeRootObject(NSObject rootObject) {

	}

	@Override
	public void encodeValueOfObjCTypeAt(char[] valueType, NSObject address) {

	}

	@Override
	public void encodeValuesOfObjCTypes(char[]... valueTypes) {

	}

	@Override
	public void decodeArrayOfObjCTypeCountAt(char[] itemType, int count, NSObject address) {

	}

	@Override
	public void decodeBytesWithReturnedLength(int numBytes) {

	}

	@Override
	public NSData decodeDataObject() {

		return null;
	}

	@Override
	public int decodeIntegerForKey(NSString key) {

		return 0;
	}

	@Override
	public NSObject decodeObject() {

		return null;
	}

	@Override
	public void decodeValueOfObjCTypeAt(char[] valueType, NSObject data) {

	}

	@Override
	public void decodeValuesOfObjCTypes(char[] valueTypes) {

	}

	@Override
	public NSObject decodeObjectOfClassForKey(Class<?> aClass, NSString key) {

		return null;
	}

	@Override
	public void decodeObjectOfClassesForKey(NSSet<?> classes, NSString key) {

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
	public void setObjectZone(NSZone zone) {

	}

	@Override
	public void encodeBoolean(boolean paramBoolean) {

	}

	@Override
	public void encodeByte(byte paramByte) {

	}

	@Override
	public void encodeBytes(byte[] paramArrayOfByte) {

	}

	@Override
	public void encodeChar(char paramChar) {

	}

	@Override
	public void encodeShort(short paramShort) {

	}

	@Override
	public void encodeInt(int paramInt) {

	}

	@Override
	public void encodeLong(long paramLong) {

	}

	@Override
	public void encodeFloat(float paramFloat) {

	}

	@Override
	public void encodeDouble(double paramDouble) {

	}

	@Override
	public void encodeObject(Object paramObject) {

	}

	@Override
	public void encodeClass(Class<?> paramClass) {

	}

	@Override
	public void encodeObjects(Object... paramArrayOfObject) {

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

		return null;
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
		return null;
	}

}