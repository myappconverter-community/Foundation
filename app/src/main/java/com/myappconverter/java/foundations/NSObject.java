
package com.myappconverter.java.foundations;

import com.myappconverter.java.corefoundations.CFArrayRef;
import com.myappconverter.java.corefoundations.CFBundleRef;
import com.myappconverter.java.corefoundations.CFData;
import com.myappconverter.java.corefoundations.CFDateRef;
import com.myappconverter.java.corefoundations.CFErrorRef;
import com.myappconverter.java.corefoundations.CFStringRef;
import com.myappconverter.java.corefoundations.CFTypeRef;
import com.myappconverter.java.foundations.protocols.Protocol;
import com.myappconverter.java.foundations.utilities.BinaryPropertyListWriter;
import com.myappconverter.java.foundations.utilities.GenericProxyFactory;
import com.myappconverter.mapping.utils.GenericMainContext;
import com.myappconverter.mapping.utils.InvokableHelper;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TreeSet;
import java.util.logging.Logger;


public class NSObject extends Object
		implements com.myappconverter.java.foundations.protocols.NSObject, Cloneable {

	public static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private Handler mHandler;
	private Handler runOnUiThreadHandler;
	private Context wrappedContext = GenericMainContext.sharedContext;
	private boolean isRunningInUi;
	private HashMap<SEL, Runnable> registredObject = new HashMap<SEL, Runnable>();

	protected static Class<?> isa = NSObject.class;
	private int version;
	private List<Method> classMethods;

	public NSObject() {
	}

	public NSObject(Context ctx) {
		wrappedContext = ctx;
		classMethods = new ArrayList<Method>();
		mHandler = new Handler(wrappedContext.getMainLooper());
		runOnUiThreadHandler = new Handler(wrappedContext.getMainLooper());
	}

	// Initializing a Class
	/**
	 * @Signature: initialize
	 * @Declaration : + (void)initialize
	 * @Description : Initializes the class before it receives its first message.
	 * @Discussion The runtime sends initialize to each class in a program just before the class, or any class that inherits from it, is
	 *             sent its first message from within the program. The runtime sends the initialize message to classes in a thread-safe
	 *             manner. Superclasses receive this message before their subclasses. The superclass implementation may be called multiple
	 *             times if subclasses do not implement initialize—the runtime will call the inherited implementation—or if subclasses
	 *             explicitly call [super initialize]. If you want to protect yourself from being run multiple times, you can structure your
	 *             implementation along these lines: + (void)initialize { if (self == [ClassName self]) { // ... do the initialization ... }
	 *             } Because initialize is called in a thread-safe manner and the order of initialize being called on different classes is
	 *             not guaranteed, it’s important to do the minimum amount of work necessary in initialize methods. Specifically, any code
	 *             that takes locks that might be required by other classes in their initialize methods is liable to lead to deadlocks.
	 *             Therefore you should not rely on initialize for complex initialization, and should instead limit it to straightforward,
	 *             class local initialization.
	 **/
	
	public static void _initialize() {
	}

	
	public void initialize() {
	}

	/**
	 * @Signature: load
	 * @Declaration : + (void)load
	 * @Description : Invoked whenever a class or category is added to the Objective-C runtime; implement this method to perform
	 *              class-specific behavior upon loading.
	 * @Discussion The load message is sent to classes and categories that are both dynamically loaded and statically linked, but only if
	 *             the newly loaded class or category implements a method that can respond. The order of initialization is as follows: All
	 *             initializers in any framework you link to. All +load methods in your image. All C++ static initializers and C/C++
	 *             __attribute__(constructor) functions in your image. All initializers in frameworks that link to you. In addition: A
	 *             class’s +load method is called after all of its superclasses’ +load methods. A category +load method is called after the
	 *             class’s own +load method. In a custom implementation of load you can therefore safely message other unrelated classes
	 *             from the same image, but any load methods implemented by those classes may not have run yet.
	 **/
	
	public static void _load() {
	}

	// Creating, Copying, and Deallocating Objects

	/**
	 * @Signature: alloc
	 * @Declaration : + (id)alloc
	 * @Description : Returns a new instance of the receiving class.
	 * @return Return Value A new instance of the receiver.
	 * @Discussion The isa instance variable of the new instance is initialized to a data structure that describes the class; memory for all
	 *             other instance variables is set to 0. You must use an init... method to complete the initialization process. For example:
	 *             TheClass *newObject = [[TheClass alloc] init]; Do not override alloc to include initialization code. Instead, implement
	 *             class-specific versions of init... methods. For historical reasons, alloc invokes allocWithZone:.
	 **/
	
	protected static Object alloc() {
		try {
			return isa.newInstance();
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return new Object();
	}

	/**
	 * @Signature: allocWithZone:
	 * @Declaration : + (id)allocWithZone:(NSZone *)zone
	 * @Description : Returns a new instance of the receiving class.
	 * @param zone This parameter is ignored.
	 * @return Return Value A new instance of the receiver.
	 * @Discussion The isa instance variable of the new instance is initialized to a data structure that describes the class; memory for all
	 *             other instance variables is set to 0. You must use an init... method to complete the initialization process. For example:
	 *             TheClass *newObject = [[TheClass allocWithZone:nil] init]; Do not override allocWithZone: to include any initialization
	 *             code. Instead, class-specific versions of init... methods. This method exists for historical reasons; memory zones are no
	 *             longer used by Objective-C.
	 **/
	
	public static NSObject allocWithZone(NSZone zone) {
		return new NSObject();
	}

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Implemented by subclasses to initialize a new object (the receiver) immediately after memory for it has been
	 *              allocated.
	 * @return Return Value An initialized object, or nil if an object could not be created for some reason that would not result in an
	 *         exception.
	 * @Discussion An init message is coupled with an alloc (or allocWithZone:) message in the same line of code: TheClass *newObject =
	 *             [[TheClass alloc] init]; An object isn’t ready to be used until it has been initialized. The init method defined in the
	 *             NSObject class does no initialization; it simply returns self. In a custom implementation of this method, you must invoke
	 *             super’s designated initializer then initialize and return the new object. If the new object can’t be initialized, the
	 *             method should return nil. For example, a hypothetical BuiltInCamera class might return nil from its init method if run on
	 *             a device that has no camera. - (id)init { self = [super init]; if (self) { // Initialize self. } return self; } In some
	 *             cases, an init method might return a substitute object. You must therefore always use the object returned by init, and
	 *             not the one returned by alloc or allocWithZone:, in subsequent code.
	 **/
	@Override
	
	public Object init() {
		return this;
	}

	/**
	 * @Signature: copy
	 * @Declaration : - (id)copy
	 * @Description : Returns the object returned by copyWithZone:.
	 * @return Return Value The object returned by the NSCopying protocol method copyWithZone:,.
	 * @Discussion This is a convenience method for classes that adopt the NSCopying protocol. An exception is raised if there is no
	 *             implementation for copyWithZone:. NSObject does not itself support the NSCopying protocol. Subclasses must support the
	 *             protocol and implement the copyWithZone: method. A subclass version of the copyWithZone: method should send the message
	 *             to super first, to incorporate its implementation, unless the subclass descends directly from NSObject.
	 **/
	@Override
	
	public NSObject copy() {
		try {
			return (NSObject) this.clone();
		} catch (CloneNotSupportedException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: copyWithZone:
	 * @Declaration : + (id)copyWithZone:(NSZone *)zone
	 * @Description : Returns the receiver.
	 * @param zone This argument is ignored.
	 * @return Return Value The receiver.
	 * @Discussion This method exists so class objects can be used in situations where you need an object that conforms to the NSCopying
	 *             protocol. For example, this method lets you use a class object as a key to an NSDictionary object. You should not
	 *             override this method.
	 **/
	
	public static NSObject _copyWithZone(NSZone zone) {
		return new NSObject();
	}

	/**
	 * @Signature: mutableCopy
	 * @Declaration : - (id)mutableCopy
	 * @Description : Returns the object returned by mutableCopyWithZone: where the zone is nil.
	 * @return Return Value The object returned by the NSMutableCopying protocol method mutableCopyWithZone:, where the zone is nil.
	 * @Discussion This is a convenience method for classes that adopt the NSMutableCopying protocol. An exception is raised if there is no
	 *             implementation for mutableCopyWithZone:.
	 **/
	
	public NSObject mutableCopy() {
		try {
			return (NSObject) this.clone();
		} catch (CloneNotSupportedException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	/**
	 * @Signature: mutableCopyWithZone:
	 * @Declaration : + (id)mutableCopyWithZone:(NSZone *)zone
	 * @Description : Returns the receiver.
	 * @param zone The memory zone in which to create the copy of the receiver.
	 * @return Return Value The receiver.
	 * @Discussion This method exists so class objects can be used in situations where you need an object that conforms to the
	 *             NSMutableCopying protocol. For example, this method lets you use a class object as a key to an NSDictionary object. You
	 *             should not override this method.
	 **/
	
	public static Object _mutableCopyWithZone(NSZone zone) {
		return null;
	}

	/**
	 * @Signature: dealloc
	 * @Declaration : - (void)dealloc
	 * @Description : Deallocates the memory occupied by the receiver.
	 * @Discussion Subsequent messages to the receiver may generate an error indicating that a message was sent to a deallocated object
	 *             (provided the deallocated memory hasn’t been reused yet). You override this method to dispose of resources other than the
	 *             object’s instance variables, for example: - (void)dealloc { free(myBigBlockOfMemory); } In an implementation of dealloc,
	 *             do not invoke the superclass’s implementation. You should try to avoid managing the lifetime of limited resources such as
	 *             file descriptors using dealloc. You never send a dealloc message directly. Instead, an object’s dealloc method is invoked
	 *             by the runtime. See Advanced Memory Management Programming Guide for more details.
	 **/
	
	public void dealloc() {
	}

	/**
	 * @return
	 * @Signature: new
	 * @Declaration : + (id)new
	 * @Description : Allocates a new instance of the receiving class, sends it an init message, and returns the initialized object.
	 * @return Return Value A new instance of the receiver.
	 * @Discussion This method is a combination of alloc and init. Like alloc, it initializes the isa instance variable of the new object so
	 *             it points to the class data structure. It then invokes the init method to complete the initialization process.
	 **/
	
	public static <T extends NSObject> T _new() {
		try {
			return (T) isa.newInstance();
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return null;
	}

	// Identifying Classes
	/**
	 * @Signature: class
	 * @Declaration : + (Class)class
	 * @Description : Returns the class object.
	 * @return Return Value The class object.
	 * @Discussion Refer to a class only by its name when it is the receiver of a message. In all other cases, the class object must be
	 *             obtained through this or a similar method. For example, here SomeClass is passed as an argument to the isKindOfClass:
	 *             method (declared in the NSObject protocol): BOOL test = [self isKindOfClass:[SomeClass class]];
	 **/
	
	public static Class<?> __class() {
		return isa;
	}

	/**
	 * @Signature: superclass
	 * @Declaration : + (Class)superclass
	 * @Description : Returns the class object for the receiver’s superclass.
	 * @return Return Value The class object for the receiver’s superclass.
	 **/
	
	public static Class<?> _superclass() {
		return isa.getSuperclass();
	}

	/**
	 * @Signature: isSubclassOfClass:
	 * @Declaration : + (BOOL)isSubclassOfClass:(Class)aClass
	 * @Description : Returns a Boolean value that indicates whether the receiving class is a subclass of, or identical to, a given class.
	 * @param aClass A class object.
	 * @return Return Value YES if the receiving class is a subclass of—or identical to—aClass, otherwise NO.
	 **/
	
	public static boolean isSubclassOfClass(Class<?> cls) {
		if (!cls.isInterface() && cls.isAssignableFrom(isa))
			return true;
		return false;
	}

	// Testing Class Functionality

	/**
	 * @Signature: instancesRespondToSelector:
	 * @Declaration : + (BOOL)instancesRespondToSelector:(SEL)aSelector
	 * @Description : Returns a Boolean value that indicates whether instances of the receiver are capable of responding to a given
	 *              selector.
	 * @param aSelector A selector.
	 * @return Return Value YES if instances of the receiver are capable of responding to aSelector messages, otherwise NO.
	 * @Discussion If aSelector messages are forwarded to other objects, instances of the class are able to receive those messages without
	 *             error even though this method returns NO. To ask the class whether it, rather than its instances, can respond to a
	 *             particular message, send to the class instead the NSObject protocol instance method respondsToSelector:.
	 **/
	
	public static boolean instancesRespondToSelector(SEL aSelector) {
		try {
			return SEL.respondsToSelector(isa.newInstance(), aSelector);
		} catch (InstantiationException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;
	}

	// Testing Protocol Conformance

	/**
	 * @Signature: conformsToProtocol:
	 * @Declaration : + (BOOL)conformsToProtocol:(Protocol *)aProtocol
	 * @Description : Returns a Boolean value that indicates whether the receiver conforms to a given protocol.
	 * @param aProtocol A protocol.
	 * @return Return Value YES if the receiver conforms to aProtocol, otherwise NO.
	 * @Discussion A class is said to “conform to�? a protocol if it adopts the protocol or inherits from another class that adopts it.
	 *             Protocols are adopted by listing them within angle brackets after the interface declaration. For example, here MyClass
	 *             adopts the (fictitious) AffiliationRequests and Normalization protocols: @interface MyClass : NSObject
	 *             <AffiliationRequests, Normalization> A class also conforms to any protocols that are incorporated in the protocols it
	 *             adopts or inherits. Protocols incorporate other protocols in the same way classes adopt them. For example, here the
	 *             AffiliationRequests protocol incorporates the Joining protocol: @protocol AffiliationRequests <Joining> If a class adopts
	 *             a protocol that incorporates another protocol, it must also implement all the methods in the incorporated protocol or
	 *             inherit those methods from a class that adopts it. This method determines conformance solely on the basis of the formal
	 *             declarations in header files, as illustrated above. It doesn’t check to see whether the methods declared in the protocol
	 *             are actually implemented—that’s the programmer’s responsibility. The protocol required as this method’s argument can be
	 *             specified using the @protocol() directive: BOOL canJoin = [MyClass conformsToProtocol:];
	 **/
	@Override
	
	public boolean conformsToProtocol(Class<?> aProtocol) {
		if (!aProtocol.isInterface()) {
			throw new IllegalArgumentException("Not an interface: " + aProtocol.getName());
		} else if (aProtocol.isAssignableFrom(this.getClass()))
			return true;
		return false;
	}

	// Obtaining Information About Methods

	/**
	 * @Signature: methodForSelector:
	 * @Declaration : - (IMP)methodForSelector:(SEL)aSelector
	 * @Description : Locates and returns the address of the receiver’s implementation of a method so it can be called as a function.
	 * @param aSelector A selector that identifies the method for which to return the implementation address. The selector must be a valid
	 *            and non-NULL. If in doubt, use the respondsToSelector: method to check before passing the selector to methodForSelector:.
	 * @return Return Value The address of the receiver’s implementation of the aSelector.
	 * @Discussion If the receiver is an instance, aSelector should refer to an instance method; if the receiver is a class, it should refer
	 *             to a class method.
	 **/
	
	public IMP methodForSelector(SEL aSelector) {
		IMP methodImpl = new IMP();
		Method[] methods = getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			// search method by name
			if (method.getName().equals(aSelector.getMethodName())) {
				methodImpl.setSelector(aSelector);
				methodImpl.setReceiver(this);
				break;
			}
		}
		return methodImpl;
	}

	/**
	 * @Signature: instanceMethodForSelector:
	 * @Declaration : + (IMP)instanceMethodForSelector:(SEL)aSelector
	 * @Description : Locates and returns the address of the implementation of the instance method identified by a given selector.
	 * @param aSelector A selector that identifies the method for which to return the implementation address. The selector must be non-NULL
	 *            and valid for the receiver. If in doubt, use the respondsToSelector: method to check before passing the selector to
	 *            methodForSelector:.
	 * @return Return Value The address of the implementation of the aSelector instance method.
	 * @Discussion An error is generated if instances of the receiver can’t respond to aSelector messages. Use this method to ask the class
	 *             object for the implementation of instance methods only. To ask the class for the implementation of a class method, send
	 *             the methodForSelector: instance method to the class instead.
	 **/
	
	public static IMP instanceMethodForSelector(SEL aSelector) {
		IMP methodImpl = new IMP();
		Method[] methods = null;// getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			// search method by name
			if (method.getName().equalsIgnoreCase(aSelector.getMethodName())
					&& !Modifier.isStatic(method.getModifiers())) {
				methodImpl.setSelector(aSelector);
				break;
			}
		}
		return methodImpl;
	}

	/**
	 * @Signature: instanceMethodSignatureForSelector:
	 * @Declaration : + (NSMethodSignature *)instanceMethodSignatureForSelector:(SEL)aSelector
	 * @Description : Returns an NSMethodSignature object that contains a description of the instance method identified by a given selector.
	 * @param aSelector A selector that identifies the method for which to return the implementation address.
	 * @return Return Value An NSMethodSignature object that contains a description of the instance method identified by aSelector, or nil
	 *         if the method can’t be found.
	 **/
	
	public static NSMethodSignature instanceMethodSignatureForSelector(SEL aSelector) {
		NSMethodSignature nsMethodSignature = new NSMethodSignature();
		Method mMethod = getMethodFromReceiver(aSelector, null);
		if (!Modifier.isStatic(mMethod.getModifiers())) {
			nsMethodSignature.setWrappedMethod(mMethod);
			return nsMethodSignature;
		} else
			return null;
	}

	/**
	 * @Signature: methodSignatureForSelector:
	 * @Declaration : - (NSMethodSignature *)methodSignatureForSelector:(SEL)aSelector
	 * @Description : Returns an NSMethodSignature object that contains a description of the method identified by a given selector.
	 * @param aSelector A selector that identifies the method for which to return the implementation address. When the receiver is an
	 *            instance, aSelector should identify an instance method; when the receiver is a class, it should identify a class method.
	 * @return Return Value An NSMethodSignature object that contains a description of the method identified by aSelector, or nil if the
	 *         method can’t be found.
	 * @Discussion This method is used in the implementation of protocols. This method is also used in situations where an NSInvocation
	 *             object must be created, such as during message forwarding. If your object maintains a delegate or is capable of handling
	 *             messages that it does not directly implement, you should override this method to return an appropriate method signature.
	 **/
	
	public NSMethodSignature methodSignatureForSelector(SEL aSelector) {
		Method method = getMethodFromReceiver(aSelector, null);
		if (Modifier.isStatic(method.getModifiers())) {
			NSMethodSignature methodeSignature = new NSMethodSignature();
			methodeSignature.setWrappedMethod(method);
			return methodeSignature;
		} else
			return null;
	}

	// Describing Objects
	/**
	 * @Signature: description
	 * @Declaration : + (NSString *)description
	 * @Description : Returns a string that represents the contents of the receiving class.
	 * @return Return Value A string that represents the contents of the receiving class.
	 * @Discussion The debugger’s print-object command invokes this method to produce a textual description of an object. NSObject's
	 *             implementation of this method simply prints the name of the class.
	 **/
	
	public static NSString _description() {
		return new NSString(isa.toString());
	}

	// Discardable Content Proxy Support
	/**
	 * @Signature: autoContentAccessingProxy
	 * @Declaration : - (id)autoContentAccessingProxy
	 * @Description : Creates and returns a proxy for the receiving object
	 * @return Return Value A proxy of the receiver.
	 * @Discussion This method creates and returns a proxy for the receiving object if the receiver adopts the NSDiscardableContent protocol
	 *             and still has content that has not been discarded. The proxy calls beginContentAccess on the receiver to keep the content
	 *             available as long as the proxy lives, and calls endContentAccess when the proxy is deallocated. The wrapper object is
	 *             otherwise a subclass of NSProxy and forwards messages to the original receiver object as an NSProxy does. This method can
	 *             be used to hide an NSDiscardableContent object's content volatility by creating an object that responds to the same
	 *             messages but holds the contents of the original receiver available as long as the created proxy lives. Thus hidden, the
	 *             NSDiscardableContent object (by way of the proxy) can be given out to unsuspecting recipients of the object who would
	 *             otherwise not know they might have to call beginContentAccess and endContentAccess around particular usages (specific to
	 *             each NSDiscardableContent object) of the NSDiscardableContent object.
	 **/
	
	public Object autoContentAccessingProxy() {
		return GenericProxyFactory.getProxy(this.getClass(), this);
	}

	// Sending Messages

	/**
	 * @Signature: performSelector:withObject:afterDelay:
	 * @Declaration : - (void)performSelector:(SEL)aSelector withObject:(id)anArgument afterDelay:(NSTimeInterval)delay
	 * @Description : Invokes a method of the receiver on the current thread using the default mode after a delay.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param anArgument The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param delay The minimum time before which the message is sent. Specifying a delay of 0 does not necessarily cause the selector to be
	 *            performed immediately. The selector is still queued on the thread’s run loop and performed as soon as possible.
	 * @Discussion This method sets up a timer to perform the aSelector message on the current thread’s run loop. The timer is configured to
	 *             run in the default mode (NSDefaultRunLoopMode). When the timer fires, the thread attempts to dequeue the message from the
	 *             run loop and perform the selector. It succeeds if the run loop is running and in the default mode; otherwise, the timer
	 *             waits until the run loop is in the default mode. If you want the message to be dequeued when the run loop is in a mode
	 *             other than the default mode, use the performSelector:withObject:afterDelay:inModes: method instead. If you are not sure
	 *             whether the current thread is the main thread, you can use the performSelectorOnMainThread:withObject:waitUntilDone: or
	 *             performSelectorOnMainThread:withObject:waitUntilDone:modes: method to guarantee that your selector executes on the main
	 *             thread. To cancel a queued message, use the cancelPreviousPerformRequestsWithTarget: or
	 *             cancelPreviousPerformRequestsWithTarget:selector:object: method.
	 **/
	
	public void performSelectorWithObjectAfterDelay(SEL aSelector, final Object firstMethodParam,
			double delay) {
		final SEL fSel = aSelector;
		Timer mTimer = new Timer();
		mTimer.schedule(new java.util.TimerTask() {

			@Override
			public void run() {
				try {
					fSel.invokeMethodOnTarget(NSObject.this, fSel.getMethodName(),
							firstMethodParam);
				} catch (Exception e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}

			}
		}, (int) (delay * 1000));
	}

	/**
	 * @Signature: performSelector:withObject:afterDelay:inModes:
	 * @Declaration : - (void)performSelector:(SEL)aSelector withObject:(id)anArgument afterDelay:(NSTimeInterval)delay inModes:(NSArray
	 *              *)modes
	 * @Description : Invokes a method of the receiver on the current thread using the specified modes after a delay.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param anArgument The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param delay The minimum time before which the message is sent. Specifying a delay of 0 does not necessarily cause the selector to be
	 *            performed immediately. The selector is still queued on the thread’s run loop and performed as soon as possible.
	 * @param modes An array of strings that identify the modes to associate with the timer that performs the selector. This array must
	 *            contain at least one string. If you specify nil or an empty array for this parameter, this method returns without
	 *            performing the specified selector. For information about run loop modes, see “Run Loops�? in Threading Programming Guide.
	 * @Discussion This method sets up a timer to perform the aSelector message on the current thread’s run loop. The timer is configured to
	 *             run in the modes specified by the modes parameter. When the timer fires, the thread attempts to dequeue the message from
	 *             the run loop and perform the selector. It succeeds if the run loop is running and in one of the specified modes;
	 *             otherwise, the timer waits until the run loop is in one of those modes. If you want the message to be dequeued when the
	 *             run loop is in a mode other than the default mode, use the performSelector:withObject:afterDelay:inModes: method instead.
	 *             If you are not sure whether the current thread is the main thread, you can use the
	 *             performSelectorOnMainThread:withObject:waitUntilDone: or performSelectorOnMainThread:withObject:waitUntilDone:modes:
	 *             method to guarantee that your selector executes on the main thread. To cancel a queued message, use the
	 *             cancelPreviousPerformRequestsWithTarget: or cancelPreviousPerformRequestsWithTarget:selector:object: method.
	 **/
	
	public void performSelectorWithObjectAfterDelayInModes(Object receiver, SEL aSelector,
			final Object firstMethodParam, double delay, NSArray modes) {
		if (modes == null)
			return;
		// performSelector(receiver, aSelector, firstMethodParam, delay);
	}

	// – performSelectorOnMainThread:withObject:waitUntilDone:

	/**
	 * @Signature: performSelectorOnMainThread:withObject:waitUntilDone:
	 * @Declaration : - (void)performSelectorOnMainThread:(SEL)aSelector withObject:(id)arg waitUntilDone:(BOOL)wait
	 * @Description : Invokes a method of the receiver on the main thread using the default mode.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param arg The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param wait A Boolean that specifies whether the current thread blocks until after the specified selector is performed on the
	 *            receiver on the main thread. Specify YES to block this thread; otherwise, specify NO to have this method return
	 *            immediately. If the current thread is also the main thread, and you specify YES for this parameter, the message is
	 *            delivered and processed immediately.
	 * @Discussion You can use this method to deliver messages to the main thread of your application. The main thread encompasses the
	 *             application’s main run loop, and is where the NSApplication object receives events. The message in this case is a method
	 *             of the current object that you want to execute on the thread. This method queues the message on the run loop of the main
	 *             thread using the common run loop modes—that is, the modes associated with the NSRunLoopCommonModes constant. As part of
	 *             its normal run loop processing, the main thread dequeues the message (assuming it is running in one of the common run
	 *             loop modes) and invokes the desired method. Multiple calls to this method from the same thread cause the corresponding
	 *             selectors to be queued and performed in the same same order in which the calls were made. You cannot cancel messages
	 *             queued using this method. If you want the option of canceling a message on the current thread, you must use either the
	 *             performSelector:withObject:afterDelay: or performSelector:withObject:afterDelay:inModes: method.
	 **/
	
	public void performSelectorOnMainThreadWithObjectWaitUntilDone(SEL aSelector,
			final Object firstMethodParam, boolean wait) {
		final Method method = getMethodFromReceiver(aSelector, firstMethodParam);
		if (method != null) {
			if (wait) {
				synchronized (method) {
					runHandler(aSelector, method, firstMethodParam);
					if (isRunningInUi)
						try {
							runOnUiThreadHandler.wait();
						} catch (InterruptedException e) {
							Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
									+ Log.getStackTraceString(e));
						}
				}
			} else {
				runHandler(aSelector, method, firstMethodParam);
			}
		}

	}

	/**
	 * @Signature: performSelectorOnMainThread:withObject:waitUntilDone:modes:
	 * @Declaration : - (void)performSelectorOnMainThread:(SEL)aSelector withObject:(id)arg waitUntilDone:(BOOL)wait modes:(NSArray *)array
	 * @Description : Invokes a method of the receiver on the main thread using the specified modes.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param arg The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param wait A Boolean that specifies whether the current thread blocks until after the specified selector is performed on the
	 *            receiver on the main thread. Specify YES to block this thread; otherwise, specify NO to have this method return
	 *            immediately. If the current thread is also the main thread, and you pass YES, the message is performed immediately,
	 *            otherwise the perform is queued to run the next time through the run loop.
	 * @param array An array of strings that identifies the modes in which it is permissible to perform the specified selector. This array
	 *            must contain at least one string. If you specify nil or an empty array for this parameter, this method returns without
	 *            performing the specified selector. For information about run loop modes, see “Run Loops�? in Threading Programming Guide.
	 * @Discussion You can use this method to deliver messages to the main thread of your application. The main thread encompasses the
	 *             application’s main run loop, and is where the NSApplication object receives events. The message in this case is a method
	 *             of the current object that you want to execute on the thread. This method queues the message on the run loop of the main
	 *             thread using the run loop modes specified in the array parameter. As part of its normal run loop processing, the main
	 *             thread dequeues the message (assuming it is running in one of the specified modes) and invokes the desired method.
	 *             Multiple calls to this method from the same thread cause the corresponding selectors to be queued and performed in the
	 *             same same order in which the calls were made, assuming the associated run loop modes for each selector are the same. If
	 *             you specify different modes for each selector, any selectors whose associated mode does not match the current run loop
	 *             mode are skipped until the run loop subsequently executes in that mode. You cannot cancel messages queued using this
	 *             method. If you want the option of canceling a message on the current thread, you must use either the
	 *             performSelector:withObject:afterDelay: or performSelector:withObject:afterDelay:inModes: method.
	 **/
	
	public void performSelectorOnMainThreadWithObjectWaitUntilDoneModes(SEL aSelector,
			Object firstMethodParam, boolean wait, NSArray modes) {
		// if (modes != null)
		// performSelectorOnMainThread(aSelector, firstMethodParam, wait);

	}

	/**
	 * @Signature: performSelector:onThread:withObject:waitUntilDone:
	 * @Declaration : - (void)performSelector:(SEL)aSelector onThread:(NSThread *)thread withObject:(id)arg waitUntilDone:(BOOL)wait
	 * @Description : Invokes a method of the receiver on the specified thread using the default mode.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param thread The thread on which to execute aSelector.
	 * @param arg The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param wait A Boolean that specifies whether the current thread blocks until after the specified selector is performed on the
	 *            receiver on the specified thread. Specify YES to block this thread; otherwise, specify NO to have this method return
	 *            immediately. If the current thread and target thread are the same, and you specify YES for this parameter, the selector is
	 *            performed immediately on the current thread. If you specify NO, this method queues the message on the thread’s run loop
	 *            and returns, just like it does for other threads. The current thread must then dequeue and process the message when it has
	 *            an opportunity to do so.
	 * @Discussion You can use this method to deliver messages to other threads in your application. The message in this case is a method of
	 *             the current object that you want to execute on the target thread. This method queues the message on the run loop of the
	 *             target thread using the default run loop modes—that is, the modes associated with the NSRunLoopCommonModes constant. As
	 *             part of its normal run loop processing, the target thread dequeues the message (assuming it is running in one of the
	 *             default run loop modes) and invokes the desired method. You cannot cancel messages queued using this method. If you want
	 *             the option of canceling a message on the current thread, you must use either the performSelector:withObject:afterDelay:
	 *             or performSelector:withObject:afterDelay:inModes: method.
	 **/
	
	public void performSelectorOnThreadWithObjectWaitUntilDone(SEL aSelector, NSThread thread,
			final Object firstMethodParam, boolean wait) {
		final Method method = getMethodFromReceiver(aSelector, firstMethodParam);
		// TODO not finished
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					method.invoke(this, firstMethodParam);
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
			}
		};
		runnable.run();
	}

	/**
	 * @Signature: performSelector:onThread:withObject:waitUntilDone:modes:
	 * @Declaration : - (void)performSelector:(SEL)aSelector onThread:(NSThread *)thread withObject:(id)arg waitUntilDone:(BOOL)wait
	 *              modes:(NSArray *)array
	 * @Description : Invokes a method of the receiver on the specified thread using the specified modes.
	 * @param aSelector A selector that identifies the method to invoke. It should not have a significant return value and should take a
	 *            single argument of type id, or no arguments.
	 * @param thread The thread on which to execute aSelector. This thread represents the target thread.
	 * @param arg The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @param wait A Boolean that specifies whether the current thread blocks until after the specified selector is performed on the
	 *            receiver on the specified thread. Specify YES to block this thread; otherwise, specify NO to have this method return
	 *            immediately. If the current thread and target thread are the same, and you specify YES for this parameter, the selector is
	 *            performed immediately. If you specify NO, this method queues the message and returns immediately, regardless of whether
	 *            the threads are the same or different.
	 * @param array An array of strings that identifies the modes in which it is permissible to perform the specified selector. This array
	 *            must contain at least one string. If you specify nil or an empty array for this parameter, this method returns without
	 *            performing the specified selector. For information about run loop modes, see “Run Loops�? in Threading Programming Guide.
	 * @Discussion You can use this method to deliver messages to other threads in your application. The message in this case is a method of
	 *             the current object that you want to execute on the target thread. This method queues the message on the run loop of the
	 *             target thread using the run loop modes specified in the array parameter. As part of its normal run loop processing, the
	 *             target thread dequeues the message (assuming it is running in one of the specified modes) and invokes the desired method.
	 *             You cannot cancel messages queued using this method. If you want the option of canceling a message on the current thread,
	 *             you must use either the performSelector:withObject:afterDelay: or performSelector:withObject:afterDelay:inModes: method
	 *             instead.
	 **/
	
	public void performSelectorOnThreadWithObjectWaitUntilDoneModes(SEL aSelector, NSThread thread,
			Object firstMethodParam, boolean wait, NSArray modes) {
		if (modes == null)
			return;
		// performSelector(aSelector, thread, firstMethodParam, wait);
	}

	/**
	 * @Signature: performSelectorInBackground:withObject:
	 * @Declaration : - (void)performSelectorInBackground:(SEL)aSelector withObject:(id)arg
	 * @Description : Invokes a method of the receiver on a new background thread.
	 * @param aSelector A selector that identifies the method to invoke. The method should not have a significant return value and should
	 *            take a single argument of type id, or no arguments.
	 * @param arg The argument to pass to the method when it is invoked. Pass nil if the method does not take an argument.
	 * @Discussion This method creates a new thread in your application, putting your application into multithreaded mode if it was not
	 *             already. The method represented by aSelector must set up the thread environment just as you would for any other new
	 *             thread in your program. For more information about how to configure and run threads, see Threading Programming Guide.
	 **/
	
	public void performSelectorInBackgroundWithObject(SEL aSelector,
			final Object firstMethodParam) {
		// Handler that runs on a background thread
		HandlerThread bgHandlerThread = new HandlerThread("bgThread");
		bgHandlerThread.start();
		mHandler = new Handler(bgHandlerThread.getLooper());
		final Method method = getMethodFromReceiver(aSelector, firstMethodParam);

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					method.invoke(this, firstMethodParam);
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
			}
		};
		mHandler.post(runnable);
		registredObject.put(aSelector, runnable);
	}

	/**
	 * @Signature: cancelPreviousPerformRequestsWithTarget:
	 * @Declaration : + (void)cancelPreviousPerformRequestsWithTarget:(id)aTarget
	 * @Description : Cancels perform requests previously registered with the performSelector:withObject:afterDelay: instance method.
	 * @param aTarget The target for requests previously registered with the performSelector:withObject:afterDelay: instance method.
	 * @Discussion All perform requests having the same target aTarget are canceled. This method removes perform requests only in the
	 *             current run loop, not all run loops.
	 **/
	
	public static void cancelPreviousPerformRequestsWithTarget(Object target) {

	}

	/**
	 * @Signature: cancelPreviousPerformRequestsWithTarget:selector:object:
	 * @Declaration : + (void)cancelPreviousPerformRequestsWithTarget:(id)aTarget selector:(SEL)aSelector object:(id)anArgument
	 * @Description : Cancels perform requests previously registered with performSelector:withObject:afterDelay:.
	 * @param aTarget The target for requests previously registered with the performSelector:withObject:afterDelay: instance method
	 * @param aSelector The selector for requests previously registered with the performSelector:withObject:afterDelay: instance method.
	 * @param anArgument The argument for requests previously registered with the performSelector:withObject:afterDelay: instance method.
	 *            Argument equality is determined using isEqual:, so the value need not be the same object that was passed originally. Pass
	 *            nil to match a request for nil that was originally passed as the argument.
	 * @Discussion All perform requests are canceled that have the same target as aTarget, argument as anArgument, and selector as
	 *             aSelector. This method removes perform requests only in the current run loop, not all run loops.
	 **/
	
	public static void cancelPreviousPerformRequestsWithTargetSelectorObject(NSObject aTarget,
			SEL aSelector, Object anArgument) {

	}

	// Forwarding Messages

	/**
	 * @Signature: forwardingTargetForSelector:
	 * @Declaration : - (id)forwardingTargetForSelector:(SEL)aSelector
	 * @Description : Returns the object to which unrecognized messages should first be directed.
	 * @param aSelector A selector for a method that the receiver does not implement.
	 * @return Return Value The object to which unrecognized messages should first be directed.
	 * @Discussion If an object implements (or inherits) this method, and returns a non-nil (and non-self) result, that returned object is
	 *             used as the new receiver object and the message dispatch resumes to that new object. (Obviously if you return self from
	 *             this method, the code would just fall into an infinite loop.) If you implement this method in a non-root class, if your
	 *             class has nothing to return for the given selector then you should return the result of invoking super’s implementation.
	 *             This method gives an object a chance to redirect an unknown message sent to it before the much more expensive
	 *             forwardInvocation: machinery takes over. This is useful when you simply want to redirect messages to another object and
	 *             can be an order of magnitude faster than regular forwarding. It is not useful where the goal of the forwarding is to
	 *             capture the NSInvocation, or manipulate the arguments or return value during the forwarding.
	 **/
	
	public Object forwardingTargetForSelector(SEL selector) {
		// Does nothing, should be overridden by subclasses.
		return null;
	}

	/**
	 * @Signature: forwardInvocation:
	 * @Declaration : - (void)forwardInvocation:(NSInvocation *)anInvocation
	 * @Description : Overridden by subclasses to forward messages to other objects.
	 * @param anInvocation The invocation to forward.
	 * @Discussion When an object is sent a message for which it has no corresponding method, the runtime system gives the receiver an
	 *             opportunity to delegate the message to another receiver. It delegates the message by creating an NSInvocation object
	 *             representing the message and sending the receiver a forwardInvocation: message containing this NSInvocation object as the
	 *             argument. The receiver’s forwardInvocation: method can then choose to forward the message to another object. (If that
	 *             object can’t respond to the message either, it too will be given a chance to forward it.) The forwardInvocation: message
	 *             thus allows an object to establish relationships with other objects that will, for certain messages, act on its behalf.
	 *             The forwarding object is, in a sense, able to “inherit�? some of the characteristics of the object it forwards the message
	 *             to. Important: To respond to methods that your object does not itself recognize, you must override
	 *             methodSignatureForSelector: in addition to forwardInvocation:.
	 **/
	
	public void forwardInvocation(NSInvocation anInvocation) {

		try {
			doesNotRecognizeSelector(null);
		} catch (IllegalArgumentException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	// Dynamically Resolving Methods

	/**
	 * @Signature: resolveClassMethod:
	 * @Declaration : + (BOOL)resolveClassMethod:(SEL)name
	 * @Description : Dynamically provides an implementation for a given selector for a class method.
	 * @param name The name of a selector to resolve.
	 * @return Return Value YES if the method was found and added to the receiver, otherwise NO.
	 * @Discussion This method allows you to dynamically provide an implementation for a given selector. See resolveInstanceMethod: for
	 *             further discussion.
	 **/
	
	public static boolean resolveClassMethod(SEL name) {
		return false;
	}

	/**
	 * @Signature: resolveInstanceMethod:
	 * @Declaration : + (BOOL)resolveInstanceMethod:(SEL)name
	 * @Description : Dynamically provides an implementation for a given selector for an instance method.
	 * @param name The name of a selector to resolve.
	 * @return Return Value YES if the method was found and added to the receiver, otherwise NO.
	 * @Discussion This method and resolveClassMethod: allow you to dynamically provide an implementation for a given selector. An
	 *             Objective-C method is simply a C function that take at least two arguments—self and _cmd. Using the class_addMethod
	 *             function, you can add a function to a class as a method. Given the following function: void dynamicMethodIMP(id self, SEL
	 *             _cmd) { // implementation .... } you can use resolveInstanceMethod: to dynamically add it to a class as a method (called
	 *             resolveThisMethodDynamically) like this: + (BOOL) resolveInstanceMethod:(SEL)aSEL { if (aSEL
	 *             == ) { class_addMethod([self class], aSEL, (IMP) dynamicMethodIMP, "v@:"); return
	 *             YES; } return [super resolveInstanceMethod:aSel]; }
	 **/
	
	public static boolean resolveInstanceMethod(SEL name) {
		return false;
	}

	// Error Handling

	/**
	 * @Signature: doesNotRecognizeSelector:
	 * @Declaration : - (void)doesNotRecognizeSelector:(SEL)aSelector
	 * @Description : Handles messages the receiver doesn’t recognize.
	 * @param aSelector A selector that identifies a method not implemented or recognized by the receiver.
	 * @Discussion The runtime system invokes this method whenever an object receives an aSelector message it can’t respond to or forward.
	 *             This method, in turn, raises an NSInvalidArgumentException, and generates an error message. Any doesNotRecognizeSelector:
	 *             messages are generally sent only by the runtime system. However, they can be used in program code to prevent a method
	 *             from being inherited. For example, an NSObject subclass might renounce the copy or init method by re-implementing it to
	 *             include a doesNotRecognizeSelector: message as follows: - (id)copy { [self doesNotRecognizeSelector:_cmd]; } The _cmd
	 *             variable is a hidden argument passed to every method that is the current selector; in this example, it identifies the
	 *             selector for the copy method. This code prevents instances of the subclass from responding to copy messages or
	 *             superclasses from forwarding copy messages—although respondsToSelector: will still report that the receiver has access to
	 *             a copy method. If you override this method, you must call super or raise an NSInvalidArgumentException exception at the
	 *             end of your implementation. In other words, this method must not return normally; it must always result in an exception
	 *             being thrown.
	 **/
	
	public void doesNotRecognizeSelector(SEL aSelector) {

	}

	// Archiving

	/**
	 * @Signature: awakeAfterUsingCoder:
	 * @Declaration : - (id)awakeAfterUsingCoder:(NSCoder *)aDecoder
	 * @Description : Overridden by subclasses to substitute another object in place of the object that was decoded and subsequently
	 *              received this message.
	 * @param aDecoder The decoder used to decode the receiver.
	 * @return Return Value The receiver, or another object to take the place of the object that was decoded and subsequently received this
	 *         message.
	 * @Discussion You can use this method to eliminate redundant objects created by the coder. For example, if after decoding an object you
	 *             discover that an equivalent object already exists, you can return the existing object. If a replacement is returned, your
	 *             overriding method is responsible for releasing the receiver. This method is invoked by NSCoder. NSObject’s implementation
	 *             simply returns self.
	 **/
	
	public Object awakeAfterUsingCoder(NSCoder aDecoder) {
		// Does nothing, should be overridden by subclasses.
		return null;

	}

	/**
	 * @Signature: classForCoder
	 * @Declaration : - (Class)classForCoder
	 * @Description : Overridden by subclasses to substitute a class other than its own during coding.
	 * @return Return Value The class to substitute for the receiver's own class during coding.
	 * @Discussion This method is invoked by NSCoder. NSObject’s implementation returns the receiver’s class. The private subclasses of a
	 *             class cluster substitute the name of their public superclass when being archived.
	 **/
	
	public Class<?> classForCoder() {
		// Does nothing, should be overridden by subclasses.
		return null;

	}

	/**
	 * @Signature: classForKeyedArchiver
	 * @Declaration : - (Class)classForKeyedArchiver
	 * @Description : Overridden by subclasses to substitute a new class for instances during keyed archiving.
	 * @Discussion The object will be encoded as if it were a member of the returned class. The results of this method are overridden by the
	 *             encoder class and instance name to class encoding tables. If nil is returned, the result of this method is ignored.
	 **/
	
	public Class classForKeyedArchiver() {
		// Overridden by subclasses to substitute a new class for instances
		// during keyed archiving.
		// Does nothing, should be overridden by subclasses.
		return null;
	}

	/**
	 * @Signature: classFallbacksForKeyedArchiver
	 * @Declaration : + (NSArray *)classFallbacksForKeyedArchiver
	 * @Description : Overridden to return the names of classes that can be used to decode objects if their class is unavailable.
	 * @return Return Value An array of NSString objects that specify the names of classes in preferred order for unarchiving
	 * @Discussion NSKeyedArchiver calls this method and stores the result inside the archive. If the actual class of an object doesn’t
	 *             exist at the time of unarchiving, NSKeyedUnarchiver goes through the stored list of classes and uses the first one that
	 *             does exists as a substitute class for decoding the object. The default implementation of this method returns nil. You can
	 *             use this method if you introduce a new class into your application to provide some backwards compatibility in case the
	 *             archive will be read on a system that does not have that class. Sometimes there may be another class which may work
	 *             nearly as well as a substitute for the new class, and the archive keys and archived state for the new class can be
	 *             carefully chosen (or compatibility written out) so that the object can be unarchived as the substitute class if
	 *             necessary.
	 **/
	
	public static void classFallbacksForKeyedArchiver() {
	}

	/**
	 * @Signature: classForKeyedUnarchiver
	 * @Declaration : + (Class)classForKeyedUnarchiver
	 * @Description : Overridden by subclasses to substitute a new class during keyed unarchiving.
	 * @return Return Value The class to substitute for the receiver during keyed unarchiving.
	 * @Discussion During keyed unarchiving, instances of the receiver will be decoded as members of the returned class. This method
	 *             overrides the results of the decoder’s class and instance name to class encoding tables.
	 **/
	
	public static void classForKeyedUnarchiver() {
	}

	/**
	 * @Signature: replacementObjectForCoder:
	 * @Declaration : - (id)replacementObjectForCoder:(NSCoder *)aCoder
	 * @Description : Overridden by subclasses to substitute another object for itself during encoding.
	 * @param aCoder The coder encoding the receiver.
	 * @return Return Value The object encode instead of the receiver (if different).
	 * @Discussion An object might encode itself into an archive, but encode a proxy for itself if it’s being encoded for distribution. This
	 *             method is invoked by NSCoder. NSObject’s implementation returns self.
	 **/
	
	public Object replacementObjectForCoder(NSCoder aCoder) {
		// Does nothing, should be overridden by subclasses.
		return null;
	}

	/**
	 * @Signature: replacementObjectForKeyedArchiver:
	 * @Declaration : - (id)replacementObjectForKeyedArchiver:(NSKeyedArchiver *)archiver
	 * @Description : Overridden by subclasses to substitute another object for itself during keyed archiving.
	 * @param archiver A keyed archiver creating an archive.
	 * @return Return Value The object encode instead of the receiver (if different).
	 * @Discussion This method is called only if no replacement mapping for the object has been set up in the encoder (for example, due to a
	 *             previous call of replacementObjectForKeyedArchiver: to that object).
	 **/
	
	public Object replacementObjectForKeyedArchiver(NSKeyedArchiver archiver) {
		// Does nothing, should be overridden by subclasses.
		return null;
	}

	/**
	 * @Signature: setVersion:
	 * @Declaration : + (void)setVersion:(NSInteger)aVersion
	 * @Description : Sets the receiver's version number.
	 * @param aVersion The version number for the receiver.
	 * @Discussion The version number is helpful when instances of the class are to be archived and reused later. The default version is 0.
	 **/
	
	public static void setVersion(int version) {

	}

	/**
	 * @Signature: version
	 * @Declaration : + (NSInteger)version
	 * @Description : Returns the version number assigned to the class.
	 * @return Return Value The version number assigned to the class.
	 * @Discussion If no version has been set, the default is 0. Version numbers are needed for decoding or unarchiving, so older versions
	 *             of an object can be detected and decoded correctly. Caution should be taken when obtaining the version from within an
	 *             NSCoding protocol or other methods. Use the class name explicitly when getting a class version number: version = [MyClass
	 *             version]; Don’t simply send version to the return value of class—a subclass version number may be returned instead.
	 **/
	
	public static int version() {
		return 1;
	}

	// Deprecated Methods

	/**
	 * @throws Throwable
	 * @Signature: finalize
	 * @Declaration : - (void)finalize
	 * @Description : The garbage collector invokes this method on the receiver before disposing of the memory it uses. (Deprecated. Garbage
	 *              collection is deprecated in OS X v10.8; instead, you should use Automatic Reference Counting—see Transitioning to ARC
	 *              Release Notes.)
	 * @Discussion The garbage collector invokes this method on the receiver before disposing of the memory it uses. When garbage collection
	 *             is enabled, this method is invoked instead of dealloc. You can override this method to relinquish resources the receiver
	 *             has obtained, as shown in the following example: - (void)finalize { if (log_file != NULL) { fclose(log_file); log_file =
	 *             NULL;     } [super finalize]; } Typically, however, you are encouraged to relinquish resources prior to finalization if
	 *             at all possible. For more details, see “Implementing a finalize Method�?.
	 **/
	
	
	// public void finalize() throws Throwable {
	// super.finalize();
	// }
	@Override
	public boolean equals(Object o) {

		return super.equals(o);
	}

	@Override
	public int hashCode() {

		return super.hashCode();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.clone();
	}

	//

	public String toXMLPropertyList() {
		StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("\n");
		xml.append(
				"<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
		xml.append("\n");
		xml.append("<plist version=\"1.0\">");
		xml.append("\n");
		toXML(xml, 0);
		xml.append("\n");
		xml.append("</plist>");
		return xml.toString();
	}

	// Helper methods

	private void runHandler(SEL aSelector, final Method mm, final Object firstMethodParam) {
		Runnable runable = new Runnable() {

			@Override
			public void run() {
				try {
					mm.invoke(this, firstMethodParam);
					isRunningInUi = true;
				} catch (IllegalArgumentException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (IllegalAccessException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				} catch (InvocationTargetException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: "
							+ Log.getStackTraceString(e));
				}
			}
		};
		runOnUiThreadHandler.post(runable);
		registredObject.put(aSelector, runable);
	}

	private static Method getMethodFromReceiver(SEL aSelector, Object firstMethodParam) {
		// get methods
		Method[] methods = isa.getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			// search method by name
			if (method.getName().equalsIgnoreCase(aSelector.getMethodName())) {

				Class[] parameterTypes = method.getParameterTypes();
				if (firstMethodParam == null) {
					return method;
				} else {

					if (!parameterTypes[0].isAssignableFrom(firstMethodParam.getClass())) {

						throw new RuntimeException("unkonw type");
					} else
						return method;
				}
			}
		}
		return null;
	}

	private boolean isInUiThread() {
		return (mHandler.getLooper() == Looper.getMainLooper());
	}

	
	@Override
	public Object autorelease() {
		return this;
	}

	@Override
	public Class<?> _class() {
		return getClass();
	}

	
	@Override
	public boolean conformsToProtocol(Protocol aProtocol) {
		return false;
	}

	
	@Override
	public NSString debugDescription() {
		return new NSString("");
	}

	
	@Override
	public NSString description() {
		return new NSString("");
	}

	@Override
	public int hash() {
		return 0;
	}

	
	@Override
	public boolean isEqual(Object anObject) {
		return anObject.equals(this) && anObject.getClass().equals(this.getClass());
	}

	
	@Override
	public boolean isKindOfClass(Class<?> aClass) {
		boolean isAssignable = this.getClass().isAssignableFrom(aClass);
		return isAssignable;
	}

	
	@Override
	public boolean isMemberOfClass(Class<?> aClass) {
		return this.getClass().equals(aClass);
	}

	
	@Override
	public boolean isProxy() {
		return false;
	}

	
	@Override
	public Object performSelector(SEL aSelector) {
		return InvokableHelper.invoke(this, aSelector.getMethodName(), null);
	}

	@Override
	
	public Object performSelectorWithObject(SEL aSelector, Object anObject) {
		return InvokableHelper.invoke(this, aSelector.getMethodName(), anObject);
	}

	
	@Override
	public void release() {
	}

	
	@Override
	public boolean respondsToSelector(SEL aSelector) {
		Method method;
		try {
			method = this.getClass().getDeclaredMethod(aSelector.getMethodName());
			if (method != null)
				return true;
		} catch (NoSuchMethodException e) {
			LOGGER.info(e.getMessage());
		}
		return false;
	}

	
	@Override
	public Object retain() {
		return this;
	}

	
	@Override
	public int retainCount() {
		return 0;
	}

	
	@Override
	public NSObject self() {
		return this;
	}

	
	@Override
	public Class<?> superclass() {
		return getClass();
	}

	
	@Override
	public NSZone zone() {
		return null;
	}

	/**
	 * Generates the XML representation of the object (without XML headers or enclosing plist-tags).
	 *
	 * @param xml The StringBuilder onto which the XML representation is appended.
	 * @param level The indentation level of the object.
	 */
	public void toXML(StringBuilder xml, int level) {
		indent(xml, level);
		xml.append("<dict>");
		xml.append("\n");
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (NSObject.class.isAssignableFrom(field.getType())) {
				callToXmlOnField(xml, level, field);
				indent(xml, level);
			}
		}
		indent(xml, level);
		xml.append("</dict>");
	}

	public void toXML(StringBuilder xml, Integer level) {
		toXML(xml, level.intValue());
	}

	private void callToXmlOnField(StringBuilder xml, int level, Field field) {
		Method method;
		try {
			method = field.getType().getMethod("toXML", StringBuilder.class, Integer.class);
			method.invoke(field.get(this), xml, level);
		} catch (SecurityException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (NoSuchMethodException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalArgumentException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (InvocationTargetException e) {
			Log.d("Exception ",
					"Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * Assigns IDs to all the objects in this NSObject subtree.
	 *
	 * @param out The writer object that handles the binary serialization.
	 */
	public void assignIDs(BinaryPropertyListWriter out) {
		out.assignID(this);
	}

	/**
	 * Generates the binary representation of the object.
	 *
	 * @param out The output stream to serialize the object to.
	 */
	public void toBinary(BinaryPropertyListWriter out) throws IOException {

	}

	/**
	 * Generates the ASCII representation of this object. The generated ASCII representation does not end with a newline. Complies with
	 * https://developer .apple.com/library/mac/#documentation/Cocoa/Conceptual/PropertyLists /OldStylePlists /OldStylePLists.html
	 *
	 * @param ascii The StringBuilder onto which the ASCII representation is appended.
	 * @param level The indentation level of the object.
	 */
	public void toASCII(StringBuilder ascii, int level) {
	}

	/**
	 * Generates the ASCII representation of this object in the GnuStep format. The generated ASCII representation does not end with a
	 * newline.
	 *
	 * @param ascii The StringBuilder onto which the ASCII representation is appended.
	 * @param level The indentation level of the object.
	 */
	public void toASCIIGnuStep(StringBuilder ascii, int level) {
	}

	/**
	 * Helper method that adds correct identation to the xml output. Calling this method will add <code>level</code> number of tab
	 * characters to the <code>xml</code> string.
	 *
	 * @param xml The string builder for the XML document.
	 * @param level The level of identation.
	 */
	void indent(StringBuilder xml, int level) {
		for (int i = 0; i < level; i++)
			xml.append("\t");
	}

	/**
	 * Wraps the given value inside a NSObject.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 */
	public static NSNumber wrap(long value) {
		return new NSNumber(value);
	}

	/**
	 * Wraps the given value inside a NSObject.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 */
	public static NSNumber wrap(double value) {
		return new NSNumber(value);
	}

	/**
	 * Wraps the given value inside a NSObject.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 */
	public static NSNumber wrap(boolean value) {
		return new NSNumber(value);
	}

	/**
	 * Wraps the given value inside a NSObject.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 */
	public static NSData wrap(byte[] value) {
		return new NSData(value);
	}

	/**
	 * Creates a NSArray with the contents of the given array.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 * @throws RuntimeException When one of the objects contained in the array cannot be represented by a NSObject.
	 */
	public static NSArray wrap(Object[] value) {
		NSArray arr = new NSArray(value.length);
		for (int i = 0; i < value.length; i++) {
			arr.getWrappedList().set(i, wrap(value[i]));
		}
		return arr;
	}

	/**
	 * Creates a NSDictionary with the contents of the given map.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 * @throws RuntimeException When one of the values contained in the map cannot be represented by a NSObject.
	 */
	public static NSDictionary wrap(Map<String, Object> value) {
		NSDictionary dict = new NSDictionary();
		for (String key : value.keySet())
			dict.put(key, wrap(value.get(key)));
		return dict;
	}

	/**
	 * Creates a NSSet with the contents of this set.
	 *
	 * @param value The value to represent as a NSObject.
	 * @return A NSObject representing the given value.
	 * @throws RuntimeException When one of the values contained in the set cannot be represented by a NSObject.
	 */
	public static NSSet wrap(Set<Object> value) {
		NSSet set = new NSSet();
		for (Object o : value.toArray())
			set.addObject(wrap(o));
		return set;
	}

	/**
	 * Creates a NSObject representing the given Java Object. Numerics of type bool, int, long, short, byte, float or double are wrapped as
	 * NSNumber objects. Strings are wrapped as NSString objects abd byte arrays as NSData objects. Date objects are wrapped as NSDate
	 * objects. Serializable classes are serialized and their data is stored in NSData objects. Arrays and Collection objects are converted
	 * to NSArrays where each array member is wrapped into a NSObject. Map objects are converted to NSDictionaries. Each key is converted to
	 * a string and each value wrapped into a NSObject.
	 *
	 * @param o The object to represent.
	 * @return A NSObject equivalent to the given object.
	 */
	public static NSObject wrap(Object o) {
		if (o == null)
			throw new NullPointerException("A null object cannot be wrapped as a NSObject");

		if (o instanceof NSObject)
			return (NSObject) o;

		Class<?> c = o.getClass();
		if (Boolean.class.equals(c)) {
			return wrap((boolean) (Boolean) o);
		}
		if (Byte.class.equals(c)) {
			return wrap((int) (Byte) o);
		}
		if (Short.class.equals(c)) {
			return wrap((int) (Short) o);
		}
		if (Integer.class.equals(c)) {
			return wrap((int) (Integer) o);
		}
		if (Long.class.isAssignableFrom(c)) {
			return wrap((long) (Long) o);
		}
		if (Float.class.equals(c)) {
			return wrap((double) (Float) o);
		}
		if (Double.class.isAssignableFrom(c)) {
			return wrap((double) (Double) o);
		}
		if (String.class.equals(c)) {
			return new NSString((String) o);
		}
		if (Date.class.equals(c)) {
			return new NSDate((Date) o);
		}
		if (c.isArray()) {
			Class<?> cc = c.getComponentType();
			if (cc.equals(byte.class)) {
				return wrap((byte[]) o);
			} else if (cc.equals(boolean.class)) {
				boolean[] array = (boolean[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else if (float.class.equals(cc)) {
				float[] array = (float[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else if (double.class.equals(cc)) {
				double[] array = (double[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else if (short.class.equals(cc)) {
				short[] array = (short[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else if (int.class.equals(cc)) {
				int[] array = (int[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else if (long.class.equals(cc)) {
				long[] array = (long[]) o;
				NSArray nsa = new NSArray(array.length);
				for (int i = 0; i < array.length; i++)
					nsa.getWrappedList().set(i, wrap(array[i]));
				return nsa;
			} else {
				return wrap((Object[]) o);
			}
		}
		if (Map.class.isAssignableFrom(c)) {
			Map map = (Map) o;
			Set keys = map.keySet();
			NSDictionary dict = new NSDictionary();
			for (Object key : keys) {
				Object val = map.get(key);
				dict.put(String.valueOf(key), wrap(val));
			}
			return dict;
		}
		if (Collection.class.isAssignableFrom(c)) {
			Collection coll = (Collection) o;
			return wrap(coll.toArray());
		}
		return wrapSerialized(o);
	}

	/**
	 * Serializes the given object using Java's default object serialization and wraps the serialized object in a NSData object.
	 *
	 * @param o The object to serialize and wrap.
	 * @return A NSData object
	 * @throws RuntimeException When the object could not be serialized.
	 */
	public static NSData wrapSerialized(Object o) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			return new NSData(baos.toByteArray());
		} catch (IOException ex) {
			LOGGER.info(ex.getMessage());
			return null;
		}
	}

	/**
	 * Converts this NSObject into an equivalent object of the Java Runtime Environment.
	 * <ul>
	 * <li>NSArray objects are converted to arrays.</li>
	 * <li>NSDictionary objects are converted to objects extending the java.util.Map class.</li>
	 * <li>NSSet objects are converted to objects extending the java.util.Set class.</li>
	 * <li>NSNumber objects are converted to primitive number values (int, long, double or boolean).</li>
	 * <li>NSString objects are converted to String objects.</li>
	 * <li>NSData objects are converted to byte arrays.</li>
	 * <li>NSDate objects are converted to java.util.Date objects.</li>
	 * <li>UID objects are converted to byte arrays.</li>
	 * </ul>
	 *
	 * @return A native java object representing this NSObject's value.
	 */
	public Object toJavaObject() {
		if (this instanceof NSArray) {
			NSObject[] arrayA = ((NSArray) this).getArray();
			Object[] arrayB = new Object[arrayA.length];
			for (int i = 0; i < arrayA.length; i++) {
				arrayB[i] = arrayA[i].toJavaObject();
			}
			return arrayB;
		} else if (this instanceof NSDictionary) {
			Map<String, NSObject> hashMapA = ((NSDictionary) this).getHashMap();
			HashMap<String, Object> hashMapB = new HashMap<String, Object>(hashMapA.size());
			for (String key : hashMapA.keySet()) {
				hashMapB.put(key, hashMapA.get(key).toJavaObject());
			}
			return hashMapB;
		} else if (this instanceof NSSet) {
			Set<NSObject> setA = ((NSSet) this).getSet();
			Set<Object> setB;
			if (setA instanceof LinkedHashSet) {
				setB = new LinkedHashSet<Object>(setA.size());
			} else {
				setB = new TreeSet<Object>();
			}
			for (NSObject o : setA) {
				setB.add(o.toJavaObject());
			}
			return setB;
		} else if (this instanceof NSNumber) {
			NSNumber num = (NSNumber) this;
			switch (num.type()) {
			case NSNumber.INTEGER: {
				long longVal = num.longValue();
				if (longVal > Integer.MAX_VALUE || longVal < Integer.MIN_VALUE) {
					return longVal;
				} else {
					return num.intValue();
				}
			}
			case NSNumber.REAL: {
				return num.doubleValue();
			}
			case NSNumber.BOOLEAN: {
				return num.boolValue();
			}
			default: {
				return num.doubleValue();
			}
			}
		} else if (this instanceof NSString) {
			return ((NSString) this).getContent();
		} else if (this instanceof NSData) {
			return ((NSData) this).bytes();
		} else if (this instanceof NSDate) {
			return ((NSDate) this).getDate();
		} else if (this instanceof NSUUID) {
			return ((NSUUID) this).getBytes();
		} else {
			return this;
		}
	}

	public int compareTo(NSObject object) {
		return this.equals(object) ? 1 : 0;
	}

	
	@Override
	public Object performSelectorWithObjectWithObject(SEL aSelector, Object anObject,
			Object anotherObject) {
		return InvokableHelper.invoke(this, aSelector.getMethodName(), anObject, anotherObject);
	}

	// Functions Declarations

	/**
	 * @Description : Creates an exact copy of an object.
	 * @param object : The object to copy.
	 * @param extraBytes : The number of extra bytes required for indexed instance variables (this value is typically 0).
	 * @param zone : The zone in which to create the new instance (pass NULL to specify the default zone).
	 * @return : Return Value A new object that’s an exact copy of anObject, or nil if object is nil or if object could not be copied.
	 **/

	
	public static Object NSCopyObject(Object object, long extraBytes, NSZone zone) {
		return object;

	}

	/**
	 * @Description : Destroys an existing object.
	 * @param object : An object.
	 **/

	
	public static void NSDeallocateObject(Object object) {

	}

	/**
	 * @Description : Decrements the specified object’s reference count.
	 * @param object : An object.
	 * @return : Return Value NO if anObject had an extra reference count, or YES if anObject didn’t have an extra reference
	 *         count—indicating that the object should be deallocated (with dealloc).
	 **/

	
	public static boolean NSDecrementExtraRefCountWasZero(Object object) {
		return false;
	}

	/**
	 * @Description : Returns the specified object’s reference count.
	 * @param object : An object.
	 * @return : Return Value The current reference count of object.
	 **/

	
	public static int NSExtraRefCount(Object object) {
		return 0;
	}

	/**
	 * @Description : Increments the specified object’s reference count.
	 * @param object : An object.
	 **/

	
	public static void NSIncrementExtraRefCount(Object object) {

	}

	/**
	 * @Description : Indicates whether an object should be retained.
	 * @param anObject : An object.
	 * @param requestedZone : A memory zone.
	 * @return : Return Value Returns YES if requestedZone is NULL, the default zone, or the zone in which anObject was allocated; otherwise
	 *         NO.
	 **/

	
	public static boolean NSShouldRetainWithZone(Object anObject, NSZone requestedZone) {
		return false;
	}

	/**
	 * Prepares the receiver for service after it has been loaded from an Interface Builder archive, or nib file.
	 *
	 * @Declaration OBJECTIVE-C - (void)awakeFromNib
	 * @Discussion The nib-loading infrastructure sends an awakeFromNib message to each object recreated from a nib archive, but only after
	 *             all the objects in the archive have been loaded and initialized. When an object receives an awakeFromNib message, it is
	 *             guaranteed to have all its outlet and action connections already established. You must call the super implementation of
	 *             awakeFromNib to give parent classes the opportunity to perform any additional initialization they require. Although the
	 *             default implementation of this method does nothing, many UIKit classes provide non-empty implementations. You may call
	 *             the super implementation at any point during your own awakeFromNib method.
	 * @NOTE During Interface Builder’s test mode, this message is also sent to objects instantiated from loaded Interface Builder plug-ins.
	 *       Because plug-ins link against the framework containing the object definition code, Interface Builder is able to call their
	 *       awakeFromNib method when present. The same is not true for custom objects that you create for your Xcode projects. Interface
	 *       Builder knows only about the defined outlets and actions of those objects; it does not have access to the actual code for them.
	 *       During the instantiation process, each object in the archive is unarchived and then initialized with the method befitting its
	 *       type. Objects that conform to the NSCoding protocol (including all subclasses of UIView and UIViewController) are initialized
	 *       using their initWithCoder: method. All objects that do not conform to the NSCoding protocol are initialized using their init
	 *       method. After all objects have been instantiated and initialized, the nib-loading code reestablishes the outlet and action
	 *       connections for all of those objects. It then calls the awakeFromNib method of the objects. For more detailed information about
	 *       the steps followed during the nib-loading process, see Nib Files in Resource Programming Guide.
	 * @IMPORTANT Because the order in which objects are instantiated from an archive is not guaranteed, your initialization methods should
	 *            not send messages to other objects in the hierarchy. Messages to other objects can be sent safely from within an
	 *            awakeFromNib method. Typically, you implement awakeFromNib for objects that require additional set up that cannot be done
	 *            at design time. For example, you might use this method to customize the default configuration of any controls to match
	 *            user preferences or the values in other controls. You might also use it to restore individual controls to some previous
	 *            state of your application.
	 */
	
	public void awakeFromNib() {
	}

	/**
	 * Moves a non-Objective-C pointer to Objective-C and also transfers ownership to ARC.
	 *
	 * @Declaration OBJECTIVE-C id CFBridgingRelease ( CFTypeRef X );
	 * @Discussion You use this function to cast a Core Foundation-style object as an Objective-C object and <br>
	 *             transfer ownership of the object to ARC such that you don’t have to release the object, as illustrated in this example:
	 *             CFStringRef cfName = ABRecordCopyValue(person, kABPersonFirstNameProperty); NSString *name = (NSString
	 *             *)CFBridgingRelease(cfName);
	 */
	
	public static Object CFBridgingRelease(CFTypeRef val) {

		if (val instanceof CFStringRef) {
			return new NSString(((CFStringRef) val).getWrappedString());
		} else if (val instanceof CFData) {
			return new NSData(toPrimitives(((CFData) val).getWrappedData()));
		} else if (val instanceof CFBundleRef) {
			NSBundle bundle = new NSBundle();
			NSURL url = new NSURL();
			url.setWrappedURL(((CFBundleRef) val).getUrlRef().getWrappedUrl());
			bundle.initWithURL(url);
			return bundle;

		} else if (val.getClass().isAssignableFrom(CFArrayRef.class)) {
			// CFArrayRef array = val;
			// return new NSArray<>(((CFArrayRef) val).getWrappedList());
			return null;

		} else if (val instanceof CFDateRef) {
			CFDateRef obj = new CFDateRef();
			obj.setWrappedDate(((CFDateRef) val).getWrappedDate());
			return obj;
		} else if (val instanceof CFErrorRef) {
			NSError error = new NSError();
			error.setCode((int) ((CFErrorRef) val).getCode());
			error.setCode((int) ((CFErrorRef) val).getCode());
			error.setDomain((NSString) CFBridgingRelease(((CFErrorRef) val).getDomain()));
			return error;
		}
		return val;

	}

	private static byte[] toPrimitives(Byte[] oBytes) {

		byte[] bytes = new byte[oBytes.length];
		for (int i = 0; i < oBytes.length; i++) {
			bytes[i] = oBytes[i];
		}
		return bytes;
	}

}