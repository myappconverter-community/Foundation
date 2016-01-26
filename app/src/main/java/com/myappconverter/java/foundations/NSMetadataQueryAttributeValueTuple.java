
package com.myappconverter.java.foundations;



public class NSMetadataQueryAttributeValueTuple extends NSObject {

	NSString attributename;
	NSObject value;
	int valueNbr;

	// Getting Query Attribute/Value Information

	/**
	 * @Signature: attribute
	 * @Declaration : - (NSString *)attribute
	 * @Description : Returns the receiver’s attribute name.
	 * @return Return Value The receiver’s attribute name.
	 **/
	
	public NSString attribute() {
		return attributename;
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of instances of the value that exist for the attribute name of the receiver.
	 * @return Return Value The number of instantes of the value that exist for the attribute name of the receiver.
	 **/
	
	public int count() {
		return valueNbr;
	}

	/**
	 * @Signature: value
	 * @Declaration : - (id)value
	 * @Description : Returns the receiver’s attribute value.
	 * @return Return Value The receiver’s attribute value.
	 **/
	
	public NSObject value() {
		return value;
	}
}