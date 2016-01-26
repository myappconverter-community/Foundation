
package com.myappconverter.java.foundations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;


public class NSIndexPath extends NSObject implements NSCoding, NSCopying, Iterable, Iterator {

	private List<Integer> wrappedArrayList;

	public List<Integer> getWrappedArrayList() {
		return wrappedArrayList;
	}

	public void setWrappedArrayList(List<Integer> wrappedArrayList) {
		this.wrappedArrayList = wrappedArrayList;
	}

	// Creating Index Paths

	/**
	 * @Signature: indexPathWithIndexes:length:
	 * @Declaration : + (instancetype)indexPathWithIndexes:(const NSUInteger [])indexes length:(NSUInteger)length
	 * @Description : Creates an index path with one or more nodes.
	 * @param indexes Array of indexes to make up the index path.
	 * @param length Number of nodes to include in the index path.
	 * @return Return Value Index path with indexes up to length.
	 **/
	
	public static NSIndexPath indexPathWithIndexesLength(Integer[] indexs, Integer length) {
		NSIndexPath indexPath = new NSIndexPath();
		for (int i = 0; i < length.intValue(); i++) {
			indexPath.wrappedArrayList.set(i, indexs[i]);
		}
		return indexPath;
	}

	/**
	 * @Signature: indexPathWithIndex:
	 * @Declaration : + (instancetype)indexPathWithIndex:(NSUInteger)index
	 * @Description : Creates an one-node index path.
	 * @param index Index of the item in node 0 to point to.
	 * @return Return Value One-node index path with index.
	 **/
	
	public static NSIndexPath indexPathWithIndex(Integer index) {
		NSIndexPath indexPath = new NSIndexPath();
		if (index != null) {
			indexPath.wrappedArrayList.add(index);
		}
		return indexPath;
	}

	/**
	 * @Signature: initWithIndex:
	 * @Declaration : - (instancetype)initWithIndex:(NSUInteger)index
	 * @Description : Initializes an allocated NSIndexPath object with a one-node index path.
	 * @param index Index of the item in node 0 to point to.
	 * @return Return Value Initialized NSIndexPath object representing a one-node index path with index.
	 **/
	
	public NSIndexPath initWithIndex(Integer index) {
		if (index != null) {
			wrappedArrayList.add(index);
		}
		return this;
	}

	/**
	 * @Signature: initWithIndexes:length:
	 * @Declaration : - (instancetype)initWithIndexes:(const NSUInteger [])indexes length:(NSUInteger)length
	 * @Description : Initializes an allocated NSIndexPath object with an index path of a specific length.
	 * @param indexes Array of indexes to make up the index path.
	 * @param length Number of nodes to include in the index path.
	 * @return Return Value Initialized NSIndexPath object with indexes up to length.
	 * @Discussion This method is a designated initializer of NSIndexPath.
	 **/
	
	public NSIndexPath initWithIndexesLength(Integer[] indexs, Integer length) {
		for (int i = 0; i < length.intValue(); i++) {
			this.wrappedArrayList.set(i, indexs[i]);
		}
		return this;
	}

	/**
	 * @Signature: init
	 * @Declaration : - (instancetype)init
	 * @Description : Initializes an allocated NSIndexPath object.
	 * @return Return Value Initialized NSIndexPath object.
	 * @Discussion This method is a designated initializer for NSIndexPath.
	 **/
	@Override
	
	public NSIndexPath init() {
		return new NSIndexPath();
	}

	// Querying Index Paths

	/**
	 * @Signature: indexAtPosition:
	 * @Declaration : - (NSUInteger)indexAtPosition:(NSUInteger)node
	 * @Description : Provides the index at a particular node in the index path.
	 * @param node Index value of the desired node. Node numbering starts at zero.
	 * @return Return Value The index value at node or NSNotFound if the node is outside the range of the index path.
	 **/
	
	public Integer indexAtPosition(Integer node) {
		return wrappedArrayList.get(node.intValue());
	}

	/**
	 * @Signature: indexPathByAddingIndex:
	 * @Declaration : - (NSIndexPath *)indexPathByAddingIndex:(NSUInteger)index
	 * @Description : Provides an index path containing the indexes in the receiving index path and another index.
	 * @param index Index to append to the index path’s indexes.
	 * @return Return Value New NSIndexPath object containing the receiving index path’s indexes and index.
	 **/
	
	public NSIndexPath indexPathByAddingIndex(Integer index) {
		wrappedArrayList.add(index);
		return this;
	}

	/**
	 * @Signature: indexPathByRemovingLastIndex
	 * @Declaration : - (NSIndexPath *)indexPathByRemovingLastIndex
	 * @Description : Provides an index path with the indexes in the receiving index path, excluding the last one.
	 * @return Return Value New index path with the receiving index path’s indexes, excluding the last one.
	 * @Discussion Returns an empty NSIndexPath instance if the receiving index path’s length is 1 or less.
	 **/
	
	public NSIndexPath indexPathByRemovingLastIndex() {
		wrappedArrayList.remove(wrappedArrayList.size() - 1);
		return this;
	}

	/**
	 * @Signature: length
	 * @Declaration : - (NSUInteger)length
	 * @Description : Provides the number of indexes in the index path.
	 * @return Return Value Number of indexes in the index path.
	 **/
	
