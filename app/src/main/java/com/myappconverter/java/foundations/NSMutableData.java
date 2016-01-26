
package com.myappconverter.java.foundations;

import java.io.Serializable;
import java.util.Arrays;




public class NSMutableData extends NSData implements  Serializable{

	public NSMutableData() {
		super();
	}

	/***
	 * @param data
	 */
	public NSMutableData(byte[] data) {
		super(data);
	}

	// Creating and Initializing an NSMutableData Object
	/**
	 * @Signature: dataWithCapacity:
	 * @Declaration : + (id)dataWithCapacity:(NSUInteger)aNumItems
	 * @Description : Creates and returns an NSMutableData object capable of holding the specified number of bytes.
	 * @param aNumItems The number of bytes the new data object can initially contain.
	 * @return Return Value A new NSMutableData object capable of holding aNumItems bytes.
	 * @Discussion This method doesn’t necessarily allocate the requested memory right away. Mutable data objects allocate additional memory
	 *             as needed, so aNumItems simply establishes the object’s initial capacity. When it does allocate the initial memory,
	 *             though, it allocates the specified amount. This method sets the length of the data object to 0. If the capacity specified
	 *             in aNumItems is greater than four memory pages in size, this method may round the amount of requested memory up to the
	 *             nearest full page.
	 **/
	
	public static Object dataWithCapacity(long aNumItems) {
		if (aNumItems == 0) {
			return null;
		}
		NSMutableData aData = new NSMutableData();
		aData.setWrappedData(new byte[(int) aNumItems]);
		return aData;
	}

	/**
	 * @Signature: dataWithLength:
	 * @Declaration : + (id)dataWithLength:(NSUInteger)length
	 * @Description : Creates and returns an NSMutableData object containing a given number of zeroed bytes.
	 * @param length The number of bytes the new data object initially contains.
	 * @return Return Value A new NSMutableData object of length bytes, filled with zeros.
	 **/
	
	public static Object dataWithLength(long length) {
		NSMutableData aData = (NSMutableData) dataWithCapacity(length);
		for (int i = 0; i < aData.getWrappedData().length; i++) {
			aData.getWrappedData()[i] = (byte) 0;
		}
		return aData;
	}

	/**
	 * @Signature: initWithCapacity:
	 * @Declaration : - (id)initWithCapacity:(NSUInteger)capacity
	 * @Description : Returns an initialized NSMutableData object capable of holding the specified number of bytes.
	 * @param capacity The number of bytes the data object can initially contain.
	 * @return Return Value An initialized NSMutableData object capable of holding capacity bytes.
	 * @Discussion This method doesn’t necessarily allocate the requested memory right away. Mutable data objects allocate additional memory
	 *             as needed, so aNumItems simply establishes the object’s initial capacity. When it does allocate the initial memory,
	 *             though, it allocates the specified amount. This method sets the length of the data object to 0. If the capacity specified
	 *             in aNumItems is greater than four memory pages in size, this method may round the amount of requested memory up to the
	 *             nearest full page.
	 **/
	
	public Object initWithCapacity(long aNumItems) {
		setWrappedData(new byte[(int) aNumItems]);
		return this;
	}

	/**
	 * @Signature: initWithLength:
	 * @Declaration : - (id)initWithLength:(NSUInteger)length
	 * @Description : Initializes and returns an NSMutableData object containing a given number of zeroed bytes.
	 * @param length The number of bytes the object initially contains.
	 * @return Return Value An initialized NSMutableData object containing length zeroed bytes.
	 **/
	
	public Object initWithLength(long length) {
		initWithCapacity(length);
		for (int i = 0; i < getWrappedData().length; i++) {
			getWrappedData()[i] = 0;
		}
		return this;
	}

	// Adjusting Capacity
	/**
	 * @Signature: increaseLengthBy:
	 * @Declaration : - (void)increaseLengthBy:(NSUInteger)extraLength
	 * @Description : Increases the length of the receiver by a given number of bytes.
	 * @param extraLength The number of bytes by which to increase the receiver's length.
	 * @Discussion The additional bytes are all set to 0.
	 **/
	
	public void increaseLengthBy(long extraLength) {
		setLength((int) (length() + extraLength));
	}

	/**
	 * @Signature: setLength:
	 * @Declaration : - (void)setLength:(NSUInteger)length
	 * @Description : Extends or truncates a mutable data object to a given length.
	 * @param length The new length for the receiver.
	 * @Discussion If the mutable data object is extended, the additional bytes are filled with zeros.
	 **/
	
	public void setLength(int length) {
		if(length < getWrappedData().length) {
			byte[] nData = new byte[length]; // inits to zeroes
			int limit = length > getWrappedData().length ? getWrappedData().length : length;
			System.arraycopy(getWrappedData(), 0, nData, 0, limit);
			setWrappedData(nData);
		}
	}

	public int length() {
		return getWrappedData().length;
	}

	public int getLength() {
		return getWrappedData().length;
	}

