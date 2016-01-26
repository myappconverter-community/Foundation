
package com.myappconverter.java.foundations;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.myappconverter.java.foundations.NSPointerFunctions.NSPointerFunctionsOptions;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSFastEnumeration;


public class NSPointerArray extends NSObject implements NSCoding, NSCopying, NSFastEnumeration, Iterable<NSObject>, Iterator<NSObject> {

	private NSPointerFunctionsOptions ptrOptions = NSPointerFunctionsOptions.NSPointerFunctionsStrongMemory;
	private NSMutableArray<NSObject> pointerArray;
	private NSPointerFunctions functionsPtr;

	public NSPointerArray(NSPointerFunctionsOptions options) {
		this.ptrOptions = options;
	}

	public NSPointerArray(NSPointerFunctions functions) {
		this.functionsPtr = functions;
	}

	/**
	 * @Signature: pointerArrayWithOptions:
	 * @Declaration : + (id)pointerArrayWithOptions:(NSPointerFunctionsOptions)options
	 * @Description : Returns a new pointer array initialized to use the given options.
	 * @param options The pointer functions options for the new instance.
	 * @return Return Value A new pointer array initialized to use the given options.
	 **/

	public static NSObject pointerArrayWithOptions(NSPointerFunctionsOptions options) {
		return new NSPointerArray(options);
	}

	public NSArray<NSObject> getPointerArray() {
		return pointerArray;
	}

	/**
	 * @Signature: pointerArrayWithPointerFunctions:
	 * @Declaration : + (id)pointerArrayWithPointerFunctions:(NSPointerFunctions *)functions
	 * @Description : A new pointer array initialized to use the given functions.
	 * @param functions The pointer functions for the new instance.
	 * @return Return Value A new pointer array initialized to use the given pointer functions.
	 **/

	public static NSObject pointerArrayWithPointerFunctions(NSPointerFunctions functions) {

		return new NSPointerArray(functions);
	}

	/**
	 * @Signature: strongObjectsPointerArray
	 * @Declaration : + (id)strongObjectsPointerArray
	 * @Description : Returns a new pointer array that maintains strong references to its elements.
	 * @return Return Value A new pointer array that maintains strong references to its elements.
	 **/

	public static NSObject strongObjectsPointerArray() {
		return new NSPointerArray(NSPointerFunctionsOptions.NSPointerFunctionsStrongMemory);
	}

	/**
	 * @Signature: weakObjectsPointerArray
	 * @Declaration : + (id)weakObjectsPointerArray
	 * @Description : Returns a new pointer array that maintains weak references to its elements.
	 * @return Return Value A new pointer array that maintains weak references to its elements.
	 **/

	public static NSObject weakObjectsPointerArray() {
		return new NSPointerArray(NSPointerFunctionsOptions.NSPointerFunctionsWeakMemory);
	}

	/**
	 * @Signature: addPointer:
	 * @Declaration : - (void)addPointer:(void *)pointer
	 * @Description : Adds a given pointer to the receiver.
	 * @param pointer The pointer to add. This value may be NULL.
	 * @Discussion pointer is added at index count.
	 **/

	public void addPointer(Object anObject) {
		// not yet covered
	}

	/**
	 * @Signature: allObjects
	 * @Declaration : - (NSArray *)allObjects
	 * @Description : Returns an array containing all the objects in the receiver.
	 * @return Return Value An array containing all the objects in the receiver.
	 **/

	public NSArray allObjects() {
		return this.pointerArray;
	}

	/**
	 * @Signature: compact
	 * @Declaration : - (void)compact
	 * @Description : Removes NULL values from the receiver.
	 **/