	public Integer length() {
		if (wrappedArrayList != null) {
			return Integer.valueOf(wrappedArrayList.size());
		}
		return 0;
	}

	/**
	 * @Signature: getIndexes:
	 * @Declaration : - (void)getIndexes:(NSUInteger *)indexes
	 * @Description : Copies the objects contained in the index path into indexes.
	 * @param indexes Pointer to a C array of objects of size at least the length of the index path. On return, the index path’s indexes.
	 * @Discussion It is the developer’s responsibility to allocate the memory for the C array.
	 **/
	
	public void getIndexes(List<Integer> indexes) {
		for (int i = 0; i < wrappedArrayList.size(); i++) {
			indexes.set(i, wrappedArrayList.get(i));
		}
	}

	// Comparing Index Paths

	/**
	 * @Signature: compare:
	 * @Declaration : - (NSComparisonResult)compare:(NSIndexPath *)indexPath
	 * @Description : Indicates the depth-first traversal order of the receiving index path and another index path.
	 * @param indexPath Index path to compare. This value must not be nil. If the value is nil, the behavior is undefined.
	 * @return Return Value The depth-first traversal ordering of the receiving index path and indexPath. NSOrderedAscending: The receiving
	 *         index path comes before indexPath. NSOrderedDescending: The receiving index path comes after indexPath. NSOrderedSame: The
	 *         receiving index path and indexPath are the same index path.
	 **/
	
	public NSComparisonResult compare(NSIndexPath indexPath) {

		if (indexPath.wrappedArrayList.size() == this.getWrappedArrayList().size()) {
			if (this.getWrappedArrayList().get(0).intValue() < indexPath.wrappedArrayList.get(0)
					.intValue()) {
				int i = 1;
				while (this.getWrappedArrayList().get(i).intValue() < indexPath.wrappedArrayList
						.get(i).intValue()) {
					i++;
				}
				if (i == this.getWrappedArrayList().size())
					return NSComparisonResult.NSOrderedAscending;
				else {
					return null;
				}
			}
			if (this.getWrappedArrayList().get(0).intValue() > indexPath.wrappedArrayList.get(0)
					.intValue()) {
				int i = 1;
				while (this.getWrappedArrayList().get(i).intValue() > indexPath.wrappedArrayList
						.get(i).intValue()) {
					i++;
				}
				if (i == this.getWrappedArrayList().size())
					return NSComparisonResult.NSOrderedDescending;
				else {
					return null;
				}
			}
			if (this.getWrappedArrayList().get(0).intValue() == indexPath.wrappedArrayList.get(0)
					.intValue()) {
				int i = 1;
				while (this.getWrappedArrayList().get(i).intValue() == indexPath.wrappedArrayList
						.get(i).intValue()) {
					i++;
				}
				if (i == this.getWrappedArrayList().size())
					return NSComparisonResult.NSOrderedSame;
				else {
					return null;
				}
			}
		}
		return null;
	}

	// UIKit Additions

	// 1
	/**
	 * @Signature: indexPathForItem:inSection:
	 * @Declaration : + (NSIndexPath *)indexPathForItem:(NSInteger)item inSection:(NSInteger)section
	 * @Description : Returns an index-path object initialized with the indexes of a specific item and section in a collection view.
	 **/
	
	public static void indexPathForItemInSection(int item, int section) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @Signature: indexPathForRow:inSection:
	 * @Declaration : + (NSIndexPath *)indexPathForRow:(NSInteger)row inSection:(NSInteger)section
	 * @Description : Returns an index-path object initialized with the indexes of a specific row and section in a table view.
	 **/
	
	public static NSIndexPath indexPathForRowInSection(int row, int section) {
		return null;
	}

	/**
	 * @Declaration : @property (nonatomic, readonly) NSInteger item;
	 * @Description : An index number identifying an item in a section of a collection view. (read-only)
	 * @Discussion The section the item is in is identified by the value of section.
	 **/

	
	public int item;

	
	public int item() {
		return item;
	}

	
	public int getItem() {
		return item;
	}

	
	public void setItem(int item) {
		this.item = item;
	}

	// 6
	/**
	 * @Declaration :  NSInteger row
	 * @Description : An index number identifying a row in a section of a table view. (read-only)
	 * @Discussion The section the row is in is identified by the value of section.
	 **/

	
	public int row;

	
	public int row() {
		return row;
	}

	
	public int getRow() {
		return row;
	}

	
	public void setRow(int row) {
		this.row = row;
	}

	// 7
	/**
	 * @Declaration :  NSInteger section
	 * @Description : An index number identifying a section in a table view or collection view. (read-only)
	 **/

	
	public int section;

	
	public int section() {
		return section;
	}

	
	public int getSection() {
		return section;
	}

	
	public void setSection(int section) {
		this.section = section;
	}

	// Protocols Methods

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
		return wrappedArrayList.iterator().hasNext();
	}

	@Override
	public Object next() {
		return wrappedArrayList.iterator().next();
	}

	@Override
	public void remove() {
		wrappedArrayList.iterator().remove();
	}

	@Override
	public Iterator iterator() {
		return wrappedArrayList.iterator();
	}
}