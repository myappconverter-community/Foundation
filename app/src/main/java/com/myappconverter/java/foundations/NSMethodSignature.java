
package com.myappconverter.java.foundations;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import android.util.Log;



public class NSMethodSignature extends NSObject {

	private String classeName;
	private String methodName;
	private Method wrappedMethod;
	private Class<?> wrappedClass;
	private Object returnType;

	public NSMethodSignature(String c, String m) {
		classeName = c;
		methodName = m;
        try {
            wrappedClass = Class.forName(c);
        }
        catch (Exception e) {
            Logger.getLogger("context", String.valueOf(e));
        }
	}

	public NSMethodSignature() {
	}

	public Method getWrappedMethod() {
		return wrappedMethod;
	}

	public void setWrappedMethod(Method wrappedMethod) {
		this.wrappedMethod = wrappedMethod;
	}

	public NSMethodSignature(Class clazz, Method method) {
		wrappedClass = clazz;
		setWrappedMethod(method);
	}

	// Creating a Method Signature Object

	/**
	 * @Signature: signatureWithObjCTypes:
	 * @Declaration : + (NSMethodSignature *)signatureWithObjCTypes:(const char *)types
	 * @Description : Returns an NSMethodSignature object for the given Objective C method type string.
	 * @param types An array of characters containing the type encodings for the method arguments. Indices begin with 0. The hidden
	 *            arguments self (of type id) and _cmd (of type SEL) are at indices 0 and 1; method-specific arguments begin at index 2.
	 * @return Return Value An NSMethodSignature object for the given Objective C method type string in types.
	 * @Discussion
	 **/
	
	public static NSMethodSignature signatureWithObjCTypes(char[] types) {
		NSMethodSignature methodSignature = new NSMethodSignature();
        SEL _cmd = new SEL();
		_cmd.setMethodName(String.valueOf(types[1]));
		return null;
	}

	/**
	 * @Signature: getArgumentTypeAtIndex:
	 * @Declaration : - (const char *)getArgumentTypeAtIndex:(NSUInteger)index
	 * @Description : Returns the type encoding for the argument at a given index.
	 * @param index The index of the argument to get.
	 * @return Return Value The type encoding for the argument at index.
	 * @Discussion Indices begin with 0. The hidden arguments self (of type id) and _cmd (of type SEL) are at indices 0 and 1;
	 *             method-specific arguments begin at index 2. Raises NSInvalidArgumentException if index is too large for the actual number
	 *             of arguments. Argument types are given as C strings with Objective-C type encoding. This encoding is
	 *             implementation-specific, so applications should use it with caution.
	 **/
	
	public String getArgumentTypeAtIndex(int index) {
		Class[] args = null;
		try {
			args = Class.forName(classeName).getMethod(methodName).getParameterTypes();
		} catch (NoSuchMethodException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		String argsType = args[index].toString();

		return argsType;
	}

	/**
	 * Returns the number of arguments recorded in the receiver.
	 */

	/**
	 * @Signature: numberOfArguments
	 * @Declaration : - (NSUInteger)numberOfArguments
	 * @Description : Returns the number of arguments recorded in the receiver.
	 * @return Return Value The number of arguments recorded in the receiver.
	 * @Discussion There are always at least 2 arguments, because an NSMethodSignature object includes the hidden arguments self and _cmd,
	 *             which are the first two arguments passed to every method implementation.
	 **/
	
	public Integer numberOfArguments() {
		Class[] args = null;

		try {
			args = Class.forName(classeName).getMethod(methodName).getParameterTypes();
		} catch (NoSuchMethodException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		return args.length;
	}

	/**
	 * Returns the number of bytes that the arguments, taken together, occupy on the stack.
	 */

	/**
	 * @Signature: frameLength
	 * @Declaration : - (NSUInteger)frameLength
	 * @Description : Returns the number of bytes that the arguments, taken together, occupy on the stack.
	 * @return Return Value The number of bytes that the arguments, taken together, occupy on the stack.
	 * @Discussion This number varies with the hardware architecture the application runs on.
	 **/
	
	public Integer frameLength() {
		String argsallType = "";

		Class[] args = null;

		try {
			args = Class.forName(classeName).getMethod(methodName).getParameterTypes();
		} catch (NoSuchMethodException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		for (int i = 0; i < args.length; i++) {
			argsallType = argsallType + args[i].toString();
		}
		return argsallType.getBytes().length;
	}

	/**
	 * @Signature: methodReturnType
	 * @Declaration : - (const char *)methodReturnType
	 * @Description : Returns a C string encoding the return type of the method in Objective-C type encoding.
	 * @return Return Value A C string encoding the return type of the method in Objective-C type encoding.
	 * @Discussion This encoding is implementation-specific, so applications should use it with caution.
	 **/
	
	public String methodReturnType() {

		Class retour = null;

		try {
			retour = Class.forName(classeName).getMethod(methodName).getReturnType();
		} catch (NoSuchMethodException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		return retour.toString();
	}

	/**
	 * Returns the number of bytes required for the return value.
	 */

	/**
	 * @Signature: methodReturnLength
	 * @Declaration : - (NSUInteger)methodReturnLength
	 * @Description : Returns the number of bytes required for the return value.
	 * @return Return Value The number of bytes required for the return value.
	 **/
	
	public Integer methodReturnLength() {
		Class retour = null;
		try {
			retour = Class.forName(classeName).getMethod(methodName).getReturnType();
		} catch (NoSuchMethodException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (ClassNotFoundException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		return retour.toString().length();
	}

	public String getClasseName() {
		return classeName;
	}

	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * @Signature: isOneway
	 * @Declaration : - (BOOL)isOneway
	 * @Description : Returns a Boolean value that indicates whether the receiver is asynchronous when invoked through distributed objects.
	 * @return Return Value YES if the receiver is asynchronous when invoked through distributed objects, otherwise NO.
	 * @Discussion If the method is oneway, the sender of the remote message doesn’t block awaiting a reply.
	 **/
	
	public boolean isOneway() {
		return returnType.getClass().getSimpleName().startsWith("V");
	}

}