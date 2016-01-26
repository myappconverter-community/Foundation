
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSMutableCopying;
import com.myappconverter.mapping.utils.PerformBlock;



public class NSIndexSet extends NSObject implements NSCoding, NSCopying, NSMutableCopying {

	public TreeSet<Integer> wrappedTreeSet = new TreeSet<Integer>();

	public TreeSet<Integer> getWrappedTreeSet() {
		return wrappedTreeSet;
	}

	public void setWrappedTreeSet(TreeSet<Integer> wrappedTreeSet) {
		this.wrappedTreeSet = wrappedTreeSet;
	}

	public NSIndexSet() {
		super();
		this.wrappedTreeSet = new TreeSet<Integer>();
	}

	// Creating Index Sets

	/**
	 * @Signature: indexSet
	 * @Declaration : + (instancetype)indexSet
	 * @Description : Creates an empty index set.
	 * @return Return Value NSIndexSet object with no members.
	 **/
	
	public static NSIndexSet indexSet() {
		NSIndexSet indexSet = new NSIndexSet();
		return indexSet;
	}

	/**
	 * @Signature: indexSetWithIndex:
	 * @Declaration : + (instancetype)indexSetWithIndex:(NSUInteger)index
	 * @Description : Creates an index set with an index.
	 * @param index An index. Must be in the range 0 .. NSNotFound - 1.
	 * @return Return Value NSIndexSet object containing index.
	 **/
	
	public static NSIndexSet indexSetWithIndex(int index) {
		NSIndexSet indexSet = new NSIndexSet();
		indexSet.wrappedTreeSet.add(index);
		return indexSet;
	}

	/**
	 * @Signature: indexSetWithIndexesInRange:
	 * @Declaration : + (instancetype)indexSetWithIndexesInRange:(NSRange)indexRange
	 * @Description : Creates an index set with an index range.
	 * @param indexRange An index range. Must be in the range 0 .. NSNotFound - 1.
	 * @return Return Value NSIndexSet object containing indexRange.
	 **/
	
	public static NSIndexSet indexSetWithIndexesInRange(NSRange indexRange) {
		NSIndexSet indexSet = new NSIndexSet();
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			indexSet.wrappedTreeSet.add(i);
		}
		return indexSet;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes an allocated NSIndexSet object.
	 * @return Return Value Initialized, empty NSIndexSet object.
	 * @Discussion This method is a designated initializer for NSIndexSet.
	 **/
	@Override
	
	public NSIndexSet init() {
		this.wrappedTreeSet = new TreeSet<Integer>();
		return this;
	}

	/**
	 * @Signature: initWithIndex:
	 * @Declaration : - (instancetype)initWithIndex:(NSUInteger)index
	 * @Description : Initializes an allocated NSIndexSet object with an index.
	 * @param index An index. Must be in the range 0 .. NSNotFound - 1.
	 * @return Return Value Initialized NSIndexSet object with index.
	 **/
	
