
package com.myappconverter.java.foundations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.os.Process;
import android.util.Log;



public abstract class NSOperation extends NSObject {

	private boolean mayInterruptIfRunning;


	enum NSOperationQueueDefaultMaxConcurrentOperationCount {
		NSOperationQueueDefaultMaxConcurrentOperationCount (-1);
		int value;

		NSOperationQueueDefaultMaxConcurrentOperationCount(int v) {
			value = v;
		}
	};


	enum NSOperationQueuePriority {
		NSOperationQueuePriorityVeryLow(-8), //
		NSOperationQueuePriorityLow(-4), //
		NSOperationQueuePriorityNormal(0), //
		NSOperationQueuePriorityHigh(4), //
		NSOperationQueuePriorityVeryHigh(8);//
		int value;

		NSOperationQueuePriority(int v) {
			value = v;
		}
	};

	private NSOperationQueuePriority nsOperationQueuePriority;

	private Set<NSOperation> dependencies = new HashSet<NSOperation>();
	int threadPriority;

	NSOperationQueue nsOperationQueue;
	NSOperationBlock completionBlock;
	Thread thread;

	NSOperation nsoperation;

	interface NSOperationBlock {
		public void perform();
	}

	public NSOperation() {
	}

	/**
	 * @Signature: init
	 * @Declaration : - (id)init
	 * @Description : Returns an initialized NSOperation object.
	 * @return Return Value The initialized NSOperation object.
	 * @Discussion Your custom subclasses must call this method. The default implementation initializes the objectâ€™s instance variables and
	 *             prepares it for use.
	 **/
	@Override

	public Object init() {
		return this;

	}

	/**
	 * @Signature: start
	 * @Declaration : - (void)start
	 * @Description : Begins the execution of the operation.
	 * @Discussion The default implementation of this method updates the execution state of the operation and calls the receiverâ€™s main
	 *             method. This method also performs several checks to ensure that the operation can actually run. For example, if the
	 *             receiver was cancelled or is already finished, this method simply returns without calling main. (In OS X v10.5, this
	 *             method throws an exception if the operation is already finished.) If the operation is currently executing or is not ready
	 *             to execute, this method throws an NSInvalidArgumentException exception. In OS X v10.5, this method catches and ignores
	 *             any exceptions thrown by your main method automatically. In OS X v10.6 and later, exceptions are allowed to propagate
	 *             beyond this method. You should never allow exceptions to propagate out of your main method. Note:Â An operation is not
	 *             considered ready to execute if it is still dependent on other operations that have not yet finished. If you are
	 *             implementing a concurrent operation, you must override this method and use it to initiate your operation. Your custom
	 *             implementation must not call super at any time. In addition to configuring the execution environment for your task, your
	 *             implementation of this method must also track the state of the operation and provide appropriate state transitions. When
	 *             the operation executes and subsequently finishes its work, it should generate KVO notifications for the isExecuting and
	 *             isFinished key paths respectively. For more information about manually generating KVO notifications, see Key-Value
	 *             Observing Programming Guide. You can call this method explicitly if you want to execute your operations manually.
	 *             However, it is a programmer error to call this method on an operation object that is already in an operation queue or to
	 *             queue the operation after calling this method. Once you add an operation object to a queue, the queue assumes all
	 *             responsibility for it.
	 **/

	public void start() {
		thread.start();
	}

	/**
	 * @Signature: main
	 * @Declaration : - (void)main
	 * @Description : Performs the receiverâ€™s non-concurrent task.
	 * @Discussion The default implementation of this method does nothing. You should override this method to perform the desired task. In
	 *             your implementation, do not invoke super. If you are implementing a concurrent operation, you are not required to
	 *             override this method but may do so if you plan to call it from your custom start method.
	 **/

	public abstract void main();

	/**
	 * @Signature: completionBlock
	 * @Declaration : - (void (^)(void))completionBlock
	 * @Description : Returns the block to execute when the operationâ€™s main task is complete.
	 * @return Return Value The block to execute after the operationâ€™s main task is completed. This block takes no parameters and has no
	 *         return value.
	 * @Discussion The completion block you provide is executed when the value returned by the isFinished method changes to YES. Thus, this
	 *             block is executed by the operation object after the operationâ€™s primary task is finished or cancelled.
	 **/

	public NSOperationBlock completionBlock() {
		if (isFinished() == true) {
			return completionBlock;
		} else {
			return null;
		}
	}

