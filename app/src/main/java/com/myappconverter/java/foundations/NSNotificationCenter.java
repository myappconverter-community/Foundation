
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.utilities.NSSelectable;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.InvokableHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class NSNotificationCenter extends NSObject implements NSSelectable {

	//public final NSMutableDictionary<NSString, NotificationRegistry> _namedRegistries = new NSMutableDictionary<NSString, NotificationRegistry>();
	//public final NotificationRegistry _unnamedRegistry = new NotificationRegistry();

	//public static List<NSNotification> notifications = new ArrayList<NSNotification>();
	//public static NSMutableArray dispatchTable = new NSMutableArray();
	public static final Map<NSNotification, NotificationObserver> entrysNotification = new HashMap<NSNotification, NotificationObserver>();


	public static  NSNotificationCenter _defaultCenter = new NSNotificationCenter();

	public interface NSNotificationCenterBlock {

		public boolean performAction(NSNotification notification);
	}

	// Getting the Notification Center
	/**
	 * @Signature: defaultCenter
	 * @Declaration : + (id)defaultCenter
	 * @Description : Returns the process’s default notification center.
	 * @return Return Value The current process’s default notification center, which is used for system notifications.
	 **/
	
	public static NSNotificationCenter defaultCenter() {
		if (_defaultCenter == null) {
			_defaultCenter = new NSNotificationCenter();
		}
		return _defaultCenter;
	}

	public static NSNotificationCenter defaultCenter(Class clazz) {
		NSNotificationCenter nsn = defaultCenter();
		return (NSNotificationCenter) InstanceTypeFactory.getInstance(nsn,
				NSNotificationCenter.class, clazz, new NSString("defaultCenter"), null);
	}

	// Managing Notification Observers
	/**
	 * @Signature: addObserverForName:object:queue:usingBlock:
	 * @Declaration : - (id)addObserverForName:(NSString *)name object:(id)obj queue:(NSOperationQueue *)queue usingBlock:(void
	 *              (^)(NSNotification *))block
	 * @Description : Adds an entry to the receiver’s dispatch table with a notification queue and a block to add to the queue, and optional
	 *              criteria: notification name and sender.
	 * @param name The name of the notification for which to register the observer; that is, only notifications with this name are used to
	 *            add the block to the operation queue. If you pass nil, the notification center doesn’t use a notification’s name to decide
	 *            whether to add the block to the operation queue.
	 * @param obj The object whose notifications you want to add the block to the operation queue. If you pass nil, the notification center
	 *            doesn’t use a notification’s sender to decide whether to add the block to the operation queue.
	 * @param queue The operation queue to which block should be added. If you pass nil, the block is run synchronously on the posting
	 *            thread.
	 * @param block The block to be executed when the notification is received. The block is copied by the notification center and (the
	 *            copy) held until the observer registration is removed. The block takes one argument: notification The notification.
	 * @param notification The notification.
	 * @return Return Value An opaque object to act as the observer.
	 * @Discussion If a given notification triggers more than one observer block, the blocks may all be executed concurrently with respect
	 *             to one another (but on their given queue or on the current thread).
	 **/
	
	public void addObserverForNameObjectQueueUsingBlock(NSString name, NSObject obj,
														NSOperationQueue queue, NSNotificationCenterBlock block) {
		// not yet covered
	}

	/**
	 * @Signature: addObserver:selector:name:object:
	 * @Declaration : - (void)addObserver:(id)notificationObserver selector:(SEL)notificationSelector name:(NSString *)notificationName
	 *              object:(id)notificationSender
	 * @Description : Adds an entry to the receiver’s dispatch table with an observer, a notification selector and optional criteria:
	 *              notification name and sender.
	 * @param notificationObserver Object registering as an observer. This value must not be nil.
	 * @param notificationSelector Selector that specifies the message the receiver sends notificationObserver to notify it of the
	 *            notification posting. The method specified by notificationSelector must have one and only one argument (an instance of
	 *            NSNotification).
	 * @param notificationName The name of the notification for which to register the observer; that is, only notifications with this name
	 *            are delivered to the observer. If you pass nil, the notification center doesn’t use a notification’s name to decide
	 *            whether to deliver it to the observer.
	 * @param notificationSender The object whose notifications the observer wants to receive; that is, only notifications sent by this
	 *            sender are delivered to the observer. If you pass nil, the notification center doesn’t use a notification’s sender to
	 *            decide whether to deliver it to the observer.
	 * @Discussion Be sure to invoke removeObserver:name:object: before notificationObserver or any object specified in
	 *             addObserver:selector:name:object: is deallocated.
	 **/
	
	public void addObserverSelectorNameObject(Object notificationObserver, SEL notificationSelector,
											  NSString notificationName, Object notificationSender) {


		if(notificationObserver == null)return ;
		if(notificationName == null)return;
		boolean notificationExist = false;
		NSNotification notificationCriteria = new NSNotification(notificationName, notificationSender);
		NotificationObserver notificationObsv = new NotificationObserver(notificationObserver,
				notificationSelector);

		for (NSNotification notification : entrysNotification.keySet()) {
			if (notification.equals(notificationCriteria)) {
				entrysNotification.put(notification, notificationObsv);
				notificationExist = true;
				break;
			}
		}

		if (!notificationExist) {
			entrysNotification.put(notificationCriteria, notificationObsv);
		}

		// not yet covered

//		dispatchTable.addObject(entrysNotification);
//
//		Map entrys = NSNotificationCenter.defaultCenter().entrysNotification;
//
//		Set cles = entrys.keySet();
//		Iterator it = cles.iterator();
//		while (it.hasNext()) {
//			NSNotification notification = (NSNotification) it.next();
//
//			NSNotificationCenter.NotificationObserver valeur = (NSNotificationCenter.NotificationObserver) entrys
//					.get(notification);
//			if (valeur.get_observer() != null && valeur.get_selector() != null
//					&& valeur.get_selector().getMethodName() != null
//					&& !valeur.get_selector().getMethodName().isEmpty()) {
//				InvokableHelper.invoke(valeur.get_observer(), valeur.get_selector().getMethodName(),
//						notification);
//			}
//
//		}

		//
		// if (notificationObserver == null || notificationSelector == null) {
		// throw new IllegalArgumentException(
		// "NSNotificationCenter addObserver() requires non null observer and selector parameters");
		// }
		// _checkSelector(notificationSelector);
		//
		// NotificationRegistry registry;
		// NotificationObserver _observer = new NotificationObserver(notificationObserver,
		// notificationSelector);
		// if (notificationSender == null) {
		// registry = _unnamedRegistry;
		// } else if ((registry = (NotificationRegistry) _namedRegistries
		// .objectForKey(notificationSender)) == null) {
		// registry = new NotificationRegistry();
		// _namedRegistries.setObjectForKey(registry, notificationName);
		// }
		// registry.addObserver(_observer, notificationSender);
	}

	/**
	 * @Signature: removeObserver:
	 * @Declaration : - (void)removeObserver:(id)notificationObserver
	 * @Description : Removes all the entries specifying a given observer from the receiver’s dispatch table.
	 * @param notificationObserver The observer to remove. Must not be nil.
	 * @Discussion Be sure to invoke this method (or removeObserver:name:object:) before notificationObserver or any object specified in
	 *             addObserver:selector:name:object: is deallocated. You should not use this method to remove all observers from an object
	 *             that is going to be long-lived, because your code may not be the only code adding observers that involve the object.
	 **/
	
	public void removeObserver(Object notificationObserver) {
		if(notificationObserver == null)
			throw new IllegalArgumentException(
					"Can't remove entry with null observer object from NSNotificationCenter");
		for (NSNotification notification : entrysNotification.keySet()) {
			NotificationObserver notificationObserv = entrysNotification.get(notification);
			if (notificationObserv == notificationObserver) {
				entrysNotification.remove(notification);
				break;
			}
		}
    }

	/**
	 * @Signature: removeObserver:name:object:
	 * @Declaration : - (void)removeObserver:(id)notificationObserver name:(NSString *)notificationName object:(id)notificationSender
	 * @Description : Removes matching entries from the receiver’s dispatch table.
	 * @param notificationObserver Observer to remove from the dispatch table. Specify an observer to remove only entries for this observer.
	 *            Must not be nil, or message will have no effect.
	 * @param notificationName Name of the notification to remove from dispatch table. Specify a notification name to remove only entries
	 *            that specify this notification name. When nil, the receiver does not use notification names as criteria for removal.
	 * @param notificationSender Sender to remove from the dispatch table. Specify a notification sender to remove only entries that specify
	 *            this sender. When nil, the receiver does not use notification senders as criteria for removal.
	 * @Discussion Be sure to invoke this method (or removeObserver:) before the observer object or any object specified in
	 *             addObserver:selector:name:object: is deallocated.
	 **/
	
	public void removeObserverNameObject(Object notificationObserver, NSString notificationName,
										 Object notificationSender) {
		if (notificationObserver == null) {
			throw new IllegalArgumentException(
					"Can't remove entry with null observer object from NSNotificationCenter");
		}

		Set cles = entrysNotification.keySet();
		Iterator it = cles.iterator();
		while (it.hasNext()) {
			NSNotification notification = (NSNotification) it.next();

			NotificationObserver valeur = (NotificationObserver) entrysNotification
					.get(notification);

			if (valeur.get_observer() != null && notificationObserver.equals(valeur)) {
				entrysNotification.remove(notification);
			}

		}

		// if (notificationObserver == null && notificationName == null
		// && notificationSender == null) {
		// throw new IllegalArgumentException(
		// "Can't remove entry with null observer, null name, and null object from NSNotificationCenter");
		// }
		//
		// if (notificationName == null) {
		// for (NSString key : _namedRegistries.getWrappedDictionary().keySet()) {
		// ((NotificationRegistry) _namedRegistries.objectForKey(key))
		// .removeObserver(notificationObserver, notificationSender);
		// }
		//
		// _unnamedRegistry.removeObserver(notificationObserver, notificationSender);
		// } else {
		// ((NotificationRegistry) _namedRegistries.objectForKey(notificationName))
		// .removeObserver(notificationObserver, notificationSender);
		// }

	}

	// Posting Notifications
	/**
	 * @Signature: postNotification:
	 * @Declaration : - (void)postNotification:(NSNotification *)notification
	 * @Description : Posts a given notification to the receiver.
	 * @param notification The notification to post. This value must not be nil.
	 * @Discussion You can create a notification with the NSNotification class method notificationWithName:object: or
	 *             notificationWithName:object:userInfo:. An exception is raised if notification is nil.
	 **/
	
	public void postNotification(NSNotification notification) {
		if (notification == null) {
			throw new IllegalArgumentException("Notification cannot be null");
		}
		NotificationObserver observer = null;
		for(NSNotification notification1 : entrysNotification.keySet()){
			if(notification1.equals(notification)){
				observer = entrysNotification.get(notification1);
				break;
			}
		}
		InvokableHelper.invoke(observer.get_observer(), observer.get_selector().getMethodName(), notification);
	}

	/**
	 * @Signature: postNotificationName:object:
	 * @Declaration : - (void)postNotificationName:(NSString *)notificationName object:(id)notificationSender
	 * @Description : Creates a notification with a given name and sender and posts it to the receiver.
	 * @param notificationName The name of the notification.
	 * @param notificationSender The object posting the notification.
	 * @Discussion This method invokes postNotificationName:object:userInfo: with a userInfo argument of nil.
	 **/
	
	public void postNotificationNameObject(NSString notificationName, NSObject notificationSender) {
		if (notificationName == null) {
			throw new IllegalArgumentException("Notification cannot be null");
		}
		NSNotification notification = new NSNotification(notificationName, notificationSender);
		NotificationObserver observer = null;
		for(NSNotification notification1 : entrysNotification.keySet()){
			if(notification1.equals(notification)){
				observer = entrysNotification.get(notification1);
				break;
			}
		}
		InvokableHelper.invoke(observer.get_observer(), observer.get_selector().getMethodName(), notification);
	}

	/**
	 * @Signature: postNotificationName:object:userInfo:
	 * @Declaration : - (void)postNotificationName:(NSString *)notificationName object:(id)notificationSender userInfo:(NSDictionary
	 *              *)userInfo
	 * @Description : Creates a notification with a given name, sender, and information and posts it to the receiver.
	 * @param notificationName The name of the notification.
	 * @param notificationSender The object posting the notification.
	 * @param userInfo Information about the the notification. May be nil.
	 * @Discussion This method is the preferred method for posting notifications.
	 **/
	
	public void postNotificationNameObjectUserInfo(NSString notificationName,
												   NSObject notificationSender, NSDictionary<NSString, NSObject> userInfo) {
		if (notificationName == null) {
			throw new IllegalArgumentException("Notification cannot be null");
		}
		NSNotification notification = new NSNotification(notificationName, notificationSender, userInfo);
		NotificationObserver observer = null;
		for(NSNotification notification1 : entrysNotification.keySet()){
			if(notification1.equals(notification)){
				observer = entrysNotification.get(notification1);
				break;
			}
		}
		InvokableHelper.invoke(observer.get_observer(), observer.get_selector().getMethodName(), notification);
	}

	@Override
	public String toString() {
		return super.toString();
	}

	/**** Added ****/

//	private static void postNotification(NSNotificationCenter notificationCenter,
//										 NSNotification notification) {
//		notificationCenter._unnamedRegistry.postNotification(notification);
//		try {
//			((NSNotificationCenter) notificationCenter._namedRegistries
//					.objectForKey(notification.name())).postNotification(notification);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	private void _checkSelector(SEL selector) {
//		Class<?>[] parameterTypes = selector.parameterTypes();
//		boolean invalid = false;
//		if (parameterTypes == null || parameterTypes.length != 1) {
//			invalid = true;
//		} else {
//			Class<?> arg = parameterTypes[0];
//			if (!_NSReflectionUtilities.classIsAssignableFrom(arg, NSNotification.class)) {
//				invalid = true;
//			}
//		}
//		if (invalid) {
//			throw new IllegalArgumentException(
//					"NSNotificationCenter addObserver() requires a selector taking a single NSNotification argument");
//		}
//	}

//	private static class NotificationRegistry {
//		private NSMutableDictionary<Integer, NSMutableArray<NotificationObserver>> _objectObservers = new NSMutableDictionary<Integer, NSMutableArray<NotificationObserver>>();
//		private boolean _observerRemoval = false;
//		private NSArray<NotificationObserver> _postingObservers;
//
//		public void addObserver(NotificationObserver observer, Object _object) {
//			Object object = _object;
//			if (object == null) {
//				object = NSKeyValueCoding.NullValue;
//			}
//
//			NSMutableArray<NotificationObserver> observers = (NSMutableArray<NotificationObserver>) _objectObservers
//					.objectForKey(object.hashCode());
//			if (observers == null) {
//				observers = new NSMutableArray<NotificationObserver>();
//				_objectObservers.setObjectForKey(observers, object.hashCode());
//			}
//
//			if (observers == _postingObservers) {
//				_postingObservers = observers.immutableClone();
//			}
//
//			observers.getWrappedList().add(observer);
//		}
//
//		public void removeObserver(Object observer, Object object) {
//			NSMutableArray<Integer> removedKeys = new NSMutableArray<Integer>();
//
//			Collection<Integer> keys;
//			if (object == null) {
//				keys = _objectObservers.getWrappedDictionary().keySet();
//			} else {
//				keys = new HashSet<Integer>(object.hashCode());
//			}
//
//			for (Integer key : keys) {
//				NSArray<NotificationObserver> observers = (NSArray<NotificationObserver>) _objectObservers
//						.objectForKey(key);
//				if (observers != null) {
//					int index = observers.count();
//					while (index-- > 0) {
//						if (observers.objectAtIndex(index).get_observer() == observer) {
//							_observerRemoval = true;
//							if (observers == _postingObservers) {
//								_postingObservers = observers.immutableClone();
//							}
//
//							observers.getWrappedList().remove(index);
//						}
//					}
//				}
//
//				if (observers == null || observers.count() == 0) {
//					removedKeys.getWrappedList().add(key);
//				}
//			}
//
//			for (Integer key : removedKeys.getWrappedList()) {
//				_objectObservers.removeObjectForKey(key);
//			}
//		}
//
//		public void postNotification(NSNotification notification) {
//			Object object = notification.object();
//
//			Collection<Object> keys = new HashSet<Object>();
//			keys.add(NSKeyValueCoding.NullValue.hashCode());
//			if (object != null) {
//				keys.add(object.hashCode());
//			}
//
//			for (Object key : keys) {
//				_postingObservers = (NSArray<NotificationObserver>) _objectObservers
//						.objectForKey(key);
//				if (_postingObservers == null) {
//					continue;
//				}
//
//				NSArray<NotificationObserver> observers = _postingObservers;
//
//				_observerRemoval = false;
//				int index = observers.count();
//				while (index-- > 0) {
//					NotificationObserver observer = _postingObservers.getWrappedList().get(index);
//					if (!_observerRemoval
//							|| observers.indexOfIdenticalObject(observer) != NSArray.NotFound) {
//						observer.postNotification(notification);
//					}
//				}
//			}
//			_postingObservers = null;
//		}
//	}

	public static class NotificationObserver {
		private final Object _observer;
		private final SEL _selector;

		public NotificationObserver(Object observer, SEL selector) {
			_observer = observer;
			_selector = selector;
		}

		public Object get_observer() {
			return _observer;
		}

		public SEL get_selector() {
			return _selector;
		}

		public void postNotification(NSNotification notification) {
			SEL._safeInvokeSelector(_selector, _observer, notification);
		}
	}
}