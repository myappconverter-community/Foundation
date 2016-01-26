
package com.myappconverter.java.foundations;

import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.myappconverter.java.foundations.protocols.NSLocking;



public class NSRecursiveLock extends NSObject implements NSLocking {

	// Acquiring a Lock

	NSLock nsLock;

	public NSRecursiveLock() {
		nsLock = new NSLock();
	}

	/**
	 * @Signature: lockBeforeDate:
	 * @Declaration : - (BOOL)lockBeforeDate:(NSDate *)limit
	 * @Description : Attempts to acquire a lock before a given date.
	 * @param limit The time before which the lock should be acquired.
	 * @return Return Value YES if the lock is acquired before limit, otherwise NO.
	 * @Discussion The thread is blocked until the receiver acquires the lock or limit is reached.
	 **/

	public boolean lockBeforeDate(NSDate limit) {
		try {
			return nsLock.getWrappedLock().tryLock(limit.getWrappedDate().getTime(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;

	}

	/**
	 * @Signature: tryLock
	 * @Declaration : - (BOOL)tryLock
	 * @Description : Attempts to acquire a lock, and immediately returns a Boolean value that indicates whether the attempt was successful.
	 * @return Return Value YES if successful, otherwise NO.
	 **/

	public boolean tryLock() {
		return nsLock.getWrappedLock().tryLock();

	}

	// Naming the Lock

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)newName
	 * @Description : Assigns a name to the receiver
	 * @param newName The new name for the receiver. This method makes a copy of the specified string.
	 * @Discussion You can use a name string to identify a lock within your code. Cocoa also uses this name as part of any error
	 *             descriptions involving the receiver.
	 **/

	public void setName(NSString newName) {
		nsLock.nameLock = new NSString(newName.getWrappedString());
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name associated with the receiver.
	 * @return Return Value The name of the receiver.
	 **/

	public NSString name() {
		return nsLock.nameLock;

	}

	public void lock() {
		nsLock.getWrappedLock().lock();
	}

	public synchronized void unlock() {
		nsLock.getWrappedLock().unlock();
	}

}