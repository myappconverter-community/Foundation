
package com.myappconverter.java.foundations;



public abstract class NSProxy implements com.myappconverter.java.foundations.protocols.NSObject {

	// Creating Instances

	/**
	 * @Signature: alloc
	 * @Declaration : + (id)alloc
	 * @Description : Returns a new instance of the receiving class
	 **/

	public static Object alloc() {
		return null;
	}

	/**
	 * @Signature: allocWithZone:
	 * @Declaration : + (id)allocWithZone:(NSZone *)zone
	 * @Description : Returns a new instance of the receiving class
	 * @return Return Value A new instance of the receiving class, as described in the NSObject class specification under the allocWithZone:
	 *         class method.
	 **/

	public static Object allocWithZone(NSZone zone) {
		return zone;
	}

	/**
	 * @Signature: class
	 * @Declaration : + (Class)class
	 * @Description : Returns self (the class object).
	 * @return Return Value self. Because this is a class method, it returns the class object
	 **/

	public static Class<? extends NSObject> __class() {
		return null;
	}

	/**
	 * @Signature: respondsToSelector:
	 * @Declaration : + (BOOL)respondsToSelector:(SEL)aSelector
	 * @Description : Returns a Boolean value that indicates whether the receiving class responds to a given selector.
	 * @param aSelector A selector.
	 * @return Return Value YES if the receiving class responds to aSelector messages, otherwise NO.
	 **/
	@Override

	public boolean respondsToSelector(SEL aSelector) {
		return false;
	}

	/**
	 * @Signature: dealloc
	 * @Declaration : - (void)dealloc
	 * @Description : Deallocates the memory occupied by the receiver.
	 * @Discussion This method behaves as described in the NSObject class specification under the dealloc instance method.
	 **/

	public void dealloc() {
	}

	/**
	 * @Signature: debugDescription
	 * @Declaration : - (NSString *)debugDescription
	 * @Description : Returns an string containing the real class name and the id of the receiver as a hexadecimal number.
	 * @return Return Value A string object containing the real class name and the id of the receiver as a hexadecimal number.
	 * @Discussion This allows this method declared by the NSObject protocol to be implemented in developer classes without causing an issue
	 *             to be flagged in the iOS and Mac app stores, where otherwise it might look like the developer is overriding a private
	 *             method in the Foundation Kit. Implementation of this method is optional, so be sure to test for its existence before
	 *             attempting to invoke it.
	 **/
	@Override

	public NSString debugDescription() {
		return null;
	}

	/**
	 * @Signature: description
	 * @Declaration : - (NSString *)description
	 * @Description : Returns an NSString object containing the real class name and the id of the receiver as a hexadecimal number.
	 * @return Return Value An NSString object containing the real class name and the id of the receiver as a hexadecimal number.
	 **/
	@Override

	public NSString description() {
		return null;
	}

	/**
	 * @throws Throwable
	 * @Signature: finalize
	 * @Declaration : - (void)finalize
	 * @Description : The garbage collector invokes this method on the receiver before disposing of the memory it uses.
	 * @Discussion This method behaves as described in the NSObject class specification under the finalize instance method. Note that a
	 *             finalize method must be thread-safe.
	 **/
	// 
	// public void finalize() throws Throwable {
	// super.finalize();
	// }

	/**
	 * @Signature: forwardInvocation:
	 * @Declaration : - (void)forwardInvocation:(NSInvocation *)anInvocation
	 * @Description : Passes a given invocation to the real object the proxy represents.
	 * @param anInvocation The invocation to forward.
	 * @Discussion NSProxy’s implementation merely raises NSInvalidArgumentException. Override this method in your subclass to handle
	 *             anInvocation appropriately, at the very least by setting its return value. For example, if your proxy merely forwards
	 *             messages to an instance variable named realObject, it can implement forwardInvocation: like this: -
	 *             (void)forwardInvocation:(NSInvocation *)anInvocation { [anInvocation setTarget:realObject]; [anInvocation invoke];
	 *             return; }
	 **/

	public void forwardInvocation(NSInvocation anInvocation) {
	}

	/**
	 * @Signature: methodSignatureForSelector:
	 * @Declaration : - (NSMethodSignature *)methodSignatureForSelector:(SEL)aSelector
	 * @Description : Raises NSInvalidArgumentException. Override this method in your concrete subclass to return a proper NSMethodSignature
	 *              object for the given selector and the class your proxy objects stand in for.
	 * @param aSelector The selector for which to return a method signature.
	 * @return Return Value Not applicable. The implementation provided by NSProxy raises an exception.
	 * @Discussion Be sure to avoid an infinite loop when necessary by checking that aSelector isn’t the selector for this method itself and
	 *             by not sending any message that might invoke this method. For example, if your proxy merely forwards messages to an
	 *             instance variable named realObject, it can implement methodSignatureForSelector: like this: – (NSMethodSignature
	 *             *)methodSignatureForSelector:(SEL)aSelector { return [realObject methodSignatureForSelector:aSelector]; }
	 **/

	public NSMethodSignature methodSignatureForSelector(SEL aSelector) {
		return null;
	}

}