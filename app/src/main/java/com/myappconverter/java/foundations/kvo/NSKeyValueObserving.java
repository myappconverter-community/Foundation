package com.myappconverter.java.foundations.kvo;

import java.util.EnumSet;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSError;
import com.myappconverter.java.foundations.NSMutableArray;
import com.myappconverter.java.foundations.NSMutableDictionary;
import com.myappconverter.java.foundations.NSMutableOrderedSet;
import com.myappconverter.java.foundations.NSMutableSet;
import com.myappconverter.java.foundations.NSSet;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.kvc.NSKeyValueCoding;
import com.myappconverter.java.foundations.kvc.NSKeyValueCodingAdditions;

public interface NSKeyValueObserving extends NSKeyValueCodingAdditions {

	public static enum NSKeyValueObservingOptions {
		NSKeyValueObservingOptionNew(0x01), //
		NSKeyValueObservingOptionOld(0x02), //
		NSKeyValueObservingOptionInitial(0x04), //
		NSKeyValueObservingOptionPrior(0x08);
		int value;

		NSKeyValueObservingOptions(int v) {
			value = v;
		}
	};

	public static enum NSKeyValueChange {
		NSKeyValueChangeSetting(1), //
		NSKeyValueChangeInsertion(2), //
		NSKeyValueChangeRemoval(3), //
		NSKeyValueChangeReplacement(4);
		int value;

		NSKeyValueChange(int v) {
			value = v;
		}
	};

	public enum Options {
		New, Old, Initial, Prior;

		public static final EnumSet<Options> NewAndOld = EnumSet.of(New, Old);
	}

	public enum Changes {
		Setting, Insertion, Removal, Replacement
	}

	public enum SetMutation {
		Union, Minus, Intersect, Set
	}

	/*
	 * Notifying observers of changes
	 */
	/**
	 * 
	 - willChangeValueForKey: <br>
	 * Invoked to inform the receiver that the value of a given property is about to change.
	 * 
	 * @Declaration OBJECTIVE-C - (void)willChangeValueForKey:(NSString *)key
	 * @param key The name of the property that will change.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually.
	 * 
	 *             The change type of this method is NSKeyValueChangeSetting.
	 * @IMPORTANT : After the values have been changed, a corresponding didChangeValueForKey: must be invoked with the same parameter.
	 * @Special Considerations You should not override this method in subclasses.
	 * 
	 */

	public void willChangeValueForKey(NSString key);

	/**
	 * Invoked to inform the receiver that the value of a given property has changed.
	 * 
	 * @Declaration OBJECTIVE-C - (void)didChangeValueForKey:(NSString *)key
	 * @param key The name of the property that changed.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually.
	 * @Special Considerations You should not override this method in subclasses.
	 */

	public void didChangeValueForKey(NSString key);

	public class KeyValueChange {
		public EnumSet<Changes> kind;
		public Object newValue;
		public Object oldValue;
		public NSSet<Integer> indexes;
		public Boolean isPrior;

		public KeyValueChange() {
		}

		public KeyValueChange(Changes... changes) {
			kind = EnumSet.of(changes[0], changes);
		}

		public KeyValueChange(KeyValueChange changes) {
			this.kind = changes.kind;
			this.newValue = changes.newValue;
			this.oldValue = changes.oldValue;
			this.indexes = changes.indexes;
		}

		public KeyValueChange(EnumSet<Changes> change, NSSet<Integer> indexes) {
			this.kind = change;
			this.indexes = indexes;
		}
	}

	public static class DefaultImplementation {

		public static boolean automaticallyNotifiesObserversForKey(NSObservable targetObject, String key) {
			return true;
		}

		public static void addObserverForKeyPath(NSObservable targetObject, NSObserver observer, String keyPath, EnumSet<Options> options,
				Object context) {
			if (observer == null || keyPath == null || keyPath.length() == 0) {
				return;
			}

			KeyValueObservingProxy.proxyForObject(targetObject).addObserverForKeyPath(observer, keyPath, options, context);
		}

		public static void didChangeValueForKey(NSObservable targetObject, String key) {
			if (key == null || key.length() == 0) {
				return;
			}

			KeyValueObservingProxy.proxyForObject(targetObject).sendNotificationsForKey(key, null, false);
		}

