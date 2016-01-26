
package com.myappconverter.java.foundations;



public class NSAutoreleasePool extends NSObject {

	// This class has no sense in JAVA: this is mostly used for reference counting in iOS which is not available on
	// java.

	/**
	 * addObject: Adds a given object to the active autorelease pool in the current thread. + (void)addObject:(id)object Parameters object
	 * The object to add to the active autorelease pool in the current thread. Discussion The same object may be added several times to the
	 * active pool and, when the pool is deallocated, it will receive a release message for each time it was added. Normally you don’t
	 * invoke this method directly—you send autorelease to object instead.
	 */
	
	public static void addObjectStatic(Object object) {
		// Does nothing : it's better if the parser ignore this coe.
	}

	/**
	 * showPools Displays the state of the current thread's autorelease pool stack to stderr.<br>
	 * + (void)showPools Special Considerations Warning: Unsupported API This method is part of a module that contains material that is only
	 * partially supported—if at all. Do not depend on the existence of this symbol in your code in future releases of this software.
	 * Certainly, do not depend on this symbol in production code. The format of any data produced by this method may also change at any
	 * time.
	 */
	
	public static void showPools() {
		// Does nothing : it's better if the parser ignore this coe.
	}

	/**
	 * addObject: Adds a given object to the receiver<br>
	 * - (void)addObject:(id)object Parameters object The object to add to the receiver. Discussion The same object may be added several
	 * times to the same pool; when the pool is deallocated, the object will receive a release message for each time it was added. Normally
	 * you don’t invoke this method directly—you send autorelease to object instead.
	 */
	
	public void addObject(Object object) {
		// Does nothing : it's better if the parser ignore this coe.
	}

	/**
	 * autorelease Raises an exception. - (id)autorelease Return Value self. Discussion In a reference-counted environment, this method
	 * raises an exception.
	 */
	@Override
	
	public NSObject autorelease() {
		// Does nothing : it's better if the parser ignore this coe.
		return this;
	}

	/**
	 * drain In a reference-counted environment, releases and pops the receiver; in a garbage-collected environment, triggers garbage
	 * collection if the memory allocated since the last collection is greater than the current threshold.<br>
	 * - (void)drain
	 * 
	 * @Discussion: In a reference-counted environment, this method behaves the same as release. Since an autorelease pool cannot be
	 *              retained (see retain), this therefore causes the receiver to be deallocated. When an autorelease pool is deallocated, it
	 *              sends a release message to all its autoreleased objects. If an object is added several times to the same pool, when the
	 *              pool is deallocated it receives a release message for each time it was added. Special Considerations In a
	 *              garbage-collected environment, release is a no-op, so unless you do not want to give the collector a hint it is
	 *              important to use drain in any code that may be compiled for a garbage-collected environment.
	 */
	
	public void drain() {
		// Does nothing : it's better if the parser ignore this coe.
	}

	/**
	 * release Releases and pops the receiver. - (void)release
	 * 
	 * @Discussion In a reference-counted environment, since an autorelease pool cannot be retained (see retain), this method causes the
	 *             receiver to be deallocated. When an autorelease pool is deallocated, it sends a release message to all its autoreleased
	 *             objects. If an object is added several times to the same pool, when the pool is deallocated it receives a release message
	 *             for each time it was added. In a garbage-collected environment, this method is a no-op. special Considerations You should
	 *             typically use drain instead of release. See Also – drain retain Raises an exception.
	 */
	@Override
	
	public void release() {
		// Does nothing : it's better if the parser ignore this coe.
	}

	/**
	 * - (id)retain
	 * 
	 * @Return Value self.
	 * @Discussion In a reference-counted environment, this method raises an exception.
	 */
	@Override
	
	public NSObject retain() {
		// Does nothing : it's better if the parser ignore this coe.
		return this;
	}
}