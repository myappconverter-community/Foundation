
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.java.foundations.utilities.PropertyListParser;
import com.myappconverter.mapping.utils.FunctionPointer;
import com.myappconverter.mapping.utils.InstanceTypeFactory;
import com.myappconverter.mapping.utils.InvokableHelper;
import com.myappconverter.mapping.utils.SerializationUtil;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;



public class NSMutableArray<E extends Object> extends NSArray<E> implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());

    @Override
    public void setWrappedList(List<E> list) {
        super.setWrappedList(list);
    }

    public NSMutableArray(List<E> list) {
        this.wrappedList = list;
    }

    /**
     * @param numItems The initial capacity of the new array.
     * @return Return Value An array initialized with enough memory to hold numItems objects. The returned object might be different than
     * the original receiver.
     * @Signature: initWithCapacity:
     * @Declaration : - (instancetype)initWithCapacity:(NSUInteger)numItems
     * @Description : Returns an array, initialized with enough memory to initially hold a given number of objects.
     * @Discussion Mutable arrays expand as needed; numItems simply establishes the object’s initial capacity. This method is a designated
     * initializer.
     **/
    
    public Object initWithCapacity(int numItems) {
        this.wrappedList = new ArrayList<E>(numItems);
        return this;
    }

    public NSMutableArray<E> init() {
        return (NSMutableArray<E>) super.init();
    }

    // Adding Objects

	/**
	 * @Signature: addObject:
	 * @Declaration : - (void)addObject:(id)anObject
	 * @Description : Inserts a given object at the end of the array.
	 * @param anObject The object to add to the end of the array's content. This value must not be nil. Important: Raises an
	 *            NSInvalidArgumentException if anObject is nil.
	 **/
	
	public void addObject(E anObject) {
		if (anObject == null) {
			throw new IllegalArgumentException("NSInvalidArgumentException : object must not be nil");
		}
		wrappedList.add(anObject);
	}

    /**
     * @Signature: addObjectsFromArray:
     * @Declaration : - (void)addObjectsFromArray:(NSArray *)otherArray
     * @Description : Adds the objects contained in another given array to the end of the receiving array’s content.
	 * @param otherArray An array of objects to add to the end of the receiving array’s content.
     **/
    
    public void addObjectsFromArray(NSArray<E> otherArray) {
        wrappedList.addAll(otherArray.wrappedList);
    }

    /**
	 * @Signature: insertObject:atIndex:
	 * @Declaration : - (void)insertObject:(id)anObject atIndex:(NSUInteger)index
	 * @Description : Inserts a given object into the array's contents at a given index.
     * @param anObject The object to add to the array's content. This value must not be nil. Important: Raises an NSInvalidArgumentException
     *                 if anObject is nil.
     * @param index    The index in the array at which to insert anObject. This value must not be greater than the count of elements in the
     *                 array. Important: Raises an NSRangeException if index is greater than the number of elements in the array.
     * @Discussion If index is already occupied, the objects at index and beyond are shifted by adding 1 to their indices to make room. Note
     * that NSArray objects are not like C arrays. That is, even though you specify a size when you create an array, the
     * specified size is regarded as a “hint�?; the actual size of the array is still 0. This means that you cannot insert an
     * object at an index greater than the current count of an array. For example, if an array contains two objects, its size is
     * 2, so you can add objects at indices 0, 1, or 2. Index 3 is illegal and out of bounds; if you try to add an object at
     * index 3 (when the size of the array is 2), NSMutableArray raises an exception.
     **/
    
    public void insertObjectAtIndex(E anObject, long index) {
        wrappedList.add((int) index, anObject);
    }

    /**
     * @param objects An array of objects to insert into the receiving array.
     * @param indexes The indexes at which the objects in objects should be inserted. The count of locations in indexes must equal the count
     *                of objects. For more details, see the Discussion.
     * @Signature: insertObjects:atIndexes:
     * @Declaration : - (void)insertObjects:(NSArray *)objects atIndexes:(NSIndexSet *)indexes
     * @Description : Inserts the objects in the provided array into the receiving array at the specified indexes.
     * @Discussion Each object in objects is inserted into the receiving array in turn at the corresponding location specified in indexes
     * after earlier insertions have been made.
     **/
    
    public void insertObjectsAtIndexes(NSArray<E> objects, NSIndexSet indexes) {
        if (objects.wrappedList.size() == indexes.getWrappedTreeSet().size()) {
            List<Integer> listIndex = new ArrayList<Integer>(indexes.getWrappedTreeSet());
            int currentIndex = listIndex.get(0);
            int i;
            int count = indexes.getWrappedTreeSet().size();

            for (i = 0; i < count; i++) {
                currentIndex = listIndex.get(i);
                insertObjectAtIndex(objects.wrappedList.get(i), currentIndex);
            }
        } else
            throw new IllegalArgumentException("The count of locations in indexes must equal the count of objects");

    }

    // Removing Objects

    /**
     * @Signature: removeAllObjects
     * @Declaration : - (void)removeAllObjects
     * @Description : Empties the array of all its elements.
     **/
    
    public void removeAllObjects() {
        wrappedList.clear();
    }

    /**
     * @Signature: removeLastObject
     * @Declaration : - (void)removeLastObject
     * @Description : Removes the object with the highest-valued index in the array
     * @Discussion removeLastObject raises an NSRangeException if there are no objects in the array.
     **/
    
    public void removeLastObject() {
        wrappedList.remove(wrappedList.size() - 1);
    }

    /**
     * @param anObject The object to remove from the array.
     * @Signature: removeObject:
     * @Declaration : - (void)removeObject:(id)anObject
     * @Description : Removes all occurrences in the array of a given object.
     * @Discussion This method uses indexOfObject: to locate matches and then removes them by using removeObjectAtIndex:. Thus, matches are
     * determined on the basis of an object’s response to the isEqual: message. If the array does not contain anObject, the
     * method has no effect (although it does incur the overhead of searching the contents).
     **/
    
    public void removeObject(E anObject) {
        wrappedList.remove(anObject);
    }

    /**
     * @param anObject The object to remove from the array's content.
     * @param aRange   The range from which to remove anObject. Important: Raises an NSRangeException if aRange exceeds the bounds of the
     *                 array.
     * @Signature: removeObject:inRange:
     * @Declaration : - (void)removeObject:(id)anObject inRange:(NSRange)aRange
     * @Description : Removes all occurrences within a specified range in the array of a given object.
     * @Discussion Matches are determined on the basis of an object’s response to the isEqual: message. If the array does not contain
     * anObject within aRange, the method has no effect (although it does incur the overhead of searching the contents).
     **/
    
    public void removeObjectInRange(E anObject, NSRange aRange) {
        if ((anObject == null) || (aRange == null))
            return;

        int loc = aRange.location;
        int max = aRange.length + loc;
        for (int i = loc; i < max; i++) {
            if (anObject.equals(wrappedList.get(i))) {
                wrappedList.remove(i);
                i--;
                max = max - 1;
            }
        }
    }

    /**
     * @param index The index from which to remove the object in the array. The value must not exceed the bounds of the array.
     *              Important: Raises an NSRangeException if index is beyond the end of the array.
     * @Signature: removeObjectAtIndex:
     * @Declaration : - (void)removeObjectAtIndex:(NSUInteger)index
     * @Description : Removes the object at index .
     * @Discussion To fill the gap, all elements beyond index are moved by subtracting 1 from their index.
     **/
    
    public void removeObjectAtIndex(long index) {
        wrappedList.remove((int) index);
    }

    /**
     * @param indexes The indexes of the objects to remove from the array. The locations specified by indexes must lie within the bounds of
     *                the array.
     * @Signature: removeObjectsAtIndexes:
     * @Declaration : - (void)removeObjectsAtIndexes:(NSIndexSet *)indexes
     * @Description : Removes the objects at the specified indexes from the array.
     * @Discussion This method is similar to removeObjectAtIndex:, but allows you to efficiently remove multiple objects with a single
     * operation. indexes specifies the locations of objects to be removed given the state of the array when the method is
     * invoked, as illustrated in the following example. NSMutableArray *array = [NSMutableArray arrayWithObjects: @"one", @"a",
     * @"two", @"b", @"three", @"four", nil]; NSMutableIndexSet *indexes = [NSMutableIndexSet indexSetWithIndex:1]; [indexes addIndex:3];
     * [array removeObjectsAtIndexes:indexes]; NSLog(@"array: %@", array); // Output: array: (one, two, three, four) If indexes is
     * nil this method will raise an exception.
     **/
    
    public void removeObjectsAtIndexes(NSIndexSet indexes) {
        if (this.wrappedList.size() >= indexes.getWrappedTreeSet().size()) {
            for (int i = 0; i < indexes.getWrappedTreeSet().size(); i++) {
                removeObjectAtIndex(i);
            }
        } else
            throw new IllegalArgumentException("The count of locations in indexes must equal the count of objects");
    }

    /**
     * @param anObject The object to remove from the array.
     * @Signature: removeObjectIdenticalTo:
     * @Declaration : - (void)removeObjectIdenticalTo:(id)anObject
     * @Description : Removes all occurrences of a given object in the array.
     * @Discussion This method uses the indexOfObjectIdenticalTo: method to locate matches and then removes them by using
     * removeObjectAtIndex:. Thus, matches are determined using object addresses. If the array does not contain anObject, the
     * method has no effect (although it does incur the overhead of searching the contents).
     **/
    
    public void removeObjectIdenticalTo(E anObject) {
        removeObject(anObject);
    }

    /**
     * @param anObject The object to remove from the array within aRange.
     * @param aRange   The range in the array from which to remove anObject. Important: Raises an NSRangeException if aRange exceeds the
     *                 bounds of the array.
     * @Signature: removeObjectIdenticalTo:inRange:
     * @Declaration : - (void)removeObjectIdenticalTo:(id)anObject inRange:(NSRange)aRange
     * @Description : Removes all occurrences of anObject within the specified range in the array.
     * @Discussion This method uses the indexOfObjectIdenticalTo: method to locate matches and then removes them by using
     * removeObjectAtIndex:. Thus, matches are determined using object addresses. If the array does not contain anObject within
     * aRange, the method has no effect (although it does incur the overhead of searching the contents).
     **/
    
    public void removeObjectIdenticalToInRange(E anObject, NSRange aRange) {
        removeObjectInRange(anObject, aRange);
    }

    /**
     * @param otherArray An array containing the objects to be removed from the receiving array.
     * @Signature: removeObjectsInArray:
     * @Declaration : - (void)removeObjectsInArray:(NSArray *)otherArray
     * @Description : Removes from the receiving array the objects in another given array.
     * @Discussion This method is similar to removeObject:, but allows you to efficiently remove large sets of objects with a single
     * operation. If the receiving array does not contain objects in otherArray, the method has no effect (although it does
     * incur the overhead of searching the contents). This method assumes that all elements in otherArray respond to hash and
     * isEqual:.
     **/
    
    public void removeObjectsInArray(NSArray<E> otherArray) {
        wrappedList.removeAll(otherArray.wrappedList);
    }

    /**
     * @param aRange The range of the objects to remove from the array.
     * @Signature: removeObjectsInRange:
     * @Declaration : - (void)removeObjectsInRange:(NSRange)aRange
     * @Description : Removes from the array each of the objects within a given range.
     * @Discussion The objects are removed using removeObjectAtIndex:.
     **/
    
    public void removeObjectsInRange(NSRange aRange) {
        if (aRange == null)
            return;
        if (aRange.location < 0) {
            return;
        }
        for (int i = 0; i < aRange.length; i++) {
            wrappedList.remove(aRange.location);
        }
    }

    /**
     * @param indices A C array of the indices of the objects to remove from the receiving array.
     * @param count   The number of objects to remove from the receiving array.
     * @Signature: removeObjectsFromIndices:numIndices:
     * @Declaration : - (void)removeObjectsFromIndices:(NSUInteger *)indices numIndices:(NSUInteger)count
     * @Description : Removes the specified number of objects from the array, beginning at the specified index. (Deprecated in iOS 4.0. Use
     * removeObjectsAtIndexes: instead.)
     * @Discussion This method is similar to removeObjectAtIndex:, but allows you to efficiently remove multiple objects with a single
     * operation. If you sort the list of indices in ascending order, you will improve the speed of this operation. This method
     * cannot be sent to a remote object with distributed objects.
     **/
    
    
    public void removeObjectsFromIndicesNumIndices(long indices, long count) {
        List<E> removedList;
        removedList = new ArrayList<E>(wrappedList.subList((int) indices, (int) (indices + count)));
        for (E object : removedList) {
            wrappedList.remove(object);
        }
    }

    // Replacing Objects

    /**
     * @param index    The index of the object to be replaced. This value must not exceed the bounds of the array. Important: Raises an
     *                 NSRangeException if index is beyond the end of the array.
     * @param anObject The object with which to replace the object at index index in the array. This value must not be nil.
     *                 Important: Raises an NSInvalidArgumentException if anObject is nil.
     * @Signature: replaceObjectAtIndex:withObject:
     * @Declaration : - (void)replaceObjectAtIndex:(NSUInteger)index withObject:(id)anObject
     * @Description : Replaces the object at index with anObject.
     **/
    
    public void replaceObjectAtIndexWithObject(int index, E withObject) {
        if (withObject == null)
            throw new IllegalArgumentException();
        else if (index > wrappedList.size()) {
            throw new IndexOutOfBoundsException();
        } else if (index == wrappedList.size()) {
            wrappedList.add(withObject);
        } else {
            wrappedList.set(index, withObject);
        }
    }

    /**
     * @param anObject The object with which to replace the object at index index in the array. This value must not be nil.
     *                 Important: Raises an NSInvalidArgumentException if anObject is nil.
     * @param index    The index of the object to be replaced. This value must not exceed the bounds of the array. Important: Raises an
     *                 NSRangeException if index is beyond the end of the array.
     * @Signature: setObject:atIndexedSubscript:
     * @Declaration : - (void)setObject:(id)anObject atIndexedSubscript:(NSUInteger)index
     * @Description : Replaces the object at the index with the new object, possibly adding the object.
     * @Discussion If the index is equal to count the element is added to the end of the array, growing the array.
     **/
    
    public void setObjectAtIndexedSubscript(E anObject, long atIndexedSubscript) {
        if (atIndexedSubscript == wrappedList.size()) {
            this.wrappedList.add(anObject);
            return;
        }
        replaceObjectAtIndexWithObject((int) atIndexedSubscript, anObject);
    }

    /**
     * @param indexes The indexes of the objects to be replaced.
     * @param objects The objects with which to replace the objects in the receiving array at the indexes specified by indexes. The count of
     *                locations in indexes must equal the count of objects.
     * @Signature: replaceObjectsAtIndexes:withObjects:
     * @Declaration : - (void)replaceObjectsAtIndexes:(NSIndexSet *)indexes withObjects:(NSArray *)objects
     * @Description : Replaces the objects in the receiving array at specified locations specified with the objects from a given array.
     * @Discussion The indexes in indexes are used in the same order as the objects in objects. If objects or indexes is nil this method
     * will raise an exception.
     **/
    
    public void replaceObjectsAtIndexesWithObjects(NSIndexSet indexes, NSArray<E> objects) {
        if (indexes == null || objects == null)
            throw new IllegalArgumentException("Arguments must not be null");
        if (objects.wrappedList.size() == indexes.getWrappedTreeSet().size()) {
            List<Integer> listIndex = new ArrayList<Integer>(indexes.getWrappedTreeSet());
            int currentIndex = listIndex.get(0);
            int i;
            int count = indexes.getWrappedTreeSet().size();

            for (i = 0; i < count; i++) {
                currentIndex = listIndex.get(i);
                this.replaceObjectAtIndexWithObject(currentIndex, objects.wrappedList.get(i));
            }
        } else
            throw new IllegalArgumentException("The count of locations in indexes must equal the count of objects");
        // TODO complete implementation
    }

    /**
     * @param aRange     The range of objects to replace in (or remove from) the receiving array.
     * @param otherArray The array of objects from which to select replacements for the objects in aRange.
     * @param otherRange The range of objects to select from otherArray as replacements for the objects in aRange.
     * @Signature: replaceObjectsInRange:withObjectsFromArray:range:
     * @Declaration : - (void)replaceObjectsInRange:(NSRange)aRange withObjectsFromArray:(NSArray *)otherArray range:(NSRange)otherRange
     * @Description : Replaces the objects in the receiving array specified by one given range with the objects in another array specified
     * by another range.
     * @Discussion The lengths of aRange and otherRange don’t have to be equal: if aRange is longer than otherRange, the extra objects in
     * the receiving array are removed; if otherRange is longer than aRange, the extra objects from otherArray are inserted into
     * the receiving array.
     **/
    
    public void replaceObjectsInRangeWithObjectsFromArrayRange(NSRange aRange, NSArray<E> otherArray, NSRange otherRange) {
        if (aRange == null || otherRange == null)
            throw new IllegalArgumentException("Both ranges cannot be null");
        if (otherArray == null)
            throw new IllegalArgumentException("Other array cannot be null");

        int rangeLength = aRange.length;
        int rangeLocation = aRange.location;
        int otherRangeLength = otherRange.length;
        int otherRangeLocation = otherRange.location;

        while (rangeLength > 0 && otherRangeLength > 0) {
            replaceObjectAtIndexWithObject(rangeLocation++, otherArray.objectAtIndex(otherRangeLocation++));
            rangeLength--;
            otherRangeLength--;
        }

        while (otherRangeLength > 0) {
            insertObjectAtIndex(otherArray.objectAtIndex(otherRangeLocation++), rangeLocation++);
            otherRangeLength--;
        }

        while (rangeLength > 0) {
            removeObjectAtIndex(rangeLocation);
            rangeLength--;
        }
    }

    /**
     * @param aRange     The range of objects to replace in (or remove from) the receiving array.
     * @param otherArray The array of objects from which to select replacements for the objects in aRange.
     * @Signature: replaceObjectsInRange:withObjectsFromArray:
     * @Declaration : - (void)replaceObjectsInRange:(NSRange)aRange withObjectsFromArray:(NSArray *)otherArray
     * @Description : Replaces the objects in the receiving array specified by a given range with all of the objects from a given array.
     * @Discussion If otherArray has fewer objects than are specified by aRange, the extra objects in the receiving array are removed. If
     * otherArray has more objects than are specified by aRange, the extra objects from otherArray are inserted into the
     * receiving array.
     **/
    
    public void replaceObjectsInRangeWithObjectsFromArray(NSRange aRange, NSArray<E> otherArray) {
        if (aRange == null)
            return;
        if (aRange.location < 0)
            return;
        int index = aRange.location;
        if (otherArray.count() >= aRange.location + aRange.length) {
            for (int i = 0; i < otherArray.count(); i++) {
                if (index < aRange.location + aRange.length) {
                    replaceObjectAtIndexWithObject(index, otherArray.wrappedList.get(i));
                } else
                    wrappedList.add(otherArray.wrappedList.get(i));
                index++;
            }
        } else {
            int j = 0;
            for (int i = 0; i < otherArray.count(); i++) {
                replaceObjectAtIndexWithObject(index, otherArray.wrappedList.get(i));
                index++;
                j++;
            }
            this.removeObjectsInRange(new NSRange(index, aRange.length - j));
        }
    }

    /**
     * @param otherArray The array of objects with which to replace the receiving array's content.
     * @Signature: setArray:
     * @Declaration : - (void)setArray:(NSArray *)otherArray
     * @Description : Sets the receiving array’s elements to those in another given array.
     **/
    
    public void setArray(NSArray<E> otherArray) {
        removeAllObjects();
        addObjectsFromArray(otherArray);
    }

    // Filtering Content

    /**
     * @param predicate The predicate to evaluate against the array's elements.
     * @Signature: filterUsingPredicate:
     * @Declaration : - (void)filterUsingPredicate:(NSPredicate *)predicate
     * @Description : Evaluates a given predicate against the array’s content and leaves only objects that match
     **/
    
    public void filterUsingPredicate(NSPredicate predicate) {
        for (E object : wrappedList) {
            if (!predicate.evaluateWithObject(object))
                removeObject(object);
        }
    }

    // Rearranging Content

    /**
     * /**
     *
     * @param idx1 The index of the object with which to replace the object at index idx2.
     * @param idx2 The index of the object with which to replace the object at index idx1.
     * @Signature: exchangeObjectAtIndex:withObjectAtIndex:
     * @Declaration : - (void)exchangeObjectAtIndex:(NSUInteger)idx1 withObjectAtIndex:(NSUInteger)idx2
     * @Description : Exchanges the objects in the array at given indices.
     **/
    
    public void exchangeObjectAtIndexWithObjectAtIndex(long idx1, long idx2) {
        E tmp = objectAtIndex((int) idx1);
        replaceObjectAtIndexWithObject((int) idx1, objectAtIndex((int) idx2));
        replaceObjectAtIndexWithObject((int) idx2, tmp);
    }

    /**
     * @param sortDescriptors An array containing the NSSortDescriptor objects to use to sort the receiving array's contents.
     * @Signature: sortUsingDescriptors:
     * @Declaration : - (void)sortUsingDescriptors:(NSArray *)sortDescriptors
     * @Description : Sorts the receiving array using a given array of sort descriptors.
     * @Discussion See NSSortDescriptor for additional information.
     **/
    
    public void sortUsingDescriptors(final NSArray<NSSortDescriptor> sortDescriptors) {
        Collections.sort(this.getWrappedList(), new Comparator() {
            @Override
            public int compare(Object lhs, Object rhs) {
                int ordre = 0;
                for(NSSortDescriptor nsSort :sortDescriptors){
                    if(nsSort != null) {
                        ordre = nsSort.getComparatorObject().compare(lhs, rhs);
                        if (ordre != 0) return ordre;
                    }
                }
                return ordre;
            }
        });
    }

    /**
     * @param cmptr A comparator block.
     * @Signature: sortUsingComparator:
     * @Declaration : - (void)sortUsingComparator:(NSComparator)cmptr
     * @Description : Sorts the array using the comparison method specified by a given NSComparator Block.
     **/
    
    public void sortUsingComparator(final NSComparator<E> cmptr) {
        Collections.sort(this.wrappedList, new Comparator<E>() {
            @Override
            public int compare(E lhs, E rhs) {
                return cmptr.compare(lhs, rhs);
            }
        });
    }

    /**
     * @param opts  A bitmask that specifies the options for the sort (whether it should be performed concurrently and whether it should be
     *              performed stably).
     * @param cmptr A comparator block.
     * @Signature: sortWithOptions:usingComparator:
     * @Declaration : - (void)sortWithOptions:(NSSortOptions)opts usingComparator:(NSComparator)cmptr
     * @Description : Sorts the array using the specified options and the comparison method specified by a given NSComparator Block.
     **/
    
    public void sortWithOptionsUsingComparator(int opts, final NSComparator<E> cmptr) {

        if (opts == NSObjCRuntime.NSSortConcurrent)
            synchronized (wrappedList) {
                Collections.sort(wrappedList, new Comparator<E>() {
                    @Override
                    public int compare(E lhs, E rhs) {
                        return cmptr.compare(lhs, rhs);
                    }
                });
            }
        else
            sortUsingComparator(cmptr);
    }

    /**
     * @param compare The comparison function to use to compare two elements at a time. The function's parameters are two objects to compare
     *                and the context parameter, context. The function should return NSOrderedAscending if the first element is smaller than the
     *                second, NSOrderedDescending if the first element is larger than the second, and NSOrderedSame if the elements are equal.
     * @param context The context argument to pass to the compare function.
     * @Signature: sortUsingFunction:context:
     * @Declaration : - (void)sortUsingFunction:(NSInteger (*)(id, id, void *))compare context:(void *)context
     * @Description : Sorts the array’s elements in ascending order as defined by the comparison function compare.
     * @Discussion This approach allows the comparison to be based on some outside parameter, such as whether character sorting is
     * case-sensitive or case-insensitive.
     **/
    
    public void sortUsingFunctionContext(final FunctionPointer pFunctinPointer, final Object caller) {
        Comparator<Object> cmptr = new Comparator<Object>() {

            @Override
            public int compare(Object lhs, Object rhs) {
                NSComparisonResult result = NSComparisonResult.NSOrderedSame;
                try {

                    Method[] methods = caller.getClass().getMethods();
                    Method function = null;

                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].getName().equals(pFunctinPointer.getFunctionName())) {
                            function = methods[i];
                            break;
                        }
                    }

                    if (function != null)
                        result = (NSComparisonResult) function.invoke(caller, lhs, rhs);

                    if (result == NSComparisonResult.NSOrderedAscending) {
                        return -1;

                    } else if (result == NSComparisonResult.NSOrderedDescending) {
                        return 1;

                    } else if (result == NSComparisonResult.NSOrderedSame) {
                        return 0;

                    }
                } catch (IllegalAccessException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                } catch (IllegalArgumentException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                } catch (InvocationTargetException e) {

                    Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
                }

                return 0;
            }
        };

        Collections.sort(wrappedList, cmptr);
    }

    /**
     * @param comparator A selector that specifies the comparison method to use to compare elements in the array. The comparator message is
     *                   sent to each object in the array and has as its single argument another object in the array. The comparator method should
     *                   return NSOrderedAscending if the array is smaller than the argument, NSOrderedDescending if the array is larger than the
     *                   argument, and NSOrderedSame if they are equal.
     * @Signature: sortUsingSelector:
     * @Declaration : - (void)sortUsingSelector:(SEL)comparator
     * @Description : Sorts the array’s elements in ascending order, as determined by the comparison method specified by a given selector.
     **/
    
    public void sortUsingSelector(final SEL comparator) {
        if (comparator != null && !this.getWrappedList().isEmpty()
                && comparator.getMethodName() != null) {
            final Method m = InvokableHelper.getMethod(this.objectAtIndex(0),
                    comparator.getMethodName(), this.objectAtIndex(0));
            if (m != null)
                Collections.sort(this.wrappedList, new Comparator<E>() {
                    @Override
                    public int compare(E lhs, E rhs) {
                        NSComparisonResult indice = NSComparisonResult.NSOrderedSame;
                        try {
                            indice = (NSComparisonResult) m.invoke(lhs, rhs);
                        } catch (IllegalAccessException e) {
                            LOGGER.info(e.getMessage());
                        } catch (InvocationTargetException e) {
                            LOGGER.info(e.getMessage());
                        }
                        if (indice == NSComparisonResult.NSOrderedSame) {
                            return 0;
                        } else if (indice == NSComparisonResult.NSOrderedDescending) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
        }
    }

    // Overrided

    @Override
    public int count() {
        return wrappedList.size();
    }

    /***
     * KV
     ***/

    @Override
    public NSArray<E> immutableClone() {
        return null;
    }

    public NSMutableArray(int size) {
    }

    public NSMutableArray() {
        super();
    }

    public NSMutableArray(NSArray<E> array) {
        wrappedList = new ArrayList<E>(array.getWrappedList());
    }

    /**
     * for iteration
     **/

    @Override
    public Iterator<E> iterator() {
        return getWrappedList().iterator();
    }

    @Override
    public boolean hasNext() {
        return getWrappedList().iterator().hasNext();
    }

    @Override
    public E next() {
        return getWrappedList().iterator().next();
    }

    @Override
    public void remove() {
        getWrappedList().iterator().remove();
    }


	
	public static NSMutableArray arrayWithContentsOfFile(NSString aPath) {
		NSString compentExt = aPath.lastPathComponent();
		if(aPath.getWrappedString().contains("asset")){
			try {
				Object obj = PropertyListParser.parse(compentExt.getWrappedString());
				if(obj != null)
					if( obj instanceof NSArray)
						return new NSMutableArray((NSArray)obj);
					else if(obj instanceof NSMutableArray)
						return (NSMutableArray)obj;
				return null;
			} catch (Exception e) {
                LOGGER.info(e.getMessage());
			}
		}
		else{
			Object obj = SerializationUtil.retrieveObject(compentExt.getWrappedString());
			return obj != null && obj instanceof List ? new NSMutableArray((List)obj) : null;
		}
		return null;
	}


	
	public static <E> NSMutableArray<E> arrayWithCapacity(Class clazz, int capacity) {
		NSMutableArray<E> array = (NSMutableArray<E>) InstanceTypeFactory.getInstance(clazz);
		array.wrappedList = new ArrayList<E>(capacity);
		return array;
	}


	
	public static NSMutableArray arrayWithContentsOfURL(NSURL aURL) {
		try {
			Object obj = PropertyListParser.parse(aURL.getFileName());
			if(obj != null)
				if( obj instanceof NSArray)
					return new NSMutableArray((NSArray)obj);
				else if(obj instanceof NSMutableArray)
					return (NSMutableArray)obj;
			return null;
		} catch (Exception e) {
            LOGGER.info(e.getMessage());
		}
		return null;
	}


}