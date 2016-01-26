
package com.myappconverter.java.foundations;

import com.myappconverter.java.foundations.NSObjCRuntime.NSComparisonResult;
import com.myappconverter.java.foundations.constants.NSComparator;
import com.myappconverter.java.foundations.protocols.NSCoding;
import com.myappconverter.java.foundations.protocols.NSCopying;
import com.myappconverter.java.foundations.protocols.NSSecureCoding;
import com.myappconverter.mapping.utils.GenericComparator;
import com.myappconverter.mapping.utils.InstanceTypeFactory;

import android.util.Log;

import java.util.Comparator;
import java.util.logging.Logger;



public class NSSortDescriptor extends NSObject implements NSCopying, NSSecureCoding {

	private static final Logger LOGGER = Logger.getLogger(NSBundle.class.getName());
	private NSString mProperty;
	private boolean mAscending;
	private SEL mSelector;
	NSComparator mComparator;
	private Object mReversedSortDescriptor;
	private Comparator comparatorObject;


	public NSSortDescriptor() {
		super();
	}

	public NSSortDescriptor(NSString property, boolean isAscending) {
		this.mProperty = property;
		this.mAscending = isAscending;
	}

	public NSSortDescriptor(NSString property, boolean isAscending, SEL selector) {
		super();
		this.mProperty = property;
		this.mAscending = isAscending;
		this.mSelector = selector;
	}

	public NSSortDescriptor(NSString property, boolean isAscending, NSComparator cmptr) {
		super();
		this.mProperty = property;
		this.mAscending = isAscending;
		this.mComparator = cmptr;
	}


	/**
	 * @Signature: sortDescriptorWithKey:ascending:
	 * @Declaration : + (id)sortDescriptorWithKey:(NSString *)key ascending:(BOOL)ascending
	 * @Description : Creates and returns an NSSortDescriptor with the specified key and ordering.
	 * @param key The property key to use when performing a comparison. In the comparison, the property is accessed using key-value coding
	 *            (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @return Return Value An NSSortDescriptor initialized with the specified key and ordering.
	 **/

	public static NSSortDescriptor sortDescriptorWithKeyAscending(Class clazz, NSString key, boolean ascending) {
		NSSortDescriptor sortDescriptor = (NSSortDescriptor) InstanceTypeFactory.getInstance(clazz);
		sortDescriptor.mProperty = key;
		sortDescriptor.mAscending = ascending;
		sortDescriptor.comparatorObject = new GenericComparator(key.getWrappedString(), ascending);
		return sortDescriptor;
	}

	/**
	 * @Signature: initWithKey:ascending:
	 * @Declaration : - (id)initWithKey:(NSString *)keyPath ascending:(BOOL)ascending
	 * @Description : Returns an NSSortDescriptor object initialized with a given property key path and sort order, and with the default
	 *              comparison selector.
	 * @param keyPath The property key to use when performing a comparison. In the comparison, the property is accessed using key-value
	 *            coding (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @return Return Value An NSSortDescriptor object initialized with the property key path specified by keyPath, sort order specified by
	 *         ascending, and the default comparison selector (compare:).
	 **/

	public NSSortDescriptor initWithKeyAscending(NSString keyPath, boolean ascending) {
		this.mProperty = keyPath;
		this.mAscending = ascending;
		this.comparatorObject = new GenericComparator(keyPath.getWrappedString(), ascending);
		this.mSelector = null;
		this.mComparator = null;
		return this;
	}

	/**
	 * @Signature: sortDescriptorWithKey:ascending:selector:
	 * @Declaration : + (id)sortDescriptorWithKey:(NSString *)key ascending:(BOOL)ascending selector:(SEL)selector
	 * @Description : Creates an NSSortDescriptor with the specified ordering and comparison selector.
	 * @param key The property key to use when performing a comparison. In the comparison, the property is accessed using key-value coding
	 *            (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @param selector The method to use when comparing the properties of objects, for example caseInsensitiveCompare: or localizedCompare:.
	 *            The selector must specify a method implemented by the value of the property identified by keyPath. The selector used for
	 *            the comparison is passed a single parameter, the object to compare against self, and must return the appropriate
	 *            NSComparisonResult constant. The selector must have the same method signature as: -
	 *            (NSComparisonResult)localizedCompare:(NSString *)aString
	 * @return Return Value An NSSortDescriptor object initialized with the property key path specified by keyPath, sort order specified by
	 *         ascending, and the selector specified by selector.
	 **/

	public static NSSortDescriptor sortDescriptorWithKeyAscendingSelector(final NSString key, boolean ascending, final SEL selector) {
		NSSortDescriptor sort =  new NSSortDescriptor(key, ascending, selector);
		sort.comparatorObject = new GenericComparator(key.getWrappedString(), ascending, selector);
		return sort;
	}



