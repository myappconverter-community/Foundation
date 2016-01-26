package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSZone;

/**
 * @Overview The NSMutableCopying protocol declares a method for providing mutable copies of an object. Only classes that define an
 *           “immutable vs. mutable” distinction should adopt this protocol. Classes that don’t define such a distinction should adopt
 *           NSCopying instead. NSMutableCopying declares one method, mutableCopyWithZone:, but mutable copying is commonly invoked with the
 *           convenience method mutableCopy. The mutableCopy method is defined for all NSObjects and simply invokes mutableCopyWithZone:
 *           with the default zone. If a subclass inherits NSMutableCopying from its superclass and declares additional instance variables,
 *           the subclass has to override mutableCopyWithZone: to properly handle its own instance variables, invoking the superclass’s
 *           implementation first.
 */

public interface NSMutableCopying {

	// 1
	/**
	 * @Signature: mutableCopyWithZone:
	 * @Declaration : - (id)mutableCopyWithZone:(NSZone *)zone
	 * @Description : Returns a new instance that’s a mutable copy of the receiver. (required)
	 * @Discussion The returned object is implicitly retained by the sender, which is responsible for releasing it. The copy returned is
	 *             mutable whether the original is mutable or not.
	 **/
	public Object mutableCopyWithZone(NSZone zone);
}
