package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSString;
import com.myappconverter.java.foundations.NSZone;
import com.myappconverter.java.foundations.SEL;

/**
 * Overview The NSObject protocol groups methods that are fundamental to all Objective-C objects. If an object conforms to this protocol, it
 * can be considered a first-class object. Such an object can be asked about its: Class, and the place of its class in the inheritance
 * hierarchy Conformance to protocols Ability to respond to a particular message The Cocoa root class, NSObject, adopts this protocol, so
 * all objects inheriting from NSObject have the features described by this protocol.
 */

public interface NSObject {

	public Object init();

	public Object copy();

	/**
	 * @Signature: autorelease
	 * @Declaration : - (id)autorelease
	 * @Description : Decrements the receiver’s retain count at the end of the current autorelease pool block. (required)
	 * @return Return Value self.
	 * @Discussion For more information about autorelease pool blocks, see Advanced Memory Management Programming Guide.
	 **/

	public Object autorelease();

	/**
	 * @Signature: class
	 * @Declaration : - (Class)class
	 * @Description : Returns the class object for the receiver’s class. (required)
	 * @return Return Value The class object for the receiver’s class.
	 **/

	public Class<?> _class();

	/**
	 * @Signature: conformsToProtocol:
	 * @Declaration : - (BOOL)conformsToProtocol:(Protocol *)aProtocol
	 * @Description : Returns a Boolean value that indicates whether the receiver conforms to a given protocol. (required)
	 * @param aProtocol A protocol object that represents a particular protocol.
	 * @return Return Value YES if the receiver conforms to aProtocol, otherwise NO.
	 * @Discussion This method works identically to the conformsToProtocol: class method declared in NSObject. It’s provided as a
	 *             convenience so that you don’t need to get the class object to find out whether an instance can respond to a given set of
	 *             messages.
	 **/

	public boolean conformsToProtocol(Protocol aProtocol);


	public boolean conformsToProtocol(Class<?> aProtocol);

	/**
	 * @Signature: debugDescription
	 * @Declaration : - (NSString *)debugDescription
	 * @Description : Returns a string that describes the contents of the receiver for presentation in the debugger.
	 * @return Return Value A string that describes the contents of the receiver for presentation in the debugger.
	 * @Discussion The debugger’s print-object command invokes this method to produce a textual description of an object. NSObject
	 *             implements this method by calling through to the description method. Thus, by default, an object’s debug description is
	 *             the same as its description. However, you can override debugDescription if you want to decouple these.
	 **/

	public NSString debugDescription();

	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns a string that describes the contents of the receiver. (required)
	 * @return Return Value A string that describes the contents of the receiver.
	 * @Discussion This method is used to create a textual representation of an object,
	 **/

	public NSString description();

	/**
	 * @Signature: hash
	 * @Declaration : - (NSUInteger)hash
	 * @Description : Returns an integer that can be used as a table address in a hash table structure. (required)
	 * @return Return Value An integer that can be used as a table address in a hash table structure.
	 * @Discussion If two objects are equal (as determined by the isEqual: method), they must have the same hash value. This last point is
	 *             particularly important if you define hash in a subclass and intend to put instances of that subclass into a collection.
	 *             If a mutable object is added to a collection that uses hash values to determine the object’s position in the collection,
	 *             the value returned by the hash method of the object must not change while the object is in the collection. Therefore,
	 *             either the hash method must not rely on any of the object’s internal state information or you must make sure the object’s
	 *             internal state information does not change while the object is in the collection. Thus, for example, a mutable dictionary
	 *             can be put in a hash table but you must not change it while it is in there. (Note that it can be difficult to know
	 *             whether or not a given object is in a collection.)
	 **/

	public int hash();

	/**
	 * @Signature: isEqual:
	 * @Declaration : - (BOOL)isEqual:(id)anObject
	 * @Description : Returns a Boolean value that indicates whether the receiver and a given object are equal. (required)
	 * @param anObject The object to be compared to the receiver. May be nil, in which case this method returns NO.
	 * @return Return Value YES if the receiver and anObject are equal, otherwise NO.
	 * @Discussion This method defines what it means for instances to be equal. For example, a container object might define two containers
	 *             as equal if their corresponding objects all respond YES to an isEqual: request. See the NSData, NSDictionary, NSArray,
	 *             and NSString class specifications for examples of the use of this method. If two objects are equal, they must have the
	 *             same hash value. This last point is particularly important if you define isEqual: in a subclass and intend to put
	 *             instances of that subclass into a collection. Make sure you also define hash in your subclass.
	 **/

	public boolean isEqual(Object anObject);

