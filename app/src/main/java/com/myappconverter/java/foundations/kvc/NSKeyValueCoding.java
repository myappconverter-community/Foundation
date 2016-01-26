package com.myappconverter.java.foundations.kvc;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSMutableArray;
import com.myappconverter.java.foundations.NSMutableDictionary;
import com.myappconverter.java.foundations.NSMutableOrderedSet;
import com.myappconverter.java.foundations.NSMutableSet;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.functions.NSLog;
import com.myappconverter.java.foundations.kvo.NSKeyValueObserving;
import com.myappconverter.java.foundations.utilities._NSPropertyAccessor;
import com.myappconverter.java.foundations.utilities._NSStringUtilities;


public interface NSKeyValueCoding {

	// added

	public static final Null<?> NullValue = new Null<Object>();

	public Object valueForKey(String key);

	public void takeValueForKey(Object value, String key);

	public interface _KeyBindingCreation {
		public static _KeyBindingFactory defaultFactory = new _GeneratedKeyBindingCreation();

		public _KeyBinding _createKeyGetBindingForKey(String key);

		public _KeyBinding _createKeySetBindingForKey(String key);

		public _KeyBinding _keyGetBindingForKey(String s);

		public _KeyBinding _keySetBindingForKey(String s);

		public interface _KeyBindingFactory {
			public static final int MethodLookup = 0;
			public static final int UnderbarMethodLookup = 1;
			public static final int FieldLookup = 2;
			public static final int UnderbarFieldLookup = 3;
			public static final int OtherStorageLookup = 4;
			public static final int _ValueForKeyLookupOrder[] = { 0, 1, 3, 2, 4 };
			public static final int _StoredValueForKeyLookupOrder[] = { 1, 3, 2, 4, 0 };

			public _KeyBinding _createKeyGetBindingForKey(Object object, String key, int lookupOrder[]);

			public _KeyBinding _createKeySetBindingForKey(Object object, String key, int lookupOrder[]);

			public static class _BindingStorage {
				public _KeyBinding _keyGetBindings[];

				public _KeyBinding _keySetBindings[];

				public _BindingStorage() {
					_keyGetBindings = new _KeyBinding[UnderbarFieldLookup + 1];
					_keySetBindings = new _KeyBinding[UnderbarFieldLookup + 1];
				}
			}

			public static interface Callback {

				public abstract _KeyBinding _fieldKeyBinding(String s, String s1);

				public abstract _KeyBinding _methodKeyGetBinding(String s, String s1);

				public abstract _KeyBinding _methodKeySetBinding(String s, String s1);

				public abstract _KeyBinding _otherStorageBinding(String s);
			}
		}
	}

	public abstract static class ValueAccessor {
		private static final String _PackageProtectedAccessorClassName = "KeyValueCodingProtectedAccessor";
		private static final NSMutableDictionary<String, ValueAccessor> _packageNameToValueAccessorMapTable = NSMutableDictionary
				.asMutableDictionary(new ConcurrentHashMap<String, ValueAccessor>(256));

		public static void _flushCaches() {
			_packageNameToValueAccessorMapTable.removeAllObjects();
		}

		public static void setProtectedAccessorForPackageNamed(ValueAccessor valueAccessor, String packageName) {
			if (packageName == null)
				throw new IllegalArgumentException("No package name specified");
			if (valueAccessor == null) {
				throw new IllegalArgumentException((new StringBuilder()).append("No value accessor specified for package ")
						.append(packageName).toString());
			} else {
				_packageNameToValueAccessorMapTable.setObjectForKey(valueAccessor, packageName);
			}
		}

