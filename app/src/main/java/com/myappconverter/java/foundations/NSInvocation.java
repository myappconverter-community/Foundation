
package com.myappconverter.java.foundations;

import java.lang.reflect.InvocationTargetException;

import android.util.Log;



public class NSInvocation extends NSObject {

	private NSMethodSignature sig;
	private SEL selector;
	private Object target = null;
	private Object[] arguments;
	private Object returnValue;

	public NSInvocation() {
		super();
	}

	public NSInvocation(NSMethodSignature signature) {
		this.sig = signature;
		this.selector = new SEL(this.sig.getMethodName());
		arguments = new Object[sig.numberOfArguments() + 2];
		arguments[0] = target;
		arguments[1] = sig.getMethodName();
	}

	/**
	 * @Signature: invocationWithMethodSignature:
	 * @Declaration : + (NSInvocation *)invocationWithMethodSignature:(NSMethodSignature *)signature
	 * @Description : Returns an NSInvocation object able to construct messages using a given method signature.
	 * @param signature An object encapsulating a method signature.
	 * @Discussion The new object must have its selector set with setSelector: and its arguments set with setArgument:atIndex: before it can
	 *             be invoked. Do not use the alloc/init approach to create NSInvocation objects.
	 **/
	
	public static NSInvocation invocationWithMethodSignature(NSMethodSignature signature) {
		return new NSInvocation(signature);
	}

	/**
	 * @Signature: setSelector:
	 * @Declaration : - (void)setSelector:(SEL)selector
	 * @Description : Sets the receiver’s selector.
	 * @param selector The selector to assign to the receiver.
	 **/
	
	public void setSelector(SEL selector) {
		this.selector = selector;

	}

	/**
	 * @Signature: selector
	 * @Declaration : - (SEL)selector
	 * @Description : Returns the receiver’s selector, or 0 if it hasn’t been set.
	 **/
	
	public SEL selector() {
		return this.selector;
	}

	/**
	 * @Signature: setTarget:
	 * @Declaration : - (void)setTarget:(id)anObject
	 * @Description : Sets the receiver’s target.
	 * @param anObject The object to assign to the receiver as target. The target is the receiver of the message sent by invoke.
	 * @Discussion
	 **/
	
	public void setTarget(Object anObject) {
		this.target = anObject;
		arguments[0] = this.target;
	}

	/**
	 * @Signature: target
	 * @Declaration : - (id)target
	 * @Description : Returns the receiver’s target, or nil if the receiver has no target.
	 **/
	
	public Object target() {
		return target;
	}

	/**
	 * @Signature: setArgument:atIndex:
	 * @Declaration : - (void)setArgument:(void *)buffer atIndex:(NSInteger)index
	 * @Description : Sets an argument of the receiver.
	 * @param buffer An untyped buffer containing an argument to be assigned to the receiver. See the discussion below relating to argument
	 *            values that are objects.
	 * @param index An integer specifying the index of the argument. Indices 0 and 1 indicate the hidden arguments self and _cmd,
	 *            respectively; you should set these values directly with the setTarget: and setSelector: methods. Use indices 2 and greater
	 *            for the arguments normally passed in a message.
	 * @Discussion This method copies the contents of buffer as the argument at index. The number of bytes copied is determined by the
	 *             argument size. When the argument value is an object, pass a pointer to the variable (or memory) from which the object
	 *             should be copied: NSArray *anArray; [invocation setArgument:&anArray atIndex:3]; This method raises
	 *             NSInvalidArgumentException if the value of index is greater than the actual number of arguments for the selector.
	 **/
	
	public void setArgumentAtIndex(Object buffer, Integer index) {
		if (index < arguments.length) {
			arguments[index] = buffer;
		}
	}

	/**
	 * @Signature: getArgument:atIndex:
	 * @Declaration : - (void)getArgument:(void *)buffer atIndex:(NSInteger)index
	 * @Description : Returns by indirection the receiver's argument at a specified index.
	 * @param buffer An untyped buffer to hold the returned argument. See the discussion below relating to argument values that are objects.
	 * @param index An integer specifying the index of the argument to get. Indices 0 and 1 indicate the hidden arguments self and _cmd,
	 *            respectively; these values can be retrieved directly with the target and selector methods. Use indices 2 and greater for
	 *            the arguments normally passed in a message.
	 * @Discussion This method copies the argument stored at index into the storage pointed to by buffer. The size of buffer must be large
	 *             enough to accommodate the argument value. When the argument value is an object, pass a pointer to the variable (or
	 *             memory) into which the object should be placed: NSArray *anArray; [invocation getArgument:&anArray atIndex:3]; This
	 *             method raises NSInvalidArgumentException if index is greater than the actual number of arguments for the selector.
	 **/
	