	/**
	 * @Signature: isKindOfClass:
	 * @Declaration : - (BOOL)isKindOfClass:(Class)aClass
	 * @Description : Returns a Boolean value that indicates whether the receiver is an instance of given class or an instance of any class
	 *              that inherits from that class. (required)
	 * @param aClass A class object representing the Objective-C class to be tested.
	 * @return Return Value YES if the receiver is an instance of aClass or an instance of any class that inherits from aClass, otherwise
	 *         NO.
	 * @Discussion For example, in this code, isKindOfClass: would return YES because, in Foundation, the NSArchiver class inherits from
	 *             NSCoder: NSMutableData *myData = [NSMutableData dataWithCapacity:30]; id anArchiver = [[NSArchiver alloc]
	 *             initForWritingWithMutableData:myData]; if ( [anArchiver isKindOfClass:[NSCoder class]] ) ... Be careful when using this
	 *             method on objects represented by a class cluster. Because of the nature of class clusters, the object you get back may
	 *             not always be the type you expected. If you call a method that returns a class cluster, the exact type returned by the
	 *             method is the best indicator of what you can do with that object. For example, if a method returns a pointer to an
	 *             NSArray object, you should not use this method to see if the array is mutable, as shown in the following code: // DO NOT
	 *             DO THIS! if ([myArray isKindOfClass:[NSMutableArray class]]) { // Modify the object } If you use such constructs in your
	 *             code, you might think it is alright to modify an object that in reality should not be modified. Doing so might then
	 *             create problems for other code that expected the object to remain unchanged. If the receiver is a class object, this
	 *             method returns YES if aClass is a Class object of the same type, NO otherwise.
	 **/

	public boolean isKindOfClass(Class<?> aClass);

	/**
	 * @Signature: isMemberOfClass:
	 * @Declaration : - (BOOL)isMemberOfClass:(Class)aClass
	 * @Description : Returns a Boolean value that indicates whether the receiver is an instance of a given class. (required)
	 * @param aClass A class object representing the Objective-C class to be tested.
	 * @return Return Value YES if the receiver is an instance of aClass, otherwise NO.
	 * @Discussion For example, in this code, isMemberOfClass: would return NO: NSMutableData *myData = [NSMutableData dataWithCapacity:30];
	 *             id anArchiver = [[NSArchiver alloc] initForWritingWithMutableData:myData]; if ([anArchiver isMemberOfClass:[NSCoder
	 *             class]]) ... Class objects may be compiler-created objects but they still support the concept of membership. Thus, you
	 *             can use this method to verify that the receiver is a specific Class object.
	 **/

	public boolean isMemberOfClass(Class<?> aClass);

	/**
	 * @Signature: isProxy
	 * @Declaration : - (BOOL)isProxy
	 * @Description : Returns a Boolean value that indicates whether the receiver does not descend from NSObject. (required)
	 * @return Return Value NO if the receiver really descends from NSObject, otherwise YES.
	 * @Discussion This method is necessary because sending isKindOfClass: or isMemberOfClass: to an NSProxy object will test the object the
	 *             proxy stands in for, not the proxy itself. Use this method to test if the receiver is a proxy (or a member of some other
	 *             root class).
	 **/

	public boolean isProxy();

	/**
	 * @Signature: performSelector:
	 * @Declaration : - (id)performSelector:(SEL)aSelector
	 * @Description : Sends a specified message to the receiver and returns the result of the message. (required)
	 * @param aSelector A selector identifying the message to send. If aSelector is NULL, an NSInvalidArgumentException is raised.
	 * @return Return Value An object that is the result of the message.
	 * @Discussion The performSelector: method is equivalent to sending an aSelector message directly to the receiver. For example, all
	 *             three of the following messages do the same thing: id myClone = [anObject copy]; id myClone = [anObject
	 *             performSelector:@selector(copy)]; id myClone = [anObject performSelector:sel_getUid("copy")]; However, the
	 *             performSelector: method allows you to send messages that aren’t determined until runtime. A variable selector can be
	 *             passed as the argument: SEL myMethod = findTheAppropriateSelectorForTheCurrentSituation(); [anObject
	 *             performSelector:myMethod]; The aSelector argument should identify a method that takes no arguments. For methods that
	 *             return anything other than an object, use NSInvocation.
	 **/

	public Object performSelector(SEL aSelector);

	/**
	 * @Signature: performSelector:withObject:
	 * @Declaration : - (id)performSelector:(SEL)aSelector withObject:(id)anObject
	 * @Description : Sends a message to the receiver with an object as the argument. (required)
	 * @param aSelector A selector identifying the message to send. If aSelector is NULL, an NSInvalidArgumentException is raised.
	 * @param anObject An object that is the sole argument of the message.
	 * @return Return Value An object that is the result of the message.
	 * @Discussion This method is the same as performSelector: except that you can supply an argument for aSelector. aSelector should
	 *             identify a method that takes a single argument of type id. For methods with other argument types and return values, use
	 *             NSInvocation.
	 **/

