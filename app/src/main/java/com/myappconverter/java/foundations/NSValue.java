package com.myappconverter.java.foundations;

import java.io.Serializable;
import java.nio.ByteBuffer;

import android.util.Log;

import com.myappconverter.java.coregraphics.CGPoint;
import com.myappconverter.java.coregraphics.CGRect;
import com.myappconverter.java.coregraphics.CGSize;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.AndroidArrayUtils;



public class NSValue extends NSObject implements NSCoding, NSCopying, NSSecureCoding ,Serializable{

	private Object object;
	private String objectType;

	// Objective-c primitive type
	String objc_type[] = { "char", "int", "short", "long", "long long", "unsigned char", "unsigned int", "unsigned short", "unsigned long",
			"unsigned long long", "float", "double", "void" };

	public NSValue() {
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	private static String objcPrimitiveTypeToJava(String value) {
		Class clz = null;
		if ("c".equals(value) || ("C").equals(value)) {
			clz = char.class;
		} else if ("i".equals(value) || ("I").equals(value)) {
			clz = int.class;
		} else if ("s".equals(value) || ("S").equals(value)) {
			clz = short.class;
		} else if ("byte".equals(value)) {
			clz = byte.class;
		} else if ("l".equals(value) || ("L").equals(value) || ("Q").equals(value) || ("q").equals(value)) {
			clz = long.class;
		} else if ("B".equals(value)) {
			clz = boolean.class;
		} else if ("?".equals(value)) {
			// An unknown type (among other things, this code is used for
			// function pointers)
		} else if ("^type".equals(value)) {
			// A pointer to type
			// exp [12^f] array of 12 float
		} else if ("f".equals(value)) {
			clz = float.class;
		} else if ("d".equals(value)) {
			clz = double.class;
		} else if ("v".equals(value)) {
			clz = void.class;
		} else if ("@".equals(value)) {
			clz = Object.class;
		} else if ("#".equals(value)) {
			clz = Class.class;
		}
		if (clz != null) {
			Log.d("@encode : ", clz.getSimpleName());
			return clz.getSimpleName();
		} else
			throw new UnknownError("unkonw type " + value);

	}

	// Creating an NSValue

	public NSValue(Object value, String type) {
		object = value;
		objectType = objcPrimitiveTypeToJava(type);
	}

	/**
	 * @Signature: initWithBytes:objCType:
	 * @Declaration : - (id)initWithBytes:(const void *)value objCType:(const char *)type
	 * @Description : Initializes and returns an NSValue object that contains a given value, which is interpreted as being of a given
	 *              Objective-C type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value An initialized NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 *         The returned object might be different than the original receiver.
	 * @Discussion See Number and Value Programming Topics for other considerations in creating an NSValue object. This is the designated
	 *             initializer for the NSValue class.
	 **/

	public Object initWithBytesobjCType(Object[] value, String type) {
		object = value;
		objectType = objcPrimitiveTypeToJava(type);
		return object;
	}

	public Object initWithBytes(byte[] value, String type) {
		object = AndroidArrayUtils.toObject(value);
		objectType = objcPrimitiveTypeToJava(type);
		return object;
	}

	/**
	 * @Signature: initWithBytes:objCType:
	 * @Declaration : - (id)initWithBytes:(const void *)value objCType:(const char *)type
	 * @Description : Initializes and returns an NSValue object that contains a given value, which is interpreted as being of a given
	 *              Objective-C type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value An initialized NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 *         The returned object might be different than the original receiver.
	 * @Discussion See Number and Value Programming Topics for other considerations in creating an NSValue object. This is the designated
	 *             initializer for the NSValue class.
	 **/

	public Object initWithBytesobjCType(Object[] value, char[] type) {
		object = value;
		objectType = objcPrimitiveTypeToJava(String.copyValueOf(type));
		return object;
	}

	/**
	 * @Signature: valueWithBytes:objCType:
	 * @Declaration : + (NSValue *)valueWithBytes:(const void *)value objCType:(const char *)type
	 * @Description : Creates and returns an NSValue object that contains a given value, which is interpreted as being of a given
	 *              Objective-C type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value A new NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 * @Discussion See Number and Value Programming Topics for other considerations in creating an NSValue object and code examples.
	 **/

	public static NSValue valueWithBytesobjCType(Object value, String type) {
		NSValue nsValue = new NSValue();
		nsValue.object = value;
		nsValue.objectType = objcPrimitiveTypeToJava(type);
		return nsValue;
	}

	/**
	 * @Signature: valueWithBytes:objCType:
	 * @Declaration : + (NSValue *)valueWithBytes:(const void *)value objCType:(const char *)type
	 * @Description : Creates and returns an NSValue object that contains a given value, which is interpreted as being of a given
	 *              Objective-C type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value A new NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 * @Discussion See Number and Value Programming Topics for other considerations in creating an NSValue object and code examples.
	 **/

	public static NSValue valueWithBytesobjCType(Object value, char[] type) {
		NSValue nsValue = new NSValue();
		nsValue.object = value;
		nsValue.objectType = objcPrimitiveTypeToJava(String.copyValueOf(type));
		return nsValue;
	}

	/**
	 * @Signature: value:withObjCType:
	 * @Declaration : + (NSValue *)value:(const void *)value withObjCType:(const char *)type
	 * @Description : Creates and returns an NSValue object that contains a given value which is interpreted as being of a given Objective-C
	 *              type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value A new NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 * @Discussion This method has the same effect as valueWithBytes:objCType: and may be deprecated in a future release. You should use
	 *             valueWithBytes:objCType: instead.
	 **/

	public static NSValue valuewithObjCType(Object value, String type) {
		NSValue nsVal = new NSValue();
		nsVal.object = value;
		nsVal.objectType = objcPrimitiveTypeToJava(type);
		return nsVal;
	}

	/**
	 * @Signature: value:withObjCType:
	 * @Declaration : + (NSValue *)value:(const void *)value withObjCType:(const char *)type
	 * @Description : Creates and returns an NSValue object that contains a given value which is interpreted as being of a given Objective-C
	 *              type.
	 * @param value The value for the new NSValue object.
	 * @param type The Objective-C type of value. type should be created with the Objective-C @encode() compiler directive; it should not be
	 *            hard-coded as a C string.
	 * @return Return Value A new NSValue object that contains value, which is interpreted as being of the Objective-C type type.
	 * @Discussion This method has the same effect as valueWithBytes:objCType: and may be deprecated in a future release. You should use
	 *             valueWithBytes:objCType: instead.
	 **/

	public static NSValue valuewithObjCType(Object value, char[] type) {
		NSValue nsVal = new NSValue();
		nsVal.object = value;
		nsVal.objectType = objcPrimitiveTypeToJava(String.valueOf(type));
		return nsVal;
	}

	/**
	 * @Signature: valueWithNonretainedObject:
	 * @Declaration : + (NSValue *)valueWithNonretainedObject:(id)anObject
	 * @Description : Creates and returns an NSValue object that contains a given object.
	 * @param anObject The value for the new object.
	 * @return Return Value A new NSValue object that contains anObject.
	 * @Discussion This method is equivalent to invoking value:withObjCType: in this manner: NSValue *theValue = [NSValue value:&anObject
	 *             withObjCType:]; This method is useful if you want to add an object to a collection but don’t want the
	 *             collection to create a strong reference to it.
	 **/

	public static NSValue valueWithNonretainedObject(Object anObject) {
		NSValue nsVal = new NSValue();
		nsVal.object = anObject;
		nsVal.objectType = anObject.getClass().getSimpleName();
		return nsVal;
	}

	/**
	 * @Signature: valueWithPointer:
	 * @Declaration : + (NSValue *)valueWithPointer:(const void *)aPointer
	 * @Description : Creates and returns an NSValue object that contains a given pointer.
	 * @param aPointer The value for the new object.
	 * @return Return Value A new NSValue object that contains aPointer.
	 * @Discussion This method is equivalent to invoking value:withObjCType: in this manner: NSValue *theValue = [NSValue value:&aPointer
	 *             withObjCType:]; This method does not copy the contents of aPointer, so you must not to free the memory at
	 *             the pointer destination while the NSValue object exists. NSData objects may be more suited for arbitrary pointers than
	 *             NSValue objects.
	 **/

	public static NSValue valueWithPointer(Object pointer) {
		NSValue nsVal = new NSValue();
		nsVal.object = pointer;
		nsVal.objectType = pointer.getClass().getSimpleName();
		return nsVal;
	}

	/**
	 * @Signature: valueWithRange:
	 * @Declaration : + (NSValue *)valueWithRange:(NSRange)range
	 * @Description : Creates and returns an NSValue object that contains a given NSRange structure.
	 * @param range The value for the new object.
	 * @return Return Value A new NSValue object that contains the value of range.
	 **/

	public static NSValue valueWithRange(NSRange range) {
		NSValue nsValue = new NSValue();
		nsValue.object = range;
		nsValue.objectType = range.getClass().getSimpleName();
		return nsValue;
	}


	public static NSValue valueWithCGPoint(CGPoint point) {
		NSValue nsValue = new NSValue();
		nsValue.object = point;
		nsValue.objectType = point.getClass().getSimpleName();
		return nsValue;
	}

	/**
	 * @Signature: getValue:
	 * @Declaration : - (void)getValue:(void *)buffer
	 * @Description : Copies the receiver’s value into a given buffer.
	 * @param buffer A buffer into which to copy the receiver's value. buffer must be large enough to hold the value.
	 **/

	public void getValue(Object buffer) {
		buffer = object;
	}

	/**
	 * @Signature: nonretainedObjectValue
	 * @Declaration : - (id)nonretainedObjectValue
	 * @Description : Returns the receiver's value as an id.
	 * @return Return Value The receiver's value as an id. If the receiver was not created to hold a pointer-sized data item, the result is
	 *         undefined.
	 **/

	public Object nonretainedObjectValue() {
		return object;
	}

	/**
	 * @Signature: objCType
	 * @Declaration : - (const char *)objCType
	 * @Description : Returns a C string containing the Objective-C type of the data contained in the receiver.
	 * @return Return Value A C string containing the Objective-C type of the data contained in the receiver, as encoded by the @encode()
	 *         compiler directive.
	 **/

	public String objCType() {
		return objectType;
	}

	/**
	 * @Signature: pointerValue
	 * @Declaration : - (void *)pointerValue
	 * @Description : Returns the receiver's value as a pointer to void.
	 * @return Return Value The receiver's value as a pointer to void. If the receiver was not created to hold a pointer-sized data item,
	 *         the result is undefined.
	 **/

	public byte[] pointerValue() {
		ByteBuffer buffer = ByteBuffer.allocate(2045);
		buffer.put((Byte) object);
		return buffer.array();
	}

	/**
	 * @Signature: rangeValue
	 * @Declaration : - (NSRange)rangeValue
	 * @Description : Returns an NSRange structure representation of the receiver.
	 * @return Return Value An NSRange structure representation of the receiver.
	 **/

	public NSRange rangeValue() {
		if (objectType.equals(NSRange.class.getSimpleName())) {
			NSRange nsRange = (NSRange) object;
			return nsRange;
		}
		return null;
	}

	// Comparing Objects

	/**
	 * @Signature: isEqualToValue:
	 * @Declaration : - (BOOL)isEqualToValue:(NSValue *)value
	 * @Description : Returns a Boolean value that indicates whether the receiver and another value are equal.
	 * @param aValue The value with which to compare the receiver.
	 * @return Return Value YES if the receiver and aValue are equal, otherwise NO. For NSValue objects, the class, type, and contents are
	 *         compared to determine equality.
	 **/

	public boolean isEqualToValue(NSValue value) {
		if (this == value)
			return true;
		if (!super.equals(value))
			return false;
		if (getClass() != value.getClass())
			return false;
		NSValue other = value;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		return true;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {

	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {

		return null;
	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((objectType == null) ? 0 : objectType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NSValue other = (NSValue) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		return true;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {
		return null;
	}

	// ////////////* NSValue Core Animation Additions*//////////////



	/*
	 * NSValue UIKit Additions Reference
	 */

	/**
	 * @Signature: valueWithCGRect:
	 * @Declaration : + (NSValue *)valueWithCGRect:(CGRect)rect
	 * @Description : Creates and returns a value object that contains the specified rectangle structure.
	 * @param rect The value for the new object.
	 * @return Return Value A new value object that contains the rectangle information.
	 **/

	public static NSValue valueWithCGRect(CGRect rect) {
		NSValue nsValue = new NSValue();
		nsValue.object = rect;
		nsValue.objectType = rect.getClass().getSimpleName();
		return nsValue;
	}


	public static NSValue valueWithCGSize(CGSize size) {
		NSValue nsValue = new NSValue();
		nsValue.object = size;
		nsValue.objectType = size.getClass().getSimpleName();
		return nsValue;
	}

	public CGPoint CGPointValue() {
		return new CGPoint();
	}

}