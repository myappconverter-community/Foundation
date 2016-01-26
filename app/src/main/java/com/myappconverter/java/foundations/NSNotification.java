
package com.myappconverter.java.foundations;

import java.io.Serializable;

import android.content.Context;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;


public class NSNotification extends NSObject implements NSCoding, NSCopying, Serializable {

	private static final long serialVersionUID = -7856133126517622077L;
	// tag identifying the notification.
	private NSString name;
	// object that the poster of the notification wants to send to observers
	private Object object; // event specific data
	// dictionary stores other related objects
	private NSDictionary<NSString, NSObject> userInfo;

	private Context context;

	public NSNotification() {
	}

	public void setUserInfo(NSDictionary<NSString, NSObject> userInfo) {
		this.userInfo = userInfo;
	}

	public NSString getName() {
		return name;
	}

	public void setName(NSString name) {
		this.name = name;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public NSNotification(Context ctx) {
		context = ctx;
	}

	public NSNotification(NSString name, Object object, NSDictionary<?, ?> userInfo) {
		if (name == null) {
			throw new IllegalArgumentException("attempt to create notification without a name");
		}
		this.name = name;
		this.object = object;
		this.userInfo = ((userInfo != null) ? userInfo : NSDictionary.EmptyDictionary);
	}

	public NSNotification(NSString name, Object object) {
		this(name, object, null);
	}

	public NSDictionary<NSString, NSObject> getUserInfo() {
		return userInfo;
	}

	// Creating Notifications
	/**
	 * @Signature: notificationWithName:object:
	 * @Declaration : + (instancetype)notificationWithName:(NSString *)aName object:(id)anObject
	 * @Description : Returns a new notification object with a specified name and object.
	 * @param aName The name for the new notification. May not be nil.
	 * @param anObject The object for the new notification.
	 **/
	
	public static NSNotification notificationWithNameObject(NSString aName, NSObject anObject) {
		return notificationWithNameObjectUserInfo(aName, anObject, null);
	}

	/**
	 * @Signature: notificationWithName:object:userInfo:
	 * @Declaration : + (instancetype)notificationWithName:(NSString *)aName object:(id)anObject userInfo:(NSDictionary *)userInfo
	 * @Description : Returns a notification object with a specified name, object, and user information.
	 * @param aName The name for the new notification. May not be nil.
	 * @param anObject The object for the new notification.
	 * @param userInfo The user information dictionary for the new notification. May be nil.
	 **/
	
	public static NSNotification notificationWithNameObjectUserInfo(NSString aName,
			NSObject anObject, NSDictionary userInfo) {
		if (aName == null) {
			throw new IllegalArgumentException("attempt to create notification without a name");
		}
		NSNotification nsNotification = new NSNotification();
		nsNotification.name = aName;
		nsNotification.object = anObject;
		nsNotification.userInfo = ((userInfo != null) ? userInfo : new NSDictionary());
		return nsNotification;
	}

	/**
	 * @Signature: initWithName:object:userInfo:
	 * @Declaration : - (instancetype)initWithName:(NSString *)aName object:(id)object userInfo:(NSDictionary *)userInfo
	 * @Description : Initializes a notification with a specified name, object, and user information.
	 * @param aName The name for the new notification. May not be nil.
	 * @param object The object for the new notification.
	 * @param userInfo The user information dictionary for the new notification. May be nil.
	 **/
	
	public void initWithNameObjectUserInfo(NSString aName, NSObject object, NSDictionary userInfo) {
		if (aName == null) {
			throw new IllegalArgumentException("attempt to create notification without a name");
		}
		name = aName;
		this.object = object;
		this.userInfo = ((userInfo != null) ? userInfo : new NSDictionary());
	}

	// Getting Notification Information
	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name of the notification.
	 * @return Return Value The name of the notification. Typically you use this method to find out what kind of notification you are
	 *         dealing with when you receive a notification.
	 **/
	
	public NSString name() {
		return name;
	}

	/**
	 * @Signature: object
	 * @Declaration : - (id)object
	 * @Description : Returns the object associated with the notification.
	 * @return Return Value The object associated with the notification. This is often the object that posted this notification. It may be
	 *         nil. Typically you use this method to find out what object a notification applies to when you receive a notification.
	 * @Discussion For example, suppose you’ve registered an object to receive the message handlePortDeath: when the “PortInvalid
	 *             notification is posted to the notification center and that handlePortDeath: needs to access the object monitoring the
	 *             port that is now invalid. handlePortDeath: can retrieve that object as shown here: -
	 *             (void)handlePortDeath:(NSNotification *)notification { ... [self reclaimResourcesForPort:[notification object]]; ... }
	 **/
	
	public Object object() {
		return object;
	}

	/**
	 * @Signature: userInfo
	 * @Declaration : - (NSDictionary *)userInfo
	 * @Description : Returns the user information dictionary associated with the receiver.
	 * @return Return Value Returns the user information dictionary associated with the receiver. May be nil. The user information
	 *         dictionary stores any additional objects that objects receiving the notification might use.
	 * @Discussion For example, in the Application Kit, NSControl objects post the NSControlTextDidChangeNotification whenever the field
	 *             editor (an NSText object) changes text inside the NSControl. This notification provides the NSControl object as the
	 *             notification's associated object. In order to provide access to the field editor, the NSControl object posting the
	 *             notification adds the field editor to the notification's user information dictionary. Objects receiving the notification
	 *             can access the field editor and the NSControl object posting the notification as follows: -
	 *             (void)controlTextDidBeginEditing:(NSNotification *)notification { NSText *fieldEditor = [[notification userInfo]
	 *             objectForKey:@"NSFieldEditor"]; // the field editor NSControl *postingObject = [notification object]; // the object that
	 *             posted the notification ... }
	 **/
	
	public NSDictionary<NSString, NSObject> userInfo() {
		return userInfo;
	}

	// @Override
	// public boolean equals(NSObject anObject) {
	// if (anObject == this) {
	// return true;
	// }
	//
	// if (anObject instanceof NSNotification) {
	// NSNotification otherNotification = (NSNotification) anObject;
	// if ((name().equals(otherNotification.name())) && (object() == otherNotification.object())) {
	// NSDictionary<?, ?> userInfo = userInfo();
	// NSDictionary<?, ?> otherUserInfo = otherNotification.userInfo();
	// if (userInfo != otherUserInfo) {
	// return (((userInfo == null) || (otherUserInfo == null)) ? false : userInfo.equals(otherUserInfo));
	// }
	// return true;
	// }
	// }
	// return false;
	// }

	@Override
	public String toString() {
		if ((object == null) && (userInfo == null)) {
			return "<" + getClass().toString() + "(name=" + name() + ")>";
		}
		if (object == null) {
			return "<" + getClass().toString() + "(name=" + name() + ", userInfo=" + userInfo
					+ ")>";
		}
		if (userInfo == null) {
			return "<" + getClass().toString() + "(name=" + name() + ", object=" + object + ")>";
		}

		return "<" + getClass().toString() + "(name=" + name() + ", object=" + object
				+ ", userInfo=" + userInfo + ")>";
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
		encoder.encodeObject(name());
		encoder.encodeObject(object());
		encoder.encodeObject(userInfo());

	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof NSNotification) {
			NSNotification otherNotification = (NSNotification) other;
			if (name().equals(otherNotification.name())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Class<?> classForCoder() {
		return getClass();
	}

	public static Object decodeObject(NSCoder coder) {
		NSString name = (NSString) coder.decodeObject();
		NSObject object = coder.decodeObject();
		NSDictionary<?, ?> userInfo = (NSDictionary<?, ?>) coder.decodeObject();

		return new NSNotification(name, object, userInfo);
	}

	@Override
	public Object copyWithZone(NSZone zone) {
		return null;
	}
}