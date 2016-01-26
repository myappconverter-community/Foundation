
package com.myappconverter.java.foundations;

import java.util.Set;



class NSNotificationQueue extends NSObject {
	// Notification Queue
	private static final NSNotificationQueue _defaultQueue = new NSNotificationQueue();
	// Notification center
	private transient final NSNotificationCenter _center;
	private transient final NSMutableArray<NSNotificationQueueEntry> _asapQueue = new NSMutableArray<NSNotificationQueueEntry>();
	private transient final NSMutableArray<NSNotificationQueueEntry> _idleQueue = new NSMutableArray<NSNotificationQueueEntry>();

	
	enum NSNotificationCoalescing {
		NSNotificationNoCoalescing(0), //
		NSNotificationCoalescingOnName(1), //
		NSNotificationCoalescingOnSender(2);
		byte value;

		NSNotificationCoalescing(int v) {
			value = (byte) v;
		}

		NSNotificationCoalescing(byte v) {
			value = v;
		}

		@Override
		public String toString() {
			return "" + value;
		}
	}

	
	enum NSPostingStyle {
		NSPostWhenIdle(1), //
		NSPostASAP(2), //
		NSPostNow(3);
		int value;

		NSPostingStyle(int v) {
			value = v;
		}
	}

	// Creating Notification Queues

	/**
	 * @Signature: initWithNotificationCenter:
	 * @Declaration : - (id)initWithNotificationCenter:(NSNotificationCenter *)notificationCenter
	 * @Description : Initializes and returns a notification queue for the specified notification center.
	 * @param notificationCenter The notification center used by the new notification queue.
	 * @return Return Value The newly initialized notification queue.
	 * @Discussion This is the designated initializer for the NSNotificationQueue class.
	 **/
	
	public NSNotificationQueue initWithNotificationCenter(NSNotificationCenter notificationCenter) {
		return new NSNotificationQueue(notificationCenter);
	}

	// Getting the Default Queue
	/**
	 * @Signature: defaultQueue
	 * @Declaration : + (id)defaultQueue
	 * @Description : Returns the default notification queue for the current thread.
	 * @return Return Value Returns the default notification queue for the current thread. This notification queue uses the default
	 *         notification center.
	 **/
	
	public static NSNotificationQueue defaultQueue() {
		return _defaultQueue;
	}

	// Managing Notifications
	/**
	 * @Signature: enqueueNotification:postingStyle:
	 * @Declaration : - (void)enqueueNotification:(NSNotification *)notification postingStyle:(NSPostingStyle)postingStyle
	 * @Description : Adds a notification to the notification queue with a specified posting style.
	 * @param notification The notification to add to the queue.
	 * @param postingStyle The posting style for the notification. The posting style indicates when the notification queue should post the
	 *            notification to its notification center.
	 * @Discussion Notifications added with this method are posted using the runloop mode NSDefaultRunLoopMode and coalescing criteria that
	 *             will coalesce only notifications that match both the notification’s name and object. This method invokes
	 *             enqueueNotification:postingStyle:coalesceMask:forModes:.
	 **/
	
	public void enqueueNotificationPostingStyle(NSNotification notification, NSPostingStyle postingStyle) {
		// not yet covered
	}

	/**
	 * @Signature: enqueueNotification:postingStyle:coalesceMask:forModes:
	 * @Declaration : - (void)enqueueNotification:(NSNotification *)notification postingStyle:(NSPostingStyle)postingStyle
	 *              coalesceMask:(NSUInteger)coalesceMask forModes:(NSArray *)modes
	 * @Description : Adds a notification to the notification queue with a specified posting style, criteria for coalescing, and runloop
	 *              mode.
	 * @param notification The notification to add to the queue.
	 * @param postingStyle The posting style for the notification. The posting style indicates when the notification queue should post the
	 *            notification to its notification center.
	 * @param coalesceMask A mask indicating what criteria to use when matching attributes of notification to attributes of notifications in
	 *            the queue. The mask is created by combining any of the constants NSNotificationNoCoalescing,
	 *            NSNotificationCoalescingOnName, and NSNotificationCoalescingOnSender.
	 * @param modes The list of modes the notification may be posted in. The notification queue will only post the notification to its
	 *            notification center if the run loop is in one of the modes provided in the array. May be nil, in which case it defaults to
	 *            NSDefaultRunLoopMode.
	 **/
	
	public void enqueueNotificationPostingStyleCoalesceMaskForModes(NSNotification notification, NSPostingStyle postingStyle,
			NSNotificationCoalescing coalesceMask, NSArray modes) {

		if (postingStyle == null) {
			throw new IllegalArgumentException("postingStyle cannot be null");
		}
		if (postingStyle == NSPostingStyle.NSPostNow) {
			_center.postNotification(notification);
		} else {
			NSMutableArray<NSNotificationQueueEntry> queue = null;

			if (postingStyle == NSPostingStyle.NSPostWhenIdle) {
				queue = _idleQueue;
			} else {
				queue = _asapQueue;
			}
			// not yet covered

			// coalesceNotificationInQueueWithCoalesceMask(notification, queue, coalesceMask);
			// if (canPlaceNotificationInQueueWithCoalesceMask(notification, queue, coalesceMask)) {
			// queue.addObject(new NSNotificationQueueEntry(notification, modes));
			// }
		}

	}