		public static ValueAccessor protectedAccessorForPackageNamed(String packageName) {
			ValueAccessor valueAccessor = _packageNameToValueAccessorMapTable.getWrappedDictionary().get(packageName);
			if (valueAccessor == null) {
				String valueAccessorClassName = _PackageProtectedAccessorClassName;
				if (packageName.length() > 0) {
					valueAccessorClassName = packageName + "." + valueAccessorClassName;
				}
				Class<?> valueAccessorClass;
				try {
					valueAccessorClass = Class.forName(valueAccessorClassName);
					if (!ValueAccessor.class.isAssignableFrom(valueAccessorClass)) {
						valueAccessorClass = null;
					}
				} catch (ClassNotFoundException exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				} catch (ClassFormatError exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				} catch (SecurityException exception) {
					NSLog._conditionallyLogPrivateException(exception);
					valueAccessorClass = null;
				}
				if (valueAccessorClass != null) {
					try {
						valueAccessor = (ValueAccessor) valueAccessorClass.newInstance();
					} catch (Exception throwable) {
						throw new IllegalStateException(
								"Cannot instantiate protected accessor of class "
										+ valueAccessorClassName
										+ " (make sure that it is a subclass of NSKeyValueCoding.ValueAccessor and has a constructor without arguments)");
					}
				}
				if (valueAccessor == null) {
					valueAccessor = _defaultValueAccessor;
				}
				_packageNameToValueAccessorMapTable.setObjectForKey(valueAccessor, packageName);
			}
			return valueAccessor != _defaultValueAccessor ? valueAccessor : null;
		}

		public static void removeProtectedAccessorForPackageNamed(String packageName) {
			if (packageName != null) {
				_packageNameToValueAccessorMapTable.setObjectForKey(_defaultValueAccessor, packageName);
			}
		}

		public static ValueAccessor _valueAccessorForClass(Class<?> objectClass) {
			String className = objectClass.getName();
			int index = className.lastIndexOf('.');
			String packageName = index <= 0 ? "" : className.substring(0, index);
			return protectedAccessorForPackageNamed(packageName);
		}

		public abstract Object fieldValue(Object obj, Field field) throws IllegalArgumentException, IllegalAccessException;

		public abstract void setFieldValue(Object obj, Field field, Object obj1) throws IllegalArgumentException, IllegalAccessException;

		public abstract Object methodValue(Object obj, Method method) throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException;

		public abstract void setMethodValue(Object obj, Method method, Object obj1) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException;

		static final ValueAccessor _defaultValueAccessor = new ValueAccessor() {

			@Override
			public Object fieldValue(Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
				return field.get(object);
			}

			@Override
			public void setFieldValue(Object object, Field field, Object value) throws IllegalArgumentException, IllegalAccessException {
				field.set(object, value);
			}

			@Override
			public Object methodValue(Object object, Method method) throws IllegalArgumentException, IllegalAccessException,
					InvocationTargetException {
				return method.invoke(object, new Object[0]);
			}

			@Override
			public void setMethodValue(Object object, Method method, Object value) throws IllegalArgumentException, IllegalAccessException,
					InvocationTargetException {
				method.invoke(object, new Object[] { value });
			}

			@Override
			public String toString() {
				return "<DEFAULT VALUE ACCESSOR>";
			}
		};
	}

	public static class _KeyBinding {
		protected static final Short _shortFalse = (short) 0;
		protected static final Short _shortTrue = (short) 1;
		protected Class<?> _targetClass;
		protected String _key;
		private int _hashCode;

		public static final int _hash(Class<?> targetClass, String key) {
			return (targetClass == null || key == null) ? 0 : 31 * targetClass.hashCode() ^ key.hashCode();
		}

		public _KeyBinding(Class<?> targetClass, String key) {
			_targetClass = targetClass;
			_key = key;
			_hashCode = _hash(targetClass, key);
		}

		public final Class<?> targetClass() {
			return _targetClass;
		}

		public final String key() {
			return _key;
		}

		@Override
		public final int hashCode() {
			return _hashCode;
		}

		public final boolean isEqualToKeyBinding(_KeyBinding otherKeyBinding) {
			if (otherKeyBinding == null)
				return false;

			if (otherKeyBinding == this)
				return true;

			return ((_targetClass == otherKeyBinding._targetClass) && (((_key == otherKeyBinding._key) || (_key
					.equals(otherKeyBinding._key)))));
		}

