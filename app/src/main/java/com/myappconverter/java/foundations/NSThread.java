
package com.myappconverter.java.foundations;


import android.os.Looper;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;



public class NSThread extends NSObject {

	// Initializing an NSThread Object

	Thread wrappedThread;
	boolean isMultithreading = true;

	public NSThread() {
		wrappedThread = new Thread("NSThread");
	}

	public NSThread(Thread aThread) {
		wrappedThread = aThread;
	}

	// Initializing an NSThread Object

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Returns an initialized NSThread object.
	 * @return Return Value An initialized NSThread object.
	 * @Discussion This is the designated initializer for NSThread.
	 **/
	@Override

	public NSThread init() {
		NSThread nsth = new NSThread();
		nsth.wrappedThread = new Thread("init");
		return nsth;
	}

	/**
	 * @Signature: initWithTarget:selector:object:
	 * @Declaration : - (id)initWithTarget:(id)target selector:(SEL)selector object:(id)argument
	 * @Description : Returns an NSThread object initialized with the given arguments.
	 * @param target The object to which the message specified by selector is sent.
	 * @param selector The selector for the message to send to target. This selector must take only one argument and must not have a return
	 *            value.
	 * @param argument The single argument passed to the target. May be nil.
	 * @return Return Value An NSThread object initialized with the given arguments.
	 * @Discussion For non garbage-collected applications, the method selector is responsible for setting up an autorelease pool for the
	 *             newly detached thread and freeing that pool before it exits. Garbage-collected applications do not need to create an
	 *             autorelease pool. The objects target and argument are retained during the execution of the detached thread. They are
	 *             released when the thread finally exits.
	 **/

