package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSIndexSet;
import com.myappconverter.java.foundations.NSSet;
import com.myappconverter.java.foundations.NSString;

public interface NSKeyValueObserving {
	// 1
	/**
	 * @Signature: automaticallyNotifiesObserversForKey:
	 * @Declaration : + (BOOL)automaticallyNotifiesObserversForKey:(NSString *)key
	 * @Description : Returns a Boolean value that indicates whether the receiver supports automatic key-value observation for the given
	 *              key.
	 * @return Return Value YES if the key-value observing machinery should automatically invoke
	 *         willChangeValueForKey:/didChangeValueForKey: and willChange:valuesAtIndexes:forKey:/didChange:valuesAtIndexes:forKey:
	 *         whenever instances of the class receive key-value coding messages for the key, or mutating key-value-coding-compliant methods
	 *         for the key are invoked; otherwise NO.
	 * @Discussion The default implementation returns YES.
	 **/

	public boolean automaticallyNotifiesObserversForKey(NSString key);

	// 2
	/**
	 * @Signature: keyPathsForValuesAffectingValueForKey:
	 * @Declaration : + (NSSet *)keyPathsForValuesAffectingValueForKey:(NSString *)key
	 * @Description : Returns a set of key paths for properties whose values affect the value of the specified key.
	 * @return Return Value
	 * @Discussion When an observer for the key is registered with an instance of the receiving class, key-value observing itself
	 *             automatically observes all of the key paths for the same instance, and sends change notifications for the key to the
	 *             observer when the value for any of those key paths changes. The default implementation of this method searches the
	 *             receiving class for a method whose name matches the pattern +keyPathsForValuesAffecting<Key>, and returns the result of
	 *             invoking that method if it is found. Any such method must return an NSSet. If no such method is found, an NSSet that is
	 *             computed from information provided by previous invocations of the now-deprecated
	 *             setKeys:triggerChangeNotificationsForDependentKey: method is returned, for backward binary compatibility. You can
	 *             override this method when the getter method of one of your properties computes a value to return using the values of
	 *             other properties, including those that are located by key paths. Your override should typically invoke super and return a
	 *             set that includes any members in the set that result from doing that (so as not to interfere with overrides of this
	 *             method in superclasses). Note: You must not override this method when you add a computed property to an existing class
	 *             using a category, overriding methods in categories is unsupported. In that case, implement a matching
	 *             +keyPathsForValuesAffecting<Key> to take advantage of this mechanism.
	 **/

	public NSSet keyPathsForValuesAffectingValueForKey(NSString key);

	// 3
	/**
	 * @Signature: addObserver:forKeyPath:options:context:
	 * @Declaration : - (void)addObserver:(NSObject *)anObserver forKeyPath:(NSString *)keyPath options:(NSKeyValueObservingOptions)options
	 *              context:(void *)context
	 * @Description : Registers anObserver to receive KVO notifications for the specified key-path relative to the receiver.
	 * @Discussion Neither the receiver, nor anObserver, are retained.
	 **/

	public void addObserverForKeyPathOptionsContext(NSObject anObserver, NSString keyPath, NSKeyValueObservingOptions options,
													Object context);

	// 4
	/**
	 * @Signature: didChange:valuesAtIndexes:forKey:
	 * @Declaration : - (void)didChange:(NSKeyValueChange)change valuesAtIndexes:(NSIndexSet *)indexes forKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the specified change has occurred on the indexes for a specified ordered to-many
	 *              relationship.
	 * @Discussion You should invoke this method when implementing key-value-observing compliance manually.
	 **/

	public void didChangeValuesAtIndexesForKey(NSKeyValueChange change, NSIndexSet indexes, NSString key);

	// 5
	/**
	 * @Signature: didChangeValueForKey:
	 * @Declaration : - (void)didChangeValueForKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the value of a given property has changed.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually.
	 **/

	public void didChangeValueForKey(NSString key);

	// 6
	/**
	 * @Signature: didChangeValueForKey:withSetMutation:usingObjects:
	 * @Declaration : - (void)didChangeValueForKey:(NSString *)key withSetMutation:(NSKeyValueSetMutationKind)mutationKind
	 *              usingObjects:(NSSet *)objects
	 * @Description : Invoked to inform the receiver that the specified change was made to a specified unordered to-many relationship.
	 * @Discussion You invoke this method when implementing key-value observer compliance manually.
	 **/

	public void didChangeValueForKeyWithSetMutationUsingObjects(NSString key, NSKeyValueSetMutationKind mutationKind, NSSet objects);

	// 7
	/**
	 * @Signature: observationInfo
	 * @Declaration : - (void *)observationInfo
	 * @Description : Returns a pointer that identifies information about all of the observers that are registered with the receiver.
	 * @return Return Value A pointer that identifies information about all of the observers that are registered with the receiver, the
	 *         options that were used at registration-time, and so on.
	 * @Discussion The default implementation of this method retrieves the information from a global dictionary keyed by the receiver’s
	 *             pointers. For improved performance, this method and setObservationInfo: can be overridden to store the opaque data
	 *             pointer in an instance variable. Overrides of this method must not attempt to send Objective-C messages to the stored
	 *             data, including retain and release.
	 **/

	public Object observationInfo();

