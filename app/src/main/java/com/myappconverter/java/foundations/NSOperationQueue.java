
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;




public class NSOperationQueue extends NSObject {

	private int corePoolSize = 1;
	private int maximumPoolSize =1 ;
	private long keepAliveTime;
	private TimeUnit unit;
	private BlockingQueue<Runnable> workQueue;
	private static ThreadPoolExecutor threadPoolExecutor;
	private NSString name;
	private Runnable oper;

	private interface NSOperationQueueBlock {
		public void perform(Runnable operation);
	}

	/**
	 * @Signature: addOperation:
	 * @Declaration : - (void)addOperation:(NSOperation *)operation
	 * @Description : Adds the specified operation object to the receiver.
	 * @param operation The operation object to be added to the queue. In memory-managed applications, this object is retained by the
	 *            operation queue. In garbage-collected applications, the queue strongly references the operation object.
	 * @Discussion Once added, the specified operation remains in the queue until it finishes executing. An operation object can be in at
	 *             most one operation queue at a time and this method throws an NSInvalidArgumentException exception if the operation is
	 *             already in another queue. Similarly, this method throws an NSInvalidArgumentException exception if the operation is
	 *             currently executing or has already finished executing.
	 **/

	public void addOperation(NSOperation operation) {
		workQueue = new SynchronousQueue<Runnable>();
		workQueue.add(operation.getThread());
		threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		threadPoolExecutor.execute(operation.thread);
	}

	/**
	 * @Signature: addOperations:waitUntilFinished:
	 * @Declaration : - (void)addOperations:(NSArray *)ops waitUntilFinished:(BOOL)wait
	 * @Description : Adds the specified array of operations to the queue.
	 * @param ops The array of NSOperation objects that you want to add to the receiver.
	 * @param wait If YES, the current thread is blocked until all of the specified operations finish executing. If NO, the operations are
	 *            added to the queue and control returns immediately to the caller.
	 * @Discussion An operation object can be in at most one operation queue at a time and cannot be added if it is currently executing or
	 *             finished. This method throws an NSInvalidArgumentException exception if any of those error conditions are true for any of
	 *             the operations in the ops parameter. Once added, the specified operation remains in the queue until its isFinished method
	 *             returns YES.
	 **/