		public final boolean equalsNSKeyValueCoding(Class<?> targetClass, String key) {
			if (targetClass == null || key == null) {
				return false;
			}
			return ((_targetClass == targetClass) && ((_key == key) || _key.equals(key)));
		}

		@Override
		public final boolean equals(Object object) {
			return ((object instanceof _KeyBinding) ? isEqualToKeyBinding((_KeyBinding) object) : false);
		}

		public Class<?> valueType() {
			return Object.class;
		}

		public boolean isScalarProperty() {
			return false;
		}

		public Object valueInObject(Object object) {
			return Utility.handleQueryWithUnboundKey(object, this._key);
		}

		public void setValueInObject(Object value, Object object) {
			Utility.handleTakeValueForUnboundKey(object, value, this._key);
		}

		@Override
		public String toString() {
			return super.getClass().getName() + ": target class = "
					+ ((this._targetClass != null) ? this._targetClass.getName() : "<NULL>") + ", key = "
					+ ((this._key != null) ? this._key : "<NULL>");
		}
	}

	public static class _KeyBindingCache {
		private Map<_KeyBinding, _KeyBinding> _store;
		private _KeyBinding[] _storeCache = new _KeyBinding[4096];

		public _KeyBindingCache() {
			this(256);
		}

		public _KeyBindingCache(int size) {
			_store = new ConcurrentHashMap<_KeyBinding, _KeyBinding>(size);
		}

		public void clear() {
			_store.clear();
			_storeCache = new _KeyBinding[4096];
		}

		public _KeyBinding get(_KeyBinding binding) {
			return _store.get(binding);
		}

		public _KeyBinding get(Class<?> targetClass, String key) {
			_KeyBinding binding = _storeCache[_KeyBinding._hash(targetClass, key) & 0xfff];
			if (binding != null && binding.equalsNSKeyValueCoding(targetClass, key)) {
				return binding;
			}
			binding = _store.get(new _KeyBinding(targetClass, key));
			if (binding != null) {
				_storeCache[binding.hashCode() & 0xfff] = binding;
			}
			return binding;
		}

		public void put(_KeyBinding binding) {
			_store.put(binding, binding);
			_storeCache[binding.hashCode() & 0xfff] = binding;
		}

		public int size() {
			return _store.size();
		}

		public void remove(_KeyBinding binding) {
			_store.remove(binding);
			_storeCache[binding.hashCode() & 0xfff] = null;
		}
	}

	public static class _GeneratedKeyBindingCreation implements _KeyBindingCreation._KeyBindingFactory {

		@Override
		public _KeyBinding _createKeyGetBindingForKey(Object object, String key, int lookupOrder[]) {
			// return new _NSPropertyAccessor(object).bindingForKey(key);
			return _NSPropertyAccessor._createKeyBindingForKey(object, key, lookupOrder, false);
		}

		@Override
		public _KeyBinding _createKeySetBindingForKey(Object object, String key, int lookupOrder[]) {
			// return new _NSPropertyAccessor(object).bindingForKey(key);
			return _NSPropertyAccessor._createKeyBindingForKey(object, key, lookupOrder, true);
		}
	}

	public abstract static class Utility {
		@SuppressWarnings("unchecked")
		public static final <T> T nullValue() {
			return (T) NullValue;
		}

		public static Object valueForKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding)
				return ((NSKeyValueCoding) object).valueForKey(key);

