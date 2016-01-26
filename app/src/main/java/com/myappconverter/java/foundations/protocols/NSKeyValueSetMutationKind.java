package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSDictionary;
import com.myappconverter.java.foundations.NSIndexSet;
import com.myappconverter.java.foundations.NSSet;
import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.NSKeyValueChange;
import com.myappconverter.java.foundations.kvo.NSKeyValueObserving.NSKeyValueObservingOptions;

/**
 * Overview The NSKeyValueObserving (KVO) informal protocol defines a mechanism that allows objects to be notified of changes to the
 * specified properties of other objects. You can observe any object properties including simple attributes, to-one relationships, and
 * to-many relationships. Observers of to-many relationships are informed of the type of change made — as well as which objects are involved
 * in the change. NSObject provides an implementation of the NSKeyValueObserving protocol that provides an automatic observing capability
 * for all objects. You can further refine notifications by disabling automatic observer notifications and implementing manual notifications
 * using the methods in this protocol.
 */

// Note: Key-value observing is not available for Java applications.

public interface NSKeyValueSetMutationKind {

	// Change Notification
	/**
	 * @Declaration : - (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary *)change context:(void
	 *              *)context
	 * @Description : This message is sent to the receiver when the value at the specified key path relative to the given object has
	 *              changed.
	 * @param keyPath The key path, relative to object, to the value that has changed.
	 * @param object The source object of the key path keyPath.
	 * @param change A dictionary that describes the changes that have been made to the value of the property at the key path keyPath
	 *            relative to object. Entries are described in “Change Dictionary Keys.”
	 * @param context The value that was provided when the receiver was registered to receive key-value observation notifications.
	 * @Discussion The receiver must be registered as an observer for the specified keyPath and object.
	 */
	public void observeValueForKeyPathofObjectchangecontext(NSString keyPath, Object object, NSDictionary change, Object context);

	// Registering for Observation
	/**
	 * @Declaration : - (void)addObserver:(NSObject *)anObserver forKeyPath:(NSString *)keyPath options:(NSKeyValueObservingOptions)options
	 *              context:(void *)context
	 * @Description : Registers anObserver to receive KVO notifications for the specified key-path relative to the receiver.
	 * @param anObserver The object to register for KVO notifications. The observer must implement the key-value observing method
	 *            observeValueForKeyPath:ofObject:change:context:.
	 * @param keyPath The key path, relative to the receiver, of the property to observe. This value must not be nil.
	 * @param options A combination of the NSKeyValueObservingOptions values that specifies what is included in observation notifications.
	 *            For possible values, see “NSKeyValueObservingOptions.”
	 * @param context Arbitrary data that is passed to anObserver in observeValueForKeyPath:ofObject:change:context:.
	 * @Discussion Neither the receiver, nor anObserver, are retained.
	 */
	public void addObserverforKeyPathoptionscontext(NSObject anObserver, NSString keyPath, NSKeyValueObservingOptions options,
													Object context);

	/**
	 * @Declaration : - (void)removeObserver:(NSObject *)anObserver forKeyPath:(NSString *)keyPath
	 * @Description : Stops a given object from receiving change notifications for the property specified by a given key-path relative to
	 *              the receiver.
	 * @param anObserver The object to remove as an observer.
	 * @param keyPath A key-path, relative to the receiver, for which anObserver is registered to receive KVO change notifications.
	 */
	public void removeObserverforKeyPath(NSObject anObserver, NSString keyPath);

	/**
	 * @Declaration : - (void)removeObserver:(NSObject *)observer forKeyPath:(NSString *)keyPath context:(void *)context
	 * @Description : Stops a given object from receiving change notifications for the property specified by a given key-path relative to
	 *              the receiver and a context.
	 * @param observer The object to remove as an observer.
	 * @param keyPath A key-path, relative to the receiver, for which anObserver is registered to receive KVO change notifications.
	 * @param context Arbitrary data that more specifically identifies the observer to be removed.
	 * @Discussion Examining the value in context you are able to determine precisely which addObserver:forKeyPath:options:context:
	 *             invocation was used to create the observation relationship. When the same observer is registered for the same key-path
	 *             multiple times, but with different context pointers, an application can determine specifically which object to stop
	 *             observing.
	 */
	public void removeObserverforKeyPathcontext(NSObject observer, NSString keyPath, Object context);

	// Notifying Observers of Changes

	/**
	 * @Declaration : - (void)willChangeValueForKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the value of a given property is about to change.
	 * @param key The name of the property that will change.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually. The change type of this method is
	 *             NSKeyValueChangeSetting. Important: After the values have been changed, a corresponding didChangeValueForKey: must be
	 *             invoked with the same parameter.
	 */
	public void willChangeValueForKey(NSString key);

	/**
	 * @Declaration : - (void)didChangeValueForKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the value of a given property has changed.
	 * @param key The name of the property that changed.
	 * @Discussion You should invoke this method when implementing key-value observer compliance manually.
	 */
	public void didChangeValueForKey(NSString key);