	public void compact() {
		// not yet covered
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of elements in the receiver.
	 * @return Return Value The number of elements in the receiver.
	 **/

	public long count() {
		return this.pointerArray.count();
	}

	/**
	 * @Signature: initWithOptions:
	 * @Declaration : - (id)initWithOptions:(NSPointerFunctionsOptions)options
	 * @Description : Initializes the receiver to use the given options.
	 * @param options The pointer functions options for the new instance.
	 * @return Return Value The receiver, initialized to use the given options.
	 **/

	public NSObject initWithOptions(NSPointerFunctionsOptions options) {
		return new NSPointerArray(options);
	}

	/**
	 * @Signature: initWithPointerFunctions:
	 * @Declaration : - (id)initWithPointerFunctions:(NSPointerFunctions *)functions
	 * @Description : Initializes the receiver to use the given functions.
	 * @param functions The pointer functions for the new instance.
	 * @return Return Value The receiver, initialized to use the given functions.
	 **/

	public NSObject initWithPointerFunctions(NSPointerFunctions functions) {
		return new NSPointerArray(functions);
	}

	/**
	 * @Signature: insertPointer:atIndex:
	 * @Declaration : - (void)insertPointer:(void *)item atIndex:(NSUInteger)index
	 * @Description : Inserts a pointer at a given index.
	 * @param item The pointer to add.
	 * @param index The index of an element in the receiver. This value must be less than the count of the receiver.
	 * @Discussion Elements at and above index, including NULL values, slide higher.
	 **/

	public void insertPointerAtIndex(NSObject anObject, long index) {
		// not yet covered
	}

	/**
	 * @Signature: pointerAtIndex:
	 * @Declaration : - (void *)pointerAtIndex:(NSUInteger)index
	 * @Description : Returns the pointer at a given index.
	 * @param index The index of an element in the receiver. This value must be less than the count of the receiver.
	 * @return Return Value The pointer at index.
	 * @Discussion The returned value may be NULL.
	 **/

	public NSObject pointerAtIndex(int index) {
		return null;
	}

	/**
	 * @Signature: pointerFunctions
	 * @Declaration : - (NSPointerFunctions *)pointerFunctions
	 * @Description : Returns a new NSPointerFunctions object reflecting the functions in use by the receiver.
	 * @return Return Value A new NSPointerFunctions object reflecting the functions in use by the receiver.
	 * @Discussion The returned object is a new NSPointerFunctions object that you can modify and/or use directly to create other pointer
	 *             collections.
	 **/

	public NSPointerFunctions pointerFunctions() {
		return this.functionsPtr;
	}

	/**
	 * @Signature: removePointerAtIndex:
	 * @Declaration : - (void)removePointerAtIndex:(NSUInteger)index
	 * @Description : Removes the pointer at a given index.
	 * @param index The index of an element in the receiver. This value must be less than the count of the receiver.
	 * @Discussion Elements above index, including NULL values, slide lower.
	 **/

	public void removePointerAtIndex(int index) {
		this.pointerArray.removeObjectAtIndex(index);
	}

	/**
	 * @Signature: replacePointerAtIndex:withPointer:
	 * @Declaration : - (void)replacePointerAtIndex:(NSUInteger)index withPointer:(void *)item
	 * @Description : Replaces the pointer at a given index.
	 * @param index The index of an element in the receiver. This value must be less than the count of the receiver.
	 * @param item The item with which to replace the element at index. This value may be NULL.
	 **/

	public void replacePointerAtIndexWithPointer(long index, NSObject item) {
		// not yet covered
	}

	/**
	 * @Signature: setCount:
	 * @Declaration : - (void)setCount:(NSUInteger)count
	 * @Description : Sets the count for the receiver.
	 * @param count The count for the receiver.
	 * @Discussion If count is greater than the count of the receiver, NULL values are added; if count is less than the count of the
	 *             receiver, then elements at indexes count and greater are removed from the receiver.
	 **/

	public void setCount(int count) {
		if (count > this.pointerArray.count()) {
			for (int i = this.pointerArray.count(); i < count; i++) {
				this.pointerArray.addObjectsFromArray(null);
			}
		} else {
			for (int i = this.pointerArray.count(); i < count; i++) {
				this.pointerArray.removeObjectAtIndex(i);
			}
		}
	}

	@Override
	public int countByEnumeratingWithStateObjectsCount(NSFastEnumerationState state, Object[] stackbuf, int len) {
		return 0;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public boolean hasNext() {
		return pointerArray.iterator().hasNext();
	}

	@Override
	public NSObject next() throws NoSuchElementException{
		return pointerArray.iterator().next();
	}

	@Override
	public void remove() {
		pointerArray.iterator().remove();

	}

	@Override
	public Iterator<NSObject> iterator() {
		return pointerArray.iterator();
	}
}