		public static void didChangeValuesAtIndexForKey(NSObservable targetObject, EnumSet<Changes> change, NSSet<Integer> indexes,
				String key) {
			if (key == null) {
				return;
			}

			KeyValueObservingProxy.proxyForObject(targetObject).sendNotificationsForKey(key, null, false);
		}

		public static NSSet<String> keyPathsForValuesAffectingValueForKey(NSObservable targetObject, String key) {
			return NSSet.emptySet();
		}

		public static void removeObserverForKeyPath(NSObservable targetObject, NSObserver observer, String keyPath) {
			if (observer == null || keyPath == null || keyPath.length() == 0) {
				return;
			}

			KeyValueObservingProxy.proxyForObject(targetObject).removeObserverForKeyPath(observer, keyPath);
		}

		public static void willChangeValueForKey(NSObservable targetObject, String key) {
			if (key == null || key.length() == 0)
				return;

			KeyValueChange changeOptions = new KeyValueChange(Changes.Setting);
			KeyValueObservingProxy.proxyForObject(targetObject).sendNotificationsForKey(key, changeOptions, true);
		}

		public static void willChangeValuesAtIndexForKey(final NSObservable targetObject, final EnumSet<Changes> change,
				final NSSet<Integer> indexes, final String key) {
			if (key == null || key.length() == 0)
				return;

			KeyValueChange changeOptions = new KeyValueChange(change, indexes);
			KeyValueObservingProxy.proxyForObject(targetObject).sendNotificationsForKey(key, changeOptions, true);
		}

		public static void observeValueForKeyPath(NSObserver observer, String keyPath, NSObservable targetObject, KeyValueChange changes,
				Object context) {
		}
	}

	public static class Utility {

		public static NSObservable observable(Object object) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			if (object instanceof NSObservable) {
				return (NSObservable) object;
			}
			return KeyValueObservingProxy.proxyForObject(object);
		}

