package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSArray;
import com.myappconverter.java.foundations.NSKeyedUnarchiver;
import com.myappconverter.java.foundations.NSString;

public interface NSKeyedUnarchiverDelegate {

	// 1
	/**
	 * @Signature: unarchiver:cannotDecodeObjectOfClassName:originalClasses:
	 * @Declaration : - (Class)unarchiver:(NSKeyedUnarchiver *)unarchiver cannotDecodeObjectOfClassName:(NSString *)name
	 *              originalClasses:(NSArray *)classNames
	 * @Description : Informs the delegate that the class with a given name is not available during decoding.
	 * @return Return Value The class unarchiver should use in place of the class named name.
	 * @Discussion The delegate may, for example, load some code to introduce the class to the runtime and return the class, or substitute a
	 *             different class object. If the delegate returns nil, unarchiving aborts and the method raises an
	 *             NSInvalidUnarchiveOperationException.
	 **/

	public Class unarchiverCannotDecodeObjectOfClassNameOriginalClasses(NSKeyedUnarchiver unarchiver, NSString name, NSArray classNames);

	// 2
	/**
	 * @Signature: unarchiver:didDecodeObject:
	 * @Declaration : - (id)unarchiver:(NSKeyedUnarchiver *)unarchiver didDecodeObject:(id)object
	 * @Description : Informs the delegate that a given object has been decoded.
	 * @return Return Value The object to use in place of object. The delegate can either return object or return a different object to
	 *         replace the decoded one. If the delegate returns nil, the decoded value will be unchanged (that is, the original object will
	 *         be decoded).
	 * @Discussion This method is called after object has been sent initWithCoder: and awakeAfterUsingCoder:. The delegate may use this
	 *             method to keep track of the decoded objects.
	 **/

	public Object unarchiverDidDecodeObject(NSKeyedUnarchiver unarchiver, Object object);

	// 3
	/**
	 * @Signature: unarchiver:willReplaceObject:withObject:
	 * @Declaration : - (void)unarchiver:(NSKeyedUnarchiver *)unarchiver willReplaceObject:(id)object withObject:(id)newObject
	 * @Description : Informs the delegate that one object is being substituted for another.
	 * @Discussion This method is called even when the delegate itself is doing, or has done, the substitution with
	 *             unarchiver:didDecodeObject:. The delegate may use this method if it is keeping track of the encoded or decoded objects.
	 **/

	public void unarchiverWillReplaceObjectWithObject(NSKeyedUnarchiver unarchiver, Object object, Object newObject);

	// 4
	/**
	 * @Signature: unarchiverDidFinish:
	 * @Declaration : - (void)unarchiverDidFinish:(NSKeyedUnarchiver *)unarchiver
	 * @Description : Notifies the delegate that decoding has finished.
	 **/

	public void unarchiverDidFinish(NSKeyedUnarchiver unarchiver);

	// 5
	/**
	 * @Signature: unarchiverWillFinish:
	 * @Declaration : - (void)unarchiverWillFinish:(NSKeyedUnarchiver *)unarchiver
	 * @Description : Notifies the delegate that decoding is about to finish.
	 **/

	public void unarchiverWillFinish(NSKeyedUnarchiver unarchiver);
}