	// Accessing Data
	/**
	 * @Signature: mutableBytes
	 * @Declaration : - (void *)mutableBytes
	 * @Description : Returns a pointer to the receiver’s data.
	 * @return Return Value A pointer to the receiver’s data.
	 * @Discussion If the length of the receiver’s data is not zero, this function is guaranteed to return a pointer to the object's
	 *             internal bytes. If the length of receiver’s data is zero, this function may or may not return NULL dependent upon many
	 *             factors related to how the object was created (moreover, in this case the method result might change between different
	 *             releases). A sample using this method can be found in Working With Mutable Binary Data.
	 **/
	
	public byte[] mutableBytes() {
		return bytes();
	}

	public byte[] getMutableBytes() {
		return bytes();
	}
	// Adding Data
	/**
	 * @Signature: appendBytes:length:
	 * @Declaration : - (void)appendBytes:(const void *)bytes length:(NSUInteger)length
	 * @Description : Appends to the receiver a given number of bytes from a given buffer.
	 * @param bytes A buffer containing data to append to the receiver's content.
	 * @param length The number of bytes from bytes to append.
	 * @Discussion A sample using this method can be found in Working With Mutable Binary Data.
	 **/
	
	public void appendBytesLength(byte[] bytes, long length) {
		int origLen = getWrappedData().length;
		setLength((int) (origLen + length));
		System.arraycopy(bytes, 0, getWrappedData(), origLen, (int) length);
	}

	/**
	 * @Signature: appendData:
	 * @Declaration : - (void)appendData:(NSData *)otherData
	 * @Description : Appends the content of another NSData object to the receiver.
	 * @param otherData The data object whose content is to be appended to the contents of the receiver.
	 **/
	
	public void appendData(NSData otherData) {
		appendBytesLength(otherData.getWrappedData(), otherData.getWrappedData().length);
	}

	// Modifying Data
	/**
	 * @Signature: replaceBytesInRange:withBytes:
	 * @Declaration : - (void)replaceBytesInRange:(NSRange)range withBytes:(const void *)bytes
	 * @Description : Replaces with a given set of bytes a given range within the contents of the receiver.
	 * @param range The range within the receiver's contents to replace with bytes. The range must not exceed the bounds of the receiver.
	 * @param bytes The data to insert into the receiver's contents.
	 * @Discussion If the location of range isn’t within the receiver’s range of bytes, an NSRangeException is raised. The receiver is
	 *             resized to accommodate the new bytes, if necessary. A sample using this method is given in Working With Mutable Binary
	 *             Data.
	 **/
	
	public void replaceBytesInRangeWithBytes(NSRange range, byte[] bytes) {
		if (getWrappedData().length >= range.location + range.length) {
			System.arraycopy(bytes, 0, getWrappedData(), range.location, bytes.length);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * @Signature: replaceBytesInRange:withBytes:length:
	 * @Declaration : - (void)replaceBytesInRange:(NSRange)range withBytes:(const void *)replacementBytes
	 *              length:(NSUInteger)replacementLength
	 * @Description : Replaces with a given set of bytes a given range within the contents of the receiver.
	 * @param range The range within the receiver's contents to replace with bytes. The range must not exceed the bounds of the receiver.
	 * @param replacementBytes The data to insert into the receiver's contents.
	 * @param replacementLength The number of bytes to take from replacementBytes.
	 * @Discussion If the length of range is not equal to replacementLength, the receiver is resized to accommodate the new bytes. Any bytes
	 *             past range in the receiver are shifted to accommodate the new bytes. You can therefore pass NULL for replacementBytes and
	 *             0 for replacementLength to delete bytes in the receiver in the range range. You can also replace a range (which might be
	 *             zero-length) with more bytes than the length of the range, which has the effect of insertion (or “replace some and insert
	 *             more).
	 **/
	
	public void replaceBytesInRangeWithBytesLength(NSRange range, byte[] replacementBytes, long replacementLength) {
		if (getWrappedData().length >= range.location + range.length) {
			System.arraycopy(replacementBytes, 0, getWrappedData(), range.location, (int) replacementLength);
		} else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @Signature: resetBytesInRange:
	 * @Declaration : - (void)resetBytesInRange:(NSRange)range
	 * @Description : Replaces with zeroes the contents of the receiver in a given range.
	 * @param range The range within the contents of the receiver to be replaced by zeros. The range must not exceed the bounds of the
	 *            receiver.
	 * @Discussion If the location of range isn’t within the receiver’s range of bytes, an NSRangeException is raised. The receiver is
	 *             resized to accommodate the new bytes, if necessary.
	 **/
	
	public void resetBytesInRange(NSRange range) {
		Arrays.fill(getWrappedData(), range.location, range.length + range.location, (byte) 0);
	}

	/**
	 * - (void)setData:(NSData *)aData Replaces the entire contents of the receiver with the contents of another data object.
	 * 
	 * @param aData
	 * @discussion As part of its implementation, this method calls replaceBytesInRange:withBytes:.
	 */
	
	public void setData(NSData aData) {
		setWrappedData(aData.getWrappedData());
	}

}