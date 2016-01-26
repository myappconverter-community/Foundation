package com.myappconverter.java.foundations.protocols;

import com.myappconverter.java.foundations.NSMetadataItem;
import com.myappconverter.java.foundations.NSMetadataQuery;
import com.myappconverter.java.foundations.NSString;

public interface NSMetadataQueryDelegate {

	// 1
	/**
	 * @Signature: metadataQuery:replacementObjectForResultObject:
	 * @Declaration : - (id)metadataQuery:(NSMetadataQuery *)query replacementObjectForResultObject:(NSMetadataItem *)result
	 * @Description : Implemented by the delegate to return a different object for a specific query result object.
	 * @return Return Value Object that replaces the query result object.
	 * @Discussion By default query result objects are instances of the NSMetadataItem class. By implementing this method, you can return an
	 *             object of a different class type for the specified result object.
	 **/

	public Object metadataQueryReplacementObjectForResultObject(NSMetadataQuery query, NSMetadataItem result);

	// 2
	/**
	 * @Signature: metadataQuery:replacementValueForAttribute:value:
	 * @Declaration : - (id)metadataQuery:(NSMetadataQuery *)query replacementValueForAttribute:(NSString *)attribute
	 *              value:(id)attributeValue
	 * @Description : Implemented by the delegate to return a different value for a specific attribute.
	 * @return Return Value Object that replaces the value of attribute in the result object
	 * @Discussion The delegate implementation of this method could convert specific query attribute values to other attribute values, for
	 *             example, converting date object values to formatted strings for display.
	 **/

	public Object metadataQueryReplacementValueForAttributeValue(NSMetadataQuery query, NSString attribute, Object attributeValue);
}