	public Object performSelectorWithObject(SEL aSelector, Object anObject);

	/**
	 * @Signature: performSelector:withObject:withObject:
	 * @Declaration : - (id)performSelector:(SEL)aSelector withObject:(id)anObject withObject:(id)anotherObject
	 * @Description : Sends a message to the receiver with two objects as arguments. (required)
	 * @param aSelector A selector identifying the message to send. If aSelector is NULL, an NSInvalidArgumentException is raised.
	 * @param anObject An object that is the first argument of the message.
	 * @param anotherObject An object that is the second argument of the message
	 * @return Return Value An object that is the result of the message.
	 * @Discussion This method is the same as performSelector: except that you can supply two arguments for aSelector. aSelector should
	 *             identify a method that can take two arguments of type id. For methods with other argument types and return values, use
	 *             NSInvocation.
	 **/

	public Object performSelectorWithObjectWithObject(SEL aSelector, Object anObject, Object anotherObject);

	/**
	 * @Signature: release
	 * @Declaration : - (oneway void)release
	 * @Description : Decrements the receiver’s reference count. (required)
	 * @Discussion The receiver is sent a dealloc message when its reference count reaches 0. You would only implement this method to define
	 *             your own reference-counting scheme. Such implementations should not invoke the inherited method; that is, they should not
	 *             include a release message to super. For more information on the reference counting mechanism, see Advanced Memory
	 *             Management Programming Guide.
	 **/

	public void release();

	/**
	 * @Signature: respondsToSelector:
	 * @Declaration : - (BOOL)respondsToSelector:(SEL)aSelector
	 * @Description : Returns a Boolean value that indicates whether the receiver implements or inherits a method that can respond to a
	 *              specified message. (required)
	 * @param aSelector A selector that identifies a message.
	 * @return Return Value YES if the receiver implements or inherits a method that can respond to aSelector, otherwise NO.
	 * @Discussion The application is responsible for determining whether a NO response should be considered an error. You cannot test
	 *             whether an object inherits a method from its superclass by sending respondsToSelector: to the object using the super
	 *             keyword. This method will still be testing the object as a whole, not just the superclass’s implementation. Therefore,
	 *             sending respondsToSelector: to super is equivalent to sending it to self. Instead, you must invoke the NSObject class
	 *             method instancesRespondToSelector: directly on the object’s superclass, as illustrated in the following code fragment.
	 *             if( [MySuperclass instancesRespondToSelector:@selector(aMethod)] ) { // invoke the inherited method [super aMethod]; }
	 *             You cannot simply use [[self superclass] instancesRespondToSelector:@selector(aMethod)] since this may cause the method
	 *             to fail if it is invoked by a subclass. Note that if the receiver is able to forward aSelector messages to another
	 *             object, it will be able to respond to the message, albeit indirectly, even though this method returns NO.
	 **/

	public boolean respondsToSelector(SEL aSelector);

	/**
	 * @Signature: retain
	 * @Declaration : - (id)retain
	 * @Description : Increments the receiver’s reference count. (required)
	 * @return Return Value self.
	 * @Discussion You send an object a retain message when you want to prevent it from being deallocated until you have finished using it.
	 *             An object is deallocated automatically when its reference count reaches 0. retain messages increment the reference count,
	 *             and release messages decrement it. For more information on this mechanism, see Advanced Memory Management Programming
	 *             Guide. As a convenience, retain returns self because it may be used in nested expressions. You would implement this
	 *             method only if you were defining your own reference-counting scheme. Such implementations must return self and should not
	 *             invoke the inherited method by sending a retain message to super.
	 **/

	public Object retain();

	/**
	 * @Signature: retainCount
	 * @Declaration : - (NSUInteger)retainCount
	 * @Description : Do not use this method. (required)
	 * @return Return Value The receiver’s reference count.
	 **/

	public int retainCount();

	/**
	 * @Signature: self
	 * @Declaration : - (id)self
	 * @Description : Returns the receiver. (required)
	 * @return Return Value The receiver.
	 **/

	public Object self();

	/**
	 * @Signature: superclass
	 * @Declaration : - (Class)superclass
	 * @Description : Returns the class object for the receiver’s superclass. (required)
	 * @return Return Value The class object for the receiver’s superclass.
	 **/

	public Class<?> superclass();

	/**
	 * @Signature: zone
	 * @Declaration : - (NSZone *)zone
	 * @Description : Zones are deprecated and ignored by most classes that have it as a parameter. (required)
	 * @return Return Value A pointer to the zone from which the receiver was allocated.
	 **/

	public NSZone zone();

}
