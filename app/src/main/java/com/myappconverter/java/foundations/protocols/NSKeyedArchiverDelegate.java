package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSKeyedArchiver;

public interface NSKeyedArchiverDelegate {

	// 1
	/**
	 * @Signature: archiver:didEncodeObject:
	 * @Declaration : - (void)archiver:(NSKeyedArchiver *)archiver didEncodeObject:(id)object
	 * @Description : Informs the delegate that a given object has been encoded.
	 * @Discussion The delegate might restore some state it had modified previously, or use this opportunity to keep track of the objects
	 *             that are encoded. This method is not called for conditional objects until they are actually encoded (if ever).
	 **/

	public void archiverDidEncodeObject(NSKeyedArchiver archiver, Object object);

	// 2
	/**
	 * @Signature: archiver:willEncodeObject:
	 * @Declaration : - (id)archiver:(NSKeyedArchiver *)archiver willEncodeObject:(id)object
	 * @Description : Informs the delegate that object is about to be encoded.
	 * @return Return Value Either object or a different object to be encoded in its stead. The delegate can also modify the coder state. If
	 *         the delegate returns nil, nil is encoded.
	 * @Discussion This method is called after the original object may have replaced itself with replacementObjectForKeyedArchiver::. This
	 *             method is called whether or not the object is being encoded conditionally. This method is not called for an object once a
	 *             replacement mapping has been set up for that object (either explicitly, or because the object has previously been
	 *             encoded). This method is also not called when nil is about to be encoded.
	 **/

	public Object archiverWillEncodeObject(NSKeyedArchiver archiver, Object object);

	// 3
	/**
	 * @Signature: archiver:willReplaceObject:withObject:
	 * @Declaration : - (void)archiver:(NSKeyedArchiver *)archiver willReplaceObject:(id)object withObject:(id)newObject
	 * @Description : Informs the delegate that one given object is being substituted for another given object.
	 * @Discussion This method is called even when the delegate itself is doing, or has done, the substitution. The delegate may use this
	 *             method if it is keeping track of the encoded or decoded objects.
	 **/

	public void archiverWillReplaceObjectWithObject(NSKeyedArchiver archiver, Object object, Object newObject);

	// 4
	/**
	 * @Signature: archiverDidFinish:
	 * @Declaration : - (void)archiverDidFinish:(NSKeyedArchiver *)archiver
	 * @Description : Notifies the delegate that encoding has finished.
	 **/

	public void archiverDidFinish(NSKeyedArchiver archiver);

	// 5
	/**
	 * @Signature: archiverWillFinish:
	 * @Declaration : - (void)archiverWillFinish:(NSKeyedArchiver *)archiver
	 * @Description : Notifies the delegate that encoding is about to finish.
	 **/

	public void archiverWillFinish(NSKeyedArchiver archiver);

}
