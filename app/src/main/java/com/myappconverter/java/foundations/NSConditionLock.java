
package com.myappconverter.java.foundations;

import android.util.Log;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;




public class NSConditionLock extends NSObject {
	int condition;
	Lock lock = new ReentrantLock();
	NSString name = null;

	public NSConditionLock() {
		super();
	}

	// Initializing an NSConditionLock Object

	/**
	 * @Signature: initWithCondition:
	 * @Declaration : - (id)initWithCondition:(NSInteger)condition
	 * @Description : Initializes a newly allocated NSConditionLock object and sets its condition.
	 * @param condition The user-defined condition for the lock. The value of condition is user-defined; see the class description for more
	 *            information.
	 * @return Return Value An initialized condition lock object; may be different than the original receiver.
	 **/
	
	public NSConditionLock initWithCondition(int condition) {
		this.condition = condition;
		return this;
	}

	// Returning the Condition
	/**
	 * @Signature: condition
	 * @Declaration : - (NSInteger)condition
	 * @Description : Returns the condition associated with the receiver.
	 * @return Return Value The condition associated with the receiver. If no condition has been set, returns 0.
	 **/
	
	public int condition() {
		return this.condition;
	}

	// Acquiring and Releasing a Lock

	/**
	 * @Signature: lockBeforeDate:
	 * @Declaration : - (BOOL)lockBeforeDate:(NSDate *)limit
	 * @Description : Attempts to acquire a lock before a specified moment in time.
	 * @param limit The date by which the lock must be acquired or the attempt will time out.
	 * @return Return Value YES if the lock is acquired within the time limit, NO otherwise.
	 * @Discussion The condition associated with the receiver isn’t taken into account in this operation. This method blocks the thread’s
	 *             execution until the receiver acquires the lock or limit is reached.
	 **/
	
	public boolean lockBeforeDate(NSDate limit) {
		try {
			long limitTime = limit.getWrappedDate().getTime();
			return lock.tryLock(limitTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}
		return false;
	}

	/**
	 * @Signature: lockWhenCondition:
	 * @Declaration : - (void)lockWhenCondition:(NSInteger)condition
	 * @Description : Attempts to acquire a lock.
	 * @param condition The condition to match on.
	 * @Discussion The receiver’s condition must be equal to condition before the locking operation will succeed. This method blocks the
	 *             thread’s execution until the lock can be acquired.
	 **/
	
	public void lockWhenCondition(int condition) {
		if (this.condition == condition) {
			lock.lock();
		}
	}

	/**
	 * @Signature: lockWhenCondition:
	 * @Declaration : - (void)lockWhenCondition:(NSInteger)condition
	 * @Description : Attempts to acquire a lock.
	 * @param condition The condition to match on.
	 * @Discussion The receiver’s condition must be equal to condition before the locking operation will succeed. This method blocks the
	 *             thread’s execution until the lock can be acquired.
	 **/
	
	public boolean lockWhenConditionBeforeDate(int condition, NSDate limit) throws InterruptedException {
		if (this.condition == condition) {
			return lock.tryLock(limit.getWrappedDate().getTime(), TimeUnit.MILLISECONDS);
		}
		return false;
	}

	/**
	 * @Signature: tryLock
	 * @Declaration : - (BOOL)tryLock
	 * @Description : Attempts to acquire a lock without regard to the receiver’s condition.
	 * @return Return Value YES if the lock could be acquired, NO otherwise.
	 * @Discussion This method returns immediately.
	 **/
	
	public boolean tryLock() {
		return lock.tryLock();

	}

	/**
	 * @Signature: tryLockWhenCondition:
	 * @Declaration : - (BOOL)tryLockWhenCondition:(NSInteger)condition
	 * @Description : Attempts to acquire a lock if the receiver’s condition is equal to the specified condition.
	 * @return Return Value YES if the lock could be acquired, NO otherwise.
	 * @Discussion As part of its implementation, this method invokes lockWhenCondition:beforeDate:. This method returns immediately.
	 **/
	
	public boolean tryLockWhenCondition(int condition) {
		if (this.condition == condition) {
			return lock.tryLock();
		}
		return false;
	}

	/**
	 * @Signature: unlockWithCondition:
	 * @Declaration : - (void)unlockWithCondition:(NSInteger)condition
	 * @Description : Relinquishes the lock and sets the receiver’s condition.
	 * @param condition The user-defined condition for the lock. The value of condition is user-defined; see the class description for more
	 *            information.
	 **/
	
	public void unlockWithCondition(int condition) {
		if (condition == this.condition) {
			lock.unlock();
		}
	}

	// Accessor Methods

	/**
	 * @Signature: setName:
	 * @Declaration : - (void)setName:(NSString *)newName
	 * @Description : Assigns a name to the receiver.
	 * @param newName The new name for the receiver. This method makes a copy of the specified string.
	 * @Discussion You can use a name string to identify a condition lock within your code. Cocoa also uses this name as part of any error
	 *             descriptions involving the receiver.
	 **/
	
	public void setName(NSString newName) {
		this.name = newName;
	}

	/**
	 * @Signature: name
	 * @Declaration : - (NSString *)name
	 * @Description : Returns the name associated with the receiver.
	 * @return Return Value The name of the receiver.
	 **/
	
	public NSString name() {
		return name;

	}

}