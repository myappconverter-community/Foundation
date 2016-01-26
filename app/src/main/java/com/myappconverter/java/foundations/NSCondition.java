
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.protocols.NSLocking;

import android.util.Log;

import java.util.concurrent.locks.Condition;



public class NSCondition extends NSObject implements NSLocking {

	Condition condition;
	NSString name = null;

	// Waiting for the Lock
	/**
	 * @Signature: wait
	 * @Declaration : - (void)wait
	 * @Description : Blocks the current thread until the condition is signaled.
	 * @Discussion You must lock the receiver prior to calling this method.
	 **/
	
	public void _wait() {
		try {
			this.condition.await();
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
	}

	/**
	 * @Signature: waitUntilDate:
	 * @Declaration : - (BOOL)waitUntilDate:(NSDate *)limit
	 * @Description : Blocks the current thread until the condition is signaled or the specified time limit is reached.
	 * @param limit The time at which to wake up the thread if the condition has not been signaled.
	 * @return Return Value YES if the condition was signaled; otherwise, NO if the time limit was reached.
	 * @Discussion You must lock the receiver prior to calling this method.
	 **/
	
	public boolean waitUntilDate(NSDate limit) {
		try {
			return this.condition.awaitUntil(limit.getWrappedDate());
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;
	}

	// Signaling Waiting Threads

	/**
	 * @Signature: signal
	 * @Declaration : - (void)signal
	 * @Description : Signals the condition, waking up one thread waiting on it.
	 * @Discussion You use this method to wake up one thread that is waiting on the condition. You may call this method multiple times to
	 *             wake up multiple threads. If no threads are waiting on the condition, this method does nothing. To avoid race conditions,
	 *             you should invoke this method only while the receiver is locked.
	 **/
	
	public void signal() {
		this.condition.signal();
	}

	/**
	 * @Signature: broadcast
	 * @Declaration : - (void)broadcast
	 * @Description : Signals the condition, waking up all threads waiting on it.
	 * @Discussion If no threads are waiting on the condition, this method does nothing. To avoid race conditions, you should invoke this
	 *             method only while the receiver is locked.
	 **/
	
	public void broadcast() {
		condition.signalAll();
	}

	// Accessor Methods

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name associated with the receiver.
	 * @return Return Value The name of the receiver.
	 **/
	
	public NSString name() {
		return name;

	}

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)newName
	 * @Description : Assigns a name to the receiver.
	 * @param newName The new name for the receiver. This method makes a copy of the specified string.
	 * @Discussion You can use a name string to identify a condition object within your code. Cocoa also uses this name as part of any error
	 *             descriptions involving the receiver.
	 **/
	
	public void setName(NSString newName) {
		this.name = newName;
	}

	
	@Override
	public void lock() {
		throw new UnsupportedOperationException();
	}

	
	@Override
	public void unlock() {
		throw new UnsupportedOperationException();
	}

}