	/**
	 * @Signature: setCompletionBlock:
	 * @Declaration : - (void)setCompletionBlock:(void (^)(void))block
	 * @Description : Sets the block to execute when the operation has finished executing.
	 * @param block The block to be executed when the operation finishes. This method creates a copy of the specified block. The block
	 *            itself should take no parameters and have no return value.
	 * @Discussion The exact execution context for your completion block is not guaranteed but is typically a secondary thread. Therefore,
	 *             you should not use this block to do any work that requires a very specific execution context. Instead, you should shunt
	 *             that work to your applicationâ€™s main thread or to the specific thread that is capable of doing it. For example, if you
	 *             have a custom thread for coordinating the completion of the operation, you could use the completion block to ping that
	 *             thread. Because the completion block executes after the operation indicates it has finished its task, you must not use a
	 *             completion block to queue additional work considered to be part of that task. An operation object whose isFinished method
	 *             returns YES must be done with all of its task-related work by definition. The completion block should be used to notify
	 *             interested objects that the work is complete or perform other tasks that might be related to, but not part of, the
	 *             operationâ€™s actual task. A finished operation may finish either because it was cancelled or because it successfully
	 *             completed its task. You should take that fact into account when writing your block code. Similarly, you should not make
	 *             any assumptions about the successful completion of dependent operations, which may themselves have been cancelled.
	 **/

	public void setCompletionBlock(NSOperationBlock block) {
		if (isFinished() == true) {
			completionBlock = block;
		}

	}

	/**
	 * @Signature: cancel
	 * @Declaration : - (void)cancel
	 * @Description : Advises the operation object that it should stop executing its task.
	 * @Discussion This method does not force your operation code to stop. Instead, it updates the objectâ€™s internal flags to reflect the
	 *             change in state. If the operation has already finished executing, this method has no effect. Canceling an operation that
	 *             is currently in an operation queue, but not yet executing, makes it possible to remove the operation from the queue
	 *             sooner than usual. In OS X v10.6 and later, if an operation is in a queue but waiting on unfinished dependent operations,
	 *             those operations are subsequently ignored. Because it is already cancelled, this behavior allows the operation queue to
	 *             call the operationâ€™s start method sooner and clear the object out of the queue. If you cancel an operation that is not in
	 *             a queue, this method immediately marks the object as finished. In each case, marking the object as ready or finished
	 *             results in the generation of the appropriate KVO notifications. In versions of OS X prior to 10.6, an operation object
	 *             remains in the queue until all of its dependencies are removed through the normal processes. Thus, the operation must
	 *             wait until all of its dependent operations finish executing or are themselves cancelled and have their start method
	 *             called. For more information on what you must do in your operation objects to support cancellation, see â€œResponding to
	 *             the Cancel Command.â€?
	 **/

	public void cancel() {
		thread.interrupt();
	}

	/**
	 * @Signature: isCancelled
	 * @Declaration : - (BOOL)isCancelled
	 * @Description : Returns a Boolean value indicating whether the operation has been cancelled.
	 * @return Return Value YES if the operation was explicitly cancelled by an invocation of the receiverâ€™s cancel method; otherwise, NO.
	 *         This method may return YES even if the operation is currently executing.
	 * @Discussion Canceling an operation does not actively stop the receiverâ€™s code from executing. An operation object is responsible for
	 *             calling this method periodically and stopping itself if the method returns YES. You should always call this method before
	 *             doing any work towards accomplishing the operationâ€™s task, which typically means calling it at the beginning of your
	 *             custom main method. It is possible for an operation to be cancelled before it begins executing or at any time while it is
	 *             executing. Therefore, calling this method at the beginning of your main method (and periodically throughout that method)
	 *             lets you exit as quickly as possible when an operation is cancelled.
	 **/

	public boolean isCancelled() {
		return thread.isInterrupted();
	}

	/**
	 * @Signature: isExecuting
	 * @Declaration : - (BOOL)isExecuting
	 * @Description : Returns a Boolean value indicating whether the operation is currently executing.
	 * @return Return Value YES if the operation is executing; otherwise, NO if the operation has not been started or is already finished.
	 * @Discussion If you are implementing a concurrent operation, you should override this method to return the execution state of your
	 *             operation. If you do override it, be sure to generate KVO notifications for the isExecuting key path whenever the
	 *             execution state of your operation object changes. For more information about manually generating KVO notifications, see
	 *             Key-Value Observing Programming Guide.
	 **/