	/**
	 * @Signature: sortDescriptorWithKey:ascending:selector:
	 * @Declaration : + (id)sortDescriptorWithKey:(NSString *)key ascending:(BOOL)ascending selector:(SEL)selector
	 * @Description : Creates an NSSortDescriptor with the specified ordering and comparison selector.
	 * @param key The property key to use when performing a comparison. In the comparison, the property is accessed using key-value coding
	 *            (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @param selector The method to use when comparing the properties of objects, for example caseInsensitiveCompare: or localizedCompare:.
	 *            The selector must specify a method implemented by the value of the property identified by keyPath. The selector used for
	 *            the comparison is passed a single parameter, the object to compare against self, and must return the appropriate
	 *            NSComparisonResult constant. The selector must have the same method signature as: -
	 *            (NSComparisonResult)localizedCompare:(NSString *)aString
	 * @return Return Value An NSSortDescriptor object initialized with the property key path specified by keyPath, sort order specified by
	 *         ascending, and the selector specified by selector.
	 **/

	public Object initWithKeyAscendingSelector(NSString keyPath, boolean ascending, SEL selector) {
		this.mProperty = keyPath;
		this.mAscending = ascending;
		this.mSelector = selector;
		this.comparatorObject = new GenericComparator(keyPath.getWrappedString(), ascending, selector);
		this.mComparator = null;
		return this;
	}

	/**
	 * @Signature: sortDescriptorWithKey:ascending:comparator:
	 * @Declaration : + (id)sortDescriptorWithKey:(NSString *)key ascending:(BOOL)ascending comparator:(NSComparator)cmptr
	 * @Description : Creates and returns an NSSortDescriptor object initialized to do with the given ordering and comparator block.
	 * @param key The property key to use when performing a comparison. In the comparison, the property is accessed using key-value coding
	 *            (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @param cmptr A comparator block.
	 * @return Return Value An NSSortDescriptor initialized with the specified key, ordering and comparator.
	 **/

	public static Object sortDescriptorWithKeyAscendingComparator(NSString key, boolean ascending, NSComparator cmptr) {
		NSSortDescriptor sort =  new NSSortDescriptor(key, ascending, cmptr);
		sort.comparatorObject = new GenericComparator(key.getWrappedString(), ascending, cmptr);
		return sort;
	}

	/**
	 * @Signature: initWithKey:ascending:comparator:
	 * @Declaration : - (id)initWithKey:(NSString *)key ascending:(BOOL)ascending comparator:(NSComparator)cmptr
	 * @Description : Returns an NSSortDescriptor object initialized to do with the given ordering and comparator block.
	 * @param key The property key to use when performing a comparison. In the comparison, the property is accessed using key-value coding
	 *            (see Key-Value Coding Programming Guide).
	 * @param ascending YES if the receiver specifies sorting in ascending order, otherwise NO.
	 * @param cmptr A comparator block.
	 * @return Return Value An NSSortDescriptor initialized with the specified key, ordering and comparator.
	 **/

	public Object initWithKeyAscendingComparator(NSString key, boolean ascending, NSComparator cmptr) {
		this.mSelector = null;
		this.mProperty = key;
		this.mAscending = ascending;
		this.mComparator = cmptr;
		this.comparatorObject = new GenericComparator(key.getWrappedString(), ascending, cmptr);
		return this;

	}

	/**
	 * @Signature: ascending
	 * @Declaration : - (BOOL)ascending
	 * @Description : Returns a Boolean value that indicates whether the receiver specifies sorting in ascending order.
	 * @return Return Value YES if the receiver specifies sorting in ascending order, otherwise NO.
	 **/

	public boolean ascending() {
		return mAscending;

	}

	/**
	 * Returns the receiver’s property key path. - (NSString *)key
	 *
	 * @return The receiver’s property key path.
	 * @Discussion This key path specifies the property that is compared during sorting.
	 */


	public NSString key() {
		return mProperty;

	}

	/**
	 * Returns the selector the receiver specifies to use when comparing objects. - (SEL)selector
	 *
	 * @return The selector the receiver specifies to use when comparing objects.
	 */

	public SEL selector() {
		return mSelector;

	}

	/**
	 * @Signature: compareObject:toObject:
	 * @Declaration : - (NSComparisonResult)compareObject:(id)object1 toObject:(id)object2
	 * @Description : Returns an NSComparisonResult value that indicates the ordering of two given objects.
	 * @param object1 The object to compare with object2. This object must have a property accessible using the key-path specified by key.
	 *            This value must not be nil. If the value is nil, the behavior is undefined and may change in future versions of OS X.
	 * @param object2 The object to compare with object1. This object must have a property accessible using the key-path specified by key.
	 *            This value must not be nil. If the value is nil, the behavior is undefined and may change in future versions of OS X.
	 * @return Return Value NSOrderedAscending if object1 is less than object2, NSOrderedDescending if object1 is greater than object2, or
	 *         NSOrderedSame if object1 is equal to object2.
	 * @Discussion The ordering is determined by comparing, using the selector specified selector, the values of the properties specified by
	 *             key of object1 and object2.
	 **/

