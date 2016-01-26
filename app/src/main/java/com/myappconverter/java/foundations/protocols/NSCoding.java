package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSCoder;

import java.io.Serializable;

/**
 * @Overview The NSCoding protocol declares the two methods that a class must implement so that instances of that class can be encoded and
 *           decoded. This capability provides the basis for archiving (where objects and other structures are stored on disk) and
 *           distribution (where objects are copied to different address spaces).
 */

public interface NSCoding extends Serializable{

	// 1
	/**
	 * @Signature: encodeWithCoder:
	 * @Declaration : - (void)encodeWithCoder:(NSCoder *)encoder
	 * @Description : Encodes the receiver using a given archiver. (required)
	 **/
	public void encodeWithCoder(NSCoder encoder);

	// 2
	/**
	 * @Signature: initWithCoder:
	 * @Declaration : - (id)initWithCoder:(NSCoder *)decoder
	 * @Description : Returns an object initialized from data in a given unarchiver. (required)
	 * @return Return Value self, initialized using the data in decoder.
	 * @Discussion You must return self from initWithCoder:. If you have an advanced need that requires substituting a different object
	 *             after decoding, you can do so in awakeAfterUsingCoder:.
	 **/
	public Object initWithCoder(NSCoder decoder);
}
