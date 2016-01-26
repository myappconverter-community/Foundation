
package com.myappconverter.java.foundations;

import java.util.Iterator;
import java.util.TreeSet;




public class NSMutableIndexSet extends NSIndexSet {

	public NSMutableIndexSet() {
		super();
	}

	// Adding Indexes
	/**
	 * @Signature: addIndex:
	 * @Declaration : - (void)addIndex:(NSUInteger)index
	 * @Description : Adds an index to the receiver.
	 * @param index Index to add. Must be in the range 0 .. NSNotFound - 1.
	 **/
	
	public void addIndex(long index) {
		wrappedTreeSet.add((int) index);
		// not yet covered
	}

	/**
	 * @Signature: addIndexes:
	 * @Declaration : - (void)addIndexes:(NSIndexSet *)indexSet
	 * @Description : Adds the indexes in an index set to the receiver.
	 * @param indexSet Index set to add.
	 **/
	
	public void addIndexes(NSIndexSet indexSet) {
		for (Integer element : indexSet.getWrappedTreeSet()) {
			wrappedTreeSet.add(element);
		}
		// not yet covered
	}

	/**
	 * - (void)addIndexesInRange:(NSRange)indexRange
	 * 
	 * @Description : Adds the indexes in an index range to the receiver.
	 * @param indexRange Index range to add. Must be in the range 0 .. NSNotFound - 1.
	 * @Discussion This method raises an NSRangeException when indexRange would add an index that exceeds the maximum allowed value for
	 *             unsigned integers.
	 */
	
	public void addIndexesInRange(NSRange indexRange) {
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			wrappedTreeSet.add(i);
		}
		// not yet covered
	}

	// Removing Indexes

	/**
	 * @Signature: removeIndex:
	 * @Declaration : - (void)removeIndex:(NSUInteger)index
	 * @Description : Removes an index from the receiver.
	 * @param index Index to remove.
	 **/
	
	public void removeIndex(long index) {// FIXME
		wrappedTreeSet.remove(index);
	}

	/**
	 * - (void)removeIndexes:(NSIndexSet *)indexSet
	 * 
	 * @Description : Removes the indexes in an index set from the receiver.
	 * @param indexSet Index set to remove.
	 */
	
	public void removeIndexes(NSIndexSet indexSet) {
		Iterator<Integer> iter = wrappedTreeSet.iterator();
		while (iter.hasNext()) {
			if (indexSet.getWrappedTreeSet().contains(iter.next()))
				iter.remove();
		}
	}

	/**
	 * - (void)removeAllIndexes
	 * 
	 * @Description : Removes the receiver’s indexes.
	 */
	
	public void removeAllIndexes() {// FIXME
		wrappedTreeSet.clear();
	}

	/**
	 * - (void)removeIndexesInRange:(NSRange)indexRange
	 * 
	 * @Description : Removes the indexes in an index range from the receiver.
	 * @param indexRange Index range to remove.
	 */
	
	public void removeIndexesInRange(NSRange indexRange) {
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			wrappedTreeSet.remove(i);
		}
	}

	// Shifting Index Groups

	/**
	 * @Signature: shiftIndexesStartingAtIndex:by:
	 * @Declaration : - (void)shiftIndexesStartingAtIndex:(NSUInteger)startIndex by:(NSInteger)delta
	 * @Description : Shifts a group of indexes to the left or the right within the receiver.
	 * @param startIndex Head of the group of indexes to shift.
	 * @param delta Amount and direction of the shift. Positive integers shift the indexes to the right. Negative integers shift the indexes
	 *            to the left.
	 * @Discussion The group of indexes shifted is made up by startIndex and the indexes that follow it in the set. A left shift deletes the
	 *             indexes in a range the length of delta preceding startIndex from the set. A right shift inserts empty space in the range
	 *             (startIndex,delta) in the receiver. The resulting indexes must all be in the range 0 .. NSNotFound - 1.
	 **/
	
	public void shiftIndexesStartingAtIndexBy(long startIndex, long delta) {
		// remove elements
		Iterator<Integer> iter = wrappedTreeSet.iterator();
		while (iter.hasNext()) {
			Integer element = iter.next();
			if (element >= startIndex && element < (startIndex + delta)) {
				iter.remove();
			}
		}
		// add elements

		// FIXME last()?
		int lastValue = wrappedTreeSet.last().intValue();
		for (int j = lastValue + 1; j < (lastValue + delta + 1); j++) {
			wrappedTreeSet.add(j);
		}
	}

	public static NSMutableIndexSet indexSet() {
		NSMutableIndexSet indexSet = new NSMutableIndexSet();
		return indexSet;
	}

	public static NSMutableIndexSet indexSetWithIndex(int index) {
		NSMutableIndexSet indexSet = new NSMutableIndexSet();
		indexSet.wrappedTreeSet.add(index);
		return indexSet;
	}

	public static NSMutableIndexSet indexSetWithIndexesInRange(NSRange indexRange) {
		NSMutableIndexSet indexSet = new NSMutableIndexSet();
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			indexSet.wrappedTreeSet.add(i);
		}
		return indexSet;
	}

	public NSMutableIndexSet init() {
		this.wrappedTreeSet = new TreeSet<Integer>();
		return this;
	}

	public NSMutableIndexSet initWithIndex(int index) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		this.wrappedTreeSet.add(index);
		return this;
	}

	public NSMutableIndexSet initWithIndexesInRange(NSRange indexRange) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			this.wrappedTreeSet.add(i);
		}
		return this;
	}

	public NSMutableIndexSet initWithIndexSet(NSIndexSet indexSet) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		for (Integer element : indexSet.wrappedTreeSet) {
			this.wrappedTreeSet.add(element);
		}
		return this;
	}

}