	/**
	 * @Signature: dequeueNotificationsMatching:coalesceMask:
	 * @Declaration : - (void)dequeueNotificationsMatching:(NSNotification *)notification coalesceMask:(NSUInteger)coalesceMask
	 * @Description : Removes all notifications from the queue that match a provided notification using provided matching criteria.
	 * @param notification The notification used for matching notifications to remove from the notification queue.
	 * @param coalesceMask A mask indicating what criteria to use when matching attributes of notification to attributes of notifications in
	 *            the queue. The mask is created by combining any of the constants NSNotificationNoCoalescing,
	 *            NSNotificationCoalescingOnName, and NSNotificationCoalescingOnSender.
	 **/
	
	public void dequeueNotificationsMatchingCoalesceMask(NSNotification notification, int coalesceMask) {
        // not yet covered
	}

	/*** Added ****/

	public static NSNotificationQueue defaultList() {
		return _defaultQueue;
	}

	public NSNotificationQueue() {
		this(NSNotificationCenter.defaultCenter());
	}

	private class NSNotificationQueueEntry {
		private final NSArray<String> _modes;
		private final NSNotification _notification;

		NSNotificationQueueEntry(NSNotification notification, NSArray<String> modes) {
			_notification = notification;
			_modes = modes;
		}

		public NSNotification notification() {
			return _notification;
		}

		public NSArray<String> modes() {
			return _modes;
		}
	}

	public void asapProcessMode(String mode) {
		boolean hasNotifications = false;

		do {
			int count = _asapQueue.count();

			hasNotifications = false;
			for (int i = 0; i < count && !hasNotifications; i++) {
				NSNotificationQueueEntry qd = _asapQueue.objectAtIndex(i);
				NSArray<String> modes = qd.modes();

				if (modes == null || modes.containsObject(mode)) {
					_asapQueue.removeObjectAtIndex(i);

					hasNotifications = true;
					_center.postNotification(qd.notification());
				}
			}
		} while (hasNotifications);
	}

	public boolean hasIdleNotificationsInMode(String mode) {
		int count = _idleQueue.count();

		while (--count >= 0) {
			NSNotificationQueueEntry check = _idleQueue.objectAtIndex(count);
			NSArray<String> modes = check.modes();
			if (modes == null || modes.containsObject(mode)) {
				return true;
			}
		}
		return false;
	}

	public void idleProcessMode(String mode) {
		NSMutableArray<NSNotificationQueueEntry> idle = new NSMutableArray<NSNotificationQueueEntry>();
		int count = _idleQueue.count();

		while (--count >= 0) {
			NSNotificationQueueEntry check = _idleQueue.objectAtIndex(count);
			NSArray<String> modes = check.modes();

			if (modes == null || modes.containsObject(mode)) {
				idle.addObject(check);
				_idleQueue.removeObjectAtIndex(count);
			}
		}

		count = idle.count();
		while (--count >= 0) {
			NSNotificationQueueEntry check = idle.objectAtIndex(count);

			_center.postNotification(check.notification());
		}
	}

	protected void coalesceNotificationInQueueWithCoalesceMask(NSNotification notification, NSMutableArray<NSNotificationQueueEntry> queue,
			Set<NSNotificationCoalescing> coalesceMask) {
		if (coalesceMask != null && !coalesceMask.isEmpty()) {
			int count = queue.count();

			while (--count >= 0) {
				NSNotification check = queue.objectAtIndex(count).notification();
				boolean remove = false;

				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnName) &&
                        check.name().equals(notification.name())) {
						remove = true;
				}

				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnSender) &&
                        check.object() == notification.object()) {
						remove = true;
				}

				if (remove) {
					queue.removeObjectAtIndex(count);
				}
			}
		}
	}

	protected boolean canPlaceNotificationInQueueWithCoalesceMask(NSNotification notification,
			NSMutableArray<NSNotificationQueueEntry> queue, Set<NSNotificationCoalescing> coalesceMask) {
		if (coalesceMask != null && !coalesceMask.isEmpty()) {
			int count = queue.count();
			for (int i = 0; i < count; i++) {
				NSNotification check = queue.objectAtIndex(i).notification();

				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnName) &&
                        check.name().equals(notification.name())) {
						return false;
				}

				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnSender) &&
                        check.name().equals(notification.object())) {
						return false;
				}
			}
		}
		return true;
	}

	public NSNotificationQueue(NSNotificationCenter notificationCenter) {
		_center = notificationCenter;
	}

	protected void dequeueNotificationInQueueWithCoalesceMask(NSNotification notification, NSMutableArray<NSNotificationQueueEntry> queue,
			Set<NSNotificationCoalescing> coalesceMask) {
		int count = queue.count();

		while (--count >= 0) {
			NSNotification check = queue.objectAtIndex(count).notification();
			boolean remove = false;

			if (coalesceMask == null || coalesceMask.isEmpty() && notification.equals(check)) {
					remove = true;
			} else {
				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnName) &&
                        check.name().equals(notification.name())) {
						remove = true;
				}

				if (coalesceMask.contains(NSNotificationCoalescing.NSNotificationCoalescingOnSender) &&
                        check.object() == notification.object()) {
						remove = true;
				}
			}
			if (remove) {
				queue.removeObjectAtIndex(count);
			}
		}
	}

}