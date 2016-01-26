
package com.myappconverter.java.foundations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.myappconverter.java.foundations.protocols.NSLocking;


public class NSLock extends NSObject implements NSLocking {

	protected Lock wrappedLock;
	protected NSString nameLock;

	public NSLock() {
		wrappedLock = new ReentrantLock();
	}

	public Lock getWrappedLock() {
		return wrappedLock;
	}

	public void setWrappedLock(Lock wrappedLock) {
		this.wrappedLock = wrappedLock;
	}

	/**
	 * @Signature: lockBeforeDate:
	 * @Declaration : - (BOOL)lockBeforeDate:(NSDate *)limit
	 * @Description : Attempts to acquire a lock before a given time and returns a Boolean value indicating whether the attempt was
	 *              successful.
	 * @param limit The time limit for attempting to acquire a lock.
	 * @return Return Value YES if the lock is acquired before limit, otherwise NO.
	 * @Discussion The thread is blocked until the receiver acquires the lock or limit is reached.
	 **/
	
	public boolean lockBeforeDate(NSDate limit) throws InterruptedException {
		long lng = limit.getWrappedDate().getTime();
		return wrappedLock.tryLock(lng, TimeUnit.MILLISECONDS);
	}

	/**
	 * @Signature: tryLock
	 * @Declaration : - (BOOL)tryLock
	 * @Description : Attempts to acquire a lock and immediately returns a Boolean value that indicates whether the attempt was successful.
	 * @return Return Value YES if the lock was acquired, otherwise NO.
	 **/
	
	public boolean tryLock() {
		return wrappedLock.tryLock();

	}

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)newName
	 * @Description : Assigns a name to the receiver.
	 * @param newName The new name for the receiver. This method makes a copy of the specified string.
	 * @Discussion You can use a name string to identify a lock within your code. Cocoa also uses this name as part of any error
	 *             descriptions involving the receiver.
	 **/
	
	public void setName(NSString newName) {
		this.nameLock = newName;
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name associated with the receiver.
	 * @return Return Value The name of the receiver.
	 **/
	
	public NSString name() {
		return this.nameLock;

	}

	@Override
	
	public void lock() {
		wrappedLock.lock();

	}

	@Override
	
	public void unlock() {
		wrappedLock.unlock();

	}

}