	public NSThread initWithTargetSelectorObject(final Object target, SEL selector, final Object anArgument) {

		// TODO test
		Thread nthread = null;
		Method[] methods = target.getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			if (method.getName().equalsIgnoreCase(selector.getMethodName()) &&
					method.getReturnType().equals(Void.class)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (!parameterTypes[0].isAssignableFrom(anArgument.getClass())) {

					nthread = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								method.invoke(target, anArgument);
							} catch (IllegalAccessException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							} catch (IllegalArgumentException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							} catch (InvocationTargetException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							}
						}
					}, "initWithTarget");

				}
			}
		}
		return new NSThread(nthread);
	}

	// Starting a Thread

	/**
	 * @Signature: detachNewThreadSelector:toTarget:withObject:
	 * @Declaration : + (void)detachNewThreadSelector:(SEL)aSelector toTarget:(id)aTarget withObject:(id)anArgument
	 * @Description : Detaches a new thread and uses the specified selector as the thread entry point.
	 * @param aSelector The selector for the message to send to the target. This selector must take only one argument and must not have a
	 *            return value.
	 * @param aTarget The object that will receive the message aSelector on the new thread.
	 * @param anArgument The single argument passed to the target. May be nil.
	 * @Discussion For non garbage-collected applications, the method aSelector is responsible for setting up an autorelease pool for the
	 *             newly detached thread and freeing that pool before it exits. Garbage-collected applications do not need to create an
	 *             autorelease pool. The objects aTarget and anArgument are retained during the execution of the detached thread, then
	 *             released. The detached thread is exited (using the exit class method) as soon as aTarget has completed executing the
	 *             aSelector method. If this thread is the first thread detached in the application, this method posts the
	 *             NSWillBecomeMultiThreadedNotification with object nil to the default notification center.
	 **/

	public static void detachNewThreadSelectorToTargetWithObject(SEL aSelector, final Object aTarget, final Object anArgument) {

		Thread nthread = null;
		Method[] methods = aTarget.getClass().getMethods();
		List<Method> methodList = Arrays.asList(methods);
		for (final Method method : methodList) {
			if (method.getName().equalsIgnoreCase(aSelector.getMethodName()) &&
					method.getReturnType().equals(Void.class)) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (!parameterTypes[0].isAssignableFrom(anArgument.getClass())) {

					nthread = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								method.invoke(aTarget, anArgument);
							} catch (IllegalAccessException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							} catch (IllegalArgumentException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							} catch (InvocationTargetException e) {
								Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
							}
						}
					}, "detachNewThreadSelector");

				}
			}
		}
		if (nthread != null) {
			nthread.start();
		}
	}

	/**
	 * @Signature: start
	 * @Declaration : - (void)start
	 * @Description : Starts the receiver.
	 * @Discussion This method spawns the new thread and invokes the receiverâ€™s main method on the new thread. If you initialized the
	 *             receiver with a target and selector, the default main method invokes that selector automatically. If this thread is the
	 *             first thread detached in the application, this method posts the NSWillBecomeMultiThreadedNotification with object nil to
	 *             the default notification center.
	 **/

	public void start() {
		wrappedThread.start();
		isMultithreading = true;

	}

	/**
	 * @Signature: main
	 * @Declaration : - (void)main
	 * @Description : The main entry point routine for the thread.
	 * @Discussion The default implementation of this method takes the target and selector used to initialize the receiver and invokes the
	 *             selector on the specified target. If you subclass NSThread, you can override this method and use it to implement the main
	 *             body of your thread instead. If you do so, you do not need to invoke super. You should never invoke this method directly.
	 *             You should always start your thread by invoking the start method.
	 **/

	public void main() {
		// FIXME ?? The run() method was invoked on a thread rather than the start() method.
		// thread.run();
		wrappedThread.start();
	}

	// Stopping a Thread

	/**
	 * @Signature: sleepUntilDate:
	 * @Declaration : + (void)sleepUntilDate:(NSDate *)aDate
	 * @Description : Blocks the current thread until the time specified.
	 * @param aDate The time at which to resume processing.
	 * @Discussion No run loop processing occurs while the thread is blocked.
	 **/

	public static void sleepUntilDate(NSDate aDate) {
		double mills = (aDate.timeIntervalSinceReferenceDate() - System.currentTimeMillis());
		try {
			Thread.sleep(Math.round(mills));
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	/**
	 * @Signature: sleepForTimeInterval:
	 * @Declaration : + (void)sleepForTimeInterval:(NSTimeInterval)ti
	 * @Description : Sleeps the thread for a given time interval.
	 * @param ti The duration of the sleep.
	 * @Discussion No run loop processing occurs while the thread is blocked.
	 **/

	public static void sleepForTimeInterval(double ti) {
		try {
			Thread.sleep(Math.round((long) ti));
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Signature: exit
	 * @Declaration : + (void)exit
	 * @Description : Terminates the current thread.
	 * @Discussion This method uses the currentThread class method to access the current thread. Before exiting the thread, this method
	 *             posts the NSThreadWillExitNotification with the thread being exited to the default notification center. Because
	 *             notifications are delivered synchronously, all observers of NSThreadWillExitNotification are guaranteed to receive the
	 *             notification before the thread exits. Invoking this method should be avoided as it does not give your thread a chance to
	 *             clean up any resources it allocated during its execution.
	 **/

	public static void exit() {
		Thread.currentThread().interrupt();
	}

	/**
	 * @Signature: cancel
	 * @Declaration : - (void)cancel
	 * @Description : Changes the cancelled state of the receiver to indicate that it should exit.
	 * @Discussion The semantics of this method are the same as those used for the NSOperation object. This method sets state information in
	 *             the receiver that is then reflected by the isCancelled method. Threads that support cancellation should periodically call
	 *             the isCancelled method to determine if the thread has in fact been cancelled, and exit if it has been. For more
	 *             information about cancellation and operation objects, see NSOperation Class Reference.
	 **/

	public void cancel() {
		wrappedThread.stop();
	}

	// Determining the Thread's Execution State
	/**
	 * @Signature: isExecuting
	 * @Declaration : - (BOOL)isExecuting
	 * @Description : Returns a Boolean value that indicates whether the receiver is executing.
	 * @return Return Value YES if the receiver is executing, otherwise NO.
	 **/

	public boolean isExecuting() {
		return wrappedThread.isAlive();
	}

	/**
	 * @Signature: isFinished
	 * @Declaration : - (BOOL)isFinished
	 * @Description : Returns a Boolean value that indicates whether the receiver has finished execution.
	 * @return Return Value YES if the receiver has finished execution, otherwise NO.
	 **/

	public boolean isFinished() {
		return wrappedThread.isInterrupted();
	}

	/**
	 * @Signature: isCancelled
	 * @Declaration : - (BOOL)isCancelled
	 * @Description : Returns a Boolean value that indicates whether the receiver is cancelled.
	 * @return Return Value YES if the receiver has been cancelled, otherwise NO.
	 * @Discussion If your thread supports cancellation, it should call this method periodically and exit if it ever returns YES.
	 **/

	public boolean isCancelled() {
		return wrappedThread.isInterrupted();
	}

	// Working with the Main Thread

	/**
	 * @Signature: isMainThread
	 * @Declaration : - (BOOL)isMainThread
	 * @Description : Returns a Boolean value that indicates whether the receiver is the main thread.
	 * @return Return Value YES if the receiver is the main thread, otherwise NO.
	 **/

	public boolean isMainThread() {
		return (wrappedThread == Looper.getMainLooper().getThread());
	}

	/**
	 * @Signature: mainThread
	 * @Declaration : + (NSThread *)mainThread
	 * @Description : Returns the NSThread object representing the main thread.
	 * @return Return Value The NSThread object representing the main thread.
	 **/

	public static NSThread mainThread() {
		NSThread nst = new NSThread(Looper.getMainLooper().getThread());
		return nst;
	}

	public static boolean isMainThreadStatic() {
		if (Looper.myLooper().getThread() == Looper.getMainLooper().getThread())
			return true;
		return false;
	}

	// Querying the Environment
	/**
	 * @Signature: isMultiThreaded
	 * @Declaration : + (BOOL)isMultiThreaded
	 * @Description : Returns whether the application is multithreaded.
	 * @return Return Value YES if the application is multithreaded, NO otherwise.
	 * @Discussion An application is considered multithreaded if a thread was ever detached from the main thread using either
	 *             detachNewThreadSelector:toTarget:withObject: or start. If you detached a thread in your application using a non-Cocoa
	 *             API, such as the POSIX or Multiprocessing Services APIs, this method could still return NO. The detached thread does not
	 *             have to be currently running for the application to be considered multithreadedâ€”this method only indicates whether a
	 *             single thread has been spawned.
	 **/

	public boolean isMultiThreaded() {
		return isMultithreading;
	}

	/**
	 * @Signature: currentThread
	 * @Declaration : + (NSThread *)currentThread
	 * @Description : Returns the thread object representing the current thread of execution.
	 * @return Return Value A thread object representing the current thread of execution.
	 **/

	public static NSThread currentThread() {
		return new NSThread(Thread.currentThread());
	}

	/**
	 * @Signature: callStackReturnAddresses
	 * @Declaration : + (NSArray *)callStackReturnAddresses
	 * @Description : Returns an array containing the call stack return addresses.
	 * @return Return Value An array containing the call stack return addresses. This value is nil by default.
	 **/

	public static NSArray<?> callStackReturnAddresses() {
		NSArray nsArray = new NSMutableArray();
		nsArray.getWrappedList().add(Arrays.asList(Thread.currentThread().getStackTrace()));
		return nsArray;
	}

	/**
	 * @Signature: callStackSymbols
	 * @Declaration : + (NSArray *)callStackSymbols
	 * @Description : Returns an array containing the call stack symbols.
	 * @return Return Value An array containing the call stack symbols.
	 * @Discussion This method returns an array of strings describing the call stack backtrace of the current thread at the moment this
	 *             method was called. The format of each string is non-negotiable and is determined by the backtrace_symbols() API
	 **/

	public NSArray callStackSymbols() {
		NSArray nsArray = new NSMutableArray();
		nsArray.getWrappedList().add(Arrays.asList(wrappedThread.getStackTrace()));
		return nsArray;

	}

	// Working with Thread Properties
	/**
	 * @Signature: threadDictionary
	 * @Declaration : - (NSMutableDictionary *)threadDictionary
	 * @Description : Returns the thread object's dictionary.
	 * @return Return Value The thread object's dictionary.
	 * @Discussion You can use the returned dictionary to store thread-specific data. The thread dictionary is not used during any
	 *             manipulations of the NSThread objectâ€”it is simply a place where you can store any interesting data. For example,
	 *             Foundation uses it to store the threadâ€™s default NSConnection and NSAssertionHandler instances. You may define your own
	 *             keys for the dictionary.
	 **/

	public NSMutableDictionary threadDictionary() {
		return null;
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name of the receiver.
	 * @return Return Value The name of the receiver.
	 **/
	public NSString name() {
		return new NSString(wrappedThread.getName());
	}

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)n
	 * @Description : Sets the name of the receiver.
	 * @param n The name for the receiver.
	 **/
	public void setName(NSString name) {
		wrappedThread.setName(name.getWrappedString());
	}

	/**
	 * @Signature: stackSize
	 * @Declaration : - (NSUInteger)stackSize
	 * @Description : Returns the stack size of the receiver.
	 * @return Return Value The stack size of the receiver, in bytes.
	 **/

	public int stackSize() {
		return 0;

	}

	/**
	 * @Signature: setStackSize:
	 * @Declaration : - (void)setStackSize:(NSUInteger)s
	 * @Description : Sets the stack size of the receiver.
	 * @param s The stack size for the receiver. This value must be in bytes and a multiple of 4KB.
	 * @Discussion You must call this method before starting your thread. Setting the stack size after the thread has started changes the
	 *             attribute size (which is reflected by the stackSize method), but it does not affect the actual number of pages set aside
	 *             for the thread.
	 **/

	public void setStackSize(int s) {
	}

	// Working with Thread Priorities

	/**
	 * @Signature: threadPriority
	 * @Declaration : + (double)threadPriority
	 * @Description : Returns the current threadâ€™s priority.
	 * @return Return Value The current threadâ€™s priority, which is specified by a floating point number from 0.0 to 1.0, where 1.0 is
	 *         highest priority.
	 * @Discussion The priorities in this range are mapped to the operating system's priority values. A â€œtypicalâ€? thread priority might be
	 *             0.5, but because the priority is determined by the kernel, there is no guarantee what this value actually will be.
	 **/

	public static double _threadPriority() {
		return Thread.currentThread().getPriority();
	}

	/**
	 * @Signature: threadPriority
	 * @Declaration : - (double)threadPriority
	 * @Description : Returns the receiverâ€™s priority
	 * @return Return Value The threadâ€™s priority, which is specified by a floating point number from 0.0 to 1.0, where 1.0 is highest
	 *         priority.
	 * @Discussion The priorities in this range are mapped to the operating system's priority values. A â€œtypicalâ€? thread priority might be
	 *             0.5, but because the priority is determined by the kernel, there is no guarantee what this value actually will be.
	 **/

	public double threadPriority() {
		return wrappedThread.getPriority();
	}

	/**
	 * @Signature: setThreadPriority:
	 * @Declaration : - (void)setThreadPriority:(double)priority
	 * @Description : Sets the receiverâ€™s priority.
	 * @param priority The new priority, specified with a floating point number from 0.0 to 1.0, where 1.0 is highest priority.
	 * @Discussion The priorities in this range are mapped to the operating system's priority values.
	 **/

	public void setThreadPriority(double priority) {
		wrappedThread.setPriority((int) priority);
	}

	/**
	 * @Signature: setThreadPriority:
	 * @Declaration : + (BOOL)setThreadPriority:(double)priority
	 * @Description : Sets the current threadâ€™s priority.
	 * @param priority The new priority, specified with a floating point number from 0.0 to 1.0, where 1.0 is highest priority.
	 * @return Return Value YES if the priority assignment succeeded, NO otherwise.
	 * @Discussion The priorities in this range are mapped to the operating system's priority values.
	 **/

	public static boolean _setThreadPriority(double priority) {
		try {
			Thread.currentThread().setPriority((int) priority);
			return true;
		} catch (Exception e) {
			Logger.getLogger("context",String.valueOf(e));
			return false;
		}
	}
}