	public void getArgument(Object buffer, Integer index) {

		if (index < arguments.length) {
			buffer = arguments[index];
		} else {
			buffer = null;
		}

	}

	/**
	 * @Signature: argumentsRetained
	 * @Declaration : - (BOOL)argumentsRetained
	 * @Description : Returns YES if the receiver has retained its arguments, NO otherwise.
	 **/
	
	public boolean argumentsRetained() {
		return true;

	}

	/**
	 * @Signature: retainArguments
	 * @Declaration : - (void)retainArguments
	 * @Description : If the receiver hasn’t already done so, retains the target and all object arguments of the receiver and copies all of
	 *              its C-string arguments and blocks.
	 * @Discussion Before this method is invoked, argumentsRetained returns NO; after, it returns YES. For efficiency, newly created
	 *             NSInvocation objects don’t retain or copy their arguments, nor do they retain their targets, copy C strings, or copy any
	 *             associated blocks. You should instruct an NSInvocation object to retain its arguments if you intend to cache it, because
	 *             the arguments may otherwise be released before the invocation is invoked. NSTimer objects always instruct their
	 *             invocations to retain their arguments, for example, because there’s usually a delay before a timer fires.
	 **/
	
	public void retainArguments() {
		// not mapped
	}

	/**
	 * @Signature: setReturnValue:
	 * @Declaration : - (void)setReturnValue:(void *)buffer
	 * @Description : Sets the receiver’s return value.
	 * @param buffer An untyped buffer whose contents are copied as the receiver's return value.
	 * @Discussion This value is normally set when you send an invoke or invokeWithTarget: message.
	 **/
	
	public void setReturnValue(Object buffer) {
		Object[] args = new Object[arguments.length - 2];
		for (int i = 0; i < arguments.length - 2; i++) {
			args[i] = arguments[i + 2];
		}
		try {
			returnValue = sig.getWrappedMethod().invoke(target, args);
			buffer = returnValue;
		} catch (IllegalArgumentException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (InvocationTargetException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	/**
	 * @Signature: getReturnValue:
	 * @Declaration : - (void)getReturnValue:(void *)buffer
	 * @Description : Gets the receiver's return value.
	 * @param buffer An untyped buffer into which the receiver copies its return value. It should be large enough to accommodate the value.
	 *            See the discussion below for more information about buffer.
	 * @Discussion Use the NSMethodSignature method methodReturnLength to determine the size needed for buffer: NSUInteger length =
	 *             [[myInvocation methodSignature] methodReturnLength]; buffer = (void *)malloc(length); [invocation getReturnValue:buffer];
	 *             When the return value is an object, pass a pointer to the variable (or memory) into which the object should be placed: id
	 *             anObject; NSArray *anArray; [invocation1 getReturnValue:&anObject]; [invocation2 getReturnValue:&anArray]; If the
	 *             NSInvocation object has never been invoked, the result of this method is undefined.
	 **/
	
	public void getReturnValue(Object buffer) {
		Object[] args = new Object[arguments.length - 2];
		for (int i = 0; i < arguments.length - 2; i++) {
			args[i] = arguments[i + 2];
		}
		try {
			returnValue = sig.getWrappedMethod().invoke(target, args);
			buffer = returnValue;
		} catch (IllegalArgumentException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (InvocationTargetException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	/**
	 * @Signature: invoke
	 * @Declaration : - (void)invoke
	 * @Description : Sends the receiver’s message (with arguments) to its target and sets the return value.
	 * @Discussion You must set the receiver’s target, selector, and argument values before calling this method.
	 **/
	
	public void invoke() {
		Object[] args = null;
		if (arguments != null) {
			args = new Object[arguments.length - 2];
			for (int i = 0; i < arguments.length - 2; i++) {
				args[i] = arguments[i + 2];
			}
		}
		try {
			sig.getWrappedMethod().invoke(target, args);
		} catch (IllegalArgumentException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalAccessException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (InvocationTargetException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Signature: invokeWithTarget:
	 * @Declaration : - (void)invokeWithTarget:(id)anObject
	 * @Description : Sets the receiver’s target, sends the receiver’s message (with arguments) to that target, and sets the return value.
	 * @param anObject The object to set as the receiver's target.
	 * @Discussion You must set the receiver’s selector and argument values before calling this method.
	 **/
	
	public void invokeWithTarget(Object target) {
		this.setTarget(target);
		this.invoke();
	}

	/**
	 * @Signature: methodSignature
	 * @Declaration : - (NSMethodSignature *)methodSignature
	 * @Description : Returns the receiver’s method signature.
	 **/
	
	public NSMethodSignature methodSignature() {
		return sig;
	}
}