	/**
	 * @Declaration : - (void)willChange:(NSKeyValueChange)change valuesAtIndexes:(NSIndexSet *)indexes forKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the specified change is about to be executed at given indexes for a specified
	 *              ordered to-many relationship.
	 * @param change The type of change that is about to be made.
	 * @param indexes The indexes of the to-many relationship that will be affected by the change.
	 * @param key The name of a property that is an ordered to-many relationship.
	 * @Discussion You should invoke this method when implementing key-value-observing compliance manually. Important: After the values have
	 *             been changed, a corresponding didChange:valuesAtIndexes:forKey: must be invoked with the same parameters.
	 */
	public void willChangevaluesAtIndexesforKey(NSKeyValueChange change, NSIndexSet indexes, NSString key);

	/**
	 * @Declaration : - (void)didChange:(NSKeyValueChange)change valuesAtIndexes:(NSIndexSet *)indexes forKey:(NSString *)key
	 * @Description : Invoked to inform the receiver that the specified change has occurred on the indexes for a specified ordered to-many
	 *              relationship.
	 * @param change The type of change that was made.
	 * @param indexes The indexes of the to-many relationship that were affected by the change.
	 * @param key The name of a property that is an ordered to-many relationship.
	 * @Discussion You should invoke this method when implementing key-value-observing compliance manually.
	 */
	public void didChangevaluesAtIndexesforKey(NSKeyValueChange change, NSIndexSet indexes, NSString key);

	/**
	 * @Declaration : - (void)willChangeValueForKey:(NSString *)key withSetMutation:(NSKeyValueSetMutationKind)mutationKind
	 *              usingObjects:(NSSet *)objects
	 * @Description : Invoked to inform the receiver that the specified change is about to be made to a specified unordered to-many
	 *              relationship.
	 * @param key The name of a property that is an unordered to-many relationship
	 * @param mutationKind The type of change that will be made.
	 * @param objects The objects that are involved in the change (see “NSKeyValueSetMutationKind”).
	 * @Discussion You invoke this method when implementing key-value observer compliance manually. Important: After the values have been
	 *             changed, a corresponding didChangeValueForKey:withSetMutation:usingObjects: must be invoked with the same parameters.
	 */
	public void willChangeValueForKeywithSetMutationusingObjects(NSString key, NSKeyValueSetMutationKind mutationKind, NSSet objects);

	/**
	 * @Declaration : - (void)didChangeValueForKey:(NSString *)key withSetMutation:(NSKeyValueSetMutationKind)mutationKind
	 *              usingObjects:(NSSet *)objects
	 * @Description : Invoked to inform the receiver that the specified change was made to a specified unordered to-many relationship.
	 * @param key The name of a property that is an unordered to-many relationship
	 * @param mutationKind The type of change that was made.
	 * @param objects The objects that were involved in the change (see “NSKeyValueSetMutationKind”).
	 * @Discussion You invoke this method when implementing key-value observer compliance manually.
	 */
	public void didChangeValueForKeywithSetMutationusingObjects(NSString key, NSKeyValueSetMutationKind mutationKind, NSSet objects);

	// Observing Customization
	/**
	 * @Declaration : + (BOOL)automaticallyNotifiesObserversForKey:(NSString *)key
	 * @Description : Returns a Boolean value that indicates whether the receiver supports automatic key-value observation for the given
	 *              key.
	 * @return Return Value YES if the key-value observing machinery should automatically invoke
	 *         willChangeValueForKey:/didChangeValueForKey: and willChange:valuesAtIndexes:forKey:/didChange:valuesAtIndexes:forKey:
	 *         whenever instances of the class receive key-value coding messages for the key, or mutating key-value-coding-compliant methods
	 *         for the key are invoked; otherwise NO.
	 * @Discussion The default implementation returns YES.
	 */
	public boolean automaticallyNotifiesObserversForKey(NSString key);

	/**
	 * @Declaration : + (NSSet *)keyPathsForValuesAffectingValueForKey:(NSString *)key
	 * @Description : Returns a set of key paths for properties whose values affect the value of the specified key.
	 * @param key The key whose value is affected by the key paths.
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
	 */
	public void keyPathsForValuesAffectingValueForKey(NSString key);

	/**
	 * @Declaration : - (void)setObservationInfo:(void *)observationInfo
	 * @Description : Sets the observation info for the receiver.
	 * @param observationInfo The observation info for the receiver.
	 * @Discussion The observationInfo is a pointer that identifies information about all of the observers that are registered with the
	 *             receiver. The default implementation of this method stores observationInfo in a global dictionary keyed by the receiver’s
	 *             pointers. For improved performance, this method and observationInfo can be overridden to store the opaque data pointer in
	 *             an instance variable. Classes that override this method must not attempt to send Objective-C messages to observationInfo,
	 *             including retain and release.
	 */
	public void setObservationInfo(Object observationInfo);

	/**
	 * @Declaration : - (void *)observationInfo
	 * @Description : Returns a pointer that identifies information about all of the observers that are registered with the receiver.
	 * @return Return Value A pointer that identifies information about all of the observers that are registered with the receiver, the
	 *         options that were used at registration-time, and so on.
	 * @Discussion The default implementation of this method retrieves the information from a global dictionary keyed by the receiver’s
	 *             pointers. For improved performance, this method and setObservationInfo: can be overridden to store the opaque data
	 *             pointer in an instance variable. Overrides of this method must not attempt to send Objective-C messages to the stored
	 *             data, including retain and release.
	 */
	public Object observationInfo();

}