		public static void addObserverForKeyPath(Object object, NSObserver observer, String keyPath, EnumSet<Options> options,
				Object context) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).addObserverForKeyPath(observer, keyPath, options, context);
		}

		public static boolean automaticallyNotifiesObserversForKey(Object object, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			return observable(object).automaticallyNotifiesObserversForKey(key);
		}

		public static void didChangeValueForKey(Object object, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).didChangeValueForKey(key);
		}

		public static void didChangeValuesAtIndexForKey(Object object, EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).didChangeValuesAtIndexForKey(change, indexes, key);
		}

		public static NSSet<String> keyPathsForValuesAffectingValueForKey(Object object, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			return observable(object).keyPathsForValuesAffectingValueForKey(key);
		}

		public static void removeObserverForKeyPath(Object object, NSObserver observer, String keyPath) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).removeObserverForKeyPath(observer, keyPath);
		}

		public static void willChangeValueForKey(Object object, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).willChangeValueForKey(key);
		}

		public static void willChangeValuesAtIndexForKey(Object object, EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			observable(object).willChangeValuesAtIndexForKey(change, indexes, key);
		}

		public static void observeValueForKeyPath(Object object, String keyPath, NSObservable targetObject, KeyValueChange changes,
				Object context) {
			if (object == null) {
				throw new IllegalArgumentException("Object cannot be null");
			}
			if (object instanceof NSObserver) {
				((NSObserver) object).observeValueForKeyPath(keyPath, targetObject, changes, context);
			}
			throw new IllegalArgumentException("Object must implement NSObserver");
		}
	}

	public static class KeyValueObservingProxy implements NSObservable {
		private static final NSMutableDictionary<Object, KeyValueObservingProxy> _proxyCache = new NSMutableDictionary<Object, KeyValueObservingProxy>();
		private static final NSMutableDictionary<Class<? extends Object>, NSMutableDictionary<String, NSMutableSet<String>>> _dependentKeys = new NSMutableDictionary<Class<? extends Object>, NSMutableDictionary<String, NSMutableSet<String>>>();
		private final Object _targetObject;
		private NSMutableDictionary<String, KeyValueChange> _changesForKey = new NSMutableDictionary<String, KeyValueChange>();
		private NSMutableDictionary<String, NSMutableDictionary<NSObserver, ObserverInfo>> _observersForKey = new NSMutableDictionary<String, NSMutableDictionary<NSObserver, ObserverInfo>>();
		int _changeCount = 0;

		public static KeyValueObservingProxy proxyForObject(Object object) {
			KeyValueObservingProxy proxy = (KeyValueObservingProxy) _proxyCache.objectForKey(object);

			if (proxy != null) {
				return proxy;
			}

			proxy = new KeyValueObservingProxy(object);
			_proxyCache.setObjectForKey(proxy, object);
			return proxy;
		}

		private KeyValueObservingProxy(Object object) {
			_targetObject = object;
		}

		private NSObservable observable() {
			if (_targetObject instanceof NSObservable) {
				return (NSObservable) _targetObject;
			}
			return this;
		}

		@Override
		public void addObserverForKeyPath(NSObserver observer, String keyPath, EnumSet<Options> options, Object context) {
			if (observer == null)
				return;

			KeyValueForwardingObserver forwarder = null;
			if (keyPath.contains(".")) {
				forwarder = new KeyValueForwardingObserver(keyPath, observable(), observer, options, context);
			} else {
				addDependentKeysForKey(keyPath);
			}

			NSMutableDictionary<NSObserver, ObserverInfo> observers = (NSMutableDictionary) _observersForKey.objectForKey(new NSString(
					keyPath));
			if (observers == null) {
				observers = new NSMutableDictionary<NSObserver, ObserverInfo>();
				_observersForKey.setObjectForKey(observers, keyPath);
				NSKeyValueCoding.DefaultImplementation._addKVOAdditionsForKey(_targetObject, keyPath);
			}
			observers.setObjectForKey(new ObserverInfo(observer, options, context, forwarder), observer);

			if (options.contains(Options.Initial)) {
				Object _newValue = NSKeyValueCodingAdditions.Utility.valueForKeyPath(_targetObject, keyPath);
				if (_newValue == null)
					_newValue = NSKeyValueCoding.NullValue;

				final Object value = _newValue;
				KeyValueChange changes = new KeyValueChange() {
					{
						this.newValue = value;
					}
				};

				observer.observeValueForKeyPath(keyPath, observable(), changes, context);
			}
		}

		private void addDependentKeysForKey(String key) {
			NSSet<String> composedOfKeys = Utility.keyPathsForValuesAffectingValueForKey(_targetObject, key);

			NSMutableDictionary<String, NSMutableSet<String>> dependentKeysForClass = (NSMutableDictionary) _dependentKeys
					.objectForKey(_targetObject.getClass());
			if (dependentKeysForClass == null) {
				dependentKeysForClass = new NSMutableDictionary<String, NSMutableSet<String>>();
				_dependentKeys.setObjectForKey(dependentKeysForClass, _targetObject.getClass());
			}

			for (String componentKey : composedOfKeys.getWrappedSet()) {
				NSMutableSet<String> keysComposedOfKey = (NSMutableSet) dependentKeysForClass.objectForKey(new NSString(componentKey));
				if (keysComposedOfKey == null) {
					keysComposedOfKey = new NSMutableSet<String>();
					dependentKeysForClass.setObjectForKey(keysComposedOfKey, componentKey);
				}

				keysComposedOfKey.addObject(key);
				addDependentKeysForKey(componentKey);
			}

		}

		@Override
		public void removeObserverForKeyPath(NSObserver observer, String keyPath) {
			NSMutableDictionary<NSObserver, ObserverInfo> observers = (NSMutableDictionary) _observersForKey.objectForKey(new NSString(
					keyPath));
			if (keyPath.contains(".")) {
				// KeyValueForwardingObserver forwarder = (() observers.objectForKey(observer)).forwarder;
				// forwarder.destroy();
			}

			observers.removeObjectForKey(observer);
			if (observers.isEmpty()) {
				_observersForKey.removeObjectForKey(keyPath);
				NSKeyValueCoding.DefaultImplementation._removeKVOAdditionsForKey(_targetObject, keyPath);
			}
			if (_observersForKey.isEmpty())
				_proxyCache.removeObjectForKey(_targetObject);
		}

		@SuppressWarnings("unchecked")
		public void sendNotificationsForKey(String key, KeyValueChange changeOptions, boolean isBefore) {
			KeyValueChange changes = (KeyValueChange) _changesForKey.objectForKey(new NSString(key));

			if (isBefore) {
				changes = new KeyValueChange(changeOptions);

				NSSet<Integer> indexes = changes.indexes;
				if (indexes != null) {
					EnumSet<Changes> type = changes.kind;
					if (type.contains(Changes.Replacement) || type.contains(Changes.Removal)) {
						NSMutableArray<Object> oldValues = new NSMutableArray<Object>(
								(NSArray<Object>) NSKeyValueCodingAdditions.Utility.valueForKeyPath(_targetObject, key));
						changes.oldValue = oldValues;
					}
				} else {
					Object oldValue = NSKeyValueCoding.Utility.valueForKey(_targetObject, key);

					if (oldValue == null)
						oldValue = NSKeyValueCoding.NullValue;

					changes.oldValue = oldValue;
				}

				changes.isPrior = true;
				_changesForKey.setObjectForKey(changes, key);
			} else {
				changes.isPrior = null;
				NSSet<Integer> indexes = changes.indexes;

				if (indexes != null) {
					EnumSet<Changes> type = changes.kind;
					if (type.contains(Changes.Replacement) || type.contains(Changes.Insertion)) {
						NSMutableArray<Object> newValues = new NSMutableArray<Object>(
								(NSArray<Object>) NSKeyValueCodingAdditions.Utility.valueForKeyPath(_targetObject, key));
						changes.newValue = newValues;
					}
				} else {
					Object newValue = NSKeyValueCoding.Utility.valueForKey(_targetObject, key);
					if (newValue == null)
						newValue = NSKeyValueCoding.NullValue;
					changes.newValue = newValue;
				}
				// FIXME - Is this safe? Sink the change notification if nothing
				// actually changed.
				if (changes.oldValue == changes.newValue)
					return;
			}

			NSArray<ObserverInfo> observers;
			if (_observersForKey.getWrappedDictionary().containsKey(key)) {
				observers = ((NSDictionary) _observersForKey.objectForKey(new NSString(key))).allValues();
			} else {
				observers = new NSArray<ObserverInfo>();
			}

			int count = observers.count();
			while (count-- > 0) {
				ObserverInfo observerInfo = observers.objectAtIndex(count);
				if (isBefore && (observerInfo.options.contains(Options.Prior))) {
					observerInfo.observer.observeValueForKeyPath(key, observable(), changes, observerInfo.context);
				} else if (!isBefore) {
					observerInfo.observer.observeValueForKeyPath(key, observable(), changes, observerInfo.context);
				}
			}

			NSSet<String> keysComposedOfKey = null;
			if (_dependentKeys.getWrappedDictionary().containsKey(_targetObject.getClass()))
				keysComposedOfKey = (NSSet) ((NSDictionary) _dependentKeys.objectForKey(_targetObject.getClass()))
						.objectForKey(new NSString(key));
			if (keysComposedOfKey == null || keysComposedOfKey.isEmpty())
				return;

			for (String dependentKey : keysComposedOfKey.getWrappedSet()) {
				sendNotificationsForKey(dependentKey, changeOptions, isBefore);
			}
		}

		@Override
		public boolean automaticallyNotifiesObserversForKey(String key) {
			return true;
		}

		@Override
		public void didChangeValueForKey(String key) {
			if (--_changeCount == 0)
				DefaultImplementation.didChangeValueForKey(this, key);
		}

		@Override
		public void didChangeValuesAtIndexForKey(EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
			DefaultImplementation.didChangeValuesAtIndexForKey(this, change, indexes, key);
		}

		@Override
		public NSSet<String> keyPathsForValuesAffectingValueForKey(String key) {
			return NSSet.emptySet();
		}

		@Override
		public void willChangeValueForKey(String key) {
			_changeCount++;
			DefaultImplementation.willChangeValueForKey(this, key);
		}

		@Override
		public void willChangeValuesAtIndexForKey(EnumSet<Changes> change, NSSet<Integer> indexes, String key) {
			DefaultImplementation.willChangeValuesAtIndexForKey(this, change, indexes, key);
		}

		@Override
		public void takeValueForKeyPath(Object value, String keyPath) {
			NSKeyValueCodingAdditions.Utility.takeValueForKeyPath(_targetObject, value, keyPath);
		}

		@Override
		public Object valueForKeyPath(String keyPath) {
			return NSKeyValueCodingAdditions.Utility.valueForKeyPath(_targetObject, keyPath);
		}

		@Override
		public void takeValueForKey(Object value, String key) {
			NSKeyValueCoding.Utility.takeValueForKey(_targetObject, value, key);
		}

		@Override
		public Object valueForKey(String key) {
			return NSKeyValueCoding.Utility.valueForKey(_targetObject, key);
		}

		@Override
		public void willChangeValueForKey(NSString key) {
		}

		@Override
		public void didChangeValueForKey(NSString key) {
		}

		@Override
		public boolean accessInstanceVariablesDirectly() {
			return false;
		}

		@Override
		public NSDictionary dictionaryWithValuesForKeys(NSArray keys) {
			return null;
		}

		@Override
		public NSMutableArray mutableArrayValueForKey(NSString key) {
			return null;
		}

		@Override
		public NSMutableArray mutableArrayValueForKeyPath(NSString keyPath) {
			return null;
		}

		@Override
		public NSMutableOrderedSet mutableOrderedSetValueForKey(NSString key) {
			return null;
		}

		@Override
		public NSMutableOrderedSet mutableOrderedSetValueForKeyPath(NSString keyPath) {
			return null;
		}

		@Override
		public NSMutableSet mutableSetValueForKey(NSString key) {
			return null;
		}

		@Override
		public NSMutableSet mutableSetValueForKeyPath(NSString keyPath) {
			return null;
		}

		@Override
		public void setNilValueForKey(NSString key) {
		}

		@Override
		public void setValueForKey(Object value, NSString key) {
		}

		@Override
		public void setValueForKeyPath(Object value, NSString keyPath) {
		}

		@Override
		public void setValueForUndefinedKey(Object value, NSString key) {
		}

		@Override
		public void setValuesForKeysWithDictionary(NSDictionary keyedValues) {
		}

		@Override
		public boolean validateValueForKeyError(Object ioValue, NSString key, NSError[] outError) {
			return false;
		}

		@Override
		public boolean validateValueForKeyPathError(Object ioValue, NSString inKeyPath, NSError[] outError) {
			return false;
		}

		@Override
		public Object valueForKey(NSString key) {
			return null;
		}

		@Override
		public Object valueForKeyPath(NSString keyPath) {
			return null;
		}

		@Override
		public Object valueForUndefinedKey(NSString key) {
			return null;
		}

	}

	public static class ObserverInfo {
		public final NSObserver observer;
		public final EnumSet<Options> options;
		public final Object context;
		public final KeyValueForwardingObserver forwarder;

		public ObserverInfo(NSObserver observer, EnumSet<Options> options, Object context, KeyValueForwardingObserver forwarder) {
			this.observer = observer;
			this.options = options;
			this.context = context;
			this.forwarder = forwarder;
		}
	}

	public static class KeyValueForwardingObserver implements NSObserver {
		private final NSObservable _targetObject;
		private final NSObserver _observer;
		private final EnumSet<Options> _options;

		private final String _firstPart;
		private final String _secondPart;

		private NSObservable _value;

		public KeyValueForwardingObserver(String keyPath, NSObservable object, NSObserver observer, EnumSet<Options> options, Object context) {
			_observer = observer;
			_targetObject = object;
			_options = options;

			if (!keyPath.contains("."))
				throw new IllegalArgumentException("Created KeyValueForwardingObserver without a compound key path: " + keyPath);

			int index = keyPath.indexOf('.');
			_firstPart = keyPath.substring(0, index);
			_secondPart = keyPath.substring(index + 1);

			_targetObject.addObserverForKeyPath(this, keyPath, options, context);

			_value = (NSObservable) _targetObject.valueForKey(_firstPart);
			if (_value != null) {
				_value.addObserverForKeyPath(this, keyPath, options, context);
			}
		}

		@Override
		public void observeValueForKeyPath(String keyPath, NSObservable targetObject, KeyValueChange changes, Object context) {
			if (targetObject == _targetObject) {
				_observer.observeValueForKeyPath(_firstPart, targetObject, changes, context);

				Object newValue = _targetObject.valueForKeyPath(_secondPart);
				if (_value != null && _value != newValue)
					_value.removeObserverForKeyPath(this, _secondPart);

				_value = (NSObservable) newValue;
				if (_value != null)
					_value.addObserverForKeyPath(this, _secondPart, _options, context);
			} else {
				_observer.observeValueForKeyPath(_firstPart + "." + keyPath, targetObject, changes, context);
			}
		}

		private void destroy() {
			if (_value != null)
				_value.removeObserverForKeyPath(this, _secondPart);
			_targetObject.removeObserverForKeyPath(this, _firstPart);

			_value = null;
		}
	}

	public interface _NSKeyValueObserving {
	}
	//

}