	// 8
	/**
	 * @Signature: observeValueForKeyPath:ofObject:change:context:
	 * @Declaration : - (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void
	 *              *)context
	 * @Description : This message is sent to the receiver when the value at the specified key path relative to the given object has
	 *              changed.
	 * @Discussion The receiver must be registered as an observer for the specified keyPath and object.
	 **/

	public void observeValueForKeyPathOfObjectChangeContext(NSString keyPath, Object object, NSDictionary change, Object context);

	// 9
	/**
	 * @Signature: removeObserver:forKeyPath:
	 * @Declaration : - (void)removeObserver:(NSObject *)anObserver forKeyPath:(NSString *)keyPath
	 * @Description : Stops a given object from receiving change notifications for the property specified by a given key-path relative to
	 *              the receiver.
	 **/

	public void removeObserverForKeyPath(NSObject anObserver, NSString keyPath);

	// 10
	/**
	 * @Signature: removeObserver:forKeyPath:context:
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath context:(void *)context
	 * @Description : Stops a given object from receiving change notifications for the property specified by a given key-path relative to
	 *              the receiver and a context.
	 * @Discussion Examining the value in context you are able to determine precisely which addObserver:forKeyPath:options:context:
	 *             invocation was used to create the observation relationship. When the same observer is registered for the same key-path
	 *             multiple times, but with different context pointers, an application can determine specifically which object to stop
	 *             observing.
	 **/

	public void removeObserverForKeyPathContext(NSObject observer, NSString keyPath, Object context);

	// 11
	/**
	 * @Signature: setObservationInfo:
	 * @Declaration : - (void)setObservationInfo:(void *)observationInfo
	 * @Description : Sets the observation info for the receiver.
	 * @Discussion The observationInfo is a pointer that identifies information about all of the observers that are registered with the
	 *             receiver. The default implementation of this method stores observationInfo in a global dictionary keyed by the receiver’s
	 *             pointers. For improved performance, this method and observationInfo can be overridden to store the opaque data pointer in
	 *             an instance variable. Classes that override this method must not attempt to send Objective-C messages to observationInfo,
	 *             including retain and release.
	 **/

	public void setObservationInfo(Object observationInfo);

	// 12
	/**
	 * @Signature: willChange:valuesAtIndexes:forKey:
	 * @Declaration : - (void)willChange:(NSKeyValueChange)change valuesAtIndexes:(NSIndexSet *)indexes forKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the specified change is about to be executed at given indexes for a specified
	 *              ordered to-many relationship.
	 * @Discussion You should invoke this method when implementing key-value-observing compliance manually. Important: After the values have
	 *             been changed, a corresponding didChange:valuesAtIndexes:forKey: must be invoked with the same parameters.
	 **/

	public void willChangeValuesAtIndexesForKey(NSKeyValueChange change, NSIndexSet indexes, NSString key);

	// 13
	/**
	 * @Signature: willChangeValueForKey:
	 * @Declaration : - (void)willChangeValueForKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the value of a given property is about to change.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually. The change type of this method is
	 *             NSKeyValueChangeSetting. Important: After the values have been changed, a corresponding didChangeValueForKey: must be
	 *             invoked with the same parameter.
	 **/

	public void willChangeValueForKey(NSString key);

	// 14
	/**
	 * @Signature: willChangeValueForKey:withSetMutation:usingObjects:
	 * @Declaration : - (void)willChangeValueForKey:(NSString *)key withSetMutation:(NSKeyValueSetMutationKind)mutationKind
	 *              usingObjects:(NSSet *)objects
	 * @Description : Invoked to inform the receiver that the specified change is about to be made to a specified unordered to-many
	 *              relationship.
	 * @Discussion You invoke this method when implementing key-value observer compliance manually. Important: After the values have been
	 *             changed, a corresponding didChangeValueForKey:withSetMutation:usingObjects: must be invoked with the same parameters.
	 **/

	public void willChangeValueForKeyWithSetMutationUsingObjects(NSString key, NSKeyValueSetMutationKind mutationKind, NSSet objects);

	// Constants
	public static enum NSKeyValueChange {
		NSKeyValueChangeSetting(1), NSKeyValueChangeInsertion(2), NSKeyValueChangeRemoval(3), NSKeyValueChangeReplacement(4);
		int value;

		NSKeyValueChange(int value) {
			this.value = value;

		}

	}

	public static enum NSKeyValueObservingOptions {
		NSKeyValueObservingOptionNew(0x01), NSKeyValueObservingOptionOld(0x02), NSKeyValueObservingOptionInitial(0x04), NSKeyValueObservingOptionPrior(
				0x08);
		int value;

		NSKeyValueObservingOptions(int value) {
			this.value = value;
		}

	}

	public static final NSString NSKeyValueChangeKindKey = new NSString();
	public static final NSString NSKeyValueChangeNewKey = new NSString();
	public static final NSString NSKeyValueChangeOldKey = new NSString();
	public static final NSString NSKeyValueChangeIndexesKey = new NSString();
	public static final NSString NSKeyValueChangeNotificationIsPriorKey = new NSString();

	public static enum NSKeyValueSetMutationKind {
		NSKeyValueUnionSetMutation(1), NSKeyValueMinusSetMutation(2), NSKeyValueIntersectSetMutation(3), NSKeyValueSetSetMutation(4);
		int value;

		NSKeyValueSetMutationKind(int value) {
			this.value = value;
		}

	}

}