	public void addOperationsWaitUntilFinished(NSArray<NSOperation> ops, boolean wait) {

		if (wait) {
			try {
				threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
		} else {
			threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
			workQueue = new SynchronousQueue<Runnable>();
			for (int i = 0; i < ops.count(); i++) {
				workQueue.add(((NSOperation) ops.objectAtIndex(i)).thread);
				threadPoolExecutor.execute(ops.objectAtIndex(i).thread);
			}
		}

	}

	/**
	 * @Signature: addOperationWithBlock:
	 * @Declaration : - (void)addOperationWithBlock:(void (^)(void))block
	 * @Description : Wraps the specified block in an operation object and adds it to the receiver.
	 * @param block The block to execute from the operation object. The block should take no parameters and have no return value.
	 * @Discussion This method adds a single block to the receiver by first wrapping it in an operation object. You should not attempt to
	 *             get a reference to the newly created operation object or divine its type information.
	 **/

	public void addOperationWithBlock(NSOperationQueueBlock block) {
		block.perform(oper);
	}

	/**
	 * @Signature: operations
	 * @Declaration : - (NSArray *)operations
	 * @Description : Returns a new array containing the operations currently in the queue.
	 * @return Return Value A new array object containing the NSOperation objects in the order in which they were added to the queue.
	 * @Discussion You can use this method to access the operations queued at any given moment. Operations remain queued until they finish
	 *             their task. Therefore, the returned array may contain operations that are either executing or waiting to be executed. The
	 *             list may also contain operations that were executing when the array was initially created but have subsequently finished.
	 **/

	public NSArray<NSOperation> operations() {
		workQueue = threadPoolExecutor.getQueue();
		List<Runnable> operations = new ArrayList<Runnable>();
		for (Runnable op : workQueue) {
			operations.add(op);

		}
		return new NSArray(operations);
	}

	/**
	 * @Signature: operationCount
	 * @Declaration : - (NSUInteger)operationCount
	 * @Description : Returns the number of operations currently in the queue.
	 * @return Return Value The number of operations in the queue.
	 * @Discussion The value returned by this method reflects the instantaneous number of objects in the queue and changes as operations are
	 *             completed. As a result, by the time you use the returned value, the actual number of operations may be different. You
	 *             should therefore use this value only for approximate guidance and should not rely on it for object enumerations or other
	 *             precise calculations.
	 **/

	public long operationCount() {
		return threadPoolExecutor.getTaskCount();

	}

	/**
	 * @Signature: cancelAllOperations
	 * @Declaration : - (void)cancelAllOperations
	 * @Description : Cancels all queued and executing operations.
	 * @Discussion This method sends a cancel message to all operations currently in the queue. Queued operations are cancelled before they
	 *             begin executing. If an operation is already executing, it is up to that operation to recognize the cancellation and stop
	 *             what it is doing.
	 **/

	public void cancelAllOperations() {
		workQueue = threadPoolExecutor.getQueue();
		for (Runnable op : workQueue) {
			threadPoolExecutor.remove(op);

		}
	}

	/**
	 * @Signature: waitUntilAllOperationsAreFinished
	 * @Declaration : - (void)waitUntilAllOperationsAreFinished
	 * @Description : Blocks the current thread until all of the receiverâ€™s queued and executing operations finish executing.
	 * @Discussion When called, this method blocks the current thread and waits for the receiverâ€™s current and queued operations to finish
	 *             executing. While the current thread is blocked, the receiver continues to launch already queued operations and monitor
	 *             those that are executing. During this time, the current thread cannot add operations to the queue, but other threads may.
	 *             Once all of the pending operations are finished, this method returns. If there are no operations in the queue, this
	 *             method returns immediately.
	 **/

	public void waitUntilAllOperationsAreFinished() {
		try {
			threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

	}

	/**
	 * @Signature: maxConcurrentOperationCount
	 * @Declaration : - (NSInteger)maxConcurrentOperationCount
	 * @Description : Returns the maximum number of concurrent operations that the receiver can execute.
	 * @return Return Value The maximum number of concurrent operations set explicitly on the receiver using the
	 *         setMaxConcurrentOperationCount: method. If no value has been explicitly set, this method returns
	 *         NSOperationQueueDefaultMaxConcurrentOperationCount by default.
	 **/

	public int maxConcurrentOperationCount() {
		return threadPoolExecutor.getMaximumPoolSize();
	}

	/**
	 * @Signature: setMaxConcurrentOperationCount:
	 * @Declaration : - (void)setMaxConcurrentOperationCount:(NSInteger)count
	 * @Description : Sets the maximum number of concurrent operations that the receiver can execute.
	 * @param count The maximum number of concurrent operations. Specify the value NSOperationQueueDefaultMaxConcurrentOperationCount if you
	 *            want the receiver to choose an appropriate value based on the number of available processors and other relevant factors.
	 * @Discussion The specified value affects only the receiver and the operations in its queue. Other operation queue objects can also
	 *             execute their maximum number of operations in parallel. Reducing the number of concurrent operations does not affect any
	 *             operations that are currently executing. If you specify the value NSOperationQueueDefaultMaxConcurrentOperationCount
	 *             (which is recommended), the maximum number of operations can change dynamically based on system conditions.
	 **/

	public void setMaxConcurrentOperationCount(int count) {
		threadPoolExecutor.setMaximumPoolSize(count);
	}

	/**
	 * @Signature: setSuspended:
	 * @Declaration : - (void)setSuspended:(BOOL)suspend
	 * @Description : Modifies the execution of pending operations
	 * @param suspend If YES, the queue stops scheduling queued operations for execution. If NO, the queue begins scheduling operations
	 *            again.
	 * @Discussion This method suspends or resumes the execution of operations. Suspending a queue prevents that queue from starting
	 *             additional operations. In other words, operations that are in the queue (or added to the queue later) and are not yet
	 *             executing are prevented from starting until the queue is resumed. Suspending a queue does not stop operations that are
	 *             already running. Operations are removed from the queue only when they finish executing. However, in order to finish
	 *             executing, an operation must first be started. Because a suspended queue does not start any new operations, it does not
	 *             remove any operations (including cancelled operations) that are currently queued and not executing.
	 **/

	public void setSuspended(boolean suspend) {
		if (suspend) {
			try {
				threadPoolExecutor.wait();
			} catch (InterruptedException e) {
				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}
		} else {
			threadPoolExecutor.notifyAll();
		}
	}

	/**
	 * @Signature: isSuspended
	 * @Declaration : - (BOOL)isSuspended
	 * @Description : Returns a Boolean value indicating whether the receiver is scheduling queued operations for execution.
	 * @return Return Value NO if operations are being scheduled for execution; otherwise, YES.
	 * @Discussion If you want to know when the queueâ€™s suspended state changes, configure a KVO observer to observe the suspended key path
	 *             of the operation queue.
	 **/

	public boolean isSuspended() {

		/*
		 * boolean isSusp=false; if(!threadPoolExecutor.isShutdown()){ try { threadPoolExecutor.wait(); isSusp=true; return isSusp; } catch
		 * (InterruptedException e) { Log.d("Exception ","Message :"+e.getMessage()+"\n StackTrace: "+Log.getStackTraceString(e)); } }
		 * return isSusp;
		 */

		return threadPoolExecutor.isShutdown();
	}

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)newName
	 * @Description : Assigns the specified name to the receiver.
	 * @param newName The new name to associate with the receiver.
	 * @Discussion Names provide a way for you to identify your operation queues at run time. Tools may also use this name to provide
	 *             additional context during debugging or analysis of your code.
	 **/

	public void setName(NSString newName) {
		this.name = newName;
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name of the receiver.
	 * @return Return Value The name of the receiver.
	 * @Discussion The default value of this string is â€œNSOperationQueue <id>â€?, where <id> is the memory address of the operation queue. If
	 *             you want to know when a queueâ€™s name changes, configure a KVO observer to observe the name key path of the operation
	 *             queue.
	 **/

	public NSString name() {
		return this.name;
	}

	/**
	 * @Signature: currentQueue
	 * @Declaration : + (id)currentQueue
	 * @Description : Returns the operation queue that launched the current operation.
	 * @return Return Value The operation queue that started the operation or nil if the queue could not be determined.
	 * @Discussion You can use this method from within a running operation object to get a reference to the operation queue that started it.
	 *             Calling this method from outside the context of a running operation typically results in nil being returned.
	 **/

	public static NSObject currentQueue() {
		NSOperation operation = new NSInvocationOperation();
		if (NSOperationQueue.threadPoolExecutor != null) {
			operation.setThread(Thread.currentThread());
			return operation;
		} else {
			throw new IllegalArgumentException("the queue could not be determined");
		}

	}

	/**
	 * @Signature: mainQueue
	 * @Declaration : + (id)mainQueue
	 * @Description : Returns the operation queue associated with the main thread.
	 * @return Return Value The default operation queue bound to the main thread.
	 * @Discussion The returned queue executes operations on the main thread. The main threadâ€™s run loop controls the execution times of
	 *             these operations.
	 **/

	public static NSObject mainQueue() {
		NSOperation operation = new NSInvocationOperation();
		// FIXME NSOperationQueue.threadPoolExecutor
		return operation;

	}

	class ImplBlock implements NSOperationQueueBlock {

		@Override
		public void perform(Runnable operation) {
			workQueue = new SynchronousQueue<Runnable>();
			workQueue.add(operation);

			threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
			threadPoolExecutor.execute(operation);
		}

	}
}