	public NSComparisonResult compareObjectToObject(Object object1, Object object2) {

		Object objectInstance = new Object();

		int nameComp = 0;
		try {

			Object propertyType1 = object1.getClass().getField(getProperty()).getType();
			Object propertyType2 = object2.getClass().getField(getProperty()).getType();

			if (propertyType1 instanceof String && propertyType2 instanceof String && propertyType1.equals(propertyType2)) {

				nameComp = ((String) object1.getClass().getField(getProperty()).get(objectInstance)).compareTo((String) object2.getClass()
						.getField(getProperty()).get(objectInstance));
			} else if (propertyType1 instanceof Integer && propertyType2 instanceof Integer && propertyType1.equals(propertyType2)) {

				int val1 = (Integer) object1.getClass().getField(getProperty()).get(objectInstance);
				int val2 = (Integer) object2.getClass().getField(getProperty()).get(objectInstance);

				if (val1 < val2) {
					nameComp = -1;
				} else if (val1 > val2) {
					nameComp = 1;
				} else if (val1 == val2) {
					nameComp = 0;
				}
			}
		} catch (IllegalAccessException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (IllegalArgumentException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		} catch (NoSuchFieldException e) {

			Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
		}

		if (nameComp == 1) {

			return NSComparisonResult.NSOrderedDescending;

		} else if (nameComp == 0) {

			return NSComparisonResult.NSOrderedSame;

		} else {
			return NSComparisonResult.NSOrderedAscending;
		}

	}

	/**
	 * @Signature: reversedSortDescriptor
	 * @Declaration : - (id)reversedSortDescriptor
	 * @Description : Returns a copy of the receiver with the sort order reversed.
	 * @return Return Value A copy of the receiver with the sort order reversed
	 **/

	public Object getReversedSortDescriptor() {
		try {
			NSSortDescriptor reverseDescriptor = (NSSortDescriptor) this.clone();
			reverseDescriptor.mAscending = !this.mAscending;
			mReversedSortDescriptor = reverseDescriptor;
			return mReversedSortDescriptor ;
		} catch (CloneNotSupportedException e) {
			LOGGER.info(String.valueOf(e));
			return null;
		}
	}


	public String getProperty() {
		return mProperty.getWrappedString();
	}

	public boolean isAscending() {
		return mAscending;
	}

	public SEL getSelector() {
		return mSelector;
	}

	public NSComparator getComparator() {
		return mComparator;
	}

	public Comparator getComparatorObject() {
		return comparatorObject;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Class clazz = this.getClass();
		NSSortDescriptor obj = (NSSortDescriptor) InstanceTypeFactory.getInstance(clazz);
		obj.mProperty = this.mProperty;
		obj.mAscending = this.mAscending;
		obj.mSelector = this.mSelector;
		obj.mComparator = this.mComparator;
		obj.comparatorObject = this.comparatorObject;
		return this;
	}

	/**
	 * Force a sort descriptor that was securely decoded to allow evaluation. - (void)allowEvaluation
	 *
	 * @Discussion When securely decoding an NSSortDescriptor object encoded using NSSecureCoding, evaluation is disabled because it is
	 *             potentially unsafe to evaluate descriptors you get out of an archive. Before you enable evaluation, you should validate
	 *             key paths, selectors, etc to ensure no erroneous or malicious code will be executed. Once you’ve preflighted the sort
	 *             descriptor, you can enable the receiver for evaluation by calling allowEvaluation.
	 */


	public void allowEvaluation() {

	}

	/**
	 * Creates and returns an NSComparator for the sort descriptor. - (NSComparator)comparator
	 *
	 * @return An NSComparator object representing the sort descriptor.
	 */

	public NSComparator comparator() {
		return mComparator;
	}

	private class ImplNSComparator<T> extends NSComparator<T> {

		@Override
		public int compare(T obj1, T obj2) {

			Object objectInstance = new Object();

			int nameComp = 0;
			try {

				Object propertyType1 = obj1.getClass().getField(getProperty()).getType();
				Object propertyType2 = obj2.getClass().getField(getProperty()).getType();

				if (propertyType1 instanceof String && propertyType2 instanceof String && propertyType1.equals(propertyType2)) {

					nameComp = ((String) obj1.getClass().getField(getProperty()).get(objectInstance)).compareTo((String) obj2.getClass()
							.getField(getProperty()).get(objectInstance));
				} else if (propertyType1 instanceof Integer && propertyType2 instanceof Integer && propertyType1.equals(propertyType2)) {

					int val1 = (Integer) obj1.getClass().getField(getProperty()).get(objectInstance);
					int val2 = (Integer) obj2.getClass().getField(getProperty()).get(objectInstance);

					if (val1 < val2) {
						nameComp = -1;
					} else if (val1 > val2) {
						nameComp = 1;
					} else if (val1 == val2) {
						nameComp = 0;
					}
				}
			} catch (IllegalAccessException e) {

				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (IllegalArgumentException e) {

				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			} catch (NoSuchFieldException e) {

				Log.d("Exception ", "Message :" + e.getMessage() + "\n StackTrace: " + Log.getStackTraceString(e));
			}

			return nameComp;
		}

	}

	@Override
	public boolean supportsSecureCoding() {
		return false;
	}

	@Override
	public NSObject copyWithZone(NSZone zone) {

		return null;
	}

	@Override
	public void encodeWithCoder(NSCoder encoder) {
	}

	@Override
	public NSCoding initWithCoder(NSCoder decoder) {
		return null;
	}

}