
package com.myappconverter.java.foundations;

import android.os.Handler;
import android.os.Message;



public class NSInvocationOperation extends NSOperation {

	private Handler shandler;
	private Message message;

	public NSInvocationOperation() {
		shandler = new Handler();
		message = shandler.obtainMessage();
	}

	/**
	 * @Signature: initWithTarget:selector:object:
	 * @Declaration : - (id)initWithTarget:(id)target selector:(SEL)sel object:(id)arg
	 * @Description : Returns an NSInvocationOperation object initialized with the specified target and selector.
	 * @param target The object defining the specified selector.
	 * @param sel The selector to invoke when running the operation. The selector may take 0 or 1 parameters; if it accepts a parameter, the
	 *            type of that parameter must be id. The return type of the method may be void, a scalar value, or an object that can be
	 *            returned as an id type.
	 * @param arg The parameter object to pass to the selector. If the selector does not take an argument, specify nil.
	 * @return Return Value An initialized NSInvocationOperation object or nil if the target object does not implement the specified
	 *         selector.
	 * @Discussion If you specify a selector with a non-void return type, you can get the return value by calling the result method after
	 *             the operation finishes executing. The receiver tells the invocation object to retain its arguments.
	 **/
	
	public Object initWithTarget(Object target, SEL sel, Object arg) {

		message.setTarget((Handler) target);
		return init();

	}

	/**
	 * @Signature: initWithInvocation:
	 * @Declaration : - (id)initWithInvocation:(NSInvocation *)inv
	 * @Description : Returns an NSInvocationOperation object initialized with the specified invocation object.
	 * @param inv The invocation object identifying the target object, selector, and parameter objects.
	 * @return Return Value An initialized NSInvocationOperation object or nil if the object could not be initialized.
	 * @Discussion This method is the designated initializer. The receiver tells the invocation object to retain its arguments.
	 **/
	
	public Object initWithInvocation(Message inv) {
		inv.setTarget(shandler);
		return init();
	}

	/**
	 * @Signature: invocation
	 * @Declaration : - (NSInvocation *)invocation
	 * @Description : Returns the receiver’s invocation object.
	 * @return Return Value The invocation object identifying the target object, selector, and parameters to use to execute the operation’s
	 *         task.
	 **/
	
	Message invocation() {
		return Message.obtain();
	}

	/**
	 * @Signature: result
	 * @Declaration : - (id)result
	 * @Description : Returns the result of the invocation or method.
	 * @return Return Value The object returned by the method or an NSValue object containing the return value if it is not an object. If
	 *         the method or invocation is not finished executing, this method returns nil.
	 * @Discussion If an exception was raised during the execution of the method or invocation, this method raises that exception again. If
	 *             the operation was cancelled or the invocation or method has a void return type, calling this method raises an exception;
	 *             see “Result Exceptions.
	 **/
	
	public Object result() {
		return Message.obtain().getData();
	}

	@Override
	public void main() {
		//
	}

	@Override
	public Object init() {

		return null;
	}

}