package com.myappconverter.java.foundations.protocols;


public interface NSLocking {

	/**
	 * @Signature: lock
	 * @Declaration : - (void)lock
	 * @Description : Attempts to acquire a lock, blocking a thread’s execution until the lock can be acquired. (required)
	 * @Discussion An application protects a critical section of code by requiring a thread to acquire a lock before executing the code.
	 *             Once the critical section is completed, the thread relinquishes the lock by invoking unlock.
	 **/
	public void lock();

	/**
	 * @Signature: unlock
	 * @Declaration : - (void)unlock
	 * @Description : Relinquishes a previously acquired lock. (required)
	 **/
	public void unlock();

}