	public NSIndexSet initWithIndex(int index) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		this.wrappedTreeSet.add(index);
		return this;
	}

	/**
	 * - (instancetype)initWithIndexesInRange:(NSRange)indexRange
	 * 
	 * @Description : Initializes an allocated NSIndexSet object with an index range.
	 * @param indexRange An index range. Must be in the range 0 .. NSNotFound - 1..
	 * @return Return Value Initialized NSIndexSet object with indexRange.
	 * @Discussion This method raises an NSRangeException when indexRange would add an index that exceeds the maximum allowed value for
	 *             unsigned integers. This method is a designated initializer for NSIndexSet.
	 */
	
	public NSIndexSet initWithIndexesInRange(NSRange indexRange) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		for (int i = indexRange.location; i < indexRange.location + indexRange.length; i++) {
			this.wrappedTreeSet.add(i);
		}
		return this;
	}

	/**
	 * - (instancetype)initWithIndexSet:(NSIndexSet *)indexSet
	 * 
	 * @Description : Initializes an allocated NSIndexSet object with an index set.
	 * @param indexSet An index set.
	 * @return Return Value Initialized NSIndexSet object with indexSet.
	 * @Discussion This method is a designated initializer for NSIndexSet.
	 */
	
	public NSIndexSet initWithIndexSet(NSIndexSet indexSet) {
		this.wrappedTreeSet = new TreeSet<Integer>();
		for (Integer element : indexSet.wrappedTreeSet) {
			this.wrappedTreeSet.add(element);
		}
		return this;
	}

	// Querying Index Sets

	/**
	 * @Signature: containsIndex:
	 * @Declaration : - (BOOL)containsIndex:(NSUInteger)index
	 * @Description : Indicates whether the index set contains a specific index.
	 * @param index Index being inquired about.
	 * @return Return Value YES when the index set contains index, NO otherwise.
	 **/
	
	public Boolean containsIndex(int index) {
		return wrappedTreeSet.contains(index);
	}

	/**
	 * @Signature: containsIndexes:
	 * @Declaration : - (BOOL)containsIndexes:(NSIndexSet *)indexSet
	 * @Description : Indicates whether the receiving index set contains a superset of the indexes in another index set.
	 * @param indexSet Index set being inquired about.
	 * @return Return Value YES when the receiving index set contains a superset of the indexes in indexSet, NO otherwise.
	 **/
	
	public Boolean containsIndexes(NSIndexSet indexSet) {
		for (Integer element : indexSet.wrappedTreeSet) {
			if (!wrappedTreeSet.contains(element)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * - (BOOL)containsIndexesInRange:(NSRange)indexRange
	 * 
	 * @Description : Indicates whether the index set contains the indexes represented by an index range.
	 * @param indexRange The index range being inquired about.
	 * @return Return Value YES when the index set contains the indexes in indexRange, NO otherwise.
	 */
	
	public Boolean containsIndexesInRange(NSRange indexRange) {
		NSIndexSet indRange = NSIndexSet.indexSetWithIndexesInRange(indexRange);
		return containsIndexes(indRange);
	}

	/**
	 * - (BOOL)intersectsIndexesInRange:(NSRange)indexRange
	 * 
	 * @Description : Indicates whether the index set contains any of the indexes in a range.
	 * @param indexRange Index range being inquired about.
	 * @return Return Value YES when the index set contains one or more of the indexes in indexRange, NO otherwise.
	 */
	
	public Boolean intersectsIndexesInRange(NSRange indexRange) {
		for (Integer element : this.wrappedTreeSet) {
			if (element.intValue() >= indexRange.location
					&& element.intValue() < (indexRange.length + indexRange.location)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Signature: count
	 * @Declaration : - (NSUInteger)count
	 * @Description : Returns the number of indexes in the index set.
	 * @return Return Value Number of indexes in the index set.
	 **/
	
	public int count() {
		return wrappedTreeSet.size();
	}

	/**
	 * @Signature: countOfIndexesInRange:
	 * @Declaration : - (NSUInteger)countOfIndexesInRange:(NSRange)indexRange
	 * @Description : Returns the number of indexes in the index set that are members of a given range.
	 * @param indexRange Index range being inquired about.
	 * @return Return Value Number of indexes in the index set that are members of indexRange.
	 **/
	
	public int countOfIndexesInRange(NSRange indexRange) {
		int nb = 0;
		for (Integer element : this.wrappedTreeSet) {
			if (element.intValue() >= indexRange.location
					&& element.intValue() < (indexRange.length + indexRange.location)) {
				nb++;
			}
		}
		return nb;
	}

	/**
	 * @Signature: indexPassingTest:
	 * @Declaration : - (NSUInteger)indexPassingTest:(BOOL (^)(NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index of the first object that passes the predicate Block test.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The index of the first object that passes the predicate test.
	 **/
	
	public int indexPassingTest(PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {
		return indexWithOptionsPassingTest(NSObjCRuntime.NSEnumerationConcurrent, predicate);
		// TODO check that like NSArray
	}

	/**
	 * @Signature: indexesPassingTest:
	 * @Declaration : - (NSIndexSet *)indexesPassingTest:(BOOL (^)(NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns an NSIndexSet containing the receiving index set’s objects that pass the Block test.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value An NSIndexSet containing the indexes of the receiving index set that passed the predicate Block test.
	 **/
	
	public NSIndexSet indexesPassingTest(PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {

		return indexesWithOptionsPassingTest(NSObjCRuntime.NSEnumerationConcurrent, predicate);
		// TODO Check that like NSArray
	}

	/**
	 * @Signature: indexWithOptions:passingTest:
	 * @Declaration : - (NSUInteger)indexWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(NSUInteger idx, BOOL *stop))predicate
	 * @Description : Returns the index of the first object that passes the predicate Block test using the specified enumeration options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The index of the first object that passes the predicate test.
	 **/
	
	public int indexWithOptionsPassingTest(int opts,
			PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {
		List<Integer> tmpList = new ArrayList<Integer>(wrappedTreeSet);
		boolean[] stop = new boolean[1];
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationReverse) {
			Collections.reverse(tmpList);
		}
		for (int i = 0; i < tmpList.size(); i++) {
			if (predicate.perform(tmpList.get(i), i, stop))
				return i;
		}
		return NSObjCRuntime.NSNotFound;
		// TODO check that like NSArray
	}

	/**
	 * @Signature: indexesWithOptions:passingTest:
	 * @Declaration : - (NSIndexSet *)indexesWithOptions:(NSEnumerationOptions)opts passingTest:(BOOL (^)(NSUInteger idx, BOOL
	 *              *stop))predicate
	 * @Description : Returns an NSIndexSet containing the receiving index set’s objects that pass the Block test using the specified
	 *              enumeration options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value An NSIndexSet containing the indexes of the receiving index set that passed the predicate Block test.
	 **/
	
	public NSIndexSet indexesWithOptionsPassingTest(int opts,
			PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {
		NSIndexSet resIndexSet = new NSIndexSet();
		boolean[] stop = new boolean[1];
		resIndexSet.wrappedTreeSet = new TreeSet<Integer>();
		List<Integer> tmpList = new ArrayList<Integer>(wrappedTreeSet);
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationReverse) {
			Collections.reverse(tmpList);
		}
		for (int i = 0; i < tmpList.size(); i++) {

			if (predicate.perform(tmpList.get(i), i, stop))
				resIndexSet.wrappedTreeSet.add(i);
		}
		return resIndexSet;
		// TODO Check that like NSArray
	}

	/**
	 * @Signature: indexInRange:options:passingTest:
	 * @Declaration : - (NSUInteger)indexInRange:(NSRange)range options:(NSEnumerationOptions)opts passingTest:(BOOL (^)(NSUInteger idx,
	 *              BOOL *stop))predicate
	 * @Description : Returns the index of the first object in the specified range that passes the predicate Block test.
	 * @param range The range of indexes to test.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value The index of the first object that passes the predicate test.
	 **/
	
	public int indexInRangeOptionsPassingTest(NSRange range, int opts,
			PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {
		List<Integer> tmpList = new ArrayList<Integer>(wrappedTreeSet);
		boolean[] stop = new boolean[1];
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationReverse) {
			Collections.reverse(tmpList);
		}
		for (int i = range.location; i < range.location + range.length; i++) {
			if (predicate.perform(tmpList.get(i), i, stop))
				return i;
		}
		return NSObjCRuntime.NSNotFound;
		// TODO check that like NSArray
	}

	/**
	 * @Signature: indexesInRange:options:passingTest:
	 * @Declaration : - (NSIndexSet *)indexesInRange:(NSRange)range options:(NSEnumerationOptions)opts passingTest:(BOOL (^)(NSUInteger idx,
	 *              BOOL *stop))predicate
	 * @Description : Returns an NSIndexSet containing the receiving index set’s objects in the specified range that pass the Block test.
	 * @param range The range of indexes to test.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param predicate The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument
	 *            is an out-only argument. You should only ever set this Boolean to YES within the Block. The Block returns a Boolean value
	 *            that indicates whether obj passed the test.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @return Return Value An NSIndexSet containing the indexes of the receiving index set that passed the predicate Block test.
	 **/
	
	public NSIndexSet indexesInRangeOptionsPassingTest(NSRange range, int opts,
			PerformBlock.BOOLBlockNSUIntegerBOOL predicate) {
		NSIndexSet resIndexSet = new NSIndexSet();
		boolean[] stop = new boolean[1];
		resIndexSet.wrappedTreeSet = new TreeSet<Integer>();
		;
		List<Integer> tmpList = new ArrayList<Integer>(wrappedTreeSet);
		if (predicate == null)
			throw new IllegalArgumentException("null parameter not allowed");

		if (opts != NSObjCRuntime.NSEnumerationReverse) {
			Collections.reverse(tmpList);
		}
		for (int i = range.location; i < range.location + range.length; i++) {
			if (predicate.perform(tmpList.get(i), i, stop))
				resIndexSet.wrappedTreeSet.add(i);
		}
		return resIndexSet;
		// TODO Check that like NSArray
	}

	// Enumerating Index Set Content

	/**
	 * @Signature: enumerateRangesInRange:options:usingBlock:
	 * @Declaration : - (void)enumerateRangesInRange:(NSRange)range options:(NSEnumerationOptions)opts usingBlock:(void (^)(NSRange range,
	 *              BOOL *stop))block
	 * @Description : Enumerates over the ranges in the range of objects using the block
	 * @param range The range of items to enumerate. If the range intersects a range of the receiver's indexes, then that intersection will
	 *            be passed to the block.
	 * @param opts A bitmask that specifies the NSEnumerationOptions for the enumeration.
	 * @param block The block to apply to elements in the index set. The block takes two arguments: range The range of elements. stop A
	 *            reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @param range The range of elements.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the indexed set range to the last
	 *             object in the range. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify
	 *             this behavior. This method executes synchronously. Important: If the Block parameter is nil this method will raise an
	 *             exception.
	 **/
	
	public void enumerateRangesInRangeOptionsUsingBlock(NSRange range, boolean stop,
			PerformBlock.VoidBlockNSRangeBOOL block) {
        // not yet covered
	}

	/**
	 * @Signature: enumerateRangesUsingBlock:
	 * @Declaration : - (void)enumerateRangesUsingBlock:(void (^)(NSRange range, BOOL *stop))block
	 * @Description : Executes a given block using each object in the index set, in the specified ranges.
	 * @param block The block to apply to elements in the index set. The block takes two arguments: range The range of objects of the
	 *            elements in the index set. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block.
	 * @param range The range of objects of the elements in the index set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion If the Block parameter is nil this method will raise an exception. This method executes synchronously.
	 **/
	
	public void enumerateRangesUsingBlock(PerformBlock.VoidBlockNSRangeBOOL block) {
        // not yet covered
	}

	/**
	 * @Signature: enumerateRangesWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateRangesWithOptions:(NSEnumerationOptions)opts usingBlock:(void (^)(NSRange range, BOOL *stop))block
	 * @Description : Executes a given block using each object in the index set, in the specified ranges.
	 * @param opts A bitmask that specifies the NSEnumerationOptions for the enumeration (whether it should be performed concurrently and
	 *            whether it should be performed in reverse order).
	 * @param block The block to apply to elements in the index set. The block takes two arguments: range The range of objects of the
	 *            elements in the index set. stop A reference to a Boolean value. The block can set the value to YES to stop further
	 *            processing of the array. The stop argument is an out-only argument. You should only ever set this Boolean to YES within
	 *            the Block.
	 * @param range The range of objects of the elements in the index set.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the array. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion By default, the enumeration starts with the first object and continues serially through the indexed set range to the last
	 *             object in the range. You can specify NSEnumerationConcurrent and/or NSEnumerationReverse as enumeration options to modify
	 *             this behavior. This method executes synchronously. Important: If the Block parameter is nil this method will raise an
	 *             exception.
	 **/
	
	public void enumerateRangesWithOptionsUsingBlock(int opts,
			PerformBlock.VoidBlockNSRangeBOOL block) {
		// boolean[] stop = new boolean[1];
		// block.performAction(range, stop);
		// enumerateIndexesInRangeOptionsUsingBlock(range, opts, block);
		// TODO check that
	}

	// Comparing Index Sets

	/**
	 * @Signature: isEqualToIndexSet:
	 * @Declaration : - (BOOL)isEqualToIndexSet:(NSIndexSet *)indexSet
	 * @Description : Indicates whether the indexes in the receiving index set are the same indexes contained in another index set.
	 * @param indexSet Index set being inquired about.
	 * @return Return Value YES when the indexes in the receiving index set are the same indexes indexSet contains, NO otherwise.
	 **/
	
	public Boolean isEqualToIndexSet(NSIndexSet indexSet) {
		return this.wrappedTreeSet.equals(indexSet.wrappedTreeSet);
	}

	// Getting Indexes

	/**
	 * @Signature: firstIndex
	 * @Declaration : - (NSUInteger)firstIndex
	 * @Description : Returns either the first index in the index set or the not-found indicator.
	 * @return Return Value First index in the index set or NSNotFound when the index set is empty.
	 **/
	
	public int firstIndex() {
		return this.wrappedTreeSet.first();
	}

	/**
	 * @Signature: lastIndex
	 * @Declaration : - (NSUInteger)lastIndex
	 * @Description : Returns either the last index in the index set or the not-found indicator.
	 * @return Return Value Last index in the index set or NSNotFound when the index set is empty.
	 **/
	
	public int lastIndex() {
		return this.wrappedTreeSet.last();
	}

	/**
	 * @Signature: indexLessThanIndex:
	 * @Declaration : - (NSUInteger)indexLessThanIndex:(NSUInteger)index
	 * @Description : Returns either the closest index in the index set that is less than a specific index or the not-found indicator.
	 * @param index Index being inquired about.
	 * @return Return Value Closest index in the index set less than index; NSNotFound when the index set contains no qualifying index.
	 **/
	
	public int indexLessThanIndex(int index) {
		if (this.wrappedTreeSet.lower(index) == null) {
			return NSObjCRuntime.NSNotFound;
		}
		return this.wrappedTreeSet.lower(index);
	}

	/**
	 * @Signature: indexLessThanOrEqualToIndex:
	 * @Declaration : - (NSUInteger)indexLessThanOrEqualToIndex:(NSUInteger)index
	 * @Description : Returns either the closest index in the index set that is less than or equal to a specific index or the not-found
	 *              indicator.
	 * @param index Index being inquired about.
	 * @return Return Value Closest index in the index set less than or equal to index; NSNotFound when the index set contains no qualifying
	 *         index.
	 **/
	
	public int indexLessThanOrEqualToIndex(int index) {
		if (this.wrappedTreeSet.floor(index) == null) {
			return NSObjCRuntime.NSNotFound;
		}
		return this.wrappedTreeSet.floor(index);
	}

	/**
	 * @Signature: indexGreaterThanOrEqualToIndex:
	 * @Declaration : - (NSUInteger)indexGreaterThanOrEqualToIndex:(NSUInteger)index
	 * @Description : Returns either the closest index in the index set that is greater than or equal to a specific index or the not-found
	 *              indicator.
	 * @param index Index being inquired about.
	 * @return Return Value Closest index in the index set greater than or equal to index; NSNotFound when the index set contains no
	 *         qualifying index.
	 **/
	
	public int indexGreaterThanOrEqualToIndex(int index) {
		if (this.wrappedTreeSet.ceiling(index) == null) {
			return NSObjCRuntime.NSNotFound;
		}
		return this.wrappedTreeSet.ceiling(index);
	}

	/**
	 * @Signature: indexGreaterThanIndex:
	 * @Declaration : - (NSUInteger)indexGreaterThanIndex:(NSUInteger)index
	 * @Description : Returns either the closest index in the index set that is greater than a specific index or the not-found indicator.
	 * @param index Index being inquired about.
	 * @return Return Value Closest index in the index set greater than index; NSNotFound when the index set contains no qualifying index.
	 **/
	
	public int indexGreaterThanIndex(int index) {
		if (this.wrappedTreeSet.higher(index) == null) {
			return NSObjCRuntime.NSNotFound;
		}
		return this.wrappedTreeSet.higher(index);
	}

	/**
	 * @Signature: getIndexes:maxCount:inIndexRange:
	 * @Declaration : - (NSUInteger)getIndexes:(NSUInteger *)indexBuffer maxCount:(NSUInteger)bufferSize
	 *              inIndexRange:(NSRangePointer)indexRangePointer
	 * @Description : The index set fills an index buffer with the indexes contained both in the index set and in an index range, returning
	 *              the number of indexes copied.
	 * @param indexBuffer Index buffer to fill.
	 * @param bufferSize Maximum size of indexBuffer.
	 * @param indexRange Index range to compare with indexes in the index set; nil represents all the indexes in the index set. Indexes in
	 *            the index range and in the index set are copied to indexBuffer. On output, the range of indexes not copied to indexBuffer.
	 * @return Return Value Number of indexes placed in indexBuffer.
	 * @Discussion You are responsible for allocating the memory required for indexBuffer and for releasing it later. Suppose you have an
	 *             index set with contiguous indexes from 1 to 100. If you use this method to request a range of (1, 100)—which represents
	 *             the set of indexes 1 through 100—and specify a buffer size of 20, this method returns 20 indexes—1 through 20—in
	 *             indexBuffer and sets indexRange to (21, 80)—which represents the indexes 21 through 100. Use this method to retrieve
	 *             entries quickly and efficiently from an index set. You can call this method repeatedly to retrieve blocks of index values
	 *             and then process them. When doing so, use the return value and indexRange to determine when you have finished processing
	 *             the desired indexes. When the return value is less than bufferSize, you have reached the end of the range.
	 **/
	
	public int getIndexesMaxCountInIndexRange(int[] indexBuffer, int bufferSize,
			NSRangePointer indexRangePointer) {
		if (bufferSize > indexBuffer.length) {
			throw new IllegalArgumentException("bufferSize > indexBuffer.length");
		}
		if (indexRangePointer == null) {
			int i = 0;
			for (Integer element : this.wrappedTreeSet) {
				if (i < indexBuffer.length) {
					indexBuffer[i] = element;
					i++;
				}
			}
		} else {
			int i = 0;
			int j = 0;
			for (Integer element : this.wrappedTreeSet) {
				if (i >= indexRangePointer.location
						&& (i < indexRangePointer.location + indexRangePointer.length)
						&& j < indexBuffer.length) {
					indexBuffer[j] = element;
					j++;
					i++;
				}
			}
		}

		return indexBuffer.length;
	}

	// Enumerating Indexes
	/**
	 * @Signature: enumerateIndexesUsingBlock:
	 * @Declaration : - (void)enumerateIndexesUsingBlock:(void (^)(NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given Block using each object in the index set.
	 * @param block The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A reference
	 *            to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an
	 *            out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion This method executes synchronously.
	 **/
	
	public void enumerateIndexesUsingBlock(PerformBlock.VoidBlockNSUIntegerBOOL block) {
		boolean[] stop = new boolean[1];
		int i = 0;
		while (wrappedTreeSet.iterator().hasNext()) {
			if (!block.perform(wrappedTreeSet.iterator().next(), i, stop))
				break;
			i++;
		}
	}

	/**
	 * @Signature: enumerateIndexesWithOptions:usingBlock:
	 * @Declaration : - (void)enumerateIndexesWithOptions:(NSEnumerationOptions)opts usingBlock:(void (^)(NSUInteger idx, BOOL *stop))block
	 * @Description : Executes a given Block over the index set’s indexes, using the specified enumeration options.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param block The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A reference
	 *            to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an
	 *            out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion This method executes synchronously.
	 **/
	
	public void enumerateIndexesWithOptionsUsingBlock(int opts,
			PerformBlock.VoidBlockNSUIntegerBOOL block) {
		boolean[] stop = new boolean[1];
		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			int idx = 0;
			while (wrappedTreeSet.iterator().hasNext()) {
				if (!block.perform(wrappedTreeSet.iterator().next(), idx, stop))
					break;
				idx++;
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			List<Integer> myTmpList = new ArrayList();
			myTmpList.addAll(wrappedTreeSet);

			Collections.reverse(myTmpList);

			int idx = 0;
			while (myTmpList.iterator().hasNext()) {
				if (!block.perform(myTmpList.iterator().next(), idx, stop))
					break;
				idx++;
			}
		}
		// TODO check that
	}

	/**
	 * @Signature: enumerateIndexesInRange:options:usingBlock:
	 * @Declaration : - (void)enumerateIndexesInRange:(NSRange)range options:(NSEnumerationOptions)opts usingBlock:(void (^)(NSUInteger idx,
	 *              BOOL *stop))block
	 * @Description : Executes a given Block using the indexes in the specified range, using the specified enumeration options.
	 * @param range The range to enumerate.
	 * @param opts A bitmask that specifies the options for the enumeration (whether it should be performed concurrently and whether it
	 *            should be performed in reverse order). See NSEnumerationOptions for the supported values.
	 * @param block The Block to apply to elements in the set. The Block takes two arguments: idx The index of the object. stop A reference
	 *            to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop argument is an
	 *            out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @param idx The index of the object.
	 * @param stop A reference to a Boolean value. The block can set the value to YES to stop further processing of the set. The stop
	 *            argument is an out-only argument. You should only ever set this Boolean to YES within the Block.
	 * @Discussion This method executes synchronously.
	 **/
	
	public void enumerateIndexesInRangeOptionsUsingBlock(NSRange range, int opts,
			PerformBlock.VoidBlockNSUIntegerBOOL block) {
		boolean[] stop = new boolean[1];
		if (opts == NSObjCRuntime.NSEnumerationReverse) {
			Collections.reverse((List<?>) this.wrappedTreeSet);// NO casted //TODO
		}

		if (opts == NSObjCRuntime.NSEnumerationConcurrent) {
			int i = 0;
			while (wrappedTreeSet.iterator().hasNext()) {
				if (i > range.location && i < range.location + range.length &&
                        !block.perform(wrappedTreeSet.iterator().next(), i, stop))
						break;
					i++;
			}
		} else if (opts == NSObjCRuntime.NSEnumerationReverse) {
			List<Integer> myTmpList = new ArrayList();
			myTmpList.addAll(wrappedTreeSet);
			Collections.reverse(myTmpList);
			int i = 0;
			for (Integer element : myTmpList) {
				if (i > range.location && i < range.location + range.length &&
                        !block.perform(wrappedTreeSet.iterator().next(), i, stop))
						break;
					i++;
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((wrappedTreeSet == null) ? 0 : wrappedTreeSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NSIndexSet other = (NSIndexSet) obj;
		if (wrappedTreeSet == null) {
			if (other.wrappedTreeSet != null)
				return false;
		} else if (!wrappedTreeSet.equals(other.wrappedTreeSet))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NSIndexSet [indexes=" + wrappedTreeSet + "]";
	}

	@Override
	public Object mutableCopyWithZone(NSZone zone) {

		return null;
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

}