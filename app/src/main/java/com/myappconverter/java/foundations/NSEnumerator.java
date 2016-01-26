
package com.myappconverter.java.foundations;

import java.util.Iterator;



public class NSEnumerator<E extends Object> extends NSObject implements Iterable<E>, Iterator<E> {
	protected Iterator<E> mIterator;

	public NSEnumerator() {
	}

	public NSEnumerator(Iterator<E> iterator) {
		mIterator = iterator;
	}

	// Getting the Enumerated Objects

	/**
	 * @Signature: allObjects
	 * @Declaration : - (NSArray *)allObjects
	 * @Description : Returns an array of objects the receiver has yet to enumerate.
	 * @return Return Value An array of objects the receiver has yet to enumerate.
	 * @Discussion Put another way, the array returned by this method does not contain objects that have already been enumerated with
	 *             previous nextObject messages. Invoking this method exhausts the enumerator’s collection so that subsequent invocations of
	 *             nextObject return nil.
	 **/
	
	public NSArray<E> allObjects() {
		NSMutableArray<E> myArray = new NSMutableArray<E>();
		while (mIterator.hasNext())
			myArray.addObject(mIterator.next());
		return myArray;
	}

	public NSArray<E> getAllObjects() {
		return allObjects();
	}

	/**
	 * @Signature: nextObject
	 * @Declaration : - (id)nextObject
	 * @Description : Returns the next object from the collection being enumerated.
	 * @return Return Value The next object from the collection being enumerated, or nil when all objects have been enumerated.
	 * @Discussion The following code illustrates how this method works using an array: NSArray *anArray = // ... ; NSEnumerator *enumerator
	 *             = [anArray objectEnumerator]; id object; while ((object = [enumerator nextObject])) { // do something with object... }
	 **/
	
	public Object nextObject() {
		if (mIterator.hasNext()) {
			return mIterator.next();
		}
		return null;
	}

	@Override
	public boolean hasNext() {
		return mIterator.hasNext();
	}

	@Override
	public E next() {
		return mIterator.next();
	}

	@Override
	public void remove() {
		mIterator.remove();

	}

	@Override
	public Iterator<E> iterator() {
		return mIterator;
	}

}