			return DefaultImplementation.valueForKey(object, key);
		}

		public static void takeValueForKey(Object object, Object value, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof NSKeyValueCoding)
				((NSKeyValueCoding) object).takeValueForKey(value, key);
			else
				DefaultImplementation.takeValueForKey(object, value, key);
		}

		public static Object handleQueryWithUnboundKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof ErrorHandling)
				return ((ErrorHandling) object).handleQueryWithUnboundKey(key);

			return DefaultImplementation.handleQueryWithUnboundKey(object, key);
		}

		public static void handleTakeValueForUnboundKey(Object object, Object value, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof ErrorHandling)
				((ErrorHandling) object).handleTakeValueForUnboundKey(value, key);
			else
				DefaultImplementation.handleTakeValueForUnboundKey(object, value, key);
		}

		public static void unableToSetNullForKey(Object object, String key) {
			if (object == null)
				throw new IllegalArgumentException("Object cannot be null");
			if (object instanceof ErrorHandling)
				((ErrorHandling) object).unableToSetNullForKey(key);
			else
				DefaultImplementation.unableToSetNullForKey(object, key);
		}
	}

	public static abstract class DefaultImplementation {
		private static final _KeyBindingCache _keyGetBindings = new _KeyBindingCache(256);
		private static final _KeyBindingCache _keySetBindings = new _KeyBindingCache(256);

		public static void _flushCaches() {
			_keyGetBindings.clear();
			_keySetBindings.clear();
		}

		public static Object valueForKey(Object object, String key) {
			if (key == null)
				return null;
			if (object instanceof Map<?, ?>) {
				return MapImplementation.valueForKey((Map<?, ?>) object, key);
			}
			_KeyBinding binding = (object instanceof _KeyBindingCreation) ? ((_KeyBindingCreation) object)._keyGetBindingForKey(key)
					: _keyGetBindingForKey(object, key);
			return binding.valueInObject(object);
		}

		@SuppressWarnings("unchecked")
		public static void takeValueForKey(Object object, Object value, String key) {
			if (key == null)
				throw new IllegalArgumentException("Key cannot be null");
			if (object instanceof Map) {
				MapImplementation.takeValueForKey((Map<String, Object>) object, value, key);
				return;
			}
			_KeyBinding binding = (object instanceof _KeyBindingCreation) ? ((_KeyBindingCreation) object)._keySetBindingForKey(key)
					: _keySetBindingForKey(object, key);
			binding.setValueInObject(value, object);
		}

		private static String _identityString(Object object) {
			return "<" + object.getClass().getName() + " 0x" + Integer.toHexString(System.identityHashCode(object)) + ">";
		}

		public static Object handleQueryWithUnboundKey(Object object, String key) {
			String capitalizedKey = _NSStringUtilities.capitalizedString(key);
			throw new UnknownKeyException(_identityString(object) + " valueForKey(): lookup of unknown key: '" + key
					+ "'.\nThis class does not have an instance variable of the name " + key + " or _" + key
					+ ", nor a method of the name " + key + ", _" + key + ", get" + capitalizedKey + ", or _get" + capitalizedKey, object,
					key);
		}

		public static void handleTakeValueForUnboundKey(Object object, Object value, String key) {
			String capitalizedKey = _NSStringUtilities.capitalizedString(key);
			throw new UnknownKeyException(_identityString(object) + " takeValueForKey(): attempt to assign value to unknown key: '" + key
					+ "'.\nThis class does not have an instance variable of the name " + key + " or _" + key
					+ ", nor a method of the name set" + capitalizedKey + " or _set" + capitalizedKey, object, key);
		}

		public static void unableToSetNullForKey(Object object, String key) {
			throw new IllegalArgumentException(
					"KeyValueCoding: Failed to assign null to key '"
							+ key
							+ "' in object of class '"
							+ object.getClass().getName()
							+ "'.  If you want to handle assignments of null to properties of primitive types, implement the interface NSKeyValueCoding.ErrorHandling with 'public void unableToSetNullForKey(String key)' on your object class.");
		}

		public static _KeyBinding _keyGetBindingForKey(Object object, String key) {
			Class<?> objectClass = object.getClass();

			_KeyBinding keyBinding = _keyGetBindings.get(objectClass, key);
			if (keyBinding == null) {
				keyBinding = (object instanceof _KeyBindingCreation) ? ((_KeyBindingCreation) object)._createKeyGetBindingForKey(key)
						: _createKeyGetBindingForKey(object, key);
				if (keyBinding == null) {
					keyBinding = new _KeyBinding(objectClass, key);
				}
				_keyGetBindings.put(keyBinding);
			}
			return keyBinding;
		}

		public static _KeyBinding _keySetBindingForKey(Object object, String key) {
			Class<?> objectClass = object.getClass();

			_KeyBinding keyBinding = _keySetBindings.get(objectClass, key);
			if (keyBinding == null) {
				keyBinding = (object instanceof _KeyBindingCreation) ? ((_KeyBindingCreation) object)._createKeySetBindingForKey(key)
						: _createKeySetBindingForKey(object, key);

				if (keyBinding == null) {
					keyBinding = new _KeyBinding(objectClass, key);
				}
				_keySetBindings.put(keyBinding);
			}
			return keyBinding;
		}

		public static void _addKVOAdditionsForKey(Object object, final String key) {
			Class<?> objectClass = object.getClass();
			final _KeyBinding keyBinding = _keySetBindings.get(objectClass, key);
			if (keyBinding != null) {
				_KeyBinding kvoKeyBinding = new _KeyBinding(object.getClass(), key) {
					@Override
					public void setValueInObject(Object _value, Object _object) {
						if (NSKeyValueObserving.Utility.automaticallyNotifiesObserversForKey(_object, key)) {
							NSKeyValueObserving.Utility.willChangeValueForKey(_object, key);
							keyBinding.setValueInObject(_value, _object);
							NSKeyValueObserving.Utility.didChangeValueForKey(_object, key);
						} else {
							keyBinding.setValueInObject(_value, _object);
						}
					}

					@Override
					public Class<?> valueType() {
						return keyBinding.valueType();
					}
				};
				_keySetBindings.put(kvoKeyBinding);
			}
		}

		public static void _removeKVOAdditionsForKey(Object object, String key) {
			Class<?> objectClass = object.getClass();
			_KeyBinding keyBinding = _keySetBindings.get(objectClass, key);
			if (keyBinding == null) {
				return;
			}
			_keySetBindings.remove(keyBinding);
		}

		public static _KeyBinding _createKeyGetBindingForKey(Object object, String key) {
			return _KeyBindingCreation.defaultFactory._createKeyGetBindingForKey(object, key,
					_KeyBindingCreation._KeyBindingFactory._ValueForKeyLookupOrder);
		}

		public static _KeyBinding _createKeySetBindingForKey(Object object, String key) {
			return _KeyBindingCreation.defaultFactory._createKeySetBindingForKey(object, key,
					_KeyBindingCreation._KeyBindingFactory._ValueForKeyLookupOrder);
		}

		public static _KeyBinding _createKeyGetBindingForKey(Object object, String key, int[] lookupOrder) {
			return _KeyBindingCreation.defaultFactory._createKeyGetBindingForKey(object, key, lookupOrder);
		}

		public static _KeyBinding _createKeySetBindingForKey(Object object, String key, int[] lookupOrder) {
			return _KeyBindingCreation.defaultFactory._createKeySetBindingForKey(object, key, lookupOrder);
		}
	}

	public static abstract class MapImplementation {
		public static Object valueForKey(Map<?, ?> map, String key) {
			if (key == null) {
				return null;
			}

			Object value = map.get(key);
			if (value == null)
				if ("values".equals(key)) {
					value = map.values();
				} else if ("keySet".equals(key)) {
					value = map.keySet();
				} else {
					if ("size".equals(key))
						return map.size();
					if ("entrySet".equals(key))
						value = map.entrySet();
					else
						try {
							_KeyBinding binding = DefaultImplementation._keyGetBindingForKey(map, key);
							value = binding.valueInObject(map);
						} catch (RuntimeException e) {
						}
				}

			return value;
		}

		public static void takeValueForKey(Map<String, Object> map, Object value, String key) {
			if (key == null) {
				throw new IllegalArgumentException("Key cannot be null");
			}

			try {
				_KeyBinding binding = DefaultImplementation._keySetBindingForKey(map, key);
				binding.setValueInObject(value, map);
			} catch (Exception e) {
				map.put(key, value);
			}
		}
	}

	public static final class Null<T> implements Serializable, Cloneable {

		private static final long serialVersionUID = 3141016060373964210L;

		private Null() {

		}

		@Override
		public String toString() {
			return "<" + super.getClass().getName() + ">";
		}

		@Override
		public Object clone() {
			return this;
		}

		public Class<?> classForCoder() {
			return Null.class;
		}

		Object readResolve() {
			return NullValue;
		}
	}

	public static class UnknownKeyException extends RuntimeException {

		private static final long serialVersionUID = 6419165081704759506L;
		public static final transient String TargetObjectUserInfoKey = "NSTargetObjectUserInfoKey";
		public static final transient String UnknownUserInfoKey = "NSUnknownUserInfoKey";
		private String _key;
		private Object _object;

		protected UnknownKeyException() {
		}

		public UnknownKeyException(String message, Object object, String key) {
			super(message);
			_object = object;
			_key = key;
		}

		public Object object() {
			return _object;
		}

		public String key() {
			return _key;
		}

		@Override
		public String toString() {
			return "<" + getClass().getName() + " message '" + getMessage() + "' object '" + object() + "' key '" + key() + "'>";
		}
	}

	public interface ErrorHandling {
		public Object handleQueryWithUnboundKey(String paramString);

		public void handleTakeValueForUnboundKey(Object paramObject, String paramString);

		public void unableToSetNullForKey(String paramString);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD, ElementType.METHOD })
	public @interface Property {

	}

	// methods
	public static final NSString NSUndefinedKeyException = new NSString("NSUndefinedKeyException");
	public static final NSString NSTargetObjectUserInfoKey = new NSString("NSTargetObjectUserInfoKey");
	public static final NSString NSUnknownUserInfoKey = new NSString("NSUnknownUserInfoKey");
	public static final NSString NSAverageKeyValueOperator = new NSString("NSAverageKeyValueOperator");
	public static final NSString NSCountKeyValueOperator = new NSString("NSCountKeyValueOperator");
	public static final NSString NSDistinctUnionOfArraysKeyValueOperator = new NSString("NSDistinctUnionOfArraysKeyValueOperator");
	public static final NSString NSDistinctUnionOfObjectsKeyValueOperator = new NSString("NSDistinctUnionOfObjectsKeyValueOperator");
	public static final NSString NSDistinctUnionOfSetsKeyValueOperator = new NSString("NSDistinctUnionOfSetsKeyValueOperator");
	public static final NSString NSMaximumKeyValueOperator = new NSString("NSMaximumKeyValueOperator");
	public static final NSString NSMinimumKeyValueOperator = new NSString("NSMinimumKeyValueOperator");
	public static final NSString NSSumKeyValueOperator = new NSString("NSSumKeyValueOperator");
	public static final NSString NSUnionOfArraysKeyValueOperator = new NSString("NSUnionOfArraysKeyValueOperator");
	public static final NSString NSUnionOfObjectsKeyValueOperator = new NSString("NSUnionOfObjectsKeyValueOperator");
	public static final NSString NSUnionOfSetsKeyValueOperator = new NSString("NSUnionOfSetsKeyValueOperator");

	// 1
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
	// Static

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

	public boolean validateValueForKeyError(Object ioValue, NSString key, NSError[] outError);

	// 15
	/**
	 * @Signature: validateValue:forKeyPath:error:
	 * @Declaration : - (BOOL)validateValue:(id *)ioValue forKeyPath:(NSString *)inKeyPath error:(NSError **)outError
	 * @Description : Returns a Boolean value that indicates whether the value specified by a given pointer is valid for a given key path
	 *              relative to the receiver.
	 * @Discussion The default implementation gets the destination object for each relationship using valueForKey: and returns the result of
	 *             a validateValue:forKey:error: message to the final object.
	 **/

	public boolean validateValueForKeyPathError(Object ioValue, NSString inKeyPath, NSError[] outError);

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

	//

}