	public boolean isExecuting() {
		if (thread.getState() == Thread.State.RUNNABLE) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @Signature: isFinished
	 * @Declaration : - (BOOL)isFinished
	 * @Description : Returns a Boolean value indicating whether the operation is done executing.
	 * @return Return Value YES if the operation is no longer executing; otherwise, NO.
	 * @Discussion If you are implementing a concurrent operation, you should override this method and return a Boolean to indicate whether
	 *             your operation is currently finished. If you do override it, be sure to generate appropriate KVO notifications for the
	 *             isFinished key path when the completion state of your operation object changes. For more information about manually
	 *             generating KVO notifications, see Key-Value Observing Programming Guide.
	 **/

	public boolean isFinished() {
		if (thread.getState() == Thread.State.TERMINATED) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @Signature: isConcurrent
	 * @Declaration : - (BOOL)isConcurrent
	 * @Description : Returns a Boolean value indicating whether the operation runs asynchronously.
	 * @return Return Value YES if the operation runs asynchronously with respect to the current thread or NO if the operation runs
	 *         synchronously on whatever thread started it. This method returns NO by default.
	 * @Discussion If you are implementing a concurrent operation, you must override this method and return YES from your implementation.
	 *             For more information about the differences between concurrent and non-concurrent operations, see â€œConcurrent Versus
	 *             Non-Concurrent Operations.â€? In OS X v10.6 and later, operation queues ignore the value returned by this method and always
	 *             start operations on a separate thread.
	 **/

	// TODO is not complete yet
	public boolean isConcurrent() {
		return mayInterruptIfRunning;

		/*
		 * if(thread.getState().equals("RUNNABLE ")){ return true; }else{ return false; }
		 */
	}

	/**
	 * @Signature: isReady
	 * @Declaration : - (BOOL)isReady
	 * @Description : Returns a Boolean value indicating whether the receiverâ€™s operation can be performed now.
	 * @return Return Value YES if the operation can be performed now; otherwise, NO.
	 * @Discussion Operations may not be ready due to dependencies on other operations or because of external conditions that might prevent
	 *             needed data from being ready. The NSOperation class manages dependencies on other operations and reports the readiness of
	 *             the receiver based on those dependencies. If you want to use custom conditions to determine the readiness of your
	 *             operation object, you can override this method and return a value that accurately reflects the readiness of the receiver.
	 *             If you do so, your custom implementation should invoke super and incorporate its return value into the readiness state of
	 *             the object. Your custom implementation must also generate appropriate KVO notifications for the isReady key path.
	 **/

	public boolean isReady() {
		if (thread.getState() == Thread.State.RUNNABLE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Signature: addDependency:
	 * @Declaration : - (void)addDependency:(NSOperation *)operation
	 * @Description : Makes the receiver dependent on the completion of the specified operation.
	 * @param operation The operation on which the receiver should depend. The same dependency should not be added more than once to the
	 *            receiver, and the results of doing so are undefined.
	 * @Discussion The receiver is not considered ready to execute until all of its dependent operations have finished executing. If the
	 *             receiver is already executing its task, adding dependencies has no practical effect. This method may change the isReady
	 *             and dependencies properties of the receiver. It is a programmer error to create any circular dependencies among a set of
	 *             operations. Doing so can cause a deadlock among the operations and may freeze your program.
	 **/

	public void addDependency(NSOperation operation) {
		dependencies.add(operation);
		for (NSOperation nso : dependencies) {
			if (!nso.isFinished()) {
				try {
					thread.wait();
				} catch (InterruptedException e) {
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
					Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
				}
			}
		}
	}

	/**
	 * @Signature: removeDependency:
	 * @Declaration : - (void)removeDependency:(NSOperation *)operation
	 * @Description : Removes the receiverâ€™s dependence on the specified operation.
	 * @param operation The dependent operation to be removed from the receiver.
	 * @Discussion This method may change the isReady and dependencies properties of the receiver.
	 **/

	public void removeDependency(NSOperation operation) {
		dependencies.remove(operation);
	}

	/**
	 * @Signature: dependencies
	 * @Declaration : - (NSArray *)dependencies
	 * @Description : Returns a new array object containing the operations on which the receiver is dependent.
	 * @return Return Value A new array object containing the NSOperation objects.
	 * @Discussion The receiver is not considered ready to execute until all of its dependent operations finish executing. Operations are
	 *             not removed from this dependency list as they finish executing. You can therefore use this list to track all dependent
	 *             operations, including those that have already finished executing. The only way to remove an operation from this list is
	 *             to use the removeDependency: method.
	 **/

	public NSArray<NSOperation> dependencies() {
		return new NSArray(Arrays.asList(dependencies));
	}

	/**
	 * @Signature: queuePriority
	 * @Declaration : - (NSOperationQueuePriority)queuePriority
	 * @Description : Returns the priority of the operation in an operation queue.
	 * @return Return Value The relative priority of the operation. The returned value always corresponds to one of the predefined
	 *         constants. (For a list of valid values, see â€œOperation Priorities.â€?) If no priority is explicitly set, this method returns
	 *         NSOperationQueuePriorityNormal.
	 **/

	public NSOperationQueuePriority queuePriority() {
		return nsOperationQueuePriority;
	}

	/**
	 * @Signature: setQueuePriority:
	 * @Declaration : - (void)setQueuePriority:(NSOperationQueuePriority)priority
	 * @Description : Sets the priority of the operation when used in an operation queue.
	 * @param priority The relative priority of the operation. For a list of valid values, see â€œOperation Priorities.â€?
	 * @Discussion You should use priority values only as needed to classify the relative priority of non-dependent operations. Priority
	 *             values should not be used to implement dependency management among different operation objects. If you need to establish
	 *             dependencies between operations, use the addDependency: method instead. If you attempt to specify a priority value that
	 *             does not match one of the defined constants, this method automatically adjusts the value you specify towards the
	 *             NSOperationQueuePriorityNormal priority, stopping at the first valid constant value. For example, if you specified the
	 *             value -10, this method would adjust that value to match the NSOperationQueuePriorityVeryLow constant. Similarly, if you
	 *             specified +10, this method would adjust the value to match the NSOperationQueuePriorityVeryHigh constant.
	 **/

	public void setQueuePriority(NSOperationQueuePriority priority) {
		this.nsOperationQueuePriority = priority;
	}

	/**
	 * @Signature: threadPriority
	 * @Declaration : - (double)threadPriority
	 * @Description : Returns the thread priority to use when executing the operation.
	 * @return Return Value A floating-point number in the range 0.0 to 1.0, where 1.0 is the highest priority. The default thread priority
	 *         is 0.5.
	 **/

	public double threadPriority() {
		return Process.getThreadPriority(getThreadPriority());
	}

	/**
	 * @Signature: setThreadPriority:
	 * @Declaration : - (void)setThreadPriority:(double)priority
	 * @Description : Sets the thread priority to use when executing the operation.
	 * @param priority The new thread priority, specified as a floating-point number in the range 0.0 to 1.0, where 1.0 is the highest
	 *            priority.
	 * @Discussion The value you specify is mapped to the operating systemâ€™s priority values. The specified thread priority is applied to
	 *             the thread only while the operationâ€™s main method is executing. It is not applied while the operationâ€™s completion block
	 *             is executing. For a concurrent operation in which you create your own thread, you must set the thread priority yourself
	 *             in your custom start method and reset the original priority when the operation is finished.
	 **/

	public void setThreadPriority(double priority) {

		Process.setThreadPriority((int) priority);

	}

	/**
	 * @Signature: waitUntilFinished
	 * @Declaration : - (void)waitUntilFinished
	 * @Description : Blocks execution of the current thread until the receiver finishes.
	 * @Discussion The receiver should never call this method on itself and should avoid calling it on any operations submitted to the same
	 *             operation queue as itself. Doing so can cause the operation to deadlock. It is generally safe to call this method on an
	 *             operation that is in a different operation queue, although it is still possible to create deadlocks if each operation
	 *             waits on the other. A typical use for this method would be to call it from the code that created the operation in the
	 *             first place. After submitting the operation to a queue, you would call this method to wait until that operation finished
	 *             executing.
	 **/

	public void waitUntilFinished() {
		Thread thread = Thread.currentThread();
		if (!this.isFinished() == true) {
			try {
				thread.wait();
			} catch (InterruptedException e) {

				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
		}

	}

	public int getThreadPriority() {
		return threadPriority;
	}

	public void setThreadPriority(int threadPriority) {
		this.threadPriority = threadPriority;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

}