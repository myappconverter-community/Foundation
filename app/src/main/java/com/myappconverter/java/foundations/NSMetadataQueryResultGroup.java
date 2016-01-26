
package com.myappconverter.java.foundations;



public class NSMetadataQueryResultGroup extends NSObject {

	private NSObject attribute;
	private NSObject value;
	private NSMutableArray subgroups;

	// Getting Query Results
	/**
	 * @Signature: attribute
	 * @Declaration : - (NSString *)attribute
	 * @Description : Returns the attribute name for the receiver’s result group.
	 * @return Return Value The attribute name for the receiver’s result group.
	 **/
	
	public NSString attribute() {
		return (NSString) attribute;
	}

	/**
	 * @Signature: value
	 * @Declaration : - (id)value
	 * @Description : Returns the value of the attribute name for the receiver.
	 * @return Return Value The value of the attribute name for the receiver.
	 **/
	
	public NSObject value() {
		return value;
	}

	/**
	 * @Signature: results
	 * @Declaration : - (NSArray *)results
	 * @Description : Returns an array containing the result objects for the receiver.
	 * @return Return Value An array containing the result objects for the receiver.
	 * @Discussion The results array is a proxy object that is primarily intended for use with Cocoa bindings. While it is possible to copy
	 *             the proxy array to get a “snapshot of the complete current query results, it is generally not recommended due to
	 *             performance and memory issues. To access individual result array elements you should instead use the resultCount and
	 *             resultAtIndex: methods.
	 **/
	
	public NSArray results() {
		return subgroups;
	}

	/**
	 * @Signature: resultCount
	 * @Declaration : - (NSUInteger)resultCount
	 * @Description : Returns the number of results returned by the receiver.
	 * @return Return Value The number of results returned by the receiver.
	 * @Discussion For performance reasons, you should use this method, rather than invoking count on results.
	 **/
	
	public int resultCount() {
		return subgroups.count();
	}

	/**
	 * @Signature: resultAtIndex:
	 * @Declaration : - (id)resultAtIndex:(NSUInteger)index
	 * @Description : Returns the query result at a specific index.
	 * @param index The index of the desired result.
	 * @return Return Value The query result at a specific index.
	 * @Discussion For performance reasons, you should use this method when retrieving a specific result, rather than they array returned by
	 *             results.
	 **/
	
	public Object resultAtIndex(int index) {
		return subgroups.objectAtIndex(index);
	}

	/**
	 * @Signature: subgroups
	 * @Declaration : - (NSArray *)subgroups
	 * @Description : Returns an array containing the subgroups of the receiver.
	 * @return Return Value An array containing the subgroups of the receiver.
	 **/
	
	public NSArray subgroups() {
		return subgroups;
	}

}