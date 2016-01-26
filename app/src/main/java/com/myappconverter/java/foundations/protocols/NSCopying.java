package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSZone;


public interface NSCopying {

	/**
	 * Returns a new instance that's a copy of the receiver. (required) - (id)copyWithZone:(NSZone *)zone
	 * 
	 * @param zone The zone identifies an area of memory from which to allocate for the new instance. If zone is NULL, the new instance is
	 *            allocated from the default zone, which is returned from the function NSDefaultMallocZone.
	 * @Discussion The returned object is implicitly retained by the sender, who is responsible for releasing it. The copy returned is
	 *             immutable if the consideration 'immutable vs. mutable' applies to the receiving object; otherwise the exact nature of the
	 *             copy is determined by the class.
	 */
	public Object copyWithZone(NSZone zone);
}
